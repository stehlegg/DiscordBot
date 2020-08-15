package pr0;

import Core.Config;
import Events.GuildMessage;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class Login {
	public static void pr0Login(String token, String captcha) throws IOException {

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

		for(String line; (line = reader.readLine()) != null;)    {
			System.out.println(line);
		}
	}
}
