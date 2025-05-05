package fr.nationsglory.itemmanager.client;

import fr.nationsglory.itemmanager.CommonProxy;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import net.ilexiconn.nationsgui.forge.server.asm.NationsGUITransformer;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

public class ClientProxy extends CommonProxy {

   public void postInit() {
      super.postInit();
      Iterator var1 = NationsGUITransformer.config.getCreativeTabBlacklist().iterator();

      while(var1.hasNext()) {
         String tabName = (String)var1.next();
         ArrayList creativeTabsList = new ArrayList();
         CreativeTabs tabs = null;
         boolean passed = false;
         CreativeTabs[] var6 = CreativeTabs.field_78032_a;
         int var7 = var6.length;

         int var8;
         for(var8 = 0; var8 < var7; ++var8) {
            CreativeTabs item = var6[var8];
            if(!item.func_78013_b().equals(tabName)) {
               if(passed) {
                  try {
                     Field e = CreativeTabs.class.getDeclaredField(NationsGUITransformer.inDevelopment?"tabIndex":"field_78033_n");
                     e.setAccessible(true);
                     e.set(item, Integer.valueOf(item.func_78021_a() - 1));
                  } catch (NoSuchFieldException var11) {
                     var11.printStackTrace();
                  } catch (IllegalAccessException var12) {
                     var12.printStackTrace();
                  }
               }

               creativeTabsList.add(item);
            } else {
               tabs = item;
               passed = true;
            }
         }

         if(tabs != null) {
            Item[] var13 = Item.field_77698_e;
            var7 = var13.length;

            for(var8 = 0; var8 < var7; ++var8) {
               Item var14 = var13[var8];
               if(var14 != null && var14.func_77640_w() != null && var14.func_77640_w().equals(tabs)) {
                  var14.func_77637_a(CreativeTabs.field_78026_f);
                  if(var14 instanceof ItemBlock) {
                     Block.field_71973_m[var14.field_77779_bT].func_71849_a(CreativeTabs.field_78026_f);
                  }
               }
            }

            CreativeTabs.field_78032_a = (CreativeTabs[])creativeTabsList.toArray(new CreativeTabs[creativeTabsList.size()]);
         }
      }

   }
}
