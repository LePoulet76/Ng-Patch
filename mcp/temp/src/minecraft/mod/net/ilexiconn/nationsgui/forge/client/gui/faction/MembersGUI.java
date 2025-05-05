package net.ilexiconn.nationsgui.forge.client.gui.faction;

import com.google.gson.internal.LinkedTreeMap;
import cpw.mods.fml.common.network.PacketDispatcher;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;
import net.ilexiconn.nationsgui.forge.client.ClientData;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScrollBarGeneric;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionGUI;
import net.ilexiconn.nationsgui.forge.client.gui.faction.LeaderConfirmGui;
import net.ilexiconn.nationsgui.forge.client.gui.faction.MembersGUI$1;
import net.ilexiconn.nationsgui.forge.client.gui.faction.ProfilGui;
import net.ilexiconn.nationsgui.forge.client.gui.faction.TabbedFactionGUI;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionMembersDataPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionNewMembersDataPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionPlayerDataPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionProfilActionPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.util.ResourceLocation;

public class MembersGUI extends TabbedFactionGUI {

   public static boolean loaded = false;
   public static boolean loaded_new_player = false;
   public static TreeMap<String, Object> factionMembersInfos;
   public static LinkedHashMap<String, Object> factionNewMembersInfos;
   private GuiScrollBarGeneric scrollBarMembers;
   private GuiTextField searchInput;
   private String displayMode = "members";
   private String hoveredPlayer;
   public static Long lastPromotePlayer = Long.valueOf(0L);
   private HashMap<String, EntityOtherPlayerMP> entities = new HashMap();
   public static ArrayList<Integer> OFFICERS_POSITION_X = new ArrayList(Arrays.asList(new Integer[]{Integer.valueOf(225), Integer.valueOf(435), Integer.valueOf(255), Integer.valueOf(405), Integer.valueOf(285), Integer.valueOf(375)}));
   public static ArrayList<Integer> OFFICERS_POSITION_Y = new ArrayList(Arrays.asList(new Integer[]{Integer.valueOf(127), Integer.valueOf(127), Integer.valueOf(121), Integer.valueOf(121), Integer.valueOf(115), Integer.valueOf(115)}));
   public int countMembersBySearch = -1;
   public int countNewMembersBySearch = -1;
   public static HashMap<String, Integer> blockMembers = new MembersGUI$1();


   public MembersGUI() {
      loaded = false;
      factionMembersInfos = new TreeMap();
      factionNewMembersInfos = new LinkedHashMap();
      PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new FactionMembersDataPacket((String)FactionGUI.factionInfos.get("id"))));
      PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new FactionNewMembersDataPacket((String)FactionGUI.factionInfos.get("id"))));
   }

   public void func_73876_c() {
      this.searchInput.func_73780_a();
   }

   public void func_73866_w_() {
      super.func_73866_w_();
      this.scrollBarMembers = new GuiScrollBarGeneric((float)(this.guiLeft + 250), (float)(this.guiTop + 101), 119, new ResourceLocation("nationsgui", "textures/gui/generic_ingame/cursor_faction.png"), 2, 8);
      this.searchInput = new GuiTextField(this.field_73886_k, this.guiLeft + 60, this.guiTop + 63, 97, 10);
      this.searchInput.func_73786_a(false);
      this.searchInput.func_73804_f(15);
      this.countMembersBySearch = -1;
      this.countNewMembersBySearch = -1;
   }

   public void func_73863_a(int mouseX, int mouseY, float partialTick) {
      this.func_73873_v_();
      tooltipToDraw.clear();
      this.hoveredAction = "";
      this.hoveredPlayer = "";
      ClientEventHandler.STYLE.bindTexture("faction_global_2");
      ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 30), (float)(this.guiTop + 0), (float)(0 * GUI_SCALE), (float)(0 * GUI_SCALE), (this.xSize - 30) * GUI_SCALE, this.ySize * GUI_SCALE, this.xSize - 30, this.ySize, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
      if(loaded) {
         if(FactionGUI.factionInfos.get("banners") != null && ((Map)FactionGUI.factionInfos.get("banners")).containsKey("members")) {
            ModernGui.bindRemoteTexture((String)((Map)FactionGUI.factionInfos.get("banners")).get("members"));
         } else {
            ModernGui.bindRemoteTexture("https://static.nationsglory.fr/N3255yGyNN.png");
         }

         ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 30 + 154), (float)(this.guiTop + 0), 0.0F, 0.0F, 279 * GUI_SCALE, 110 * GUI_SCALE, 279, 89, (float)(279 * GUI_SCALE), (float)(110 * GUI_SCALE), false);
         ClientEventHandler.STYLE.bindTexture("faction_global");
         ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 30), (float)(this.guiTop + 0), (float)(33 * GUI_SCALE), (float)(280 * GUI_SCALE), 433 * GUI_SCALE, 89 * GUI_SCALE, 433, 89, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
         ModernGui.drawScaledStringCustomFont((String)FactionGUI.factionInfos.get("name"), (float)(this.guiLeft + 43), (float)(this.guiTop + 6), 10395075, 0.5F, "left", false, "georamaMedium", 32);
         ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("faction.members.title"), (float)(this.guiLeft + 43), (float)(this.guiTop + 16), 16777215, 0.75F, "left", false, "georamaSemiBold", 32);
         ModernGui.drawSectionStringCustomFont(((String)FactionGUI.factionInfos.get("description")).replaceAll("\u00a7[0-9a-z]{1}", ""), (float)(this.guiLeft + 43), (float)(this.guiTop + 30), 10395075, 0.5F, "left", false, "georamaMedium", 22, 7, 360);
         ClientEventHandler.STYLE.bindTexture("faction_members");
         ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 43), (float)(this.guiTop + 61), (float)(177 * GUI_SCALE), (float)(123 * GUI_SCALE), 113 * GUI_SCALE, 12 * GUI_SCALE, 113, 12, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
         ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 46), (float)(this.guiTop + 63), (float)(177 * GUI_SCALE), (float)(24 * GUI_SCALE), 8 * GUI_SCALE, 8 * GUI_SCALE, 8, 8, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
         this.searchInput.func_73795_f();
         ArrayList officers = new ArrayList();
         ArrayList members = new ArrayList();
         Iterator it = factionMembersInfos.entrySet().iterator();

         while(it.hasNext()) {
            Entry officersPositionX = (Entry)it.next();
            String officersPositionY = ((String)officersPositionX.getKey()).split(" ")[1];
            LinkedTreeMap index = (LinkedTreeMap)officersPositionX.getValue();
            if(!index.get("rank").equals("LEADER") && !index.get("rank").equals("OFFICER")) {
               if(index.get("rank").equals("MEMBER")) {
                  members.add(officersPositionY);
               }
            } else {
               if(!this.entities.containsKey(officersPositionY)) {
                  try {
                     this.entities.put(officersPositionY, new EntityOtherPlayerMP(this.field_73882_e.field_71441_e, officersPositionY));
                  } catch (Exception var31) {
                     this.entities.put(officersPositionY, (Object)null);
                  }
               }

               if(index.get("rank").equals("OFFICER")) {
                  officers.add(officersPositionY);
               }
            }
         }

         ArrayList var32 = OFFICERS_POSITION_X;
         ArrayList var33 = OFFICERS_POSITION_Y;
         if(officers.size() <= 2) {
            var32 = new ArrayList(OFFICERS_POSITION_X.subList(4, 6));
            var33 = new ArrayList(OFFICERS_POSITION_Y.subList(4, 6));
         } else if(officers.size() == 3) {
            var32 = new ArrayList(Arrays.asList(new Integer[]{(Integer)OFFICERS_POSITION_X.get(2), (Integer)OFFICERS_POSITION_X.get(4), (Integer)OFFICERS_POSITION_X.get(5)}));
            var33 = new ArrayList(Arrays.asList(new Integer[]{(Integer)OFFICERS_POSITION_Y.get(2), (Integer)OFFICERS_POSITION_Y.get(4), (Integer)OFFICERS_POSITION_Y.get(5)}));
         } else if(officers.size() == 4) {
            var32 = new ArrayList(OFFICERS_POSITION_X.subList(2, 6));
            var33 = new ArrayList(OFFICERS_POSITION_Y.subList(2, 6));
         } else if(officers.size() == 5) {
            var32 = new ArrayList(Arrays.asList(new Integer[]{(Integer)OFFICERS_POSITION_X.get(0), (Integer)OFFICERS_POSITION_X.get(2), (Integer)OFFICERS_POSITION_X.get(3), (Integer)OFFICERS_POSITION_X.get(4), (Integer)OFFICERS_POSITION_X.get(5)}));
            var33 = new ArrayList(Arrays.asList(new Integer[]{(Integer)OFFICERS_POSITION_Y.get(0), (Integer)OFFICERS_POSITION_Y.get(2), (Integer)OFFICERS_POSITION_Y.get(3), (Integer)OFFICERS_POSITION_Y.get(4), (Integer)OFFICERS_POSITION_Y.get(5)}));
         }

         int var34;
         for(var34 = 0; var34 < Math.min(6, officers.size()); ++var34) {
            if(this.entities.containsKey(officers.get(var34)) && this.entities.get(officers.get(var34)) != null) {
               GUIUtils.startGLScissor(this.guiLeft + ((Integer)var32.get(var34)).intValue() - 80, this.guiTop + ((Integer)var33.get(var34)).intValue() - 83, 500, 89 - (((Integer)var33.get(var34)).intValue() - 83));
               GuiInventory.func_110423_a(this.guiLeft + ((Integer)var32.get(var34)).intValue(), this.guiTop + ((Integer)var33.get(var34)).intValue(), 40, 0.0F, 0.0F, (EntityLivingBase)this.entities.get(officers.get(var34)));
               GUIUtils.endGLScissor();
            }
         }

         if(this.entities.containsKey((String)FactionGUI.factionInfos.get("leader")) && this.entities.get((String)FactionGUI.factionInfos.get("leader")) != null) {
            GUIUtils.startGLScissor(this.guiLeft + 330 - 50, this.guiTop + 135 - 135, 160, 89);
            GuiInventory.func_110423_a(this.guiLeft + 330, this.guiTop + 135, 65, 0.0F, 0.0F, (EntityLivingBase)this.entities.get((String)FactionGUI.factionInfos.get("leader")));
            GUIUtils.endGLScissor();
         }

         ClientEventHandler.STYLE.bindTexture("faction_skills");
         ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 42), (float)(this.guiTop + 77), (float)(375 * GUI_SCALE), (float)((this.displayMode.equals("members")?0:15) * GUI_SCALE), 65 * GUI_SCALE, 12 * GUI_SCALE, 65, 12, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
         ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("faction.members.button.members"), (float)(this.guiLeft + 42 + 33), (float)(this.guiTop + 80), this.displayMode.equals("members")?7239406:16777215, 0.5F, "center", false, "georamaSemiBold", 24);
         if(mouseX > this.guiLeft + 42 && mouseX < this.guiLeft + 42 + 65 && mouseY >= this.guiTop + 77 && mouseY <= this.guiTop + 77 + 12) {
            this.hoveredAction = "members";
         }

         ClientEventHandler.STYLE.bindTexture("faction_skills");
         ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 108), (float)(this.guiTop + 77), (float)(375 * GUI_SCALE), (float)((this.displayMode.equals("new_members")?0:15) * GUI_SCALE), 65 * GUI_SCALE, 12 * GUI_SCALE, 65, 12, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
         ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("faction.members.button.new_members"), (float)(this.guiLeft + 108 + 33), (float)(this.guiTop + 80), this.displayMode.equals("new_members")?7239406:16777215, 0.5F, "center", false, "georamaSemiBold", 24);
         if(!factionNewMembersInfos.isEmpty()) {
            ClientEventHandler.STYLE.bindTexture("faction_members");
            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 170), (float)(this.guiTop + 76), (float)(450 * GUI_SCALE), (float)(278 * GUI_SCALE), 5 * GUI_SCALE, 5 * GUI_SCALE, 5, 5, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
         }

         if(mouseX > this.guiLeft + 108 && mouseX < this.guiLeft + 108 + 65 && mouseY >= this.guiTop + 77 && mouseY <= this.guiTop + 77 + 12) {
            this.hoveredAction = "new_members";
         }

         ClientEventHandler.STYLE.bindTexture("faction_members");
         ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 250), (float)(this.guiTop + 101), (float)(237 * GUI_SCALE), (float)(0 * GUI_SCALE), 2 * GUI_SCALE, 119 * GUI_SCALE, 2, 119, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
         int rewardPossiblePower;
         int var37;
         int var39;
         if(this.displayMode.equals("members")) {
            GUIUtils.startGLScissor(this.guiLeft + 45, this.guiTop + 103, 213, 115);
            var34 = 0;
            Iterator multiplier = factionMembersInfos.entrySet().iterator();

            while(multiplier.hasNext()) {
               Entry rewardPossible = (Entry)multiplier.next();
               rewardPossiblePower = this.guiLeft + 45;
               Float rewardGiven = Float.valueOf((float)(this.guiTop + 103 + var34 * 13) + this.getSlideMembers());
               String rewardGivenPower = ((String)rewardPossible.getKey()).split(" ")[0];
               String itMembers = ((String)rewardPossible.getKey()).split(" ")[1];
               if(itMembers.toLowerCase().contains(this.searchInput.func_73781_b().toLowerCase())) {
                  LinkedTreeMap pair = (LinkedTreeMap)rewardPossible.getValue();
                  if(rewardGiven.intValue() < this.guiTop + 103 + 115) {
                     if(!ClientProxy.cacheHeadPlayer.containsKey(itMembers)) {
                        try {
                           ResourceLocation value = AbstractClientPlayer.field_110314_b;
                           value = AbstractClientPlayer.func_110311_f(itMembers);
                           AbstractClientPlayer.func_110304_a(value, itMembers);
                           ClientProxy.cacheHeadPlayer.put(itMembers, value);
                        } catch (Exception var30) {
                           System.out.println(var30.getMessage());
                        }
                     } else {
                        Minecraft.func_71410_x().field_71446_o.func_110577_a((ResourceLocation)ClientProxy.cacheHeadPlayer.get(itMembers));
                        this.field_73882_e.func_110434_K().func_110577_a((ResourceLocation)ClientProxy.cacheHeadPlayer.get(itMembers));
                        GUIUtils.drawScaledCustomSizeModalRect(rewardPossiblePower + 10, rewardGiven.intValue() + 10, 8.0F, 16.0F, 8, -8, -10, -10, 64.0F, 64.0F);
                     }
                  }

                  String var44 = ((String)pair.get("title")).length() <= 12?(String)pair.get("title"):((String)pair.get("title")).substring(0, 12);
                  ModernGui.drawScaledStringCustomFont(var44, (float)(rewardPossiblePower + 13), (float)(rewardGiven.intValue() + 2), 7239406, 0.5F, "left", false, "georamaMedium", 28);
                  ClientEventHandler.STYLE.bindTexture("faction_global");
                  if(rewardGivenPower.contains("**")) {
                     ModernGui.drawScaledCustomSizeModalRect((float)(rewardPossiblePower + 62), (float)(rewardGiven.intValue() + 2), (float)(345 * GUI_SCALE), (float)((((Boolean)pair.get("isOnline")).booleanValue()?96:136) * GUI_SCALE), 40 * GUI_SCALE, 40 * GUI_SCALE, 8, 8, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
                  } else if(rewardGivenPower.contains("*")) {
                     ModernGui.drawScaledCustomSizeModalRect((float)(rewardPossiblePower + 62), (float)(rewardGiven.intValue() + 2), (float)(305 * GUI_SCALE), (float)((((Boolean)pair.get("isOnline")).booleanValue()?96:136) * GUI_SCALE), 40 * GUI_SCALE, 40 * GUI_SCALE, 8, 8, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
                  } else if(rewardGivenPower.contains("-")) {
                     ModernGui.drawScaledCustomSizeModalRect((float)(rewardPossiblePower + 62), (float)(rewardGiven.intValue() + 2), (float)(265 * GUI_SCALE), (float)((((Boolean)pair.get("isOnline")).booleanValue()?96:136) * GUI_SCALE), 40 * GUI_SCALE, 40 * GUI_SCALE, 8, 8, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
                  } else {
                     ModernGui.drawScaledCustomSizeModalRect((float)(rewardPossiblePower + 62), (float)(rewardGiven.intValue() + 2), (float)(225 * GUI_SCALE), (float)((((Boolean)pair.get("isOnline")).booleanValue()?96:136) * GUI_SCALE), 40 * GUI_SCALE, 40 * GUI_SCALE, 8, 8, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
                  }

                  ModernGui.drawScaledStringCustomFont(itMembers, (float)(rewardPossiblePower + 70), (float)(rewardGiven.intValue() + 2), ((Boolean)pair.get("isOnline")).booleanValue()?16777215:10395075, 0.5F, "left", false, "georamaMedium", 28);
                  if(rewardGiven.floatValue() >= (float)(this.guiTop + 95) && rewardGiven.floatValue() <= (float)(this.guiTop + 103 + 115)) {
                     if(mouseX >= rewardPossiblePower + 62 && mouseX <= rewardPossiblePower + 62 + 50 && (float)mouseY >= rewardGiven.floatValue() + 1.0F && (float)mouseY <= rewardGiven.floatValue() + 2.0F + 8.0F) {
                        if(FactionGUI.playerTooltip.containsKey(itMembers)) {
                           this.hoveredPlayer = itMembers;
                           this.hoveredAction = "see_player";
                           tooltipToDraw = FactionGUI.getPlayerTooltip(itMembers);
                        } else {
                           PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new FactionPlayerDataPacket(itMembers)));
                           FactionGUI.playerTooltip.put(itMembers, (Object)null);
                        }
                     }

                     if(((Boolean)FactionGUI.factionInfos.get("isInCountry")).booleanValue()) {
                        ClientEventHandler.STYLE.bindTexture("faction_members");
                        if(!itMembers.equals((String)FactionGUI.factionInfos.get("leader"))) {
                           if(mouseX >= rewardPossiblePower + 153 && mouseX <= rewardPossiblePower + 153 + 8 && mouseY >= rewardGiven.intValue() + 1 && mouseY <= rewardGiven.intValue() + 1 + 8) {
                              ModernGui.drawScaledCustomSizeModalRect((float)(rewardPossiblePower + 153), (float)(rewardGiven.intValue() + 1), (float)(177 * GUI_SCALE), (float)(13 * GUI_SCALE), 8 * GUI_SCALE, 8 * GUI_SCALE, 8, 8, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                              if(pair.get("rank").equals("MEMBER")) {
                                 this.hoveredAction = "demote_recruit";
                              } else if(pair.get("rank").equals("OFFICER")) {
                                 this.hoveredAction = "demote_member";
                              }

                              this.hoveredPlayer = itMembers;
                              tooltipToDraw.add(I18n.func_135053_a("faction.members.action.demote").replaceAll("#rank#", FactionGUI.getRoleName(this.hoveredAction.replace("demote_", ""))));
                           } else {
                              ModernGui.drawScaledCustomSizeModalRect((float)(rewardPossiblePower + 153), (float)(rewardGiven.intValue() + 1), (float)(177 * GUI_SCALE), (float)(2 * GUI_SCALE), 8 * GUI_SCALE, 8 * GUI_SCALE, 8, 8, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                           }

                           if(mouseX >= rewardPossiblePower + 164 && mouseX <= rewardPossiblePower + 164 + 8 && mouseY >= rewardGiven.intValue() + 1 && mouseY <= rewardGiven.intValue() + 1 + 8) {
                              ModernGui.drawScaledCustomSizeModalRect((float)(rewardPossiblePower + 164), (float)(rewardGiven.intValue() + 1), (float)(188 * GUI_SCALE), (float)(13 * GUI_SCALE), 8 * GUI_SCALE, 8 * GUI_SCALE, 8, 8, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                              if(pair.get("rank").equals("RECRUIT")) {
                                 this.hoveredAction = "promote_member";
                              } else if(pair.get("rank").equals("MEMBER")) {
                                 this.hoveredAction = "promote_officer";
                              } else if(pair.get("rank").equals("OFFICER")) {
                                 this.hoveredAction = "promote_leader";
                              }

                              this.hoveredPlayer = itMembers;
                              tooltipToDraw.add(I18n.func_135053_a("faction.members.action.promote").replaceAll("#rank#", FactionGUI.getRoleName(this.hoveredAction.replace("promote_", ""))));
                           } else {
                              ModernGui.drawScaledCustomSizeModalRect((float)(rewardPossiblePower + 164), (float)(rewardGiven.intValue() + 1), (float)(188 * GUI_SCALE), (float)(2 * GUI_SCALE), 8 * GUI_SCALE, 8 * GUI_SCALE, 8, 8, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                           }

                           if(mouseX >= rewardPossiblePower + 175 && mouseX <= rewardPossiblePower + 175 + 8 && mouseY >= rewardGiven.intValue() + 1 && mouseY <= rewardGiven.intValue() + 1 + 8) {
                              ModernGui.drawScaledCustomSizeModalRect((float)(rewardPossiblePower + 175), (float)(rewardGiven.intValue() + 1), (float)(201 * GUI_SCALE), (float)((((Boolean)pair.get("bankMember")).booleanValue()?13:2) * GUI_SCALE), 10 * GUI_SCALE, 8 * GUI_SCALE, 10, 8, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                              this.hoveredAction = ((Boolean)pair.get("bankMember")).booleanValue()?"remove_bank":"add_bank";
                              this.hoveredPlayer = itMembers;
                              tooltipToDraw.add(I18n.func_135053_a("faction.members.action." + this.hoveredAction));
                           } else {
                              ModernGui.drawScaledCustomSizeModalRect((float)(rewardPossiblePower + 175), (float)(rewardGiven.intValue() + 1), (float)(201 * GUI_SCALE), (float)((((Boolean)pair.get("bankMember")).booleanValue()?2:13) * GUI_SCALE), 10 * GUI_SCALE, 8 * GUI_SCALE, 10, 8, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                           }

                           if(mouseX >= rewardPossiblePower + 186 && mouseX <= rewardPossiblePower + 186 + 8 && mouseY >= rewardGiven.intValue() + 1 && mouseY <= rewardGiven.intValue() + 1 + 8) {
                              ModernGui.drawScaledCustomSizeModalRect((float)(rewardPossiblePower + 186), (float)(rewardGiven.intValue() + 1), (float)(214 * GUI_SCALE), (float)(13 * GUI_SCALE), 8 * GUI_SCALE, 8 * GUI_SCALE, 8, 8, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                              this.hoveredAction = "exclude";
                              this.hoveredPlayer = itMembers;
                              tooltipToDraw.add(I18n.func_135053_a("faction.members.action.exclude"));
                           } else {
                              ModernGui.drawScaledCustomSizeModalRect((float)(rewardPossiblePower + 186), (float)(rewardGiven.intValue() + 1), (float)(214 * GUI_SCALE), (float)(2 * GUI_SCALE), 8 * GUI_SCALE, 8 * GUI_SCALE, 8, 8, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                           }
                        }
                     }
                  }

                  ++var34;
               }
            }

            if(this.countMembersBySearch == -1) {
               this.countMembersBySearch = var34 + 1;
            }

            GUIUtils.endGLScissor();
            if(mouseX > this.guiLeft + 45 && mouseX < this.guiLeft + 45 + 215 && mouseY >= this.guiTop + 100 && mouseY <= this.guiTop + 100 + 127) {
               this.scrollBarMembers.draw(mouseX, mouseY);
            }

            ClientEventHandler.STYLE.bindTexture("faction_members");
            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 276), (float)(this.guiTop + 101), (float)(0 * GUI_SCALE), (float)(((Integer)blockMembers.get((String)FactionGUI.factionInfos.get("actualRelation"))).intValue() * GUI_SCALE), 172 * GUI_SCALE, 119 * GUI_SCALE, 172, 119, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
            ModernGui.drawScaledStringCustomFont(FactionGUI.getRoleName("leader"), (float)(this.guiLeft + 276 + 86), (float)(this.guiTop + 106), 16777215, 0.5F, "center", false, "georamaBold", 28);
            if(!ClientProxy.cacheHeadPlayer.containsKey((String)FactionGUI.factionInfos.get("leader"))) {
               try {
                  ResourceLocation var36 = AbstractClientPlayer.field_110314_b;
                  var36 = AbstractClientPlayer.func_110311_f((String)FactionGUI.factionInfos.get("leader"));
                  AbstractClientPlayer.func_110304_a(var36, (String)FactionGUI.factionInfos.get("leader"));
                  ClientProxy.cacheHeadPlayer.put((String)FactionGUI.factionInfos.get("leader"), var36);
               } catch (Exception var29) {
                  System.out.println(var29.getMessage());
               }
            } else {
               Minecraft.func_71410_x().field_71446_o.func_110577_a((ResourceLocation)ClientProxy.cacheHeadPlayer.get((String)FactionGUI.factionInfos.get("leader")));
               this.field_73882_e.func_110434_K().func_110577_a((ResourceLocation)ClientProxy.cacheHeadPlayer.get((String)FactionGUI.factionInfos.get("leader")));
               GUIUtils.drawScaledCustomSizeModalRect(this.guiLeft + 276 + 86 + 6, this.guiTop + 115 + 11, 8.0F, 16.0F, 8, -8, -12, -12, 64.0F, 64.0F);
               if(mouseX >= this.guiLeft + 276 + 86 + 6 - 12 && mouseX <= this.guiLeft + 276 + 86 + 6 && mouseY >= this.guiTop + 115 + 11 - 12 && mouseY <= this.guiTop + 115 + 11) {
                  tooltipToDraw.add((String)FactionGUI.factionInfos.get("leader"));
               }
            }

            ModernGui.drawScaledStringCustomFont(FactionGUI.getRoleName("officer"), (float)(this.guiLeft + 276 + 86), (float)(this.guiTop + 132), 16777215, 0.5F, "center", false, "georamaSemiBold", 28);
            var37 = this.guiLeft + 276 + 86;
            if(officers.size() > 1) {
               var37 = var37 - 2 - (Math.min(10, officers.size()) - 2) * 8;
            }

            for(rewardPossiblePower = 0; rewardPossiblePower < Math.min(10, officers.size()); ++rewardPossiblePower) {
               if(!ClientProxy.cacheHeadPlayer.containsKey(officers.get(rewardPossiblePower))) {
                  try {
                     ResourceLocation var38 = AbstractClientPlayer.field_110314_b;
                     var38 = AbstractClientPlayer.func_110311_f((String)officers.get(rewardPossiblePower));
                     AbstractClientPlayer.func_110304_a(var38, (String)officers.get(rewardPossiblePower));
                     ClientProxy.cacheHeadPlayer.put(officers.get(rewardPossiblePower), var38);
                  } catch (Exception var28) {
                     System.out.println(var28.getMessage());
                  }
               } else {
                  Minecraft.func_71410_x().field_71446_o.func_110577_a((ResourceLocation)ClientProxy.cacheHeadPlayer.get(officers.get(rewardPossiblePower)));
                  this.field_73882_e.func_110434_K().func_110577_a((ResourceLocation)ClientProxy.cacheHeadPlayer.get(officers.get(rewardPossiblePower)));
                  GUIUtils.drawScaledCustomSizeModalRect(var37 + rewardPossiblePower * 16, this.guiTop + 153, 8.0F, 16.0F, 8, -8, -12, -12, 64.0F, 64.0F);
                  if(mouseX >= var37 + rewardPossiblePower * 16 - 12 && mouseX <= var37 + rewardPossiblePower * 16 && mouseY >= this.guiTop + 153 - 12 && mouseY <= this.guiTop + 153) {
                     tooltipToDraw.add(officers.get(rewardPossiblePower));
                  }
               }
            }

            ModernGui.drawScaledStringCustomFont(FactionGUI.getRoleName("member"), (float)(this.guiLeft + 276 + 86), (float)(this.guiTop + 162), 16777215, 0.5F, "center", false, "georamaSemiBold", 28);
            var37 = this.guiLeft + 276 + 86;
            rewardPossiblePower = this.guiTop + 183;
            if(members.size() > 1) {
               var37 = var37 - 2 - (Math.min(10, members.size()) - 2) * 8;
            }

            for(var39 = 0; var39 < Math.min(30, members.size()); ++var39) {
               if(!ClientProxy.cacheHeadPlayer.containsKey(members.get(var39))) {
                  try {
                     ResourceLocation var40 = AbstractClientPlayer.field_110314_b;
                     var40 = AbstractClientPlayer.func_110311_f((String)members.get(var39));
                     AbstractClientPlayer.func_110304_a(var40, (String)members.get(var39));
                     ClientProxy.cacheHeadPlayer.put(members.get(var39), var40);
                  } catch (Exception var27) {
                     System.out.println(var27.getMessage());
                  }
               } else {
                  Minecraft.func_71410_x().field_71446_o.func_110577_a((ResourceLocation)ClientProxy.cacheHeadPlayer.get(members.get(var39)));
                  this.field_73882_e.func_110434_K().func_110577_a((ResourceLocation)ClientProxy.cacheHeadPlayer.get(members.get(var39)));
                  GUIUtils.drawScaledCustomSizeModalRect(var37 + var39 % 10 * 16, rewardPossiblePower + 16 * (var39 / 10), 8.0F, 16.0F, 8, -8, -12, -12, 64.0F, 64.0F);
                  if(mouseX >= var37 + var39 % 10 * 16 - 12 && mouseX <= var37 + var39 % 10 * 16 && mouseY >= rewardPossiblePower + 16 * (var39 / 10) - 12 && mouseY <= rewardPossiblePower + 16 * (var39 / 10)) {
                     tooltipToDraw.add(members.get(var39));
                  }
               }
            }
         } else {
            GUIUtils.startGLScissor(this.guiLeft + 45, this.guiTop + 103, 213, 115);
            var34 = 0;
            byte var35 = 1;
            if(((Boolean)FactionGUI.factionInfos.get("isReferent")).booleanValue()) {
               var35 = 2;
            }

            var37 = 0;
            rewardPossiblePower = 0;
            var39 = 0;
            int var41 = 0;
            Iterator var42 = factionNewMembersInfos.entrySet().iterator();

            Entry var43;
            Map var45;
            while(var42.hasNext()) {
               var43 = (Entry)var42.next();
               var45 = (Map)var43.getValue();
               LinkedHashMap offsetX = new LinkedHashMap(var45);
               double offsetY = ((Number)offsetX.get("time")).doubleValue() / 3600.0D;
               if(offsetY > 4.0D) {
                  var39 += 500 * var35;
               } else {
                  var37 += 500 * var35;
               }

               if(offsetY > 8.0D) {
                  var39 += 1000 * var35;
                  var41 += 2 * var35;
               } else {
                  var37 += 1000 * var35;
                  rewardPossiblePower += 2 * var35;
               }

               if(offsetY > 24.0D) {
                  var39 += 1500 * var35;
                  var41 += 5 * var35;
               } else {
                  var37 += 1500 * var35;
                  rewardPossiblePower += 5 * var35;
               }

               if(offsetY > 48.0D) {
                  var39 += 2000 * var35;
                  var41 += 10 * var35;
               } else {
                  var37 += 2000 * var35;
                  rewardPossiblePower += 10 * var35;
               }
            }

            var42 = factionNewMembersInfos.entrySet().iterator();

            while(var42.hasNext()) {
               var43 = (Entry)var42.next();
               var45 = (Map)var43.getValue();
               int var46 = this.guiLeft + 45;
               Float var47 = Float.valueOf((float)(this.guiTop + 103 + var34 * 13) + this.getSlideMembers());
               double amountPlayTime = ((Double)var45.get("time")).doubleValue();
               boolean isOnline = ((Boolean)var45.get("online")).booleanValue();
               String playerName = (String)var43.getKey();
               if(playerName.toLowerCase().contains(this.searchInput.func_73781_b().toLowerCase())) {
                  amountPlayTime = (double)((int)(amountPlayTime / 60.0D / 60.0D));
                  LinkedTreeMap playerData = (LinkedTreeMap)var43.getValue();
                  if(var47.intValue() < this.guiTop + 103 + 115) {
                     if(!ClientProxy.cacheHeadPlayer.containsKey(playerName)) {
                        try {
                           ResourceLocation e = AbstractClientPlayer.field_110314_b;
                           e = AbstractClientPlayer.func_110311_f(playerName);
                           AbstractClientPlayer.func_110304_a(e, playerName);
                           ClientProxy.cacheHeadPlayer.put(playerName, e);
                        } catch (Exception var26) {
                           System.out.println(var26.getMessage());
                        }
                     } else {
                        Minecraft.func_71410_x().field_71446_o.func_110577_a((ResourceLocation)ClientProxy.cacheHeadPlayer.get(playerName));
                        this.field_73882_e.func_110434_K().func_110577_a((ResourceLocation)ClientProxy.cacheHeadPlayer.get(playerName));
                        GUIUtils.drawScaledCustomSizeModalRect(var46 + 10, var47.intValue() + 10, 8.0F, 16.0F, 8, -8, -10, -10, 64.0F, 64.0F);
                     }
                  }

                  ModernGui.drawScaledStringCustomFont(playerName, (float)(var46 + 14), (float)(var47.intValue() + 2), isOnline?16777215:10395075, 0.5F, "left", false, "georamaMedium", 28);
                  ModernGui.drawScaledStringCustomFont(amountPlayTime + "h", (float)(var46 + 110), (float)(var47.intValue() + 2), 10395075, 0.5F, "center", false, "georamaMedium", 28);
                  if(var47.floatValue() >= (float)(this.guiTop + 95) && var47.floatValue() <= (float)(this.guiTop + 103 + 115)) {
                     if(mouseX >= var46 + 14 && mouseX <= var46 + 44 + 50 && (float)mouseY >= var47.floatValue() + 1.0F && (float)mouseY <= var47.floatValue() + 2.0F + 8.0F) {
                        if(FactionGUI.playerTooltip.containsKey(playerName)) {
                           this.hoveredPlayer = playerName;
                           this.hoveredAction = "see_player";
                           tooltipToDraw = FactionGUI.getPlayerTooltip(playerName);
                        } else {
                           PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new FactionPlayerDataPacket(playerName)));
                           FactionGUI.playerTooltip.put(playerName, (Object)null);
                        }
                     }

                     ClientEventHandler.STYLE.bindTexture("faction_members");
                     ModernGui.drawScaledCustomSizeModalRect((float)(var46 + 154), (float)(var47.intValue() + 1), (float)(462 * GUI_SCALE), (float)(287 * GUI_SCALE), 44 * GUI_SCALE, 9 * GUI_SCALE, 44, 9, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                     if(amountPlayTime > 4.0D) {
                        ModernGui.drawScaledCustomSizeModalRect((float)(var46 + 154), (float)(var47.intValue() + 1), (float)(462 * GUI_SCALE), (float)(275 * GUI_SCALE), 9 * GUI_SCALE, 9 * GUI_SCALE, 9, 9, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                     }

                     if(amountPlayTime > 8.0D) {
                        ModernGui.drawScaledCustomSizeModalRect((float)(var46 + 166), (float)(var47.intValue() + 1), (float)(462 * GUI_SCALE), (float)(275 * GUI_SCALE), 9 * GUI_SCALE, 9 * GUI_SCALE, 9, 9, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                     }

                     if(amountPlayTime > 24.0D) {
                        ModernGui.drawScaledCustomSizeModalRect((float)(var46 + 178), (float)(var47.intValue() + 1), (float)(462 * GUI_SCALE), (float)(275 * GUI_SCALE), 9 * GUI_SCALE, 9 * GUI_SCALE, 9, 9, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                     }

                     if(amountPlayTime > 48.0D) {
                        ModernGui.drawScaledCustomSizeModalRect((float)(var46 + 190), (float)(var47.intValue() + 1), (float)(462 * GUI_SCALE), (float)(275 * GUI_SCALE), 9 * GUI_SCALE, 9 * GUI_SCALE, 9, 9, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                     }

                     ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("1"), (float)(var46 + 160), (float)var47.intValue() + 2.5F, amountPlayTime < 4.0D?10395075:7815169, 0.4F, "right", false, "georamaMedium", 31);
                     if(mouseX >= var46 + 154 && mouseX <= var46 + 154 + 8 && mouseY >= var47.intValue() + 2 && mouseY <= var47.intValue() + 2 + 8) {
                        tooltipToDraw.add("Apr\u00e8s 4h de temps de jeux inter-serveur");
                        tooltipToDraw.add("\u00a7a+" + 500 * var35 + " $");
                     }

                     ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("2"), (float)(var46 + 172), (float)var47.intValue() + 2.5F, amountPlayTime < 8.0D?10395075:7815169, 0.4F, "right", false, "georamaMedium", 31);
                     if(mouseX >= var46 + 166 && mouseX <= var46 + 166 + 8 && mouseY >= var47.intValue() + 2 && mouseY <= var47.intValue() + 2 + 8) {
                        tooltipToDraw.add("Apr\u00e8s 8h de temps de jeux inter-serveur");
                        tooltipToDraw.add("\u00a7a+" + 1000 * var35 + " $");
                        tooltipToDraw.add("\u00a7e+" + 2 * var35 + " Power (30jours)");
                     }

                     ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("3"), (float)(var46 + 184), (float)var47.intValue() + 2.5F, amountPlayTime < 24.0D?10395075:7815169, 0.4F, "right", false, "georamaMedium", 31);
                     if(mouseX >= var46 + 178 && mouseX <= var46 + 178 + 8 && mouseY >= var47.intValue() + 2 && mouseY <= var47.intValue() + 2 + 8) {
                        tooltipToDraw.add("Apr\u00e8s 24h de temps de jeux inter-serveur");
                        tooltipToDraw.add("\u00a7a+" + 1500 * var35 + " $");
                        tooltipToDraw.add("\u00a7e+" + 5 * var35 + " Power (30jours)");
                     }

                     ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("4"), (float)(var46 + 196), (float)var47.intValue() + 2.5F, amountPlayTime < 48.0D?10395075:7815169, 0.4F, "right", false, "georamaMedium", 31);
                     if(mouseX >= var46 + 190 && mouseX <= var46 + 190 + 8 && mouseY >= var47.intValue() + 2 && mouseY <= var47.intValue() + 2 + 8) {
                        tooltipToDraw.add("Apr\u00e8s 48h de temps de jeux inter-serveur");
                        tooltipToDraw.add("\u00a7a+" + 2000 * var35 + " $");
                        tooltipToDraw.add("\u00a7e+" + 10 * var35 + " Power (30jours)");
                     }
                  }

                  ++var34;
               }
            }

            this.countNewMembersBySearch = var34 + 1;
            GUIUtils.endGLScissor();
            if(mouseX > this.guiLeft + 45 && mouseX < this.guiLeft + 45 + 215 && mouseY >= this.guiTop + 100 && mouseY <= this.guiTop + 100 + 127) {
               this.scrollBarMembers.draw(mouseX, mouseY);
            }

            ClientEventHandler.STYLE.bindTexture("faction_members");
            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 269), (float)(this.guiTop + 89), (float)(282 * GUI_SCALE), (float)(305 * GUI_SCALE), 194 * GUI_SCALE, 146 * GUI_SCALE, 194, 146, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("faction.members.data.info.title"), (float)(this.guiLeft + 293), (float)(this.guiTop + 108), 15463162, 0.5F, "left", false, "georamaMedium", 32);
            ModernGui.drawSectionStringCustomFont(I18n.func_135053_a("faction.members.data.info.desc"), (float)(this.guiLeft + 293), (float)(this.guiTop + 120), 10395075, 0.5F, "left", false, "georamaMedium", 20, 6, 300);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("faction.members.data.stats.title"), (float)(this.guiLeft + 293), (float)(this.guiTop + 164), 15463162, 0.5F, "left", false, "georamaMedium", 32);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("faction.members.data.stats.forma_progress"), (float)(this.guiLeft + 293), (float)(this.guiTop + 177), -856952070, 0.4F, "left", false, "georamaMedium", 32);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("10"), (float)(this.guiLeft + 439), (float)(this.guiTop + 177), -2132020486, 0.4F, "right", false, "georamaMedium", 32);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("faction.members.data.stats.forma_finish"), (float)(this.guiLeft + 293), (float)(this.guiTop + 187), -856952070, 0.4F, "left", false, "georamaMedium", 32);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("10"), (float)(this.guiLeft + 439), (float)(this.guiTop + 187), -2132020486, 0.4F, "right", false, "georamaMedium", 32);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("faction.members.data.stats.reward_possible"), (float)(this.guiLeft + 293), (float)(this.guiTop + 197), -856952070, 0.4F, "left", false, "georamaMedium", 32);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a(var37 + " $ / " + rewardPossiblePower + " power"), (float)(this.guiLeft + 439), (float)(this.guiTop + 197), 16171012, 0.4F, "right", false, "georamaMedium", 32);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a("faction.members.data.stats.reward_finish"), (float)(this.guiLeft + 293), (float)(this.guiTop + 207), -856952070, 0.4F, "left", false, "georamaMedium", 32);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a(var39 + " $ / " + var41 + " power"), (float)(this.guiLeft + 439), (float)(this.guiTop + 207), 16171012, 0.4F, "right", false, "georamaMedium", 32);
         }
      }

      if(tooltipToDraw != null && !tooltipToDraw.isEmpty()) {
         this.drawHoveringText(tooltipToDraw, mouseX, mouseY, this.field_73886_k);
      }

      super.func_73863_a(mouseX, mouseY, partialTick);
   }

   private float getSlideMembers() {
      return this.displayMode.equals("members")?(this.countMembersBySearch > 10?(float)(-(this.countMembersBySearch - 10) * 13) * this.scrollBarMembers.getSliderValue():0.0F):(this.countNewMembersBySearch > 10?(float)(-(this.countNewMembersBySearch - 10) * 13) * this.scrollBarMembers.getSliderValue():0.0F);
   }

   public void drawScreen(int mouseX, int mouseY) {}

   protected void func_73869_a(char typedChar, int keyCode) {
      this.searchInput.func_73802_a(typedChar, keyCode);
      this.countMembersBySearch = -1;
      this.countNewMembersBySearch = -1;
      super.func_73869_a(typedChar, keyCode);
   }

   protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
      if(mouseButton == 0 && !this.hoveredAction.isEmpty()) {
         this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0F, 1.0F);
         if(this.hoveredAction.equals("new_members")) {
            loaded_new_player = false;
            this.scrollBarMembers.setSliderValue(0.0F);
            this.displayMode = this.hoveredAction;
            PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new FactionNewMembersDataPacket((String)FactionGUI.factionInfos.get("id"))));
            return;
         }

         if(this.hoveredAction.equals("members")) {
            this.scrollBarMembers.setSliderValue(0.0F);
            this.displayMode = this.hoveredAction;
            return;
         }

         if(this.hoveredAction.equals("edit_photo")) {
            ClientData.lastCaptureScreenshot.put("members", Long.valueOf(System.currentTimeMillis()));
            Minecraft.func_71410_x().func_71373_a((GuiScreen)null);
            Minecraft.func_71410_x().field_71439_g.func_70006_a(ChatMessageComponent.func_111066_d(I18n.func_135053_a("faction.take_picture")));
         }

         if(this.hoveredAction.equals("see_player")) {
            Minecraft.func_71410_x().func_71373_a(new ProfilGui(this.hoveredPlayer, ""));
         }

         if(this.hoveredAction.contains("promote")) {
            if(System.currentTimeMillis() - lastPromotePlayer.longValue() < 2000L) {
               return;
            }

            lastPromotePlayer = Long.valueOf(System.currentTimeMillis());
         }

         if(this.hoveredAction.equals("promote_leader")) {
            Minecraft.func_71410_x().func_71373_a(new LeaderConfirmGui(this, this.hoveredPlayer));
         } else {
            PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new FactionProfilActionPacket(this.hoveredPlayer, "", this.hoveredAction)));
            PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new FactionMembersDataPacket((String)FactionGUI.factionInfos.get("id"))));
            PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new FactionNewMembersDataPacket((String)FactionGUI.factionInfos.get("id"))));
         }

         this.hoveredAction = "";
      }

      this.searchInput.func_73793_a(mouseX, mouseY, mouseButton);
      super.func_73864_a(mouseX, mouseY, mouseButton);
   }

   public boolean isNumeric(String str) {
      try {
         return Double.parseDouble(str) > 0.0D;
      } catch (NumberFormatException var3) {
         return false;
      }
   }

}
