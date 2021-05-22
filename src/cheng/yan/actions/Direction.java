package cheng.yan.actions;

import java.util.function.Function;

public abstract class Direction extends Action {
	
	public Direction() {
		
	}
	
	protected Function<Direction, Void> stopFunction = new Function<Direction, Void>() {
		@Override
		public Void apply(Direction t) {
			t.stop();
			return null;
		}
	};
	
	public enum CarDirectionController {
		
		forward(new Forward()),
		backward(new Backward()),
		left(new Left()),
		right(new Right());
		
		
		private CarAction carDirection;
		
		CarDirectionController(CarAction carDirection) {
			this.carDirection = carDirection;
		}
		
		public CarAction getCarDirection() {
			return this.carDirection;
		}
	}
	
	public enum CarDirectionControllerExecutor {
		stop(new Function<CarAction, Void>() {
			@Override
			public Void apply(CarAction t) {
				t.stop();
				return null;
			}
		});
		
		private Function<CarAction, Void> directionExecutor;
		
		CarDirectionControllerExecutor(Function<CarAction, Void> directionExecutor) {
			this.directionExecutor = directionExecutor;
		}
		
		public Function<CarAction, Void> getDirectionExecutor() {
			return this.directionExecutor;
		}
	}
	
	@Override
	public abstract void stop();
	
	
}
