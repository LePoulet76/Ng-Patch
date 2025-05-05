package net.minecraft.block;

import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class BlockHay extends BlockRotatedPillar {

   public BlockHay(int p_i2210_1_) {
      super(p_i2210_1_, Material.field_76247_b);
      this.func_71849_a(CreativeTabs.field_78030_b);
   }

   public int func_71857_b() {
      return 31;
   }
}
