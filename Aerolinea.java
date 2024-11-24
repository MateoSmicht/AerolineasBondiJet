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
		if (this.nombreAerolinea.length() == 0 || this.cuit.length() == 0) {
			throw new RuntimeException("Nombre o cuit son null");
		}
		this.aeropuertos = new HashMap<>();
		this.clientes = new HashMap<>();
		this.vuelos = new HashMap<>();
		if (this.vuelos == null || this.clientes == null || this.aeropuertos == null) {
			throw new RuntimeException("Una coleccion es null");
		}
	}

	public void registrarAeropuerto(String nombreAeropuerto, String pais, String provincia, String direccion) {
		if (aeropuertos.containsKey(nombreAeropuerto)) {
			throw new RuntimeException("El aeropuerto ya existe en el sistema ");
		} else {
			Aeropuerto aeropuertoNuevo = new Aeropuerto(nombreAeropuerto, pais, provincia, direccion);
			aeropuertos.put(nombreAeropuerto, aeropuertoNuevo);
		}
	}

	public void registrarCliente(int dni, String nombre, String telefono) {
		if (clientes.containsKey(dni)) {
			throw new RuntimeException("El cliente ya esta cargado ");
		} else {
			Cliente clienteNuevo = new Cliente(dni, nombre, telefono);
			clientes.put(dni, clienteNuevo);
		}
	}

	public String registrarVueloPublicoNacional(String origen, String destino, String fecha, int tripulantes,
			double valorRefrigerio, double[] precios, int[] cantAsientos) {
		String cantidadDeVuelos = Integer.toString(vuelos.size() + 1); // Genera el codigo del vuelo
		Nacional nuevoVueloNacional = new Nacional(valorRefrigerio, cantidadDeVuelos, aeropuertos.get(origen),
				aeropuertos.get(destino), fecha, cantAsientos, tripulantes, precios);
		// Almacenamos los vuelos en las tablas hash
		vuelos.put(nuevoVueloNacional.getIdentificacion(), nuevoVueloNacional);
		return nuevoVueloNacional.getIdentificacion();
	}

	public String registrarVueloPublicoInternacional(String origen, String destino, String fecha, int tripulantes,
			double valorRefrigerio, int cantRefrigerios, double[] precios, int[] cantAsientos, String[] escalas) {
		String codigo = Integer.toString(vuelos.size() + 1); // Genera el codigo del vuelo
		Internacional nuevoVueloPubInternacional = new Internacional(cantRefrigerios, valorRefrigerio, escalas, codigo,
				aeropuertos.get(origen), aeropuertos.get(destino), fecha, cantAsientos, tripulantes, precios);
		// Generemos codigo de vuelo
		vuelos.put(nuevoVueloPubInternacional.getIdentificacion(), nuevoVueloPubInternacional);
		return nuevoVueloPubInternacional.getIdentificacion();
	}

	public String VenderVueloPrivado(String origen, String destino, String fecha, int tripulantes, double precio,
			int dniComprador, int[] acompaniantes) {
		// Calculamos jets necesarios y precio total.
		int jets = Privado.calcularJetsNecesarios(acompaniantes);
		// Generamos codigo de vuelo.
		String codigoPriv = Integer.toString(vuelos.size() + 1);
		// Creamos vuelo
		Privado nuevoVueloPrivado = new Privado(dniComprador, acompaniantes, tripulantes, precio, jets, codigoPriv,
				aeropuertos.get(origen), aeropuertos.get(destino), fecha);

		// Guardamos el nuevo vuelo.
		vuelos.put(nuevoVueloPrivado.getIdentificacion(), nuevoVueloPrivado);
		return nuevoVueloPrivado.getIdentificacion();
	}

	public int venderPasaje(int dniCliente, String codVuelo, int nroAsiento, boolean aOcupar) {
		// Verificar si el cliente está registrado
		if (!clientes.containsKey(dniCliente)) {
			throw new RuntimeException("El cliente no está registrado.");
		}
		Publico vuelo = buscarVueloPublico(codVuelo);
		// vende el pasaje
		return vuelo.venderPasajePublico(clientes.get(dniCliente), vuelo, nroAsiento, aOcupar);
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
		List<String> registros = new LinkedList<>();
		Publico vueloCancelado = buscarVueloPublico(codVuelo);
		List<Vuelo> vuelosSimilares = new ArrayList<>();
		vuelosSimilares = vueloCancelado.vuelosSimelares_vueloCancelado(vuelos);
		Publico vueloSimilar = (Publico) vuelosSimilares.get(0); 
		registros = vueloCancelado.pasarPasajerosNuevoVuelo(vueloCancelado, vueloSimilar, vuelos);
		// elimina el vuelo
		vuelos.remove(codVuelo);
		vueloCancelado=null;
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
				+ "\nClientes totales:" + this.clientes.size() + "\nTotal de Vuelos :" + this.vuelos.size();
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
	//Metodo auxiliares
	public Publico buscarVueloPublico(String codVuelo) {
		if(vuelos.get(codVuelo).getClass().equals(Publico.class));
			Publico vueloEncontrado= (Publico) vuelos.get(codVuelo);
			return vueloEncontrado;
	}

}// end
