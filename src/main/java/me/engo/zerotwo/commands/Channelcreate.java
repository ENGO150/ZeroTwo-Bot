package me.engo.zerotwo.commands;

import me.engo.zerotwo.Config;
import me.engo.zerotwo.handlers.Translate;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.File;
import java.util.Objects;

public class Channelcreate extends ListenerAdapter {
	
public static String alias = "cc";
	
	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent Context) {
		Config c = new Config();
		String[] messageSent = Context.getMessage().getContentRaw().split(" ");
		if (Context.getAuthor().isBot()) return;
		
		if (messageSent[0].equalsIgnoreCase(c.prefix + "channelcreate") || messageSent[0].equalsIgnoreCase(c.prefix + alias)) {

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
				String text = Translate.getTranslate(language, "advanced_warnings", "template_channel");
				Context.getChannel().sendMessage(text + c.prefix + "channelcreate general text`").queue();
			} else if (messageSent.length > 3) {
				String text = Translate.getTranslate(language, "basic_warnings", "high_parameters");
				Context.getChannel().sendMessage(text).queue();
			} else {
				if (c.moderation) {
					if (Objects.requireNonNull(Context.getMember()).getPermissions(Context.getChannel()).contains(Permission.MANAGE_CHANNEL)) {
						if (messageSent[2].toLowerCase().equals("text") || messageSent[2].toLowerCase().equals("voice")) {
							if (messageSent[2].equals("text")) {
								Context.getGuild().createTextChannel(messageSent[1]).queue();
							} else {
								Context.getGuild().createVoiceChannel(messageSent[1]).queue();
							}
							String text = Translate.getTranslate(language, "logs", "channel_created");
							Context.getChannel().sendMessage(text + messageSent[1].toLowerCase() + "'.").queue();
						} else {
							String text1 = Translate.getTranslate(language, "advanced_warnings", "invalid_channel_type_1");
							String text2 = Translate.getTranslate(language, "advanced_warnings", "invalid_channel_type_2");
							Context.getChannel().sendMessage(text1 + messageSent[2] + text2).queue();
						}
					} else {
						String text = Translate.getTranslate(language, "basic_warnings", "permissions_false");
						Context.getChannel().sendMessage(text).queue();
					}
				} else {
					String text = Translate.getTranslate(language, "basic_warnings", "moderationmodule_false");
					Context.getChannel().sendMessage(text).queue();
				}
			}
		}
	}
}
