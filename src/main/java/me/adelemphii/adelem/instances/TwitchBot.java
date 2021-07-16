package me.adelemphii.adelem.instances;

import com.github.philippheuer.credentialmanager.domain.OAuth2Credential;
import com.github.philippheuer.events4j.simple.SimpleEventHandler;
import com.github.twitch4j.TwitchClient;
import com.github.twitch4j.TwitchClientBuilder;
import me.adelemphii.adelem.Core;
import me.adelemphii.adelem.twitchevents.WriteChannelChatToConsole;
import me.adelemphii.adelem.util.Configuration;

public class TwitchBot {

    private final Configuration config = Core.config;

    private final TwitchClient client;

    public TwitchBot() {

        TwitchClientBuilder builder = TwitchClientBuilder.builder();

        OAuth2Credential credential = new OAuth2Credential(
          "twitch",
          config.getCredentials().get("irc")
        );

        // TwitchClient
        client = builder
                .withClientId(config.getApi().get("twitch_client_id"))
                .withClientSecret(config.getApi().get("twitch_client_secret"))
                .withEnableHelix(true)

                // Chat Module
                .withChatAccount(credential)
                .withEnableChat(true)

                .withEnableKraken(true)
                .build();
    }

    public void registerEvents() {
        SimpleEventHandler eventHandler = client.getEventManager().getEventHandler(SimpleEventHandler.class);

        WriteChannelChatToConsole writeChannelChatToConsole = new WriteChannelChatToConsole(eventHandler);
    }

    public void start() {
        // Connect to all channels
        for(String channel : config.getChannels()) {
            client.getChat().joinChannel(channel);
        }
    }

}
