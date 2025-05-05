package acs.tabbychat;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.ilexiconn.nationsgui.forge.client.gui.chat.tag.AbstractChatTag;
import net.ilexiconn.nationsgui.forge.client.gui.chat.tag.ChatTagManager;
import net.ilexiconn.nationsgui.forge.server.util.Tuple;
import net.minecraft.client.gui.ChatLine;

public class TaggableChatLine extends ChatLine
{
    private ArrayList<AbstractChatTag> tags = new ArrayList();
    private String line;

    public TaggableChatLine(int par1, String par2Str, ArrayList<AbstractChatTag> list, int par3)
    {
        super(par1, par2Str, par3);
        this.line = par2Str;
        this.tags = list;
    }

    public TaggableChatLine(int par1, String par2Str, int par3)
    {
        super(par1, par2Str, par3);
        this.computeString(par2Str);
    }

    public TaggableChatLine(ChatLine chatLine)
    {
        super(chatLine.getUpdatedCounter(), chatLine.getChatLineString(), chatLine.getChatLineID());

        if (chatLine instanceof TaggableChatLine)
        {
            TaggableChatLine chatLine1 = (TaggableChatLine)chatLine;
            this.line = chatLine1.line;
            this.tags = chatLine1.tags;
        }
        else
        {
            this.computeString(chatLine.getChatLineString());
        }
    }

    public String getChatLineString()
    {
        return this.line;
    }

    private void computeString(String line)
    {
        Tuple tuple = ChatTagManager.parseChatLine(line);
        this.line = (String)tuple.a;
        this.tags = (ArrayList)tuple.b;
    }

    public ArrayList<AbstractChatTag> getTags()
    {
        return this.tags;
    }

    public static ArrayList<TaggableChatLine> convertList(List<ChatLine> list)
    {
        ArrayList r = new ArrayList();
        Iterator var2 = list.iterator();

        while (var2.hasNext())
        {
            ChatLine chatLine = (ChatLine)var2.next();
            r.add(new TaggableChatLine(chatLine));
        }

        return r;
    }
}
