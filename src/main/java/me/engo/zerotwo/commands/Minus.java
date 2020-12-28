package me.engo.zerotwo.commands;

import com.google.gson.JsonParser;
import me.engo.zerotwo.Config;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class Minus extends ListenerAdapter {
	
	public static String alias = "-";
	
	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent Context) {
		Config c = new Config();
		String[] messageSent = Context.getMessage().getContentRaw().split(" ");
		if (Context.getAuthor().isBot()) return;
		
		if (messageSent[0].equalsIgnoreCase(c.prefix + "minus") || messageSent[0].equalsIgnoreCase(c.prefix + alias)) {

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
				} else if (messageSent.length > 3) {
					String text = JsonParser.parseReader(new FileReader("languages/" + language + ".json")).getAsJsonObject().get("basic_warnings").getAsJsonObject().get("high_parameters").getAsString();
					Context.getChannel().sendMessage(text).queue();
				} else {
					try {
						double a = Double.parseDouble(messageSent[1]);
						double b = Double.parseDouble(messageSent[2]);

						double result = a - b;

						String text1 = JsonParser.parseReader(new FileReader("languages/" + language + ".json")).getAsJsonObject().get("logs").getAsJsonObject().get("count_result_1").getAsString();
						String text2 = JsonParser.parseReader(new FileReader("languages/" + language + ".json")).getAsJsonObject().get("logs").getAsJsonObject().get("count_result_2").getAsString();
						Context.getChannel().sendMessage(text1 + result + text2).queue();
					} catch (Exception ex) {
						String text = JsonParser.parseReader(new FileReader("languages/" + language + ".json")).getAsJsonObject().get("logs").getAsJsonObject().get("count_error").getAsString();
						Context.getChannel().sendMessage(text + ex.getMessage()).queue();
					}
				}
			} catch (FileNotFoundException e){
				e.printStackTrace();
			}
		}
	}
}
