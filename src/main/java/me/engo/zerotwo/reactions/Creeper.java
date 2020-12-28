package me.engo.zerotwo.reactions;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Creeper extends ListenerAdapter {

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;
        if (!event.getMessage().getContentRaw().equalsIgnoreCase("creeper")) return;

        event.getChannel().sendMessage("Aww man").queue();
    }
}
