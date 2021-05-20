package me.adelemphii.adelem;

import me.adelemphii.adelem.commands.ServerStatusCommands;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.DiscordClient;
import org.javacord.api.entity.user.UserStatus;

public class Core {

    public static void main(String[] args) {

        DiscordApi api = new DiscordApiBuilder()
                .setToken(System.getenv("TOKEN"))
                .login().join();

        api.addListener(new ServerStatusCommands());

        api.updateActivity("Type 'wifeyStatus' to check the status of the server!");

        System.out.println("You can invite the bot by using the following url: " + api.createBotInvite());

    }

}
