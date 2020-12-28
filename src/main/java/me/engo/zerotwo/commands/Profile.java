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
import java.util.Objects;

public class Profile extends ListenerAdapter {

    public static String alias = "me";

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        Config c = new Config();
        String[] args = event.getMessage().getContentRaw().split(" ");
        if (event.getAuthor().isBot()) return;

        if (args[0].equalsIgnoreCase(c.prefix + "profile") || args[0].equalsIgnoreCase(c.prefix + alias)) {

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

                if (args.length == 1 || args.length == 2){
                    User u;
                    if (args.length == 1){
                        u = event.getAuthor();
                    } else {
                        u = event.getMessage().getMentionedUsers().get(0);

                        if (u == null){
                            try {
                                String text = JsonParser.parseReader(new FileReader("languages/" + language + ".json")).getAsJsonObject().get("basic_warnings").getAsJsonObject().get("low_parameters").getAsString();
                                event.getChannel().sendMessage(text).queue();
                                return;
                            } catch (FileNotFoundException ex) {
                                ex.printStackTrace();
                            }
                        }

                        assert u != null;
                    }


                    EmbedBuilder em = new EmbedBuilder();
                    File marry = new File("Database/Marry/" +u.getId());

                    em.setTitle("Profile");
                    em.addField("Name", u.getAsMention(), false);
                    em.setThumbnail(u.getAvatarUrl());
                    em.setFooter(c.footer1, c.footer2);
                    em.setColor(new Color(c.Color));

                    //Marry
                    if (marry.exists()) {
                        File[] a = marry.listFiles();
                        assert a != null;
                        String names = a[0].getName();
                        String name = Objects.requireNonNull(event.getJDA().getUserById(names)).getAsTag();
                        em.addField("Married", name, false);
                    } else {
                        em.addField("Married", "No", false);
                    }

                    //Badges
                    File badges = new File("Database/Profile/" + u.getId());
                    if (badges.exists()){
                        File[] a = badges.listFiles();
                        assert a != null;
                        StringBuilder odznaky = new StringBuilder();
                        for (File odznak : a){
                            odznaky.append(", ").append(odznak.getName());
                        }
                        em.addField("Badges", odznaky.toString().substring(2), false);
                    } else {
                        em.addField("Badges", "None", false);
                    }

                    event.getChannel().sendMessage(em.build()).queue();
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
