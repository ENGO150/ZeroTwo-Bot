package me.engo.zerotwo.commands;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;

import org.json.JSONObject;
import org.json.JSONTokener;

import com.google.gson.JsonParser;

import me.engo.zerotwo.Config;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Thigh extends ListenerAdapter {

    public static String alias = "There's no aliases.";

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent Context) {
        Config c = new Config();
        String[] messageSent = Context.getMessage().getContentRaw().split(" ");
        if (Context.getAuthor().isBot()) return;

        if (messageSent[0].equalsIgnoreCase(c.prefix + "thigh")) {

            try {
                String language;
                File languages = new File("Database/Language/" + Context.getAuthor().getId());
                if (languages.exists()) {
                    File[] languages_ = languages.listFiles();
                    assert languages_ != null;
                    language = languages_[0].getName();
                } else {
                    language = "english_en";
                }

                if (messageSent.length < 3) {

                    if (c.nsfw) {
                        if (Context.getChannel().isNSFW()) {
                        	try {
                                URL url = new URL("https://nekobot.xyz/api/image?type=hthigh");
                                JSONTokener tokener = new JSONTokener(url.openStream());
                                JSONObject obj = new JSONObject(tokener);
                                String message = obj.getString("message");

                                EmbedBuilder em = new EmbedBuilder();
                                em.setImage(message);
                                em.setFooter(c.footer1, c.footer2);
                                em.setColor(new Color(c.Color));
                                Context.getChannel().sendMessage(em.build()).queue();
                            } catch (IOException ex){
                                ex.printStackTrace();
                            }
                        } else {
                            String text = JsonParser.parseReader(new FileReader("languages/" + language + ".json")).getAsJsonObject().get("basic_warnings").getAsJsonObject().get("nsfwchannel_false").getAsString();
                            Context.getChannel().sendMessage(text).queue();
                        }
                    } else {
                        String text = JsonParser.parseReader(new FileReader("languages/" + language + ".json")).getAsJsonObject().get("basic_warnings").getAsJsonObject().get("nsfwmodule_false").getAsString();
                        Context.getChannel().sendMessage(text).queue();
                    }

                } else {

                    String text = JsonParser.parseReader(new FileReader("languages/" + language + ".json")).getAsJsonObject().get("basic_warnings").getAsJsonObject().get("high_parameters").getAsString();

                    Context.getChannel().sendMessage(text).queue();

                }
            } catch (FileNotFoundException e){
                e.printStackTrace();
            }
        }
    }

}
