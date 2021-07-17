package me.adelemphii.adelem.discordevents;

import me.adelemphii.adelem.Core;
import me.adelemphii.adelem.instances.TwitchBot;
import me.adelemphii.adelem.util.Configuration;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

public class WriteDiscordChatToTwitch implements MessageCreateListener {

    Configuration config = Core.config;

    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        if(event.getChannel().getId() != 865687218742624286L) return;

        if(event.getMessageAuthor().isBotUser()) return;
        if(event.getMessageAuthor().isWebhook()) return;

        String formattedMessage = "[Discord] " + event.getMessageAuthor().getDisplayName() + ": " + event.getMessage().getContent();

        for(String channel : TwitchBot.client.getChat().getChannels())
            TwitchBot.client.getChat().sendMessage(channel, formattedMessage);


        System.out.printf(
                "Channel [%s] - User[%s] - Message [%s]%n",
                event.getChannel().getId(),
                event.getMessageAuthor(),
                event.getMessage()
        );
    }
}
