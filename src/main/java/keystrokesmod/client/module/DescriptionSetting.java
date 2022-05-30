package keystrokesmod.client.module;

public class DescriptionSetting extends Setting {
   public static final String settingType = "desc";
   private String desc;
   private final String defaultDesc;

   public DescriptionSetting(String t) {
      super(t, settingType);
      this.desc = t;
      this.defaultDesc = t;
   }

   public String getDesc() {
      return this.desc;
   }

   public void setDesc(String t) {
      this.desc = t;
   }

   @Override
   public void resetToDefaults() {
      this.desc = defaultDesc;
   }
}
