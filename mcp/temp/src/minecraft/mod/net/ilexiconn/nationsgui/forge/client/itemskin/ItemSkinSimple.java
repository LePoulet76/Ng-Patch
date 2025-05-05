package net.ilexiconn.nationsgui.forge.client.itemskin;

import java.io.File;
import java.util.Iterator;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.itemskin.AbstractItemSkin;
import net.ilexiconn.nationsgui.forge.client.itemskin.AbstractSkin;
import net.ilexiconn.nationsgui.forge.client.itemskin.ItemSkinBow;
import net.ilexiconn.nationsgui.forge.client.itemskin.SkinType;
import net.ilexiconn.nationsgui.forge.client.texture.LocalTextureAtlasSprite;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import org.apache.commons.io.FilenameUtils;
import org.json.simple.JSONObject;
import org.lwjgl.opengl.GL11;

public class ItemSkinSimple extends AbstractItemSkin {

   private Icon icon = null;
   private final String textureName;


   public ItemSkinSimple(JSONObject object) {
      super(object);
      this.textureName = (String)object.get("textureName");
   }

   protected void render(float partialTick) {
      if(this.icon != null) {
         TextureManager textureManager = Minecraft.func_71410_x().func_110434_K();
         ItemStack itemStack = new ItemStack(this.getItemID(), 1, 0);
         textureManager.func_110577_a(textureManager.func_130087_a(itemStack.func_94608_d()));
         Tessellator tessellator = Tessellator.field_78398_a;
         float f = this.icon.func_94209_e();
         float f1 = this.icon.func_94212_f();
         float f2 = this.icon.func_94206_g();
         float f3 = this.icon.func_94210_h();
         GL11.glEnable('\u803a');
         GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
         ItemRenderer.func_78439_a(tessellator, f1, f2, f, f3, this.icon.func_94211_a(), this.icon.func_94216_b(), 0.0625F);
      }
   }

   public static void register(IconRegister iconRegister) {
      AbstractSkin[] var1 = SkinType.ITEM_SIMPLE.getSkins();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         AbstractSkin abstractSkin = var1[var3];
         ItemSkinSimple skinSimple = (ItemSkinSimple)abstractSkin;
         File file = new File("assets/textures/itemskins/" + skinSimple.textureName + ".png");
         TextureMap textureMap = (TextureMap)iconRegister;
         String name = "itemskin-" + FilenameUtils.removeExtension(file.getName());
         Object icon = textureMap.getTextureExtry(name);
         if(icon == null) {
            LocalTextureAtlasSprite sprite = new LocalTextureAtlasSprite(name, file);
            textureMap.setTextureEntry(name, sprite);
            icon = sprite;
         }

         skinSimple.icon = (Icon)icon;
      }

   }

   public static Icon getCustomIcon(Icon originalIcon, EntityPlayer player, ItemStack itemStack) {
      Iterator bowIcon = getSkinsOfItem(SkinType.ITEM_SIMPLE, itemStack.field_77993_c).iterator();

      AbstractItemSkin abstractItemSkin;
      do {
         if(!bowIcon.hasNext()) {
            Icon bowIcon1 = ItemSkinBow.getCustomIcon(player, itemStack);
            if(bowIcon1 != null) {
               return bowIcon1;
            }

            return originalIcon;
         }

         abstractItemSkin = (AbstractItemSkin)bowIcon.next();
      } while(!ClientProxy.SKIN_MANAGER.playerHasSkin(player.field_71092_bJ, (AbstractSkin)abstractItemSkin));

      return ((ItemSkinSimple)abstractItemSkin).icon;
   }
}
