package io.lemonjuice.tvlgensokyo.api;

import com.google.common.base.Suppliers;
import io.lemonjuice.tvlgensokyo.common.spell.Spell;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;

import java.util.function.Supplier;

public class TGAPI {
    private static final Supplier<ITGAPI> API_SUPPLIER = Suppliers.memoize(() -> {
        try {
            return (ITGAPI) Class.forName("io.lemonjuice.tvlgensokyo.api.TGAPIImpl").newInstance();
        } catch (ReflectiveOperationException e) {
            LogManager.getLogger().warn("Unable to find TGAPIImpl, using a dummy one");
            return TGDummyAPI.INSTANCE;
        }
    });

    public static ITGAPI getInstance() {
        return API_SUPPLIER.get();
    }

    public interface ITGAPI {
        public void registerSpell(ResourceLocation name, Spell spell);
    }
}
