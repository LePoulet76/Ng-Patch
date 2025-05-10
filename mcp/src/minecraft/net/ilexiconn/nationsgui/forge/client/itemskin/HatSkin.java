package net.ilexiconn.nationsgui.forge.client.itemskin;

import org.json.simple.JSONObject;

public class HatSkin extends BodyPartSkin
{
    public HatSkin(JSONObject obj)
    {
        super(obj);
    }

    protected String getDomain()
    {
        return "hats";
    }
}
