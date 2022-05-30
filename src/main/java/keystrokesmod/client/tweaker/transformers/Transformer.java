package keystrokesmod.client.tweaker.transformers;

import net.minecraftforge.fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

public interface Transformer extends Opcodes {
   String[] getClassName();

   void transform(ClassNode classNode, String transformedName);

   default String mapMethodName(ClassNode classNode, MethodNode methodNode) {
      return FMLDeobfuscatingRemapper.INSTANCE.mapMethodName(classNode.name, methodNode.name, methodNode.desc);
   }
}
