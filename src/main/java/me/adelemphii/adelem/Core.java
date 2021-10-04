package me.adelemphii.adelem;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import java.io.IOException;
import me.adelemphii.adelem.botinstance.TwitchBot;
import me.adelemphii.adelem.commands.CommandLockDown;
import me.adelemphii.adelem.menus.Menu;
import me.adelemphii.adelem.util.Configuration;

import javax.swing.*;
import java.io.InputStream;
import org.jetbrains.annotations.Nullable;

public final class Core {

    public static void main(String @Nullable [] args) {
        new Core(args);
    }

    private Configuration config;
    private TwitchBot twitchBot;
    private Menu consoleMenu;
    private String channelChosen;
    private CommandLockDown lockDown;

    public Core(String @Nullable[] args) {
        loadConfiguration();
        registerTwitchBot();
        registerFeel();
        addShutdownHooks();
    }

    private void registerTwitchBot() {
        twitchBot = new TwitchBot(this);
        twitchBot.registerEvents();
        twitchBot.start();
    }

    private void registerFeel() {
        lockDown = new CommandLockDown(this);
        if (config.getConsole()) {
            try {
                consoleMenu = new Menu(this, lockDown);
                consoleMenu.setVisible(true);
            } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private void addShutdownHooks() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            twitchBot.stop();
            System.out.println();
            System.out.println("Shutting down...");
        }, "Shutdown-thread"));
    }

    private void loadConfiguration() {
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

    public String getChannelChosen() {
        return channelChosen;
    }

    public void setChannelChosen(String channelChosen) {
        this.channelChosen = channelChosen;
    }

    public Configuration getConfig() {
        return config;
    }

    public void setConfig(Configuration config) {
        this.config = config;
    }

    public TwitchBot getTwitchBot() {
        return twitchBot;
    }

    public void setTwitchBot(TwitchBot twitchBot) {
        this.twitchBot = twitchBot;
    }

    public Menu getConsoleMenu() {
        return consoleMenu;
    }

    public void setConsoleMenu(Menu consoleMenu) {
        this.consoleMenu = consoleMenu;
    }

    public CommandLockDown getLockDown() {
        return lockDown;
    }
}
