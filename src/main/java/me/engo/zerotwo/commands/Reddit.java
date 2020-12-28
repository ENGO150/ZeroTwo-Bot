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

public class Reddit extends ListenerAdapter {

    public static String alias = "There's no aliases.";
    String language;

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        Config c = new Config();
        String[] args = event.getMessage().getContentRaw().split(" ");
        if (event.getAuthor().isBot()) return;

        if (args[0].equalsIgnoreCase(c.prefix + "reddit")) {

            try {
                File languages = new File("Database/Language/" + event.getAuthor().getId());
                if (languages.exists()) {
                    File[] languages_ = languages.listFiles();
                    assert languages_ != null;
                    language = languages_[0].getName();
                } else {
                    language = "english_en";
                }

                if (args.length < 2){
                    String text = JsonParser.parseReader(new FileReader("languages/" + language + ".json")).getAsJsonObject().get("basic_warnings").getAsJsonObject().get("low_parameters").getAsString();
                    event.getChannel().sendMessage(text).queue();
                } else if (args.length == 2){
                    URL url = new URL("https://meme-api.herokuapp.com/gimme/" + args[1]);
                    JSONTokener tokener = new JSONTokener(url.openStream());
                    JSONObject obj = new JSONObject(tokener);

                    String img = obj.getString("url");
                    String title = obj.getString("title");
                    boolean nsfw = obj.getBoolean("nsfw");
                    boolean spoiler = obj.getBoolean("spoiler");

                    if (spoiler){
                        String text = JsonParser.parseReader(new FileReader("languages/" + language + ".json")).getAsJsonObject().get("basic_warnings").getAsJsonObject().get("spoiler_true").getAsString();
                        event.getChannel().sendMessage(text).queue();
                        return;
                    }

                    if (nsfw && !event.getChannel().isNSFW()){
                        String text = JsonParser.parseReader(new FileReader("languages/" + language + ".json")).getAsJsonObject().get("basic_warnings").getAsJsonObject().get("nsfwchannel_false").getAsString();
                        event.getChannel().sendMessage(text).queue();
                        return;
                    }

                    EmbedBuilder em = new EmbedBuilder();
                    em.setTitle(title);
                    em.setImage(img);
                    em.setFooter(c.footer1, c.footer2);
                    em.setColor(new Color(c.Color));

                    if (nsfw && event.getChannel().isNSFW()){
                    	event.getChannel().sendMessage(em.build()).queue();
                    } else {
                        event.getChannel().sendMessage(em.build()).queue();
                    }
                } else {
                    String text = JsonParser.parseReader(new FileReader("languages/" + language + ".json")).getAsJsonObject().get("basic_warnings").getAsJsonObject().get("high_parameters").getAsString();
                    event.getChannel().sendMessage(text).queue();
                }
            } catch (IOException e){
                try {
                    String text = JsonParser.parseReader(new FileReader("languages/" + language + ".json")).getAsJsonObject().get("advanced_warnings").getAsJsonObject().get("subreddit_false").getAsString();
                    event.getChannel().sendMessage(text).queue();
                } catch (FileNotFoundException ex){
                    ex.printStackTrace();
                }
            }
        }
    }
}
