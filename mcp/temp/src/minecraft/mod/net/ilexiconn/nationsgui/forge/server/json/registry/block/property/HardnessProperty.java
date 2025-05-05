package net.ilexiconn.nationsgui.forge.server.json.registry.block.property;

import com.google.gson.JsonElement;
import net.ilexiconn.nationsgui.forge.server.json.registry.JSONProperty;
import net.ilexiconn.nationsgui.forge.server.json.registry.block.JSONBlock;

public class HardnessProperty implements JSONProperty<JSONBlock> {

   public boolean isApplicable(String name, JsonElement element, JSONBlock block) {
      return name.equals("hardness");
   }

   public void setProperty(String name, JsonElement element, JSONBlock block) {
      block.func_71848_c(element.getAsFloat());
   }

   // $FF: synthetic method
   // $FF: bridge method
   public void setProperty(String var1, JsonElement var2, Object var3) {
      this.setProperty(var1, var2, (JSONBlock)var3);
   }

   // $FF: synthetic method
   // $FF: bridge method
   public boolean isApplicable(String var1, JsonElement var2, Object var3) {
      return this.isApplicable(var1, var2, (JSONBlock)var3);
   }
}
