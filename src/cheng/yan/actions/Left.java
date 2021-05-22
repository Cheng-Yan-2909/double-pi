package cheng.yan.actions;

public class Left extends Direction {

	@Override
	public void run() {
		if( getLeft().isPresent() && getRight().isPresent()) {
			getRight().get().low();
			getLeft().get().high();
		}
	}

	@Override
	public void stop() {
		if( getLeft().isPresent() && getRight().isPresent()) {
			getRight().get().low();
			getLeft().get().low();
		}
	}
}
