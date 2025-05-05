package net.ilexiconn.nationsgui.forge.client;

import acs.tabbychat.GuiNewChatTC;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.google.gson.internal.LinkedTreeMap;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.ModContainer;
import cpw.mods.fml.common.ObfuscationReflectionHelper;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.GameRegistry.UniqueIdentifier;
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
import java.io.IOException;
import java.lang.reflect.Field;
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
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import micdoodle8.mods.galacticraft.core.tile.GCCoreTileEntityUniversalElectrical;
import micdoodle8.mods.galacticraft.edora.common.planet.gen.GCEdoraWorldProvider;
import net.ilexiconn.nationsgui.forge.NationsGUI;
import net.ilexiconn.nationsgui.forge.client.ClientData;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler$1;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler$2;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler$3;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler$4;
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
import net.ilexiconn.nationsgui.forge.client.itemskin.BuddySkin;
import net.ilexiconn.nationsgui.forge.client.itemskin.CapeSkin;
import net.ilexiconn.nationsgui.forge.client.itemskin.SkinType;
import net.ilexiconn.nationsgui.forge.client.render.item.SkullItemRenderer;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
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
import net.minecraft.client.renderer.IImageBuffer;
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
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.MathHelper;
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
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Chat;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Text;
import net.minecraftforge.client.event.RenderPlayerEvent.Pre;
import net.minecraftforge.client.event.RenderPlayerEvent.Specials.Post;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.client.event.sound.SoundLoadEvent;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import noppes.npcs.EntityNPCInterface;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class ClientEventHandler {

   public static final GUIStyle STYLE = GUIStyle.DEFAULT;
   public static final ResourceLocation DURA_ICON = new ResourceLocation("nationsgui", "textures/gui/dura_icon.png");
   private static final List<? extends Class> renderLightClassBlacklist = Arrays.asList(new Class[]{ItemClientSpartanArmor.class, ItemClientReaperArmor.class, ItemClientVigilanteArmor.class, ItemVigilanteSword.class});
   private static final List<Integer> renderLightIdsBlacklist = Arrays.asList(new Integer[]{Integer.valueOf(-25)});
   public static long joinTime;
   public static int currentClicks = 0;
   public static int lastClicks = 0;
   public static boolean modsChecked = false;
   public static Long lastPlasmaCounter = Long.valueOf(0L);
   public static Long lastLaserPlasmaHit = Long.valueOf(0L);
   private static ClientEventHandler instance;
   public static List<String> md5list = new ArrayList();
   public static List<String> modNamelist = new ArrayList();
   public static Map<String, String> playersFaction = new HashMap();
   public static Map<String, String> factionsFlag = new HashMap();
   public static Map<String, DynamicTexture> factionsFlagTexture = new HashMap();
   public static ArrayList<String> MACHINE_WITH_DURABILITY = new ArrayList(Arrays.asList(new String[]{"GCCoreTileEntitySolar", "GCCrusherBlockEntity", "GCEcotronBlockEntity", "GCFishFeederBlockEntity", "GCIncineratorBlockEntity", "GCIncubatorBlockEntity", "GCOilGeneratorBlockEntity", "GCOilPumpBlockEntity", "GCPotionsBlockEntity", "GCPrinterBlockEntity", "GCPumpBlockEntity", "GCRandomBlockEntity", "GCRepairBlockEntity", "GCTraderBlockEntity", "GCCoreTileEntityCoalGenerator", "GCCoreTileEntityElectricFurnace", "GCCoreTileEntityElectricIngotCompressor", "GCCogsAssemblerBlockEntity", "GCPetrolPumpJackBlockEntity", "GCRefineryBlockEntity", "GCFuelGeneratorBlockEntity", "GCElectricCollectorBlockEntity", "GCBioFuelGeneratorEntity", "GCPetrolRubberBlockEntity", "GCFlourMachineBlockEntity"}));
   public static Map<String, String> playersIslandTeamPrefix = new HashMap();
   public static Map<String, Long> playersIslandTeamPrefixLastRefresh = new HashMap();
   public static Long lastPlayerDispayTAB = Long.valueOf(0L);
   public static HashMap<String, List<String>> cerealsByAnimal = new ClientEventHandler$1();
   public static Long NGPrimeTimer = Long.valueOf(0L);
   public static Long NGPrimeTextInterval = Long.valueOf(5000L);
   public PlayerListGUI playerListGUI = new PlayerListGUI(Minecraft.func_71410_x());
   public SnackbarGUI snackbarGUI;
   private List<ElementOverride> elementOverrides = new ArrayList();
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
      this.elementOverrides.add(this.objective = new ObjectiveOverride());
      this.elementOverrides.add(new AssaultOverride());
      this.elementOverrides.add(new WarzoneOverride());
      this.elementOverrides.add(new NotificationOverride());
      this.elementOverrides.add(new LobbyOverride());
      this.elementOverrides.add(new BuildOverride());
      this.elementOverrides.add(this.title = new TitleOverride());
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
      (new ClientEventHandler$2(this)).start();

      try {
         Class e = Class.forName("co.uk.flansmods.client.TickHandlerClient");
         this.tickHandlerClient = e.newInstance();
      } catch (IllegalAccessException var2) {
         var2.printStackTrace();
      } catch (ClassNotFoundException var3) {
         ;
      }

      this.fovMethod = ReflectionHelper.findMethod(EntityRenderer.class, Minecraft.func_71410_x().field_71460_t, new String[]{"getFOVModifier", "func_78481_a", "a"}, new Class[]{Float.TYPE, Boolean.TYPE});
   }

   public static TextureObject setResourceUrl(ResourceLocation loc, String url) {
      TextureManager texturemanager = Minecraft.func_71410_x().field_71446_o;
      ThreadDownloadImageData threadDownloadImageData = (ThreadDownloadImageData)texturemanager.func_110581_b(loc);
      if(threadDownloadImageData == null) {
         threadDownloadImageData = new ThreadDownloadImageData(url, (ResourceLocation)null, (IImageBuffer)null);
         texturemanager.func_110579_a(loc, threadDownloadImageData);
      }

      return threadDownloadImageData;
   }

   public static int generatRandomPositiveNegitiveValue(int max, int min) {
      int ii = -min + (int)(Math.random() * (double)(max - -min + 1));
      return ii;
   }

   public static boolean isPlayerTalk(String player) {
      try {
         Iterator var1 = VoiceChatClient.activeStreams.iterator();

         while(var1.hasNext()) {
            PlayableStream stream = (PlayableStream)var1.next();
            if(stream != null && stream.isEntityPlayer() && stream.getPlayer() != null && stream.getPlayer().field_71092_bJ.equals(player)) {
               return true;
            }
         }
      } catch (ConcurrentModificationException var3) {
         ;
      }

      return false;
   }

   private static String getFileChecksum(MessageDigest digest, File file) throws IOException {
      FileInputStream fis = new FileInputStream(file);
      byte[] byteArray = new byte[1024];
      boolean bytesCount = false;

      int var8;
      while((var8 = fis.read(byteArray)) != -1) {
         digest.update(byteArray, 0, var8);
      }

      fis.close();
      byte[] bytes = digest.digest();
      StringBuilder sb = new StringBuilder();

      for(int i = 0; i < bytes.length; ++i) {
         sb.append(Integer.toString((bytes[i] & 255) + 256, 16).substring(1));
      }

      return sb.toString();
   }

   private static void parseFolder(File folder, boolean parseParent) {
      File[] matchingMinecraftJar;
      int var5;
      File md5Digest;
      if(parseParent) {
         File mods = folder.getAbsoluteFile().getParentFile();
         matchingMinecraftJar = mods.listFiles(new ClientEventHandler$3());
         File[] var4 = matchingMinecraftJar;
         var5 = matchingMinecraftJar.length;

         for(int mod = 0; mod < var5; ++mod) {
            md5Digest = var4[mod];
            MessageDigest e = null;

            try {
               e = MessageDigest.getInstance("MD5");
               String e1 = getFileChecksum(e, md5Digest);
               md5list.add(e1);
               modNamelist.add(md5Digest.getName());
            } catch (NoSuchAlgorithmException var12) {
               var12.printStackTrace();
            } catch (IOException var13) {
               var13.printStackTrace();
            }
         }
      }

      File[] var14 = folder.listFiles();
      matchingMinecraftJar = var14;
      int var15 = var14.length;

      for(var5 = 0; var5 < var15; ++var5) {
         File var16 = matchingMinecraftJar[var5];
         if(var16.isFile()) {
            if(!var16.getPath().equalsIgnoreCase("mods\\rei_minimap\\keyconfig.txt")) {
               md5Digest = null;

               try {
                  MessageDigest var17 = MessageDigest.getInstance("MD5");
                  String var18 = getFileChecksum(var17, var16);
                  md5list.add(var18);
                  modNamelist.add(var16.getName());
                  if(var16.getName().contains("Shaders")) {
                     ClientData.useShaderMod = true;
                  }
               } catch (NoSuchAlgorithmException var10) {
                  var10.printStackTrace();
               } catch (IOException var11) {
                  var11.printStackTrace();
               }
            }
         } else if(var16.getName().equals("1.6.4")) {
            parseFolder(var16, false);
         }
      }

   }

   @ForgeSubscribe
   public void onClick(MouseEvent event) {
      if(event.button == 0 && event.buttonstate) {
         ++currentClicks;
      }

   }

   @ForgeSubscribe
   public void preRender(Pre event) {
      AbstractClientPlayer player = (AbstractClientPlayer)event.entityPlayer;
      List capeSkins = ClientProxy.SKIN_MANAGER.getPlayerActiveSkins(event.entityPlayer.field_71092_bJ, SkinType.CAPES);
      CapeSkin capeSkin = capeSkins.size() > 0?(CapeSkin)capeSkins.get(0):null;
      ResourceLocation location;
      if(capeSkin != null) {
         ClientProxy.getCachedCape(capeSkin.getId());
         location = new ResourceLocation("nationsgui", "cache/" + capeSkin.getId());
         if(location != null) {
            ObfuscationReflectionHelper.setPrivateValue(AbstractClientPlayer.class, player, location, 4);
            ObfuscationReflectionHelper.setPrivateValue(AbstractClientPlayer.class, player, setResourceUrl(location, capeSkin.getTextureURL()), 2);
         }
      } else {
         location = AbstractClientPlayer.func_110299_g(player.field_71092_bJ);
         ObfuscationReflectionHelper.setPrivateValue(AbstractClientPlayer.class, player, location, 4);
         ObfuscationReflectionHelper.setPrivateValue(AbstractClientPlayer.class, player, setResourceUrl(location, AbstractClientPlayer.func_110308_e(player.field_71092_bJ)), 2);
      }

   }

   @ForgeSubscribe
   public void onGUIOpened(GuiOpenEvent event) {
      if(event.gui instanceof GuiMultiplayer && !(event.gui instanceof MultiGUI)) {
         event.gui = new MultiGUI(new MainGUI());
      } else if(ClientData.textureClean) {
         if(event.gui instanceof GuiMainMenu && !(event.gui instanceof MainGUI)) {
            modsChecked = false;
            if(ClientProxy.clientConfig != null) {
               if(ClientProxy.clientConfig.TPToTutorial) {
                  ClientProxy.clientConfig.TPToTutorial = false;

                  try {
                     ClientProxy.saveConfig();
                  } catch (IOException var6) {
                     var6.printStackTrace();
                  }

                  if(!ClientProxy.serverIp.equalsIgnoreCase("127.0.0.1") && !ClientProxy.serverIp.equalsIgnoreCase("localhost")) {
                     ClientSocket.connectPlayerToServer("hub.nationsglory.fr:25579");
                  }
               } else {
                  event.gui = new MainGUI();
               }

               if(ClientProxy.clientConfig.FirstSetupClient) {
                  ClientProxy.clientConfig.FirstSetupClient = false;

                  try {
                     ClientProxy.saveConfig();
                  } catch (IOException var5) {
                     var5.printStackTrace();
                  }

                  Minecraft.func_71410_x().field_71474_y.field_74335_Z = 2;
                  if(System.getProperty("java.lang").equalsIgnoreCase("fr")) {
                     Minecraft.func_71410_x().field_71474_y.field_74351_w.field_74512_d = 44;
                     Minecraft.func_71410_x().field_71474_y.field_74368_y.field_74512_d = 31;
                     Minecraft.func_71410_x().field_71474_y.field_74370_x.field_74512_d = 16;
                     Minecraft.func_71410_x().field_71474_y.field_74366_z.field_74512_d = 32;
                     Minecraft.func_71410_x().field_71474_y.field_74316_C.field_74512_d = 30;
                     Minecraft.func_71410_x().field_71474_y.field_74363_ab = "fr_FR";
                  }
               }
            }
         } else if(event.gui instanceof GuiIngameMenu) {
            event.gui = new SummaryGUI();
         } else {
            Field e;
            if(event.gui instanceof GuiOptions && !(event.gui instanceof OptionsGUI)) {
               try {
                  e = GuiOptions.class.getDeclaredField(NationsGUITransformer.inDevelopment?"parentScreen":"field_74053_c");
                  e.setAccessible(true);
                  event.gui = new OptionsGUI((GuiScreen)e.get(event.gui));
               } catch (Exception var4) {
                  var4.printStackTrace();
               }
            } else if(event.gui instanceof GuiGameOver && !(Minecraft.func_71410_x().field_71462_r instanceof CustomGuiGameOver) && ClientProxy.multiRespawn) {
               event.gui = new CustomGuiGameOver();
            } else if(event.gui instanceof GuiInventory && !Minecraft.func_71410_x().field_71439_g.field_71075_bZ.field_75098_d) {
               event.gui = new InventoryGUI(Minecraft.func_71410_x().field_71439_g);
            } else if(event.gui instanceof ScreenChatOptions && !(event.gui instanceof MultiplayerConfigGUI)) {
               try {
                  e = ScreenChatOptions.class.getDeclaredField(NationsGUITransformer.inDevelopment?"theGuiScreen":"field_73889_b");
                  e.setAccessible(true);
                  event.gui = new MultiplayerConfigGUI((GuiScreen)e.get(event.gui), Minecraft.func_71410_x().field_71474_y);
               } catch (IllegalAccessException var3) {
                  var3.printStackTrace();
               }
            } else if(event.gui instanceof GuiSleepMP) {
               event.gui = new SleepGUI();
            }
         }

         if(this.scaleGUIBase != -1 && !(event.gui instanceof AbstractFirstConnectionGui)) {
            Minecraft.func_71410_x().field_71474_y.field_74335_Z = this.scaleGUIBase;
            this.scaleGUIBase = -1;
         } else if(event.gui != null && event.gui instanceof AbstractFirstConnectionGui) {
            this.scaleGUIBase = Minecraft.func_71410_x().field_71474_y.field_74335_Z;
            Minecraft.func_71410_x().field_71474_y.field_74335_Z = 3;
         }
      } else if(!(event.gui instanceof GuiScreenTemporaryResourcePackSelect)) {
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
      if(event.type == ElementType.PLAYER_LIST) {
         lastPlayerDispayTAB = Long.valueOf(System.currentTimeMillis());
         this.playerListGUI.renderPlayerList(event.resolution);
         event.setCanceled(true);
      }

   }

   @ForgeSubscribe
   public void onJoinWorld(EntityJoinWorldEvent event) {
      PermissionCache.INSTANCE.clearCache();
      if(event.entity == Minecraft.func_71410_x().field_71439_g) {
         playersIslandTeamPrefix.clear();
         playersFaction.clear();
         factionsFlag.clear();
         ClientProxy.SKIN_MANAGER.cachedPlayersSkin.clear();
         PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new GetGroupAndPrimePacket()));
         PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new GetGroupAndPrimeOthersPacket()));
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
         ClientData.currentJumpStartTime = Long.valueOf(-1L);
         ClientData.currentJumpLocation = "";
         ClientData.lastCaptureScreenshot.clear();
         ClientData.versusOverlayData.clear();
         ClientData.dialogs.clear();
         DialogOverride.resetDisplay();
         ClientData.markers.clear();
         ClientData.hotbarMessage = "";
         ClientData.isCombatTagged = false;
         ClientData.customRenderColorRed = 1.0F;
         ClientData.customRenderColorGreen = 1.0F;
         ClientData.customRenderColorBlue = 1.0F;
         ClientData.lastCustomConnectionIP = "";
         ClientData.lastCustomConnectionPort = 0;
         ClientData.lastCustomConnectionServerName = "";
         ClientData.noelMegaGiftTimeSpawn = Long.valueOf(0L);
         (new Timer()).schedule(new ClientEventHandler$4(this), 500L);
         PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new FactionNamePacket()));
         String displayName = "null";

         try {
            InetAddress e = InetAddress.getLocalHost();
            NetworkInterface network = NetworkInterface.getByInetAddress(e);
            byte[] mac = network.getHardwareAddress();
            if(mac != null) {
               StringBuilder sb = new StringBuilder();

               for(int i = 0; i < mac.length; ++i) {
                  sb.append(String.format("%02X%s", new Object[]{Byte.valueOf(mac[i]), i < mac.length - 1?"-":""}));
               }

               displayName = sb.toString();
            }
         } catch (UnknownHostException var8) {
            var8.printStackTrace();
         } catch (SocketException var9) {
            var9.printStackTrace();
         } catch (NullPointerException var10) {
            var10.printStackTrace();
         }

         PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new PlayerMapPreferencePacket(System.getProperty("java.tweaker"), displayName)));
      }

      if(NationsGUI.RELEASE_TYPE == ReleaseType.DEVELOP && event.entity == Minecraft.func_71410_x().field_71439_g) {
         Minecraft.func_71410_x().field_71439_g.func_70006_a((new ChatMessageComponent()).func_111059_a(EnumChatFormatting.RED).func_111071_a(Boolean.valueOf(true)).func_111079_a("You\'re running a development build of NationsGUI"));
      }

   }

   @ForgeSubscribe
   public void onRenderPlayerPost(Chat event) {
      int mouseXChat = event.mouseX - 2;
      int mouseYChat = event.resolution.func_78328_b() - event.mouseY - 29;
      NationsGUIChatHooks.mouseXChat = mouseXChat;
      NationsGUIChatHooks.mouseYChat = mouseYChat;
      GuiNewChatTC.mouseXChat = mouseXChat;
      GuiNewChatTC.mouseYChat = mouseYChat;
   }

   @ForgeSubscribe
   public void onRenderPlayerSpecialsPre(net.minecraftforge.client.event.RenderPlayerEvent.Specials.Pre event) {
      GuiScreen gui = Minecraft.func_71410_x().field_71462_r;
      if(gui != null && !(gui instanceof GuiChat) && !(gui instanceof CosmeticCategoryGUI)) {
         event.renderCape = false;
      }

   }

   @ForgeSubscribe
   public void onRenderPlayerPost(Post event) {
      if(Minecraft.func_71382_s() && !event.entityPlayer.func_98034_c(Minecraft.func_71410_x().field_71439_g)) {
         String username = event.entityPlayer.field_71092_bJ;
         List buddySkins = ClientProxy.SKIN_MANAGER.getPlayerActiveSkins(event.entityPlayer.field_71092_bJ, SkinType.BUDDIES);
         BuddySkin buddySkin = buddySkins.size() > 0?(BuddySkin)buddySkins.get(0):null;
         if(buddySkin != null) {
            float bob = MathHelper.func_76126_a(((float)event.entity.field_70173_aa + event.partialRenderTick) / 15.0F) * 0.1F;
            GL11.glPushMatrix();
            GL11.glDisable(2884);
            GL11.glEnable('\u803a');
            GL11.glRotatef(-GUIUtils.interpolate(event.entityPlayer.field_70760_ar, event.entityPlayer.field_70761_aq, event.partialRenderTick), 0.0F, 1.0F, 0.0F);
            GL11.glTranslatef(0.0F, -0.3F + bob, 0.0F);
            GL11.glScalef(-0.5F, -0.5F, 0.5F);
            GL11.glRotatef(0.0F, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(-GUIUtils.interpolate(event.entityPlayer.field_70758_at, event.entityPlayer.field_70759_as, event.partialRenderTick), 0.0F, 1.0F, 0.0F);
            GL11.glTranslatef(-1.2F, 0.0F, 0.0F);
            GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
            GL11.glEnable(3008);
            String aliasName = buddySkin.getId().split("_")[1];
            ResourceLocation resourceLocation = AbstractClientPlayer.field_110314_b;
            if(aliasName != null && aliasName.length() > 0) {
               resourceLocation = AbstractClientPlayer.func_110305_h(aliasName);
               AbstractClientPlayer.func_110304_a(resourceLocation, aliasName);
            }

            Minecraft.func_71410_x().field_71446_o.func_110577_a(resourceLocation);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            SkullItemRenderer.MODEL_SKULL_LARGE.func_78088_a((Entity)null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
            GL11.glPopMatrix();
         }
      }

   }

   @ForgeSubscribe
   public void onRenderLivingPost(net.minecraftforge.client.event.RenderLivingEvent.Specials.Pre event) {
      if(!event.entity.func_70685_l(Minecraft.func_71410_x().field_71451_h) && (event.entity instanceof EntityAnimal || event.entity instanceof EntityMob) && !(event.entity instanceof EntityHorse)) {
         event.setCanceled(true);
      }

      if(event.entity instanceof EntityPlayer && !event.entity.func_70093_af() && event.entity.func_70685_l(Minecraft.func_71410_x().field_71451_h)) {
         event.setCanceled(true);
      }

   }

   @ForgeSubscribe
   public void onChatRecieved(ClientChatReceivedEvent event) {
      String username = Minecraft.func_71410_x().field_71439_g.field_71092_bJ;
      JsonObject object = (new JsonParser()).parse(event.message).getAsJsonObject();
      String translate = object.has("translate")?object.get("translate").getAsString():"";
      if(ChatHandler.INSTANCE.executeFallback(event.message.replace(username, "${username}"))) {
         event.setCanceled(true);
      } else if(translate.equals("chat.type.text")) {
         JsonArray using = object.get("using").getAsJsonArray();
         String message = using.get(1).getAsString();
         if(message.toLowerCase(Locale.ENGLISH).contains("@" + username.toLowerCase(Locale.ENGLISH))) {
            JsonArray array = new JsonArray();
            array.add(using.get(0));
            array.add(new JsonPrimitive(message.replaceAll("(?i)@" + username, EnumChatFormatting.GOLD.toString() + "@" + username + EnumChatFormatting.RESET.toString())));
            object.add("using", array);
            Minecraft.func_71410_x().field_71416_A.func_77366_a("random.orb", 1.0F, 1.0F);
            event.message = (new Gson()).toJson(object);
         }
      }

   }

   @ForgeSubscribe
   public void onRenderWorldLast(RenderWorldLastEvent event) {
      Minecraft mc = Minecraft.func_71410_x();
      if(Minecraft.func_71382_s()) {
         EntityLivingBase cameraEntity = mc.field_71451_h;
         Vec3 renderingVector = cameraEntity.func_70666_h(event.partialTicks);
         Frustrum frustrum = new Frustrum();
         double viewX = cameraEntity.field_70142_S + (cameraEntity.field_70165_t - cameraEntity.field_70142_S) * (double)event.partialTicks;
         double viewY = cameraEntity.field_70137_T + (cameraEntity.field_70163_u - cameraEntity.field_70137_T) * (double)event.partialTicks;
         double viewZ = cameraEntity.field_70136_U + (cameraEntity.field_70161_v - cameraEntity.field_70136_U) * (double)event.partialTicks;
         frustrum.func_78547_a(viewX, viewY, viewZ);
         WorldClient client = mc.field_71441_e;
         Set entities = (Set)ReflectionHelper.getPrivateValue(WorldClient.class, client, new String[]{"entityList", "field_73032_d", "J"});
         Iterator var14 = entities.iterator();

         while(var14.hasNext()) {
            Entity entity = (Entity)var14.next();
            if(entity != null && entity instanceof EntityLivingBase && entity.func_70102_a((new Vector3(renderingVector.field_72450_a, renderingVector.field_72448_b, renderingVector.field_72449_c)).toVec3()) && (entity.field_70158_ak || frustrum.func_78546_a(entity.field_70121_D)) && entity.func_70089_S() && !entity.func_70093_af()) {
               this.renderBar((EntityLivingBase)entity, event.partialTicks, cameraEntity);
            }
         }

         this.renderMarkers();
      }
   }

   private void renderMarkers() {
      EntityClientPlayerMP player = Minecraft.func_71410_x().field_71439_g;
      Iterator var2 = ClientData.markers.iterator();

      while(var2.hasNext()) {
         Map markerData = (Map)var2.next();
         double markerX = ((Double)markerData.get("x")).doubleValue();
         double markerY = ((Double)markerData.get("y")).doubleValue();
         double markerZ = ((Double)markerData.get("z")).doubleValue();
         double dx = markerX - player.field_70165_t;
         double dy = markerY - player.field_70163_u;
         double dz = markerZ - player.field_70161_v;
         double distance = Math.sqrt(dx * dx + dy * dy + dz * dz);
         if(((Boolean)markerData.get("hideOnReach")).booleanValue() && distance < 5.0D) {
            PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new MarkerRemovePacket((String)markerData.get("name"))));
            ClientData.markers.remove(markerData);
            return;
         }

         if(markerData.containsKey("lifeTime") && System.currentTimeMillis() > ((Double)markerData.get("lifeTime")).longValue()) {
            PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new MarkerRemovePacket((String)markerData.get("name"))));
            ClientData.markers.remove(markerData);
            return;
         }

         String distanceStr = String.format("%.2f", new Object[]{Double.valueOf(distance)}) + "m";
         double scaleDistance = Math.max(1.0D, distance / 20.0D);
         float scale = 0.026666673F * (float)scaleDistance;
         GL11.glPushMatrix();
         GL11.glTranslatef((float)(markerX - RenderManager.field_78725_b), (float)(markerY - RenderManager.field_78726_c + 0.0D + 0.6000000238418579D), (float)(markerZ - RenderManager.field_78723_d));
         GL11.glNormal3f(0.0F, 1.0F, 0.0F);
         GL11.glRotatef(-RenderManager.field_78727_a.field_78735_i, 0.0F, 1.0F, 0.0F);
         GL11.glRotatef(RenderManager.field_78727_a.field_78732_j, 1.0F, 0.0F, 0.0F);
         GL11.glScalef(-scale, -scale, scale);
         GL11.glDepthMask(false);
         GL11.glDisable(2929);
         GL11.glDisable(3553);
         GL11.glEnable(3042);
         GL11.glBlendFunc(770, 771);
         Tessellator tessellator = Tessellator.field_78398_a;
         GL11.glEnable(3553);
         GL11.glPushMatrix();
         GL11.glPushMatrix();
         float size = 1.0F;
         GL11.glTranslated(-21.0D, -31.0D, 0.0D);
         if(markerData.get("icon") != null && !((String)markerData.get("icon")).isEmpty()) {
            GL11.glScalef(size, size, size);
            STYLE.bindTexture((String)markerData.get("icon"));
            ModernGui.drawScaledCustomSizeModalRect(0.0F, 0.0F, 0.0F, 0.0F, 131, 190, 43, 63, 131.0F, 190.0F, true);
            GL11.glPushMatrix();
            size = 1.5F;
            GL11.glScalef(size, size, size);
            ModernGui.drawScaledStringCustomFont(distanceStr, 0.0F, 23.0F, 16777215, 0.5F, "center", true, "georamaSemiBold", 30);
            GL11.glPopMatrix();
         } else if(markerData.get("text") != null && ((String)markerData.get("name")).contains("autel#")) {
            GL11.glScalef(3.0F, 3.0F, 3.0F);
            ModernGui.drawScaledStringCustomFont((String)markerData.get("text"), 7.0F, -10.0F, distance > 10.0D?15132390:7988722, 0.5F, "center", true, "minecraftDungeons", 23);
            GL11.glScalef(0.33333334F, 0.33333334F, 0.33333334F);
            ModernGui.drawScaledStringCustomFont("\u00a7c" + I18n.func_135053_a("marker.autel") + " " + ((String)markerData.get("name")).replace("autel#", ""), 22.0F, -3.0F, 7988722, 0.5F, "center", true, "minecraftDungeons", 20);
         }

         GL11.glPopMatrix();
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         GL11.glPopMatrix();
         GL11.glDisable(3042);
         GL11.glEnable(2929);
         GL11.glDepthMask(true);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         GL11.glPopMatrix();
      }

   }

   private void renderBar(EntityLivingBase entity2, float partialTicks, EntityLivingBase viewPoint) {
      if(entity2.field_70153_n == null) {
         EntityLivingBase entity = entity2;
         if(entity2.field_70154_o != null && entity2.field_70154_o instanceof EntityLivingBase) {
            entity = (EntityLivingBase)entity2.field_70154_o;
         }

         Minecraft mc = Minecraft.func_71410_x();
         if(NGPrimeTimer.longValue() == 0L) {
            NGPrimeTimer = Long.valueOf(System.currentTimeMillis());
         }

         if(entity != null) {
            if(entity2.func_70068_e(viewPoint) <= (double)(entity2 instanceof EntityPlayer?4096:256) && entity2 != mc.field_71439_g && (!entity.func_82150_aj() || entity2 instanceof EntityPlayer && isPlayerTalk(((EntityPlayer)entity2).field_71092_bJ)) && entity.func_70685_l(viewPoint)) {
               double riddenBy = entity2.field_70142_S + (entity2.field_70165_t - entity2.field_70142_S) * (double)partialTicks;
               double y = entity2.field_70137_T + (entity2.field_70163_u - entity2.field_70137_T) * (double)partialTicks;
               double z = entity2.field_70136_U + (entity2.field_70161_v - entity2.field_70136_U) * (double)partialTicks;
               float scale = 0.026666673F;
               float maxHealth = entity.func_110138_aP();
               float health = Math.min(maxHealth, entity.func_110143_aJ());
               if(maxHealth > 0.0F) {
                  GL11.glPushMatrix();
                  GL11.glTranslatef((float)(riddenBy - RenderManager.field_78725_b), (float)(y - RenderManager.field_78726_c + (double)entity2.field_70131_O + 0.6000000238418579D), (float)(z - RenderManager.field_78723_d));
                  GL11.glNormal3f(0.0F, 1.0F, 0.0F);
                  GL11.glRotatef(-RenderManager.field_78727_a.field_78735_i, 0.0F, 1.0F, 0.0F);
                  GL11.glRotatef(RenderManager.field_78727_a.field_78732_j, 1.0F, 0.0F, 0.0F);
                  GL11.glScalef(-scale, -scale, scale);
                  GL11.glDepthMask(false);
                  GL11.glDisable(2929);
                  GL11.glDisable(3553);
                  GL11.glEnable(3042);
                  GL11.glBlendFunc(770, 771);
                  Tessellator tessellator = Tessellator.field_78398_a;
                  String name = "";
                  if(entity2 != null && entity2 instanceof EntityPlayer) {
                     String padding = "";
                     ScorePlayerTeam minBoxWidth = ((EntityPlayer)entity2).func_96123_co().func_96509_i(((EntityPlayer)entity2).func_70005_c_());
                     if(minBoxWidth != null && (minBoxWidth.func_96668_e().contains("ATTAQUANT") || minBoxWidth.func_96668_e().contains("DEFENSEUR") || minBoxWidth.func_96668_e().matches("\u00a7.{1}"))) {
                        padding = minBoxWidth.func_96668_e();
                     }

                     if(ClientProxy.serverType.equals("build") && playersIslandTeamPrefix.containsKey(((EntityPlayer)entity2).field_71092_bJ)) {
                        padding = (String)playersIslandTeamPrefix.get(((EntityPlayer)entity2).field_71092_bJ);
                     }

                     if(ClientProxy.currentServerName.equals("aff") && minBoxWidth != null) {
                        padding = minBoxWidth.func_96668_e() + "[" + minBoxWidth.func_96669_c().substring(0, 1).toUpperCase() + "] ";
                     }

                     if(!ClientData.currentFoot.isEmpty()) {
                        if(ClientData.currentFoot.containsKey("team1Players") && !((String)ClientData.currentFoot.get("team1Players")).isEmpty() && ((String)ClientData.currentFoot.get("team1Players")).toLowerCase().contains(((EntityPlayer)entity2).field_71092_bJ.toLowerCase())) {
                           padding = "\u00a7" + (String)ClientData.currentFoot.get("team1Color") + "[" + (String)ClientData.currentFoot.get("team1") + "] ";
                        }

                        if(ClientData.currentFoot.containsKey("team2Players") && !((String)ClientData.currentFoot.get("team2Players")).isEmpty() && ((String)ClientData.currentFoot.get("team2Players")).toLowerCase().contains(((EntityPlayer)entity2).field_71092_bJ.toLowerCase())) {
                           padding = "\u00a7" + (String)ClientData.currentFoot.get("team2Color") + "[" + (String)ClientData.currentFoot.get("team2") + "] ";
                        }
                     }

                     if(ClientProxy.serverType.equals("ng") && ClientData.eventsInfos.size() > 0) {
                        HashMap sizeX = (HashMap)((HashMap)ClientData.eventsInfos.get(0)).clone();
                        boolean height = false;
                        List sizeY = (List)sizeX.get("teams");
                        if(sizeY != null) {
                           Iterator maxHpStr = sizeY.iterator();

                           while(maxHpStr.hasNext()) {
                              LinkedTreeMap hpStr = (LinkedTreeMap)maxHpStr.next();
                              List text = (List)hpStr.get("players");
                              if(text.contains(((EntityPlayer)entity2).field_71092_bJ)) {
                                 padding = hpStr.get("color") + "[" + (!hpStr.get("displayName").equals("")?hpStr.get("displayName"):hpStr.get("name")) + "] ";
                                 height = true;
                                 break;
                              }
                           }
                        }

                        if(!height && ((List)sizeX.get("players")).contains(((EntityPlayer)entity2).field_71092_bJ)) {
                           padding = "\u00a77[" + (!sizeX.get("displayName").equals("")?sizeX.get("displayName"):sizeX.get("name")) + "] \u00a7r";
                        }
                     }

                     name = padding + ((EntityPlayer)entity2).func_70005_c_();
                  }

                  if(entity2 instanceof EntityPlayer) {
                     EntityPlayer var35 = (EntityPlayer)entity2;
                     if(ClientProxy.serverType.equals("ng")) {
                        if(!playersFaction.containsKey(var35.field_71092_bJ)) {
                           PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new TagPlayerFactionPacket(var35.field_71092_bJ)));
                           playersFaction.put(var35.field_71092_bJ, "");
                        } else if(!((String)playersFaction.get(var35.field_71092_bJ)).equals("") && !((String)playersFaction.get(var35.field_71092_bJ)).equals("no_country") && !((String)playersFaction.get(var35.field_71092_bJ)).equals("\u00a77Sans pays") && !factionsFlag.containsKey(((String)playersFaction.get(var35.field_71092_bJ)).replaceFirst("\u00a7.{1}", ""))) {
                           PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new TagFactionFlagPacket((String)playersFaction.get(var35.field_71092_bJ))));
                        }
                     }

                     if(ClientProxy.serverType.equals("build") && (!playersIslandTeamPrefix.containsKey(var35.field_71092_bJ) || playersIslandTeamPrefixLastRefresh.containsKey(var35.field_71092_bJ) && System.currentTimeMillis() - ((Long)playersIslandTeamPrefixLastRefresh.get(var35.field_71092_bJ)).longValue() > 10000L)) {
                        playersIslandTeamPrefixLastRefresh.put(var35.field_71092_bJ, Long.valueOf(System.currentTimeMillis()));
                        PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new IslandGetTeamPrefixPacket(var35.field_71092_bJ)));
                        if(!playersIslandTeamPrefix.containsKey(var35.field_71092_bJ)) {
                           playersIslandTeamPrefix.put(var35.field_71092_bJ, "");
                        }
                     }
                  }

                  byte var36 = 2;
                  int var33 = 60;
                  int var34 = var33 / 2;
                  byte var37 = 25;
                  int var38 = var37 / 2;
                  String var39 = EnumChatFormatting.BOLD + "" + (double)Math.round((double)maxHealth * 100.0D) / 100.0D;
                  if(entity instanceof EntityPlayer) {
                     var39 = EnumChatFormatting.BOLD + "" + Math.min(20.0D, (double)Math.round((double)maxHealth * 100.0D) / 100.0D);
                  }

                  String var40 = "" + (double)Math.round((double)health * 100.0D) / 100.0D;
                  if(entity instanceof EntityPlayer) {
                     var40 = "" + Math.min(20.0D, (double)Math.round((double)health * 100.0D) / 100.0D);
                  }

                  String var41 = var40 + "/" + var39;
                  GL11.glEnable(3553);
                  GL11.glPushMatrix();
                  EntityPlayer p;
                  String healthStr;
                  float var45;
                  if(entity instanceof EntityPlayer) {
                     p = (EntityPlayer)entity;
                     if(GetGroupAndPrimePacket.NGPRIME_PLAYERS.contains(p.field_71092_bJ)) {
                        long size = System.currentTimeMillis() - NGPrimeTimer.longValue();
                        String index = "\u00a7dNGPrime";
                        if(size > NGPrimeTextInterval.longValue() && size < NGPrimeTextInterval.longValue() + 125L) {
                           index = "\u00a75\u00a7lN\u00a7dGPrime";
                        } else if(size >= NGPrimeTextInterval.longValue() + 125L && size < NGPrimeTextInterval.longValue() + 250L) {
                           index = "\u00a7dN\u00a75\u00a7lG\u00a7dPrime";
                        } else if(size >= NGPrimeTextInterval.longValue() + 250L && size < NGPrimeTextInterval.longValue() + 375L) {
                           index = "\u00a7dNG\u00a75\u00a7lP\u00a7drime";
                        } else if(size >= NGPrimeTextInterval.longValue() + 375L && size < NGPrimeTextInterval.longValue() + 500L) {
                           index = "\u00a7dNGP\u00a75\u00a7lr\u00a7dime";
                        } else if(size >= NGPrimeTextInterval.longValue() + 500L && size < NGPrimeTextInterval.longValue() + 625L) {
                           index = "\u00a7dNGPr\u00a75\u00a7li\u00a7dme";
                        } else if(size >= NGPrimeTextInterval.longValue() + 625L && size < NGPrimeTextInterval.longValue() + 750L) {
                           index = "\u00a7dNGPri\u00a75\u00a7lm\u00a7de";
                        } else if(size >= NGPrimeTextInterval.longValue() + 750L && size < NGPrimeTextInterval.longValue() + 875L) {
                           index = "\u00a7dNGPrim\u00a75\u00a7le";
                        } else if(size >= NGPrimeTextInterval.longValue() + 875L) {
                           NGPrimeTimer = Long.valueOf(System.currentTimeMillis());
                        }

                        this.drawRect(30, -29, -30, -21, !index.equals("\u00a7dNGPrime")?1969902556:1617581020);
                        if(NationsGUI.BADGES_RESOURCES.containsKey("badges_ngprime")) {
                           Minecraft.func_71410_x().func_110434_K().func_110577_a((ResourceLocation)NationsGUI.BADGES_RESOURCES.get("badges_ngprime"));
                           ModernGui.drawModalRectWithCustomSizedTexture(-((float)Minecraft.func_71410_x().field_71466_p.func_78256_a("NGPrime") * 0.6F / 2.0F) - 0.0F - 3.0F - 2.0F, -28.0F, 0, 0, 6, 6, 6.0F, 6.0F, false);
                           ModernGui.drawScaledString(index, 4, -27, 6968284, 0.6F, true, false);
                        }
                     }

                     String var43;
                     if(playersFaction.containsKey(p.field_71092_bJ)) {
                        var43 = (String)playersFaction.get(p.field_71092_bJ);
                        if(var43.equals("no_country")) {
                           var43 = I18n.func_135053_a("overlay.no_country");
                        }

                        if((float)mc.field_71466_p.func_78256_a(var43.replaceFirst("\u00a7.{1}", "")) * 0.5F + 12.0F + 2.0F > (float)var33) {
                           var33 = (int)((float)mc.field_71466_p.func_78256_a(var43.replaceFirst("\u00a7.{1}", "")) * 0.5F + 12.0F + 2.0F + (float)(var36 * 2));
                           var34 = var33 / 2;
                        }
                     }

                     if((float)mc.field_71466_p.func_78256_a(name) * 0.75F + 6.0F + 2.0F > (float)var33) {
                        var33 = (int)((float)mc.field_71466_p.func_78256_a(name) * 0.75F + 6.0F + 2.0F + (float)(var36 * 2));
                        var34 = var33 / 2;
                     }

                     this.drawRect(var34, -var38 - 8, -var34, var38 - 5, this.getBadgeBGColor((String)GetGroupAndPrimePacket.GRP_BADGES_PLAYERS.get(p.field_71092_bJ)));
                     if(ClientProxy.serverType.equals("ng")) {
                        var43 = ClientData.topPlayersSkills.containsKey(p.field_71092_bJ)?(String)ClientData.topPlayersSkills.get(p.field_71092_bJ):"";
                        if(!var43.isEmpty()) {
                           STYLE.bindTexture("overlay_" + var43);
                           ModernGui.drawScaledCustomSizeModalRect((float)(-var34 - 2), GetGroupAndPrimePacket.NGPRIME_PLAYERS.contains(p.field_71092_bJ)?-38.0F:-28.0F, 0.0F, 0.0F, 866, 141, var34 * 2 + 4, 10, 866.0F, 141.0F, false);
                        }
                     }

                     if(NationsGUI.BADGES_RESOURCES.containsKey(GetGroupAndPrimePacket.GRP_BADGES_PLAYERS.get(p.field_71092_bJ))) {
                        Minecraft.func_71410_x().func_110434_K().func_110577_a((ResourceLocation)NationsGUI.BADGES_RESOURCES.get(GetGroupAndPrimePacket.GRP_BADGES_PLAYERS.get(p.field_71092_bJ)));
                        ModernGui.drawModalRectWithCustomSizedTexture((float)(-var34 + var36), -18.0F, 0, 0, 6, 6, 6.0F, 6.0F, false);
                        ModernGui.drawScaledString(this.getBadgeTextColor((String)GetGroupAndPrimePacket.GRP_BADGES_PLAYERS.get(p.field_71092_bJ)) + name, -var34 + var36 + 6 + 2, -18, 16777215, 0.75F, false, false);
                     } else {
                        ModernGui.drawScaledString(this.getBadgeTextColor((String)GetGroupAndPrimePacket.GRP_BADGES_PLAYERS.get(p.field_71092_bJ)) + name, -var34 + var36, -18, 16777215, 0.75F, false, false);
                     }

                     if(ClientProxy.serverType.equals("ng")) {
                        if(ClientProxy.playersInAdminMode.containsKey(p.field_71092_bJ) && ((Boolean)ClientProxy.playersInAdminMode.get(p.field_71092_bJ)).booleanValue() && Minecraft.func_71382_s()) {
                           Minecraft.func_71410_x().func_110434_K().func_110577_a(AssistancePlayerGUI.GUI_TEXTURE);
                           GUIUtils.drawScaledCustomSizeModalRect(-var34 + var36, -9, 1.0F, 320.0F, 37, 38, 6, 6, 512.0F, 512.0F);
                           ModernGui.drawScaledString(I18n.func_135053_a("overlay.service"), -var34 + var36 + 6 + 2, -8, 16777215, 0.5F, false, false);
                        } else if(playersFaction.containsKey(p.field_71092_bJ) && !((String)playersFaction.get(p.field_71092_bJ)).equals("")) {
                           var43 = (String)playersFaction.get(p.field_71092_bJ);
                           healthStr = var43.replaceFirst("\u00a7.{1}", "");
                           if(var43.equals("no_country")) {
                              var43 = I18n.func_135053_a("overlay.no_country");
                           }

                           if(var43 != null && factionsFlag.containsKey(healthStr) && factionsFlag.get(healthStr) != null && (!((String)factionsFlag.get(healthStr)).equals("") || !((String)factionsFlag.get(healthStr)).contains(" "))) {
                              ModernGui.drawScaledString(var43, -var34 + var36 + 12 + 2, -9, 16777215, 0.5F, false, false);
                              if(!factionsFlagTexture.containsKey(healthStr)) {
                                 BufferedImage var47 = FactionGui_OLD.decodeToImage((String)factionsFlag.get(healthStr));
                                 if(var47 != null) {
                                    DynamicTexture t = new DynamicTexture(var47);
                                    factionsFlagTexture.put(healthStr, t);
                                 }
                              }

                              if(factionsFlagTexture.containsKey(healthStr)) {
                                 GL11.glBindTexture(3553, ((DynamicTexture)factionsFlagTexture.get(healthStr)).func_110552_b());
                                 ModernGui.drawScaledCustomSizeModalRect((float)(-var34 + var36), -10.0F, 0.0F, 0.0F, 156, 78, 12, 6, 156.0F, 78.0F, false);
                              }
                           } else {
                              ModernGui.drawScaledString(var43, -var34 + var36, -9, 16777215, 0.5F, false, false);
                           }
                        }
                     }

                     if(ClientProxy.currentServerName.equals("aff") && ClientData.eventsInfos.size() > 0 && ((HashMap)ClientData.eventsInfos.get(0)).containsKey("playersKills")) {
                        Matcher var44 = Pattern.compile(p.field_71092_bJ + "#(\\d+)").matcher((String)((HashMap)ClientData.eventsInfos.get(0)).get("playersKills"));
                        healthStr = var44.find()?var44.group(1):"0";
                        ModernGui.drawScaledString("\u00a7eScore: " + healthStr, -var34 + var36, -9, 16777215, 0.5F, false, false);
                     }

                     this.drawRect(-var34 + var36, 4, var34 - var36, -2, 2139062143);
                     this.drawRect(-var34 + var36, 4, (int)((float)var33 * (health / maxHealth) - (float)var34) - var36, -2, this.getHealthColor(entity, health, maxHealth));
                     ModernGui.drawScaledString(var41, 0, 0, 16777215, 0.4F, true, false);
                  } else {
                     GL11.glPushMatrix();
                     if(entity instanceof IAnimalFarm) {
                        IAnimalFarm var42 = (IAnimalFarm)entity;
                        var45 = var42.getMaturity();
                        this.drawRect(var34, -var38 - 8, -var34, var38 - 8, 1073741824);
                        ModernGui.drawScaledString("Elevage:", -var34 + var36, -18, 16777215, 0.4F, false, false);
                        healthStr = "\u00a74" + (int)health;
                        ModernGui.drawScaledString(healthStr, var34 - var36 - 6 - (int)((double)Minecraft.func_71410_x().field_71466_p.func_78256_a(healthStr) * 0.5D), -18, 16777215, 0.5F, false, false);
                        GL11.glPushMatrix();
                        GL11.glScalef(0.7F, 0.7F, 0.7F);
                        STYLE.bindTexture("hud");
                        ModernGui.drawModalRectWithCustomSizedTexture((float)(var34 - var36 - 5) / 0.7F, -25.714287F, 1, (double)(entity.func_110143_aJ() / entity.func_110138_aP()) >= 0.75D?58:((double)(entity.func_110143_aJ() / entity.func_110138_aP()) >= 0.3D?65:72), 7, 7, 256.0F, 256.0F, false);
                        GL11.glPopMatrix();
                        this.drawRect(-var34 + var36, -7, var34 - var36, -13, 2139062143);
                        this.drawRect(-var34 + var36, -7, (int)((double)var33 * ((double)var45 / 100.0D) - (double)var34) - var36, -13, 2139501803);
                        ModernGui.drawScaledString(var45 + "%", 0, -12, 16777215, 0.4F, true, false);
                        GL11.glPushMatrix();
                        GL11.glScalef(0.4F, 0.4F, 0.4F);
                        GL11.glEnable(2896);
                        int var48 = 0;

                        for(Iterator var46 = var42.getAcceptedCereals().iterator(); var46.hasNext(); ++var48) {
                           String cerealName = (String)var46.next();
                           ItemStack cereal = new ItemStack(NGContent.getCerealIdFromName(cerealName).intValue(), 1, 0);
                           this.itemRenderer.func_82406_b(Minecraft.func_71410_x().field_71466_p, mc.func_110434_K(), cereal, (int)((float)(-var34 + var48 * 10 + var36) / 0.4F), -12);
                        }

                        GL11.glDisable(2896);
                        GL11.glPopMatrix();
                     } else if(!ClientProxy.serverType.equals("tvg") && (!(entity instanceof GenericGeckoEntity) || ((GenericGeckoEntity)entity).showHealthBar()) && (!(entity instanceof EntityNPCInterface) || (double)((EntityNPCInterface)entity).func_110138_aP() != 1.0D) && !(entity instanceof GenericGeckoBikeRidingEntity) && !(entity instanceof GenericGeckoBikeFlyingEntity) && !(entity instanceof GenericGeckoBikeSwimmingEntity) && !(entity instanceof EntityMobKey)) {
                        this.drawRect(-var34 + var36, 4, var34 - var36, -2, 2139062143);
                        this.drawRect(-var34 + var36, 4, (int)((float)var33 * (health / maxHealth) - (float)var34) - var36, -2, this.getHealthColor(entity, health, maxHealth));
                        ModernGui.drawScaledString(var41, 0, 0, 16777215, 0.4F, true, false);
                     }

                     GL11.glPopMatrix();
                     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                  }

                  if(entity2 instanceof EntityPlayer) {
                     p = (EntityPlayer)entity2;
                     var45 = 0.25F;
                     GL11.glPushMatrix();
                     GL11.glTranslated((double)((float)(-var34 - var36 * 2) - 32.0F * var45), (double)(-(32.0F * var45)) * 1.5D, 0.0D);
                     GL11.glScalef(var45, var45, var45);
                     if(VoiceChat.getProxyInstance().getSettings().isPlayerMuted(p.field_71092_bJ)) {
                        STYLE.bindTexture("voice_off");
                        ModernGui.drawModalRectWithCustomSizedTexture(0.0F, 0.0F, 0, 0, 32, 32, 32.0F, 32.0F, false);
                     } else if(isPlayerTalk(p.field_71092_bJ)) {
                        STYLE.bindTexture("voice_vol");
                        switch((int)(Minecraft.func_71386_F() % 1000L / 250L)) {
                        case 0:
                           ModernGui.drawModalRectWithCustomSizedTextureWithTransparency(0.0F, 0.0F, 0, 0, 19, 32, 128.0F, 32.0F, false);
                           break;
                        case 1:
                           ModernGui.drawModalRectWithCustomSizedTextureWithTransparency(0.0F, 0.0F, 22, 0, 27, 32, 128.0F, 32.0F, false);
                           break;
                        case 2:
                           ModernGui.drawModalRectWithCustomSizedTextureWithTransparency(0.0F, 0.0F, 53, 0, 31, 32, 128.0F, 32.0F, false);
                           break;
                        case 3:
                           ModernGui.drawModalRectWithCustomSizedTextureWithTransparency(0.0F, 0.0F, 89, 0, 37, 32, 128.0F, 32.0F, false);
                        }
                     }

                     GL11.glPopMatrix();
                  }

                  GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                  GL11.glPopMatrix();
                  GL11.glDisable(3042);
                  GL11.glEnable(2929);
                  GL11.glDepthMask(true);
                  GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                  GL11.glPopMatrix();
               }
            }

            Entity var32 = entity.field_70153_n;
            if(!(var32 instanceof EntityLivingBase)) {
               return;
            }

            entity = (EntityLivingBase)var32;
         }

      }
   }

   public void drawRect(int x, int y, int x2, int y2, int color) {
      float f = (float)(color >> 24 & 255) / 255.0F;
      float f1 = (float)(color >> 16 & 255) / 255.0F;
      float f2 = (float)(color >> 8 & 255) / 255.0F;
      float f3 = (float)(color & 255) / 255.0F;
      Tessellator tessellator = Tessellator.field_78398_a;
      GL11.glEnable(3042);
      GL11.glDisable(3553);
      GL11.glBlendFunc(770, 771);
      tessellator.func_78382_b();
      tessellator.func_78369_a(f1, f2, f3, f);
      tessellator.func_78377_a((double)x, (double)y, 0.0D);
      tessellator.func_78377_a((double)x2, (double)y, 0.0D);
      tessellator.func_78377_a((double)x2, (double)y2, 0.0D);
      tessellator.func_78377_a((double)x, (double)y2, 0.0D);
      tessellator.func_78381_a();
      GL11.glEnable(3553);
      GL11.glDisable(3042);
   }

   @ForgeSubscribe
   public void onOverlayF3Render(Text event) {}

   @ForgeSubscribe
   public void onOverlayRender(net.minecraftforge.client.event.RenderGameOverlayEvent.Pre event) {
      Iterator fakeEvent = this.elementOverrides.iterator();

      while(fakeEvent.hasNext()) {
         ElementOverride e = (ElementOverride)fakeEvent.next();
         if(e.getType() == event.type) {
            event.setCanceled(true);
            if(!(Minecraft.func_71410_x().field_71462_r instanceof AbstractFirstConnectionGui)) {
               e.renderOverride(Minecraft.func_71410_x(), event.resolution, event.partialTicks);
            }
         } else if(e.getSubTypes() != null) {
            List subTypes = Arrays.asList(e.getSubTypes());
            if(subTypes.contains(event.type)) {
               event.setCanceled(true);
            }
         }
      }

      if(event.type == ElementType.HEALTHMOUNT || event.type == ElementType.JUMPBAR) {
         GL11.glPushMatrix();
         GL11.glTranslatef(0.0F, -15.0F, 0.0F);
      }

      if(event.type == ElementType.HOTBAR && this.tickHandlerClient != null) {
         net.minecraftforge.client.event.RenderGameOverlayEvent.Post fakeEvent1 = new net.minecraftforge.client.event.RenderGameOverlayEvent.Post(event, ElementType.HOTBAR);

         try {
            this.tickHandlerClient.getClass().getDeclaredMethod("eventHandler", new Class[]{RenderGameOverlayEvent.class}).invoke(this.tickHandlerClient, new Object[]{fakeEvent1});
         } catch (NoSuchMethodException var5) {
            var5.printStackTrace();
         }
      }

      if(Minecraft.func_71410_x().field_71462_r == null && this.snackbarGUI != null && event.type == ElementType.HOTBAR) {
         this.snackbarGUI.drawSnackbar();
      }

   }

   @ForgeSubscribe
   public void onOverlayRenderPost(net.minecraftforge.client.event.RenderGameOverlayEvent.Post event) {
      if(event.type == ElementType.HEALTHMOUNT || event.type == ElementType.JUMPBAR) {
         GL11.glPopMatrix();
      }

      if(ClientProxy.clientConfig.blockInfoEnabled) {
         MovingObjectPosition movingObjectPosition = Minecraft.func_71410_x().field_71476_x;
         if(movingObjectPosition != null && movingObjectPosition.field_72313_a == EnumMovingObjectType.TILE) {
            FontRenderer fontRenderer = Minecraft.func_71410_x().field_71466_p;
            int id = Minecraft.func_71410_x().field_71441_e.func_72798_a(movingObjectPosition.field_72311_b, movingObjectPosition.field_72312_c, movingObjectPosition.field_72309_d);
            int meta = Minecraft.func_71410_x().field_71441_e.func_72805_g(movingObjectPosition.field_72311_b, movingObjectPosition.field_72312_c, movingObjectPosition.field_72309_d);
            ItemStack itemStack = new ItemStack(id, 1, meta);
            UniqueIdentifier uniqueIdentifier = GameRegistry.findUniqueIdentifierFor(Block.field_71973_m[id]);
            String modName = "Minecraft";
            if(uniqueIdentifier != null) {
               Iterator itemRenderer = Loader.instance().getModList().iterator();

               while(itemRenderer.hasNext()) {
                  ModContainer blockName = (ModContainer)itemRenderer.next();
                  if(blockName.getModId().equals(uniqueIdentifier.modId)) {
                     modName = blockName.getName();
                  }
               }
            }

            RenderItem var26 = new RenderItem();
            String var27 = Block.field_71973_m[id].func_71931_t() + " (#" + id + ":" + meta + ")";
            int w = 20 + fontRenderer.func_78256_a(var27);
            int wMin = fontRenderer.func_78256_a(modName) + 20;
            if(w < wMin) {
               w = wMin;
            }

            byte h = 17;
            int durability = -1;
            TileEntity tileEntity = Minecraft.func_71410_x().field_71441_e.func_72796_p(movingObjectPosition.field_72311_b, movingObjectPosition.field_72312_c, movingObjectPosition.field_72309_d);
            if(tileEntity instanceof GCCoreTileEntityUniversalElectrical) {
               durability = ((GCCoreTileEntityUniversalElectrical)tileEntity).durability;
               h = 30;
            }

            ScaledResolution resolution = new ScaledResolution(Minecraft.func_71410_x().field_71474_y, Minecraft.func_71410_x().field_71443_c, Minecraft.func_71410_x().field_71440_d);
            int x = resolution.func_78326_a() / 2 - w / 2;
            byte y = 5;
            GL11.glPushMatrix();
            int l1 = -267386864;
            GUIUtils.drawGradientRect(x - 3, y - 4, x + w + 3, y - 3, l1, l1);
            GUIUtils.drawGradientRect(x - 3, y + h + 3, x + w + 3, y + h + 4, l1, l1);
            GUIUtils.drawGradientRect(x - 3, y - 3, x + w + 3, y + h + 3, l1, l1);
            GUIUtils.drawGradientRect(x - 4, y - 3, x - 3, y + h + 3, l1, l1);
            GUIUtils.drawGradientRect(x + w + 3, y - 3, x + w + 4, y + h + 3, l1, l1);
            int i2 = 1347420415;
            int j2 = (i2 & 16711422) >> 1 | i2 & -16777216;
            GUIUtils.drawGradientRect(x - 3, y - 3 + 1, x - 3 + 1, y + h + 3 - 1, i2, j2);
            GUIUtils.drawGradientRect(x + w + 2, y - 3 + 1, x + w + 3, y + h + 3 - 1, i2, j2);
            GUIUtils.drawGradientRect(x - 3, y - 3, x + w + 3, y - 3 + 1, i2, i2);
            GUIUtils.drawGradientRect(x - 3, y + h + 2, x + w + 3, y + h + 3, j2, j2);
            GL11.glEnable(3042);
            GL11.glEnable('\u803a');
            RenderHelper.func_74520_c();

            try {
               var26.func_77015_a(fontRenderer, Minecraft.func_71410_x().func_110434_K(), itemStack, x, y);
            } catch (Exception var25) {
               ;
            }

            RenderHelper.func_74518_a();
            GL11.glDisable('\u803a');
            GL11.glDisable(3042);
            fontRenderer.func_78276_b(var27, x + 20, y + 4, 16777215);
            if(durability != -1) {
               Minecraft.func_71410_x().func_110434_K().func_110577_a(DURA_ICON);
               ModernGui.drawModalRectWithCustomSizedTexture((float)x + (float)w / 2.0F - (float)fontRenderer.func_78256_a("||||||||||||||||||||||||||||||||||||||||") / 2.0F - 12.0F, (float)(y + 17), 0, 0, 16, 16, 16.0F, 16.0F, false);
               String color = "";
               if(durability >= 70) {
                  color = "\u00a7a";
               } else if(durability >= 35) {
                  color = "\u00a76";
               } else {
                  color = "\u00a74";
               }

               String bar = "";

               int i;
               for(i = 1; i <= Math.round((float)durability / 2.5F); ++i) {
                  bar = bar + color + "|";
               }

               for(i = 1; i <= 40 - Math.round((float)durability / 2.5F); ++i) {
                  bar = bar + "\u00a77|";
               }

               fontRenderer.func_78276_b(bar, x + w / 2 - fontRenderer.func_78256_a("||||||||||||||||||||||||||||||||||||||||") / 2 + 8, y + 20, 13703963);
            }

            GL11.glColor3f(1.0F, 1.0F, 1.0F);
            GL11.glPopMatrix();
         }
      }

   }

   @ForgeSubscribe
   public void onTextureStitch(net.minecraftforge.client.event.TextureStitchEvent.Pre event) {
      Iterator var2;
      if(event.map.func_130086_a() == 0) {
         var2 = JSONRegistries.getRegistry(JSONBlock.class).getRegistry().iterator();

         while(var2.hasNext()) {
            JSONBlock armorSet = (JSONBlock)var2.next();
            if(armorSet.textureFrontString != null) {
               event.map.setTextureEntry(armorSet.textureFrontString, (TextureAtlasSprite)((TextureAtlasSprite)(armorSet.textureFront = new JSONTexture(armorSet.textureFrontString))));
            }

            if(armorSet.textureBackString != null) {
               event.map.setTextureEntry(armorSet.textureBackString, (TextureAtlasSprite)((TextureAtlasSprite)(armorSet.textureBack = new JSONTexture(armorSet.textureBackString))));
            }

            if(armorSet.textureTopString != null) {
               event.map.setTextureEntry(armorSet.textureTopString, (TextureAtlasSprite)((TextureAtlasSprite)(armorSet.textureTop = new JSONTexture(armorSet.textureTopString))));
            }

            if(armorSet.textureBottomString != null) {
               event.map.setTextureEntry(armorSet.textureBottomString, (TextureAtlasSprite)((TextureAtlasSprite)(armorSet.textureBottom = new JSONTexture(armorSet.textureBottomString))));
            }

            if(armorSet.textureSideString != null) {
               event.map.setTextureEntry(armorSet.textureSideString, (TextureAtlasSprite)((TextureAtlasSprite)(armorSet.textureSide = new JSONTexture(armorSet.textureSideString))));
            }
         }
      } else if(event.map.func_130086_a() == 1) {
         var2 = JSONRegistries.getRegistry(JSONArmorSet.class).getRegistry().iterator();

         while(var2.hasNext()) {
            JSONArmorSet var6 = (JSONArmorSet)var2.next();

            for(int i = 0; i < var6.getArmorSet().length; ++i) {
               JSONArmor armor = var6.getArmorSet()[i];
               if(armor != null) {
                  event.map.setTextureEntry(armor.iconHash, armor.setIcon(new JSONTexture(armor.iconHash)));
               }
            }
         }
      }

   }

   @ForgeSubscribe
   public void onTooltip(ItemTooltipEvent event) {
      if(Minecraft.func_71410_x().field_71462_r instanceof RecipeListGUI) {
         event.toolTip.add("ID " + event.itemStack.field_77993_c + ":" + event.itemStack.func_77960_j());
      } else if(Minecraft.func_71410_x().field_71462_r instanceof InventoryGUI && ClientProxy.serverType.equals("ng") && ClientKeyHandler.KEY_SELL.field_74512_d > 0) {
         event.toolTip.add(I18n.func_135052_a("sell.press", new Object[]{Keyboard.getKeyName(ClientKeyHandler.KEY_SELL.field_74512_d)}));
      }

   }

   @ForgeSubscribe
   public void onWorldRendering(RenderWorldLastEvent event) {
      EntityClientPlayerMP entity = Minecraft.func_71410_x().field_71439_g;
      GL11.glPushMatrix();
      RenderUtils.translateToWorldCoords(entity, event.partialTicks);
      if(ClientKeyHandler.displayChunck) {
         this.renderChunkBounds(entity);
      }

      if(ClientKeyHandler.displayMobSpawning) {
         this.renderMobSpawnOverlay(entity);
      }

      GL11.glPopMatrix();
   }

   private void renderChunkBounds(Entity entity) {
      GL11.glDisable(3553);
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      GL11.glDisable(2896);
      GL11.glLineWidth(1.5F);
      GL11.glBegin(1);

      for(int cx = -4; cx <= 4; ++cx) {
         for(int cz = -4; cz <= 4; ++cz) {
            double x1 = (double)(entity.field_70176_ah + cx << 4);
            double z1 = (double)(entity.field_70164_aj + cz << 4);
            double x2 = x1 + 16.0D;
            double z2 = z1 + 16.0D;
            double dy = 128.0D;
            double y1 = Math.floor(entity.field_70163_u - dy / 2.0D);
            double y2 = y1 + dy;
            if(y1 < 0.0D) {
               y1 = 0.0D;
               y2 = dy;
            }

            if(y1 > (double)entity.field_70170_p.func_72800_K()) {
               y2 = (double)entity.field_70170_p.func_72800_K();
               y1 = y2 - dy;
            }

            double dist = Math.pow(1.5D, (double)(-(cx * cx + cz * cz)));
            GL11.glColor4d(0.9D, 0.0D, 0.0D, dist);
            if(cx >= 0 && cz >= 0) {
               GL11.glVertex3d(x2, y1, z2);
               GL11.glVertex3d(x2, y2, z2);
            }

            if(cx >= 0 && cz <= 0) {
               GL11.glVertex3d(x2, y1, z1);
               GL11.glVertex3d(x2, y2, z1);
            }

            if(cx <= 0 && cz >= 0) {
               GL11.glVertex3d(x1, y1, z2);
               GL11.glVertex3d(x1, y2, z2);
            }

            if(cx <= 0 && cz <= 0) {
               GL11.glVertex3d(x1, y1, z1);
               GL11.glVertex3d(x1, y2, z1);
            }

            if(cx == 0 && cz == 0) {
               dy = 32.0D;
               y1 = Math.floor(entity.field_70163_u - dy / 2.0D);
               y2 = y1 + dy;
               if(y1 < 0.0D) {
                  y1 = 0.0D;
                  y2 = dy;
               }

               if(y1 > (double)entity.field_70170_p.func_72800_K()) {
                  y2 = (double)entity.field_70170_p.func_72800_K();
                  y1 = y2 - dy;
               }

               GL11.glColor4d(0.0D, 0.9D, 0.0D, 0.4D);

               double h;
               for(h = (double)((int)y1); h <= y2; ++h) {
                  GL11.glVertex3d(x2, h, z1);
                  GL11.glVertex3d(x2, h, z2);
                  GL11.glVertex3d(x1, h, z1);
                  GL11.glVertex3d(x1, h, z2);
                  GL11.glVertex3d(x1, h, z2);
                  GL11.glVertex3d(x2, h, z2);
                  GL11.glVertex3d(x1, h, z1);
                  GL11.glVertex3d(x2, h, z1);
               }

               for(h = 1.0D; h <= 15.0D; ++h) {
                  GL11.glVertex3d(x1 + h, y1, z1);
                  GL11.glVertex3d(x1 + h, y2, z1);
                  GL11.glVertex3d(x1 + h, y1, z2);
                  GL11.glVertex3d(x1 + h, y2, z2);
                  GL11.glVertex3d(x1, y1, z1 + h);
                  GL11.glVertex3d(x1, y2, z1 + h);
                  GL11.glVertex3d(x2, y1, z1 + h);
                  GL11.glVertex3d(x2, y2, z1 + h);
               }
            }
         }
      }

      GL11.glEnd();
      GL11.glEnable(2896);
      GL11.glEnable(3553);
      GL11.glDisable(3042);
   }

   private void renderMobSpawnOverlay(Entity entity) {
      GL11.glDisable(3553);
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      GL11.glDisable(2896);
      GL11.glLineWidth(1.5F);
      GL11.glBegin(1);
      GL11.glColor4f(1.0F, 0.0F, 0.0F, 1.0F);
      int curSpawnMode = 2;
      World world = entity.field_70170_p;
      int x1 = (int)entity.field_70165_t;
      int z1 = (int)entity.field_70161_v;
      int y1 = (int)net.ilexiconn.nationsgui.forge.client.util.MathHelper.clip(entity.field_70163_u, 16.0D, (double)(world.func_72800_K() - 16));
      AxisAlignedBB aabb = AxisAlignedBB.func_72330_a(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D);

      for(int x = x1 - 16; x <= x1 + 16; ++x) {
         for(int z = z1 - 16; z <= z1 + 16; ++z) {
            Chunk chunk = world.func_72938_d(x, z);
            BiomeGenBase biome = world.func_72807_a(x, z);
            if(!biome.func_76747_a(EnumCreatureType.monster).isEmpty() && biome.func_76741_f() > 0.0F) {
               for(int y = y1 - 16; y < y1 + 16; ++y) {
                  int spawnMode = this.getSpawnMode(chunk, aabb, x, y, z);
                  if(spawnMode != 0) {
                     if(spawnMode != curSpawnMode) {
                        if(spawnMode == 1) {
                           GL11.glColor4f(1.0F, 1.0F, 0.0F, 1.0F);
                        } else {
                           GL11.glColor4f(1.0F, 0.0F, 0.0F, 1.0F);
                        }

                        curSpawnMode = spawnMode;
                     }

                     GL11.glVertex3d((double)x, (double)y + 0.004D, (double)z);
                     GL11.glVertex3d((double)(x + 1), (double)y + 0.004D, (double)(z + 1));
                     GL11.glVertex3d((double)(x + 1), (double)y + 0.004D, (double)z);
                     GL11.glVertex3d((double)x, (double)y + 0.004D, (double)(z + 1));
                  }
               }
            }
         }
      }

      GL11.glEnd();
      GL11.glEnable(2896);
      GL11.glEnable(3553);
      GL11.glDisable(3042);
   }

   private int getSpawnMode(Chunk chunk, AxisAlignedBB aabb, int x, int y, int z) {
      if(SpawnerAnimals.func_77190_a(EnumCreatureType.monster, chunk.field_76637_e, x, y, z) && chunk.func_76614_a(EnumSkyBlock.Block, x & 15, y, z & 15) < 8) {
         aabb.field_72340_a = (double)x + 0.2D;
         aabb.field_72336_d = (double)x + 0.8D;
         aabb.field_72338_b = (double)y + 0.01D;
         aabb.field_72337_e = (double)y + 1.8D;
         aabb.field_72339_c = (double)z + 0.2D;
         aabb.field_72334_f = (double)z + 0.8D;
         return chunk.field_76637_e.func_72855_b(aabb) && chunk.field_76637_e.func_72840_a(aabb).isEmpty() && !chunk.field_76637_e.func_72953_d(aabb)?(chunk.func_76614_a(EnumSkyBlock.Sky, x & 15, y, z & 15) >= 8?1:2):0;
      } else {
         return 0;
      }
   }

   public ObjectiveOverride getObjectiveOverlay() {
      return this.objective;
   }

   public TitleOverride getTitleOverlay() {
      return this.title;
   }

   public String getBadgeTextColor(String badge) {
      if(badge == null) {
         return "";
      } else {
         byte var3 = -1;
         switch(badge.hashCode()) {
         case -2016291104:
            if(badge.equals("moderateur")) {
               var3 = 8;
            }
            break;
         case -1996099632:
            if(badge.equals("fondateur")) {
               var3 = 0;
            }
            break;
         case -1973319297:
            if(badge.equals("respadmin")) {
               var3 = 4;
            }
            break;
         case -1148811906:
            if(badge.equals("adminmanager")) {
               var3 = 5;
            }
            break;
         case -1106574323:
            if(badge.equals("legend")) {
               var3 = 16;
            }
            break;
         case -674640977:
            if(badge.equals("founder")) {
               var3 = 2;
            }
            break;
         case -624447305:
            if(badge.equals("mod_plus")) {
               var3 = 12;
            }
            break;
         case -624334929:
            if(badge.equals("mod_test")) {
               var3 = 13;
            }
            break;
         case -596537522:
            if(badge.equals("co-founder")) {
               var3 = 3;
            }
            break;
         case -332106840:
            if(badge.equals("supermodo")) {
               var3 = 7;
            }
            break;
         case -318452137:
            if(badge.equals("premium")) {
               var3 = 14;
            }
            break;
         case 108290:
            if(badge.equals("mod")) {
               var3 = 11;
            }
            break;
         case 3198970:
            if(badge.equals("hero")) {
               var3 = 18;
            }
            break;
         case 55934456:
            if(badge.equals("legende")) {
               var3 = 15;
            }
            break;
         case 92668751:
            if(badge.equals("admin")) {
               var3 = 6;
            }
            break;
         case 99168185:
            if(badge.equals("heros")) {
               var3 = 17;
            }
            break;
         case 1588692301:
            if(badge.equals("affiliate")) {
               var3 = 19;
            }
            break;
         case 1670429593:
            if(badge.equals("moderateur_plus")) {
               var3 = 9;
            }
            break;
         case 1670541969:
            if(badge.equals("moderateur_test")) {
               var3 = 10;
            }
            break;
         case 1854118753:
            if(badge.equals("co-fonda")) {
               var3 = 1;
            }
         }

         switch(var3) {
         case 0:
         case 1:
         case 2:
         case 3:
            return "\u00a7b";
         case 4:
         case 5:
         case 6:
         case 7:
            return "\u00a7c";
         case 8:
         case 9:
         case 10:
         case 11:
         case 12:
         case 13:
            return "\u00a7a";
         case 14:
            return "\u00a76";
         case 15:
         case 16:
            return "\u00a73";
         case 17:
         case 18:
            return "\u00a77";
         case 19:
            return "\u00a7d";
         default:
            return "";
         }
      }
   }

   public int getBadgeBGColor(String badge) {
      if(badge == null) {
         return 1073741824;
      } else {
         String var2 = badge.replaceAll("badges_", "");
         byte var3 = -1;
         switch(var2.hashCode()) {
         case -2016291104:
            if(var2.equals("moderateur")) {
               var3 = 8;
            }
            break;
         case -1996099632:
            if(var2.equals("fondateur")) {
               var3 = 0;
            }
            break;
         case -1973319297:
            if(var2.equals("respadmin")) {
               var3 = 4;
            }
            break;
         case -1148811906:
            if(var2.equals("adminmanager")) {
               var3 = 5;
            }
            break;
         case -1106574323:
            if(var2.equals("legend")) {
               var3 = 16;
            }
            break;
         case -674640977:
            if(var2.equals("founder")) {
               var3 = 2;
            }
            break;
         case -624447305:
            if(var2.equals("mod_plus")) {
               var3 = 12;
            }
            break;
         case -624334929:
            if(var2.equals("mod_test")) {
               var3 = 13;
            }
            break;
         case -596537522:
            if(var2.equals("co-founder")) {
               var3 = 3;
            }
            break;
         case -332106840:
            if(var2.equals("supermodo")) {
               var3 = 7;
            }
            break;
         case -318452137:
            if(var2.equals("premium")) {
               var3 = 14;
            }
            break;
         case 108290:
            if(var2.equals("mod")) {
               var3 = 11;
            }
            break;
         case 3198970:
            if(var2.equals("hero")) {
               var3 = 18;
            }
            break;
         case 55934456:
            if(var2.equals("legende")) {
               var3 = 15;
            }
            break;
         case 92668751:
            if(var2.equals("admin")) {
               var3 = 6;
            }
            break;
         case 99168185:
            if(var2.equals("heros")) {
               var3 = 17;
            }
            break;
         case 1588692301:
            if(var2.equals("affiliate")) {
               var3 = 19;
            }
            break;
         case 1670429593:
            if(var2.equals("moderateur_plus")) {
               var3 = 9;
            }
            break;
         case 1670541969:
            if(var2.equals("moderateur_test")) {
               var3 = 10;
            }
            break;
         case 1854118753:
            if(var2.equals("co-fonda")) {
               var3 = 1;
            }
         }

         switch(var3) {
         case 0:
         case 1:
         case 2:
         case 3:
            return 1074218055;
         case 4:
         case 5:
         case 6:
         case 7:
            return 1078396679;
         case 8:
         case 9:
         case 10:
         case 11:
         case 12:
         case 13:
            return 1074808583;
         case 14:
            return 1078409991;
         case 15:
         case 16:
            return 1074218805;
         case 17:
         case 18:
            return 1076307751;
         case 19:
            return 1077610039;
         default:
            return 1073741824;
         }
      }
   }

   public int getHealthColor(Entity entity, float health, float maxHealth) {
      if(entity instanceof CarePackageEntity) {
         return 2146707018;
      } else {
         float ratio = health / maxHealth;
         return ratio >= 0.75F?(Minecraft.func_71410_x().field_71441_e.field_73011_w instanceof GCEdoraWorldProvider?2138695154:2130771712):(ratio >= 0.5F?2146954241:(ratio >= 0.25F?2146929409:2146898945));
      }
   }

   public void openMoonGui(boolean goalAchieved, double goal, double actualMoney, List<String> donators) {
      Minecraft.func_71410_x().func_71373_a(new ToTheMoonGUI(!goalAchieved, goal, actualMoney, donators));
   }

   @ForgeSubscribe
   public void onPlayerPreRender(Pre event) {
      if(!ClientProxy.clientConfig.renderCustomArmors) {
         this.armorSave = (ItemStack[])event.entityPlayer.field_71071_by.field_70460_b.clone();
         ItemStack currentItem = event.entityPlayer.field_71071_by.func_70448_g();
         this.itemSave = currentItem;

         for(int sword = 0; sword < event.entityPlayer.field_71071_by.field_70460_b.length; ++sword) {
            ItemStack slot = event.entityPlayer.field_71071_by.field_70460_b[sword];
            if(slot != null && (renderLightClassBlacklist.contains(slot.func_77973_b().getClass()) || renderLightIdsBlacklist.contains(Integer.valueOf(slot.field_77993_c)))) {
               ItemStack newItem = null;
               switch(sword) {
               case 0:
                  newItem = new ItemStack(Item.field_77802_ao);
                  break;
               case 1:
                  newItem = new ItemStack(Item.field_77808_an);
                  break;
               case 2:
                  newItem = new ItemStack(Item.field_77806_am);
                  break;
               case 3:
                  newItem = new ItemStack(Item.field_77796_al);
               }

               if(newItem != null) {
                  newItem.func_77966_a(Enchantment.field_77342_w, 1);
                  event.entityPlayer.field_71071_by.field_70460_b[sword] = newItem;
               }
            }
         }

         if(currentItem != null && (renderLightClassBlacklist.contains(currentItem.func_77973_b().getClass()) || renderLightIdsBlacklist.contains(Integer.valueOf(currentItem.field_77993_c)))) {
            ItemStack var6 = new ItemStack(Item.field_77672_G);
            var6.func_77966_a(Enchantment.field_77342_w, 1);
            event.entityPlayer.field_71071_by.func_70299_a(event.entityPlayer.field_71071_by.field_70461_c, var6);
         }
      }

   }

   @ForgeSubscribe
   public void onPlayerPostRender(net.minecraftforge.client.event.RenderPlayerEvent.Post event) {
      if(!ClientProxy.clientConfig.renderCustomArmors) {
         event.entityPlayer.field_71071_by.field_70460_b = this.armorSave;
         event.entityPlayer.field_71071_by.func_70299_a(event.entityPlayer.field_71071_by.field_70461_c, this.itemSave);
      }

   }

   @ForgeSubscribe
   public void onPreRender(UpdateLightMapEvent event) {
      if(System.currentTimeMillis() - lastPlasmaCounter.longValue() < 1000L) {
         event.r = 0.25882354F;
         event.b = 0.88235295F;
         event.g = 1.0F;
      } else if(System.currentTimeMillis() - lastLaserPlasmaHit.longValue() < 5000L) {
         event.r = 1.0F;
         event.b = 0.1F;
         event.g = 0.1F;
      } else if(Minecraft.func_71410_x().field_71439_g.func_70660_b(PotionParanoia.potionParanoia) != null) {
         event.r = 1.0F;
         event.b = 0.0F;
         event.g = 0.0F;
      }

      if(ClientData.customRenderColorRed != 1.0F || ClientData.customRenderColorGreen != 1.0F || ClientData.customRenderColorBlue != 1.0F) {
         event.r = ClientData.customRenderColorRed;
         event.g = ClientData.customRenderColorGreen;
         event.b = ClientData.customRenderColorBlue;
      }

   }

   @ForgeSubscribe
   public void onPlayStepSoundHub(PlaySoundEvent event) {
      if(Minecraft.func_71410_x().field_71441_e.field_72995_K && ClientProxy.currentServerName.contains("hub") && event.name != null && event.name.contains("step")) {
         event.result = null;
      }

   }

   public static ClientEventHandler getInstance() {
      if(instance == null) {
         instance = new ClientEventHandler();
      }

      return instance;
   }

}
