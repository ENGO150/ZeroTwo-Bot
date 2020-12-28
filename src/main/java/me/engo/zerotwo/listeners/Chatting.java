package me.engo.zerotwo.listeners;

import me.engo.zerotwo.Config;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.io.File;
import java.util.Objects;

public class Chatting extends ListenerAdapter {

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event){
        if (event.getAuthor().isBot()) return;

        File f = new File("Database/Botchat/" + event.getGuild().getId());
        if (!f.exists()) return;
        File channel = new File("Database/Botchat/" + event.getGuild().getId() + "/" + event.getChannel().getId());
        if (!channel.exists()) return;

        File database = new File("Database/Botchat");
        File[] guilds = database.listFiles();
        assert guilds != null;
        if (guilds.length == 0) return;

        for (File guildy : guilds){
            File[] list = guildy.listFiles();
            assert list != null;
            TextChannel tc = event.getJDA().getTextChannelById(list[0].getName());
            if (!event.getChannel().equals(tc)) {
                EmbedBuilder em = new EmbedBuilder();
                em.setDescription(event.getMessage().getContentRaw());
                em.setColor(new Color(new Config().Color));
                em.setFooter(event.getAuthor().getAsTag(), event.getAuthor().getAvatarUrl());

                assert tc != null;
                if (Objects.requireNonNull(event.getJDA().getGuildById(guildy.getName())).getSelfMember().getPermissions(tc).contains(Permission.MESSAGE_WRITE)) {
                    tc.sendMessage(em.build()).queue();
                }
            }
        }
    }

}
