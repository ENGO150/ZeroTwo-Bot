package me.engo.zerotwo.commands;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import com.google.gson.JsonParser;
import me.engo.zerotwo.Config;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Supreme extends ListenerAdapter {

    public static String alias = "There's no aliases.";

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent Context) {
        Config c = new Config();
        String[] args = Context.getMessage().getContentRaw().split(" ");
        if (Context.getAuthor().isBot()) return;

        if (args[0].equalsIgnoreCase(c.prefix + "supreme")) {

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

                if (args.length < 2) {
                    String text = JsonParser.parseReader(new FileReader("languages/" + language + ".json")).getAsJsonObject().get("basic_warnings").getAsJsonObject().get("low_parameters").getAsString();
                    Context.getChannel().sendMessage(text).queue();
                } else {

                    int a = args.length;

                    String[] slova = new String[a];

                    System.arraycopy(args, 1, slova, 0, a - 1);

                    StringBuilder done = new StringBuilder();

                    EmbedBuilder em = new EmbedBuilder();
                    em.setFooter(c.footer1, c.footer2);
                    em.setColor(new Color(c.Color));

                    final boolean dark;

                    if (slova[slova.length - 2].equalsIgnoreCase("dark") || slova[slova.length - 2].equalsIgnoreCase("red")){
                        for (int i = 0; i < slova.length - 2; i++) {
                            done.append("+").append(slova[i]);
                        }

                        done = new StringBuilder(done.substring(1));
                    } else {
                        for (int i = 0; i < slova.length - 1; i++) {
                            done.append("+").append(slova[i]);
                        }

                        done = new StringBuilder(done.substring(1));
                    }

                    dark = slova[slova.length - 2].equalsIgnoreCase("dark");

                    em.setImage("https://api.alexflipnote.dev/supreme?text=" + done.toString() + "&dark=" + dark);

                    Context.getChannel().sendMessage(em.build()).queue();

                }
            } catch (FileNotFoundException e){
                e.printStackTrace();
            }
        }
    }

}
