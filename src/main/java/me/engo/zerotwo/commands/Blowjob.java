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

public class Blowjob extends ListenerAdapter {
	
	public static String alias = "oral";
	
	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent Context) {
		Config c = new Config();
		String[] messageSent = Context.getMessage().getContentRaw().split(" ");
		if (Context.getAuthor().isBot()) return;
		
		if (messageSent[0].equalsIgnoreCase(c.prefix + "blowjob") || messageSent[0].equalsIgnoreCase(c.prefix + "oral")) {

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
						int rndm = (1 + rnd.nextInt(4)) - 1;

						String[] zt = new String[4];
						zt[0] = "https://cdn.discordapp.com/attachments/609425123291693056/668930227187220525/20734828.gif";
						zt[1] = "https://cdn.discordapp.com/attachments/609425123291693056/668926830870528049/bb5a405f-6351-4d4d-a1f6-aa7fbc199b63.gif";
						zt[2] = "https://cdn.discordapp.com/attachments/553515884660916235/690421912681185290/9ebdc170bf90b163d539a652f6a5840f.png";
						zt[3] = "https://cdn.discordapp.com/attachments/553515884660916235/688255348745699358/sally-whitemane-hentai-86_01DFWP9Z1ECEAABBY0ZM5W1QC7.png";

						EmbedBuilder em = new EmbedBuilder();

						if (messageSent.length == 2) {
							User m = Context.getMessage().getMentionedUsers().get(0);

							if (m == null){
								String text = Translate.getTranslate(language, "basic_warnings", "low_parameters");
								Context.getChannel().sendMessage(text).queue();
								return;
							}

							em.setDescription( Context.getAuthor().getName() + " blowjobs " + m.getName());
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
