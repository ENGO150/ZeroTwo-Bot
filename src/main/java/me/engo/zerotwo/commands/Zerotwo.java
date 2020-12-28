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
import java.util.Random;

public class Zerotwo extends ListenerAdapter {

    public static String alias = "zt";

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        Config c = new Config();
        String[] args = event.getMessage().getContentRaw().split(" ");
        if (event.getAuthor().isBot()) return;

        if (args[0].equalsIgnoreCase(c.prefix + "zerotwo") || args[0].equalsIgnoreCase(c.prefix + alias)) {

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
                    String[] zt = new String[19];
                    Random rnd = new Random();

                    zt[0] = "https://www.pngitem.com/pimgs/m/230-2304108_zerotwo-zero-two-darlinginthefranxx-darling-in-zero-two.png";
                    zt[1] = "https://i.pinimg.com/originals/b1/29/ef/b129ef0221c71384b6586ca02fdb1d73.jpg";
                    zt[2] = "https://www.pngitem.com/pimgs/m/186-1861172_zerotwo-anime-darlinginthefranxx-animegirl-smile-darling-in-the.png";
                    zt[3] = "https://i.pinimg.com/originals/b3/81/a7/b381a7a3602874a957e1293d999bef59.jpg";
                    zt[4] = "https://thumbs.gfycat.com/AdmiredWeepyHartebeest-max-1mb.gif";
                    zt[5] = "https://data.whicdn.com/images/311867143/original.gif";
                    zt[6] = "https://data.whicdn.com/images/311860667/original.gif";
                    zt[7] = "https://media1.giphy.com/media/fH6uIAcZ8zdNsbK5WE/giphy.gif";
                    zt[8] = "https://giffiles.alphacoders.com/209/209716.gif";
                    zt[9] = "https://media3.giphy.com/media/57XXgyHn5iZQ9kYvKR/giphy.gif";
                    zt[10] = "https://data.whicdn.com/images/311867192/original.gif";
                    zt[11] = "https://i.pinimg.com/originals/d3/4d/09/d34d096dcc353904428e2a4be4507f75.gif";
                    zt[12] = "https://i.pinimg.com/originals/fa/a4/75/faa475f97255b1f33dc304720790892c.gif";
                    zt[13] = "https://i.pinimg.com/originals/80/d5/7d/80d57da371dedc447fc556c0e203b263.gif";
                    zt[14] = "https://i.imgur.com/z1DkZLB.jpg";
                    zt[15] = "https://static.zerochan.net/Zero.Two.%28Darling.in.the.FranXX%29.full.2395560.jpg";
                    zt[16] = "https://media.giphy.com/media/cP6OYFU26vssjWxZBc/giphy.gif";
                    zt[17] = "https://66.media.tumblr.com/78949ccdd350d29cc0539bc6bd7a9879/tumblr_p38f2xo0xv1vvfsp9o1_540.gifv";
                    zt[18] = "https://data.whicdn.com/images/312717949/original.gif";

                    EmbedBuilder builder = new EmbedBuilder();
                    builder.setImage(zt[rnd.nextInt(18)]);
                    builder.setFooter(c.footer1, c.footer2);
                    builder.setColor(new Color(c.Color));
                    event.getChannel().sendMessage(builder.build()).queue();
                } else {
                    String text = JsonParser.parseReader(new FileReader("languages/" + language + ".json")).getAsJsonObject().get("basic_warnings").getAsJsonObject().get("high_parameters").getAsString();
                    event.getChannel().sendMessage(text).queue();
                }
            } catch (FileNotFoundException e){
                e.printStackTrace();
            }
        }
    }
}
