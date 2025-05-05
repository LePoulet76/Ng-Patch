package net.ilexiconn.nationsgui.forge.client.gui.shop.type;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.ilexiconn.nationsgui.forge.client.gui.shop.type.ICategoryType;
import net.ilexiconn.nationsgui.forge.client.gui.shop.type.ShopType;
import net.ilexiconn.nationsgui.forge.client.gui.shop.type.URLType;

@SideOnly(Side.CLIENT)
public enum CategoryTypes {

   SHOP("SHOP", 0, new ShopType()),
   URL("URL", 1, new URLType());
   private ICategoryType type;
   // $FF: synthetic field
   private static final CategoryTypes[] $VALUES = new CategoryTypes[]{SHOP, URL};


   private CategoryTypes(String var1, int var2, ICategoryType type) {
      this.type = type;
   }

   public ICategoryType getType() {
      return this.type;
   }

}
