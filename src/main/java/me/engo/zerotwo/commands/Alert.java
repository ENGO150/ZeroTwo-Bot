package me.engo.zerotwo.commands;

import me.engo.zerotwo.Config;
import me.engo.zerotwo.handlers.Translate;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.File;

public class Alert extends ListenerAdapter {

    public static String alias = "There's no aliases";

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        Config c = new Config();
        String[] args = event.getMessage().getContentRaw().split(" ");
        if (event.getAuthor().isBot()) return;

        if (args[0].equalsIgnoreCase(c.prefix + "alert")) {

            String language;
            File languages = new File("Database/Language/" + event.getAuthor().getId());
            if (languages.exists()) {
                File[] languages_ = languages.listFiles();
                assert languages_ != null;
                language = languages_[0].getName();
            } else {
                language = "english_en";
            }

            if (event.getAuthor().getId().equalsIgnoreCase("634385503956893737")){
                if (args.length < 2) {
                    String text = Translate.getTranslate(language, "basic_warnings", "low_parameters");
                    event.getChannel().sendMessage(text).queue();
                } else {
                    int a = args.length;

                    String[] slova = new String[a];

                    System.arraycopy(args, 1, slova, 0, a - 1);

                    StringBuilder done = new StringBuilder();

                    for (int i = 0; i < slova.length - 1; i++) {
                        done.append(" ").append(slova[i]);
                    }

                    done = new StringBuilder(done.substring(1));

                    StringBuilder finalDone = done;
                    event.getChannel().sendMessage("***Processing...***").queue(message -> {
                        for (Guild g : event.getJDA().getGuilds()){
                            File f = new File("Database/Updates/" + g.getId());
                            TextChannel tc;
                            if (f.exists()){
                                File f1 = new File("Database/Updates/" + g.getId() + "/Channel");
                                File[] files = f1.listFiles();
                                assert files != null;
                                tc = g.getTextChannelById(files[0].getName());
                            } else {
                                tc = g.getDefaultChannel();
                            }

                            if (tc != null && g.getSelfMember().getPermissions(tc).contains(Permission.MESSAGE_WRITE)){
                                tc.sendMessage(finalDone.toString()).queue();
                            }
                        }
                        message.editMessage("**Done.**").queue();
                    });
                }
            } else {
                String text = Translate.getTranslate(language, "basic_warnings", "developer_false");
                event.getChannel().sendMessage(text).queue();
            }
        }
    }

}
