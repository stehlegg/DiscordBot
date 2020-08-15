package pr0;


import Core.Embeds;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.*;
import java.util.Iterator;

public class Inspect {
	public static void inspectMsg(Message msg) throws IOException, URISyntaxException {
		//https://pr0gramm.com/top/API/3273699
		if(msg.getContentRaw().contains("comment"))    {
			String[] url = msg.getContentRaw().split("/");
			int len = url.length - 1;
			int id = Integer.parseInt(url[len]);
			System.out.println(id);
		}   else    {
			String[] url = msg.getContentRaw().split("/");
			int len = url.length - 1;
			int id = Integer.parseInt(url[len]);
			System.out.println(id);
			inspectApiUpload(id, msg.getTextChannel());
		}
	}

	private static String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1) {
			sb.append((char) cp);
		}
		return sb.toString();
	}

	public static JSONObject readJson(String url)   throws IOException  {
		InputStream in = new URL(url).openStream();
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			String jsonText = readAll(reader);
			JSONObject json = new JSONObject(jsonText);
			return json;
		} finally {
			in.close();
		}
	}

	public static String topTags(int id) throws IOException {
		String url = "https://pr0gramm.com/api/items/info?itemId=" + id;
		JSONObject json = readJson(url);
		JSONArray array = json.getJSONArray("tags");
		JSONObject j2 = array.getJSONObject(0);
		String topTag = j2.getString("tag");
		return topTag;
	}

	public static void inspectApiUpload(int id, TextChannel ch) throws IOException {

		int nid = id + 1;
		String url = "https://pr0gramm.com/api/items/get?flags=15&older=" + nid;
		JSONObject json = readJson(url);
		JSONArray array = json.getJSONArray("items");
		JSONObject j2 = array.getJSONObject(0);
		System.out.println(j2.get("image"));
		String imgUrl;
		if(j2.get("image").toString().contains("mp4"))  {
			imgUrl = "https://vid.pr0gramm.com/";
		}   else    {
			imgUrl = "https://img.pr0gramm.com/";
		}
		imgUrl += j2.get("image");
		String Author = j2.get("user").toString();
		String postUrl = "https://pr0gramm.com/new/" + id;
		String time = j2.get("created").toString();
		int up = j2.getInt("up");
		int down = j2.getInt("down");
		int result = up - down;

		Embeds.createEmbed(ch, topTags(id) + " von " + Author, "Link zum Post: " + postUrl, "Benis: "+result+", Ups: "+ up + ", Downs: "+down,imgUrl,postUrl);
		//TextChannel ch, String title, String desc, String footer, String imgUrl, String postUrl
	}
}