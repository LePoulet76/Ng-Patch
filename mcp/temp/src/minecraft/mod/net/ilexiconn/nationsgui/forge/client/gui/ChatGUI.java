package net.ilexiconn.nationsgui.forge.client.gui;

import cpw.mods.fml.common.network.PacketDispatcher;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import net.ilexiconn.nationsgui.forge.client.ClientData;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.data.Objective;
import net.ilexiconn.nationsgui.forge.client.gui.ChatGUI$Button;
import net.ilexiconn.nationsgui.forge.client.gui.ChatGUI$ScrollBar;
import net.ilexiconn.nationsgui.forge.client.gui.ChatGUI$SimpleButton;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.ilexiconn.nationsgui.forge.server.asm.NationsGUIClientHooks;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.ObjectivePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

public class ChatGUI extends GuiChat {

   private boolean displayText = false;
   private List<String> lines = new ArrayList();
   private ChatGUI$ScrollBar scrollBar;
   public static final int BOX_WIDTH = 400;
   public static final int BOX_HEIGHT = 250;
   private int heightModifier = 0;
   private RenderItem itemRenderer = new RenderItem();


   public ChatGUI() {}

   public ChatGUI(String par1Str) {
      super(par1Str);
   }

   public void func_73866_w_() {
      super.func_73866_w_();
      this.field_73887_h.clear();
      byte y = 15;
      if(ClientProxy.clientConfig.displayObjectives && !Minecraft.func_71410_x().field_71474_y.field_74330_P) {
         if(!this.displayText) {
            this.field_73887_h.add(new ChatGUI$Button(this, 0, 118, y, 16, 16));
            this.field_73887_h.add(new ChatGUI$Button(this, 1, 134, y, 16, 16));
            if(!ClientData.objectives.isEmpty()) {
               this.field_73887_h.add(new ChatGUI$Button(this, 2, 134, y + 19, 16, 15));
            }

            this.scrollBar = null;
         } else {
            this.heightModifier = ((Objective)ClientData.objectives.get(ClientData.currentObjectiveIndex)).getItemStack() != null?10:0;
            this.scrollBar = new ChatGUI$ScrollBar(this, (float)(this.field_73880_f / 2 + 200 - 15), (float)(this.field_73881_g / 2 - 125 + 30), 185);
            if(((Objective)ClientData.objectives.get(ClientData.currentObjectiveIndex)).getItemStack() == null) {
               this.field_73887_h.add(new ChatGUI$SimpleButton(this, 3, this.field_73880_f / 2 + 200 - 56, this.field_73881_g / 2 + 125 - 25, 50, 15, "Ok"));
            } else {
               this.field_73887_h.add(new ChatGUI$SimpleButton(this, 4, this.field_73880_f / 2 + 200 - 56, this.field_73881_g / 2 + 125 - 25, 50, 15, "Ok"));
               this.field_73887_h.add(new ChatGUI$SimpleButton(this, 3, this.field_73880_f / 2 + 200 - 56 - 105, this.field_73881_g / 2 + 125 - 25, 100, 15, I18n.func_135053_a("objectives.validate")));
            }
         }
      }

   }

   public void func_73871_c(int par1) {}

   public void func_73863_a(int par1, int par2, float par3) {
      int posYHelperAttacker;
      int x;
      int y;
      int y1;
      if(this.displayText) {
         Objective posXHelperAttacker = (Objective)ClientData.objectives.get(ClientData.currentObjectiveIndex);
         func_73734_a(this.field_73880_f / 2 - 200, this.field_73881_g / 2 - 125 + 25, this.field_73880_f / 2 + 200, this.field_73881_g / 2 + 125, -301989888);
         func_73734_a(this.field_73880_f / 2 - 200, this.field_73881_g / 2 - 125 + 5, this.field_73880_f / 2 + 200, this.field_73881_g / 2 - 125 + 25, -1728053248);
         ClientEventHandler.STYLE.bindTexture("hud2");
         GL11.glColor3f(1.0F, 1.0F, 1.0F);
         this.func_73729_b(this.field_73880_f / 2 - 200 + 5, this.field_73881_g / 2 - 125 + 8, 2, 20, 13, 13);
         this.func_73731_b(this.field_73886_k, posXHelperAttacker.getTitle(), this.field_73880_f / 2 - 200 + 20, this.field_73881_g / 2 - 125 + 11, 16777215);
         GUIUtils.startGLScissor(this.field_73880_f / 2 - 200, this.field_73881_g / 2 - 125 + 5 + 30, 380, 185);
         GL11.glPushMatrix();
         if(this.lines.size() > 11) {
            GL11.glTranslatef(0.0F, -this.scrollBar.sliderValue * (float)((this.lines.size() - 11) * 12 + this.heightModifier), 0.0F);
         }

         posYHelperAttacker = 0;

         for(Iterator entityPlayer = this.lines.iterator(); entityPlayer.hasNext(); ++posYHelperAttacker) {
            String handItem = (String)entityPlayer.next();
            this.func_73731_b(this.field_73886_k, handItem, this.field_73880_f / 2 - 200 + 10, this.field_73881_g / 2 - 125 + 35 + posYHelperAttacker * 12, 16777215);
         }

         ItemStack var14 = ((Objective)ClientData.objectives.get(ClientData.currentObjectiveIndex)).getItemStack();
         boolean var16 = false;
         if(var14 != null) {
            x = this.lines.size() * 12 + 2;
            y = this.field_73886_k.func_78256_a(I18n.func_135053_a("objectives.collect"));
            y1 = y + 16 + 4;
            this.field_73886_k.func_78276_b(I18n.func_135053_a("objectives.collect"), this.field_73880_f / 2 - 10 - y1 / 2, this.field_73881_g / 2 - 125 + 35 + x + 4, 16777215);
            this.itemRenderer.func_82406_b(this.field_73886_k, this.field_73882_e.func_110434_K(), var14, this.field_73880_f / 2 - 10 - y1 / 2 + y + 4, this.field_73881_g / 2 - 125 + 35 + x);
            this.itemRenderer.func_77021_b(this.field_73886_k, this.field_73882_e.func_110434_K(), var14, this.field_73880_f / 2 - 10 - y1 / 2 + y + 4, this.field_73881_g / 2 - 125 + 35 + x);
            int pX = this.field_73880_f / 2 - 10 - y1 / 2 + y + 4;
            int pY = this.field_73881_g / 2 - 125 + 35 + x - (int)(this.scrollBar.sliderValue * (float)((this.lines.size() - 11) * 12 + this.heightModifier));
            var16 = par1 >= pX && par1 <= pX + 18 && par2 >= pY && par2 <= pY + 18;
            GL11.glDisable(2896);
         }

         GL11.glPopMatrix();
         GUIUtils.endGLScissor();
         if(var16) {
            NationsGUIClientHooks.drawItemStackTooltip(var14, par1, par2);
            GL11.glDisable(2896);
         }

         this.scrollBar.draw(par1, par2);
      }

      int var13;
      if(ClientProxy.clientConfig.specialEnabled && ClientProxy.clientConfig.displayArmorInInfo) {
         var13 = this.field_73880_f - 146;
         posYHelperAttacker = this.field_73881_g - 100;
         EntityClientPlayerMP var15 = Minecraft.func_71410_x().field_71439_g;

         for(int var17 = 0; var17 < 4; ++var17) {
            ItemStack var18 = var15.func_82169_q(3 - var17);
            if(var18 != null) {
               y = var13 + 75 + 17 * var17;
               y1 = posYHelperAttacker + 5;
               if(par1 >= y && par1 <= y + 16 && par2 >= y1 && par2 <= y1 + 16) {
                  NationsGUIClientHooks.drawItemStackTooltip(var18, par1, par2);
               }
            }
         }

         ItemStack var19 = Minecraft.func_71410_x().field_71439_g.field_71071_by.func_70448_g();
         if(var19 != null) {
            x = var13 + 100;
            y = posYHelperAttacker + 30;
            if(par1 >= x && par1 <= x + 16 && par2 >= y && par2 <= y + 16) {
               NationsGUIClientHooks.drawItemStackTooltip(var19, par1, par2);
            }
         }
      }

      if(ClientData.currentAssault != null && !ClientData.currentAssault.isEmpty()) {
         if(!((String)ClientData.currentAssault.get("attackerHelpersCount")).equals("0")) {
            var13 = this.field_73880_f - 140 + 23 + this.field_73886_k.func_78256_a((String)ClientData.currentAssault.get("attackerFactionName")) - 1;
            posYHelperAttacker = (int)((double)this.field_73881_g * 0.4D) + 26;
            if(par1 >= var13 && par1 <= var13 + 19 && par2 >= posYHelperAttacker && par2 <= posYHelperAttacker + 3) {
               this.drawHoveringText(Arrays.asList(((String)ClientData.currentAssault.get("attackerHelpersName")).split(",")), par1, par2, this.field_73886_k);
            }
         }

         if(!((String)ClientData.currentAssault.get("defenderHelpersCount")).equals("0")) {
            var13 = this.field_73880_f - 140 + 23 + this.field_73886_k.func_78256_a((String)ClientData.currentAssault.get("defenderFactionName")) - 1;
            posYHelperAttacker = (int)((double)this.field_73881_g * 0.4D) + 26;
            if(par1 >= var13 && par1 <= var13 + 19 && par2 >= posYHelperAttacker && par2 <= posYHelperAttacker + 3) {
               this.drawHoveringText(Arrays.asList(((String)ClientData.currentAssault.get("defenderHelpersName")).split(",")), par1, par2, this.field_73886_k);
            }
         }
      }

      super.func_73863_a(par1, par2, par3);
   }

   protected void func_73875_a(GuiButton par1GuiButton) {
      super.func_73875_a(par1GuiButton);
      List objectives = ClientData.objectives;
      switch(par1GuiButton.field_73741_f) {
      case 0:
         if(ClientData.currentObjectiveIndex - 1 >= 0) {
            --ClientData.currentObjectiveIndex;
         } else {
            ClientData.currentObjectiveIndex = ClientData.objectives.size() - 1;
         }
         break;
      case 1:
         if(ClientData.currentObjectiveIndex + 1 < objectives.size()) {
            ++ClientData.currentObjectiveIndex;
         } else {
            ClientData.currentObjectiveIndex = 0;
         }
         break;
      case 2:
         this.displayText = true;
         this.generateTextLines();
         this.func_73866_w_();
         break;
      case 3:
         Objective current = (Objective)ClientData.objectives.get(ClientData.currentObjectiveIndex);
         if(current != null && current.getId().split("-").length == 3) {
            PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new ObjectivePacket()));
         }

         this.displayText = false;
         this.func_73866_w_();
         break;
      case 4:
         this.displayText = false;
         this.func_73866_w_();
      }

   }

   private void generateTextLines() {
      this.lines.clear();
      short lineWidth = 360;
      int spaceWidth = this.field_73886_k.func_78256_a(" ");
      Objective current = (Objective)ClientData.objectives.get(ClientData.currentObjectiveIndex);
      String[] l = current.getText().split("\n");
      String[] var5 = l;
      int var6 = l.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         String line = var5[var7];
         int spaceLeft = lineWidth;
         String[] words = line.split(" ");
         StringBuilder stringBuilder = new StringBuilder();
         String[] var12 = words;
         int var13 = words.length;

         for(int var14 = 0; var14 < var13; ++var14) {
            String word = var12[var14];
            int wordWidth = this.field_73886_k.func_78256_a(word);
            if(wordWidth + spaceWidth > spaceLeft) {
               this.lines.add(stringBuilder.toString());
               stringBuilder = new StringBuilder();
               spaceLeft = lineWidth - wordWidth;
            } else {
               spaceLeft -= wordWidth + spaceWidth;
            }

            stringBuilder.append(word);
            stringBuilder.append(' ');
         }

         this.lines.add(stringBuilder.toString());
      }

   }

   protected void func_73864_a(int par1, int par2, int par3) {
      super.func_73864_a(par1, par2, par3);
   }

   public boolean isDisplayingText() {
      return this.displayText;
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

   // $FF: synthetic method
   static FontRenderer access$000(ChatGUI x0) {
      return x0.field_73886_k;
   }

   // $FF: synthetic method
   static FontRenderer access$100(ChatGUI x0) {
      return x0.field_73886_k;
   }
}
