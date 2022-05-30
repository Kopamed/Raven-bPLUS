package keystrokesmod.client.tweaker.transformers;

import keystrokesmod.client.tweaker.ASMTransformerClass;
import org.objectweb.asm.tree.*;

import java.util.ArrayList;

public class TransformerEntityPlayerSP implements Transformer {
   public String[] getClassName() {
      return new String[]{"net.minecraft.client.entity.EntityPlayerSP"};
   }

   public void transform(ClassNode classNode, String transformedName) {
      for (MethodNode methodNode : classNode.methods) {
         String mappedMethodName = this.mapMethodName(classNode, methodNode);
         if (mappedMethodName.equalsIgnoreCase("onLivingUpdate") || mappedMethodName.equalsIgnoreCase("func_70636_d")) {
            int i = 0;
            ArrayList<AbstractInsnNode> to_remove = new ArrayList<>();
            AbstractInsnNode[] arr = methodNode.instructions.toArray();
            boolean isASMEvntHandlerAdded = false;

            while (i < methodNode.instructions.toArray().length) {
               if (arr[i].getOpcode() == ALOAD) {
                  to_remove.add(arr[i]);
                  i++;
                  if (arr[i].getOpcode() == GETFIELD
                    && arr[i] instanceof FieldInsnNode
                  ) {
                     if (((FieldInsnNode) arr[i]).desc.equals("Lnet/minecraft/util/MovementInput;") || ((FieldInsnNode) arr[i]).desc.equals("Lbeu;" /* obf name */)) {
                        to_remove.add(arr[i]);
                        i++;
                        if (arr[i].getOpcode() == DUP) {
                           to_remove.add(arr[i]);
                           i++;
                           if (arr[i].getOpcode() == GETFIELD && ((FieldInsnNode) arr[i]).desc.equals("F")) {
                              to_remove.add(arr[i]);
                              i++;
                              if (arr[i].getOpcode() == LDC) {
                                 to_remove.add(arr[i]);
                                 i++;
                                 if (arr[i].getOpcode() == FMUL) {
                                    to_remove.add(arr[i]);
                                    i++;
                                    if (arr[i].getOpcode() == PUTFIELD && ((FieldInsnNode) arr[i]).desc.equals("F")) {
                                       to_remove.add(arr[i]);
                                       if (!isASMEvntHandlerAdded) {
                                          isASMEvntHandlerAdded = true;
                                          methodNode.instructions.insert(arr[i], getEventInsn());
                                       }
                                       for (AbstractInsnNode abstractInsnNode : to_remove) {
                                          methodNode.instructions.remove(abstractInsnNode);
                                       }
                                    }
                                 }
                              }
                           }
                        }
                     }
                  }
               }
               to_remove.clear();
               i++;
            }
         }
      }
   }

   private InsnList getEventInsn() {
      InsnList i = new InsnList();
      i.add(new MethodInsnNode(184, ASMTransformerClass.eventHandlerClassName, "onLivingUpdate", "()V", false));
      return i;
   }
}
