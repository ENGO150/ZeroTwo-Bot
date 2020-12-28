package me.engo.zerotwo.commands;

import com.google.gson.JsonParser;
import me.engo.zerotwo.Config;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;

public class Pornhub extends ListenerAdapter {

    public static String alias = "phub";

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        Config c = new Config();
        String[] args = event.getMessage().getContentRaw().split(" ");
        if (event.getAuthor().isBot()) return;

        if (args[0].equalsIgnoreCase(c.prefix + "pornhub") || args[0].equalsIgnoreCase(c.prefix + alias)) {

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

                if (args.length < 2 || !Arrays.toString(args).contains(";") || args[1].equalsIgnoreCase(";")) {
                    String text = JsonParser.parseReader(new FileReader("languages/" + language + ".json")).getAsJsonObject().get("basic_warnings").getAsJsonObject().get("low_parameters").getAsString();
                    event.getChannel().sendMessage(text).queue();
                } else {StringBuilder word = new StringBuilder();

                    for (int i = 1; i < args.length; i++) {
                        word.append("+").append(args[i]);
                    }

                    word = new StringBuilder(word.toString().replace(";", "�"));

                    String firstWord;
                    String secondWord;

                    firstWord = word.substring(0, word.indexOf("�"));
                    secondWord = word.substring(word.indexOf("�") + 1);

                    firstWord = firstWord.substring(1);

                    char a = secondWord.charAt(0);

                    boolean b = String.valueOf(a).equalsIgnoreCase("+");

                    if (b) {secondWord = secondWord.substring(1);}

                    EmbedBuilder em = new EmbedBuilder();
                    em.setImage("https://api.alexflipnote.dev/pornhub?text=" + firstWord + "&text2=" + secondWord);
                    em.setColor(new Color(c.Color));
                    em.setFooter(c.footer1, c.footer2);

                    event.getChannel().sendMessage(em.build()).queue();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
