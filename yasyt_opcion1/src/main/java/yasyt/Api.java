package yasyt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import yasyt.modelo.Empleado;

public class Api {

	// Atributo de la clase que es la URL a la que vamos hacer la petición.
	// En caso de modificación, unicamente habría que cambiar esta URL i no tocar el
	// código
	public static String urlApi = "https://randomuser.me/api/?inc=";

	// Parámetros para enviar a la petición y que modificaremos con los setters
	// adecuados nuestro objeto Empleado.
	// Estos parámetros son sacados de la documentación de RandomUserAPI.
	public static String parametros = "email,gender,phone";

	public HttpURLConnection obtenerConexionParametros() throws IOException {

		// Crear objeto URL donde le pasamos el atributo que contiene la url y los
		// parametros
		URL url = new URL(urlApi + parametros);

		// Abrir la connexión con el Objeto HttpURLConnection que nos proporciona ya
		// Java
		HttpURLConnection con = (HttpURLConnection) url.openConnection();

		// Asignar a la conexión el metodo GET
		con.setRequestMethod("GET");
		return con;
	}

	public Empleado connexionApi(Empleado emp) {
		try {
			HttpURLConnection conexion = obtenerConexionParametros();

			// Leer la respuesta que nos devuelve la petición con los parámetros
			BufferedReader in = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
			String inputLine;
			StringBuilder response = new StringBuilder();
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			// Método para añadirle los parámetros recibidos
			añadirParametros(response, emp);

			// Cerrar conexión
			conexion.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return emp;
	}

	private void añadirParametros(StringBuilder response, Empleado emp)
			throws JsonMappingException, JsonProcessingException {

		// Procesar la respuesta JSON y almacenar en variables los campos que queremos
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode rootNode = objectMapper.readTree(response.toString());
		JsonNode resultsNode = rootNode.path("results").get(0);
		String email = resultsNode.path("email").asText();
		String genero = resultsNode.path("gender").asText();
		String telefono = resultsNode.path("phone").asText();

		// Modificar con setters los atributos del Empleado que recibimos por parámetro
		emp.setEmail(email);
		emp.setGenero(genero);
		emp.setTelefono(telefono);
	}

}
