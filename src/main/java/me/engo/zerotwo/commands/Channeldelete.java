package me.engo.zerotwo.commands;

import me.engo.zerotwo.Config;
import me.engo.zerotwo.handlers.Translate;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.File;
import java.util.Objects;

public class Channeldelete extends ListenerAdapter {
	
public static String alias = "cd";
	
	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent Context) {
		Config c = new Config();
		String[] messageSent = Context.getMessage().getContentRaw().split(" ");
		if (Context.getAuthor().isBot()) return;
		
		if (messageSent[0].equalsIgnoreCase(c.prefix + "channeldelete") || messageSent[0].equalsIgnoreCase(c.prefix + alias)) {

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
				if (c.moderation) {
					if (Objects.requireNonNull(Context.getMember()).getPermissions(Context.getChannel()).contains(Permission.MANAGE_CHANNEL)) {
						TextChannel ch = Context.getMessage().getMentionedChannels().get(0);

						ch.delete().queue();
						String text = Translate.getTranslate(language, "logs", "channel_deleted");
						Context.getChannel().sendMessage(text + ch.getName() + "'.").queue();
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
