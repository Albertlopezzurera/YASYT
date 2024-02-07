package yasyt.modelo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GestorEmpleados {

	// Único atributo de la clase que es contener una lista de Empleados
	private List<Empleado> listaEmpleados;

	// Constructor de la clase, el cual inicializamos la listaEmpleados
	public GestorEmpleados() {
		listaEmpleados = new ArrayList<>();
	}

	// Método para añadir un Empleado
	public void añadirEmpleado(Empleado empleado) {
		listaEmpleados.add(empleado);
	}

	// Método para listar todos los empleados
	public List<Empleado> listarTodosEmpleados() {
		return listaEmpleados;
	}

	// Método para eliminar un empleado por un nombre que recibe por parámetro
	public boolean eliminarEmpleadoPorNombre(String nombre) {
		Iterator<Empleado> iterator = listaEmpleados.iterator();
		while (iterator.hasNext()) {
			Empleado empleado = iterator.next();
			if (empleado.getNombre().equals(nombre)) {
				iterator.remove();
				return true;
			}
		}
		return false;
	}

	// Getters i setters del atributo de la clase
	public List<Empleado> getListaEmpleados() {
		return listaEmpleados;
	}

	public void setListaEmpleados(List<Empleado> listaEmpleados) {
		this.listaEmpleados = listaEmpleados;
	}
}
