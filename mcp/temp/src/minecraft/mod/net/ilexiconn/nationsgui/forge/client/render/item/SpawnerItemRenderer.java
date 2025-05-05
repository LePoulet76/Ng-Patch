package net.ilexiconn.nationsgui.forge.client.render.item;

import net.ilexiconn.nationsgui.forge.client.render.item.SpawnerItemRenderer$1;
import net.ilexiconn.nationsgui.forge.server.item.MobSpawnerItem;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.boss.BossStatus;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import net.minecraftforge.client.IItemRenderer.ItemRendererHelper;
import org.lwjgl.opengl.GL11;

public class SpawnerItemRenderer implements IItemRenderer {

   public boolean handleRenderType(ItemStack item, ItemRenderType type) {
      return true;
   }

   public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
      return true;
   }

   public void renderItem(ItemRenderType type, ItemStack item, Object ... data) {
      switch(SpawnerItemRenderer$1.$SwitchMap$net$minecraftforge$client$IItemRenderer$ItemRenderType[type.ordinal()]) {
      case 1:
      case 2:
         GL11.glTranslatef(0.5F, 0.5F, 0.5F);
         this.renderInventoryItem((RenderBlocks)data[0], item);
         break;
      case 3:
         this.renderInventoryItem((RenderBlocks)data[0], item);
         break;
      case 4:
         GL11.glScalef(0.5F, 0.5F, 0.5F);
         this.renderInventoryItem((RenderBlocks)data[0], item);
      }

   }

   public void renderInventoryItem(RenderBlocks render, ItemStack item) {
      int meta = item.func_77960_j();
      if(meta == 0) {
         meta = 90;
      }

      String bossName = BossStatus.field_82827_c;
      int bossTimeout = BossStatus.field_82826_b;

      try {
         WorldClient e = Minecraft.func_71410_x().field_71441_e;
         render.func_78600_a(Block.field_72065_as, 0, 1.0F);
         GL11.glPushMatrix();
         EntityLiving entity = MobSpawnerItem.getEntity(meta, e);
         if(entity != null) {
            entity.func_70029_a(e);
            float f1 = 0.4375F;
            if((double)entity.field_82151_R > 1.5D) {
               f1 = 0.1F;
            }

            GL11.glRotatef(-20.0F, 1.0F, 0.0F, 0.0F);
            GL11.glTranslatef(0.0F, -0.4F, 0.0F);
            GL11.glScalef(f1, f1, f1);
            entity.func_70012_b(0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
            RenderManager.field_78727_a.func_78719_a(entity, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
         }

         GL11.glPopMatrix();
         GL11.glEnable('\u803a');
         OpenGlHelper.func_77473_a(OpenGlHelper.field_77476_b);
         GL11.glDisable(3553);
         OpenGlHelper.func_77473_a(OpenGlHelper.field_77478_a);
      } catch (Exception var9) {
         if(Tessellator.field_78398_a.field_78415_z) {
            Tessellator.field_78398_a.func_78381_a();
         }
      }

      BossStatus.field_82827_c = bossName;
      BossStatus.field_82826_b = bossTimeout;
   }
}
