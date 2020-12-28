package me.engo.zerotwo.commands;

import com.google.gson.JsonParser;
import me.engo.zerotwo.Config;
import me.engo.zerotwo.handlers.Music;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class Radio extends ListenerAdapter {

    public static String alias = "There's no aliases.";

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        Config c = new Config();
        String[] args = event.getMessage().getContentRaw().split(" ");
        if (event.getAuthor().isBot()) return;

        if (args[0].equalsIgnoreCase(c.prefix + "radio")) {

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

                if (args.length < 2){
                    EmbedBuilder em = new EmbedBuilder();
                    em.setTitle("Radio List");
                    em.addField("\uD83C\uDDE8\uD83C\uDDFF", "1. Radio Beat\n2. Radio Kiss\n3. Frekvence 1\n4. Evropa 2\n5. Radiožurnál\n6. Radio Impuls\n7. Radio Blaník\n8. Proglas\n9. Rock Radio\n10. Expres Radio", false);
                    em.setColor(new Color(c.Color));
                    em.setFooter(c.footer1, c.footer2);
                    event.getChannel().sendMessage(em.build()).queue();
                } else if (args.length == 2){
                    Music music = new Music();
                    switch (args[1]){
                        case "1":
                            music.loadAndPlay(event.getChannel(), "http://icecast2.play.cz/radiobeat128.mp3");
                            break;

                        case "2":
                            music.loadAndPlay(event.getChannel(), "http://icecast4.play.cz/kiss128.mp3");
                            break;

                        case "3":
                            music.loadAndPlay(event.getChannel(), "http://ice.actve.net/fm-frekvence1-128");
                            break;

                        case "4":
                            music.loadAndPlay(event.getChannel(), "https://ice.actve.net/fm-evropa2-128");
                            break;

                        case "5":
                            music.loadAndPlay(event.getChannel(), "http://icecast5.play.cz:8000/cro1-32.mp3");
                            break;

                        case "6":
                            music.loadAndPlay(event.getChannel(), "http://icecast5.play.cz:8000/impuls128.mp3");
                            break;

                        case "7":
                            music.loadAndPlay(event.getChannel(), "https://ice.abradio.cz/blanikfm128.mp3");
                            break;

                        case "8":
                            music.loadAndPlay(event.getChannel(), "http://icecast1.play.cz/Proglas96aac");
                            break;

                        case "9":
                            music.loadAndPlay(event.getChannel(), "https://ice.abradio.cz/sumava128.mp3");
                            break;

                        case "10":
                            music.loadAndPlay(event.getChannel(), "http://icecast5.play.cz:8000/expres128mp3");
                            break;

                        default:
                            String text = JsonParser.parseReader(new FileReader("languages/" + language + ".json")).getAsJsonObject().get("basic_warnings").getAsJsonObject().get("low_parameters").getAsString();
                            event.getChannel().sendMessage(text).queue();
                            break;
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

}
