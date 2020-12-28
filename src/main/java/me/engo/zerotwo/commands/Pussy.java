package me.engo.zerotwo.commands;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Random;

import com.google.gson.JsonParser;

import me.engo.zerotwo.Config;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Pussy extends ListenerAdapter {

    public static String alias = "hole";

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent Context) {
        Config c = new Config();
        String[] messageSent = Context.getMessage().getContentRaw().split(" ");
        if (Context.getAuthor().isBot()) return;

        if (messageSent[0].equalsIgnoreCase(c.prefix + "pussy") || messageSent[0].equalsIgnoreCase(c.prefix + alias)) {

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

                    if (c.nsfw) {
                        if (Context.getChannel().isNSFW()) {

                        	Random rnd = new Random();
                            int rndm = rnd.nextInt(8) - 1;
                            if (rndm < 0){ rndm = rndm + 1; }

                            String[] zt = new String[8];
                            zt[0] = "https://cdn.discordapp.com/attachments/609425123291693056/692188869600346203/327842_-_Darling_in_the_FranXX_Zero_Two.jpg";
                            zt[1] = "https://cdn.discordapp.com/attachments/609425123291693056/692292420658003998/image0.jpg";
                            zt[2] = "https://cdn.discordapp.com/attachments/609425123291693056/692180358908608572/20200219_223512460_iOS_1.jpg";
                            zt[3] = "https://cdn.discordapp.com/attachments/609425123291693056/692180336247046144/20200219_170501625_iOS_1_1_1_1_1.jpg";
                            zt[4] = "https://cdn.discordapp.com/attachments/609425123291693056/689002482025889846/ddp8jhq-98d6fc55-8970-4bf4-ab88-c115626fd67a.jpg";
                            zt[5] = "https://cdn.discordapp.com/attachments/609425123291693056/689000297368256512/image0.jpg";
                            zt[6] = "https://cdn.discordapp.com/attachments/609425123291693056/688998877977903126/image0.jpg";
                            zt[7] = "https://cdn.discordapp.com/attachments/609425123291693056/688664932857937920/image0.jpg";

                            EmbedBuilder em = new EmbedBuilder();
                            em.setImage(zt[rndm]);
                            em.setFooter(c.footer1, c.footer2);
                            em.setColor(new Color(c.Color));

                            Context.getChannel().sendMessage(em.build()).queue();

                        } else {
                            String text = JsonParser.parseReader(new FileReader("languages/" + language + ".json")).getAsJsonObject().get("basic_warnings").getAsJsonObject().get("nsfwchannel_false").getAsString();
                            Context.getChannel().sendMessage(text).queue();
                        }
                    } else {
                        String text = JsonParser.parseReader(new FileReader("languages/" + language + ".json")).getAsJsonObject().get("basic_warnings").getAsJsonObject().get("nsfwmodule_false").getAsString();
                        Context.getChannel().sendMessage(text).queue();
                    }

                } else {

                    if (messageSent[1].equalsIgnoreCase("secret") && messageSent.length == 2){
                        EmbedBuilder em = new EmbedBuilder();
                        em.setImage("https://cdn.discordapp.com/attachments/670661798000852994/693423233252327474/images.jpeg");
                        em.setFooter(c.footer1, c.footer2);
                        em.setColor(new Color(c.Color));

                        Context.getChannel().sendMessage(em.build()).queue();
                    } else {

                        String text = JsonParser.parseReader(new FileReader("languages/" + language + ".json")).getAsJsonObject().get("basic_warnings").getAsJsonObject().get("high_parameters").getAsString();

                        Context.getChannel().sendMessage(text).queue();

                    }
                }
            } catch (FileNotFoundException e){
                e.printStackTrace();
            }
        }
    }
}
