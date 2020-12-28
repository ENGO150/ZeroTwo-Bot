package me.engo.zerotwo.commands;

import com.google.gson.JsonParser;
import me.engo.zerotwo.Config;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class Saydm extends ListenerAdapter {

    public static String alias = "sdm";

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent Context) {
        Config c = new Config();
        String[] messageSent = Context.getMessage().getContentRaw().split(" ");
        if (Context.getAuthor().isBot()) return;

        if (messageSent[0].equalsIgnoreCase(c.prefix + "saydm") || messageSent[0].equalsIgnoreCase(c.prefix + alias)) {

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

                if (messageSent.length < 3) {
                    String text = JsonParser.parseReader(new FileReader("languages/" + language + ".json")).getAsJsonObject().get("basic_warnings").getAsJsonObject().get("low_parameters").getAsString();
                    Context.getChannel().sendMessage(text).queue();
                } else {

                    User u = Context.getMessage().getMentionedUsers().get(0);

                    int a = messageSent.length;

                    String[] slova = new String[a];

                    System.arraycopy(messageSent, 1, slova, 0, a - 1);

                    StringBuilder done = new StringBuilder();

                    for (int i = 1; i < slova.length - 1; i++) {
                        done.append(" ").append(slova[i]);
                    }

                    done = new StringBuilder(done.substring(1));

                    String done_ = done.toString();

                    u.openPrivateChannel().queue((channel -> channel.sendMessage(Context.getAuthor().getName() + " | " + done_).queue()));

                    String text = JsonParser.parseReader(new FileReader("languages/" + language + ".json")).getAsJsonObject().get("logs").getAsJsonObject().get("done").getAsString();
                    Context.getChannel().sendMessage(text).queue();
                }
            } catch (FileNotFoundException e){
                e.printStackTrace();
            }
        }
    }
}
