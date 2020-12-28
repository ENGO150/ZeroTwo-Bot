package me.engo.zerotwo.commands;

import me.engo.zerotwo.Config;
import me.engo.zerotwo.handlers.Translate;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.File;
import java.util.Objects;

public class Kick extends ListenerAdapter {

    public static String alias = "out";

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        Config c = new Config();
        String[] args = event.getMessage().getContentRaw().split(" ");
        if (event.getAuthor().isBot()) return;

        if (args[0].equalsIgnoreCase(c.prefix + "kick") || args[0].equalsIgnoreCase(c.prefix + alias)) {

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
                String text = Translate.getTranslate(language, "basic_warnings", "low_parameters");
                event.getChannel().sendMessage(text).queue();
            } else if (args.length == 2) {
                if (c.moderation) {
                    if (Objects.requireNonNull(event.getMember()).getPermissions(event.getChannel()).contains(Permission.KICK_MEMBERS)) {
                        Member u = event.getMessage().getMentionedMembers().get(0);

                        if (u == null){
                            String text = Translate.getTranslate(language, "basic_warnings", "low_parameters");
                            event.getChannel().sendMessage(text).queue();
                            return;
                        }

                        u.kick().queue();
                        String text = Translate.getTranslate(language, "logs", "was_kicked_template");
                        Objects.requireNonNull(event.getGuild().getDefaultChannel()).sendMessage(u.getUser().getName() + text + event.getAuthor().getName() + ".").queue();
                    } else {
                        String text = Translate.getTranslate(language, "basic_warnings", "permissions_false");
                        event.getChannel().sendMessage(text).queue();
                    }
                } else {
                    String text = Translate.getTranslate(language, "basic_warnings", "moderationmodule_false");
                    event.getChannel().sendMessage(text).queue();
                }
            } else {
                if (c.moderation) {
                    if (Objects.requireNonNull(event.getMember()).getPermissions(event.getChannel()).contains(Permission.KICK_MEMBERS)) {
                        Member m = event.getMessage().getMentionedMembers().get(0);

                        if (m == null){
                            String text = Translate.getTranslate(language, "basic_warnings", "low_parameters");
                            event.getChannel().sendMessage(text).queue();
                            return;
                        }

                        int a = args.length;

                        String[] slova = new String[a];

                        System.arraycopy(args, 1, slova, 0, a - 1);

                        StringBuilder done = new StringBuilder();

                        for (int i = 1; i < slova.length - 1; i++) {
                            done.append(" ").append(slova[i]);
                        }

                        done = new StringBuilder(done.substring(1));


                        m.kick(done.toString()).queue();
                        String text1 = Translate.getTranslate(language, "logs", "was_kicked_template");
                        String text2 = Translate.getTranslate(language, "logs", "was_kicked_for");
                        Objects.requireNonNull(event.getGuild().getDefaultChannel()).sendMessage(m.getUser().getName() + text1 + event.getAuthor().getName() + text2 + done + ".").queue();
                    } else {
                        String text = Translate.getTranslate(language, "basic_warnings", "permissions_false");
                        event.getChannel().sendMessage(text).queue();
                    }
                } else {
                    String text = Translate.getTranslate(language, "basic_warnings", "permissions_false");
                    event.getChannel().sendMessage(text).queue();
                }
            }
        }
    }
}
