package me.adelemphii.adelem.commands.discordcommands;

import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import java.util.Arrays;
import java.util.List;

import static me.adelemphii.adelem.Core.config;

public class DiscordCommandHandler implements MessageCreateListener {

    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        for(Long channel : config.getSpecifyDiscordChannels()) {
            if(event.getChannel().getId() != channel) return;
        }

        if(event.getMessageAuthor().isBotUser()) return;
        if(event.getMessageAuthor().isWebhook()) return;

        List<String> args = Arrays.asList(event.getMessage().getContent().split(" "));
        if(args.isEmpty()) return;

        System.out.println(event.getMessage());

    }
}
