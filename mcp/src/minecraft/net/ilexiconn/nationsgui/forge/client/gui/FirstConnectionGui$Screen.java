package net.ilexiconn.nationsgui.forge.client.gui;

import java.util.ArrayList;
import java.util.List;

class FirstConnectionGui$Screen
{
    private String image;
    private List<String> text;

    final FirstConnectionGui this$0;

    private FirstConnectionGui$Screen(FirstConnectionGui var1)
    {
        this.this$0 = var1;
        this.image = "";
        this.text = new ArrayList();
    }

    public String getImage()
    {
        return this.image;
    }

    public List<String> getText()
    {
        return this.text;
    }

    static String access$000(FirstConnectionGui$Screen x0)
    {
        return x0.image;
    }

    static List access$100(FirstConnectionGui$Screen x0)
    {
        return x0.text;
    }
}
