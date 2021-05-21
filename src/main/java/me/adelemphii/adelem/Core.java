package me.adelemphii.adelem;

import me.adelemphii.adelem.commands.ServerStatusCommands;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.DiscordClient;
import org.javacord.api.entity.user.UserStatus;

public class Core {

    public static void main(String[] args) {
        // (System.getenv("TOKEN"))
        DiscordApi api = new DiscordApiBuilder()
                .setToken("ODQ0OTU3NTQ4MTM0MDA2ODA2.YKZ-Cg.GyuyndNFkB0NsuFClz1-qj2MIVI")
                .login().join();

        api.addListener(new ServerStatusCommands());

        api.updateActivity("Hey! I was made by Adelemphii so you should check out her Github!" +
                " https://github.com/Adelemphii");

        System.out.println("You can invite the bot by using the following url: " + api.createBotInvite());
    }
}
