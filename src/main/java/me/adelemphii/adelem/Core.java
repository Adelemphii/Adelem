package me.adelemphii.adelem;

import me.adelemphii.adelem.commands.ServerStatusCommands;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;

public class Core {

    public static void main(String[] args) {

        DiscordApi api = new DiscordApiBuilder()
                .setToken(System.getenv("TOKEN"))
                .login().join();

        api.addListener(new ServerStatusCommands());

        System.out.println("You can invite the bot by using the following url: " + api.createBotInvite());

    }

}
