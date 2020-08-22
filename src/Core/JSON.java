package Core;

import org.json.JSONObject;

import java.io.*;
import java.net.URL;

public class JSON {
	private static String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1) {
			sb.append((char) cp);
		}
		return sb.toString();
	}

	public static JSONObject readJson(String url)   throws IOException  {
		try (InputStream in = new URL(url).openStream()) {
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			String jsonText = readAll(reader);
			return new JSONObject(jsonText);
		}
	}
}
