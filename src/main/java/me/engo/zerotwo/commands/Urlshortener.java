package me.engo.zerotwo.commands;

import com.google.gson.JsonParser;
import me.engo.zerotwo.Bot;
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

public class Urlshortener extends ListenerAdapter {

    public static String alias = "cuttly";

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        Config c = new Config();
        String[] args = event.getMessage().getContentRaw().split(" ");
        if (event.getAuthor().isBot()) return;

        if (args[0].equalsIgnoreCase(c.prefix + "urlshortener") || args[0].equalsIgnoreCase(c.prefix + alias)) {

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

                if (args.length < 3) {
                    String text = JsonParser.parseReader(new FileReader("languages/" + language + ".json")).getAsJsonObject().get("basic_warnings").getAsJsonObject().get("low_parameters").getAsString();
                    event.getChannel().sendMessage(text).queue();
                } else if (args.length == 3){
                    try {

                        URL url = new URL("https://cutt.ly/api/api.php?key=" + Bot.token_url + "&short=" + args[1] +"&name=" + args[2]);
                        JSONTokener tokener = new JSONTokener(url.openStream());
                        JSONObject obj = new JSONObject(tokener);
                        JSONObject shorten = obj.getJSONObject("url");

                        int status = shorten.getInt("status");

                        if (status == 7){
                            EmbedBuilder em = new EmbedBuilder();
                            String shorted = shorten.getString("shortLink");
                            em.addField("Long Url", args[1], false);
                            em.addField("Short Url", shorted, false);
                            em.setFooter(c.footer1, c.footer2);
                            em.setColor(new Color(c.Color));
                            event.getChannel().sendMessage(em.build()).queue();
                        } else {
                            switch (status){
                                case 3:
                                    String text1 = JsonParser.parseReader(new FileReader("languages/" + language + ".json")).getAsJsonObject().get("advanced_warnings").getAsJsonObject().get("urlshortener_name").getAsString();
                                    event.getChannel().sendMessage(text1).queue();
                                    break;

                                case 2:
                                case 5:
                                case 6:
                                    String text2 = JsonParser.parseReader(new FileReader("languages/" + language + ".json")).getAsJsonObject().get("advanced_warnings").getAsJsonObject().get("urlshortener_link").getAsString();
                                    event.getChannel().sendMessage(text2).queue();
                                    break;

                                case 1:
                                    String text3 = JsonParser.parseReader(new FileReader("languages/" + language + ".json")).getAsJsonObject().get("advanced_warnings").getAsJsonObject().get("urlshortener_shortened").getAsString();
                                    event.getChannel().sendMessage(text3).queue();
                                    break;
                            }
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
