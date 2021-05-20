package me.adelemphii.adelem;

import me.adelemphii.adelem.commands.ServerStatusCommands;
import me.adelemphii.adelem.util.MineStats;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;

public class Core {

    // Make this secure later
    private static final String TOKEN = "ODQ0OTU3NTQ4MTM0MDA2ODA2.YKZ-Cg.GyuyndNFkB0NsuFClz1-qj2MIVI";

    public static void main(String[] args) {

        DiscordApi api = new DiscordApiBuilder()
                .setToken(TOKEN)
                .login().join();

        api.addListener(new ServerStatusCommands());

        System.out.println("You can invite the bot by using the following url: " + api.createBotInvite());

    }

}
