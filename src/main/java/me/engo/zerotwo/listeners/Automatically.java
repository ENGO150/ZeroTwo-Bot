package me.engo.zerotwo.listeners;

import me.engo.zerotwo.handlers.Music;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.File;

public class Automatically extends ListenerAdapter {

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;

        //MUSIC
        Music.id = event.getChannel().getId();
        Music.member = event.getMember();
        Music.id2 = event.getAuthor().getId();
        File languages = new File("Database/Language/" + event.getAuthor().getId());
        if (languages.exists()) {
            File[] languages_ = languages.listFiles();
            assert languages_ != null;
            Music.language = languages_[0].getName();
        } else {
            Music.language = "english_en";
        }
    }
}
