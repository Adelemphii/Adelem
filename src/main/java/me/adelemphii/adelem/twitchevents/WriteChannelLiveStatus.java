package me.adelemphii.adelem.twitchevents;

import com.github.philippheuer.events4j.simple.SimpleEventHandler;
import com.github.twitch4j.chat.events.channel.HostOffEvent;
import com.github.twitch4j.chat.events.channel.HostOnEvent;
import com.github.twitch4j.events.ChannelGoLiveEvent;
import com.github.twitch4j.events.ChannelGoOfflineEvent;
import java.util.Map;
import me.adelemphii.adelem.Core;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;

public class WriteChannelLiveStatus {

    private final Core core;

    public WriteChannelLiveStatus(@NotNull Core core, @NotNull SimpleEventHandler eventHandler) {
        this.core = core;
        eventHandler.onEvent(ChannelGoLiveEvent.class, this::onChanneGolLive);
        eventHandler.onEvent(ChannelGoOfflineEvent.class, this::onChannelGoOffline);
        eventHandler.onEvent(HostOnEvent.class, this::onChannelHost);
        eventHandler.onEvent(HostOffEvent.class, this::onChannelStopHost);
    }

    public void onChanneGolLive(@NotNull ChannelGoLiveEvent event) {
        System.out.printf(
                "Channel[%s] - Is now live%n",
                StringUtils.upperCase(event.getChannel().getName())
        );
    }

    public void onChannelGoOffline(@NotNull ChannelGoOfflineEvent event) {
        System.out.printf(
                "Channel[%s] - Is now offline%n",
                StringUtils.upperCase(event.getChannel().getName())
        );
    }

    public void onChannelHost(@NotNull HostOnEvent event) {
        System.out.printf(
                "Channel[%s] - Is now hosting [%s]%n",
                StringUtils.upperCase(event.getChannel().getName()),
                event.getTargetChannel().getName()
        );
    }

    public void onChannelStopHost(@NotNull HostOffEvent event) {
        System.out.printf(
                "Channel[%s] - Is no longer hosting anyone%n",
                StringUtils.capitalize(event.getChannel().getName())
        );
    }
}
