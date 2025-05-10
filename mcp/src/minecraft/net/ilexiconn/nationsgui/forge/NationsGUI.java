package net.ilexiconn.nationsgui.forge;

import co.uk.flansmods.common.EntityParachute;
import co.uk.flansmods.common.ToolType;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerAboutToStartEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.common.network.NetworkMod.SidedPacketHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import fr.nationsglory.ngupgrades.common.world.WorldBorder;
import fr.nationsglory.ngupgrades.common.world.WorldExtension;
import fr.nationsglory.server.block.entity.FeederWaterBlockEntity;
import fr.nationsglory.server.block.entity.GCSiloBlockEntity;
import fr.nationsglory.server.block.entity.GCTraderBlockEntity;
import icbm.explosion.entities.EntityMissile;
import icbm.explosion.explosive.blast.BlastNuclear;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.ilexiconn.nationsgui.forge.client.voices.networking.ClientPacketHandler;
import net.ilexiconn.nationsgui.forge.server.ModBlocks;
import net.ilexiconn.nationsgui.forge.server.ModItems;
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
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
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

@NetworkMod(
    serverSideRequired = true,
    clientSideRequired = true,
    channels = {"nationsgui"},
    packetHandler = PacketHandler.class,
    versionBounds = "[1.2.0,)",
    clientPacketHandlerSpec =       @SidedPacketHandler(
                                        channels = {"GVC-SMPL", "GVC-FORCEB", "GVC-SMPLE", "GVC-ED", "GVC-E", "GVC-VSA", "GVC-VS", "GVC-CHSMPL"},
                                        packetHandler = ClientPacketHandler.class
                                    ),
    serverPacketHandlerSpec =       @SidedPacketHandler(
                                        channels = {"GVC-SMPL", "GVC-SMPLE", "GVC-VS"},
                                        packetHandler = CommonPacketHandler.class
                                    )
)
@Mod(
    modid = "nationsgui",
    name = "NationsGUI",
    version = "1.8.0"
)
public class NationsGUI
{
    @SidedProxy(
        serverSide = "net.ilexiconn.nationsgui.forge.server.ServerProxy",
        clientSide = "net.ilexiconn.nationsgui.forge.client.ClientProxy"
    )
    public static ServerProxy PROXY;
    @Instance("nationsgui")
    public static NationsGUI INSTANCE;
    public static RemoteButtonJSON[] REMOTE_BUTTONS;
    public static Map<String, List<String>> CONTROL_BLACKLIST;
    public static List<String> CRAFTING_BLACKLIST;
    public static HashMap<String, ResourceLocation> BADGES_RESOURCES = new HashMap();
    public static HashMap<String, List<String>> BADGES_TOOLTIPS = new HashMap();
    public static HashMap<String, String> BADGES_NAMES = new HashMap();
    public static HashMap<String, ResourceLocation> EMOTES_RESOURCES = new HashMap();
    public static HashMap<String, ResourceLocation> ANIMATIONS_RESOURCES = new HashMap();
    public static HashMap<String, List<String>> EMOTES_TOOLTIPS = new HashMap();
    public static HashMap<String, String> EMOTES_NAMES = new HashMap();
    public static HashMap<String, String> EMOTES_SYMBOLS = new HashMap();
    public static ArrayList<String> ANIMATIONS = new ArrayList();
    public static MachineConfig CONFIG = new MachineConfig();
    public static final String MODID = "nationsgui";
    public static final String VERSION = "1.8.0";
    public static final ReleaseType RELEASE_TYPE = ReleaseType.parseVersion("1.8.0");
    public static final String HATS_BASE_LINK = "https://apiv2.nationsglory.fr/json/hats/";
    public static final String CAPES_BASE_LINK = "https://apiv2.nationsglory.fr/json/capes/";
    private static VoiceChat chat;
    public static HashMap<String, Integer> resoucesZones = new HashMap();
    public static String cachedServerType = "";

    public static void addTraderPoint(String enterpriseName, int point)
    {
        Plugin pl = Bukkit.getPluginManager().getPlugin("NationsJob");

        if (pl != null)
        {
            try
            {
                Method e = pl.getClass().getDeclaredMethod("addTraderPoint", new Class[] {String.class, Integer.TYPE});
                e.invoke(pl, new Object[] {enterpriseName, Integer.valueOf(point)});
            }
            catch (InvocationTargetException var4)
            {
                var4.printStackTrace();
            }
        }
    }

    public static float withdrawPetrolInCoords(String world, int posX, int posZ, int amount)
    {
        Plugin pl = Bukkit.getPluginManager().getPlugin("NationsUtils");

        if (pl != null)
        {
            try
            {
                Method e = pl.getClass().getDeclaredMethod("withdrawPetrolInCoords", new Class[] {String.class, Integer.TYPE, Integer.TYPE, Integer.TYPE});
                return ((Float)e.invoke(pl, new Object[] {world, Integer.valueOf(posX), Integer.valueOf(posZ), Integer.valueOf(amount)})).floatValue();
            }
            catch (InvocationTargetException var6)
            {
                var6.printStackTrace();
            }
        }

        return 1.0F;
    }

    public static float withdrawGasInCoords(String world, int posX, int posZ, int amount)
    {
        Plugin pl = Bukkit.getPluginManager().getPlugin("NationsUtils");

        if (pl != null)
        {
            try
            {
                Method e = pl.getClass().getDeclaredMethod("withdrawGasInCoords", new Class[] {String.class, Integer.TYPE, Integer.TYPE, Integer.TYPE});
                return ((Float)e.invoke(pl, new Object[] {world, Integer.valueOf(posX), Integer.valueOf(posZ), Integer.valueOf(amount)})).floatValue();
            }
            catch (InvocationTargetException var6)
            {
                var6.printStackTrace();
            }
        }

        return 1.0F;
    }

    public static void storeEnergy(String enterpriseName, int energy)
    {
        Plugin pl = Bukkit.getPluginManager().getPlugin("NationsJob");

        if (pl != null)
        {
            try
            {
                Method e = pl.getClass().getDeclaredMethod("storeEnergy", new Class[] {String.class, Integer.TYPE});
                e.invoke(pl, new Object[] {enterpriseName, Integer.valueOf(energy)});
            }
            catch (InvocationTargetException var4)
            {
                var4.printStackTrace();
            }
        }
    }

    public static float withdrawEnergy(String enterpriseName, int energy, int posX, int posZ)
    {
        Plugin pl = Bukkit.getPluginManager().getPlugin("NationsJob");

        if (pl != null)
        {
            try
            {
                Method e = pl.getClass().getDeclaredMethod("withdrawEnergy", new Class[] {String.class, Integer.TYPE, Integer.TYPE, Integer.TYPE});
                return ((Float)e.invoke(pl, new Object[] {enterpriseName, Integer.valueOf(energy), Integer.valueOf(posX), Integer.valueOf(posZ)})).floatValue();
            }
            catch (InvocationTargetException var6)
            {
                var6.printStackTrace();
            }
        }

        return 0.0F;
    }

    public static boolean canPlayerTalk(String playerName)
    {
        Plugin pl = Bukkit.getPluginManager().getPlugin("NationsGUI");

        if (pl != null)
        {
            try
            {
                Method e = pl.getClass().getDeclaredMethod("canPlayerTalk", new Class[] {String.class});
                return ((Boolean)e.invoke(pl, new Object[] {playerName})).booleanValue();
            }
            catch (InvocationTargetException var3)
            {
                var3.printStackTrace();
            }
        }

        return false;
    }

    public static String getFactionNameFromId(String factionId)
    {
        Plugin pl = Bukkit.getPluginManager().getPlugin("NationsGUI");

        if (pl != null)
        {
            try
            {
                Method e = pl.getClass().getDeclaredMethod("getFactionNameFromId", new Class[] {String.class});
                return (String)e.invoke(pl, new Object[] {factionId});
            }
            catch (InvocationTargetException var3)
            {
                var3.printStackTrace();
            }
        }

        return factionId;
    }

    public static boolean canPlayerHear(String playerName, String playerNameSpeaking)
    {
        Plugin pl = Bukkit.getPluginManager().getPlugin("NationsGUI");

        if (pl != null)
        {
            try
            {
                Method e = pl.getClass().getDeclaredMethod("canPlayerHear", new Class[] {String.class, String.class});
                return ((Boolean)e.invoke(pl, new Object[] {playerName, playerNameSpeaking})).booleanValue();
            }
            catch (InvocationTargetException var4)
            {
                var4.printStackTrace();
            }
        }

        return false;
    }

    public static void sendHawkEyeLog(String action, String player, String location, String data)
    {
        Plugin pl = Bukkit.getPluginManager().getPlugin("NationsGUI");

        if (pl != null)
        {
            try
            {
                Method e = pl.getClass().getDeclaredMethod("sendHawkEyeLog", new Class[] {String.class, String.class, String.class, String.class});
                e.invoke(pl, new Object[] {action, player, location, data});
            }
            catch (InvocationTargetException var6)
            {
                var6.printStackTrace();
            }
        }
    }

    public static String islandsChangePlateTeam(String playerName, int posX, int posY, int posZ, String currentTeam, boolean isSpawn)
    {
        Plugin pl = Bukkit.getPluginManager().getPlugin("NationsBuild");

        if (pl != null)
        {
            try
            {
                Method e = pl.getClass().getDeclaredMethod("changePlateTeam", new Class[] {String.class, Integer.TYPE, Integer.TYPE, Integer.TYPE, String.class, Boolean.TYPE});
                return (String)e.invoke(pl, new Object[] {playerName, Integer.valueOf(posX), Integer.valueOf(posY), Integer.valueOf(posZ), currentTeam, Boolean.valueOf(isSpawn)});
            }
            catch (InvocationTargetException var8)
            {
                var8.printStackTrace();
            }
        }

        return "";
    }

    public static String islandsRemovePlateTeam(String worldName, int posX, int posY, int posZ, String currentTeam)
    {
        Plugin pl = Bukkit.getPluginManager().getPlugin("NationsBuild");

        if (pl != null)
        {
            try
            {
                Method e = pl.getClass().getDeclaredMethod("removePlateTeam", new Class[] {String.class, Integer.TYPE, Integer.TYPE, Integer.TYPE, String.class});
                return (String)e.invoke(pl, new Object[] {worldName, Integer.valueOf(posX), Integer.valueOf(posY), Integer.valueOf(posZ), currentTeam});
            }
            catch (InvocationTargetException var7)
            {
                var7.printStackTrace();
            }
        }

        return "";
    }

    public static boolean islandsCheckPermission(String worldName, String playerName, String permission)
    {
        Plugin pl = Bukkit.getPluginManager().getPlugin("NationsBuild");

        if (pl != null)
        {
            try
            {
                Method e = pl.getClass().getDeclaredMethod("checkPermission", new Class[] {String.class, String.class, String.class});
                return ((Boolean)e.invoke(pl, new Object[] {worldName, playerName, permission})).booleanValue();
            }
            catch (InvocationTargetException var5)
            {
                var5.printStackTrace();
            }
        }

        return false;
    }

    public static void islandsPlayerJoinTeam(String worldName, String playerName, String teamName)
    {
        Plugin pl = Bukkit.getPluginManager().getPlugin("NationsBuild");

        if (pl != null)
        {
            try
            {
                Method e = pl.getClass().getDeclaredMethod("playerJoinTeam", new Class[] {String.class, String.class, String.class});
                e.invoke(pl, new Object[] {worldName, playerName, teamName});
            }
            catch (InvocationTargetException var5)
            {
                var5.printStackTrace();
            }
        }
    }

    public static void islandsCaptureZone(String worldName, ArrayList<String> players)
    {
        Plugin pl = Bukkit.getPluginManager().getPlugin("NationsBuild");

        if (pl != null)
        {
            try
            {
                Method e = pl.getClass().getDeclaredMethod("captureZone", new Class[] {String.class, ArrayList.class});
                e.invoke(pl, new Object[] {worldName, players});
            }
            catch (InvocationTargetException var4)
            {
                var4.printStackTrace();
            }
        }
    }

    public static void islandsFootGoal(String worldName, int posX, int posY, int posZ, List<String> lastPlayersHit, String goalTeam)
    {
        Plugin pl = Bukkit.getPluginManager().getPlugin("NationsBuild");

        if (pl != null)
        {
            try
            {
                Method e = pl.getClass().getDeclaredMethod("footGoal", new Class[] {String.class, Integer.TYPE, Integer.TYPE, Integer.TYPE, List.class, String.class});
                e.invoke(pl, new Object[] {worldName, Integer.valueOf(posX), Integer.valueOf(posY), Integer.valueOf(posZ), lastPlayersHit, goalTeam});
            }
            catch (InvocationTargetException var8)
            {
                var8.printStackTrace();
            }
        }
    }

    public static Map<String, Integer> islandsFootGetScores(String worldName)
    {
        Plugin pl = Bukkit.getPluginManager().getPlugin("NationsBuild");

        if (pl != null)
        {
            try
            {
                Method e = pl.getClass().getDeclaredMethod("footGetScores", new Class[] {String.class});
                return (Map)e.invoke(pl, new Object[] {worldName});
            }
            catch (InvocationTargetException var3)
            {
                var3.printStackTrace();
            }
        }

        return new HashMap();
    }

    public static void printFactionChestContent(String factionId, String factionName)
    {
        NBTTagCompound compound = (NBTTagCompound)NBTConfig.CONFIG.getCompound().getTag("FactionChest");
        System.out.println(Translation.get("--- COFFRE DE PAYS AVANT DISBAND DE ") + factionName + " ---");
        NBTTagList itemsTag = compound.getTagList(factionId);

        for (int i = 0; i < itemsTag.tagCount(); ++i)
        {
            NBTTagCompound itemTag = (NBTTagCompound)itemsTag.tagAt(i);
            ItemStack item = ItemStack.loadItemStackFromNBT(itemTag);
            System.out.println(item.itemID + ":" + item.getItemDamage() + " x" + item.stackSize);
        }

        System.out.println("---------------------------");
    }

    public static List<String> getOGMRecipe(String ogmName)
    {
        Plugin pl = Bukkit.getPluginManager().getPlugin("NationsUtils");

        if (pl != null)
        {
            try
            {
                Method e = pl.getClass().getDeclaredMethod("getOGMRecipe", new Class[] {String.class});
                return (List)e.invoke(pl, new Object[] {ogmName});
            }
            catch (InvocationTargetException var3)
            {
                var3.printStackTrace();
            }
        }

        return null;
    }

    public static void islandsSaveJumpPosition(String position, String positionType, Long time)
    {
        PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new IslandsSaveJumpPlayerPacket(position, positionType, time.longValue())));
    }

    public static void countOGMREcipe(String ogmName, boolean success, int percent)
    {
        Plugin pl = Bukkit.getPluginManager().getPlugin("NationsUtils");

        if (pl != null)
        {
            try
            {
                Method e = pl.getClass().getDeclaredMethod("countOGMRecipe", new Class[] {String.class, Boolean.TYPE, Integer.TYPE});
                e.invoke(pl, new Object[] {ogmName, Boolean.valueOf(success), Integer.valueOf(percent)});
            }
            catch (InvocationTargetException var5)
            {
                var5.printStackTrace();
            }
        }
    }

    public static boolean canPlayerDriveVehicle(String targetPlayerName, String ownerName, boolean allowFaction, boolean allowEnterprise)
    {
        Plugin pl = Bukkit.getPluginManager().getPlugin("NationsUtils");

        if (pl != null)
        {
            try
            {
                Method e = pl.getClass().getDeclaredMethod("canPlayerUseVehicle", new Class[] {String.class, String.class, Boolean.TYPE, Boolean.TYPE});
                return ((Boolean)e.invoke(pl, new Object[] {targetPlayerName, ownerName, Boolean.valueOf(allowFaction), Boolean.valueOf(allowEnterprise)})).booleanValue();
            }
            catch (InvocationTargetException var6)
            {
                var6.printStackTrace();
            }
        }

        return false;
    }

    public static void sendPlayerPacketTitle(Player player, String title, String subtitle, int duration)
    {
        PacketDispatcher.sendPacketToPlayer(PacketRegistry.INSTANCE.generatePacket(new TitlePacket(new Title(title.replace('&', '\u00a7'), subtitle.replace('&', '\u00a7'), (float)(duration * 20)))), player);
    }

    public static void playEmoteForPlayers(ArrayList<Player> players, ArrayList<String> playersName, String emote)
    {
        for (int i = 0; i < players.size(); ++i)
        {
            PacketDispatcher.sendPacketToPlayer(PacketRegistry.INSTANCE.generatePacket(new PacketEmote(emote, (String)playersName.get(i))), (Player)players.get(i));
        }
    }

    public static boolean canPlayerInteractVehicle(String targetPlayerName, String worldName, double posX, double posY, double posZ)
    {
        Plugin pl = Bukkit.getPluginManager().getPlugin("NationsUtils");

        if (pl != null)
        {
            try
            {
                Method e = pl.getClass().getDeclaredMethod("canPlayerInteractVehicle", new Class[] {String.class, String.class, Double.TYPE, Double.TYPE, Double.TYPE});
                return ((Boolean)e.invoke(pl, new Object[] {targetPlayerName, worldName, Double.valueOf(posX), Double.valueOf(posY), Double.valueOf(posZ)})).booleanValue();
            }
            catch (InvocationTargetException var10)
            {
                var10.printStackTrace();
            }
        }

        return false;
    }

    public static void addWaterInFeeder(String worldName, Integer posX, Integer posY, Integer posZ)
    {
        WorldServer world = null;
        WorldServer[] tile = MinecraftServer.getServer().worldServers;
        int var6 = tile.length;

        for (int var7 = 0; var7 < var6; ++var7)
        {
            WorldServer worldServer = tile[var7];

            if (worldServer.getWorldInfo().getWorldName().equalsIgnoreCase(worldName))
            {
                world = worldServer;
            }
        }

        if (world != null)
        {
            TileEntity var9 = world.getBlockTileEntity(posX.intValue(), posY.intValue(), posZ.intValue());

            if (var9 != null && var9 instanceof FeederWaterBlockEntity && ((FeederWaterBlockEntity)var9).quantity < FeederWaterBlockEntity.MAX_QTE)
            {
                ((FeederWaterBlockEntity)var9).quantity = Math.min(FeederWaterBlockEntity.MAX_QTE, ((FeederWaterBlockEntity)var9).quantity + 5);
            }
        }
    }

    public static boolean canCropTick(String cerealName, String worldName, int posX, int posZ)
    {
        Plugin pl = Bukkit.getPluginManager().getPlugin("NationsUtils");

        if (pl != null)
        {
            try
            {
                Method e = pl.getClass().getDeclaredMethod("canCropTick", new Class[] {String.class, String.class, Integer.TYPE, Integer.TYPE});
                return ((Boolean)e.invoke(pl, new Object[] {cerealName, worldName, Integer.valueOf(posX), Integer.valueOf(posZ)})).booleanValue();
            }
            catch (InvocationTargetException var6)
            {
                var6.printStackTrace();
            }
        }

        return false;
    }

    public static int repairVehicle(String playerName, int entityID)
    {
        Plugin pl = Bukkit.getPluginManager().getPlugin("NationsUtils");

        if (pl != null)
        {
            try
            {
                Method e = pl.getClass().getDeclaredMethod("repairVehicle", new Class[] {String.class, Integer.TYPE});
                return ((Integer)e.invoke(pl, new Object[] {playerName, Integer.valueOf(entityID)})).intValue();
            }
            catch (InvocationTargetException var4)
            {
                var4.printStackTrace();
            }
        }

        return 0;
    }

    public static void removePlayerBadge(String playerName, String badgeName)
    {
        Plugin pl = Bukkit.getPluginManager().getPlugin("NationsUtils");

        if (pl != null)
        {
            try
            {
                Method e = pl.getClass().getDeclaredMethod("removePlayerBadge", new Class[] {String.class, String.class});
                e.invoke(pl, new Object[] {playerName, badgeName});
            }
            catch (InvocationTargetException var4)
            {
                var4.printStackTrace();
            }
        }
    }

    public static void spawnOffPlayer(String playerName)
    {
        Plugin pl = Bukkit.getPluginManager().getPlugin("NationsGUI");

        if (pl != null)
        {
            try
            {
                Method e = pl.getClass().getDeclaredMethod("spawnOffPlayer", new Class[] {String.class});
                e.invoke(pl, new Object[] {playerName});
            }
            catch (InvocationTargetException var3)
            {
                var3.printStackTrace();
            }
        }
    }

    public static void openCountryChest(EntityPlayer player, String factionId, int chestLevel)
    {
        Plugin pl = Bukkit.getPluginManager().getPlugin("NationsGUI");

        if (pl != null)
        {
            try
            {
                Method e = pl.getClass().getDeclaredMethod("canPlayerOpenCountryChest", new Class[] {String.class, String.class});
                boolean res = ((Boolean)e.invoke(pl, new Object[] {player.username, factionId})).booleanValue();

                if (res)
                {
                    RemoteOpenFactionChestPacket.openedChestsByPlayer.put(factionId, player.getDisplayName());
                    RemoteOpenFactionChestPacket.playersTargetGui.put(player.getDisplayName(), factionId);
                    RemoteOpenFactionChestPacket.playersCanTake.put(player.getDisplayName(), Boolean.valueOf(true));
                    RemoteOpenFactionChestPacket.playersCanDeposit.put(player.getDisplayName(), Boolean.valueOf(true));
                    RemoteOpenFactionChestPacket.playersChestLevel.put(player.getDisplayName(), Integer.valueOf(chestLevel));
                    player.openGui(INSTANCE, 666, player.worldObj, (int)player.posX, (int)player.posY, (int)player.posZ);
                }
            }
            catch (InvocationTargetException var6)
            {
                var6.printStackTrace();
            }
        }
    }

    public static void refreshBadgesSite(String player, String badges)
    {
        try
        {
            Class.forName("org.bukkit.Bukkit");
            Plugin pl = Bukkit.getPluginManager().getPlugin("NationsGUI");

            if (pl != null)
            {
                try
                {
                    Method e = pl.getClass().getDeclaredMethod("refreshBadgesSite", new Class[] {String.class, String.class});
                    e.invoke(pl, new Object[] {player, badges});
                }
                catch (InvocationTargetException var4)
                {
                    var4.printStackTrace();
                }
            }
        }
        catch (ClassNotFoundException var5)
        {
            ;
        }
    }

    public static boolean canPlayerModifyBlock(String playerName, int blockX, int blockY, int blockZ, String worldName)
    {
        Plugin pl = Bukkit.getPluginManager().getPlugin("NationsGUI");

        if (pl != null)
        {
            try
            {
                Method e = pl.getClass().getDeclaredMethod("canPlayerModifyBlock", new Class[] {String.class, Integer.TYPE, Integer.TYPE, Integer.TYPE, String.class});
                return ((Boolean)e.invoke(pl, new Object[] {playerName, Integer.valueOf(blockX), Integer.valueOf(blockY), Integer.valueOf(blockZ), worldName})).booleanValue();
            }
            catch (InvocationTargetException var7)
            {
                var7.printStackTrace();
            }
        }

        return false;
    }

    public static boolean sendSocketMessage(String message)
    {
        Plugin pl = Bukkit.getPluginManager().getPlugin("NationsGUI");

        if (pl != null)
        {
            try
            {
                Method e = pl.getClass().getDeclaredMethod("sendSocketMessage", new Class[] {String.class});
                return ((Boolean)e.invoke(pl, new Object[] {message})).booleanValue();
            }
            catch (InvocationTargetException var3)
            {
                var3.printStackTrace();
            }
        }

        return false;
    }

    public static boolean canPlayerUseSpecialAttack(String playerName, int blockX, int blockY, int blockZ, String worldName)
    {
        Plugin pl = Bukkit.getPluginManager().getPlugin("NationsGUI");

        if (pl != null)
        {
            try
            {
                Method e = pl.getClass().getDeclaredMethod("canPlayerUseSpecialAttack", new Class[] {String.class, Integer.TYPE, Integer.TYPE, Integer.TYPE, String.class});
                return ((Boolean)e.invoke(pl, new Object[] {playerName, Integer.valueOf(blockX), Integer.valueOf(blockY), Integer.valueOf(blockZ), worldName})).booleanValue();
            }
            catch (InvocationTargetException var7)
            {
                var7.printStackTrace();
            }
        }

        return false;
    }

    public static int getPlayerSkillLevel(String playerName, String skillName)
    {
        Plugin pl = Bukkit.getPluginManager().getPlugin("NationsJob");

        if (pl != null)
        {
            try
            {
                Method e = pl.getClass().getDeclaredMethod("getPlayerSkillLevel", new Class[] {String.class, String.class});
                return ((Integer)e.invoke(pl, new Object[] {playerName, skillName})).intValue();
            }
            catch (InvocationTargetException var4)
            {
                var4.printStackTrace();
            }
        }

        return 0;
    }

    public static HashMap<String, HashMap<String, Object>> getPlayerSkillsInfos(String playerName)
    {
        Plugin pl = Bukkit.getPluginManager().getPlugin("NationsJob");

        if (pl != null)
        {
            try
            {
                Method e = pl.getClass().getDeclaredMethod("getPlayerSkillsInfos", new Class[] {String.class});
                return (HashMap)e.invoke(pl, new Object[] {playerName});
            }
            catch (InvocationTargetException var3)
            {
                var3.printStackTrace();
            }
        }

        return new HashMap();
    }

    public static void spawnCarePackage(String defenserName, int x, int y, int z, int defenserLevel, int nbAtt, int nbDef) {}

    public static void updateWaitingSize(int size)
    {
        NationsGUIHooks.waitingSize = size;
    }

    public static void addIpToAllowConnection(String ip)
    {
        NationsGUIHooks.allowedIPConnection.add(ip.split(":")[0]);
        System.out.println("DEBUG Add IP to allowed connection: " + ip);
    }

    public static HashMap<String, String> getTopPlayersSkills()
    {
        Plugin pl = Bukkit.getPluginManager().getPlugin("NationsJob");

        if (pl != null)
        {
            try
            {
                Method e = pl.getClass().getDeclaredMethod("getTopPlayersSkills", new Class[0]);
                return (HashMap)e.invoke(pl, new Object[0]);
            }
            catch (InvocationTargetException var2)
            {
                var2.printStackTrace();
            }
        }

        return new HashMap();
    }

    public static void setPlayerAvailableBadges(String player, String badges)
    {
        NBTTagCompound compound = (NBTTagCompound)NBTConfig.CONFIG.getCompound().getTag("Badges");
        NBTTagCompound user = compound.getCompoundTag(player);

        if (!user.hasKey("AvailableBadges"))
        {
            user.setTag("AvailableBadges", new NBTTagString(""));
        }

        user.setString("AvailableBadges", badges);
        compound.setCompoundTag(player, user);
        NBTConfig.CONFIG.save();
    }

    public static void addPlayerSkill(String playerName, String skillName, int points)
    {
        Plugin pl = Bukkit.getPluginManager().getPlugin("NationsJob");

        if (pl != null)
        {
            try
            {
                Method e = pl.getClass().getDeclaredMethod("addPlayerSkill", new Class[] {String.class, String.class, Integer.TYPE});
                e.invoke(pl, new Object[] {playerName, skillName, Integer.valueOf(points)});
            }
            catch (InvocationTargetException var5)
            {
                var5.printStackTrace();
            }
        }
    }

    public static boolean canPlayerHaveDoubleLoot(String playerName, String skillName)
    {
        Plugin pl = Bukkit.getPluginManager().getPlugin("NationsJob");

        if (pl != null)
        {
            try
            {
                Method e = pl.getClass().getDeclaredMethod("canPlayerHaveDoubleLoot", new Class[] {String.class, String.class});
                return ((Boolean)e.invoke(pl, new Object[] {playerName, skillName})).booleanValue();
            }
            catch (InvocationTargetException var4)
            {
                var4.printStackTrace();
            }
        }

        return false;
    }

    public static boolean canPlayerContractContagious(String playerName)
    {
        EntityPlayerMP playerForUsername = MinecraftServer.getServer().getConfigurationManager().getPlayerForUsername(playerName);

        if (playerForUsername != null && playerForUsername.isPotionActive(43))
        {
            return false;
        }
        else
        {
            Plugin pl = Bukkit.getPluginManager().getPlugin("NationsGUI");

            if (pl != null)
            {
                try
                {
                    Method e = pl.getClass().getDeclaredMethod("canPlayerContractContagious", new Class[] {String.class});
                    return ((Boolean)e.invoke(pl, new Object[] {playerName})).booleanValue();
                }
                catch (InvocationTargetException var4)
                {
                    var4.printStackTrace();
                }
            }

            return false;
        }
    }

    public static String getServerType()
    {
        if (!cachedServerType.isEmpty())
        {
            return cachedServerType;
        }
        else
        {
            try
            {
                Class.forName("org.bukkit.Bukkit");

                if (Bukkit.getServer() != null)
                {
                    Plugin e = Bukkit.getPluginManager().getPlugin("NationsGUI");

                    if (e != null)
                    {
                        try
                        {
                            Method e1 = e.getClass().getDeclaredMethod("getServerType", new Class[0]);
                            String serverType = (String)e1.invoke(e, new Object[0]);

                            if (serverType != null)
                            {
                                cachedServerType = serverType;
                            }

                            return cachedServerType;
                        }
                        catch (InvocationTargetException var3)
                        {
                            var3.printStackTrace();
                        }
                    }
                }
            }
            catch (ClassNotFoundException var4)
            {
                var4.printStackTrace();
            }

            return "";
        }
    }

    public static Long isMachineOnMagneticArea(String worldName, int x, int y, int z)
    {
        try
        {
            Class.forName("org.bukkit.Bukkit");

            if (Bukkit.getServer() != null)
            {
                Plugin e = Bukkit.getPluginManager().getPlugin("NationsGUI");

                if (e != null)
                {
                    try
                    {
                        Method e1 = e.getClass().getDeclaredMethod("isMachineOnMagneticArea", new Class[] {String.class, Integer.class, Integer.class, Integer.class});
                        return (Long)e1.invoke(e, new Object[] {worldName, Integer.valueOf(x), Integer.valueOf(y), Integer.valueOf(z)});
                    }
                    catch (InvocationTargetException var6)
                    {
                        var6.printStackTrace();
                    }
                }
            }
        }
        catch (ClassNotFoundException var7)
        {
            var7.printStackTrace();
        }

        return Long.valueOf(0L);
    }

    public static void teleportPlayerToLocation(String playerName, String worldNameOrDimensionID, int x, int y, int z, float yaw, float pitch)
    {
        try
        {
            Class.forName("org.bukkit.Bukkit");

            if (Bukkit.getServer() != null)
            {
                Plugin e = Bukkit.getPluginManager().getPlugin("NationsGUI");

                if (e != null)
                {
                    try
                    {
                        String e1 = worldNameOrDimensionID;

                        try
                        {
                            int m = Integer.parseInt(worldNameOrDimensionID);
                            WorldServer forgeWorld = MinecraftServer.getServer().worldServerForDimension(m);

                            if (forgeWorld != null)
                            {
                                e1 = forgeWorld.getWorldInfo().getWorldName();
                            }
                        }
                        catch (NumberFormatException var11)
                        {
                            ;
                        }

                        Method m1 = e.getClass().getDeclaredMethod("teleportPlayerToLocation", new Class[] {String.class, String.class, Integer.class, Integer.class, Integer.class, Float.class, Float.class});
                        m1.invoke(e, new Object[] {playerName, e1, Integer.valueOf(x), Integer.valueOf(y), Integer.valueOf(z), Float.valueOf(yaw), Float.valueOf(pitch)});
                    }
                    catch (InvocationTargetException var12)
                    {
                        var12.printStackTrace();
                    }
                }
            }
        }
        catch (ClassNotFoundException var13)
        {
            var13.printStackTrace();
        }
    }

    public static void spawnMissileAt(int x, int y, int z, String worldName, int missileID, String launcher)
    {
        WorldServer world = null;
        WorldServer[] worldObj = MinecraftServer.getServer().worldServers;
        int missile = worldObj.length;

        for (int entityPlayerMP = 0; entityPlayerMP < missile; ++entityPlayerMP)
        {
            WorldServer worldServer = worldObj[entityPlayerMP];

            if (worldServer.getWorldInfo().getWorldName().equals(worldName))
            {
                world = worldServer;
            }
        }

        if (world != null)
        {
            World var11 = world.provider.worldObj;
            EntityMissile var12 = new EntityMissile(var11, new Vector3((double)x, (double)y, (double)z), missileID, 0.0F, 0.0F);
            EntityPlayerMP var13 = MinecraftServer.getServer().getConfigurationManager().getPlayerForUsername(launcher);
            var12.setPlayerLauncher(var13);
            var12.setFromPlatform(false);
            var11.spawnEntityInWorld(var12);
            var12.launch(new Vector3((double)x, 10.0D, (double)z));
        }
    }

    public static void placeMobSpawnerAt(String world, int x, int y, int z, String mobId)
    {
        WorldServer worldServer = null;
        WorldServer[] var8 = MinecraftServer.getServer().worldServers;
        int var7 = var8.length;

        for (int var81 = 0; var81 < var7; ++var81)
        {
            WorldServer worldServer1 = var8[var81];

            if (worldServer1.getWorldInfo().getWorldName().equals(world))
            {
                worldServer = worldServer1;
            }
        }

        if (worldServer != null)
        {
            worldServer.setBlock(x, y, z, Block.mobSpawner.blockID, 0, 3);
            TileEntityMobSpawner var10 = (TileEntityMobSpawner)worldServer.getBlockTileEntity(x, y, z);

            if (var10 != null)
            {
                var10.getSpawnerLogic().setMobID(mobId);
            }
        }
    }

    public static void createBlastExplosionAt(int x, int y, int z, String worldName, float size, float energy)
    {
        WorldServer world = null;
        WorldServer[] var7 = MinecraftServer.getServer().worldServers;
        int var8 = var7.length;

        for (int var9 = 0; var9 < var8; ++var9)
        {
            WorldServer worldServer = var7[var9];

            if (worldServer.getWorldInfo().getWorldName().equals(worldName))
            {
                world = worldServer;
            }
        }

        if (world != null)
        {
            (new BlastNuclear(world, (Entity)null, (double)x, (double)y, (double)z, size, energy)).explode();
        }
    }

    public static void registerBlockExplosion(String worldName, int x, int y, int z, int blockId, int blockMeta)
    {
        try
        {
            Class.forName("org.bukkit.Bukkit");

            if (Bukkit.getServer() != null)
            {
                Plugin e = Bukkit.getPluginManager().getPlugin("NationsGUI");

                if (e != null)
                {
                    try
                    {
                        Method e1 = e.getClass().getDeclaredMethod("registerBlockExplosion", new Class[] {String.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class});
                        e1.invoke(e, new Object[] {worldName, Integer.valueOf(x), Integer.valueOf(y), Integer.valueOf(z), Integer.valueOf(blockId), Integer.valueOf(blockMeta)});
                    }
                    catch (InvocationTargetException var8)
                    {
                        var8.printStackTrace();
                    }
                }
            }
        }
        catch (ClassNotFoundException var9)
        {
            var9.printStackTrace();
        }
    }

    public static boolean isRadiusSet(String str)
    {
        try
        {
            Class e = Class.forName("main.java.fr.ng.ibalix.nationsutils.CommonHelper");

            if (Bukkit.getServer() != null)
            {
                try
                {
                    Method e1 = e.getDeclaredMethod("isRadiusSet", new Class[] {String.class});
                    return ((Boolean)e1.invoke(e, new Object[] {str})).booleanValue();
                }
                catch (InvocationTargetException var3)
                {
                    var3.printStackTrace();
                }
            }
        }
        catch (ClassNotFoundException var4)
        {
            var4.printStackTrace();
        }

        return false;
    }

    public static int getRadius(String str)
    {
        try
        {
            Class.forName("org.bukkit.Bukkit");

            if (Bukkit.getServer() != null)
            {
                Plugin e = Bukkit.getPluginManager().getPlugin("NationsGUI");

                if (e != null)
                {
                    try
                    {
                        Method e1 = e.getClass().getDeclaredMethod("getServerType", new Class[] {String.class});
                        return ((Integer)e1.invoke(e, new Object[] {str})).intValue();
                    }
                    catch (InvocationTargetException var3)
                    {
                        var3.printStackTrace();
                    }
                }
            }
        }
        catch (ClassNotFoundException var4)
        {
            var4.printStackTrace();
        }

        return 0;
    }

    public static boolean isPlayerImmuniseFromMissile(String playerName)
    {
        Plugin pl = Bukkit.getPluginManager().getPlugin("NationsGUI");

        if (pl != null)
        {
            try
            {
                Method e = pl.getClass().getDeclaredMethod("isPlayerImmuniseFromMissile", new Class[] {String.class});
                return ((Boolean)e.invoke(pl, new Object[] {playerName})).booleanValue();
            }
            catch (InvocationTargetException var3)
            {
                var3.printStackTrace();
            }
        }

        return false;
    }

    public static void executePlayerCmd(String playerName, String cmd)
    {
        Plugin pl = Bukkit.getPluginManager().getPlugin("NationsGUI");

        if (pl != null)
        {
            try
            {
                Method e = pl.getClass().getDeclaredMethod("executePlayerCmd", new Class[] {String.class, String.class});
                e.invoke(pl, new Object[] {playerName, cmd});
            }
            catch (InvocationTargetException var4)
            {
                var4.printStackTrace();
            }
        }
    }

    public static void addSkinToPlayer(String playerName, String skinName)
    {
        Plugin pl = Bukkit.getPluginManager().getPlugin("NationsGUI");

        if (pl != null)
        {
            try
            {
                Method e = pl.getClass().getDeclaredMethod("addSkinToPlayer", new Class[] {String.class, String.class});
                e.invoke(pl, new Object[] {playerName, skinName});
            }
            catch (InvocationTargetException var4)
            {
                var4.printStackTrace();
            }
        }
    }

    public static void setPlayerBlockReach(String playerName, float range)
    {
        EntityPlayerMP entityPlayerMP = MinecraftServer.getServer().getConfigurationManager().getPlayerForUsername(playerName);

        if (entityPlayerMP != null)
        {
            entityPlayerMP.theItemInWorldManager.setBlockReachDistance((double)range);
            PacketDispatcher.sendPacketToPlayer(PacketRegistry.INSTANCE.generatePacket(new BlockReachPacket(range)), (Player)entityPlayerMP);
        }
    }

    public static boolean hasSiloEnoughCereal(int blockX, int blockY, int blockZ, String worldName, String cerealType, int quantity)
    {
        WorldServer world = null;
        WorldServer[] tileEntity = MinecraftServer.getServer().worldServers;
        int var8 = tileEntity.length;

        for (int var9 = 0; var9 < var8; ++var9)
        {
            WorldServer worldServer = tileEntity[var9];

            if (worldServer.getWorldInfo().getWorldName().equalsIgnoreCase(worldName))
            {
                world = worldServer;
            }
        }

        if (world != null)
        {
            TileEntity var11 = world.getBlockTileEntity(blockX, blockY, blockZ);

            if (var11 instanceof GCSiloBlockEntity)
            {
                return ((GCSiloBlockEntity)var11).cerealType.equals(cerealType) && ((GCSiloBlockEntity)var11).quantity >= quantity;
            }
        }

        return false;
    }

    public static void deployPlayerParachute(String playerName)
    {
        EntityPlayerMP entityPlayerMP = MinecraftServer.getServer().getConfigurationManager().getPlayerForUsername(playerName);

        if (entityPlayerMP != null)
        {
            EntityParachute var18 = new EntityParachute(entityPlayerMP.worldObj, ToolType.getType("mwParachute"), entityPlayerMP);
            entityPlayerMP.worldObj.spawnEntityInWorld(var18);
            entityPlayerMP.mountEntity(var18);
        }
    }

    public static void reduceBorder(String worldName, String borderName, double ratio, int duration)
    {
        WorldServer world = null;
        WorldServer[] worldExtension = MinecraftServer.getServer().worldServers;
        int worldBorder = worldExtension.length;

        for (int data = 0; data < worldBorder; ++data)
        {
            WorldServer e = worldExtension[data];

            if (e.getWorldInfo().getWorldName().equalsIgnoreCase(worldName))
            {
                world = e;
            }
        }

        if (world != null)
        {
            WorldExtension var11 = WorldExtension.get(world);

            if (var11.getWorldBorders().containsKey(borderName))
            {
                ratio /= 100.0D;
                WorldBorder var12 = (WorldBorder)var11.getWorldBorders().get(borderName);

                if (ratio <= 1.0D)
                {
                    var12.setWantedSafeRatio(ratio);
                    var12.setReducingTickRate(duration);
                }

                var11.markDirty();
                ByteArrayDataOutput var13 = ByteStreams.newDataOutput();
                var13.writeInt(3);

                try
                {
                    NBTTagCompound var14 = new NBTTagCompound();
                    var11.writeToNBT(var14);
                    CompressedStreamTools.write(var14, var13);
                    PacketDispatcher.sendPacketToAllPlayers(PacketDispatcher.getPacket("ngupgrades", var13.toByteArray()));
                }
                catch (IOException var10)
                {
                    var10.printStackTrace();
                }
            }
        }
    }

    public static void openLootboxForPlayer(String playerName, String lootboxName, int x, int y, int z, String worldName, boolean animate)
    {
        Plugin pl = Bukkit.getPluginManager().getPlugin("NationsGUI");

        if (pl != null)
        {
            try
            {
                Method e = pl.getClass().getDeclaredMethod("openLootboxForPlayer", new Class[] {String.class, String.class, Integer.TYPE, Integer.TYPE, Integer.TYPE, String.class, Boolean.TYPE});
                e.invoke(pl, new Object[] {playerName, lootboxName, Integer.valueOf(x), Integer.valueOf(y), Integer.valueOf(z), worldName, Boolean.valueOf(animate)});
            }
            catch (InvocationTargetException var9)
            {
                var9.printStackTrace();
            }
        }
    }

    public static String getTradingMachineEnterprise(String worldName, int posX, int posY, int posZ)
    {
        WorldServer world = null;
        WorldServer[] tile = MinecraftServer.getServer().worldServers;
        int var6 = tile.length;

        for (int var7 = 0; var7 < var6; ++var7)
        {
            WorldServer worldServer = tile[var7];

            if (worldServer.getWorldInfo().getWorldName().equalsIgnoreCase(worldName))
            {
                world = worldServer;
            }
        }

        if (world != null)
        {
            TileEntity var9 = world.getBlockTileEntity(posX, posY, posZ);

            if (var9 != null && var9 instanceof GCTraderBlockEntity)
            {
                return ((GCTraderBlockEntity)var9).enterpriseName;
            }
        }

        return null;
    }

    public static void sendEdoraBossOverlayDataToPlayer(EntityPlayer player, HashMap<String, Object> data)
    {
        PacketDispatcher.sendPacketToPlayer(PacketRegistry.INSTANCE.generatePacket(new EdoraBossOverlayDataPacket(data)), (Player)player);
    }

    public static String spawnGeckoEntityAt(int x, int y, int z, String worldName, String entityName, double maxHealth)
    {
        try
        {
            Method e = ServerUtils.class.getMethod("spawnGeckoEntityAt", new Class[] {Integer.TYPE, Integer.TYPE, Integer.TYPE, String.class, String.class, Double.TYPE, Boolean.TYPE, Boolean.TYPE});
            return (String)e.invoke((Object)null, new Object[] {Integer.valueOf(x), Integer.valueOf(y), Integer.valueOf(z), worldName, entityName, Double.valueOf(maxHealth), Boolean.valueOf(false), Boolean.valueOf(false)});
        }
        catch (InvocationTargetException var8)
        {
            var8.printStackTrace();
            return null;
        }
    }

    @EventHandler
    public void onPreInit(FMLPreInitializationEvent event)
    {
        chat = new VoiceChat(event.getSide());
        Capes.refresh();
        chat.preInit(event);
        CONFIG.loadConfig(new Configuration(event.getSuggestedConfigurationFile()));
        PROXY.onPreInit();
        ModItems.init();
        ModBlocks.init();
        JSONRegistry blockRegistry = JSONRegistries.getRegistry(JSONBlock.class);
        JSONRegistry armorRegistry = JSONRegistries.getRegistry(JSONArmorSet.class);

        try
        {
            JSONRegistries.parseJSON(blockRegistry, "https://apiv2.nationsglory.fr/json/moreblocs.json");
            JSONRegistries.parseJSON(armorRegistry, "https://apiv2.nationsglory.fr/json/morearmors.json");
        }
        catch (Exception var5)
        {
            throw new RuntimeException(var5);
        }

        if (event.getSide() == Side.SERVER)
        {
            NetworkRegistry.instance().registerConnectionHandler(new ConnectionHandler());
        }
    }

    @EventHandler
    public void onInit(FMLInitializationEvent event)
    {
        PROXY.onInit();
        chat.init(event);
    }

    @EventHandler
    public void onPostInit(FMLPostInitializationEvent event)
    {
        PROXY.onPostInit();
    }

    @EventHandler
    public void onServerPreInit(FMLServerAboutToStartEvent event)
    {
        chat.preInitServer(event);
    }

    @EventHandler
    public void onServerStarting(FMLServerStartingEvent event)
    {
        chat.initServer(event);
        event.registerServerCommand(new SnackbarCommand());
        event.registerServerCommand(new AnimCommand());
        event.registerServerCommand(new GMOCommand());
        event.registerServerCommand(new COCommand());
        event.registerServerCommand(new POCommand());
        event.registerServerCommand(new PlayMusicCommand());
        event.registerServerCommand(new CookCommand());
        event.registerServerCommand(new WelcomeCommand());
        event.registerServerCommand(new ChangeServerCommand());
        event.registerServerCommand(new JoinWaitingCommand());
        event.registerServerCommand(new CommandConnectPhone());
        event.registerServerCommand(new CommandDisconnectPhone());
        event.registerServerCommand(new CommandPictureFrame());
        event.registerServerCommand(new PingCommand());
        event.registerServerCommand(new NotifyCommand());
        event.registerServerCommand(new CommandTroc());
        event.registerServerCommand(new CommandDebug());
        event.registerServerCommand(new CommandTitle());
        event.registerServerCommand(new CommandEmote());
        event.registerServerCommand(new ClearForceChunksCommand());
        event.registerServerCommand(new MoonCommand());
        event.registerServerCommand(new ParticleCommand());
        event.registerServerCommand(new ScreenPlayerCommand());
        event.registerServerCommand(new SearchIdsCommand());
        event.registerServerCommand(new ViewCommand());
        event.registerServerCommand(new SocketCommand());
        event.registerServerCommand(new SpawnGeckoEntityCommand());
        event.registerServerCommand(new CommandRefill());
        event.registerServerCommand(new SpawnGeckoBikeCommand());
        event.registerServerCommand(new GiveSpecificCommand());
        event.registerServerCommand(new FrameCommand());
        event.registerServerCommand(new EventCommand());
        event.registerServerCommand(new ClearPlayerDataCommand());
        event.registerServerCommand(new SpawnFloatingItemEntityCommand());
    }
}
