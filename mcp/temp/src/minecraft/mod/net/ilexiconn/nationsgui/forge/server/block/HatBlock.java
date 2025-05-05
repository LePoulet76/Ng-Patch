package net.ilexiconn.nationsgui.forge.server.block;

import java.util.Random;
import net.ilexiconn.nationsgui.forge.NationsGUI;
import net.ilexiconn.nationsgui.forge.server.block.entity.TileEntityHatBlock;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class HatBlock extends Block implements ITileEntityProvider {

   public HatBlock() {
      super(3590, Material.field_76253_m);
      this.func_71894_b(Block.field_71981_t.field_72029_cc);
      this.func_71864_b("hatblock");
      this.func_111022_d("wool_colored_white");
   }

   public void func_71860_a(World world, int x, int y, int z, EntityLivingBase entity, ItemStack itemStack) {
      world.func_72921_c(x, y, z, MathHelper.func_76128_c((double)(entity.field_70177_z * 16.0F / 360.0F) + 0.5D) & 15, 2);
      if(world.func_72796_p(x, y, z) instanceof TileEntityHatBlock) {
         TileEntityHatBlock tileEntityHatBlock = (TileEntityHatBlock)world.func_72796_p(x, y, z);
         if(itemStack.func_77978_p() != null && itemStack.func_77978_p().func_74764_b("HatID")) {
            tileEntityHatBlock.setHatID(itemStack.func_77978_p().func_74779_i("HatID"));
         }
      }

   }

   public void func_71852_a(World world, int x, int y, int z, int metadata, int fortune) {
      ItemStack is = new ItemStack(NationsGUI.HATBLOCK.field_71990_ca, 1, 0);
      if(world.func_72796_p(x, y, z) instanceof TileEntityHatBlock) {
         TileEntityHatBlock te = (TileEntityHatBlock)world.func_72796_p(x, y, z);
         NBTTagCompound comp = new NBTTagCompound();
         comp.func_74778_a("HatID", te.getHatID());
         is.field_77990_d = comp;
         is.func_82834_c("\u00a76" + te.getHatID());
         world.func_72838_d(new EntityItem(world, (double)x, (double)y, (double)z, is));
         super.func_71852_a(world, x, y, z, metadata, fortune);
      }
   }

   public int func_71885_a(int par1, Random par2Random, int par3) {
      return 0;
   }

   public AxisAlignedBB func_71911_a_(World par1World, int par2, int par3, int par4) {
      return AxisAlignedBB.func_72330_a((double)par2 + 0.2D, (double)par3 + 0.0D, (double)par4 + 0.2D, (double)par2 + 0.8D, (double)par3 + 0.6D, (double)par4 + 0.8D);
   }

   public AxisAlignedBB func_71872_e(World par1World, int par2, int par3, int par4) {
      return AxisAlignedBB.func_72330_a((double)par2 + 0.2D, (double)par3 + 0.0D, (double)par4 + 0.2D, (double)par2 + 0.8D, (double)par3 + 0.6D, (double)par4 + 0.8D);
   }

   public int func_71857_b() {
      return -1;
   }

   public boolean func_71886_c() {
      return false;
   }

   public boolean func_71926_d() {
      return false;
   }

   public TileEntity func_72274_a(World world) {
      return new TileEntityHatBlock();
   }
}
