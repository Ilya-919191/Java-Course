import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		System.out.println("Введіть значення a: ");
		double a = scanner.nextDouble();

		double b = 3.0;
		double x = 5.0;

		if (a <= 0) System.out.println("Значення 'a' має бути додатнім");
		else if (x - a == 0) System.out.println("Значення 'a' не може дорівнювати 'x' (" + x + ")");
		else {
			double R = (a / (x - a)) + ((Math.pow(b, x) + Math.pow(Math.cos(x), 3)) / (Math.pow(Math.log(a), 3) + 4.5));

			if (Double.isNaN(R))
				System.out.println("Не вдалось обчислити результат");
			else
				System.out.printf("Результат 'R' = %.4f. При значеннях 'a' = %.4f, 'b' = %.2f, 'x' = %.2f\n", R, a, b, x);
		}

		scanner.close();
	}
}
