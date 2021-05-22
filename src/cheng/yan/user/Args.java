package cheng.yan.user;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import cheng.yan.actions.Action;
import cheng.yan.actions.CarAction;
import cheng.yan.actions.Direction;
import cheng.yan.actions.Action.CarStateControlerExecutor;
import cheng.yan.actions.Direction.CarDirectionControllerExecutor;
import cheng.yan.logger.Logger;

public class Args {
	
	private final Map<String, String> userArgMap = new HashMap<String, String>();
	
	private static final String ACTION_KEY = "action"; // run, stop
	private static final String EXE_KEY = "exe"; 

	public void parse(String[] args) {
		for(String s : args) {
			Logger.logln("Parsing user arg: " + s);
			String[] nameValue = s.split("=");
			
			if( nameValue.length > 1 ) {
				userArgMap.put(nameValue[0], nameValue[1]);
			}
		}
	}
	
	public Optional<CarAction> getAction() {
		if( userArgMap.containsKey(ACTION_KEY)) {
			Logger.logln(ACTION_KEY + ": " + userArgMap.get(ACTION_KEY));
			
			for(Direction.CarDirectionController carDirectionController : Direction.CarDirectionController.values()) {
				Logger.logln("carDirectionController: " + carDirectionController.toString());
				if( userArgMap.get(ACTION_KEY).equals(carDirectionController.toString())) {
					Logger.logln("Direction.CarDirectionController: " + carDirectionController.toString());
					return Optional.ofNullable(Direction.CarDirectionController.valueOf(userArgMap.get(ACTION_KEY)).getCarDirection());
				}
			}
			
			for( Action.CarStateController carStateController : Action.CarStateController.values() ){
				Logger.logln("carStateController: " + carStateController.toString());
				if( userArgMap.get(ACTION_KEY).equals(carStateController.toString())) {
					Logger.logln("Action.CarStateController: " + carStateController.toString());
					return Optional.ofNullable(Action.CarStateController.valueOf(userArgMap.get(ACTION_KEY)).getCarState());
				}
			}
		}
		
		Logger.logln(ACTION_KEY + ": Undefined");
		return Optional.empty();
	}
	
	public Optional<Function<CarAction, Void>> getActionExecutor() {
		if( !userArgMap.containsKey(EXE_KEY)) {
			userArgMap.put(EXE_KEY, CarStateControlerExecutor.getDefault());
		}
		
		Logger.logln(EXE_KEY + ": " + userArgMap.get(EXE_KEY));
		
		for(CarDirectionControllerExecutor CarDirectionControllerExecutor : Direction.CarDirectionControllerExecutor.values()) {
			Logger.logln("CarDirectionControllerExecutor: " + CarDirectionControllerExecutor.toString());
			
			if( userArgMap.get(EXE_KEY).equals(CarDirectionControllerExecutor.toString())) {
				Logger.logln("Direction.CarDirectionControllerExecutor: " + CarDirectionControllerExecutor.toString());
				return Optional.ofNullable(Direction.CarDirectionControllerExecutor.valueOf(userArgMap.get(EXE_KEY)).getDirectionExecutor());
			}
		}
		
		for(CarStateControlerExecutor carStateControlerExecutor : Action.CarStateControlerExecutor.values()) {
			Logger.logln("carStateControlerExecutor: " + carStateControlerExecutor.toString());
			
			if( userArgMap.get(EXE_KEY).equals(carStateControlerExecutor.toString())) {
				Logger.logln("Action.CarStateControlerExecutor: " + carStateControlerExecutor.toString());
				return Optional.ofNullable(Action.CarStateControlerExecutor.valueOf(userArgMap.get(EXE_KEY)).getActionExecutor());
			}
		}
		
		Logger.logln(CarStateControlerExecutor.getDefault() + ": Undefined");
		return Optional.empty();
	}
}
