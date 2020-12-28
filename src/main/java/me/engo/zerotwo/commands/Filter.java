package me.engo.zerotwo.commands;

import me.engo.zerotwo.Config;
import me.engo.zerotwo.handlers.Translate;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;

public class Filter extends ListenerAdapter {

    public static String alias = "There's no aliases";

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        Config c = new Config();
        String[] args = event.getMessage().getContentRaw().split(" ");
        if (event.getAuthor().isBot()) return;

        if (args[0].equalsIgnoreCase(c.prefix + "filter")) {

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
                    String text = Translate.getTranslate(language, "basic_warnings", "low_parameters");
                    event.getChannel().sendMessage(text).queue();
                } else if (args.length == 2 || args.length == 3){

                    File f = new File("Database/Filter/" + event.getGuild().getId());

                    if (args[1].equalsIgnoreCase("on")){
                        if (args.length == 3) {
                            String text = Translate.getTranslate(language, "basic_warnings", "high_parameters");
                            event.getChannel().sendMessage(text).queue();
                            return;
                        }

                        if (Objects.requireNonNull(event.getMember()).hasPermission(Permission.MESSAGE_MANAGE)){
                            if (!f.exists()){
                                Files.createDirectory(f.toPath());
                                String text = Translate.getTranslate(language, "logs", "filter_enabled");
                                event.getChannel().sendMessage(text).queue();
                            } else {
                                String text = Translate.getTranslate(language, "logs", "filter_err_1");
                                event.getChannel().sendMessage(text).queue();
                            }
                        } else {
                            String text = Translate.getTranslate(language, "basic_warnings", "permissions_false");
                            event.getChannel().sendMessage(text).queue();
                        }
                    } else if (args[1].equalsIgnoreCase("off")){
                        if (args.length == 3) {
                            String text = Translate.getTranslate(language, "basic_warnings", "high_parameters");
                            event.getChannel().sendMessage(text).queue();
                            return;
                        }

                        if (Objects.requireNonNull(event.getMember()).hasPermission(Permission.MESSAGE_MANAGE)){
                            if (f.exists()){
                                File[] files = f.listFiles();
                                assert files != null;
                                if (!(files.length == 0)){
                                    for (File file : files) {
                                        Files.delete(file.toPath());
                                    }
                                }
                                Files.delete(f.toPath());
                                String text = Translate.getTranslate(language, "logs", "filter_disabled");
                                event.getChannel().sendMessage(text).queue();
                            } else {
                                String text = Translate.getTranslate(language, "logs", "filter_err_2");
                                event.getChannel().sendMessage(text).queue();
                            }
                        } else {
                            String text = Translate.getTranslate(language, "basic_warnings", "permissions_false");
                            event.getChannel().sendMessage(text).queue();
                        }
                    } else if (args[1].equalsIgnoreCase("set")) {
                        if (args.length == 2) {
                            String text = Translate.getTranslate(language, "basic_warnings", "low_parameters");
                            event.getChannel().sendMessage(text).queue();
                            return;
                        }

                        if (Objects.requireNonNull(event.getMember()).hasPermission(Permission.MESSAGE_MANAGE)) {
                            if (!f.exists()){
                                String text = Translate.getTranslate(language, "logs", "filter_err_2");
                                event.getChannel().sendMessage(text).queue();
                                return;
                            }
                            String a = args[2].toLowerCase();
                            File b = new File(f + "/" + a);
                            if (b.exists()){
                                String text = Translate.getTranslate(language, "logs", "filter_err_3");
                                event.getChannel().sendMessage(text).queue();
                            } else {
                                Files.createFile(b.toPath());
                                String text = Translate.getTranslate(language, "logs", "filter_added");
                                event.getChannel().sendMessage(text).queue();
                            }
                        } else {
                            String text = Translate.getTranslate(language, "basic_warnings", "permissions_false");
                            event.getChannel().sendMessage(text).queue();
                        }
                    } else if (args[1].equalsIgnoreCase("del")){
                        if (args.length == 2) {
                            String text = Translate.getTranslate(language, "basic_warnings", "low_parameters");
                            event.getChannel().sendMessage(text).queue();
                            return;
                        }

                        if (!f.exists()){
                            String text = Translate.getTranslate(language, "logs", "filter_err_2");
                            event.getChannel().sendMessage(text).queue();
                            return;
                        }

                        if (Objects.requireNonNull(event.getMember()).hasPermission(Permission.MESSAGE_MANAGE)) {
                            File b = new File(f + "/" + args[2].toLowerCase());
                            if (b.exists()){
                                Files.delete(b.toPath());
                                String text = Translate.getTranslate(language, "logs", "filter_removed");
                                event.getChannel().sendMessage(text).queue();
                            } else {
                                String text = Translate.getTranslate(language, "logs", "filter_err_4");
                                event.getChannel().sendMessage(text).queue();
                            }
                        } else {
                            String text = Translate.getTranslate(language, "basic_warnings", "permissions_false");
                            event.getChannel().sendMessage(text).queue();
                        }
                    } else {
                        String text = Translate.getTranslate(language, "advanced_warnings", "invalid_parameter");
                        event.getChannel().sendMessage("'" + args[1] + text).queue();
                    }
                } else {
                    String text = Translate.getTranslate(language, "basic_warnings", "high_parameters");
                    event.getChannel().sendMessage(text).queue();
                }
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }

}
