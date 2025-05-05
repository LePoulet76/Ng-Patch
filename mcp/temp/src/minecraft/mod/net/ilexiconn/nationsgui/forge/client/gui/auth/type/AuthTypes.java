package net.ilexiconn.nationsgui.forge.client.gui.auth.type;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.ilexiconn.nationsgui.forge.client.gui.auth.type.IAuthType;
import net.ilexiconn.nationsgui.forge.client.gui.auth.type.LoginType;
import net.ilexiconn.nationsgui.forge.client.gui.auth.type.RegisterType;

@SideOnly(Side.CLIENT)
public enum AuthTypes {

   LOGIN("LOGIN", 0, new LoginType()),
   REGISTER("REGISTER", 1, new RegisterType());
   private IAuthType type;
   // $FF: synthetic field
   private static final AuthTypes[] $VALUES = new AuthTypes[]{LOGIN, REGISTER};


   private AuthTypes(String var1, int var2, IAuthType type) {
      this.type = type;
   }

   public IAuthType getType() {
      return this.type;
   }

}
