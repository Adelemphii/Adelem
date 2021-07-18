package me.adelemphii.adelem.twitchevents;

import com.github.philippheuer.events4j.simple.SimpleEventHandler;
import com.github.twitch4j.chat.events.channel.HostOffEvent;
import com.github.twitch4j.chat.events.channel.HostOnEvent;
import com.github.twitch4j.events.ChannelGoLiveEvent;
import com.github.twitch4j.events.ChannelGoOfflineEvent;
import me.adelemphii.adelem.Core;
import me.adelemphii.adelem.util.Configuration;
import org.apache.commons.lang.StringUtils;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.message.WebhookMessageBuilder;

public class WriteChannelLiveStatus {

    private final Configuration config = Core.config;

    private final DiscordApi api = Core.discordBot.getApi();

    public WriteChannelLiveStatus(SimpleEventHandler eventHandler) {
        eventHandler.onEvent(ChannelGoLiveEvent.class, this::onChanneGolLive);
        eventHandler.onEvent(ChannelGoOfflineEvent.class, this::onChannelGoOffline);
        eventHandler.onEvent(HostOnEvent.class, this::onChannelHost);
        eventHandler.onEvent(HostOffEvent.class, this::onChannelStopHost);
    }

    public void onChanneGolLive(ChannelGoLiveEvent event) {
        System.out.printf(
                "Channel[%s] - Is now live%n",
                StringUtils.capitalize(event.getChannel().getName())
        );

        if(config.getDiscordBroadcast()) {
            String payload = "Channel[" + StringUtils.capitalize(event.getChannel().getName()) + "] - Is now live.";

            for(String webhook : config.getWebhooks())
                new WebhookMessageBuilder()
                        .setContent(payload)
                        .send(api, webhook);
        }
    }

    public void onChannelGoOffline(ChannelGoOfflineEvent event) {
        System.out.printf(
                "Channel[%s] - Is now offline%n",
                StringUtils.capitalize(event.getChannel().getName())
        );

        if(config.getDiscordBroadcast()) {
            String payload = "Channel[" + StringUtils.capitalize(event.getChannel().getName()) + "] - Is now offline.";

            for(String webhook : config.getWebhooks())
                new WebhookMessageBuilder()
                        .setContent(payload)
                        .send(api, webhook);
        }
    }

    public void onChannelHost(HostOnEvent event) {
        System.out.printf(
                "Channel[%s] - Is now hosting [%s]%n",
                StringUtils.capitalize(event.getChannel().getName()),
                event.getTargetChannel().getName()
        );

        if(config.getDiscordBroadcast()) {
            String payload = "Channel[" + StringUtils.capitalize(event.getChannel().getName()) + "] - Is now hosting " + event.getTargetChannel().getName();

            for(String webhook : config.getWebhooks())
            new WebhookMessageBuilder()
                    .setContent(payload)
                    .send(api, webhook);
        }
    }

    public void onChannelStopHost(HostOffEvent event) {
        System.out.printf(
                "Channel[%s] - Is no longer hosting anyone%n",
                StringUtils.capitalize(event.getChannel().getName())
        );

        if(config.getDiscordBroadcast()) {
            String payload = "Channel[" + StringUtils.capitalize(event.getChannel().getName()) + "] - Is no longer hosting anyone.";
            
            for(String webhook : config.getWebhooks())
                new WebhookMessageBuilder()
                        .setContent(payload)
                        .send(api, webhook);
        }
    }
}
