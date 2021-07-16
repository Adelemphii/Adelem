package me.adelemphii.adelem;

import me.adelemphii.adelem.instances.DiscordBot;
import me.adelemphii.adelem.instances.TwitchBot;

public class Core {

    public static void main(String[] args) {
        // Initialize Discord Bot
        DiscordBot discordBot = new DiscordBot();
        discordBot.start();

        // Initialize Twitch Bot
        TwitchBot twitchBot = new TwitchBot();
        twitchBot.registerEvents();
        twitchBot.start();

    }
}
