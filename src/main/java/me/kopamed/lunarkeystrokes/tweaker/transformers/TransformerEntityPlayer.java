package me.kopamed.lunarkeystrokes.tweaker.transformers;

import me.kopamed.lunarkeystrokes.tweaker.ASMTransformerClass;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

public class TransformerEntityPlayer implements Transformer {
   public String[] getClassName() {
      return new String[]{"net.minecraft.entity.player.EntityPlayer"};
   }

   public void transform(ClassNode classNode, String transformedName) {
      for (MethodNode methodNode : classNode.methods) {
         String mappedMethodName = this.mapMethodName(classNode, methodNode);
         if (mappedMethodName.equalsIgnoreCase("attackTargetEntityWithCurrentItem") || mappedMethodName.equalsIgnoreCase("func_71059_n")) {
            AbstractInsnNode[] arr = methodNode.instructions.toArray();

            for (int i = 0; i < arr.length; ++i) {
               AbstractInsnNode ins = arr[i];
               if (i == 232) {
                  methodNode.instructions.insert(ins, this.h());
               } else if (i >= 233 && i <= 248) {
                  methodNode.instructions.remove(ins);
               } else if (i == 249) {
                  return;
               }
            }

            return;
         }
      }

   }

   private InsnList h() {
      InsnList insnList = new InsnList();
      insnList.add(new VarInsnNode(25, 1));
      insnList.add(new MethodInsnNode(184, ASMTransformerClass.eventHandlerClassName, "onAttackTargetEntityWithCurrentItem", "(Lnet/minecraft/entity/Entity;)V", false));
      return insnList;
   }
}
