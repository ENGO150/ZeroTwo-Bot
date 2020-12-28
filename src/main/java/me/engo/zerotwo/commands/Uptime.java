package me.engo.zerotwo.commands;

import com.google.gson.JsonParser;
import me.engo.zerotwo.Config;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

public class Uptime extends ListenerAdapter {

    public static String alias = "There's no aliases.";

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent Context) {
        Config c = new Config();
        String[] messageSent = Context.getMessage().getContentRaw().split(" ");
        if (Context.getAuthor().isBot()) return;

        if (messageSent[0].equalsIgnoreCase(c.prefix + "uptime")) {

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
                    RuntimeMXBean runTimeMXBean = ManagementFactory.getRuntimeMXBean();
                    long uptime = runTimeMXBean.getUptime();
                    long uptimeInSeconds = uptime / 1000;

                    long hour = uptimeInSeconds / (60 * 60);
                    long minute = (uptimeInSeconds / 60) - (hour * 60);
                    long second = uptimeInSeconds % 60;

                    String text1 = JsonParser.parseReader(new FileReader("languages/" + language + ".json")).getAsJsonObject().get("logs").getAsJsonObject().get("uptime").getAsString();
                    String text2 = JsonParser.parseReader(new FileReader("languages/" + language + ".json")).getAsJsonObject().get("logs").getAsJsonObject().get("uptime_hours").getAsString();
                    String text3 = JsonParser.parseReader(new FileReader("languages/" + language + ".json")).getAsJsonObject().get("logs").getAsJsonObject().get("uptime_minutes").getAsString();
                    String text4 = JsonParser.parseReader(new FileReader("languages/" + language + ".json")).getAsJsonObject().get("logs").getAsJsonObject().get("uptime_seconds").getAsString();

                    Context.getChannel().sendMessage(text1 + hour + text2 + minute + text3 + second + text4).queue();
                } else {
                    String text = JsonParser.parseReader(new FileReader("languages/" + language + ".json")).getAsJsonObject().get("basic_warnings").getAsJsonObject().get("high_parameters").getAsString();
                    Context.getChannel().sendMessage(text).queue();
                }
            } catch (FileNotFoundException e){
                e.printStackTrace();
            }
        }
    }
}
