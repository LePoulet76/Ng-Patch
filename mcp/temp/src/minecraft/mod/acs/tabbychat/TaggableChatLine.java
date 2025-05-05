package acs.tabbychat;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.ilexiconn.nationsgui.forge.client.gui.chat.tag.AbstractChatTag;
import net.ilexiconn.nationsgui.forge.client.gui.chat.tag.ChatTagManager;
import net.ilexiconn.nationsgui.forge.server.util.Tuple;
import net.minecraft.client.gui.ChatLine;

public class TaggableChatLine extends ChatLine {

   private ArrayList<AbstractChatTag> tags = new ArrayList();
   private String line;


   public TaggableChatLine(int p_i1000_1_, String p_i1000_2_, ArrayList<AbstractChatTag> list, int p_i1000_3_) {
      super(p_i1000_1_, p_i1000_2_, p_i1000_3_);
      this.line = p_i1000_2_;
      this.tags = list;
   }

   public TaggableChatLine(int p_i1000_1_, String p_i1000_2_, int p_i1000_3_) {
      super(p_i1000_1_, p_i1000_2_, p_i1000_3_);
      this.computeString(p_i1000_2_);
   }

   public TaggableChatLine(ChatLine chatLine) {
      super(chatLine.func_74540_b(), chatLine.func_74538_a(), chatLine.func_74539_c());
      if(chatLine instanceof TaggableChatLine) {
         TaggableChatLine chatLine1 = (TaggableChatLine)chatLine;
         this.line = chatLine1.line;
         this.tags = chatLine1.tags;
      } else {
         this.computeString(chatLine.func_74538_a());
      }

   }

   public String func_74538_a() {
      return this.line;
   }

   private void computeString(String line) {
      Tuple tuple = ChatTagManager.parseChatLine(line);
      this.line = (String)tuple.a;
      this.tags = (ArrayList)tuple.b;
   }

   public ArrayList<AbstractChatTag> getTags() {
      return this.tags;
   }

   public static ArrayList<TaggableChatLine> convertList(List<ChatLine> list) {
      ArrayList r = new ArrayList();
      Iterator var2 = list.iterator();

      while(var2.hasNext()) {
         ChatLine chatLine = (ChatLine)var2.next();
         r.add(new TaggableChatLine(chatLine));
      }

      return r;
   }
}
