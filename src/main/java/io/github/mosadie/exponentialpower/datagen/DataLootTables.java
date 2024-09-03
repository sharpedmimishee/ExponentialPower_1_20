package io.github.mosadie.exponentialpower.datagen;

import io.github.mosadie.exponentialpower.ExponentialPower;
import io.github.mosadie.exponentialpower.setup.Registration;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.DynamicLoot;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.CopyNameFunction;
import net.minecraft.world.level.storage.loot.functions.CopyNbtFunction;
import net.minecraft.world.level.storage.loot.functions.SetContainerContents;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.providers.nbt.ContextNbtProvider;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class DataLootTables extends LootTableProvider {
    public DataLootTables(PackOutput output) {
        super(output, Collections.emptySet(), List.of(
                new SubProviderEntry(ExponentialBlockTable::new, LootContextParamSets.BLOCK)
        ));
    }

    public static class ExponentialBlockTable extends BlockLootSubProvider {
        protected ExponentialBlockTable() {
            super(Set.of(), FeatureFlags.REGISTRY.allFlags());
        }

        @Override
        protected @NotNull Iterable<Block> getKnownBlocks() {
            return List.of(
                    Registration.ENDER_GENERATOR.get(),
                    Registration.ADV_ENDER_GENERATOR.get(),
                    Registration.ENDER_STORAGE.get(),
                    Registration.ADV_ENDER_STORAGE.get()
            );
        }

        @Override
        protected void generate() {
            ExponentialPower.LOGGER.info("Generating Loot Tables!");
            dropSelf(Registration.ENDER_GENERATOR.get());
            dropSelf(Registration.ADV_ENDER_GENERATOR.get());

            add(Registration.ENDER_STORAGE.get(), createEnderStorageTable("enderstorage", Registration.ENDER_STORAGE.get()));
            add(Registration.ADV_ENDER_STORAGE.get(), createEnderStorageTable("advancedenderstorage", Registration.ADV_ENDER_STORAGE.get()));
        }
    }

    protected static LootTable.Builder createEnderGeneratorTable(String name, Block block, BlockEntityType<?> type) {
        LootPool.Builder builder = LootPool.lootPool()
                .name(name)
                .setRolls(ConstantValue.exactly(1))
                .add(LootItem.lootTableItem(block)
                        .apply(CopyNameFunction.copyName(CopyNameFunction.NameSource.BLOCK_ENTITY))
                        .apply(CopyNbtFunction.copyData(ContextNbtProvider.BLOCK_ENTITY)
                                .copy("Items", "BlockEntityTag.Items", CopyNbtFunction.MergeStrategy.REPLACE))
                        .apply(SetContainerContents.setContents(type)
                                        .withEntry(DynamicLoot.dynamicEntry(new ResourceLocation("minecraft", "contents"))))
                );
        return LootTable.lootTable().withPool(builder);
    }

    protected static LootTable.Builder createEnderStorageTable(String name, Block block) {
        LootPool.Builder builder = LootPool.lootPool()
                .name(name)
                .setRolls(ConstantValue.exactly(1))
                .add(LootItem.lootTableItem(block)
                        .apply(CopyNameFunction.copyName(CopyNameFunction.NameSource.BLOCK_ENTITY))
                        .apply(CopyNbtFunction.copyData(ContextNbtProvider.BLOCK_ENTITY)
                                .copy("energy", "BlockEntityTag.energy", CopyNbtFunction.MergeStrategy.REPLACE))
                );
        return LootTable.lootTable().withPool(builder);
    }
//
//    @Override
//    public void run(CachedOutput output) {
//        addTables();
//
//        Map<ResourceLocation, LootTable> tables = new HashMap<>();
//        for (Map.Entry<Block, LootTable.Builder> entry : lootTables.entrySet()) {
//            tables.put(entry.getKey().getLootTable(), entry.getValue().setParamSet(LootContextParamSets.BLOCK).build());
//        }
//        writeTables(output, tables);
//    }
//
//    private void writeTables(CachedOutput cache, Map<ResourceLocation, LootTable> tables) {
//        Path outputFolder = this.generator.getOutputFolder();
//        tables.forEach((key, lootTable) -> {
//            Path path = outputFolder.resolve("data/" + key.getNamespace() + "/loot_tables/" + key.getPath() + ".json");
//            try {
//                DataProvider.saveStable(cache, LootTables.serialize(lootTable), path);
//            } catch (IOException e) {
//                LOGGER.error("Couldn't write loot table {}", path, e);
//            }
//        });
//    }
//
//    @Override
//    public String getName() {
//        return "ExponentialPower Loot Tables";
//    }
}
