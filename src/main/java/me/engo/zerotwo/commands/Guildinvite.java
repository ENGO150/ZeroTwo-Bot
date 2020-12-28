package me.engo.zerotwo.commands;

import me.engo.zerotwo.Config;
import me.engo.zerotwo.handlers.Translate;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.File;

public class Guildinvite extends ListenerAdapter {

    public static String alias = "ginvite";

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        Config c = new Config();
        String[] args = event.getMessage().getContentRaw().split(" ");
        if (event.getAuthor().isBot()) return;

        if (args[0].equalsIgnoreCase(c.prefix + "guildinvite") || args[0].equalsIgnoreCase(c.prefix + alias)) {

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
                String text = Translate.getTranslate(language, "logs", "ginvite");
                event.getChannel().createInvite().queue(invite -> event.getChannel().sendMessage(text + invite.getUrl()).queue());
            } else {
                String text = Translate.getTranslate(language, "basic_warnings", "high_parameters");
                event.getChannel().sendMessage(text).queue();
            }
        }
    }
}
