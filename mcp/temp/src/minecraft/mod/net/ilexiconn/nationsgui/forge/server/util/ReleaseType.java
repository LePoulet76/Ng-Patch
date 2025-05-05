package net.ilexiconn.nationsgui.forge.server.util;

import net.ilexiconn.nationsgui.forge.server.util.ReleaseType$1;

public enum ReleaseType {

   DEVELOP("DEVELOP", 0),
   RELEASE_CANDIDATE("RELEASE_CANDIDATE", 1),
   RELEASE("RELEASE", 2);
   // $FF: synthetic field
   private static final ReleaseType[] $VALUES = new ReleaseType[]{DEVELOP, RELEASE_CANDIDATE, RELEASE};


   private ReleaseType(String var1, int var2) {}

   public abstract String getBranding();

   public static ReleaseType parseVersion(String version) {
      return version.contains("-dev")?DEVELOP:(version.contains("-rc")?RELEASE_CANDIDATE:RELEASE);
   }

   // $FF: synthetic method
   ReleaseType(String x0, int x1, ReleaseType$1 x2) {
      this(x0, x1);
   }

}
