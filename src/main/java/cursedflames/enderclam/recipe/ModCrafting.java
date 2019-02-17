package cursedflames.enderclam.recipe;

import cursedflames.enderclam.EnderClam;
import cursedflames.enderclam.item.ModItems;
import net.minecraft.init.Items;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.registries.IForgeRegistry;

public class ModCrafting {
	@SubscribeEvent
	public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {
		// @formatter:off
		IForgeRegistry<IRecipe> r = event.getRegistry();
		r.register(new ShapedOreRecipe(new ResourceLocation(EnderClam.MODID, "wand"), ModItems.clamWand, new String[] {
				"cec",
				" s " },
				'e', Items.ENDER_PEARL,
				'c', ModItems.clamShell,
				's', Items.STICK)
				.setRegistryName(new ResourceLocation(EnderClam.MODID, "wand")));
	}
}
