package me.adelemphii.adelem.commands;

import me.adelemphii.adelem.util.MineStats;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;
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

        // Prevent people from using the command outside my BT channel and #minecraft-whitelist

        if(msg.getChannel().getId() != MINECRAFT_CHANNEL_ID && msg.getChannel().getId() != TEST_CHANNEL_ID
            && msg.getChannel().getId() != BOT_COMMANDS_ID) { return; }
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

                        .addInlineField("Playable Time EST", "3pm EST - 7am EST")
                        .addInlineField("Playable Time BST", "8pm BST - 12pm BST")

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

                        .addInlineField("Playable Time EST", "3pm EST - 7am EST")
                        .addInlineField("Playable Time BST", "8pm BST - 12pm BST")

                        .setThumbnail(new File("./src/main/resources/server-icon.png"))
                        .setFooter("Try using 'wifeyRules' to access the rules page!")
                        .setColor(Color.RED);

                event.getChannel().sendMessage(status);
            }
        }

        if(msg.getContent().equalsIgnoreCase(prefix + "rules")) {

            EmbedBuilder ruleEmbed = new EmbedBuilder()
                    .setTitle("SMP Rules")
                    .addField("Rule 1", "No Griefing w/ TNT or Explosives")
                    .addField("Rule 2", "No spawn camping")

                    .addInlineField("Playable Time EST", "3pm EST - 7am EST")
                    .addInlineField("Playable Time BST", "8pm BST - 12pm BST")

                    .setThumbnail(new File("./src/main/resources/server-icon.png"))
                    .setFooter("Try using 'wifeyStatus' to check the status of the server!")
                    .setColor(Color.PINK);

            event.getChannel().sendMessage(ruleEmbed);

        }

        if(msg.getContent().equalsIgnoreCase(prefix + "help")) {

            EmbedBuilder helpEmbed = new EmbedBuilder()
                    .setTitle("AdelemBot Command List")

                    .addField("wifeyStatus", "Displays the status of the SMP server!")
                    .addField("wifeyRules", "Displays the rules of the SMP server!")
                    .addField("wifeyHelp", "Displays this information box!")

                    .setFooter("This bot was made by Adelemphii#6213")

                    .setColor(Color.PINK);

            event.getChannel().sendMessage("Message sent!");
            User user = event.getMessageAuthor().asUser().get();
            user.sendMessage(helpEmbed);

        }

    }
}
