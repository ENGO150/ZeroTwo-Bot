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

public class Png extends ListenerAdapter {

    public static String alias = "There's no aliases.";

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event){
        Config c = new Config();
        String[] args = event.getMessage().getContentRaw().split(" ");
        if (event.getAuthor().isBot()) return;

        if (args[0].equalsIgnoreCase(c.prefix + "png")){

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
                    String text = JsonParser.parseReader(new FileReader("languages/" + language + ".json")).getAsJsonObject().get("basic_warnings").getAsJsonObject().get("low_parameters").getAsString();
                    event.getChannel().sendMessage(text).queue();
                } else {

                    int a = args.length;

                    String[] slova = new String[a];

                    System.arraycopy(args, 1, slova, 0, a - 1);

                    StringBuilder done = new StringBuilder();

                    for (int i = 0; i < slova.length - 1; i++) {
                        done.append("+").append(slova[i]);
                    }

                    done = new StringBuilder(done.substring(1));

                    EmbedBuilder em = new EmbedBuilder();
                    em.setImage("https://dummyimage.com/1000x395/417426.png/FFA718/&text=" + done);
                    em.setColor(new Color(c.Color));
                    em.setFooter(c.footer1, c.footer2);

                    event.getChannel().sendMessage(em.build()).queue();
                }
            } catch (FileNotFoundException e){
                e.printStackTrace();
            }
        }
    }

}
