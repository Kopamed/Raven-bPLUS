package keystrokesmod.client.tweaker.transformers;

import java.util.List;

import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

import net.minecraftforge.fml.common.Loader;

/**
 * @author JMRaich aka JMRaichDev
 */
public class TransformerFMLCommonHandler implements Transformer {
    public String[] getClassName() {
        return new String[]{"net.minecraftforge.fml.common.FMLCommonHandler"};
    }

    public void transform(ClassNode classNode, String transformedName) {
        for (MethodNode methodNode : classNode.methods) {
            String mappedMethodName = this.mapMethodName(classNode, methodNode);

            // locate the method by its name
            if (mappedMethodName.equalsIgnoreCase("getModName")) {
                // empty the method
                for (AbstractInsnNode insnNode : methodNode.instructions.toArray()) {
                    methodNode.instructions.remove(insnNode);
                }

                // add our new instructions
                methodNode.instructions.insert(getInsn());

                return;
            }
        }

    }

    private InsnList getInsn() {
        InsnList insnList = new InsnList();

        // add a method call to keystrokesmod.client.tweaker.transformers.TransformerFMLCommonHandler.getModName();
        insnList.add(new MethodInsnNode(INVOKESTATIC, TransformerFMLCommonHandler.class.getName().replace(".", "/"), "getModName", "()Ljava/lang/String;", false));

        // return the result
        insnList.add(new InsnNode(ARETURN));
        return insnList;
    }

    public static String getModName() {
        List<String> modNames = Lists.newArrayListWithExpectedSize(3);
        modNames.add("fml");
        modNames.add("forge");

        if (Loader.instance().getFMLBrandingProperties().containsKey("snooperbranding"))
        {
            modNames.add(Loader.instance().getFMLBrandingProperties().get("snooperbranding"));
        }
        return Joiner.on(',').join(modNames);
    }
}
