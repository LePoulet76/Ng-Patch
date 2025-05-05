package net.ilexiconn.nationsgui.forge.client.gui.chat.tag;

import java.util.Map;
import net.ilexiconn.nationsgui.forge.client.gui.chat.tag.AbstractChatTag;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionGUI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;

public class CountryChatTag extends AbstractChatTag {

   String countryName;
   String reformatedCountryName;
   String countryColor;


   public CountryChatTag(Map<String, String> parameters) throws Exception {
      super(parameters);
      this.countryName = (String)parameters.get("name");
      this.reformatedCountryName = this.countryName.replaceAll("Empire", "Emp");
      this.countryColor = ((String)parameters.get("color")).replaceAll("\u00a7f", "\u00a77");
   }

   public void render(int mouseX, int mouseY) {
      if(!this.reformatedCountryName.isEmpty()) {
         Minecraft.func_71410_x().field_71466_p.func_78261_a(this.countryColor + this.reformatedCountryName, 1, 0, 16777215);
      } else {
         Minecraft.func_71410_x().field_71466_p.func_78261_a("\u00a77" + I18n.func_135053_a("main.no_country"), 1, 0, 16777215);
      }

   }

   public void onClick(int mouseX, int mouseY) {
      if(!this.countryName.isEmpty() && mouseX >= 2 && mouseX <= Minecraft.func_71410_x().field_71466_p.func_78256_a(this.reformatedCountryName) && mouseY >= 0 && mouseY <= 8) {
         Minecraft.func_71410_x().func_71373_a(new FactionGUI(this.countryName));
      }

   }

   public int getWidth() {
      return Minecraft.func_71410_x().field_71466_p.func_78256_a(!this.countryName.isEmpty()?this.reformatedCountryName:I18n.func_135053_a("main.no_country"));
   }
}
