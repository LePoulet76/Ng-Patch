package net.ilexiconn.nationsgui.forge.client.gui;

import java.util.ArrayList;
import java.util.List;
import net.ilexiconn.nationsgui.forge.client.gui.FirstConnectionChoiceGui;

public class FirstConnectionChoiceGui$Data {

   private List<String> availableFactions;
   private List<String> openFactions;
   // $FF: synthetic field
   final FirstConnectionChoiceGui this$0;


   public FirstConnectionChoiceGui$Data(FirstConnectionChoiceGui this$0) {
      this.this$0 = this$0;
      this.availableFactions = new ArrayList();
      this.openFactions = new ArrayList();
   }

   public List<String> getAvailableFactions() {
      return this.availableFactions;
   }

   public List<String> getOpenFactions() {
      return this.openFactions;
   }

   // $FF: synthetic method
   static List access$000(FirstConnectionChoiceGui$Data x0) {
      return x0.availableFactions;
   }

   // $FF: synthetic method
   static List access$100(FirstConnectionChoiceGui$Data x0) {
      return x0.openFactions;
   }
}
