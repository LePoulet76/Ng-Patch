package net.ilexiconn.nationsgui.forge.client.gui.faction;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScreenTab;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScrollBarFaction;
import net.ilexiconn.nationsgui.forge.client.gui.faction.BankGUI;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionGUI;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.ilexiconn.nationsgui.forge.server.container.FactionChestContainer;
import net.ilexiconn.nationsgui.forge.server.inventory.FactionChestInventory;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionChestOpenDataPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class ChestGui extends GuiContainer {

   public static final List<GuiScreenTab> TABS = new ArrayList();
   private int xSize = 320;
   private int ySize = 321;
   protected FactionChestInventory inv;
   protected InventoryPlayer playerInv;
   public int rows;
   private RenderItem itemRenderer = new RenderItem();
   private int guiLeft;
   private int guiTop;
   private int chestLevel = 1;
   private GuiScrollBarFaction scrollBarLogs;
   private EntityPlayer entityPlayer = null;
   private List<String> chestLogs = new ArrayList();


   public ChestGui(InventoryPlayer inventoryPlayer, FactionChestInventory factionChestInventory) {
      super(new FactionChestContainer(inventoryPlayer, factionChestInventory, FactionGUI.hasPermissions("chest_access"), FactionGUI.hasPermissions("chest_access"), (String)FactionGUI.factionInfos.get("id")));
      super.field_74194_b = this.xSize;
      super.field_74195_c = this.ySize;
      this.playerInv = this.playerInv;
      this.chestLevel = Integer.parseInt((String)FactionGUI.factionInfos.get("chestLevel"));
      this.chestLogs = (List)BankGUI.factionBankInfos.get("chestLogs");
      this.entityPlayer = inventoryPlayer.field_70458_d;
   }

   public void func_73866_w_() {
      super.func_73866_w_();
      this.guiLeft = (this.field_73880_f - this.xSize) / 2;
      this.guiTop = (this.field_73881_g - this.ySize) / 2;
      this.scrollBarLogs = new GuiScrollBarFaction(298.0F, 47.0F, 100);
      PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new FactionChestOpenDataPacket((String)FactionGUI.factionInfos.get("id"))));
   }

   protected void func_74189_g(int mouseX, int mouseY) {
      String tooltipToDraw = "";
      String tooltipToDrawLog = "";
      ClientEventHandler.STYLE.bindTexture("faction_chest");
      if(mouseX >= this.guiLeft + 304 && mouseX <= this.guiLeft + 304 + 9 && mouseY >= this.guiTop - 6 && mouseY <= this.guiTop - 6 + 10) {
         ModernGui.drawModalRectWithCustomSizedTexture(304.0F, -6.0F, 0, 336, 9, 10, 512.0F, 512.0F, false);
      } else {
         ModernGui.drawModalRectWithCustomSizedTexture(304.0F, -6.0F, 0, 326, 9, 10, 512.0F, 512.0F, false);
      }

      this.drawScaledString(I18n.func_135053_a("faction.bank.chest_title") + " " + FactionGUI.factionInfos.get("name"), 50, 22, 5263440, 1.3F, false, false);
      this.drawScaledString(I18n.func_135053_a("container.inventory"), 70, 206, 16777215, 1.3F, false, true);
      if(this.chestLevel < 6) {
         ClientEventHandler.STYLE.bindTexture("faction_chest");
         ModernGui.drawModalRectWithCustomSizedTextureWithTransparency(50.0F, 133.0F, 0, 349, 162, 18, 512.0F, 512.0F, false);
         if(mouseX >= this.guiLeft + 127 && mouseX <= this.guiLeft + 127 + 8 && mouseY >= this.guiTop + 133 + 3 && mouseY <= this.guiTop + 133 + 15) {
            tooltipToDraw = I18n.func_135053_a("faction.bank.chest_level") + " 15";
         }
      }

      if(this.chestLevel < 5) {
         ClientEventHandler.STYLE.bindTexture("faction_chest");
         ModernGui.drawModalRectWithCustomSizedTextureWithTransparency(50.0F, 115.0F, 0, 349, 162, 18, 512.0F, 512.0F, false);
         if(mouseX >= this.guiLeft + 127 && mouseX <= this.guiLeft + 127 + 8 && mouseY >= this.guiTop + 115 + 3 && mouseY <= this.guiTop + 115 + 15) {
            tooltipToDraw = I18n.func_135053_a("faction.bank.chest_level") + " 13";
         }
      }

      if(this.chestLevel < 4) {
         ClientEventHandler.STYLE.bindTexture("faction_chest");
         ModernGui.drawModalRectWithCustomSizedTextureWithTransparency(50.0F, 97.0F, 0, 349, 162, 18, 512.0F, 512.0F, false);
         if(mouseX >= this.guiLeft + 127 && mouseX <= this.guiLeft + 127 + 8 && mouseY >= this.guiTop + 97 + 3 && mouseY <= this.guiTop + 97 + 15) {
            tooltipToDraw = I18n.func_135053_a("faction.bank.chest_level") + " 12";
         }
      }

      if(this.chestLevel < 3) {
         ClientEventHandler.STYLE.bindTexture("faction_chest");
         ModernGui.drawModalRectWithCustomSizedTextureWithTransparency(50.0F, 79.0F, 0, 349, 162, 18, 512.0F, 512.0F, false);
         if(mouseX >= this.guiLeft + 127 && mouseX <= this.guiLeft + 127 + 8 && mouseY >= this.guiTop + 79 + 3 && mouseY <= this.guiTop + 79 + 15) {
            tooltipToDraw = I18n.func_135053_a("faction.bank.chest_level") + " 9";
         }
      }

      if(this.chestLevel < 2) {
         ClientEventHandler.STYLE.bindTexture("faction_chest");
         ModernGui.drawModalRectWithCustomSizedTextureWithTransparency(50.0F, 61.0F, 0, 349, 162, 18, 512.0F, 512.0F, false);
         if(mouseX >= this.guiLeft + 127 && mouseX <= this.guiLeft + 127 + 8 && mouseY >= this.guiTop + 61 + 3 && mouseY <= this.guiTop + 61 + 15) {
            tooltipToDraw = I18n.func_135053_a("faction.bank.chest_level") + " 5";
         }
      }

      if(this.chestLogs != null) {
         GUIUtils.startGLScissor(this.guiLeft + 218, this.guiTop + 44, 80, 106);

         for(int j = 0; j < this.chestLogs.size(); ++j) {
            short offsetX = 218;
            Float offsetY = Float.valueOf((float)(44 + j * 17) + this.getSlideLogs());
            ClientEventHandler.STYLE.bindTexture("faction_chest");
            ModernGui.drawModalRectWithCustomSizedTexture((float)offsetX, (float)offsetY.intValue(), 218, 44, 80, 17, 512.0F, 512.0F, false);
            String[] itemInfos = ((String)this.chestLogs.get(j)).split("##");
            String sign = "\u00a7a+";
            if(itemInfos[5].equals("removed")) {
               sign = "\u00a7c-";
            }

            this.drawScaledString(sign + " \u00a77", offsetX + 5, offsetY.intValue() + 5, 11842740, 0.85F, false, false);
            ItemStack missile = new ItemStack(Integer.parseInt(itemInfos[0]), Integer.parseInt(itemInfos[2]), Integer.parseInt(itemInfos[1]));
            this.itemRenderer.func_82406_b(this.field_73886_k, this.field_73882_e.func_110434_K(), missile, offsetX + 13, offsetY.intValue());
            this.itemRenderer.func_77021_b(this.field_73886_k, this.field_73882_e.func_110434_K(), missile, offsetX + 13, offsetY.intValue());
            ClientEventHandler.STYLE.bindTexture("faction_chest");
            ModernGui.drawModalRectWithCustomSizedTexture((float)(offsetX + 66), (float)(offsetY.intValue() + 3), 14, 326, 10, 11, 512.0F, 512.0F, false);
            if(mouseX >= this.guiLeft + offsetX + 66 && mouseX <= this.guiLeft + offsetX + 66 + 10 && (float)mouseY >= (float)this.guiTop + offsetY.floatValue() + 3.0F && (float)mouseY <= (float)this.guiTop + offsetY.floatValue() + 3.0F + 11.0F) {
               tooltipToDrawLog = itemInfos[3] + "##" + itemInfos[4];
            }
         }

         GUIUtils.endGLScissor();
         if(mouseX > this.guiLeft + 218 && mouseX < this.guiLeft + 218 + 84 && mouseY > this.guiTop + 44 && mouseY < this.guiTop + 44 + 106) {
            this.scrollBarLogs.draw(mouseX, mouseY);
         }
      }

      if(!tooltipToDrawLog.isEmpty()) {
         this.drawTooltipLog(tooltipToDrawLog, mouseX, mouseY);
      }

      if(!tooltipToDraw.isEmpty()) {
         this.drawTooltip(tooltipToDraw, mouseX, mouseY);
      }

   }

   protected void func_74185_a(float prt, int x, int y) {
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      ClientEventHandler.STYLE.bindTexture("faction_chest");
      ModernGui.drawModalRectWithCustomSizedTexture((float)this.guiLeft, (float)this.guiTop, 0, 0, this.xSize, this.ySize, 512.0F, 512.0F, false);
   }

   private float getSlideLogs() {
      return this.chestLogs.size() > 6?(float)(-(this.chestLogs.size() - 6) * 17) * this.scrollBarLogs.getSliderValue():0.0F;
   }

   protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
      if(mouseButton == 0 && mouseX > this.guiLeft + 304 && mouseX < this.guiLeft + 304 + 9 && mouseY > this.guiTop - 6 && mouseY < this.guiTop - 6 + 10) {
         this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0F, 1.0F);
         Minecraft.func_71410_x().func_71373_a(new BankGUI());
      }

      super.func_73864_a(mouseX, mouseY, mouseButton);
   }

   public void drawTooltip(String text, int mouseX, int mouseY) {
      int mouseXGui = mouseX - this.guiLeft;
      int mouseYGui = mouseY - this.guiTop;
      this.drawHoveringText(Arrays.asList(new String[]{text.substring(0, 1).toUpperCase() + text.substring(1)}), mouseXGui, mouseYGui + 15, this.field_73886_k);
   }

   public void drawTooltipLog(String stringToDraw, int mouseX, int mouseY) {
      int mouseXGui = mouseX - this.guiLeft;
      int mouseYGui = mouseY - this.guiTop;
      String playerName = stringToDraw.split("##")[0];
      String time = stringToDraw.split("##")[1];
      String date = "\u00a78";
      long diff = System.currentTimeMillis() - Long.parseLong(time);
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
               date = date + " " + seconds + " " + I18n.func_135053_a("faction.common.seconds");
            } else {
               date = date + " " + minutes + " " + I18n.func_135053_a("faction.common.minutes");
            }
         } else {
            date = date + " " + hours + " " + I18n.func_135053_a("faction.common.hours");
         }
      } else {
         date = date + " " + days + " " + I18n.func_135053_a("faction.common.days");
      }

      this.drawHoveringText(Arrays.asList(new String[]{playerName + " - " + date}), mouseXGui, mouseYGui, this.field_73886_k);
   }

   public void drawScaledString(String text, int x, int y, int color, float scale, boolean centered, boolean shadow) {
      GL11.glPushMatrix();
      GL11.glScalef(scale, scale, scale);
      float newX = (float)x;
      if(centered) {
         newX = (float)x - (float)this.field_73886_k.func_78256_a(text) * scale / 2.0F;
      }

      if(shadow) {
         this.field_73886_k.func_85187_a(text, (int)(newX / scale), (int)((float)(y + 1) / scale), (color & 16579836) >> 2 | color & -16777216, false);
      }

      this.field_73886_k.func_85187_a(text, (int)(newX / scale), (int)((float)y / scale), color, false);
      GL11.glPopMatrix();
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
   }

   public boolean func_73868_f() {
      return false;
   }

}
