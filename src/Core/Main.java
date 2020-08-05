package Core;

import Events.GuildMessage;
import Music.VoiceJoin;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

import javax.security.auth.login.LoginException;
import java.io.IOException;

public class Main   {

	public static JDA api;

	public static void main(String[] args) throws IllegalArgumentException, LoginException, InterruptedException, IOException {
		config.loadConfig();
		api = JDABuilder.createDefault(config.getValue("token"))
				.addEventListeners(
						new GuildMessage(),
						new VoiceJoin())
				.build().awaitReady();
	}
}