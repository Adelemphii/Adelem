package me.adelemphii.adelem.commands;

import me.adelemphii.adelem.util.MineStats;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.Embed;
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
        System.out.println("Message (" + event.getMessageAuthor() + ", content: " + msg.getContent() + ")");

        final Long MINECRAFT_CHANNEL_ID = 844913252597235723L;
        final Long TEST_CHANNEL_ID = 844958913178370132L;

        // Prevent people from using the command outside my BT channel and #minecraft-whitelist

        if(msg.getChannel().getId() != MINECRAFT_CHANNEL_ID && msg.getChannel().getId() != TEST_CHANNEL_ID) { return; }

        if(!msg.getContent().toLowerCase().startsWith("wifey")) { return; }

        if(msg.getContent().equalsIgnoreCase("wifeyStatus")) {

            MineStats ms = new MineStats("server.fairy.land", 25572);

            if(ms.isServerUp()) {

                String onlinePlayers = Integer.toString(ms.getCurrentPlayers());
                String maxPlayers = Integer.toString(ms.getMaximumPlayers());

                EmbedBuilder embed = new EmbedBuilder()
                        .setTitle("Server Status")
                        .addField("MoTD", ms.getMotd())
                        .addField("Server IP", ms.getAddress() + ":" + ms.getPort())
                        .addField("Server Version", "1.16.5")
                        .addField("Capacity", onlinePlayers + "/" + maxPlayers)
                        .addInlineField("Whitelist Status", "20/20 - FULL")
                        .setThumbnail(new File("./src/main/resources/server-icon.png"))
                        .setColor(Color.GREEN);

                event.getChannel().sendMessage(embed);


            } else {

                EmbedBuilder embed = new EmbedBuilder()
                        .setTitle("Server Status")
                        .addField("Server IP", ms.getAddress() + ":" + ms.getPort())
                        .addField("Server Status", "Offline")
                        .addInlineField("Whitelist Status", "20/20 - FULL")
                        .setImage(new File("./src/main/resources/server-icon.png"))
                        .setColor(Color.RED);

                event.getChannel().sendMessage(embed);
            }
        }
    }
}
