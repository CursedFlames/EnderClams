package worldgen;

import java.util.Random;

import cursedflames.enderclam.ModConfig;
import cursedflames.enderclam.block.BlockClam;
import cursedflames.enderclam.block.ModBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

public class ClamWorldGen implements IWorldGenerator {
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world,
			IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
		genClams(random, chunkX, chunkZ, world);
	}

	private void genClams(Random random, int chunkX, int chunkZ, World world) {
		// TODO spawntries conf option
		for (int i = 0; i<ModConfig.spawnTries.getInt(1); i++) {
			int x = (chunkX*16)+random.nextInt(16)+8;
			int z = (chunkZ*16)+random.nextInt(16)+8;
			BlockPos pos = new BlockPos(x, 63, z);
			Biome biome = world.getBiome(pos);
			if (biome==Biomes.OCEAN||biome==Biomes.DEEP_OCEAN) {
				for (int y = 62; y>16; y--) {
					pos = pos.offset(EnumFacing.DOWN);
					IBlockState state = world.getBlockState(pos);
					if (state.isSideSolid(world, pos, EnumFacing.UP)) {
						IBlockState state1 = world.getBlockState(pos.add(0, 1, 0));
						IBlockState state2 = world.getBlockState(pos.add(0, 2, 0));
						if (state1.getBlock()==Blocks.WATER&&state2.getBlock()==Blocks.WATER
								&&random.nextDouble()<ModConfig.spawnChance.getDouble()) {
							int growth = random.nextInt(3);
							if (growth==2
									&&random.nextDouble()<BlockClam.pearlChance.getDouble(0.1D)) {
								growth = 3;
							}
							world.setBlockState(pos.add(0, 1, 0),
									ModBlocks.clam.getDefaultState()
											.withProperty(BlockClam.FACING,
													EnumFacing.getHorizontal(random.nextInt(4)))
											.withProperty(BlockClam.GROWTH, growth));
						}
						break;
					}
				}
			}
		}
	}
}
