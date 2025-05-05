package net.minecraft.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class BlockStoneBrick extends Block {

   public static final String[] field_72188_a = new String[]{"default", "mossy", "cracked", "chiseled"};
   public static final String[] field_94407_b = new String[]{null, "mossy", "cracked", "carved"};


   public BlockStoneBrick(int p_i2255_1_) {
      super(p_i2255_1_, Material.field_76246_e);
      this.func_71849_a(CreativeTabs.field_78030_b);
   }

   public int func_71899_b(int p_71899_1_) {
      return p_71899_1_;
   }

}
