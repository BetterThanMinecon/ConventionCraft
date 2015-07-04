package mc.Mitchellbrine.btm;

import mc.Mitchellbrine.btm.components.Booth;
import mc.Mitchellbrine.btm.components.Panel;
import net.minecraft.util.ChatStyle;

import java.util.List;

/**
 * Created by Mitchellbrine on 2015.
 */
public class Convention {

	public String eventName;
	public String eventHosts;
	public String description;
	public List<Panel> panels;
	public List<Booth> booths;
	public String startTime;
	public String endTime;
	public ChatStyle titleColor, nameColor, headerFooterColor, boothColor, panelColor, hostColor;
}
