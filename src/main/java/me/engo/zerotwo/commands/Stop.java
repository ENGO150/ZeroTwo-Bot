package me.engo.zerotwo.commands;

import com.google.gson.JsonParser;
import me.engo.zerotwo.Bot;
import me.engo.zerotwo.Config;
import me.engo.zerotwo.handlers.GuildMusicManager;
import me.engo.zerotwo.handlers.Music;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Objects;

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

                                String text = JsonParser.parseReader(new FileReader("languages/" + language + ".json")).getAsJsonObject().get("logs").getAsJsonObject().get("done").getAsString();
                                event.getChannel().sendMessage(text).queue();
                            } else {
                                String text = JsonParser.parseReader(new FileReader("languages/" + language + ".json")).getAsJsonObject().get("basic_warnings").getAsJsonObject().get("permissions_false").getAsString();
                                event.getChannel().sendMessage(text).queue();
                            }
                        } else {
                            String text = JsonParser.parseReader(new FileReader("languages/" + language + ".json")).getAsJsonObject().get("logs").getAsJsonObject().get("music_false_2").getAsString();
                            event.getChannel().sendMessage(text).queue();
                        }
                    } else {
                        String text = JsonParser.parseReader(new FileReader("languages/" + language + ".json")).getAsJsonObject().get("logs").getAsJsonObject().get("music_false").getAsString();
                        Objects.requireNonNull(Bot.jda.getTextChannelById(Music.id)).sendMessage(text).queue();
                    }
                } else {
                    String text = JsonParser.parseReader(new FileReader("languages/" + language + ".json")).getAsJsonObject().get("basic_warnings").getAsJsonObject().get("high_parameters").getAsString();
                    event.getChannel().sendMessage(text).queue();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
