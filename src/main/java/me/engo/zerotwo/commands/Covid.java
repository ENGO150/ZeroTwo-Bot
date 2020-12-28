package me.engo.zerotwo.commands;

import me.engo.zerotwo.Config;
import me.engo.zerotwo.handlers.Translate;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.File;
import java.net.URL;

public class Covid extends ListenerAdapter {

    public static String alias = "corona";

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        Config c = new Config();
        String[] args = event.getMessage().getContentRaw().split(" ");
        if (event.getAuthor().isBot()) return;

        if (args[0].equalsIgnoreCase(c.prefix + "covid") || args[0].equalsIgnoreCase(c.prefix + alias)) {

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
                try {
                    URL url = new URL("https://api.covid19api.com/summary");
                    JSONTokener tokener = new JSONTokener(url.openStream());
                    JSONObject obj = new JSONObject(tokener);
                    JSONObject data = obj.getJSONObject("Global");
                    String deaths = String.valueOf(data.getInt("TotalDeaths"));
                    String recovered = String.valueOf(data.getInt("TotalRecovered"));
                    String confirmed = String.valueOf(data.getInt("TotalConfirmed"));

                    event.getChannel().sendMessage("There are " + confirmed + " confirmed, " + recovered + " recovered and " + deaths + " dead people.").queue();
                } catch (Exception e){
                    event.getChannel().sendMessage("Something went wrong. Try it again.").queue();
                }
            } else {
                String text = Translate.getTranslate(language, "basic_warnings", "high_parameters");
                event.getChannel().sendMessage(text).queue();
            }
        }
    }
}
