package net.ilexiconn.nationsgui.forge.server.asm.transformer;

import java.util.Iterator;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

public class TileEntityRendererTransformer implements Transformer
{
    public String getTarget()
    {
        return "net.minecraft.client.renderer.tileentity.TileEntityRenderer";
    }

    public void transform(ClassNode node, boolean dev)
    {
        Iterator var3 = node.methods.iterator();

        while (var3.hasNext())
        {
            MethodNode methodNode = (MethodNode)var3.next();

            if (methodNode.name.equals(dev ? "renderTileEntityAt" : "renderTileEntityAt"))
            {
                LabelNode firstLabel = null;
                AbstractInsnNode[] patch = methodNode.instructions.toArray();
                int var7 = patch.length;
                int var8 = 0;

                while (true)
                {
                    if (var8 < var7)
                    {
                        AbstractInsnNode insnNode = patch[var8];

                        if (!(insnNode instanceof LabelNode))
                        {
                            ++var8;
                            continue;
                        }

                        firstLabel = (LabelNode)insnNode;
                    }

                    InsnList var10 = new InsnList();
                    var10.add(new VarInsnNode(25, 1));
                    var10.add(new VarInsnNode(24, 2));
                    var10.add(new VarInsnNode(24, 4));
                    var10.add(new VarInsnNode(24, 6));
                    var10.add(new VarInsnNode(23, 8));
                    var10.add(new MethodInsnNode(184, "net/ilexiconn/nationsgui/forge/client/ClientHooks", "skipTileRender", "(Lnet/minecraft/tileentity/TileEntity;DDDF)Z"));
                    var10.add(new JumpInsnNode(153, firstLabel));
                    var10.add(new InsnNode(177));
                    methodNode.instructions.insert(var10);
                    break;
                }
            }
        }
    }
}
