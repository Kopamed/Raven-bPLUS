package keystrokesmod.client.tweaker;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import keystrokesmod.client.tweaker.transformers.*;
import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;

import java.util.Collection;

public class ASMTransformerClass implements IClassTransformer {
   public static String eventHandlerClassName = ASMEventHandler.class.getName().replace(".", "/"); //added replace or it won't launch
   private final Multimap<String, Transformer> m = ArrayListMultimap.create();
   public static final boolean outputBytecode = Boolean.parseBoolean(System.getProperty("debugBytecode", "false"));

   public ASMTransformerClass() {
      this.addTransformer(new TransformerFontRenderer());
      this.addTransformer(new TransformerGuiUtilRenderComponents());
      this.addTransformer(new TransformerEntityPlayerSP());

      this.addTransformer(new TransformerEntity());
      this.addTransformer(new TransformerEntityPlayer());
      this.addTransformer(new TransformerMinecraft());

      this.addTransformer(new TransformerSplashProgress());
      this.addTransformer(new TransformerFMLCommonHandler());

      this.addTransformer(new TransformerThrowableItem());
   }

   private void addTransformer(Transformer transformer) {
      String[] targetClasses = transformer.getClassName();

      for (String targetClass : targetClasses) {
         this.m.put(targetClass, transformer);
      }
   }

   @Override
   public byte[] transform(String name, String transformedName, byte[] basicClass) {
      if (basicClass == null) return null;

      Collection<Transformer> tr = this.m.get(transformedName);
      if (tr.isEmpty()) return basicClass;

      ClassReader classReader = new ClassReader(basicClass);
      ClassNode classNode = new ClassNode();
      classReader.accept(classNode, 8);

      tr.forEach((transformer) -> transformer.transform(classNode, transformedName));

      ClassWriter classWriter = new ClassWriter(3);
      try {
         classNode.accept(classWriter);
      } catch (Throwable ignored) {}

      return classWriter.toByteArray();
   }
}
