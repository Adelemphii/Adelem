package me.adelemphii.adelem.commands;

import com.github.twitch4j.chat.TwitchChat;
import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;
import com.github.twitch4j.chat.events.channel.IRCMessageEvent;
import com.github.twitch4j.common.enums.CommandPermission;
import me.adelemphii.adelem.Core;
import me.adelemphii.adelem.util.LockdownStatus;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class CommandLockDown {

  private static final Set<String> SUB_ALIAS = Set.of("sub", "subscriber", "s");
  private static final Set<String> FOLLOWER_ALIAS = Set.of("followers", "f");
  private static final Set<String> SIMPLE_LOCKDOWN_CMDS = Set.of("/followers 1h", "/clear", "/slow 5s");
  private static final Set<String> DISABLE_LOCKDOWN_CMDS = Set.of("/followersoff", "/uniquechatoff", "/emoteonlyoff", "/subscribersoff", "/slowoff");

  private final Core core;

  private LockdownStatus lockdownStatus = LockdownStatus.DISABLED;
  private String lockdownType = "";
  private String lockdownTime = "";

  public CommandLockDown(@NotNull Core core) {
    this.core = core;
  }

  public void runCommand(@NotNull ChannelMessageEvent event) {

    List<String> args = Arrays.asList(event.getMessage().split(" "));

    if (args.isEmpty()) return;
    if (!event.getPermissions().contains(CommandPermission.MODERATOR)) {
      sendInformation(event.getChannel().getName(), event.getUser().getName());
      return;
    }

    if (args.size() == 1) {
      simpleLockdown(event);
    }
    else if(args.size() == 2) {
      // !lockdown disable - 2
      final String arg = args.get(1);
      if (arg.equalsIgnoreCase("disable")) disableLockdown(event);
      else if (SUB_ALIAS.stream().anyMatch(arg::equalsIgnoreCase)) advancedLockdown(event, args, "subscribers");
    }
    else if (args.size() == 3) {
      // !lockdown f 60m - 3
      final String arg = args.get(1);
      if (FOLLOWER_ALIAS.stream().anyMatch(arg::equalsIgnoreCase)) advancedLockdown(event, args, "followers");
    }
  }

  public void runCommand(@NotNull String channel, @NotNull String user, @NotNull String text) {

    List<String> args = Arrays.asList(text.split(" "));
    if (args.isEmpty()) return;

    if (args.size() == 1) {
      simpleLockdown(channel, user);
    }
    else if (args.size() == 2) {
      // !lockdown disable - 2
      final String arg = args.get(1);
      if (arg.equalsIgnoreCase("disable")) disableLockdown(channel, user);
      else if (SUB_ALIAS.stream().anyMatch(arg::equalsIgnoreCase)) advancedLockdown(channel, user, args, "subscribers");
    }
    else if (args.size() == 3) {
      // !lockdown f 60m - 3
      final String arg = args.get(1);
      if (FOLLOWER_ALIAS.stream().anyMatch(arg::equalsIgnoreCase)) advancedLockdown(channel, user, args, "followers");
    }
  }

  private void simpleLockdown(@NotNull ChannelMessageEvent event) {

    final TwitchChat chat = event.getTwitchChat();
    final String name = event.getChannel().getName();
    final IRCMessageEvent ircMessageEvent = event.getMessageEvent();

    SIMPLE_LOCKDOWN_CMDS.forEach(cmd -> chat.sendMessage(name, cmd));

    System.out.println(ircMessageEvent.getTags());

    chat.sendMessage(name,
        ircMessageEvent.getTagValue("display-name").orElse(event.getUser().getName()) + " has registered this chat as being under simple lockdown!");
    setLockdownStatus(LockdownStatus.ENABLED, "simple", "1h");
  }

  private void disableLockdown(@NotNull ChannelMessageEvent event) {

    final TwitchChat chat = event.getTwitchChat();
    final String name = event.getChannel().getName();
    final IRCMessageEvent ircMessageEvent = event.getMessageEvent();

    DISABLE_LOCKDOWN_CMDS.forEach(cmd -> chat.sendMessage(name, cmd));

    chat.sendMessage(name,
        ircMessageEvent.getTagValue("display-name").orElse(event.getUser().getName()) + " has disabled the lockdown!");
    setLockdownStatus(LockdownStatus.DISABLED, "", "");
  }

  private void advancedLockdown(@NotNull ChannelMessageEvent event, @NotNull List<String> args, @NotNull String type) {

    final TwitchChat chat = event.getTwitchChat();
    final String name = event.getChannel().getName();
    final IRCMessageEvent ircMessageEvent = event.getMessageEvent();

    chat.sendMessage(name, "/clear");

    if (type.equals("followers")) {
      args.set(1, "followers");
      // !lockdown f 1h
      chat.sendMessage(name, formatChatCommand(args));
      chat.sendMessage(name,
          ircMessageEvent.getTagValue("display-name").orElse(event.getUser().getName())
              + " has registered this chat as being under a " + args.get(2) + " follower lockdown!");
      setLockdownStatus(LockdownStatus.ENABLED, "follower", args.get(2));
    }
    else if(type.equals("subscribers")) {
      args.set(1, "subscribers");
      // lockdown s 1h
      chat.sendMessage(name, "/subscribers");
      chat.sendMessage(name,
          ircMessageEvent.getTagValue("display-name").orElse(event.getUser().getName())
              + " has registered this chat as being under sub-only lockdown!");
      setLockdownStatus(LockdownStatus.ENABLED, "sub", args.get(2));
    } else {
      simpleLockdown(event);
    }
  }

  private @NotNull String formatChatCommand(@NotNull List<String> args) {
    return "/%s %s".formatted(args.get(1), args.get(2));
  }

  private void simpleLockdown(@NotNull String channel, @NotNull String user) {
    final TwitchChat chat = core.getTwitchBot().getClient().getChat();
    SIMPLE_LOCKDOWN_CMDS.forEach(cmd -> chat.sendMessage(channel, cmd));
    chat.sendMessage(channel,
        user + " has registered this chat as being under simple lockdown!");
    setLockdownStatus(LockdownStatus.ENABLED, "simple", "1h");
  }

  private void disableLockdown(@NotNull String channel, @NotNull String user) {
    final TwitchChat chat = core.getTwitchBot().getClient().getChat();
    DISABLE_LOCKDOWN_CMDS.forEach(cmd -> chat.sendMessage(channel, cmd));
    chat.sendMessage(channel,
        user + " has disabled the lockdown!");
    setLockdownStatus(LockdownStatus.DISABLED, "", "");
  }

  private void advancedLockdown(@NotNull String channel, @NotNull String user, @NotNull List<String> args, @NotNull String type) {
    final TwitchChat chat = core.getTwitchBot().getClient().getChat();
    chat.sendMessage(channel, "/clear");
    if (type.equals("followers")) {
      args.set(1, "followers");
      // !lockdown f 1h
      chat.sendMessage(channel, formatChatCommand(args));
      chat.sendMessage(channel,
          user + " has registered this chat as being under a " + args.get(2) + " follower lockdown!");
      setLockdownStatus(LockdownStatus.ENABLED, "follower", args.get(2));
    }
    else if (type.equals("subscribers")) {
      args.set(1, "subscribers");
      // lockdown s 1h
      chat.sendMessage(channel, "/subscribers");
      chat.sendMessage(channel,
          user + " has registered this chat as being under sub-only lockdown!");
      setLockdownStatus(LockdownStatus.ENABLED, "sub", args.get(2));
    } else {
      simpleLockdown(channel, user);
    }
  }

  private void sendInformation(@NotNull String channel, @NotNull String user) {
    final TwitchChat chat = core.getTwitchBot().getClient().getChat();

    if(lockdownStatus == LockdownStatus.ENABLED) {
      chat.sendMessage(channel, user + ": The current lockdown is " + lockdownType + " for " + lockdownTime + ".");
    }
  }

  public LockdownStatus getLockdownStatus() {
    return lockdownStatus;
  }

  private void setLockdownStatus(LockdownStatus lockdownStatus, String lockdownType, String lockdownTime) {
    this.lockdownStatus = lockdownStatus;
    this.lockdownType = lockdownType;
    this.lockdownTime = lockdownTime;
  }

}