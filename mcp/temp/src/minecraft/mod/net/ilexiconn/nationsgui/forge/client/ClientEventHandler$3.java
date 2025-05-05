package net.ilexiconn.nationsgui.forge.client;

import java.io.File;
import java.io.FilenameFilter;

final class ClientEventHandler$3 implements FilenameFilter {

   public boolean accept(File dir, String name) {
      return name.endsWith("jar");
   }
}
