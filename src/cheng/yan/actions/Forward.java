package cheng.yan.actions;

import cheng.yan.logger.Logger;

public class Forward extends Direction {

	@Override
	public void run() {
		Logger.logln("Run...");
		if( getForward().isPresent() && getBackward().isPresent() ) {
			getBackward().get().low();
			getForward().get().high();
		}
		else {
			Logger.logln("Pin not present");
		}
	}

	@Override
	public void stop() {
		if( getForward().isPresent() && getBackward().isPresent() ) {
			getBackward().get().low();
			getForward().get().low();
		}
	}
}
