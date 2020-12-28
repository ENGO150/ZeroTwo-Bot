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

public class Mcserver extends ListenerAdapter {

    public static String alias = "There's no aliases.";

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        Config c = new Config();
        String[] args = event.getMessage().getContentRaw().split(" ");
        if (event.getAuthor().isBot()) return;

        if (args[0].equalsIgnoreCase(c.prefix + "mcserver")) {

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
                } else if (args.length == 2) {
                    EmbedBuilder builder = new EmbedBuilder();
                    builder.setImage("http://status.mclive.eu/" + args[1] + "/" + args[1] + "/25565/banner.png?rnd=0.5795028031631655");

                    builder.setFooter(c.footer1, c.footer2);
                    builder.setColor(new Color(c.Color));
                    event.getChannel().sendMessage(builder.build()).queue();
                } else if (args.length == 3) {
                    EmbedBuilder builder = new EmbedBuilder();
                    builder.setImage("http://status.mclive.eu/" + args[2] + "/" + args[1] + "/25565/banner.png?rnd=0.5795028031631655");

                    builder.setFooter(c.footer1, c.footer2);
                    builder.setColor(new Color(c.Color));
                    event.getChannel().sendMessage(builder.build()).queue();
                } else if (args.length == 4) {
                    EmbedBuilder builder = new EmbedBuilder();
                    builder.setImage("http://status.mclive.eu/" + args[2] + "/" + args[1] + "/" + args[3] + "/banner.png?rnd=0.5795028031631655");

                    builder.setFooter(c.footer1, c.footer2);
                    builder.setColor(new Color(c.Color));
                    event.getChannel().sendMessage(builder.build()).queue();
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
