package net.ilexiconn.nationsgui.forge.client.render.debug;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import icbm.explosion.ex.Ex;
import icbm.explosion.explosive.ExplosiveRegistry;
import icbm.explosion.machines.TileMissileAssembler;
import icbm.explosion.model.tiles.ModelMissileAssemblerClaw;
import icbm.explosion.model.tiles.ModelMissileAssemblerPanel;
import icbm.explosion.render.entity.RenderMissile;
import java.util.HashMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModelCustom;
import net.minecraftforge.common.ForgeDirection;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RenderMissileAssembler extends TileEntitySpecialRenderer {

   public static final ResourceLocation TEXTURE_FILE = new ResourceLocation("icbm", "textures/models/missileAssembler.png");
   public static final ModelMissileAssemblerPanel MODEL_PANEL = new ModelMissileAssemblerPanel();
   public static final ModelMissileAssemblerClaw MODEL_CLAW1 = new ModelMissileAssemblerClaw(-2);
   public static final ModelMissileAssemblerClaw MODEL_CLAW2 = new ModelMissileAssemblerClaw(12);
   public static final ModelMissileAssemblerClaw MODEL_CLAW3 = new ModelMissileAssemblerClaw(-16);


   public void renderAModelAt(TileMissileAssembler tileEntity, double x, double y, double z, float f) {
      GL11.glPushMatrix();
      this.func_110628_a(TEXTURE_FILE);
      GL11.glTranslatef((float)x + 0.5F, (float)y + 1.5F, (float)z + 0.5F);
      GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
      if(tileEntity.placedSide != ForgeDirection.UP && tileEntity.placedSide != ForgeDirection.DOWN) {
         if(tileEntity.placedSide == ForgeDirection.EAST) {
            GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);
            GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
            GL11.glTranslatef(1.0F, -1.0F, 0.0F);
         } else if(tileEntity.placedSide == ForgeDirection.WEST) {
            GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);
            GL11.glTranslatef(1.0F, -1.0F, 0.0F);
            if(tileEntity.rotationSide == 0) {
               GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
            } else if(tileEntity.rotationSide == 1) {
               GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
            } else if(tileEntity.rotationSide == 2) {
               GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
            }
         } else if(tileEntity.placedSide == ForgeDirection.NORTH) {
            GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);
            GL11.glTranslatef(1.0F, -1.0F, 0.0F);
         } else if(tileEntity.placedSide == ForgeDirection.SOUTH) {
            GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);
            GL11.glTranslatef(0.0F, -1.0F, 1.0F);
            if(tileEntity.rotationSide == 1) {
               GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
            } else if(tileEntity.rotationSide == 2) {
               GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
            } else if(tileEntity.rotationSide == 3) {
               GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
            }
         }
      } else {
         if(tileEntity.rotationSide == 1) {
            GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
         } else if(tileEntity.rotationSide == 2) {
            GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
         } else if(tileEntity.rotationSide == 3) {
            GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
         }

         if(tileEntity.placedSide == ForgeDirection.DOWN) {
            GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
            GL11.glTranslatef(0.0F, -2.0F, 0.0F);
         }
      }

      MODEL_PANEL.render(0.0625F);
      MODEL_CLAW1.render(0.0625F);
      MODEL_CLAW2.render(0.0625F);
      MODEL_CLAW3.render(0.0625F);
      if(tileEntity.missileID >= 0) {
         Ex missile = (Ex)ExplosiveRegistry.get(tileEntity.missileID);
         float scale = 0.8F;
         float right = 1.0F;
         if(missile.getTier() != 2 && missile.hasBlockForm() && missile.getTier() != 3 && missile.getTier() == 4) {
            ;
         }

         GL11.glTranslatef(right, 0.0F, 0.0F);
         GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);
         GL11.glScalef(scale, scale, scale);
         GL11.glTranslatef(1.0F, 0.0F, 0.0F);
         FMLClientHandler.instance().getClient().field_71446_o.func_110577_a(missile.getMissileResource());
         HashMap var12 = RenderMissile.cache;
         HashMap var13 = RenderMissile.cache;
         synchronized(RenderMissile.cache) {
            if(!RenderMissile.cache.containsKey(missile)) {
               RenderMissile.cache.put(missile, missile.getMissileModel());
            }

            ((IModelCustom)RenderMissile.cache.get(missile)).renderAll();
         }
      }

      GL11.glPopMatrix();
   }

   public void func_76894_a(TileEntity tileentity, double d, double d1, double d2, float f) {
      this.renderAModelAt((TileMissileAssembler)tileentity, d, d1, d2, f);
   }

}
