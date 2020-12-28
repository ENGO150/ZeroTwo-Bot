package me.engo.zerotwo.commands;

import me.engo.zerotwo.Config;
import me.engo.zerotwo.handlers.Translate;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.io.File;
import java.lang.reflect.Field;

public class Help extends ListenerAdapter {
	
	public static String alias = "h";

	public String done;

	public String language;

	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent Context) {
		Config c = new Config();
		String[] messageSent = Context.getMessage().getContentRaw().split(" ");
		if (Context.getAuthor().isBot()) return;
		
		if (messageSent[0].equalsIgnoreCase(c.prefix + "help") || messageSent[0].equalsIgnoreCase(c.prefix + "h")) {

			try {
				File languages = new File("Database/Language/" + Context.getAuthor().getId());
				if (languages.exists()) {
					File[] languages_ = languages.listFiles();
					assert languages_ != null;
					language = languages_[0].getName();
				} else {
					language = "english_en";
				}

				if (messageSent.length < 2) {

					EmbedBuilder em = new EmbedBuilder();

					em.setTitle("Help");
					em.addField("List of Modules", "`" + c.prefix + "modules`", false);
					em.addField("Commands of Module", "`" + c.prefix + "module [moduleName]`", false);
					em.addField("Brackets", "[obligatory] (optional)", false);
					em.setColor(new Color(c.Color));
					em.setFooter(c.footer1, c.footer2);

					Context.getChannel().sendMessage(em.build()).queue((message -> message.addReaction("❓").queue()));

				} else if (messageSent.length == 2){
					char ch = messageSent[1].charAt(0);

					String a = String.valueOf(ch).toUpperCase();
					String b = messageSent[1].substring(1).toLowerCase();
					done = a + b;

					Class<?> cl = Class.forName("me.engo.zerotwo.commands." + done);
					Field[] fields = cl.getFields();
					Object o = fields[0].get(cl);
					String Alias = (String) fields[0].get(o);
					String alias;

					EmbedBuilder em = new EmbedBuilder();

					if (Alias.toLowerCase().equals("there's no aliases.")){
						alias = "There's no aliases.";
					} else {
						alias = c.prefix + Alias;
					}

					em.setTitle("Help");
					em.addField("Command", cl.getSimpleName(), false);
					em.addField("Usage", c.prefix + cl.getSimpleName().toLowerCase(), false);
					em.addField("Alias", alias, false);
					em.setFooter(c.footer1, c.footer2);
					em.setColor(new Color(c.Color));
					Context.getChannel().sendMessage(em.build()).queue();
				} else {
					String text = Translate.getTranslate(language, "basic_warnings", "high_parameters");
					Context.getChannel().sendMessage(text).queue();
				}
			} catch (IllegalAccessException | ClassNotFoundException e){
				if (e.getMessage().equals(e.getMessage().substring(0, e.getMessage().length() - messageSent[1].length()) + done)){
					String text = Translate.getTranslate(language, "advanced_warnings", "invalid_command");
					Context.getChannel().sendMessage("'" + done + text).queue();
				} else {
					System.out.println("B");
				}
			}
		}
	}
}
