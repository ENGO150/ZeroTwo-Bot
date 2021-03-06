package me.engo.zerotwo.commands;

import me.engo.zerotwo.Config;
import me.engo.zerotwo.handlers.Translate;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.io.File;

public class Fluffy extends ListenerAdapter {
	
	public static String alias = "There's no aliases.";
	
	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent Context) {
		Config c = new Config();
		String[] messageSent = Context.getMessage().getContentRaw().split(" ");
		if (Context.getAuthor().isBot()) return;
		
		if (messageSent[0].equalsIgnoreCase(c.prefix + "fluffy")) {

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
			} else {

				int a = messageSent.length;

				String[] slova = new String[a];

				System.arraycopy(messageSent, 1, slova, 0, a - 1);

				StringBuilder done = new StringBuilder();

				for (int i = 0; i < slova.length - 1; i++) {
					done.append("+").append(slova[i]);
				}

				done = new StringBuilder(done.substring(1));

				EmbedBuilder em = new EmbedBuilder();
				em.setImage("https://flamingtext.com/net-fu/proxy_form.cgi?script=fluffy-logo&text=" + done + "&_loc=generate&imageoutput=true");
				em.setFooter(c.footer1, c.footer2);
				em.setColor(new Color(c.Color));
				Context.getChannel().sendMessage(em.build()).queue();
			}
		}
	}

}
