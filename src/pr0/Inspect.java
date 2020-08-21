package pr0;

import Core.Embeds;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Inspect {
	public static void inspectMsg(Message msg) throws IOException {
		//https://pr0gramm.com/top/API/3273699
		String poster = msg.getAuthor().getName();
		String[] msgSplit = msg.getContentRaw().split(" ");
		String url = "";
		for (String s : msgSplit) {
			if (s.contains("https://pr0gramm.com/")) {
				url = s;
			}
		}
		String[] urlSplit = url.split("/");
		if(msg.getContentRaw().contains("comment"))    {
			String[] ids = urlSplit[urlSplit.length-1].split(":");
			String postID = ids[0];
			String commID = ids[1];
			inspectApiComment(msg.getTextChannel(), postID, commID, poster);
		}   else    {
			int len = urlSplit.length - 1;
			int id = Integer.parseInt(urlSplit[len]);
			inspectApiUpload(id, msg.getTextChannel(), poster);
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
		try (InputStream in = new URL(url).openStream()) {
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			String jsonText = readAll(reader);
			return new JSONObject(jsonText);
		}
	}

	public static String topTags(int id) throws IOException {
		String url = "https://pr0gramm.com/api/items/info?itemId=" + id;
		JSONObject json = readJson(url);
		JSONArray array = json.getJSONArray("tags");
		JSONObject j2 = array.getJSONObject(0);
		return j2.getString("tag");
	}

	public static void inspectApiUpload(int id, TextChannel ch, String poster) throws IOException {
		String url = "https://pr0gramm.com/api/items/get?flags=15&id=" + id;
		JSONObject json = readJson(url);
		JSONArray array = json.getJSONArray("items");
		JSONObject j2 = array.getJSONObject(0);
		String imgUrl;
		String Author = j2.get("user").toString();
		String postUrl = "https://pr0gramm.com/new/" + id;
		int up = j2.getInt("up");
		int down = j2.getInt("down");
		int result = up - down;
		String title = "\"" + topTags(id) + "\"" + " von " + Author;
		String footer = "Benis: " + result + ", Davon Blussi: " + up + ", und Minus: " + down;
		String date = j2.get("created").toString();
		String newDate = formatDate(date);
		int flag = j2.getInt("flags");

		String cat = "";
		switch(flag)    {
			case 1:
				cat = "sfw";
				break;
			case 2:
				cat = "nsfw";
				break;
			case 4:
				cat = "nsfl";
				break;
			case 8:
				cat = "nsfp";
				break;
		}

		if(j2.get("image").toString().contains("mp4"))  {
			imgUrl = "https://vid.pr0gramm.com/" + j2.get("image");
			Embeds.createVideo(ch,title,Author,footer,imgUrl,postUrl, newDate, cat,poster);
			Log.pr0gramm.getLogger().info("Created VIDEO Embed of " + id + " in " + ch.getName());
		}   else    {
			imgUrl = "https://img.pr0gramm.com/" + j2.get("image");
			Embeds.createImage(ch,title,Author,footer,imgUrl,postUrl, newDate, cat, poster);
			Log.pr0gramm.getLogger().info("Created IMAGE Embed of " + id + " in " + ch.getName());
		}
	}

	public static void inspectApiComment(TextChannel ch, String postID, String commID, String poster) throws IOException {
		String url = "https://pr0gramm.com/api/items/info?itemId=" + postID;
		JSONObject json = readJson(url);
		JSONArray array = json.getJSONArray("comments");
		JSONObject j2;
		commID = commID.split("t")[1];
		for(int i = 0; i < array.length(); i++) {
			if(array.getJSONObject(i).get("id").toString().equals(commID))   {
				j2 = array.getJSONObject(i);
				int up = j2.getInt("up");
				int down = j2.getInt("down");
				int result = up-down;
				String benis = result + "Benis (" + up + " - " + down +")";
				String date = j2.get("created").toString();
				String newDate = formatDate(date);
				String author = j2.getString("name");
				String postURL = "https://pr0gramm.com/new/" + postID;
				String commURL = postURL + ":comment" + commID;
				String commentText = j2.getString("content");
				Embeds.createComment(ch,author,commURL,commentText,benis,newDate,postURL,poster);
				Log.pr0gramm.getLogger().info("Created COMMENT Embed of " + commID + "of" + postID + " in " + ch.getName());
				break;
			}
		}
	}

	public static String formatDate(String date)    {
		DateFormat format= new SimpleDateFormat("dd.MM.yyyy");
		Date dateFormatted = new Date(Long.parseLong(date)*1000);
		return format.format(dateFormatted);
	}
}