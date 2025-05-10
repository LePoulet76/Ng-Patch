package net.ilexiconn.nationsgui.forge.server;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;

// Blocs machines
import net.ilexiconn.nationsgui.forge.server.block.IncubatorBlock;
import net.ilexiconn.nationsgui.forge.server.block.PortalBlock;
import net.ilexiconn.nationsgui.forge.server.block.RadioBlock;
import net.ilexiconn.nationsgui.forge.server.block.RepairMachineBlock;
import net.ilexiconn.nationsgui.forge.server.block.SpeakerBlock;
import net.ilexiconn.nationsgui.forge.server.block.URLBlock;
import net.ilexiconn.nationsgui.forge.server.block.ImageHologramBlock;
import net.ilexiconn.nationsgui.forge.server.block.HatBlock;

public class ModBlocks {
    public static Block INCUBATOR;
    public static Block PORTAL;
    public static Block RADIO;
    public static Block REPAIR_MACHINE;
    public static Block SPEAKER;
    public static Block URL;
    public static Block IMAGE_HOLOGRAM;
    public static Block HATBLOCK;

    public static void init() {
        INCUBATOR = new IncubatorBlock();
        PORTAL = new PortalBlock();
        RADIO = new RadioBlock();
        REPAIR_MACHINE = new RepairMachineBlock();
        SPEAKER = new SpeakerBlock();
        URL = new URLBlock();
        IMAGE_HOLOGRAM = new ImageHologramBlock();
        HATBLOCK = new HatBlock();

        GameRegistry.registerBlock(INCUBATOR, "incubator");
        GameRegistry.registerBlock(PORTAL, "portal");
        GameRegistry.registerBlock(RADIO, "radio");
        GameRegistry.registerBlock(REPAIR_MACHINE, "repair_machine");
        GameRegistry.registerBlock(SPEAKER, "speaker");
        GameRegistry.registerBlock(URL, "url_block");
        GameRegistry.registerBlock(IMAGE_HOLOGRAM, "image_hologram_block");
        GameRegistry.registerBlock(HATBLOCK, "hatblock");
    }
}
