package me.kopamed.lunarkeystrokes.tweaker;

import java.util.Map;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.MCVersion;

//@SortingIndex(1000)
@MCVersion("1.8.9")
public class FMLLoadingPlugin implements net.minecraftforge.fml.relauncher.IFMLLoadingPlugin {
   public String[] getASMTransformerClass() {
      return new String[]{ASMTransformerClass.class.getName()};
   }

   public String getModContainerClass() {
      return null;
   }

   public String getSetupClass() {
      return null;
   }

   public void injectData(Map<String, Object> data) {
   }

   public String getAccessTransformerClass() {
      return null;
   }
}
