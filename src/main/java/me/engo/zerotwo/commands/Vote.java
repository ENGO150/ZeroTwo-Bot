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

public class Vote extends ListenerAdapter {

    public static String alias = "There's no aliases.";

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event){
        Config c = new Config();
        String[] args = event.getMessage().getContentRaw().split(" ");
        if (event.getAuthor().isBot()) return;

        if (args[0].equalsIgnoreCase(c.prefix + "vote")){

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
                    EmbedBuilder em = new EmbedBuilder();
                    em.addField("TOP.GG", "https://top.gg/bot/668847135860719639", false);
                    em.addField("BOTSFORDISCORD.COM", "https://botsfordiscord.com/bot/668847135860719639", false);
                    em.addField("More", "Still waiting.", false);
                    em.setColor(new Color(c.Color));
                    em.setFooter(c.footer1, c.footer2);
                    event.getChannel().sendMessage(em.build()).queue();
                } else if (args.length == 2) {
                    EmbedBuilder em = new EmbedBuilder();
                    switch (args[1]){
                        case "top.gg":
                            em.setTitle("TOP.GG", "https://top.gg/bot/668847135860719639");
                            em.setColor(new Color(c.Color));
                            em.setFooter(c.footer1, c.footer2);
                            event.getChannel().sendMessage(em.build()).queue();
                            break;

                        case "botsfordiscord.com":
                            em.setTitle("BOTSFORDISCORD.COM", "https://botsfordiscord.com/bot/668847135860719639");
                            em.setColor(new Color(c.Color));
                            em.setFooter(c.footer1, c.footer2);
                            event.getChannel().sendMessage(em.build()).queue();
                            break;

                        default:
                            String text = JsonParser.parseReader(new FileReader("languages/" + language + ".json")).getAsJsonObject().get("advanced_warnings").getAsJsonObject().get("invalid_web").getAsString();
                            event.getChannel().sendMessage(text).queue();
                            break;
                    }
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
