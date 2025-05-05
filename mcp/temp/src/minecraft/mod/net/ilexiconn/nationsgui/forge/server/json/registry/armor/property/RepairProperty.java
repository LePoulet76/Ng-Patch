package net.ilexiconn.nationsgui.forge.server.json.registry.armor.property;

import com.google.gson.JsonElement;
import net.ilexiconn.nationsgui.forge.server.json.registry.JSONProperty;
import net.ilexiconn.nationsgui.forge.server.json.registry.armor.JSONArmor;
import net.ilexiconn.nationsgui.forge.server.json.registry.armor.JSONArmorSet;

public class RepairProperty implements JSONProperty<JSONArmorSet> {

   public boolean isApplicable(String name, JsonElement element, JSONArmorSet armorSet) {
      return name.equals("repair_item");
   }

   public void setProperty(String name, JsonElement element, JSONArmorSet armorSet) {
      JSONArmor[] var4 = armorSet.getArmorSet();
      int var5 = var4.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         JSONArmor armor = var4[var6];
         armor.repairItemID = element.getAsInt();
      }

   }

   // $FF: synthetic method
   // $FF: bridge method
   public void setProperty(String var1, JsonElement var2, Object var3) {
      this.setProperty(var1, var2, (JSONArmorSet)var3);
   }

   // $FF: synthetic method
   // $FF: bridge method
   public boolean isApplicable(String var1, JsonElement var2, Object var3) {
      return this.isApplicable(var1, var2, (JSONArmorSet)var3);
   }
}
