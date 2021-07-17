package me.adelemphii.adelem.twitchevents;

import com.github.philippheuer.events4j.simple.SimpleEventHandler;
import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;
import me.adelemphii.adelem.Core;
import me.adelemphii.adelem.util.Configuration;
import org.apache.commons.lang.StringUtils;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.message.WebhookMessageBuilder;

import java.util.List;

public class WriteChannelChatToDiscord {

    private Configuration config = Core.config;

    private DiscordApi api = Core.discordBot.getApi();

    private List<String> webhooks = config.getWebhooks();

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
            String author = StringUtils.capitalize(event.getUser().getName());

            String formattedMessage = payload.replaceAll("@everyone", "(I tried to @ everyone)");

            WebhookMessageBuilder builder = new WebhookMessageBuilder();
            builder.setContent(formattedMessage);
            builder.setDisplayName(author);
            for(String webhook : webhooks)
                builder.send(api, webhook);
        }
    }

}

            /*
                Get Profile Picture From Viewer

            try {
                URL url = new URL("https://api.twitch.tv/helix/users?id=" + event.getUser().getId());
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                connection.setRequestMethod("GET");
                connection.setRequestProperty("Authorization", "ylyi0lcdgtfv18i42rd566ueshe634");
                connection.setRequestProperty("Client-Id", "ayzr22fmzg2zvtd8ai2wyncqt2a0kz");

                String profilePicture = (String)((JSONObject)new JSONParser().parse(new InputStreamReader(connection.getInputStream()))).get("profile_image_url");
                builder.setDisplayAvatar(new URL(profilePicture));

            } catch (IOException | ParseException e) {
                e.printStackTrace();
            }
             */
