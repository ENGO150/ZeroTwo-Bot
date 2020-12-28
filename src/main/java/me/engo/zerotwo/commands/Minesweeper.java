package me.engo.zerotwo.commands;

import com.google.gson.JsonParser;
import me.engo.zerotwo.Config;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class Minesweeper extends ListenerAdapter {

    public static String alias = "ms";

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        Config c = new Config();
        String[] args = event.getMessage().getContentRaw().split(" ");
        if (event.getAuthor().isBot()) return;

        if (args[0].equalsIgnoreCase(c.prefix + "minesweeper") || args[0].equalsIgnoreCase(c.prefix + alias)) {

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
                    event.getChannel().sendMessage("||:white_small_square:||||:white_small_square:||||:white_small_square:||||:white_small_square:||||:white_small_square:||||:white_small_square:||\n||:one:||||:one:||||:one:||||:one:||||:one:||||:one:||\n||:one:||||:bomb:||||:one:||||:one:||||:bomb:||||:one:||\n||:one:||||:two:||||:two:||||:three:||||:two:||||:two:||\n||:one:||||:two:||||:bomb:||||:two:||||:bomb:||||:two:||\n||:one:||||:bomb:||||:two:||||:two:||||:two:||||:bomb:||").queue();
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
