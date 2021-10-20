package me.kopamed.lunarkeystrokes.tweaker.transformers;

import me.kopamed.lunarkeystrokes.tweaker.ASMTransformerClass;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

public class TransformerEntity implements Transformer {
   public String[] getClassName() {
      return new String[]{"net.minecraft.entity.Entity"};
   }

   public void transform(ClassNode classNode, String transformedName) {
      for (MethodNode methodNode : classNode.methods) {
         String n = this.mapMethodName(classNode, methodNode);
         if (n.equalsIgnoreCase("moveEntity") || n.equalsIgnoreCase("func_70091_d")) {
            AbstractInsnNode[] arr = methodNode.instructions.toArray();

            for (int i = 0; i < arr.length; ++i) {
               AbstractInsnNode ins = arr[i];
               if (i >= 99 && i <= 117) {
                  methodNode.instructions.remove(ins);
               } else if (i == 118) {
                  methodNode.instructions.insertBefore(ins, this.getEventInsn());
                  return;
               }
            }

            return;
         }
      }

   }

   private InsnList getEventInsn() {
      InsnList insnList = new InsnList();
      insnList.add(new VarInsnNode(25, 0));
      insnList.add(new MethodInsnNode(184, ASMTransformerClass.eventHandlerClassName, "onEntityMove", "(Lnet/minecraft/entity/Entity;)Z", false));
      insnList.add(new VarInsnNode(54, 19));
      return insnList;
   }
}
