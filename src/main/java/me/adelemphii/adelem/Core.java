package me.adelemphii.adelem;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import java.io.IOException;
import me.adelemphii.adelem.botinstance.TwitchBot;
import me.adelemphii.adelem.menus.Menu;
import me.adelemphii.adelem.util.Configuration;

import javax.swing.*;
import java.io.InputStream;

public class Core {

    public static Configuration config;
    public static TwitchBot twitchBot;

    public static Menu consoleMenu;

    private static String channelChosen;

    public static void main(String[] args) {
        loadConfiguration();
        registerTwitchBot();
        registerFeel();
        addShutdownHooks();
    }

    private static void registerTwitchBot() {
        // Initialize Twitch Bot
        twitchBot = new TwitchBot();
        twitchBot.registerEvents();

        twitchBot.start();
    }

    private static void registerFeel() {
        if (config.getConsole()) {
            try {
                consoleMenu = new Menu();
                consoleMenu.setVisible(true);
            } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private static void addShutdownHooks() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            twitchBot.stop();
            System.out.println();
            System.out.println("Shutting down...");
        }, "Shutdown-thread"));
    }

    private static void loadConfiguration() {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        try (InputStream is = loader.getResourceAsStream("config.yml")) {
            ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
            config = mapper.readValue(is, Configuration.class);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Unable to load Configuration ... Exiting.");
            System.exit(1);
        }
    }

    public static String getChannelChosen() {
        return channelChosen;
    }

    public static void setChannelChosen(String channelChosen) {
        Core.channelChosen = channelChosen;
    }

}
