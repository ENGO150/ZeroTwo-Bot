package me.engo.zerotwo.commands;

import com.google.gson.JsonParser;
import me.engo.zerotwo.Config;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class Source extends ListenerAdapter {

    public static String alias = "src";
    /*public */String cmd_3;
    String language;

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        Config c = new Config();
        String[] args = event.getMessage().getContentRaw().split(" ");
        if (event.getAuthor().isBot()) return;

        if (args[0].equalsIgnoreCase(c.prefix + "source") || args[0].equalsIgnoreCase(c.prefix + alias)) {

            try {
                File languages = new File("Database/Language/" + event.getAuthor().getId());
                if (languages.exists()) {
                    File[] languages_ = languages.listFiles();
                    assert languages_ != null;
                    language = languages_[0].getName();
                } else {
                    language = "english_en";
                }

                if (args.length < 2) {
                    String text = JsonParser.parseReader(new FileReader("languages/" + language + ".json")).getAsJsonObject().get("basic_warnings").getAsJsonObject().get("low_parameters").getAsString();
                    event.getChannel().sendMessage(text).queue();
                } else if (args.length == 2 || (args.length == 3 && args[2].equalsIgnoreCase("raw"))) {
                    String cmd_broken = args[1].toLowerCase();
                    String cmd_1 = cmd_broken.substring(0, cmd_broken.length() - (cmd_broken.length() - 1)).toUpperCase();
                    String cmd_2 = cmd_broken.substring(1);
                    String cmd = cmd_1 + cmd_2;
                    cmd_3 = cmd;
                    if (cmd.endsWith(".java")){ cmd = cmd.substring(0, cmd.length() - 5); }
                    Class.forName("me.engo.zerotwo.commands." + cmd);
                    cmd = cmd + ".java";

                    String text = JsonParser.parseReader(new FileReader("languages/" + language + ".json")).getAsJsonObject().get("logs").getAsJsonObject().get("source").getAsString();
                    if (args.length == 2)
                    {
                    	event.getChannel().sendMessage(text + cmd + ":\n"  + "https://github.com/ENGO150/ZeroTwo-Bot/blob/master/src/main/java/me/engo/zerotwo/commands/" + cmd).queue();
                    } else
                    {
                    	File raw = new File("assets/raw/" + cmd);
                    	event.getChannel().sendMessage(text + cmd + " (RAW)").addFile(raw).queue();
                    }
                } else {
                    String text = JsonParser.parseReader(new FileReader("languages/" + language + ".json")).getAsJsonObject().get("basic_warnings").getAsJsonObject().get("high_parameters").getAsString();
                    event.getChannel().sendMessage(text).queue();
                }
            } catch (FileNotFoundException | ClassNotFoundException e){
                try {
                    String text = JsonParser.parseReader(new FileReader("languages/" + language + ".json")).getAsJsonObject().get("advanced_warnings").getAsJsonObject().get("invalid_command").getAsString();
                    event.getChannel().sendMessage("'" + cmd_3 + text).queue();
                } catch (FileNotFoundException ex){
                    ex.printStackTrace();
                }
            }
        }
    }
}
