package net.ilexiconn.nationsgui.forge.client.gui.auth.type;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public enum AuthTypes
{
    LOGIN(new LoginType()),
    REGISTER(new RegisterType());
    private IAuthType type;

    private AuthTypes(IAuthType type)
    {
        this.type = type;
    }

    public IAuthType getType()
    {
        return this.type;
    }
}
