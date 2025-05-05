package net.ilexiconn.nationsgui.forge.server.json.registry.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import net.ilexiconn.nationsgui.forge.server.json.registry.block.JSONDrop;
import net.ilexiconn.nationsgui.forge.server.json.registry.block.JSONMaterial;
import net.ilexiconn.nationsgui.forge.server.json.registry.block.JSONParticle;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class JSONBlock extends Block {

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
   public String textureFrontString;
   public String textureBackString;
   public String textureTopString;
   public String textureBottomString;
   public String textureSideString;
   public boolean isTranslucent;
   public List<JSONDrop> drops;
   public Map<String, List<JSONParticle>> particles = new HashMap();


   public JSONBlock(int id, JSONMaterial material) {
      super(id, material.getMaterial());
      this.func_111022_d("dirt");
      this.func_71849_a(CreativeTabs.field_78030_b);
      this.func_71884_a(material.getStepSound());
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

   }

   public boolean func_71926_d() {
      return false;
   }

   public boolean func_71886_c() {
      return this.isTranslucent;
   }

   @SideOnly(Side.CLIENT)
   public Icon func_71858_a(int side, int meta) {
      return side == 0 && this.textureBottom != null?this.textureBottom:(side == 1 && this.textureTop != null?this.textureTop:(side == meta && this.textureFront != null?this.textureFront:((side % 2 == 0 && side + 1 == meta || side % 2 == 1 && side - 1 == meta) && this.textureBack != null?this.textureBack:this.textureSide)));
   }

   public void func_71893_a(World world, EntityPlayer player, int x, int y, int z, int meta) {
      if(this.drops != null) {
         Collections.shuffle(this.drops);
         Iterator var7 = this.drops.iterator();

         while(var7.hasNext()) {
            JSONDrop drop = (JSONDrop)var7.next();
            if(drop.canDrop()) {
               drop.dropItem(player, world, x, y, z, this.field_71990_ca, this.drops.indexOf(drop));
               return;
            }
         }
      } else {
         super.func_71893_a(world, player, x, y, z, meta);
      }

   }

   @SideOnly(Side.CLIENT)
   public void func_71862_a(World world, int x, int y, int z, Random random) {
      List particles = (List)this.particles.get("tick");
      if(particles != null) {
         Iterator var7 = particles.iterator();

         while(var7.hasNext()) {
            JSONParticle particle = (JSONParticle)var7.next();
            particle.spawnParticle(world, x, y, z);
         }
      }

   }

   public void func_71898_d(World world, int x, int y, int z, int meta) {
      if(world.field_72995_K) {
         List particles = (List)this.particles.get("break");
         if(particles != null) {
            Iterator var7 = particles.iterator();

            while(var7.hasNext()) {
               JSONParticle particle = (JSONParticle)var7.next();
               particle.spawnParticle(world, x, y, z);
            }
         }
      }

   }
}
