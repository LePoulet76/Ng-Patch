package net.ilexiconn.nationsgui.forge.client.voices.textures;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class IndependentTexture {

   public static final IndependentTexture TEXTURES = new IndependentTexture("gvctextures");
   public static final IndependentTexture GUI_WIZARD = new IndependentTexture("wizard_gui");
   private String texture;
   private Object resource;
   private static final ResourceLocation steve = new ResourceLocation("textures/entity/steve.png");


   public IndependentTexture(String texture) {
      this.texture = texture;
      this.resource = new ResourceLocation("gvc", "textures/" + texture + ".png");
   }

   public void bindTexture(Minecraft mc) {
      mc.func_110434_K().func_110577_a((ResourceLocation)this.getTexture());
   }

   public static void bindDefaultPlayer(Minecraft mc) {
      mc.func_110434_K().func_110577_a(steve);
   }

   public static void bindPlayer(Minecraft mc, Entity entity) {
      mc.func_110434_K().func_110577_a(((AbstractClientPlayer)entity).func_110306_p());
   }

   public static void bindClientPlayer(Minecraft mc) {
      mc.func_110434_K().func_110577_a(mc.field_71439_g.func_110306_p());
   }

   public String getTexturePath() {
      return "textures/" + this.texture + ".png";
   }

   public Object getTexture() {
      return this.resource;
   }

}
