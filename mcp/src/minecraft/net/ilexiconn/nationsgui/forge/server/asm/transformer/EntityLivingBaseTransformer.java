package net.ilexiconn.nationsgui.forge.server.asm.transformer;

import java.util.Iterator;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

public class EntityLivingBaseTransformer implements Transformer
{
    public String getTarget()
    {
        return "net.minecraft.entity.EntityLivingBase";
    }

    public void transform(ClassNode node, boolean dev)
    {
        Iterator var3 = node.methods.iterator();

        while (var3.hasNext())
        {
            MethodNode methodNode = (MethodNode)var3.next();
            InsnList insnList = methodNode.instructions;
            AbstractInsnNode[] var6;
            int var7;
            int var8;
            AbstractInsnNode insnNode;

            if (!methodNode.name.equals("onNewPotionEffect") && !methodNode.name.equals("onNewPotionEffect"))
            {
                if (methodNode.name.equals("onChangedPotionEffect") || methodNode.name.equals("onChangedPotionEffect"))
                {
                    var6 = insnList.toArray();
                    var7 = var6.length;

                    for (var8 = 0; var8 < var7; ++var8)
                    {
                        insnNode = var6[var8];

                        if (insnNode.getOpcode() == 177)
                        {
                            insnList.insertBefore(insnNode, new VarInsnNode(25, 0));
                            insnList.insertBefore(insnNode, new VarInsnNode(25, 1));
                            insnList.insertBefore(insnNode, new MethodInsnNode(184, "net/ilexiconn/nationsgui/forge/server/asm/NationsGUIHooks", "onChangedPotionEffect", "(Lnet/minecraft/entity/EntityLivingBase;Lnet/minecraft/potion/PotionEffect;)V"));
                        }
                    }
                }
                else if (methodNode.name.equals("onFinishedPotionEffect") || methodNode.name.equals("onFinishedPotionEffect"))
                {
                    var6 = insnList.toArray();
                    var7 = var6.length;

                    for (var8 = 0; var8 < var7; ++var8)
                    {
                        insnNode = var6[var8];

                        if (insnNode.getOpcode() == 177)
                        {
                            insnList.insertBefore(insnNode, new VarInsnNode(25, 0));
                            insnList.insertBefore(insnNode, new VarInsnNode(25, 1));
                            insnList.insertBefore(insnNode, new MethodInsnNode(184, "net/ilexiconn/nationsgui/forge/server/asm/NationsGUIHooks", "onFinishedPotionEffect", "(Lnet/minecraft/entity/EntityLivingBase;Lnet/minecraft/potion/PotionEffect;)V"));
                        }
                    }
                }
                else if (!methodNode.name.equals("setArrowCountInEntity") && !methodNode.name.equals("setArrowCountInEntity"))
                {
                    if (methodNode.name.equals("getArrowCountInEntity") || methodNode.name.equals("getArrowCountInEntity"))
                    {
                        insnList.clear();
                        insnList.add(new InsnNode(3));
                        insnList.add(new InsnNode(172));
                    }
                }
                else
                {
                    insnList.clear();
                    insnList.add(new InsnNode(177));
                }
            }
            else
            {
                var6 = insnList.toArray();
                var7 = var6.length;

                for (var8 = 0; var8 < var7; ++var8)
                {
                    insnNode = var6[var8];

                    if (insnNode.getOpcode() == 177)
                    {
                        insnList.insertBefore(insnNode, new VarInsnNode(25, 0));
                        insnList.insertBefore(insnNode, new VarInsnNode(25, 1));
                        insnList.insertBefore(insnNode, new MethodInsnNode(184, "net/ilexiconn/nationsgui/forge/server/asm/NationsGUIHooks", "onNewPotionEffect", "(Lnet/minecraft/entity/EntityLivingBase;Lnet/minecraft/potion/PotionEffect;)V"));
                    }
                }
            }
        }
    }
}
