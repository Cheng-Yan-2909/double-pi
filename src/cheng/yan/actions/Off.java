package cheng.yan.actions;

public class Off extends Action {

	@Override
	public void run() {
		if( getBackward().isPresent() ) {
			getBackward().get().low();
		}
		
		if( getForward().isPresent() ) {
			getForward().get().low();
		}
		
		if( getLeft().isPresent() ) {
			getLeft().get().low();
		}
		
		if( getRight().isPresent() ) {
			getRight().get().low();
		}
		
		unprovisionAll();
	}
}
