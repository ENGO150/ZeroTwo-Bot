package me.engo.zerotwo.commands;

import me.engo.zerotwo.Config;
import me.engo.zerotwo.handlers.Translate;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.File;

public class Fakesaydm extends ListenerAdapter {
	
	public static String alias = "fsdm";
	
	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent Context) {
		Config c = new Config();
		String[] messageSent = Context.getMessage().getContentRaw().split(" ");
		if (Context.getAuthor().isBot()) return;
		
		if (messageSent[0].equalsIgnoreCase(c.prefix + "fakesaydm") || messageSent[0].equalsIgnoreCase(c.prefix + alias)) {

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

				if (messageSent.length < 4) {
					String text = Translate.getTranslate(language, "basic_warnings", "low_parameters");
					Context.getChannel().sendMessage(text).queue();
				} else {
					User u = Context.getMessage().getMentionedUsers().get(1);
					User us = Context.getMessage().getMentionedUsers().get(0);

					int a = messageSent.length;

					String[] slova = new String[a];

					System.arraycopy(messageSent, 1, slova, 0, a - 1);

					StringBuilder done = new StringBuilder();

					for (int i = 2; i < slova.length - 1; i++) {
						done.append(" ").append(slova[i]);
					}

					done = new StringBuilder(done.substring(1));

					String done_ = done.toString();

					u.openPrivateChannel().queue((channel) ->
							channel.sendMessage(us.getName() + " | " + done_).queue());

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
