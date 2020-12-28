package me.engo.zerotwo.commands;

import com.google.gson.JsonParser;
import me.engo.zerotwo.Config;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Upvotes extends ListenerAdapter {

    public static String alias = "There's no aliases.";

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        Config c = new Config();
        String[] args = event.getMessage().getContentRaw().split(" ");
        if (event.getAuthor().isBot()) return;

        if (args[0].equalsIgnoreCase(c.prefix + "upvotes")) {

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
                    Document document1 = Jsoup.connect("https://top.gg/bot/668847135860719639").followRedirects(true).timeout(10000).get();
                    Document document2 = Jsoup.connect("https://botsfordiscord.com/bot/668847135860719639").followRedirects(false).timeout(10000).get();
                    String top = document1.getElementById("points").ownText();
                    String bfd = document2.getElementsByClass("tag is-warning").get(0).ownText();

                    String text1 = JsonParser.parseReader(new FileReader("languages/" + language + ".json")).getAsJsonObject().get("logs").getAsJsonObject().get("upvotes_final_1").getAsString();
                    String text2 = JsonParser.parseReader(new FileReader("languages/" + language + ".json")).getAsJsonObject().get("logs").getAsJsonObject().get("upvotes_final_2").getAsString();

                    event.getChannel().sendMessage(text1 + (Integer.parseInt(top) + Integer.parseInt(bfd)) + text2 + "TOP.gg = " + top + ", BotsForDiscord.com = " + bfd + ")").queue();
                } else {
                    String text = JsonParser.parseReader(new FileReader("languages/" + language + ".json")).getAsJsonObject().get("basic_warnings").getAsJsonObject().get("high_parameters").getAsString();
                    event.getChannel().sendMessage(text).queue();
                }
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
