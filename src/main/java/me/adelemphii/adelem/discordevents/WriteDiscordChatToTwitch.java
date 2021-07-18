package me.adelemphii.adelem.discordevents;

import me.adelemphii.adelem.Core;
import me.adelemphii.adelem.instances.TwitchBot;
import me.adelemphii.adelem.util.Configuration;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WriteDiscordChatToTwitch implements MessageCreateListener {

    Configuration config = Core.config;

    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        if(event.getChannel().getId() != 865687218742624286L) return;

        if(event.getMessageAuthor().isBotUser()) return;
        if(event.getMessageAuthor().isWebhook()) return;

        String author = event.getMessageAuthor().getDisplayName();
        String authorFormatted = author.replace('/', ' ');

        String formattedMessage = "[Discord] " + authorFormatted + ": " + event.getMessage().getContent();

        Pattern pattern = Pattern.compile("([\"'])(?:(?=(\\\\?))\\2.)*?\\1");
        Matcher matcher = pattern.matcher(formattedMessage);

        String match = "";
        while(matcher.find()) {
            match = matcher.group();
        }

        for(String channel : config.getChannels()) {
            if(match.equalsIgnoreCase("\"" + channel + "\"")) {
                TwitchBot.client.getChat().sendMessage(channel, formattedMessage.replaceAll(pattern.pattern(), ""));
                return;
            }
        }
    }
}
