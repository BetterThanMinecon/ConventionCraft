package mc.Mitchellbrine.btm;

import mc.Mitchellbrine.btm.components.Booth;
import mc.Mitchellbrine.btm.components.Panel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mitchellbrine on 2015.
 */
public class StringUtils {

	public static boolean arrayContains(String[] array, String search) {
		for (String string : array) {
			if (string.equals(search))
				return true;
		}
		return false;
	}

	public static Panel getPanelByName(String name) {
		for (Panel panel : ConventionInfoMod.convention.panels) {
			if (panel.name.equals(name))
				return panel;
		}
		return null;
	}

	public static Booth getBooothByName(String name) {
		for (Booth booth : ConventionInfoMod.convention.booths) {
			if (booth.name.equals(name))
				return booth;
		}
		return null;
	}

	public static String joinArray(String[] array, int startIndex) {
		String finalString = "";

		do {
			finalString += array[startIndex];
			startIndex++;
			if (startIndex < array.length) {
				finalString += " ";
			}
		} while (startIndex < array.length);

		return finalString;
	}

	public static List<Booth> getBooths(Convention con, String name) {
		List<Booth> booths = null;
		for (Booth booth : con.booths) {
			if (booth.host.contains(name)) {
				if (booths == null)
					booths = new ArrayList<Booth>();
				booths.add(booth);
			}
		}
		return booths;
	}

	public static List<Panel> getPanels(Convention con, String name) {
		List<Panel> panels = null;
		for (Panel panel : con.panels) {
			if (arrayContains(panel.participants,name)) {
				if (panels == null)
					panels = new ArrayList<Panel>();
				panels.add(panel);
			}
		}
		return panels;
	}

}
