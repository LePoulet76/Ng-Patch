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
import net.ilexiconn.nationsgui.forge.server.packet.impl.EnterpriseContractCreate_Loan_Packet;
import net.ilexiconn.nationsgui.forge.server.packet.impl.EnterpriseContractFormLoanPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class EnterpriseContractForm_Loan_Gui extends GuiScreen {

   private GuiButton cancelButton;
   private GuiButton validButton;
   private GuiTextField priceInput;
   private GuiTextField garantInput;
   private GuiScrollBarFaction scrollBarDuration;
   private GuiScrollBarFaction scrollBarRate;
   private GuiScrollBarFaction scrollBarType;
   private RenderItem itemRenderer = new RenderItem();
   protected int xSize = 371;
   protected int ySize = 223;
   private int guiLeft;
   private int guiTop;
   private boolean rateExpanded = false;
   private int selectedRate = -1;
   private int hoveredRate = -1;
   private ArrayList<Integer> rates = new ArrayList();
   private boolean durationExpanded = false;
   private int selectedDuration = -1;
   private int hoveredDuration = -1;
   private ArrayList<Integer> durations = new ArrayList();
   private boolean typeExpanded = false;
   private String selectedType = "";
   private String hoveredType = "";
   private ArrayList<String> types = new ArrayList();
   public static boolean loaded = false;
   public static HashMap<String, Object> data = new HashMap();


   public void func_73876_c() {
      this.priceInput.func_73780_a();
      this.garantInput.func_73780_a();
   }

   public void func_73866_w_() {
      super.func_73866_w_();
      loaded = false;
      PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new EnterpriseContractFormLoanPacket((String)EnterpriseGui.enterpriseInfos.get("name"))));
      this.guiLeft = (this.field_73880_f - this.xSize) / 2;
      this.guiTop = (this.field_73881_g - this.ySize) / 2;
      this.cancelButton = new GuiButton(0, this.guiLeft + 197, this.guiTop + 197, 80, 20, I18n.func_135053_a("enterprise.contract.action.cancel"));
      this.validButton = new GuiButton(1, this.guiLeft + 282, this.guiTop + 197, 80, 20, I18n.func_135053_a("enterprise.contract.action.valid"));
      this.scrollBarDuration = new GuiScrollBarFaction((float)(this.guiLeft + 355), (float)(this.guiTop + 96), 90);
      this.scrollBarRate = new GuiScrollBarFaction((float)(this.guiLeft + 275), (float)(this.guiTop + 137), 90);
      this.scrollBarType = new GuiScrollBarFaction((float)(this.guiLeft + 355), (float)(this.guiTop + 137), 90);
      this.priceInput = new GuiTextField(this.field_73886_k, this.guiLeft + 203, this.guiTop + 79, 78, 14);
      this.priceInput.func_73786_a(false);
      this.priceInput.func_73804_f(7);
      this.priceInput.func_73782_a("0");
      this.garantInput = new GuiTextField(this.field_73886_k, this.guiLeft + 203, this.guiTop + 160, 158, 14);
      this.garantInput.func_73786_a(false);
      this.garantInput.func_73804_f(20);
      this.garantInput.func_73782_a("");
      this.rates.clear();
      this.rates.addAll(Arrays.asList(new Integer[]{Integer.valueOf(0), Integer.valueOf(1), Integer.valueOf(2), Integer.valueOf(3), Integer.valueOf(5), Integer.valueOf(10)}));
      this.selectedRate = ((Integer)this.rates.get(0)).intValue();
      this.durations.clear();
      this.durations.addAll(Arrays.asList(new Integer[]{Integer.valueOf(1), Integer.valueOf(2), Integer.valueOf(3), Integer.valueOf(5), Integer.valueOf(7), Integer.valueOf(10), Integer.valueOf(14), Integer.valueOf(21), Integer.valueOf(30), Integer.valueOf(60)}));
      this.selectedDuration = ((Integer)this.durations.get(0)).intValue();
      this.types.clear();
      this.types.addAll(Arrays.asList(new String[]{"garant", "items"}));
      this.selectedType = (String)this.types.get(0);
   }

   public void func_73863_a(int mouseX, int mouseY, float par3) {
      this.func_73873_v_();
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      ClientEventHandler.STYLE.bindTexture("enterprise_contract_form");
      ModernGui.drawModalRectWithCustomSizedTextureWithTransparency((float)this.guiLeft, (float)this.guiTop, 0, 0, this.xSize, this.ySize, 512.0F, 512.0F, false);
      this.hoveredRate = -1;
      this.hoveredDuration = -1;
      this.hoveredType = "";
      List tooltipToDraw = null;
      ClientEventHandler.STYLE.bindTexture("enterprise_contract_form");
      ModernGui.drawModalRectWithCustomSizedTexture((float)((int)((double)(this.guiLeft + 91) - (double)this.field_73886_k.func_78256_a(I18n.func_135053_a("enterprise.contract.title.services")) * 1.2D / 2.0D - 8.0D - 2.0D)), (float)(this.guiTop + 16), 0, 276, 16, 16, 512.0F, 512.0F, false);
      this.drawScaledString(I18n.func_135053_a("enterprise.contract.title.services"), this.guiLeft + 91 + 8, this.guiTop + 21, 16777215, 1.2F, true, false);
      int index = 0;
      String[] type = ((String)EnterpriseGui.enterpriseInfos.get("services")).split("##");
      int totalDue = type.length;

      int i;
      for(i = 0; i < totalDue; ++i) {
         String offsetX = type[i];
         this.drawScaledString(offsetX.replace("&", "\u00a7"), this.guiLeft + 6, this.guiTop + 54 + index * 9, 16777215, 0.8F, false, false);
         ++index;
      }

      this.drawScaledString((String)EnterpriseGui.enterpriseInfos.get("name"), this.guiLeft + 280, this.guiTop + 11, 16777215, 1.7F, true, true);
      String var13 = I18n.func_135053_a("enterprise.type." + ((String)EnterpriseGui.enterpriseInfos.get("type")).toLowerCase());
      this.cancelButton.func_73737_a(Minecraft.func_71410_x(), mouseX, mouseY);
      this.validButton.func_73737_a(Minecraft.func_71410_x(), mouseX, mouseY);
      if(loaded) {
         ClientEventHandler.STYLE.bindTexture("enterprise_main");
         ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 280 - this.field_73886_k.func_78256_a(var13) / 2 - 7 - 4), (float)(this.guiTop + 23), EnterpriseGui.getTypeOffsetX((String)EnterpriseGui.enterpriseInfos.get("type")), 442, 16, 16, 512.0F, 512.0F, false);
         this.drawScaledString(I18n.func_135053_a("enterprise.type." + ((String)EnterpriseGui.enterpriseInfos.get("type")).toLowerCase()), this.guiLeft + 280 + 7, this.guiTop + 29, 11842740, 1.0F, true, false);
         ClientEventHandler.STYLE.bindTexture("enterprise_contract_form");
         ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 350), (float)(this.guiTop + 52), 16, 276, 10, 11, 512.0F, 512.0F, false);
         if(mouseX >= this.guiLeft + 350 && mouseX <= this.guiLeft + 350 + 10 && mouseY >= this.guiTop + 52 && mouseY <= this.guiTop + 52 + 11) {
            tooltipToDraw = Arrays.asList(I18n.func_135053_a("enterprise.loan.infos").split("##"));
         }

         this.drawScaledString(I18n.func_135053_a("enterprise.contract.label.loan.amount"), this.guiLeft + 201, this.guiTop + 62, 1644825, 1.0F, false, false);
         ClientEventHandler.STYLE.bindTexture("enterprise_contract_form");
         ModernGui.drawModalRectWithCustomSizedTextureWithTransparency((float)(this.guiLeft + 200), (float)(this.guiTop + 72), 0, 333, 80, 20, 512.0F, 512.0F, false);
         this.drawScaledString("\u00a7a$", this.guiLeft + 200 + 72, this.guiTop + 72 + 6, 16777215, 1.3F, true, false);
         this.priceInput.func_73795_f();
         this.drawScaledString(I18n.func_135053_a("enterprise.contract.label.loan.duration"), this.guiLeft + 288, this.guiTop + 62, 1644825, 1.0F, false, false);
         ClientEventHandler.STYLE.bindTexture("enterprise_contract_form");
         ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 287), (float)(this.guiTop + 72), 0, 313, 73, 20, 512.0F, 512.0F, false);
         if(this.selectedDuration != -1) {
            this.drawScaledString(this.selectedDuration + I18n.func_135053_a("enterprise.contract.duration.days"), this.guiLeft + 290, this.guiTop + 78, 16777215, 1.0F, false, false);
         }

         this.drawScaledString(I18n.func_135053_a("enterprise.contract.label.loan.rate"), this.guiLeft + 201, this.guiTop + 103, 1644825, 1.0F, false, false);
         ClientEventHandler.STYLE.bindTexture("enterprise_contract_form");
         ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 200), (float)(this.guiTop + 113), 0, 255, 80, 20, 512.0F, 512.0F, false);
         if(this.selectedRate != -1) {
            this.drawScaledString(this.selectedRate + "%", this.guiLeft + 202, this.guiTop + 119, 16777215, 1.0F, false, false);
         }

         this.drawScaledString(I18n.func_135053_a("enterprise.contract.label.loan.type"), this.guiLeft + 288, this.guiTop + 103, 1644825, 1.0F, false, false);
         ClientEventHandler.STYLE.bindTexture("enterprise_contract_form");
         ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 287), (float)(this.guiTop + 113), 0, 313, 73, 20, 512.0F, 512.0F, false);
         totalDue = 0;
         if(!this.priceInput.func_73781_b().isEmpty() && this.isNumeric(this.priceInput.func_73781_b())) {
            totalDue = Integer.parseInt(this.priceInput.func_73781_b());
         }

         if(this.selectedRate != -1 && this.selectedRate != 0) {
            totalDue = (int)((double)totalDue + (double)this.selectedRate / 100.0D * (double)totalDue);
         }

         this.drawScaledString(I18n.func_135053_a("enterprise.contract.label.loan.totalDue") + " \u00a74" + totalDue + "$", this.guiLeft + 201, this.guiTop + 184, 1644825, 1.0F, false, false);
         if(this.selectedType != "") {
            this.drawScaledString(I18n.func_135053_a("enterprise.contract.loan.type." + this.selectedType), this.guiLeft + 290, this.guiTop + 119, 16777215, 1.0F, false, false);
         }

         if(this.selectedType.equalsIgnoreCase("garant")) {
            this.drawScaledString(I18n.func_135053_a("enterprise.contract.label.loan.garant"), this.guiLeft + 201, this.guiTop + 143, 1644825, 1.0F, false, false);
            ModernGui.drawNGBlackSquare(this.guiLeft + 200, this.guiTop + 153, 160, 20);
            this.garantInput.func_73795_f();
         } else if(this.selectedType.equalsIgnoreCase("items")) {
            this.drawScaledString(I18n.func_135053_a("enterprise.contract.label.loan.items"), this.guiLeft + 201, this.guiTop + 143, 1644825, 1.0F, false, false);
            ModernGui.drawNGBlackSquare(this.guiLeft + 200, this.guiTop + 153, 160, 20);
            i = 0;
            if(!this.durationExpanded && !this.rateExpanded && !this.typeExpanded) {
               for(Iterator var14 = ((ArrayList)data.get("items")).iterator(); var14.hasNext(); ++i) {
                  String offsetY = (String)var14.next();
                  String[] itemInfos = offsetY.split("#");
                  ItemStack stack = new ItemStack(Integer.parseInt(itemInfos[0]), Integer.parseInt(itemInfos[2]), Integer.parseInt(itemInfos[1]));
                  GL11.glEnable(2929);
                  RenderHelper.func_74520_c();
                  this.itemRenderer.func_82406_b(this.field_73886_k, Minecraft.func_71410_x().func_110434_K(), stack, this.guiLeft + 200 + 18 * i, this.guiTop + 155);
                  this.itemRenderer.func_94148_a(this.field_73886_k, Minecraft.func_71410_x().func_110434_K(), stack, this.guiLeft + 200 + 18 * i, this.guiTop + 155, stack.field_77994_a + "");
                  RenderHelper.func_74518_a();
                  GL11.glDisable(2896);
                  if(mouseX >= this.guiLeft + 200 + 18 * i && mouseX <= this.guiLeft + 200 + 18 * i + 18 && mouseY >= this.guiTop + 155 - 18 && mouseY >= this.guiTop + 155) {
                     tooltipToDraw = Arrays.asList(new String[]{stack.func_82833_r()});
                  }
               }
            }
         }

         int var15;
         Float var16;
         if(this.durationExpanded) {
            ClientEventHandler.STYLE.bindTexture("enterprise_contract_form");
            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 287), (float)(this.guiTop + 91), 81, 234, 73, 99, 512.0F, 512.0F, false);
            GUIUtils.startGLScissor(this.guiLeft + 288, this.guiTop + 92, 67, 97);

            for(i = 0; i < this.durations.size(); ++i) {
               var15 = this.guiLeft + 288;
               var16 = Float.valueOf((float)(this.guiTop + 92 + i * 20) + this.getSlideDuration());
               ClientEventHandler.STYLE.bindTexture("enterprise_contract_form");
               ModernGui.drawModalRectWithCustomSizedTexture((float)var15, (float)var16.intValue(), 82, 235, 67, 20, 512.0F, 512.0F, false);
               this.drawScaledString(this.durations.get(i) + I18n.func_135053_a("enterprise.contract.duration.days"), var15 + 2, var16.intValue() + 5, 16777215, 1.0F, false, false);
               if(mouseX > var15 && mouseX < var15 + 67 && (float)mouseY > var16.floatValue() && (float)mouseY < var16.floatValue() + 20.0F) {
                  this.hoveredDuration = ((Integer)this.durations.get(i)).intValue();
               }
            }

            GUIUtils.endGLScissor();
            this.scrollBarDuration.draw(mouseX, mouseY);
         } else if(this.rateExpanded) {
            ClientEventHandler.STYLE.bindTexture("enterprise_contract_form");
            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 200), (float)(this.guiTop + 132), 247, 234, 80, 99, 512.0F, 512.0F, false);
            GUIUtils.startGLScissor(this.guiLeft + 201, this.guiTop + 133, 74, 97);

            for(i = 0; i < this.rates.size(); ++i) {
               var15 = this.guiLeft + 201;
               var16 = Float.valueOf((float)(this.guiTop + 133 + i * 20) + this.getSlideRate());
               ClientEventHandler.STYLE.bindTexture("enterprise_contract_form");
               ModernGui.drawModalRectWithCustomSizedTexture((float)var15, (float)var16.intValue(), 248, 235, 74, 20, 512.0F, 512.0F, false);
               this.drawScaledString(this.rates.get(i) + "%", var15 + 2, var16.intValue() + 5, 16777215, 1.0F, false, false);
               if(mouseX > var15 && mouseX < var15 + 74 && (float)mouseY > var16.floatValue() && (float)mouseY < var16.floatValue() + 20.0F) {
                  this.hoveredRate = ((Integer)this.rates.get(i)).intValue();
               }
            }

            GUIUtils.endGLScissor();
            this.scrollBarRate.draw(mouseX, mouseY);
         } else if(this.typeExpanded) {
            ClientEventHandler.STYLE.bindTexture("enterprise_contract_form");
            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 287), (float)(this.guiTop + 132), 155, 234, 73, 40, 512.0F, 512.0F, false);
            GUIUtils.startGLScissor(this.guiLeft + 288, this.guiTop + 133, 67, 97);

            for(i = 0; i < this.types.size(); ++i) {
               var15 = this.guiLeft + 288;
               var16 = Float.valueOf((float)(this.guiTop + 133 + i * 20) + this.getSlideType());
               ClientEventHandler.STYLE.bindTexture("enterprise_contract_form");
               ModernGui.drawModalRectWithCustomSizedTexture((float)var15, (float)var16.intValue(), 82, 235, 67, 20, 512.0F, 512.0F, false);
               this.drawScaledString(I18n.func_135053_a("enterprise.contract.loan.type." + (String)this.types.get(i)), var15 + 2, var16.intValue() + 5, 16777215, 1.0F, false, false);
               if(mouseX > var15 && mouseX < var15 + 67 && (float)mouseY > var16.floatValue() && (float)mouseY < var16.floatValue() + 20.0F) {
                  this.hoveredType = (String)this.types.get(i);
               }
            }

            GUIUtils.endGLScissor();
            this.scrollBarType.draw(mouseX, mouseY);
         }

         if(tooltipToDraw != null) {
            this.drawHoveringText(tooltipToDraw, mouseX, mouseY, this.field_73886_k);
         }
      }

   }

   protected void func_73869_a(char typedChar, int keyCode) {
      this.priceInput.func_73802_a(typedChar, keyCode);
      this.garantInput.func_73802_a(typedChar, keyCode);
      super.func_73869_a(typedChar, keyCode);
   }

   private float getSlideDuration() {
      return this.durations.size() > 5?(float)(-(this.durations.size() - 5) * 20) * this.scrollBarDuration.getSliderValue():0.0F;
   }

   private float getSlideRate() {
      return this.rates.size() > 5?(float)(-(this.rates.size() - 5) * 20) * this.scrollBarRate.getSliderValue():0.0F;
   }

   private float getSlideType() {
      return this.types.size() > 5?(float)(-(this.types.size() - 5) * 20) * this.scrollBarType.getSliderValue():0.0F;
   }

   protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
      if(mouseButton == 0) {
         if(!this.typeExpanded && !this.rateExpanded && !this.durationExpanded && mouseX > this.guiLeft + 197 && mouseX < this.guiLeft + 197 + 80 && mouseY > this.guiTop + 197 && mouseY < this.guiTop + 197 + 20) {
            this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0F, 1.0F);
            Minecraft.func_71410_x().func_71373_a(new EnterpriseGui((String)EnterpriseGui.enterpriseInfos.get("name")));
         }

         if(this.validButton.field_73742_g && !this.durationExpanded && !this.typeExpanded && !this.rateExpanded && this.isNumeric(this.priceInput.func_73781_b()) && mouseX > this.guiLeft + 282 && mouseX < this.guiLeft + 282 + 80 && mouseY > this.guiTop + 197 && mouseY < this.guiTop + 197 + 20) {
            this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0F, 1.0F);
            int totalDue = 0;
            if(!this.priceInput.func_73781_b().isEmpty() && this.isNumeric(this.priceInput.func_73781_b())) {
               totalDue = Integer.parseInt(this.priceInput.func_73781_b());
            }

            if(this.selectedRate != -1 && this.selectedRate != 0) {
               totalDue = (int)((double)totalDue + (double)this.selectedRate / 100.0D * (double)totalDue);
            }

            String description = "LOAN##" + this.priceInput.func_73781_b() + "##" + totalDue + "##" + this.selectedDuration + "##" + this.selectedRate + "##" + this.selectedType;
            if(this.selectedType.equalsIgnoreCase("garant")) {
               if(this.garantInput.func_73781_b().equalsIgnoreCase(Minecraft.func_71410_x().field_71439_g.field_71092_bJ)) {
                  return;
               }

               description = description + "##" + this.garantInput.func_73781_b();
            }

            EnterpriseGui.lastContractDemand = Long.valueOf(System.currentTimeMillis());
            PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new EnterpriseContractCreate_Loan_Packet((String)EnterpriseGui.enterpriseInfos.get("name"), description, Integer.valueOf(totalDue), this.selectedType)));
            Minecraft.func_71410_x().func_71373_a((GuiScreen)null);
         }

         if(mouseX > this.guiLeft + 341 && mouseX < this.guiLeft + 341 + 19 && mouseY > this.guiTop + 72 && mouseY < this.guiTop + 72 + 20) {
            this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0F, 1.0F);
            this.durationExpanded = !this.durationExpanded;
            this.rateExpanded = false;
            this.typeExpanded = false;
         }

         if(mouseX > this.guiLeft + 261 && mouseX < this.guiLeft + 261 + 19 && mouseY > this.guiTop + 113 && mouseY < this.guiTop + 113 + 20) {
            this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0F, 1.0F);
            this.rateExpanded = !this.rateExpanded;
            this.durationExpanded = false;
            this.typeExpanded = false;
         }

         if(mouseX > this.guiLeft + 341 && mouseX < this.guiLeft + 341 + 19 && mouseY > this.guiTop + 113 && mouseY < this.guiTop + 113 + 20) {
            this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0F, 1.0F);
            this.typeExpanded = !this.typeExpanded;
            this.durationExpanded = false;
            this.rateExpanded = false;
         }

         if(this.durationExpanded && this.hoveredDuration != -1) {
            this.selectedDuration = this.hoveredDuration;
            this.hoveredDuration = -1;
            this.durationExpanded = false;
         } else if(this.rateExpanded && this.hoveredRate != -1) {
            System.out.println("select rate");
            this.selectedRate = this.hoveredRate;
            this.hoveredRate = -1;
            this.rateExpanded = false;
         } else if(this.typeExpanded && this.hoveredType != "" && !this.hoveredType.isEmpty()) {
            this.selectedType = this.hoveredType;
            this.hoveredType = "";
            this.typeExpanded = false;
         }
      }

      this.priceInput.func_73793_a(mouseX, mouseY, mouseButton);
      this.garantInput.func_73793_a(mouseX, mouseY, mouseButton);
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

         if(Integer.parseInt(str) < 1000) {
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
