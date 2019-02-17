package cursedflames.enderclam;

import org.apache.logging.log4j.Logger;

import cursedflames.enderclam.block.ModBlocks;
import cursedflames.enderclam.item.ModItems;
import cursedflames.enderclam.network.PacketHandler;
import cursedflames.enderclam.proxy.ISideProxy;
import cursedflames.enderclam.recipe.ModCrafting;
import cursedflames.lib.RegistryHelper;
import cursedflames.lib.config.Config;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import worldgen.ClamWorldGen;

@Mod(modid = EnderClam.MODID, useMetadata = true)
@Mod.EventBusSubscriber
public class EnderClam {
	@Mod.Instance
	public static EnderClam instance;

	public static final String MODID = "enderclam";

	public static final RegistryHelper registryHelper = new RegistryHelper(MODID);
	// TODO config gui
	public static Config config;

	public static Logger logger;

	@SidedProxy(clientSide = "cursedflames.enderclam.proxy.ClientProxy", serverSide = "cursedflames.enderclam.proxy.ServerProxy")
	public static ISideProxy proxy;

//	public static final CreativeTabs TAB = new CreativeTabs("bountifulbaubles") {
//		@SideOnly(Side.CLIENT)
//		public ItemStack getTabIconItem() {
//			return new ItemStack(ModItems.trinketObsidianSkull);
//		}
//	};

	@Mod.EventHandler
	public static void preInit(FMLPreInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(ModCrafting.class);
		logger = event.getModLog();
		config = new Config(MODID, "1", logger);
		config.preInit(event);
		ModConfig.initConfig();
	}

	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event) {
		ModBlocks.registerToRegistry();
		registryHelper.registerBlocks(event);
	}

	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event) {
		ModItems.registerToRegistry();
		registryHelper.registerItems(event);
		ModItems.registerOreDictionaryEntries();
	}

	// TODO maybe stop using sideonly
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public static void registerModels(ModelRegistryEvent event) {
		registryHelper.registerModels();
	}

	@Mod.EventHandler
	public static void init(FMLInitializationEvent event) {
//		NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiProxy());
		PacketHandler.registerMessages();
		GameRegistry.registerWorldGenerator(new ClamWorldGen(), 0);
	}

	@Mod.EventHandler
	public static void postInit(FMLPostInitializationEvent event) {
		config.postInit(event);
	}
}
