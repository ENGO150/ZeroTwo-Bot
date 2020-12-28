package me.engo.zerotwo.commands;

import me.engo.zerotwo.Config;
import me.engo.zerotwo.handlers.Translate;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.File;

public class Count extends ListenerAdapter {

	public static String alias = "write";
	
	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent Context) {
		Config c = new Config();
		String[] messageSent = Context.getMessage().getContentRaw().split(" ");
		if (Context.getAuthor().isBot()) return;
		
		if (messageSent[0].equalsIgnoreCase(c.prefix + "count") || messageSent[0].equalsIgnoreCase(c.prefix + "write")) {

			String language;
			File languages = new File("Database/Language/" + Context.getAuthor().getId());
			if (languages.exists()) {
				File[] languages_ = languages.listFiles();
				assert languages_ != null;
				language = languages_[0].getName();
			} else {
				language = "english_en";
			}

			if (messageSent.length <= 2) {
				String text = Translate.getTranslate(language, "basic_warnings", "low_parameters");
				Context.getChannel().sendMessage(text).queue();
			} else  if (messageSent.length > 3) {
				String text = Translate.getTranslate(language, "basic_warnings", "high_parameters");
				Context.getChannel().sendMessage(text).queue();
			} else {

				if (Context.getChannel().getName().contains("counting") || Context.getChannel().getName().contains("spam")) {
					int a = Integer.parseInt(messageSent[2]);
					int b = Integer.parseInt(messageSent[1]);

					if (Context.getMember() == Context.getGuild().getOwner()) {

						if (a - b <= 20) {
							for (int i = b; i <= a; i = i + 1) {

								Context.getChannel().sendMessage(i + ".").queue();

							}
						} else {

							String text = Translate.getTranslate(language, "advanced_warnings", "count_owner_high");

							Context.getChannel().sendMessage(text).queue();

						}

					} else {

						if (a - b <= 10) {
							for (int i = b; i <= a; i = i + 1) {

								Context.getChannel().sendMessage(i + ".").queue();

							}
						} else {

							String text = Translate.getTranslate(language, "advanced_warnings", "count_member_high");

							Context.getChannel().sendMessage(text).queue();

						}

					}
				} else {
					String text = Translate.getTranslate(language, "advanced_warnings", "count_invalid_channel");
					Context.getChannel().sendMessage(text).queue();
				}
			}
		}
	}
}
