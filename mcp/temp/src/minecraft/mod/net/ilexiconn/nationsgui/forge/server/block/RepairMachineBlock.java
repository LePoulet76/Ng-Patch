package net.ilexiconn.nationsgui.forge.server.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Random;
import net.ilexiconn.nationsgui.forge.NationsGUI;
import net.ilexiconn.nationsgui.forge.server.block.entity.RepairMachineBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class RepairMachineBlock extends BlockContainer {

   @SideOnly(Side.CLIENT)
   public Icon textureFront;
   @SideOnly(Side.CLIENT)
   public Icon textureBack;
   @SideOnly(Side.CLIENT)
   public Icon textureTop;
   @SideOnly(Side.CLIENT)
   public Icon textureBottom;
   @SideOnly(Side.CLIENT)
   public Icon textureSide;
   public Random random = new Random();


   public RepairMachineBlock() {
      super(NationsGUI.CONFIG.repairMachineID, Material.field_76245_d);
      this.func_71864_b("repair_machine");
      this.func_71849_a(CreativeTabs.field_78030_b);
      this.func_71848_c(2.0F);
   }

   public boolean func_71903_a(World world, int x, int y, int z, EntityPlayer entityPlayer, int side, float hitX, float hitY, float hitZ) {
      if(!world.field_72995_K) {
         entityPlayer.openGui(NationsGUI.INSTANCE, this.field_71990_ca, world, x, y, z);
      }

      return true;
   }

   public void func_71852_a(World world, int x, int y, int z, int blockID, int metadata) {
      RepairMachineBlockEntity blockEntity = (RepairMachineBlockEntity)world.func_72796_p(x, y, z);
      if(blockEntity != null) {
         for(int j1 = 0; j1 < blockEntity.func_70302_i_(); ++j1) {
            ItemStack itemstack = blockEntity.func_70301_a(j1);
            if(itemstack != null) {
               float offsetX = this.random.nextFloat() * 0.8F + 0.1F;
               float offsetY = this.random.nextFloat() * 0.8F + 0.1F;
               float offsetZ = this.random.nextFloat() * 0.8F + 0.1F;

               while(itemstack.field_77994_a > 0) {
                  int stackSize = this.random.nextInt(21) + 10;
                  if(stackSize > itemstack.field_77994_a) {
                     stackSize = itemstack.field_77994_a;
                  }

                  itemstack.field_77994_a -= stackSize;
                  EntityItem entityItem = new EntityItem(world, (double)((float)x + offsetX), (double)((float)y + offsetY), (double)((float)z + offsetZ), new ItemStack(itemstack.field_77993_c, stackSize, itemstack.func_77960_j()));
                  if(itemstack.func_77942_o()) {
                     entityItem.func_92059_d().func_77982_d((NBTTagCompound)itemstack.func_77978_p().func_74737_b());
                  }

                  float radius = 0.05F;
                  entityItem.field_70159_w = (double)((float)this.random.nextGaussian() * radius);
                  entityItem.field_70181_x = (double)((float)this.random.nextGaussian() * radius + 0.2F);
                  entityItem.field_70179_y = (double)((float)this.random.nextGaussian() * radius);
                  world.func_72838_d(entityItem);
               }
            }
         }
      }

      super.func_71852_a(world, x, y, z, blockID, metadata);
   }

   public void func_71861_g(World world, int x, int y, int z) {
      super.func_71861_g(world, x, y, z);
      if(!world.field_72995_K) {
         int back = world.func_72798_a(x, y, z - 1);
         int front = world.func_72798_a(x, y, z + 1);
         int left = world.func_72798_a(x - 1, y, z);
         int right = world.func_72798_a(x + 1, y, z);
         byte metadata = 3;
         if(Block.field_71970_n[back] && !Block.field_71970_n[front]) {
            metadata = 3;
         }

         if(Block.field_71970_n[front] && !Block.field_71970_n[back]) {
            metadata = 2;
         }

         if(Block.field_71970_n[left] && !Block.field_71970_n[right]) {
            metadata = 5;
         }

         if(Block.field_71970_n[right] && !Block.field_71970_n[left]) {
            metadata = 4;
         }

         world.func_72921_c(x, y, z, metadata, 2);
      }

   }

   public void func_71860_a(World world, int x, int y, int z, EntityLivingBase entityLivingBase, ItemStack itemStack) {
      int metadata = MathHelper.func_76128_c((double)(entityLivingBase.field_70177_z * 4.0F / 360.0F) + 0.5D) & 3;
      if(metadata == 0) {
         world.func_72921_c(x, y, z, 2, 2);
      } else if(metadata == 1) {
         world.func_72921_c(x, y, z, 5, 2);
      } else if(metadata == 2) {
         world.func_72921_c(x, y, z, 3, 2);
      } else if(metadata == 3) {
         world.func_72921_c(x, y, z, 4, 2);
      }

      if(itemStack.func_82837_s()) {
         ((RepairMachineBlockEntity)world.func_72796_p(x, y, z)).setCustomName(itemStack.func_82833_r());
      }

   }

   @SideOnly(Side.CLIENT)
   public void func_94332_a(IconRegister iconRegister) {
      this.textureFront = iconRegister.func_94245_a("nationsgui:repair_machine_front");
      this.textureBack = iconRegister.func_94245_a("nationsgui:repair_machine_back");
      this.textureTop = iconRegister.func_94245_a("nationsgui:repair_machine_top");
      this.textureBottom = iconRegister.func_94245_a("nationsgui:repair_machine_bottom");
      this.textureSide = iconRegister.func_94245_a("nationsgui:repair_machine_side");
   }

   @SideOnly(Side.CLIENT)
   public Icon func_71858_a(int side, int meta) {
      return side == 0?this.textureBottom:(side == 1?this.textureTop:(side % 2 == 0 && side + 1 == meta?this.textureBack:(side % 2 == 1 && side - 1 == meta?this.textureBack:(side != meta?this.textureSide:this.textureFront))));
   }

   public TileEntity func_72274_a(World world) {
      return new RepairMachineBlockEntity();
   }
}
