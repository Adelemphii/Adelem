package me.adelemphii.adelem.discordcommands;

import me.adelemphii.adelem.util.MineStats;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import java.awt.*;
import java.io.File;

public class ServerStatusCommands implements MessageCreateListener {

    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        if(event.getMessageAuthor().isBotUser()) { return; }

        Message msg = event.getMessage();

        final String prefix = "wifey";

        final long MINECRAFT_CHANNEL_ID = 844913252597235723L;
        final long BOT_COMMANDS_ID = 844574813511090226L;
        final long TEST_CHANNEL_ID = 844958913178370132L;
        final long CHAT_CHANNEL_ID = 846209082113392660L;

        // Prevent people from using the command outside my BT channel and #minecraft-whitelist

        if(msg.getChannel().getId() != MINECRAFT_CHANNEL_ID && msg.getChannel().getId() != TEST_CHANNEL_ID
            && msg.getChannel().getId() != BOT_COMMANDS_ID && msg.getChannel().getId() != CHAT_CHANNEL_ID) { return; }
        System.out.println("Message (" + event.getMessageAuthor() + ", content: " + msg.getContent() + ")");

        if(!msg.getContent().toLowerCase().startsWith(prefix)) { return; }

        if(msg.getContent().equalsIgnoreCase("wifeyStatus")) {
            MineStats ms = new MineStats("server.fairy.land", 25572);

            if(ms.isServerUp()) {

                String onlinePlayers = Integer.toString(ms.getCurrentPlayers());
                String maxPlayers = Integer.toString(ms.getMaximumPlayers());

                EmbedBuilder status = new EmbedBuilder()
                        .setTitle("Server Status")

                        .addField("MoTD", ms.getMotd())
                        .addField("Server IP", ms.getAddress() + ":" + ms.getPort())
                        .addField("Server Version", "1.16.5")
                        .addField("Capacity", onlinePlayers + "/" + maxPlayers)
                        .addField("Whitelist Status", "20/20 - FULL")

                        .addInlineField("Playable Time EST", "2pm EST - 6am EST")
                        .addInlineField("Playable Time BST", "7pm BST - 11am BST")

                        .setThumbnail(new File("./src/main/resources/server-icon.png"))
                        .setFooter("Try using 'wifeyRules' to access the rules page!")
                        .setColor(Color.GREEN);

                event.getChannel().sendMessage(status);
            } else {

                EmbedBuilder status = new EmbedBuilder()
                        .setTitle("Server Status")

                        .addField("Server IP", ms.getAddress() + ":" + ms.getPort())
                        .addField("Server Status", "Offline")
                        .addField("Whitelist Status", "20/20 - FULL")

                        .addInlineField("Playable Time EST", "2pm EST - 6am EST")
                        .addInlineField("Playable Time BST", "7pm BST - 11am BST")

                        .setThumbnail(new File("./src/main/resources/server-icon.png"))
                        .setFooter("Try using 'wifeyRules' to access the rules page!")
                        .setColor(Color.RED);

                event.getChannel().sendMessage(status);
            }
        }

        if(msg.getContent().equalsIgnoreCase("wifeyrules")) {

            EmbedBuilder ruleEmbed = new EmbedBuilder()
                    .setTitle("SMP Rules")
                    .addField("Rule 1", "No Griefing/Blowing up bases")
                    .addField("Rule 2", "No spawn camping")
                    .addField("Rule 3", "Spawn Island is a safe zone")

                    .addInlineField("Playable Time EST", "3pm EST - 7am EST")
                    .addInlineField("Playable Time BST", "8pm BST - 12pm BST")

                    .setFooter("Try using 'wifeyStatus' to check the status of the server!")
                    .setColor(Color.PINK);

            event.getChannel().sendMessage(ruleEmbed);
            return;
        }

        if(msg.getContent().equalsIgnoreCase("wifeyhelp")) {

            EmbedBuilder helpEmbed = new EmbedBuilder()
                    .setTitle("AdelemBot Command List")

                    .addField("wifeyStatus", "Displays the status of the SMP server!")
                    .addField("wifeyRules", "Displays the rules of the SMP server!")
                    .addField("wifeyOnline - DM ONLY", "Displays the amount of players online!")
                    .addField("wifeyHelp", "Displays this information box!")
                    .addField("wifeyStats - DM ONLY", "Displays the chosen statistic information!")
                    .addField("Note:", "If you run the non-DM ONLY commands in a server, you get differing information")

                    .setFooter("This bot was made by Adelemphii#6213")

                    .setColor(Color.PINK);


            event.getChannel().sendMessage(helpEmbed);
        }

    }
}
