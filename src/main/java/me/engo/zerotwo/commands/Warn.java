package me.engo.zerotwo.commands;

import com.google.gson.JsonParser;
import me.engo.zerotwo.Config;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.io.*;
import java.util.Objects;

public class Warn extends ListenerAdapter {

    public static String alias = "There's no aliases.";

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent Context) {
        Config c = new Config();
        String[] messageSent = Context.getMessage().getContentRaw().split(" ");
        if (Context.getAuthor().isBot()) return;

        if (messageSent[0].equalsIgnoreCase(c.prefix + "warn")) {

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

                if (messageSent.length < 3) {
                    String text = JsonParser.parseReader(new FileReader("languages/" + language + ".json")).getAsJsonObject().get("basic_warnings").getAsJsonObject().get("low_parameters").getAsString();
                    Context.getChannel().sendMessage(text).queue();
                } else {

                    if (c.moderation) {
                        if (Objects.requireNonNull(Context.getMember()).getPermissions(Context.getChannel()).contains(Permission.MESSAGE_MANAGE)) {
                            Context.getMessage().delete().queue();

                            User u = Context.getMessage().getMentionedUsers().get(0);

                            if (u == null){
                                try {
                                    String text = JsonParser.parseReader(new FileReader("languages/" + language + ".json")).getAsJsonObject().get("basic_warnings").getAsJsonObject().get("low_parameters").getAsString();
                                    Context.getChannel().sendMessage(text).queue();
                                    return;
                                } catch (FileNotFoundException ex) {
                                    ex.printStackTrace();
                                }
                            }

                            assert u != null;

                            int aa = messageSent.length;

                            String[] slova = new String[aa];

                            System.arraycopy(messageSent, 1, slova, 0, aa - 1);

                            StringBuilder done = new StringBuilder();

                            for (int i = 1; i < slova.length - 1; i++) {
                                done.append(" ").append(slova[i]);
                            }

                            done = new StringBuilder(done.substring(1));

                            EmbedBuilder em = new EmbedBuilder();
                            em.setTitle("Warn");
                            em.addField("Name", u.getName(), false);
                            em.addField("Reason", String.valueOf(done), false);
                            em.addField("Moderator", Context.getAuthor().getAsMention(), false);
                            em.addField("Guild", Context.getGuild().getName(), false);
                            em.setThumbnail("https://media1.giphy.com/media/fH6uIAcZ8zdNsbK5WE/giphy.gif");
                            em.setFooter(c.footer1, c.footer2);
                            em.setColor(new Color(c.Color));
                            Context.getChannel().sendMessage(em.build()).queue();

                            if (Context.getGuild().getId().equals("591606754530557952")){
                                File a = new File("Database/Warns/" + u.getId());
                                if (a.exists()){
                                    File[] warns = a.listFiles();
                                    assert warns != null;
                                    String last_warn = warns[warns.length - 1].getName();
                                    int last_warn_id = Integer.parseInt(last_warn);
                                    int newWarn = last_warn_id + 1;
                                    File b = new File("Database/Warns/" + u.getId() + "/" + newWarn);
                                    b.createNewFile();
                                    b.setWritable(true);
                                    FileWriter myWriter = new FileWriter(b);
                                    myWriter.write(String.valueOf(done));
                                    myWriter.close();
                                } else {
                                    a.mkdir();
                                    File b = new File("Database/Warns/" + u.getId() + "/" + 1);
                                    b.createNewFile();
                                    FileWriter myWriter = new FileWriter(b);
                                    myWriter.write(String.valueOf(done));
                                    myWriter.close();
                                }
                            }
                        }
                    } else {
                        String text = JsonParser.parseReader(new FileReader("languages/" + language + ".json")).getAsJsonObject().get("basic_warnings").getAsJsonObject().get("moderationmodule_false").getAsString();
                        Context.getChannel().sendMessage(text).queue();
                    }

                }
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
