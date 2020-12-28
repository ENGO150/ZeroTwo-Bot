package me.engo.zerotwo.commands;

import me.engo.zerotwo.Config;
import me.engo.zerotwo.handlers.Translate;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.io.File;

public class Fakesay extends ListenerAdapter {
	
	public static String alias = "fs";
	
	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent Context) {
		Config c = new Config();
		String[] messageSent = Context.getMessage().getContentRaw().split(" ");
		if (Context.getAuthor().isBot()) return;
		
		if (messageSent[0].equalsIgnoreCase(c.prefix + "fakesay") || messageSent[0].equalsIgnoreCase(c.prefix + alias)) {

			String language;
			File languages = new File("Database/Language/" + Context.getAuthor().getId());
			if (languages.exists()) {
				File[] languages_ = languages.listFiles();
				assert languages_ != null;
				language = languages_[0].getName();
			} else {
				language = "english_en";
			}

			File f = new File("Database/Premium/" + Context.getAuthor().getId());

			if (f.exists()) {
				Context.getMessage().delete().queue();

				if (messageSent.length < 3) {
					String text = Translate.getTranslate(language, "basic_warnings", "low_parameters");
					Context.getChannel().sendMessage(text).queue();
				} else {
					Member m = Context.getMessage().getMentionedMembers().get(0);

					int a = messageSent.length;

					String[] slova = new String[a];

					System.arraycopy(messageSent, 1, slova, 0, a - 1);

					StringBuilder done = new StringBuilder();

					for (int i = 1; i < slova.length - 1; i++) {
						done.append(" ").append(slova[i]);
					}

					done = new StringBuilder(done.substring(1));

					EmbedBuilder em = new EmbedBuilder();
					em.setDescription(m.getAsMention() + " said:\n" + done);
					em.setColor(new Color(c.Color));
					em.setFooter(c.footer1, c.footer2);

					Context.getChannel().sendMessage(em.build()).queue();

					String text = Translate.getTranslate(language, "logs", "done");
					Context.getAuthor().openPrivateChannel().queue((channel) ->
							channel.sendMessage(text).queue());
				}
			} else {
				String text = Translate.getTranslate(language, "basic_warnings", "permissions_false");
				Context.getChannel().sendMessage(text).queue();
			}
		}
	}
}
