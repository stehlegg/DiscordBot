package Core;

import Events.GuildMessage;
import Music.VoiceJoin;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.managers.Presence;
import org.xml.sax.SAXException;

import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.net.CookieHandler;
import java.net.CookieManager;

public class Main   {

	public static JDA api;
	public static Presence pc;

	public static void main(String[] args) throws IllegalArgumentException, LoginException, InterruptedException, IOException, SAXException {
		Config.loadConfig();
		CookieManager cookieManager = new CookieManager();
		CookieHandler.setDefault(cookieManager);
		api = JDABuilder
				.createDefault(Config.getValue("token"))
				.addEventListeners(
						new GuildMessage(),
						new VoiceJoin())
				.build().awaitReady();
		pc =  api.getPresence();
		pc.setPresence(OnlineStatus.DO_NOT_DISTURB, Activity.playing("Bei pr0gramm anmelden - !login"));
	}
}