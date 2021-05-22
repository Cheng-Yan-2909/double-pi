package cheng.yan.actions;

import java.util.Optional;
import java.util.function.Function;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;

public abstract class Action implements CarAction {

    private final GpioController gpio = GpioFactory.getInstance();
	
	private GpioPinDigitalOutput left;
	private GpioPinDigitalOutput right;
	
	private GpioPinDigitalOutput forward;
	private GpioPinDigitalOutput backward;
	
	public enum CarStateController {
		start( new Start() ),
		off(new Off());
		
		private CarAction carState;
		
		CarStateController(CarAction carState) {
			this.carState = carState;
		}
		
		public CarAction getCarState() {
			return this.carState;
		}
	}
	
	public enum CarStateControlerExecutor {
		run(new Function<CarAction, Void>() {
			@Override
			public Void apply(CarAction t) {
				t.run();
				return null;
			}
		});
		
		private Function<CarAction, Void> actionExecutor;
		
		CarStateControlerExecutor(Function<CarAction, Void> actionExecutor) {
			this.actionExecutor = actionExecutor;
		}
		
		public Function<CarAction, Void> getActionExecutor() {
			return this.actionExecutor;
		}
		
		public static String getDefault() {
			return "run";
		}
	}
	
	public Action() {
		provisionAll();
	}
	
	@Override
	public void stop() {
		run();
	}
	
	
	protected Optional<GpioPinDigitalOutput> getLeft() {
		getProvisionedPinLeft();
		return Optional.ofNullable(left);
	}
	
	protected Optional<GpioPinDigitalOutput> getRight() {
		getProvisionedPinRight();
		return Optional.ofNullable(right);
	}
	
	protected Optional<GpioPinDigitalOutput> getForward() {
		getProvisionedPinForward();
		return Optional.ofNullable(forward);
	}
	
	protected Optional<GpioPinDigitalOutput> getBackward() {
		getProvisionedPinBackward();
		return Optional.ofNullable(backward);
	}
	
	private void getProvisionedPinLeft() {
		try {
			left = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_00, PinState.LOW);
		}
		catch(Exception e) {
		    left = (GpioPinDigitalOutput) gpio.getProvisionedPin(RaspiPin.GPIO_00);
		}
	}
	
	private void getProvisionedPinRight() {
		try {
		    right= gpio.provisionDigitalOutputPin(RaspiPin.GPIO_02, PinState.LOW);
		}
		catch(Exception e) {
			right = (GpioPinDigitalOutput) gpio.getProvisionedPin(RaspiPin.GPIO_02);
		}
	}
	
	private void getProvisionedPinForward() {
		try {
		    forward = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_03, PinState.LOW);
		}
		catch(Exception e) {
			forward = (GpioPinDigitalOutput) gpio.getProvisionedPin(RaspiPin.GPIO_03);
		}
	}
	
	private void getProvisionedPinBackward() {
		try {
		    backward = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_04, PinState.LOW);
		}
		catch(Exception e) {
			backward = (GpioPinDigitalOutput) gpio.getProvisionedPin(RaspiPin.GPIO_04);
		}
	}
	
	private void provisionAll() {
		getProvisionedPinBackward();
		getProvisionedPinForward();
		getProvisionedPinLeft();
	}
	
	protected void unprovisionAll() {
		gpio.unprovisionPin(left);
        gpio.unprovisionPin(right);
        gpio.unprovisionPin(forward);
        gpio.unprovisionPin(backward);
        
        gpio.shutdown();
	}
}
