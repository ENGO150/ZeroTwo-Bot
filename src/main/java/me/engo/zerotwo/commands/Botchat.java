package me.engo.zerotwo.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

import me.engo.zerotwo.Config;
import me.engo.zerotwo.handlers.Translate;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Botchat extends ListenerAdapter {

    public static String alias = "chat";

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        Config c = new Config();
        String[] args = event.getMessage().getContentRaw().split(" ");
        if (event.getAuthor().isBot()) return;

        if (args[0].equalsIgnoreCase(c.prefix + "botchat") || args[0].equalsIgnoreCase(c.prefix + alias)) {

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
                } else {
                    if (!Objects.requireNonNull(event.getMember()).hasPermission(Permission.MESSAGE_MANAGE)){
                        String text = Translate.getTranslate(language, "basic_warnings", "permissions_false");
                        event.getChannel().sendMessage(text).queue();
                        return;
                    }
                    File f = new File("Database/Botchat/" + event.getGuild().getId());

                    if (args[1].equalsIgnoreCase("on")){
                        //ZAPNUTĂŤ
                            if (args.length == 3){
                                //POKUD JE VĹ E SPRĂ�VNÄš
                                if (f.mkdir()) {
                                    //POKUD JEĹ TÄš NEEXISTUJE

                                    TextChannel channel = event.getMessage().getMentionedChannels().get(0);

                                    Files.createFile(Paths.get(f.getPath() + "/" + channel.getId()));

                                    String text = Translate.getTranslate(language, "logs", "botchat_enabled");
                                    event.getChannel().sendMessage(text).queue();
                                } else {
                                    //POKUD JIĹ˝ EXISTUJE
                                    String text = Translate.getTranslate(language, "logs", "botchat_err_1");
                                    event.getChannel().sendMessage(text).queue();
                                }
                            } else {
                                if (args.length < 4){
                                    String text = Translate.getTranslate(language, "basic_warnings", "low_parameters");
                                    event.getChannel().sendMessage(text).queue();
                                } else {
                                    String text = Translate.getTranslate(language, "basic_warnings", "high_parameters");
                                    event.getChannel().sendMessage(text).queue();
                                }
                            }
                    } else if (args[1].equalsIgnoreCase("off")){
                        //VYPNUTĂŤ
                        if (args.length == 2){
                            if (f.exists()){
                                //POKUD EXISTUJE
                                for (File file : Objects.requireNonNull(f.listFiles())){
                                    Files.delete(file.toPath());
                                }
                                Files.delete(f.toPath());

                                String text = Translate.getTranslate(language, "logs", "botchat_disabled");
                                event.getChannel().sendMessage(text).queue();
                            } else {
                                //POKUD NEEXISTUJE
                                String text = Translate.getTranslate(language, "logs", "botchat_err_2");
                                event.getChannel().sendMessage(text).queue();
                            }
                        } else {
                            String text = Translate.getTranslate(language, "basic_warnings", "high_parameters");
                            event.getChannel().sendMessage(text).queue();
                        }
                    } else {
                        String text = Translate.getTranslate(language, "advanced_warnings", "invalid_parameter");
                        event.getChannel().sendMessage("'" + args[1] + text).queue();
                    }
                }

            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }

}
