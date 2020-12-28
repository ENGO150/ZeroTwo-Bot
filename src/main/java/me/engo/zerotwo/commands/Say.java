package me.engo.zerotwo.commands;

import com.google.gson.JsonParser;
import me.engo.zerotwo.Config;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class Say extends ListenerAdapter {

    public static String alias = "s";

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent Context) {
        Config c = new Config();
        String[] messageSent = Context.getMessage().getContentRaw().split(" ");
        if (Context.getAuthor().isBot()) return;

        if (messageSent[0].equalsIgnoreCase(c.prefix + "say") || messageSent[0].equalsIgnoreCase(c.prefix + alias)) {

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

                if (messageSent.length < 2) {
                    String text = JsonParser.parseReader(new FileReader("languages/" + language + ".json")).getAsJsonObject().get("basic_warnings").getAsJsonObject().get("low_parameters").getAsString();
                    Context.getChannel().sendMessage(text).queue();
                } else {

                    int a = messageSent.length;

                    String[] slova = new String[a];

                    System.arraycopy(messageSent, 1, slova, 0, a - 1);

                    StringBuilder done = new StringBuilder();

                    for (int i = 0; i < slova.length - 1; i++) {
                        done.append(" ").append(slova[i]);
                    }

                    done = new StringBuilder(done.substring(1));

                    EmbedBuilder em = new EmbedBuilder();
                    em.setDescription(Context.getAuthor().getAsMention() + " said:\n" + done);
                    em.setColor(new Color(c.Color));
                    em.setFooter(c.footer1, c.footer2);

                    Context.getChannel().sendMessage(em.build()).queue();
                    Context.getMessage().delete().complete();
                }
            } catch (FileNotFoundException e){
                e.printStackTrace();
            }
        }
    }
}
