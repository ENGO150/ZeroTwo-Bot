package me.engo.zerotwo.commands;

import me.engo.zerotwo.Config;
import me.engo.zerotwo.handlers.Translate;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.io.File;
import java.util.Random;

public class Hug extends ListenerAdapter {
	
	public static String alias = "There's no aliases.";
	
	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent Context) {
		Config c = new Config();
		String[] messageSent = Context.getMessage().getContentRaw().split(" ");
		if (Context.getAuthor().isBot()) return;
		
		if (messageSent[0].equalsIgnoreCase(c.prefix + "hug")) {

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
				if (c.roleplay) {
					Random rnd = new Random();
					int rndm = rnd.nextInt(3) - 1;
					if (rndm < 0){ rndm = rndm + 1; }

					String[] zt = new String[3];
					zt[0] = "https://acegif.com/wp-content/uploads/anime-hug.gif";
					zt[1] = "https://media.giphy.com/media/143v0Z4767T15e/giphy.gif";
					zt[2] = "https://media2.giphy.com/media/yziFo5qYAOgY8/giphy.gif";

					User u = Context.getMessage().getMentionedUsers().get(0);

					if (u == null){
						String text = Translate.getTranslate(language, "basic_warnings", "low_parameters");
						Context.getChannel().sendMessage(text).queue();
						return;
					}

					EmbedBuilder em = new EmbedBuilder();
					em.setDescription(Context.getAuthor().getName() + " hugs " + u.getName());
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
