package net.ilexiconn.nationsgui.forge.server.json.registry.armor;

import com.google.gson.JsonObject;
import cpw.mods.fml.common.registry.GameRegistry;
import net.ilexiconn.nationsgui.forge.server.json.registry.JSONRegistry;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraftforge.common.EnumHelper;

public class JSONArmorRegistry extends JSONRegistry<JSONArmorSet>
{
    public JSONArmorSet constructNew(String name, JsonObject object)
    {
        JSONArmor[] armorSet = new JSONArmor[4];
        int[] damageReduction = new int[4];
        JsonObject helmet = null;
        JsonObject chestplate = null;
        JsonObject leggings = null;
        JsonObject boots = null;

        if (object.has("helmet"))
        {
            helmet = object.get("helmet").getAsJsonObject();
            damageReduction[0] = helmet.get("damage_reduction").getAsInt();
        }

        if (object.has("chestplate"))
        {
            chestplate = object.get("chestplate").getAsJsonObject();
            damageReduction[1] = chestplate.get("damage_reduction").getAsInt();
        }

        if (object.has("leggings"))
        {
            leggings = object.get("leggings").getAsJsonObject();
            damageReduction[2] = leggings.get("damage_reduction").getAsInt();
        }

        if (object.has("boots"))
        {
            boots = object.get("boots").getAsJsonObject();
            damageReduction[3] = boots.get("damage_reduction").getAsInt();
        }

        EnumArmorMaterial material = EnumHelper.addArmorMaterial(name, object.get("durability").getAsInt(), damageReduction, object.get("enchantability").getAsInt());

        if (helmet != null)
        {
            armorSet[0] = new JSONArmor(helmet.get("id").getAsInt() - 256, material, 0);
            armorSet[0].setUnlocalizedName("nationsgui." + name + ".helmet");
        }

        if (chestplate != null)
        {
            armorSet[1] = new JSONArmor(chestplate.get("id").getAsInt() - 256, material, 1);
            armorSet[1].setUnlocalizedName("nationsgui." + name + ".chestplate");
        }

        if (leggings != null)
        {
            armorSet[2] = new JSONArmor(leggings.get("id").getAsInt() - 256, material, 2);
            armorSet[2].setUnlocalizedName("nationsgui." + name + ".leggings");
        }

        if (boots != null)
        {
            armorSet[3] = new JSONArmor(boots.get("id").getAsInt() - 256, material, 3);
            armorSet[3].setUnlocalizedName("nationsgui." + name + ".boots");
        }

        return JSONArmorSet.create(armorSet[0], armorSet[1], armorSet[2], armorSet[3]);
    }

    public void register(String name, JSONArmorSet armorSet)
    {
        if (armorSet.getHelmet() != null)
        {
            GameRegistry.registerItem(armorSet.getHelmet(), "nationsgui." + name + ".helmet");
        }

        if (armorSet.getChestplate() != null)
        {
            GameRegistry.registerItem(armorSet.getChestplate(), "nationsgui." + name + ".chestplate");
        }

        if (armorSet.getLeggings() != null)
        {
            GameRegistry.registerItem(armorSet.getLeggings(), "nationsgui." + name + ".leggings");
        }

        if (armorSet.getBoots() != null)
        {
            GameRegistry.registerItem(armorSet.getBoots(), "nationsgui." + name + ".boots");
        }
    }

    public Class<JSONArmorSet> getType()
    {
        return JSONArmorSet.class;
    }

    public void register(String var1, Object var2)
    {
        this.register(var1, (JSONArmorSet)var2);
    }

    public Object constructNew(String var1, JsonObject var2)
    {
        return this.constructNew(var1, var2);
    }
}
