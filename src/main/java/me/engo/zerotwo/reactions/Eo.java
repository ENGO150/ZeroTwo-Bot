package me.engo.zerotwo.reactions;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Eo extends ListenerAdapter {

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;
        if (!event.getMessage().getContentRaw().equalsIgnoreCase("eo")) return;

        event.getChannel().sendMessage("EEEEEEEEEEEE OOOOOOOOOOOOOOOO").queue();
    }
}