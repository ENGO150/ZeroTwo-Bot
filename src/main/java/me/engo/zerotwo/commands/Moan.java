package me.engo.zerotwo.commands;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Objects;
import java.util.Random;

import com.google.gson.JsonParser;

import me.engo.zerotwo.Config;
import me.engo.zerotwo.handlers.Music;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Moan extends ListenerAdapter {

    public static String alias = "There's no aliases.";

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        Config c = new Config();
        String[] messageSent = event.getMessage().getContentRaw().split(" ");
        if (event.getAuthor().isBot()) return;

        if (messageSent[0].equalsIgnoreCase(c.prefix + "moan")) {

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

                if (messageSent.length < 2) {

                    if (c.nsfw) {
                        if (event.getChannel().isNSFW()) {

                        	if (Objects.requireNonNull(Objects.requireNonNull(event.getMember()).getVoiceState()).inVoiceChannel() && Objects.requireNonNull(Objects.requireNonNull(Objects.requireNonNull(event.getMember()).getVoiceState()).getChannel()).getMembers().size() < 2) {
                                try {
                                    int i = new Random().nextInt(8);
                                    if (i < 1) {
                                        i++;
                                    }
                                    new Music().loadAndPlay(event.getChannel(), "https://raw.githubusercontent.com/ZeroTwobt/moan/master/moan" + i + ".mp3");
                                } catch (FileNotFoundException ex) {
                                    ex.printStackTrace();
                                }
                            } else if (!Objects.requireNonNull(event.getMember().getVoiceState()).inVoiceChannel()){
                                try {
                                    String text = JsonParser.parseReader(new FileReader("languages/" + language + ".json")).getAsJsonObject().get("logs").getAsJsonObject().get("music_false").getAsString();
                                    event.getChannel().sendMessage(text).queue();
                                } catch (FileNotFoundException ex) {
                                    ex.printStackTrace();
                                }
                            } else {
                                Member m = event.getGuild().getMember(event.getJDA().getSelfUser());
                                if (Objects.requireNonNull(event.getMember().getVoiceState().getChannel()).getMembers().contains(m) && event.getMember().getVoiceState().getChannel().getMembers().size() == 2){
                                    try {
                                        int i = new Random().nextInt(8);
                                        if (i < 1) {
                                            i++;
                                        }
                                        new Music().loadAndPlay(event.getChannel(), "https://raw.githubusercontent.com/ZeroTwobt/moan/master/moan" + i + ".mp3");
                                    } catch (FileNotFoundException ex) {
                                        ex.printStackTrace();
                                    }
                                    return;
                                }
                                try {
                                    String text = JsonParser.parseReader(new FileReader("languages/" + language + ".json")).getAsJsonObject().get("advanced_warnings").getAsJsonObject().get("alone_false").getAsString();
                                    event.getChannel().sendMessage(text).queue();
                                } catch (FileNotFoundException ex) {
                                    ex.printStackTrace();
                                }
                            }

                        } else {
                            String text = JsonParser.parseReader(new FileReader("languages/" + language + ".json")).getAsJsonObject().get("basic_warnings").getAsJsonObject().get("nsfwchannel_false").getAsString();
                            event.getChannel().sendMessage(text).queue();
                        }
                    } else {
                        String text = JsonParser.parseReader(new FileReader("languages/" + language + ".json")).getAsJsonObject().get("basic_warnings").getAsJsonObject().get("nsfwmodule_false").getAsString();
                        event.getChannel().sendMessage(text).queue();
                    }

                } else {
                    String text = JsonParser.parseReader(new FileReader("languages/" + language + ".json")).getAsJsonObject().get("basic_warnings").getAsJsonObject().get("high_parameters").getAsString();
                    event.getChannel().sendMessage(text).queue();
                }
            } catch (FileNotFoundException e){
                e.printStackTrace();
            }
        }
    }
}
