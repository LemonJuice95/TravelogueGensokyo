package io.lemonjuice.tvlgensokyo.common.world.feature.tree.decorator;

import io.lemonjuice.tvlgensokyo.TravelogueGensokyo;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;
import net.minecraftforge.registries.ForgeRegistries;

public class TGTreeDecoratorTypes {
    public static final TreeDecoratorType<FallenLeavesTreeDecorator> FALLEN_LEAVES = new TreeDecoratorType<>(FallenLeavesTreeDecorator.CODEC);

    public static void registerDecorators() {
        register(FALLEN_LEAVES, "fallen_leaves");
    }

    public static void register(TreeDecoratorType<?> decoratorType, String key) {
        decoratorType.setRegistryName(new ResourceLocation(TravelogueGensokyo.MODID, key));
        ForgeRegistries.TREE_DECORATOR_TYPES.register(decoratorType);
    }
}
