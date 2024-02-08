package yasyt.verificacion;

import java.util.Scanner;

public class Verificaciones {

	// Validación del nombre
	public static String validacionNombre(String nombre) {
		// Verificación de que el nombre no esta vacio, no es un numero y que contiene
		// letras y no carácteres
		if (!nombre.isEmpty() && !esNumero(nombre) && nombre.matches("[a-zA-Z]+")) {
			return nombre;
		} else {
			System.out.print("Nombre incorrecto. \n");
			return null;
		}
	}

	// Validación para el salario
	public static Double validacionSalario(Scanner scanner) {
		String input = scanner.nextLine();
		if (esDouble(input) || esNumero(input)) {
			int salario = Integer.valueOf(input);
			if (salario <= 0) {
				System.out.println("Salario incorrecto.");
				return null;
			}
			return Double.valueOf(input);
		} else {
			System.out.println("Salario incorrecto.");
			return null;
		}
	}

	// Validación para la edad
	public static int validacionEdad(Scanner scanner) {
		String input = scanner.nextLine();
		if (esNumero(input)) {
			return Integer.valueOf(input);
		} else {
			System.out.print("Edad incorrecta.\n");
			return 0;
		}
	}

	// Validación para saber si es un numero
	public static boolean esNumero(String str) {
		try {
			Integer.parseInt(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	// Validación para saber si es un double
	public static boolean esDouble(String str) {
		try {
			Double.parseDouble(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
}
