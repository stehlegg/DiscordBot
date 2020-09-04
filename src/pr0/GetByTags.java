package pr0;

import net.dv8tion.jda.api.entities.Message;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class GetByTags {
    public static void inspectCmd(Message msg) throws IOException {
        String poster;
        if(msg.getMember().getNickname() == null)
            poster = msg.getAuthor().getName();
        else
            poster = msg.getMember().getNickname();

        String request = "https://pr0gramm.com/api/items/get?tags=" + URLEncoder.encode(msg.getContentRaw().split(" ")[1], StandardCharsets.UTF_8.toString());
        JSONObject json = Core.JSON.readJson(request);
        JSONArray jArray = json.getJSONArray("items");
    }
}
