package me.engo.zerotwo.commands;

import com.google.gson.JsonParser;
import me.engo.zerotwo.Config;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Random;

public class Slap extends ListenerAdapter {

    public static String alias = "There's no aliases.";

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent Context) {
        Config c = new Config();
        String[] messageSent = Context.getMessage().getContentRaw().split(" ");
        if (Context.getAuthor().isBot()) return;

        if (messageSent[0].equalsIgnoreCase(c.prefix + "slap")) {

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
                    String text = JsonParser.parseReader(new FileReader("languages/" + language + ".json")).getAsJsonObject().get("basic_warnings").getAsJsonObject().get("high_parameters").getAsString();
                    Context.getChannel().sendMessage(text).queue();
                } else {
                    if (c.roleplay) {
                        Random rnd = new Random();
                        int rndm = rnd.nextInt(3) - 1;
                        if (rndm < 0){ rndm = rndm + 1; }

                        String[] zt = new String[3];
                        zt[0] = "https://media.giphy.com/media/jLeyZWgtwgr2U/giphy.gif";
                        zt[1] = "https://i.imgur.com/o2SJYUS.gif";
                        zt[2] = "https://media0.giphy.com/media/Zau0yrl17uzdK/giphy.gif";

                        User u = Context.getMessage().getMentionedUsers().get(0);

                        if (u == null){
                            try {
                                String text = JsonParser.parseReader(new FileReader("languages/" + language + ".json")).getAsJsonObject().get("basic_warnings").getAsJsonObject().get("low_parameters").getAsString();
                                Context.getChannel().sendMessage(text).queue();
                                return;
                            } catch (FileNotFoundException ex) {
                                ex.printStackTrace();
                            }
                        }

                        assert u != null;

                        EmbedBuilder em = new EmbedBuilder();
                        em.setDescription(Context.getAuthor().getName() + " slaps " + u.getName());
                        em.setImage(zt[rndm]);
                        em.setFooter(c.footer1, c.footer2);
                        em.setColor(new Color(c.Color));
                        Context.getChannel().sendMessage(em.build()).queue();
                    } else {
                        String text = JsonParser.parseReader(new FileReader("languages/" + language + ".json")).getAsJsonObject().get("basic_warnings").getAsJsonObject().get("roleplaymodule_false").getAsString();
                        Context.getChannel().sendMessage(text).queue();
                    }
                }
            } catch (FileNotFoundException e){
                e.printStackTrace();
            }
        }
    }

}
