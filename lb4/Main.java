import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		System.out.print("Введіть значення a: ");

		try {
			double a = scanner.nextDouble();
			double b = 3.0;

			if (a <= 0) {
				throw new IllegalArgumentException("Значення 'a' має бути додатнім.");
			}

			int N = 13;
			double xStart = -10 - 2.5 * N;
			double xEnd = 5 + 1.2 * N;
			double dx = 0.5 + (double) N / 20;

			int i = 1;
			System.out.println("\n\tЦикл FOR\n");
			for (double x = xStart; x <= xEnd + 1e-9; x += dx)
			{
				if (Math.abs(x - a) < 1e-9) {
					System.out.printf("%2d: x = %.2f;\tДілення на нуль (x = a).\n", i, x);
					continue;
				}

				double R = (a / (x - a)) + ((Math.pow(b, x) + Math.pow(Math.cos(x), 3)) / (Math.pow(Math.log(a), 3) + 4.5));

				if (Double.isNaN(R) || Double.isInfinite(R)) {
					System.out.printf("%2d: x = %.2f;\tНе вдалось обчислити результат.\n", i, x);
					continue;
				}

				System.out.printf("%2d: x = %.2f;\tR = %.4f\n", i, x, R);
				i++;
			}

			i = 1;
			System.out.println("\n\tЦикл WHILE\n");
			double x = xStart;
			while (x <= xEnd + 1e-9)
			{
				if (Math.abs(x - a) < 1e-9) {
					System.out.printf("%2d: x = %.2f;\tДілення на нуль (x = a).\n", i, x);
					x += dx;
					continue;
				}

				double R = (a / (x - a)) + ((Math.pow(b, x) + Math.pow(Math.cos(x), 3)) / (Math.pow(Math.log(a), 3) + 4.5));

				if (Double.isNaN(R) || Double.isInfinite(R)) {
					System.out.printf("%2d: x = %.2f;\tНе вдалось обчислити результат\n", i, x);
				} else {
					System.out.printf("%2d: x = %.2f;\tR = %.4f\n", i, x, R);
				}

				x += dx;
				i++;
			}

		} catch (InputMismatchException e) {
			System.out.println("Ви ввели некоректні дані! Потрібно ввести число.");

		} catch (IllegalArgumentException e) {
			System.out.println("Помилка обчислення: " + e.getMessage());

		} finally {
			scanner.close();
		}
	}
}