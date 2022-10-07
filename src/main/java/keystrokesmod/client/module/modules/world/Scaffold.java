package keystrokesmod.client.module.modules.world;

import com.google.common.eventbus.Subscribe;

import keystrokesmod.client.event.impl.LookEvent;
import keystrokesmod.client.module.Module;
import keystrokesmod.client.module.setting.impl.DescriptionSetting;

public class Scaffold extends Module {

	//this is a test for lookevent
	public Scaffold() {
		super("Scaffold", ModuleCategory.world);
		this.registerSetting(new DescriptionSetting("This is testing for look event dont use"));
	}

	@Subscribe
	public void lookEvent(LookEvent e) {
		e.setPitch(77);
		e.setYaw(e.getYaw() - 180);
	}

}
