package me.engo.zerotwo.commands;

import me.engo.zerotwo.Config;
import me.engo.zerotwo.handlers.Translate;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.io.File;

public class Bad extends ListenerAdapter {

    public static String alias = "There's no aliases.";

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        Config c = new Config();
        String[] args = event.getMessage().getContentRaw().split(" ");
        if (event.getAuthor().isBot()) return;

        if (args[0].equalsIgnoreCase(c.prefix + "bad")) {

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
                String url = event.getAuthor().getAvatarUrl();
                assert url != null;
                String done1 = url.substring(0, url.length() - 4);
                String done = done1 + ".png";

                EmbedBuilder em = new EmbedBuilder();
                em.setImage("https://api.alexflipnote.dev/bad?image=" + done);
                em.setColor(new Color(c.Color));
                em.setFooter(c.footer1, c.footer2);
                event.getChannel().sendMessage(em.build()).queue();
            } else if (args.length == 2) {
                User u = event.getMessage().getMentionedUsers().get(0);

                if (u == null){
                    String text = Translate.getTranslate(language, "basic_warnings", "low_parameters");
                    event.getChannel().sendMessage(text).queue();
                    return;
                }

                String url = u.getAvatarUrl();
                assert url != null;
                String done1 = url.substring(0, url.length() - 4);
                String done = done1 + ".png";

                EmbedBuilder em = new EmbedBuilder();
                em.setImage("https://api.alexflipnote.dev/bad?image=" + done);
                em.setColor(new Color(c.Color));
                em.setFooter(c.footer1, c.footer2);
                event.getChannel().sendMessage(em.build()).queue();
            } else {
                String text = Translate.getTranslate(language, "basic_warnings", "high_parameters");

                event.getChannel().sendMessage(text).queue();
            }
        }
    }
}
