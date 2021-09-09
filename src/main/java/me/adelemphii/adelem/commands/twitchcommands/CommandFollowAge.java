package me.adelemphii.adelem.commands.twitchcommands;

import com.github.twitch4j.TwitchClient;
import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;
import com.github.twitch4j.common.enums.CommandPermission;
import com.github.twitch4j.helix.domain.FollowList;
import me.adelemphii.adelem.Core;
import org.ocpsoft.prettytime.PrettyTime;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class CommandFollowAge {

    static TwitchClient client = Core.twitchBot.getClient();

    public static void runCommand(ChannelMessageEvent event) {
        List<String> args = Arrays.asList(event.getMessage().split(" "));

        if(args.isEmpty()) return;

        if(args.size() < 2) return;

        if(args.size() > 2) {
            if(!event.getPermissions().contains(CommandPermission.MODERATOR)) return;

            for(int i = 1; i < args.size(); i++) {
                String arg = args.get(i);

                FollowList resultList = client.getHelix().getFollowers(null, null, event.getChannel().getId(), null, 100).execute();

                resultList.getFollows().forEach(follow -> {
                    if(follow.getFromName().equalsIgnoreCase(arg)) {
                        Date dateFollowed = Date.from(follow.getFollowedAtInstant());

                        PrettyTime p = new PrettyTime();

                        event.getTwitchChat().sendMessage(event.getChannel().getName(),
                                follow.getFromName() + " has been following since " + p.format(dateFollowed));
                    }
                });

            }
            return;
        }

        FollowList resultList = client.getHelix().getFollowers(null, null, event.getChannel().getId(), null, 100).execute();

        resultList.getFollows().forEach(follow -> {
            if(follow.getFromName().equalsIgnoreCase(args.get(1))) {
                Date dateFollowed = Date.from(follow.getFollowedAtInstant());

                PrettyTime p = new PrettyTime();

                event.getTwitchChat().sendMessage(event.getChannel().getName(),
                        follow.getFromName() + " has been following since " + p.format(dateFollowed));
            }
        });
    }
}
