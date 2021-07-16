package me.adelemphii.adelem;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import me.adelemphii.adelem.instances.DiscordBot;
import me.adelemphii.adelem.instances.TwitchBot;
import me.adelemphii.adelem.util.Configuration;

import java.io.InputStream;

public class Core {

    public static Configuration config;

    public static DiscordBot discordBot;

    public static void main(String[] args) {
        loadConfiguration();

        // Initialize Discord Bot
        discordBot = new DiscordBot();
        discordBot.start();

        // Initialize Twitch Bot
        TwitchBot twitchBot = new TwitchBot();
        twitchBot.registerEvents();
        twitchBot.start();

    }

    private static void loadConfiguration() {
        try {
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            InputStream is = loader.getResourceAsStream("config.yml");

            ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
            config = mapper.readValue(is, Configuration.class);
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Unable to load Configuration ... Exiting.");
            System.exit(1);
        }
    }
}
