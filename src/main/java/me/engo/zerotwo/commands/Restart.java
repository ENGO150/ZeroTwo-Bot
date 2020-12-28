package me.engo.zerotwo.commands;

import com.google.gson.JsonParser;
import me.engo.zerotwo.Bot;
import me.engo.zerotwo.Config;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Restart extends ListenerAdapter {

    public static String alias = "reset";

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        Config c = new Config();
        String[] args = event.getMessage().getContentRaw().split(" ");
        if (event.getAuthor().isBot()) return;

        if (args[0].equalsIgnoreCase(c.prefix + "restart") || args[0].equalsIgnoreCase(c.prefix + alias)) {

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

                if (event.getAuthor().getId().equals("634385503956893737")) {

                    event.getChannel().sendMessage("OK.").queue();

                    //REMINDER
                    File f = new File("Cooldown/Reminder");
                    File[] files = f.listFiles();
                    assert files != null;
                    assert files.length != 0;
                    for (File file : files) {
                        User u = event.getJDA().getUserById(file.getName());
                        assert u != null;
                        System.out.println(u.getAsTag());
                        u.openPrivateChannel().queue(privateChannel -> privateChannel.sendMessage("Your reminder was stopped, because I am now shutting down.").queue());
                        Files.delete(Paths.get(file.getPath()));
                    }

                    event.getJDA().shutdown();
                    try {
                        String[] arguments = new String[0];
                        Bot.main(arguments);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

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
