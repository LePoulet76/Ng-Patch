package net.ilexiconn.nationsgui.forge.client.gui;

import com.google.gson.internal.LinkedTreeMap;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScrollBarGeneric;
import net.ilexiconn.nationsgui.forge.client.gui.LotoGui$1;
import net.ilexiconn.nationsgui.forge.client.gui.LotoGui$2;
import net.ilexiconn.nationsgui.forge.client.gui.LotoGui$3;
import net.ilexiconn.nationsgui.forge.client.gui.LotoGui$4;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionGUI;
import net.ilexiconn.nationsgui.forge.client.gui.main.component.CustomInputFieldGUI;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.LotoBuyPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.LotoDataPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.LotoGetItemsPacket;
import net.ilexiconn.nationsgui.forge.server.util.SoundStreamer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class LotoGui extends GuiScreen {

   public static int GUI_SCALE = 3;
   public static HashMap<String, Object> data = new HashMap();
   public static boolean loaded = false;
   public static SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
   public static SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
   public static SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yy HH:mm");
   public static LinkedHashMap<String, Integer> colors = new LotoGui$1();
   public static LinkedHashMap<String, Integer> colorIndex = new LotoGui$2();
   public static LinkedHashMap<String, Integer> lightsY = new LotoGui$3();
   public static LinkedHashMap<String, Integer> iconsX = new LotoGui$4();
   public String hoveredAction = "";
   public static long lastLightsSwitch = 0L;
   protected int xSize = 380;
   protected int ySize = 218;
   private int guiLeft;
   private int guiTop;
   private RenderItem itemRenderer = new RenderItem();
   public static boolean currentLightsStatus = false;
   public static long lastFlareFullOpacity = 0L;
   private int carouselLotteryIndex = 0;
   private LinkedTreeMap<String, Object> selectedLottery = null;
   public String displayMode = "in_progress";
   public long lastMusicCheck = 0L;
   private GuiTextField ticketsInput;
   private GuiTextField donationInput;
   private GuiScrollBarGeneric scrollBar;
   private GuiScrollBarGeneric scrollBarWinner;
   private int hoveredLotteryPastId = -1;
   private EntityOtherPlayerMP cachedPlayerEntity = null;


   public LotoGui() {
      loaded = false;
      this.carouselLotteryIndex = 0;
   }

   public static int getElementYByColor(int defaultY, String color) {
      return defaultY + 70 * ((Integer)colorIndex.get(color)).intValue();
   }

   public static List<ItemStack> stringToItemstacks(String itemsSerialized) {
      ArrayList itemStacks = new ArrayList();
      String[] var2 = itemsSerialized.split(",");
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         String itemStackStr = var2[var4];
         itemStacks.add(new ItemStack(Integer.parseInt(itemStackStr.split(":")[0]), 1, Integer.parseInt(itemStackStr.split(":")[1])));
      }

      return itemStacks;
   }

   public void func_73866_w_() {
      super.func_73866_w_();
      PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new LotoDataPacket(false)));
      this.guiLeft = (this.field_73880_f - this.xSize) / 2;
      this.guiTop = (this.field_73881_g - this.ySize) / 2;
      this.ticketsInput = new CustomInputFieldGUI(this.guiLeft + 226, this.guiTop + 135, 29, 12, "georamaMedium", 25);
      this.ticketsInput.func_73804_f(5);
      this.donationInput = new CustomInputFieldGUI(this.guiLeft + 226, this.guiTop + 147, 29, 12, "georamaMedium", 25);
      this.donationInput.func_73804_f(7);
      this.scrollBar = new GuiScrollBarGeneric((float)(this.guiLeft + 348), (float)(this.guiTop + 42), 152, new ResourceLocation("nationsgui", "textures/gui/generic_ingame/cursor_faction.png"), 2, 8);
      this.scrollBarWinner = new GuiScrollBarGeneric((float)(this.guiLeft + 338), (float)(this.guiTop + 130), 49, new ResourceLocation("nationsgui", "textures/gui/generic_ingame/cursor_faction.png"), 2, 8);
      this.cachedPlayerEntity = null;
   }

   public void func_73874_b() {
      if(ClientProxy.commandPlayer != null && ClientProxy.commandPlayer.isPlaying()) {
         ClientProxy.commandPlayer.softClose();
      }

      super.func_73874_b();
   }

   public void func_73863_a(int mouseX, int mouseY, float par3) {
      if(System.currentTimeMillis() - this.lastMusicCheck > 1000L) {
         this.lastMusicCheck = System.currentTimeMillis();
         if(ClientProxy.commandPlayer == null || !ClientProxy.commandPlayer.isPlaying()) {
            ClientProxy.commandPlayer = new SoundStreamer("https://static.nationsglory.fr/N332GNyG2N.mp3");
            ClientProxy.commandPlayer.setVolume(Minecraft.func_71410_x().field_71474_y.field_74340_b * 0.15F);
            (new Thread(ClientProxy.commandPlayer)).start();
         }
      }

      ArrayList tooltipToDraw = new ArrayList();
      this.hoveredAction = "";
      this.hoveredLotteryPastId = -1;
      this.func_73873_v_();
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      ClientEventHandler.STYLE.bindTexture("loto");
      ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 7), (float)(this.guiTop + 7), (float)(7 * GUI_SCALE), (float)(7 * GUI_SCALE), 366 * GUI_SCALE, 204 * GUI_SCALE, 366, 204, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
      ClientEventHandler.STYLE.bindTexture("loto");
      if(mouseX >= this.guiLeft + 347 && mouseX <= this.guiLeft + 347 + 9 && mouseY >= this.guiTop + 20 && mouseY <= this.guiTop + 20 + 9) {
         ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 347), (float)(this.guiTop + 20), (float)(442 * GUI_SCALE), (float)(53 * GUI_SCALE), 9 * GUI_SCALE, 9 * GUI_SCALE, 9, 9, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
         this.hoveredAction = "close";
      } else {
         ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 347), (float)(this.guiTop + 20), (float)(431 * GUI_SCALE), (float)(53 * GUI_SCALE), 9 * GUI_SCALE, 9 * GUI_SCALE, 9, 9, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
      }

      if(this.selectedLottery != null || this.displayMode.equals("past")) {
         ClientEventHandler.STYLE.bindTexture("loto");
         if(mouseX >= this.guiLeft + 305 && mouseX <= this.guiLeft + 305 + 30 && mouseY >= this.guiTop + 20 && mouseY <= this.guiTop + 20 + 9) {
            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 305), (float)(this.guiTop + 20), (float)(439 * GUI_SCALE), (float)(67 * GUI_SCALE), 6 * GUI_SCALE, 9 * GUI_SCALE, 6, 9, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("lottery.label.return"), (float)(this.guiLeft + 315), (float)(this.guiTop + 21), 14803951, 0.5F, "left", false, "georamaSemiBold", 30);
            this.hoveredAction = "return";
         } else {
            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 305), (float)(this.guiTop + 20), (float)(431 * GUI_SCALE), (float)(67 * GUI_SCALE), 6 * GUI_SCALE, 9 * GUI_SCALE, 6, 9, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("lottery.label.return"), (float)(this.guiLeft + 315), (float)(this.guiTop + 21), 10395075, 0.5F, "left", false, "georamaSemiBold", 30);
         }
      }

      if(loaded) {
         ArrayList lotteries_inprogress = (ArrayList)data.get("lotteries_inprogress");
         String color = "yellow";
         LinkedTreeMap currentCarouselLottery = null;
         if(lotteries_inprogress.size() > 0) {
            currentCarouselLottery = (LinkedTreeMap)lotteries_inprogress.get(this.carouselLotteryIndex);
         }

         if(this.selectedLottery != null) {
            currentCarouselLottery = this.selectedLottery;
         }

         if(currentCarouselLottery != null) {
            color = (String)currentCarouselLottery.get("designColor");
         }

         if(this.displayMode.equals("past") && this.selectedLottery == null) {
            color = "yellow";
         }

         ModernGui.glColorHex(((Integer)colors.get(color)).intValue(), 1.0F);
         ModernGui.drawRectangle((float)this.guiLeft, (float)this.guiTop, this.field_73735_i, (float)this.xSize, 4.0F);
         ModernGui.glColorHex(((Integer)colors.get(color)).intValue(), 0.5F);
         ModernGui.drawRectangle((float)this.guiLeft + 4.0F, (float)this.guiTop + 4.0F, this.field_73735_i, (float)(this.xSize - 8), 3.0F);
         ModernGui.glColorHex(((Integer)colors.get(color)).intValue(), 1.0F);
         ModernGui.drawRectangle((float)this.guiLeft, (float)(this.guiTop + this.ySize - 4), this.field_73735_i, (float)this.xSize, 4.0F);
         ModernGui.glColorHex(((Integer)colors.get(color)).intValue(), 0.5F);
         ModernGui.drawRectangle((float)this.guiLeft + 4.0F, (float)this.guiTop + (float)this.ySize - 7.0F, this.field_73735_i, (float)(this.xSize - 8), 3.0F);
         ModernGui.glColorHex(((Integer)colors.get(color)).intValue(), 1.0F);
         ModernGui.drawRectangle((float)this.guiLeft, (float)this.guiTop, this.field_73735_i, 4.0F, (float)this.ySize);
         ModernGui.glColorHex(((Integer)colors.get(color)).intValue(), 0.5F);
         ModernGui.drawRectangle((float)this.guiLeft + 4.0F, (float)this.guiTop + 4.0F, this.field_73735_i, 3.0F, (float)(this.ySize - 8));
         ModernGui.glColorHex(((Integer)colors.get(color)).intValue(), 1.0F);
         ModernGui.drawRectangle((float)this.guiLeft + (float)this.xSize - 4.0F, (float)this.guiTop, this.field_73735_i, 4.0F, (float)this.ySize);
         ModernGui.glColorHex(((Integer)colors.get(color)).intValue(), 0.5F);
         ModernGui.drawRectangle((float)this.guiLeft + (float)this.xSize - 7.0F, (float)this.guiTop + 4.0F, this.field_73735_i, 3.0F, (float)(this.ySize - 8));
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         if(System.currentTimeMillis() - lastLightsSwitch > 250L) {
            lastLightsSwitch = System.currentTimeMillis();
            currentLightsStatus = !currentLightsStatus;
         }

         ClientEventHandler.STYLE.bindTexture("loto");
         ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 307), (float)(this.guiTop - 8), (float)(397 * GUI_SCALE), (float)((currentLightsStatus?((Integer)lightsY.get(color)).intValue():0) * GUI_SCALE), 20 * GUI_SCALE, 20 * GUI_SCALE, 20, 20, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), true);
         ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 368), (float)(this.guiTop - 8), (float)(397 * GUI_SCALE), (float)((!currentLightsStatus?((Integer)lightsY.get(color)).intValue():0) * GUI_SCALE), 20 * GUI_SCALE, 20 * GUI_SCALE, 20, 20, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), true);
         ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 368), (float)(this.guiTop + 46), (float)(397 * GUI_SCALE), (float)((currentLightsStatus?((Integer)lightsY.get(color)).intValue():0) * GUI_SCALE), 20 * GUI_SCALE, 20 * GUI_SCALE, 20, 20, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), true);
         ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 368), (float)(this.guiTop + 100), (float)(397 * GUI_SCALE), (float)((!currentLightsStatus?((Integer)lightsY.get(color)).intValue():0) * GUI_SCALE), 20 * GUI_SCALE, 20 * GUI_SCALE, 20, 20, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), true);
         ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 368), (float)(this.guiTop + 154), (float)(397 * GUI_SCALE), (float)((currentLightsStatus?((Integer)lightsY.get(color)).intValue():0) * GUI_SCALE), 20 * GUI_SCALE, 20 * GUI_SCALE, 20, 20, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), true);
         ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 368), (float)(this.guiTop + 206), (float)(397 * GUI_SCALE), (float)((!currentLightsStatus?((Integer)lightsY.get(color)).intValue():0) * GUI_SCALE), 20 * GUI_SCALE, 20 * GUI_SCALE, 20, 20, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), true);
         ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 307), (float)(this.guiTop + 206), (float)(397 * GUI_SCALE), (float)((currentLightsStatus?((Integer)lightsY.get(color)).intValue():0) * GUI_SCALE), 20 * GUI_SCALE, 20 * GUI_SCALE, 20, 20, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), true);
         ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 244), (float)(this.guiTop + 206), (float)(397 * GUI_SCALE), (float)((!currentLightsStatus?((Integer)lightsY.get(color)).intValue():0) * GUI_SCALE), 20 * GUI_SCALE, 20 * GUI_SCALE, 20, 20, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), true);
         ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 181), (float)(this.guiTop + 206), (float)(397 * GUI_SCALE), (float)((currentLightsStatus?((Integer)lightsY.get(color)).intValue():0) * GUI_SCALE), 20 * GUI_SCALE, 20 * GUI_SCALE, 20, 20, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), true);
         ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 118), (float)(this.guiTop + 206), (float)(397 * GUI_SCALE), (float)((!currentLightsStatus?((Integer)lightsY.get(color)).intValue():0) * GUI_SCALE), 20 * GUI_SCALE, 20 * GUI_SCALE, 20, 20, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), true);
         ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 55), (float)(this.guiTop + 206), (float)(397 * GUI_SCALE), (float)((currentLightsStatus?((Integer)lightsY.get(color)).intValue():0) * GUI_SCALE), 20 * GUI_SCALE, 20 * GUI_SCALE, 20, 20, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), true);
         ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft - 8), (float)(this.guiTop + 206), (float)(397 * GUI_SCALE), (float)((!currentLightsStatus?((Integer)lightsY.get(color)).intValue():0) * GUI_SCALE), 20 * GUI_SCALE, 20 * GUI_SCALE, 20, 20, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), true);
         ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft - 8), (float)(this.guiTop + 154), (float)(397 * GUI_SCALE), (float)((currentLightsStatus?((Integer)lightsY.get(color)).intValue():0) * GUI_SCALE), 20 * GUI_SCALE, 20 * GUI_SCALE, 20, 20, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), true);
         ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft - 8), (float)(this.guiTop + 100), (float)(397 * GUI_SCALE), (float)((!currentLightsStatus?((Integer)lightsY.get(color)).intValue():0) * GUI_SCALE), 20 * GUI_SCALE, 20 * GUI_SCALE, 20, 20, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), true);
         ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft - 8), (float)(this.guiTop + 46), (float)(397 * GUI_SCALE), (float)((currentLightsStatus?((Integer)lightsY.get(color)).intValue():0) * GUI_SCALE), 20 * GUI_SCALE, 20 * GUI_SCALE, 20, 20, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), true);
         ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft - 8), (float)(this.guiTop - 8), (float)(397 * GUI_SCALE), (float)((!currentLightsStatus?((Integer)lightsY.get(color)).intValue():0) * GUI_SCALE), 20 * GUI_SCALE, 20 * GUI_SCALE, 20, 20, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), true);
         ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 55), (float)(this.guiTop - 8), (float)(397 * GUI_SCALE), (float)((currentLightsStatus?((Integer)lightsY.get(color)).intValue():0) * GUI_SCALE), 20 * GUI_SCALE, 20 * GUI_SCALE, 20, 20, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), true);
         ClientEventHandler.STYLE.bindTexture("loto");
         ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 100), (float)(this.guiTop - 35), (float)(248 * GUI_SCALE), (float)(404 * GUI_SCALE), 188 * GUI_SCALE, 95 * GUI_SCALE, 188, 95, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), true);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 133), (float)(this.guiTop - 20), (float)(471 * GUI_SCALE), (float)(411 * GUI_SCALE), 120 * GUI_SCALE, 50 * GUI_SCALE, 120, 50, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
         ModernGui.drawScaledStringCustomFont(I18n.func_135053_a(this.selectedLottery != null?"lottery.player.informations":(this.displayMode.equals("past")?"lottery.player.past_lotteries":"lottery.player.informations")), (float)(this.guiLeft + 25), (float)(this.guiTop + 30), 10395075, 0.5F, "left", false, "georamaSemiBold", 26);
         int e;
         int i;
         String winnerName;
         int var28;
         if(this.displayMode.equals("past") && this.selectedLottery == null) {
            ClientEventHandler.STYLE.bindTexture("loto");
            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 24), (float)(this.guiTop + 38), (float)(0 * GUI_SCALE), (float)(512 * GUI_SCALE), 332 * GUI_SCALE, 160 * GUI_SCALE, 332, 160, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 348), (float)(this.guiTop + 43), (float)(513 * GUI_SCALE), (float)(8 * GUI_SCALE), 2 * GUI_SCALE, 152 * GUI_SCALE, 2, 152, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
            e = 0;

            for(Iterator var23 = ((ArrayList)data.get("lotteries_past")).iterator(); var23.hasNext(); ++e) {
               LinkedTreeMap var25 = (LinkedTreeMap)var23.next();
               GUIUtils.startGLScissor(this.guiLeft + 29, this.guiTop + 46, 315, 152);
               var28 = this.guiLeft + 29;
               Float winner = Float.valueOf((float)(this.guiTop + 46 + e * 39) + this.getSlideLotteries());
               ClientEventHandler.STYLE.bindTexture("loto_staff");
               ModernGui.drawScaledCustomSizeModalRect((float)var28, (float)winner.intValue(), (float)(0 * GUI_SCALE), (float)(219 * GUI_SCALE), 245 * GUI_SCALE, 35 * GUI_SCALE, 245, 35, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
               ModernGui.glColorHex(((Integer)colors.get(var25.get("designColor"))).intValue(), 1.0F);
               ModernGui.drawRoundedRectangle((float)var28 + 234.0F, winner.floatValue(), this.field_73735_i, 81.0F, 35.0F);
               GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
               ClientEventHandler.STYLE.bindTexture("loto");
               ModernGui.drawScaledCustomSizeModalRect((float)(var28 + 2), (float)(winner.intValue() + 1), (float)(((Integer)iconsX.get("calendar")).intValue() * GUI_SCALE), (float)(getElementYByColor(16, (String)var25.get("designColor")) * GUI_SCALE), 11 * GUI_SCALE, 11 * GUI_SCALE, 11, 11, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
               winnerName = dateFormat.format(new Date(((Double)var25.get("endTime")).longValue()));
               ModernGui.drawScaledStringCustomFont(winnerName, (float)(var28 + 15), (float)(winner.intValue() + 3), 16514302, 0.5F, "left", false, "georamaMedium", 26);
               ClientEventHandler.STYLE.bindTexture("loto");
               ModernGui.drawScaledCustomSizeModalRect((float)(var28 + 2), (float)(winner.intValue() + 12), (float)(((Integer)iconsX.get("people")).intValue() * GUI_SCALE), (float)(getElementYByColor(16, (String)var25.get("designColor")) * GUI_SCALE), 11 * GUI_SCALE, 11 * GUI_SCALE, 11, 11, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
               ModernGui.drawScaledStringCustomFont((((String)var25.get("players")).isEmpty()?"0":Integer.valueOf(((String)var25.get("players")).split(",").length)) + " " + I18n.func_135053_a("lottery.label.players"), (float)(var28 + 15), (float)(winner.intValue() + 14), 16514302, 0.5F, "left", false, "georamaMedium", 26);
               ClientEventHandler.STYLE.bindTexture("loto");
               ModernGui.drawScaledCustomSizeModalRect((float)(var28 + 2), (float)(winner.intValue() + 23), (float)(((Integer)iconsX.get("trophee")).intValue() * GUI_SCALE), (float)(getElementYByColor(16, (String)var25.get("designColor")) * GUI_SCALE), 11 * GUI_SCALE, 11 * GUI_SCALE, 11, 11, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
               ModernGui.drawScaledStringCustomFont(String.format("%.0f", new Object[]{(Double)var25.get("winnersCount")}) + " " + I18n.func_135053_a("lottery.label.winners"), (float)(var28 + 15), (float)(winner.intValue() + 25), 16514302, 0.5F, "left", false, "georamaMedium", 26);
               ModernGui.drawScaledStringCustomFont(String.format("%.0f", new Object[]{(Double)var25.get("cashPrice")}) + "$", (float)(var28 + 125), (float)(winner.intValue() + 8), 15463162, 0.75F, "left", false, "georamaSemiBold", 28);
               ModernGui.drawScaledStringCustomFont((((String)var25.get("itemsPrice")).isEmpty()?"0":Integer.valueOf(((String)var25.get("itemsPrice")).split(",").length)) + " item(s)", (float)(var28 + 125), (float)(winner.intValue() + 20), 15463162, 0.5F, "left", false, "georamaSemiBold", 28);
               if(!((String)var25.get("designLogo")).isEmpty() && ((String)var25.get("designLogo")).matches("https://static.nationsglory.fr/.*\\.png")) {
                  ModernGui.bindRemoteTexture((String)var25.get("designLogo"));
                  ModernGui.drawScaledCustomSizeModalRect((float)(var28 + 245), (float)(winner.intValue() + 5), (float)(0 * GUI_SCALE), (float)(0 * GUI_SCALE), 600, 250, 60, 25, 600.0F, 250.0F, false);
               } else {
                  ClientEventHandler.STYLE.bindTexture("loto");
                  ModernGui.drawScaledCustomSizeModalRect((float)(var28 + 245), (float)(winner.intValue() + 5), (float)(471 * GUI_SCALE), (float)(411 * GUI_SCALE), 120 * GUI_SCALE, 50 * GUI_SCALE, 60, 25, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
               }

               GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
               if(mouseX >= this.guiLeft + 29 && mouseX <= this.guiLeft + 29 + 315 && mouseY >= this.guiTop + 46 && mouseY <= this.guiTop + 46 + 152 && mouseX <= var28 + 315 && mouseY >= winner.intValue() && mouseY <= winner.intValue() + 35) {
                  this.hoveredAction = "open_lottery_past";
                  this.hoveredLotteryPastId = e;
               }

               GUIUtils.endGLScissor();
            }

            this.scrollBar.draw(mouseX, mouseY);
         } else if(currentCarouselLottery != null) {
            ModernGui.glColorHex(((Integer)colors.get(color)).intValue(), 1.0F);
            ModernGui.drawRoundedRectangle((float)this.guiLeft + 24.0F + 10.0F, (float)this.guiTop + 38.0F, this.field_73735_i, 322.0F, 76.0F);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            ClientEventHandler.STYLE.bindTexture("loto");
            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 24), (float)(this.guiTop + 38), (float)(172 * GUI_SCALE), (float)(229 * GUI_SCALE), 332 * GUI_SCALE, 76 * GUI_SCALE, 332, 76, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 294), (float)(this.guiTop + 31), (float)(432 * GUI_SCALE), (float)(8 * GUI_SCALE), 62 * GUI_SCALE, 12 * GUI_SCALE, 62, 12, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
            ModernGui.drawScaledStringCustomFont(((Double)currentCarouselLottery.get("ticketsPlayer")).intValue() + " " + I18n.func_135053_a("lottery.label.tickets.short") + " | " + String.format("%.3f", new Object[]{Double.valueOf(((Double)currentCarouselLottery.get("ticketsPlayer")).doubleValue() / Math.max(1.0D, ((Double)currentCarouselLottery.get("ticketsSold")).doubleValue()) * 100.0D)}) + "%", (float)(this.guiLeft + 325), (float)(this.guiTop + 34), 10395075, 0.5F, "center", false, "georamaMedium", 24);
            if(!((String)currentCarouselLottery.get("designLogo")).isEmpty() && ((String)currentCarouselLottery.get("designLogo")).matches("https://static.nationsglory.fr/.*\\.png")) {
               ModernGui.bindRemoteTexture((String)currentCarouselLottery.get("designLogo"));
               ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 247), (float)(this.guiTop + 57), (float)(0 * GUI_SCALE), (float)(0 * GUI_SCALE), 600, 250, 96, 40, 600.0F, 250.0F, false);
            } else {
               ClientEventHandler.STYLE.bindTexture("loto");
               ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 247), (float)(this.guiTop + 57), (float)(471 * GUI_SCALE), (float)(411 * GUI_SCALE), 120 * GUI_SCALE, 50 * GUI_SCALE, 96, 40, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
            }

            e = 0;
            if(((Double)currentCarouselLottery.get("cashPrice")).doubleValue() <= 0.0D && (!currentCarouselLottery.containsKey("poolMode") || !((String)currentCarouselLottery.get("poolMode")).equals("true"))) {
               if(!((String)currentCarouselLottery.get("itemsPrice")).isEmpty()) {
                  ModernGui.drawScaledString(I18n.func_135053_a("lottery.player.items_lot"), this.guiLeft + 35, this.guiTop + 55, 16514302, 2.5F, false, true);
                  e += 22;
               }
            } else {
               ModernGui.drawScaledString(ModernGui.formatIntToDevise(((Double)currentCarouselLottery.get("cashPrice")).intValue()) + "$", this.guiLeft + 35, this.guiTop + 55, 16514302, 2.5F, false, true);
               e += 22;
            }

            if(!((String)currentCarouselLottery.get("itemsPrice")).isEmpty()) {
               List e1 = stringToItemstacks((String)currentCarouselLottery.get("itemsPrice"));

               for(i = 0; i < e1.size(); ++i) {
                  ClientEventHandler.STYLE.bindTexture("loto");
                  ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 33 + 20 * i), (float)(this.guiTop + 55 + e), (float)(461 * GUI_SCALE), (float)(52 * GUI_SCALE), 16 * GUI_SCALE, 16 * GUI_SCALE, 16, 16, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
                  ItemStack itemStack = (ItemStack)e1.get(i);
                  GL11.glPushMatrix();
                  this.itemRenderer.func_82406_b(this.field_73886_k, Minecraft.func_71410_x().func_110434_K(), itemStack, this.guiLeft + 33 + 20 * i, this.guiTop + 55 + e);
                  GL11.glPopMatrix();
                  GL11.glDisable(2896);
                  GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
               }
            }
         } else {
            ClientEventHandler.STYLE.bindTexture("loto");
            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 24), (float)(this.guiTop + 38 - 31), (float)(0 * GUI_SCALE), (float)(917 * GUI_SCALE), 332 * GUI_SCALE, 107 * GUI_SCALE, 332, 107, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), true);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("lottery.player.no_lottery_inprogress"), (float)(this.guiLeft + 35), (float)(this.guiTop + 50), 16514302, 0.5F, "left", false, "georamaSemiBold", 30);
         }

         if(this.selectedLottery != null) {
            ClientEventHandler.STYLE.bindTexture("loto");
            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 24), (float)(this.guiTop + 110), (float)(0 * GUI_SCALE), (float)(308 * GUI_SCALE), 332 * GUI_SCALE, 89 * GUI_SCALE, 332, 89, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 37), (float)(this.guiTop + 122), (float)(((Integer)iconsX.get("calendar")).intValue() * GUI_SCALE), (float)(getElementYByColor(16, (String)this.selectedLottery.get("designColor")) * GUI_SCALE), 11 * GUI_SCALE, 11 * GUI_SCALE, 11, 11, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("lottery.player.draw_on") + " " + dateTimeFormat.format(new Date(((Double)this.selectedLottery.get("endTime")).longValue())), (float)(this.guiLeft + 51), (float)(this.guiTop + 124), 16514302, 0.5F, "left", false, "georamaSemiBold", 26);
            ClientEventHandler.STYLE.bindTexture("loto");
            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 37), (float)(this.guiTop + 137), (float)(((Integer)iconsX.get("people")).intValue() * GUI_SCALE), (float)(getElementYByColor(16, (String)this.selectedLottery.get("designColor")) * GUI_SCALE), 11 * GUI_SCALE, 11 * GUI_SCALE, 11, 11, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
            ModernGui.drawScaledStringCustomFont((((String)this.selectedLottery.get("players")).isEmpty()?"0":Integer.valueOf(((String)this.selectedLottery.get("players")).split(",").length)) + " " + I18n.func_135053_a("lottery.label.players"), (float)(this.guiLeft + 51), (float)(this.guiTop + 139), 16514302, 0.5F, "left", false, "georamaSemiBold", 26);
            ModernGui.glColorHex(((Integer)colors.get(this.selectedLottery.get("designColor"))).intValue(), 1.0F);
            ModernGui.drawRoundedRectangle((float)this.guiLeft + 34.0F, (float)this.guiTop + 155.0F, this.field_73735_i, 150.0F, 35.0F);
            ClientEventHandler.STYLE.bindTexture("loto");
            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 37), (float)(this.guiTop + 159), (float)(443 * GUI_SCALE), (float)(84 * GUI_SCALE), 11 * GUI_SCALE, 11 * GUI_SCALE, 11, 11, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
            ModernGui.drawScaledStringCustomFont(((Double)this.selectedLottery.get("ticketsPlayer")).intValue() + " " + I18n.func_135053_a(((Double)this.selectedLottery.get("ticketsPlayer")).doubleValue() > 0.0D?"lottery.player.bought_tickets":"lottery.player.bought_ticket") + " (" + Double.valueOf(((Double)this.selectedLottery.get("ticketsPlayer")).doubleValue() * ((Double)this.selectedLottery.get("ticketPrice")).doubleValue()).intValue() + "$)", (float)(this.guiLeft + 51), (float)(this.guiTop + 162), 2826561, 0.5F, "left", false, "georamaSemiBold", 26);
            ClientEventHandler.STYLE.bindTexture("loto");
            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 37), (float)(this.guiTop + 174), (float)(465 * GUI_SCALE), (float)(84 * GUI_SCALE), 11 * GUI_SCALE, 11 * GUI_SCALE, 11, 11, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
            ModernGui.drawScaledStringCustomFont(String.format("%.3f", new Object[]{Double.valueOf(((Double)this.selectedLottery.get("ticketsPlayer")).doubleValue() / Math.max(1.0D, ((Double)this.selectedLottery.get("ticketsSold")).doubleValue()) * 100.0D)}) + "% " + I18n.func_135053_a("lottery.player.win_chance"), (float)(this.guiLeft + 51), (float)(this.guiTop + 177), 2826561, 0.5F, "left", false, "georamaSemiBold", 26);
            if(this.displayMode.equals("past")) {
               ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("lottery.player.winners"), (float)(this.guiLeft + 196), (float)(this.guiTop + 118), 16514302, 0.5F, "left", false, "georamaSemiBold", 28);
               String[] var24;
               String var29;
               if(!((String)this.selectedLottery.get("winners")).isEmpty() && ((String)this.selectedLottery.get("winners")).split(",").length == 1) {
                  String var26 = ((String)this.selectedLottery.get("winners")).split("#")[0];
                  ModernGui.drawScaledStringCustomFont(var26, (float)(this.guiLeft + 219), (float)(this.guiTop + 149), 16514302, 0.5F, "left", false, "georamaBold", 28);
                  var24 = ((String)this.selectedLottery.get("players")).split(",");
                  i = var24.length;

                  for(var28 = 0; var28 < i; ++var28) {
                     var29 = var24[var28];
                     if(var29.split("#")[0].equals(var26)) {
                        ModernGui.drawScaledStringCustomFont(var29.split("#")[1] + " " + I18n.func_135053_a("lottery.label.tickets"), (float)(this.guiLeft + 219), (float)(this.guiTop + 158), 10395075, 0.5F, "left", false, "georamaSemiBold", 28);
                     }
                  }

                  if(this.cachedPlayerEntity != null && this.cachedPlayerEntity.field_71092_bJ.equals(var26)) {
                     GUIUtils.startGLScissor(this.guiLeft + 285, this.guiTop + 111, 51, 75);
                     GuiInventory.func_110423_a(this.guiLeft + 310, this.guiTop + 212, 50, 0.0F, 0.0F, this.cachedPlayerEntity);
                     GUIUtils.endGLScissor();
                  } else {
                     try {
                        this.cachedPlayerEntity = new EntityOtherPlayerMP(this.field_73882_e.field_71441_e, var26);
                     } catch (Exception var22) {
                        this.cachedPlayerEntity = null;
                     }
                  }
               } else if(!((String)this.selectedLottery.get("winners")).isEmpty()) {
                  ClientEventHandler.STYLE.bindTexture("loto");
                  ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 338), (float)(this.guiTop + 130), (float)(513 * GUI_SCALE), (float)(8 * GUI_SCALE), 2 * GUI_SCALE, 49 * GUI_SCALE, 2, 49, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
                  GUIUtils.startGLScissor(this.guiLeft + 201, this.guiTop + 131, 140, 55);
                  e = 0;
                  var24 = ((String)this.selectedLottery.get("winners")).split(",");
                  i = var24.length;

                  for(var28 = 0; var28 < i; ++var28) {
                     var29 = var24[var28];
                     winnerName = var29.split("#")[0];
                     int offsetX = this.guiLeft + 201;
                     Float offsetY = Float.valueOf((float)(this.guiTop + 131 + e * 13) + this.getSlideWinners());
                     if(!ClientProxy.cacheHeadPlayer.containsKey(winnerName)) {
                        try {
                           ResourceLocation e2 = AbstractClientPlayer.field_110314_b;
                           e2 = AbstractClientPlayer.func_110311_f(winnerName);
                           AbstractClientPlayer.func_110304_a(e2, winnerName);
                           ClientProxy.cacheHeadPlayer.put(winnerName, e2);
                        } catch (Exception var21) {
                           System.out.println(var21.getMessage());
                        }
                     } else {
                        Minecraft.func_71410_x().field_71446_o.func_110577_a((ResourceLocation)ClientProxy.cacheHeadPlayer.get(winnerName));
                        this.field_73882_e.func_110434_K().func_110577_a((ResourceLocation)ClientProxy.cacheHeadPlayer.get(winnerName));
                        GUIUtils.drawScaledCustomSizeModalRect(offsetX + 12, offsetY.intValue() + 0 + 10, 8.0F, 16.0F, 8, -8, -10, -10, 64.0F, 64.0F);
                     }

                     ModernGui.drawScaledStringCustomFont(winnerName, (float)(offsetX + 17), (float)(offsetY.intValue() + 2), 16514302, 0.5F, "left", false, "georamaSemiBold", 26);
                     String[] var30 = ((String)this.selectedLottery.get("players")).split(",");
                     int var17 = var30.length;

                     for(int var18 = 0; var18 < var17; ++var18) {
                        String player = var30[var18];
                        if(player.split("#")[0].equals(winnerName)) {
                           ModernGui.drawScaledStringCustomFont(player.split("#")[1] + " " + I18n.func_135053_a("lottery.label.tickets"), (float)(offsetX + 132), (float)(offsetY.intValue() + 1), 10395075, 0.5F, "right", false, "georamaMedium", 26);
                        }
                     }

                     ++e;
                  }

                  GUIUtils.endGLScissor();
                  this.scrollBarWinner.draw(mouseX, mouseY);
               }

               if(((String)this.selectedLottery.get("players_itemsPrice")).matches("(,.*|^)" + Minecraft.func_71410_x().field_71439_g.field_71092_bJ + "(,.*|$)")) {
                  ClientEventHandler.STYLE.bindTexture("loto");
                  if(mouseX >= this.guiLeft + 244 && mouseX <= this.guiLeft + 244 + 53 && mouseY >= this.guiTop + 180 && mouseY <= this.guiTop + 180 + 11) {
                     ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 244), (float)(this.guiTop + 180), (float)(519 * GUI_SCALE), (float)(8 * GUI_SCALE), 53 * GUI_SCALE, 11 * GUI_SCALE, 53, 11, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
                     this.hoveredAction = "get_items";
                  } else {
                     ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 244), (float)(this.guiTop + 180), (float)(779 * GUI_SCALE), (float)(getElementYByColor(3, (String)this.selectedLottery.get("designColor")) * GUI_SCALE), 53 * GUI_SCALE, 11 * GUI_SCALE, 53, 11, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
                  }

                  ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("lottery.label.get_items"), (float)(this.guiLeft + 244 + 27), (float)(this.guiTop + 183), 2234425, 0.5F, "center", false, "georamaSemiBold", 24);
               }
            } else {
               ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("lottery.player.participate"), (float)(this.guiLeft + 196), (float)(this.guiTop + 116), 16514302, 0.5F, "left", false, "georamaSemiBold", 28);
               ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("lottery.player.tickets"), (float)(this.guiLeft + 200), (float)(this.guiTop + 138), 15463162, 0.5F, "left", false, "georamaSemiBold", 26);
               ClientEventHandler.STYLE.bindTexture("loto");
               ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 227), (float)(this.guiTop + 137), (float)(838 * GUI_SCALE), (float)(getElementYByColor(3, (String)this.selectedLottery.get("designColor")) * GUI_SCALE), 30 * GUI_SCALE, 9 * GUI_SCALE, 30, 9, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
               this.ticketsInput.func_73795_f();
               if(((String)this.selectedLottery.get("allowDonation")).equals("true")) {
                  ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("lottery.player.donations"), (float)(this.guiLeft + 200), (float)(this.guiTop + 150), 15463162, 0.5F, "left", false, "georamaSemiBold", 26);
                  ClientEventHandler.STYLE.bindTexture("loto");
                  ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 227), (float)(this.guiTop + 149), (float)(472 * GUI_SCALE), (float)(185 * GUI_SCALE), 30 * GUI_SCALE, 9 * GUI_SCALE, 30, 9, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
                  this.donationInput.func_73795_f();
               }

               ModernGui.drawScaledStringCustomFont("TOTAL " + (FactionGUI.isNumeric(this.ticketsInput.func_73781_b(), true) && FactionGUI.isNumeric(this.donationInput.func_73781_b(), true)?Integer.parseInt(this.ticketsInput.func_73781_b()) * ((Double)this.selectedLottery.get("ticketPrice")).intValue() + Integer.parseInt(this.donationInput.func_73781_b()):0) + "$", (float)(this.guiLeft + 271), (float)(this.guiTop + 170), 16514302, 0.5F, "center", false, "georamaSemiBold", 32);
               ClientEventHandler.STYLE.bindTexture("loto");
               if(mouseX >= this.guiLeft + 244 && mouseX <= this.guiLeft + 244 + 53 && mouseY >= this.guiTop + 180 && mouseY <= this.guiTop + 180 + 11) {
                  ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 244), (float)(this.guiTop + 180), (float)(519 * GUI_SCALE), (float)(8 * GUI_SCALE), 53 * GUI_SCALE, 11 * GUI_SCALE, 53, 11, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
                  this.hoveredAction = "buy";
               } else {
                  ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 244), (float)(this.guiTop + 180), (float)(779 * GUI_SCALE), (float)(getElementYByColor(3, (String)this.selectedLottery.get("designColor")) * GUI_SCALE), 53 * GUI_SCALE, 11 * GUI_SCALE, 53, 11, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
               }

               ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("lottery.label.buy"), (float)(this.guiLeft + 244 + 27), (float)(this.guiTop + 183), 2234425, 0.5F, "center", false, "georamaSemiBold", 24);
               ClientEventHandler.STYLE.bindTexture("loto");
               ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 276), (float)(this.guiTop + 137), (float)(437 * GUI_SCALE), (float)(197 * GUI_SCALE), 65 * GUI_SCALE, 21 * GUI_SCALE, 65, 21, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
               ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("lottery.player.ticket_price") + " : " + ((Double)this.selectedLottery.get("ticketPrice")).intValue() + "$", (float)(this.guiLeft + 280), (float)(this.guiTop + 140), 10395075, 0.5F, "left", false, "georamaSemiBold", 24);
               ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("lottery.player.remaining_tickets") + " : " + (((Double)this.selectedLottery.get("maxTicketsGlobal")).doubleValue() != 0.0D?String.format("%.0f", new Object[]{Double.valueOf(Math.max(0.0D, ((Double)this.selectedLottery.get("maxTicketsGlobal")).doubleValue() - ((Double)this.selectedLottery.get("ticketsSold")).doubleValue()))}):"-"), (float)(this.guiLeft + 280), (float)(this.guiTop + 149), 10395075, 0.5F, "left", false, "georamaSemiBold", 24);
            }
         } else if(!this.displayMode.equals("past")) {
            if(currentCarouselLottery != null) {
               ClientEventHandler.STYLE.bindTexture("loto");
               if(mouseX >= this.guiLeft + 33 && mouseX <= this.guiLeft + 33 + 53 && mouseY >= this.guiTop + 97 && mouseY <= this.guiTop + 97 + 11) {
                  ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 33), (float)(this.guiTop + 97), (float)(519 * GUI_SCALE), (float)(8 * GUI_SCALE), 53 * GUI_SCALE, 11 * GUI_SCALE, 53, 11, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
                  this.hoveredAction = "participate";
               } else {
                  ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 33), (float)(this.guiTop + 97), (float)(779 * GUI_SCALE), (float)(getElementYByColor(3, (String)currentCarouselLottery.get("designColor")) * GUI_SCALE), 53 * GUI_SCALE, 11 * GUI_SCALE, 53, 11, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
               }

               ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("lottery.label.participate"), (float)(this.guiLeft + 33 + 27), (float)(this.guiTop + 100), 2234425, 0.5F, "center", false, "georamaSemiBold", 24);
               ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("lottery.player.draw_on") + " " + dateTimeFormat.format(new Date(((Double)currentCarouselLottery.get("endTime")).longValue())), (float)(this.guiLeft + 352), (float)(this.guiTop + 107), 2234425, 0.5F, "right", false, "georamaSemiBold", 22);
               ClientEventHandler.STYLE.bindTexture("loto");
               if(mouseX >= this.guiLeft + 12 && mouseX <= this.guiLeft + 12 + 10 && mouseY >= this.guiTop + 67 && mouseY <= this.guiTop + 67 + 18) {
                  ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 12), (float)(this.guiTop + 67), (float)(454 * GUI_SCALE), (float)(29 * GUI_SCALE), 10 * GUI_SCALE, 18 * GUI_SCALE, 10, 18, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
                  this.hoveredAction = "carousel_previous";
               } else {
                  ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 12), (float)(this.guiTop + 67), (float)(466 * GUI_SCALE), (float)(29 * GUI_SCALE), 10 * GUI_SCALE, 18 * GUI_SCALE, 10, 18, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
               }

               ClientEventHandler.STYLE.bindTexture("loto");
               if(mouseX >= this.guiLeft + 359 && mouseX <= this.guiLeft + 359 + 10 && mouseY >= this.guiTop + 67 && mouseY <= this.guiTop + 67 + 18) {
                  ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 359), (float)(this.guiTop + 67), (float)(442 * GUI_SCALE), (float)(29 * GUI_SCALE), 10 * GUI_SCALE, 18 * GUI_SCALE, 10, 18, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
                  this.hoveredAction = "carousel_next";
               } else {
                  ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 359), (float)(this.guiTop + 67), (float)(430 * GUI_SCALE), (float)(29 * GUI_SCALE), 10 * GUI_SCALE, 18 * GUI_SCALE, 10, 18, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
               }
            }

            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("lottery.player.old_draws"), (float)(this.guiLeft + 25), (float)(this.guiTop + 122), 10395075, 0.5F, "left", false, "georamaSemiBold", 26);
            ClientEventHandler.STYLE.bindTexture("loto");
            if(mouseX >= this.guiLeft + 24 && mouseX <= this.guiLeft + 24 + 160 && mouseY >= this.guiTop + 130 && mouseY <= this.guiTop + 130 + 61) {
               ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 24), (float)(this.guiTop + 130), (float)(613 * GUI_SCALE), (float)(getElementYByColor(3, !lotteries_inprogress.isEmpty()?(String)((LinkedTreeMap)lotteries_inprogress.get(this.carouselLotteryIndex)).get("designColor"):"yellow") * GUI_SCALE), 160 * GUI_SCALE, 61 * GUI_SCALE, 160, 61, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
               ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 159), (float)(this.guiTop + 153), (float)(539 * GUI_SCALE), (float)(24 * GUI_SCALE), 16 * GUI_SCALE, 16 * GUI_SCALE, 16, 16, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
               this.hoveredAction = "old_draw";
            } else {
               ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 24), (float)(this.guiTop + 130), (float)(0 * GUI_SCALE), (float)(225 * GUI_SCALE), 160 * GUI_SCALE, 61 * GUI_SCALE, 160, 61, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
               ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 159), (float)(this.guiTop + 153), (float)(519 * GUI_SCALE), (float)(24 * GUI_SCALE), 16 * GUI_SCALE, 16 * GUI_SCALE, 16, 16, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
            }

            ClientEventHandler.STYLE.bindTexture("loto");
            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 45), (float)(this.guiTop + 143), (float)(471 * GUI_SCALE), (float)(411 * GUI_SCALE), 120 * GUI_SCALE, 50 * GUI_SCALE, 80, 35, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("lottery.player.my_profil"), (float)(this.guiLeft + 196), (float)(this.guiTop + 122), 10395075, 0.5F, "left", false, "georamaSemiBold", 26);
            ClientEventHandler.STYLE.bindTexture("loto");
            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 195), (float)(this.guiTop + 130), (float)(0 * GUI_SCALE), (float)(225 * GUI_SCALE), 160 * GUI_SCALE, 61 * GUI_SCALE, 160, 61, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
            if(!ClientProxy.cacheHeadPlayer.containsKey(Minecraft.func_71410_x().field_71439_g.field_71092_bJ)) {
               try {
                  ResourceLocation var27 = AbstractClientPlayer.field_110314_b;
                  var27 = AbstractClientPlayer.func_110311_f(Minecraft.func_71410_x().field_71439_g.field_71092_bJ);
                  AbstractClientPlayer.func_110304_a(var27, Minecraft.func_71410_x().field_71439_g.field_71092_bJ);
                  ClientProxy.cacheHeadPlayer.put(Minecraft.func_71410_x().field_71439_g.field_71092_bJ, var27);
               } catch (Exception var20) {
                  System.out.println(var20.getMessage());
               }
            } else {
               Minecraft.func_71410_x().field_71446_o.func_110577_a((ResourceLocation)ClientProxy.cacheHeadPlayer.get(Minecraft.func_71410_x().field_71439_g.field_71092_bJ));
               this.field_73882_e.func_110434_K().func_110577_a((ResourceLocation)ClientProxy.cacheHeadPlayer.get(Minecraft.func_71410_x().field_71439_g.field_71092_bJ));
               GUIUtils.drawScaledCustomSizeModalRect(this.guiLeft + 202 + 18, this.guiTop + 134 + 18, 8.0F, 16.0F, 8, -8, -18, -18, 64.0F, 64.0F);
            }

            float var10001 = (float)(this.guiLeft + 230);
            float var10002 = (float)(this.guiTop + 135);
            ModernGui.drawScaledStringCustomFont(Minecraft.func_71410_x().field_71439_g.field_71092_bJ, var10001, var10002, 15463162, 0.5F, "left", false, "georamaBold", 32);
            ClientEventHandler.STYLE.bindTexture("loto");
            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 202), (float)(this.guiTop + 155), (float)(((Integer)iconsX.get("trophee")).intValue() * GUI_SCALE), (float)(getElementYByColor(16, color) * GUI_SCALE), 11 * GUI_SCALE, 11 * GUI_SCALE, 11, 11, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("lottery.player.wins"), (float)(this.guiLeft + 216), (float)(this.guiTop + 156), 14342893, 0.5F, "left", false, "georamaSemiBold", 26);
            ModernGui.drawScaledStringCustomFont(((Double)data.get("player_wins")).intValue() + "", (float)(this.guiLeft + 297), (float)(this.guiTop + 156), 15463162, 0.5F, "left", false, "georamaSemiBold", 26);
            ClientEventHandler.STYLE.bindTexture("loto");
            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 202), (float)(this.guiTop + 165), (float)(((Integer)iconsX.get("ticket")).intValue() * GUI_SCALE), (float)(getElementYByColor(16, color) * GUI_SCALE), 11 * GUI_SCALE, 11 * GUI_SCALE, 11, 11, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("lottery.player.participations"), (float)(this.guiLeft + 216), (float)(this.guiTop + 167), 14342893, 0.5F, "left", false, "georamaSemiBold", 26);
            ModernGui.drawScaledStringCustomFont(((Double)data.get("player_participations")).intValue() + "", (float)(this.guiLeft + 297), (float)(this.guiTop + 167), 15463162, 0.5F, "left", false, "georamaSemiBold", 26);
            ClientEventHandler.STYLE.bindTexture("loto");
            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 202), (float)(this.guiTop + 177), (float)(((Integer)iconsX.get("money")).intValue() * GUI_SCALE), (float)(getElementYByColor(16, color) * GUI_SCALE), 11 * GUI_SCALE, 11 * GUI_SCALE, 11, 11, (float)(1024 * GUI_SCALE), (float)(1024 * GUI_SCALE), false);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("lottery.player.money_earn"), (float)(this.guiLeft + 216), (float)(this.guiTop + 178), 14342893, 0.5F, "left", false, "georamaSemiBold", 26);
            ModernGui.drawScaledStringCustomFont(((Double)data.get("player_total_money")).intValue() + "$", (float)(this.guiLeft + 297), (float)(this.guiTop + 178), 15463162, 0.5F, "left", false, "georamaSemiBold", 26);
         }
      }

      if(!tooltipToDraw.isEmpty()) {
         this.drawHoveringText(tooltipToDraw, mouseX, mouseY, this.field_73886_k);
      }

      super.func_73863_a(mouseX, mouseY, par3);
      GL11.glEnable(2896);
      RenderHelper.func_74519_b();
   }

   private float getSlideLotteries() {
      return ((ArrayList)data.get("lotteries_past")).size() > 4?(float)(-(((ArrayList)data.get("lotteries_past")).size() - 4) * 39) * this.scrollBar.getSliderValue():0.0F;
   }

   private float getSlideWinners() {
      return ((String)this.selectedLottery.get("winners")).split(",").length > 4?(float)(-(((String)this.selectedLottery.get("winners")).split(",").length - 4) * 13) * this.scrollBarWinner.getSliderValue():0.0F;
   }

   public void func_73876_c() {
      this.ticketsInput.func_73780_a();
      this.donationInput.func_73780_a();
      super.func_73876_c();
   }

   protected void func_73869_a(char par1, int par2) {
      this.ticketsInput.func_73802_a(par1, par2);
      this.donationInput.func_73802_a(par1, par2);
      super.func_73869_a(par1, par2);
   }

   public boolean func_73868_f() {
      return false;
   }

   protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
      if(mouseButton == 0) {
         if(this.hoveredAction.equals("close")) {
            this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0F, 1.0F);
            Minecraft.func_71410_x().func_71373_a((GuiScreen)null);
         } else if(this.hoveredAction.equals("return")) {
            if(this.selectedLottery == null && this.displayMode.equals("past")) {
               this.displayMode = "in_progress";
            }

            this.selectedLottery = null;
         } else if(this.hoveredAction.equals("open_lottery_past")) {
            this.selectedLottery = (LinkedTreeMap)((ArrayList)data.get("lotteries_past")).get(this.hoveredLotteryPastId);
         } else if(this.hoveredAction.equals("carousel_next")) {
            this.carouselLotteryIndex = ((ArrayList)data.get("lotteries_inprogress")).size() <= this.carouselLotteryIndex + 1?0:this.carouselLotteryIndex + 1;
         } else if(this.hoveredAction.equals("carousel_previous")) {
            this.carouselLotteryIndex = this.carouselLotteryIndex - 1 < 0?((ArrayList)data.get("lotteries_inprogress")).size() - 1:this.carouselLotteryIndex - 1;
         } else if(this.hoveredAction.equals("participate")) {
            this.ticketsInput.func_73782_a("0");
            this.donationInput.func_73782_a("0");
            this.selectedLottery = (LinkedTreeMap)((ArrayList)data.get("lotteries_inprogress")).get(this.carouselLotteryIndex);
         } else if(this.hoveredAction.equals("old_draw")) {
            this.displayMode = "past";
         } else if(this.hoveredAction.equals("buy")) {
            if(this.ticketsInput.func_73781_b().isEmpty()) {
               this.ticketsInput.func_73782_a("0");
            }

            if(this.donationInput.func_73781_b().isEmpty()) {
               this.donationInput.func_73782_a("0");
            }

            if(!FactionGUI.isNumeric(this.ticketsInput.func_73781_b(), true) || !FactionGUI.isNumeric(this.donationInput.func_73781_b(), true) || Integer.parseInt(this.ticketsInput.func_73781_b()) == 0 && Integer.parseInt(this.donationInput.func_73781_b()) == 0) {
               Minecraft.func_71410_x().field_71439_g.func_71035_c(I18n.func_135053_a("lotery.error.wrong_data"));
               return;
            }

            PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new LotoBuyPacket(((Double)this.selectedLottery.get("id")).intValue(), Integer.parseInt(this.ticketsInput.func_73781_b()), Integer.parseInt(this.donationInput.func_73781_b()))));
            this.selectedLottery = null;
            this.ticketsInput.func_73782_a("0");
            this.donationInput.func_73782_a("0");
         } else if(this.hoveredAction.equals("get_items")) {
            PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new LotoGetItemsPacket(((Double)this.selectedLottery.get("id")).intValue())));
            Minecraft.func_71410_x().func_71373_a((GuiScreen)null);
         }

         if(this.selectedLottery != null && !this.displayMode.equals("past")) {
            this.ticketsInput.func_73793_a(mouseX, mouseY, mouseButton);
            if(((String)this.selectedLottery.get("allowDonation")).equals("true")) {
               this.donationInput.func_73793_a(mouseX, mouseY, mouseButton);
            }
         }
      }

      super.func_73864_a(mouseX, mouseY, mouseButton);
   }

   protected void drawHoveringText(List par1List, int par2, int par3, FontRenderer font) {
      if(!par1List.isEmpty()) {
         GL11.glDisable('\u803a');
         RenderHelper.func_74518_a();
         GL11.glDisable(2896);
         GL11.glDisable(2929);
         int k = 0;
         Iterator iterator = par1List.iterator();

         int j1;
         while(iterator.hasNext()) {
            String i1 = (String)iterator.next();
            j1 = font.func_78256_a(i1);
            if(j1 > k) {
               k = j1;
            }
         }

         int var15 = par2 + 12;
         j1 = par3 - 12;
         int k1 = 8;
         if(par1List.size() > 1) {
            k1 += 2 + (par1List.size() - 1) * 10;
         }

         if(var15 + k > this.field_73880_f) {
            var15 -= 28 + k;
         }

         if(j1 + k1 + 6 > this.field_73881_g) {
            j1 = this.field_73881_g - k1 - 6;
         }

         this.field_73735_i = 300.0F;
         this.itemRenderer.field_77023_b = 300.0F;
         int l1 = -267386864;
         this.func_73733_a(var15 - 3, j1 - 4, var15 + k + 3, j1 - 3, l1, l1);
         this.func_73733_a(var15 - 3, j1 + k1 + 3, var15 + k + 3, j1 + k1 + 4, l1, l1);
         this.func_73733_a(var15 - 3, j1 - 3, var15 + k + 3, j1 + k1 + 3, l1, l1);
         this.func_73733_a(var15 - 4, j1 - 3, var15 - 3, j1 + k1 + 3, l1, l1);
         this.func_73733_a(var15 + k + 3, j1 - 3, var15 + k + 4, j1 + k1 + 3, l1, l1);
         int i2 = 1347420415;
         int j2 = (i2 & 16711422) >> 1 | i2 & -16777216;
         this.func_73733_a(var15 - 3, j1 - 3 + 1, var15 - 3 + 1, j1 + k1 + 3 - 1, i2, j2);
         this.func_73733_a(var15 + k + 2, j1 - 3 + 1, var15 + k + 3, j1 + k1 + 3 - 1, i2, j2);
         this.func_73733_a(var15 - 3, j1 - 3, var15 + k + 3, j1 - 3 + 1, i2, i2);
         this.func_73733_a(var15 - 3, j1 + k1 + 2, var15 + k + 3, j1 + k1 + 3, j2, j2);

         for(int k2 = 0; k2 < par1List.size(); ++k2) {
            String s1 = (String)par1List.get(k2);
            font.func_78261_a(s1, var15, j1, -1);
            if(k2 == 0) {
               j1 += 2;
            }

            j1 += 10;
         }

         this.field_73735_i = 0.0F;
         this.itemRenderer.field_77023_b = 0.0F;
         GL11.glDisable(2896);
         GL11.glDisable(2929);
         GL11.glEnable('\u803a');
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      }

   }

}
