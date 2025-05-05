package net.ilexiconn.nationsgui.forge.server.asm;

import cpw.mods.fml.client.registry.KeyBindingRegistry.KeyHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
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
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import jds.bibliocraft.items.ItemClipboard;
import net.ilexiconn.nationsgui.forge.NationsGUI;
import net.ilexiconn.nationsgui.forge.server.ServerEventHandler;
import net.ilexiconn.nationsgui.forge.server.event.PotionEffectEvent$Change;
import net.ilexiconn.nationsgui.forge.server.event.PotionEffectEvent$Finish;
import net.ilexiconn.nationsgui.forge.server.event.PotionEffectEvent$Start;
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
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public enum NationsGUIHooks
{
    INSTANCE;
    public static int waitingSize = 0;
    public static ArrayList<String> allowedIPConnection = new ArrayList();
    public static ArrayList<Integer> blockWhitelist = new ArrayList();
    private static DataOutputStream writer = null;

    public static int getPlayerRenderRadius(EntityPlayerMP entityPlayerMP)
    {
        try
        {
            Class.forName("org.bukkit.Bukkit");
            Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("NationsGUI");

            try
            {
                Method e = plugin.getClass().getMethod("getPlayerRenderDistance", new Class[] {String.class});
                return ((Integer)e.invoke(plugin, new Object[] {entityPlayerMP.getCommandSenderName()})).intValue();
            }
            catch (InvocationTargetException var3)
            {
                return 3;
            }
        }
        catch (ClassNotFoundException var4)
        {
            return 3;
        }
    }

    public static void handleInventoryBibliocraft(Packet250CustomPayload packet, EntityPlayer player)
    {
        DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(packet.data));
        ItemStack stackostuff;

        try
        {
            stackostuff = Packet.readItemStack(inputStream);
        }
        catch (IOException var9)
        {
            var9.printStackTrace();
            return;
        }

        if (stackostuff != null)
        {
            if (stackostuff.getItem() instanceof ItemClipboard)
            {
                player.inventory.mainInventory[player.inventory.currentItem] = stackostuff;
            }
            else
            {
                URL url = null;

                try
                {
                    url = new URL("https://apiv2.nationsglory.fr/mods/bad_packet_api?username=" + player.getDisplayName() + "&reason=Bibliocraft");
                    BufferedReader e = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
                    e.close();
                }
                catch (MalformedURLException var6)
                {
                    var6.printStackTrace();
                }
                catch (UnsupportedEncodingException var7)
                {
                    var7.printStackTrace();
                }
                catch (IOException var8)
                {
                    var8.printStackTrace();
                }
            }
        }
    }

    public void applyControlBlacklist(Set<KeyHandler> keyHandlerSet, List<KeyBinding> keyBindingList)
    {
        Iterator var3 = keyHandlerSet.iterator();

        while (var3.hasNext())
        {
            KeyHandler keyHandler = (KeyHandler)var3.next();

            if (NationsGUI.CONTROL_BLACKLIST.containsKey(keyHandler.getLabel()))
            {
                List blacklist = (List)NationsGUI.CONTROL_BLACKLIST.get(keyHandler.getLabel());
                KeyBinding[] var6 = keyHandler.getKeyBindings();
                int var7 = var6.length;

                for (int var8 = 0; var8 < var7; ++var8)
                {
                    KeyBinding keyBinding = var6[var8];

                    if (blacklist.contains(keyBinding.keyDescription))
                    {
                        keyBindingList.remove(keyBinding);
                    }
                }
            }
        }
    }

    public ItemStack getPickBlock(Block block, World world, int x, int y, int z)
    {
        ItemStack itemStack = new ItemStack(Item.skull.itemID, 1, block.getDamageValue(world, x, y, z));

        if ((world.getBlockMetadata(x, y, z) & 8) == 0)
        {
            TileEntitySkull tileEntity = (TileEntitySkull)world.getBlockTileEntity(x, y, z);

            if (tileEntity.getSkullType() == 3 && tileEntity.getExtraType() != null && tileEntity.getExtraType().length() > 0)
            {
                itemStack.setTagCompound(new NBTTagCompound());
                itemStack.getTagCompound().setString("SkullOwner", tileEntity.getExtraType());
            }
        }

        return itemStack;
    }

    public static void knockBack(EntityLivingBase entityLivingBase, Entity damager, float par2, double par3, double par5)
    {
        entityLivingBase.isAirBorne = true;
        MathHelper.sqrt_double(par3 * par3 + par5 * par5);
        float f2 = 0.4F;
        Vec3 knockback = entityLivingBase.getPosition(1.0F).subtract(((EntityLivingBase)damager).getPosition(1.0F)).normalize();
        double sprintMultiplier = damager.isSprinting() ? 0.8D : 0.5D;
        double kbEnchantMultiplier = (double)EnchantmentHelper.getEnchantmentLevel(19, ((EntityPlayer)damager).getHeldItem()) * 0.2D;
        double airMultiplier = entityLivingBase.onGround ? 1.0D : 0.5D;
        double motionX = knockback.xCoord * sprintMultiplier + kbEnchantMultiplier * (knockback.xCoord / Math.abs(knockback.xCoord));
        double motionZ = knockback.zCoord * sprintMultiplier + kbEnchantMultiplier * (knockback.zCoord / Math.abs(knockback.zCoord));
        double motionY = 0.35D * airMultiplier + kbEnchantMultiplier;
        entityLivingBase.motionX = motionX;
        entityLivingBase.motionY = motionY;
        entityLivingBase.motionZ = motionZ;

        if (entityLivingBase instanceof EntityPlayerMP)
        {
            EntityPlayerMP entityPlayerMP = (EntityPlayerMP)entityLivingBase;
            entityPlayerMP.playerNetServerHandler.sendPacketToPlayer(new Packet28EntityVelocity(entityLivingBase.entityId, motionX, motionY, motionZ));
        }
    }

    public int getPlayersInWaiting()
    {
        return waitingSize;
    }

    public int getSpawnerMeta(World world, int x, int y, int z)
    {
        TileEntityMobSpawner tileEntity = (TileEntityMobSpawner)world.getBlockTileEntity(x, y, z);
        return ((Integer)ServerEventHandler.INSTANCE.nameToID.get(tileEntity.getSpawnerLogic().getEntityNameToSpawn())).intValue();
    }

    public static void onNewPotionEffect(EntityLivingBase entity, PotionEffect effect)
    {
        MinecraftForge.EVENT_BUS.post(new PotionEffectEvent$Start(entity, effect));
    }

    public static void onChangedPotionEffect(EntityLivingBase entity, PotionEffect effect)
    {
        MinecraftForge.EVENT_BUS.post(new PotionEffectEvent$Change(entity, effect));
    }

    public static void onFinishedPotionEffect(EntityLivingBase entity, PotionEffect effect)
    {
        MinecraftForge.EVENT_BUS.post(new PotionEffectEvent$Finish(entity, effect));
    }

    public static boolean isElectricityEnabled()
    {
        return true;
    }

    public static void sendPacketParticle(WorldServer server, String particleName, double x, double y, double z, double xd, double yd, double zd)
    {
        PacketDispatcher.sendPacketToAllAround(x, y, z, 128.0D, server.provider.dimensionId, PacketRegistry.INSTANCE.generatePacket(new PacketSpawnParticle(particleName, x, y, z, xd, yd, zd)));
    }

    public static boolean isBlocBypassCheckNormalCube(Block block)
    {
        if (blockWhitelist.isEmpty())
        {
            blockWhitelist.add(Integer.valueOf(89));
            blockWhitelist.add(Integer.valueOf(2793));
            blockWhitelist.add(Integer.valueOf(152));
            blockWhitelist.add(Integer.valueOf(2832));
            blockWhitelist.add(Integer.valueOf(18));
            blockWhitelist.add(Integer.valueOf(2016));
            blockWhitelist.add(Integer.valueOf(2017));
            blockWhitelist.add(Integer.valueOf(2033));
            blockWhitelist.add(Integer.valueOf(2077));
            blockWhitelist.add(Integer.valueOf(46));
            blockWhitelist.add(Integer.valueOf(566));
            blockWhitelist.add(Integer.valueOf(3479));
            blockWhitelist.add(Integer.valueOf(3570));
            blockWhitelist.add(Integer.valueOf(3571));
            blockWhitelist.add(Integer.valueOf(3572));
            blockWhitelist.add(Integer.valueOf(3573));
            blockWhitelist.add(Integer.valueOf(3574));
            blockWhitelist.add(Integer.valueOf(3575));
            blockWhitelist.add(Integer.valueOf(3576));
            blockWhitelist.add(Integer.valueOf(3577));
            blockWhitelist.add(Integer.valueOf(3578));
            blockWhitelist.add(Integer.valueOf(3589));
            blockWhitelist.add(Integer.valueOf(3324));
            blockWhitelist.add(Integer.valueOf(3326));
            blockWhitelist.add(Integer.valueOf(3327));
        }

        return blockWhitelist.contains(Integer.valueOf(block.blockID));
    }

    public static DataOutputStream getNetworkLogs() throws IOException {
        if (writer == null)
        {
            writer = new DataOutputStream(Files.newOutputStream(Paths.get("networkDebug.rawlog", new String[0]), new OpenOption[0]));
        }

        return writer;
    }

    public static void networkDebug(Packet250CustomPayload packet, INetworkManager network, NetHandler handler)
    {
        if (handler instanceof NetLoginHandler)
        {
            try
            {
                DataOutputStream e = getNetworkLogs();
                e.write(66);
                e.write(102);
                e.writeLong(System.currentTimeMillis());
                e.writeUTF(((NetLoginHandler)handler).myTCPConnection.getSocketAddress().toString());
                e.writeUTF(packet.channel);

                if (packet.data != null)
                {
                    e.write(packet.data);
                }

                e.flush();
            }
            catch (IOException var4)
            {
                throw new RuntimeException(var4);
            }
        }
    }
}
