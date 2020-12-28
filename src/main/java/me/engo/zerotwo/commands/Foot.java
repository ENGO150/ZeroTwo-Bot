package me.engo.zerotwo.commands;

import java.awt.Color;
import java.io.File;
import java.util.Random;

import me.engo.zerotwo.Config;
import me.engo.zerotwo.handlers.Translate;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Foot extends ListenerAdapter {
	
	public static String alias = "leg";
	
	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent Context) {
		Config c = new Config();
		String[] messageSent = Context.getMessage().getContentRaw().split(" ");
		if (Context.getAuthor().isBot()) return;
		
		if (messageSent[0].equalsIgnoreCase(c.prefix + "foot") || messageSent[0].equalsIgnoreCase(c.prefix + alias)) {

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

				if (c.nsfw) {
					if (Context.getChannel().isNSFW()) {

						Random rnd = new Random();
						int rndm = rnd.nextInt(6) - 1;
						if (rndm < 0){ rndm = rndm + 1; }

						String[] zt = new String[6];
						zt[0] = "https://cdn.discordapp.com/attachments/609425123291693056/670495033400623104/zero_two_darling_in_the_franxx_drawn_by_aimee_emi__f374c859123a513d62941a4ce236607c.jpg";
						zt[1] = "https://cdn.discordapp.com/attachments/609425123291693056/670026740470513664/IMG_20200121_180852.jpg";
						zt[2] = "https://cdn.discordapp.com/attachments/609425123291693056/670026740164198429/IMG_20200121_180728.jpg";
						zt[3] = "https://cdn.discordapp.com/attachments/609425123291693056/670008667164377175/72527288_p0_master1200.webp";
						zt[4] = "https://cdn.discordapp.com/attachments/609425123291693056/670006935369678859/sample-8a2fb6b83265b4cf5e1c1009604b618b.jpg";
						zt[5] = "https://cdn.discordapp.com/attachments/609425123291693056/670006465024753700/IMG_20200121_175023.jpg";

						EmbedBuilder em = new EmbedBuilder();
						em.setImage(zt[rndm]);
						em.setFooter(c.footer1, c.footer2);
						em.setColor(new Color(c.Color));

						Context.getChannel().sendMessage(em.build()).queue();

					} else {
						String text = Translate.getTranslate(language, "basic_warnings", "nsfwchannel_false");
						Context.getChannel().sendMessage(text).queue();
					}
				} else {
					String text = Translate.getTranslate(language, "basic_warnings", "nsfwmodule_false");
					Context.getChannel().sendMessage(text).queue();
				}

			} else {

				String text = Translate.getTranslate(language, "basic_warnings", "high_parameters");

				Context.getChannel().sendMessage(text).queue();

			}
		}
	}

}
