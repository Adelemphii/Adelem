package me.adelemphii.adelem;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import me.adelemphii.adelem.botinstance.TwitchBot;
import me.adelemphii.adelem.util.Configuration;

import java.io.InputStream;

public class Core {

    public static Configuration config;

    public static TwitchBot twitchBot;

    public static void main(String[] args) {
        loadConfiguration();

        // Initialize Twitch Bot
        twitchBot = new TwitchBot();
        twitchBot.registerEvents();

        twitchBot.start();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            twitchBot.stop();
            System.out.println();

            System.out.println("Shutting down...");
        }, "Shutdown-thread"));
    }

    private static void loadConfiguration() {
        try {
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            InputStream is = loader.getResourceAsStream("config.yml");

            ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
            config = mapper.readValue(is, Configuration.class);
            is.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Unable to load Configuration ... Exiting.");
            System.exit(1);
        }
    }
}
