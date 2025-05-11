/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParser
 *  com.google.gson.JsonPrimitive
 *  com.google.gson.internal.LinkedTreeMap
 *  cpw.mods.fml.common.Loader
 *  cpw.mods.fml.common.ModContainer
 *  cpw.mods.fml.common.ObfuscationReflectionHelper
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  cpw.mods.fml.common.registry.GameRegistry
 *  cpw.mods.fml.common.registry.GameRegistry$UniqueIdentifier
 *  cpw.mods.fml.relauncher.ReflectionHelper
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  fr.nationsglory.ngcontent.NGContent
 *  fr.nationsglory.ngcontent.client.armor.ItemClientReaperArmor
 *  fr.nationsglory.ngcontent.client.armor.ItemClientSpartanArmor
 *  fr.nationsglory.ngcontent.client.armor.ItemClientVigilanteArmor
 *  fr.nationsglory.ngcontent.server.entity.mobs.IAnimalFarm
 *  fr.nationsglory.ngcontent.server.item.ItemVigilanteSword
 *  fr.nationsglory.ngcontent.server.potion.PotionParanoia
 *  fr.nationsglory.ngupgrades.common.entity.CarePackageEntity
 *  fr.nationsglory.ngupgrades.common.entity.EntityMobKey
 *  fr.nationsglory.ngupgrades.common.entity.GenericGeckoBikeFlyingEntity
 *  fr.nationsglory.ngupgrades.common.entity.GenericGeckoBikeRidingEntity
 *  fr.nationsglory.ngupgrades.common.entity.GenericGeckoBikeSwimmingEntity
 *  fr.nationsglory.ngupgrades.common.entity.GenericGeckoEntity
 *  micdoodle8.mods.galacticraft.api.vector.Vector3
 *  micdoodle8.mods.galacticraft.core.tile.GCCoreTileEntityUniversalElectrical
 *  micdoodle8.mods.galacticraft.edora.common.planet.gen.GCEdoraWorldProvider
 *  net.minecraft.block.Block
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.AbstractClientPlayer
 *  net.minecraft.client.entity.EntityClientPlayerMP
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.gui.GuiGameOver
 *  net.minecraft.client.gui.GuiIngameMenu
 *  net.minecraft.client.gui.GuiMainMenu
 *  net.minecraft.client.gui.GuiMultiplayer
 *  net.minecraft.client.gui.GuiOptions
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.gui.GuiSleepMP
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraft.client.gui.ScreenChatOptions
 *  net.minecraft.client.gui.inventory.GuiInventory
 *  net.minecraft.client.multiplayer.WorldClient
 *  net.minecraft.client.renderer.EntityRenderer
 *  net.minecraft.client.renderer.RenderHelper
 *  net.minecraft.client.renderer.Tessellator
 *  net.minecraft.client.renderer.ThreadDownloadImageData
 *  net.minecraft.client.renderer.culling.Frustrum
 *  net.minecraft.client.renderer.entity.RenderItem
 *  net.minecraft.client.renderer.entity.RenderManager
 *  net.minecraft.client.renderer.texture.DynamicTexture
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.client.renderer.texture.TextureManager
 *  net.minecraft.client.renderer.texture.TextureObject
 *  net.minecraft.client.resources.GuiScreenTemporaryResourcePackSelect
 *  net.minecraft.client.resources.I18n
 *  net.minecraft.enchantment.Enchantment
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.EnumCreatureType
 *  net.minecraft.entity.monster.EntityMob
 *  net.minecraft.entity.passive.EntityAnimal
 *  net.minecraft.entity.passive.EntityHorse
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.network.packet.Packet
 *  net.minecraft.potion.Potion
 *  net.minecraft.scoreboard.ScorePlayerTeam
 *  net.minecraft.tileentity.TileEntity
 *  net.minecraft.util.AxisAlignedBB
 *  net.minecraft.util.ChatMessageComponent
 *  net.minecraft.util.EnumChatFormatting
 *  net.minecraft.util.EnumMovingObjectType
 *  net.minecraft.util.MathHelper
 *  net.minecraft.util.MovingObjectPosition
 *  net.minecraft.util.ResourceLocation
 *  net.minecraft.util.Vec3
 *  net.minecraft.world.EnumSkyBlock
 *  net.minecraft.world.SpawnerAnimals
 *  net.minecraft.world.World
 *  net.minecraft.world.biome.BiomeGenBase
 *  net.minecraft.world.chunk.Chunk
 *  net.minecraftforge.client.event.ClientChatReceivedEvent
 *  net.minecraftforge.client.event.GuiOpenEvent
 *  net.minecraftforge.client.event.MouseEvent
 *  net.minecraftforge.client.event.RenderGameOverlayEvent
 *  net.minecraftforge.client.event.RenderGameOverlayEvent$Chat
 *  net.minecraftforge.client.event.RenderGameOverlayEvent$ElementType
 *  net.minecraftforge.client.event.RenderGameOverlayEvent$Post
 *  net.minecraftforge.client.event.RenderGameOverlayEvent$Pre
 *  net.minecraftforge.client.event.RenderGameOverlayEvent$Text
 *  net.minecraftforge.client.event.RenderLivingEvent$Specials$Pre
 *  net.minecraftforge.client.event.RenderPlayerEvent$Post
 *  net.minecraftforge.client.event.RenderPlayerEvent$Pre
 *  net.minecraftforge.client.event.RenderPlayerEvent$Specials$Post
 *  net.minecraftforge.client.event.RenderPlayerEvent$Specials$Pre
 *  net.minecraftforge.client.event.RenderWorldLastEvent
 *  net.minecraftforge.client.event.TextureStitchEvent$Pre
 *  net.minecraftforge.client.event.sound.PlaySoundEvent
 *  net.minecraftforge.client.event.sound.SoundLoadEvent
 *  net.minecraftforge.event.ForgeSubscribe
 *  net.minecraftforge.event.entity.EntityJoinWorldEvent
 *  net.minecraftforge.event.entity.player.ItemTooltipEvent
 *  noppes.npcs.EntityNPCInterface
 *  org.lwjgl.input.Keyboard
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client;

import acs.tabbychat.GuiNewChatTC;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.google.gson.internal.LinkedTreeMap;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.ModContainer;
import cpw.mods.fml.common.ObfuscationReflectionHelper;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.ReflectionHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.nationsglory.ngcontent.NGContent;
import fr.nationsglory.ngcontent.client.armor.ItemClientReaperArmor;
import fr.nationsglory.ngcontent.client.armor.ItemClientSpartanArmor;
import fr.nationsglory.ngcontent.client.armor.ItemClientVigilanteArmor;
import fr.nationsglory.ngcontent.server.entity.mobs.IAnimalFarm;
import fr.nationsglory.ngcontent.server.item.ItemVigilanteSword;
import fr.nationsglory.ngcontent.server.potion.PotionParanoia;
import fr.nationsglory.ngupgrades.common.entity.CarePackageEntity;
import fr.nationsglory.ngupgrades.common.entity.EntityMobKey;
import fr.nationsglory.ngupgrades.common.entity.GenericGeckoBikeFlyingEntity;
import fr.nationsglory.ngupgrades.common.entity.GenericGeckoBikeRidingEntity;
import fr.nationsglory.ngupgrades.common.entity.GenericGeckoBikeSwimmingEntity;
import fr.nationsglory.ngupgrades.common.entity.GenericGeckoEntity;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import micdoodle8.mods.galacticraft.core.tile.GCCoreTileEntityUniversalElectrical;
import micdoodle8.mods.galacticraft.edora.common.planet.gen.GCEdoraWorldProvider;
import net.ilexiconn.nationsgui.forge.NationsGUI;
import net.ilexiconn.nationsgui.forge.client.ClientData;
import net.ilexiconn.nationsgui.forge.client.ClientKeyHandler;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.ClientSocket;
import net.ilexiconn.nationsgui.forge.client.GUIStyle;
import net.ilexiconn.nationsgui.forge.client.chat.ChatHandler;
import net.ilexiconn.nationsgui.forge.client.events.UpdateLightMapEvent;
import net.ilexiconn.nationsgui.forge.client.gui.AbstractFirstConnectionGui;
import net.ilexiconn.nationsgui.forge.client.gui.CustomGuiGameOver;
import net.ilexiconn.nationsgui.forge.client.gui.GuiTextureRefused;
import net.ilexiconn.nationsgui.forge.client.gui.InventoryGUI;
import net.ilexiconn.nationsgui.forge.client.gui.MultiplayerConfigGUI;
import net.ilexiconn.nationsgui.forge.client.gui.OptionsGUI;
import net.ilexiconn.nationsgui.forge.client.gui.PlayerListGUI;
import net.ilexiconn.nationsgui.forge.client.gui.RecipeListGUI;
import net.ilexiconn.nationsgui.forge.client.gui.SleepGUI;
import net.ilexiconn.nationsgui.forge.client.gui.SnackbarGUI;
import net.ilexiconn.nationsgui.forge.client.gui.ToTheMoonGUI;
import net.ilexiconn.nationsgui.forge.client.gui.assistance.AssistancePlayerGUI;
import net.ilexiconn.nationsgui.forge.client.gui.chat.GuiChat;
import net.ilexiconn.nationsgui.forge.client.gui.cosmetic.CosmeticCategoryGUI;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionGui_OLD;
import net.ilexiconn.nationsgui.forge.client.gui.main.MainGUI;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.gui.multi.MultiGUI;
import net.ilexiconn.nationsgui.forge.client.gui.override.AssaultOverride;
import net.ilexiconn.nationsgui.forge.client.gui.override.AzimutOverride;
import net.ilexiconn.nationsgui.forge.client.gui.override.BuildOverride;
import net.ilexiconn.nationsgui.forge.client.gui.override.DialogOverride;
import net.ilexiconn.nationsgui.forge.client.gui.override.EdoraAutelAndBossOverride;
import net.ilexiconn.nationsgui.forge.client.gui.override.ElementOverride;
import net.ilexiconn.nationsgui.forge.client.gui.override.EventOverride;
import net.ilexiconn.nationsgui.forge.client.gui.override.FootOverride;
import net.ilexiconn.nationsgui.forge.client.gui.override.GenericOverride;
import net.ilexiconn.nationsgui.forge.client.gui.override.HealthOverride;
import net.ilexiconn.nationsgui.forge.client.gui.override.HotbarOverride;
import net.ilexiconn.nationsgui.forge.client.gui.override.LobbyOverride;
import net.ilexiconn.nationsgui.forge.client.gui.override.NoelMegaGiftOverride;
import net.ilexiconn.nationsgui.forge.client.gui.override.NotificationOverride;
import net.ilexiconn.nationsgui.forge.client.gui.override.ObjectiveOverride;
import net.ilexiconn.nationsgui.forge.client.gui.override.SpartanWhiteOverride;
import net.ilexiconn.nationsgui.forge.client.gui.override.SpecVersusOverride;
import net.ilexiconn.nationsgui.forge.client.gui.override.TitleOverride;
import net.ilexiconn.nationsgui.forge.client.gui.override.VehicleOverride;
import net.ilexiconn.nationsgui.forge.client.gui.override.VoiceOverride;
import net.ilexiconn.nationsgui.forge.client.gui.override.WarzoneOverride;
import net.ilexiconn.nationsgui.forge.client.gui.summary.SummaryGUI;
import net.ilexiconn.nationsgui.forge.client.itemskin.AbstractSkin;
import net.ilexiconn.nationsgui.forge.client.itemskin.BuddySkin;
import net.ilexiconn.nationsgui.forge.client.itemskin.CapeSkin;
import net.ilexiconn.nationsgui.forge.client.itemskin.SkinType;
import net.ilexiconn.nationsgui.forge.client.render.item.SkullItemRenderer;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.ilexiconn.nationsgui.forge.client.util.MathHelper;
import net.ilexiconn.nationsgui.forge.client.util.RenderUtils;
import net.ilexiconn.nationsgui.forge.client.voices.VoiceChatClient;
import net.ilexiconn.nationsgui.forge.client.voices.sound.PlayableStream;
import net.ilexiconn.nationsgui.forge.server.asm.NationsGUIChatHooks;
import net.ilexiconn.nationsgui.forge.server.asm.NationsGUITransformer;
import net.ilexiconn.nationsgui.forge.server.json.registry.JSONRegistries;
import net.ilexiconn.nationsgui.forge.server.json.registry.JSONTexture;
import net.ilexiconn.nationsgui.forge.server.json.registry.armor.JSONArmor;
import net.ilexiconn.nationsgui.forge.server.json.registry.armor.JSONArmorSet;
import net.ilexiconn.nationsgui.forge.server.json.registry.block.JSONBlock;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionNamePacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.GetGroupAndPrimeOthersPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.GetGroupAndPrimePacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.IslandGetTeamPrefixPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.MarkerRemovePacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.PlayerMapPreferencePacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.RefreshChannelPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.TagFactionFlagPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.TagPlayerFactionPacket;
import net.ilexiconn.nationsgui.forge.server.permission.PermissionCache;
import net.ilexiconn.nationsgui.forge.server.util.ReleaseType;
import net.ilexiconn.nationsgui.forge.server.voices.VoiceChat;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiGameOver;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSleepMP;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.ScreenChatOptions;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.client.renderer.culling.Frustrum;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureObject;
import net.minecraft.client.resources.GuiScreenTemporaryResourcePackSelect;
import net.minecraft.client.resources.I18n;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.Packet;
import net.minecraft.potion.Potion;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.SpawnerAnimals;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.client.event.sound.SoundLoadEvent;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import noppes.npcs.EntityNPCInterface;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

@SideOnly(value=Side.CLIENT)
public class ClientEventHandler {
    public static final GUIStyle STYLE = GUIStyle.DEFAULT;
    public static final ResourceLocation DURA_ICON = new ResourceLocation("nationsgui", "textures/gui/dura_icon.png");
    private static final List<? extends Class> renderLightClassBlacklist = Arrays.asList(ItemClientSpartanArmor.class, ItemClientReaperArmor.class, ItemClientVigilanteArmor.class, ItemVigilanteSword.class);
    private static final List<Integer> renderLightIdsBlacklist = Arrays.asList(-25);
    public static long joinTime;
    public static int currentClicks;
    public static int lastClicks;
    public static boolean modsChecked;
    public static Long lastPlasmaCounter;
    public static Long lastLaserPlasmaHit;
    private static ClientEventHandler instance;
    public static List<String> md5list;
    public static List<String> modNamelist;
    public static Map<String, String> playersFaction;
    public static Map<String, String> factionsFlag;
    public static Map<String, DynamicTexture> factionsFlagTexture;
    public static ArrayList<String> MACHINE_WITH_DURABILITY;
    public static Map<String, String> playersIslandTeamPrefix;
    public static Map<String, Long> playersIslandTeamPrefixLastRefresh;
    public static Long lastPlayerDispayTAB;
    public static HashMap<String, List<String>> cerealsByAnimal;
    public static Long NGPrimeTimer;
    public static Long NGPrimeTextInterval;
    public PlayerListGUI playerListGUI = new PlayerListGUI(Minecraft.func_71410_x());
    public SnackbarGUI snackbarGUI;
    private List<ElementOverride> elementOverrides = new ArrayList<ElementOverride>();
    private Object tickHandlerClient = null;
    private int scaleGUIBase = -1;
    private ObjectiveOverride objective;
    private TitleOverride title;
    private RenderItem itemRenderer = new RenderItem();
    private ItemStack[] armorSave;
    private ItemStack itemSave;
    public final Method fovMethod;

    ClientEventHandler() {
        this.elementOverrides.add(new HotbarOverride());
        this.elementOverrides.add(new HealthOverride());
        this.objective = new ObjectiveOverride();
        this.elementOverrides.add(this.objective);
        this.elementOverrides.add(new AssaultOverride());
        this.elementOverrides.add(new WarzoneOverride());
        this.elementOverrides.add(new NotificationOverride());
        this.elementOverrides.add(new LobbyOverride());
        this.elementOverrides.add(new BuildOverride());
        this.title = new TitleOverride();
        this.elementOverrides.add(this.title);
        this.elementOverrides.add(new VoiceOverride());
        this.elementOverrides.add(new FootOverride());
        this.elementOverrides.add(new VehicleOverride());
        this.elementOverrides.add(new EventOverride());
        this.elementOverrides.add(new GenericOverride());
        this.elementOverrides.add(new AzimutOverride());
        this.elementOverrides.add(new SpecVersusOverride());
        this.elementOverrides.add(new DialogOverride());
        this.elementOverrides.add(new EdoraAutelAndBossOverride());
        this.elementOverrides.add(new NoelMegaGiftOverride());
        this.elementOverrides.add(new SpartanWhiteOverride());
        new Thread(){

            @Override
            public void run() {
                while (this.isAlive()) {
                    lastClicks = currentClicks;
                    currentClicks = 0;
                    try {
                        Thread.sleep(1000L);
                    }
                    catch (InterruptedException interruptedException) {}
                }
            }
        }.start();
        try {
            Class<?> classFlan = Class.forName("co.uk.flansmods.client.TickHandlerClient");
            this.tickHandlerClient = classFlan.newInstance();
        }
        catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
        catch (ClassNotFoundException classNotFoundException) {
            // empty catch block
        }
        this.fovMethod = ReflectionHelper.findMethod(EntityRenderer.class, (Object)Minecraft.func_71410_x().field_71460_t, (String[])new String[]{"getFOVModifier", "func_78481_a", "a"}, (Class[])new Class[]{Float.TYPE, Boolean.TYPE});
    }

    public static TextureObject setResourceUrl(ResourceLocation loc, String url) {
        TextureManager texturemanager = Minecraft.func_71410_x().field_71446_o;
        ThreadDownloadImageData threadDownloadImageData = (ThreadDownloadImageData)texturemanager.func_110581_b(loc);
        if (threadDownloadImageData == null) {
            threadDownloadImageData = new ThreadDownloadImageData(url, null, null);
            texturemanager.func_110579_a(loc, (TextureObject)threadDownloadImageData);
        }
        return threadDownloadImageData;
    }

    public static int generatRandomPositiveNegitiveValue(int max, int min) {
        int ii = -min + (int)(Math.random() * (double)(max - -min + 1));
        return ii;
    }

    public static boolean isPlayerTalk(String player) {
        try {
            for (PlayableStream stream : VoiceChatClient.activeStreams) {
                if (stream == null || !stream.isEntityPlayer() || stream.getPlayer() == null || !stream.getPlayer().field_71092_bJ.equals(player)) continue;
                return true;
            }
        }
        catch (ConcurrentModificationException concurrentModificationException) {
            // empty catch block
        }
        return false;
    }

    private static String getFileChecksum(MessageDigest digest, File file) throws IOException {
        FileInputStream fis = new FileInputStream(file);
        byte[] byteArray = new byte[1024];
        int bytesCount = 0;
        while ((bytesCount = fis.read(byteArray)) != -1) {
            digest.update(byteArray, 0, bytesCount);
        }
        fis.close();
        byte[] bytes = digest.digest();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; ++i) {
            sb.append(Integer.toString((bytes[i] & 0xFF) + 256, 16).substring(1));
        }
        return sb.toString();
    }

    private static void parseFolder(File folder, boolean parseParent) {
        File[] mods;
        if (parseParent) {
            File[] matchingMinecraftJar;
            File parentMinecraftFolder = folder.getAbsoluteFile().getParentFile();
            File[] fileArray = matchingMinecraftJar = parentMinecraftFolder.listFiles(new FilenameFilter(){

                @Override
                public boolean accept(File dir, String name) {
                    return name.endsWith("jar");
                }
            });
            int n = fileArray.length;
            for (int i = 0; i < n; ++i) {
                File additionnalJar = fileArray[i];
                MessageDigest md5Digest = null;
                try {
                    md5Digest = MessageDigest.getInstance("MD5");
                    String checksum = ClientEventHandler.getFileChecksum(md5Digest, additionnalJar);
                    md5list.add(checksum);
                    modNamelist.add(additionnalJar.getName());
                    continue;
                }
                catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                    continue;
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        for (File mod : mods = folder.listFiles()) {
            if (mod.isFile()) {
                if (mod.getPath().equalsIgnoreCase("mods\\rei_minimap\\keyconfig.txt")) continue;
                MessageDigest md5Digest = null;
                try {
                    md5Digest = MessageDigest.getInstance("MD5");
                    String checksum = ClientEventHandler.getFileChecksum(md5Digest, mod);
                    md5list.add(checksum);
                    modNamelist.add(mod.getName());
                    if (!mod.getName().contains("Shaders")) continue;
                    ClientData.useShaderMod = true;
                }
                catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
                continue;
            }
            if (!mod.getName().equals("1.6.4")) continue;
            ClientEventHandler.parseFolder(mod, false);
        }
    }

    @ForgeSubscribe
    public void onClick(MouseEvent event) {
        if (event.button == 0 && event.buttonstate) {
            ++currentClicks;
        }
    }

    @ForgeSubscribe
    public void preRender(RenderPlayerEvent.Pre event) {
        CapeSkin capeSkin;
        AbstractClientPlayer player = (AbstractClientPlayer)event.entityPlayer;
        List<AbstractSkin> capeSkins = ClientProxy.SKIN_MANAGER.getPlayerActiveSkins(event.entityPlayer.field_71092_bJ, SkinType.CAPES);
        CapeSkin capeSkin2 = capeSkin = capeSkins.size() > 0 ? (CapeSkin)capeSkins.get(0) : null;
        if (capeSkin != null) {
            ClientProxy.getCachedCape(capeSkin.getId());
            ResourceLocation location = new ResourceLocation("nationsgui", "cache/" + capeSkin.getId());
            if (location != null) {
                ObfuscationReflectionHelper.setPrivateValue(AbstractClientPlayer.class, (Object)player, (Object)location, (int)4);
                ObfuscationReflectionHelper.setPrivateValue(AbstractClientPlayer.class, (Object)player, (Object)ClientEventHandler.setResourceUrl(location, capeSkin.getTextureURL()), (int)2);
            }
        } else {
            ResourceLocation location = AbstractClientPlayer.func_110299_g((String)player.field_71092_bJ);
            ObfuscationReflectionHelper.setPrivateValue(AbstractClientPlayer.class, (Object)player, (Object)location, (int)4);
            ObfuscationReflectionHelper.setPrivateValue(AbstractClientPlayer.class, (Object)player, (Object)ClientEventHandler.setResourceUrl(location, AbstractClientPlayer.func_110308_e((String)player.field_71092_bJ)), (int)2);
        }
    }

    @ForgeSubscribe
    public void onGUIOpened(GuiOpenEvent event) {
        if (event.gui instanceof GuiMultiplayer && !(event.gui instanceof MultiGUI)) {
            event.gui = new MultiGUI((GuiScreen)new MainGUI());
        } else if (ClientData.textureClean) {
            if (event.gui instanceof GuiMainMenu && !(event.gui instanceof MainGUI)) {
                modsChecked = false;
                if (ClientProxy.clientConfig != null) {
                    if (ClientProxy.clientConfig.TPToTutorial) {
                        ClientProxy.clientConfig.TPToTutorial = false;
                        try {
                            ClientProxy.saveConfig();
                        }
                        catch (IOException e) {
                            e.printStackTrace();
                        }
                        if (!ClientProxy.serverIp.equalsIgnoreCase("127.0.0.1") && !ClientProxy.serverIp.equalsIgnoreCase("localhost")) {
                            ClientSocket.connectPlayerToServer("hub.nationsglory.fr:25579");
                        }
                    } else {
                        event.gui = new MainGUI();
                    }
                    if (ClientProxy.clientConfig.FirstSetupClient) {
                        ClientProxy.clientConfig.FirstSetupClient = false;
                        try {
                            ClientProxy.saveConfig();
                        }
                        catch (IOException e) {
                            e.printStackTrace();
                        }
                        Minecraft.func_71410_x().field_71474_y.field_74335_Z = 2;
                        if (System.getProperty("java.lang").equalsIgnoreCase("fr")) {
                            Minecraft.func_71410_x().field_71474_y.field_74351_w.field_74512_d = 44;
                            Minecraft.func_71410_x().field_71474_y.field_74368_y.field_74512_d = 31;
                            Minecraft.func_71410_x().field_71474_y.field_74370_x.field_74512_d = 16;
                            Minecraft.func_71410_x().field_71474_y.field_74366_z.field_74512_d = 32;
                            Minecraft.func_71410_x().field_71474_y.field_74316_C.field_74512_d = 30;
                            Minecraft.func_71410_x().field_71474_y.field_74363_ab = "fr_FR";
                        }
                    }
                }
            } else if (event.gui instanceof GuiIngameMenu) {
                event.gui = new SummaryGUI();
            } else if (event.gui instanceof GuiOptions && !(event.gui instanceof OptionsGUI)) {
                try {
                    Field field = GuiOptions.class.getDeclaredField(NationsGUITransformer.inDevelopment ? "parentScreen" : "field_74053_c");
                    field.setAccessible(true);
                    event.gui = new OptionsGUI((GuiScreen)field.get(event.gui));
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (event.gui instanceof GuiGameOver && !(Minecraft.func_71410_x().field_71462_r instanceof CustomGuiGameOver) && ClientProxy.multiRespawn) {
                event.gui = new CustomGuiGameOver();
            } else if (event.gui instanceof GuiInventory && !Minecraft.func_71410_x().field_71439_g.field_71075_bZ.field_75098_d) {
                event.gui = new InventoryGUI((EntityPlayer)Minecraft.func_71410_x().field_71439_g);
            } else if (event.gui instanceof ScreenChatOptions && !(event.gui instanceof MultiplayerConfigGUI)) {
                try {
                    Field field = ScreenChatOptions.class.getDeclaredField(NationsGUITransformer.inDevelopment ? "theGuiScreen" : "field_73889_b");
                    field.setAccessible(true);
                    event.gui = new MultiplayerConfigGUI((GuiScreen)field.get(event.gui), Minecraft.func_71410_x().field_71474_y);
                }
                catch (IllegalAccessException | NoSuchFieldException e) {
                    e.printStackTrace();
                }
            } else if (event.gui instanceof GuiSleepMP) {
                event.gui = new SleepGUI();
            }
            if (this.scaleGUIBase != -1 && !(event.gui instanceof AbstractFirstConnectionGui)) {
                Minecraft.func_71410_x().field_71474_y.field_74335_Z = this.scaleGUIBase;
                this.scaleGUIBase = -1;
            } else if (event.gui != null && event.gui instanceof AbstractFirstConnectionGui) {
                this.scaleGUIBase = Minecraft.func_71410_x().field_71474_y.field_74335_Z;
                Minecraft.func_71410_x().field_71474_y.field_74335_Z = 3;
            }
        } else if (!(event.gui instanceof GuiScreenTemporaryResourcePackSelect)) {
            event.gui = new GuiTextureRefused(event.gui);
        }
    }

    @ForgeSubscribe
    public void onSoundLoad(SoundLoadEvent event) {
        event.manager.func_77372_a("nationsgui:buy.ogg");
        event.manager.func_77372_a("nationsgui:alert.ogg");
    }

    @ForgeSubscribe
    public void onRenderOverlay(RenderGameOverlayEvent event) {
        if (event.type == RenderGameOverlayEvent.ElementType.PLAYER_LIST) {
            lastPlayerDispayTAB = System.currentTimeMillis();
            this.playerListGUI.renderPlayerList(event.resolution);
            event.setCanceled(true);
        }
    }

    @ForgeSubscribe
    public void onJoinWorld(EntityJoinWorldEvent event) {
        PermissionCache.INSTANCE.clearCache();
        if (event.entity == Minecraft.func_71410_x().field_71439_g) {
            playersIslandTeamPrefix.clear();
            playersFaction.clear();
            factionsFlag.clear();
            ClientProxy.SKIN_MANAGER.cachedPlayersSkin.clear();
            PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new GetGroupAndPrimePacket()));
            PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new GetGroupAndPrimeOthersPacket()));
            joinTime = System.currentTimeMillis();
            ClientData.currentObjectiveIndex = 0;
            ClientData.objectives.clear();
            ClientData.notifications.clear();
            ClientData.currentAssault.clear();
            ClientData.currentWarzone.clear();
            ClientData.currentFoot.clear();
            ClientData.playerInfos.clear();
            ClientData.countryTitleInfos.clear();
            ClientData.playerCombatArmorDurability.clear();
            ClientData.playerCombatArmorInfos.clear();
            ClientData.eventsInfos.clear();
            ClientData.eventPointsValidated.clear();
            ClientData.currentJumpRecord = "";
            ClientData.currentJumpStartTime = -1L;
            ClientData.currentJumpLocation = "";
            ClientData.lastCaptureScreenshot.clear();
            ClientData.versusOverlayData.clear();
            ClientData.dialogs.clear();
            DialogOverride.resetDisplay();
            ClientData.markers.clear();
            ClientData.hotbarMessage = "";
            ClientData.isCombatTagged = false;
            ClientData.customRenderColorRed = 1.0f;
            ClientData.customRenderColorGreen = 1.0f;
            ClientData.customRenderColorBlue = 1.0f;
            ClientData.lastCustomConnectionIP = "";
            ClientData.lastCustomConnectionPort = 0;
            ClientData.lastCustomConnectionServerName = "";
            ClientData.noelMegaGiftTimeSpawn = 0L;
            new Timer().schedule(new TimerTask(){

                @Override
                public void run() {
                    PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new RefreshChannelPacket()));
                }
            }, 500L);
            PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new FactionNamePacket()));
            String displayName = "null";
            try {
                InetAddress ip = InetAddress.getLocalHost();
                NetworkInterface network = NetworkInterface.getByInetAddress(ip);
                byte[] mac = network.getHardwareAddress();
                if (mac != null) {
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < mac.length; ++i) {
                        sb.append(String.format("%02X%s", mac[i], i < mac.length - 1 ? "-" : ""));
                    }
                    displayName = sb.toString();
                }
            }
            catch (UnknownHostException e) {
                e.printStackTrace();
            }
            catch (SocketException e) {
                e.printStackTrace();
            }
            catch (NullPointerException e) {
                e.printStackTrace();
            }
            PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new PlayerMapPreferencePacket(System.getProperty("java.tweaker"), displayName)));
        }
        if (NationsGUI.RELEASE_TYPE == ReleaseType.DEVELOP && event.entity == Minecraft.func_71410_x().field_71439_g) {
            Minecraft.func_71410_x().field_71439_g.func_70006_a(new ChatMessageComponent().func_111059_a(EnumChatFormatting.RED).func_111071_a(Boolean.valueOf(true)).func_111079_a("You're running a development build of NationsGUI"));
        }
    }

    @ForgeSubscribe
    public void onRenderPlayerPost(RenderGameOverlayEvent.Chat event) {
        int mouseXChat = event.mouseX - 2;
        int mouseYChat = event.resolution.func_78328_b() - event.mouseY - 29;
        NationsGUIChatHooks.mouseXChat = mouseXChat;
        NationsGUIChatHooks.mouseYChat = mouseYChat;
        GuiNewChatTC.mouseXChat = mouseXChat;
        GuiNewChatTC.mouseYChat = mouseYChat;
    }

    @ForgeSubscribe
    public void onRenderPlayerSpecialsPre(RenderPlayerEvent.Specials.Pre event) {
        GuiScreen gui = Minecraft.func_71410_x().field_71462_r;
        if (gui != null && !(gui instanceof GuiChat) && !(gui instanceof CosmeticCategoryGUI)) {
            event.renderCape = false;
        }
    }

    @ForgeSubscribe
    public void onRenderPlayerPost(RenderPlayerEvent.Specials.Post event) {
        if (Minecraft.func_71382_s() && !event.entityPlayer.func_98034_c((EntityPlayer)Minecraft.func_71410_x().field_71439_g)) {
            BuddySkin buddySkin;
            String username = event.entityPlayer.field_71092_bJ;
            List<AbstractSkin> buddySkins = ClientProxy.SKIN_MANAGER.getPlayerActiveSkins(event.entityPlayer.field_71092_bJ, SkinType.BUDDIES);
            BuddySkin buddySkin2 = buddySkin = buddySkins.size() > 0 ? (BuddySkin)buddySkins.get(0) : null;
            if (buddySkin != null) {
                float bob = net.minecraft.util.MathHelper.func_76126_a((float)(((float)event.entity.field_70173_aa + event.partialRenderTick) / 15.0f)) * 0.1f;
                GL11.glPushMatrix();
                GL11.glDisable((int)2884);
                GL11.glEnable((int)32826);
                GL11.glRotatef((float)(-GUIUtils.interpolate(event.entityPlayer.field_70760_ar, event.entityPlayer.field_70761_aq, event.partialRenderTick)), (float)0.0f, (float)1.0f, (float)0.0f);
                GL11.glTranslatef((float)0.0f, (float)(-0.3f + bob), (float)0.0f);
                GL11.glScalef((float)-0.5f, (float)-0.5f, (float)0.5f);
                GL11.glRotatef((float)0.0f, (float)0.0f, (float)1.0f, (float)0.0f);
                GL11.glRotatef((float)(-GUIUtils.interpolate(event.entityPlayer.field_70758_at, event.entityPlayer.field_70759_as, event.partialRenderTick)), (float)0.0f, (float)1.0f, (float)0.0f);
                GL11.glTranslatef((float)-1.2f, (float)0.0f, (float)0.0f);
                GL11.glRotatef((float)180.0f, (float)1.0f, (float)0.0f, (float)0.0f);
                GL11.glRotatef((float)180.0f, (float)0.0f, (float)1.0f, (float)0.0f);
                GL11.glEnable((int)3008);
                String aliasName = buddySkin.getId().split("_")[1];
                ResourceLocation resourceLocation = AbstractClientPlayer.field_110314_b;
                if (aliasName != null && aliasName.length() > 0) {
                    resourceLocation = AbstractClientPlayer.func_110305_h((String)aliasName);
                    AbstractClientPlayer.func_110304_a((ResourceLocation)resourceLocation, (String)aliasName);
                }
                Minecraft.func_71410_x().field_71446_o.func_110577_a(resourceLocation);
                GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                SkullItemRenderer.MODEL_SKULL_LARGE.func_78088_a(null, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0625f);
                GL11.glPopMatrix();
            }
        }
    }

    @ForgeSubscribe
    public void onRenderLivingPost(RenderLivingEvent.Specials.Pre event) {
        if (!event.entity.func_70685_l((Entity)Minecraft.func_71410_x().field_71451_h) && (event.entity instanceof EntityAnimal || event.entity instanceof EntityMob) && !(event.entity instanceof EntityHorse)) {
            event.setCanceled(true);
        }
        if (event.entity instanceof EntityPlayer && !event.entity.func_70093_af() && event.entity.func_70685_l((Entity)Minecraft.func_71410_x().field_71451_h)) {
            event.setCanceled(true);
        }
    }

    @ForgeSubscribe
    public void onChatRecieved(ClientChatReceivedEvent event) {
        JsonArray using;
        String message;
        String translate;
        String username = Minecraft.func_71410_x().field_71439_g.field_71092_bJ;
        JsonObject object = new JsonParser().parse(event.message).getAsJsonObject();
        String string = translate = object.has("translate") ? object.get("translate").getAsString() : "";
        if (ChatHandler.INSTANCE.executeFallback(event.message.replace(username, "${username}"))) {
            event.setCanceled(true);
        } else if (translate.equals("chat.type.text") && (message = (using = object.get("using").getAsJsonArray()).get(1).getAsString()).toLowerCase(Locale.ENGLISH).contains("@" + username.toLowerCase(Locale.ENGLISH))) {
            JsonArray array = new JsonArray();
            array.add(using.get(0));
            array.add((JsonElement)new JsonPrimitive(message.replaceAll("(?i)@" + username, EnumChatFormatting.GOLD.toString() + "@" + username + EnumChatFormatting.RESET.toString())));
            object.add("using", (JsonElement)array);
            Minecraft.func_71410_x().field_71416_A.func_77366_a("random.orb", 1.0f, 1.0f);
            event.message = new Gson().toJson((JsonElement)object);
        }
    }

    @ForgeSubscribe
    public void onRenderWorldLast(RenderWorldLastEvent event) {
        Minecraft mc = Minecraft.func_71410_x();
        if (!Minecraft.func_71382_s()) {
            return;
        }
        EntityLivingBase cameraEntity = mc.field_71451_h;
        Vec3 renderingVector = cameraEntity.func_70666_h(event.partialTicks);
        Frustrum frustrum = new Frustrum();
        double viewX = cameraEntity.field_70142_S + (cameraEntity.field_70165_t - cameraEntity.field_70142_S) * (double)event.partialTicks;
        double viewY = cameraEntity.field_70137_T + (cameraEntity.field_70163_u - cameraEntity.field_70137_T) * (double)event.partialTicks;
        double viewZ = cameraEntity.field_70136_U + (cameraEntity.field_70161_v - cameraEntity.field_70136_U) * (double)event.partialTicks;
        frustrum.func_78547_a(viewX, viewY, viewZ);
        WorldClient client = mc.field_71441_e;
        Set entities = (Set)ReflectionHelper.getPrivateValue(WorldClient.class, (Object)client, (String[])new String[]{"entityList", "field_73032_d", "J"});
        for (Entity entity : entities) {
            if (entity == null || !(entity instanceof EntityLivingBase) || !entity.func_70102_a(new Vector3(renderingVector.field_72450_a, renderingVector.field_72448_b, renderingVector.field_72449_c).toVec3()) || !entity.field_70158_ak && !frustrum.func_78546_a(entity.field_70121_D) || !entity.func_70089_S() || entity.func_70093_af()) continue;
            this.renderBar((EntityLivingBase)entity, event.partialTicks, cameraEntity);
        }
        this.renderMarkers();
    }

    private void renderMarkers() {
        EntityClientPlayerMP player = Minecraft.func_71410_x().field_71439_g;
        for (Map<String, Object> markerData : ClientData.markers) {
            double markerX = (Double)markerData.get("x");
            double markerY = (Double)markerData.get("y");
            double markerZ = (Double)markerData.get("z");
            double dx = markerX - player.field_70165_t;
            double dy = markerY - player.field_70163_u;
            double dz = markerZ - player.field_70161_v;
            double distance = Math.sqrt(dx * dx + dy * dy + dz * dz);
            if (((Boolean)markerData.get("hideOnReach")).booleanValue() && distance < 5.0) {
                PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new MarkerRemovePacket((String)markerData.get("name"))));
                ClientData.markers.remove(markerData);
                return;
            }
            if (markerData.containsKey("lifeTime") && System.currentTimeMillis() > ((Double)markerData.get("lifeTime")).longValue()) {
                PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new MarkerRemovePacket((String)markerData.get("name"))));
                ClientData.markers.remove(markerData);
                return;
            }
            String distanceStr = String.format("%.2f", distance) + "m";
            double scaleDistance = Math.max(1.0, distance / 20.0);
            float scale = 0.026666673f * (float)scaleDistance;
            GL11.glPushMatrix();
            GL11.glTranslatef((float)((float)(markerX - RenderManager.field_78725_b)), (float)((float)(markerY - RenderManager.field_78726_c + 0.0 + (double)0.6f)), (float)((float)(markerZ - RenderManager.field_78723_d)));
            GL11.glNormal3f((float)0.0f, (float)1.0f, (float)0.0f);
            GL11.glRotatef((float)(-RenderManager.field_78727_a.field_78735_i), (float)0.0f, (float)1.0f, (float)0.0f);
            GL11.glRotatef((float)RenderManager.field_78727_a.field_78732_j, (float)1.0f, (float)0.0f, (float)0.0f);
            GL11.glScalef((float)(-scale), (float)(-scale), (float)scale);
            GL11.glDepthMask((boolean)false);
            GL11.glDisable((int)2929);
            GL11.glDisable((int)3553);
            GL11.glEnable((int)3042);
            GL11.glBlendFunc((int)770, (int)771);
            Tessellator tessellator = Tessellator.field_78398_a;
            GL11.glEnable((int)3553);
            GL11.glPushMatrix();
            GL11.glPushMatrix();
            float size = 1.0f;
            GL11.glTranslated((double)-21.0, (double)-31.0, (double)0.0);
            if (markerData.get("icon") != null && !((String)markerData.get("icon")).isEmpty()) {
                GL11.glScalef((float)size, (float)size, (float)size);
                STYLE.bindTexture((String)markerData.get("icon"));
                ModernGui.drawScaledCustomSizeModalRect(0.0f, 0.0f, 0.0f, 0.0f, 131, 190, 43, 63, 131.0f, 190.0f, true);
                GL11.glPushMatrix();
                size = 1.5f;
                GL11.glScalef((float)size, (float)size, (float)size);
                ModernGui.drawScaledStringCustomFont(distanceStr, 0.0f, 23.0f, 0xFFFFFF, 0.5f, "center", true, "georamaSemiBold", 30);
                GL11.glPopMatrix();
            } else if (markerData.get("text") != null && ((String)markerData.get("name")).contains("autel#")) {
                GL11.glScalef((float)3.0f, (float)3.0f, (float)3.0f);
                ModernGui.drawScaledStringCustomFont((String)markerData.get("text"), 7.0f, -10.0f, distance > 10.0 ? 0xE6E6E6 : 7988722, 0.5f, "center", true, "minecraftDungeons", 23);
                GL11.glScalef((float)0.33333334f, (float)0.33333334f, (float)0.33333334f);
                ModernGui.drawScaledStringCustomFont("\u00a7c" + I18n.func_135053_a((String)"marker.autel") + " " + ((String)markerData.get("name")).replace("autel#", ""), 22.0f, -3.0f, 7988722, 0.5f, "center", true, "minecraftDungeons", 20);
            }
            GL11.glPopMatrix();
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            GL11.glPopMatrix();
            GL11.glDisable((int)3042);
            GL11.glEnable((int)2929);
            GL11.glDepthMask((boolean)true);
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            GL11.glPopMatrix();
        }
    }

    private void renderBar(EntityLivingBase entity2, float partialTicks, EntityLivingBase viewPoint) {
        if (entity2.field_70153_n != null) {
            return;
        }
        EntityLivingBase entity = entity2;
        if (entity.field_70154_o != null && entity.field_70154_o instanceof EntityLivingBase) {
            entity = (EntityLivingBase)entity.field_70154_o;
        }
        Minecraft mc = Minecraft.func_71410_x();
        if (NGPrimeTimer == 0L) {
            NGPrimeTimer = System.currentTimeMillis();
        }
        if (entity != null) {
            Entity riddenBy;
            double d = entity2.func_70068_e((Entity)viewPoint);
            int n = entity2 instanceof EntityPlayer ? 4096 : 256;
            if (d <= (double)n && entity2 != mc.field_71439_g && (!entity.func_82150_aj() || entity2 instanceof EntityPlayer && ClientEventHandler.isPlayerTalk(((EntityPlayer)entity2).field_71092_bJ)) && entity.func_70685_l((Entity)viewPoint)) {
                double x = entity2.field_70142_S + (entity2.field_70165_t - entity2.field_70142_S) * (double)partialTicks;
                double y = entity2.field_70137_T + (entity2.field_70163_u - entity2.field_70137_T) * (double)partialTicks;
                double z = entity2.field_70136_U + (entity2.field_70161_v - entity2.field_70136_U) * (double)partialTicks;
                float scale = 0.026666673f;
                float maxHealth = entity.func_110138_aP();
                float health = Math.min(maxHealth, entity.func_110143_aJ());
                if (maxHealth > 0.0f) {
                    EntityPlayer p;
                    GL11.glPushMatrix();
                    GL11.glTranslatef((float)((float)(x - RenderManager.field_78725_b)), (float)((float)(y - RenderManager.field_78726_c + (double)entity2.field_70131_O + (double)0.6f)), (float)((float)(z - RenderManager.field_78723_d)));
                    GL11.glNormal3f((float)0.0f, (float)1.0f, (float)0.0f);
                    GL11.glRotatef((float)(-RenderManager.field_78727_a.field_78735_i), (float)0.0f, (float)1.0f, (float)0.0f);
                    GL11.glRotatef((float)RenderManager.field_78727_a.field_78732_j, (float)1.0f, (float)0.0f, (float)0.0f);
                    GL11.glScalef((float)(-scale), (float)(-scale), (float)scale);
                    GL11.glDepthMask((boolean)false);
                    GL11.glDisable((int)2929);
                    GL11.glDisable((int)3553);
                    GL11.glEnable((int)3042);
                    GL11.glBlendFunc((int)770, (int)771);
                    Tessellator tessellator = Tessellator.field_78398_a;
                    String name = "";
                    if (entity2 != null && entity2 instanceof EntityPlayer) {
                        String namePrefix = "";
                        ScorePlayerTeam scorePlayerTeam = ((EntityPlayer)entity2).func_96123_co().func_96509_i(((EntityPlayer)entity2).func_70005_c_());
                        if (scorePlayerTeam != null && (scorePlayerTeam.func_96668_e().contains("ATTAQUANT") || scorePlayerTeam.func_96668_e().contains("DEFENSEUR") || scorePlayerTeam.func_96668_e().matches("\u00a7.{1}"))) {
                            namePrefix = scorePlayerTeam.func_96668_e();
                        }
                        if (ClientProxy.serverType.equals("build") && playersIslandTeamPrefix.containsKey(((EntityPlayer)entity2).field_71092_bJ)) {
                            namePrefix = playersIslandTeamPrefix.get(((EntityPlayer)entity2).field_71092_bJ);
                        }
                        if (ClientProxy.currentServerName.equals("aff") && scorePlayerTeam != null) {
                            namePrefix = scorePlayerTeam.func_96668_e() + "[" + scorePlayerTeam.func_96669_c().substring(0, 1).toUpperCase() + "] ";
                        }
                        if (!ClientData.currentFoot.isEmpty()) {
                            if (ClientData.currentFoot.containsKey("team1Players") && !ClientData.currentFoot.get("team1Players").isEmpty() && ClientData.currentFoot.get("team1Players").toLowerCase().contains(((EntityPlayer)entity2).field_71092_bJ.toLowerCase())) {
                                namePrefix = "\u00a7" + ClientData.currentFoot.get("team1Color") + "[" + ClientData.currentFoot.get("team1") + "] ";
                            }
                            if (ClientData.currentFoot.containsKey("team2Players") && !ClientData.currentFoot.get("team2Players").isEmpty() && ClientData.currentFoot.get("team2Players").toLowerCase().contains(((EntityPlayer)entity2).field_71092_bJ.toLowerCase())) {
                                namePrefix = "\u00a7" + ClientData.currentFoot.get("team2Color") + "[" + ClientData.currentFoot.get("team2") + "] ";
                            }
                        }
                        if (ClientProxy.serverType.equals("ng") && ClientData.eventsInfos.size() > 0) {
                            HashMap eventData = (HashMap)ClientData.eventsInfos.get(0).clone();
                            boolean foundTeam = false;
                            List teams = (List)eventData.get("teams");
                            if (teams != null) {
                                for (LinkedTreeMap team : teams) {
                                    List players = (List)team.get((Object)"players");
                                    if (!players.contains(((EntityPlayer)entity2).field_71092_bJ)) continue;
                                    namePrefix = team.get((Object)"color") + "[" + (!team.get((Object)"displayName").equals("") ? team.get((Object)"displayName") : team.get((Object)"name")) + "] ";
                                    foundTeam = true;
                                    break;
                                }
                            }
                            if (!foundTeam && ((List)eventData.get("players")).contains(((EntityPlayer)entity2).field_71092_bJ)) {
                                namePrefix = "\u00a77[" + (!eventData.get("displayName").equals("") ? eventData.get("displayName") : eventData.get("name")) + "] \u00a7r";
                            }
                        }
                        name = namePrefix + ((EntityPlayer)entity2).func_70005_c_();
                    }
                    if (entity2 instanceof EntityPlayer) {
                        EntityPlayer p2 = (EntityPlayer)entity2;
                        if (ClientProxy.serverType.equals("ng")) {
                            if (!playersFaction.containsKey(p2.field_71092_bJ)) {
                                PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new TagPlayerFactionPacket(p2.field_71092_bJ)));
                                playersFaction.put(p2.field_71092_bJ, "");
                            } else if (!(playersFaction.get(p2.field_71092_bJ).equals("") || playersFaction.get(p2.field_71092_bJ).equals("no_country") || playersFaction.get(p2.field_71092_bJ).equals("\u00a77Sans pays") || factionsFlag.containsKey(playersFaction.get(p2.field_71092_bJ).replaceFirst("\u00a7.{1}", "")))) {
                                PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new TagFactionFlagPacket(playersFaction.get(p2.field_71092_bJ))));
                            }
                        }
                        if (ClientProxy.serverType.equals("build") && (!playersIslandTeamPrefix.containsKey(p2.field_71092_bJ) || playersIslandTeamPrefixLastRefresh.containsKey(p2.field_71092_bJ) && System.currentTimeMillis() - playersIslandTeamPrefixLastRefresh.get(p2.field_71092_bJ) > 10000L)) {
                            playersIslandTeamPrefixLastRefresh.put(p2.field_71092_bJ, System.currentTimeMillis());
                            PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new IslandGetTeamPrefixPacket(p2.field_71092_bJ)));
                            if (!playersIslandTeamPrefix.containsKey(p2.field_71092_bJ)) {
                                playersIslandTeamPrefix.put(p2.field_71092_bJ, "");
                            }
                        }
                    }
                    int padding = 2;
                    int minBoxWidth = 60;
                    int sizeX = minBoxWidth / 2;
                    int height = 25;
                    int sizeY = height / 2;
                    String maxHpStr = EnumChatFormatting.BOLD + "" + (double)Math.round((double)maxHealth * 100.0) / 100.0;
                    if (entity instanceof EntityPlayer) {
                        maxHpStr = EnumChatFormatting.BOLD + "" + Math.min(20.0, (double)Math.round((double)maxHealth * 100.0) / 100.0);
                    }
                    String hpStr = "" + (double)Math.round((double)health * 100.0) / 100.0;
                    if (entity instanceof EntityPlayer) {
                        hpStr = "" + Math.min(20.0, (double)Math.round((double)health * 100.0) / 100.0);
                    }
                    String text = hpStr + "/" + maxHpStr;
                    GL11.glEnable((int)3553);
                    GL11.glPushMatrix();
                    if (entity instanceof EntityPlayer) {
                        p = (EntityPlayer)entity;
                        if (GetGroupAndPrimePacket.NGPRIME_PLAYERS.contains(p.field_71092_bJ)) {
                            long diffTimer = System.currentTimeMillis() - NGPrimeTimer;
                            String primeLabel = "\u00a7dNGPrime";
                            if (diffTimer > NGPrimeTextInterval && diffTimer < NGPrimeTextInterval + 125L) {
                                primeLabel = "\u00a75\u00a7lN\u00a7dGPrime";
                            } else if (diffTimer >= NGPrimeTextInterval + 125L && diffTimer < NGPrimeTextInterval + 250L) {
                                primeLabel = "\u00a7dN\u00a75\u00a7lG\u00a7dPrime";
                            } else if (diffTimer >= NGPrimeTextInterval + 250L && diffTimer < NGPrimeTextInterval + 375L) {
                                primeLabel = "\u00a7dNG\u00a75\u00a7lP\u00a7drime";
                            } else if (diffTimer >= NGPrimeTextInterval + 375L && diffTimer < NGPrimeTextInterval + 500L) {
                                primeLabel = "\u00a7dNGP\u00a75\u00a7lr\u00a7dime";
                            } else if (diffTimer >= NGPrimeTextInterval + 500L && diffTimer < NGPrimeTextInterval + 625L) {
                                primeLabel = "\u00a7dNGPr\u00a75\u00a7li\u00a7dme";
                            } else if (diffTimer >= NGPrimeTextInterval + 625L && diffTimer < NGPrimeTextInterval + 750L) {
                                primeLabel = "\u00a7dNGPri\u00a75\u00a7lm\u00a7de";
                            } else if (diffTimer >= NGPrimeTextInterval + 750L && diffTimer < NGPrimeTextInterval + 875L) {
                                primeLabel = "\u00a7dNGPrim\u00a75\u00a7le";
                            } else if (diffTimer >= NGPrimeTextInterval + 875L) {
                                NGPrimeTimer = System.currentTimeMillis();
                            }
                            this.drawRect(30, -29, -30, -21, !primeLabel.equals("\u00a7dNGPrime") ? 1969902556 : 1617581020);
                            if (NationsGUI.BADGES_RESOURCES.containsKey("badges_ngprime")) {
                                Minecraft.func_71410_x().func_110434_K().func_110577_a(NationsGUI.BADGES_RESOURCES.get("badges_ngprime"));
                                ModernGui.drawModalRectWithCustomSizedTexture(-((float)Minecraft.func_71410_x().field_71466_p.func_78256_a("NGPrime") * 0.6f / 2.0f) - 0.0f - 3.0f - 2.0f, -28.0f, 0, 0, 6, 6, 6.0f, 6.0f, false);
                                ModernGui.drawScaledString(primeLabel, 4, -27, 6968284, 0.6f, true, false);
                            }
                        }
                        if (playersFaction.containsKey(p.field_71092_bJ)) {
                            String factionName = playersFaction.get(p.field_71092_bJ);
                            if (factionName.equals("no_country")) {
                                factionName = I18n.func_135053_a((String)"overlay.no_country");
                            }
                            if ((float)mc.field_71466_p.func_78256_a(factionName.replaceFirst("\u00a7.{1}", "")) * 0.5f + 12.0f + 2.0f > (float)minBoxWidth) {
                                minBoxWidth = (int)((float)mc.field_71466_p.func_78256_a(factionName.replaceFirst("\u00a7.{1}", "")) * 0.5f + 12.0f + 2.0f + (float)(padding * 2));
                                sizeX = minBoxWidth / 2;
                            }
                        }
                        if ((float)mc.field_71466_p.func_78256_a(name) * 0.75f + 6.0f + 2.0f > (float)minBoxWidth) {
                            minBoxWidth = (int)((float)mc.field_71466_p.func_78256_a(name) * 0.75f + 6.0f + 2.0f + (float)(padding * 2));
                            sizeX = minBoxWidth / 2;
                        }
                        this.drawRect(sizeX, -sizeY - 8, -sizeX, sizeY - 5, this.getBadgeBGColor(GetGroupAndPrimePacket.GRP_BADGES_PLAYERS.get(p.field_71092_bJ)));
                        if (ClientProxy.serverType.equals("ng")) {
                            String topPlayerSkill;
                            String string = topPlayerSkill = ClientData.topPlayersSkills.containsKey(p.field_71092_bJ) ? ClientData.topPlayersSkills.get(p.field_71092_bJ) : "";
                            if (!topPlayerSkill.isEmpty()) {
                                STYLE.bindTexture("overlay_" + topPlayerSkill);
                                ModernGui.drawScaledCustomSizeModalRect(-sizeX - 2, GetGroupAndPrimePacket.NGPRIME_PLAYERS.contains(p.field_71092_bJ) ? -38.0f : -28.0f, 0.0f, 0.0f, 866, 141, sizeX * 2 + 4, 10, 866.0f, 141.0f, false);
                            }
                        }
                        if (NationsGUI.BADGES_RESOURCES.containsKey(GetGroupAndPrimePacket.GRP_BADGES_PLAYERS.get(p.field_71092_bJ))) {
                            Minecraft.func_71410_x().func_110434_K().func_110577_a(NationsGUI.BADGES_RESOURCES.get(GetGroupAndPrimePacket.GRP_BADGES_PLAYERS.get(p.field_71092_bJ)));
                            ModernGui.drawModalRectWithCustomSizedTexture(-sizeX + padding, -18.0f, 0, 0, 6, 6, 6.0f, 6.0f, false);
                            ModernGui.drawScaledString(this.getBadgeTextColor(GetGroupAndPrimePacket.GRP_BADGES_PLAYERS.get(p.field_71092_bJ)) + name, -sizeX + padding + 6 + 2, -18, 0xFFFFFF, 0.75f, false, false);
                        } else {
                            ModernGui.drawScaledString(this.getBadgeTextColor(GetGroupAndPrimePacket.GRP_BADGES_PLAYERS.get(p.field_71092_bJ)) + name, -sizeX + padding, -18, 0xFFFFFF, 0.75f, false, false);
                        }
                        if (ClientProxy.serverType.equals("ng")) {
                            if (ClientProxy.playersInAdminMode.containsKey(p.field_71092_bJ) && ClientProxy.playersInAdminMode.get(p.field_71092_bJ).booleanValue() && Minecraft.func_71382_s()) {
                                Minecraft.func_71410_x().func_110434_K().func_110577_a(AssistancePlayerGUI.GUI_TEXTURE);
                                GUIUtils.drawScaledCustomSizeModalRect(-sizeX + padding, -9, 1.0f, 320.0f, 37, 38, 6, 6, 512.0f, 512.0f);
                                ModernGui.drawScaledString(I18n.func_135053_a((String)"overlay.service"), -sizeX + padding + 6 + 2, -8, 0xFFFFFF, 0.5f, false, false);
                            } else if (playersFaction.containsKey(p.field_71092_bJ) && !playersFaction.get(p.field_71092_bJ).equals("")) {
                                String factionName = playersFaction.get(p.field_71092_bJ);
                                String factionNameWithoutColor = factionName.replaceFirst("\u00a7.{1}", "");
                                if (factionName.equals("no_country")) {
                                    factionName = I18n.func_135053_a((String)"overlay.no_country");
                                }
                                if (!(factionName == null || !factionsFlag.containsKey(factionNameWithoutColor) || factionsFlag.get(factionNameWithoutColor) == null || factionsFlag.get(factionNameWithoutColor).equals("") && factionsFlag.get(factionNameWithoutColor).contains(" "))) {
                                    BufferedImage image;
                                    ModernGui.drawScaledString(factionName, -sizeX + padding + 12 + 2, -9, 0xFFFFFF, 0.5f, false, false);
                                    if (!factionsFlagTexture.containsKey(factionNameWithoutColor) && (image = FactionGui_OLD.decodeToImage(factionsFlag.get(factionNameWithoutColor))) != null) {
                                        DynamicTexture t = new DynamicTexture(image);
                                        factionsFlagTexture.put(factionNameWithoutColor, t);
                                    }
                                    if (factionsFlagTexture.containsKey(factionNameWithoutColor)) {
                                        GL11.glBindTexture((int)3553, (int)factionsFlagTexture.get(factionNameWithoutColor).func_110552_b());
                                        ModernGui.drawScaledCustomSizeModalRect(-sizeX + padding, -10.0f, 0.0f, 0.0f, 156, 78, 12, 6, 156.0f, 78.0f, false);
                                    }
                                } else {
                                    ModernGui.drawScaledString(factionName, -sizeX + padding, -9, 0xFFFFFF, 0.5f, false, false);
                                }
                            }
                        }
                        if (ClientProxy.currentServerName.equals("aff") && ClientData.eventsInfos.size() > 0 && ClientData.eventsInfos.get(0).containsKey("playersKills")) {
                            Matcher matcherKills = Pattern.compile(p.field_71092_bJ + "#(\\d+)").matcher((String)ClientData.eventsInfos.get(0).get("playersKills"));
                            String score = matcherKills.find() ? matcherKills.group(1) : "0";
                            ModernGui.drawScaledString("\u00a7eScore: " + score, -sizeX + padding, -9, 0xFFFFFF, 0.5f, false, false);
                        }
                        this.drawRect(-sizeX + padding, 4, sizeX - padding, -2, 0x7F7F7F7F);
                        this.drawRect(-sizeX + padding, 4, (int)((float)minBoxWidth * (health / maxHealth) - (float)sizeX) - padding, -2, this.getHealthColor((Entity)entity, health, maxHealth));
                        ModernGui.drawScaledString(text, 0, 0, 0xFFFFFF, 0.4f, true, false);
                    } else {
                        GL11.glPushMatrix();
                        if (entity instanceof IAnimalFarm) {
                            IAnimalFarm animalFarm = (IAnimalFarm)entity;
                            float maturity = animalFarm.getMaturity();
                            this.drawRect(sizeX, -sizeY - 8, -sizeX, sizeY - 8, 0x40000000);
                            ModernGui.drawScaledString("Elevage:", -sizeX + padding, -18, 0xFFFFFF, 0.4f, false, false);
                            String healthStr = "\u00a74" + (int)health;
                            ModernGui.drawScaledString(healthStr, sizeX - padding - 6 - (int)((double)Minecraft.func_71410_x().field_71466_p.func_78256_a(healthStr) * 0.5), -18, 0xFFFFFF, 0.5f, false, false);
                            GL11.glPushMatrix();
                            GL11.glScalef((float)0.7f, (float)0.7f, (float)0.7f);
                            STYLE.bindTexture("hud");
                            ModernGui.drawModalRectWithCustomSizedTexture((float)(sizeX - padding - 5) / 0.7f, -25.714287f, 1, (double)(entity.func_110143_aJ() / entity.func_110138_aP()) >= 0.75 ? 58 : ((double)(entity.func_110143_aJ() / entity.func_110138_aP()) >= 0.3 ? 65 : 72), 7, 7, 256.0f, 256.0f, false);
                            GL11.glPopMatrix();
                            this.drawRect(-sizeX + padding, -7, sizeX - padding, -13, 0x7F7F7F7F);
                            this.drawRect(-sizeX + padding, -7, (int)((double)minBoxWidth * ((double)maturity / 100.0) - (double)sizeX) - padding, -13, 2139501803);
                            ModernGui.drawScaledString(maturity + "%", 0, -12, 0xFFFFFF, 0.4f, true, false);
                            GL11.glPushMatrix();
                            GL11.glScalef((float)0.4f, (float)0.4f, (float)0.4f);
                            GL11.glEnable((int)2896);
                            int index = 0;
                            for (String cerealName : animalFarm.getAcceptedCereals()) {
                                ItemStack cereal = new ItemStack(NGContent.getCerealIdFromName((String)cerealName).intValue(), 1, 0);
                                this.itemRenderer.func_82406_b(Minecraft.func_71410_x().field_71466_p, mc.func_110434_K(), cereal, (int)((float)(-sizeX + index * 10 + padding) / 0.4f), -12);
                                ++index;
                            }
                            GL11.glDisable((int)2896);
                            GL11.glPopMatrix();
                        } else if (!(ClientProxy.serverType.equals("tvg") || entity instanceof GenericGeckoEntity && !((GenericGeckoEntity)entity).showHealthBar() || entity instanceof EntityNPCInterface && (double)((EntityNPCInterface)entity).func_110138_aP() == 1.0 || entity instanceof GenericGeckoBikeRidingEntity || entity instanceof GenericGeckoBikeFlyingEntity || entity instanceof GenericGeckoBikeSwimmingEntity || entity instanceof EntityMobKey)) {
                            this.drawRect(-sizeX + padding, 4, sizeX - padding, -2, 0x7F7F7F7F);
                            this.drawRect(-sizeX + padding, 4, (int)((float)minBoxWidth * (health / maxHealth) - (float)sizeX) - padding, -2, this.getHealthColor((Entity)entity, health, maxHealth));
                            ModernGui.drawScaledString(text, 0, 0, 0xFFFFFF, 0.4f, true, false);
                        }
                        GL11.glPopMatrix();
                        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                    }
                    if (entity2 instanceof EntityPlayer) {
                        p = (EntityPlayer)entity2;
                        float size = 0.25f;
                        GL11.glPushMatrix();
                        GL11.glTranslated((double)((float)(-sizeX - padding * 2) - 32.0f * size), (double)((double)(-(32.0f * size)) * 1.5), (double)0.0);
                        GL11.glScalef((float)size, (float)size, (float)size);
                        if (VoiceChat.getProxyInstance().getSettings().isPlayerMuted(p.field_71092_bJ)) {
                            STYLE.bindTexture("voice_off");
                            ModernGui.drawModalRectWithCustomSizedTexture(0.0f, 0.0f, 0, 0, 32, 32, 32.0f, 32.0f, false);
                        } else if (ClientEventHandler.isPlayerTalk(p.field_71092_bJ)) {
                            STYLE.bindTexture("voice_vol");
                            switch ((int)(Minecraft.func_71386_F() % 1000L / 250L)) {
                                case 0: {
                                    ModernGui.drawModalRectWithCustomSizedTextureWithTransparency(0.0f, 0.0f, 0, 0, 19, 32, 128.0f, 32.0f, false);
                                    break;
                                }
                                case 1: {
                                    ModernGui.drawModalRectWithCustomSizedTextureWithTransparency(0.0f, 0.0f, 22, 0, 27, 32, 128.0f, 32.0f, false);
                                    break;
                                }
                                case 2: {
                                    ModernGui.drawModalRectWithCustomSizedTextureWithTransparency(0.0f, 0.0f, 53, 0, 31, 32, 128.0f, 32.0f, false);
                                    break;
                                }
                                case 3: {
                                    ModernGui.drawModalRectWithCustomSizedTextureWithTransparency(0.0f, 0.0f, 89, 0, 37, 32, 128.0f, 32.0f, false);
                                }
                            }
                        }
                        GL11.glPopMatrix();
                    }
                    GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                    GL11.glPopMatrix();
                    GL11.glDisable((int)3042);
                    GL11.glEnable((int)2929);
                    GL11.glDepthMask((boolean)true);
                    GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                    GL11.glPopMatrix();
                }
            }
            if ((riddenBy = entity.field_70153_n) instanceof EntityLivingBase) {
                entity = (EntityLivingBase)riddenBy;
            } else {
                return;
            }
        }
    }

    public void drawRect(int x, int y, int x2, int y2, int color) {
        float f = (float)(color >> 24 & 0xFF) / 255.0f;
        float f1 = (float)(color >> 16 & 0xFF) / 255.0f;
        float f2 = (float)(color >> 8 & 0xFF) / 255.0f;
        float f3 = (float)(color & 0xFF) / 255.0f;
        Tessellator tessellator = Tessellator.field_78398_a;
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3553);
        GL11.glBlendFunc((int)770, (int)771);
        tessellator.func_78382_b();
        tessellator.func_78369_a(f1, f2, f3, f);
        tessellator.func_78377_a((double)x, (double)y, 0.0);
        tessellator.func_78377_a((double)x2, (double)y, 0.0);
        tessellator.func_78377_a((double)x2, (double)y2, 0.0);
        tessellator.func_78377_a((double)x, (double)y2, 0.0);
        tessellator.func_78381_a();
        GL11.glEnable((int)3553);
        GL11.glDisable((int)3042);
    }

    @ForgeSubscribe
    public void onOverlayF3Render(RenderGameOverlayEvent.Text event) {
    }

    @ForgeSubscribe
    public void onOverlayRender(RenderGameOverlayEvent.Pre event) {
        for (ElementOverride override : this.elementOverrides) {
            List<RenderGameOverlayEvent.ElementType> subTypes;
            if (override.getType() == event.type) {
                event.setCanceled(true);
                if (Minecraft.func_71410_x().field_71462_r instanceof AbstractFirstConnectionGui) continue;
                override.renderOverride(Minecraft.func_71410_x(), event.resolution, event.partialTicks);
                continue;
            }
            if (override.getSubTypes() == null || !(subTypes = Arrays.asList(override.getSubTypes())).contains(event.type)) continue;
            event.setCanceled(true);
        }
        if (event.type == RenderGameOverlayEvent.ElementType.HEALTHMOUNT || event.type == RenderGameOverlayEvent.ElementType.JUMPBAR) {
            GL11.glPushMatrix();
            GL11.glTranslatef((float)0.0f, (float)-15.0f, (float)0.0f);
        }
        if (event.type == RenderGameOverlayEvent.ElementType.HOTBAR && this.tickHandlerClient != null) {
            RenderGameOverlayEvent.Post fakeEvent = new RenderGameOverlayEvent.Post((RenderGameOverlayEvent)event, RenderGameOverlayEvent.ElementType.HOTBAR);
            try {
                this.tickHandlerClient.getClass().getDeclaredMethod("eventHandler", RenderGameOverlayEvent.class).invoke(this.tickHandlerClient, fakeEvent);
            }
            catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        if (Minecraft.func_71410_x().field_71462_r == null && this.snackbarGUI != null && event.type == RenderGameOverlayEvent.ElementType.HOTBAR) {
            this.snackbarGUI.drawSnackbar();
        }
    }

    @ForgeSubscribe
    public void onOverlayRenderPost(RenderGameOverlayEvent.Post event) {
        MovingObjectPosition movingObjectPosition;
        if (event.type == RenderGameOverlayEvent.ElementType.HEALTHMOUNT || event.type == RenderGameOverlayEvent.ElementType.JUMPBAR) {
            GL11.glPopMatrix();
        }
        if (ClientProxy.clientConfig.blockInfoEnabled && (movingObjectPosition = Minecraft.func_71410_x().field_71476_x) != null && movingObjectPosition.field_72313_a == EnumMovingObjectType.TILE) {
            int wMin;
            FontRenderer fontRenderer = Minecraft.func_71410_x().field_71466_p;
            int id = Minecraft.func_71410_x().field_71441_e.func_72798_a(movingObjectPosition.field_72311_b, movingObjectPosition.field_72312_c, movingObjectPosition.field_72309_d);
            int meta = Minecraft.func_71410_x().field_71441_e.func_72805_g(movingObjectPosition.field_72311_b, movingObjectPosition.field_72312_c, movingObjectPosition.field_72309_d);
            ItemStack itemStack = new ItemStack(id, 1, meta);
            GameRegistry.UniqueIdentifier uniqueIdentifier = GameRegistry.findUniqueIdentifierFor((Block)Block.field_71973_m[id]);
            String modName = "Minecraft";
            if (uniqueIdentifier != null) {
                for (ModContainer mod : Loader.instance().getModList()) {
                    if (!mod.getModId().equals(uniqueIdentifier.modId)) continue;
                    modName = mod.getName();
                }
            }
            RenderItem itemRenderer = new RenderItem();
            String blockName = Block.field_71973_m[id].func_71931_t() + " (#" + id + ":" + meta + ")";
            int w = 20 + fontRenderer.func_78256_a(blockName);
            if (w < (wMin = fontRenderer.func_78256_a(modName) + 20)) {
                w = wMin;
            }
            int h = 17;
            int durability = -1;
            TileEntity tileEntity = Minecraft.func_71410_x().field_71441_e.func_72796_p(movingObjectPosition.field_72311_b, movingObjectPosition.field_72312_c, movingObjectPosition.field_72309_d);
            if (tileEntity instanceof GCCoreTileEntityUniversalElectrical) {
                durability = ((GCCoreTileEntityUniversalElectrical)tileEntity).durability;
                h = 30;
            }
            ScaledResolution resolution = new ScaledResolution(Minecraft.func_71410_x().field_71474_y, Minecraft.func_71410_x().field_71443_c, Minecraft.func_71410_x().field_71440_d);
            int x = resolution.func_78326_a() / 2 - w / 2;
            int y = 5;
            GL11.glPushMatrix();
            int l1 = -267386864;
            GUIUtils.drawGradientRect(x - 3, y - 4, x + w + 3, y - 3, l1, l1);
            GUIUtils.drawGradientRect(x - 3, y + h + 3, x + w + 3, y + h + 4, l1, l1);
            GUIUtils.drawGradientRect(x - 3, y - 3, x + w + 3, y + h + 3, l1, l1);
            GUIUtils.drawGradientRect(x - 4, y - 3, x - 3, y + h + 3, l1, l1);
            GUIUtils.drawGradientRect(x + w + 3, y - 3, x + w + 4, y + h + 3, l1, l1);
            int i2 = 0x505000FF;
            int j2 = (i2 & 0xFEFEFE) >> 1 | i2 & 0xFF000000;
            GUIUtils.drawGradientRect(x - 3, y - 3 + 1, x - 3 + 1, y + h + 3 - 1, i2, j2);
            GUIUtils.drawGradientRect(x + w + 2, y - 3 + 1, x + w + 3, y + h + 3 - 1, i2, j2);
            GUIUtils.drawGradientRect(x - 3, y - 3, x + w + 3, y - 3 + 1, i2, i2);
            GUIUtils.drawGradientRect(x - 3, y + h + 2, x + w + 3, y + h + 3, j2, j2);
            GL11.glEnable((int)3042);
            GL11.glEnable((int)32826);
            RenderHelper.func_74520_c();
            try {
                itemRenderer.func_77015_a(fontRenderer, Minecraft.func_71410_x().func_110434_K(), itemStack, x, y);
            }
            catch (Exception exception) {
                // empty catch block
            }
            RenderHelper.func_74518_a();
            GL11.glDisable((int)32826);
            GL11.glDisable((int)3042);
            fontRenderer.func_78276_b(blockName, x + 20, y + 4, 0xFFFFFF);
            if (durability != -1) {
                int i;
                Minecraft.func_71410_x().func_110434_K().func_110577_a(DURA_ICON);
                ModernGui.drawModalRectWithCustomSizedTexture((float)x + (float)w / 2.0f - (float)fontRenderer.func_78256_a("||||||||||||||||||||||||||||||||||||||||") / 2.0f - 12.0f, y + 17, 0, 0, 16, 16, 16.0f, 16.0f, false);
                String color = "";
                color = durability >= 70 ? "\u00a7a" : (durability >= 35 ? "\u00a76" : "\u00a74");
                String bar = "";
                for (i = 1; i <= Math.round((float)durability / 2.5f); ++i) {
                    bar = bar + color + "|";
                }
                for (i = 1; i <= 40 - Math.round((float)durability / 2.5f); ++i) {
                    bar = bar + "\u00a77|";
                }
                fontRenderer.func_78276_b(bar, x + w / 2 - fontRenderer.func_78256_a("||||||||||||||||||||||||||||||||||||||||") / 2 + 8, y + 20, 0xD11B1B);
            }
            GL11.glColor3f((float)1.0f, (float)1.0f, (float)1.0f);
            GL11.glPopMatrix();
        }
    }

    @ForgeSubscribe
    public void onTextureStitch(TextureStitchEvent.Pre event) {
        block8: {
            block7: {
                if (event.map.func_130086_a() != 0) break block7;
                for (JSONBlock block : JSONRegistries.getRegistry(JSONBlock.class).getRegistry()) {
                    if (block.textureFrontString != null) {
                        block.textureFront = new JSONTexture(block.textureFrontString);
                        event.map.setTextureEntry(block.textureFrontString, (TextureAtlasSprite)block.textureFront);
                    }
                    if (block.textureBackString != null) {
                        block.textureBack = new JSONTexture(block.textureBackString);
                        event.map.setTextureEntry(block.textureBackString, (TextureAtlasSprite)block.textureBack);
                    }
                    if (block.textureTopString != null) {
                        block.textureTop = new JSONTexture(block.textureTopString);
                        event.map.setTextureEntry(block.textureTopString, (TextureAtlasSprite)block.textureTop);
                    }
                    if (block.textureBottomString != null) {
                        block.textureBottom = new JSONTexture(block.textureBottomString);
                        event.map.setTextureEntry(block.textureBottomString, (TextureAtlasSprite)block.textureBottom);
                    }
                    if (block.textureSideString == null) continue;
                    block.textureSide = new JSONTexture(block.textureSideString);
                    event.map.setTextureEntry(block.textureSideString, (TextureAtlasSprite)block.textureSide);
                }
                break block8;
            }
            if (event.map.func_130086_a() != 1) break block8;
            for (JSONArmorSet armorSet : JSONRegistries.getRegistry(JSONArmorSet.class).getRegistry()) {
                for (int i = 0; i < armorSet.getArmorSet().length; ++i) {
                    JSONArmor armor = armorSet.getArmorSet()[i];
                    if (armor == null) continue;
                    event.map.setTextureEntry(armor.iconHash, armor.setIcon(new JSONTexture(armor.iconHash)));
                }
            }
        }
    }

    @ForgeSubscribe
    public void onTooltip(ItemTooltipEvent event) {
        if (Minecraft.func_71410_x().field_71462_r instanceof RecipeListGUI) {
            event.toolTip.add("ID " + event.itemStack.field_77993_c + ":" + event.itemStack.func_77960_j());
        } else if (Minecraft.func_71410_x().field_71462_r instanceof InventoryGUI && ClientProxy.serverType.equals("ng") && ClientKeyHandler.KEY_SELL.field_74512_d > 0) {
            event.toolTip.add(I18n.func_135052_a((String)"sell.press", (Object[])new Object[]{Keyboard.getKeyName((int)ClientKeyHandler.KEY_SELL.field_74512_d)}));
        }
    }

    @ForgeSubscribe
    public void onWorldRendering(RenderWorldLastEvent event) {
        EntityClientPlayerMP entity = Minecraft.func_71410_x().field_71439_g;
        GL11.glPushMatrix();
        RenderUtils.translateToWorldCoords((Entity)entity, event.partialTicks);
        if (ClientKeyHandler.displayChunck) {
            this.renderChunkBounds((Entity)entity);
        }
        if (ClientKeyHandler.displayMobSpawning) {
            this.renderMobSpawnOverlay((Entity)entity);
        }
        GL11.glPopMatrix();
    }

    private void renderChunkBounds(Entity entity) {
        GL11.glDisable((int)3553);
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glDisable((int)2896);
        GL11.glLineWidth((float)1.5f);
        GL11.glBegin((int)1);
        for (int cx = -4; cx <= 4; ++cx) {
            for (int cz = -4; cz <= 4; ++cz) {
                double x1 = entity.field_70176_ah + cx << 4;
                double z1 = entity.field_70164_aj + cz << 4;
                double x2 = x1 + 16.0;
                double z2 = z1 + 16.0;
                double dy = 128.0;
                double y1 = Math.floor(entity.field_70163_u - dy / 2.0);
                double y2 = y1 + dy;
                if (y1 < 0.0) {
                    y1 = 0.0;
                    y2 = dy;
                }
                if (y1 > (double)entity.field_70170_p.func_72800_K()) {
                    y2 = entity.field_70170_p.func_72800_K();
                    y1 = y2 - dy;
                }
                double dist = Math.pow(1.5, -(cx * cx + cz * cz));
                GL11.glColor4d((double)0.9, (double)0.0, (double)0.0, (double)dist);
                if (cx >= 0 && cz >= 0) {
                    GL11.glVertex3d((double)x2, (double)y1, (double)z2);
                    GL11.glVertex3d((double)x2, (double)y2, (double)z2);
                }
                if (cx >= 0 && cz <= 0) {
                    GL11.glVertex3d((double)x2, (double)y1, (double)z1);
                    GL11.glVertex3d((double)x2, (double)y2, (double)z1);
                }
                if (cx <= 0 && cz >= 0) {
                    GL11.glVertex3d((double)x1, (double)y1, (double)z2);
                    GL11.glVertex3d((double)x1, (double)y2, (double)z2);
                }
                if (cx <= 0 && cz <= 0) {
                    GL11.glVertex3d((double)x1, (double)y1, (double)z1);
                    GL11.glVertex3d((double)x1, (double)y2, (double)z1);
                }
                if (cx != 0 || cz != 0) continue;
                dy = 32.0;
                y1 = Math.floor(entity.field_70163_u - dy / 2.0);
                y2 = y1 + dy;
                if (y1 < 0.0) {
                    y1 = 0.0;
                    y2 = dy;
                }
                if (y1 > (double)entity.field_70170_p.func_72800_K()) {
                    y2 = entity.field_70170_p.func_72800_K();
                    y1 = y2 - dy;
                }
                GL11.glColor4d((double)0.0, (double)0.9, (double)0.0, (double)0.4);
                for (double y = (double)((int)y1); y <= y2; y += 1.0) {
                    GL11.glVertex3d((double)x2, (double)y, (double)z1);
                    GL11.glVertex3d((double)x2, (double)y, (double)z2);
                    GL11.glVertex3d((double)x1, (double)y, (double)z1);
                    GL11.glVertex3d((double)x1, (double)y, (double)z2);
                    GL11.glVertex3d((double)x1, (double)y, (double)z2);
                    GL11.glVertex3d((double)x2, (double)y, (double)z2);
                    GL11.glVertex3d((double)x1, (double)y, (double)z1);
                    GL11.glVertex3d((double)x2, (double)y, (double)z1);
                }
                for (double h = 1.0; h <= 15.0; h += 1.0) {
                    GL11.glVertex3d((double)(x1 + h), (double)y1, (double)z1);
                    GL11.glVertex3d((double)(x1 + h), (double)y2, (double)z1);
                    GL11.glVertex3d((double)(x1 + h), (double)y1, (double)z2);
                    GL11.glVertex3d((double)(x1 + h), (double)y2, (double)z2);
                    GL11.glVertex3d((double)x1, (double)y1, (double)(z1 + h));
                    GL11.glVertex3d((double)x1, (double)y2, (double)(z1 + h));
                    GL11.glVertex3d((double)x2, (double)y1, (double)(z1 + h));
                    GL11.glVertex3d((double)x2, (double)y2, (double)(z1 + h));
                }
            }
        }
        GL11.glEnd();
        GL11.glEnable((int)2896);
        GL11.glEnable((int)3553);
        GL11.glDisable((int)3042);
    }

    private void renderMobSpawnOverlay(Entity entity) {
        GL11.glDisable((int)3553);
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glDisable((int)2896);
        GL11.glLineWidth((float)1.5f);
        GL11.glBegin((int)1);
        GL11.glColor4f((float)1.0f, (float)0.0f, (float)0.0f, (float)1.0f);
        int curSpawnMode = 2;
        World world = entity.field_70170_p;
        int x1 = (int)entity.field_70165_t;
        int z1 = (int)entity.field_70161_v;
        int y1 = (int)MathHelper.clip(entity.field_70163_u, 16.0, world.func_72800_K() - 16);
        AxisAlignedBB aabb = AxisAlignedBB.func_72330_a((double)0.0, (double)0.0, (double)0.0, (double)0.0, (double)0.0, (double)0.0);
        for (int x = x1 - 16; x <= x1 + 16; ++x) {
            for (int z = z1 - 16; z <= z1 + 16; ++z) {
                Chunk chunk = world.func_72938_d(x, z);
                BiomeGenBase biome = world.func_72807_a(x, z);
                if (biome.func_76747_a(EnumCreatureType.monster).isEmpty() || !(biome.func_76741_f() > 0.0f)) continue;
                for (int y = y1 - 16; y < y1 + 16; ++y) {
                    int spawnMode = this.getSpawnMode(chunk, aabb, x, y, z);
                    if (spawnMode == 0) continue;
                    if (spawnMode != curSpawnMode) {
                        if (spawnMode == 1) {
                            GL11.glColor4f((float)1.0f, (float)1.0f, (float)0.0f, (float)1.0f);
                        } else {
                            GL11.glColor4f((float)1.0f, (float)0.0f, (float)0.0f, (float)1.0f);
                        }
                        curSpawnMode = spawnMode;
                    }
                    GL11.glVertex3d((double)x, (double)((double)y + 0.004), (double)z);
                    GL11.glVertex3d((double)(x + 1), (double)((double)y + 0.004), (double)(z + 1));
                    GL11.glVertex3d((double)(x + 1), (double)((double)y + 0.004), (double)z);
                    GL11.glVertex3d((double)x, (double)((double)y + 0.004), (double)(z + 1));
                }
            }
        }
        GL11.glEnd();
        GL11.glEnable((int)2896);
        GL11.glEnable((int)3553);
        GL11.glDisable((int)3042);
    }

    private int getSpawnMode(Chunk chunk, AxisAlignedBB aabb, int x, int y, int z) {
        if (!SpawnerAnimals.func_77190_a((EnumCreatureType)EnumCreatureType.monster, (World)chunk.field_76637_e, (int)x, (int)y, (int)z) || chunk.func_76614_a(EnumSkyBlock.Block, x & 0xF, y, z & 0xF) >= 8) {
            return 0;
        }
        aabb.field_72340_a = (double)x + 0.2;
        aabb.field_72336_d = (double)x + 0.8;
        aabb.field_72338_b = (double)y + 0.01;
        aabb.field_72337_e = (double)y + 1.8;
        aabb.field_72339_c = (double)z + 0.2;
        aabb.field_72334_f = (double)z + 0.8;
        if (!chunk.field_76637_e.func_72855_b(aabb) || !chunk.field_76637_e.func_72840_a(aabb).isEmpty() || chunk.field_76637_e.func_72953_d(aabb)) {
            return 0;
        }
        if (chunk.func_76614_a(EnumSkyBlock.Sky, x & 0xF, y, z & 0xF) >= 8) {
            return 1;
        }
        return 2;
    }

    public ObjectiveOverride getObjectiveOverlay() {
        return this.objective;
    }

    public TitleOverride getTitleOverlay() {
        return this.title;
    }

    public String getBadgeTextColor(String badge) {
        if (badge == null) {
            return "";
        }
        switch (badge) {
            case "fondateur": 
            case "co-fonda": 
            case "founder": 
            case "co-founder": {
                return "\u00a7b";
            }
            case "respadmin": 
            case "adminmanager": 
            case "admin": 
            case "supermodo": {
                return "\u00a7c";
            }
            case "moderateur": 
            case "moderateur_plus": 
            case "moderateur_test": 
            case "mod": 
            case "mod_plus": 
            case "mod_test": {
                return "\u00a7a";
            }
            case "premium": {
                return "\u00a76";
            }
            case "legende": 
            case "legend": {
                return "\u00a73";
            }
            case "heros": 
            case "hero": {
                return "\u00a77";
            }
            case "affiliate": {
                return "\u00a7d";
            }
        }
        return "";
    }

    public int getBadgeBGColor(String badge) {
        if (badge == null) {
            return 0x40000000;
        }
        switch (badge.replaceAll("badges_", "")) {
            case "fondateur": 
            case "co-fonda": 
            case "founder": 
            case "co-founder": {
                return 0x40074447;
            }
            case "respadmin": 
            case "adminmanager": 
            case "admin": 
            case "supermodo": {
                return 0x40470707;
            }
            case "moderateur": 
            case "moderateur_plus": 
            case "moderateur_test": 
            case "mod": 
            case "mod_plus": 
            case "mod_test": {
                return 1074808583;
            }
            case "premium": {
                return 1078409991;
            }
            case "legende": 
            case "legend": {
                return 1074218805;
            }
            case "heros": 
            case "hero": {
                return 1076307751;
            }
            case "affiliate": {
                return 1077610039;
            }
        }
        return 0x40000000;
    }

    public int getHealthColor(Entity entity, float health, float maxHealth) {
        if (entity instanceof CarePackageEntity) {
            return 2146707018;
        }
        float ratio = health / maxHealth;
        if (ratio >= 0.75f) {
            if (Minecraft.func_71410_x().field_71441_e.field_73011_w instanceof GCEdoraWorldProvider) {
                return 2138695154;
            }
            return 0x7F00FF00;
        }
        if (ratio >= 0.5f) {
            return 2146954241;
        }
        if (ratio >= 0.25f) {
            return 2146929409;
        }
        return 2146898945;
    }

    public void openMoonGui(boolean goalAchieved, double goal, double actualMoney, List<String> donators) {
        Minecraft.func_71410_x().func_71373_a((GuiScreen)new ToTheMoonGUI(!goalAchieved, goal, actualMoney, donators));
    }

    @ForgeSubscribe
    public void onPlayerPreRender(RenderPlayerEvent.Pre event) {
        if (!ClientProxy.clientConfig.renderCustomArmors) {
            ItemStack currentItem;
            this.armorSave = (ItemStack[])event.entityPlayer.field_71071_by.field_70460_b.clone();
            this.itemSave = currentItem = event.entityPlayer.field_71071_by.func_70448_g();
            for (int i = 0; i < event.entityPlayer.field_71071_by.field_70460_b.length; ++i) {
                ItemStack slot = event.entityPlayer.field_71071_by.field_70460_b[i];
                if (slot == null || !renderLightClassBlacklist.contains(slot.func_77973_b().getClass()) && !renderLightIdsBlacklist.contains(slot.field_77993_c)) continue;
                ItemStack newItem = null;
                switch (i) {
                    case 0: {
                        newItem = new ItemStack((Item)Item.field_77802_ao);
                        break;
                    }
                    case 1: {
                        newItem = new ItemStack((Item)Item.field_77808_an);
                        break;
                    }
                    case 2: {
                        newItem = new ItemStack((Item)Item.field_77806_am);
                        break;
                    }
                    case 3: {
                        newItem = new ItemStack((Item)Item.field_77796_al);
                    }
                }
                if (newItem == null) continue;
                newItem.func_77966_a(Enchantment.field_77342_w, 1);
                event.entityPlayer.field_71071_by.field_70460_b[i] = newItem;
            }
            if (currentItem != null && (renderLightClassBlacklist.contains(currentItem.func_77973_b().getClass()) || renderLightIdsBlacklist.contains(currentItem.field_77993_c))) {
                ItemStack sword = new ItemStack(Item.field_77672_G);
                sword.func_77966_a(Enchantment.field_77342_w, 1);
                event.entityPlayer.field_71071_by.func_70299_a(event.entityPlayer.field_71071_by.field_70461_c, sword);
            }
        }
    }

    @ForgeSubscribe
    public void onPlayerPostRender(RenderPlayerEvent.Post event) {
        if (!ClientProxy.clientConfig.renderCustomArmors) {
            event.entityPlayer.field_71071_by.field_70460_b = this.armorSave;
            event.entityPlayer.field_71071_by.func_70299_a(event.entityPlayer.field_71071_by.field_70461_c, this.itemSave);
        }
    }

    @ForgeSubscribe
    public void onPreRender(UpdateLightMapEvent event) {
        if (System.currentTimeMillis() - lastPlasmaCounter < 1000L) {
            event.r = 0.25882354f;
            event.b = 0.88235295f;
            event.g = 1.0f;
        } else if (System.currentTimeMillis() - lastLaserPlasmaHit < 5000L) {
            event.r = 1.0f;
            event.b = 0.1f;
            event.g = 0.1f;
        } else if (Minecraft.func_71410_x().field_71439_g.func_70660_b((Potion)PotionParanoia.potionParanoia) != null) {
            event.r = 1.0f;
            event.b = 0.0f;
            event.g = 0.0f;
        }
        if (ClientData.customRenderColorRed != 1.0f || ClientData.customRenderColorGreen != 1.0f || ClientData.customRenderColorBlue != 1.0f) {
            event.r = ClientData.customRenderColorRed;
            event.g = ClientData.customRenderColorGreen;
            event.b = ClientData.customRenderColorBlue;
        }
    }

    @ForgeSubscribe
    public void onPlayStepSoundHub(PlaySoundEvent event) {
        if (Minecraft.func_71410_x().field_71441_e.field_72995_K && ClientProxy.currentServerName.contains("hub") && event.name != null && event.name.contains("step")) {
            event.result = null;
        }
    }

    public static ClientEventHandler getInstance() {
        if (instance == null) {
            instance = new ClientEventHandler();
        }
        return instance;
    }

    static {
        currentClicks = 0;
        lastClicks = 0;
        modsChecked = false;
        lastPlasmaCounter = 0L;
        lastLaserPlasmaHit = 0L;
        md5list = new ArrayList<String>();
        modNamelist = new ArrayList<String>();
        playersFaction = new HashMap<String, String>();
        factionsFlag = new HashMap<String, String>();
        factionsFlagTexture = new HashMap<String, DynamicTexture>();
        MACHINE_WITH_DURABILITY = new ArrayList<String>(Arrays.asList("GCCoreTileEntitySolar", "GCCrusherBlockEntity", "GCEcotronBlockEntity", "GCFishFeederBlockEntity", "GCIncineratorBlockEntity", "GCIncubatorBlockEntity", "GCOilGeneratorBlockEntity", "GCOilPumpBlockEntity", "GCPotionsBlockEntity", "GCPrinterBlockEntity", "GCPumpBlockEntity", "GCRandomBlockEntity", "GCRepairBlockEntity", "GCTraderBlockEntity", "GCCoreTileEntityCoalGenerator", "GCCoreTileEntityElectricFurnace", "GCCoreTileEntityElectricIngotCompressor", "GCCogsAssemblerBlockEntity", "GCPetrolPumpJackBlockEntity", "GCRefineryBlockEntity", "GCFuelGeneratorBlockEntity", "GCElectricCollectorBlockEntity", "GCBioFuelGeneratorEntity", "GCPetrolRubberBlockEntity", "GCFlourMachineBlockEntity"));
        playersIslandTeamPrefix = new HashMap<String, String>();
        playersIslandTeamPrefixLastRefresh = new HashMap<String, Long>();
        lastPlayerDispayTAB = 0L;
        cerealsByAnimal = new HashMap<String, List<String>>(){
            {
                this.put("chicken", Arrays.asList("wheat", "barley", "oats", "rye", "corn", "sunflower"));
                this.put("pig", Arrays.asList("barley", "oats", "rye"));
                this.put("cow", Arrays.asList("barley", "soybean", "corn"));
            }
        };
        NGPrimeTimer = 0L;
        NGPrimeTextInterval = 5000L;
    }
}

