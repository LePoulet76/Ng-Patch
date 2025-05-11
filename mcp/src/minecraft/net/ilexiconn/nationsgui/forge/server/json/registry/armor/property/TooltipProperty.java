/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 */
package net.ilexiconn.nationsgui.forge.server.json.registry.armor.property;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.math.BigInteger;
import net.ilexiconn.nationsgui.forge.server.json.registry.JSONProperty;
import net.ilexiconn.nationsgui.forge.server.json.registry.armor.JSONArmorSet;

public class TooltipProperty
implements JSONProperty<JSONArmorSet> {
    @Override
    public boolean isApplicable(String name, JsonElement element, JSONArmorSet jsonArmorSet) {
        return element.isJsonObject();
    }

    @Override
    public void setProperty(String name, JsonElement element, JSONArmorSet armorSet) {
        JsonObject object = element.getAsJsonObject();
        String color = "FF";
        String background = "FF";
        if (object.has("tooltipColor")) {
            color = color + object.get("tooltipColor").getAsString();
        }
        if (object.has("tooltipBackgroundColor")) {
            background = background + object.get("tooltipBackgroundColor").getAsString();
        }
        if (name.equals("helmet")) {
            armorSet.getArmorSet()[0].tooltipColor = new BigInteger(color, 16).intValue();
            armorSet.getArmorSet()[0].tooltipBackgroundColor = new BigInteger(background, 16).intValue();
        } else if (name.equals("chestplate")) {
            armorSet.getArmorSet()[1].tooltipColor = new BigInteger(color, 16).intValue();
            armorSet.getArmorSet()[1].tooltipBackgroundColor = new BigInteger(background, 16).intValue();
        } else if (name.equals("leggings")) {
            armorSet.getArmorSet()[2].tooltipColor = new BigInteger(color, 16).intValue();
            armorSet.getArmorSet()[2].tooltipBackgroundColor = new BigInteger(background, 16).intValue();
        } else if (name.equals("boots")) {
            armorSet.getArmorSet()[3].tooltipColor = new BigInteger(color, 16).intValue();
            armorSet.getArmorSet()[3].tooltipBackgroundColor = new BigInteger(background, 16).intValue();
        }
    }
}

