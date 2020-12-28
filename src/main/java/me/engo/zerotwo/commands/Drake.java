package me.engo.zerotwo.commands;

import java.awt.Color;
import java.io.File;
import java.util.Arrays;

import me.engo.zerotwo.Config;
import me.engo.zerotwo.handlers.Translate;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Drake extends ListenerAdapter {
	
	public static String alias = "There's no aliases.";
	
	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent Context) {
		Config c = new Config();
		String[] messageSent = Context.getMessage().getContentRaw().split(" ");
		if (Context.getAuthor().isBot()) return;
		
		if (messageSent[0].equalsIgnoreCase(c.prefix + "drake")) {

			String language;
			File languages = new File("Database/Language/" + Context.getAuthor().getId());
			if (languages.exists()) {
				File[] languages_ = languages.listFiles();
				assert languages_ != null;
				language = languages_[0].getName();
			} else {
				language = "english_en";
			}

			if (messageSent.length < 2 || !Arrays.toString(messageSent).contains(";") || messageSent[1].equalsIgnoreCase(";")) {
				String text = Translate.getTranslate(language, "basic_warnings", "low_parameters");
				Context.getChannel().sendMessage(text).queue();
			} else {
				StringBuilder word = new StringBuilder();

				for (int i = 1; i < messageSent.length; i++) {
					word.append("+").append(messageSent[i]);
				}

				word = new StringBuilder(word.toString().replace(";", "�"));

				String firstWord;
				String secondWord;

				firstWord = word.substring(0, word.indexOf("�"));
				secondWord = word.substring(word.indexOf("�") + 1);

				firstWord = firstWord.substring(1);

				char a = secondWord.charAt(0);

				boolean b = String.valueOf(a).equalsIgnoreCase("+");

				if (b) {secondWord = secondWord.substring(1);}

				EmbedBuilder em = new EmbedBuilder();
				em.setImage("https://api.alexflipnote.dev/drake?top=" + firstWord + "&bottom=" + secondWord);
				em.setColor(new Color(c.Color));
				em.setFooter(c.footer1, c.footer2);

				Context.getChannel().sendMessage(em.build()).queue();

			}
		}
	}
}
