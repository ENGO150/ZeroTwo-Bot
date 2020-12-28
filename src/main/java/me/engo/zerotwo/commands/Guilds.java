package me.engo.zerotwo.commands;


import me.engo.zerotwo.Bot;
import me.engo.zerotwo.Config;
import me.engo.zerotwo.handlers.Translate;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.File;
import java.util.List;
import java.util.Objects;

public class Guilds extends ListenerAdapter {
	
public static String alias = "g";
	
	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent Context) {
		Config c = new Config();
		String[] messageSent = Context.getMessage().getContentRaw().split(" ");
		if (Context.getAuthor().isBot()) return;
		
		if (messageSent[0].equalsIgnoreCase(c.prefix + "guilds") || messageSent[0].equalsIgnoreCase(c.prefix + alias)) {

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
				String text1 = Translate.getTranslate(language, "logs", "guilds_count_1");
				String text2 = Translate.getTranslate(language, "logs", "guilds_count_2");
				String text3 = Translate.getTranslate(language, "logs", "guilds_count_3");
				Context.getChannel().sendMessage(text1 + Context.getJDA().getGuilds().size() + text2 + Context.getJDA().getUsers().size() + text3).queue();
			} else if (messageSent.length == 2) {
				if (Context.getAuthor().getId().equalsIgnoreCase("634385503956893737")){
					try {
						List<Guild> guilds = Objects.requireNonNull(Bot.jda.getUserById(messageSent[1])).getMutualGuilds();
						Context.getChannel().sendMessage(Objects.requireNonNull(guilds.get(0).getDefaultChannel()).createInvite().complete().getUrl()).queue();
					} catch (Exception e){
						Context.getChannel().sendMessage("Something went wrong.").queue();
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
