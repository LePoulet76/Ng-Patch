/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.potion.Potion
 */
package net.ilexiconn.nationsgui.forge.client;

import com.google.common.collect.Lists;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;

@SideOnly(value=Side.CLIENT)
public class ClientPotions {
    private static final Map<Integer, List<Potion>> POTIONS = new WeakHashMap<Integer, List<Potion>>();

    public static void addPotionToEntity(EntityLivingBase entity, Potion potion) {
        if (POTIONS.containsKey(entity.field_70157_k)) {
            POTIONS.get(entity.field_70157_k).add(potion);
        } else {
            POTIONS.put(entity.field_70157_k, Lists.newArrayList((Object[])new Potion[]{potion}));
        }
    }

    public static void removePotionFromEntity(EntityLivingBase entity, Potion potion) {
        if (POTIONS.containsKey(entity.field_70157_k)) {
            POTIONS.get(entity.field_70157_k).remove(potion);
        }
    }

    public static List getPotionsForEntity(EntityLivingBase entity) {
        if (entity == null) {
            return Collections.emptyList();
        }
        return ClientPotions.getOrDefault(entity.field_70157_k, Collections.emptyList());
    }

    private static List getOrDefault(Object key, List defaultValue) {
        List v = POTIONS.get(key);
        return v != null || POTIONS.containsKey(key) ? v : defaultValue;
    }
}

