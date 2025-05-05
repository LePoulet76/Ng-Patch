package net.ilexiconn.nationsgui.forge.server;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import fr.nationsglory.ngcontent.server.entity.EntityCarePackageWarzone;
import fr.nationsglory.ngupgrades.NGUpgrades;
import fr.nationsglory.ngupgrades.common.block.entity.GenericGeckoTileEntity;
import fr.nationsglory.ngupgrades.common.entity.CarePackageEntity;
import fr.nationsglory.ngupgrades.common.entity.EntityFloatingItem;
import fr.nationsglory.ngupgrades.common.entity.EntityMobKey;
import fr.nationsglory.ngupgrades.common.entity.GenericGeckoBikeFlyingEntity;
import fr.nationsglory.ngupgrades.common.entity.GenericGeckoBikeRidingEntity;
import fr.nationsglory.ngupgrades.common.entity.GenericGeckoEntity;
import fr.nationsglory.server.block.entity.GCFluidTankBlockEntity;
import fr.nationsglory.server.block.entity.GCSiloBlockEntity;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import micdoodle8.mods.galacticraft.edora.common.entity.GCEdoraEntityCreeperAutel;
import micdoodle8.mods.galacticraft.edora.common.entity.GCEdoraEntityPhantomHawk;
import micdoodle8.mods.galacticraft.edora.common.entity.GCEdoraEntityPhantomReaper;
import micdoodle8.mods.galacticraft.edora.common.entity.GCEdoraEntityVoriack;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.PlayerEventPointsPacket;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class ServerUtils {

   public static void spawnCarePackageWarzone(int x, int y, int z, String worldName) {
      WorldServer spawnWorld = null;
      WorldServer[] entityCarePackage = MinecraftServer.func_71276_C().field_71305_c;
      int var6 = entityCarePackage.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         WorldServer world = entityCarePackage[var7];
         if(world.func_72912_H().func_76065_j().equalsIgnoreCase(worldName)) {
            spawnWorld = world;
            break;
         }
      }

      if(spawnWorld != null) {
         EntityCarePackageWarzone var9 = new EntityCarePackageWarzone(spawnWorld, x, y, z);
         spawnWorld.func_72838_d(var9);
         MinecraftServer.func_71276_C().func_71244_g("FORGE DROP PACKAGE IN " + x + ", " + y + ", " + z);
      }

   }

   public static void spawnAssaultArea(String label, int xCenter, int yCenter, int zCenter, int radius) {
      NGUpgrades.addAssaultArea(label, xCenter, yCenter, zCenter, radius);
   }

   public static boolean isPackageInValidAssault(int positionX, int positionZ) {
      Plugin pl = Bukkit.getPluginManager().getPlugin("NationsBattle");
      if(pl != null) {
         try {
            Method e = pl.getClass().getDeclaredMethod("getAssaultAtPosition", new Class[]{Integer.TYPE, Integer.TYPE});
            return e.invoke(pl, new Object[]{Integer.valueOf(positionX), Integer.valueOf(positionZ)}) != null;
         } catch (InvocationTargetException var4) {
            var4.printStackTrace();
         }
      }

      return false;
   }

   public static void setCarePackageHealth(String entityUUID, int health) {
      WorldServer[] var2 = MinecraftServer.func_71276_C().field_71305_c;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         WorldServer world = var2[var4];
         Iterator var6 = world.field_72996_f.iterator();

         while(var6.hasNext()) {
            Object entity = var6.next();
            if(entity instanceof CarePackageEntity && ((CarePackageEntity)entity).func_110124_au().toString().equals(entityUUID)) {
               System.out.println("found care package to boost");
               double currentMaxHealth = ((CarePackageEntity)entity).func_110148_a(SharedMonsterAttributes.field_111267_a).func_111126_e();
               double diff = (double)health - currentMaxHealth;
               System.out.println("diff: " + diff + " currentMaxHealth: " + currentMaxHealth + " health: " + health);
               ((CarePackageEntity)entity).func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a((double)health);
               ((CarePackageEntity)entity).func_70606_j(((CarePackageEntity)entity).func_110143_aJ() + (float)diff);
            }
         }
      }

   }

   public static void withdrawCerealFromSilo(EntityPlayer entityPlayer, int posX, int posY, int posZ, int amount) {
      TileEntity tileEntity = entityPlayer.field_70170_p.func_72796_p(posX, posY, posZ);
      if(tileEntity instanceof GCSiloBlockEntity) {
         ((GCSiloBlockEntity)tileEntity).quantity = Math.max(0, ((GCSiloBlockEntity)tileEntity).quantity - amount);
         if(((GCSiloBlockEntity)tileEntity).quantity == 0) {
            ((GCSiloBlockEntity)tileEntity).cerealType = "";
         }
      }

   }

   public static boolean classIsChildOf(Class classe, String parentName) {
      while(true) {
         if((classe = classe.getSuperclass()) != null) {
            if(!classe.getName().equals(parentName)) {
               continue;
            }

            return true;
         }

         return false;
      }
   }

   public static String spawnGeckoEntityAt(int x, int y, int z, String worldName, String entityName, double maxHealth, boolean isStalking, boolean hideHealthBar) {
      System.out.println("SPAWN GECKO ENTITY AT " + x + ", " + y + ", " + z + " IN " + worldName);
      WorldServer world = null;
      WorldServer[] worldObj = MinecraftServer.func_71276_C().field_71305_c;
      int entity = worldObj.length;

      for(int var12 = 0; var12 < entity; ++var12) {
         WorldServer worldServer = worldObj[var12];
         if(worldServer.func_72912_H().func_76065_j().equals(worldName)) {
            world = worldServer;
         }
      }

      if(world != null && !world.field_72995_K) {
         World var14 = world.field_73011_w.field_76579_a;
         Object var15 = null;
         if(entityName.equalsIgnoreCase("EntityMobKey")) {
            var15 = new EntityMobKey(var14);
         } else {
            var15 = new GenericGeckoEntity(var14, entityName, maxHealth, isStalking, hideHealthBar);
         }

         ((Entity)var15).func_70107_b((double)x + 0.5D, (double)y, (double)z + 0.5D);
         var14.func_72838_d((Entity)var15);
         return ((Entity)var15).func_110124_au().toString();
      } else {
         return null;
      }
   }

   public static String spawnGeckoCarePackageAt(int x, int y, int z, String worldName, String entityName, double maxHealth) {
      System.out.println("SPAWN GECKO CARE PACKAGE AT " + x + ", " + y + ", " + z + " IN " + worldName);
      WorldServer world = null;
      WorldServer[] worldObj = MinecraftServer.func_71276_C().field_71305_c;
      int entity = worldObj.length;

      for(int var10 = 0; var10 < entity; ++var10) {
         WorldServer worldServer = worldObj[var10];
         if(worldServer.func_72912_H().func_76065_j().equals(worldName)) {
            world = worldServer;
         }
      }

      if(world != null && !world.field_72995_K) {
         World var12 = world.field_73011_w.field_76579_a;
         CarePackageEntity var13 = new CarePackageEntity(var12, entityName, maxHealth);
         var13.func_70107_b((double)x + 0.5D, (double)y, (double)z + 0.5D);
         var12.func_72838_d(var13);
         return var13.func_110124_au().toString();
      } else {
         return null;
      }
   }

   public static String spawnFloatingItemAt(int x, int y, int z, String worldName, int itemId, int itemMeta, float scale, float motionX, float motionY, float motionZ, boolean bounceAndRotate) {
      System.out.println("SPAWN FLOATING ITEM AT " + x + ", " + y + ", " + z + " IN " + worldName + ", ITEM ID: " + itemId + ", META: " + itemMeta + ", SCALE: " + scale + ", MOTION X: " + motionX + ", MOTION Y: " + motionY + ", MOTION Z: " + motionZ + ", BOUNCE AND ROTATE: " + bounceAndRotate);
      WorldServer world = null;
      WorldServer[] worldObj = MinecraftServer.func_71276_C().field_71305_c;
      int floatingItem = worldObj.length;

      for(int var14 = 0; var14 < floatingItem; ++var14) {
         WorldServer worldServer = worldObj[var14];
         if(worldServer.func_72912_H().func_76065_j().equals(worldName)) {
            world = worldServer;
         }
      }

      if(world != null && !world.field_72995_K) {
         World var16 = world.field_73011_w.field_76579_a;
         EntityFloatingItem var17 = new EntityFloatingItem(world, itemId, itemMeta, scale, motionX, motionY, motionZ, bounceAndRotate);
         var17.func_70107_b((double)x, (double)y, (double)z);
         world.func_72838_d(var17);
         return var17.func_110124_au().toString();
      } else {
         return null;
      }
   }

   public static void spawnGeckoBike(EntityPlayer entityPlayer, int x, int y, int z, String worldName, String entityName, boolean flying) {
      WorldServer world = null;
      WorldServer[] worldObj = MinecraftServer.func_71276_C().field_71305_c;
      int entity = worldObj.length;

      for(int var10 = 0; var10 < entity; ++var10) {
         WorldServer worldServer = worldObj[var10];
         if(worldServer.func_72912_H().func_76065_j().equals(worldName)) {
            world = worldServer;
         }
      }

      if(world != null && !world.field_72995_K) {
         World var12 = world.field_73011_w.field_76579_a;
         if(flying) {
            GenericGeckoBikeFlyingEntity var13 = new GenericGeckoBikeFlyingEntity(var12, entityName);
            var13.setOwner(entityPlayer.func_70023_ak());
            var13.func_70080_a((double)((float)x + 0.5F), (double)((float)y + 1.0F), (double)((float)z + 0.5F), entityPlayer.field_70177_z, 0.0F);
            var12.func_72838_d(var13);
            System.out.println("SPAWN GECKO BIKE FLYING AT " + x + ", " + y + ", " + z + " IN " + worldName);
         } else {
            GenericGeckoBikeRidingEntity var14 = new GenericGeckoBikeRidingEntity(var12, entityName);
            var14.setOwner(entityPlayer.func_70023_ak());
            var14.func_70080_a((double)((float)x + 0.5F), (double)((float)y + 1.0F), (double)((float)z + 0.5F), entityPlayer.field_70177_z, 0.0F);
            var12.func_72838_d(var14);
            System.out.println("SPAWN GECKO BIKE AT " + x + ", " + y + ", " + z + " IN " + worldName);
         }
      }

   }

   public static void spawnModdedEntity(String type, int x, int y, int z, String worldName) {
      WorldServer world = null;
      WorldServer[] worldObj = MinecraftServer.func_71276_C().field_71305_c;
      int entity = worldObj.length;

      for(int var8 = 0; var8 < entity; ++var8) {
         WorldServer worldServer = worldObj[var8];
         if(worldServer.func_72912_H().func_76065_j().equals(worldName)) {
            world = worldServer;
         }
      }

      if(world != null && !world.field_72995_K) {
         World var10 = world.field_73011_w.field_76579_a;
         Object var11 = null;
         if(type.equalsIgnoreCase("phantom_reaper")) {
            var11 = new GCEdoraEntityPhantomReaper(var10);
         } else if(type.equalsIgnoreCase("phantom_hawk")) {
            var11 = new GCEdoraEntityPhantomHawk(var10);
         } else if(type.equalsIgnoreCase("voriack")) {
            var11 = new GCEdoraEntityVoriack(var10);
         } else if(type.equalsIgnoreCase("creeper_autel")) {
            var11 = new GCEdoraEntityCreeperAutel(var10);
         }

         if(var11 == null) {
            return;
         }

         ((Entity)var11).func_70107_b((double)((float)x + 0.5F), (double)((float)y + 1.0F), (double)((float)z + 0.5F));
         var10.func_72838_d((Entity)var11);
         System.out.println("SPAWN MODDED ENTITY " + type + " AT " + x + ", " + y + ", " + z + " IN " + worldName);
      }

   }

   public static void addFactionResearchValue(String playerNameOrFactionNameOrFactionId, String researchName, Double value) {
      if(playerNameOrFactionNameOrFactionId != null && researchName != null && value != null) {
         Plugin pl = Bukkit.getPluginManager().getPlugin("NationsUtils");
         if(pl != null) {
            try {
               Method e = pl.getClass().getDeclaredMethod("addFactionResearchValue", new Class[]{String.class, String.class, Double.class});
               e.invoke(pl, new Object[]{playerNameOrFactionNameOrFactionId, researchName, value});
            } catch (InvocationTargetException var5) {
               var5.printStackTrace();
            }
         }

      }
   }

   public static int getCountryResearchLevel(String playerNameOrFactionNameOrFactionId, String domain) {
      try {
         Class.forName("org.bukkit.Bukkit");
         if(Bukkit.getServer() != null) {
            Plugin e = Bukkit.getPluginManager().getPlugin("NationsUtils");
            if(e != null) {
               try {
                  Method e1 = e.getClass().getDeclaredMethod("getCountryResearchLevel", new Class[]{String.class, String.class});
                  return ((Integer)e1.invoke(e, new Object[]{playerNameOrFactionNameOrFactionId, domain})).intValue();
               } catch (InvocationTargetException var4) {
                  var4.printStackTrace();
               }
            }
         }
      } catch (ClassNotFoundException var5) {
         var5.printStackTrace();
      }

      return 0;
   }

   public static String getFactionIdAtCoordonates(String worldName, int x, int y, int z) {
      try {
         Class.forName("org.bukkit.Bukkit");
         if(Bukkit.getServer() != null) {
            Plugin e = Bukkit.getPluginManager().getPlugin("NationsGUI");
            if(e != null) {
               try {
                  Method e1 = e.getClass().getDeclaredMethod("getFactionIdAtCoordonates", new Class[]{String.class, Integer.TYPE, Integer.TYPE, Integer.TYPE});
                  return (String)e1.invoke(e, new Object[]{worldName, Integer.valueOf(x), Integer.valueOf(y), Integer.valueOf(z)});
               } catch (Exception var6) {
                  var6.printStackTrace();
               }
            }
         }
      } catch (ClassNotFoundException var7) {
         var7.printStackTrace();
      }

      return null;
   }

   public static String getFactionHomeInWorld(String playerNameOrFactionNameOrFactionId, String worldName) {
      try {
         Class.forName("org.bukkit.Bukkit");
         if(Bukkit.getServer() != null) {
            Plugin e = Bukkit.getPluginManager().getPlugin("NationsGUI");
            if(e != null) {
               try {
                  Method e1 = e.getClass().getDeclaredMethod("getFactionHomeInWorld", new Class[]{String.class, String.class});
                  return (String)e1.invoke(e, new Object[]{playerNameOrFactionNameOrFactionId, worldName});
               } catch (Exception var4) {
                  var4.printStackTrace();
               }
            }
         }
      } catch (ClassNotFoundException var5) {
         var5.printStackTrace();
      }

      return null;
   }

   public static boolean getWorldguardFlagValue(String worldName, int posX, int posY, int posZ, String flagName) {
      try {
         Class.forName("org.bukkit.Bukkit");
         if(Bukkit.getServer() != null) {
            Plugin e = Bukkit.getPluginManager().getPlugin("NationsGUI");
            if(e != null) {
               try {
                  Method e1 = e.getClass().getDeclaredMethod("getWorldguardFlagValue", new Class[]{String.class, Integer.TYPE, Integer.TYPE, Integer.TYPE, String.class});
                  return ((Boolean)e1.invoke(e, new Object[]{worldName, Integer.valueOf(posX), Integer.valueOf(posY), Integer.valueOf(posZ), flagName})).booleanValue();
               } catch (Exception var7) {
                  var7.printStackTrace();
               }
            }
         }
      } catch (ClassNotFoundException var8) {
         var8.printStackTrace();
      }

      return false;
   }

   public static void savePlayerCollectEventPoint(String worldName, int posX, int posY, int posZ, String playerName, String eventPointName) {
      System.out.println("SAVE PLAYER COLLECT EVENT POINT " + playerName + " " + eventPointName + " AT " + posX + ", " + posY + ", " + posZ + " IN " + worldName);

      try {
         Class.forName("org.bukkit.Bukkit");
         if(Bukkit.getServer() != null) {
            Plugin e = Bukkit.getPluginManager().getPlugin("NationsUtils");
            if(e != null) {
               try {
                  Method e1 = e.getClass().getDeclaredMethod("savePlayerCollectEventPoint", new Class[]{String.class, Integer.TYPE, Integer.TYPE, Integer.TYPE, String.class, String.class});
                  e1.invoke(e, new Object[]{worldName, Integer.valueOf(posX), Integer.valueOf(posY), Integer.valueOf(posZ), playerName, eventPointName});
                  EntityPlayerMP entityPlayerMP = MinecraftServer.func_71276_C().func_71203_ab().func_72361_f(playerName);
                  if(entityPlayerMP != null) {
                     ArrayList list = new ArrayList(Arrays.asList(new String[]{posX + "#" + posY + "#" + posZ}));
                     PacketDispatcher.sendPacketToPlayer(PacketRegistry.INSTANCE.generatePacket(new PlayerEventPointsPacket(list)), (Player)entityPlayerMP);
                  }
               } catch (Exception var10) {
                  var10.printStackTrace();
               }
            }
         }
      } catch (ClassNotFoundException var11) {
         var11.printStackTrace();
      }

   }

   public static void processVoriackDeath() {
      try {
         Class.forName("org.bukkit.Bukkit");
         if(Bukkit.getServer() != null) {
            Plugin e = Bukkit.getPluginManager().getPlugin("NationsUtils");
            if(e != null) {
               try {
                  Method e1 = e.getClass().getDeclaredMethod("processVoriackDeath", new Class[0]);
                  e1.invoke(e, new Object[0]);
               } catch (Exception var2) {
                  var2.printStackTrace();
               }
            }
         }
      } catch (Exception var3) {
         System.out.println(var3.getMessage());
      }

   }

   public static boolean isValidWorldDimension(int dimensionId) {
      WorldServer world = MinecraftServer.func_71276_C().func_71218_a(dimensionId);
      return world != null;
   }

   public static int getWorldDimensionId(String worldName) {
      WorldServer[] var1 = MinecraftServer.func_71276_C().field_71305_c;
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         WorldServer world = var1[var3];
         if(world.func_72912_H().func_76065_j().equalsIgnoreCase(worldName)) {
            return world.field_73011_w.field_76574_g;
         }
      }

      return 0;
   }

   public static void changeMegaGiftAnimation(String animation, int dimensionId, int blockX, int blockY, int blockZ) {
      WorldServer world = MinecraftServer.func_71276_C().func_71218_a(dimensionId);
      TileEntity tileEntity = world.func_72796_p(blockX, blockY, blockZ);
      if(tileEntity instanceof GenericGeckoTileEntity) {
         GenericGeckoTileEntity geckoTileEntity = (GenericGeckoTileEntity)tileEntity;
         geckoTileEntity.currentAnimation = animation;
         System.out.println("debug change mega gift animatino in serverutils");
      }

   }

   public static boolean playerCanShoot(EntityPlayer entityPlayer) {
      Boolean canUseGun = Boolean.valueOf(true);

      try {
         Plugin e = Bukkit.getPluginManager().getPlugin("NationsGUI");
         if(e != null) {
            try {
               Method e1 = e.getClass().getDeclaredMethod("userCanShoot", new Class[]{String.class});
               canUseGun = (Boolean)e1.invoke(e, new Object[]{entityPlayer.field_71092_bJ});
            } catch (Exception var4) {
               var4.printStackTrace();
            }
         }
      } catch (Exception var5) {
         System.out.println(var5.getMessage());
      }

      return canUseGun.booleanValue();
   }

   public static boolean playerCanShootLaseSpartan(EntityPlayer entityPlayer) {
      Boolean canUseGun = Boolean.valueOf(false);

      try {
         Plugin e = Bukkit.getPluginManager().getPlugin("NationsGUI");
         if(e != null) {
            try {
               Method e1 = e.getClass().getDeclaredMethod("playerCanShootLaseSpartan", new Class[]{String.class});
               canUseGun = (Boolean)e1.invoke(e, new Object[]{entityPlayer.field_71092_bJ});
            } catch (Exception var4) {
               var4.printStackTrace();
            }
         }
      } catch (Exception var5) {
         System.out.println(var5.getMessage());
      }

      return canUseGun.booleanValue();
   }

   public static float getFuelQuantityFluidTank(String worldName, int posX, int posY, int posZ) {
      WorldServer world = null;
      WorldServer[] tileEntity = MinecraftServer.func_71276_C().field_71305_c;
      int var6 = tileEntity.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         WorldServer existingWorld = tileEntity[var7];
         if(existingWorld.func_72912_H().func_76065_j().equalsIgnoreCase(worldName)) {
            world = existingWorld;
            break;
         }
      }

      if(world != null) {
         TileEntity var9 = world.func_72796_p(posX, posY, posZ);
         if(var9 instanceof GCFluidTankBlockEntity) {
            return ((GCFluidTankBlockEntity)var9).getFuelStored();
         }
      }

      return 0.0F;
   }
}
