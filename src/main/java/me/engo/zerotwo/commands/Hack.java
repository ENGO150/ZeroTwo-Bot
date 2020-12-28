package me.engo.zerotwo.commands;

import me.engo.zerotwo.Config;
import me.engo.zerotwo.handlers.Translate;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.File;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Hack extends ListenerAdapter {
	
	public static String alias = "virus";
	
	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent Context) {
		Config c = new Config();
		String[] messageSent = Context.getMessage().getContentRaw().split(" ");
		if (Context.getAuthor().isBot()) return;
		
		if (messageSent[0].equalsIgnoreCase(c.prefix + "hack") || messageSent[0].equalsIgnoreCase(c.prefix + alias)) {

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
			} else if (messageSent.length > 2) {
				String text = Translate.getTranslate(language, "basic_warnings", "high_parameters");
				Context.getChannel().sendMessage(text).queue();
			} else {
				try {
					String text1 = Translate.getTranslate(language, "logs", "hack_IP_1");
					String text2 = Translate.getTranslate(language, "logs", "hack_IP_2");
					String text3 = Translate.getTranslate(language, "logs", "hack_nudes_1");
					String text4 = Translate.getTranslate(language, "logs", "hack_nudes_2");
					String text5 = Translate.getTranslate(language, "logs", "hack_money_1");
					String text6 = Translate.getTranslate(language, "logs", "hack_money_2");
					String text7 = Translate.getTranslate(language, "logs", "hack_complete");

					Random rnd = new Random();
					User u = Context.getMessage().getMentionedUsers().get(0);

					Context.getChannel().sendMessage(text1 + u.getName() + text2).queue();

					Context.getChannel().sendMessage(text3 + u.getName() + text4).queueAfter(rnd.nextInt(3) + 5, TimeUnit.SECONDS);

					Context.getChannel().sendMessage(text5 + u.getName() + text6).queueAfter(rnd.nextInt(3) + 5, TimeUnit.SECONDS);

					Context.getChannel().sendMessage(text7).queueAfter(rnd.nextInt(3) + 5, TimeUnit.SECONDS);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

}
