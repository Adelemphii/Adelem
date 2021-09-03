package me.adelemphii.adelem.twitchevents;

import com.github.philippheuer.events4j.simple.SimpleEventHandler;
import com.github.twitch4j.TwitchClient;
import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;
import com.github.twitch4j.helix.domain.UserList;
import me.adelemphii.adelem.Core;
import me.adelemphii.adelem.util.Configuration;
import org.apache.commons.lang.StringUtils;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.message.WebhookMessageBuilder;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WriteChannelChatToDiscord {

    private Configuration config = Core.config;

    private DiscordApi api = Core.discordBot.getApi();

    private List<String> webhooks = config.getWebhooks();

    private TwitchClient client = Core.twitchBot.getClient();

    /**
     * Register events of this class with the EventManager/EventHandler
     *
     * @param eventHandler SimpleEventHandler
     */
    public WriteChannelChatToDiscord(SimpleEventHandler eventHandler) {
        eventHandler.onEvent(ChannelMessageEvent.class, this::onChannelMessage);
    }

    /**
     * Subscribe to the ChannelMessage Event and write the output to the console
     */
    public void onChannelMessage(ChannelMessageEvent event) {
        if(config.getDiscordBroadcast()) {

            String payload = event.getMessage();
            String author = event.getMessageEvent().getTagValue("display-name").orElse(event.getUser().getName());

            String formattedMessage = payload.replaceAll("@everyone", "(I tried to @ everyone)");

            Pattern pattern = Pattern.compile("https?:\\/\\/(www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_\\+.~#?&//=]*)");
            Matcher matcher = pattern.matcher(formattedMessage);

            String link = "";
            while(matcher.find()) {
                link = matcher.group();
            }

            link = "<" + link + ">";

            formattedMessage = formattedMessage.replaceAll(pattern.pattern(), link);

            WebhookMessageBuilder builder = new WebhookMessageBuilder();
            builder.setContent(formattedMessage);

            builder.setDisplayName("[" + event.getChannel().getName() + "] " + author);
            if(getUserProfilePicture(author) == null) {
                for (String webhook : webhooks)
                    builder.send(api, webhook);
            }
            else {
                try {
                    URL url = new URL(getUserProfilePicture(author));
                    builder.setDisplayAvatar(url);

                    for(String webhook : webhooks)
                        builder.send(api, webhook);

                } catch (MalformedURLException e) {
                    System.getLogger(e.getMessage());
                }
            }
        }
    }

    private String getUserProfilePicture(String author) {
        AtomicReference<String> imageUrl = new AtomicReference<>("");

        UserList resultList = client.getHelix().getUsers(null, null, Arrays.asList(author)).execute();
        resultList.getUsers().forEach(user -> {
            if(user.getDisplayName().equalsIgnoreCase(author)) {
                imageUrl.set(user.getProfileImageUrl());
            }
        });
        return imageUrl.toString();
    }

}
