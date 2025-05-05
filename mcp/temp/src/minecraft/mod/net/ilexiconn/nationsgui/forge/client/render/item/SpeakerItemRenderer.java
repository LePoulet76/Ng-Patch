package net.ilexiconn.nationsgui.forge.client.render.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.ilexiconn.nationsgui.forge.client.model.block.SpeakerModel;
import net.ilexiconn.nationsgui.forge.client.render.item.SpeakerItemRenderer$1;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import net.minecraftforge.client.IItemRenderer.ItemRendererHelper;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class SpeakerItemRenderer implements IItemRenderer {

   public static final ResourceLocation TEXTURE = new ResourceLocation("nationsgui", "textures/blocks/speaker.png");
   public static final SpeakerModel MODEL = new SpeakerModel();


   public boolean handleRenderType(ItemStack item, ItemRenderType type) {
      return type != ItemRenderType.FIRST_PERSON_MAP;
   }

   public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
      return helper != ItemRendererHelper.BLOCK_3D;
   }

   public void renderItem(ItemRenderType type, ItemStack item, Object ... data) {
      switch(SpeakerItemRenderer$1.$SwitchMap$net$minecraftforge$client$IItemRenderer$ItemRenderType[type.ordinal()]) {
      case 1:
         GL11.glScalef(0.8F, 0.8F, 0.8F);
         GL11.glTranslatef(0.0F, 0.9F, 0.6F);
         this.renderBlock(0.0F, 1.5F, 0.0F);
         break;
      case 2:
         GL11.glScalef(1.4F, 1.4F, 1.4F);
         GL11.glRotatef(30.0F, 0.0F, 1.0F, 0.0F);
         GL11.glTranslatef(-0.6F, 1.1F, 0.8F);
         this.renderBlock(0.5F, 1.5F, 0.5F);
         break;
      case 3:
         GL11.glScalef(1.4F, 1.4F, 1.4F);
         GL11.glRotatef(60.0F, 0.0F, 1.0F, 0.0F);
         GL11.glTranslatef(-0.5F, 0.7F, 0.7F);
         this.renderBlock(0.5F, 1.5F, 0.5F);
         break;
      case 4:
         GL11.glScalef(1.4F, 1.4F, 1.4F);
         GL11.glTranslatef(0.7F, 1.55F, 0.0F);
         GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
         this.renderBlock(0.0F, 1.0F, 0.0F);
      }

   }

   public void renderBlock(float x, float y, float z) {
      GL11.glPushMatrix();
      Minecraft.func_71410_x().field_71446_o.func_110577_a(TEXTURE);
      GL11.glTranslatef(x, y, z);
      GL11.glScalef(-1.0F, -1.0F, 1.0F);
      MODEL.func_78088_a((Entity)null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
      GL11.glPopMatrix();
   }

}
