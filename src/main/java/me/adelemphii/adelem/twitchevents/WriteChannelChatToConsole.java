package me.adelemphii.adelem.twitchevents;

import com.github.philippheuer.events4j.simple.SimpleEventHandler;
import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;

public class WriteChannelChatToConsole {

    /**
     * Register events of this class with the EventManager/EventHandler
     *
     * @param eventHandler SimpleEventHandler
     */
    public WriteChannelChatToConsole(SimpleEventHandler eventHandler) {
        eventHandler.onEvent(ChannelMessageEvent.class, this::onChannelMessage);
    }

    /**
     * Subscribe to the ChannelMessage Event and write the output to the console
     */
    public void onChannelMessage(ChannelMessageEvent event) {
        String author = event.getMessageEvent().getTagValue("display-name").orElse(event.getUser().getName());

        System.out.printf(
                "[Twitch] Channel [%s] - User[%s] - Message [%s]%n" +
                        "User ID[%s]%n",
                event.getChannel().getName(),
                author,
                event.getMessage(),
                event.getUser().getId()
        );
    }

}