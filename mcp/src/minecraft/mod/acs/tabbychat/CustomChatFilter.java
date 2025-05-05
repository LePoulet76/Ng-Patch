package acs.tabbychat;

import java.io.Serializable;
import java.util.regex.Pattern;
import net.minecraft.client.Minecraft;

public class CustomChatFilter implements Serializable
{
    protected String name;
    protected Pattern filter;
    protected boolean invert = false;
    protected boolean caseSensitive = true;
    protected boolean sendToTab = false;
    protected boolean highlight = false;
    protected ChatColorEnum highlightColor;
    protected ChatColorEnum highlightFormat;
    protected boolean ding;
    protected int chanID;
    protected boolean active;
    private static final long serialVersionUID = 1245780L;
    private String lastMatch;

    protected CustomChatFilter()
    {
        this.highlightColor = ChatColorEnum.RED;
        this.highlightFormat = ChatColorEnum.BOLD;
        this.ding = false;
        this.chanID = 0;
        this.active = true;
        this.lastMatch = "";
        this.name = "<New>";
        this.filter = Pattern.compile(Minecraft.getMinecraft().thePlayer.username);
    }
}
