package fr.nationsglory.itemmanager;

import cpw.mods.fml.common.Loader;
import java.util.List;
import net.ilexiconn.nationsgui.forge.server.asm.NationsGUITransformer;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

public class RegisterHook {

   public static boolean blockIsBlacklisted(Object object, String modID) {
      if(modID == null) {
         modID = Loader.instance().activeModContainer().getModId();
      }

      List list = (List)NationsGUITransformer.config.getItemBlacklist().get(modID);
      if(list != null && (object instanceof Block && list.contains(Integer.valueOf(((Block)object).field_71990_ca)) || object instanceof Item && list.contains(Integer.valueOf(((Item)object).field_77779_bT)))) {
         int id = object instanceof Block?((Block)object).field_71990_ca:((Item)object).field_77779_bT;
         Block.field_71973_m[id] = null;
         Item.field_77698_e[id] = null;
         return true;
      } else {
         return false;
      }
   }
}
