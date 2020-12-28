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

public class Kill extends ListenerAdapter {
	
	public static String alias = "There's no aliases.";
	
	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent Context) {
		Config c = new Config();
		String[] messageSent = Context.getMessage().getContentRaw().split(" ");
		if (Context.getAuthor().isBot()) return;
		
		if (messageSent[0].equalsIgnoreCase(c.prefix + "kill")) {

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
					zt[0] = "https://media.giphy.com/media/yy1rPT45jdX1K/giphy.gif";
					zt[1] = "https://em.wattpad.com/a571a4e2e2ef085aad7afd726e30787562bbd7e9/68747470733a2f2f73332e616d617a6f6e6177732e636f6d2f776174747061642d6d656469612d736572766963652f53746f7279496d6167652f385f5a644c30626b6c4a623868513d3d2d3530333239313333352e313562363264643463633831613761363732303830343430303234382e676966?s=fit&w=720&h=720";
					zt[2] = "https://media2.giphy.com/media/UsYGLbhLewWCA/giphy.gif";

					User u = Context.getMessage().getMentionedUsers().get(0);

					if (u == null){
						String text = Translate.getTranslate(language, "basic_warnings", "low_parameters");
						Context.getChannel().sendMessage(text).queue();
						return;
					}

					EmbedBuilder em = new EmbedBuilder();
					if (u.getId().equals("668847135860719639")) {
						em.setDescription("ZeroTwo rected " + Context.getAuthor().getName());
						em.setImage("https://live.staticflickr.com/7043/6904397957_aa41456455_c.jpg");
					} else {
						em.setDescription(Context.getAuthor().getName() + " killed " + u.getName());
						em.setImage(zt[rndm]);
					}
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
