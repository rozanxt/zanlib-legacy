package zan.lib.sample;

import zan.lib.core.CoreEngine;

public class SampleCore extends CoreEngine {

	public static void main(String[] args) {
		SampleCore core = new SampleCore();
		core.setTitle("Sample Program");
		core.setIcon("res/ico/sample_icon.png");
		core.setPanel(new SamplePanel(core));
		core.run();
	}

}
