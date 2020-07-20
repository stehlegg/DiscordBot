import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

import javax.security.auth.login.LoginException;
import java.io.IOException;

public class Main   {
	public static void main(String[] args) throws IllegalArgumentException, LoginException, InterruptedException, IOException {
		config.loadConfig();
		JDA api = JDABuilder.createDefault(config.getValue("token"))
				.addEventListeners(
						new GuildMessage(),
						new VoiceJoin())
				.build().awaitReady();
	}
}