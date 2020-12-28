package me.engo.zerotwo.commands;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import com.google.gson.JsonParser;
import me.engo.zerotwo.Config;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Userinfo extends ListenerAdapter {

    public static String alias = "ui";
    public String month;

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent Context) {
        Config c = new Config();
        String[] messageSent = Context.getMessage().getContentRaw().split(" ");
        if (Context.getAuthor().isBot()) return;

        if (messageSent[0].equalsIgnoreCase(c.prefix + "userinfo") || messageSent[0].equalsIgnoreCase(c.prefix + alias)) {

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

                if (messageSent.length > 2) {
                    String text = JsonParser.parseReader(new FileReader("languages/" + language + ".json")).getAsJsonObject().get("basic_warnings").getAsJsonObject().get("high_parameters").getAsString();
                    Context.getChannel().sendMessage(text).queue();
                } else {
                    if (messageSent.length == 2) {

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

                        if (u.getTimeCreated().getMonth().toString().toLowerCase().equals("january")) {
                            month = "January";
                        } else if (u.getTimeCreated().getMonth().toString().toLowerCase().equals("february")) {
                            month = "February";
                        } else if (u.getTimeCreated().getMonth().toString().toLowerCase().equals("march")) {
                            month = "March";
                        } else if (u.getTimeCreated().getMonth().toString().toLowerCase().equals("april")) {
                            month = "April";
                        } else if (u.getTimeCreated().getMonth().toString().toLowerCase().equals("may")) {
                            month = "May";
                        } else if (u.getTimeCreated().getMonth().toString().toLowerCase().equals("june")) {
                            month = "June";
                        } else if (u.getTimeCreated().getMonth().toString().toLowerCase().equals("july")) {
                            month = "July";
                        } else if (u.getTimeCreated().getMonth().toString().toLowerCase().equals("august")) {
                            month = "August";
                        } else if (u.getTimeCreated().getMonth().toString().toLowerCase().equals("september")) {
                            month = "September";
                        } else if (u.getTimeCreated().getMonth().toString().toLowerCase().equals("october")) {
                            month = "October";
                        } else if (u.getTimeCreated().getMonth().toString().toLowerCase().equals("november")) {
                            month = "November";
                        } else if (u.getTimeCreated().getMonth().toString().toLowerCase().equals("december")) {
                            month = "December";
                        } else {
                            month = u.getTimeCreated().getMonth().toString().toLowerCase();
                        }

                        if (!(u.isBot())) {
                            EmbedBuilder em = new EmbedBuilder();
                            em.setTitle("User Info");
                            em.addField("Name", u.getName(), true);
                            em.addField("Tag", u.getAsTag().replace(u.getName() + "#", ""), true);
                            em.addField("ID", u.getId(), false);
                            em.addField("Created", u.getTimeCreated().getDayOfMonth() + "." + month + "." + u.getTimeCreated().getYear(), false);
                            em.setThumbnail(u.getAvatarUrl());
                            em.setColor(new Color(c.Color));
                            em.setFooter(c.footer1, c.footer2);
                            Context.getChannel().sendMessage(em.build()).queue();
                        } else {
                            String text = JsonParser.parseReader(new FileReader("languages/" + language + ".json")).getAsJsonObject().get("advanced_warnings").getAsJsonObject().get("user_false").getAsString();
                            Context.getChannel().sendMessage(text + c.prefix + "botinfo`").queue();
                        }
                    } else {

                        User u = Context.getAuthor();

                        if (u.getTimeCreated().getMonth().toString().toLowerCase().equals("january")) {
                            month = "January";
                        } else if (u.getTimeCreated().getMonth().toString().toLowerCase().equals("february")) {
                            month = "February";
                        } else if (u.getTimeCreated().getMonth().toString().toLowerCase().equals("march")) {
                            month = "March";
                        } else if (u.getTimeCreated().getMonth().toString().toLowerCase().equals("april")) {
                            month = "April";
                        } else if (u.getTimeCreated().getMonth().toString().toLowerCase().equals("may")) {
                            month = "May";
                        } else if (u.getTimeCreated().getMonth().toString().toLowerCase().equals("june")) {
                            month = "June";
                        } else if (u.getTimeCreated().getMonth().toString().toLowerCase().equals("july")) {
                            month = "July";
                        } else if (u.getTimeCreated().getMonth().toString().toLowerCase().equals("august")) {
                            month = "August";
                        } else if (u.getTimeCreated().getMonth().toString().toLowerCase().equals("september")) {
                            month = "September";
                        } else if (u.getTimeCreated().getMonth().toString().toLowerCase().equals("october")) {
                            month = "October";
                        } else if (u.getTimeCreated().getMonth().toString().toLowerCase().equals("november")) {
                            month = "November";
                        } else if (u.getTimeCreated().getMonth().toString().toLowerCase().equals("december")) {
                            month = "December";
                        } else {
                            month = u.getTimeCreated().getMonth().toString().toLowerCase();
                        }

                        EmbedBuilder em = new EmbedBuilder();
                        em.setTitle("User Info");
                        em.addField("Name", Context.getAuthor().getName(), true);
                        em.addField("Tag", Context.getAuthor().getAsTag().replace(Context.getAuthor().getName() + "#", ""), true);
                        em.addField("ID", Context.getAuthor().getId(), false);
                        em.addField("Created", Context.getAuthor().getTimeCreated().getDayOfMonth() + "." + month + "." + Context.getAuthor().getTimeCreated().getYear(), false);
                        em.setThumbnail(Context.getAuthor().getAvatarUrl());
                        em.setColor(new Color(c.Color));
                        em.setFooter(c.footer1, c.footer2);
                        Context.getChannel().sendMessage(em.build()).queue();
                    }
                }
            } catch (FileNotFoundException e){
                e.printStackTrace();
            }
        }
    }
}
