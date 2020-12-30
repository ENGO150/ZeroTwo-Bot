package me.engo.zerotwo.commands;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Objects;

import me.engo.zerotwo.Bot;
import me.engo.zerotwo.Config;
import me.engo.zerotwo.handlers.GuildMusicManager;
import me.engo.zerotwo.handlers.Music;
import me.engo.zerotwo.handlers.Translate;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;

public class Stop extends ListenerAdapter {

    public static String alias = "There's no aliases.";

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        Config c = new Config();
        String[] args = event.getMessage().getContentRaw().split(" ");
        if (event.getAuthor().isBot()) return;

        if (args[0].equalsIgnoreCase(c.prefix + "stop")) {

            try {
                String language;
                File languages = new File("Database/Language/" + event.getAuthor().getId());
                if (languages.exists()) {
                    File[] languages_ = languages.listFiles();
                    assert languages_ != null;
                    language = languages_[0].getName();
                } else {
                    language = "english_en";
                }

                if (args.length < 2) {

                    Music music = new Music();

                    if (Objects.requireNonNull(Objects.requireNonNull(event.getMember()).getVoiceState()).inVoiceChannel()) {
                        Guild guild = event.getGuild();
                        AudioManager audioManager = guild.getAudioManager();
                        if (audioManager.isConnected()) {
                            VoiceChannel vc = guild.getAudioManager().getConnectedChannel();
                            assert vc != null;
                            if (Objects.requireNonNull(event.getMember()).getPermissions(vc).contains(Permission.MANAGE_CHANNEL) || event.getMember().getPermissions(Objects.requireNonNull(Objects.requireNonNull(event.getMember().getVoiceState()).getChannel())).contains(Permission.MANAGE_CHANNEL)) {
                                GuildMusicManager musicManager = music.getGuildAudioPlayer(event.getGuild());
                                musicManager.player.stopTrack();
                                audioManager.closeAudioConnection();

                                String text = Translate.getTranslate(language, "logs", "done");
                                event.getChannel().sendMessage(text).queue();
                            } else {
                                String text = Translate.getTranslate(language, "basic_warnings", "permissions_false");
                                event.getChannel().sendMessage(text).queue();
                            }
                        } else {
                            String text = Translate.getTranslate(language, "logs", "music_false_2");
                            event.getChannel().sendMessage(text).queue();
                        }
                    } else {
                        String text = Translate.getTranslate(language, "logs", "music_false");
                        Objects.requireNonNull(Bot.jda.getTextChannelById(Music.id)).sendMessage(text).queue();
                    }
                } else {
                    String text = Translate.getTranslate(language, "basic_warnings", "high_parameters");
                    event.getChannel().sendMessage(text).queue();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
