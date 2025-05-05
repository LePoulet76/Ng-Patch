package net.ilexiconn.nationsgui.forge.server.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Map.Entry;
import net.ilexiconn.nationsgui.forge.server.ServerEventHandler;
import net.ilexiconn.nationsgui.forge.server.asm.NationsGUITransformer;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.Icon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class MobSpawnerItem extends ItemBlock {

   private int lastX;
   private int lastY;
   private int lastZ;
   private static Map<Integer, EntityLiving> entityHashMap = new HashMap();


   public MobSpawnerItem() {
      super(3335);

      try {
         Field e = ItemBlock.class.getDeclaredField(NationsGUITransformer.inDevelopment?"blockID":"field_77885_a");
         e.setAccessible(true);
         e.set(this, Integer.valueOf(Block.field_72065_as.field_71990_ca));
      } catch (Exception var2) {
         throw new RuntimeException(var2);
      }

      this.func_77655_b("mob_spawner");
      this.func_111206_d("coal");
      this.func_77627_a(true);
      this.func_77625_d(64);
      this.func_77637_a((CreativeTabs)null);
   }

   public Icon func_77617_a(int par1) {
      return Block.field_72065_as.func_71851_a(0);
   }

   public boolean func_77648_a(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int meta, float hitX, float hitY, float hitZ) {
      if(super.func_77648_a(stack, player, world, x, y, z, meta, hitX, hitY, hitZ) && !world.field_72995_K) {
         TileEntityMobSpawner tileEntity = (TileEntityMobSpawner)world.func_72796_p(this.lastX, this.lastY, this.lastZ);
         if(tileEntity != null) {
            this.setDefaultTag(stack);
            String name = (String)ServerEventHandler.INSTANCE.idToName.get(Integer.valueOf(stack.func_77960_j()));
            if(name != null) {
               tileEntity.func_98049_a().func_98272_a(name);
            }
         }

         return true;
      } else {
         return false;
      }
   }

   @SideOnly(Side.CLIENT)
   public void func_77624_a(ItemStack stack, EntityPlayer player, List list, boolean advanced) {
      ServerEventHandler.INSTANCE.loadEntityIDs(player.field_70170_p);
      this.setDefaultTag(stack);
      int meta = stack.func_77960_j();
      if(meta == 0) {
         meta = getIDFromClass(EntityPig.class).intValue();
      }

      EntityLiving entity = getEntity(meta, player.field_70170_p);
      if(entity != null) {
         list.add("\u00a7" + (entity instanceof IMob?"4":"3") + (String)ServerEventHandler.INSTANCE.idToName.get(Integer.valueOf(meta)));
      }
   }

   public static EntityLiving getEntity(int id, World world) {
      EntityLiving entityliving = (EntityLiving)entityHashMap.get(Integer.valueOf(id));
      if(entityliving == null) {
         try {
            Class t = (Class)EntityList.field_75623_d.get(Integer.valueOf(id));
            if(t != null && EntityLiving.class.isAssignableFrom(t)) {
               entityliving = (EntityLiving)t.getConstructor(new Class[]{World.class}).newInstance(new Object[]{world});
            }
         } catch (Throwable var4) {
            var4.printStackTrace();
         }

         entityHashMap.put(Integer.valueOf(id), entityliving);
      }

      return entityliving;
   }

   private void setDefaultTag(ItemStack stack) {
      if(!ServerEventHandler.INSTANCE.idToName.containsKey(Integer.valueOf(stack.func_77960_j()))) {
         stack.func_77964_b(90);
      }

   }

   public String func_77635_s() {
      return StatCollector.func_74838_a("tile.mobSpawner.name");
   }

   public String func_77653_i(ItemStack stack) {
      return StatCollector.func_74838_a("tile.mobSpawner.name");
   }

   public String func_77628_j(ItemStack stack) {
      return StatCollector.func_74838_a("tile.mobSpawner.name");
   }

   public static Integer getIDFromClass(Class<EntityPig> value) {
      Iterator var1 = entityHashMap.entrySet().iterator();

      Entry entry;
      do {
         if(!var1.hasNext()) {
            return null;
         }

         entry = (Entry)var1.next();
      } while(!Objects.equals(value, entry.getValue()));

      return (Integer)entry.getKey();
   }

   public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int metadata) {
      if(!world.func_72832_d(x, y, z, Block.field_72065_as.field_71990_ca, metadata, 3)) {
         return false;
      } else {
         if(world.func_72798_a(x, y, z) == Block.field_72065_as.field_71990_ca) {
            Block.field_71973_m[Block.field_72065_as.field_71990_ca].func_71860_a(world, x, y, z, player, stack);
            Block.field_71973_m[Block.field_72065_as.field_71990_ca].func_85105_g(world, x, y, z, metadata);
            this.lastX = x;
            this.lastY = y;
            this.lastZ = z;
         }

         return true;
      }
   }

}
