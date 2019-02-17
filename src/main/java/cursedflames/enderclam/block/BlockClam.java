package cursedflames.enderclam.block;

import java.util.Random;

import cursedflames.enderclam.EnderClam;
import cursedflames.enderclam.item.ModItems;
import cursedflames.lib.block.GenericBlock;
import cursedflames.lib.config.Config.EnumPropSide;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.config.Property;

public class BlockClam extends GenericBlock {
	public static final PropertyDirection FACING = PropertyDirection.create("facing",
			EnumFacing.Plane.HORIZONTAL);
	public static final PropertyInteger GROWTH = PropertyInteger.create("growth", 0, 3);
	// Need this so the game doesn't crash, since we have Material.WATER
	public static final PropertyInteger LEVEL = PropertyInteger.create("level", 0, 15);

	public static Property pearlChance;

	public BlockClam() {
		// need water material so air bubble doesn't appear - in 1.13 we'll be
		// able to just use waterlogging
		super(EnderClam.MODID, "clam", null, Material.WATER, 1, 0);

		setDefaultState(this.getDefaultState().withProperty(FACING, EnumFacing.NORTH)
				.withProperty(GROWTH, 0).withProperty(LEVEL, 15));
		setTickRandomly(true);

		setHarvestLevel("axe", 0);

		pearlChance = EnderClam.config.addPropDouble("pearlChance", "General",
				"The probability of an ender clam producing a pearl instead of a block of sand",
				0.1D, EnumPropSide.SERVER, 0D, 1D);
	}

	@Override
	public boolean canPlaceBlockAt(World world, BlockPos pos) {
		return super.canPlaceBlockAt(world, pos)&&this.canBlockStay(world, pos);
	}

	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state,
			EntityLivingBase placer, ItemStack stack) {
		world.setBlockState(pos,
				state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()), 2);
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, FACING, GROWTH, LEVEL);
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta&3))
				.withProperty(GROWTH, (meta&12)>>2);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(FACING).getHorizontalIndex()+(state.getValue(GROWTH)<<2);
	}

	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
		if (!world.isAreaLoaded(pos, 1))
			return;

		int growth = state.getValue(GROWTH);
		if (growth<2) {
			if (rand.nextInt(25)==0) {
				if (growth==1) {
					world.setBlockState(pos, state.withProperty(GROWTH,
							growth+(rand.nextDouble()<pearlChance.getDouble() ? 2 : 1)), 2);
				} else {
					world.setBlockState(pos, state.withProperty(GROWTH, growth+1), 2);
				}
			}
		}
		// TODO causes issues with high tick speeds?
//		if (!this.canBlockStay(world, pos)) {
//			world.setBlockState(pos, Blocks.WATER.getDefaultState());
//			spawnAsEntity(world, pos, new ItemStack(Item.getItemFromBlock(this)));
//		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public void neighborChanged(IBlockState state, World world, BlockPos pos, Block blockIn,
			BlockPos fromPos) {
		super.neighborChanged(state, world, pos, blockIn, fromPos);

		if (!this.canBlockStay(world, pos)) {
			world.setBlockState(pos, Blocks.WATER.getDefaultState());
			spawnAsEntity(world, pos, new ItemStack(Item.getItemFromBlock(this)));
		}
	}

	@Override
	public boolean canHarvestBlock(IBlockAccess world, BlockPos pos, EntityPlayer player) {
		return true;
	};

	@Override
	public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state,
			float chance, int fortune) {
		int growth = state.getValue(GROWTH);
		if (growth==2) {
			spawnAsEntity(worldIn, pos, new ItemStack(Blocks.SAND));
		} else if (growth==3) {
			spawnAsEntity(worldIn, pos, new ItemStack(Items.ENDER_PEARL));
		}
		spawnAsEntity(worldIn, pos, new ItemStack(ModItems.clamShell, 2));
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	private static final AxisAlignedBB BB = new AxisAlignedBB(0, 0, 0, 1, 0.5, 1);

	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn,
			BlockPos pos) {
		return BB;
	}

	public boolean canBlockStay(World world, BlockPos pos) {
		return world.getBlockState(pos.offset(EnumFacing.NORTH)).getBlock()==Blocks.WATER
				&&world.getBlockState(pos.offset(EnumFacing.SOUTH)).getBlock()==Blocks.WATER
				&&world.getBlockState(pos.offset(EnumFacing.EAST)).getBlock()==Blocks.WATER
				&&world.getBlockState(pos.offset(EnumFacing.WEST)).getBlock()==Blocks.WATER
				&&(world.getBlockState(pos.offset(EnumFacing.UP)).getBlock()==Blocks.WATER||world
						.getBlockState(pos.offset(EnumFacing.UP)).getBlock()==Blocks.FLOWING_WATER)
				&&world.getBlockState(pos.offset(EnumFacing.DOWN)).isSideSolid(world, pos,
						EnumFacing.UP);
	}

	@Override
	public boolean isReplaceable(IBlockAccess worldIn, BlockPos pos) {
		return false;
	}
}
