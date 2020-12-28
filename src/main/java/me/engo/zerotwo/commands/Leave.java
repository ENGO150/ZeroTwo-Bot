package me.engo.zerotwo.commands;

import me.engo.zerotwo.Config;
import me.engo.zerotwo.handlers.Translate;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.File;
import java.util.Objects;

public class Leave extends ListenerAdapter {
	
	public static String alias = "bye";
	
	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent Context) {
		Config c = new Config();
		String[] messageSent = Context.getMessage().getContentRaw().split(" ");
		if (Context.getAuthor().isBot()) return;
		
		if (messageSent[0].equalsIgnoreCase(c.prefix + "leave") || messageSent[0].equalsIgnoreCase(c.prefix + alias)) {

			String language;
			File languages = new File("Database/Language/" + Context.getAuthor().getId());
			if (languages.exists()) {
				File[] languages_ = languages.listFiles();
				assert languages_ != null;
				language = languages_[0].getName();
			} else {
				language = "english_en";
			}

			if (messageSent.length >= 2) {
				String text = Translate.getTranslate(language, "basic_warnings", "high_parameters");
				Context.getChannel().sendMessage(text).queue();
			} else {
				try {
					if (Objects.requireNonNull(Context.getMember()).hasPermission(Context.getChannel(), Permission.KICK_MEMBERS)) {
						String text = Translate.getTranslate(language, "logs", "leave");
						Context.getChannel().sendMessage(text).queue();
						Context.getGuild().leave().complete();
					} else {
						String text = Translate.getTranslate(language, "basic_warnings", "permissions_false");
						Context.getChannel().sendMessage(text).queue();
					}
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
		}
	}

}
