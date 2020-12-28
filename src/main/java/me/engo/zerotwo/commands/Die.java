package me.engo.zerotwo.commands;

import me.engo.zerotwo.Config;
import me.engo.zerotwo.handlers.Translate;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.io.File;
import java.util.Random;

public class Die extends ListenerAdapter {
	
	public static String alias = "There's no aliases.";
	
	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent Context) {
		Config c = new Config();
		String[] messageSent = Context.getMessage().getContentRaw().split(" ");
		if (Context.getAuthor().isBot()) return;
		
		if (messageSent[0].equalsIgnoreCase(c.prefix + "die")) {

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
				if (c.roleplay) {
					Random rnd = new Random();
					int rndm = (1 + rnd.nextInt(3)) - 1;

					String[] zt = new String[3];
					zt[0] = "https://media.giphy.com/media/jDP46a3UoinRu/giphy.gif";
					zt[1] = "https://cdn.zerotwo.dev/WASTED/f57a03fd-9eb3-49e0-979d-627d76f39eb7.gif";
					zt[2] = "https://i.kym-cdn.com/photos/images/original/001/003/097/41a.gif";

					EmbedBuilder em = new EmbedBuilder();
					em.setDescription(Context.getAuthor().getAsMention() + " died");
					em.setImage(zt[rndm]);
					em.setFooter(c.footer1, c.footer2);
					em.setColor(new Color(c.Color));

					Context.getChannel().sendMessage(em.build()).queue();
				} else {
					String text = Translate.getTranslate(language, "basic_warnings", "roleplaymodule_false");
					Context.getChannel().sendMessage(text).queue();
				}
			}
		}
	}
}
