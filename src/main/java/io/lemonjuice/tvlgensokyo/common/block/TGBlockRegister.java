package io.lemonjuice.tvlgensokyo.common.block;

import io.lemonjuice.tvlgensokyo.TravelogueGensokyo;
import io.lemonjuice.tvlgensokyo.client.particle.TGParticleRegister;
import io.lemonjuice.tvlgensokyo.common.block.crops.RiceBlock;
import io.lemonjuice.tvlgensokyo.common.block.workbench.SpellBookBindingTableBlock;
import io.lemonjuice.tvlgensokyo.common.block.workbench.SpellWritingTableBlock;
import io.lemonjuice.tvlgensokyo.common.world.feature.tree.MapleTree;
import io.lemonjuice.tvlgensokyo.common.misc.TGWoodType;
import io.lemonjuice.tvlgensokyo.common.world.feature.tree.SakuraTree;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.EntityType;
import net.minecraft.item.DyeColor;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class TGBlockRegister {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, TravelogueGensokyo.MODID);

    //Crops
    public static final RegistryObject<CropsBlock> CUCUMBER = BLOCKS.register("cucumber", () -> new CropsBlock(AbstractBlock.Properties.create(Material.PLANTS).doesNotBlockMovement().tickRandomly().zeroHardnessAndResistance().sound(SoundType.CROP)));
    public static final RegistryObject<Block> RICE = BLOCKS.register("rice", RiceBlock::new);

    //Workbench
    public static final RegistryObject<Block> SPELL_BOOK_BINDING_TABLE = BLOCKS.register("spell_book_binding_table", () -> new SpellBookBindingTableBlock(AbstractBlock.Properties.create(Material.WOOD).hardnessAndResistance(2.5F).sound(SoundType.WOOD).notSolid()));
    public static final RegistryObject<Block> SPELL_WRITING_TABLE = BLOCKS.register("spell_writing_table", () -> new SpellWritingTableBlock(AbstractBlock.Properties.create(Material.WOOD).hardnessAndResistance(2.5F).sound(SoundType.WOOD).notSolid()));

    //Maple
    public static final RegistryObject<Block> MAPLE_SAPLING = BLOCKS.register("maple_sapling", () -> new SaplingBlock(new MapleTree(), AbstractBlock.Properties.create(Material.PLANTS).doesNotBlockMovement().tickRandomly().zeroHardnessAndResistance().sound(SoundType.PLANT)));
    public static final RegistryObject<Block> MAPLE_LOG = BLOCKS.register("maple_log", () -> createLogBlock(MaterialColor.SAND, MaterialColor.OBSIDIAN));
    public static final RegistryObject<Block> FALLEN_MAPLE_LEAVES = BLOCKS.register("fallen_maple_leaves", TGBlockRegister::createFallenLeaves);
    public static final RegistryObject<Block> MAPLE_LEAVES = BLOCKS.register("maple_leaves", () -> createFallableLeaves(FALLEN_MAPLE_LEAVES.get().getDefaultState(), TGParticleRegister.FALLING_MAPLE_LEAF));
    public static final RegistryObject<Block> STRIPPED_MAPLE_LOG = BLOCKS.register("stripped_maple_log", () -> createLogBlock(MaterialColor.SAND, MaterialColor.SAND));
    public static final RegistryObject<Block> MAPLE_WOOD = BLOCKS.register("maple_wood", () -> new RotatedPillarBlock(AbstractBlock.Properties.create(Material.WOOD, MaterialColor.SAND).hardnessAndResistance(2.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> STRIPPED_MAPLE_WOOD = BLOCKS.register("stripped_maple_wood", () -> new RotatedPillarBlock(AbstractBlock.Properties.create(Material.WOOD, MaterialColor.SAND).hardnessAndResistance(2.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> MAPLE_PLANKS = BLOCKS.register("maple_planks", () -> new Block(AbstractBlock.Properties.create(Material.WOOD, MaterialColor.SAND).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> MAPLE_SLAB = BLOCKS.register("maple_slab", () -> new SlabBlock(AbstractBlock.Properties.from(MAPLE_PLANKS.get())));
    public static final RegistryObject<Block> MAPLE_STAIRS = BLOCKS.register("maple_stairs", () -> new StairsBlock(() -> MAPLE_PLANKS.get().getDefaultState(), AbstractBlock.Properties.from(MAPLE_PLANKS.get())));
    public static final RegistryObject<StandingSignBlock> MAPLE_SIGN = BLOCKS.register("maple_sign", () -> new TGSignBlock(AbstractBlock.Properties.create(Material.WOOD).doesNotBlockMovement().hardnessAndResistance(1.0F).sound(SoundType.WOOD), TGWoodType.MAPLE));
    public static final RegistryObject<WallSignBlock> MAPLE_WALL_SIGN = BLOCKS.register("maple_wall_sign", () -> new TGWallSignBlock(AbstractBlock.Properties.create(Material.WOOD).doesNotBlockMovement().hardnessAndResistance(1.0F).sound(SoundType.WOOD), TGWoodType.MAPLE));
    public static final RegistryObject<Block> MAPLE_FENCE = BLOCKS.register("maple_fence", () -> new FenceBlock(AbstractBlock.Properties.from(MAPLE_PLANKS.get())));
    public static final RegistryObject<Block> MAPLE_FENCE_GATE = BLOCKS.register("maple_fence_gate", () -> new FenceGateBlock(AbstractBlock.Properties.from(MAPLE_PLANKS.get())));
    public static final RegistryObject<Block> MAPLE_BUTTON = BLOCKS.register("maple_button", () -> new WoodButtonBlock(AbstractBlock.Properties.create(Material.MISCELLANEOUS).doesNotBlockMovement().hardnessAndResistance(0.5F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> MAPLE_PRESSURE_PLATE = BLOCKS.register("maple_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, AbstractBlock.Properties.create(Material.WOOD, MaterialColor.SAND).doesNotBlockMovement().hardnessAndResistance(0.5F).sound(SoundType.WOOD)));

    //Sakura
    public static final RegistryObject<Block> SAKURA_SAPLING = BLOCKS.register("sakura_sapling", () -> new SaplingBlock(new SakuraTree(), AbstractBlock.Properties.create(Material.PLANTS).doesNotBlockMovement().tickRandomly().zeroHardnessAndResistance().sound(SoundType.PLANT)));
    public static final RegistryObject<Block> SAKURA_LOG = BLOCKS.register("sakura_log", () -> createLogBlock(MaterialColor.WHITE_TERRACOTTA, MaterialColor.GRAY_TERRACOTTA));
    public static final RegistryObject<Block> STRIPPED_SAKURA_LOG = BLOCKS.register("stripped_sakura_log", () -> new RotatedPillarBlock(AbstractBlock.Properties.create(Material.WOOD, MaterialColor.WHITE_TERRACOTTA).hardnessAndResistance(2.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> PINK_PETALS = BLOCKS.register("pink_petals", PinkPetalsBlock::new);
    public static final RegistryObject<Block> SAKURA_LEAVES = BLOCKS.register("sakura_leaves", SakuraLeavesBlock::new);
    public static final RegistryObject<Block> SAKURA_WOOD = BLOCKS.register("sakura_wood", () -> new RotatedPillarBlock(AbstractBlock.Properties.create(Material.WOOD, MaterialColor.GRAY_TERRACOTTA).hardnessAndResistance(2.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> STRIPPED_SAKURA_WOOD = BLOCKS.register("stripped_sakura_wood", () -> new RotatedPillarBlock(AbstractBlock.Properties.create(Material.WOOD, MaterialColor.WHITE_TERRACOTTA).hardnessAndResistance(2.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> SAKURA_PLANKS = BLOCKS.register("sakura_planks", () -> new Block(AbstractBlock.Properties.create(Material.WOOD, MaterialColor.WHITE_TERRACOTTA).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> SAKURA_SLAB = BLOCKS.register("sakura_slab", () -> new SlabBlock(AbstractBlock.Properties.from(SAKURA_PLANKS.get())));
    public static final RegistryObject<Block> SAKURA_STAIRS = BLOCKS.register("sakura_stairs", () -> new StairsBlock(() -> SAKURA_PLANKS.get().getDefaultState(), AbstractBlock.Properties.from(SAKURA_PLANKS.get())));
    public static final RegistryObject<StandingSignBlock> SAKURA_SIGN = BLOCKS.register("sakura_sign", () -> new TGSignBlock(AbstractBlock.Properties.create(Material.WOOD).doesNotBlockMovement().hardnessAndResistance(1.0F).sound(SoundType.WOOD), TGWoodType.SAKURA));
    public static final RegistryObject<WallSignBlock> SAKURA_WALL_SIGN = BLOCKS.register("sakura_wall_sign", () -> new TGWallSignBlock(AbstractBlock.Properties.create(Material.WOOD).doesNotBlockMovement().hardnessAndResistance(1.0F).sound(SoundType.WOOD), TGWoodType.SAKURA));
    public static final RegistryObject<Block> SAKURA_FENCE = BLOCKS.register("sakura_fence", () -> new FenceBlock(AbstractBlock.Properties.from(SAKURA_PLANKS.get())));
    public static final RegistryObject<Block> SAKURA_FENCE_GATE = BLOCKS.register("sakura_fence_gate", () -> new FenceGateBlock(AbstractBlock.Properties.from(SAKURA_PLANKS.get())));
    public static final RegistryObject<Block> SAKURA_BUTTON = BLOCKS.register("sakura_button", () -> new WoodButtonBlock(AbstractBlock.Properties.create(Material.MISCELLANEOUS).doesNotBlockMovement().hardnessAndResistance(0.5F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> SAKURA_PRESSURE_PLATE = BLOCKS.register("sakura_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, AbstractBlock.Properties.create(Material.WOOD, MaterialColor.WHITE_TERRACOTTA).doesNotBlockMovement().hardnessAndResistance(0.5F).sound(SoundType.WOOD)));

    //Sweet Bed
    public static final RegistryObject<SweetBedBlock> SWEET_BED_WHITE = BLOCKS.register("sweet_bed_white", () -> new SweetBedBlock((BedBlock)Blocks.WHITE_BED, DyeColor.WHITE));
    public static final RegistryObject<SweetBedBlock> SWEET_BED_ORANGE = BLOCKS.register("sweet_bed_orange", () -> new SweetBedBlock((BedBlock)Blocks.ORANGE_BED, DyeColor.ORANGE));
    public static final RegistryObject<SweetBedBlock> SWEET_BED_MAGENTA = BLOCKS.register("sweet_bed_magenta", () -> new SweetBedBlock((BedBlock)Blocks.MAGENTA_BED, DyeColor.MAGENTA));
    public static final RegistryObject<SweetBedBlock> SWEET_BED_LIGHT_BLUE = BLOCKS.register("sweet_bed_light_blue", () -> new SweetBedBlock((BedBlock)Blocks.LIGHT_BLUE_BED, DyeColor.LIGHT_BLUE));
    public static final RegistryObject<SweetBedBlock> SWEET_BED_YELLOW = BLOCKS.register("sweet_bed_yellow", () -> new SweetBedBlock((BedBlock)Blocks.YELLOW_BED, DyeColor.YELLOW));
    public static final RegistryObject<SweetBedBlock> SWEET_BED_LIME = BLOCKS.register("sweet_bed_lime", () -> new SweetBedBlock((BedBlock)Blocks.LIME_BED, DyeColor.LIME));
    public static final RegistryObject<SweetBedBlock> SWEET_BED_PINK = BLOCKS.register("sweet_bed_pink", () -> new SweetBedBlock((BedBlock)Blocks.PINK_BED, DyeColor.PINK));
    public static final RegistryObject<SweetBedBlock> SWEET_BED_GRAY = BLOCKS.register("sweet_bed_gray", () -> new SweetBedBlock((BedBlock)Blocks.GRAY_BED, DyeColor.GRAY));
    public static final RegistryObject<SweetBedBlock> SWEET_BED_LIGHT_GRAY = BLOCKS.register("sweet_bed_light_gray", () -> new SweetBedBlock((BedBlock)Blocks.LIGHT_GRAY_BED, DyeColor.LIGHT_GRAY));
    public static final RegistryObject<SweetBedBlock> SWEET_BED_CYAN = BLOCKS.register("sweet_bed_cyan", () -> new SweetBedBlock((BedBlock)Blocks.CYAN_BED, DyeColor.CYAN));
    public static final RegistryObject<SweetBedBlock> SWEET_BED_PURPLE = BLOCKS.register("sweet_bed_purple", () -> new SweetBedBlock((BedBlock)Blocks.PURPLE_BED, DyeColor.PURPLE));
    public static final RegistryObject<SweetBedBlock> SWEET_BED_BLUE = BLOCKS.register("sweet_bed_blue", () -> new SweetBedBlock((BedBlock)Blocks.BLUE_BED, DyeColor.BLUE));
    public static final RegistryObject<SweetBedBlock> SWEET_BED_BROWN = BLOCKS.register("sweet_bed_brown", () -> new SweetBedBlock((BedBlock)Blocks.BROWN_BED, DyeColor.BROWN));
    public static final RegistryObject<SweetBedBlock> SWEET_BED_GREEN = BLOCKS.register("sweet_bed_green", () -> new SweetBedBlock((BedBlock)Blocks.GREEN_BED, DyeColor.GREEN));
    public static final RegistryObject<SweetBedBlock> SWEET_BED_RED = BLOCKS.register("sweet_bed_red", () -> new SweetBedBlock((BedBlock)Blocks.RED_BED, DyeColor.RED));
    public static final RegistryObject<SweetBedBlock> SWEET_BED_BLACK = BLOCKS.register("sweet_bed_black", () -> new SweetBedBlock((BedBlock)Blocks.BLACK_BED, DyeColor.BLACK));

    //Structure
    public static final RegistryObject<Block> DREAM_BASE = BLOCKS.register("dream_base", () -> new GlassBlock(AbstractBlock.Properties.create(Material.BARRIER).hardnessAndResistance(-1.0F, 3600000.0F).noDrops().setAllowsSpawn(TGBlockRegister::neverAllowSpawn).notSolid().setOpaque(TGBlockRegister::isntSolid).setBlocksVision(TGBlockRegister::isntSolid).setSuffocates(TGBlockRegister::isntSolid)));

    public static Boolean neverAllowSpawn(BlockState state, IBlockReader reader, BlockPos pos, EntityType<?> entity) {
        return false;
    }

    public static Boolean alwaysAllowSpawn(BlockState state, IBlockReader reader, BlockPos pos, EntityType<?> entity) {
        return true;
    }

    public static Boolean allowsSpawnOnLeaves(BlockState state, IBlockReader reader, BlockPos pos, EntityType<?> entity) {
        return entity == EntityType.OCELOT || entity == EntityType.PARROT;
    }

    public static boolean isntSolid(BlockState state, IBlockReader reader, BlockPos pos) {
        return false;
    }

    private static RotatedPillarBlock createLogBlock(MaterialColor topColor, MaterialColor barkColor) {
        return new RotatedPillarBlock(AbstractBlock.Properties.create(Material.WOOD, (state) -> {
            return state.get(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? topColor : barkColor;
        }).hardnessAndResistance(2.0F).sound(SoundType.WOOD));
    }

    private static FallableLeavesBlock createFallableLeaves(BlockState fallenLeaves) {
        return new FallableLeavesBlock(AbstractBlock.Properties.create(Material.LEAVES).hardnessAndResistance(0.2F).tickRandomly().sound(SoundType.PLANT).notSolid().setAllowsSpawn(TGBlockRegister::allowsSpawnOnLeaves).setSuffocates((state, reader, pos) -> false).setBlocksVision((state, reader, pos) -> false), fallenLeaves);
    }

    private static FallableLeavesBlock createFallableLeaves(BlockState fallenLeaves, BasicParticleType fallingLeafParticle) {
        return new FallableLeavesBlock(AbstractBlock.Properties.create(Material.LEAVES).hardnessAndResistance(0.2F).tickRandomly().sound(SoundType.PLANT).notSolid().setAllowsSpawn(TGBlockRegister::allowsSpawnOnLeaves).setSuffocates((state, reader, pos) -> false).setBlocksVision((state, reader, pos) -> false), fallenLeaves, fallingLeafParticle);
    }

    private static LeavesBlock createLeavesBlock() {
        return new LeavesBlock(AbstractBlock.Properties.create(Material.LEAVES).hardnessAndResistance(0.2F).tickRandomly().sound(SoundType.PLANT).notSolid().setAllowsSpawn(TGBlockRegister::allowsSpawnOnLeaves).setSuffocates((state, reader, pos) -> false).setBlocksVision((state, reader, pos) -> false));
    }

    private static FallenLeavesBlock createFallenLeaves() {
        return new FallenLeavesBlock(AbstractBlock.Properties.create(Material.PLANTS).hardnessAndResistance(0.2F).sound(SoundType.PLANT).notSolid().doesNotBlockMovement().setAllowsSpawn(TGBlockRegister::neverAllowSpawn).setSuffocates((state, reader, pos) -> false).setBlocksVision((state, reader, pos) -> false));
    }
}