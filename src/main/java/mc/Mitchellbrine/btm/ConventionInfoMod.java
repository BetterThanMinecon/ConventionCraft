package mc.Mitchellbrine.btm;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.relauncher.FMLInjectionData;

import java.io.File;
import java.io.FileReader;

/**
 * Created by Mitchellbrine on 2015.
 */
@Mod(modid = "ConventionInfo",name="Convention Info Mod",version = "1.0")
public class ConventionInfoMod {

	public static Convention convention;
	public static File conventionFile = new File((File) FMLInjectionData.data()[6],"convention.json");

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		if (conventionFile.exists()) {
			readJSON(conventionFile.getPath());
		}
	}

	@Mod.EventHandler
	public void server(FMLServerStartingEvent event) {
		event.registerServerCommand(new InfoCommand());
	}

	public static void readJSON(String fileName) {

		try {
			GsonBuilder builder = new GsonBuilder();
			builder.registerTypeAdapter(Convention.class, new ConDeserializer());
			Gson gson = builder.create();

			File file = new File(fileName);

			convention = gson.fromJson(new FileReader(file), Convention.class);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
