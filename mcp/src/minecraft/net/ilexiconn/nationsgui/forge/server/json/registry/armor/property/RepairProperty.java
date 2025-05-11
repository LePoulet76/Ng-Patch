/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonElement
 */
package net.ilexiconn.nationsgui.forge.server.json.registry.armor.property;

import com.google.gson.JsonElement;
import net.ilexiconn.nationsgui.forge.server.json.registry.JSONProperty;
import net.ilexiconn.nationsgui.forge.server.json.registry.armor.JSONArmor;
import net.ilexiconn.nationsgui.forge.server.json.registry.armor.JSONArmorSet;

public class RepairProperty
implements JSONProperty<JSONArmorSet> {
    @Override
    public boolean isApplicable(String name, JsonElement element, JSONArmorSet armorSet) {
        return name.equals("repair_item");
    }

    @Override
    public void setProperty(String name, JsonElement element, JSONArmorSet armorSet) {
        for (JSONArmor armor : armorSet.getArmorSet()) {
            armor.repairItemID = element.getAsInt();
        }
    }
}

