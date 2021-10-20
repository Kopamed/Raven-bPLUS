package me.kopamed.lunarkeystrokes.tweaker.transformers;

import org.objectweb.asm.Label;
import org.objectweb.asm.tree.*;

public class TransformerSplashProgress implements Transformer {
    public String[] getClassName() {
        return new String[]{"net.minecraftforge.fml.client.SplashProgress"};
    }

    public void transform(ClassNode classNode, String transformedName) {
        for (MethodNode methodNode : classNode.methods) {
            String n = this.mapMethodName(classNode, methodNode);
            if (n.equalsIgnoreCase("getString") && methodNode.desc.equals("(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;")) {
                for (AbstractInsnNode insnNode : methodNode.instructions.toArray()) {
                    if (insnNode.getOpcode() == ARETURN)
                        methodNode.instructions.insertBefore(insnNode, this.getEventInsn());
                }
                return;
            }
        }
    }

    private InsnList getEventInsn() {
        InsnList insnList = new InsnList();
        Label l1 = new Label();
        insnList.add(new LabelNode(l1));
        insnList.add(new VarInsnNode(ALOAD, 2));
        insnList.add(new LdcInsnNode("fml:textures/gui/forge.gif"));
        insnList.add(new LdcInsnNode("keystrokes:raven_loading.png"));
        insnList.add(new MethodInsnNode(INVOKEVIRTUAL, "java/lang/String", "replace", "(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;", false));
        insnList.add(new InsnNode(ARETURN));
        return insnList;
    }


    /*
    Original method :

    private static String getString(String name, String def) {
        String value = config.getProperty(name, def);
        config.setProperty(name, value);
        return value;
    }

    -----------------------------------

    patched method :

    private static String getString(String name, String def) {
        String value = config.getProperty(name, def);
        config.setProperty(name, value);
        return value.replace("fml:textures/gui/forge.gif", "keystrokes:raven_loading.png");
    }
    */
}
