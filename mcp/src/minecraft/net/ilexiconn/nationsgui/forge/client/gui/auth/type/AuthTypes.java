/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 */
package net.ilexiconn.nationsgui.forge.client.gui.auth.type;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.ilexiconn.nationsgui.forge.client.gui.auth.type.IAuthType;
import net.ilexiconn.nationsgui.forge.client.gui.auth.type.LoginType;
import net.ilexiconn.nationsgui.forge.client.gui.auth.type.RegisterType;

@SideOnly(value=Side.CLIENT)
public enum AuthTypes {
    LOGIN(new LoginType()),
    REGISTER(new RegisterType());

    private IAuthType type;

    private AuthTypes(IAuthType type) {
        this.type = type;
    }

    public IAuthType getType() {
        return this.type;
    }
}

