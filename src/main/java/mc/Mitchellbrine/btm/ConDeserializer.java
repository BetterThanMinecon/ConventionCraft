package mc.Mitchellbrine.btm;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import mc.Mitchellbrine.btm.components.Booth;
import mc.Mitchellbrine.btm.components.Panel;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mitchellbrine on 2015.
 */
public class ConDeserializer implements JsonDeserializer<Convention> {

	@Override
	public Convention deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		Convention con = new Convention();
		JsonObject obj = json.getAsJsonObject();
		con.eventName = obj.get("name").getAsString();
		con.eventHosts = obj.get("host").getAsString();
		con.description = obj.get("description").getAsString();
		con.startTime = obj.get("startTime").getAsString();
		con.endTime = obj.get("endTime").getAsString();
		con.titleColor = new ChatStyle().setColor(EnumChatFormatting.getValueByName(obj.get("titleColor").getAsString()));
		con.nameColor = new ChatStyle().setColor(EnumChatFormatting.getValueByName(obj.get("nameColor").getAsString()));
		con.hostColor = new ChatStyle().setColor(EnumChatFormatting.getValueByName(obj.get("hostColor").getAsString()));
		con.headerFooterColor = new ChatStyle().setColor(EnumChatFormatting.getValueByName(obj.get("headerColor").getAsString()));
		con.boothColor = new ChatStyle().setColor(EnumChatFormatting.getValueByName(obj.get("boothColor").getAsString()));
		con.panelColor = new ChatStyle().setColor(EnumChatFormatting.getValueByName(obj.get("panelColor").getAsString()));
		List<Booth> booths = new ArrayList<Booth>();
		JsonArray array = obj.getAsJsonArray("booths");
		for (int i = 0; i < array.size();i++) {
			Booth booth = new Booth();
			JsonObject boothObj = array.get(i).getAsJsonObject();
			booth.name = boothObj.get("name").getAsString();
			booth.host = boothObj.get("host").getAsString();
			booth.url = boothObj.get("url").getAsString();
			booth.description = boothObj.get("description").getAsString();
			booths.add(booth);
		}
		con.booths = booths;

		List<Panel> panels = new ArrayList<Panel>();
		JsonArray panelArray = obj.getAsJsonArray("panels");
		for (int i = 0; i < panelArray.size();i++) {
			Panel panel = new Panel();
			JsonObject object = panelArray.get(i).getAsJsonObject();
			panel.name = object.get("name").getAsString();
			panel.description = object.get("description").getAsString();
			panel.startTime = object.get("startTime").getAsString();
			panel.endTime = object.get("endTime").getAsString();
			panel.day = object.get("day").getAsString();
			JsonArray array1 = object.getAsJsonArray("participants");
			String[] panelists = new String[array1.size()];
			for (int index = 0; index < array1.size();index++) {
				panelists[index] = array1.get(index).getAsString();
			}
			panel.participants = panelists;
			panels.add(panel);
		}
		con.panels = panels;
		return con;
	}
}
