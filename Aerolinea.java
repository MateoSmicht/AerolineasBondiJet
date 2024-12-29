package bondiJet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Aerolinea implements IAerolinea {
	private String nombreAerolinea;
	private String cuit;
	private HashMap<String, Aeropuerto> aeropuertos;
	private Map<Integer, Cliente> clientes;
	private Map<String, Vuelo> vuelos;

	public Aerolinea(String nombreAerolinea, String cuit) {

		this.nombreAerolinea = nombreAerolinea;
		this.cuit = cuit;
		// IREP
		if (this.nombreAerolinea.length() == 0 || this.cuit.length() == 0) {
			throw new RuntimeException("Nombre o cuit son null");
		}
		this.aeropuertos = new HashMap<>();
		this.clientes = new HashMap<>();
		this.vuelos = new HashMap<>();
		
	}

	public void registrarAeropuerto(String nombreAeropuerto, String pais, String provincia, String direccion) {
		if (aeropuertos.containsKey(nombreAeropuerto)) {
			throw new RuntimeException("El aeropuerto ya existe en el sistema "); // irep
		} else {
			// Crea el nuevl aeropuerto
			Aeropuerto aeropuertoNuevo = new Aeropuerto(nombreAeropuerto, pais, provincia, direccion);
			aeropuertos.put(nombreAeropuerto, aeropuertoNuevo);
		}
	}

	public void registrarCliente(int dni, String nombre, String telefono) {
		if (clientes.containsKey(dni)) {
			throw new RuntimeException("El cliente ya esta cargado "); // irep
		} else {
			// Crea el nuevo clientes.
			Cliente clienteNuevo = new Cliente(dni, nombre, telefono);
			clientes.put(dni, clienteNuevo);
		}
	}

	public String registrarVueloPublicoNacional(String origen, String destino, String fecha, int tripulantes,
			double valorRefrigerio, double[] precios, int[] cantAsientos) {
		String cantidadDeVuelos = Integer.toString(vuelos.size() + 1); // Genera el codigo del vuelo
		if (vuelos.containsKey(cantidadDeVuelos)) {
			throw new RuntimeException("El vuelo nacional ya esta cargado ");
		}
		// creamos vuelo nacional.
		Nacional nuevoVueloNacional = new Nacional(valorRefrigerio, cantidadDeVuelos, aeropuertos.get(origen),
				aeropuertos.get(destino), fecha, cantAsientos, tripulantes, precios);
		// Almacenamos los vuelos en las tablas hash
		vuelos.put(nuevoVueloNacional.getIdentificacion(), nuevoVueloNacional);
		return nuevoVueloNacional.getIdentificacion();
	}

	public String registrarVueloPublicoInternacional(String origen, String destino, String fecha, int tripulantes,
			double valorRefrigerio, int cantRefrigerios, double[] precios, int[] cantAsientos, String[] escalas) {
		String codigo = Integer.toString(vuelos.size() + 1); // Genera el codigo del vuelo
		if (vuelos.containsKey(codigo)) {
			throw new RuntimeException("El vuelo internacional ya esta cargado ");
		}
		// creamos vuelo internacional
		Internacional nuevoVueloPubInternacional = new Internacional(cantRefrigerios, valorRefrigerio, escalas, codigo,
				aeropuertos.get(origen), aeropuertos.get(destino), fecha, cantAsientos, tripulantes, precios);
		// Generemos codigo de vuelo
		vuelos.put(nuevoVueloPubInternacional.getIdentificacion(), nuevoVueloPubInternacional);
		return nuevoVueloPubInternacional.getIdentificacion();
	}

	public String VenderVueloPrivado(String origen, String destino, String fecha, int tripulantes, double precio,
			int dniComprador, int[] acompaniantes) {
		// Generamos codigo de vuelo.
		String codigoPriv = Integer.toString(vuelos.size() + 1);
		
		// Creamos vuelo
		Privado nuevoVueloPrivado = new Privado(dniComprador, acompaniantes, tripulantes, precio, codigoPriv,
				aeropuertos.get(origen), aeropuertos.get(destino), fecha);

		// Guardamos el nuevo vuelo.
		vuelos.put(nuevoVueloPrivado.getIdentificacion(), nuevoVueloPrivado);
		return nuevoVueloPrivado.getIdentificacion();
	}

	public int venderPasaje(int dniCliente, String codVuelo, int nroAsiento, boolean aOcupar) {
		Publico vuelo = buscarVueloPublico(codVuelo);
		// vende el pasaje
		return vuelo.venderPasajePublico(buscarCliente(dniCliente), vuelo, nroAsiento, aOcupar);
	}

	public Map<Integer, String> asientosDisponibles(String codVuelo) {
		// Buscar el vuelo en el mapa de vuelos públicos nacionales
		Publico vuelo = (Publico) vuelos.get(codVuelo);
		return vuelo.asientosDisponibles();
	}

	public List<String> consultarVuelosSimilares(String origen, String destino, String fecha) {
		List<String> vuelosSimilares = new ArrayList<>();
		// Recorrer todos los vuelos
		for (Vuelo vuelo : vuelos.values()) {
			vuelosSimilares = vuelo.vueloSimilar(vuelo, fecha, origen, destino);
		}
		return vuelosSimilares;
	}

	public List<String> cancelarVuelo(String codVuelo) {
		// Registro del estado de los pasajes
		List<String> registros = new LinkedList<>();
		// Vuelos
		Publico vueloCancelado = buscarVueloPublico(codVuelo);
		Publico vueloSimilar = (Publico) vueloCancelado.vueloSimilar_vueloCancelado(vuelos);
		// Pasa los pasajeros al nuevo vuelo.
		registros = vueloCancelado.pasarPasajerosNuevoVuelo(vueloCancelado, vueloSimilar, vuelos);
		// elimina el vuelo
		vuelos.remove(codVuelo);
		vueloCancelado = null;
		return registros;
	}

	public double totalRecaudado(String destino) {
		double totalRecaudacion = 0.0;
		// Recaudación de vuelos
		for (Vuelo vuelo : vuelos.values()) {
			if (vuelo.vaAlDestino(destino)) {
				totalRecaudacion = vuelo.totalRecaudado();
			}
		}
		return totalRecaudacion;
	}

	public String detalleDeVuelo(String codVuelo) {
		Vuelo vuelo = vuelos.get(codVuelo);
		return vuelo.generarDetalle();
	}

	@Override
	public String toString() {
		return "Aerolínea: " + this.nombreAerolinea + "\nCUIT: " + cuit + "\nVuelos :" + this.vuelos
				+ "\nClientes totales:" +this.clientes.size()+ "\nDetalles clientes :" + this.clientes 
				+ "\nTotal de Vuelos :" + this.vuelos.size() + "\nAeropuertos :" + this.aeropuertos;
	};

	public void cancelarPasaje(int dni, String codigoVuelo, int codPasaje) {
		Vuelo v = vuelos.get(codigoVuelo);
		v.cancelarPasajePublico(dni, codPasaje);// Resuelto en O(1)
	}

	// Resuelto en O(n)= n es el total de vuelos.
	public void cancelarPasaje(int dni, int codPasaje) {
		for (Vuelo vuelo : vuelos.values()) {
			vuelo.cancelarPasajePublico(dni, codPasaje);
		}

	}

	// Metodo auxiliares
	private Vuelo buscarVuelo(String codVuelo) {
;		if (!(vuelos.containsKey(codVuelo))) {
			throw new RuntimeException("el vuelo no existe");
		}
		return vuelos.get(codVuelo);
	}
	private Publico buscarVueloPublico(String codVuelo) {
		Vuelo vuelo =buscarVuelo(codVuelo);
		if (!(vuelo instanceof Publico)) {
			throw new RuntimeException("no es vuelo publico");
		}
		Publico vueloEncontrado = (Publico) vuelo;
		return vueloEncontrado;
	}
	
	//BUSCARCLIENTE
	private Cliente buscarCliente(int dni) {
		if (clientes.get(dni)== null || !clientes.containsKey(dni)) {
			throw new RuntimeException("El cliente no existe");
		}
		 return clientes.get(dni);
	}
}// end
