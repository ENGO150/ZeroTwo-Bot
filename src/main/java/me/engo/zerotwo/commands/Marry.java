package me.engo.zerotwo.commands;

import me.engo.zerotwo.Config;
import me.engo.zerotwo.handlers.Translate;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class Marry extends ListenerAdapter {

    public static String alias = "love";

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        Config c = new Config();
        String[] args = event.getMessage().getContentRaw().split(" ");
        if (event.getAuthor().isBot()) return;

        if (args[0].equalsIgnoreCase(c.prefix + "marry") || args[0].equalsIgnoreCase(c.prefix + alias)) {

            String language;
            File languages = new File("Database/Language/" + event.getAuthor().getId());
            if (languages.exists()) {
                File[] languages_ = languages.listFiles();
                assert languages_ != null;
                language = languages_[0].getName();
            } else {
                language = "english_en";
            }

            if (args.length > 3) {
                String text = Translate.getTranslate(language, "basic_warnings", "high_parameters");
                event.getChannel().sendMessage(text).queue();
            } else if (args.length < 2) {
                String text = Translate.getTranslate(language, "basic_warnings", "low_parameters");
                event.getChannel().sendMessage(text).queue();
            } else {
                File acc = new File("Database/Marry/" + event.getAuthor().getId());
                switch (args[1].toLowerCase()) {
                    case "send":
                        if (args.length == 3) {
                            if (acc.exists()) {
                                String text = Translate.getTranslate(language, "logs", "marry_already_married");
                                event.getChannel().sendMessage(text).queue();
                            } else {
                                Member m = event.getMessage().getMentionedMembers().get(0);

                                if (m == null){
                                    String text = Translate.getTranslate(language, "basic_warnings", "low_parameters");
                                    event.getChannel().sendMessage(text).queue();
                                    return;
                                }

                                if (m.getUser().isBot()) {
                                    String text = Translate.getTranslate(language, "logs", "marry_cant_marry_bot");
                                    event.getChannel().sendMessage(text).queue();
                                } else {
                                    if (event.getAuthor().getId().equals(m.getId())) {
                                        String text = Translate.getTranslate(language, "logs", "marry_cant_marry_yourself");
                                        event.getChannel().sendMessage(text).queue();
                                    } else {
                                        File marry = new File("Database/Marry/" + m.getId());
                                        if (marry.exists()) {
                                            String text = Translate.getTranslate(language, "logs", "marry_user_is_married");
                                            event.getChannel().sendMessage(text).queue();
                                        } else {
                                            File requestfo = new File("Database/Marry/Requests/" + m.getId());
                                            if (requestfo.exists()) {
                                                String text = Translate.getTranslate(language, "logs", "marry_user_already_have_a_request");
                                                event.getChannel().sendMessage(text).queue();
                                            } else {
                                                requestfo.mkdir();
                                                File requestfi = new File("Database/Marry/Requests/" + m.getId() + "/" + event.getAuthor().getId());
                                                try {
                                                    requestfi.createNewFile();
                                                } catch (IOException e) {
                                                    e.printStackTrace();
                                                }

                                                String text1 = Translate.getTranslate(language, "logs", "marry_request_was_sent");
                                                String text2 = Translate.getTranslate(language, "logs", "marry_sent_you_1");
                                                String text3 = Translate.getTranslate(language, "logs", "marry_sent_you_2");
                                                String text4 = Translate.getTranslate(language, "logs", "marry_sent_you_3");

                                                event.getChannel().sendMessage(text1).queue();
                                                Objects.requireNonNull(event.getGuild().getDefaultChannel()).sendMessage(m.getAsMention() + ", " + event.getAuthor().getAsMention() + text2 + c.prefix + "marry accept " + event.getAuthor().getId() + text3 + c.prefix + "marry decline " + event.getAuthor().getId() + text4).queue();
                                            }
                                        }
                                    }
                                }
                            }
                        } else {
                            String text = Translate.getTranslate(language, "logs", "marry_must_provide_a_user");
                            event.getChannel().sendMessage(text).queue();
                        }
                        break;

                    case "accept":
                        if (args.length == 3) {
                            if (acc.exists()) {
                                String text = Translate.getTranslate(language, "logs", "marry_already_married");
                                event.getChannel().sendMessage(text).queue();
                            } else {
                                File requestfo = new File("Database/Marry/Requests/" + event.getAuthor().getId());

                                if (requestfo.exists()) {
                                    File requestfi = new File("Database/Marry/Requests/" + event.getAuthor().getId() + "/" + args[2]);
                                    if (requestfi.exists()) {
                                        File zenich = new File("Database/Marry/" + args[2]);
                                        if (zenich.exists()) {
                                            String text = Translate.getTranslate(language, "logs", "marry_user_is_married");
                                            event.getChannel().sendMessage(text).queue();

                                            if (requestfi.delete()) ;
                                            if (requestfo.delete()) ;
                                        } else {
                                            if (requestfi.delete()) ;
                                            if (requestfo.delete()) ;

                                            File a = new File("Database/Marry/" + event.getAuthor().getId());
                                            File b = new File("Database/Marry/" + args[2]);

                                            File ce = new File("Database/Marry/" + event.getAuthor().getId() + "/" + args[2]);
                                            File d = new File("Database/Marry/" + args[2] + "/" + event.getAuthor().getId());

                                            a.mkdir();
                                            b.mkdir();
                                            try {
                                                ce.createNewFile();
                                                d.createNewFile();
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                            String text1 = Translate.getTranslate(language, "logs", "marry_you_are_now_married");
                                            String text2 = Translate.getTranslate(language, "logs", "marry_accepted");
                                            event.getChannel().sendMessage(text1).queue();
                                            Objects.requireNonNull(event.getGuild().getDefaultChannel()).sendMessage(Objects.requireNonNull(event.getJDA().getUserById(args[2])).getAsMention() + ", " + event.getAuthor().getAsMention() + text2).queue();
                                        }
                                    } else {
                                        String text = Translate.getTranslate(language, "logs", "marry_invalid_request");
                                        event.getChannel().sendMessage(text).queue();
                                    }
                                } else {
                                    String text = Translate.getTranslate(language, "logs", "marry_no_requests");
                                    event.getChannel().sendMessage(text).queue();
                                }
                            }
                        } else {
                            String text = Translate.getTranslate(language, "logs", "marry_must_provide_a_user");
                            event.getChannel().sendMessage(text).queue();
                        }
                        break;

                    case "decline":
                        if (args.length == 3) {
                            File requestfi = new File("Database/Marry/Requests/" + event.getAuthor().getId() + "/" + args[2]);
                            File requestfo = new File("Database/Marry/Requests/" + event.getAuthor().getId());

                            if (requestfo.exists()) {
                                if (requestfi.exists()) {
                                    if (requestfi.delete()) ;
                                    if (requestfo.delete()) ;

                                    String text1 = Translate.getTranslate(language, "logs", "marry_you_declined");
                                    String text2 = Translate.getTranslate(language, "logs", "marry_you_were_declined");

                                    event.getChannel().sendMessage(text1 + Objects.requireNonNull(event.getJDA().getUserById(args[2])).getName() + ".").queue();
                                    Objects.requireNonNull(event.getGuild().getDefaultChannel()).sendMessage(Objects.requireNonNull(event.getJDA().getUserById(args[2])).getAsMention() + ", " + event.getAuthor().getAsMention() + text2).queue();
                                } else {
                                    String text = Translate.getTranslate(language, "logs", "marry_invalid_user");
                                    event.getChannel().sendMessage(text).queue();
                                }
                            } else {
                                String text = Translate.getTranslate(language, "logs", "marry_no_requests");
                                event.getChannel().sendMessage(text).queue();
                            }
                        } else {
                            String text = Translate.getTranslate(language, "logs", "marry_must_provide_a_user_id");
                            event.getChannel().sendMessage(text).queue();
                        }
                        break;

                    case "end":
                        if (args.length == 2) {
                            if (acc.exists()) {
                                File[] manzel = acc.listFiles();
                                assert manzel != null;
                                String manzelkaa = manzel[0].getName();

                                File manzelka = new File("Database/Marry/" + manzelkaa + "/" + event.getAuthor().getId());
                                File manzelka_ = new File("Database/Marry/" + manzelkaa);

                                if (manzel[0].delete()) ;
                                if (acc.delete()) ;

                                if (manzelka.delete()) ;
                                if (manzelka_.delete()) ;

                                String text1 = Translate.getTranslate(language, "logs", "marry_you_ended_marriage");
                                String text2 = Translate.getTranslate(language, "logs", "marry_your_marriage_ended");

                                event.getChannel().sendMessage(text1).queue();
                                Objects.requireNonNull(event.getGuild().getDefaultChannel()).sendMessage(Objects.requireNonNull(event.getJDA().getUserById(manzelkaa)).getAsMention() + ", " + event.getAuthor().getAsMention() + text2).queue();
                            } else {
                                String text = Translate.getTranslate(language, "logs", "marry_you_are_not_married");
                                event.getChannel().sendMessage(text).queue();
                            }
                        } else {
                            String text = Translate.getTranslate(language, "basic_warnings", "high_parameters");
                            event.getChannel().sendMessage(text).queue();
                        }
                        break;

                    default:
                        String text = Translate.getTranslate(language, "logs", "marry_invalid_parameter");
                        event.getChannel().sendMessage(text + args[1] + "'").queue();
                        break;
                }
            }
        }
    }
}
