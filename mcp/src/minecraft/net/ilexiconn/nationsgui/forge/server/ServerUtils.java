/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  cpw.mods.fml.common.network.Player
 *  fr.nationsglory.ngcontent.server.entity.EntityCarePackageWarzone
 *  fr.nationsglory.ngupgrades.NGUpgrades
 *  fr.nationsglory.ngupgrades.common.block.entity.GenericGeckoTileEntity
 *  fr.nationsglory.ngupgrades.common.entity.CarePackageEntity
 *  fr.nationsglory.ngupgrades.common.entity.EntityFloatingItem
 *  fr.nationsglory.ngupgrades.common.entity.EntityMobKey
 *  fr.nationsglory.ngupgrades.common.entity.GenericGeckoBikeFlyingEntity
 *  fr.nationsglory.ngupgrades.common.entity.GenericGeckoBikeRidingEntity
 *  fr.nationsglory.ngupgrades.common.entity.GenericGeckoEntity
 *  fr.nationsglory.server.block.entity.GCFluidTankBlockEntity
 *  fr.nationsglory.server.block.entity.GCSiloBlockEntity
 *  micdoodle8.mods.galacticraft.edora.common.entity.GCEdoraEntityCreeperAutel
 *  micdoodle8.mods.galacticraft.edora.common.entity.GCEdoraEntityPhantomHawk
 *  micdoodle8.mods.galacticraft.edora.common.entity.GCEdoraEntityPhantomReaper
 *  micdoodle8.mods.galacticraft.edora.common.entity.GCEdoraEntityVoriack
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.SharedMonsterAttributes
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.entity.player.EntityPlayerMP
 *  net.minecraft.network.packet.Packet
 *  net.minecraft.server.MinecraftServer
 *  net.minecraft.tileentity.TileEntity
 *  net.minecraft.world.World
 *  net.minecraft.world.WorldServer
 *  org.bukkit.Bukkit
 *  org.bukkit.plugin.Plugin
 */
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
import net.minecraft.network.packet.Packet;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class ServerUtils {
    public static void spawnCarePackageWarzone(int x, int y, int z, String worldName) {
        WorldServer spawnWorld = null;
        for (WorldServer world : MinecraftServer.func_71276_C().field_71305_c) {
            if (!world.func_72912_H().func_76065_j().equalsIgnoreCase(worldName)) continue;
            spawnWorld = world;
            break;
        }
        if (spawnWorld != null) {
            EntityCarePackageWarzone entityCarePackage = new EntityCarePackageWarzone(spawnWorld, x, y, z);
            spawnWorld.func_72838_d((Entity)entityCarePackage);
            MinecraftServer.func_71276_C().func_71244_g("FORGE DROP PACKAGE IN " + x + ", " + y + ", " + z);
        }
    }

    public static void spawnAssaultArea(String label, int xCenter, int yCenter, int zCenter, int radius) {
        NGUpgrades.addAssaultArea((String)label, (int)xCenter, (int)yCenter, (int)zCenter, (int)radius);
    }

    public static boolean isPackageInValidAssault(int positionX, int positionZ) {
        Plugin pl = Bukkit.getPluginManager().getPlugin("NationsBattle");
        if (pl != null) {
            try {
                Method m = pl.getClass().getDeclaredMethod("getAssaultAtPosition", Integer.TYPE, Integer.TYPE);
                return m.invoke(pl, positionX, positionZ) != null;
            }
            catch (IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static void setCarePackageHealth(String entityUUID, int health) {
        for (WorldServer world : MinecraftServer.func_71276_C().field_71305_c) {
            for (Object entity : world.field_72996_f) {
                if (!(entity instanceof CarePackageEntity) || !((CarePackageEntity)entity).func_110124_au().toString().equals(entityUUID)) continue;
                System.out.println("found care package to boost");
                double currentMaxHealth = ((CarePackageEntity)entity).func_110148_a(SharedMonsterAttributes.field_111267_a).func_111126_e();
                double diff = (double)health - currentMaxHealth;
                System.out.println("diff: " + diff + " currentMaxHealth: " + currentMaxHealth + " health: " + health);
                ((CarePackageEntity)entity).func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a((double)health);
                ((CarePackageEntity)entity).func_70606_j(((CarePackageEntity)entity).func_110143_aJ() + (float)diff);
            }
        }
    }

    public static void withdrawCerealFromSilo(EntityPlayer entityPlayer, int posX, int posY, int posZ, int amount) {
        TileEntity tileEntity = entityPlayer.field_70170_p.func_72796_p(posX, posY, posZ);
        if (tileEntity instanceof GCSiloBlockEntity) {
            ((GCSiloBlockEntity)tileEntity).quantity = Math.max(0, ((GCSiloBlockEntity)tileEntity).quantity - amount);
            if (((GCSiloBlockEntity)tileEntity).quantity == 0) {
                ((GCSiloBlockEntity)tileEntity).cerealType = "";
            }
        }
    }

    public static boolean classIsChildOf(Class classe, String parentName) {
        while ((classe = classe.getSuperclass()) != null) {
            if (!classe.getName().equals(parentName)) continue;
            return true;
        }
        return false;
    }

    public static String spawnGeckoEntityAt(int x, int y, int z, String worldName, String entityName, double maxHealth, boolean isStalking, boolean hideHealthBar) {
        System.out.println("SPAWN GECKO ENTITY AT " + x + ", " + y + ", " + z + " IN " + worldName);
        WorldServer world = null;
        for (WorldServer worldServer : MinecraftServer.func_71276_C().field_71305_c) {
            if (!worldServer.func_72912_H().func_76065_j().equals(worldName)) continue;
            world = worldServer;
        }
        if (world != null && !world.field_72995_K) {
            World worldObj = world.field_73011_w.field_76579_a;
            Object entity = null;
            entity = entityName.equalsIgnoreCase("EntityMobKey") ? new EntityMobKey(worldObj) : new GenericGeckoEntity(worldObj, entityName, maxHealth, isStalking, hideHealthBar);
            entity.func_70107_b((double)x + 0.5, (double)y, (double)z + 0.5);
            worldObj.func_72838_d((Entity)entity);
            return entity.func_110124_au().toString();
        }
        return null;
    }

    public static String spawnGeckoCarePackageAt(int x, int y, int z, String worldName, String entityName, double maxHealth) {
        System.out.println("SPAWN GECKO CARE PACKAGE AT " + x + ", " + y + ", " + z + " IN " + worldName);
        WorldServer world = null;
        for (WorldServer worldServer : MinecraftServer.func_71276_C().field_71305_c) {
            if (!worldServer.func_72912_H().func_76065_j().equals(worldName)) continue;
            world = worldServer;
        }
        if (world != null && !world.field_72995_K) {
            World worldObj = world.field_73011_w.field_76579_a;
            CarePackageEntity entity = new CarePackageEntity(worldObj, entityName, maxHealth);
            entity.func_70107_b((double)x + 0.5, (double)y, (double)z + 0.5);
            worldObj.func_72838_d((Entity)entity);
            return entity.func_110124_au().toString();
        }
        return null;
    }

    public static String spawnFloatingItemAt(int x, int y, int z, String worldName, int itemId, int itemMeta, float scale, float motionX, float motionY, float motionZ, boolean bounceAndRotate) {
        System.out.println("SPAWN FLOATING ITEM AT " + x + ", " + y + ", " + z + " IN " + worldName + ", ITEM ID: " + itemId + ", META: " + itemMeta + ", SCALE: " + scale + ", MOTION X: " + motionX + ", MOTION Y: " + motionY + ", MOTION Z: " + motionZ + ", BOUNCE AND ROTATE: " + bounceAndRotate);
        WorldServer world = null;
        for (WorldServer worldServer : MinecraftServer.func_71276_C().field_71305_c) {
            if (!worldServer.func_72912_H().func_76065_j().equals(worldName)) continue;
            world = worldServer;
        }
        if (world != null && !world.field_72995_K) {
            World worldObj = world.field_73011_w.field_76579_a;
            EntityFloatingItem floatingItem = new EntityFloatingItem((World)world, itemId, itemMeta, scale, motionX, motionY, motionZ, bounceAndRotate);
            floatingItem.func_70107_b((double)x, (double)y, (double)z);
            world.func_72838_d((Entity)floatingItem);
            return floatingItem.func_110124_au().toString();
        }
        return null;
    }

    public static void spawnGeckoBike(EntityPlayer entityPlayer, int x, int y, int z, String worldName, String entityName, boolean flying) {
        WorldServer world = null;
        for (WorldServer worldServer : MinecraftServer.func_71276_C().field_71305_c) {
            if (!worldServer.func_72912_H().func_76065_j().equals(worldName)) continue;
            world = worldServer;
        }
        if (world != null && !world.field_72995_K) {
            World worldObj = world.field_73011_w.field_76579_a;
            if (flying) {
                GenericGeckoBikeFlyingEntity entity = new GenericGeckoBikeFlyingEntity(worldObj, entityName);
                entity.setOwner(entityPlayer.func_70023_ak());
                entity.func_70080_a((double)((float)x + 0.5f), (double)((float)y + 1.0f), (double)((float)z + 0.5f), entityPlayer.field_70177_z, 0.0f);
                worldObj.func_72838_d((Entity)entity);
                System.out.println("SPAWN GECKO BIKE FLYING AT " + x + ", " + y + ", " + z + " IN " + worldName);
            } else {
                GenericGeckoBikeRidingEntity entity = new GenericGeckoBikeRidingEntity(worldObj, entityName);
                entity.setOwner(entityPlayer.func_70023_ak());
                entity.func_70080_a((double)((float)x + 0.5f), (double)((float)y + 1.0f), (double)((float)z + 0.5f), entityPlayer.field_70177_z, 0.0f);
                worldObj.func_72838_d((Entity)entity);
                System.out.println("SPAWN GECKO BIKE AT " + x + ", " + y + ", " + z + " IN " + worldName);
            }
        }
    }

    public static void spawnModdedEntity(String type, int x, int y, int z, String worldName) {
        WorldServer world = null;
        for (WorldServer worldServer : MinecraftServer.func_71276_C().field_71305_c) {
            if (!worldServer.func_72912_H().func_76065_j().equals(worldName)) continue;
            world = worldServer;
        }
        if (world != null && !world.field_72995_K) {
            World worldObj = world.field_73011_w.field_76579_a;
            GCEdoraEntityPhantomReaper entity = null;
            if (type.equalsIgnoreCase("phantom_reaper")) {
                entity = new GCEdoraEntityPhantomReaper(worldObj);
            } else if (type.equalsIgnoreCase("phantom_hawk")) {
                entity = new GCEdoraEntityPhantomHawk(worldObj);
            } else if (type.equalsIgnoreCase("voriack")) {
                entity = new GCEdoraEntityVoriack(worldObj);
            } else if (type.equalsIgnoreCase("creeper_autel")) {
                entity = new GCEdoraEntityCreeperAutel(worldObj);
            }
            if (entity == null) {
                return;
            }
            entity.func_70107_b((double)((float)x + 0.5f), (double)((float)y + 1.0f), (double)((float)z + 0.5f));
            worldObj.func_72838_d((Entity)entity);
            System.out.println("SPAWN MODDED ENTITY " + type + " AT " + x + ", " + y + ", " + z + " IN " + worldName);
        }
    }

    public static void addFactionResearchValue(String playerNameOrFactionNameOrFactionId, String researchName, Double value) {
        if (playerNameOrFactionNameOrFactionId == null || researchName == null || value == null) {
            return;
        }
        Plugin pl = Bukkit.getPluginManager().getPlugin("NationsUtils");
        if (pl != null) {
            try {
                Method m = pl.getClass().getDeclaredMethod("addFactionResearchValue", String.class, String.class, Double.class);
                m.invoke(pl, playerNameOrFactionNameOrFactionId, researchName, value);
            }
            catch (IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    public static int getCountryResearchLevel(String playerNameOrFactionNameOrFactionId, String domain) {
        try {
            Plugin pl;
            Class.forName("org.bukkit.Bukkit");
            if (Bukkit.getServer() != null && (pl = Bukkit.getPluginManager().getPlugin("NationsUtils")) != null) {
                try {
                    Method m = pl.getClass().getDeclaredMethod("getCountryResearchLevel", String.class, String.class);
                    return (Integer)m.invoke(pl, playerNameOrFactionNameOrFactionId, domain);
                }
                catch (IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String getFactionIdAtCoordonates(String worldName, int x, int y, int z) {
        try {
            Plugin pl;
            Class.forName("org.bukkit.Bukkit");
            if (Bukkit.getServer() != null && (pl = Bukkit.getPluginManager().getPlugin("NationsGUI")) != null) {
                try {
                    Method m = pl.getClass().getDeclaredMethod("getFactionIdAtCoordonates", String.class, Integer.TYPE, Integer.TYPE, Integer.TYPE);
                    return (String)m.invoke(pl, worldName, x, y, z);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getFactionHomeInWorld(String playerNameOrFactionNameOrFactionId, String worldName) {
        try {
            Plugin pl;
            Class.forName("org.bukkit.Bukkit");
            if (Bukkit.getServer() != null && (pl = Bukkit.getPluginManager().getPlugin("NationsGUI")) != null) {
                try {
                    Method m = pl.getClass().getDeclaredMethod("getFactionHomeInWorld", String.class, String.class);
                    return (String)m.invoke(pl, playerNameOrFactionNameOrFactionId, worldName);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean getWorldguardFlagValue(String worldName, int posX, int posY, int posZ, String flagName) {
        try {
            Plugin pl;
            Class.forName("org.bukkit.Bukkit");
            if (Bukkit.getServer() != null && (pl = Bukkit.getPluginManager().getPlugin("NationsGUI")) != null) {
                try {
                    Method m = pl.getClass().getDeclaredMethod("getWorldguardFlagValue", String.class, Integer.TYPE, Integer.TYPE, Integer.TYPE, String.class);
                    return (Boolean)m.invoke(pl, worldName, posX, posY, posZ, flagName);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void savePlayerCollectEventPoint(String worldName, int posX, int posY, int posZ, String playerName, String eventPointName) {
        block5: {
            System.out.println("SAVE PLAYER COLLECT EVENT POINT " + playerName + " " + eventPointName + " AT " + posX + ", " + posY + ", " + posZ + " IN " + worldName);
            try {
                Plugin pl;
                Class.forName("org.bukkit.Bukkit");
                if (Bukkit.getServer() == null || (pl = Bukkit.getPluginManager().getPlugin("NationsUtils")) == null) break block5;
                try {
                    Method m = pl.getClass().getDeclaredMethod("savePlayerCollectEventPoint", String.class, Integer.TYPE, Integer.TYPE, Integer.TYPE, String.class, String.class);
                    m.invoke(pl, worldName, posX, posY, posZ, playerName, eventPointName);
                    EntityPlayerMP entityPlayerMP = MinecraftServer.func_71276_C().func_71203_ab().func_72361_f(playerName);
                    if (entityPlayerMP != null) {
                        ArrayList<String> list = new ArrayList<String>(Arrays.asList(posX + "#" + posY + "#" + posZ));
                        PacketDispatcher.sendPacketToPlayer((Packet)PacketRegistry.INSTANCE.generatePacket(new PlayerEventPointsPacket(list)), (Player)((Player)entityPlayerMP));
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
            catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public static void processVoriackDeath() {
        try {
            Plugin plugin;
            Class.forName("org.bukkit.Bukkit");
            if (Bukkit.getServer() != null && (plugin = Bukkit.getPluginManager().getPlugin("NationsUtils")) != null) {
                try {
                    Method m = plugin.getClass().getDeclaredMethod("processVoriackDeath", new Class[0]);
                    m.invoke(plugin, new Object[0]);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static boolean isValidWorldDimension(int dimensionId) {
        WorldServer world = MinecraftServer.func_71276_C().func_71218_a(dimensionId);
        return world != null;
    }

    public static int getWorldDimensionId(String worldName) {
        for (WorldServer world : MinecraftServer.func_71276_C().field_71305_c) {
            if (!world.func_72912_H().func_76065_j().equalsIgnoreCase(worldName)) continue;
            return world.field_73011_w.field_76574_g;
        }
        return 0;
    }

    public static void changeMegaGiftAnimation(String animation, int dimensionId, int blockX, int blockY, int blockZ) {
        WorldServer world = MinecraftServer.func_71276_C().func_71218_a(dimensionId);
        TileEntity tileEntity = world.func_72796_p(blockX, blockY, blockZ);
        if (tileEntity instanceof GenericGeckoTileEntity) {
            GenericGeckoTileEntity geckoTileEntity = (GenericGeckoTileEntity)tileEntity;
            geckoTileEntity.currentAnimation = animation;
            System.out.println("debug change mega gift animatino in serverutils");
        }
    }

    public static boolean playerCanShoot(EntityPlayer entityPlayer) {
        Boolean canUseGun = true;
        try {
            Plugin plugin = Bukkit.getPluginManager().getPlugin("NationsGUI");
            if (plugin != null) {
                try {
                    Method m = plugin.getClass().getDeclaredMethod("userCanShoot", String.class);
                    canUseGun = (Boolean)m.invoke(plugin, entityPlayer.field_71092_bJ);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return canUseGun;
    }

    public static boolean playerCanShootLaseSpartan(EntityPlayer entityPlayer) {
        Boolean canUseGun = false;
        try {
            Plugin plugin = Bukkit.getPluginManager().getPlugin("NationsGUI");
            if (plugin != null) {
                try {
                    Method m = plugin.getClass().getDeclaredMethod("playerCanShootLaseSpartan", String.class);
                    canUseGun = (Boolean)m.invoke(plugin, entityPlayer.field_71092_bJ);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return canUseGun;
    }

    public static float getFuelQuantityFluidTank(String worldName, int posX, int posY, int posZ) {
        TileEntity tileEntity;
        WorldServer world = null;
        for (WorldServer existingWorld : MinecraftServer.func_71276_C().field_71305_c) {
            if (!existingWorld.func_72912_H().func_76065_j().equalsIgnoreCase(worldName)) continue;
            world = existingWorld;
            break;
        }
        if (world != null && (tileEntity = world.func_72796_p(posX, posY, posZ)) instanceof GCFluidTankBlockEntity) {
            return ((GCFluidTankBlockEntity)tileEntity).getFuelStored();
        }
        return 0.0f;
    }
}

