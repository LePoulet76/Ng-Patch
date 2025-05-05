package net.ilexiconn.nationsgui.forge.client.gui;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.ChallengeAcceptPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.ChallengeDataPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.ChallengeRequestPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class ChallengeGui extends GuiScreen {

   protected int xSize = 171;
   protected int ySize = 172;
   private int guiLeft;
   private int guiTop;
   private RenderItem itemRenderer = new RenderItem();
   public static boolean loaded = false;
   public static ArrayList<String> kits = new ArrayList();
   public static String playerAttStats = "0";
   public static String playerDefStats = "0";
   public boolean kitExpanded = false;
   public String selectedKit = "";
   public String hoveredKit = "";
   public GuiTextField betInput;
   private String playerAtt;
   private String playerDef;
   private boolean isSetup;
   private String duelInfos;


   public ChallengeGui(String playerAtt, String playerDef, boolean isSetup, String duelInfos) {
      this.playerAtt = playerAtt;
      this.playerDef = playerDef;
      this.isSetup = isSetup;
      this.duelInfos = duelInfos;
      loaded = false;
   }

   public void func_73876_c() {
      this.betInput.func_73780_a();
   }

   public void func_73866_w_() {
      super.func_73866_w_();
      PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new ChallengeDataPacket(this.playerAtt, this.playerDef)));
      this.guiLeft = (this.field_73880_f - this.xSize) / 2;
      this.guiTop = (this.field_73881_g - this.ySize) / 2;
      this.betInput = new GuiTextField(this.field_73886_k, this.guiLeft + 58, this.guiTop + 100, 75, 10);
      this.betInput.func_73786_a(false);
      this.betInput.func_73804_f(8);
      if(this.isSetup) {
         this.betInput.func_73782_a("0");
      } else {
         this.betInput.func_73782_a(this.duelInfos.split("##")[1]);
         this.selectedKit = this.duelInfos.split("##")[0];
      }

   }

   public void func_73863_a(int mouseX, int mouseY, float par3) {
      this.func_73873_v_();
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      ClientEventHandler.STYLE.bindTexture("challenge");
      ModernGui.drawModalRectWithCustomSizedTexture((float)this.guiLeft, (float)this.guiTop, 0, 0, this.xSize, this.ySize, 512.0F, 512.0F, false);
      GL11.glPushMatrix();
      GL11.glTranslatef((float)(this.guiLeft + 14), (float)(this.guiTop + 128), 0.0F);
      GL11.glRotatef(-90.0F, 0.0F, 0.0F, 1.0F);
      GL11.glTranslatef((float)(-(this.guiLeft + 14)), (float)(-(this.guiTop + 128)), 0.0F);
      this.drawScaledString(I18n.func_135053_a("challenge.title"), this.guiLeft + 14, this.guiTop + 128, 16777215, 1.5F, false, false);
      GL11.glPopMatrix();
      ArrayList tooltipToDraw = new ArrayList();
      if(mouseX >= this.guiLeft + 278 && mouseX <= this.guiLeft + 278 + 9 && mouseY >= this.guiTop - 8 && mouseY <= this.guiTop - 8 + 10) {
         ClientEventHandler.STYLE.bindTexture("warzones");
         ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 278), (float)(this.guiTop - 8), 0, 261, 9, 10, 512.0F, 512.0F, false);
      } else {
         ClientEventHandler.STYLE.bindTexture("warzones");
         ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 278), (float)(this.guiTop - 8), 0, 251, 9, 10, 512.0F, 512.0F, false);
      }

      if(loaded) {
         this.drawScaledString(I18n.func_135053_a("Duel"), this.guiLeft + 56, this.guiTop + 27, 11476767, 1.0F, false, true);
         ClientEventHandler.STYLE.bindTexture("challenge");
         this.drawScaledString(playerAttStats + "%", this.guiLeft + 73, this.guiTop + 38, 16777215, 0.7F, true, false);

         ResourceLocation selectedKitText;
         try {
            selectedKitText = AbstractClientPlayer.field_110314_b;
            selectedKitText = AbstractClientPlayer.func_110311_f(this.playerAtt);
            AbstractClientPlayer.func_110304_a(selectedKitText, this.playerAtt);
            Minecraft.func_71410_x().field_71446_o.func_110577_a(selectedKitText);
            this.field_73882_e.func_110434_K().func_110577_a(selectedKitText);
            GUIUtils.drawScaledCustomSizeModalRect(this.guiLeft + 65 + 14, this.guiTop + 45 + 14, 8.0F, 16.0F, 8, -8, -14, -14, 64.0F, 64.0F);
            GUIUtils.drawScaledCustomSizeModalRect(this.guiLeft + 65 + 14, this.guiTop + 45 + 14, 40.0F, 16.0F, 8, -8, -14, -14, 64.0F, 64.0F);
         } catch (Exception var8) {
            System.out.println(var8.getMessage());
         }

         this.drawScaledString(playerDefStats + "%", this.guiLeft + 134, this.guiTop + 38, 16777215, 0.7F, true, false);

         try {
            selectedKitText = AbstractClientPlayer.field_110314_b;
            selectedKitText = AbstractClientPlayer.func_110311_f(this.playerDef);
            AbstractClientPlayer.func_110304_a(selectedKitText, this.playerDef);
            Minecraft.func_71410_x().field_71446_o.func_110577_a(selectedKitText);
            this.field_73882_e.func_110434_K().func_110577_a(selectedKitText);
            GUIUtils.drawScaledCustomSizeModalRect(this.guiLeft + 126 + 14, this.guiTop + 45 + 14, 8.0F, 16.0F, 8, -8, -14, -14, 64.0F, 64.0F);
            GUIUtils.drawScaledCustomSizeModalRect(this.guiLeft + 126 + 14, this.guiTop + 45 + 14, 40.0F, 16.0F, 8, -8, -14, -14, 64.0F, 64.0F);
         } catch (Exception var7) {
            System.out.println(var7.getMessage());
         }

         ClientEventHandler.STYLE.bindTexture("challenge");
         if(this.isSetup) {
            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 55), (float)(this.guiTop + 132), 0, 175, 94, 15, 512.0F, 512.0F, false);
            if(this.selectedKit.isEmpty() || mouseX >= this.guiLeft + 55 && mouseX <= this.guiLeft + 55 + 94 && mouseY >= this.guiTop + 132 && mouseY <= this.guiTop + 132 + 15) {
               ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 55), (float)(this.guiTop + 132), 0, 190, 94, 15, 512.0F, 512.0F, false);
            }

            this.drawScaledString(I18n.func_135053_a("challenge.send_duel"), this.guiLeft + 102, this.guiTop + 136, 16777215, 1.0F, true, false);
         } else {
            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 55), (float)(this.guiTop + 132), 94, 175, 94, 15, 512.0F, 512.0F, false);
            if(mouseX >= this.guiLeft + 55 && mouseX <= this.guiLeft + 55 + 94 && mouseY >= this.guiTop + 132 && mouseY <= this.guiTop + 132 + 15) {
               ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 55), (float)(this.guiTop + 132), 94, 190, 94, 15, 512.0F, 512.0F, false);
            }

            this.drawScaledString(I18n.func_135053_a("challenge.accept_duel"), this.guiLeft + 102, this.guiTop + 136, 16777215, 1.0F, true, false);
         }

         this.betInput.func_73795_f();
         this.hoveredKit = "";
         String var9 = I18n.func_135053_a("challenge.placeholder_kit");
         if(!this.selectedKit.isEmpty()) {
            var9 = this.selectedKit;
         }

         this.drawScaledString(var9, this.guiLeft + 60, this.guiTop + 71, 16777215, 1.0F, false, false);
         if(this.isSetup && this.kitExpanded) {
            for(int i = 0; i < kits.size(); ++i) {
               ClientEventHandler.STYLE.bindTexture("challenge");
               ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 55), (float)(this.guiTop + 82 + 14 * i), 181, 28, 94, 14, 512.0F, 512.0F, false);
               this.drawScaledString((String)kits.get(i), this.guiLeft + 60, this.guiTop + 81 + 14 * i + 4, 16777215, 0.9F, false, false);
               if(mouseX >= this.guiLeft + 55 && mouseX <= this.guiLeft + 55 + 94 && mouseY >= this.guiTop + 81 + 14 * i && mouseY <= this.guiTop + 81 + 14 * i + 14) {
                  this.hoveredKit = (String)kits.get(i);
               }
            }
         }

         if(!tooltipToDraw.isEmpty()) {
            this.drawHoveringText(tooltipToDraw, mouseX, mouseY, this.field_73886_k);
         }
      }

      super.func_73863_a(mouseX, mouseY, par3);
      GL11.glEnable(2896);
      RenderHelper.func_74519_b();
   }

   protected void func_73869_a(char typedChar, int keyCode) {
      this.betInput.func_73802_a(typedChar, keyCode);
      super.func_73869_a(typedChar, keyCode);
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

   protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
      if(mouseButton == 0) {
         if(mouseX > this.guiLeft + 278 && mouseX < this.guiLeft + 278 + 9 && mouseY > this.guiTop - 8 && mouseY < this.guiTop - 8 + 10) {
            this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0F, 1.0F);
            Minecraft.func_71410_x().func_71373_a((GuiScreen)null);
         } else if(mouseX > this.guiLeft + 134 && mouseX < this.guiLeft + 134 + 15 && mouseY > this.guiTop + 67 && mouseY < this.guiTop + 67 + 14) {
            this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0F, 1.0F);
            this.kitExpanded = !this.kitExpanded;
         } else if(!this.hoveredKit.isEmpty()) {
            this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0F, 1.0F);
            this.selectedKit = this.hoveredKit;
            this.kitExpanded = false;
            this.hoveredKit = "";
         } else if(mouseX >= this.guiLeft + 55 && mouseX <= this.guiLeft + 55 + 94 && mouseY >= this.guiTop + 132 && mouseY <= this.guiTop + 132 + 15) {
            if(this.isSetup) {
               if(this.betInput.func_73781_b().isEmpty()) {
                  this.betInput.func_73782_a("0");
               }

               if(!this.selectedKit.isEmpty() && this.isNumeric(this.betInput.func_73781_b())) {
                  this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0F, 1.0F);
                  Minecraft.func_71410_x().func_71373_a((GuiScreen)null);
                  PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new ChallengeRequestPacket(this.playerAtt, this.playerDef, this.selectedKit, Integer.parseInt(this.betInput.func_73781_b()))));
               }
            } else {
               this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0F, 1.0F);
               Minecraft.func_71410_x().func_71373_a((GuiScreen)null);
               PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new ChallengeAcceptPacket(this.playerAtt, this.playerDef, this.selectedKit, Integer.parseInt(this.betInput.func_73781_b()))));
            }
         }
      }

      if(this.isSetup) {
         this.betInput.func_73793_a(mouseX, mouseY, mouseButton);
      }

      super.func_73864_a(mouseX, mouseY, mouseButton);
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

   public boolean isNumeric(String str) {
      if(str != null && str.length() != 0) {
         char[] var2 = str.toCharArray();
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            char c = var2[var4];
            if(!Character.isDigit(c)) {
               return false;
            }
         }

         if(Integer.parseInt(str) < 0) {
            return false;
         } else {
            return true;
         }
      } else {
         return false;
      }
   }

}
