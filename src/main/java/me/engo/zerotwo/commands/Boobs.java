package me.engo.zerotwo.commands;

import java.awt.Color;
import java.io.File;
import java.util.Random;

import me.engo.zerotwo.Config;
import me.engo.zerotwo.handlers.Translate;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Boobs extends ListenerAdapter {
	
public static String alias = "tittiess";
	
	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent Context) {
		Config c = new Config();
		String[] messageSent = Context.getMessage().getContentRaw().split(" ");
		if (Context.getAuthor().isBot()) return;
		
		if (messageSent[0].equalsIgnoreCase(c.prefix + "boobs") || messageSent[0].equalsIgnoreCase(c.prefix + alias)) {

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

				if (c.nsfw) {
					if (Context.getChannel().isNSFW()) {

						Random rnd = new Random();
						int rndm = (1 + rnd.nextInt(10)) - 1;

						String[] zt = new String[10];
						zt[0] = "https://images-ext-1.discordapp.net/external/ZnylXYx1dweT2h_kS9hd2XBDBEmCQQSXy-rr-WeG4YE/https/i.redd.it/job2w7le48c41.gif";
						zt[1] = "https://cdn.discordapp.com/attachments/609425123291693056/668930226213879838/184052db2407050fea7b035bb84222b9_01DQGAC8Q0HER1C5DAD41KQY2W.gif";
						zt[2] = "https://cdn.discordapp.com/attachments/609425123291693056/668521643001249792/3372379_-_Akali_BADCOMPZERO_League_of_Legends_Qiyana_Senna.jpg";
						zt[3] = "https://cdn.discordapp.com/attachments/609425123291693056/692180387014901770/ANIME-PICTURES.NET_-_626772-2894x4093-darlinginthefranxx-zerotwo28darlinginthefranxx29-alenqki-longh.jpg";
						zt[4] = "https://cdn.discordapp.com/attachments/609425123291693056/692017691728281610/IMG-20200307-WA0024.jpg";
						zt[5] = "https://cdn.discordapp.com/attachments/609425123291693056/689002695331282971/image0.jpg";
						zt[6] = "https://cdn.discordapp.com/attachments/609425123291693056/689000926966710275/image0.jpg";
						zt[7] = "https://cdn.discordapp.com/attachments/609425123291693056/689000297368256512/image0.jpg";
						zt[8] = "https://cdn.discordapp.com/attachments/609425123291693056/688998877977903126/image0.jpg";
						zt[9] = "https://cdn.discordapp.com/attachments/609425123291693056/688665920365002782/image6.jpg";

						EmbedBuilder em = new EmbedBuilder();

						if (messageSent.length == 2) {
							User m = Context.getMessage().getMentionedUsers().get(0);

							if (m == null){
								String text = Translate.getTranslate(language, "basic_warnings", "low_parameters");
								Context.getChannel().sendMessage(text).queue();
								return;
							}

							em.setDescription( Context.getAuthor().getName() + " tittiess " + m.getName());
						}

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
