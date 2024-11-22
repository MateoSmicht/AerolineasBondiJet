package bondiJet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Aerolinea implements IAerolinea {
	public String nombreAerolinea;
	public String cuit;
	public HashMap<String, Aeropuerto> aeropuertos;
	private Map<Integer, Cliente> clientes;
	private Map<String, Vuelo> vuelos;
	private Map<String, Privado> vuelosPrivados;
	private Map<String, Publico> vuelosPublicos;

	public Aerolinea(String nombreAerolinea, String cuit) {

		this.nombreAerolinea = nombreAerolinea;
		this.cuit = cuit;
		if (this.nombreAerolinea.length() == 0 || this.cuit.length() == 0) {
			throw new RuntimeException("Nombre o cuit son null");
		}
		this.aeropuertos = new HashMap<>();
		this.clientes = new HashMap<>();
		this.vuelos = new HashMap<>();
		this.vuelosPrivados = new HashMap<>();
		this.vuelosPublicos = new HashMap<>();
		if (this.vuelosPrivados == null || this.vuelosPublicos == null || this.vuelos == null || this.clientes == null
				|| this.aeropuertos == null) {
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
		vuelosPublicos.put(nuevoVueloNacional.getIdentificacion(), nuevoVueloNacional);
		vuelos.put(nuevoVueloNacional.getIdentificacion(), nuevoVueloNacional);
		return nuevoVueloNacional.getIdentificacion();
	}

	public String registrarVueloPublicoInternacional(String origen, String destino, String fecha, int tripulantes,
			double valorRefrigerio, int cantRefrigerios, double[] precios, int[] cantAsientos, String[] escalas) {
		String codigo = Integer.toString(vuelos.size() + 1); // Genera el codigo del vuelo
		Internacional nuevoVueloPubInternacional = new Internacional(cantRefrigerios, valorRefrigerio, escalas, codigo,
				aeropuertos.get(origen), aeropuertos.get(destino), fecha, cantAsientos, tripulantes, precios);
		// Generemos codigo de vuelo
		vuelosPublicos.put(nuevoVueloPubInternacional.getIdentificacion(), nuevoVueloPubInternacional);
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
		vuelosPrivados.put(nuevoVueloPrivado.getIdentificacion(), nuevoVueloPrivado);
		vuelos.put(nuevoVueloPrivado.getIdentificacion(), nuevoVueloPrivado);
		return nuevoVueloPrivado.getIdentificacion();
	}

	public int venderPasaje(int dniCliente, String codVuelo, int nroAsiento, boolean aOcupar) {
		// Verificar si el cliente está registrado
		if (!clientes.containsKey(dniCliente)) {
			throw new RuntimeException("El cliente no está registrado.");
		}
		Publico vuelo = (Publico) vuelos.get(codVuelo);
		// vende el pasaje
		return vuelo.venderPasajePublico(clientes.get(dniCliente), vuelo, nroAsiento, aOcupar);
	}

	public Map<Integer, String> asientosDisponibles(String codVuelo) {
		// Buscar el vuelo en el mapa de vuelos públicos nacionales
		Publico vuelo = (Publico) vuelos.get(codVuelo);
		return vuelo.asientosDisponiblesPublico(vuelo);
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
		Publico vueloCancelado = (Publico) vuelos.get(codVuelo);
		List<Publico> vuelosSimilares = new ArrayList<>();
		vuelosSimilares = vueloCancelado.vuelosSimelares_vueloCancelado(vuelosPublicos);
		Publico vueloSimilar = vuelosSimilares.get(0);
		registros = vueloCancelado.pasarPasajerosNuevoVuelo(vueloCancelado, vueloSimilar, vuelosPublicos);
		Iterator<Publico> iterador = vuelosSimilares.iterator();
		// Si el vuelo no logro reubicar a todos los pasajaeros, busca otro vuelo.
		if (vueloCancelado.pasajes.size() != 0) {
			while (iterador.hasNext()) {
				Publico similar = iterador.next();
				if (vueloCancelado.pasajes.size() != 0)
					;
				registros = vueloCancelado.pasarPasajerosNuevoVuelo(vueloCancelado, similar, vuelosPublicos);
			}
		}
		// elimina el vuelo
		vuelos.remove(codVuelo);
		return registros;
	}

	public double totalRecaudado(String destino) {
		double totalRecaudacion = 0.0;
		// Recaudacion de vuelos privados
		for (Privado vueloPriv : vuelosPrivados.values()) {
			if (vueloPriv.getAeropuertoDestino().getNombre().equals(destino)) {
				totalRecaudacion = totalRecaudacion + vueloPriv.calcularPrecioFinal();
			}
		}
		// Recaudación de vuelos publicos
		for (Publico vuelo : vuelosPublicos.values()) {
			if (vuelo.elVueloEsNacional(vuelo)) {
				Nacional vueloNacional = (Nacional) vuelo;
				totalRecaudacion = totalRecaudacion + vueloNacional.recaudacion_pasajes_a_destino(destino);
			} else {
				Internacional vueloInternacional = (Internacional) vuelo;
				totalRecaudacion = totalRecaudacion + vueloInternacional.recaudacion_pasajes_a_destino(destino);
			}
		}
		return totalRecaudacion;
	}

	public String detalleDeVuelo(String codVuelo) {
		Vuelo vuelo = vuelos.get(codVuelo);
		if (vuelo != null) {
			return vuelo.generarDetalle();
		}
		throw new RuntimeException("Vuelo no encontrado para el código proporcionado: " + codVuelo);
	}

	public String toString() {
		return "Aerolínea: " + this.nombreAerolinea + "\nCUIT: " + cuit + "\nVuelos Publicos:" + this.vuelosPublicos
				+ "\nVuelos Privados:" + this.vuelosPrivados + "\nClientes totales:" + this.clientes.size();
	};

	public void cancelarPasaje(int dni, String codigoVuelo, int codPasaje) {
		Publico v = (Publico) vuelos.get(codigoVuelo);
		v.cancelarPasajePublico(dni, codPasaje, v);// Resuelto en O(1)
	}

	@Override
	public void cancelarPasaje(int dni, int codPasaje) {
		// TODO Auto-generated method stub

	}

}// end
