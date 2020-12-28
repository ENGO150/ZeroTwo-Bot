package me.engo.zerotwo.commands;

import java.awt.Color;
import java.io.File;

import me.engo.zerotwo.Config;
import me.engo.zerotwo.handlers.Translate;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Drunk extends ListenerAdapter {

    public static String alias = "There's no aliases.";

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {

        Config c = new Config();
        String[] messageSent = event.getMessage().getContentRaw().split(" ");
        if (event.getAuthor().isBot()) return;

        if (messageSent[0].equalsIgnoreCase(c.prefix + "drunk")) {

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
                String s = event.getAuthor().getAvatarUrl();
                assert s != null;
                String st = s.substring(0, s.length() - 4);
                String done = st + ".png";

                em.setImage("https://api.alexflipnote.dev/filter/magik?image=" + done);
                em.setColor(new Color(c.Color));
                em.setFooter(c.footer1, c.footer2);

                event.getChannel().sendMessage(em.build()).queue();
            } else if (messageSent.length == 2) {
                User u = event.getMessage().getMentionedUsers().get(0);

                if (u == null){
                    String text = Translate.getTranslate(language, "basic_warnings", "low_parameters");
                    event.getChannel().sendMessage(text).queue();
                    return;
                }

                String s = u.getAvatarUrl();
                assert s != null;
                String st = s.substring(0, s.length() - 4);
                String done = st + ".png";

                em.setImage("https://api.alexflipnote.dev/filter/magik?image=" + done);
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
