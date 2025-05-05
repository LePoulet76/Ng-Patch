package net.ilexiconn.nationsgui.forge.client.gui.faction;

import cpw.mods.fml.common.network.PacketDispatcher;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScrollBarGeneric;
import net.ilexiconn.nationsgui.forge.client.gui.faction.ActionsListGui;
import net.ilexiconn.nationsgui.forge.client.gui.faction.BankGUI$1;
import net.ilexiconn.nationsgui.forge.client.gui.faction.BankGUI$2;
import net.ilexiconn.nationsgui.forge.client.gui.faction.BuyActionConfirmGui;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionGUI;
import net.ilexiconn.nationsgui.forge.client.gui.faction.ProfilGui;
import net.ilexiconn.nationsgui.forge.client.gui.faction.TabbedFactionGUI;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionActionsDataPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionBankActionPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionBankDataPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionLockActionPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.RemoteOpenFactionChestPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class BankGUI extends TabbedFactionGUI {

   public static boolean loaded = false;
   public static int lastBalance = -1;
   public static long lastBalanceAnimation = -1L;
   public static HashMap<String, Object> factionBankInfos;
   public static HashMap<String, Object> factionActionsInfos;
   public static ArrayList<HashMap<String, String>> cachedLogs = new ArrayList();
   private ArrayList<HashMap<String, String>> cachedDividendesLogs = new ArrayList();
   private ArrayList<String> cachedMembers = new ArrayList();
   private GuiScrollBarGeneric scrollBarLogs;
   private GuiScrollBarGeneric scrollBarMembers;
   private GuiScrollBarGeneric scrollBarTotalDividendes;
   private GuiScrollBarGeneric scrollBarLogsDividendes;
   private GuiTextField amountInput;
   public static List<String> flagCoords = Arrays.asList(new String[]{"306,51", "324,70", "324,90", "306,109", "283,109", "266,90", "266,70", "283,51"});
   public static List<String> lockCoords = Arrays.asList(new String[]{"311,52", "328,70", "328,93", "311,109", "289,109", "272,94", "272,71", "289,52"});
   private int hoveredIndex;
   private String hoveredOwnerFactionId;
   private String hoveredStatus;
   private boolean hoveringStatus;
   private boolean hoveringFlag;
   private String hoveredPlayer;
   public static HashMap<String, Integer> blockSolde = new BankGUI$1();
   public static HashMap<String, Integer> blockActions = new BankGUI$2();


   public BankGUI() {
      loaded = false;
      PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new FactionBankDataPacket((String)FactionGUI.factionInfos.get("id"))));
      PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new FactionActionsDataPacket((String)FactionGUI.factionInfos.get("name"))));
   }

   public void func_73876_c() {
      this.amountInput.func_73780_a();
   }

   public void func_73866_w_() {
      super.func_73866_w_();
      cachedLogs = new ArrayList();
      this.cachedDividendesLogs = new ArrayList();
      this.cachedMembers = new ArrayList();
      this.scrollBarMembers = new GuiScrollBarGeneric((float)(this.guiLeft + 128), (float)(this.guiTop + 120), 97, new ResourceLocation("nationsgui", "textures/gui/generic_ingame/cursor_faction.png"), 2, 8);
      this.scrollBarLogs = new GuiScrollBarGeneric((float)(this.guiLeft + 226), (float)(this.guiTop + 120), 97, new ResourceLocation("nationsgui", "textures/gui/generic_ingame/cursor_faction.png"), 2, 8);
      this.scrollBarLogs.setScrollIncrement(0.005F);
      this.scrollBarTotalDividendes = new GuiScrollBarGeneric((float)(this.guiLeft + 343), (float)(this.guiTop + 163), 54, new ResourceLocation("nationsgui", "textures/gui/generic_ingame/cursor_faction.png"), 2, 8);
      this.scrollBarLogsDividendes = new GuiScrollBarGeneric((float)(this.guiLeft + 446), (float)(this.guiTop + 163), 54, new ResourceLocation("nationsgui", "textures/gui/generic_ingame/cursor_faction.png"), 2, 8);
      this.amountInput = new GuiTextField(this.field_73886_k, this.guiLeft + 143, this.guiTop + 38, 79, 10);
      this.amountInput.func_73786_a(false);
      this.amountInput.func_73804_f(8);
      this.amountInput.func_73782_a("0");
   }

   public void func_73863_a(int mouseX, int mouseY, float partialTick) {
      this.func_73873_v_();
      tooltipToDraw.clear();
      this.hoveredIndex = -1;
      this.hoveredOwnerFactionId = null;
      this.hoveredStatus = null;
      this.hoveringStatus = false;
      this.hoveringFlag = false;
      this.hoveredAction = "";
      this.hoveredPlayer = "";
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      ClientEventHandler.STYLE.bindTexture("faction_global_2");
      ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 30), (float)(this.guiTop + 0), (float)(0 * GUI_SCALE), (float)(0 * GUI_SCALE), (this.xSize - 30) * GUI_SCALE, this.ySize * GUI_SCALE, this.xSize - 30, this.ySize, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
      ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 242), (float)(this.guiTop + 0), (float)(0 * GUI_SCALE), (float)(240 * GUI_SCALE), (this.xSize - 242) * GUI_SCALE, this.ySize * GUI_SCALE, this.xSize - 242, this.ySize, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
      if(loaded && factionBankInfos != null && factionActionsInfos != null) {
         ModernGui.drawScaledStringCustomFont((String)FactionGUI.factionInfos.get("name"), (float)(this.guiLeft + 43), (float)(this.guiTop + 6), 10395075, 0.5F, "left", false, "georamaMedium", 32);
         ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("faction.bank.title.bank"), (float)(this.guiLeft + 43), (float)(this.guiTop + 16), 16777215, 0.75F, "left", false, "georamaSemiBold", 32);
         ClientEventHandler.STYLE.bindTexture("faction_bank");
         ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 42), (float)(this.guiTop + 32), (float)(100 * GUI_SCALE), (float)(25 * GUI_SCALE), 189 * GUI_SCALE, 32 * GUI_SCALE, 189, 32, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
         ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 42), (float)(this.guiTop + 32), (float)(100 * GUI_SCALE), (float)(((Integer)blockSolde.get((String)FactionGUI.factionInfos.get("actualRelation"))).intValue() * GUI_SCALE), 91 * GUI_SCALE, 32 * GUI_SCALE, 91, 32, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
         ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 51), (float)(this.guiTop + 40), (float)(100 * GUI_SCALE), (float)(0 * GUI_SCALE), 18 * GUI_SCALE, 17 * GUI_SCALE, 18, 17, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
         ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("faction.bank.balance"), (float)(this.guiLeft + 77), (float)(this.guiTop + 40), 16777215, 0.5F, "left", false, "georamaRegular", 25);
         int balance = ((Double)factionBankInfos.get("balance")).intValue();
         if(lastBalance != -1 && System.currentTimeMillis() - lastBalanceAnimation < 1000L) {
            double owners = ((Double)factionBankInfos.get("balance")).doubleValue() - (double)lastBalance;
            Double actionsTotals = Double.valueOf((double)(System.currentTimeMillis() - lastBalanceAnimation) / 1000.0D * owners);
            balance = lastBalance + actionsTotals.intValue();
         } else {
            lastBalance = balance;
         }

         ModernGui.drawScaledStringCustomFont(balance + "$", (float)(this.guiLeft + 77), (float)(this.guiTop + 47), 16777215, 0.75F, "left", false, "georamaSemiBold", 25);
         ClientEventHandler.STYLE.bindTexture("faction_bank");
         ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 140), (float)(this.guiTop + 36), (float)(200 * GUI_SCALE), (float)(193 * GUI_SCALE), 85 * GUI_SCALE, 11 * GUI_SCALE, 85, 11, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
         this.amountInput.func_73795_f();
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         ClientEventHandler.STYLE.bindTexture("faction_bank");
         if(mouseX >= this.guiLeft + 140 && mouseX <= this.guiLeft + 140 + 41 && mouseY >= this.guiTop + 51 && mouseY <= this.guiTop + 51 + 9) {
            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 140), (float)(this.guiTop + 51), (float)(200 * GUI_SCALE), (float)(219 * GUI_SCALE), 41 * GUI_SCALE, 9 * GUI_SCALE, 41, 9, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("faction.bank.action.deposit"), (float)(this.guiLeft + 140 + 20), (float)(this.guiTop + 53), 2826561, 0.5F, "center", false, "georamaSemiBold", 24);
            this.hoveredAction = "deposit";
         } else {
            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 140), (float)(this.guiTop + 51), (float)(200 * GUI_SCALE), (float)(208 * GUI_SCALE), 41 * GUI_SCALE, 9 * GUI_SCALE, 41, 9, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("faction.bank.action.deposit"), (float)(this.guiLeft + 140 + 20), (float)(this.guiTop + 53), 2826561, 0.5F, "center", false, "georamaSemiBold", 24);
         }

         ClientEventHandler.STYLE.bindTexture("faction_bank");
         if(!((Boolean)factionBankInfos.get("playerIsMember")).booleanValue()) {
            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 184), (float)(this.guiTop + 51), (float)(244 * GUI_SCALE), (float)(219 * GUI_SCALE), 41 * GUI_SCALE, 9 * GUI_SCALE, 41, 9, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("faction.bank.action.withdraw"), (float)(this.guiLeft + 184 + 20), (float)(this.guiTop + 53), 3682124, 0.5F, "center", false, "georamaSemiBold", 24);
         } else if(mouseX >= this.guiLeft + 184 && mouseX <= this.guiLeft + 184 + 41 && mouseY >= this.guiTop + 51 && mouseY <= this.guiTop + 51 + 9) {
            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 184), (float)(this.guiTop + 51), (float)(200 * GUI_SCALE), (float)(219 * GUI_SCALE), 41 * GUI_SCALE, 9 * GUI_SCALE, 41, 9, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("faction.bank.action.withdraw"), (float)(this.guiLeft + 184 + 20), (float)(this.guiTop + 53), 2826561, 0.5F, "center", false, "georamaSemiBold", 24);
            this.hoveredAction = "withdraw";
         } else {
            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 184), (float)(this.guiTop + 51), (float)(244 * GUI_SCALE), (float)(208 * GUI_SCALE), 41 * GUI_SCALE, 9 * GUI_SCALE, 41, 9, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("faction.bank.action.withdraw"), (float)(this.guiLeft + 184 + 20), (float)(this.guiTop + 53), 15463162, 0.5F, "center", false, "georamaSemiBold", 24);
         }

         ClientEventHandler.STYLE.bindTexture("faction_bank");
         ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 42), (float)(this.guiTop + 66), (float)(100 * GUI_SCALE), (float)(25 * GUI_SCALE), 189 * GUI_SCALE, 32 * GUI_SCALE, 189, 32, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
         ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 50), (float)(this.guiTop + 76), (float)(125 * GUI_SCALE), (float)(3 * GUI_SCALE), 21 * GUI_SCALE, 15 * GUI_SCALE, 21, 15, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
         ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("faction.bank.chest"), (float)(this.guiLeft + 77), (float)(this.guiTop + 74), 16777215, 0.5F, "left", false, "georamaRegular", 25);
         ModernGui.drawScaledStringCustomFont((String)FactionGUI.factionInfos.get("chestLevel") + " " + I18n.func_135053_a("faction.bank.chest.line"), (float)(this.guiLeft + 77), (float)(this.guiTop + 81), 16777215, 0.75F, "left", false, "georamaSemiBold", 25);
         ClientEventHandler.STYLE.bindTexture("faction_bank");
         if(!((Boolean)FactionGUI.factionInfos.get("canOpenChest")).booleanValue()) {
            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 140), (float)(this.guiTop + 79), (float)(200 * GUI_SCALE), (float)(154 * GUI_SCALE), 85 * GUI_SCALE, 9 * GUI_SCALE, 85, 9, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("faction.bank.action.open_chest"), (float)(this.guiLeft + 140 + 42), (float)(this.guiTop + 81), 3682124, 0.5F, "center", false, "georamaSemiBold", 24);
         } else if(mouseX >= this.guiLeft + 140 && mouseX <= this.guiLeft + 140 + 85 && mouseY >= this.guiTop + 79 && mouseY <= this.guiTop + 79 + 9) {
            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 140), (float)(this.guiTop + 79), (float)(200 * GUI_SCALE), (float)(167 * GUI_SCALE), 85 * GUI_SCALE, 9 * GUI_SCALE, 85, 9, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("faction.bank.action.open_chest"), (float)(this.guiLeft + 140 + 42), (float)(this.guiTop + 81), 2826561, 0.5F, "center", false, "georamaSemiBold", 24);
            this.hoveredAction = "open_chest";
         } else {
            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 140), (float)(this.guiTop + 79), (float)(200 * GUI_SCALE), (float)(180 * GUI_SCALE), 85 * GUI_SCALE, 9 * GUI_SCALE, 85, 9, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("faction.bank.action.open_chest"), (float)(this.guiLeft + 140 + 42), (float)(this.guiTop + 81), 15463162, 0.5F, "center", false, "georamaSemiBold", 24);
         }

         int var28;
         String var31;
         if(this.cachedMembers.isEmpty() && ((ArrayList)factionBankInfos.get("members")).size() > 0) {
            for(var28 = 0; var28 < ((ArrayList)factionBankInfos.get("members")).size(); ++var28) {
               String status = ((String)((ArrayList)factionBankInfos.get("members")).get(var28)).split("#")[1];
               var31 = ((String)((ArrayList)factionBankInfos.get("members")).get(var28)).split("#")[0];
               this.cachedMembers.add("[" + var31.substring(0, 1).toUpperCase() + "] " + status);
            }
         }

         ClientEventHandler.STYLE.bindTexture("faction_bank");
         ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 42), (float)(this.guiTop + 105), (float)(200 * GUI_SCALE), (float)(300 * GUI_SCALE), 91 * GUI_SCALE, 116 * GUI_SCALE, 91, 116, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
         ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("faction.bank.members"), (float)(this.guiLeft + 49), (float)(this.guiTop + 111), 16777215, 0.5F, "left", false, "georamaMedium", 30);
         ClientEventHandler.STYLE.bindTexture("faction_bank");
         ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 128), (float)(this.guiTop + 120), (float)(316 * GUI_SCALE), (float)(115 * GUI_SCALE), 2 * GUI_SCALE, 97 * GUI_SCALE, 2, 97, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
         GUIUtils.startGLScissor(this.guiLeft + 49, this.guiTop + 126, 80, 87);

         String l;
         int var29;
         Float var33;
         for(var28 = 0; var28 < this.cachedMembers.size(); ++var28) {
            var29 = this.guiLeft + 49;
            var33 = Float.valueOf((float)(this.guiTop + 126 + var28 * 13) + this.getSlideMembers());
            l = ((String)this.cachedMembers.get(var28)).split(" ")[1];
            if(!ClientProxy.cacheHeadPlayer.containsKey(l)) {
               try {
                  ResourceLocation offsetX = AbstractClientPlayer.field_110314_b;
                  offsetX = AbstractClientPlayer.func_110311_f(l);
                  AbstractClientPlayer.func_110304_a(offsetX, l);
                  ClientProxy.cacheHeadPlayer.put(l, offsetX);
               } catch (Exception var27) {
                  System.out.println(var27.getMessage());
               }
            } else {
               Minecraft.func_71410_x().field_71446_o.func_110577_a((ResourceLocation)ClientProxy.cacheHeadPlayer.get(l));
               this.field_73882_e.func_110434_K().func_110577_a((ResourceLocation)ClientProxy.cacheHeadPlayer.get(l));
               GUIUtils.drawScaledCustomSizeModalRect(var29 + 10, var33.intValue() + 10, 8.0F, 16.0F, 8, -8, -10, -10, 64.0F, 64.0F);
            }

            ModernGui.drawScaledStringCustomFont((String)this.cachedMembers.get(var28), (float)(var29 + 13), (float)(var33.intValue() + 2), 16777215, 0.5F, "left", false, "georamaMedium", 25);
            if(mouseX > var29 && mouseX < var29 + 80 && (float)mouseY > var33.floatValue() && (float)mouseY < var33.floatValue() + 13.0F) {
               this.hoveredPlayer = (String)this.cachedMembers.get(var28);
            }
         }

         GUIUtils.endGLScissor();
         if(mouseX > this.guiLeft + 42 && mouseX < this.guiLeft + 42 + 91 && mouseY >= this.guiTop + 105 && mouseY <= this.guiTop + 105 + 116) {
            this.scrollBarMembers.draw(mouseX, mouseY);
         }

         ClientEventHandler.STYLE.bindTexture("faction_bank");
         ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 140), (float)(this.guiTop + 105), (float)(200 * GUI_SCALE), (float)(300 * GUI_SCALE), 91 * GUI_SCALE, 116 * GUI_SCALE, 91, 116, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
         ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("faction.bank.transactions"), (float)(this.guiLeft + 146), (float)(this.guiTop + 111), 16777215, 0.5F, "left", false, "georamaMedium", 30);
         String line;
         if(FactionGUI.hasPermissions("bank_log")) {
            ClientEventHandler.STYLE.bindTexture("faction_bank");
            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 226), (float)(this.guiTop + 120), (float)(316 * GUI_SCALE), (float)(115 * GUI_SCALE), 2 * GUI_SCALE, 97 * GUI_SCALE, 2, 97, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
            if(factionBankInfos.get("logs") != null && ((ArrayList)factionBankInfos.get("logs")).size() > 0 && cachedLogs.size() == 0) {
               for(var28 = 0; var28 < ((ArrayList)factionBankInfos.get("logs")).size(); ++var28) {
                  HashMap var30 = new HashMap();
                  var31 = (String)((ArrayList)factionBankInfos.get("logs")).get(var28);
                  if(var31.split("#").length == 3) {
                     l = var31.split("#")[0];
                     if(l.contains("-")) {
                        l = l.replace("-", "\u00a74-\u00a7f ");
                     } else {
                        l = "\u00a7a+\u00a7f " + l;
                     }

                     l = l + "$";
                     Long var37 = Long.valueOf(Long.parseLong(var31.split("#")[1]));
                     String offsetY = ModernGui.formatDelayTime(var37);
                     line = var31.split("#")[2];
                     var30.put("amount", l);
                     var30.put("date", offsetY);
                     var30.put("playerName", line);
                     cachedLogs.add(var30);
                  }
               }
            }

            GUIUtils.startGLScissor(this.guiLeft + 146, this.guiTop + 126, 80, 87);

            for(var28 = 0; var28 < cachedLogs.size(); ++var28) {
               var29 = this.guiLeft + 146;
               var33 = Float.valueOf((float)(this.guiTop + 126 + var28 * 18) + this.getSlideLogs());
               ModernGui.drawScaledStringCustomFont((String)((HashMap)cachedLogs.get(var28)).get("playerName"), (float)var29, (float)var33.intValue(), 10395075, 0.5F, "left", false, "georamaMedium", 20);
               ModernGui.drawScaledStringCustomFont((String)((HashMap)cachedLogs.get(var28)).get("amount"), (float)var29, (float)(var33.intValue() + 6), 16777215, 0.5F, "left", false, "georamaMedium", 28);
               ModernGui.drawScaledStringCustomFont((String)((HashMap)cachedLogs.get(var28)).get("date"), (float)(var29 + 77), (float)(var33.intValue() + 7), 10395075, 0.5F, "right", false, "georamaMedium", 20);
            }

            GUIUtils.endGLScissor();
            if(mouseX > this.guiLeft + 140 && mouseX < this.guiLeft + 140 + 91 && mouseY >= this.guiTop + 105 && mouseY <= this.guiTop + 105 + 116) {
               this.scrollBarLogs.draw(mouseX, mouseY);
            }
         }

         ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("faction.bank.title.actions"), (float)(this.guiLeft + 253), (float)(this.guiTop + 14), 16777215, 0.75F, "left", false, "georamaSemiBold", 32);
         ClientEventHandler.STYLE.bindTexture("faction_bank");
         ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 253), (float)(this.guiTop + 32), (float)(295 * GUI_SCALE), (float)(0 * GUI_SCALE), 102 * GUI_SCALE, 107 * GUI_SCALE, 102, 107, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
         ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 259), (float)(this.guiTop + 41), (float)(403 * GUI_SCALE), (float)(85 * GUI_SCALE), 89 * GUI_SCALE, 89 * GUI_SCALE, 89, 89, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
         ArrayList var34 = (ArrayList)factionActionsInfos.get("owners");
         ArrayList var32 = (ArrayList)factionActionsInfos.get("status");

         long diff;
         int var38;
         long var47;
         for(int var35 = 0; var35 < var34.size(); ++var35) {
            l = (String)var34.get(var35);
            var38 = Integer.parseInt(((String)flagCoords.get(var35)).split(",")[0]);
            int var39 = Integer.parseInt(((String)flagCoords.get(var35)).split(",")[1]);
            int var44 = var38 + 5;
            int amount = var39 + 1;
            String time;
            BufferedImage now;
            if(!FactionGUI.isNumeric(l, true) && !l.equals(FactionGUI.factionInfos.get("id"))) {
               if(!ClientProxy.flagsTexture.containsKey(l)) {
                  time = (String)((ArrayList)factionActionsInfos.get("flags")).get(var35);
                  if(!time.equals("null")) {
                     now = ModernGui.decodeToImage(time);
                     ClientProxy.flagsTexture.put(l, new DynamicTexture(now));
                  }
               }

               if(ClientProxy.flagsTexture.containsKey(l)) {
                  GL11.glBindTexture(3553, ((DynamicTexture)ClientProxy.flagsTexture.get(l)).func_110552_b());
                  ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + var38), (float)(this.guiTop + var39), 0.0F, 0.0F, 156, 78, 17, 10, 156.0F, 78.0F, false);
               }
            } else if(!((String)var32.get(var35)).equals("unlocked")) {
               if(!ClientProxy.flagsTexture.containsKey((String)FactionGUI.factionInfos.get("id"))) {
                  time = (String)FactionGUI.factionInfos.get("flagImage");
                  now = ModernGui.decodeToImage(time);
                  ClientProxy.flagsTexture.put((String)FactionGUI.factionInfos.get("id"), new DynamicTexture(now));
               }

               if(ClientProxy.flagsTexture.containsKey((String)FactionGUI.factionInfos.get("id"))) {
                  GL11.glBindTexture(3553, ((DynamicTexture)ClientProxy.flagsTexture.get((String)FactionGUI.factionInfos.get("id"))).func_110552_b());
                  ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + var38), (float)(this.guiTop + var39), 0.0F, 0.0F, 156, 78, 17, 10, 156.0F, 78.0F, false);
               }
            }

            ClientEventHandler.STYLE.bindTexture("faction_bank");
            if(((String)var32.get(var35)).equals("unlocked")) {
               if(mouseX >= this.guiLeft + var38 && mouseX <= this.guiLeft + var38 + 17 && mouseY >= this.guiTop + var39 && mouseY <= this.guiTop + var39 + 10) {
                  ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + var44), (float)(this.guiTop + amount), (float)(183 * GUI_SCALE), (float)(6 * GUI_SCALE), 7 * GUI_SCALE, 8 * GUI_SCALE, 7, 8, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
               } else {
                  ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + var44), (float)(this.guiTop + amount), (float)(172 * GUI_SCALE), (float)(6 * GUI_SCALE), 7 * GUI_SCALE, 8 * GUI_SCALE, 7, 8, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
               }
            } else {
               ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + var44), (float)(this.guiTop + amount), (float)(161 * GUI_SCALE), (float)(6 * GUI_SCALE), 7 * GUI_SCALE, 8 * GUI_SCALE, 7, 8, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
            }

            if(mouseX >= this.guiLeft + var38 && mouseX <= this.guiLeft + var38 + 17 && mouseY >= this.guiTop + var39 && mouseY <= this.guiTop + var39 + 10) {
               if(FactionGUI.hasPermissions("actions") && (((String)FactionGUI.factionInfos.get("playerWhoSeeFactionId")).equals(l) || FactionGUI.isNumeric(l, true) && ((Boolean)FactionGUI.factionInfos.get("isInCountry")).booleanValue())) {
                  tooltipToDraw.add(I18n.func_135053_a("faction.actions.label.change." + (String)var32.get(var35)));
                  this.hoveredIndex = var35;
                  this.hoveredOwnerFactionId = l;
                  this.hoveredStatus = (String)var32.get(var35);
                  this.hoveringStatus = true;
               } else {
                  time = !FactionGUI.isNumeric(l, true)?(String)((ArrayList)factionActionsInfos.get("factionsName")).get(var35):(String)FactionGUI.factionInfos.get("nameColored");
                  tooltipToDraw.add(time);
                  tooltipToDraw.add(I18n.func_135053_a("faction.actions.label.status") + ": " + I18n.func_135053_a("faction.actions.status." + (String)var32.get(var35)));
                  tooltipToDraw.add(I18n.func_135053_a("faction.actions.label.price") + ": \u00a78" + factionActionsInfos.get("price") + "$");
                  if(!((String)var32.get(var35)).equals("locked") && !((String)FactionGUI.factionInfos.get("playerWhoSeeFactionId")).equals(this.hoveredOwnerFactionId) && (!FactionGUI.isNumeric(l, true) || !((Boolean)FactionGUI.factionInfos.get("isInCountry")).booleanValue())) {
                     this.hoveredIndex = var35;
                     this.hoveredOwnerFactionId = l;
                     this.hoveringFlag = true;
                     ClientEventHandler.STYLE.bindTexture("faction_bank");
                     if(FactionGUI.hasPermissions("actions")) {
                        var47 = System.currentTimeMillis() - Long.parseLong((String)factionActionsInfos.get("lastActionFromBuyer"));
                        diff = 18000000L - var47;
                        if(diff > 0L) {
                           long date = diff / 60000L;
                           tooltipToDraw.add("");
                           tooltipToDraw.add(I18n.func_135053_a("faction.actions.label.cooldown") + " \u00a74" + date + " minutes");
                        } else {
                           tooltipToDraw.add("");
                           tooltipToDraw.add(I18n.func_135053_a("faction.actions.cta.buy"));
                        }
                     }
                  }
               }
            }
         }

         ClientEventHandler.STYLE.bindTexture("faction_bank");
         ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 355), (float)(this.guiTop + 32), (float)(0 * GUI_SCALE), (float)(((Integer)blockActions.get((String)FactionGUI.factionInfos.get("actualRelation"))).intValue() * GUI_SCALE), 96 * GUI_SCALE, 107 * GUI_SCALE, 96, 107, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
         ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("faction.actions.total_dividendes"), (float)(this.guiLeft + 359), (float)(this.guiTop + 40), 16777215, 0.5F, "left", false, "georamaRegular", 25);
         ModernGui.drawScaledStringCustomFont(factionActionsInfos.get("totalDividendes") + "$", (float)(this.guiLeft + 359), (float)(this.guiTop + 47), 16777215, 0.75F, "left", false, "georamaSemiBold", 25);
         ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("faction.actions.available"), (float)(this.guiLeft + 359), (float)(this.guiTop + 65), 16777215, 0.5F, "left", false, "georamaRegular", 25);
         ModernGui.drawScaledStringCustomFont(factionActionsInfos.get("availableActions") + " action(s)", (float)(this.guiLeft + 359), (float)(this.guiTop + 72), 16777215, 0.75F, "left", false, "georamaSemiBold", 25);
         ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("faction.actions.price"), (float)(this.guiLeft + 359), (float)(this.guiTop + 90), 16777215, 0.5F, "left", false, "georamaRegular", 25);
         ModernGui.drawScaledStringCustomFont(factionActionsInfos.get("price") + "$", (float)(this.guiLeft + 359), (float)(this.guiTop + 97), 16777215, 0.75F, "left", false, "georamaSemiBold", 25);
         ClientEventHandler.STYLE.bindTexture("faction_bank");
         if(mouseX >= this.guiLeft + 360 && mouseX <= this.guiLeft + 360 + 85 && mouseY >= this.guiTop + 115 && mouseY <= this.guiTop + 115 + 9) {
            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 360), (float)(this.guiTop + 115), (float)(200 * GUI_SCALE), (float)(167 * GUI_SCALE), 85 * GUI_SCALE, 9 * GUI_SCALE, 85, 9, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("faction.bank.owned_actions"), (float)(this.guiLeft + 360 + 43), (float)(this.guiTop + 117), 2234425, 0.5F, "center", false, "georamaMedium", 25);
            this.hoveredAction = "see_owned_actions";
         } else {
            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 360), (float)(this.guiTop + 115), (float)(200 * GUI_SCALE), (float)(180 * GUI_SCALE), 85 * GUI_SCALE, 9 * GUI_SCALE, 85, 9, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("faction.bank.owned_actions"), (float)(this.guiLeft + 360 + 43), (float)(this.guiTop + 117), 16777215, 0.5F, "center", false, "georamaMedium", 25);
         }

         ModernGui.drawScaledStringCustomFont("\u00a7o" + I18n.func_135053_a("faction.actions.how_to"), (float)(this.guiLeft + 359), (float)(this.guiTop + 130), 16777215, 0.5F, "left", false, "georamaRegular", 25);
         if(mouseX > this.guiLeft + 359 && mouseX < this.guiLeft + 359 + 60 && mouseY > this.guiTop + 130 && mouseY < this.guiTop + 130 + 5) {
            tooltipToDraw.add(I18n.func_135053_a("faction.actions.help0"));
            tooltipToDraw.add("");
            tooltipToDraw.add(I18n.func_135053_a("faction.actions.help1"));
            tooltipToDraw.add(I18n.func_135053_a("faction.actions.help2"));
            tooltipToDraw.add(I18n.func_135053_a("faction.actions.help3"));
            tooltipToDraw.add("");
            tooltipToDraw.add(I18n.func_135053_a("faction.actions.help4"));
            tooltipToDraw.add(I18n.func_135053_a("faction.actions.help5"));
            tooltipToDraw.add(I18n.func_135053_a("faction.actions.help6"));
            tooltipToDraw.add("");
            tooltipToDraw.add(I18n.func_135053_a("faction.actions.help7"));
            tooltipToDraw.add(I18n.func_135053_a("faction.actions.help8"));
            tooltipToDraw.add(I18n.func_135053_a("faction.actions.help9"));
            tooltipToDraw.add("");
            tooltipToDraw.add(I18n.func_135053_a("faction.actions.help10"));
            tooltipToDraw.add(I18n.func_135053_a("faction.actions.help11"));
            tooltipToDraw.add(I18n.func_135053_a("faction.actions.help12"));
         }

         ClientEventHandler.STYLE.bindTexture("faction_bank");
         ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 253), (float)(this.guiTop + 146), (float)(401 * GUI_SCALE), (float)(0 * GUI_SCALE), 95 * GUI_SCALE, 75 * GUI_SCALE, 95, 75, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
         ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("faction.actions.title.top"), (float)(this.guiLeft + 260), (float)(this.guiTop + 151), 16777215, 0.5F, "left", false, "georamaMedium", 30);
         ClientEventHandler.STYLE.bindTexture("faction_bank");
         ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 343), (float)(this.guiTop + 163), (float)(309 * GUI_SCALE), (float)(115 * GUI_SCALE), 2 * GUI_SCALE, 54 * GUI_SCALE, 2, 54, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
         ArrayList var36 = (ArrayList)factionActionsInfos.get("totals");
         GUIUtils.startGLScissor(this.guiLeft + 259, this.guiTop + 162, 81, 57);

         int var40;
         Float var41;
         for(var40 = 0; var40 < var36.size(); ++var40) {
            var38 = this.guiLeft + 259;
            var41 = Float.valueOf((float)(this.guiTop + 162 + var40 * 10) + this.getSlideTotalDividendes());
            ModernGui.drawScaledStringCustomFont(((String)var36.get(var40)).split("##")[0], (float)var38, (float)var41.intValue(), 10395075, 0.5F, "left", false, "georamaMedium", 22);
            ModernGui.drawScaledStringCustomFont(((String)var36.get(var40)).split("##")[1] + "$", (float)(var38 + 81), (float)var41.intValue(), 16777215, 0.5F, "right", false, "georamaMedium", 22);
         }

         GUIUtils.endGLScissor();
         if(mouseX > this.guiLeft + 253 && mouseX < this.guiLeft + 253 + 95 && mouseY > this.guiTop + 146 && mouseY < this.guiTop + 146 + 75) {
            this.scrollBarTotalDividendes.draw(mouseX, mouseY);
         }

         ClientEventHandler.STYLE.bindTexture("faction_bank");
         ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 356), (float)(this.guiTop + 146), (float)(401 * GUI_SCALE), (float)(0 * GUI_SCALE), 95 * GUI_SCALE, 75 * GUI_SCALE, 95, 75, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
         ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("faction.actions.title.logs"), (float)(this.guiLeft + 363), (float)(this.guiTop + 151), 16777215, 0.5F, "left", false, "georamaMedium", 30);
         ClientEventHandler.STYLE.bindTexture("faction_bank");
         ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 446), (float)(this.guiTop + 163), (float)(309 * GUI_SCALE), (float)(115 * GUI_SCALE), 2 * GUI_SCALE, 54 * GUI_SCALE, 2, 54, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
         if(this.cachedDividendesLogs.size() == 0) {
            ArrayList var42 = (ArrayList)factionActionsInfos.get("logs");

            for(var38 = 0; var38 < var42.size(); ++var38) {
               HashMap var43 = new HashMap();
               line = (String)var42.get(var38);
               String var45 = line.split("##")[1];
               var45 = "\u00a7a+\u00a7f" + var45;
               var45 = var45 + "$";
               Long var46 = Long.valueOf(Long.parseLong(line.split("##")[2]));
               var47 = System.currentTimeMillis();
               diff = var47 - var46.longValue();
               String var48 = "";
               long days = diff / 86400000L;
               long hours = 0L;
               long minutes = 0L;
               long seconds = 0L;
               if(days == 0L) {
                  hours = diff / 3600000L;
                  if(hours == 0L) {
                     minutes = diff / 60000L;
                     if(minutes == 0L) {
                        seconds = diff / 1000L;
                        var48 = var48 + " " + seconds + " " + I18n.func_135053_a("faction.common.seconds.short");
                     } else {
                        var48 = var48 + " " + minutes + " " + I18n.func_135053_a("faction.common.minutes.short");
                     }
                  } else {
                     var48 = var48 + " " + hours + " " + I18n.func_135053_a("faction.common.hours.short");
                  }
               } else {
                  var48 = var48 + " " + days + " " + I18n.func_135053_a("faction.common.days.short");
               }

               var43.put("amount", var45);
               var43.put("date", var48);
               var43.put("factionName", line.split("##")[0]);
               this.cachedDividendesLogs.add(var43);
            }
         }

         GUIUtils.startGLScissor(this.guiLeft + 362, this.guiTop + 162, 81, 57);

         for(var40 = 0; var40 < this.cachedDividendesLogs.size(); ++var40) {
            var38 = this.guiLeft + 362;
            var41 = Float.valueOf((float)(this.guiTop + 162 + var40 * 19) + this.getSlideLogsDividendes());
            ModernGui.drawScaledStringCustomFont((String)((HashMap)this.cachedDividendesLogs.get(var40)).get("factionName"), (float)var38, (float)var41.intValue(), 10395075, 0.5F, "left", false, "georamaMedium", 20);
            ModernGui.drawScaledStringCustomFont((String)((HashMap)this.cachedDividendesLogs.get(var40)).get("amount"), (float)var38, (float)(var41.intValue() + 6), 16777215, 0.5F, "left", false, "georamaMedium", 28);
            ModernGui.drawScaledStringCustomFont((String)((HashMap)this.cachedDividendesLogs.get(var40)).get("date"), (float)(var38 + 81), (float)(var41.intValue() + 7), 10395075, 0.5F, "right", false, "georamaMedium", 20);
         }

         GUIUtils.endGLScissor();
         if(mouseX > this.guiLeft + 356 && mouseX < this.guiLeft + 356 + 95 && mouseY > this.guiTop + 146 && mouseY < this.guiTop + 146 + 75) {
            this.scrollBarLogsDividendes.draw(mouseX, mouseY);
         }
      }

      if(tooltipToDraw != null && !tooltipToDraw.isEmpty()) {
         this.drawHoveringText(tooltipToDraw, mouseX, mouseY, this.field_73886_k);
      }

      super.func_73863_a(mouseX, mouseY, partialTick);
   }

   public void drawScreen(int mouseX, int mouseY) {}

   protected void func_73869_a(char typedChar, int keyCode) {
      this.amountInput.func_73802_a(typedChar, keyCode);
      super.func_73869_a(typedChar, keyCode);
   }

   protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
      if(mouseButton == 0 && FactionGUI.factionInfos != null) {
         if(this.hoveredIndex != -1 && this.hoveredOwnerFactionId != null && this.hoveringFlag) {
            if(FactionGUI.hasPermissions("actions") && !((String)FactionGUI.factionInfos.get("playerWhoSeeFactionId")).equals(this.hoveredOwnerFactionId) && (System.currentTimeMillis() - Long.parseLong((String)factionActionsInfos.get("lastActionFromBuyer")) >= 18000000L || ((Boolean)factionActionsInfos.get("isOp")).booleanValue())) {
               Minecraft.func_71410_x().func_71373_a(new BuyActionConfirmGui(this, this.hoveredIndex, this.hoveredOwnerFactionId, (String)factionActionsInfos.get("price")));
            }
         } else if(this.hoveredIndex != -1 && this.hoveredOwnerFactionId != null && this.hoveringStatus && this.hoveredStatus != null) {
            if(FactionGUI.hasPermissions("actions") && (((String)FactionGUI.factionInfos.get("playerWhoSeeFactionId")).equals(this.hoveredOwnerFactionId) || FactionGUI.isNumeric(this.hoveredOwnerFactionId, true) && ((Boolean)FactionGUI.factionInfos.get("isInCountry")).booleanValue())) {
               PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new FactionLockActionPacket((String)FactionGUI.factionInfos.get("id"), this.hoveredIndex)));
               PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new FactionActionsDataPacket((String)FactionGUI.factionInfos.get("name"))));
            }
         } else if(this.hoveredAction.equals("open_chest")) {
            if(((Boolean)FactionGUI.factionInfos.get("canOpenChest")).booleanValue() && FactionGUI.hasPermissions("chest_access") || FactionGUI.hasPermissions("admin")) {
               if(Integer.parseInt((String)FactionGUI.factionInfos.get("chestLevel")) == 0) {
                  Minecraft.func_71410_x().field_71439_g.func_71035_c(I18n.func_135053_a(FactionGUI.hasPermissions("admin")?"faction.bank.chest_no":"faction.bank.chest_no_level"));
                  return;
               }

               PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new RemoteOpenFactionChestPacket((String)FactionGUI.factionInfos.get("id"), FactionGUI.hasPermissions("chest_access"), FactionGUI.hasPermissions("chest_access"), Integer.parseInt((String)FactionGUI.factionInfos.get("chestLevel")))));
            }
         } else if(this.hoveredAction.equals("deposit")) {
            if(!this.amountInput.func_73781_b().isEmpty() && FactionGUI.isNumeric(this.amountInput.func_73781_b(), true)) {
               this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0F, 1.0F);
               PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new FactionBankActionPacket((String)FactionGUI.factionInfos.get("name"), this.amountInput.func_73781_b().replaceAll("^0+", ""), "deposit")));
               this.amountInput.func_73782_a("0");
            }
         } else if(this.hoveredAction.equals("withdraw")) {
            if(!this.amountInput.func_73781_b().isEmpty() && FactionGUI.isNumeric(this.amountInput.func_73781_b(), true)) {
               this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0F, 1.0F);
               PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new FactionBankActionPacket((String)FactionGUI.factionInfos.get("name"), this.amountInput.func_73781_b().replaceAll("^0+", ""), "take")));
               this.amountInput.func_73782_a("0");
            }
         } else if(this.hoveredAction.equals("see_owned_actions")) {
            Minecraft.func_71410_x().func_71373_a(new ActionsListGui());
         } else if(!this.hoveredPlayer.isEmpty()) {
            this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0F, 1.0F);
            Minecraft.func_71410_x().func_71373_a(new ProfilGui(this.hoveredPlayer.split(" ")[1], ""));
         }
      }

      this.amountInput.func_73793_a(mouseX, mouseY, mouseButton);
      super.func_73864_a(mouseX, mouseY, mouseButton);
   }

   private float getSlideLogs() {
      return ((ArrayList)factionBankInfos.get("logs")).size() > 5?(float)(-(((ArrayList)factionBankInfos.get("logs")).size() - 5) * 18) * this.scrollBarLogs.getSliderValue():0.0F;
   }

   private float getSlideMembers() {
      return ((ArrayList)factionBankInfos.get("members")).size() > 7?(float)(-(((ArrayList)factionBankInfos.get("members")).size() - 7) * 13) * this.scrollBarMembers.getSliderValue():0.0F;
   }

   private float getSlideTotalDividendes() {
      return ((ArrayList)factionActionsInfos.get("totals")).size() > 6?(float)(-(((ArrayList)factionActionsInfos.get("totals")).size() - 6) * 10) * this.scrollBarTotalDividendes.getSliderValue():0.0F;
   }

   private float getSlideLogsDividendes() {
      return ((ArrayList)factionActionsInfos.get("logs")).size() > 3?(float)(-(((ArrayList)factionActionsInfos.get("logs")).size() - 3) * 19) * this.scrollBarLogsDividendes.getSliderValue():0.0F;
   }

}
