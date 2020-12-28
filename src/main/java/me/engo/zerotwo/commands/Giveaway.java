package me.engo.zerotwo.commands;

import me.engo.zerotwo.Config;
import me.engo.zerotwo.handlers.Translate;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.File;
import java.io.IOException;

public class Giveaway extends ListenerAdapter {
	
	public static String alias = "There's no aliases.";

	public File f = new File("Database/Giveaway/true");
	public File fi = new File("Database/Giveaway/false");
	
	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent Context) {
		Config c = new Config();
		String[] messageSent = Context.getMessage().getContentRaw().split(" ");
		if (Context.getAuthor().isBot()) return;

		if (messageSent[0].equalsIgnoreCase(c.prefix + "giveaway")) {

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

				boolean active;
				active = f.exists();
				if (active) {
					String text = Translate.getTranslate(language, "logs", "giveaway_on");
					Context.getChannel().sendMessage(text).queue();
				} else {
					String text = Translate.getTranslate(language, "logs", "giveaway_off");
					Context.getChannel().sendMessage(text).queue();
				}
			} else if (messageSent.length == 2) {
				if (Context.getAuthor().getId().equals("574992310048260097")) {
					switch (messageSent[1].toLowerCase()) {
						case "set":
							try {
								fi.delete();
								f.createNewFile();
								Context.getChannel().sendMessage("Giveaway was started.").queue();
							} catch (IOException e) {
								e.printStackTrace();
							}
							break;

						case "del":
							try {
								f.delete();
								fi.createNewFile();
								Context.getChannel().sendMessage("Giveaway was deleted.").queue();
							} catch (IOException e) {
								e.printStackTrace();
							}
							break;

						default:
							Context.getChannel().sendMessage("Invalid args[1]. '" + messageSent[1] + "'").queue();
							break;
					}
				} else {
					String text = Translate.getTranslate(language, "basic_warnings", "developer_false");
					Context.getChannel().sendMessage(text).queue();
				}
			} else {
				String text = Translate.getTranslate(language, "basic_warnings", "high_parameters");
				Context.getChannel().sendMessage(text).queue();
			}
		}
	}
}
