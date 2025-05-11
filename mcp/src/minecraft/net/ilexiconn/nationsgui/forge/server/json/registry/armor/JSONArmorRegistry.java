/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 *  cpw.mods.fml.common.registry.GameRegistry
 *  net.minecraft.item.EnumArmorMaterial
 *  net.minecraft.item.Item
 *  net.minecraftforge.common.EnumHelper
 */
package net.ilexiconn.nationsgui.forge.server.json.registry.armor;

import com.google.gson.JsonObject;
import cpw.mods.fml.common.registry.GameRegistry;
import net.ilexiconn.nationsgui.forge.server.json.registry.JSONRegistry;
import net.ilexiconn.nationsgui.forge.server.json.registry.armor.JSONArmor;
import net.ilexiconn.nationsgui.forge.server.json.registry.armor.JSONArmorSet;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.Item;
import net.minecraftforge.common.EnumHelper;

public class JSONArmorRegistry
extends JSONRegistry<JSONArmorSet> {
    @Override
    public JSONArmorSet constructNew(String name, JsonObject object) {
        JSONArmor[] armorSet = new JSONArmor[4];
        int[] damageReduction = new int[4];
        JsonObject helmet = null;
        JsonObject chestplate = null;
        JsonObject leggings = null;
        JsonObject boots = null;
        if (object.has("helmet")) {
            helmet = object.get("helmet").getAsJsonObject();
            damageReduction[0] = helmet.get("damage_reduction").getAsInt();
        }
        if (object.has("chestplate")) {
            chestplate = object.get("chestplate").getAsJsonObject();
            damageReduction[1] = chestplate.get("damage_reduction").getAsInt();
        }
        if (object.has("leggings")) {
            leggings = object.get("leggings").getAsJsonObject();
            damageReduction[2] = leggings.get("damage_reduction").getAsInt();
        }
        if (object.has("boots")) {
            boots = object.get("boots").getAsJsonObject();
            damageReduction[3] = boots.get("damage_reduction").getAsInt();
        }
        EnumArmorMaterial material = EnumHelper.addArmorMaterial((String)name, (int)object.get("durability").getAsInt(), (int[])damageReduction, (int)object.get("enchantability").getAsInt());
        if (helmet != null) {
            armorSet[0] = new JSONArmor(helmet.get("id").getAsInt() - 256, material, 0);
            armorSet[0].func_77655_b("nationsgui." + name + ".helmet");
        }
        if (chestplate != null) {
            armorSet[1] = new JSONArmor(chestplate.get("id").getAsInt() - 256, material, 1);
            armorSet[1].func_77655_b("nationsgui." + name + ".chestplate");
        }
        if (leggings != null) {
            armorSet[2] = new JSONArmor(leggings.get("id").getAsInt() - 256, material, 2);
            armorSet[2].func_77655_b("nationsgui." + name + ".leggings");
        }
        if (boots != null) {
            armorSet[3] = new JSONArmor(boots.get("id").getAsInt() - 256, material, 3);
            armorSet[3].func_77655_b("nationsgui." + name + ".boots");
        }
        return JSONArmorSet.create(armorSet[0], armorSet[1], armorSet[2], armorSet[3]);
    }

    @Override
    public void register(String name, JSONArmorSet armorSet) {
        if (armorSet.getHelmet() != null) {
            GameRegistry.registerItem((Item)armorSet.getHelmet(), (String)("nationsgui." + name + ".helmet"));
        }
        if (armorSet.getChestplate() != null) {
            GameRegistry.registerItem((Item)armorSet.getChestplate(), (String)("nationsgui." + name + ".chestplate"));
        }
        if (armorSet.getLeggings() != null) {
            GameRegistry.registerItem((Item)armorSet.getLeggings(), (String)("nationsgui." + name + ".leggings"));
        }
        if (armorSet.getBoots() != null) {
            GameRegistry.registerItem((Item)armorSet.getBoots(), (String)("nationsgui." + name + ".boots"));
        }
    }

    @Override
    public Class<JSONArmorSet> getType() {
        return JSONArmorSet.class;
    }
}

