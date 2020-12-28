package me.engo.zerotwo.commands;

import com.google.gson.JsonParser;
import me.engo.zerotwo.Config;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;

public class Premium extends ListenerAdapter {

    public static String alias = "There's no aliases.";

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        Config c = new Config();
        String[] messageSent = event.getMessage().getContentRaw().split(" ");
        if (event.getAuthor().isBot()) return;

        if (messageSent[0].equalsIgnoreCase(c.prefix + "premium")) {

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

                if (messageSent.length < 2) {
                    String text = JsonParser.parseReader(new FileReader("languages/" + language + ".json")).getAsJsonObject().get("logs").getAsJsonObject().get("premium").getAsString();
                    event.getChannel().sendMessage(text).queue();
                } else if (messageSent.length == 2){
                    if (event.getAuthor().getId().equalsIgnoreCase("634385503956893737")){
                        User u = event.getMessage().getMentionedUsers().get(0);

                        File f = new File("Database/Premium/" + u.getId());

                        if (!f.exists()) {
                            Files.createFile(f.toPath());
                            String text = JsonParser.parseReader(new FileReader("languages/" + language + ".json")).getAsJsonObject().get("logs").getAsJsonObject().get("done").getAsString();
                            event.getChannel().sendMessage(text).queue();
                        } else {
                            Files.delete(f.toPath());
                            String text = JsonParser.parseReader(new FileReader("languages/" + language + ".json")).getAsJsonObject().get("logs").getAsJsonObject().get("done").getAsString();
                            event.getChannel().sendMessage(text).queue();
                        }
                    } else {
                        String text = JsonParser.parseReader(new FileReader("languages/" + language + ".json")).getAsJsonObject().get("basic_warnings").getAsJsonObject().get("developer_false").getAsString();
                        event.getChannel().sendMessage(text).queue();
                    }
                } else {
                    String text = JsonParser.parseReader(new FileReader("languages/" + language + ".json")).getAsJsonObject().get("basic_warnings").getAsJsonObject().get("high_parameters").getAsString();
                    event.getChannel().sendMessage(text).queue();
                }
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }

}
