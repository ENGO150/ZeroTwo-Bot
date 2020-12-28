package me.engo.zerotwo.commands;

import me.engo.zerotwo.Config;
import me.engo.zerotwo.handlers.Translate;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Language extends ListenerAdapter {

    public static String alias = "There's no aliases.";

    public File folder;

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event){
        Config c = new Config();
        String[] args = event.getMessage().getContentRaw().split(" ");
        if (event.getAuthor().isBot()) return;

        if (args[0].equalsIgnoreCase(c.prefix + "language")) {

            @SuppressWarnings("unused")
			String language_;
            File languages_ = new File("Database/Language/" + event.getAuthor().getId());
            if (languages_.exists()) {
                File[] languages__ = languages_.listFiles();
                assert languages__ != null;
                language_ = languages__[0].getName();
            } else {
                language_ = "english_en";
            }

            folder = new File("Database/Language/" + event.getAuthor().getId());

            String language = null;
            if (args.length < 2) {

                if (!folder.exists()) {
                    language = "English";
                } else {
                    File[] languages = folder.listFiles();
                    assert languages != null;

                    if (languages[0].getName().equals("czech_cz")) {
                        language = "Czech";
                    } else if (languages[0].getName().equals("english_en")) {
                        language = "English";
                    }
                }

                EmbedBuilder em = new EmbedBuilder();
                em.setTitle("Language");
                em.setDescription("Your language is set on " + language + ".");
                em.addField("If you wanna change it, use:", c.prefix + "language [language]", false);
                em.addField("Available languages:", "English\nCzech", false);
                em.setFooter(c.footer1, c.footer2);
                em.setColor(new Color(c.Color));
                event.getChannel().sendMessage(em.build()).queue();
            } else if (args.length == 2) {
                switch (args[1].toLowerCase()) {
                    case "english":
                        File new_ = new File("Database/Language/" + event.getAuthor().getId() + "/english_en");
                        if (folder.exists()) {
                            File[] files = folder.listFiles();

                            assert files != null;
                            files[0].renameTo(new_);
                            event.getChannel().sendMessage("Your language was set to English.").queue();
                        } else {
                            folder.mkdir();

                            try {
                                new_.createNewFile();
                                event.getChannel().sendMessage("Your language was set to English.").queue();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        break;

                    case "czech":
                        File _new_ = new File("Database/Language/" + event.getAuthor().getId() + "/czech_cz");
                        if (folder.exists()) {
                            File[] files = folder.listFiles();

                            assert files != null;
                            files[0].renameTo(_new_);
                            event.getChannel().sendMessage("Tvůj jazyk byl nastaven na Češtinu.").queue();
                        } else {
                            folder.mkdir();

                            try {
                                _new_.createNewFile();
                                event.getChannel().sendMessage("Tvůj jazyk byl nastaven na Češtinu.").queue();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        break;

                    default:
                        event.getChannel().sendMessage("Sorry darling, but language '" + args[1] + "' doesn't exist, or isn't available.").queue();
                        break;
                }
            } else {
                String text = Translate.getTranslate(language, "basic_warnings", "high_parameters");
                event.getChannel().sendMessage(text).queue();
            }
        }
    }
}
