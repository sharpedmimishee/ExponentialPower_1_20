package io.github.mosadie.exponentialpower.blocks;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

public class BlockManager {
	public static final BlockBehaviour.Properties BLOCK_PROPERTIES = BlockBehaviour.Properties.of()
		.mapColor(MapColor.COLOR_BLUE)
		.forceSolidOn()
		.pushReaction(PushReaction.DESTROY)
		.strength(2.5f, 15.0f);
}
