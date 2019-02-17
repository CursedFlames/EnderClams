package cursedflames.enderclam.item;

import cursedflames.enderclam.EnderClam;
import cursedflames.enderclam.block.BlockClam;
import cursedflames.enderclam.block.ModBlocks;
import cursedflames.lib.item.GenericItem;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ModItems {
	public static Item clamShell = null;
	public static Item clamWand = null;

	public static void registerToRegistry() {
		EnderClam.registryHelper.setAutoaddItemModels(true);

		EnderClam.registryHelper.addItem(clamShell = new GenericItem(EnderClam.MODID, "clamshell"));
		EnderClam.registryHelper.addItem(clamWand = new GenericItem(EnderClam.MODID, "wand") {
			@Override
			public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos,
					EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
				IBlockState state = worldIn.getBlockState(pos);
				if (state.getBlock()==ModBlocks.clam) {
					int growth = state.getValue(BlockClam.GROWTH);
					if (growth>1) {
						if (!worldIn.isRemote) {
							if (growth==2) {
								player.addItemStackToInventory(new ItemStack(Blocks.SAND));
							} else if (growth==3) {
								player.addItemStackToInventory(new ItemStack(Items.ENDER_PEARL));
							}
							worldIn.setBlockState(pos, state.withProperty(BlockClam.GROWTH, 0), 2);
						}
						return EnumActionResult.SUCCESS;
					}
				}
				return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
			}
		});
	}

	public static void registerOreDictionaryEntries() {
	}
}
