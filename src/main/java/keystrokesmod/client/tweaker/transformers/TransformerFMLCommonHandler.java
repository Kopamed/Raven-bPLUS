package keystrokesmod.client.tweaker.transformers;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import keystrokesmod.client.main.Raven;
import keystrokesmod.client.module.Module;
import keystrokesmod.client.module.ModuleManager;
import keystrokesmod.client.module.modules.client.ClientNameSpoof;
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

        // add a method call to keystrokesmod.client.tweaker.transformers.TransformerFMLCommonHandler.getModName();
        insnList.add(new MethodInsnNode(INVOKESTATIC, TransformerFMLCommonHandler.class.getName().replace(".", "/"), "getModName", "()Ljava/lang/String;", false));

        // return the result
        insnList.add(new InsnNode(ARETURN));
        return insnList;
    }

    public static String getModName() {
        Module cns = Raven.moduleManager.getModuleByClazz(ClientNameSpoof.class);
        if (cns != null && cns.isEnabled()){
            return ClientNameSpoof.newName;
        }
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
