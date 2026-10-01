package lb5;

public class SomeClass {
	private double a;
	private double b;
	private int N;

	public SomeClass() {
		this(2, 3, 13);
	}

	public SomeClass(double a, double b, int N) {
		setA(a);
		this.b = b;
		this.N = N;
	}

	public double getA() {
		return a;
	}

	public void setA(double a) {
		checkA(a);
		this.a = a;
	}

	public double getB() {
		return b;
	}

	public void setB(double b) {
		this.b = b;
	}

	public int getN() {
		return N;
	}

	public void setN(int N) {
		this.N = N;
	}

	public static double checkA(double a) {
		if (a <= 0) {
			throw new IllegalArgumentException("Значення 'a' має бути додатнім.");
		}
		return a;
	}

	public double calculate(double x) {
		return calculateStatic(this.a, this.b, x);
	}

	public static double calculateStatic(double a, double b, double x) {
		if (Math.abs(x - a) < 1e-9) {
			return Double.NaN;
		}
		return (a / (x - a)) + ((Math.pow(b, x) + Math.pow(Math.cos(x), 3)) / (Math.pow(Math.log(a), 3) + 4.5));
	}

	public void printResults() {
		double xStart = -10 - 2.5 * N;
		double xEnd = 5 + 1.2 * N;
		double dx = 0.5 + (double) N / 20;

		System.out.printf("Параметри об'єкта: a = %.2f, b = %.2f, N = %d\n\n", a, b, N);

		int i = 1;
		for (double x = xStart; x <= xEnd + 1e-9; x += dx) {
			double R = calculate(x);

			if (Double.isNaN(R) || Double.isInfinite(R)) {
				System.out.printf("%2d: x = %6.2f;\tПомилка обчислення.\n", i, x);
			} else {
				System.out.printf("%2d: x = %6.2f;\tR = %.4f\n", i, x, R);
			}
			i++;
		}
		System.out.println();
	}
}