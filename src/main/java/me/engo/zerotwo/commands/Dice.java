package me.engo.zerotwo.commands;

import me.engo.zerotwo.Config;
import me.engo.zerotwo.handlers.Translate;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.File;
import java.util.Random;

public class Dice extends ListenerAdapter {
	
	public static String alias = "There's no aliases.";
	
	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent Context) {
		
		Config c = new Config();
		String[] messageSent = Context.getMessage().getContentRaw().split(" ");
		if (Context.getAuthor().isBot()) return;
		
		if (messageSent[0].equalsIgnoreCase(c.prefix + "dice")) {

			String language;
			File languages = new File("Database/Language/" + Context.getAuthor().getId());
			if (languages.exists()) {
				File[] languages_ = languages.listFiles();
				assert languages_ != null;
				language = languages_[0].getName();
			} else {
				language = "english_en";
			}

			if (messageSent.length > 1) {
				String text = Translate.getTranslate(language, "basic_warnings", "high_parameters");
				Context.getChannel().sendMessage(text).queue();
			} else {

				Random rnd = new Random();

				int rnd1 = rnd.nextInt(6);
				int rnd2 = rnd.nextInt(6);
				int rnd3 = rnd.nextInt(6);

				int rndb1 = rnd.nextInt(6);
				int rndb2 = rnd.nextInt(6);
				int rndb3 = rnd.nextInt(6);

				int rndv = rnd1 + rnd2 + rnd3;
				int rndbv = rndb1 + rndb2 + rndb3;

				if (rndv > rndbv) {
					String text1 = Translate.getTranslate(language, "logs", "dice_won");
					String text2 = Translate.getTranslate(language, "logs", "dice_template");
					Context.getChannel().sendMessage(text1 + rndv + text2 + rndbv + ".").queue();
				}
				if (rndv < rndbv) {
					String text1 = Translate.getTranslate(language, "logs", "dice_lost");
					String text2 = Translate.getTranslate(language, "logs", "dice_template");
					Context.getChannel().sendMessage(text1 + rndv + text2 + rndbv + ".").queue();
				}
				if (rndv == rndbv) {
					String text1 = Translate.getTranslate(language, "logs", "dice_match");
					String text2 = Translate.getTranslate(language, "logs", "dice_template");
					Context.getChannel().sendMessage(text1 + rndv + text2 + rndbv + ".").queue();
				}

			}
		}
		
	}

}
