package slogo.model;

public enum ArithmeticOperator {
	
	ADDITION("+"){
		@Override
		public double apply(double x1, double x2){
			return x1 + x2;
		}
	},
	SUBTRACTION("-"){
		@Override
		public double apply(double x1, double x2){
			return x1 - x2;
		}
	},
	MULTIPLICATION("*"){
		@Override
		public double apply(double x1, double x2){
			return x1*x2;
		}
	},
	DIVISION("/"){
		@Override
		public double apply(double x1, double x2){
			return x1/x2;
		}
	};
	
	private final String text;

	private ArithmeticOperator(String text){
		this.text = text;
	}
	
	public abstract double apply(double x1, double x2);
}
