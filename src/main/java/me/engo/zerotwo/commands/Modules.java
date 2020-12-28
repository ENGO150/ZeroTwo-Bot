package me.engo.zerotwo.commands;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import com.google.gson.JsonParser;
import me.engo.zerotwo.Config;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Modules extends ListenerAdapter {
	
	public static String alias = "There's no aliases.";
	
	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		Config c = new Config();
		String[] messageSent = event.getMessage().getContentRaw().split(" ");
		if (event.getAuthor().isBot()) return;
		
		if (messageSent[0].equalsIgnoreCase(c.prefix + "modules")) {

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
					EmbedBuilder em = new EmbedBuilder();
					em.setTitle("List of Modules:");
					em.setDescription("• Help\n• Settings\n• Games\n• Random\n• Moderation\n• Music\n• Info\n• Logs\n• Roleplay\n• Fonts\n• Filters\n• Math\n• NSFW\n• Say\n• Premium\n• Developer\n• News");
					em.setColor(new Color(c.Color));
					em.setFooter(c.footer1, c.footer2);
					event.getChannel().sendMessage(em.build()).queue();
				}
			} catch (FileNotFoundException e){
				e.printStackTrace();
			}
		}
	}

}
