package me.adelemphii.adelem.discordcommands;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.component.ActionRow;
import org.javacord.api.entity.message.component.Button;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import java.awt.*;

public class CreditsCommand implements MessageCreateListener {

    @Override
    public void onMessageCreate(MessageCreateEvent event) {

        if (event.getServer().isPresent()) {
            TextChannel channel = event.getChannel();

            Message msg = event.getMessage();

            if (msg.getContent().toLowerCase().contains("wifeycredit")) {

                EmbedBuilder creditsEmbed = new EmbedBuilder()
                        .setTitle("Bot Credits & Socials")

                        .addField("Created By", "The bot was created by Adelemphii")
                        .addField("Discord Tag", "Adelemphii#6213")
                        .addField("Portfolio & Commissions Info", "Find Adelemphii's Portfolio " +
                                "@ https://www.adelemphii.me")
                        .addField("Twitch", "Follow my twitch @ https://twitch.tv/Adelemphii")
                        .addField("Twitter", "Follow my Twitter @ https://twitter.com/Adelemphii")
                        .addField("Requests & Suggestions", "Send all requests and suggestions" +
                                " to Adelemphii#6213 on Discord!")

                        .setColor(Color.PINK);

                new MessageBuilder()
                        .setEmbed(creditsEmbed)
                        .addComponents(
                                ActionRow.of(Button.link("https://twitch.tv/Adelemphii", "My Twitch!"),
                                        Button.link("https://twitter.com/Adelemphii", "My Twitter!"),
                                        Button.link("https://www.adelemphii.me", "My Portfolio!")))
                        .send(channel);
            }
        }
    }


}
