package keystrokesmod.client.tweaker.transformers;

import keystrokesmod.client.tweaker.ASMTransformerClass;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

import java.util.ListIterator;

public class TransformerGuiUtilRenderComponents implements Transformer {
   public String[] getClassName() {
      return new String[]{"net.minecraft.client.gui.GuiUtilRenderComponents"};
   }

   public void transform(ClassNode classNode, String transformedName) {
      for (MethodNode methodNode : classNode.methods) {
         String mappedMethodName = this.mapMethodName(classNode, methodNode);
         if (mappedMethodName.equalsIgnoreCase("splitText") || mappedMethodName.equalsIgnoreCase("func_178908_a")) {
            ListIterator<AbstractInsnNode> i = methodNode.instructions.iterator();

            while (true) {
               MethodInsnNode mn;
               do {
                  do {
                     AbstractInsnNode ne;
                     do {
                        if (!i.hasNext()) {
                           return;
                        }

                        ne = i.next();
                     } while (!(ne instanceof MethodInsnNode));

                     mn = (MethodInsnNode) ne;
                  } while (!mn.owner.equals("net/minecraft/util/IChatComponent") && !mn.owner.equals("eu"));
               } while (!mn.name.equals("getUnformattedTextForChat") && !mn.name.equals("e"));

               if (mn.desc.equals("()Ljava/lang/String;")) {
                  i.add(this.getEventInsn());
               }
            }
         }
      }

   }

   private AbstractInsnNode getEventInsn() {
      return new MethodInsnNode(184, ASMTransformerClass.eventHandlerClassName, "getUnformattedTextForChat", "(Ljava/lang/String;)Ljava/lang/String;", false);
   }
}
