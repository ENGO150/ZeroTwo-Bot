package me.engo.zerotwo.commands;

import me.engo.zerotwo.Config;
import me.engo.zerotwo.handlers.Translate;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

public class Guildlist extends ListenerAdapter {

    public static String alias = "gl";

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent Context) {
        Config c = new Config();
        String[] messageSent = Context.getMessage().getContentRaw().split(" ");
        if (Context.getAuthor().isBot()) return;

        if (messageSent[0].equalsIgnoreCase(c.prefix + "guildlist") || messageSent[0].equalsIgnoreCase(c.prefix + alias)) {

            try {
                String language;
                File languages = new File("Database/Language/" + Context.getAuthor().getId());
                if (languages.exists()) {
                    File[] languages_ = languages.listFiles();
                    assert languages_ != null;
                    language = languages_[0].getName();
                } else {
                    language = "english_en";
                }

                if (Context.getAuthor().getId().equals("634385503956893737")) {

                    List<Guild> guilds = Context.getJDA().getGuilds();

                    if (messageSent.length == 2){
                        Guild g = guilds.get(Integer.parseInt(messageSent[1]));
                        if (g.getSelfMember().hasPermission(Permission.CREATE_INSTANT_INVITE)) {
                            Context.getChannel().sendMessage(Objects.requireNonNull(g.getDefaultChannel()).createInvite().complete().getUrl()).queue();
                        } else {
                            String text = Translate.getTranslate(language, "basic_warnings", "bot_permissions_false");
                            Context.getChannel().sendMessage(text).queue();
                        }
                        return;
                    }

                    StringBuilder a = new StringBuilder();

                    for (int i = 0; i < guilds.size(); i++) {
                        a.append("\n").append(i).append(". ").append(guilds.get(i).getName());
                    }

                    File f = new File("assets/GL.txt");
                    Files.createFile(Paths.get(f.getPath()));
                    f.setWritable(true);
                    f.setExecutable(true);
                    FileWriter fw = new FileWriter(f);
                    fw.write(a.toString().substring(1));
                    fw.close();

                    Context.getChannel().sendMessage("HERE").addFile(f).queue();
                    Thread.sleep(1000);
                    Files.delete(f.toPath());
                } else {
                    String text = Translate.getTranslate(language, "basic_warnings", "developer_false");
                    Context.getChannel().sendMessage(text).queue();
                }
            } catch (IOException | InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}
