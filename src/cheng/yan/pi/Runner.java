package cheng.yan.pi;

import java.util.Optional;
import java.util.function.Function;

import cheng.yan.actions.CarAction;
import cheng.yan.logger.Logger;
import cheng.yan.user.Args;

public class Runner {

	public static void main(String[] argv) {
		
		Args args = new Args();
		
		Logger.logln("main");
		
		args.parse(argv);
		
		Optional<CarAction> userAction = args.getAction();
		Optional<Function<CarAction, Void>> actionExecutor = args.getActionExecutor();
		
		if( userAction.isPresent() && actionExecutor.isPresent() ) {
			actionExecutor.get().apply(userAction.get());
		}
		else {
			Logger.logln("Error!");
			Logger.logln(argv.toString());
		}
		
		Logger.write();
	}
}
