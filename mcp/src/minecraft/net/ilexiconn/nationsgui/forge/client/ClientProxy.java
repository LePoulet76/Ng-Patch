/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.io.ByteArrayDataInput
 *  com.google.gson.Gson
 *  com.google.gson.reflect.TypeToken
 *  cpw.mods.fml.client.registry.ClientRegistry
 *  cpw.mods.fml.client.registry.KeyBindingRegistry
 *  cpw.mods.fml.client.registry.KeyBindingRegistry$KeyHandler
 *  cpw.mods.fml.client.registry.RenderingRegistry
 *  cpw.mods.fml.common.FMLCommonHandler
 *  cpw.mods.fml.common.ICrashCallable
 *  cpw.mods.fml.common.ITickHandler
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  cpw.mods.fml.common.registry.LanguageRegistry
 *  cpw.mods.fml.common.registry.TickRegistry
 *  cpw.mods.fml.relauncher.ReflectionHelper
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  fr.nationsglory.ngbrowser.NGBrowser
 *  fr.nationsglory.ngbrowser.client.ClientProxy
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.model.ModelBiped
 *  net.minecraft.client.model.ModelRenderer
 *  net.minecraft.client.renderer.IImageBuffer
 *  net.minecraft.client.renderer.ImageBufferDownload
 *  net.minecraft.client.renderer.entity.Render
 *  net.minecraft.client.renderer.texture.DynamicTexture
 *  net.minecraft.client.renderer.texture.TextureManager
 *  net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer
 *  net.minecraft.client.resources.I18n
 *  net.minecraft.command.ICommand
 *  net.minecraft.crash.CrashReport
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.Item
 *  net.minecraft.item.crafting.IRecipe
 *  net.minecraft.item.crafting.ShapedRecipes
 *  net.minecraft.item.crafting.ShapelessRecipes
 *  net.minecraft.network.packet.Packet
 *  net.minecraft.tileentity.TileEntitySkull
 *  net.minecraft.util.ResourceLocation
 *  net.minecraft.util.StatCollector
 *  net.minecraft.util.Timer
 *  net.minecraftforge.client.ClientCommandHandler
 *  net.minecraftforge.client.IItemRenderer
 *  net.minecraftforge.client.MinecraftForgeClient
 *  net.minecraftforge.client.model.obj.WavefrontObject
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.oredict.ShapedOreRecipe
 *  net.minecraftforge.oredict.ShapelessOreRecipe
 *  noppes.npcs.controllers.RecipeCarpentry
 *  org.apache.commons.io.FilenameUtils
 */
package net.ilexiconn.nationsgui.forge.client;

import com.google.common.io.ByteArrayDataInput;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.KeyBindingRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.ICrashCallable;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.ReflectionHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.nationsglory.ngbrowser.NGBrowser;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Field;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import net.ilexiconn.nationsgui.forge.NationsGUI;
import net.ilexiconn.nationsgui.forge.client.ClientData;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.ClientKeyHandler;
import net.ilexiconn.nationsgui.forge.client.ClientSocket;
import net.ilexiconn.nationsgui.forge.client.ClientTickHandler;
import net.ilexiconn.nationsgui.forge.client.RecipeWatcher;
import net.ilexiconn.nationsgui.forge.client.chat.ChatHandler;
import net.ilexiconn.nationsgui.forge.client.chat.IChatFallback;
import net.ilexiconn.nationsgui.forge.client.commands.FovCommand;
import net.ilexiconn.nationsgui.forge.client.commands.SkinDebugCommand;
import net.ilexiconn.nationsgui.forge.client.data.ClientConfig;
import net.ilexiconn.nationsgui.forge.client.data.ServersData;
import net.ilexiconn.nationsgui.forge.client.emotes.ClientEmotesHandler;
import net.ilexiconn.nationsgui.forge.client.gui.FirstConnectionGui;
import net.ilexiconn.nationsgui.forge.client.gui.SnackbarGUI;
import net.ilexiconn.nationsgui.forge.client.gui.auth.AuthGUI;
import net.ilexiconn.nationsgui.forge.client.gui.auth.type.AuthTypes;
import net.ilexiconn.nationsgui.forge.client.gui.chat.tag.ChatTagManager;
import net.ilexiconn.nationsgui.forge.client.gui.override.HotbarOverride;
import net.ilexiconn.nationsgui.forge.client.item.FakeItemSkin;
import net.ilexiconn.nationsgui.forge.client.itemskin.SkinManager;
import net.ilexiconn.nationsgui.forge.client.itemskin.SkinType;
import net.ilexiconn.nationsgui.forge.client.recipes.watchers.RecipeCarpentryWatcher;
import net.ilexiconn.nationsgui.forge.client.recipes.watchers.ShapedOreRecipeWatcher;
import net.ilexiconn.nationsgui.forge.client.recipes.watchers.ShapedRecipesWatcher;
import net.ilexiconn.nationsgui.forge.client.recipes.watchers.ShapelessOreRecipeWatcher;
import net.ilexiconn.nationsgui.forge.client.recipes.watchers.ShapelessRecipesWatcher;
import net.ilexiconn.nationsgui.forge.client.render.block.HatBlockRenderer;
import net.ilexiconn.nationsgui.forge.client.render.block.ImageHologramRenderer;
import net.ilexiconn.nationsgui.forge.client.render.block.RadioBlockRenderer;
import net.ilexiconn.nationsgui.forge.client.render.block.RenderTransparentTileEntity;
import net.ilexiconn.nationsgui.forge.client.render.block.SkullBlockRenderer;
import net.ilexiconn.nationsgui.forge.client.render.block.SpeakerBlockRenderer;
import net.ilexiconn.nationsgui.forge.client.render.entity.ArmorSkinRenderer;
import net.ilexiconn.nationsgui.forge.client.render.entity.ClearPlayerModels;
import net.ilexiconn.nationsgui.forge.client.render.entity.ExtendedPlayerSkinRenderer;
import net.ilexiconn.nationsgui.forge.client.render.entity.HandsSkinRenderer;
import net.ilexiconn.nationsgui.forge.client.render.entity.RenderPictureFrame;
import net.ilexiconn.nationsgui.forge.client.render.item.ItemBadgeRenderer;
import net.ilexiconn.nationsgui.forge.client.render.item.ItemHatRenderer;
import net.ilexiconn.nationsgui.forge.client.render.item.RadioItemRenderer;
import net.ilexiconn.nationsgui.forge.client.render.item.SkullItemRenderer;
import net.ilexiconn.nationsgui.forge.client.render.item.SpawnerItemRenderer;
import net.ilexiconn.nationsgui.forge.client.render.item.SpeakerItemRenderer;
import net.ilexiconn.nationsgui.forge.client.render.texture.DownloadableTexture;
import net.ilexiconn.nationsgui.forge.server.ServerGUIHandler;
import net.ilexiconn.nationsgui.forge.server.ServerProxy;
import net.ilexiconn.nationsgui.forge.server.block.entity.ImageHologramBlockEntity;
import net.ilexiconn.nationsgui.forge.server.block.entity.RadioBlockEntity;
import net.ilexiconn.nationsgui.forge.server.block.entity.SpeakerBlockEntity;
import net.ilexiconn.nationsgui.forge.server.block.entity.TileEntityHatBlock;
import net.ilexiconn.nationsgui.forge.server.block.entity.URLBlockEntity;
import net.ilexiconn.nationsgui.forge.server.entity.EntityPictureFrame;
import net.ilexiconn.nationsgui.forge.server.json.CategoryJSON;
import net.ilexiconn.nationsgui.forge.server.json.registry.JSONRegistries;
import net.ilexiconn.nationsgui.forge.server.json.registry.JSONRegistry;
import net.ilexiconn.nationsgui.forge.server.json.registry.armor.JSONArmorSet;
import net.ilexiconn.nationsgui.forge.server.json.registry.armor.property.IconProperty;
import net.ilexiconn.nationsgui.forge.server.json.registry.block.JSONBlock;
import net.ilexiconn.nationsgui.forge.server.json.registry.block.property.TextureProperty;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionGetImagePacket;
import net.ilexiconn.nationsgui.forge.server.trade.enums.EnumGui;
import net.ilexiconn.nationsgui.forge.server.util.SoundStreamer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.IImageBuffer;
import net.minecraft.client.renderer.ImageBufferDownload;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.resources.I18n;
import net.minecraft.command.ICommand;
import net.minecraft.crash.CrashReport;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.network.packet.Packet;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.util.Timer;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.model.obj.WavefrontObject;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import noppes.npcs.controllers.RecipeCarpentry;
import org.apache.commons.io.FilenameUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import sun.misc.BASE64Decoder;

@SideOnly(value=Side.CLIENT)
public class ClientProxy
extends ServerProxy {
    public static List<SnackbarGUI> SNACKBAR_LIST = new ArrayList<SnackbarGUI>();
    public static List<SoundStreamer> STREAMER_LIST = new ArrayList<SoundStreamer>();
    public static boolean IS_ONLINE;
    public static Timer TIMER;
    public static ServersData serversData;
    public static String zone;
    public static boolean multiRespawn;
    public static ClientConfig clientConfig;
    public static float blockReach;
    private static File configFile;
    private static Gson gson;
    public static SoundStreamer commandPlayer;
    public static boolean openFirstConnectionGuiAfterAuthMe;
    public static boolean openFirstConnectionGui;
    public static String currentServerName;
    public static Map<Class<? extends IRecipe>, RecipeWatcher> recipeWatcherMap;
    public static String serverType;
    public static String serverIp;
    public static String serverPort;
    public static String sessionID;
    public static ArrayList<HashMap<String, Object>> wiki;
    public static ClientSocket clientSocket;
    public static CategoryJSON[] containers;
    public static Long lastCollidedWithPortalTime;
    public static int lastCollidedWithPortalX;
    public static int lastCollidedWithPortalY;
    public static int lastCollidedWithPortalZ;
    public static Long currentServerTime;
    private static ExtendedPlayerSkinRenderer hatsRenderer;
    private static ClientEmotesHandler emotes;
    public static HashMap<String, String> base64FlagsByFactionName;
    public static HashMap<String, DynamicTexture> flagsTexture;
    public static HashMap<String, Boolean> playersInAdminMode;
    public static HashMap<String, ResourceLocation> cachedResources;
    public static HashMap<String, DownloadableTexture> cachedRemoteResources;
    public static HashMap<String, ResourceLocation> cacheHeadPlayer;
    public static HashMap<String, ResourceLocation> cachedCapes;
    public static final SkinManager SKIN_MANAGER;

    public static void preLoadImageMinecraftGUI() {
        ClientProxy.getRemoteResource("https://apiv2.nationsglory.fr/proxy_images/screen_loading");
        ClientProxy.getRemoteResource("https://apiv2.nationsglory.fr/proxy_images/screen_join_all");
        ClientProxy.getRemoteResource("https://apiv2.nationsglory.fr/proxy_images/screen_join_hub");
        ClientProxy.getRemoteResource("https://apiv2.nationsglory.fr/proxy_images/screen_waiting");
    }

    public static void loadWiki() {
        wiki = new ArrayList();
        JSONParser parser = new JSONParser();
        try {
            JSONArray list = (JSONArray)parser.parse(new FileReader("assets/indexes/wiki.json"));
            if (list != null) {
                block2: for (Object o : list) {
                    if (!(o instanceof JSONObject)) continue;
                    JSONObject obj = (JSONObject)o;
                    HashMap<String, Object> categoryInfos = new HashMap<String, Object>();
                    categoryInfos.put("name", (String)obj.get("name"));
                    categoryInfos.put("displayName", (String)obj.get("displayName"));
                    if (!obj.containsKey("parent")) {
                        categoryInfos.put("children", new ArrayList());
                        if (obj.containsKey("tags")) {
                            categoryInfos.put("tags", (List)obj.get("tags"));
                        }
                        wiki.add(categoryInfos);
                        continue;
                    }
                    for (HashMap<String, Object> mainCategory : wiki) {
                        if (!mainCategory.get("name").equals(obj.get("parent"))) continue;
                        categoryInfos.put("height", (Long)obj.get("height"));
                        categoryInfos.put("texts", new JSONArray());
                        categoryInfos.put("images", new JSONArray());
                        if (obj.containsKey("texts")) {
                            categoryInfos.put("texts", (JSONArray)obj.get("texts"));
                        }
                        if (obj.containsKey("images")) {
                            categoryInfos.put("images", (JSONArray)obj.get("images"));
                        }
                        if (obj.containsKey("color")) {
                            categoryInfos.put("color", (String)obj.get("color"));
                        }
                        ((ArrayList)mainCategory.get("children")).add(categoryInfos);
                        continue block2;
                    }
                }
            }
        }
        catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onInit() {
        super.onInit();
        RenderingRegistry.registerEntityRenderingHandler(EntityPictureFrame.class, (Render)new RenderPictureFrame());
        try {
            SKIN_MANAGER.loadSkins();
        }
        catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void handlePacket(ByteArrayDataInput data, IPacket packet, EntityPlayer player) {
        if (FMLCommonHandler.instance().getEffectiveSide().isServer()) {
            super.handlePacket(data, packet, player);
        } else if (packet instanceof IClientPacket) {
            packet.fromBytes(data);
            ((IClientPacket)((Object)packet)).handleClientPacket(player);
        }
    }

    public static void saveConfig() throws IOException {
        Files.write(configFile.toPath(), gson.toJson((Object)clientConfig).getBytes(), new OpenOption[0]);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void onPostInit() {
        File[] e3;
        Map.Entry pair;
        Iterator it;
        int i;
        ArrayList list;
        ResourceLocation location2;
        BufferedImage image;
        File child2;
        super.onPostInit();
        fr.nationsglory.ngbrowser.client.ClientProxy clientProxy = (fr.nationsglory.ngbrowser.client.ClientProxy)NGBrowser.proxy;
        clientProxy.setBrowserVolume(1.0f);
        recipeWatcherMap.put(ShapedRecipes.class, new ShapedRecipesWatcher());
        recipeWatcherMap.put(ShapelessRecipes.class, new ShapelessRecipesWatcher());
        recipeWatcherMap.put(ShapelessOreRecipe.class, new ShapelessOreRecipeWatcher());
        recipeWatcherMap.put(ShapedOreRecipe.class, new ShapedOreRecipeWatcher());
        recipeWatcherMap.put(RecipeCarpentry.class, new RecipeCarpentryWatcher());
        File dir = new File(new File(".", "assets"), "badges");
        File[] directoryListing = dir.listFiles();
        if (directoryListing != null) {
            for (File child2 : directoryListing) {
                if (child2.getName().contains(".json")) continue;
                image = null;
                try {
                    image = ImageIO.read(child2);
                }
                catch (IOException e2) {
                    e2.printStackTrace();
                }
                try {
                    location2 = Minecraft.func_71410_x().func_110434_K().func_110578_a("assets/badges/" + child2.getName() + ".png", new DynamicTexture(image));
                    NationsGUI.BADGES_RESOURCES.put(FilenameUtils.removeExtension((String)child2.getName()), location2);
                }
                catch (Exception location2) {
                    // empty catch block
                }
            }
        }
        try {
            list = (ArrayList)gson.fromJson((Reader)new InputStreamReader(new URL("https://apiv2.nationsglory.fr/json/badges.json").openStream(), StandardCharsets.UTF_8), new TypeToken<ArrayList<HashMap<String, HashMap<String, Object>>>>(){}.getType());
            for (i = 0; i < list.size(); ++i) {
                HashMap badge = (HashMap)list.get(i);
                it = badge.entrySet().iterator();
                while (it.hasNext()) {
                    pair = it.next();
                    String badgeName = (String)pair.getKey();
                    HashMap badgeInfo = (HashMap)pair.getValue();
                    if (badgeInfo.get("tooltips") != null) {
                        NationsGUI.BADGES_TOOLTIPS.put(badgeName, Arrays.asList(I18n.func_135053_a((String)("badge.tooltips." + badgeName)).split("##")));
                    }
                    if (badgeInfo.get("name") != null) {
                        NationsGUI.BADGES_NAMES.put(badgeName, I18n.func_135053_a((String)("badge.title." + badgeName)));
                    }
                    it.remove();
                }
            }
        }
        catch (IOException e3) {
            e3.printStackTrace();
        }
        dir = new File(new File(".", "assets"), "emotes");
        directoryListing = dir.listFiles();
        if (directoryListing != null) {
            e3 = directoryListing;
            i = e3.length;
            for (int badge = 0; badge < i; ++badge) {
                child2 = e3[badge];
                if (child2.getName().contains(".json")) continue;
                image = null;
                try {
                    image = ImageIO.read(child2);
                    continue;
                }
                catch (IOException e4) {
                    e4.printStackTrace();
                    continue;
                }
                finally {
                    location2 = Minecraft.func_71410_x().func_110434_K().func_110578_a("assets/emotes/" + child2.getName() + ".png", new DynamicTexture(image));
                    NationsGUI.EMOTES_RESOURCES.put(FilenameUtils.removeExtension((String)child2.getName()), location2);
                }
            }
        }
        if ((directoryListing = (dir = new File(new File(".", "assets"), "animations")).listFiles()) != null) {
            e3 = directoryListing;
            i = e3.length;
            for (int badge = 0; badge < i; ++badge) {
                child2 = e3[badge];
                if (child2.getName().contains(".json")) continue;
                image = null;
                try {
                    image = ImageIO.read(child2);
                    continue;
                }
                catch (IOException e5) {
                    e5.printStackTrace();
                    continue;
                }
                finally {
                    location2 = Minecraft.func_71410_x().func_110434_K().func_110578_a("assets/animations/" + child2.getName() + ".png", new DynamicTexture(image));
                    NationsGUI.ANIMATIONS_RESOURCES.put(FilenameUtils.removeExtension((String)child2.getName()), location2);
                }
            }
        }
        try {
            list = (ArrayList)gson.fromJson((Reader)new InputStreamReader(new URL("https://apiv2.nationsglory.fr/json/emotes.json").openStream(), StandardCharsets.UTF_8), new TypeToken<ArrayList<HashMap<String, HashMap<String, String>>>>(){}.getType());
            for (i = 0; i < list.size(); ++i) {
                HashMap emote = (HashMap)list.get(i);
                it = emote.entrySet().iterator();
                while (it.hasNext()) {
                    pair = it.next();
                    String emoteName = (String)pair.getKey();
                    HashMap emoteInfo = (HashMap)pair.getValue();
                    if (emoteInfo.get("symbol") != null) {
                        NationsGUI.EMOTES_SYMBOLS.put(emoteName, (String)emoteInfo.get("symbol"));
                    }
                    it.remove();
                }
            }
        }
        catch (IOException e6) {
            e6.printStackTrace();
        }
        NationsGUI.ANIMATIONS.addAll(Arrays.asList("airguitar", "balance", "cheer", "clap", "exorcist", "facepalm", "gangnamstyle", "levitate", "no", "point", "run", "salute", "wave", "yes", "zombie", "headbang"));
        ChatTagManager.registerTags();
        ClientCommandHandler.instance.func_71560_a((ICommand)new SkinDebugCommand());
        ClientCommandHandler.instance.func_71560_a((ICommand)new FovCommand());
    }

    @Override
    public void setupSkinPictureFrame(EntityPictureFrame entityPictureFrame) {
        if (ClientProxy.clientConfig.displayPictureFrame) {
            entityPictureFrame.setupSkin();
        }
    }

    public void openGui(EnumGui gui, EntityPlayer player, int x, int y, int z) {
        Minecraft.func_71410_x().func_71373_a((GuiScreen)ServerGUIHandler.INSTANCE.getClientGuiElement(gui.ordinal(), player, null, x, y, z));
    }

    public static ClientEmotesHandler getEmotes() {
        return emotes;
    }

    public static BufferedImage decodeToImage(String imageString) {
        BufferedImage image = null;
        try {
            BASE64Decoder decoder = new BASE64Decoder();
            byte[] imageByte = decoder.decodeBuffer(imageString);
            ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
            image = ImageIO.read(bis);
            bis.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return image;
    }

    public static void loadCountryFlag(String factionName) {
        if (!flagsTexture.containsKey(factionName)) {
            if (!base64FlagsByFactionName.containsKey(factionName)) {
                base64FlagsByFactionName.put(factionName, "");
                PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new FactionGetImagePacket(factionName)));
            } else if (base64FlagsByFactionName.containsKey(factionName) && !base64FlagsByFactionName.get(factionName).isEmpty()) {
                BufferedImage image = ClientProxy.decodeToImage(base64FlagsByFactionName.get(factionName));
                flagsTexture.put(factionName, new DynamicTexture(image));
            }
        }
    }

    public static void clearCacheCountryFLag(String factionName) {
        flagsTexture.remove(factionName);
        base64FlagsByFactionName.remove(factionName);
    }

    public static void loadResource(String path) {
        if (!cachedResources.containsKey(path)) {
            cachedResources.put(path, new ResourceLocation("nationsgui", path));
        }
        Minecraft.func_71410_x().func_110434_K().func_110577_a(cachedResources.get(path));
    }

    public static DownloadableTexture getRemoteResource(String url) {
        if (!cachedRemoteResources.containsKey(url)) {
            cachedRemoteResources.put(url, ClientProxy.downloadImage(ClientProxy.getLocationRemoteResource(url), url, null, (IImageBuffer)new ImageBufferDownload()));
        }
        return cachedRemoteResources.get(url);
    }

    public static ResourceLocation getLocationRemoteResource(String url) {
        return new ResourceLocation("nationsgui", "remoteimages/" + url);
    }

    private static DownloadableTexture downloadImage(ResourceLocation par0ResourceLocation, String par1Str, ResourceLocation par2ResourceLocation, IImageBuffer par3IImageBuffer) {
        TextureManager texturemanager = Minecraft.func_71410_x().func_110434_K();
        Object object = texturemanager.func_110581_b(par0ResourceLocation);
        if (object == null) {
            object = new DownloadableTexture(par1Str, par2ResourceLocation, par3IImageBuffer);
            texturemanager.func_110579_a(par0ResourceLocation, object);
        }
        return (DownloadableTexture)((Object)object);
    }

    public static ResourceLocation getCachedCape(final String cape) {
        if (!cachedCapes.containsKey(cape)) {
            try {
                File file = ClientProxy.getCacheManager().getCachedFileFromName(cape + ".png");
                if (file == null) {
                    new Thread(new Runnable(){

                        @Override
                        public void run() {
                            try {
                                ServerProxy.getCacheManager().downloadAndStockInCache("https://apiv2.nationsglory.fr/json/capes/capes/" + cape.replaceAll("cape_", "") + ".png", cape + ".png");
                            }
                            catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                } else {
                    ResourceLocation location = Minecraft.func_71410_x().func_110434_K().func_110578_a("nationsgui/cache/" + cape, new DynamicTexture(ImageIO.read(file)));
                    cachedCapes.put(cape, location);
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        return cachedCapes.get(cape);
    }

    public static void playClientMusic(String url, float volume) {
        if (commandPlayer != null && commandPlayer.isPlaying()) {
            commandPlayer.close();
        }
        if (commandPlayer == null || !commandPlayer.isPlaying()) {
            commandPlayer = new SoundStreamer(url);
            commandPlayer.setVolume(Minecraft.func_71410_x().field_71474_y.field_74340_b * 0.35f * volume);
            new Thread(commandPlayer).start();
        }
    }

    public static void stopClientMusic() {
        if (commandPlayer != null && commandPlayer.isPlaying()) {
            commandPlayer.softClose();
        }
    }

    public static String getServerIpAndPort(String serverName) {
        for (Map.Entry<String, ServersData.ServerGroup> entry : serversData.getPermanentServers().entrySet()) {
            if (!entry.getValue().getServerMap().containsKey(serverName)) continue;
            return entry.getValue().getServerMap().get(serverName).getIp();
        }
        if (serversData.getTemporaryServers().containsKey(serverName)) {
            return serversData.getTemporaryServers().get(serverName).getIp();
        }
        return null;
    }

    public static String getServerNameByIpAndPort(String ip) {
        for (Map.Entry<String, ServersData.ServerGroup> entry : serversData.getPermanentServers().entrySet()) {
            for (Map.Entry<String, ServersData.Server> server : entry.getValue().getServerMap().entrySet()) {
                if (!ip.contains(server.getValue().getIp().split(":")[1])) continue;
                return server.getKey();
            }
        }
        for (Map.Entry<String, Object> entry : serversData.getTemporaryServers().entrySet()) {
            if (!ip.contains(((ServersData.Server)entry.getValue()).getIp().split(":")[1])) continue;
            return entry.getKey();
        }
        return null;
    }

    public static boolean isPlayerOnServer() {
        Minecraft mc = Minecraft.func_71410_x();
        return mc.func_71401_C() == null && mc.field_71441_e != null;
    }

    public static void sendClientHotBarMessage(String message, long delay) {
        ClientData.hotbarMessage = message;
        HotbarOverride.lastDisplayHotbarMessageTime = System.currentTimeMillis();
        HotbarOverride.delayHotbarMessage = delay;
    }

    @Override
    public void onPreInit() {
        super.onPreInit();
        FMLCommonHandler.instance().registerCrashCallable(new ICrashCallable(){

            public String getLabel() {
                return "Username";
            }

            public String call() throws Exception {
                return Minecraft.func_71410_x().func_110432_I().func_111285_a();
            }
        });
        emotes = new ClientEmotesHandler();
        String offlineClient = System.getProperty("ng.offline");
        if (!(offlineClient != null && offlineClient.equals("true") || Minecraft.func_71410_x().func_110432_I() != null && Minecraft.func_71410_x().func_110432_I().func_111286_b() != null && !Minecraft.func_71410_x().func_110432_I().func_111286_b().isEmpty())) {
            Minecraft.func_71410_x().func_71404_a(new CrashReport("Unauthorized connection", (Throwable)new Exception("Please contact an administrator")));
        }
        try {
            if (configFile.exists()) {
                clientConfig = (ClientConfig)gson.fromJson((Reader)new InputStreamReader(new FileInputStream(configFile)), ClientConfig.class);
            } else {
                ClientProxy.saveConfig();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        System.setProperty("awt.useSystemAAFontSettings", "on");
        System.setProperty("swing.aatext", "true");
        try {
            serversData = (ServersData)new Gson().fromJson((Reader)new InputStreamReader(new URL("https://apiv2.nationsglory.fr/json/servers.json").openStream(), StandardCharsets.UTF_8), ServersData.class);
            for (Map.Entry<String, Map<String, Map<String, String>>> entry : serversData.getLang().entrySet()) {
                for (Map.Entry<String, Map<String, String>> target : entry.getValue().entrySet()) {
                    for (Map.Entry<String, String> trads : target.getValue().entrySet()) {
                        LanguageRegistry.instance().addStringLocalization("nationsgui." + entry.getKey() + "." + target.getKey(), trads.getKey(), trads.getValue());
                    }
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        zone = System.getProperty("zone");
        if (zone == null) {
            zone = "fr";
        }
        IS_ONLINE = Minecraft.func_71410_x().func_110432_I().func_111286_b() != null && !Minecraft.func_71410_x().func_110432_I().func_111286_b().equals("took");
        TIMER = (Timer)ReflectionHelper.getPrivateValue(Minecraft.class, (Object)Minecraft.func_71410_x(), (String[])new String[]{"timer", "field_71428_T"});
        MinecraftForge.EVENT_BUS.register((Object)ClientEventHandler.getInstance());
        KeyBindingRegistry.registerKeyBinding((KeyBindingRegistry.KeyHandler)new ClientKeyHandler());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySkull.class, (TileEntitySpecialRenderer)SkullBlockRenderer.INSTANCE);
        ClientRegistry.bindTileEntitySpecialRenderer(RadioBlockEntity.class, (TileEntitySpecialRenderer)new RadioBlockRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(SpeakerBlockEntity.class, (TileEntitySpecialRenderer)new SpeakerBlockRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(URLBlockEntity.class, (TileEntitySpecialRenderer)new RenderTransparentTileEntity());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityHatBlock.class, (TileEntitySpecialRenderer)new HatBlockRenderer());
        MinecraftForgeClient.registerItemRenderer((int)NationsGUI.HATBLOCK.field_71990_ca, (IItemRenderer)new ItemHatRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(ImageHologramBlockEntity.class, (TileEntitySpecialRenderer)new ImageHologramRenderer());
        MinecraftForgeClient.registerItemRenderer((int)Item.field_82799_bQ.field_77779_bT, (IItemRenderer)new SkullItemRenderer());
        MinecraftForgeClient.registerItemRenderer((int)NationsGUI.RADIO.field_71990_ca, (IItemRenderer)new RadioItemRenderer());
        MinecraftForgeClient.registerItemRenderer((int)NationsGUI.SPEAKER.field_71990_ca, (IItemRenderer)new SpeakerItemRenderer());
        TickRegistry.registerTickHandler((ITickHandler)ClientTickHandler.INSTANCE, (Side)Side.CLIENT);
        MinecraftForgeClient.registerItemRenderer((int)NationsGUI.MOB_SPAWNER.field_77779_bT, (IItemRenderer)new SpawnerItemRenderer());
        MinecraftForgeClient.registerItemRenderer((int)NationsGUI.ITEM_BADGE.field_77779_bT, (IItemRenderer)new ItemBadgeRenderer());
        try {
            Field field = WavefrontObject.class.getDeclaredField("vertexPattern");
            field.setAccessible(true);
            field.set(null, Pattern.compile("(v( (\\-){0,1}\\d+(\\.\\d+)?){3,4} *\\n)|(v( (\\-){0,1}\\d+(\\.\\d+)?){3,4} *$)"));
            field.setAccessible(false);
            field = WavefrontObject.class.getDeclaredField("vertexNormalPattern");
            field.setAccessible(true);
            field.set(null, Pattern.compile("(vn( (\\-){0,1}\\d+(\\.\\d+)?){3,4} *\\n)|(vn( (\\-){0,1}\\d+(\\.\\d+)?){3,4} *$)"));
            field.setAccessible(false);
            field = WavefrontObject.class.getDeclaredField("textureCoordinatePattern");
            field.setAccessible(true);
            field.set(null, Pattern.compile("(vt( (\\-){0,1}\\d+\\.\\d+){2,3} *\\n)|(vt( (\\-){0,1}\\d+(\\.\\d+)?){2,3} *$)"));
            field.setAccessible(false);
            field = WavefrontObject.class.getDeclaredField("face_V_VT_VN_Pattern");
            field.setAccessible(true);
            field.set(null, Pattern.compile("(f( \\d+/\\d+/\\d+){3,4} *\\n)|(f( \\d+/\\d+/\\d+){3,4} *$)"));
            field.setAccessible(false);
            field = WavefrontObject.class.getDeclaredField("face_V_VT_Pattern");
            field.setAccessible(true);
            field.set(null, Pattern.compile("(f( \\d+/\\d+){3,4} *\\n)|(f( \\d+/\\d+){3,4} *$)"));
            field.setAccessible(false);
            field = WavefrontObject.class.getDeclaredField("face_V_VN_Pattern");
            field.setAccessible(true);
            field.set(null, Pattern.compile("(f( \\d+//\\d+){3,4} *\\n)|(f( \\d+//\\d+){3,4} *$)"));
            field.setAccessible(false);
            field = WavefrontObject.class.getDeclaredField("face_V_Pattern");
            field.setAccessible(true);
            field.set(null, Pattern.compile("(f( \\d+){3,4} *\\n)|(f( \\d+){3,4} *$)"));
            field.setAccessible(false);
            field = WavefrontObject.class.getDeclaredField("groupObjectPattern");
            field.setAccessible(true);
            field.set(null, Pattern.compile("([go]( [\\w\\d\\.]+) *\\n)|([go]( [\\w\\d\\.]+) *$)"));
            field.setAccessible(false);
            MinecraftForge.EVENT_BUS.register((Object)new ClearPlayerModels());
            MinecraftForge.EVENT_BUS.register((Object)new ExtendedPlayerSkinRenderer(SkinType.HAT, 3){

                @Override
                protected ModelRenderer getBodyPart(ModelBiped modelBiped) {
                    return modelBiped.field_78116_c;
                }
            });
            MinecraftForge.EVENT_BUS.register((Object)new ExtendedPlayerSkinRenderer(SkinType.CHESTPLATE, 2){

                @Override
                protected ModelRenderer getBodyPart(ModelBiped modelBiped) {
                    return modelBiped.field_78115_e;
                }
            });
            MinecraftForge.EVENT_BUS.register((Object)new ArmorSkinRenderer());
            MinecraftForge.EVENT_BUS.register((Object)new HandsSkinRenderer());
        }
        catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
        ChatHandler.INSTANCE.registerCallback(new IChatFallback(){

            @Override
            public void call() {
                if (!(Minecraft.func_71410_x().field_71462_r instanceof AuthGUI) || ((AuthGUI)Minecraft.func_71410_x().field_71462_r).getType() != AuthTypes.REGISTER) {
                    Minecraft.func_71410_x().func_71373_a((GuiScreen)new AuthGUI(AuthTypes.REGISTER));
                }
            }
        }, "\u00a7cPour vous inscrire, utilisez /register motdepasse confirmermotdepasse", "\u00a7cPour vous inscrire, utilisez \"/register motdepasse confirmermotdepasse\"");
        ChatHandler.INSTANCE.registerCallback(new IChatFallback(){

            @Override
            public void call() {
                Minecraft.func_71410_x().field_71462_r = null;
                Minecraft.func_71410_x().func_71381_h();
                Minecraft.func_71410_x().field_71416_A.func_82461_f();
                if (openFirstConnectionGuiAfterAuthMe && serverType.equals("ng")) {
                    Minecraft.func_71410_x().func_71373_a((GuiScreen)new FirstConnectionGui(currentServerName));
                    openFirstConnectionGuiAfterAuthMe = false;
                }
            }
        }, "\u00a73Bienvenue \u00a7r\u00a7a\u00a7l${username} \u00a7r\u00a73sur \u00a7r\u00a76\u00a7lNationsGlory");
        ChatHandler.INSTANCE.registerCallback(new IChatFallback(){

            @Override
            public void call() {
                if (!(Minecraft.func_71410_x().field_71462_r instanceof AuthGUI) || ((AuthGUI)Minecraft.func_71410_x().field_71462_r).getType() != AuthTypes.LOGIN) {
                    Minecraft.func_71410_x().func_71373_a((GuiScreen)new AuthGUI(AuthTypes.LOGIN));
                }
            }
        }, "\u00a7cPour vous connecter, utilisez: /login motdepasse");
        ChatHandler.INSTANCE.registerCallback(new IChatFallback(){

            @Override
            public void call() {
                SNACKBAR_LIST.add(new SnackbarGUI(StatCollector.func_74838_a((String)"nationsgui.auth.invalid")));
            }
        }, "\u00a7cMauvais MotdePasse", "\u00a7Utilisez: /login motdepasse", "\u00a7Utilisez: /register motdepasse confirmermotdepasse");
        JSONRegistry<JSONBlock> blockRegistry = JSONRegistries.getRegistry(JSONBlock.class);
        blockRegistry.addProperty(new TextureProperty());
        JSONRegistry<JSONArmorSet> armorRegistry = JSONRegistries.getRegistry(JSONArmorSet.class);
        armorRegistry.addProperty(new IconProperty());
        armorRegistry.addProperty(new net.ilexiconn.nationsgui.forge.server.json.registry.armor.property.TextureProperty());
        clientSocket = new ClientSocket(Minecraft.func_71410_x().func_110432_I().func_111285_a());
        ClientProxy.preLoadImageMinecraftGUI();
        ClientProxy.loadWiki();
        if (Minecraft.func_71410_x().func_110432_I().func_111285_a().equalsIgnoreCase("ibalix") || Minecraft.func_71410_x().func_110432_I().func_111285_a().equalsIgnoreCase("tymothi") || Minecraft.func_71410_x().func_110432_I().func_111285_a().equalsIgnoreCase("ewilaan")) {
            Runnable runnable = new Runnable(){

                @Override
                public void run() {
                    long lastRefreshWiki = System.currentTimeMillis();
                    while (true) {
                        if (System.currentTimeMillis() - lastRefreshWiki <= 3000L) {
                            continue;
                        }
                        lastRefreshWiki = System.currentTimeMillis();
                        ClientProxy.loadWiki();
                    }
                }
            };
            Thread thread = new Thread(runnable);
            thread.start();
        }
        new FakeItemSkin(3493);
    }

    static {
        multiRespawn = false;
        clientConfig = new ClientConfig();
        blockReach = 4.5f;
        configFile = new File("nationsgui-client.json");
        gson = new Gson();
        commandPlayer = null;
        openFirstConnectionGuiAfterAuthMe = false;
        openFirstConnectionGui = false;
        currentServerName = "";
        recipeWatcherMap = new HashMap<Class<? extends IRecipe>, RecipeWatcher>();
        serverType = "ng";
        serverIp = "127.0.0.1";
        serverPort = "25565";
        sessionID = "";
        wiki = new ArrayList();
        lastCollidedWithPortalTime = 0L;
        lastCollidedWithPortalX = 0;
        lastCollidedWithPortalY = 0;
        lastCollidedWithPortalZ = 0;
        currentServerTime = 0L;
        base64FlagsByFactionName = new HashMap();
        flagsTexture = new HashMap();
        playersInAdminMode = new HashMap();
        cachedResources = new HashMap();
        cachedRemoteResources = new HashMap();
        cacheHeadPlayer = new HashMap();
        cachedCapes = new HashMap();
        SKIN_MANAGER = new SkinManager();
    }
}

