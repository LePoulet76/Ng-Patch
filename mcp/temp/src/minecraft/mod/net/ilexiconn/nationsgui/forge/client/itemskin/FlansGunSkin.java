package net.ilexiconn.nationsgui.forge.client.itemskin;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Iterator;
import javax.imageio.ImageIO;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.itemskin.AbstractItemSkin;
import net.ilexiconn.nationsgui.forge.client.itemskin.AbstractSkin;
import net.ilexiconn.nationsgui.forge.client.itemskin.SkinType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.json.simple.JSONObject;
import org.lwjgl.opengl.GL11;

public class FlansGunSkin extends AbstractItemSkin {

   private final ResourceLocation resourceLocation;


   public FlansGunSkin(JSONObject object) {
      super(object);
      String textureName = (String)object.get("textureName");
      this.resourceLocation = new ResourceLocation("nationsgui", "skins/flansmod_gun/" + textureName);

      try {
         if(Minecraft.func_71410_x().func_110434_K().func_110581_b(this.resourceLocation) == null) {
            BufferedImage e = ImageIO.read(new File("assets/textures/itemskins/" + textureName + ".png"));
            Minecraft.func_71410_x().func_110434_K().func_110579_a(this.resourceLocation, new DynamicTexture(e));
         }
      } catch (Exception var4) {
         var4.printStackTrace();
      }

   }

   protected void render(float partialTick) {
      float f1 = 1.0F;
      GL11.glColor3f(f1, f1, f1);
      ItemStack itemstack = new ItemStack(this.getItemID(), 1, 0);
      GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
      RenderManager.field_78727_a.field_78721_f.func_78443_a(Minecraft.func_71410_x().field_71439_g, itemstack, 0);
      if(itemstack.func_77973_b().func_77623_v()) {
         for(int x = 1; x < itemstack.func_77973_b().getRenderPasses(itemstack.func_77960_j()); ++x) {
            RenderManager.field_78727_a.field_78721_f.func_78443_a(Minecraft.func_71410_x().field_71439_g, itemstack, x);
         }
      }

   }

   public static ResourceLocation getGunSkinFromPlayer(EntityPlayer entityPlayer, int itemID) {
      Iterator var2 = getSkinsOfItem(SkinType.FLANSMOD_GUN, itemID).iterator();

      FlansGunSkin gunSkin;
      do {
         if(!var2.hasNext()) {
            return null;
         }

         AbstractSkin skin = (AbstractSkin)var2.next();
         gunSkin = (FlansGunSkin)skin;
      } while(!ClientProxy.SKIN_MANAGER.playerHasSkin(entityPlayer.field_71092_bJ, (AbstractSkin)gunSkin));

      return gunSkin.resourceLocation;
   }
}
