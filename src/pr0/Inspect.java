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

import static Core.JSON.readJson;

public class Inspect {
	public static void inspectMsg(Message msg) throws IOException {
		String poster;
		if(msg.getMember().getNickname() == null)
			poster = msg.getAuthor().getName();
		else
			poster = msg.getMember().getNickname();
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
			String postID = ids[0].replaceAll("[^0-9]","");
			String commID = ids[1].replaceAll("[^0-9]","");
			inspectApiComment(msg.getTextChannel(), postID, commID, poster);
		}   else    {
			int id = Integer.parseInt(urlSplit[urlSplit.length-1].replaceAll("[^0-9]",""));
			inspectApiUpload(id, msg.getTextChannel(), poster);
		}
	}

	public static String topTags(int id) throws IOException {
		JSONObject json = readJson("https://pr0gramm.com/api/items/info?itemId=" + id);
		JSONArray array = json.getJSONArray("tags");
		JSONObject j2 = array.getJSONObject(0);
		return j2.getString("tag");
	}

	public static void inspectApiUpload(int id, TextChannel ch, String poster) throws IOException {
		JSONObject json = readJson("https://pr0gramm.com/api/items/get?flags=15&id=" + id);
		JSONArray array = json.getJSONArray("items");
		JSONObject j2 = array.getJSONObject(0);

		String cat = "";
		switch(j2.getInt("flags"))    {
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

		int[] benis = {j2.getInt("up"),j2.getInt("down"), (j2.getInt("up")-j2.getInt("down"))};
		String[] infos = {j2.get("user").toString(), "https://pr0gramm.com/" + id, "\"" + topTags(id) + "\"",formatDate(j2.get("created").toString()),cat,"Kategorie: "+cat+ " | Blussi: " + benis[0] + ", Minus: " + benis[1], poster};

		if(j2.get("image").toString().contains("mp4"))  {
			String imgUrl = "https://vid.pr0gramm.com/" + j2.get("image");
			Embeds.createVideo(ch, infos, benis[2], imgUrl);
			Log.pr0gramm.getLogger().info("Created VIDEO Embed of " + id + " in " + ch.getName() + " (" + ch.getGuild().getName() + ")");
		}   else    {
			String imgUrl = "https://img.pr0gramm.com/" + j2.get("image");
			Embeds.createImage(ch, infos, benis[2], imgUrl);
			Log.pr0gramm.getLogger().info("Created IMAGE Embed of " + id + " in " + ch.getName() + " (" + ch.getGuild().getName() + ")");
		}
	}

	public static void inspectApiComment(TextChannel ch, String postID, String commID, String poster) throws IOException {
		JSONObject json = readJson("https://pr0gramm.com/api/items/info?itemId=" + postID);
		JSONArray array = json.getJSONArray("comments");
		JSONObject j2;
		for(int i = 0; i < array.length(); i++) {
			if(array.getJSONObject(i).get("id").toString().equals(commID))   {
				j2 = array.getJSONObject(i);
				String[] infos = {formatDate(j2.get("created").toString()), j2.getString("name"), "https://pr0gramm.com/new/" + postID, "https://pr0gramm.com/new/" + postID + ":comment" + commID, j2.getString("content"), poster,(j2.getInt("up") - j2.getInt("down")) + " Benis (" + j2.getInt("up") + " - " + j2.getInt("down") +")", String.valueOf(j2.getInt("up") - j2.getInt("down"))};
				Embeds.createComment(ch, infos);
				Log.pr0gramm.getLogger().info("Created COMMENT Embed of " + commID + " (" + postID + ")" + " in " + ch.getName() + " (" + ch.getGuild().getName() + ")");
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