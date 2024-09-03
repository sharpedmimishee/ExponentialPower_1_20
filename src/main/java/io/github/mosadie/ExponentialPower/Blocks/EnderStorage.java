package io.github.mosadie.exponentialpower.blocks;

import com.buuz135.industrial.item.infinity.InfinityEnergyStorage;
import io.github.mosadie.exponentialpower.entities.BaseClasses.StorageBE;
import io.github.mosadie.exponentialpower.entities.EnderStorageBE;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fml.ModList;
import org.jetbrains.annotations.NotNull;

public class EnderStorage extends Block implements EntityBlock {

    public EnderStorage() {
        super(BlockManager.BLOCK_PROPERTIES);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new EnderStorageBE(pos, state);
    }

    @Override
    public @NotNull InteractionResult use(@NotNull BlockState state, Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand handIn, @NotNull BlockHitResult hit) {
        if (level.isClientSide) {
            return InteractionResult.sidedSuccess(true);
        }

        StorageBE te = (StorageBE) level.getBlockEntity(pos);

        double percent = ((int) (te.energy / te.getMaxEnergy() * 10000.00)) / 100.00;
        player.sendSystemMessage(Component.translatable("screen.exponentialpower.storage_total", te.energy, te.getMaxEnergy(), percent));

        ItemStack stack = player.getItemInHand(handIn);
        stack.getCapability(ForgeCapabilities.ENERGY).ifPresent(cap -> {
            if (ModList.get().isLoaded("industrialforegoing") && cap instanceof InfinityEnergyStorage<?> infinityCap) {
                long sendAmount = Math.min(infinityCap.getLongCapacity() - infinityCap.getLongEnergyStored(), Math.min((long) te.energy, Long.MAX_VALUE));
                infinityCap.setEnergyStored(infinityCap.getLongEnergyStored() + sendAmount);
                te.energy -= sendAmount;
                return;
            }
            te.energy -= cap.receiveEnergy((int) Math.max(te.energy, Integer.MAX_VALUE), false);
        });

        return InteractionResult.SUCCESS;
    }
}
