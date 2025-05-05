package net.ilexiconn.nationsgui.forge.server.json.registry.block;

import net.minecraft.block.Block;
import net.minecraft.block.StepSound;
import net.minecraft.block.material.Material;

public enum JSONMaterial {

   GRASS("GRASS", 0, Material.field_76247_b, Block.field_71965_g, new String[]{"grass"}),
   DIRT("DIRT", 1, Material.field_76248_c, Block.field_71964_f, new String[]{"dirt", "ground", "gravel"}),
   METAL("METAL", 2, Material.field_76243_f, Block.field_71977_i, new String[]{"iron", "metal"}),
   WOOD("WOOD", 3, Material.field_76245_d, Block.field_71967_e, new String[]{"wood", "planks"}),
   SAND("SAND", 4, Material.field_76251_o, Block.field_71972_l, new String[]{"sand"}),
   STONE("STONE", 5, Material.field_76246_e, Block.field_71976_h, new String[]{"stone", "rock"}),
   GLASS("GLASS", 6, Material.field_76264_q, Block.field_71974_j, new String[]{"glass"});
   private static final JSONMaterial[] VALUES = values();
   private String[] names;
   private Material material;
   private StepSound stepSound;
   // $FF: synthetic field
   private static final JSONMaterial[] $VALUES = new JSONMaterial[]{GRASS, DIRT, METAL, WOOD, SAND, STONE, GLASS};


   private JSONMaterial(String var1, int var2, Material material, StepSound stepSound, String ... names) {
      this.material = material;
      this.stepSound = stepSound;
      this.names = names;
   }

   public Material getMaterial() {
      return this.material;
   }

   public StepSound getStepSound() {
      return this.stepSound;
   }

   public static JSONMaterial parseMaterial(String material) {
      JSONMaterial[] var1 = VALUES;
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         JSONMaterial value = var1[var3];
         String[] var5 = value.names;
         int var6 = var5.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            String name = var5[var7];
            if(name.equals(material)) {
               return value;
            }
         }
      }

      return STONE;
   }

}
