package me.adelemphii.adelem.commands;

import com.github.philippheuer.events4j.simple.SimpleEventHandler;
import com.github.twitch4j.TwitchClient;
import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;
import me.adelemphii.adelem.Core;

import java.util.Arrays;
import java.util.List;
import org.jetbrains.annotations.NotNull;

public class CommandHandler {

    private final TwitchClient client;
    private final CommandLockDown lockDown;

    /**
     * Register events of this class with the EventManager/EventHandler
     *
     * @param eventHandler SimpleEventHandler
     */
    public CommandHandler(@NotNull Core core, @NotNull SimpleEventHandler eventHandler, @NotNull final CommandLockDown lockDown) {
        this.client = core.getTwitchBot().getClient();
        this.lockDown = lockDown;
        eventHandler.onEvent(ChannelMessageEvent.class, this::onChannelMessage);
    }

    /**
     * Follow the ChannelMessage Event and run the commands specified
     */
    public void onChannelMessage(@NotNull ChannelMessageEvent event) {

        List<String> args = List.of(event.getMessage().split(" "));
        if (args.isEmpty()) return;

        if (args.get(0).equalsIgnoreCase("!lockdown")) {
            lockDown.runCommand(event);
        }

    }
}
