package me.engo.zerotwo.commands;

import com.google.gson.JsonParser;
import me.engo.zerotwo.Config;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Objects;

public class Pin extends ListenerAdapter {
	
	public static String alias = "There's no aliases.";
	
	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		Config c = new Config();
		String[] messageSent = event.getMessage().getContentRaw().split(" ");
		if (event.getAuthor().isBot()) return;
		
		if (messageSent[0].equalsIgnoreCase(c.prefix + "pin")) {

			try {
				String language;
				File languages = new File("Database/Language/" + event.getAuthor().getId());
				if (languages.exists()) {
					File[] languages_ = languages.listFiles();
					assert languages_ != null;
					language = languages_[0].getName();
				} else {
					language = "english_en";
				}

				if (messageSent.length < 2) {
					String text = JsonParser.parseReader(new FileReader("languages/" + language + ".json")).getAsJsonObject().get("basic_warnings").getAsJsonObject().get("low_parameters").getAsString();
					event.getChannel().sendMessage(text).queue();
				} else {
					if (c.moderation) {
						if (Objects.requireNonNull(event.getMember()).hasPermission(event.getChannel(), Permission.MESSAGE_MANAGE)) {

							int a = messageSent.length;

							String[] slova = new String[a];

							System.arraycopy(messageSent, 1, slova, 0, a - 1);

							StringBuilder done = new StringBuilder();

							for (int i = 0; i < slova.length - 1; i++) {
								done.append(" ").append(slova[i]);
							}

							done = new StringBuilder(done.substring(1));

							String text = JsonParser.parseReader(new FileReader("languages/" + language + ".json")).getAsJsonObject().get("logs").getAsJsonObject().get("done").getAsString();
							event.getChannel().sendMessage(done).queue((message -> message.pin().queue()));
							event.getChannel().sendMessage(text).queue();
						} else {
							String text = JsonParser.parseReader(new FileReader("languages/" + language + ".json")).getAsJsonObject().get("basic_warnings").getAsJsonObject().get("permissions_false").getAsString();
							event.getChannel().sendMessage(text).queue();
						}
					} else {
						String text = JsonParser.parseReader(new FileReader("languages/" + language + ".json")).getAsJsonObject().get("basic_warnings").getAsJsonObject().get("moderationmodule_false").getAsString();
						event.getChannel().sendMessage(text).queue();
					}
				}
			} catch (FileNotFoundException e){
				e.printStackTrace();
			}
		}
	}
}
