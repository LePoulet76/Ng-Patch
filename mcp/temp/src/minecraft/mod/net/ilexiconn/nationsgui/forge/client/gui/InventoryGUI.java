package net.ilexiconn.nationsgui.forge.client.gui;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import micdoodle8.mods.galacticraft.core.network.GCCorePacketHandlerServer.EnumPacketServer;
import micdoodle8.mods.galacticraft.core.util.PacketUtil;
import net.ilexiconn.nationsgui.forge.client.ClientData;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.ClientKeyHandler;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.gui.BossEdoraGui;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScreenTab;
import net.ilexiconn.nationsgui.forge.client.gui.InventoryGUI$1;
import net.ilexiconn.nationsgui.forge.client.gui.InventoryGUI$2;
import net.ilexiconn.nationsgui.forge.client.gui.InventoryGUI$3;
import net.ilexiconn.nationsgui.forge.client.gui.InventoryGUI$4;
import net.ilexiconn.nationsgui.forge.client.gui.InventoryGUI$5;
import net.ilexiconn.nationsgui.forge.client.gui.InventoryGUI$6;
import net.ilexiconn.nationsgui.forge.client.gui.InventoryGUI$7;
import net.ilexiconn.nationsgui.forge.client.gui.InventoryGUI$8;
import net.ilexiconn.nationsgui.forge.client.gui.InventoryGUI$9;
import net.ilexiconn.nationsgui.forge.client.gui.InventoryGUI$EdoraBossButton;
import net.ilexiconn.nationsgui.forge.client.gui.InventoryGUI$GalacticraftButton;
import net.ilexiconn.nationsgui.forge.client.gui.RecipeListGUI$BookButton;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionCreateGUI;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionGUI;
import net.ilexiconn.nationsgui.forge.client.gui.hdv.SellItemGUI;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.gui.shop.ShopGUI;
import net.ilexiconn.nationsgui.forge.server.packet.PacketCallbacks;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionNamePacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.IncrementObjectivePacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.OpenRecipeGUIPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class InventoryGUI extends GuiContainer {

   public static final List<GuiScreenTab> TABS = new ArrayList();
   private final Map<Class<? extends GuiScreen>, Integer> tabAlerts = new HashMap();
   private final EntityPlayer player;
   public static boolean isInAssault;
   public static boolean achievementDone = false;


   public InventoryGUI(EntityPlayer player) {
      super(player.field_71069_bz);
      this.player = player;
      this.field_74194_b = 182;
      this.field_74195_c = 223;
      if(ClientProxy.serverType.equals("ng")) {
         PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new FactionNamePacket()));
      }

      PacketCallbacks.MONEY.send(new String[0]);
      if(System.currentTimeMillis() - ClientEventHandler.joinTime > 300000L && !achievementDone) {
         achievementDone = true;
         PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new IncrementObjectivePacket("player_5_minutes", 1)));
      }

   }

   protected void func_73869_a(char par1, int par2) {
      if(par2 == ClientKeyHandler.KEY_SELL.field_74512_d) {
         try {
            Method e = GuiContainer.class.getDeclaredMethod("getSlotAtPosition", new Class[]{Integer.TYPE, Integer.TYPE});
            e.setAccessible(true);
            Slot slot = (Slot)e.invoke(this, new Object[]{Integer.valueOf(Mouse.getEventX() * this.field_73880_f / Minecraft.func_71410_x().field_71443_c), Integer.valueOf(this.field_73881_g - Mouse.getEventY() * this.field_73881_g / Minecraft.func_71410_x().field_71440_d - 1)});
            if(slot != null) {
               ItemStack itemStack = slot.func_75211_c();
               if(itemStack != null) {
                  this.field_73882_e.func_71373_a(new SellItemGUI(itemStack, slot.field_75222_d));
               }
            }
         } catch (Exception var6) {
            var6.printStackTrace();
         }
      }

      super.func_73869_a(par1, par2);
   }

   public void func_73866_w_() {
      super.func_73866_w_();
      this.field_73887_h.add(new RecipeListGUI$BookButton(0, this.field_73880_f / 2 - 6, this.field_73881_g / 2 - 29));
      this.field_73887_h.add(new InventoryGUI$GalacticraftButton(1, this.field_73880_f / 2 - 6, this.field_73881_g / 2 - 29 - 21));
      this.field_73887_h.add(new InventoryGUI$EdoraBossButton(2, this.field_73880_f / 2 - 6, this.field_73881_g / 2 - 29 + 21));
   }

   protected void func_73875_a(GuiButton par1GuiButton) {
      if(par1GuiButton.field_73741_f == 0) {
         PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new OpenRecipeGUIPacket()));
      } else if(par1GuiButton.field_73741_f == 1) {
         PacketDispatcher.sendPacketToServer(PacketUtil.createPacket("GalacticraftCore", EnumPacketServer.OPEN_EXTENDED_INVENTORY, new Object[0]));
      } else if(par1GuiButton.field_73741_f == 2) {
         Minecraft.func_71410_x().func_71373_a(new BossEdoraGui(false));
      }

   }

   protected void func_74189_g(int mouseX, int mouseY) {
      int i;
      for(i = 0; i <= 4; ++i) {
         if(mouseX >= this.field_74198_m - (this.getClass() == ((GuiScreenTab)TABS.get(i)).getClassReferent()?23:20) && mouseX <= this.field_74198_m + 3 && mouseY >= this.field_74197_n + 55 + i * 31 && mouseY <= this.field_74197_n + 85 + i * 31) {
            this.func_74190_a(I18n.func_135053_a("gui.inventory.tab." + i), mouseX - this.field_74198_m, mouseY - this.field_74197_n);
         }
      }

      for(i = 5; i < TABS.size(); ++i) {
         if((i != 5 || !isInAssault) && mouseX >= this.field_74198_m + 178 && mouseX <= this.field_74198_m + 201 && mouseY >= this.field_74197_n + 55 + (i - 4) * 31 && mouseY <= this.field_74197_n + 55 + 31 + (i - 4) * 31) {
            this.func_74190_a(I18n.func_135053_a("gui.inventory.tab." + i), mouseX - this.field_74198_m, mouseY - this.field_74197_n);
         }
      }

      if(mouseX >= this.field_74198_m + 109 && mouseX <= this.field_74198_m + 109 + 13 && mouseY >= this.field_74197_n + 51 && mouseY <= this.field_74197_n + 51 + 14) {
         this.func_74190_a("Money", mouseX - this.field_74198_m, mouseY - this.field_74197_n);
      }

   }

   protected void func_74185_a(float partialTicks, int mouseX, int mouseY) {
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      ClientEventHandler.STYLE.bindTexture("inventory");
      this.func_73729_b(this.field_74198_m, this.field_74197_n, 0, 0, this.field_74194_b, this.field_74195_c);

      int text;
      GuiScreenTab pX;
      int w;
      int y;
      for(text = 0; text <= 4; ++text) {
         pX = (GuiScreenTab)TABS.get(text);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         w = text % 3;
         y = text / 3;
         if(this.getClass() == pX.getClassReferent()) {
            this.func_73729_b(this.field_74198_m - 23, this.field_74197_n + 55 + text * 31, 23, 223, 29, 30);
            this.func_73729_b(this.field_74198_m - 23 + 3, this.field_74197_n + 55 + text * 31 + 5, 182 + w * 20, y * 20, 20, 20);
         } else {
            this.func_73729_b(this.field_74198_m - 20, this.field_74197_n + 55 + text * 31, 0, 223, 23, 30);
            GL11.glBlendFunc(770, 771);
            GL11.glEnable(3042);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.75F);
            this.func_73729_b(this.field_74198_m - 20 + 3, this.field_74197_n + 55 + text * 31 + 5, 182 + w * 20, y * 20, 20, 20);
            GL11.glDisable(3042);
         }
      }

      for(text = 5; text < TABS.size(); ++text) {
         pX = (GuiScreenTab)TABS.get(text);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         w = text % 3;
         y = text / 3;
         if(this.getClass() == pX.getClassReferent()) {
            this.func_73729_b(this.field_74198_m + 178, this.field_74197_n + 55 + (text - 4) * 31, 85, 223, 29, 30);
            this.func_73729_b(this.field_74198_m + 175, this.field_74197_n + 55 + (text - 4) * 31 + 5, 182 + w * 20, y * 20, 20, 20);
         } else {
            this.func_73729_b(this.field_74198_m + 178, this.field_74197_n + 55 + (text - 4) * 31, 114, 223, 23, 30);
            GL11.glBlendFunc(770, 771);
            GL11.glEnable(3042);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.75F);
            this.func_73729_b(this.field_74198_m + 179, this.field_74197_n + 55 + (text - 4) * 31 + 5, 182 + w * 20, y * 20, 20, 20);
            GL11.glDisable(3042);
         }
      }

      ClientData.currentFaction = ClientData.currentFaction.replaceAll("^\\\u00a7[0-9a-z]", "");
      if(ClientProxy.serverType.equals("ng") && ClientData.currentFaction != null) {
         ClientEventHandler.STYLE.bindTexture("faction_btn");
         ModernGui.drawModalRectWithCustomSizedTexture((float)(this.field_74198_m + 7), (float)(this.field_74197_n + 223), 0, 233, 169, 23, 256.0F, 256.0F, false);
         ClientEventHandler.STYLE.bindTexture("inventory");
         Double var8;
         if(!ClientData.currentFaction.contains("Wilderness")) {
            var8 = Double.valueOf((double)this.field_73886_k.func_78256_a(I18n.func_135053_a("gui.inventory.tab.country")) * 1.2D / 2.0D);
            this.func_73729_b(this.field_74198_m + 91 - var8.intValue() - 17, this.field_74197_n + 225, 201, 107, 12, 17);
            this.drawScaledString(I18n.func_135053_a("gui.inventory.tab.country"), this.field_74198_m + 93 - var8.intValue(), this.field_74197_n + 230, 16777215, 1.2F, false, true);
         } else {
            var8 = Double.valueOf((double)this.field_73886_k.func_78256_a(I18n.func_135053_a("gui.inventory.tab.create")) * 1.2D / 2.0D);
            this.func_73729_b(this.field_74198_m + 91 - var8.intValue() - 17, this.field_74197_n + 225, 185, 107, 12, 17);
            this.drawScaledString(I18n.func_135053_a("gui.inventory.tab.create"), this.field_74198_m + 93 - var8.intValue(), this.field_74197_n + 230, 16777215, 1.2F, false, true);
         }
      }

      this.drawScaledString(this.player.getDisplayName(), this.field_74198_m + 90, this.field_74197_n + 10, 16777215, 1.7F, true, true);
      this.field_73886_k.func_78276_b(ClientData.currentFaction, this.field_74198_m + 90 - this.field_73886_k.func_78256_a(ClientData.currentFaction) / 2, this.field_74197_n + 28, 13027014);
      this.field_73882_e.field_71466_p.func_85187_a((int)ShopGUI.CURRENT_MONEY + " $", this.field_74198_m + 128, this.field_74197_n + 54, 16777215, true);
      GuiInventory.func_110423_a(this.field_74198_m + 55, this.field_74197_n + 114, 30, (float)(this.field_74198_m + 55 - mouseX), (float)(this.field_74197_n + 64 - mouseY), this.field_73882_e.field_71439_g);
      String var10 = I18n.func_135053_a("key.inventory");
      int var9 = this.field_74198_m + 10 + 81;
      w = this.field_73886_k.func_78256_a(var10);
      this.field_73882_e.field_71466_p.func_85187_a(var10, var9 - w / 2, this.field_74197_n + 128, 16777215, true);
      func_73734_a(this.field_74198_m + 10, this.field_74197_n + 134, var9 - w / 2 - 3, this.field_74197_n + 135, -1);
      func_73734_a(var9 + w / 2 + 3, this.field_74197_n + 134, this.field_74198_m + 171, this.field_74197_n + 135, -1);
      func_73734_a(this.field_74198_m + 10, this.field_74197_n + 135, var9 - w / 2 - 3, this.field_74197_n + 136, -10197916);
      func_73734_a(var9 + w / 2 + 3, this.field_74197_n + 135, this.field_74198_m + 171, this.field_74197_n + 136, -10197916);
   }

   protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
      if(mouseButton == 0) {
         int i;
         GuiScreenTab type;
         for(i = 0; i <= 4; ++i) {
            type = (GuiScreenTab)TABS.get(i);
            if(mouseX >= this.field_74198_m - 20 && mouseX <= this.field_74198_m + 3 && mouseY >= this.field_74197_n + 55 + i * 31 && mouseY <= this.field_74197_n + 85 + i * 31) {
               this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0F, 1.0F);
               if(this.getClass() != type.getClassReferent()) {
                  try {
                     this.field_73882_e.field_71439_g.func_71053_j();
                     type.call();
                  } catch (Exception var8) {
                     var8.printStackTrace();
                  }
               }
            }
         }

         for(i = 5; i < TABS.size(); ++i) {
            if(i != 5 || !isInAssault) {
               type = (GuiScreenTab)TABS.get(i);
               if(mouseX >= this.field_74198_m + 178 && mouseX <= this.field_74198_m + 201 && mouseY >= this.field_74197_n + 55 + (i - 4) * 31 && mouseY <= this.field_74197_n + 55 + 31 + (i - 4) * 31) {
                  this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0F, 1.0F);
                  if(this.getClass() != type.getClassReferent()) {
                     try {
                        this.field_73882_e.field_71439_g.func_71053_j();
                        type.call();
                     } catch (Exception var7) {
                        var7.printStackTrace();
                     }
                  }
               }
            }
         }

         if(ClientProxy.serverType.equals("ng") && mouseX >= this.field_74198_m + 7 && mouseX <= this.field_74198_m + 7 + 169 && mouseY >= this.field_74197_n + 223 && mouseY <= this.field_74197_n + 223 + 23 && ClientData.currentFaction != null) {
            if(!ClientData.currentFaction.contains("Wilderness")) {
               Minecraft.func_71410_x().func_71373_a(new FactionGUI(ClientData.currentFaction));
            } else {
               Minecraft.func_71410_x().func_71373_a(new FactionCreateGUI());
            }
         }
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

   static {
      TABS.add(new InventoryGUI$1());
      TABS.add(new InventoryGUI$2());
      TABS.add(new InventoryGUI$3());
      TABS.add(new InventoryGUI$4());
      TABS.add(new InventoryGUI$5());
      TABS.add(new InventoryGUI$6());
      TABS.add(new InventoryGUI$7());
      TABS.add(new InventoryGUI$8());
      TABS.add(new InventoryGUI$9());
   }
}
