import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		System.out.print("Введіть значення a: ");

		try {
			double a = scanner.nextDouble();
			double b = 3.0;
			double x = 5.0;

			if (a <= 0) {
				throw new IllegalArgumentException("Значення 'a' має бути додатнім.");
			} 
			if (x - a == 0) {
				throw new IllegalArgumentException("Значення 'a' не може дорівнювати 'x' (" + x + ").");
			}

			double R = (a / (x - a)) + ((Math.pow(b, x) + Math.pow(Math.cos(x), 3)) / (Math.pow(Math.log(a), 3) + 4.5));

			if (Double.isNaN(R)) {
				throw new ArithmeticException("Не вдалось обчислити результат.");
			}

			System.out.printf("Результат 'R' = %.4f. При значеннях 'a' = %.4f, 'b' = %.2f, 'x' = %.2f\n", R, a, b, x);

		} catch (InputMismatchException e) {
			System.out.println("Помилка введення: Ви ввели некоректні дані! Потрібно ввести число.");

		} catch (IllegalArgumentException | ArithmeticException e) {
			System.out.println("Помилка обчислень: " + e.getMessage());

		} finally {
			scanner.close();
		}
	}
}