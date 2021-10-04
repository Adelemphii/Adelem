package me.adelemphii.adelem.botinstance;

import com.github.philippheuer.credentialmanager.domain.OAuth2Credential;
import com.github.philippheuer.events4j.simple.SimpleEventHandler;
import com.github.twitch4j.TwitchClient;
import com.github.twitch4j.TwitchClientBuilder;
import java.util.Set;
import me.adelemphii.adelem.Core;
import me.adelemphii.adelem.commands.CommandHandler;
import me.adelemphii.adelem.twitchevents.WriteChannelChatToConsole;
import me.adelemphii.adelem.twitchevents.WriteChannelLiveStatus;
import me.adelemphii.adelem.util.Configuration;
import org.jetbrains.annotations.NotNull;

public class TwitchBot {

    private final Core core;
    private final Configuration config;
    private final TwitchClient client;

    public TwitchBot(@NotNull Core core) {

        this.core = core;
        this.config = core.getConfig();

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
        new WriteChannelChatToConsole(core, eventHandler);
        new WriteChannelLiveStatus(core, eventHandler);
        new CommandHandler(core, eventHandler, core.getLockDown());
    }

    public void start() {
        // Connect to all channels
        boolean first = false;
        for (String channel : config.getChannels()) {
            if (!first) {
                core.setChannelChosen(channel);
                first = true;
            }
            client.getChat().joinChannel(channel);
        }
    }

    public void stop() {
        config.getChannels().forEach(channel -> client.getChat().leaveChannel(channel));
        client.getChat().disconnect();
    }

    public TwitchClient getClient() {
        return client;
    }

}
