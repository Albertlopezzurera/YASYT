package yasyt.main;

import java.util.Scanner;

import yasyt.Api;
import yasyt.modelo.Empleado;
import yasyt.modelo.GestorEmpleados;

//Clase App que ejecuta el main que es el programa principal
public class App {

	// Atributos creados para manejar tanto un objeto de la clase Api como de
	// GestorEmpleados para la lista de empleados
	public static Api objetoApi = new Api();
	public static GestorEmpleados listaEmpleados = new GestorEmpleados();

	public static void main(String[] args) {
		boolean finalizar = false;
		System.out.print("Bienvenid@. \n");
		mostrarmenu(objetoApi, finalizar);
	}

	// Método para mostrar el menú en la consola
	private static void mostrarmenu(Api objetoApi2, boolean finalizar) {
		while (finalizar == false) {
			System.out.print("Introduce un número para las siguientes opciones: \n");
			System.out.print("- - - - - - - - \n");
			System.out.print("1. Añadir un nuevo empleado. \n");
			System.out.print("2. Eliminar un empleado. \n");
			System.out.print("3. Listar todos los empleados. \n");
			System.out.print("Para finalizar marque 0 \n");
			Scanner scanner = new Scanner(System.in);
			int opcionEscogida = scanner.nextInt();
			if (opcionEscogida == 0) {
				System.out.print("Hasta la proxima!");
				return;
			}
			escanearOpciones(opcionEscogida);

		}

	}

	// Método para dependiendo de la opción escogida se realice una opción u otra
	private static void escanearOpciones(int opcionEscogida) {
		switch (opcionEscogida) {
		case 1:
			crearNuevoEmpleado();
			break;
		case 2:
			eliminarEmpleado();
			break;
		case 3:
			printarListaEmpleados();
			break;
		}

	}

	private static void crearNuevoEmpleado() {
		Scanner nuevoEmpleado = new Scanner(System.in);
		String nombre;
		Double salario;
		int edad;

		// Do While para saber que mínimo una vez pasará por el Do y el while para que
		// se repita hasta que se cumpla la condición
		do {
			System.out.print("Introduce un nombre:\n");
			nombre = nuevoEmpleado.nextLine();
		} while (!validacionNombre(nombre));

		do {
			System.out.print("Introduce un salario:\n");
			salario = validacionSalario(nuevoEmpleado);
		} while (salario == null || salario <= 0 || salario.isNaN());

		do {
			System.out.print("Introduce una edad: \n");
			edad = validacionEdad(nuevoEmpleado);
		} while (edad == 0 || edad <= 0 || edad > 100);

		Empleado emp = new Empleado(nombre, salario, edad);
		listaEmpleados.añadirEmpleado(emp);
	}

	// Validación del nombre
	private static boolean validacionNombre(String nombre) {

		// Verificación de que el nombre no esta vacio, no es un numero y que contiene
		// letras y no carácteres
		return !nombre.isEmpty() && !esNumero(nombre) && nombre.matches("[a-zA-Z]+");
	}

	// Validación para el salario
	private static Double validacionSalario(Scanner scanner) {
		String input = scanner.nextLine();
		if (esDouble(input) || esNumero(input)) {
			return Double.valueOf(input);
		} else {
			System.out.println("Salario inválido. Introduce un número válido.");
			return null;
		}
	}

	// Validación para la edad
	private static int validacionEdad(Scanner scanner) {
		String input = scanner.nextLine();
		if (esNumero(input)) {
			return Integer.valueOf(input);
		} else {
			System.out.print("Edad inválida. Introduce una edad válida.\n");
			return 0;
		}
	}

	// Validación para saber si es un numero
	private static boolean esNumero(String str) {
		try {
			Integer.parseInt(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	// Validación para saber si es un double
	private static boolean esDouble(String str) {
		try {
			Double.parseDouble(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	// Método para eliminar un empleado usando el propio método de la clase
	// GestorEmpleados
	private static void eliminarEmpleado() {
		Scanner eliminarEmpleado = new Scanner(System.in);
		System.out.print("Introduce el nombre del empleado a eliminar: \n");
		boolean eliminadoCorrecto = listaEmpleados.eliminarEmpleadoPorNombre(eliminarEmpleado.nextLine());
		if (eliminadoCorrecto == false) {
			System.out.print("No se ha encontrado el empleado \n");
		} else {
			System.out.print("Usuario eliminado correctamente. \n");
		}
	}

	// Método para printar listaEmpleados
	private static void printarListaEmpleados() {
		System.out.print("Lista de empleados: \n");
		int i = 1;
		for (Empleado emp : listaEmpleados.getListaEmpleados()) {
			objetoApi.connexionApi(emp);
			System.out.print("Empleado " + i + ": " + emp.getNombre() + " | Salario: " + emp.getSalario() + " | Edad: "
					+ emp.getEdad() + " | Email: " + emp.getEmail() + " | Teléfono: " + emp.getTelefono()
					+ " | Género: " + emp.getGenero() + "\n");
			i++;
		}

	}

}