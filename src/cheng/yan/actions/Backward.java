package cheng.yan.actions;

import cheng.yan.logger.Logger;

public class Backward extends Direction {

	@Override
	public void run() {
		if( getBackward().isPresent() && getForward().isPresent()) {
			Logger.logln("--- stop forward");
			getForward().get().low();
			Logger.logln("--- start backward");
			getBackward().get().high();
		}
	}
	
	@Override
	public void stop() {
		if( getBackward().isPresent() && getForward().isPresent()) {
			getForward().get().low();
			getBackward().get().low();
		}
	}
}
