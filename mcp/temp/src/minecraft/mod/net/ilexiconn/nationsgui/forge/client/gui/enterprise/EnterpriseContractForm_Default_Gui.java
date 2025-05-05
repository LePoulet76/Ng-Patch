package net.ilexiconn.nationsgui.forge.client.gui.enterprise;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScrollBarFaction;
import net.ilexiconn.nationsgui.forge.client.gui.enterprise.EnterpriseGui;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.EnterpriseContractCreate_Default_Packet;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class EnterpriseContractForm_Default_Gui extends GuiScreen {

   private GuiButton cancelButton;
   private GuiButton validButton;
   private GuiTextField priceInput;
   private GuiScrollBarFaction scrollBarDelay;
   protected int xSize = 371;
   protected int ySize = 223;
   private int guiLeft;
   private int guiTop;
   private boolean delayExpanded = false;
   private String selectedDelay = "";
   private String hoveredDelay = "";
   private ArrayList<String> delays = new ArrayList();
   private ArrayList<GuiTextField> linesTextField = new ArrayList();


   public void func_73876_c() {
      this.priceInput.func_73780_a();
      Iterator var1 = this.linesTextField.iterator();

      while(var1.hasNext()) {
         GuiTextField lineTextField = (GuiTextField)var1.next();
         lineTextField.func_73780_a();
      }

   }

   public void func_73866_w_() {
      super.func_73866_w_();
      this.guiLeft = (this.field_73880_f - this.xSize) / 2;
      this.guiTop = (this.field_73881_g - this.ySize) / 2;
      this.cancelButton = new GuiButton(0, this.guiLeft + 197, this.guiTop + 197, 80, 20, I18n.func_135053_a("enterprise.contract.action.cancel"));
      this.validButton = new GuiButton(1, this.guiLeft + 282, this.guiTop + 197, 80, 20, I18n.func_135053_a("enterprise.contract.action.valid"));
      this.scrollBarDelay = new GuiScrollBarFaction((float)(this.guiLeft + 357), (float)(this.guiTop + 184), 90);
      this.priceInput = new GuiTextField(this.field_73886_k, this.guiLeft + 219, this.guiTop + 166, 58, 10);
      this.priceInput.func_73786_a(false);
      this.priceInput.func_73804_f(7);
      this.priceInput.func_73782_a("0");
      this.delays.addAll(Arrays.asList(new String[]{"3h", "6h", "12h", "1j", "2j", "3j", "5j", "1s", "2s", "3s", "1m"}));
      this.selectedDelay = (String)this.delays.get(0);

      for(int i = 0; i < 8; ++i) {
         GuiTextField lineTextField = new GuiTextField(this.field_73886_k, this.guiLeft + 200, this.guiTop + 65 + i * 10, 160, 10);
         lineTextField.func_73786_a(false);
         lineTextField.func_73804_f(32);
         lineTextField.func_73797_d();
         this.linesTextField.add(lineTextField);
      }

   }

   public void func_73863_a(int mouseX, int mouseY, float par3) {
      this.func_73873_v_();
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      ClientEventHandler.STYLE.bindTexture("enterprise_contract_form");
      ModernGui.drawModalRectWithCustomSizedTextureWithTransparency((float)this.guiLeft, (float)this.guiTop, 0, 0, this.xSize, this.ySize, 512.0F, 512.0F, false);
      this.hoveredDelay = "";
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
      String var9 = I18n.func_135053_a("enterprise.type." + ((String)EnterpriseGui.enterpriseInfos.get("type")).toLowerCase());
      ClientEventHandler.STYLE.bindTexture("enterprise_main");
      ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 280 - this.field_73886_k.func_78256_a(var9) / 2 - 7 - 4), (float)(this.guiTop + 23), EnterpriseGui.getTypeOffsetX((String)EnterpriseGui.enterpriseInfos.get("type")), 442, 16, 16, 512.0F, 512.0F, false);
      this.drawScaledString(I18n.func_135053_a("enterprise.type." + ((String)EnterpriseGui.enterpriseInfos.get("type")).toLowerCase()), this.guiLeft + 280 + 7, this.guiTop + 29, 11842740, 1.0F, true, false);
      this.drawScaledString(I18n.func_135053_a("enterprise.contract.label.demand"), this.guiLeft + 197, this.guiTop + 53, 1644825, 1.0F, false, false);
      ClientEventHandler.STYLE.bindTexture("enterprise_contract_form");
      ModernGui.drawNGBlackSquare(this.guiLeft + 197, this.guiTop + 62, 165, 85);
      Iterator var10 = this.linesTextField.iterator();

      while(var10.hasNext()) {
         GuiTextField var11 = (GuiTextField)var10.next();
         var11.func_73795_f();
      }

      this.drawScaledString(I18n.func_135053_a("enterprise.contract.label.price"), this.guiLeft + 197, this.guiTop + 151, 1644825, 1.0F, false, false);
      ClientEventHandler.STYLE.bindTexture("enterprise_contract_form");
      ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 197), (float)(this.guiTop + 160), 0, 234, 80, 20, 512.0F, 512.0F, false);
      this.drawScaledString("\u00a7a$", this.guiLeft + 207, this.guiTop + 165, 16777215, 1.3F, true, false);
      this.priceInput.func_73795_f();
      this.drawScaledString(I18n.func_135053_a("enterprise.contract.label.delay"), this.guiLeft + 282, this.guiTop + 151, 1644825, 1.0F, false, false);
      ClientEventHandler.STYLE.bindTexture("enterprise_contract_form");
      ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 282), (float)(this.guiTop + 160), 0, 255, 80, 20, 512.0F, 512.0F, false);
      if(this.selectedDelay != null) {
         this.drawScaledString(I18n.func_135053_a("enterprise.contract.delay." + this.selectedDelay), this.guiLeft + 285, this.guiTop + 166, 16777215, 1.0F, false, false);
      }

      this.cancelButton.func_73737_a(Minecraft.func_71410_x(), mouseX, mouseY);
      this.validButton.func_73737_a(Minecraft.func_71410_x(), mouseX, mouseY);
      if(this.delayExpanded) {
         ClientEventHandler.STYLE.bindTexture("enterprise_contract_form");
         ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 282), (float)(this.guiTop + 179), 247, 234, 80, 99, 512.0F, 512.0F, false);
         GUIUtils.startGLScissor(this.guiLeft + 283, this.guiTop + 180, 74, 97);

         for(i = 0; i < this.delays.size(); ++i) {
            offsetX = this.guiLeft + 283;
            Float var12 = Float.valueOf((float)(this.guiTop + 180 + i * 20) + this.getSlideDelay());
            ClientEventHandler.STYLE.bindTexture("enterprise_contract_form");
            ModernGui.drawModalRectWithCustomSizedTexture((float)offsetX, (float)var12.intValue(), 248, 235, 74, 20, 512.0F, 512.0F, false);
            this.drawScaledString(I18n.func_135053_a("enterprise.contract.delay." + (String)this.delays.get(i)), offsetX + 2, var12.intValue() + 5, 16777215, 1.0F, false, false);
            if(mouseX > offsetX && mouseX < offsetX + 74 && (float)mouseY > var12.floatValue() && (float)mouseY < var12.floatValue() + 20.0F) {
               this.hoveredDelay = (String)this.delays.get(i);
            }
         }

         GUIUtils.endGLScissor();
         this.scrollBarDelay.draw(mouseX, mouseY);
      }

   }

   protected void func_73869_a(char typedChar, int keyCode) {
      this.priceInput.func_73802_a(typedChar, keyCode);
      Iterator var3 = this.linesTextField.iterator();

      while(var3.hasNext()) {
         GuiTextField lineTextField = (GuiTextField)var3.next();
         lineTextField.func_73802_a(typedChar, keyCode);
      }

      super.func_73869_a(typedChar, keyCode);
   }

   private float getSlideDelay() {
      return this.delays.size() > 5?(float)(-(this.delays.size() - 5) * 20) * this.scrollBarDelay.getSliderValue():0.0F;
   }

   protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
      if(mouseButton == 0) {
         if(!this.delayExpanded && mouseX > this.guiLeft + 197 && mouseX < this.guiLeft + 197 + 80 && mouseY > this.guiTop + 197 && mouseY < this.guiTop + 197 + 20) {
            this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0F, 1.0F);
            Minecraft.func_71410_x().func_71373_a(new EnterpriseGui((String)EnterpriseGui.enterpriseInfos.get("name")));
         }

         if(this.validButton.field_73742_g && !this.delayExpanded && this.isNumeric(this.priceInput.func_73781_b()) && mouseX > this.guiLeft + 282 && mouseX < this.guiLeft + 282 + 80 && mouseY > this.guiTop + 197 && mouseY < this.guiTop + 197 + 20) {
            this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0F, 1.0F);
            String description = "";

            GuiTextField lineTextField1;
            for(Iterator lineTextField = this.linesTextField.iterator(); lineTextField.hasNext(); description = description + " " + lineTextField1.func_73781_b()) {
               lineTextField1 = (GuiTextField)lineTextField.next();
            }

            description = description.trim();
            Long lineTextField2 = Long.valueOf(System.currentTimeMillis());
            String lineTextField3 = this.selectedDelay;
            byte var7 = -1;
            switch(lineTextField3.hashCode()) {
            case 1625:
               if(lineTextField3.equals("1j")) {
                  var7 = 3;
               }
               break;
            case 1628:
               if(lineTextField3.equals("1m")) {
                  var7 = 10;
               }
               break;
            case 1634:
               if(lineTextField3.equals("1s")) {
                  var7 = 7;
               }
               break;
            case 1656:
               if(lineTextField3.equals("2j")) {
                  var7 = 4;
               }
               break;
            case 1665:
               if(lineTextField3.equals("2s")) {
                  var7 = 8;
               }
               break;
            case 1685:
               if(lineTextField3.equals("3h")) {
                  var7 = 0;
               }
               break;
            case 1687:
               if(lineTextField3.equals("3j")) {
                  var7 = 5;
               }
               break;
            case 1696:
               if(lineTextField3.equals("3s")) {
                  var7 = 9;
               }
               break;
            case 1749:
               if(lineTextField3.equals("5j")) {
                  var7 = 6;
               }
               break;
            case 1778:
               if(lineTextField3.equals("6h")) {
                  var7 = 1;
               }
               break;
            case '\ube67':
               if(lineTextField3.equals("12h")) {
                  var7 = 2;
               }
            }

            switch(var7) {
            case 0:
               lineTextField2 = Long.valueOf(lineTextField2.longValue() + 10800000L);
               break;
            case 1:
               lineTextField2 = Long.valueOf(lineTextField2.longValue() + 21600000L);
               break;
            case 2:
               lineTextField2 = Long.valueOf(lineTextField2.longValue() + 43200000L);
               break;
            case 3:
               lineTextField2 = Long.valueOf(lineTextField2.longValue() + 86400000L);
               break;
            case 4:
               lineTextField2 = Long.valueOf(lineTextField2.longValue() + 172800000L);
               break;
            case 5:
               lineTextField2 = Long.valueOf(lineTextField2.longValue() + 259200000L);
               break;
            case 6:
               lineTextField2 = Long.valueOf(lineTextField2.longValue() + 432000000L);
               break;
            case 7:
               lineTextField2 = Long.valueOf(lineTextField2.longValue() + 604800000L);
               break;
            case 8:
               lineTextField2 = Long.valueOf(lineTextField2.longValue() + 1209600000L);
               break;
            case 9:
               lineTextField2 = Long.valueOf(lineTextField2.longValue() + 1814400000L);
               break;
            case 10:
               lineTextField2 = Long.valueOf(lineTextField2.longValue() + 2592000000L);
            }

            EnterpriseGui.lastContractDemand = Long.valueOf(System.currentTimeMillis());
            PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new EnterpriseContractCreate_Default_Packet((String)EnterpriseGui.enterpriseInfos.get("name"), description, Integer.valueOf(Integer.parseInt(this.priceInput.func_73781_b())), lineTextField2)));
            Minecraft.func_71410_x().func_71373_a((GuiScreen)null);
         }

         if(mouseX > this.guiLeft + 343 && mouseX < this.guiLeft + 343 + 19 && mouseY > this.guiTop + 160 && mouseY < this.guiTop + 160 + 20) {
            this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0F, 1.0F);
            this.delayExpanded = !this.delayExpanded;
         }

         if(this.delayExpanded && this.hoveredDelay != null && !this.hoveredDelay.isEmpty()) {
            this.selectedDelay = this.hoveredDelay;
            this.hoveredDelay = "";
            this.delayExpanded = false;
         }
      }

      this.priceInput.func_73793_a(mouseX, mouseY, mouseButton);
      Iterator description1 = this.linesTextField.iterator();

      while(description1.hasNext()) {
         GuiTextField lineTextField4 = (GuiTextField)description1.next();
         lineTextField4.func_73793_a(mouseX, mouseY, mouseButton);
      }

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

         if(Integer.parseInt(str) < 0) {
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
}
