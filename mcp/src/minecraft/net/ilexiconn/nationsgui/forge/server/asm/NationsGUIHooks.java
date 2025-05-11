/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.client.registry.KeyBindingRegistry$KeyHandler
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  jds.bibliocraft.items.ItemClipboard
 *  net.minecraft.block.Block
 *  net.minecraft.client.settings.KeyBinding
 *  net.minecraft.enchantment.EnchantmentHelper
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.entity.player.EntityPlayerMP
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.network.INetworkManager
 *  net.minecraft.network.NetLoginHandler
 *  net.minecraft.network.packet.NetHandler
 *  net.minecraft.network.packet.Packet
 *  net.minecraft.network.packet.Packet250CustomPayload
 *  net.minecraft.network.packet.Packet28EntityVelocity
 *  net.minecraft.potion.PotionEffect
 *  net.minecraft.tileentity.TileEntityMobSpawner
 *  net.minecraft.tileentity.TileEntitySkull
 *  net.minecraft.util.MathHelper
 *  net.minecraft.util.Vec3
 *  net.minecraft.world.World
 *  net.minecraft.world.WorldServer
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.event.Event
 *  org.bukkit.Bukkit
 *  org.bukkit.plugin.Plugin
 */
package net.ilexiconn.nationsgui.forge.server.asm;

import cpw.mods.fml.client.registry.KeyBindingRegistry;
import cpw.mods.fml.common.network.PacketDispatcher;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import jds.bibliocraft.items.ItemClipboard;
import net.ilexiconn.nationsgui.forge.NationsGUI;
import net.ilexiconn.nationsgui.forge.server.ServerEventHandler;
import net.ilexiconn.nationsgui.forge.server.event.PotionEffectEvent;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.PacketSpawnParticle;
import net.minecraft.block.Block;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.NetLoginHandler;
import net.minecraft.network.packet.NetHandler;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.network.packet.Packet28EntityVelocity;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.Event;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public enum NationsGUIHooks {
    INSTANCE;

    public static int waitingSize;
    public static ArrayList<String> allowedIPConnection;
    public static ArrayList<Integer> blockWhitelist;
    private static DataOutputStream writer;

    public static int getPlayerRenderRadius(EntityPlayerMP entityPlayerMP) {
        try {
            Class.forName("org.bukkit.Bukkit");
            Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("NationsGUI");
            try {
                Method method = plugin.getClass().getMethod("getPlayerRenderDistance", String.class);
                return (Integer)method.invoke(plugin, entityPlayerMP.func_70005_c_());
            }
            catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                return 3;
            }
        }
        catch (ClassNotFoundException classNotFoundException) {
            return 3;
        }
    }

    public static void handleInventoryBibliocraft(Packet250CustomPayload packet, EntityPlayer player) {
        ItemStack stackostuff;
        DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(packet.field_73629_c));
        try {
            stackostuff = Packet.func_73276_c((DataInput)inputStream);
        }
        catch (IOException e) {
            e.printStackTrace();
            return;
        }
        if (stackostuff != null) {
            if (stackostuff.func_77973_b() instanceof ItemClipboard) {
                player.field_71071_by.field_70462_a[player.field_71071_by.field_70461_c] = stackostuff;
            } else {
                URL url = null;
                try {
                    url = new URL("https://apiv2.nationsglory.fr/mods/bad_packet_api?username=" + player.getDisplayName() + "&reason=Bibliocraft");
                    BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
                    reader.close();
                }
                catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void applyControlBlacklist(Set<KeyBindingRegistry.KeyHandler> keyHandlerSet, List<KeyBinding> keyBindingList) {
        for (KeyBindingRegistry.KeyHandler keyHandler : keyHandlerSet) {
            if (!NationsGUI.CONTROL_BLACKLIST.containsKey(keyHandler.getLabel())) continue;
            List<String> blacklist = NationsGUI.CONTROL_BLACKLIST.get(keyHandler.getLabel());
            for (KeyBinding keyBinding : keyHandler.getKeyBindings()) {
                if (!blacklist.contains(keyBinding.field_74515_c)) continue;
                keyBindingList.remove(keyBinding);
            }
        }
    }

    public ItemStack getPickBlock(Block block, World world, int x, int y, int z) {
        TileEntitySkull tileEntity;
        ItemStack itemStack = new ItemStack(Item.field_82799_bQ.field_77779_bT, 1, block.func_71873_h(world, x, y, z));
        if ((world.func_72805_g(x, y, z) & 8) == 0 && (tileEntity = (TileEntitySkull)world.func_72796_p(x, y, z)).func_82117_a() == 3 && tileEntity.func_82120_c() != null && tileEntity.func_82120_c().length() > 0) {
            itemStack.func_77982_d(new NBTTagCompound());
            itemStack.func_77978_p().func_74778_a("SkullOwner", tileEntity.func_82120_c());
        }
        return itemStack;
    }

    public static void knockBack(EntityLivingBase entityLivingBase, Entity damager, float par2, double par3, double par5) {
        entityLivingBase.field_70160_al = true;
        float f1 = MathHelper.func_76133_a((double)(par3 * par3 + par5 * par5));
        float f2 = 0.4f;
        Vec3 knockback = entityLivingBase.func_70666_h(1.0f).func_72444_a(((EntityLivingBase)damager).func_70666_h(1.0f)).func_72432_b();
        double sprintMultiplier = damager.func_70051_ag() ? 0.8 : 0.5;
        double kbEnchantMultiplier = (double)EnchantmentHelper.func_77506_a((int)19, (ItemStack)((EntityPlayer)damager).func_70694_bm()) * 0.2;
        double airMultiplier = entityLivingBase.field_70122_E ? 1.0 : 0.5;
        double motionX = knockback.field_72450_a * sprintMultiplier + kbEnchantMultiplier * (knockback.field_72450_a / Math.abs(knockback.field_72450_a));
        double motionZ = knockback.field_72449_c * sprintMultiplier + kbEnchantMultiplier * (knockback.field_72449_c / Math.abs(knockback.field_72449_c));
        double motionY = 0.35 * airMultiplier + kbEnchantMultiplier;
        entityLivingBase.field_70159_w = motionX;
        entityLivingBase.field_70181_x = motionY;
        entityLivingBase.field_70179_y = motionZ;
        if (entityLivingBase instanceof EntityPlayerMP) {
            EntityPlayerMP entityPlayerMP = (EntityPlayerMP)entityLivingBase;
            entityPlayerMP.field_71135_a.func_72567_b((Packet)new Packet28EntityVelocity(entityLivingBase.field_70157_k, motionX, motionY, motionZ));
        }
    }

    public int getPlayersInWaiting() {
        return waitingSize;
    }

    public int getSpawnerMeta(World world, int x, int y, int z) {
        TileEntityMobSpawner tileEntity = (TileEntityMobSpawner)world.func_72796_p(x, y, z);
        return ServerEventHandler.INSTANCE.nameToID.get(tileEntity.func_98049_a().func_98276_e());
    }

    public static void onNewPotionEffect(EntityLivingBase entity, PotionEffect effect) {
        MinecraftForge.EVENT_BUS.post((Event)new PotionEffectEvent.Start(entity, effect));
    }

    public static void onChangedPotionEffect(EntityLivingBase entity, PotionEffect effect) {
        MinecraftForge.EVENT_BUS.post((Event)new PotionEffectEvent.Change(entity, effect));
    }

    public static void onFinishedPotionEffect(EntityLivingBase entity, PotionEffect effect) {
        MinecraftForge.EVENT_BUS.post((Event)new PotionEffectEvent.Finish(entity, effect));
    }

    public static boolean isElectricityEnabled() {
        return true;
    }

    public static void sendPacketParticle(WorldServer server, String particleName, double x, double y, double z, double xd, double yd, double zd) {
        PacketDispatcher.sendPacketToAllAround((double)x, (double)y, (double)z, (double)128.0, (int)server.field_73011_w.field_76574_g, (Packet)PacketRegistry.INSTANCE.generatePacket(new PacketSpawnParticle(particleName, x, y, z, xd, yd, zd)));
    }

    public static boolean isBlocBypassCheckNormalCube(Block block) {
        if (blockWhitelist.isEmpty()) {
            blockWhitelist.add(89);
            blockWhitelist.add(2793);
            blockWhitelist.add(152);
            blockWhitelist.add(2832);
            blockWhitelist.add(18);
            blockWhitelist.add(2016);
            blockWhitelist.add(2017);
            blockWhitelist.add(2033);
            blockWhitelist.add(2077);
            blockWhitelist.add(46);
            blockWhitelist.add(566);
            blockWhitelist.add(3479);
            blockWhitelist.add(3570);
            blockWhitelist.add(3571);
            blockWhitelist.add(3572);
            blockWhitelist.add(3573);
            blockWhitelist.add(3574);
            blockWhitelist.add(3575);
            blockWhitelist.add(3576);
            blockWhitelist.add(3577);
            blockWhitelist.add(3578);
            blockWhitelist.add(3589);
            blockWhitelist.add(3324);
            blockWhitelist.add(3326);
            blockWhitelist.add(3327);
        }
        return blockWhitelist.contains(block.field_71990_ca);
    }

    public static DataOutputStream getNetworkLogs() throws IOException {
        if (writer == null) {
            writer = new DataOutputStream(Files.newOutputStream(Paths.get("networkDebug.rawlog", new String[0]), new OpenOption[0]));
        }
        return writer;
    }

    public static void networkDebug(Packet250CustomPayload packet, INetworkManager network, NetHandler handler) {
        if (handler instanceof NetLoginHandler) {
            try {
                DataOutputStream stream = NationsGUIHooks.getNetworkLogs();
                stream.write(66);
                stream.write(102);
                stream.writeLong(System.currentTimeMillis());
                stream.writeUTF(((NetLoginHandler)handler).field_72538_b.func_74430_c().toString());
                stream.writeUTF(packet.field_73630_a);
                if (packet.field_73629_c != null) {
                    stream.write(packet.field_73629_c);
                }
                stream.flush();
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    static {
        waitingSize = 0;
        allowedIPConnection = new ArrayList();
        blockWhitelist = new ArrayList();
        writer = null;
    }
}

