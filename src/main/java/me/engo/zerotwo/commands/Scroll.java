package me.engo.zerotwo.commands;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import com.google.gson.JsonParser;
import me.engo.zerotwo.Config;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Scroll extends ListenerAdapter {

    public static String alias = "There's no aliases.";

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent Context) {
        Config c = new Config();
        String[] messageSent = Context.getMessage().getContentRaw().split(" ");
        if (Context.getAuthor().isBot()) return;

        if (messageSent[0].equalsIgnoreCase(c.prefix + "scroll")) {

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

                if (messageSent.length < 2) {
                    String text = JsonParser.parseReader(new FileReader("languages/" + language + ".json")).getAsJsonObject().get("basic_warnings").getAsJsonObject().get("low_parameters").getAsString();
                    Context.getChannel().sendMessage(text).queue();
                } else if (messageSent.length > 2) {
                    String text = JsonParser.parseReader(new FileReader("languages/" + language + ".json")).getAsJsonObject().get("advanced_warnings").getAsJsonObject().get("template_fonts").getAsString();
                    Context.getChannel().sendMessage(text + c.prefix + "scroll Zero+Two`").queue();
                } else {

                    String text1 = messageSent[1];

                    EmbedBuilder em = new EmbedBuilder();
                    em.setImage("https://api.alexflipnote.dev/scroll?text=" + text1);
                    em.setColor(new Color(c.Color));
                    em.setFooter(c.footer1, c.footer2);

                    Context.getChannel().sendMessage(em.build()).queue();

                }
            } catch (FileNotFoundException e){
                e.printStackTrace();
            }
        }
    }
}
