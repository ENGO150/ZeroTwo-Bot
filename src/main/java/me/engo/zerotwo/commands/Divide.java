package me.engo.zerotwo.commands;

import me.engo.zerotwo.Config;
import me.engo.zerotwo.handlers.Translate;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.File;

public class Divide extends ListenerAdapter {
	
	public static String alias = "/";
	
	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent Context) {
		Config c = new Config();
		String[] messageSent = Context.getMessage().getContentRaw().split(" ");
		if (Context.getAuthor().isBot()) return;
		
		if (messageSent[0].equalsIgnoreCase(c.prefix + "divide") || messageSent[0].equalsIgnoreCase(c.prefix + alias)) {

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
				String text = Translate.getTranslate(language, "basic_warnings", "low_parameters");
				Context.getChannel().sendMessage(text).queue();
			} else if (messageSent.length > 3) {
				String text = Translate.getTranslate(language, "basic_warnings", "high_parameters");
				Context.getChannel().sendMessage(text).queue();
			} else {
				try {
					double a = Double.parseDouble(messageSent[1]);
					double b = Double.parseDouble(messageSent[2]);

					double result = a / b;

					String text1 = Translate.getTranslate(language, "logs", "count_result_1");
					String text2 = Translate.getTranslate(language, "logs", "count_result_2");
					Context.getChannel().sendMessage(text1 + result + text2).queue();
				} catch (Exception ex) {
					String text = Translate.getTranslate(language, "logs", "count_error");
					Context.getChannel().sendMessage(text + ex.getMessage()).queue();
				}
			}
		}
	}
}
