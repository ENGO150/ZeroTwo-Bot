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

public class Trash extends ListenerAdapter {

    public static String alias = "There's no aliases.";

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {

        Config c = new Config();
        String[] messageSent = event.getMessage().getContentRaw().split(" ");
        if (event.getAuthor().isBot()) return;

        if (messageSent[0].equalsIgnoreCase(c.prefix + "trash")) {

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

                EmbedBuilder em = new EmbedBuilder();

                if (messageSent.length < 2) {
                    String text = JsonParser.parseReader(new FileReader("languages/" + language + ".json")).getAsJsonObject().get("basic_warnings").getAsJsonObject().get("low_parameters").getAsString();
                    event.getChannel().sendMessage(text).queue();
                } else if (messageSent.length == 2) {
                    User u = event.getMessage().getMentionedUsers().get(0);

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

                    String s = u.getAvatarUrl();
                    assert s != null;
                    String st = s.substring(0, s.length() - 4);
                    String done = st + ".png";

                    em.setImage("https://api.alexflipnote.dev/trash?face=" + event.getAuthor().getAvatarUrl() + "&trash=" + done);
                    em.setColor(new Color(c.Color));
                    em.setFooter(c.footer1, c.footer2);

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
