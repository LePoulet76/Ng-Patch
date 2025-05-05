package net.ilexiconn.nationsgui.forge.server.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Random;
import net.ilexiconn.nationsgui.forge.NationsGUI;
import net.ilexiconn.nationsgui.forge.client.particle.RadioParticle;
import net.ilexiconn.nationsgui.forge.server.block.RadioBlock$1;
import net.ilexiconn.nationsgui.forge.server.block.entity.RadioBlockEntity;
import net.ilexiconn.nationsgui.forge.server.permission.PermissionCache;
import net.ilexiconn.nationsgui.forge.server.permission.PermissionType;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class RadioBlock extends BlockContainer {

   public RadioBlock() {
      super(646, Material.field_76243_f);
      this.func_71864_b("radio");
      this.func_71849_a(CreativeTabs.field_78030_b);
      this.func_71848_c(2.0F);
      this.func_71894_b(2.0F);
      this.func_111022_d("wool_colored_orange");
      this.func_71905_a(0.2F, 0.0F, 0.2F, 0.8F, 0.6F, 0.8F);
   }

   public void func_71860_a(World world, int x, int y, int z, EntityLivingBase entity, ItemStack itemStack) {
      world.func_72921_c(x, y, z, MathHelper.func_76128_c((double)(entity.field_70177_z * 16.0F / 360.0F) + 0.5D) & 15, 2);
   }

   public boolean func_71903_a(World world, int x, int y, int z, EntityPlayer player, int meta, float hitX, float hitY, float hitZ) {
      if(player.func_70093_af()) {
         return false;
      } else {
         if(world.field_72995_K) {
            RadioBlockEntity blockEntity = (RadioBlockEntity)world.func_72796_p(x, y, z);
            if(blockEntity.canOpen) {
               player.openGui(NationsGUI.INSTANCE, 888, world, x, y, z);
            } else {
               PermissionCache.INSTANCE.checkPermission(PermissionType.RADIO_MODERATION, new RadioBlock$1(this, player, world, x, y, z), new String[0]);
            }
         }

         return true;
      }
   }

   public int func_71857_b() {
      return -1;
   }

   public boolean func_71926_d() {
      return false;
   }

   public boolean func_71886_c() {
      return false;
   }

   @SideOnly(Side.CLIENT)
   public void func_71862_a(World world, int x, int y, int z, Random random) {
      RadioBlockEntity blockEntity = (RadioBlockEntity)world.func_72796_p(x, y, z);
      if(blockEntity != null && (float)blockEntity.volume > 0.0F && blockEntity.streamer != null && blockEntity.streamer.isPlaying() && (!blockEntity.needsRedstone || world.func_94572_D(x, y, z) > 0)) {
         Minecraft.func_71410_x().field_71452_i.func_78873_a(new RadioParticle(world, (double)((float)x + ((float)random.nextInt(75) + 1.0F) / 100.0F), (double)((float)y + ((float)random.nextInt(100) + 1.0F) / 100.0F), (double)((float)z + ((float)random.nextInt(100) + 1.0F) / 100.0F), (float)blockEntity.volume / 100.0F, random));
      }

   }

   public TileEntity func_72274_a(World world) {
      return new RadioBlockEntity();
   }
}
