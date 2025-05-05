package net.ilexiconn.nationsgui.forge.client.exceptions;


public class HatCreationException extends Exception {

   public HatCreationException(String name) {
      System.out.println("Try to create a hat with null model URL oy texture URL! : " + name);
   }
}
