package io.lemonjuice.tvlgensokyo.datagen.loots;

import io.lemonjuice.tvlgensokyo.common.block.PinkPetalsBlock;
import io.lemonjuice.tvlgensokyo.common.block.TGBlockRegister;
import io.lemonjuice.tvlgensokyo.common.block.crops.RiceBlock;
import io.lemonjuice.tvlgensokyo.common.item.TGItemRegister;
import net.minecraft.advancements.criterion.StatePropertiesPredicate;
import net.minecraft.block.BedBlock;
import net.minecraft.block.Block;
import net.minecraft.block.CropsBlock;
import net.minecraft.data.loot.BlockLootTables;
import net.minecraft.loot.*;
import net.minecraft.loot.conditions.BlockStateProperty;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.loot.conditions.SurvivesExplosion;
import net.minecraft.loot.functions.SetCount;
import net.minecraft.state.properties.BedPart;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.state.properties.Half;
import net.minecraft.util.Hand;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class TGBlockLootTables extends BlockLootTables {
    private final Set<Block> BLOCKS = new HashSet<>();
    private static final float[] DEFAULT_SAPLING_DROP_RATES = new float[]{0.05F, 0.0625F, 0.083333336F, 0.1F};

    private ILootCondition.IBuilder tmpBuilder;

    @Override
    protected void addTables() {

        Arrays.asList(TGBlockRegister.SWEET_BED_WHITE.get(), TGBlockRegister.SWEET_BED_ORANGE.get(), TGBlockRegister.SWEET_BED_MAGENTA.get(), TGBlockRegister.SWEET_BED_LIGHT_BLUE.get(),
                TGBlockRegister.SWEET_BED_YELLOW.get(), TGBlockRegister.SWEET_BED_LIME.get(), TGBlockRegister.SWEET_BED_PINK.get(), TGBlockRegister.SWEET_BED_GRAY.get(),
                TGBlockRegister.SWEET_BED_LIGHT_GRAY.get(), TGBlockRegister.SWEET_BED_CYAN.get(), TGBlockRegister.SWEET_BED_PURPLE.get(), TGBlockRegister.SWEET_BED_BLUE.get(),
                TGBlockRegister.SWEET_BED_BROWN.get(), TGBlockRegister.SWEET_BED_GREEN.get(), TGBlockRegister.SWEET_BED_RED.get(), TGBlockRegister.SWEET_BED_BLACK.get())
        .forEach((object) -> {
            this.tmpBuilder = BlockStateProperty.builder(object).fromProperties(StatePropertiesPredicate.Builder.newBuilder().withProp(BedBlock.PART, BedPart.HEAD));

            this.registerLootTable(object, (block) -> {
                return LootTable.builder()
                        .addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(object.getBed())).acceptCondition(SurvivesExplosion.builder()).acceptCondition(tmpBuilder))
                        .addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(TGItemRegister.SWEET_PILLOW.get()).acceptCondition(SurvivesExplosion.builder()).acceptCondition(tmpBuilder)));
            });
        });

        this.tmpBuilder = BlockStateProperty.builder(TGBlockRegister.CUCUMBER.get()).fromProperties(StatePropertiesPredicate.Builder.newBuilder().withIntProp(CropsBlock.AGE, 7));
        this.registerLootTable(TGBlockRegister.CUCUMBER.get(), (block) -> {
            return droppingAndBonusWhen(TGBlockRegister.CUCUMBER.get(), TGItemRegister.CUCUMBER.get(), TGItemRegister.CUCUMBER_SEEDS.get(), this.tmpBuilder);
        });

        this.registerLootTable(TGBlockRegister.RICE.get(), (block) -> {
            LootPool.Builder poolBuilder = LootPool.builder();
            for(int i = 0; i <= 2; i++) {
                this.tmpBuilder = BlockStateProperty.builder(TGBlockRegister.RICE.get()).fromProperties(StatePropertiesPredicate.Builder.newBuilder().withIntProp(RiceBlock.AGE, i));
                poolBuilder.addEntry(ItemLootEntry.builder(TGItemRegister.RICE.get()).acceptCondition(this.tmpBuilder));
            }
            this.tmpBuilder = BlockStateProperty.builder(TGBlockRegister.RICE.get()).fromProperties(StatePropertiesPredicate.Builder.newBuilder().withIntProp(RiceBlock.AGE, 3).withProp(RiceBlock.HALF, DoubleBlockHalf.UPPER));
            poolBuilder.addEntry(ItemLootEntry.builder(TGItemRegister.EAR_OF_RICE.get()).acceptCondition(this.tmpBuilder));

            return LootTable.builder().addLootPool(poolBuilder);
        });

        this.registerLootTable(TGBlockRegister.FALLEN_MAPLE_LEAVES.get(), (block) -> {
            return droppingWithSilkTouchOrShears(TGBlockRegister.FALLEN_MAPLE_LEAVES.get(), EmptyLootEntry.func_216167_a());
        });

        this.registerDropSelfLootTable(TGBlockRegister.MAPLE_LOG.get());
        this.registerDropSelfLootTable(TGBlockRegister.STRIPPED_MAPLE_LOG.get());
        this.registerDropSelfLootTable(TGBlockRegister.MAPLE_WOOD.get());
        this.registerDropSelfLootTable(TGBlockRegister.STRIPPED_MAPLE_WOOD.get());
        this.registerDropSelfLootTable(TGBlockRegister.MAPLE_PLANKS.get());
        this.registerLootTable(TGBlockRegister.MAPLE_SLAB.get(), droppingSlab(TGBlockRegister.MAPLE_SLAB.get()));
        this.registerDropSelfLootTable(TGBlockRegister.MAPLE_STAIRS.get());
        this.registerDropSelfLootTable(TGBlockRegister.MAPLE_SIGN.get());
        this.registerLootTable(TGBlockRegister.MAPLE_WALL_SIGN.get(), dropping(TGItemRegister.MAPLE_SIGN.get()));
        this.registerDropSelfLootTable(TGBlockRegister.MAPLE_FENCE.get());
        this.registerDropSelfLootTable(TGBlockRegister.MAPLE_FENCE_GATE.get());
        this.registerDropSelfLootTable(TGBlockRegister.MAPLE_BUTTON.get());
        this.registerDropSelfLootTable(TGBlockRegister.MAPLE_PRESSURE_PLATE.get());
        this.registerDropSelfLootTable(TGBlockRegister.MAPLE_SAPLING.get());
        this.registerLootTable(TGBlockRegister.MAPLE_LEAVES.get(), droppingWithChancesAndSticks(TGBlockRegister.MAPLE_LEAVES.get(), TGBlockRegister.MAPLE_SAPLING.get(), DEFAULT_SAPLING_DROP_RATES));

        this.registerLootTable(TGBlockRegister.PINK_PETALS.get(), (block) -> {
            return LootTable.builder()
                    .addLootPool(LootPool.builder().rolls(ConstantRange.of(1))
                            .addEntry(withExplosionDecay(TGBlockRegister.PINK_PETALS.get(), ItemLootEntry.builder(block)
                                    .acceptFunction(SetCount.builder(ConstantRange.of(2)).acceptCondition(BlockStateProperty.builder(block)
                                            .fromProperties(StatePropertiesPredicate.Builder.newBuilder().withIntProp(PinkPetalsBlock.AMOUNT, 2))))
                                    .acceptFunction(SetCount.builder(ConstantRange.of(3)).acceptCondition(BlockStateProperty.builder(block)
                                            .fromProperties(StatePropertiesPredicate.Builder.newBuilder().withIntProp(PinkPetalsBlock.AMOUNT, 3))))
                                    .acceptFunction(SetCount.builder(ConstantRange.of(4)).acceptCondition(BlockStateProperty.builder(block)
                                            .fromProperties(StatePropertiesPredicate.Builder.newBuilder().withIntProp(PinkPetalsBlock.AMOUNT, 4))))
                            )));
        });

        this.registerDropSelfLootTable(TGBlockRegister.SAKURA_LOG.get());
        this.registerDropSelfLootTable(TGBlockRegister.STRIPPED_SAKURA_LOG.get());
        this.registerDropSelfLootTable(TGBlockRegister.SAKURA_WOOD.get());
        this.registerDropSelfLootTable(TGBlockRegister.STRIPPED_SAKURA_WOOD.get());
        this.registerDropSelfLootTable(TGBlockRegister.SAKURA_PLANKS.get());
        this.registerLootTable(TGBlockRegister.SAKURA_SLAB.get(), droppingSlab(TGBlockRegister.SAKURA_SLAB.get()));
        this.registerDropSelfLootTable(TGBlockRegister.SAKURA_STAIRS.get());
        this.registerDropSelfLootTable(TGBlockRegister.SAKURA_SIGN.get());
        this.registerLootTable(TGBlockRegister.SAKURA_WALL_SIGN.get(), dropping(TGItemRegister.SAKURA_SIGN.get()));
        this.registerDropSelfLootTable(TGBlockRegister.SAKURA_FENCE.get());
        this.registerDropSelfLootTable(TGBlockRegister.SAKURA_FENCE_GATE.get());
        this.registerDropSelfLootTable(TGBlockRegister.SAKURA_BUTTON.get());
        this.registerDropSelfLootTable(TGBlockRegister.SAKURA_PRESSURE_PLATE.get());
        this.registerDropSelfLootTable(TGBlockRegister.SAKURA_SAPLING.get());
        this.registerLootTable(TGBlockRegister.SAKURA_LEAVES.get(), droppingWithChancesAndSticks(TGBlockRegister.SAKURA_LEAVES.get(), TGBlockRegister.SAKURA_SAPLING.get(), DEFAULT_SAPLING_DROP_RATES));

        this.registerDropSelfLootTable(TGBlockRegister.SPELL_BOOK_BINDING_TABLE.get());
        this.registerDropSelfLootTable(TGBlockRegister.SPELL_WRITING_TABLE.get());
    }

    @Override
    protected void registerLootTable(Block block, LootTable.Builder builder) {
        super.registerLootTable(block, builder);
        this.BLOCKS.add(block);
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return this.BLOCKS;
    }
}