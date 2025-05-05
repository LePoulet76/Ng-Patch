package net.ilexiconn.nationsgui.forge.client.gui.assistance;

import cpw.mods.fml.common.network.PacketDispatcher;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import net.ilexiconn.nationsgui.forge.client.gui.advanced.CheckboxComponent;
import net.ilexiconn.nationsgui.forge.client.gui.advanced.DropdownComponent;
import net.ilexiconn.nationsgui.forge.client.gui.advanced.GuiComponent;
import net.ilexiconn.nationsgui.forge.client.gui.advanced.TextAreaComponent;
import net.ilexiconn.nationsgui.forge.client.gui.assistance.AbstractAssistanceListedGUI;
import net.ilexiconn.nationsgui.forge.client.gui.assistance.AssistanceButton;
import net.ilexiconn.nationsgui.forge.client.util.ScreenshotHelper;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.AssistanceNewTicketPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.ScreenAuthorizationPacket;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

public class AssistanceNewTicketGUI extends AbstractAssistanceListedGUI {

   private TextAreaComponent textAreaComponent;
   private CheckboxComponent screenshot;
   private DropdownComponent dropdownComponent;
   private GuiButton sendButton;
   private boolean locked = false;
   public String screenUrl = null;
   public String screenToken = null;
   public String base64 = null;
   private boolean threadLaunched = false;
   private static List<String> categories = Arrays.asList(new String[]{"assistance.category.general", "assistance.category.bug", "assistance.category.rp", "assistance.category.sanction"});


   public AssistanceNewTicketGUI() {
      PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new ScreenAuthorizationPacket()));
   }

   public void func_73866_w_() {
      super.func_73866_w_();
      this.textAreaComponent = new TextAreaComponent(this.guiLeft + 202, this.guiTop + 84, 157, 9);
      this.screenshot = new CheckboxComponent(this.guiLeft + 202, this.guiTop + 183, I18n.func_135053_a("nationsgui.assistance.takeScreenshot"));
      this.dropdownComponent = new DropdownComponent(this.guiLeft + 202, this.guiTop + 61, 157, I18n.func_135053_a("nationsgui.assistance.categoryDropdown"));
      Iterator var1 = categories.iterator();

      while(var1.hasNext()) {
         String key = (String)var1.next();
         this.dropdownComponent.getChoices().add(I18n.func_135053_a(key));
      }

      this.addComponent(this.textAreaComponent);
      this.addComponent(this.screenshot);
      this.addComponent(this.dropdownComponent);
      this.sendButton = new AssistanceButton(0, this.guiLeft + 202, this.guiTop + 197, 157, 20, I18n.func_135053_a("nationsgui.assistance.sendTicket"));
      this.field_73887_h.add(this.sendButton);
   }

   public void func_73863_a(int mouseX, int mouseY, float partialTick) {
      if(this.screenshot.isChecked() && !this.threadLaunched) {
         ScreenshotHelper.saveScreenshotToBase64(this.field_73882_e.field_71443_c, this.field_73882_e.field_71440_d);
         this.threadLaunched = true;
      }

      super.func_73863_a(mouseX, mouseY, partialTick);
   }

   protected void drawGui(int mouseX, int mouseY, float partialTick) {
      super.drawGui(mouseX, mouseY, partialTick);
      this.field_73886_k.func_78276_b(I18n.func_135053_a("nationsgui.assistance.createTicket"), this.guiLeft + 202, this.guiTop + 50, 1579032);
   }

   public boolean canSend() {
      return !this.locked && this.dropdownComponent.getSelectionIndex() != -1 && !this.textAreaComponent.getText().equals("") && (!this.screenshot.isChecked() || this.screenToken != null && this.base64 != null);
   }

   public void func_73876_c() {
      this.sendButton.field_73742_g = this.canSend();
   }

   protected void func_73875_a(GuiButton par1GuiButton) {
      if(par1GuiButton.field_73741_f == 0) {
         String screenshotUrl = "";
         if(this.screenshot.isChecked() && this.base64 != null) {
            try {
               CloseableHttpClient e = HttpClients.createDefault();
               HttpPost httpPost = new HttpPost("https://apiv2.nationsglory.fr/mods/screen_api_uploader");
               ArrayList nvps = new ArrayList();
               nvps.add(new BasicNameValuePair("file", this.base64));
               nvps.add(new BasicNameValuePair("target", this.field_73882_e.field_71439_g.field_71092_bJ));
               nvps.add(new BasicNameValuePair("token", this.screenToken));
               httpPost.setEntity(new UrlEncodedFormEntity(nvps));
               e.execute(httpPost);
               e.close();
               screenshotUrl = this.screenUrl;
            } catch (IOException var6) {
               var6.printStackTrace();
            }
         }

         PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new AssistanceNewTicketPacket((String)categories.get(this.dropdownComponent.getSelectionIndex()), this.textAreaComponent.getText(), screenshotUrl)));
         this.locked = true;
      }

   }

   public void actionPerformed(GuiComponent guiComponent) {
      super.actionPerformed(guiComponent);
   }

}
