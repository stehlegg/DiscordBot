package pr0;

import Core.Embeds;
import Core.JSON;
import net.dv8tion.jda.api.entities.TextChannel;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

import static pr0.Inspect.formatDate;

public class GetNewest {
	public static void inspectUser(TextChannel ch, String name, String poster) throws IOException {
		JSONObject json = JSON.readJson("https://pr0gramm.com/api/items/get?flags=15&user=" + name);
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
		int[] benis = {j2.getInt("up"),j2.getInt("down"), j2.getInt("up")-j2.getInt("down")};
		String[] infos = {j2.get("user").toString(), "https://pr0gramm.com/" + j2.get("id"), "Neuster Hochlad von " + j2.get("user").toString(),formatDate(j2.get("created").toString()),cat,"Kategorie: "+cat+ " | Blussi: " + benis[0] + ", Minus: " + benis[1], poster};
		if(j2.get("image").toString().contains("mp4"))  {
			String imgUrl = "https://vid.pr0gramm.com/" + j2.get("image");
			Embeds.createVideo(ch, infos, benis[2], imgUrl);
			Log.pr0gramm.getLogger().info("Created VIDEO Embed of " + j2.get("id") + " in " + ch.getName() + " (" + ch.getGuild().getName() + ")");
		}   else    {
			String imgUrl = "https://img.pr0gramm.com/" + j2.get("image");
			Embeds.createImage(ch, infos, benis[2], imgUrl);
			Log.pr0gramm.getLogger().info("Created IMAGE Embed of " + j2.get("id") + " in " + ch.getName() + " (" + ch.getGuild().getName() + ")");
		}
	}
}
