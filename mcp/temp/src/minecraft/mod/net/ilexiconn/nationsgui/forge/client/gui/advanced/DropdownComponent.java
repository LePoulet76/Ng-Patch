package net.ilexiconn.nationsgui.forge.client.gui.advanced;

import java.util.ArrayList;
import java.util.List;
import net.ilexiconn.nationsgui.forge.client.gui.assistance.AbstractAssistanceComponent;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;

public class DropdownComponent extends AbstractAssistanceComponent {

   private final int posX;
   private final int posY;
   private final int width;
   private final String placeholder;
   private final List<String> choices;
   private int selection;
   private boolean open;


   public DropdownComponent(int posX, int posY, int width) {
      this(posX, posY, width, "");
   }

   public DropdownComponent(int posX, int posY, int width, String placeholder) {
      this.choices = new ArrayList();
      this.selection = -1;
      this.open = false;
      this.posX = posX;
      this.posY = posY;
      this.width = width;
      this.placeholder = placeholder;
   }

   public List<String> getChoices() {
      return this.choices;
   }

   public String getSelection() {
      return (String)this.choices.get(this.selection);
   }

   public int getSelectionIndex() {
      return this.selection;
   }

   public void draw(int mouseX, int mouseY, float partialTicks) {
      ModernGui.drawNGBlackSquare(this.posX, this.posY, this.width, 20);
      GL11.glColor3f(1.0F, 1.0F, 1.0F);
      this.func_73729_b(this.posX + this.width - 19, this.posY, 159, 256 + (this.open?20:0), 20, 19);
      this.func_73731_b(Minecraft.func_71410_x().field_71466_p, this.getDisplayString(), this.posX + 5, this.posY + 6, 16777215);
      if(this.open) {
         for(int i = 0; i < this.choices.size(); ++i) {
            int offset = 20 * (i + 1);
            ModernGui.drawNGBlackSquare(this.posX, this.posY + offset - (1 + i), this.width, 20);
            this.func_73731_b(Minecraft.func_71410_x().field_71466_p, (String)this.choices.get(i), this.posX + 5, this.posY + 6 + offset - (1 + i), 16777215);
         }
      }

   }

   private String getDisplayString() {
      return this.selection == -1?(this.placeholder.equals("")?(String)this.choices.get(0):this.placeholder):(String)this.choices.get(this.selection);
   }

   public void onClick(int mouseX, int mouseY, int clickType) {
      if(mouseX >= this.posX && mouseX <= this.posX + this.width && mouseY >= this.posY) {
         if(mouseY <= this.posY + 20) {
            this.open = !this.open;
         } else if(this.open && mouseY <= this.posY + 19 * (this.choices.size() + 1)) {
            int index = (mouseY - this.posY - 20) / 20;
            if(index < this.choices.size()) {
               this.selection = index;
               this.open = false;
               this.container.actionPerformed(this);
            }
         }
      }

   }

   public void update() {}

   public void keyTyped(char c, int key) {}

   public boolean isPriorityClick() {
      return this.open;
   }
}
