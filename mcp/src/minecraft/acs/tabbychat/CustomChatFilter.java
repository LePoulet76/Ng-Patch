/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 */
package acs.tabbychat;

import acs.tabbychat.ChatColorEnum;
import java.io.Serializable;
import java.util.regex.Pattern;
import net.minecraft.client.Minecraft;

public class CustomChatFilter
implements Serializable {
    protected String name = "<New>";
    protected Pattern filter;
    protected boolean invert = false;
    protected boolean caseSensitive = true;
    protected boolean sendToTab = false;
    protected boolean highlight = false;
    protected ChatColorEnum highlightColor = ChatColorEnum.RED;
    protected ChatColorEnum highlightFormat = ChatColorEnum.BOLD;
    protected boolean ding = false;
    protected int chanID = 0;
    protected boolean active = true;
    private static final long serialVersionUID = 1245780L;
    private String lastMatch = "";

    protected CustomChatFilter() {
        this.filter = Pattern.compile(Minecraft.func_71410_x().field_71439_g.field_71092_bJ);
    }
}

