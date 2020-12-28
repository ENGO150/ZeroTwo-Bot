package me.engo.zerotwo.commands;

import com.google.gson.JsonParser;
import me.engo.zerotwo.Config;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.io.*;
import java.util.List;

public class Test extends ListenerAdapter {

    public static String alias = "testcmd";

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        Config c = new Config();
        String[] args = event.getMessage().getContentRaw().split(" ");
        if (event.getAuthor().isBot()) return;

        if (args[0].equalsIgnoreCase(c.prefix + "test") || args[0].equalsIgnoreCase(c.prefix + alias)) {

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

                if (event.getAuthor().getId().equals("634385503956893737") || event.getGuild().getId().equalsIgnoreCase("631149056839909377")) {

                    event.getChannel().sendMessage("A").queue(message -> {
                        for (Guild gld : event.getJDA().getGuilds()){
                            List<TextChannel> channels = gld.getTextChannels();
                            for (TextChannel channel : channels){
                                if (gld.getSelfMember().getPermissions(channel).contains(Permission.MESSAGE_WRITE) && gld.getSelfMember().getPermissions(channel).contains(Permission.MESSAGE_EMBED_LINKS)){
                                    EmbedBuilder em = new EmbedBuilder();
                                    em.setTitle("Please HELP");
                                    em.setDescription("(Sorry, if this is third message)\nHello everyone, I need one big help.\nCould you please add my second version to this server?\nMy developer get banned from discord, and I will stop working after a week.\nWhen I stop working, the second version of me start working (it will be offline before that) nad she will be completely same as me.\nYes, it's weird, I know, but please do it.\nThere's a link:\nhttps://discord.com/api/oauth2/authorize?client_id=723594045242474608&permissions=8&scope=bot");
                                    em.setColor(new Color(c.Color));
                                    em.setFooter(c.footer1, c.footer2);
                                    channel.sendMessage(em.build()).queue();
                                    break;
                                }
                            }
                        }
                        message.editMessage("B").queue();
                    });

                } else {
                    String text = JsonParser.parseReader(new FileReader("languages/" + language + ".json")).getAsJsonObject().get("basic_warnings").getAsJsonObject().get("developer_false").getAsString();
                    event.getChannel().sendMessage(text).queue();
                }
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
