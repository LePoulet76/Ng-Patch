package net.ilexiconn.nationsgui.forge.client.gui.enterprise;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScrollBarFaction;
import net.ilexiconn.nationsgui.forge.client.gui.enterprise.EnterpriseGui;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.EnterpriseContractCreate_Pvp_Packet;
import net.ilexiconn.nationsgui.forge.server.packet.impl.EnterpriseContractFormPvpPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.I18n;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class EnterpriseContractForm_Pvp_Gui extends GuiScreen {

   private GuiButton cancelButton;
   private GuiButton validButton;
   private GuiTextField priceInput;
   private GuiScrollBarFaction scrollBarDelay;
   private RenderItem itemRenderer = new RenderItem();
   protected int xSize = 371;
   protected int ySize = 223;
   private int guiLeft;
   private int guiTop;
   public static boolean loaded = false;
   private boolean conditionExpanded = false;
   private String selectedCondition = "";
   private String hoveredCondition = "";
   private ArrayList<String> conditions = new ArrayList();
   public static HashMap<String, String> data = new HashMap();


   public void func_73876_c() {
      this.priceInput.func_73780_a();
   }

   public void func_73866_w_() {
      super.func_73866_w_();
      loaded = false;
      PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new EnterpriseContractFormPvpPacket((String)EnterpriseGui.enterpriseInfos.get("name"))));
      this.guiLeft = (this.field_73880_f - this.xSize) / 2;
      this.guiTop = (this.field_73881_g - this.ySize) / 2;
      this.cancelButton = new GuiButton(0, this.guiLeft + 197, this.guiTop + 197, 80, 20, I18n.func_135053_a("enterprise.contract.action.cancel"));
      this.validButton = new GuiButton(1, this.guiLeft + 282, this.guiTop + 197, 80, 20, I18n.func_135053_a("enterprise.contract.action.valid"));
      this.scrollBarDelay = new GuiScrollBarFaction((float)(this.guiLeft + 357), (float)(this.guiTop + 184), 90);
      this.priceInput = new GuiTextField(this.field_73886_k, this.guiLeft + 219, this.guiTop + 166, 58, 10);
      this.priceInput.func_73786_a(false);
      this.priceInput.func_73804_f(7);
      this.priceInput.func_73782_a("0");
      this.conditions.addAll(Arrays.asList(new String[]{"victory", "participation"}));
      this.selectedCondition = (String)this.conditions.get(0);
   }

   public void func_73863_a(int mouseX, int mouseY, float par3) {
      this.func_73873_v_();
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      ClientEventHandler.STYLE.bindTexture("enterprise_contract_form");
      ModernGui.drawModalRectWithCustomSizedTextureWithTransparency((float)this.guiLeft, (float)this.guiTop, 0, 0, this.xSize, this.ySize, 512.0F, 512.0F, false);
      this.hoveredCondition = "";
      List tooltipToDraw = null;
      ClientEventHandler.STYLE.bindTexture("enterprise_contract_form");
      ModernGui.drawModalRectWithCustomSizedTexture((float)((int)((double)(this.guiLeft + 91) - (double)this.field_73886_k.func_78256_a(I18n.func_135053_a("enterprise.contract.title.services")) * 1.2D / 2.0D - 8.0D - 2.0D)), (float)(this.guiTop + 16), 0, 276, 16, 16, 512.0F, 512.0F, false);
      this.drawScaledString(I18n.func_135053_a("enterprise.contract.title.services"), this.guiLeft + 91 + 8, this.guiTop + 21, 16777215, 1.2F, true, false);
      int index = 0;
      String[] type = ((String)EnterpriseGui.enterpriseInfos.get("services")).split("##");
      int i = type.length;

      int offsetX;
      for(offsetX = 0; offsetX < i; ++offsetX) {
         String offsetY = type[offsetX];
         this.drawScaledString(offsetY.replace("&", "\u00a7"), this.guiLeft + 6, this.guiTop + 54 + index * 9, 16777215, 0.8F, false, false);
         ++index;
      }

      this.drawScaledString((String)EnterpriseGui.enterpriseInfos.get("name"), this.guiLeft + 280, this.guiTop + 11, 16777215, 1.7F, true, true);
      String var10 = I18n.func_135053_a("enterprise.type." + ((String)EnterpriseGui.enterpriseInfos.get("type")).toLowerCase());
      ClientEventHandler.STYLE.bindTexture("enterprise_main");
      ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 280 - this.field_73886_k.func_78256_a(var10) / 2 - 7 - 4), (float)(this.guiTop + 23), EnterpriseGui.getTypeOffsetX((String)EnterpriseGui.enterpriseInfos.get("type")), 442, 16, 16, 512.0F, 512.0F, false);
      this.drawScaledString(I18n.func_135053_a("enterprise.type." + ((String)EnterpriseGui.enterpriseInfos.get("type")).toLowerCase()), this.guiLeft + 280 + 7, this.guiTop + 29, 11842740, 1.0F, true, false);
      if(loaded) {
         if(data.containsKey("error")) {
            this.validButton.field_73742_g = false;
            if(!this.conditionExpanded && mouseX >= this.guiLeft + 282 && mouseX <= this.guiLeft + 282 + 80 && mouseY >= this.guiTop + 197 && mouseY <= this.guiTop + 197 + 20) {
               tooltipToDraw = Arrays.asList(new String[]{I18n.func_135053_a("enterprise.contract.error." + (String)data.get("error"))});
            }
         }

         this.drawScaledString(I18n.func_135053_a("enterprise.contract.label.current_assault"), this.guiLeft + 197, this.guiTop + 53, 1644825, 1.0F, false, false);
         ModernGui.drawNGBlackSquare(this.guiLeft + 197, this.guiTop + 62, 165, 40);
         if(data.containsKey("opponentFaction")) {
            this.drawScaledString("\u00a74VS \u00a7c" + (String)data.get("opponentFaction"), this.guiLeft + 280, this.guiTop + 77, 16777215, 1.0F, true, false);
         } else {
            this.drawScaledString(I18n.func_135053_a("enterprise.create.no_assault"), this.guiLeft + 280, this.guiTop + 77, 16777215, 1.0F, true, false);
         }

         this.drawScaledString(I18n.func_135053_a("enterprise.contract.label.price_pvp"), this.guiLeft + 197, this.guiTop + 151, 1644825, 1.0F, false, false);
         ClientEventHandler.STYLE.bindTexture("enterprise_contract_form");
         ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 197), (float)(this.guiTop + 160), 0, 234, 80, 20, 512.0F, 512.0F, false);
         this.drawScaledString("\u00a7a$", this.guiLeft + 207, this.guiTop + 165, 16777215, 1.3F, true, false);
         this.priceInput.func_73795_f();
         this.drawScaledString(I18n.func_135053_a("enterprise.contract.label.condition"), this.guiLeft + 282, this.guiTop + 151, 1644825, 1.0F, false, false);
         ClientEventHandler.STYLE.bindTexture("enterprise_contract_form");
         ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 282), (float)(this.guiTop + 160), 0, 255, 80, 20, 512.0F, 512.0F, false);
         if(this.selectedCondition != null) {
            this.drawScaledString(I18n.func_135053_a("enterprise.contract.condition." + this.selectedCondition), this.guiLeft + 285, this.guiTop + 166, 16777215, 1.0F, false, false);
         }

         this.cancelButton.func_73737_a(Minecraft.func_71410_x(), mouseX, mouseY);
         this.validButton.func_73737_a(Minecraft.func_71410_x(), mouseX, mouseY);
         if(this.conditionExpanded) {
            ClientEventHandler.STYLE.bindTexture("enterprise_contract_form");
            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 282), (float)(this.guiTop + 179), 247, 234, 80, 99, 512.0F, 512.0F, false);
            GUIUtils.startGLScissor(this.guiLeft + 283, this.guiTop + 180, 74, 97);

            for(i = 0; i < this.conditions.size(); ++i) {
               offsetX = this.guiLeft + 283;
               Float var11 = Float.valueOf((float)(this.guiTop + 180 + i * 20) + this.getSlideDelay());
               ClientEventHandler.STYLE.bindTexture("enterprise_contract_form");
               ModernGui.drawModalRectWithCustomSizedTexture((float)offsetX, (float)var11.intValue(), 248, 235, 74, 20, 512.0F, 512.0F, false);
               this.drawScaledString(I18n.func_135053_a("enterprise.contract.condition." + (String)this.conditions.get(i)), offsetX + 2, var11.intValue() + 5, 16777215, 1.0F, false, false);
               if(mouseX > offsetX && mouseX < offsetX + 74 && (float)mouseY > var11.floatValue() && (float)mouseY < var11.floatValue() + 20.0F) {
                  this.hoveredCondition = (String)this.conditions.get(i);
               }
            }

            GUIUtils.endGLScissor();
            this.scrollBarDelay.draw(mouseX, mouseY);
         }

         if(tooltipToDraw != null) {
            this.drawHoveringText(tooltipToDraw, mouseX, mouseY, this.field_73886_k);
         }
      }

   }

   protected void func_73869_a(char typedChar, int keyCode) {
      this.priceInput.func_73802_a(typedChar, keyCode);
      super.func_73869_a(typedChar, keyCode);
   }

   private float getSlideDelay() {
      return this.conditions.size() > 5?(float)(-(this.conditions.size() - 5) * 20) * this.scrollBarDelay.getSliderValue():0.0F;
   }

   protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
      if(mouseButton == 0) {
         if(!this.conditionExpanded && mouseX > this.guiLeft + 197 && mouseX < this.guiLeft + 197 + 80 && mouseY > this.guiTop + 197 && mouseY < this.guiTop + 197 + 20) {
            this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0F, 1.0F);
            Minecraft.func_71410_x().func_71373_a(new EnterpriseGui((String)EnterpriseGui.enterpriseInfos.get("name")));
         }

         if(this.validButton.field_73742_g && !this.conditionExpanded && this.isNumeric(this.priceInput.func_73781_b()) && mouseX > this.guiLeft + 282 && mouseX < this.guiLeft + 282 + 80 && mouseY > this.guiTop + 197 && mouseY < this.guiTop + 197 + 20) {
            this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0F, 1.0F);
            String description = "PVP##" + (String)data.get("playerFaction") + "##" + (String)data.get("factionHelpers") + "##" + (String)data.get("opponentFaction") + "##" + (String)data.get("opponentHelpers") + "##" + this.priceInput.func_73781_b() + "##" + this.selectedCondition;
            EnterpriseGui.lastContractDemand = Long.valueOf(System.currentTimeMillis());
            PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new EnterpriseContractCreate_Pvp_Packet((String)EnterpriseGui.enterpriseInfos.get("name"), description, Integer.valueOf(Integer.parseInt(this.priceInput.func_73781_b())), this.selectedCondition)));
            Minecraft.func_71410_x().func_71373_a((GuiScreen)null);
         }

         if(mouseX > this.guiLeft + 343 && mouseX < this.guiLeft + 343 + 19 && mouseY > this.guiTop + 160 && mouseY < this.guiTop + 160 + 20) {
            this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0F, 1.0F);
            this.conditionExpanded = !this.conditionExpanded;
         }

         if(this.conditionExpanded && this.hoveredCondition != null && !this.hoveredCondition.isEmpty()) {
            this.selectedCondition = this.hoveredCondition;
            this.hoveredCondition = "";
            this.conditionExpanded = false;
         }
      }

      this.priceInput.func_73793_a(mouseX, mouseY, mouseButton);
      super.func_73864_a(mouseX, mouseY, mouseButton);
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

         if(Integer.parseInt(str) < 5000) {
            return false;
         } else {
            return true;
         }
      } else {
         return false;
      }
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
