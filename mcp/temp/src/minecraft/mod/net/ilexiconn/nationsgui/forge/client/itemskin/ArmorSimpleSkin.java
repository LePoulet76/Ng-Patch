package net.ilexiconn.nationsgui.forge.client.itemskin;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import javax.imageio.ImageIO;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.itemskin.AbstractSkin;
import net.ilexiconn.nationsgui.forge.client.itemskin.SkinType;
import net.ilexiconn.nationsgui.forge.server.util.Tuple;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.json.simple.JSONObject;
import org.lwjgl.opengl.GL11;

public class ArmorSimpleSkin extends AbstractSkin {

   private final HashMap<Integer, ResourceLocation[]> textureMap = new HashMap();
   private final List<Tuple<Integer, ResourceLocation[]>> renderList = new ArrayList();
   protected ModelBiped field_82423_g;
   protected ModelBiped field_82425_h;


   public ArmorSimpleSkin(JSONObject object) {
      super(object);
      if(object.containsKey("items")) {
         Iterator var2 = ((JSONObject)object.get("items")).entrySet().iterator();

         while(var2.hasNext()) {
            Object o = var2.next();

            try {
               Entry e = (Entry)o;
               String textureName = (String)e.getValue();
               int itemID = Integer.parseInt((String)e.getKey());
               ResourceLocation[] locations = new ResourceLocation[]{this.getResourceTexture(textureName + "_layer_1"), this.getResourceTexture(textureName + "_layer_2")};
               this.textureMap.put(Integer.valueOf(itemID), locations);
               ItemStack itemStack = new ItemStack(itemID, 1, 0);
               if(itemStack.func_77973_b() != null && itemStack.func_77973_b() instanceof ItemArmor) {
                  ItemArmor itemArmor = (ItemArmor)itemStack.func_77973_b();
                  this.renderList.add(new Tuple(Integer.valueOf(itemArmor.field_77881_a), locations));
               }
            } catch (Exception var10) {
               var10.printStackTrace();
            }
         }
      }

      this.field_82423_g = new ModelBiped(1.0F);
      this.field_82425_h = new ModelBiped(0.5F);
   }

   private ResourceLocation getResourceTexture(String textureName) throws IOException {
      ResourceLocation resourceLocation = new ResourceLocation("skins/armors/" + textureName);
      if(Minecraft.func_71410_x().func_110434_K().func_110581_b(resourceLocation) == null) {
         BufferedImage textureBuffer = ImageIO.read(new File("assets/textures/armorskins/" + textureName + ".png"));
         Minecraft.func_71410_x().func_110434_K().func_110579_a(resourceLocation, new DynamicTexture(textureBuffer));
      }

      return resourceLocation;
   }

   protected void render(float partialTick) {
      GL11.glTranslatef(-0.63F, 0.8F, 0.0F);
      Iterator var2 = this.renderList.iterator();

      while(var2.hasNext()) {
         Tuple tuple = (Tuple)var2.next();
         this.renderItem(((Integer)tuple.a).intValue(), (ResourceLocation[])tuple.b);
      }

   }

   private void renderItem(int par2, ResourceLocation[] resourceLocation) {
      Minecraft.func_71410_x().func_110434_K().func_110577_a(resourceLocation[par2 == 2?1:0]);
      ModelBiped modelbiped = par2 == 2?this.field_82425_h:this.field_82423_g;
      modelbiped.field_78091_s = false;
      modelbiped.field_78116_c.field_78806_j = par2 == 0;
      modelbiped.field_78114_d.field_78806_j = par2 == 0;
      modelbiped.field_78115_e.field_78806_j = par2 == 1 || par2 == 2;
      modelbiped.field_78112_f.field_78806_j = par2 == 1;
      modelbiped.field_78113_g.field_78806_j = par2 == 1;
      modelbiped.field_78123_h.field_78806_j = par2 == 2 || par2 == 3;
      modelbiped.field_78124_i.field_78806_j = par2 == 2 || par2 == 3;
      modelbiped.func_78088_a(Minecraft.func_71410_x().field_71439_g, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
   }

   public static String getTexture(String base, Entity entity, ItemStack itemStack) {
      if(entity instanceof EntityPlayer && itemStack.func_77973_b() != null && itemStack.func_77973_b() instanceof ItemArmor) {
         EntityPlayer player = (EntityPlayer)entity;
         Iterator var4 = ClientProxy.SKIN_MANAGER.getPlayerActiveSkins(player.field_71092_bJ, SkinType.ARMOR_SIMPLE).iterator();

         while(var4.hasNext()) {
            AbstractSkin playerActiveSkin = (AbstractSkin)var4.next();
            ArmorSimpleSkin armorSimpleSkin = (ArmorSimpleSkin)playerActiveSkin;
            ItemArmor itemArmor = (ItemArmor)itemStack.func_77973_b();
            ResourceLocation[] resourceLocation = (ResourceLocation[])armorSimpleSkin.textureMap.get(Integer.valueOf(itemStack.field_77993_c));
            if(resourceLocation != null) {
               return resourceLocation[itemArmor.field_77881_a == 2?1:0].toString();
            }
         }
      }

      return base;
   }
}
