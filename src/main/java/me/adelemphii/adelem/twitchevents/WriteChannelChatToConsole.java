package me.adelemphii.adelem.twitchevents;

import com.github.philippheuer.events4j.simple.SimpleEventHandler;
import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;
import me.adelemphii.adelem.Core;
import me.adelemphii.adelem.util.Configuration;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.message.WebhookMessageBuilder;

public class WriteChannelChatToConsole {

    private Configuration config = Core.config;

    private DiscordApi api = Core.discordBot.getApi();

    private String webhook = "https://discord.com/api/webhooks/865693060447993906/Y8JoHSQPMoj7zseSvyJkQgAePEQAEQJjwldCytwCRLSjQOqJkQF2aFak1ickOKzSQyPh";

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
        System.out.printf(
                "Channel [%s] - User[%s] - Message [%s]%n" +
                        "User ID[%s]%n",
                event.getChannel().getName(),
                event.getUser().getName(),
                event.getMessage(),
                event.getUser().getId()
        );

        if(config.getDiscordBroadcast()) {
            //TextChannel textChannel = api.getTextChannelById(865687218742624286L).get();

            //textChannel.sendMessage(event.getUser().getName() + ": " + event.getMessage());

            String payload = event.getMessage();

            WebhookMessageBuilder builder = new WebhookMessageBuilder();
            builder.setContent(payload);
            builder.setDisplayName(event.getUser().getName());
            builder.send(api, webhook);
            /*
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
        }
    }

}
