package me.adelemphii.adelem.twitchevents;

import com.github.philippheuer.events4j.simple.SimpleEventHandler;
import com.github.twitch4j.chat.events.channel.ChannelMessageActionEvent;
import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;
import me.adelemphii.adelem.Core;
import org.jetbrains.annotations.NotNull;

public class WriteChannelChatToConsole {

    /**
     * Register events of this class with the EventManager/EventHandler
     *
     * @param eventHandler SimpleEventHandler
     */
    public WriteChannelChatToConsole(@NotNull SimpleEventHandler eventHandler) {
        eventHandler.onEvent(ChannelMessageEvent.class, this::onChannelMessage);
        eventHandler.onEvent(ChannelMessageActionEvent.class, this::onChannelActionMessage);
    }

    /**
     * Subscribe to the ChannelMessage Event and write the output to the console
     */
    public void onChannelMessage(@NotNull ChannelMessageEvent event) {
        String author = event.getMessageEvent().getTagValue("display-name").orElse(event.getUser().getName());

        Core.consoleMenu.sendMessageToConsole(event.getChannel().getName(), author, event.getMessage());
    }

    public void onChannelActionMessage(@NotNull ChannelMessageActionEvent event) {
        String author = event.getMessageEvent().getTagValue("display-name").orElse(event.getUser().getName());

        Core.consoleMenu.sendMessageToConsole(event.getChannel().getName(), author,
            "*%s*".formatted(event.getMessage().replace("\u0001", "")));
    }

}