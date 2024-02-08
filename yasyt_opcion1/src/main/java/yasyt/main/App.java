package yasyt.main;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Scanner;

import yasyt.api.Api;
import yasyt.modelo.Empleado;
import yasyt.modelo.GestorEmpleados;
import yasyt.verificacion.Verificaciones;

//Clase App que ejecuta el main que es el programa principal
public class App {

	// Atributos creados para manejar tanto un objeto de la clase Api como de
	// GestorEmpleados para la lista de empleados
	public static Api objetoApi = new Api();
	public static GestorEmpleados listaEmpleados = new GestorEmpleados();

	public static void main(String[] args) throws MalformedURLException, IOException {
		boolean finalizar = false;
		System.out.print("Bienvenid@. \n");
		mostrarmenu(objetoApi, finalizar);
	}

	// Método para mostrar el menú en la consola
	private static void mostrarmenu(Api objetoApi2, boolean finalizar) throws MalformedURLException, IOException {
		while (finalizar == false) {
			System.out.print("\n");
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
	private static void escanearOpciones(int opcionEscogida) throws MalformedURLException, IOException {
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
			nombre = Verificaciones.validacionNombre(nuevoEmpleado.nextLine());
		} while (nombre == null);

		do {
			System.out.print("Introduce un salario:\n");
			salario = Verificaciones.validacionSalario(nuevoEmpleado);
		} while (salario == null || salario <= 0);

		do {
			System.out.print("Introduce una edad: \n");
			edad = Verificaciones.validacionEdad(nuevoEmpleado);
		} while (edad == 0 || edad <= 0 || edad > 100);

		Empleado emp = new Empleado(nombre, salario, edad);
		listaEmpleados.añadirEmpleado(emp);
	}

	// Método para eliminar un empleado usando el propio método de la clase
	// GestorEmpleados
	private static void eliminarEmpleado() {
		Scanner eliminarEmpleado = new Scanner(System.in);
		System.out.print("Introduce el nombre del empleado a eliminar: \n");
		String empEliminar = eliminarEmpleado.nextLine();
		boolean eliminadoCorrecto = listaEmpleados.eliminarEmpleadoPorNombre(empEliminar);
		if (eliminadoCorrecto == false) {
			System.out.print("No se ha encontrado el empleado " + empEliminar + "\n");
		} else {
			System.out.print("Usuario: " + empEliminar + " ,ha sido eliminado correctamente. \n");
		}
	}

	// Método para printar listaEmpleados
	private static void printarListaEmpleados() throws MalformedURLException, IOException {
		System.out.print("Lista de empleados: \n");
		int i = 1;
		if (listaEmpleados.getListaEmpleados().size() == 0) {
			System.out.print("No existe ningun empleado. \n");
			return;
		} else {
			for (Empleado emp : listaEmpleados.getListaEmpleados()) {
				objetoApi.conexionApi(emp);
				if (emp.getEmail() == null || emp.getGenero() == null || emp.getTelefono() == null) {
					System.out.print("No se pueden mostrar los campos email, genero y telefono. \n");
					System.out.print("Empleado " + i + ": " + emp.getNombre() + " | Salario: " + emp.getSalario()
							+ " |Edad: " + emp.getEdad());
				} else {
					System.out.print("Empleado " + i + ": " + emp.getNombre() + " | Salario: " + emp.getSalario()
							+ " | Edad: " + emp.getEdad() + " | Email: " + emp.getEmail() + " | Teléfono: "
							+ emp.getTelefono() + " | Género: " + emp.getGenero() + "\n");
					i++;
				}
			}
		}
	}
}