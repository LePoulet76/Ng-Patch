/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.gui.advanced;

import java.util.ArrayList;
import java.util.List;
import net.ilexiconn.nationsgui.forge.client.gui.assistance.AbstractAssistanceComponent;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;

public class DropdownComponent
extends AbstractAssistanceComponent {
    private final int posX;
    private final int posY;
    private final int width;
    private final String placeholder;
    private final List<String> choices = new ArrayList<String>();
    private int selection = -1;
    private boolean open = false;

    public DropdownComponent(int posX, int posY, int width) {
        this(posX, posY, width, "");
    }

    public DropdownComponent(int posX, int posY, int width, String placeholder) {
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.placeholder = placeholder;
    }

    public List<String> getChoices() {
        return this.choices;
    }

    public String getSelection() {
        return this.choices.get(this.selection);
    }

    public int getSelectionIndex() {
        return this.selection;
    }

    @Override
    public void draw(int mouseX, int mouseY, float partialTicks) {
        ModernGui.drawNGBlackSquare(this.posX, this.posY, this.width, 20);
        GL11.glColor3f((float)1.0f, (float)1.0f, (float)1.0f);
        this.func_73729_b(this.posX + this.width - 19, this.posY, 159, 256 + (this.open ? 20 : 0), 20, 19);
        this.func_73731_b(Minecraft.func_71410_x().field_71466_p, this.getDisplayString(), this.posX + 5, this.posY + 6, 0xFFFFFF);
        if (this.open) {
            for (int i = 0; i < this.choices.size(); ++i) {
                int offset = 20 * (i + 1);
                ModernGui.drawNGBlackSquare(this.posX, this.posY + offset - (1 + i), this.width, 20);
                this.func_73731_b(Minecraft.func_71410_x().field_71466_p, this.choices.get(i), this.posX + 5, this.posY + 6 + offset - (1 + i), 0xFFFFFF);
            }
        }
    }

    private String getDisplayString() {
        return this.selection == -1 ? (this.placeholder.equals("") ? this.choices.get(0) : this.placeholder) : this.choices.get(this.selection);
    }

    @Override
    public void onClick(int mouseX, int mouseY, int clickType) {
        if (mouseX >= this.posX && mouseX <= this.posX + this.width && mouseY >= this.posY) {
            int index;
            if (mouseY <= this.posY + 20) {
                this.open = !this.open;
            } else if (this.open && mouseY <= this.posY + 19 * (this.choices.size() + 1) && (index = (mouseY - this.posY - 20) / 20) < this.choices.size()) {
                this.selection = index;
                this.open = false;
                this.container.actionPerformed(this);
            }
        }
    }

    @Override
    public void update() {
    }

    @Override
    public void keyTyped(char c, int key) {
    }

    @Override
    public boolean isPriorityClick() {
        return this.open;
    }
}

