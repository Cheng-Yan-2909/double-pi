package cheng.yan.debug;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.Arrays;

import com.pi4j.io.gpio.BananaProPin;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinAnalogOutput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.platform.Platform;
import com.pi4j.platform.PlatformAlreadyAssignedException;
import com.pi4j.platform.PlatformManager;
import com.pi4j.system.SystemInfo.BoardType;


public class TestIO {
	
	private static GpioPinDigitalOutput left;
	private static GpioPinDigitalOutput right;
	
	private static GpioPinDigitalOutput forward;
	private static GpioPinDigitalOutput backward;
	
	
	private double on_speed = 10;
	private double off_speed = 10;
	private String dir = "f";
	
	public class RunIo extends Thread {
		
		private GpioPinDigitalOutput getOutput() {
			switch(dir) {
			case "b" :
				return backward;
			case "l" :
				return left;
			case "r":
				return right;
			}
			return forward;
		}
		
		private void setHigh() {
			getOutput().high();
			for( int i = 0; i < on_speed * 100000; i++ );
		}
		
		private void setLow() {
			getOutput().low();
			if( off_speed == 0 ) return;
			for( int i = 0; i < (1/off_speed) * 100000; i++ );
		}
		
		@Override
		public void run() {
			final GpioController gpio = GpioFactory.getInstance();
			
			left = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_00, PinState.LOW);
			right = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_02, PinState.LOW);
			
			forward = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_03, PinState.LOW);
			backward = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_04, PinState.LOW);
			
			while( on_speed != 0 ) {
				if( on_speed > 0) {
				    setHigh();
				}
				
				if( off_speed > 0 ) {
				    setLow();
				}
			}
			
			shutDown(gpio);
		}
		
	}

	// BCM2835
	//
	public static void main(String[] args) throws InterruptedException, PlatformAlreadyAssignedException, IOException {
		
		TestIO testIo = new TestIO();
		
		System.out.println("Press ENTER when ready");
		System.in.read();
        
		testIo.testEnableSwitch();
	}
	
	private void testEnableSwitch() throws PlatformAlreadyAssignedException, IOException {
		RunIo runIo = new RunIo();
		runIo.start();
		
		do {
			System.out.println("Enter speed (on_speed : dir : off_speed): ");
			StringBuffer sb = new StringBuffer();
			int val = 0;
			do {
				val = System.in.read();
				if( val != 10) {
				    sb.append(((char) val));
				}
			} while(val != 10);
			
			if( sb.toString() == "") {
				on_speed = 0;
			}
			else {
				/*
				 *  input:
				 *  on_speed : dir : off_speed
				 *  
				 *  dir is optional
				 *  
				 *  value for dir: forward, backward, left, right
				 */
				String[] valList = sb.toString().replaceAll("/s", "").split(":");
				on_speed = Double.parseDouble(valList[0]);
				if( valList.length > 1 ) {
					dir = valList[1];
				}
				if( valList.length > 2 ) {
					off_speed = Double.parseDouble(valList[2]);
				}
			}
			System.out.println("          dir: " + dir);
			System.out.println("     on_speed: " + on_speed);
			System.out.println("    off_speed: " + off_speed);
			
		} while( on_speed != 0 );
		
		on_speed = 0;
		off_speed = 0;
		
        
        System.out.println("DONE");
	}
	
	private void shutDown(GpioController gpio) {
		left.low();
		right.low();
		forward.low();
		backward.low();
		
		gpio.unprovisionPin(left);
        gpio.unprovisionPin(right);
        gpio.unprovisionPin(forward);
        gpio.unprovisionPin(backward);
        
        gpio.shutdown();
	}
	
	private void gpioPinTest() throws IOException, PlatformAlreadyAssignedException {
		useGpioFactory(getRaspin());
	}
	
	private Pin[] getRaspin() throws PlatformAlreadyAssignedException {
		PlatformManager.setPlatform(Platform.RASPBERRYPI);
		Pin[] pins = new Pin[]{RaspiPin.GPIO_00}; // RaspiPin.allPins();
		Arrays.sort(pins);
		return pins;
	}
	
	private Pin[] getPinsFromBananaProPin() throws PlatformAlreadyAssignedException {
		PlatformManager.setPlatform(Platform.BANANAPRO);
		Pin[] pins = BananaProPin.allPins();
		Arrays.sort(pins);
		
		return pins;
	}
	
	private void useGpioFactory(Pin pins[]) throws IOException {
		final GpioController gpio = GpioFactory.getInstance();
		for(Pin pin : pins){
            // provision pin
            System.out.println("... provisioning pin: " + pin.toString());
            GpioPinDigitalOutput outputPin = gpio.provisionDigitalOutputPin(pin, PinState.LOW);

            System.out.println(">>> pin: " + pin.toString() + " should be in the LOW state.");

            // wait for user input
            System.out.println("... press ENTER to continue ...");
            System.in.read();

            // set pin to HIGH state
            outputPin.high();
            System.out.println(">>> pin: " + pin.toString() + " should be in the HIGH state.");

            // wait for user input
            System.out.println("... press ENTER to continue ...");
            System.in.read();

            // set pin to LOW state
            outputPin.toggle();
            System.out.println(">>> pin: " + pin.toString() + " should have toggled state.");

            // wait for user input
            System.out.println("... press ENTER to continue ...");
            System.in.read();

            // set pin to blink
            outputPin.blink(500);
            System.out.println(">>> pin: " + pin.toString() + " should be blinking 1 time per 1/2 second.");

            // wait for user input
            System.out.println("... press ENTER to continue ...");
            System.in.read();

            // stop blinking
            outputPin.blink(0);
            System.out.println(">>> pin: " + pin.toString() + " should have stopped blinking.");
            
            // wait for user input
            System.out.println("... press ENTER to continue ...");
            System.in.read();
            outputPin.low();
            System.out.println(">>> pin: " + pin.toString() + " should be in LOW state.");

            // un-provision pin
            System.out.println("... un-provisioning pin: " + pin.toString());
            gpio.unprovisionPin(outputPin);
        }
		gpio.shutdown();
	}
}
