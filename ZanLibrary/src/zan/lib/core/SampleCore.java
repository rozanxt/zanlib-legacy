package zan.lib.core;

import zan.lib.panel.SamplePanel;

public class SampleCore extends CoreEngine {
	
	public static void main(String[] args) {
		SampleCore core = new SampleCore();
		core.setTitle("Sample Title");
		core.setScreenSize(800, 600);
		core.setPanel(new SamplePanel(core));
		core.run();
	}
	
}
