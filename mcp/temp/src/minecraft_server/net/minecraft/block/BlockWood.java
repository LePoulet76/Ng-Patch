package net.minecraft.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class BlockWood extends Block {

   public static final String[] field_72152_a = new String[]{"oak", "spruce", "birch", "jungle"};


   public BlockWood(int p_i2289_1_) {
      super(p_i2289_1_, Material.field_76245_d);
      this.func_71849_a(CreativeTabs.field_78030_b);
   }

   public int func_71899_b(int p_71899_1_) {
      return p_71899_1_;
   }

}
