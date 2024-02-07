package yasyt.modelo;

public class Empleado {

	// Atributos que ingresará el usuario al crear un empleado nuevo
	private String nombre;
	private double salario;
	private int edad;

	// Atributos que se cogerán de la API y se mostrarán cuando se liste la lista de
	// empleados
	private String email;
	private String genero;
	private String telefono;

	// Constructor de la clase para crear un usuario
	public Empleado(String nombre, double salario, int edad) {
		super();
		this.nombre = nombre;
		this.salario = salario;
		this.edad = edad;
	}

	// Getters i setters de los atributos de la clase
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public double getSalario() {
		return salario;
	}

	public void setSalario(double salario) {
		this.salario = salario;
	}

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

}
