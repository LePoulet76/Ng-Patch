/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.io.ByteArrayDataInput
 *  com.google.common.io.ByteArrayDataOutput
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  cpw.mods.fml.common.network.Player
 *  fr.nationsglory.client.gui.OGMMachineGUI
 *  fr.nationsglory.ngcontent.NGContent
 *  fr.nationsglory.server.block.entity.GCOGMMachineBlockEntity
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.entity.player.InventoryPlayer
 *  net.minecraft.item.ItemStack
 *  net.minecraft.network.packet.Packet
 *  net.minecraft.tileentity.TileEntity
 */
package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import fr.nationsglory.client.gui.OGMMachineGUI;
import fr.nationsglory.ngcontent.NGContent;
import fr.nationsglory.server.block.entity.GCOGMMachineBlockEntity;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import net.ilexiconn.nationsgui.forge.NationsGUI;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IServerPacket;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.Packet;
import net.minecraft.tileentity.TileEntity;

public class OGMMachineCraftPacket
implements IPacket,
IServerPacket,
IClientPacket {
    public static Map<String, String> indicesText = new HashMap<String, String>(){
        {
            this.put("201#0", "V\u00e9g\u00e9tal \u00e0 bulles##Plante offrant de l'oxyg\u00e8ne##Plante remplie d'O\u00b2");
            this.put("204#0", "V\u00e9g\u00e9tal issue d'une liane aquatique##Organisme vivant capable de photosynth\u00e8se oxyg\u00e9nique##V\u00e9g\u00e9tal chlorophyllien aquatique");
            this.put("208#0", "V\u00e9g\u00e9tal tubulaire d'eau chaude##Corail vivant dans les oc\u00e9ans chauds");
            this.put("208#6", "Corail capable de percer la peau humaine##V\u00e9g\u00e9tal sous-marin de la famille des Milleporid\u00e9s##V\u00e9g\u00e9ral aquatique qui brule");
            this.put("208#4", "Esp\u00e8ce de coraux durs##Corail d'apparence mou");
            this.put("348#0", "Poussi\u00e8re de roche de lumi\u00e8re##Poudre issue du monde des enfers");
            this.put("338#0", "V\u00e9g\u00e9tal en forme de baton##V\u00e9g\u00e9tal cultiv\u00e9 pour son sucre");
            this.put("367#0", "Viande en \u00e9tat de d\u00e9composition##Viande issue de cr\u00e9atures contamin\u00e9es");
            this.put("351#15", "Poudre \u00e0 haute teneur en calcium##Os broy\u00e9s##Engrais naturel");
            this.put("381#0", "Vision du monde de l'End##Oeil montrant le chemin");
            this.put("378#0", "Boule de chaleur onctueuse##Ressource permettant de resiter au feu");
            this.put("377#0", "Poudre assimil\u00e9e au feu##Source de force en bouteille");
            this.put("376#0", "Vision d'araknyde matur\u00e9e##Ressource issue du sucre et champignon##Ressource qui fait tourner les potions");
            this.put("375#0", "Vision d'araknyde##Element principal du poison##Ressource \u00e0 haute toxicit\u00e9##Donne lieu \u00e0 un liquide mortel");
            this.put("353#0", "Element qui compense l'acidit\u00e9##Hydrate de carbone simple ou complexe");
            this.put("370#0", "Element r\u00e9g\u00e9nerant##Liquide physiologique aqueux et sal\u00e9##Larme de l'enfer");
            this.put("372#0", "Plante de l'enfer##Ressource issue du sable des \u00e2mes##Element d'alchimie de base");
            this.put("368#0", "Ressource g\u00e9n\u00e9ratrice de faille temporelle##Element principal de la vision de l'End");
            this.put("396#0", "Accel\u00e9rateur de croissance animale##Element renfor\u00e7ant la vision nocturne##D\u00e9clencheur d'amour chez les \u00e9quid\u00e9s");
            this.put("3501#0", "V\u00e9g\u00e9tal aquatique enchant\u00e9##Plante exp\u00e9riment\u00e9e");
            this.put("331#0", "Poudre \u00e9lectrique##Element allongeant les effets##Ressource permettant la transmission");
            this.put("341#0", "Colle naturelle##Ressource \u00e0 haute elasticit\u00e9");
            this.put("384#0", "Fiole scintillante##Objet d'exp\u00e9rience##Objet qui renferme des orbes");
            this.put("2323#0", "V\u00e9g\u00e9tal lointain mais d\u00e9j\u00e0 atteint##Plante carnivore lunaire");
            this.put("289#0", "Poudre explosive##M\u00e9lange de souffre, nitrate de potassium et charbon de bois");
            this.put("351#0", "Colorant sous-marin##Liquide s\u00e9cr\u00e9t\u00e9 par certains c\u00e9phalopodes##Element fabriqu\u00e9 dans la poche du noir");
            this.put("14258#0", "Graine de fleur color\u00e9e de couleur chaude##Graine de fleur d'un m\u00e9lange jaune et rouge##Graine de fleur d'encouragement");
            this.put("14271#0", "Graine de fleur color\u00e9e de couleur sang##Graine de fleur de couleur primaire##Graine de fleur symbole de vie, de feu");
            this.put("14268#0", "Graine de fleur color\u00e9e de couleur nuit##Graine de fleur de couleur primaire##Graine de fleur symbole de clart\u00e9");
            this.put("14263#0", "Graine de fleur amoureuse##Graine de fleur d'un m\u00e9lange rouge et blanc##Graine de fleur symbole d'amour");
            this.put("14267#0", "Graine de fleur d'un m\u00e9lange bleu et rouge##Graine de fleur symbole d'humilit\u00e9");
            this.put("13264#0", "Graine de c\u00e9r\u00e9ale riche en amidon##Graine de c\u00e9r\u00e9rale appr\u00e9ci\u00e9e des \u00e9quid\u00e9s");
            this.put("13270#0", "Graine de c\u00e9r\u00e9ale rustique##Grain de c\u00e9r\u00e9rale propice \u00e0 l'ergot");
            this.put("13658#0", "Graine d'arachide##Graine de cacao de terre");
            this.put("16039#0", "Graine anciennement petite rave##Graine de l\u00e9gume rose et blanc");
            this.put("16160#0", "Drag\u00e9s color\u00e9s##Sucre et gel\u00e9 color\u00e9s");
            this.put("16191#0", "Graines appel\u00e9es Pipas##Graine de soleil sal\u00e9es");
            this.put("16054#0", "Graine pour boisson aromatique##Graines id\u00e9ales pour une infusion##Graines originaires d'Himalaya");
            this.put("16044#0", "Graine douce##Graine populaire pour son tubercule");
            this.put("15991#0", "Graine d'\u00e9pice jaune ou noir##Graine de condiment");
            this.put("32#0", "V\u00e9g\u00e9tal d\u00e9sertique s\u00e9ch\u00e9##V\u00e9g\u00e9tal mort s\u00e9ch\u00e9");
            this.put("13258#0", "Graine de c\u00e9r\u00e9ale ancien##Graine de c\u00e9r\u00e9ale m\u00e9diterran\u00e9en");
            this.put("15457#0", "Graine de fruit qui n'est pas un l\u00e9gume##Graine de fruit rouge");
            this.put("15758#0", "Graine de fruit tr\u00e8s \u00e9pic\u00e9##Graine de condiment piquant");
        }
    };
    private String ogmName;
    private int tileX;
    private int tileY;
    private int tileZ;
    private String error = "";
    private float percentSucces = 0.0f;

    public OGMMachineCraftPacket(String ogmName, int tileX, int tileY, int tileZ) {
        this.ogmName = ogmName;
        this.tileX = tileX;
        this.tileY = tileY;
        this.tileZ = tileZ;
    }

    @Override
    public void fromBytes(ByteArrayDataInput data) {
        this.ogmName = data.readUTF();
        this.tileX = data.readInt();
        this.tileY = data.readInt();
        this.tileZ = data.readInt();
        this.error = data.readUTF();
        this.percentSucces = data.readFloat();
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
        data.writeUTF(this.ogmName);
        data.writeInt(this.tileX);
        data.writeInt(this.tileY);
        data.writeInt(this.tileZ);
        data.writeUTF(this.error);
        data.writeFloat(this.percentSucces);
    }

    @Override
    public void handleServerPacket(EntityPlayer player) {
        TileEntity tileEntity;
        List<String> recipeIngredients = NationsGUI.getOGMRecipe(this.ogmName);
        if (recipeIngredients != null && (tileEntity = player.func_130014_f_().func_72796_p(this.tileX, this.tileY, this.tileZ)) instanceof GCOGMMachineBlockEntity) {
            GCOGMMachineBlockEntity ogmMachineEntity = (GCOGMMachineBlockEntity)tileEntity;
            if (ogmMachineEntity.energyStored >= 50.0f) {
                ogmMachineEntity.energyStored -= 50.0f;
                Collections.shuffle(recipeIngredients);
                int amountValidItems = 0;
                int totalItemsRequired = 0;
                block0: for (String ingredientData : recipeIngredients) {
                    totalItemsRequired += Integer.parseInt(ingredientData.split("#")[2]);
                    int farmerLevel = NationsGUI.getPlayerSkillLevel(player.field_71092_bJ, "farmer");
                    int goodQte = Integer.parseInt(ingredientData.split("#")[2]);
                    Random rand = new Random();
                    int qteMin = Math.max(0, goodQte - rand.nextInt(farmerLevel >= 3 ? 2 : 4));
                    int qteMax = Math.min(64, goodQte + rand.nextInt(farmerLevel >= 3 ? 2 : 4));
                    for (int i = 0; i <= 8; ++i) {
                        if (ogmMachineEntity.itemStacks[i].field_77993_c != Integer.parseInt(ingredientData.split("#")[0]) || ogmMachineEntity.itemStacks[i].func_77960_j() != Integer.parseInt(ingredientData.split("#")[1])) continue;
                        if (ogmMachineEntity.itemStacks[i].field_77994_a != goodQte) {
                            this.error = "quantity#" + qteMin + "#" + qteMax + "###" + indicesText.get(ingredientData.split("#")[0] + "#" + ingredientData.split("#")[1]);
                            continue block0;
                        }
                        amountValidItems += Integer.parseInt(ingredientData.split("#")[2]);
                        continue block0;
                    }
                    this.error = "quantity#" + qteMin + "#" + qteMax + "###" + indicesText.get(ingredientData.split("#")[0] + "#" + ingredientData.split("#")[1]);
                }
                for (int i = 0; i <= 8; ++i) {
                    ogmMachineEntity.itemStacks[i] = null;
                }
                if (this.error.isEmpty()) {
                    this.error = "success_" + this.ogmName;
                }
                if (this.error.contains("success")) {
                    this.percentSucces = 1.0f;
                    PacketDispatcher.sendPacketToPlayer((Packet)PacketRegistry.INSTANCE.generatePacket(this), (Player)((Player)player));
                    ((GCOGMMachineBlockEntity)tileEntity).itemStacks[9] = new ItemStack(NGContent.getCerealIdFromName((String)this.ogmName) + 1, 64, 0);
                    NationsGUI.countOGMREcipe(this.ogmName, true, 100);
                    NationsGUI.addPlayerSkill(player.field_71092_bJ, "farmer", 15);
                } else {
                    this.percentSucces = (float)amountValidItems / ((float)totalItemsRequired * 1.0f);
                    if (amountValidItems > 0) {
                        this.percentSucces = Math.max(0.01f, this.percentSucces);
                    }
                    this.error = this.error + "###" + (int)(this.percentSucces * 100.0f);
                    NationsGUI.countOGMREcipe(this.ogmName, false, (int)(this.percentSucces * 100.0f));
                    PacketDispatcher.sendPacketToPlayer((Packet)PacketRegistry.INSTANCE.generatePacket(this), (Player)((Player)player));
                }
            }
        }
    }

    @Override
    public void handleClientPacket(EntityPlayer player) {
        if (!this.error.isEmpty()) {
            OGMMachineGUI.errorToDisplay = this.error;
        }
    }

    public boolean hasItemStack(InventoryPlayer inventory, ItemStack stack) {
        int count = 0;
        for (ItemStack stack1 : Arrays.asList(inventory.field_70462_a)) {
            if (stack1 == null || stack1.func_77973_b() != stack.func_77973_b()) continue;
            count += stack1.field_77994_a;
        }
        return stack.field_77994_a <= count;
    }

    public static boolean removeItemWithAmount(InventoryPlayer inventory, ItemStack stack) {
        for (int i = 0; i < inventory.func_70302_i_(); ++i) {
            ItemStack stack1 = inventory.func_70301_a(i);
            if (stack1 == null || stack1.func_77973_b() != stack.func_77973_b()) continue;
            if (stack.field_77994_a - stack1.field_77994_a < 0) {
                stack1.field_77994_a -= stack.field_77994_a;
                return true;
            }
            stack.field_77994_a -= stack1.field_77994_a;
            inventory.field_70462_a[i] = null;
            if (stack.field_77994_a != 0) continue;
            return true;
        }
        return false;
    }
}

