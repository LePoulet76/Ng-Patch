package net.ilexiconn.nationsgui.forge.client.gui;

import java.io.IOException;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;

class CustomizationGUI$3 extends GuiButtonOptionBoolean
{
    final CustomizationGUI this$0;

    CustomizationGUI$3(CustomizationGUI this$0, int x0, int x1, int x2, int x3, int x4, String x5)
    {
        super(x0, x1, x2, x3, x4, x5);
        this.this$0 = this$0;
    }

    public Boolean getData()
    {
        return Boolean.valueOf(ClientProxy.clientConfig.azimutBottom);
    }

    public void setData(Boolean data)
    {
        ClientProxy.clientConfig.azimutBottom = data.booleanValue();

        try
        {
            ClientProxy.saveConfig();
        }
        catch (IOException var3)
        {
            var3.printStackTrace();
        }
    }

    protected String getEnabledStateText()
    {
        return "Bas";
    }

    protected String getDisabledStateText()
    {
        return "Haut";
    }

    public Object getData()
    {
        return this.getData();
    }

    public void setData(Object var1)
    {
        this.setData((Boolean)var1);
    }
}
