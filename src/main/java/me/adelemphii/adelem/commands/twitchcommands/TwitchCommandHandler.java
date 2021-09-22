package me.adelemphii.adelem.commands.twitchcommands;

import com.github.philippheuer.events4j.simple.SimpleEventHandler;
import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;

import java.util.Arrays;
import java.util.List;

public class TwitchCommandHandler {

    /**
     * Register events of this class with the EventManager/EventHandler
     *
     * @param eventHandler SimpleEventHandler
     */
    public TwitchCommandHandler(SimpleEventHandler eventHandler) {
        eventHandler.onEvent(ChannelMessageEvent.class, this::onChannelMessage);
    }

    /**
     * Follow the ChannelMessage Event and run the commands specified
     */
    public void onChannelMessage(ChannelMessageEvent event) {


        List<String> args = Arrays.asList(event.getMessage().split(" "));
        if(args.isEmpty()) return;

        if(args.get(0).equalsIgnoreCase("!following")) {
            CommandFollowAge.runCommand(event);
        }

        if(args.get(0).equalsIgnoreCase("!lockdown")) {
            CommandLockDown.runCommand(event);
        }

    }
}
