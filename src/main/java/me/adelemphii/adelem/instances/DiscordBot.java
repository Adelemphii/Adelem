package me.adelemphii.adelem.instances;

import me.adelemphii.adelem.commands.CreditsCommand;
import me.adelemphii.adelem.commands.ServerStatusCommands;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;

public class DiscordBot {

    // Discord API
    private DiscordApi api;

    public DiscordBot() {
        api = new DiscordApiBuilder()
                .setToken("ODQ0OTU3NTQ4MTM0MDA2ODA2.YKZ-Cg.GyuyndNFkB0NsuFClz1-qj2MIVI")
                .login().join();

        registerListeners(api);
    }

    public void start() {
        api.updateActivity("Use 'wifeyHelp' to see my commands!");
        System.out.println("You can invite the bot by using the following url: " + api.createBotInvite());
    }

    private void registerListeners(DiscordApi api) {
        api.addListener(new ServerStatusCommands());
        api.addListener(new CreditsCommand());
    }

}
