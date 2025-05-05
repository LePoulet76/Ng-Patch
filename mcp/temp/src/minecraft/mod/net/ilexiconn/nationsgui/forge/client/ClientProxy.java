package net.ilexiconn.nationsgui.forge.client;

import com.google.common.io.ByteArrayDataInput;
import com.google.gson.Gson;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.KeyBindingRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
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
import java.util.Map.Entry;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import net.ilexiconn.nationsgui.forge.NationsGUI;
import net.ilexiconn.nationsgui.forge.client.ClientData;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.ClientKeyHandler;
import net.ilexiconn.nationsgui.forge.client.ClientProxy$1;
import net.ilexiconn.nationsgui.forge.client.ClientProxy$10;
import net.ilexiconn.nationsgui.forge.client.ClientProxy$11;
import net.ilexiconn.nationsgui.forge.client.ClientProxy$2;
import net.ilexiconn.nationsgui.forge.client.ClientProxy$3;
import net.ilexiconn.nationsgui.forge.client.ClientProxy$4;
import net.ilexiconn.nationsgui.forge.client.ClientProxy$5;
import net.ilexiconn.nationsgui.forge.client.ClientProxy$6;
import net.ilexiconn.nationsgui.forge.client.ClientProxy$7;
import net.ilexiconn.nationsgui.forge.client.ClientProxy$8;
import net.ilexiconn.nationsgui.forge.client.ClientProxy$9;
import net.ilexiconn.nationsgui.forge.client.ClientSocket;
import net.ilexiconn.nationsgui.forge.client.ClientTickHandler;
import net.ilexiconn.nationsgui.forge.client.RecipeWatcher;
import net.ilexiconn.nationsgui.forge.client.chat.ChatHandler;
import net.ilexiconn.nationsgui.forge.client.commands.FovCommand;
import net.ilexiconn.nationsgui.forge.client.commands.SkinDebugCommand;
import net.ilexiconn.nationsgui.forge.client.data.ClientConfig;
import net.ilexiconn.nationsgui.forge.client.data.ServersData;
import net.ilexiconn.nationsgui.forge.client.data.ServersData$Server;
import net.ilexiconn.nationsgui.forge.client.data.ServersData$ServerGroup;
import net.ilexiconn.nationsgui.forge.client.emotes.ClientEmotesHandler;
import net.ilexiconn.nationsgui.forge.client.gui.SnackbarGUI;
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
import net.minecraft.client.renderer.IImageBuffer;
import net.minecraft.client.renderer.ImageBufferDownload;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureObject;
import net.minecraft.client.resources.I18n;
import net.minecraft.crash.CrashReport;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Timer;
import net.minecraft.world.World;
import net.minecraftforge.client.ClientCommandHandler;
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

@SideOnly(Side.CLIENT)
public class ClientProxy extends ServerProxy {

   public static List<SnackbarGUI> SNACKBAR_LIST = new ArrayList();
   public static List<SoundStreamer> STREAMER_LIST = new ArrayList();
   public static boolean IS_ONLINE;
   public static Timer TIMER;
   public static ServersData serversData;
   public static String zone;
   public static boolean multiRespawn = false;
   public static ClientConfig clientConfig = new ClientConfig();
   public static float blockReach = 4.5F;
   private static File configFile = new File("nationsgui-client.json");
   private static Gson gson = new Gson();
   public static SoundStreamer commandPlayer = null;
   public static boolean openFirstConnectionGuiAfterAuthMe = false;
   public static boolean openFirstConnectionGui = false;
   public static String currentServerName = "";
   public static Map<Class<? extends IRecipe>, RecipeWatcher> recipeWatcherMap = new HashMap();
   public static String serverType = "ng";
   public static String serverIp = "127.0.0.1";
   public static String serverPort = "25565";
   public static String sessionID = "";
   public static ArrayList<HashMap<String, Object>> wiki = new ArrayList();
   public static ClientSocket clientSocket;
   public static CategoryJSON[] containers;
   public static Long lastCollidedWithPortalTime = Long.valueOf(0L);
   public static int lastCollidedWithPortalX = 0;
   public static int lastCollidedWithPortalY = 0;
   public static int lastCollidedWithPortalZ = 0;
   public static Long currentServerTime = Long.valueOf(0L);
   private static ExtendedPlayerSkinRenderer hatsRenderer;
   private static ClientEmotesHandler emotes;
   public static HashMap<String, String> base64FlagsByFactionName = new HashMap();
   public static HashMap<String, DynamicTexture> flagsTexture = new HashMap();
   public static HashMap<String, Boolean> playersInAdminMode = new HashMap();
   public static HashMap<String, ResourceLocation> cachedResources = new HashMap();
   public static HashMap<String, DownloadableTexture> cachedRemoteResources = new HashMap();
   public static HashMap<String, ResourceLocation> cacheHeadPlayer = new HashMap();
   public static HashMap<String, ResourceLocation> cachedCapes = new HashMap();
   public static final SkinManager SKIN_MANAGER = new SkinManager();


   public static void preLoadImageMinecraftGUI() {
      getRemoteResource("https://apiv2.nationsglory.fr/proxy_images/screen_loading");
      getRemoteResource("https://apiv2.nationsglory.fr/proxy_images/screen_join_all");
      getRemoteResource("https://apiv2.nationsglory.fr/proxy_images/screen_join_hub");
      getRemoteResource("https://apiv2.nationsglory.fr/proxy_images/screen_waiting");
   }

   public static void loadWiki() {
      wiki = new ArrayList();
      JSONParser parser = new JSONParser();

      try {
         JSONArray e = (JSONArray)parser.parse((Reader)(new FileReader("assets/indexes/wiki.json")));
         if(e != null) {
            Iterator var2 = e.iterator();

            while(var2.hasNext()) {
               Object o = var2.next();
               if(o instanceof JSONObject) {
                  JSONObject obj = (JSONObject)o;
                  HashMap categoryInfos = new HashMap();
                  categoryInfos.put("name", (String)obj.get("name"));
                  categoryInfos.put("displayName", (String)obj.get("displayName"));
                  if(!obj.containsKey("parent")) {
                     categoryInfos.put("children", new ArrayList());
                     if(obj.containsKey("tags")) {
                        categoryInfos.put("tags", (List)obj.get("tags"));
                     }

                     wiki.add(categoryInfos);
                  } else {
                     Iterator var6 = wiki.iterator();

                     while(var6.hasNext()) {
                        HashMap mainCategory = (HashMap)var6.next();
                        if(mainCategory.get("name").equals(obj.get("parent"))) {
                           categoryInfos.put("height", (Long)obj.get("height"));
                           categoryInfos.put("texts", new JSONArray());
                           categoryInfos.put("images", new JSONArray());
                           if(obj.containsKey("texts")) {
                              categoryInfos.put("texts", (JSONArray)obj.get("texts"));
                           }

                           if(obj.containsKey("images")) {
                              categoryInfos.put("images", (JSONArray)obj.get("images"));
                           }

                           if(obj.containsKey("color")) {
                              categoryInfos.put("color", (String)obj.get("color"));
                           }

                           ((ArrayList)mainCategory.get("children")).add(categoryInfos);
                           break;
                        }
                     }
                  }
               }
            }
         }
      } catch (ParseException var8) {
         var8.printStackTrace();
      }

   }

   public void onInit() {
      super.onInit();
      RenderingRegistry.registerEntityRenderingHandler(EntityPictureFrame.class, new RenderPictureFrame());

      try {
         SKIN_MANAGER.loadSkins();
      } catch (ParseException var2) {
         throw new RuntimeException(var2);
      }
   }

   public void handlePacket(ByteArrayDataInput data, IPacket packet, EntityPlayer player) {
      if(FMLCommonHandler.instance().getEffectiveSide().isServer()) {
         super.handlePacket(data, packet, player);
      } else if(packet instanceof IClientPacket) {
         packet.fromBytes(data);
         ((IClientPacket)packet).handleClientPacket(player);
      }

   }

   public static void saveConfig() throws IOException {
      Files.write(configFile.toPath(), gson.toJson(clientConfig).getBytes(), new OpenOption[0]);
   }

   public void onPostInit() {
      super.onPostInit();
      fr.nationsglory.ngbrowser.client.ClientProxy clientProxy = (fr.nationsglory.ngbrowser.client.ClientProxy)NGBrowser.proxy;
      clientProxy.setBrowserVolume(1.0F);
      recipeWatcherMap.put(ShapedRecipes.class, new ShapedRecipesWatcher());
      recipeWatcherMap.put(ShapelessRecipes.class, new ShapelessRecipesWatcher());
      recipeWatcherMap.put(ShapelessOreRecipe.class, new ShapelessOreRecipeWatcher());
      recipeWatcherMap.put(ShapedOreRecipe.class, new ShapedOreRecipeWatcher());
      recipeWatcherMap.put(RecipeCarpentry.class, new RecipeCarpentryWatcher());
      File dir = new File(new File(".", "assets"), "badges");
      File[] directoryListing = dir.listFiles();
      File[] e;
      int i;
      int emote;
      File it;
      BufferedImage pair;
      ResourceLocation emoteName;
      if(directoryListing != null) {
         e = directoryListing;
         i = directoryListing.length;

         for(emote = 0; emote < i; ++emote) {
            it = e[emote];
            if(!it.getName().contains(".json")) {
               pair = null;

               try {
                  pair = ImageIO.read(it);
               } catch (IOException var34) {
                  var34.printStackTrace();
               }

               try {
                  emoteName = Minecraft.func_71410_x().func_110434_K().func_110578_a("assets/badges/" + it.getName() + ".png", new DynamicTexture(pair));
                  NationsGUI.BADGES_RESOURCES.put(FilenameUtils.removeExtension(it.getName()), emoteName);
               } catch (Exception var33) {
                  ;
               }
            }
         }
      }

      HashMap emoteInfo;
      ArrayList var41;
      HashMap var42;
      Iterator var43;
      Entry var44;
      String var45;
      try {
         var41 = (ArrayList)gson.fromJson(new InputStreamReader((new URL("https://apiv2.nationsglory.fr/json/badges.json")).openStream(), StandardCharsets.UTF_8), (new ClientProxy$1(this)).getType());

         for(i = 0; i < var41.size(); ++i) {
            var42 = (HashMap)var41.get(i);

            for(var43 = var42.entrySet().iterator(); var43.hasNext(); var43.remove()) {
               var44 = (Entry)var43.next();
               var45 = (String)var44.getKey();
               emoteInfo = (HashMap)var44.getValue();
               if(emoteInfo.get("tooltips") != null) {
                  NationsGUI.BADGES_TOOLTIPS.put(var45, Arrays.asList(I18n.func_135053_a("badge.tooltips." + var45).split("##")));
               }

               if(emoteInfo.get("name") != null) {
                  NationsGUI.BADGES_NAMES.put(var45, I18n.func_135053_a("badge.title." + var45));
               }
            }
         }
      } catch (IOException var40) {
         var40.printStackTrace();
      }

      dir = new File(new File(".", "assets"), "emotes");
      directoryListing = dir.listFiles();
      if(directoryListing != null) {
         e = directoryListing;
         i = directoryListing.length;

         for(emote = 0; emote < i; ++emote) {
            it = e[emote];
            if(!it.getName().contains(".json")) {
               pair = null;
               boolean var32 = false;

               label415: {
                  try {
                     var32 = true;
                     pair = ImageIO.read(it);
                     var32 = false;
                     break label415;
                  } catch (IOException var38) {
                     var38.printStackTrace();
                     var32 = false;
                  } finally {
                     if(var32) {
                        ResourceLocation location = Minecraft.func_71410_x().func_110434_K().func_110578_a("assets/emotes/" + it.getName() + ".png", new DynamicTexture(pair));
                        NationsGUI.EMOTES_RESOURCES.put(FilenameUtils.removeExtension(it.getName()), location);
                     }
                  }

                  emoteName = Minecraft.func_71410_x().func_110434_K().func_110578_a("assets/emotes/" + it.getName() + ".png", new DynamicTexture(pair));
                  NationsGUI.EMOTES_RESOURCES.put(FilenameUtils.removeExtension(it.getName()), emoteName);
                  continue;
               }

               emoteName = Minecraft.func_71410_x().func_110434_K().func_110578_a("assets/emotes/" + it.getName() + ".png", new DynamicTexture(pair));
               NationsGUI.EMOTES_RESOURCES.put(FilenameUtils.removeExtension(it.getName()), emoteName);
            }
         }
      }

      dir = new File(new File(".", "assets"), "animations");
      directoryListing = dir.listFiles();
      if(directoryListing != null) {
         e = directoryListing;
         i = directoryListing.length;

         for(emote = 0; emote < i; ++emote) {
            it = e[emote];
            if(!it.getName().contains(".json")) {
               pair = null;
               boolean var23 = false;

               label398: {
                  try {
                     var23 = true;
                     pair = ImageIO.read(it);
                     var23 = false;
                     break label398;
                  } catch (IOException var36) {
                     var36.printStackTrace();
                     var23 = false;
                  } finally {
                     if(var23) {
                        ResourceLocation location1 = Minecraft.func_71410_x().func_110434_K().func_110578_a("assets/animations/" + it.getName() + ".png", new DynamicTexture(pair));
                        NationsGUI.ANIMATIONS_RESOURCES.put(FilenameUtils.removeExtension(it.getName()), location1);
                     }
                  }

                  emoteName = Minecraft.func_71410_x().func_110434_K().func_110578_a("assets/animations/" + it.getName() + ".png", new DynamicTexture(pair));
                  NationsGUI.ANIMATIONS_RESOURCES.put(FilenameUtils.removeExtension(it.getName()), emoteName);
                  continue;
               }

               emoteName = Minecraft.func_71410_x().func_110434_K().func_110578_a("assets/animations/" + it.getName() + ".png", new DynamicTexture(pair));
               NationsGUI.ANIMATIONS_RESOURCES.put(FilenameUtils.removeExtension(it.getName()), emoteName);
            }
         }
      }

      try {
         var41 = (ArrayList)gson.fromJson(new InputStreamReader((new URL("https://apiv2.nationsglory.fr/json/emotes.json")).openStream(), StandardCharsets.UTF_8), (new ClientProxy$2(this)).getType());

         for(i = 0; i < var41.size(); ++i) {
            var42 = (HashMap)var41.get(i);

            for(var43 = var42.entrySet().iterator(); var43.hasNext(); var43.remove()) {
               var44 = (Entry)var43.next();
               var45 = (String)var44.getKey();
               emoteInfo = (HashMap)var44.getValue();
               if(emoteInfo.get("symbol") != null) {
                  NationsGUI.EMOTES_SYMBOLS.put(var45, (String)emoteInfo.get("symbol"));
               }
            }
         }
      } catch (IOException var35) {
         var35.printStackTrace();
      }

      NationsGUI.ANIMATIONS.addAll(Arrays.asList(new String[]{"airguitar", "balance", "cheer", "clap", "exorcist", "facepalm", "gangnamstyle", "levitate", "no", "point", "run", "salute", "wave", "yes", "zombie", "headbang"}));
      ChatTagManager.registerTags();
      ClientCommandHandler.instance.func_71560_a(new SkinDebugCommand());
      ClientCommandHandler.instance.func_71560_a(new FovCommand());
   }

   public void setupSkinPictureFrame(EntityPictureFrame entityPictureFrame) {
      if(clientConfig.displayPictureFrame) {
         entityPictureFrame.setupSkin();
      }

   }

   public void openGui(EnumGui gui, EntityPlayer player, int x, int y, int z) {
      Minecraft.func_71410_x().func_71373_a((GuiScreen)ServerGUIHandler.INSTANCE.getClientGuiElement(gui.ordinal(), player, (World)null, x, y, z));
   }

   public static ClientEmotesHandler getEmotes() {
      return emotes;
   }

   public static BufferedImage decodeToImage(String imageString) {
      BufferedImage image = null;

      try {
         BASE64Decoder e = new BASE64Decoder();
         byte[] imageByte = e.decodeBuffer(imageString);
         ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
         image = ImageIO.read(bis);
         bis.close();
      } catch (Exception var5) {
         var5.printStackTrace();
      }

      return image;
   }

   public static void loadCountryFlag(String factionName) {
      if(!flagsTexture.containsKey(factionName)) {
         if(!base64FlagsByFactionName.containsKey(factionName)) {
            base64FlagsByFactionName.put(factionName, "");
            PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new FactionGetImagePacket(factionName)));
         } else if(base64FlagsByFactionName.containsKey(factionName) && !((String)base64FlagsByFactionName.get(factionName)).isEmpty()) {
            BufferedImage image = decodeToImage((String)base64FlagsByFactionName.get(factionName));
            flagsTexture.put(factionName, new DynamicTexture(image));
         }
      }

   }

   public static void clearCacheCountryFLag(String factionName) {
      flagsTexture.remove(factionName);
      base64FlagsByFactionName.remove(factionName);
   }

   public static void loadResource(String path) {
      if(!cachedResources.containsKey(path)) {
         cachedResources.put(path, new ResourceLocation("nationsgui", path));
      }

      Minecraft.func_71410_x().func_110434_K().func_110577_a((ResourceLocation)cachedResources.get(path));
   }

   public static DownloadableTexture getRemoteResource(String url) {
      if(!cachedRemoteResources.containsKey(url)) {
         cachedRemoteResources.put(url, downloadImage(getLocationRemoteResource(url), url, (ResourceLocation)null, new ImageBufferDownload()));
      }

      return (DownloadableTexture)cachedRemoteResources.get(url);
   }

   public static ResourceLocation getLocationRemoteResource(String url) {
      return new ResourceLocation("nationsgui", "remoteimages/" + url);
   }

   private static DownloadableTexture downloadImage(ResourceLocation par0ResourceLocation, String par1Str, ResourceLocation par2ResourceLocation, IImageBuffer par3IImageBuffer) {
      TextureManager texturemanager = Minecraft.func_71410_x().func_110434_K();
      Object object = texturemanager.func_110581_b(par0ResourceLocation);
      if(object == null) {
         object = new DownloadableTexture(par1Str, par2ResourceLocation, par3IImageBuffer);
         texturemanager.func_110579_a(par0ResourceLocation, (TextureObject)object);
      }

      return (DownloadableTexture)object;
   }

   public static ResourceLocation getCachedCape(String cape) {
      if(!cachedCapes.containsKey(cape)) {
         try {
            File e = getCacheManager().getCachedFileFromName(cape + ".png");
            if(e == null) {
               (new Thread(new ClientProxy$3(cape))).start();
            } else {
               ResourceLocation location = Minecraft.func_71410_x().func_110434_K().func_110578_a("nationsgui/cache/" + cape, new DynamicTexture(ImageIO.read(e)));
               cachedCapes.put(cape, location);
            }
         } catch (Exception var3) {
            var3.printStackTrace();
         }
      }

      return (ResourceLocation)cachedCapes.get(cape);
   }

   public static void playClientMusic(String url, float volume) {
      if(commandPlayer != null && commandPlayer.isPlaying()) {
         commandPlayer.close();
      }

      if(commandPlayer == null || !commandPlayer.isPlaying()) {
         commandPlayer = new SoundStreamer(url);
         commandPlayer.setVolume(Minecraft.func_71410_x().field_71474_y.field_74340_b * 0.35F * volume);
         (new Thread(commandPlayer)).start();
      }

   }

   public static void stopClientMusic() {
      if(commandPlayer != null && commandPlayer.isPlaying()) {
         commandPlayer.softClose();
      }

   }

   public static String getServerIpAndPort(String serverName) {
      Iterator var1 = serversData.getPermanentServers().entrySet().iterator();

      Entry entry;
      do {
         if(!var1.hasNext()) {
            if(serversData.getTemporaryServers().containsKey(serverName)) {
               return ((ServersData$Server)serversData.getTemporaryServers().get(serverName)).getIp();
            }

            return null;
         }

         entry = (Entry)var1.next();
      } while(!((ServersData$ServerGroup)entry.getValue()).getServerMap().containsKey(serverName));

      return ((ServersData$Server)((ServersData$ServerGroup)entry.getValue()).getServerMap().get(serverName)).getIp();
   }

   public static String getServerNameByIpAndPort(String ip) {
      Iterator var1 = serversData.getPermanentServers().entrySet().iterator();

      Entry server;
      while(var1.hasNext()) {
         server = (Entry)var1.next();
         Iterator var3 = ((ServersData$ServerGroup)server.getValue()).getServerMap().entrySet().iterator();

         while(var3.hasNext()) {
            Entry server1 = (Entry)var3.next();
            if(ip.contains(((ServersData$Server)server1.getValue()).getIp().split(":")[1])) {
               return (String)server1.getKey();
            }
         }
      }

      var1 = serversData.getTemporaryServers().entrySet().iterator();

      do {
         if(!var1.hasNext()) {
            return null;
         }

         server = (Entry)var1.next();
      } while(!ip.contains(((ServersData$Server)server.getValue()).getIp().split(":")[1]));

      return (String)server.getKey();
   }

   public static boolean isPlayerOnServer() {
      Minecraft mc = Minecraft.func_71410_x();
      return mc.func_71401_C() == null && mc.field_71441_e != null;
   }

   public static void sendClientHotBarMessage(String message, long delay) {
      ClientData.hotbarMessage = message;
      HotbarOverride.lastDisplayHotbarMessageTime = Long.valueOf(System.currentTimeMillis());
      HotbarOverride.delayHotbarMessage = Long.valueOf(delay);
   }

   public void onPreInit() {
      super.onPreInit();
      FMLCommonHandler.instance().registerCrashCallable(new ClientProxy$4(this));
      emotes = new ClientEmotesHandler();
      String offlineClient = System.getProperty("ng.offline");
      if((offlineClient == null || !offlineClient.equals("true")) && (Minecraft.func_71410_x().func_110432_I() == null || Minecraft.func_71410_x().func_110432_I().func_111286_b() == null || Minecraft.func_71410_x().func_110432_I().func_111286_b().isEmpty())) {
         Minecraft.func_71410_x().func_71404_a(new CrashReport("Unauthorized connection", new Exception("Please contact an administrator")));
      }

      try {
         if(configFile.exists()) {
            clientConfig = (ClientConfig)gson.fromJson(new InputStreamReader(new FileInputStream(configFile)), ClientConfig.class);
         } else {
            saveConfig();
         }
      } catch (Exception var9) {
         var9.printStackTrace();
      }

      System.setProperty("awt.useSystemAAFontSettings", "on");
      System.setProperty("swing.aatext", "true");

      try {
         serversData = (ServersData)(new Gson()).fromJson(new InputStreamReader((new URL("https://apiv2.nationsglory.fr/json/servers.json")).openStream(), StandardCharsets.UTF_8), ServersData.class);
         Iterator blockRegistry = serversData.getLang().entrySet().iterator();

         while(blockRegistry.hasNext()) {
            Entry armorRegistry = (Entry)blockRegistry.next();
            Iterator runnable = ((Map)armorRegistry.getValue()).entrySet().iterator();

            while(runnable.hasNext()) {
               Entry thread = (Entry)runnable.next();
               Iterator var6 = ((Map)thread.getValue()).entrySet().iterator();

               while(var6.hasNext()) {
                  Entry trads = (Entry)var6.next();
                  LanguageRegistry.instance().addStringLocalization("nationsgui." + (String)armorRegistry.getKey() + "." + (String)thread.getKey(), (String)trads.getKey(), (String)trads.getValue());
               }
            }
         }
      } catch (IOException var10) {
         var10.printStackTrace();
      }

      zone = System.getProperty("zone");
      if(zone == null) {
         zone = "fr";
      }

      IS_ONLINE = Minecraft.func_71410_x().func_110432_I().func_111286_b() != null && !Minecraft.func_71410_x().func_110432_I().func_111286_b().equals("took");
      TIMER = (Timer)ReflectionHelper.getPrivateValue(Minecraft.class, Minecraft.func_71410_x(), new String[]{"timer", "field_71428_T"});
      MinecraftForge.EVENT_BUS.register(ClientEventHandler.getInstance());
      KeyBindingRegistry.registerKeyBinding(new ClientKeyHandler());
      ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySkull.class, SkullBlockRenderer.INSTANCE);
      ClientRegistry.bindTileEntitySpecialRenderer(RadioBlockEntity.class, new RadioBlockRenderer());
      ClientRegistry.bindTileEntitySpecialRenderer(SpeakerBlockEntity.class, new SpeakerBlockRenderer());
      ClientRegistry.bindTileEntitySpecialRenderer(URLBlockEntity.class, new RenderTransparentTileEntity());
      ClientRegistry.bindTileEntitySpecialRenderer(TileEntityHatBlock.class, new HatBlockRenderer());
      MinecraftForgeClient.registerItemRenderer(NationsGUI.HATBLOCK.field_71990_ca, new ItemHatRenderer());
      ClientRegistry.bindTileEntitySpecialRenderer(ImageHologramBlockEntity.class, new ImageHologramRenderer());
      MinecraftForgeClient.registerItemRenderer(Item.field_82799_bQ.field_77779_bT, new SkullItemRenderer());
      MinecraftForgeClient.registerItemRenderer(NationsGUI.RADIO.field_71990_ca, new RadioItemRenderer());
      MinecraftForgeClient.registerItemRenderer(NationsGUI.SPEAKER.field_71990_ca, new SpeakerItemRenderer());
      TickRegistry.registerTickHandler(ClientTickHandler.INSTANCE, Side.CLIENT);
      MinecraftForgeClient.registerItemRenderer(NationsGUI.MOB_SPAWNER.field_77779_bT, new SpawnerItemRenderer());
      MinecraftForgeClient.registerItemRenderer(NationsGUI.ITEM_BADGE.field_77779_bT, new ItemBadgeRenderer());

      try {
         Field blockRegistry1 = WavefrontObject.class.getDeclaredField("vertexPattern");
         blockRegistry1.setAccessible(true);
         blockRegistry1.set((Object)null, Pattern.compile("(v( (\\-){0,1}\\d+(\\.\\d+)?){3,4} *\\n)|(v( (\\-){0,1}\\d+(\\.\\d+)?){3,4} *$)"));
         blockRegistry1.setAccessible(false);
         blockRegistry1 = WavefrontObject.class.getDeclaredField("vertexNormalPattern");
         blockRegistry1.setAccessible(true);
         blockRegistry1.set((Object)null, Pattern.compile("(vn( (\\-){0,1}\\d+(\\.\\d+)?){3,4} *\\n)|(vn( (\\-){0,1}\\d+(\\.\\d+)?){3,4} *$)"));
         blockRegistry1.setAccessible(false);
         blockRegistry1 = WavefrontObject.class.getDeclaredField("textureCoordinatePattern");
         blockRegistry1.setAccessible(true);
         blockRegistry1.set((Object)null, Pattern.compile("(vt( (\\-){0,1}\\d+\\.\\d+){2,3} *\\n)|(vt( (\\-){0,1}\\d+(\\.\\d+)?){2,3} *$)"));
         blockRegistry1.setAccessible(false);
         blockRegistry1 = WavefrontObject.class.getDeclaredField("face_V_VT_VN_Pattern");
         blockRegistry1.setAccessible(true);
         blockRegistry1.set((Object)null, Pattern.compile("(f( \\d+/\\d+/\\d+){3,4} *\\n)|(f( \\d+/\\d+/\\d+){3,4} *$)"));
         blockRegistry1.setAccessible(false);
         blockRegistry1 = WavefrontObject.class.getDeclaredField("face_V_VT_Pattern");
         blockRegistry1.setAccessible(true);
         blockRegistry1.set((Object)null, Pattern.compile("(f( \\d+/\\d+){3,4} *\\n)|(f( \\d+/\\d+){3,4} *$)"));
         blockRegistry1.setAccessible(false);
         blockRegistry1 = WavefrontObject.class.getDeclaredField("face_V_VN_Pattern");
         blockRegistry1.setAccessible(true);
         blockRegistry1.set((Object)null, Pattern.compile("(f( \\d+//\\d+){3,4} *\\n)|(f( \\d+//\\d+){3,4} *$)"));
         blockRegistry1.setAccessible(false);
         blockRegistry1 = WavefrontObject.class.getDeclaredField("face_V_Pattern");
         blockRegistry1.setAccessible(true);
         blockRegistry1.set((Object)null, Pattern.compile("(f( \\d+){3,4} *\\n)|(f( \\d+){3,4} *$)"));
         blockRegistry1.setAccessible(false);
         blockRegistry1 = WavefrontObject.class.getDeclaredField("groupObjectPattern");
         blockRegistry1.setAccessible(true);
         blockRegistry1.set((Object)null, Pattern.compile("([go]( [\\w\\d\\.]+) *\\n)|([go]( [\\w\\d\\.]+) *$)"));
         blockRegistry1.setAccessible(false);
         MinecraftForge.EVENT_BUS.register(new ClearPlayerModels());
         MinecraftForge.EVENT_BUS.register(new ClientProxy$5(this, SkinType.HAT, 3));
         MinecraftForge.EVENT_BUS.register(new ClientProxy$6(this, SkinType.CHESTPLATE, 2));
         MinecraftForge.EVENT_BUS.register(new ArmorSkinRenderer());
         MinecraftForge.EVENT_BUS.register(new HandsSkinRenderer());
      } catch (Exception var8) {
         var8.printStackTrace();
         System.exit(0);
      }

      ChatHandler.INSTANCE.registerCallback(new ClientProxy$7(this), new String[]{"\u00a7cPour vous inscrire, utilisez /register motdepasse confirmermotdepasse", "\u00a7cPour vous inscrire, utilisez \"/register motdepasse confirmermotdepasse\""});
      ChatHandler.INSTANCE.registerCallback(new ClientProxy$8(this), new String[]{"\u00a73Bienvenue \u00a7r\u00a7a\u00a7l${username} \u00a7r\u00a73sur \u00a7r\u00a76\u00a7lNationsGlory"});
      ChatHandler.INSTANCE.registerCallback(new ClientProxy$9(this), new String[]{"\u00a7cPour vous connecter, utilisez: /login motdepasse"});
      ChatHandler.INSTANCE.registerCallback(new ClientProxy$10(this), new String[]{"\u00a7cMauvais MotdePasse", "\u00a7Utilisez: /login motdepasse", "\u00a7Utilisez: /register motdepasse confirmermotdepasse"});
      JSONRegistry blockRegistry2 = JSONRegistries.getRegistry(JSONBlock.class);
      blockRegistry2.addProperty(new TextureProperty());
      JSONRegistry armorRegistry1 = JSONRegistries.getRegistry(JSONArmorSet.class);
      armorRegistry1.addProperty(new IconProperty());
      armorRegistry1.addProperty(new net.ilexiconn.nationsgui.forge.server.json.registry.armor.property.TextureProperty());
      clientSocket = new ClientSocket(Minecraft.func_71410_x().func_110432_I().func_111285_a());
      preLoadImageMinecraftGUI();
      loadWiki();
      if(Minecraft.func_71410_x().func_110432_I().func_111285_a().equalsIgnoreCase("ibalix") || Minecraft.func_71410_x().func_110432_I().func_111285_a().equalsIgnoreCase("tymothi") || Minecraft.func_71410_x().func_110432_I().func_111285_a().equalsIgnoreCase("ewilaan")) {
         ClientProxy$11 runnable1 = new ClientProxy$11(this);
         Thread thread1 = new Thread(runnable1);
         thread1.start();
      }

      new FakeItemSkin(3493);
   }

}
