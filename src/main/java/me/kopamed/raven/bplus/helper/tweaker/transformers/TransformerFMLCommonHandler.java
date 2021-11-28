package me.kopamed.raven.bplus.helper.tweaker.transformers;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import me.kopamed.raven.bplus.client.Raven;
import me.kopamed.raven.bplus.client.feature.module.modules.client.ClientNameSpoof;
import net.minecraftforge.fml.common.Loader;
import org.objectweb.asm.tree.*;

import java.util.List;

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

        // add a method call to ASMEventHandler.getModName();
        insnList.add(new MethodInsnNode(INVOKESTATIC, "me/kopamed/raven/bplus/helper/tweaker/ASMEventHandler", "getModName", "()Ljava/lang/String;", false));

        // return the result
        insnList.add(new InsnNode(ARETURN));
        return insnList;
    }


}
