package me.engo.zerotwo.commands;

import com.google.gson.JsonParser;
import me.engo.zerotwo.Config;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Random;

public class Meme extends ListenerAdapter {

    public static String alias = "joke";

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        Config c = new Config();
        String[] args = event.getMessage().getContentRaw().split(" ");
        if (event.getAuthor().isBot()) return;

        if (args[0].equalsIgnoreCase(c.prefix + "meme") || args[0].equalsIgnoreCase(c.prefix + alias)){
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
                    try {
                        if (new Random().nextInt(10) <= 5){
                            URL url = new URL("https://meme-api.herokuapp.com/gimme");
                            JSONTokener tokener = new JSONTokener(url.openStream());
                            JSONObject obj = new JSONObject(tokener);
                            String img = obj.getString("url");
                            String title = obj.getString("title");

                            EmbedBuilder em = new EmbedBuilder();
                            em.setTitle(title);
                            em.setImage(img);
                            em.setFooter(c.footer1, c.footer2);
                            em.setColor(new Color(c.Color));
                            event.getChannel().sendMessage(em.build()).queue();
                        } else {
                            URL url = new URL("https://some-random-api.ml/meme");
                            JSONTokener tokener = new JSONTokener(url.openStream());
                            JSONObject obj = new JSONObject(tokener);
                            String img = obj.getString("image");
                            String title = obj.getString("caption");

                            EmbedBuilder em = new EmbedBuilder();
                            em.setTitle(title);
                            em.setImage(img);
                            em.setFooter(c.footer1, c.footer2);
                            em.setColor(new Color(c.Color));
                            event.getChannel().sendMessage(em.build()).queue();
                        }
                    } catch (IOException e){
                        e.printStackTrace();
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
