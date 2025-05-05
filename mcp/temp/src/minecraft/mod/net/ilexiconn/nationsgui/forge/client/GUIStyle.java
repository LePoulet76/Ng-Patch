package net.ilexiconn.nationsgui.forge.client;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public enum GUIStyle {

   DEFAULT("DEFAULT", 0, "default");
   private Map<String, ResourceLocation> textures = new HashMap();
   private String id;
   // $FF: synthetic field
   private static final GUIStyle[] $VALUES = new GUIStyle[]{DEFAULT};


   private GUIStyle(String var1, int var2, String id) {
      this.id = id;
   }

   public void bindTexture(String type) {
      ResourceLocation texture = (ResourceLocation)this.textures.get(type);
      if(texture == null) {
         this.textures.put(type, texture = new ResourceLocation("nationsglory", "textures/gui/" + this.id + "/" + type + ".png"));
      }

      Minecraft.func_71410_x().func_110434_K().func_110577_a(texture);
   }

   public static GUIStyle getTypeByID(String id) {
      GUIStyle[] var1 = values();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         GUIStyle style = var1[var3];
         if(style.id.equals(id)) {
            return style;
         }
      }

      return DEFAULT;
   }

}
