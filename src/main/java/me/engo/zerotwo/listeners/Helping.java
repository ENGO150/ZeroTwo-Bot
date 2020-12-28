package me.engo.zerotwo.listeners;

import me.engo.zerotwo.Config;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Helping extends ListenerAdapter {

    @Override
    public void onGuildMessageReactionAdd(GuildMessageReactionAddEvent event) {
        if (event.getReactionEmote().isEmoji()) {
            if (event.getReactionEmote().getEmoji().equals("❓")) {
                event.getChannel().retrieveMessageById(event.getMessageIdLong()).queue(message -> {
                    String id = message.getAuthor().getId();
                    if (id.equals("668847135860719639")) {
                        Config c = new Config();
                        if (event.getMember().getUser().isBot()) return;

                        event.getMember().getUser().openPrivateChannel().queue((privateChannel -> privateChannel.sendMessage("Hello, I am ZeroTwo.\nTo get command, you need to know command module name. (For list of modules use `" + c.prefix + "modules`)\nTo get commands of module use `" + c.prefix + "module [moduleName]`\nTo get more info about commands use `" + c.prefix + "help [commandName]`\nDid you find any bug? I am sorry for that, but my developer is just a human and humans do mistakes. But there's a way to report it! Use `" + c.prefix + "report [message]`, thanks!").queue()));

                        event.getChannel().sendMessage("Sent you a DM with more info. " + event.getMember().getUser().getAsMention()).queue();
                    }
                });
            }
        }
    }
}
