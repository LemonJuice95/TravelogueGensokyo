package io.lemonjuice.tvlgensokyo.common.danmaku;

import io.lemonjuice.tvlgensokyo.TravelogueGensokyo;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Danmaku {
    public Type type;
    public Color color;
    public final HashMap<Integer, List<Action>> actions = new HashMap<>();
    public final int maxTick;
    public final float damage;

    public Danmaku(Type type, Color color, int maxTick, float damage) {
        this.type = type;
        this.color = color;
        this.maxTick = maxTick;
        this.damage = damage;
    }

    public Danmaku(CompoundNBT nbt) {
        this.type = Type.DOT;
        if(nbt.contains("Type"))
            this.type = Type.values()[nbt.getInt("Type")];
        this.color = Color.WHITE;
        if(nbt.contains("Color"))
            this.color = Color.values()[nbt.getInt("Color")];
        if(nbt.contains("MaxTick"))
            this.maxTick = nbt.getInt("MaxTick");
        else
            this.maxTick = 1;
        if(nbt.contains("Damage"))
            this.damage = nbt.getFloat("Damage");
        else
            this.damage = 0.0F;
        if(nbt.contains("Actions")) {
            ListNBT actionList = nbt.getList("Actions", 10);
            actionList.forEach((a) -> {
                try {
                    Action action = DanmakuActions.ACTION_SERIALIZERS.get(((CompoundNBT) a).getString("ActionType")).get().read((CompoundNBT) a);
                    this.addAction(((CompoundNBT) a).getInt("Tick"), action);
                } catch (NullPointerException e) {
                    TravelogueGensokyo.LOGGER.error("Invalid Action NBT!");
                    TravelogueGensokyo.LOGGER.error(e);
                }
            });
        }
    }

    public void addAction(int tickAt, Action action) {
        if (!this.actions.containsKey(tickAt)) {
            this.actions.put(tickAt, new ArrayList<>());
        }
        this.actions.get(tickAt).add(action);
    }

    public CompoundNBT toCompoundNBT() {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putInt("Type", this.type.ordinal());
        nbt.putInt("Color", this.color.ordinal());
        nbt.putInt("MaxTick", this.maxTick);
        nbt.putFloat("Damage", this.damage);
        ListNBT actionsNBT = new ListNBT();
        for(int i : this.actions.keySet()) {
            this.actions.get(i).forEach((a) -> {
                try {
                    Action.Serializer<Action> serializer = (Action.Serializer<Action>)DanmakuActions.ACTION_SERIALIZERS.get(a.getName()).get();
                    CompoundNBT action = serializer.write(a);
                    action.putInt("Tick", i);
                    actionsNBT.add(action);
                } catch (NullPointerException e) {
                    TravelogueGensokyo.LOGGER.error("Can't get the serializer of action: " + a + ", is it registered?");
                } catch (ClassCastException e) {
                    TravelogueGensokyo.LOGGER.error("Failed to serialize the action: " + a);
                }
            });
        }
        nbt.put("Actions", actionsNBT);
        return nbt;
    }

    public enum Type {
        DOT(0.66F),
        SMALL_JADE(0.83F);

        private final float renderingScale;

        private Type(float renderingScale){
            this.renderingScale = renderingScale;
        }

        public float getRenderingScale() {
            return this.renderingScale;
        }
    }

    public enum Color {
        WHITE,
        RED,
        ORANGE,
        YELLOW,
        GREEN,
        CYAN,
        BLUE,
        PURPLE,
        BLACK
    }
}
