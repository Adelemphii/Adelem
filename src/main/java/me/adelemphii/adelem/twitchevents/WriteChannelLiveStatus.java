package me.adelemphii.adelem.twitchevents;

import com.github.philippheuer.events4j.simple.SimpleEventHandler;
import com.github.twitch4j.chat.events.channel.HostOffEvent;
import com.github.twitch4j.chat.events.channel.HostOnEvent;
import com.github.twitch4j.events.ChannelGoLiveEvent;
import com.github.twitch4j.events.ChannelGoOfflineEvent;
import org.apache.commons.lang.StringUtils;

public class WriteChannelLiveStatus {

    public WriteChannelLiveStatus(SimpleEventHandler eventHandler) {
        eventHandler.onEvent(ChannelGoLiveEvent.class, this::onChanneGolLive);
        eventHandler.onEvent(ChannelGoOfflineEvent.class, this::onChannelGoOffline);
        eventHandler.onEvent(HostOnEvent.class, this::onChannelHost);
        eventHandler.onEvent(HostOffEvent.class, this::onChannelStopHost);
    }

    public void onChanneGolLive(ChannelGoLiveEvent event) {
        System.out.printf(
                "Channel[%s] - Is now live%n",
                StringUtils.upperCase(event.getChannel().getName())
        );
    }

    public void onChannelGoOffline(ChannelGoOfflineEvent event) {
        System.out.printf(
                "Channel[%s] - Is now offline%n",
                StringUtils.upperCase(event.getChannel().getName())
        );
    }

    public void onChannelHost(HostOnEvent event) {
        System.out.printf(
                "Channel[%s] - Is now hosting [%s]%n",
                StringUtils.upperCase(event.getChannel().getName()),
                event.getTargetChannel().getName()
        );
    }

    public void onChannelStopHost(HostOffEvent event) {
        System.out.printf(
                "Channel[%s] - Is no longer hosting anyone%n",
                StringUtils.capitalize(event.getChannel().getName())
        );
    }
}
