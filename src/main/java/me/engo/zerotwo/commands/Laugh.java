package me.engo.zerotwo.commands;

import me.engo.zerotwo.Config;
import me.engo.zerotwo.handlers.Translate;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.io.File;
import java.util.Random;

public class Laugh extends ListenerAdapter {
	
	public static String alias = "smile";
	
	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent Context) {
		Config c = new Config();
		String[] messageSent = Context.getMessage().getContentRaw().split(" ");
		if (Context.getAuthor().isBot()) return;
		
		if (messageSent[0].equalsIgnoreCase(c.prefix + "laugh") || messageSent[0].equalsIgnoreCase(c.prefix + alias)) {

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
				if (c.roleplay) {
					Random rnd = new Random();
					int rndm = rnd.nextInt(4) - 1;
					if (rndm < 0){ rndm = rndm + 1; }

					String[] zt = new String[4];
					zt[0] = "https://i.pinimg.com/originals/63/ca/58/63ca58fb23c0901176abf1787fa3bfce.gif";
					zt[1] = "https://gifimage.net/wp-content/uploads/2018/11/zero-two-darling-in-the-franxx-gif-3.gif";
					zt[2] = "https://gifimage.net/wp-content/uploads/2018/11/darling-in-the-frankxx-zero-two-gif-3.gif";
					zt[3] = "https://i.redd.it/e6mqscj6jiq21.gif";

					EmbedBuilder em = new EmbedBuilder();
					em.setDescription(Context.getAuthor().getName() + " laughs");
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
