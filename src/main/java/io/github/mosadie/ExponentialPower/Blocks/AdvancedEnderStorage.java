package io.github.mosadie.exponentialpower.blocks;

import io.github.mosadie.exponentialpower.entities.AdvancedEnderStorageBE;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class AdvancedEnderStorage extends EnderStorage {
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new AdvancedEnderStorageBE(pos, state);
	}
}
