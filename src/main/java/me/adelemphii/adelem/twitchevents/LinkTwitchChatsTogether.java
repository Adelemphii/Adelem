package me.adelemphii.adelem.twitchevents;

import com.github.philippheuer.events4j.simple.SimpleEventHandler;
import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;
import me.adelemphii.adelem.Core;
import me.adelemphii.adelem.util.Configuration;
import org.apache.commons.lang.StringUtils;

public class LinkTwitchChatsTogether {

    private final Configuration config = Core.config;

    /**
     * Register events of this class with the EventManager/EventHandler
     *
     * @param eventHandler SimpleEventHandler
     */
    public LinkTwitchChatsTogether(SimpleEventHandler eventHandler) {
        eventHandler.onEvent(ChannelMessageEvent.class, this::onChannelMessage);
    }

    /**
     * Subscribe to the ChannelMessage Event and write the output to the console
     */
    public void onChannelMessage(ChannelMessageEvent event) {
        if(!config.getChatLink()) return;

        String author = StringUtils.capitalize(event.getUser().getName());
        String fromChat = event.getMessageEvent().getTagValue("display-name").orElse(event.getUser().getName());

        String payload = "[Channel- " + fromChat + "]: " + event.getMessage() + " [Author: " + author + "]";

        for(String channel : event.getTwitchChat().getChannels()) {
            if(channel.equals(event.getChannel().getName())) continue;

            event.getTwitchChat().sendMessage(channel, payload);
        }
    }
}
