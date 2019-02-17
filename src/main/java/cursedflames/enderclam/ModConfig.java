package cursedflames.enderclam;

import cursedflames.lib.config.Config.EnumPropSide;
import net.minecraftforge.common.config.Property;

public class ModConfig {
	public static Property spawnTries;
	public static Property spawnChance;

	public static void initConfig() {
		spawnTries = EnderClam.config.addPropInt("spawnTries", "General",
				"Number of clam spawn tries per chunk", 2, EnumPropSide.SERVER, 1, 256);
		spawnChance = EnderClam.config.addPropDouble("spawnChance", "General",
				"The chance of a clam spawning on each spawn try", 0.1D, EnumPropSide.SERVER, 0D,
				1D);
	}
}
