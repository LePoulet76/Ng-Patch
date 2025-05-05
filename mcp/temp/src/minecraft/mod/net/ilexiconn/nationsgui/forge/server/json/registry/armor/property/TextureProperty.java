package net.ilexiconn.nationsgui.forge.server.json.registry.armor.property;

import com.google.common.hash.Hashing;
import com.google.gson.JsonElement;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Map;
import javax.imageio.ImageIO;
import net.ilexiconn.nationsgui.forge.server.json.registry.JSONProperty;
import net.ilexiconn.nationsgui.forge.server.json.registry.armor.JSONArmor;
import net.ilexiconn.nationsgui.forge.server.json.registry.armor.JSONArmorSet;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ResourceLocation;
import org.bouncycastle.util.encoders.Base64;

@SideOnly(Side.CLIENT)
public class TextureProperty implements JSONProperty<JSONArmorSet> {

   private File parent = new File(".", "nationsgui");
   private Field field;


   public TextureProperty() {
      try {
         this.field = RenderBiped.class.getDeclaredField("field_110859_k");
         this.field.setAccessible(true);
      } catch (NoSuchFieldException var2) {
         var2.printStackTrace();
      }

   }

   public boolean isApplicable(String name, JsonElement element, JSONArmorSet armorSet) {
      return name.equals("texture") || name.equals("texture_overlay");
   }

   public void setProperty(String name, JsonElement element, JSONArmorSet armorSet) {
      String texture = element.getAsString();
      byte[] data = Base64.decode(texture.substring(texture.indexOf(",") + 1));
      String hash = Hashing.sha1().hashBytes(data).toString();
      File imageFile = new File(this.parent, hash.substring(0, 2) + File.separator + hash);
      BufferedImage image = null;
      if(!imageFile.exists()) {
         imageFile.getParentFile().mkdirs();

         try {
            image = ImageIO.read(new ByteArrayInputStream(data));
            ImageIO.write(image, "png", imageFile);
         } catch (IOException var13) {
            var13.printStackTrace();
         }
      } else {
         try {
            image = ImageIO.read(imageFile);
         } catch (IOException var12) {
            var12.printStackTrace();
         }
      }

      for(int location = 0; location < armorSet.getArmorSet().length; ++location) {
         JSONArmor e = armorSet.getArmorSet()[location];
         if(e != null) {
            if(name.equals("texture_overlay")) {
               e.textureOverlayHash = "nationsgui/armor/" + hash;
            } else {
               e.textureHash = "nationsgui/armor/" + hash;
            }
         }
      }

      ResourceLocation var14 = Minecraft.func_71410_x().func_110434_K().func_110578_a("nationsgui/" + hash, new DynamicTexture(image));

      try {
         ((Map)this.field.get((Object)null)).put("nationsgui/armor/" + hash, var14);
      } catch (IllegalAccessException var11) {
         var11.printStackTrace();
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
