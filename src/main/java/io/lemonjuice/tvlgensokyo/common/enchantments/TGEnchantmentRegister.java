package io.lemonjuice.tvlgensokyo.common.enchantments;

import io.lemonjuice.tvlgensokyo.TravelogueGensokyo;
import io.lemonjuice.tvlgensokyo.common.enchantments.misc.PowerSavingEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class TGEnchantmentRegister {
    public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, TravelogueGensokyo.MODID);

    public static final RegistryObject<Enchantment> POWER_SAVING = ENCHANTMENTS.register("power_saving", PowerSavingEnchantment::new);
}
