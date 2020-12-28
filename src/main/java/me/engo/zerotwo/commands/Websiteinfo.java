package me.engo.zerotwo.commands;

import com.google.gson.JsonParser;
import me.engo.zerotwo.Config;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Websiteinfo extends ListenerAdapter {

    public static String alias = "wi";
    public String language;

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        Config c = new Config();
        String[] args = event.getMessage().getContentRaw().split(" ");
        if (event.getAuthor().isBot()) return;

        if (args[0].equalsIgnoreCase(c.prefix + "websiteinfo") || args[0].equalsIgnoreCase(c.prefix + alias)) {

            try {
                File languages = new File("Database/Language/" + event.getAuthor().getId());
                if (languages.exists()) {
                    File[] languages_ = languages.listFiles();
                    assert languages_ != null;
                    language = languages_[0].getName();
                } else {
                    language = "english_en";
                }

                if (args.length < 2) {
                    String text = JsonParser.parseReader(new FileReader("languages/" + language + ".json")).getAsJsonObject().get("basic_warnings").getAsJsonObject().get("low_parameters").getAsString();
                    event.getChannel().sendMessage(text).queue();
                } else if (args.length == 2){
                    String url = args[1];
                    Document document = Jsoup.connect(url).followRedirects(false).timeout(10000).get();

                    EmbedBuilder em = new EmbedBuilder();
                    em.setTitle("Info of " + document.location());

                    if (document.title().equals("")){ em.addField("Title", "nothing", true); } else em.addField("Title", document.title(), true);
                    em.addField("More", "Coming soon", true);
                    em.setImage("https://logo.clearbit.com/" + document.location());
                    em.setColor(new Color(c.Color));
                    em.setFooter(c.footer1, c.footer2);
                    event.getChannel().sendMessage(em.build()).queue();
                } else {
                    String text = JsonParser.parseReader(new FileReader("languages/" + language + ".json")).getAsJsonObject().get("basic_warnings").getAsJsonObject().get("high_parameters").getAsString();
                    event.getChannel().sendMessage(text).queue();
                }
            } catch (IOException e){
                String text = null;
                try {
                    text = JsonParser.parseReader(new FileReader("languages/" + language + ".json")).getAsJsonObject().get("advanced_warnings").getAsJsonObject().get("invalid_website").getAsString();
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                }
                if (e.getMessage().equals(args[1].replace("/", "").replace("https:", ""))){
                    assert text != null;
                    event.getChannel().sendMessage("'" + e.getMessage() + text).queue();
                } else {
                    e.printStackTrace();
                }
            }
        }
    }
}
