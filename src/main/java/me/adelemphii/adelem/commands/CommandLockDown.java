package me.adelemphii.adelem.commands;

import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;
import com.github.twitch4j.common.enums.CommandPermission;
import me.adelemphii.adelem.Core;

import java.util.Arrays;
import java.util.List;

public class CommandLockDown {

    public static void runCommand(ChannelMessageEvent event) {
        List<String> args = Arrays.asList(event.getMessage().split(" "));

        if(args.isEmpty()) return;

        if(!event.getPermissions().contains(CommandPermission.MODERATOR)) return;

        if(args.size() == 1) {
            simpleLockdown(event);
        }
        else if(args.size() == 2) {
            // !lockdown disable - 2
            if(args.get(1).equalsIgnoreCase("disable")) disableLockdown(event);
            else if (args.get(1).equalsIgnoreCase("sub") || args.get(1).equalsIgnoreCase("subscriber") || args.get(1).equalsIgnoreCase("s"))
                advancedLockdown(event, args, "subscribers");
        }
        else if(args.size() == 3) {
            // !lockdown f 60m - 3
            if (args.get(1).equalsIgnoreCase("followers") || args.get(1).equalsIgnoreCase("f"))
                advancedLockdown(event, args, "followers");
        }
    }

    public static void runCommand(String channel, String user, String text) {
        List<String> args = Arrays.asList(text.split(" "));

        if(args.isEmpty()) return;

        if(args.size() == 1) {
            simpleLockdown(channel, user);
        }
        else if(args.size() == 2) {
            // !lockdown disable - 2
            if(args.get(1).equalsIgnoreCase("disable")) disableLockdown(channel, user);
            else if (args.get(1).equalsIgnoreCase("sub") || args.get(1).equalsIgnoreCase("subscriber") || args.get(1).equalsIgnoreCase("s"))
                advancedLockdown(channel, user, args, "subscribers");
        }
        else if(args.size() == 3) {
            // !lockdown f 60m - 3
            if (args.get(1).equalsIgnoreCase("followers") || args.get(1).equalsIgnoreCase("f"))
                advancedLockdown(channel, user, args, "followers");
        }
    }

    private static void simpleLockdown(ChannelMessageEvent event) {

        event.getTwitchChat().sendMessage(event.getChannel().getName(), "/followers 1h");
        event.getTwitchChat().sendMessage(event.getChannel().getName(), "/clear");
        event.getTwitchChat().sendMessage(event.getChannel().getName(), "/slow 5s");

        System.out.println(event.getMessageEvent().getTags());

        event.getTwitchChat().sendMessage(event.getChannel().getName(),
                event.getMessageEvent().getTagValue("display-name").orElse(event.getUser().getName()) + " has registered this chat as being under simple lockdown!");
    }

    private static void disableLockdown(ChannelMessageEvent event) {
        event.getTwitchChat().sendMessage(event.getChannel().getName(), "/followersoff");
        event.getTwitchChat().sendMessage(event.getChannel().getName(), "/uniquechatoff");
        event.getTwitchChat().sendMessage(event.getChannel().getName(), "/emoteonlyoff");
        event.getTwitchChat().sendMessage(event.getChannel().getName(), "/subscribersoff");
        event.getTwitchChat().sendMessage(event.getChannel().getName(), "/slowoff");

        event.getTwitchChat().sendMessage(event.getChannel().getName(),
                event.getMessageEvent().getTagValue("display-name").orElse(event.getUser().getName()) + " has disabled the lockdown!");
    }

    private static void advancedLockdown(ChannelMessageEvent event, List<String> args, String type) {
        event.getTwitchChat().sendMessage(event.getChannel().getName(), "/clear");
        if(type.equals("followers")) {
            args.set(1, "followers");
            // !lockdown f 1h
            event.getTwitchChat().sendMessage(event.getChannel().getName(), formatChatCommand(args));

            event.getTwitchChat().sendMessage(event.getChannel().getName(),
                    event.getMessageEvent().getTagValue("display-name").orElse(event.getUser().getName())
                            + " has registered this chat as being under a " + args.get(2) + " follower lockdown!");
        }
        else if(type.equals("subscribers")) {
            args.set(1, "subscribers");
            // lockdown s 1h
            event.getTwitchChat().sendMessage(event.getChannel().getName(), "/subscribers");

            event.getTwitchChat().sendMessage(event.getChannel().getName(),
                    event.getMessageEvent().getTagValue("display-name").orElse(event.getUser().getName())
                            + " has registered this chat as being under sub-only lockdown!");
        } else {
            simpleLockdown(event);
        }
    }

    private static String formatChatCommand(List<String> args) {
        return "/" + args.get(1) + " " + args.get(2);
    }

    private static void simpleLockdown(String channel, String user) {

        Core.twitchBot.getClient().getChat().sendMessage(channel, "/followers 1h");
        Core.twitchBot.getClient().getChat().sendMessage(channel, "/clear");
        Core.twitchBot.getClient().getChat().sendMessage(channel, "/slow 5s");

        Core.twitchBot.getClient().getChat().sendMessage(channel,
                user + " has registered this chat as being under simple lockdown!");
    }

    private static void disableLockdown(String channel, String user) {
        Core.twitchBot.getClient().getChat().sendMessage(channel, "/followersoff");
        Core.twitchBot.getClient().getChat().sendMessage(channel, "/uniquechatoff");
        Core.twitchBot.getClient().getChat().sendMessage(channel, "/emoteonlyoff");
        Core.twitchBot.getClient().getChat().sendMessage(channel, "/subscribersoff");
        Core.twitchBot.getClient().getChat().sendMessage(channel, "/slowoff");

        Core.twitchBot.getClient().getChat().sendMessage(channel,
                user + " has disabled the lockdown!");
    }

    private static void advancedLockdown(String channel, String user, List<String> args, String type) {
        Core.twitchBot.getClient().getChat().sendMessage(channel, "/clear");
        if(type.equals("followers")) {
            args.set(1, "followers");
            // !lockdown f 1h
            Core.twitchBot.getClient().getChat().sendMessage(channel, formatChatCommand(args));

            Core.twitchBot.getClient().getChat().sendMessage(channel,
                    user + " has registered this chat as being under a " + args.get(2) + " follower lockdown!");
        }
        else if(type.equals("subscribers")) {
            args.set(1, "subscribers");
            // lockdown s 1h
            Core.twitchBot.getClient().getChat().sendMessage(channel, "/subscribers");

            Core.twitchBot.getClient().getChat().sendMessage(channel,
                    user + " has registered this chat as being under sub-only lockdown!");
        } else {
            simpleLockdown(channel, user);
        }
    }

}
