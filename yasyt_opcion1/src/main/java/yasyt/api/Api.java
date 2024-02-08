package yasyt.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import yasyt.modelo.Empleado;

public class Api {

	// Atributo de la clase que es la URL a la que vamos hacer la petición.
	// En caso de modificación, unicamente habría que cambiar esta URL i no tocar el
	// código
	public String urlApi = "https://randomuser.me/api/?inc=";
	// Parámetros para enviar a la petición y que modificaremos con los setters
	// adecuados nuestro objeto Empleado.
	// Estos parámetros son sacados de la documentación de RandomUserAPI.
	public static String parametros = "email,gender,phone";
	public Scanner scannerParametros = new Scanner(System.in);
	public Scanner scannerURL = new Scanner(System.in);

	public HttpURLConnection obtenerConexionParametros() throws MalformedURLException, IOException {
		HttpURLConnection con = null;
		URL url = new URL(urlApi + parametros);
		con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		return con;
	}

	public Empleado conexionApi(Empleado emp) throws IOException {
		HttpURLConnection conexion = null;
		boolean parametrosCorrectos = false;
		while (!parametrosCorrectos) {
			try {
				conexion = obtenerConexionParametros();
				BufferedReader in = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
				String inputLine;
				StringBuilder response = new StringBuilder();
				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();
				parametrosCorrectos = verificacionParametro(response, emp);
				// Si los parámetros no son correctos, solicitamos al usuario que los vuelva a
				// ingresar
				if (!parametrosCorrectos) {
					System.out.println("Introduce uno o varios parametros de forma consecutiva separado por ','\n");
					parametros = scannerParametros.nextLine();
				}
				// Método para añadirle los parámetros recibidos
				añadirParametros(response, emp);
				conexion.disconnect();
			} catch (MalformedURLException e) {
				System.err.println("Se produjo un error en el formato de la URL \n");
				manejarErrorURL(emp, conexion);
			} catch (IOException e) {
				System.err.println("Se produjo un error en la conexión a la API \n");
				manejarErrorURL(emp, conexion);
			}
		}
		return emp;
	}

	// Método para verificar los parámetros
	private boolean verificacionParametro(StringBuilder response, Empleado emp) throws IOException {
		String[] parametrosSplit = parametros.split(",");
		boolean todosParametrosEncontrados = true;
		for (String parametro : parametrosSplit) {
			if (!response.toString().contains(parametro.trim())) {
				todosParametrosEncontrados = false;
				System.out.println("No se encontraron todos los parámetros '" + parametros + "' en la respuesta.");
				return false;
			}
		}
		return todosParametrosEncontrados;
	}

	// Método para manejar cuando hay un error en la URL y para volver a pedirsela
	// al usuario
	private void manejarErrorURL(Empleado emp, HttpURLConnection conexion) throws MalformedURLException, IOException {
		System.out.println("Introduce una URL válida.");
		urlApi = scannerURL.nextLine();
		conexionApi(emp);
	}

	private void añadirParametros(StringBuilder response, Empleado emp) {
		// Procesar la respuesta JSON y almacenar en variables los campos que queremos
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode rootNode;
		try {
			rootNode = objectMapper.readTree(response.toString());
			JsonNode resultsNode = rootNode.path("results").get(0);
			String email = resultsNode.path("email").asText();
			String genero = resultsNode.path("gender").asText();
			String telefono = resultsNode.path("phone").asText();
			// Modificar con setters los atributos del Empleado que recibimos por parámetro
			emp.setEmail(email);
			emp.setGenero(genero);
			emp.setTelefono(telefono);
		} catch (JsonMappingException e) {
			System.out.print(
					"Se ha producido un error en el proceso de mapeo de JSON, verifique la estructura del JSON, el tipo de dato o los campos que se desea obtener \n");
		} catch (JsonProcessingException e) {
			System.out.print(
					"Se ha producido un error con la sintaxi JSON recibida, fallo de lectura/escritura o problemas de conversion de datos. \n");
			e.printStackTrace();
		} catch (NullPointerException e) {
			System.out
					.print("Se ha producido un error a la hora de buscar los parametros en la respuesta de la API \n");
		}
	}
}
