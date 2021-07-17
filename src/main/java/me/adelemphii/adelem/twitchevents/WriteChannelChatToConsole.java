package me.adelemphii.adelem.twitchevents;

import com.github.philippheuer.events4j.simple.SimpleEventHandler;
import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;
import me.adelemphii.adelem.Core;
import me.adelemphii.adelem.util.Configuration;
import org.apache.commons.lang.StringUtils;
import org.javacord.api.DiscordApi;

import java.util.List;

public class WriteChannelChatToConsole {

    private Configuration config = Core.config;

    private DiscordApi api = Core.discordBot.getApi();

    private List<String> webhooks = config.getWebhooks();

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
        String author = StringUtils.capitalize(event.getUser().getName());

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