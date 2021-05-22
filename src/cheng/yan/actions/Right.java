package cheng.yan.actions;

public class Right extends Direction {

	@Override
	public void run() {
		if( getRight().isPresent() && getLeft().isPresent()) {
			getLeft().get().low();
			getRight().get().high();
		}
	}

	@Override
	public void stop() {
		if( getRight().isPresent() && getLeft().isPresent()) {
			getLeft().get().low();
			getRight().get().low();
		}
	}
}
