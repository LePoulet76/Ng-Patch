package net.ilexiconn.nationsgui.forge.client.gui.island;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import javax.imageio.ImageIO;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.IslandCreateCooldownPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.IslandCreatePacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.IslandTPPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.I18n;
import org.lwjgl.opengl.GL11;
import sun.misc.BASE64Decoder;

@SideOnly(Side.CLIENT)
public class IslandCreateGui extends GuiScreen {

   protected int xSize = 289;
   protected int ySize = 248;
   private int guiLeft;
   private int guiTop;
   private RenderItem itemRenderer = new RenderItem();
   private String nameText = "";
   private String descriptionText = "";
   boolean sizeExpanded = false;
   List<String> sizes = new ArrayList();
   private String selectedSize = "";
   private String hoveredSize = "";
   private String selectedBiome = "";
   private String hoveredBiome = "";
   List<String> biomes = new ArrayList();
   private int biomeOffsetXMax = 1;
   public int biomeOffsetNumber = 0;
   public int biomeOffsetX = 0;
   private boolean isPrivate = false;
   private GuiTextField nameTextField;
   private GuiTextField descriptionTextField;
   private boolean creationStarted = false;
   private long creationTime = 0L;
   private int creationWaitingSec = 7;
   public static int creationIslandId = -1;
   public static long lastPlayerCreation = 0L;
   public static boolean loaded = false;
   public boolean isPremium = false;
   public String serverNumber = "1";


   public IslandCreateGui(boolean isPremium, String serverNumber) {
      this.isPremium = isPremium;
      this.serverNumber = serverNumber;
      this.sizes.addAll(Arrays.asList(new String[]{"25", "50", "100", "250", "500"}));
      this.selectedSize = "25";
      this.biomes.addAll(Arrays.asList(new String[]{"plaine", "marais", "desert", "neige", "jungle"}));
      this.selectedBiome = "plaine";
      this.biomeOffsetXMax = this.biomes.size() - 4;
      PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new IslandCreateCooldownPacket()));
   }

   public void func_73876_c() {
      this.nameTextField.func_73780_a();
      this.descriptionTextField.func_73780_a();
   }

   public void func_73866_w_() {
      super.func_73866_w_();
      this.guiLeft = (this.field_73880_f - this.xSize) / 2;
      this.guiTop = (this.field_73881_g - this.ySize) / 2;
      this.nameTextField = new GuiTextField(this.field_73886_k, this.guiLeft + 53, this.guiTop + 30, 218, 10);
      this.nameTextField.func_73786_a(false);
      this.nameTextField.func_73804_f(25);
      this.descriptionTextField = new GuiTextField(this.field_73886_k, this.guiLeft + 53, this.guiTop + 62, 218, 10);
      this.descriptionTextField.func_73786_a(false);
      this.descriptionTextField.func_73804_f(50);
   }

   public void func_73863_a(int mouseX, int mouseY, float par3) {
      this.func_73873_v_();
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      ClientEventHandler.STYLE.bindTexture("island_create");
      ModernGui.drawModalRectWithCustomSizedTexture((float)this.guiLeft, (float)this.guiTop, 0, 0, this.xSize, this.ySize, 512.0F, 512.0F, false);
      Object tooltipToDraw = new ArrayList();
      GL11.glPushMatrix();
      GL11.glTranslatef((float)(this.guiLeft + 14), (float)(this.guiTop + 115), 0.0F);
      GL11.glRotatef(-90.0F, 0.0F, 0.0F, 1.0F);
      GL11.glTranslatef((float)(-(this.guiLeft + 14)), (float)(-(this.guiTop + 115)), 0.0F);
      this.drawScaledString(I18n.func_135053_a("island.create.title"), this.guiLeft + 14, this.guiTop + 115, 16777215, 1.5F, false, false);
      GL11.glPopMatrix();
      ClientEventHandler.STYLE.bindTexture("island_create");
      if(mouseX >= this.guiLeft + 278 && mouseX <= this.guiLeft + 278 + 9 && mouseY >= this.guiTop - 8 && mouseY <= this.guiTop - 8 + 10) {
         ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 278), (float)(this.guiTop - 8), 239, 266, 9, 10, 512.0F, 512.0F, false);
      } else {
         ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 278), (float)(this.guiTop - 8), 239, 256, 9, 10, 512.0F, 512.0F, false);
      }

      ClientEventHandler.STYLE.bindTexture("island_create");
      this.drawScaledString(I18n.func_135053_a("island.create.name"), this.guiLeft + 50, this.guiTop + 15, 0, 1.0F, false, false);
      this.nameTextField.func_73795_f();
      this.drawScaledString(I18n.func_135053_a("island.create.description"), this.guiLeft + 50, this.guiTop + 47, 0, 1.0F, false, false);
      this.descriptionTextField.func_73795_f();
      this.drawScaledString(I18n.func_135053_a("island.create.biome"), this.guiLeft + 50, this.guiTop + 114, 0, 1.0F, false, false);
      ClientEventHandler.STYLE.bindTexture("island_create");
      int biomeOffsetXTarget = 51 * this.biomeOffsetNumber;
      if(this.biomeOffsetX < biomeOffsetXTarget) {
         ++this.biomeOffsetX;
      } else if(this.biomeOffsetX > biomeOffsetXTarget) {
         --this.biomeOffsetX;
      }

      this.hoveredBiome = "";
      GUIUtils.startGLScissor(this.guiLeft + 60, this.guiTop + 124, 203, 50);

      int i;
      int offsetX;
      int offsetY;
      for(i = 0; i < this.biomes.size(); ++i) {
         offsetX = this.guiLeft + 60 - this.biomeOffsetX + 51 * i;
         offsetY = this.guiTop + 124;
         ModernGui.drawModalRectWithCustomSizedTexture((float)offsetX, (float)offsetY, 0 + 51 * i, 279, 50, 50, 512.0F, 512.0F, false);
         if(mouseX >= offsetX && mouseX <= offsetX + 50 && mouseY >= offsetY && mouseY <= offsetY + 50) {
            this.hoveredBiome = (String)this.biomes.get(i);
         }

         if(((String)this.biomes.get(i)).equals(this.selectedBiome)) {
            ClientEventHandler.STYLE.bindTexture("island_create");
            GL11.glEnable(3042);
            GL11.glDisable(2929);
            GL11.glDepthMask(false);
            GL11.glBlendFunc(770, 771);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glDisable(3008);
            ModernGui.drawModalRectWithCustomSizedTexture((float)offsetX, (float)offsetY, 0, 331, 50, 50, 512.0F, 512.0F, false);
            GL11.glDisable(3042);
            GL11.glEnable(2929);
            GL11.glDepthMask(true);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glEnable(3008);
         }
      }

      GUIUtils.endGLScissor();
      ClientEventHandler.STYLE.bindTexture("island_create");
      if(mouseX >= this.guiLeft + 49 && mouseX <= this.guiLeft + 49 + 10 && mouseY >= this.guiTop + 124 && mouseY <= this.guiTop + 124 + 50) {
         ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 49), (float)(this.guiTop + 124), 51, 331, 10, 50, 512.0F, 512.0F, false);
      } else if(mouseX >= this.guiLeft + 264 && mouseX <= this.guiLeft + 264 + 10 && mouseY >= this.guiTop + 124 && mouseY <= this.guiTop + 124 + 50) {
         ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 264), (float)(this.guiTop + 124), 63, 331, 10, 50, 512.0F, 512.0F, false);
      }

      ClientEventHandler.STYLE.bindTexture("island_list");
      if((!loaded || System.currentTimeMillis() - lastPlayerCreation < 3600000L) && !this.creationStarted && mouseX >= this.guiLeft + 171 && mouseX <= this.guiLeft + 171 + 103 && mouseY >= this.guiTop + 195 && mouseY <= this.guiTop + 195 + 27) {
         ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 171), (float)(this.guiTop + 195), 153, 319, 103, 27, 512.0F, 512.0F, false);
         if(!loaded || System.currentTimeMillis() - lastPlayerCreation < 3600000L) {
            tooltipToDraw = Arrays.asList(new String[]{I18n.func_135053_a("island.main.create.cooldown")});
         }
      }

      ClientEventHandler.STYLE.bindTexture("island_create");
      if(this.creationStarted) {
         ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 172), (float)(this.guiTop + 216), 0, 249, 101, 5, 512.0F, 512.0F, false);
         long var10 = System.currentTimeMillis() - this.creationTime;
         offsetY = this.creationWaitingSec;

         for(int loadPercent = 0; (long)loadPercent <= var10 / (long)(offsetY * 1000 / 48); loadPercent += 2) {
            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 174 + loadPercent * 2), (float)(this.guiTop + 217), 102, 249, 2, 4, 512.0F, 512.0F, false);
         }

         if(var10 >= (long)(offsetY * 1000)) {
            Minecraft.func_71410_x().func_71373_a((GuiScreen)null);
            if(creationIslandId != -1) {
               PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new IslandTPPacket(creationIslandId + "", this.serverNumber)));
            }
         }

         Float var11 = Float.valueOf((float)var10 * 1.0F / (float)(offsetY * 1000) * 100.0F);
         this.drawScaledString(var11.intValue() + "%", this.guiLeft + 228, this.guiTop + 204, 16777215, 1.0F, true, false);
      } else {
         this.drawScaledString(I18n.func_135053_a("island.create.create_button"), this.guiLeft + 228, this.guiTop + 204, 16777215, 1.0F, true, false);
      }

      ClientEventHandler.STYLE.bindTexture("island_create");
      if(this.isPrivate) {
         ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 49), (float)(this.guiTop + 204), 0, 256, 10, 10, 512.0F, 512.0F, false);
      } else {
         ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 49), (float)(this.guiTop + 204), 0, 268, 10, 10, 512.0F, 512.0F, false);
      }

      this.drawScaledString(I18n.func_135053_a("island.create.private"), this.guiLeft + 64, this.guiTop + 204, 0, 1.0F, false, false);
      ClientEventHandler.STYLE.bindTexture("island_create");
      if(!this.isPremium) {
         ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 44), (float)(this.guiTop + 196), 75, 331, 10, 12, 512.0F, 512.0F, false);
         if(mouseX >= this.guiLeft + 44 && mouseX <= this.guiLeft + 44 + 10 && mouseY >= this.guiTop + 196 && mouseY <= this.guiTop + 196 + 12) {
            tooltipToDraw = Arrays.asList(new String[]{I18n.func_135053_a("island.global.premium_required")});
         }
      }

      this.drawScaledString(I18n.func_135053_a("island.create.size"), this.guiLeft + 50, this.guiTop + 80, 0, 1.0F, false, false);
      ClientEventHandler.STYLE.bindTexture("island_create");
      this.drawScaledString(this.selectedSize + "x" + this.selectedSize, this.guiLeft + 53, this.guiTop + 95, 16777215, 1.0F, false, false);
      ClientEventHandler.STYLE.bindTexture("island_create");
      this.hoveredSize = "";
      if(this.sizeExpanded && this.sizes.size() > 0) {
         for(i = 0; i < this.sizes.size(); ++i) {
            offsetX = this.guiLeft + 49;
            offsetY = this.guiTop + 107 + i * 16;
            ClientEventHandler.STYLE.bindTexture("island_create");
            if(mouseX >= offsetX && mouseX <= offsetX + 225 && mouseY >= offsetY && mouseY <= offsetY + 16) {
               ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 49), (float)offsetY, 0, 384, 225, 16, 512.0F, 512.0F, false);
               this.hoveredSize = (String)this.sizes.get(i);
            } else {
               ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 49), (float)offsetY, 11, 257, 225, 16, 512.0F, 512.0F, false);
            }

            if(i > 2 && !this.isPremium) {
               ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 260), (float)(offsetY + 2), 75, 331, 10, 12, 512.0F, 512.0F, false);
               if(mouseX >= this.guiLeft + 260 && mouseX <= this.guiLeft + 260 + 10 && mouseY >= offsetY + 2 && mouseY <= offsetY + 2 + 12) {
                  tooltipToDraw = Arrays.asList(new String[]{I18n.func_135053_a("island.global.premium_required")});
               }
            }

            this.drawScaledString((String)this.sizes.get(i) + "x" + (String)this.sizes.get(i), offsetX + 6, offsetY + 4, 16777215, 1.0F, false, false);
         }
      }

      if(!((List)tooltipToDraw).isEmpty()) {
         this.drawHoveringText((List)tooltipToDraw, mouseX, mouseY, this.field_73886_k);
      }

      super.func_73863_a(mouseX, mouseY, par3);
      GL11.glEnable(2896);
      RenderHelper.func_74519_b();
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
         } else if(mouseX > this.guiLeft + 49 && mouseX < this.guiLeft + 49 + 225 && mouseY > this.guiTop + 90 && mouseY < this.guiTop + 90 + 17) {
            this.sizeExpanded = !this.sizeExpanded;
         } else if(!this.hoveredSize.isEmpty() && mouseX > this.guiLeft + 49 && mouseX < this.guiLeft + 49 + 225 && mouseY > this.guiTop + 106 && mouseY < this.guiTop + 106 + 100) {
            if(this.isPremium || this.sizes.indexOf(this.hoveredSize) <= 2) {
               this.selectedSize = this.hoveredSize;
               this.sizeExpanded = false;
            }
         } else if(mouseX > this.guiLeft + 49 && mouseX < this.guiLeft + 49 + 10 && mouseY > this.guiTop + 124 && mouseY < this.guiTop + 124 + 50) {
            this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0F, 1.0F);
            this.biomeOffsetNumber = this.biomeOffsetNumber - 1 > 0?this.biomeOffsetNumber - 1:0;
         } else if(mouseX > this.guiLeft + 264 && mouseX < this.guiLeft + 264 + 10 && mouseY > this.guiTop + 124 && mouseY < this.guiTop + 124 + 50) {
            this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0F, 1.0F);
            this.biomeOffsetNumber = this.biomeOffsetNumber + 1 < this.biomeOffsetXMax?this.biomeOffsetNumber + 1:this.biomeOffsetXMax;
         } else if(!this.hoveredBiome.isEmpty() && mouseX > this.guiLeft + 60 && mouseX < this.guiLeft + 60 + 203 && mouseY > this.guiTop + 124 && mouseY < this.guiTop + 124 + 50) {
            this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0F, 1.0F);
            this.selectedBiome = this.hoveredBiome;
         } else if(this.isPremium && mouseX > this.guiLeft + 49 && mouseX < this.guiLeft + 49 + 10 && mouseY > this.guiTop + 204 && mouseY < this.guiTop + 204 + 10) {
            this.isPrivate = !this.isPrivate;
         } else if(mouseX >= this.guiLeft + 171 && mouseX <= this.guiLeft + 171 + 103 && mouseY >= this.guiTop + 195 && mouseY <= this.guiTop + 195 + 27 && loaded && System.currentTimeMillis() - lastPlayerCreation > 3600000L && !this.creationStarted && !this.nameText.isEmpty() && !this.descriptionText.isEmpty() && !this.selectedSize.isEmpty() && !this.selectedBiome.isEmpty()) {
            PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new IslandCreatePacket(this.nameText, this.descriptionText, this.selectedSize, this.selectedBiome, this.isPrivate)));
            this.creationStarted = true;
            this.creationTime = System.currentTimeMillis();
         }
      }

      this.nameTextField.func_73793_a(mouseX, mouseY, mouseButton);
      this.descriptionTextField.func_73793_a(mouseX, mouseY, mouseButton);
      super.func_73864_a(mouseX, mouseY, mouseButton);
   }

   protected void func_73869_a(char typedChar, int keyCode) {
      if(this.nameTextField.func_73802_a(typedChar, keyCode)) {
         this.nameText = this.nameTextField.func_73781_b();
      } else if(this.descriptionTextField.func_73802_a(typedChar, keyCode)) {
         this.descriptionText = this.descriptionTextField.func_73781_b();
      }

      super.func_73869_a(typedChar, keyCode);
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

   public static BufferedImage decodeToImage(String imageString) {
      BufferedImage image = null;

      try {
         BASE64Decoder e = new BASE64Decoder();
         byte[] imageByte = e.decodeBuffer(imageString);
         ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
         image = ImageIO.read(bis);
         bis.close();
      } catch (Exception var5) {
         var5.printStackTrace();
      }

      return image;
   }

}
