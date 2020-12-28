package me.engo.zerotwo.commands;

import java.io.File;
import java.util.Random;

import me.engo.zerotwo.Config;
import me.engo.zerotwo.handlers.Translate;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Flip extends ListenerAdapter {
	
	public static String alias = "coin";
	public String side;
	
	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent Context) {
		Config c = new Config();
		String[] messageSent = Context.getMessage().getContentRaw().split(" ");
		if (Context.getAuthor().isBot()) return;
		
		if (messageSent[0].equalsIgnoreCase(c.prefix + "flip") || messageSent[0].equalsIgnoreCase(c.prefix + alias)) {

			String language;
			File languages = new File("Database/Language/" + Context.getAuthor().getId());
			if (languages.exists()) {
				File[] languages_ = languages.listFiles();
				assert languages_ != null;
				language = languages_[0].getName();
			} else {
				language = "english_en";
			}

			if (messageSent.length < 2) {
				String text = Translate.getTranslate(language, "basic_warnings", "low_parameters");
				Context.getChannel().sendMessage(text).queue();
			} else if (messageSent.length == 2) {
				if (!(messageSent[1].toLowerCase().equals("tail")) && !(messageSent[1].toLowerCase().equals("head"))) {
					System.out.println(messageSent[1]);
					String text = Translate.getTranslate(language, "advanced_warnings", "invalid_coin_side");
					Context.getChannel().sendMessage(text + c.prefix + "flip [head/tail])").queue();
				} else {
					Random rnd = new Random();
					int rndm = rnd.nextInt(10);

					if (rndm <= 5) {
						side = messageSent[1].toLowerCase();
						String text1 = Translate.getTranslate(language, "logs", "flip_template");
						String text2 = Translate.getTranslate(language, "logs", "flip_won");
						Context.getChannel().sendMessage(text1 + side + text2).queue();
					} else {
						if (messageSent[1].toLowerCase().equals("head")) {
							side = "tail";
						}
						if (messageSent[1].toLowerCase().equals("tail")) {
							side = "head";
						}
						String text1 = Translate.getTranslate(language, "logs", "flip_template");
						String text2 = Translate.getTranslate(language, "logs", "flip_lost");
						Context.getChannel().sendMessage(text1 + side + text2).queue();
					}
				}
			} else {
				String text = Translate.getTranslate(language, "basic_warnings", "high_parameters");
				Context.getChannel().sendMessage(text).queue();
			}
		}
	}

}
