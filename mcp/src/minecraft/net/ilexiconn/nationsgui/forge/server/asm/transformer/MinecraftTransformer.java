/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.objectweb.asm.tree.AbstractInsnNode
 *  org.objectweb.asm.tree.ClassNode
 *  org.objectweb.asm.tree.FieldInsnNode
 *  org.objectweb.asm.tree.InsnList
 *  org.objectweb.asm.tree.InsnNode
 *  org.objectweb.asm.tree.IntInsnNode
 *  org.objectweb.asm.tree.LdcInsnNode
 *  org.objectweb.asm.tree.MethodInsnNode
 *  org.objectweb.asm.tree.MethodNode
 *  org.objectweb.asm.tree.TypeInsnNode
 *  org.objectweb.asm.tree.VarInsnNode
 */
package net.ilexiconn.nationsgui.forge.server.asm.transformer;

import java.util.ListIterator;
import net.ilexiconn.nationsgui.forge.server.asm.transformer.Transformer;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.IntInsnNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TypeInsnNode;
import org.objectweb.asm.tree.VarInsnNode;

public class MinecraftTransformer
implements Transformer {
    public static MinecraftTransformer INSTANCE;

    public MinecraftTransformer() {
        INSTANCE = this;
    }

    @Override
    public String getTarget() {
        return "net.minecraft.client.Minecraft";
    }

    @Override
    public void transform(ClassNode node, boolean dev) {
        for (MethodNode methodNode : node.methods) {
            AbstractInsnNode current;
            InsnList insnList;
            if (methodNode.name.equals("startGame") || methodNode.name.equals("func_71384_a")) {
                for (AbstractInsnNode insnNode : methodNode.instructions.toArray()) {
                    if (!(insnNode instanceof LdcInsnNode) || !((LdcInsnNode)insnNode).cst.equals("Minecraft 1.6.4")) continue;
                    ((LdcInsnNode)insnNode).cst = "NationsGlory";
                }
                InsnList insns = new InsnList();
                ListIterator iter = methodNode.instructions.iterator();
                boolean isPassed = false;
                while (iter.hasNext()) {
                    AbstractInsnNode insn = (AbstractInsnNode)iter.next();
                    if (insn instanceof MethodInsnNode && ((MethodInsnNode)insn).owner.equalsIgnoreCase("net/minecraft/client/Minecraft") && ((MethodInsnNode)insn).name.equalsIgnoreCase(dev ? "loadScreen" : "func_71357_I") && ((MethodInsnNode)insn).desc.equalsIgnoreCase("()V")) {
                        isPassed = true;
                    }
                    insns.add(insn);
                    if (!isPassed) continue;
                    insns.add((AbstractInsnNode)new VarInsnNode(25, 0));
                    insns.add((AbstractInsnNode)new MethodInsnNode(183, "net/minecraft/client/Minecraft", dev ? "loadScreen" : "func_71357_I", "()V"));
                }
                methodNode.instructions = insns;
                MethodInsnNode target = null;
                for (AbstractInsnNode abstractInsnNode : methodNode.instructions.toArray()) {
                    if (!(abstractInsnNode instanceof MethodInsnNode)) continue;
                    MethodInsnNode methodInsnNode = (MethodInsnNode)abstractInsnNode;
                    if (!methodInsnNode.name.equals("setResizable")) continue;
                    target = methodInsnNode;
                    break;
                }
                methodNode.instructions.remove(target.getPrevious());
                methodNode.instructions.insertBefore(target, (AbstractInsnNode)new MethodInsnNode(184, "net/ilexiconn/nationsgui/forge/client/ClientHooks", "defineDisplayResizable", "()V"));
                methodNode.instructions.remove((AbstractInsnNode)target);
                continue;
            }
            if (methodNode.name.equals("loadScreen") || methodNode.name.equals("func_71357_I")) {
                insnList = methodNode.instructions;
                insnList.clear();
                insnList.add((AbstractInsnNode)new FieldInsnNode(178, "net/ilexiconn/nationsgui/forge/server/asm/NationsGUIClientHooks", "INSTANCE", "Lnet/ilexiconn/nationsgui/forge/server/asm/NationsGUIClientHooks;"));
                insnList.add((AbstractInsnNode)new MethodInsnNode(182, "net/ilexiconn/nationsgui/forge/server/asm/NationsGUIClientHooks", "drawLoadingScreen", "()V"));
                insnList.add((AbstractInsnNode)new InsnNode(177));
                continue;
            }
            if (methodNode.name.equals("runTick") || methodNode.name.equals("func_71407_l")) {
                AbstractInsnNode previous;
                insnList = methodNode.instructions;
                current = null;
                for (AbstractInsnNode insnNode : insnList.toArray()) {
                    if (insnNode instanceof FieldInsnNode) {
                        FieldInsnNode fieldInsnNode = (FieldInsnNode)insnNode;
                        if (fieldInsnNode.getOpcode() != 181 || !fieldInsnNode.owner.equals("net/minecraft/entity/player/InventoryPlayer") || !fieldInsnNode.name.equals(dev ? "currentItem" : "field_70461_c")) continue;
                        current = insnNode;
                        continue;
                    }
                    if (!(insnNode instanceof TypeInsnNode) || insnNode.getOpcode() != 193) continue;
                    TypeInsnNode typeInsnNode = (TypeInsnNode)insnNode;
                    if (!typeInsnNode.desc.equals("net/minecraft/client/gui/GuiSleepMP")) continue;
                    typeInsnNode.desc = "net/ilexiconn/nationsgui/forge/client/gui/SleepGUI";
                }
                do {
                    previous = current.getPrevious();
                    insnList.remove(current);
                } while ((current = previous).getOpcode() != 25);
                insnList.remove(current);
                this.patchKey(insnList, 59, "KEY_HIDE_GUI", dev);
                this.patchKey(insnList, 61, "KEY_DEBUG", dev);
                this.patchKey(insnList, 63, "KEY_THIRD_PERSON", dev);
                this.patchKey(insnList, 66, "KEY_SMOOTH_CAMERA", dev);
                this.patchKey(insnList, 87, "KEY_FULLSCREEN", dev);
                continue;
            }
            if (methodNode.name.equals("refreshResources") || methodNode.name.equals("func_110436_a")) {
                insnList = methodNode.instructions;
                current = insnList.getFirst();
                while ((current = current.getNext()).getOpcode() != 177) {
                }
                insnList.insertBefore(current, (AbstractInsnNode)new MethodInsnNode(184, "net/ilexiconn/nationsgui/forge/server/asm/NationsGUIClientHooks", "onResourceReload", "()V"));
                continue;
            }
            if (!methodNode.name.equals("screenshotListener") && !methodNode.name.equals("func_71365_K")) continue;
            insnList = methodNode.instructions;
            this.patchKey(insnList, 60, "KEY_SCREENSHOT", dev);
        }
    }

    public void patchKey(InsnList insnList, int targetCode, String keyName, boolean dev) {
        AbstractInsnNode current = insnList.getFirst();
        while ((current = current.getNext()) != null) {
            if (current.getOpcode() != 16) continue;
            IntInsnNode varInsnNode = (IntInsnNode)current;
            if (varInsnNode.operand != targetCode) continue;
            InsnList patch = new InsnList();
            patch.add((AbstractInsnNode)new FieldInsnNode(178, "net/ilexiconn/nationsgui/forge/client/ClientKeyHandler", keyName, "Lnet/minecraft/client/settings/KeyBinding;"));
            patch.add((AbstractInsnNode)new FieldInsnNode(180, "net/minecraft/client/settings/KeyBinding", dev ? "keyCode" : "field_74512_d", "I"));
            insnList.insertBefore(current, patch);
            AbstractInsnNode node = current.getPrevious();
            insnList.remove(current);
            current = node;
        }
    }
}

