package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHalfSlab;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class BlockWoodSlab extends BlockHalfSlab {

   public static final String[] field_72243_a = new String[]{"oak", "spruce", "birch", "jungle"};


   public BlockWoodSlab(int p_i2288_1_, boolean p_i2288_2_) {
      super(p_i2288_1_, p_i2288_2_, Material.field_76245_d);
      this.func_71849_a(CreativeTabs.field_78030_b);
   }

   public int func_71885_a(int p_71885_1_, Random p_71885_2_, int p_71885_3_) {
      return Block.field_72092_bO.field_71990_ca;
   }

   protected ItemStack func_71880_c_(int p_71880_1_) {
      return new ItemStack(Block.field_72092_bO.field_71990_ca, 2, p_71880_1_ & 7);
   }

   public String func_72240_d(int p_72240_1_) {
      if(p_72240_1_ < 0 || p_72240_1_ >= field_72243_a.length) {
         p_72240_1_ = 0;
      }

      return super.func_71917_a() + "." + field_72243_a[p_72240_1_];
   }

}
