package me.engo.zerotwo.commands;

import com.google.gson.JsonParser;
import me.engo.zerotwo.Config;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class Ping extends ListenerAdapter {
	
	public static String alias = "There's no aliases.";
	
	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		Config c = new Config();
		String[] messageSent = event.getMessage().getContentRaw().split(" ");
		if (event.getAuthor().isBot()) return;
		
		if (messageSent[0].equalsIgnoreCase(c.prefix + "ping")) {

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

				if (messageSent.length >= 2) {
					String text = JsonParser.parseReader(new FileReader("languages/" + language + ".json")).getAsJsonObject().get("basic_warnings").getAsJsonObject().get("high_parameters").getAsString();
					event.getChannel().sendMessage(text).queue();
				} else {
					event.getChannel().sendMessage(":ping_pong: Pong! " + event.getJDA().getRestPing().complete() + "ms").queue();
				}
			} catch (FileNotFoundException e){
				e.printStackTrace();
			}
		}
	}

}
