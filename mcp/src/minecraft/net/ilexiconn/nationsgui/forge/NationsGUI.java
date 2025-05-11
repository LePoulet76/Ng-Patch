/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  co.uk.flansmods.common.EntityParachute
 *  co.uk.flansmods.common.ToolType
 *  com.google.common.io.ByteArrayDataOutput
 *  com.google.common.io.ByteStreams
 *  cpw.mods.fml.common.Mod
 *  cpw.mods.fml.common.Mod$EventHandler
 *  cpw.mods.fml.common.Mod$Instance
 *  cpw.mods.fml.common.SidedProxy
 *  cpw.mods.fml.common.event.FMLInitializationEvent
 *  cpw.mods.fml.common.event.FMLPostInitializationEvent
 *  cpw.mods.fml.common.event.FMLPreInitializationEvent
 *  cpw.mods.fml.common.event.FMLServerAboutToStartEvent
 *  cpw.mods.fml.common.event.FMLServerStartingEvent
 *  cpw.mods.fml.common.network.IConnectionHandler
 *  cpw.mods.fml.common.network.NetworkMod
 *  cpw.mods.fml.common.network.NetworkMod$SidedPacketHandler
 *  cpw.mods.fml.common.network.NetworkRegistry
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  cpw.mods.fml.common.network.Player
 *  cpw.mods.fml.common.registry.GameRegistry
 *  cpw.mods.fml.relauncher.Side
 *  fr.nationsglory.ngupgrades.common.world.WorldBorder
 *  fr.nationsglory.ngupgrades.common.world.WorldExtension
 *  fr.nationsglory.server.block.entity.FeederWaterBlockEntity
 *  fr.nationsglory.server.block.entity.GCSiloBlockEntity
 *  fr.nationsglory.server.block.entity.GCTraderBlockEntity
 *  icbm.explosion.entities.EntityMissile
 *  icbm.explosion.explosive.blast.BlastNuclear
 *  net.minecraft.block.Block
 *  net.minecraft.command.ICommand
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.entity.player.EntityPlayerMP
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.nbt.CompressedStreamTools
 *  net.minecraft.nbt.NBTBase
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.nbt.NBTTagList
 *  net.minecraft.nbt.NBTTagString
 *  net.minecraft.network.packet.Packet
 *  net.minecraft.server.MinecraftServer
 *  net.minecraft.tileentity.TileEntity
 *  net.minecraft.tileentity.TileEntityMobSpawner
 *  net.minecraft.util.ResourceLocation
 *  net.minecraft.world.World
 *  net.minecraft.world.WorldServer
 *  net.minecraftforge.common.Configuration
 *  org.bukkit.Bukkit
 *  org.bukkit.plugin.Plugin
 *  universalelectricity.api.vector.Vector3
 */
package net.ilexiconn.nationsgui.forge;

import co.uk.flansmods.common.EntityParachute;
import co.uk.flansmods.common.ToolType;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerAboutToStartEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.IConnectionHandler;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import fr.nationsglory.ngupgrades.common.world.WorldBorder;
import fr.nationsglory.ngupgrades.common.world.WorldExtension;
import fr.nationsglory.server.block.entity.FeederWaterBlockEntity;
import fr.nationsglory.server.block.entity.GCSiloBlockEntity;
import fr.nationsglory.server.block.entity.GCTraderBlockEntity;
import icbm.explosion.entities.EntityMissile;
import icbm.explosion.explosive.blast.BlastNuclear;
import java.io.DataOutput;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.ilexiconn.nationsgui.forge.client.voices.networking.ClientPacketHandler;
import net.ilexiconn.nationsgui.forge.server.ConnectionHandler;
import net.ilexiconn.nationsgui.forge.server.ServerProxy;
import net.ilexiconn.nationsgui.forge.server.ServerUtils;
import net.ilexiconn.nationsgui.forge.server.asm.NationsGUIHooks;
import net.ilexiconn.nationsgui.forge.server.block.HatBlock;
import net.ilexiconn.nationsgui.forge.server.block.ImageHologramBlock;
import net.ilexiconn.nationsgui.forge.server.block.IncubatorBlock;
import net.ilexiconn.nationsgui.forge.server.block.PortalBlock;
import net.ilexiconn.nationsgui.forge.server.block.RadioBlock;
import net.ilexiconn.nationsgui.forge.server.block.RepairMachineBlock;
import net.ilexiconn.nationsgui.forge.server.block.SpeakerBlock;
import net.ilexiconn.nationsgui.forge.server.block.URLBlock;
import net.ilexiconn.nationsgui.forge.server.block.entity.ImageHologramBlockEntity;
import net.ilexiconn.nationsgui.forge.server.block.entity.PortalBlockEntity;
import net.ilexiconn.nationsgui.forge.server.block.entity.RadioBlockEntity;
import net.ilexiconn.nationsgui.forge.server.block.entity.RepairMachineBlockEntity;
import net.ilexiconn.nationsgui.forge.server.block.entity.SpeakerBlockEntity;
import net.ilexiconn.nationsgui.forge.server.block.entity.TileEntityHatBlock;
import net.ilexiconn.nationsgui.forge.server.block.entity.URLBlockEntity;
import net.ilexiconn.nationsgui.forge.server.capes.Capes;
import net.ilexiconn.nationsgui.forge.server.command.AnimCommand;
import net.ilexiconn.nationsgui.forge.server.command.COCommand;
import net.ilexiconn.nationsgui.forge.server.command.ChangeServerCommand;
import net.ilexiconn.nationsgui.forge.server.command.ClearForceChunksCommand;
import net.ilexiconn.nationsgui.forge.server.command.ClearPlayerDataCommand;
import net.ilexiconn.nationsgui.forge.server.command.CommandConnectPhone;
import net.ilexiconn.nationsgui.forge.server.command.CommandDebug;
import net.ilexiconn.nationsgui.forge.server.command.CommandDisconnectPhone;
import net.ilexiconn.nationsgui.forge.server.command.CommandEmote;
import net.ilexiconn.nationsgui.forge.server.command.CommandPictureFrame;
import net.ilexiconn.nationsgui.forge.server.command.CommandRefill;
import net.ilexiconn.nationsgui.forge.server.command.CommandTitle;
import net.ilexiconn.nationsgui.forge.server.command.CommandTroc;
import net.ilexiconn.nationsgui.forge.server.command.CookCommand;
import net.ilexiconn.nationsgui.forge.server.command.EventCommand;
import net.ilexiconn.nationsgui.forge.server.command.FrameCommand;
import net.ilexiconn.nationsgui.forge.server.command.GMOCommand;
import net.ilexiconn.nationsgui.forge.server.command.GiveSpecificCommand;
import net.ilexiconn.nationsgui.forge.server.command.JoinWaitingCommand;
import net.ilexiconn.nationsgui.forge.server.command.MoonCommand;
import net.ilexiconn.nationsgui.forge.server.command.NotifyCommand;
import net.ilexiconn.nationsgui.forge.server.command.POCommand;
import net.ilexiconn.nationsgui.forge.server.command.ParticleCommand;
import net.ilexiconn.nationsgui.forge.server.command.PingCommand;
import net.ilexiconn.nationsgui.forge.server.command.PlayMusicCommand;
import net.ilexiconn.nationsgui.forge.server.command.ScreenPlayerCommand;
import net.ilexiconn.nationsgui.forge.server.command.SearchIdsCommand;
import net.ilexiconn.nationsgui.forge.server.command.SnackbarCommand;
import net.ilexiconn.nationsgui.forge.server.command.SocketCommand;
import net.ilexiconn.nationsgui.forge.server.command.SpawnFloatingItemEntityCommand;
import net.ilexiconn.nationsgui.forge.server.command.SpawnGeckoBikeCommand;
import net.ilexiconn.nationsgui.forge.server.command.SpawnGeckoEntityCommand;
import net.ilexiconn.nationsgui.forge.server.command.ViewCommand;
import net.ilexiconn.nationsgui.forge.server.command.WelcomeCommand;
import net.ilexiconn.nationsgui.forge.server.config.MachineConfig;
import net.ilexiconn.nationsgui.forge.server.config.NBTConfig;
import net.ilexiconn.nationsgui.forge.server.item.ChristmasRecord;
import net.ilexiconn.nationsgui.forge.server.item.HatBlockItem;
import net.ilexiconn.nationsgui.forge.server.item.ItemBadge;
import net.ilexiconn.nationsgui.forge.server.item.ItemBlockPortal;
import net.ilexiconn.nationsgui.forge.server.item.ItemRottenFood;
import net.ilexiconn.nationsgui.forge.server.item.MobSpawnerItem;
import net.ilexiconn.nationsgui.forge.server.item.SpeakerItem;
import net.ilexiconn.nationsgui.forge.server.json.RemoteButtonJSON;
import net.ilexiconn.nationsgui.forge.server.json.registry.JSONRegistries;
import net.ilexiconn.nationsgui.forge.server.json.registry.JSONRegistry;
import net.ilexiconn.nationsgui.forge.server.json.registry.armor.JSONArmorSet;
import net.ilexiconn.nationsgui.forge.server.json.registry.block.JSONBlock;
import net.ilexiconn.nationsgui.forge.server.packet.PacketHandler;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.BlockReachPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.EdoraBossOverlayDataPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.IslandsSaveJumpPlayerPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.PacketEmote;
import net.ilexiconn.nationsgui.forge.server.packet.impl.RemoteOpenFactionChestPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.TitlePacket;
import net.ilexiconn.nationsgui.forge.server.util.ReleaseType;
import net.ilexiconn.nationsgui.forge.server.util.Title;
import net.ilexiconn.nationsgui.forge.server.util.Translation;
import net.ilexiconn.nationsgui.forge.server.voices.VoiceChat;
import net.ilexiconn.nationsgui.forge.server.voices.networking.CommonPacketHandler;
import net.minecraft.block.Block;
import net.minecraft.command.ICommand;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.network.packet.Packet;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.Configuration;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import universalelectricity.api.vector.Vector3;

@NetworkMod(serverSideRequired=true, clientSideRequired=true, channels={"nationsgui"}, packetHandler=PacketHandler.class, versionBounds="[1.2.0,)", clientPacketHandlerSpec=@NetworkMod.SidedPacketHandler(channels={"GVC-SMPL", "GVC-FORCEB", "GVC-SMPLE", "GVC-ED", "GVC-E", "GVC-VSA", "GVC-VS", "GVC-CHSMPL"}, packetHandler=ClientPacketHandler.class), serverPacketHandlerSpec=@NetworkMod.SidedPacketHandler(channels={"GVC-SMPL", "GVC-SMPLE", "GVC-VS"}, packetHandler=CommonPacketHandler.class))
@Mod(modid="nationsgui", name="NationsGUI", version="1.8.0")
public class NationsGUI {
    @SidedProxy(serverSide="net.ilexiconn.nationsgui.forge.server.ServerProxy", clientSide="net.ilexiconn.nationsgui.forge.client.ClientProxy")
    public static ServerProxy PROXY;
    @Mod.Instance(value="nationsgui")
    public static NationsGUI INSTANCE;
    public static RemoteButtonJSON[] REMOTE_BUTTONS;
    public static Map<String, List<String>> CONTROL_BLACKLIST;
    public static List<String> CRAFTING_BLACKLIST;
    public static HashMap<String, ResourceLocation> BADGES_RESOURCES;
    public static HashMap<String, List<String>> BADGES_TOOLTIPS;
    public static HashMap<String, String> BADGES_NAMES;
    public static HashMap<String, ResourceLocation> EMOTES_RESOURCES;
    public static HashMap<String, ResourceLocation> ANIMATIONS_RESOURCES;
    public static HashMap<String, List<String>> EMOTES_TOOLTIPS;
    public static HashMap<String, String> EMOTES_NAMES;
    public static HashMap<String, String> EMOTES_SYMBOLS;
    public static ArrayList<String> ANIMATIONS;
    public static MachineConfig CONFIG;
    public static final String MODID = "nationsgui";
    public static final String VERSION = "1.8.0";
    public static final ReleaseType RELEASE_TYPE;
    public static Block RADIO;
    public static Block URL;
    public static Block IMAGE_HOLOGRAM;
    public static final Block SPEAKER;
    public static final Block INCUBATOR;
    public static final Block REPAIR_MACHINE;
    public static final Item MOB_SPAWNER;
    public static final Item CHRISTMAS_DISK;
    public static final Item ROTTEN_FOOD;
    public static final Item ITEM_BADGE;
    public static final Block HATBLOCK;
    public static final Block PORTAL;
    public static final Item PORTAL_ITEM;
    public static final String HATS_BASE_LINK = "https://apiv2.nationsglory.fr/json/hats/";
    public static final String CAPES_BASE_LINK = "https://apiv2.nationsglory.fr/json/capes/";
    private static VoiceChat chat;
    public static HashMap<String, Integer> resoucesZones;
    public static String cachedServerType;

    public static void addTraderPoint(String enterpriseName, int point) {
        Plugin pl = Bukkit.getPluginManager().getPlugin("NationsJob");
        if (pl != null) {
            try {
                Method m = pl.getClass().getDeclaredMethod("addTraderPoint", String.class, Integer.TYPE);
                m.invoke(pl, enterpriseName, point);
            }
            catch (IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    public static float withdrawPetrolInCoords(String world, int posX, int posZ, int amount) {
        Plugin pl = Bukkit.getPluginManager().getPlugin("NationsUtils");
        if (pl != null) {
            try {
                Method m = pl.getClass().getDeclaredMethod("withdrawPetrolInCoords", String.class, Integer.TYPE, Integer.TYPE, Integer.TYPE);
                return ((Float)m.invoke(pl, world, posX, posZ, amount)).floatValue();
            }
            catch (IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return 1.0f;
    }

    public static float withdrawGasInCoords(String world, int posX, int posZ, int amount) {
        Plugin pl = Bukkit.getPluginManager().getPlugin("NationsUtils");
        if (pl != null) {
            try {
                Method m = pl.getClass().getDeclaredMethod("withdrawGasInCoords", String.class, Integer.TYPE, Integer.TYPE, Integer.TYPE);
                return ((Float)m.invoke(pl, world, posX, posZ, amount)).floatValue();
            }
            catch (IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return 1.0f;
    }

    public static void storeEnergy(String enterpriseName, int energy) {
        Plugin pl = Bukkit.getPluginManager().getPlugin("NationsJob");
        if (pl != null) {
            try {
                Method m = pl.getClass().getDeclaredMethod("storeEnergy", String.class, Integer.TYPE);
                m.invoke(pl, enterpriseName, energy);
            }
            catch (IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    public static float withdrawEnergy(String enterpriseName, int energy, int posX, int posZ) {
        Plugin pl = Bukkit.getPluginManager().getPlugin("NationsJob");
        if (pl != null) {
            try {
                Method m = pl.getClass().getDeclaredMethod("withdrawEnergy", String.class, Integer.TYPE, Integer.TYPE, Integer.TYPE);
                return ((Float)m.invoke(pl, enterpriseName, energy, posX, posZ)).floatValue();
            }
            catch (IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return 0.0f;
    }

    public static boolean canPlayerTalk(String playerName) {
        Plugin pl = Bukkit.getPluginManager().getPlugin("NationsGUI");
        if (pl != null) {
            try {
                Method m = pl.getClass().getDeclaredMethod("canPlayerTalk", String.class);
                return (Boolean)m.invoke(pl, playerName);
            }
            catch (IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static String getFactionNameFromId(String factionId) {
        Plugin pl = Bukkit.getPluginManager().getPlugin("NationsGUI");
        if (pl != null) {
            try {
                Method m = pl.getClass().getDeclaredMethod("getFactionNameFromId", String.class);
                return (String)m.invoke(pl, factionId);
            }
            catch (IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return factionId;
    }

    public static boolean canPlayerHear(String playerName, String playerNameSpeaking) {
        Plugin pl = Bukkit.getPluginManager().getPlugin("NationsGUI");
        if (pl != null) {
            try {
                Method m = pl.getClass().getDeclaredMethod("canPlayerHear", String.class, String.class);
                return (Boolean)m.invoke(pl, playerName, playerNameSpeaking);
            }
            catch (IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static void sendHawkEyeLog(String action, String player, String location, String data) {
        Plugin pl = Bukkit.getPluginManager().getPlugin("NationsGUI");
        if (pl != null) {
            try {
                Method m = pl.getClass().getDeclaredMethod("sendHawkEyeLog", String.class, String.class, String.class, String.class);
                m.invoke(pl, action, player, location, data);
            }
            catch (IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    public static String islandsChangePlateTeam(String playerName, int posX, int posY, int posZ, String currentTeam, boolean isSpawn) {
        Plugin pl = Bukkit.getPluginManager().getPlugin("NationsBuild");
        if (pl != null) {
            try {
                Method m = pl.getClass().getDeclaredMethod("changePlateTeam", String.class, Integer.TYPE, Integer.TYPE, Integer.TYPE, String.class, Boolean.TYPE);
                return (String)m.invoke(pl, playerName, posX, posY, posZ, currentTeam, isSpawn);
            }
            catch (IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    public static String islandsRemovePlateTeam(String worldName, int posX, int posY, int posZ, String currentTeam) {
        Plugin pl = Bukkit.getPluginManager().getPlugin("NationsBuild");
        if (pl != null) {
            try {
                Method m = pl.getClass().getDeclaredMethod("removePlateTeam", String.class, Integer.TYPE, Integer.TYPE, Integer.TYPE, String.class);
                return (String)m.invoke(pl, worldName, posX, posY, posZ, currentTeam);
            }
            catch (IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    public static boolean islandsCheckPermission(String worldName, String playerName, String permission) {
        Plugin pl = Bukkit.getPluginManager().getPlugin("NationsBuild");
        if (pl != null) {
            try {
                Method m = pl.getClass().getDeclaredMethod("checkPermission", String.class, String.class, String.class);
                return (Boolean)m.invoke(pl, worldName, playerName, permission);
            }
            catch (IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static void islandsPlayerJoinTeam(String worldName, String playerName, String teamName) {
        Plugin pl = Bukkit.getPluginManager().getPlugin("NationsBuild");
        if (pl != null) {
            try {
                Method m = pl.getClass().getDeclaredMethod("playerJoinTeam", String.class, String.class, String.class);
                m.invoke(pl, worldName, playerName, teamName);
            }
            catch (IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    public static void islandsCaptureZone(String worldName, ArrayList<String> players) {
        Plugin pl = Bukkit.getPluginManager().getPlugin("NationsBuild");
        if (pl != null) {
            try {
                Method m = pl.getClass().getDeclaredMethod("captureZone", String.class, ArrayList.class);
                m.invoke(pl, worldName, players);
            }
            catch (IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    public static void islandsFootGoal(String worldName, int posX, int posY, int posZ, List<String> lastPlayersHit, String goalTeam) {
        Plugin pl = Bukkit.getPluginManager().getPlugin("NationsBuild");
        if (pl != null) {
            try {
                Method m = pl.getClass().getDeclaredMethod("footGoal", String.class, Integer.TYPE, Integer.TYPE, Integer.TYPE, List.class, String.class);
                m.invoke(pl, worldName, posX, posY, posZ, lastPlayersHit, goalTeam);
            }
            catch (IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    public static Map<String, Integer> islandsFootGetScores(String worldName) {
        Plugin pl = Bukkit.getPluginManager().getPlugin("NationsBuild");
        if (pl != null) {
            try {
                Method m = pl.getClass().getDeclaredMethod("footGetScores", String.class);
                return (Map)m.invoke(pl, worldName);
            }
            catch (IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return new HashMap<String, Integer>();
    }

    public static void printFactionChestContent(String factionId, String factionName) {
        NBTTagCompound compound = (NBTTagCompound)NBTConfig.CONFIG.getCompound().func_74781_a("FactionChest");
        System.out.println(Translation.get("--- COFFRE DE PAYS AVANT DISBAND DE ") + factionName + " ---");
        NBTTagList itemsTag = compound.func_74761_m(factionId);
        for (int i = 0; i < itemsTag.func_74745_c(); ++i) {
            NBTTagCompound itemTag = (NBTTagCompound)itemsTag.func_74743_b(i);
            ItemStack item = ItemStack.func_77949_a((NBTTagCompound)itemTag);
            System.out.println(item.field_77993_c + ":" + item.func_77960_j() + " x" + item.field_77994_a);
        }
        System.out.println("---------------------------");
    }

    public static List<String> getOGMRecipe(String ogmName) {
        Plugin pl = Bukkit.getPluginManager().getPlugin("NationsUtils");
        if (pl != null) {
            try {
                Method m = pl.getClass().getDeclaredMethod("getOGMRecipe", String.class);
                return (List)m.invoke(pl, ogmName);
            }
            catch (IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static void islandsSaveJumpPosition(String position, String positionType, Long time) {
        PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new IslandsSaveJumpPlayerPacket(position, positionType, time)));
    }

    public static void countOGMREcipe(String ogmName, boolean success, int percent) {
        Plugin pl = Bukkit.getPluginManager().getPlugin("NationsUtils");
        if (pl != null) {
            try {
                Method m = pl.getClass().getDeclaredMethod("countOGMRecipe", String.class, Boolean.TYPE, Integer.TYPE);
                m.invoke(pl, ogmName, success, percent);
            }
            catch (IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean canPlayerDriveVehicle(String targetPlayerName, String ownerName, boolean allowFaction, boolean allowEnterprise) {
        Plugin pl = Bukkit.getPluginManager().getPlugin("NationsUtils");
        if (pl != null) {
            try {
                Method m = pl.getClass().getDeclaredMethod("canPlayerUseVehicle", String.class, String.class, Boolean.TYPE, Boolean.TYPE);
                return (Boolean)m.invoke(pl, targetPlayerName, ownerName, allowFaction, allowEnterprise);
            }
            catch (IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static void sendPlayerPacketTitle(Player player, String title, String subtitle, int duration) {
        PacketDispatcher.sendPacketToPlayer((Packet)PacketRegistry.INSTANCE.generatePacket(new TitlePacket(new Title(title.replace('&', '\u00a7'), subtitle.replace('&', '\u00a7'), duration * 20))), (Player)player);
    }

    public static void playEmoteForPlayers(ArrayList<Player> players, ArrayList<String> playersName, String emote) {
        for (int i = 0; i < players.size(); ++i) {
            PacketDispatcher.sendPacketToPlayer((Packet)PacketRegistry.INSTANCE.generatePacket(new PacketEmote(emote, playersName.get(i))), (Player)players.get(i));
        }
    }

    public static boolean canPlayerInteractVehicle(String targetPlayerName, String worldName, double posX, double posY, double posZ) {
        Plugin pl = Bukkit.getPluginManager().getPlugin("NationsUtils");
        if (pl != null) {
            try {
                Method m = pl.getClass().getDeclaredMethod("canPlayerInteractVehicle", String.class, String.class, Double.TYPE, Double.TYPE, Double.TYPE);
                return (Boolean)m.invoke(pl, targetPlayerName, worldName, posX, posY, posZ);
            }
            catch (IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static void addWaterInFeeder(String worldName, Integer posX, Integer posY, Integer posZ) {
        TileEntity tile;
        WorldServer world = null;
        for (WorldServer worldServer : MinecraftServer.func_71276_C().field_71305_c) {
            if (!worldServer.func_72912_H().func_76065_j().equalsIgnoreCase(worldName)) continue;
            world = worldServer;
        }
        if (world != null && (tile = world.func_72796_p(posX.intValue(), posY.intValue(), posZ.intValue())) != null && tile instanceof FeederWaterBlockEntity && ((FeederWaterBlockEntity)tile).quantity < FeederWaterBlockEntity.MAX_QTE) {
            ((FeederWaterBlockEntity)tile).quantity = Math.min(FeederWaterBlockEntity.MAX_QTE, ((FeederWaterBlockEntity)tile).quantity + 5);
        }
    }

    public static boolean canCropTick(String cerealName, String worldName, int posX, int posZ) {
        Plugin pl = Bukkit.getPluginManager().getPlugin("NationsUtils");
        if (pl != null) {
            try {
                Method m = pl.getClass().getDeclaredMethod("canCropTick", String.class, String.class, Integer.TYPE, Integer.TYPE);
                return (Boolean)m.invoke(pl, cerealName, worldName, posX, posZ);
            }
            catch (IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static int repairVehicle(String playerName, int entityID) {
        Plugin pl = Bukkit.getPluginManager().getPlugin("NationsUtils");
        if (pl != null) {
            try {
                Method m = pl.getClass().getDeclaredMethod("repairVehicle", String.class, Integer.TYPE);
                return (Integer)m.invoke(pl, playerName, entityID);
            }
            catch (IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    public static void removePlayerBadge(String playerName, String badgeName) {
        Plugin pl = Bukkit.getPluginManager().getPlugin("NationsUtils");
        if (pl != null) {
            try {
                Method m = pl.getClass().getDeclaredMethod("removePlayerBadge", String.class, String.class);
                m.invoke(pl, playerName, badgeName);
            }
            catch (IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    public static void spawnOffPlayer(String playerName) {
        Plugin pl = Bukkit.getPluginManager().getPlugin("NationsGUI");
        if (pl != null) {
            try {
                Method m = pl.getClass().getDeclaredMethod("spawnOffPlayer", String.class);
                m.invoke(pl, playerName);
            }
            catch (IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    public static void openCountryChest(EntityPlayer player, String factionId, int chestLevel) {
        Plugin pl = Bukkit.getPluginManager().getPlugin("NationsGUI");
        if (pl != null) {
            try {
                Method m = pl.getClass().getDeclaredMethod("canPlayerOpenCountryChest", String.class, String.class);
                boolean res = (Boolean)m.invoke(pl, player.field_71092_bJ, factionId);
                if (res) {
                    RemoteOpenFactionChestPacket.openedChestsByPlayer.put(factionId, player.getDisplayName());
                    RemoteOpenFactionChestPacket.playersTargetGui.put(player.getDisplayName(), factionId);
                    RemoteOpenFactionChestPacket.playersCanTake.put(player.getDisplayName(), true);
                    RemoteOpenFactionChestPacket.playersCanDeposit.put(player.getDisplayName(), true);
                    RemoteOpenFactionChestPacket.playersChestLevel.put(player.getDisplayName(), chestLevel);
                    player.openGui((Object)INSTANCE, 666, player.field_70170_p, (int)player.field_70165_t, (int)player.field_70163_u, (int)player.field_70161_v);
                }
            }
            catch (IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    public static void refreshBadgesSite(String player, String badges) {
        try {
            Class.forName("org.bukkit.Bukkit");
            Plugin pl = Bukkit.getPluginManager().getPlugin("NationsGUI");
            if (pl != null) {
                try {
                    Method m = pl.getClass().getDeclaredMethod("refreshBadgesSite", String.class, String.class);
                    m.invoke(pl, player, badges);
                }
                catch (IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        catch (ClassNotFoundException classNotFoundException) {
            // empty catch block
        }
    }

    public static boolean canPlayerModifyBlock(String playerName, int blockX, int blockY, int blockZ, String worldName) {
        Plugin pl = Bukkit.getPluginManager().getPlugin("NationsGUI");
        if (pl != null) {
            try {
                Method m = pl.getClass().getDeclaredMethod("canPlayerModifyBlock", String.class, Integer.TYPE, Integer.TYPE, Integer.TYPE, String.class);
                return (Boolean)m.invoke(pl, playerName, blockX, blockY, blockZ, worldName);
            }
            catch (IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static boolean sendSocketMessage(String message) {
        Plugin pl = Bukkit.getPluginManager().getPlugin("NationsGUI");
        if (pl != null) {
            try {
                Method m = pl.getClass().getDeclaredMethod("sendSocketMessage", String.class);
                return (Boolean)m.invoke(pl, message);
            }
            catch (IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static boolean canPlayerUseSpecialAttack(String playerName, int blockX, int blockY, int blockZ, String worldName) {
        Plugin pl = Bukkit.getPluginManager().getPlugin("NationsGUI");
        if (pl != null) {
            try {
                Method m = pl.getClass().getDeclaredMethod("canPlayerUseSpecialAttack", String.class, Integer.TYPE, Integer.TYPE, Integer.TYPE, String.class);
                return (Boolean)m.invoke(pl, playerName, blockX, blockY, blockZ, worldName);
            }
            catch (IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static int getPlayerSkillLevel(String playerName, String skillName) {
        Plugin pl = Bukkit.getPluginManager().getPlugin("NationsJob");
        if (pl != null) {
            try {
                Method m = pl.getClass().getDeclaredMethod("getPlayerSkillLevel", String.class, String.class);
                return (Integer)m.invoke(pl, playerName, skillName);
            }
            catch (IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    public static HashMap<String, HashMap<String, Object>> getPlayerSkillsInfos(String playerName) {
        Plugin pl = Bukkit.getPluginManager().getPlugin("NationsJob");
        if (pl != null) {
            try {
                Method m = pl.getClass().getDeclaredMethod("getPlayerSkillsInfos", String.class);
                return (HashMap)m.invoke(pl, playerName);
            }
            catch (IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return new HashMap<String, HashMap<String, Object>>();
    }

    public static void spawnCarePackage(String defenserName, int x, int y, int z, int defenserLevel, int nbAtt, int nbDef) {
    }

    public static void updateWaitingSize(int size) {
        NationsGUIHooks.waitingSize = size;
    }

    public static void addIpToAllowConnection(String ip) {
        NationsGUIHooks.allowedIPConnection.add(ip.split(":")[0]);
        System.out.println("DEBUG Add IP to allowed connection: " + ip);
    }

    public static HashMap<String, String> getTopPlayersSkills() {
        Plugin pl = Bukkit.getPluginManager().getPlugin("NationsJob");
        if (pl != null) {
            try {
                Method m = pl.getClass().getDeclaredMethod("getTopPlayersSkills", new Class[0]);
                return (HashMap)m.invoke(pl, new Object[0]);
            }
            catch (IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return new HashMap<String, String>();
    }

    public static void setPlayerAvailableBadges(String player, String badges) {
        NBTTagCompound compound = (NBTTagCompound)NBTConfig.CONFIG.getCompound().func_74781_a("Badges");
        NBTTagCompound user = compound.func_74775_l(player);
        if (!user.func_74764_b("AvailableBadges")) {
            user.func_74782_a("AvailableBadges", (NBTBase)new NBTTagString(""));
        }
        user.func_74778_a("AvailableBadges", badges);
        compound.func_74766_a(player, user);
        NBTConfig.CONFIG.save();
    }

    public static void addPlayerSkill(String playerName, String skillName, int points) {
        Plugin pl = Bukkit.getPluginManager().getPlugin("NationsJob");
        if (pl != null) {
            try {
                Method m = pl.getClass().getDeclaredMethod("addPlayerSkill", String.class, String.class, Integer.TYPE);
                m.invoke(pl, playerName, skillName, points);
            }
            catch (IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean canPlayerHaveDoubleLoot(String playerName, String skillName) {
        Plugin pl = Bukkit.getPluginManager().getPlugin("NationsJob");
        if (pl != null) {
            try {
                Method m = pl.getClass().getDeclaredMethod("canPlayerHaveDoubleLoot", String.class, String.class);
                return (Boolean)m.invoke(pl, playerName, skillName);
            }
            catch (IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static boolean canPlayerContractContagious(String playerName) {
        EntityPlayerMP playerForUsername = MinecraftServer.func_71276_C().func_71203_ab().func_72361_f(playerName);
        if (playerForUsername != null && playerForUsername.func_82165_m(43)) {
            return false;
        }
        Plugin pl = Bukkit.getPluginManager().getPlugin("NationsGUI");
        if (pl != null) {
            try {
                Method m = pl.getClass().getDeclaredMethod("canPlayerContractContagious", String.class);
                return (Boolean)m.invoke(pl, playerName);
            }
            catch (IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static String getServerType() {
        if (!cachedServerType.isEmpty()) {
            return cachedServerType;
        }
        try {
            Plugin pl;
            Class.forName("org.bukkit.Bukkit");
            if (Bukkit.getServer() != null && (pl = Bukkit.getPluginManager().getPlugin("NationsGUI")) != null) {
                try {
                    Method m = pl.getClass().getDeclaredMethod("getServerType", new Class[0]);
                    String serverType = (String)m.invoke(pl, new Object[0]);
                    if (serverType != null) {
                        cachedServerType = serverType;
                    }
                    return cachedServerType;
                }
                catch (IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static Long isMachineOnMagneticArea(String worldName, int x, int y, int z) {
        try {
            Plugin pl;
            Class.forName("org.bukkit.Bukkit");
            if (Bukkit.getServer() != null && (pl = Bukkit.getPluginManager().getPlugin("NationsGUI")) != null) {
                try {
                    Method m = pl.getClass().getDeclaredMethod("isMachineOnMagneticArea", String.class, Integer.class, Integer.class, Integer.class);
                    return (Long)m.invoke(pl, worldName, x, y, z);
                }
                catch (IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return 0L;
    }

    public static void teleportPlayerToLocation(String playerName, String worldNameOrDimensionID, int x, int y, int z, float yaw, float pitch) {
        block7: {
            try {
                Plugin pl;
                Class.forName("org.bukkit.Bukkit");
                if (Bukkit.getServer() == null || (pl = Bukkit.getPluginManager().getPlugin("NationsGUI")) == null) break block7;
                try {
                    String worldName = worldNameOrDimensionID;
                    try {
                        int dimensionID = Integer.parseInt(worldNameOrDimensionID);
                        WorldServer forgeWorld = MinecraftServer.func_71276_C().func_71218_a(dimensionID);
                        if (forgeWorld != null) {
                            worldName = forgeWorld.func_72912_H().func_76065_j();
                        }
                    }
                    catch (NumberFormatException dimensionID) {
                        // empty catch block
                    }
                    Method m = pl.getClass().getDeclaredMethod("teleportPlayerToLocation", String.class, String.class, Integer.class, Integer.class, Integer.class, Float.class, Float.class);
                    m.invoke(pl, playerName, worldName, x, y, z, Float.valueOf(yaw), Float.valueOf(pitch));
                }
                catch (IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
            catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public static void spawnMissileAt(int x, int y, int z, String worldName, int missileID, String launcher) {
        WorldServer world = null;
        for (WorldServer worldServer : MinecraftServer.func_71276_C().field_71305_c) {
            if (!worldServer.func_72912_H().func_76065_j().equals(worldName)) continue;
            world = worldServer;
        }
        if (world != null) {
            World worldObj = world.field_73011_w.field_76579_a;
            EntityMissile missile = new EntityMissile(worldObj, new Vector3((double)x, (double)y, (double)z), missileID, 0.0f, 0.0f);
            EntityPlayerMP entityPlayerMP = MinecraftServer.func_71276_C().func_71203_ab().func_72361_f(launcher);
            missile.setPlayerLauncher((EntityPlayer)entityPlayerMP);
            missile.setFromPlatform(false);
            worldObj.func_72838_d((Entity)missile);
            missile.launch(new Vector3((double)x, 10.0, (double)z));
        }
    }

    public static void placeMobSpawnerAt(String world, int x, int y, int z, String mobId) {
        WorldServer worldServer = null;
        for (WorldServer worldServer1 : MinecraftServer.func_71276_C().field_71305_c) {
            if (!worldServer1.func_72912_H().func_76065_j().equals(world)) continue;
            worldServer = worldServer1;
        }
        if (worldServer != null) {
            worldServer.func_72832_d(x, y, z, Block.field_72065_as.field_71990_ca, 0, 3);
            TileEntityMobSpawner var8 = (TileEntityMobSpawner)worldServer.func_72796_p(x, y, z);
            if (var8 != null) {
                var8.func_98049_a().func_98272_a(mobId);
            }
        }
    }

    public static void createBlastExplosionAt(int x, int y, int z, String worldName, float size, float energy) {
        WorldServer world = null;
        for (WorldServer worldServer : MinecraftServer.func_71276_C().field_71305_c) {
            if (!worldServer.func_72912_H().func_76065_j().equals(worldName)) continue;
            world = worldServer;
        }
        if (world != null) {
            new BlastNuclear((World)world, null, (double)x, (double)y, (double)z, size, energy).explode();
        }
    }

    public static void registerBlockExplosion(String worldName, int x, int y, int z, int blockId, int blockMeta) {
        try {
            Plugin pl;
            Class.forName("org.bukkit.Bukkit");
            if (Bukkit.getServer() != null && (pl = Bukkit.getPluginManager().getPlugin("NationsGUI")) != null) {
                try {
                    Method m = pl.getClass().getDeclaredMethod("registerBlockExplosion", String.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class);
                    m.invoke(pl, worldName, x, y, z, blockId, blockMeta);
                }
                catch (IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static boolean isRadiusSet(String str) {
        try {
            Class<?> c = Class.forName("main.java.fr.ng.ibalix.nationsutils.CommonHelper");
            if (Bukkit.getServer() != null) {
                try {
                    Method m = c.getDeclaredMethod("isRadiusSet", String.class);
                    return (Boolean)m.invoke(c, str);
                }
                catch (IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static int getRadius(String str) {
        try {
            Plugin pl;
            Class.forName("org.bukkit.Bukkit");
            if (Bukkit.getServer() != null && (pl = Bukkit.getPluginManager().getPlugin("NationsGUI")) != null) {
                try {
                    Method m = pl.getClass().getDeclaredMethod("getServerType", String.class);
                    return (Integer)m.invoke(pl, str);
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

    public static boolean isPlayerImmuniseFromMissile(String playerName) {
        Plugin pl = Bukkit.getPluginManager().getPlugin("NationsGUI");
        if (pl != null) {
            try {
                Method m = pl.getClass().getDeclaredMethod("isPlayerImmuniseFromMissile", String.class);
                return (Boolean)m.invoke(pl, playerName);
            }
            catch (IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static void executePlayerCmd(String playerName, String cmd) {
        Plugin pl = Bukkit.getPluginManager().getPlugin("NationsGUI");
        if (pl != null) {
            try {
                Method m = pl.getClass().getDeclaredMethod("executePlayerCmd", String.class, String.class);
                m.invoke(pl, playerName, cmd);
            }
            catch (IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    public static void addSkinToPlayer(String playerName, String skinName) {
        Plugin pl = Bukkit.getPluginManager().getPlugin("NationsGUI");
        if (pl != null) {
            try {
                Method m = pl.getClass().getDeclaredMethod("addSkinToPlayer", String.class, String.class);
                m.invoke(pl, playerName, skinName);
            }
            catch (IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    public static void setPlayerBlockReach(String playerName, float range) {
        EntityPlayerMP entityPlayerMP = MinecraftServer.func_71276_C().func_71203_ab().func_72361_f(playerName);
        if (entityPlayerMP != null) {
            entityPlayerMP.field_71134_c.setBlockReachDistance((double)range);
            PacketDispatcher.sendPacketToPlayer((Packet)PacketRegistry.INSTANCE.generatePacket(new BlockReachPacket(range)), (Player)((Player)entityPlayerMP));
        }
    }

    public static boolean hasSiloEnoughCereal(int blockX, int blockY, int blockZ, String worldName, String cerealType, int quantity) {
        TileEntity tileEntity;
        WorldServer world = null;
        for (WorldServer worldServer : MinecraftServer.func_71276_C().field_71305_c) {
            if (!worldServer.func_72912_H().func_76065_j().equalsIgnoreCase(worldName)) continue;
            world = worldServer;
        }
        if (world != null && (tileEntity = world.func_72796_p(blockX, blockY, blockZ)) instanceof GCSiloBlockEntity) {
            return ((GCSiloBlockEntity)tileEntity).cerealType.equals(cerealType) && ((GCSiloBlockEntity)tileEntity).quantity >= quantity;
        }
        return false;
    }

    public static void deployPlayerParachute(String playerName) {
        EntityPlayerMP entityPlayerMP = MinecraftServer.func_71276_C().func_71203_ab().func_72361_f(playerName);
        if (entityPlayerMP != null) {
            EntityParachute var18 = new EntityParachute(entityPlayerMP.field_70170_p, ToolType.getType((String)"mwParachute"), (EntityPlayer)entityPlayerMP);
            entityPlayerMP.field_70170_p.func_72838_d((Entity)var18);
            entityPlayerMP.func_70078_a((Entity)var18);
        }
    }

    public static void reduceBorder(String worldName, String borderName, double ratio, int duration) {
        WorldExtension worldExtension;
        WorldServer world = null;
        for (WorldServer worldServer : MinecraftServer.func_71276_C().field_71305_c) {
            if (!worldServer.func_72912_H().func_76065_j().equalsIgnoreCase(worldName)) continue;
            world = worldServer;
        }
        if (world != null && (worldExtension = WorldExtension.get(world)).getWorldBorders().containsKey(borderName)) {
            ratio /= 100.0;
            WorldBorder worldBorder = (WorldBorder)worldExtension.getWorldBorders().get(borderName);
            if (ratio <= 1.0) {
                worldBorder.setWantedSafeRatio(ratio);
                worldBorder.setReducingTickRate(duration);
            }
            worldExtension.func_76185_a();
            ByteArrayDataOutput data = ByteStreams.newDataOutput();
            data.writeInt(3);
            try {
                NBTTagCompound nbtTagCompound = new NBTTagCompound();
                worldExtension.func_76187_b(nbtTagCompound);
                CompressedStreamTools.func_74800_a((NBTTagCompound)nbtTagCompound, (DataOutput)data);
                PacketDispatcher.sendPacketToAllPlayers((Packet)PacketDispatcher.getPacket((String)"ngupgrades", (byte[])data.toByteArray()));
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void openLootboxForPlayer(String playerName, String lootboxName, int x, int y, int z, String worldName, boolean animate) {
        Plugin pl = Bukkit.getPluginManager().getPlugin("NationsGUI");
        if (pl != null) {
            try {
                Method m = pl.getClass().getDeclaredMethod("openLootboxForPlayer", String.class, String.class, Integer.TYPE, Integer.TYPE, Integer.TYPE, String.class, Boolean.TYPE);
                m.invoke(pl, playerName, lootboxName, x, y, z, worldName, animate);
            }
            catch (IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    public static String getTradingMachineEnterprise(String worldName, int posX, int posY, int posZ) {
        TileEntity tile;
        WorldServer world = null;
        for (WorldServer worldServer : MinecraftServer.func_71276_C().field_71305_c) {
            if (!worldServer.func_72912_H().func_76065_j().equalsIgnoreCase(worldName)) continue;
            world = worldServer;
        }
        if (world != null && (tile = world.func_72796_p(posX, posY, posZ)) != null && tile instanceof GCTraderBlockEntity) {
            return ((GCTraderBlockEntity)tile).enterpriseName;
        }
        return null;
    }

    public static void sendEdoraBossOverlayDataToPlayer(EntityPlayer player, HashMap<String, Object> data) {
        PacketDispatcher.sendPacketToPlayer((Packet)PacketRegistry.INSTANCE.generatePacket(new EdoraBossOverlayDataPacket(data)), (Player)((Player)player));
    }

    public static String spawnGeckoEntityAt(int x, int y, int z, String worldName, String entityName, double maxHealth) {
        try {
            Method m = ServerUtils.class.getMethod("spawnGeckoEntityAt", Integer.TYPE, Integer.TYPE, Integer.TYPE, String.class, String.class, Double.TYPE, Boolean.TYPE, Boolean.TYPE);
            return (String)m.invoke(null, x, y, z, worldName, entityName, maxHealth, false, false);
        }
        catch (IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Mod.EventHandler
    public void onPreInit(FMLPreInitializationEvent event) {
        RADIO = new RadioBlock();
        URL = new URLBlock();
        IMAGE_HOLOGRAM = new ImageHologramBlock();
        chat = new VoiceChat(event.getSide());
        Capes.refresh();
        chat.preInit(event);
        CONFIG.loadConfig(new Configuration(event.getSuggestedConfigurationFile()));
        PROXY.onPreInit();
        JSONRegistry<JSONBlock> blockRegistry = JSONRegistries.getRegistry(JSONBlock.class);
        JSONRegistry<JSONArmorSet> armorRegistry = JSONRegistries.getRegistry(JSONArmorSet.class);
        try {
            JSONRegistries.parseJSON(blockRegistry, "https://apiv2.nationsglory.fr/json/moreblocs.json");
            JSONRegistries.parseJSON(armorRegistry, "https://apiv2.nationsglory.fr/json/morearmors.json");
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        GameRegistry.registerBlock((Block)RADIO, (String)"radio");
        GameRegistry.registerBlock((Block)URL, (String)"url");
        GameRegistry.registerBlock((Block)IMAGE_HOLOGRAM, (String)"imagehologram");
        GameRegistry.registerTileEntity(RadioBlockEntity.class, (String)"radio_entity");
        GameRegistry.registerTileEntity(URLBlockEntity.class, (String)"url_entity");
        GameRegistry.registerTileEntity(ImageHologramBlockEntity.class, (String)"imagehologram_entity");
        GameRegistry.registerBlock((Block)SPEAKER, SpeakerItem.class, (String)"speaker");
        GameRegistry.registerTileEntity(SpeakerBlockEntity.class, (String)"speaker_entity");
        GameRegistry.registerBlock((Block)REPAIR_MACHINE, (String)"repair_machine");
        GameRegistry.registerTileEntity(RepairMachineBlockEntity.class, (String)"repair_machine_entity");
        GameRegistry.registerItem((Item)MOB_SPAWNER, (String)"ng_mob_spawner");
        GameRegistry.registerItem((Item)CHRISTMAS_DISK, (String)"christmas_disk");
        GameRegistry.registerItem((Item)ROTTEN_FOOD, (String)"rotten_food");
        GameRegistry.registerBlock((Block)PORTAL, (String)"island_portal");
        GameRegistry.registerTileEntity(PortalBlockEntity.class, (String)"island_portal_entity");
        GameRegistry.registerItem((Item)PORTAL_ITEM, (String)"island_portal_item");
        GameRegistry.registerTileEntity(TileEntityHatBlock.class, (String)"nggui:tileHatBlock");
        GameRegistry.registerBlock((Block)HATBLOCK, HatBlockItem.class, (String)"hatblock");
        GameRegistry.registerItem((Item)ITEM_BADGE, (String)"itemBadge");
        if (event.getSide() == Side.SERVER) {
            NetworkRegistry.instance().registerConnectionHandler((IConnectionHandler)new ConnectionHandler());
        }
    }

    @Mod.EventHandler
    public void onInit(FMLInitializationEvent event) {
        PROXY.onInit();
        chat.init(event);
    }

    @Mod.EventHandler
    public void onPostInit(FMLPostInitializationEvent event) {
        PROXY.onPostInit();
    }

    @Mod.EventHandler
    public void onServerPreInit(FMLServerAboutToStartEvent event) {
        chat.preInitServer(event);
    }

    @Mod.EventHandler
    public void onServerStarting(FMLServerStartingEvent event) {
        chat.initServer(event);
        event.registerServerCommand((ICommand)new SnackbarCommand());
        event.registerServerCommand((ICommand)new AnimCommand());
        event.registerServerCommand((ICommand)new GMOCommand());
        event.registerServerCommand((ICommand)new COCommand());
        event.registerServerCommand((ICommand)new POCommand());
        event.registerServerCommand((ICommand)new PlayMusicCommand());
        event.registerServerCommand((ICommand)new CookCommand());
        event.registerServerCommand((ICommand)new WelcomeCommand());
        event.registerServerCommand((ICommand)new ChangeServerCommand());
        event.registerServerCommand((ICommand)new JoinWaitingCommand());
        event.registerServerCommand((ICommand)new CommandConnectPhone());
        event.registerServerCommand((ICommand)new CommandDisconnectPhone());
        event.registerServerCommand((ICommand)new CommandPictureFrame());
        event.registerServerCommand((ICommand)new PingCommand());
        event.registerServerCommand((ICommand)new NotifyCommand());
        event.registerServerCommand((ICommand)new CommandTroc());
        event.registerServerCommand((ICommand)new CommandDebug());
        event.registerServerCommand((ICommand)new CommandTitle());
        event.registerServerCommand((ICommand)new CommandEmote());
        event.registerServerCommand((ICommand)new ClearForceChunksCommand());
        event.registerServerCommand((ICommand)new MoonCommand());
        event.registerServerCommand((ICommand)new ParticleCommand());
        event.registerServerCommand((ICommand)new ScreenPlayerCommand());
        event.registerServerCommand((ICommand)new SearchIdsCommand());
        event.registerServerCommand((ICommand)new ViewCommand());
        event.registerServerCommand((ICommand)new SocketCommand());
        event.registerServerCommand((ICommand)new SpawnGeckoEntityCommand());
        event.registerServerCommand((ICommand)new CommandRefill());
        event.registerServerCommand((ICommand)new SpawnGeckoBikeCommand());
        event.registerServerCommand((ICommand)new GiveSpecificCommand());
        event.registerServerCommand((ICommand)new FrameCommand());
        event.registerServerCommand((ICommand)new EventCommand());
        event.registerServerCommand((ICommand)new ClearPlayerDataCommand());
        event.registerServerCommand((ICommand)new SpawnFloatingItemEntityCommand());
    }

    static {
        BADGES_RESOURCES = new HashMap();
        BADGES_TOOLTIPS = new HashMap();
        BADGES_NAMES = new HashMap();
        EMOTES_RESOURCES = new HashMap();
        ANIMATIONS_RESOURCES = new HashMap();
        EMOTES_TOOLTIPS = new HashMap();
        EMOTES_NAMES = new HashMap();
        EMOTES_SYMBOLS = new HashMap();
        ANIMATIONS = new ArrayList();
        CONFIG = new MachineConfig();
        RELEASE_TYPE = ReleaseType.parseVersion(VERSION);
        RADIO = null;
        URL = null;
        IMAGE_HOLOGRAM = null;
        SPEAKER = new SpeakerBlock();
        INCUBATOR = new IncubatorBlock();
        REPAIR_MACHINE = new RepairMachineBlock();
        MOB_SPAWNER = new MobSpawnerItem();
        CHRISTMAS_DISK = new ChristmasRecord();
        ROTTEN_FOOD = new ItemRottenFood();
        ITEM_BADGE = new ItemBadge(5090).func_77655_b("badgeItem");
        HATBLOCK = new HatBlock();
        PORTAL = new PortalBlock();
        PORTAL_ITEM = new ItemBlockPortal(PORTAL);
        resoucesZones = new HashMap();
        cachedServerType = "";
    }
}

