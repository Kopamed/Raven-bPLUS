package me.kopamed.raven.bplus.helper.discordRPC;

import me.kopamed.raven.bplus.helper.tweaker.transformers.Transformer;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

public class DiscordRPC_CINIT implements Transformer {
    public String[] getClassName() {
        return new String[]{
                "net.arikia.dev.drpc.DiscordRPC",
                "net.arikia.dev.drpc.DiscordRPC.DLL" // DLL is a subclass so in bytecode it's :  DiscordRPC$DLL
        };
    }

    public InsnList getPlatformCheckInsn() {
        // create a list of InsnNode
        InsnList insnList = new InsnList();

        LabelNode label0 = new LabelNode();
        insnList.add(label0);

        /* call me.kopamed.raven.bplus.helper.discordRPC.DiscordCompatibility:isCompatible();
            the result will be in the memory */
        insnList.add(new MethodInsnNode(
                INVOKESTATIC, // the method is : public and static
                "me/kopamed/raven/bplus/helper/discordRPC/DiscordCompatibility", // the clazz of the method
                "isCompatible", // the name of the method
                "()Z", // the description (LPARAM1;LPARAM2;)LRETURN_VALUE;   note ()Z means that the method don't take params and returns a boolean
                false // if the method is an implementation of an interface
        ));
        LabelNode label1 = new LabelNode();
        // IN THE LABEL

        // jump to lalbel1 if not equals
        insnList.add(new JumpInsnNode(
                IFNE, // if not equals
                label1 // label to jump to
        ));

        /* insert "void return" if the label is reached which means :
           if (!isCompatible()) {
                // label1
                return;
           }
       */
        insnList.add(new InsnNode(RETURN));

        // add the label
        insnList.add(label1);

        // don't ask me why, IDK
        insnList.add(new FrameNode(Opcodes.F_SAME, 0, null, 0, null));

        return insnList;
    }
 
    @Override
    public void transform(ClassNode classNode, String transformedName) {
        if (classNode.name.equals("net/arikia/dev/drpc/DiscordRPC")) {
            classNode.methods.forEach(methodNode -> {
                if (methodNode.name.equals("<clinit>") && methodNode.desc.equals("()V")) {
                    // inject in :  static {}
                    methodNode.instructions.insert(getPlatformCheckInsn());

                    /*
                     should look like :

                     static {
                        if (!isCompatible()) return;

                        // the normal code
                        ...
                     }
                     */
                }
            });
        } else if (classNode.name.equals("net/arikia/dev/drpc/DiscordRPC$DLL")){
            classNode.methods.forEach(methodNode -> {
                if (methodNode.name.equals("<clinit>") && methodNode.desc.equals("()V")) {
                    // inject in :  static {}
                    methodNode.instructions.insert(getPlatformCheckInsn());

                    /*
                     should look like :

                     static {
                        if (!isCompatible()) return;

                        // the normal code
                        ...
                     }
                     */
                }
            });
        }
    }
}
