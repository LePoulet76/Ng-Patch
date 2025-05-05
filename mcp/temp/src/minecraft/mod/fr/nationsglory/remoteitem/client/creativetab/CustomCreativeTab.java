package fr.nationsglory.remoteitem.client.creativetab;

import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class CustomCreativeTab extends CreativeTabs {

   public static Item creativeItem = (new Item(29744)).func_111206_d("remoteitem:nglogo");


   public CustomCreativeTab(String label) {
      super(label);
      LanguageRegistry.instance().addStringLocalization("itemGroup." + label, label);
   }

   public Item func_78016_d() {
      return creativeItem;
   }

}
