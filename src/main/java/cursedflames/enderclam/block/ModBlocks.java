package cursedflames.enderclam.block;

import cursedflames.enderclam.EnderClam;
import cursedflames.lib.block.GenericBlock;

public class ModBlocks {
	public static GenericBlock clam = null;

	public static void registerToRegistry() {
		EnderClam.registryHelper.useOldTileEntityNaming = false;
		clam = new BlockClam();
		EnderClam.registryHelper.addBlock(clam).addItemBlock(clam).addItemBlockModel(clam);
	}
}
