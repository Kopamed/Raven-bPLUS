package keystrokesmod.sToNkS.tweaker.transformers;

import keystrokesmod.sToNkS.main.Ravenbplus;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

public class TransformerFMLConfigGuiFactory implements Transformer{
    @Override
    public String[] getClassName() {
        return new String[]{"net.minecraftforge.fml.client.FMLConfigGuiFactory"};
    }

    @Override
    public void transform(ClassNode classNode, String transformedName) {
        InsnList addBytecode = new InsnList();
        addBytecode.add(new MethodInsnNode(184, Ravenbplus.class.getName().replace('.', '/'), "init", "()V", false));

        for (MethodNode methodNode : classNode.methods) {
            if (methodNode != null && methodNode.name.equalsIgnoreCase("initialize")) {
                methodNode.instructions.insert(addBytecode);
            }
        }
    }
}
