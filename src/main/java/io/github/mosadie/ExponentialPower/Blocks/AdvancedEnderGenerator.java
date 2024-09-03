package io.github.mosadie.exponentialpower.blocks;

import io.github.mosadie.exponentialpower.setup.Registration;
import io.github.mosadie.exponentialpower.entities.AdvancedEnderGeneratorBE;
import io.github.mosadie.exponentialpower.entities.BaseClasses.GeneratorBE;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class AdvancedEnderGenerator extends EnderGenerator {
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new AdvancedEnderGeneratorBE(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return type == Registration.ADV_ENDER_GENERATOR_BE.get() ? GeneratorBE::tick : null;
    }
}
