package net.ilexiconn.nationsgui.forge.server.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.HashMap;
import java.util.Random;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.ClientKeyHandler;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.server.block.entity.PortalBlockEntity;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IconFlipped;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import org.lwjgl.input.Keyboard;

public class PortalBlock extends BlockDoor implements ITileEntityProvider {

   public static HashMap<String, Long> lastTP = new HashMap();
   @SideOnly(Side.CLIENT)
   private Icon[] field_111044_a;
   @SideOnly(Side.CLIENT)
   private Icon[] field_111043_b;
   @SideOnly(Side.CLIENT)
   private Icon[] textureOff_a;
   private Icon[] textureOff_b;


   public PortalBlock() {
      super(835, Material.field_76237_B);
      this.func_71864_b("island_portal");
      this.func_71900_a(0.0F);
      this.func_71848_c(2.0F);
      this.func_71894_b(10.0F);
      this.func_111022_d("nationsgui:island_portal");
      this.func_71905_a(0.0F, 0.0F, 0.4375F, 1.0F, 1.0F, 0.5625F);
   }

   public boolean func_71926_d() {
      return false;
   }

   @SideOnly(Side.CLIENT)
   public int func_71856_s_() {
      return 1;
   }

   public boolean func_71886_c() {
      return false;
   }

   public AxisAlignedBB func_71872_e(World par1World, int par2, int par3, int par4) {
      return null;
   }

   public void func_71869_a(World par1World, int par2, int par3, int par4, Entity par5Entity) {
      PortalBlockEntity tile = (PortalBlockEntity)((PortalBlockEntity)par1World.func_72796_p(par2, par3, par4));
      if(tile != null && par5Entity.field_70154_o == null && par5Entity.field_70153_n == null && par1World.field_72995_K && System.currentTimeMillis() - ClientEventHandler.joinTime > 10000L) {
         ClientProxy.lastCollidedWithPortalTime = Long.valueOf(System.currentTimeMillis());
         ClientProxy.lastCollidedWithPortalX = par2;
         ClientProxy.lastCollidedWithPortalY = par3;
         ClientProxy.lastCollidedWithPortalZ = par4;
         if(lastTP.containsKey(Minecraft.func_71410_x().field_71439_g.field_71092_bJ) && System.currentTimeMillis() - ((Long)lastTP.get(Minecraft.func_71410_x().field_71439_g.field_71092_bJ)).longValue() < 4000L) {
            ClientProxy.sendClientHotBarMessage(I18n.func_135053_a("nationsgui.portal.teleporting"), 3000L);
         } else {
            ClientProxy.sendClientHotBarMessage(I18n.func_135053_a("nationsgui.portal.teleport").replaceAll("<key>", Keyboard.getKeyName(ClientKeyHandler.KEY_PORTAL_TP.field_74512_d)), 300L);
         }
      }

   }

   public boolean func_71903_a(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9) {
      PortalBlockEntity tile = (PortalBlockEntity)((PortalBlockEntity)par1World.func_72796_p(par2, par3, par4));
      if(tile.owner != null && par5EntityPlayer.getDisplayName().equalsIgnoreCase(tile.owner)) {
         par5EntityPlayer.func_71035_c("\u00a76Code : \u00a7e" + tile.code);
      }

      return false;
   }

   public Icon func_71895_b(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5) {
      PortalBlockEntity tile = (PortalBlockEntity)((PortalBlockEntity)par1IBlockAccess.func_72796_p(par2, par3, par4));
      if(tile != null && par5 != 1 && par5 != 0) {
         int i1 = this.func_72234_b_(par1IBlockAccess, par2, par3, par4);
         int j1 = i1 & 3;
         boolean flag = (i1 & 4) != 0;
         boolean flag1 = false;
         boolean flag2 = (i1 & 8) != 0;
         if(flag) {
            if(j1 == 0 && par5 == 2) {
               flag1 = !flag1;
            } else if(j1 == 1 && par5 == 5) {
               flag1 = !flag1;
            } else if(j1 == 2 && par5 == 3) {
               flag1 = !flag1;
            } else if(j1 == 3 && par5 == 4) {
               flag1 = !flag1;
            }
         } else {
            if(j1 == 0 && par5 == 5) {
               flag1 = !flag1;
            } else if(j1 == 1 && par5 == 3) {
               flag1 = !flag1;
            } else if(j1 == 2 && par5 == 4) {
               flag1 = !flag1;
            } else if(j1 == 3 && par5 == 2) {
               flag1 = !flag1;
            }

            if((i1 & 16) != 0) {
               flag1 = !flag1;
            }
         }

         return flag2?(tile.active?this.field_111044_a[flag1?1:0]:this.textureOff_a[flag1?1:0]):(tile.active?this.field_111043_b[flag1?1:0]:this.textureOff_b[flag1?1:0]);
      } else {
         return tile != null?(tile.active?this.field_111043_b[0]:this.textureOff_b[0]):this.field_111043_b[0];
      }
   }

   public void func_94332_a(IconRegister par1IconRegister) {
      this.field_111044_a = new Icon[2];
      this.field_111043_b = new Icon[2];
      this.textureOff_a = new Icon[2];
      this.textureOff_b = new Icon[2];
      this.field_111044_a[0] = par1IconRegister.func_94245_a("nationsgui:island_portal");
      this.field_111043_b[0] = par1IconRegister.func_94245_a("nationsgui:island_portal");
      this.textureOff_a[0] = par1IconRegister.func_94245_a("nationsgui:island_portal_off");
      this.textureOff_b[0] = par1IconRegister.func_94245_a("nationsgui:island_portal_off");
      this.field_111044_a[1] = new IconFlipped(this.field_111044_a[0], true, false);
      this.field_111043_b[1] = new IconFlipped(this.field_111043_b[0], true, false);
      this.textureOff_a[1] = new IconFlipped(this.textureOff_a[0], true, false);
      this.textureOff_b[1] = new IconFlipped(this.textureOff_b[0], true, false);
   }

   public Icon func_71858_a(int par1, int par2) {
      return this.field_111043_b[0];
   }

   public int func_71922_a(World par1World, int par2, int par3, int par4) {
      return 4755;
   }

   public int func_71885_a(int par1, Random par2Random, int par3) {
      return 0;
   }

   @SideOnly(Side.CLIENT)
   public void func_71862_a(World par1World, int par2, int par3, int par4, Random par5Random) {
      PortalBlockEntity tile = (PortalBlockEntity)((PortalBlockEntity)par1World.func_72796_p(par2, par3, par4));
      if(tile != null && tile.active) {
         if(par5Random.nextInt(100) == 0) {
            par1World.func_72980_b((double)par2 + 0.5D, (double)par3 + 0.5D, (double)par4 + 0.5D, "portal.portal", 0.5F, par5Random.nextFloat() * 0.4F + 0.8F, false);
         }

         for(int l = 0; l < 4; ++l) {
            double d0 = (double)((float)par2 + par5Random.nextFloat());
            double d1 = (double)((float)par3 + par5Random.nextFloat());
            double d2 = (double)((float)par4 + par5Random.nextFloat());
            double d3 = 0.0D;
            double d4 = 0.0D;
            double d5 = 0.0D;
            int i1 = par5Random.nextInt(2) * 2 - 1;
            d3 = ((double)par5Random.nextFloat() - 0.5D) * 0.5D;
            d4 = ((double)par5Random.nextFloat() - 0.5D) * 0.5D;
            d5 = ((double)par5Random.nextFloat() - 0.5D) * 0.5D;
            if(par1World.func_72798_a(par2 - 1, par3, par4) != this.field_71990_ca && par1World.func_72798_a(par2 + 1, par3, par4) != this.field_71990_ca) {
               d0 = (double)par2 + 0.5D + 0.25D * (double)i1;
               d3 = (double)(par5Random.nextFloat() * 2.0F * (float)i1);
            } else {
               d2 = (double)par4 + 0.5D + 0.25D * (double)i1;
               d5 = (double)(par5Random.nextFloat() * 2.0F * (float)i1);
            }

            par1World.func_72869_a("portal", d0, d1, d2, d3, d4, d5);
         }
      }

   }

   public TileEntity func_72274_a(World world) {
      return new PortalBlockEntity();
   }

   public void func_71852_a(World var1, int var2, int var3, int var4, int var5, int var6) {
      super.func_71852_a(var1, var2, var3, var4, var5, var6);
      var1.func_72932_q(var2, var3, var4);
   }

}
