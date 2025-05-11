/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 */
package net.ilexiconn.nationsgui.forge.client.gui.chat.tag;

import acs.tabbychat.TabbyChat;
import java.util.Arrays;
import java.util.Map;
import net.ilexiconn.nationsgui.forge.client.gui.chat.tag.AbstractActionTag;
import net.minecraft.client.Minecraft;

public class PlaceholderTag
extends AbstractActionTag {
    private String text;
    private String tooltip;
    private int width;

    public PlaceholderTag(Map<String, String> parameters) throws Exception {
        super(parameters);
        this.text = parameters.get("text");
        this.tooltip = parameters.get("tooltip");
        if (this.text == null) {
            throw new Exception("[ChatEngine] Invalid placeholder text");
        }
        this.text = "\u00a7n" + this.text;
        this.width = Minecraft.func_71410_x().field_71466_p.func_78256_a(this.text);
    }

    @Override
    public void render(int mouseX, int mouseY) {
        Minecraft.func_71410_x().field_71466_p.func_78276_b(this.text, 0, 0, 0xFFFFFF);
        if (this.tooltip != null && mouseX >= 0 && mouseX <= this.width && mouseY >= 0 && mouseY <= 8) {
            TabbyChat.gnc.addTooltipToDisplay(Arrays.asList(this.tooltip.split("\n")), mouseX, mouseY);
        }
    }

    @Override
    public void onClick(int mouseX, int mouseY) {
        if (mouseX >= 0 && mouseX <= this.getWidth() && mouseY >= 0 && mouseY <= 8) {
            System.out.println("CLICK ON SWAG !");
            this.doAction();
        }
    }

    @Override
    public int getWidth() {
        return this.width;
    }
}

