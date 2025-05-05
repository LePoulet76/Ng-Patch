package net.ilexiconn.nationsgui.forge.server;


public class Mail {

   public final String sender;
   public final String title;
   public final String content;


   public Mail(String sender, String title, String content) {
      this.sender = sender;
      this.title = title;
      this.content = content;
   }
}
