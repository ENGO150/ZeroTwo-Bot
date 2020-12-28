package me.engo.zerotwo.commands;

import me.engo.zerotwo.Config;
import me.engo.zerotwo.handlers.Translate;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.File;

public class Checkfor extends ListenerAdapter {
	
	public static String alias = "check";
	
	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent Context) {
		Config c = new Config();
		String[] messageSent = Context.getMessage().getContentRaw().split(" ");
		if (Context.getAuthor().isBot()) return;
		
		if (messageSent[0].equalsIgnoreCase(c.prefix + "checkfor") || messageSent[0].equalsIgnoreCase(c.prefix + alias)) {

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
				Member u = Context.getGuild().getMemberById(messageSent[1]);
				if (u == null) {
					String text = Translate.getTranslate(language, "logs", "checkfor_false");
					Context.getChannel().sendMessage(text).queue();
				} else {
					String text1 = Translate.getTranslate(language, "logs", "checkfor_true_1");
					String text2 = Translate.getTranslate(language, "logs", "checkfor_true_2");
					Context.getChannel().sendMessage(text1 + u.getAsMention() + text2).queue();
				}
			}
		}
	}

}
