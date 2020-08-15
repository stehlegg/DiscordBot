package pr0;

import Core.Config;
import Core.Main;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.TextChannel;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class Login {
	public static void pr0Login(String token, String captcha, TextChannel ch) throws IOException {

		String body = "name=" + URLEncoder.encode(Config.getValue("pr0-name"),"UTF-8") + "&" +
				      "password=" + URLEncoder.encode(Config.getValue("pr0-pwd"),"UTF-8") + "&" +
				      "token=" + URLEncoder.encode(token,"UTF-8") + "&" +
				      "captcha=" + URLEncoder.encode(captcha,"UTF-8");

		URL url = new URL("https://pr0gramm.com/api/user/login/post/");
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("POST");
		connection.setDoInput(true);
		connection.setDoOutput(true);
		connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		connection.setRequestProperty("Content-Length", String.valueOf(body.length()));

		OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
		writer.write(body);
		writer.flush();

		BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

		String output = reader.readLine();
		if(output.contains("\"success\":true")) {
			ch.sendMessage(output).queue();
			Main.pc.setPresence(OnlineStatus.ONLINE, Activity.playing("pr0gramm eingeloggt"));
		}   else    {
			Main.pc.setPresence(OnlineStatus.DO_NOT_DISTURB,Activity.playing("Login failed - !login"));
		}
	}
}
