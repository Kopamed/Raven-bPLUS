package keystrokesmod.tweaker;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import java.util.Collection;

import keystrokesmod.tweaker.transformers.*;
import keystrokesmod.tweaker.transformers.Transformer;
import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;

public class ASMTransformerClass implements IClassTransformer {
   public static String eventHandlerClassName = ASMEventHandler.class.getName();
   private final Multimap<String, Transformer> m = ArrayListMultimap.create();

   public ASMTransformerClass() {
      this.addTransformer(new TransformerFontRenderer());
      this.addTransformer(new TransformerGuiUtilRenderComponents());
      this.addTransformer(new TransformerEntityPlayerSP());
      this.addTransformer(new TransformerEntity());
      this.addTransformer(new TransformerEntityPlayer());
      this.addTransformer(new TransformerMinecraft());
   }

   private void addTransformer(Transformer transformer) {
      String[] var2 = transformer.getClassName();

      for (String c : var2) {
         this.m.put(c, transformer);
      }
   }

   public byte[] transform(String name, String transformedName, byte[] basicClass) {
      if (basicClass == null) {
         return null;
      } else {
         Collection<Transformer> tr = this.m.get(transformedName);
         if (tr.isEmpty()) {
            return basicClass;
         } else {
            ClassReader classReader = new ClassReader(basicClass);
            ClassNode classNode = new ClassNode();
            classReader.accept(classNode, 8);
            tr.forEach((transformer) -> {
               transformer.transform(classNode, transformedName);
            });
            ClassWriter classWriter = new ClassWriter(3);

            try {
               classNode.accept(classWriter);
            } catch (Throwable var9) {
            }

            return classWriter.toByteArray();
         }
      }
   }
}
