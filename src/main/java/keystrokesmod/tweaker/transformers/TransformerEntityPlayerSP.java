package keystrokesmod.tweaker.transformers;

import keystrokesmod.tweaker.ASMTransformerClass;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

public class TransformerEntityPlayerSP implements Transformer {
   public String[] getClassName() {
      return new String[]{"net.minecraft.client.entity.EntityPlayerSP"};
   }

   public void transform(ClassNode classNode, String transformedName) {
      for (MethodNode methodNode : classNode.methods) {
         String mappedMethodName = this.mapMethodName(classNode, methodNode);
         if (mappedMethodName.equalsIgnoreCase("onLivingUpdate") || mappedMethodName.equalsIgnoreCase("func_70636_d")) {
            AbstractInsnNode[] arr = methodNode.instructions.toArray();

            for (int i = 0; i < arr.length; ++i) {
               AbstractInsnNode ins = arr[i];
               if (i == 243) {
                  methodNode.instructions.insert(ins, this.getEventInsn());
               } else if (i >= 244 && i <= 261) {
                  methodNode.instructions.remove(ins);
               } else if (i == 262) {
                  return;
               }
            }

            return;
         }
      }

   }

   private InsnList getEventInsn() {
      InsnList i = new InsnList();
      i.add(new MethodInsnNode(184, ASMTransformerClass.eventHandlerClassName, "onLivingUpdate", "()V", false));
      return i;
   }
}
