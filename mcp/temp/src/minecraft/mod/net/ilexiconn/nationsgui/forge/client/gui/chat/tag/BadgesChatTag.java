package net.ilexiconn.nationsgui.forge.client.gui.chat.tag;

import acs.tabbychat.TabbyChat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.ilexiconn.nationsgui.forge.NationsGUI;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.gui.chat.tag.AbstractChatTag;
import net.ilexiconn.nationsgui.forge.client.itemskin.AbstractSkin;
import net.ilexiconn.nationsgui.forge.client.itemskin.SkinType;
import org.lwjgl.opengl.GL11;

public class BadgesChatTag extends AbstractChatTag {

   String playerName;
   Integer badgeCount = Integer.valueOf(0);


   public BadgesChatTag(Map<String, String> parameters) throws Exception {
      super(parameters);
      this.playerName = ((String)parameters.get("playerName")).split(" ")[((String)parameters.get("playerName")).split(" ").length - 1].replaceAll("\u00a70x.{6}", "").replaceAll("\u00a7.{1}", "");
   }

   public void render(int mouseX, int mouseY) {
      if(!ClientProxy.SKIN_MANAGER.getPlayerActiveSkins(this.playerName, SkinType.BADGES).isEmpty()) {
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         int badgeCounter = 0;
         ArrayList displayedBadges = new ArrayList();
         Iterator var5 = ClientProxy.SKIN_MANAGER.getPlayerActiveSkins(this.playerName, SkinType.BADGES).iterator();

         while(var5.hasNext()) {
            AbstractSkin badgeSkin = (AbstractSkin)var5.next();
            if(!displayedBadges.contains(badgeSkin.getId()) && badgeCounter < 2) {
               GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
               badgeSkin.renderInGUI(0 + badgeCounter * 7, 0, 0.33F, 0.0F);
               displayedBadges.add(badgeSkin.getId());
               if(mouseX >= 2 + badgeCounter * 8 && mouseX <= 2 + badgeCounter * 8 + 8 && mouseY >= 1 && mouseY <= 8) {
                  TabbyChat.gnc.addTooltipToDisplay((List)NationsGUI.BADGES_TOOLTIPS.get(badgeSkin.getId()), mouseX, mouseY);
               }

               ++badgeCounter;
            }
         }

         this.badgeCount = Integer.valueOf(badgeCounter);
      }

   }

   public void onClick(int mouseX, int mouseY) {}

   public int getWidth() {
      return this.badgeCount.intValue() * 6;
   }
}
