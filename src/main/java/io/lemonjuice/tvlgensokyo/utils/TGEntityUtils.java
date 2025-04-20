package io.lemonjuice.tvlgensokyo.utils;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.vector.Vector3d;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Optional;
import java.util.function.Predicate;

public class TGEntityUtils {
    public static ArrayList<EntityRayTraceResult> rayTraceAllEntities(Entity entity, Vector3d start, Vector3d end, Predicate<Entity> predicate) {
        ArrayList<EntityRayTraceResult> list = new ArrayList<>();

        for(Entity entity1 : entity.world.getEntitiesInAABBexcluding(entity, entity.getBoundingBox().expand(entity.getMotion()).grow(1.0D), predicate)) {
            AxisAlignedBB axisAlignedBB = entity1.getBoundingBox().grow(0.3D);
            Optional<Vector3d> optional = axisAlignedBB.rayTrace(start, end);
            if(optional.isPresent()) {
                list.add(new EntityRayTraceResult(entity1));
            }
        }

        return list;
    }

    @Nullable
    public static Entity getClosestEntity(Entity entity, Predicate<Entity> predicate, float maxDistance) {
        Entity entity1 = null;
        double d0 = -1.0;
        double x = entity.getPosX();
        double y = entity.getPosY();
        double z = entity.getPosZ();

        for(Entity i : entity.world.getEntitiesInAABBexcluding(entity, new AxisAlignedBB(x - maxDistance, y - maxDistance, z - maxDistance, x + maxDistance, y + maxDistance, z + maxDistance), predicate)) {
            double d1 = entity.getDistanceSq(i.getPosX(), i.getPosY(), i.getPosZ());
            if(d0 == -1.0 || d1 < d0) {
                d0 = d1;
                entity1 = i;
            }
        }

        return entity1;
    }
}
