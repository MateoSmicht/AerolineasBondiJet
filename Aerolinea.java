package bondiJet;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Aerolinea implements IAerolinea {
	public String nombreAerolinea;
	public String cuit;
	public HashMap<String, Aeropuerto> aeropuertos;
	private Map<Integer, Cliente> clientes;
	private Map<String, Nacional> vuelosPublicosNacional;
	private Map<String, Internacional> vuelosPublicosInternacionales;
	private Map<String, Privado> vuelosPrivados;
	private Map<String, Vuelo> vuelos; // Mapa para todos los vuelo

	public Aerolinea(String nombreAerolinea, String cuit) {
		this.nombreAerolinea = nombreAerolinea;
		this.cuit = cuit;
		this.aeropuertos = new HashMap<>();
		this.clientes = new HashMap<>();
		this.vuelosPublicosNacional = new HashMap<>();
		this.vuelosPublicosInternacionales = new HashMap<>();
		this.vuelosPrivados = new HashMap<>();
		this.vuelos = new HashMap<>();
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
		int cantidadDeVuelos = vuelos.size();
		Nacional nuevoVueloNacional = new Nacional(valorRefrigerio,
				cantidadDeVuelos, aeropuertos.get(origen),
				aeropuertos.get(destino), fecha, cantAsientos, tripulantes, precios);
		// Comprobamos si el vuelo es nacional
		if (nuevoVueloNacional.esUnVueloValido() == false) {
			throw new RuntimeException("El vuelo no es nacional");
		}
		// Almacenamos los vuelos en las tablas hash
		String codigo = nuevoVueloNacional.generarCodigoVuelo(cantidadDeVuelos);
		vuelos.put(codigo, nuevoVueloNacional);
		return codigo;
	}

	public String registrarVueloPublicoInternacional(String origen, String destino, String fecha, int tripulantes,
			double valorRefrigerio, int cantRefrigerios, double[] precios, int[] cantAsientos, String[] escalas) {
		int cantidadDeVuelos = vuelos.size();
		Internacional nuevoVueloPubInternacional = new Internacional(cantRefrigerios, valorRefrigerio, escalas, cantidadDeVuelos,
				aeropuertos.get(origen), aeropuertos.get(destino), fecha, cantAsientos, tripulantes, precios);
		// Comprobamos si el vuelo es nacional
		if (nuevoVueloPubInternacional.esUnVueloValido() == false) {
			throw new RuntimeException("El vuelo no es internacional");
		}
		// Generemos codigo de vuelo
		String codigo = nuevoVueloPubInternacional.generarCodigoVuelo(cantidadDeVuelos);
		vuelos.put(codigo, nuevoVueloPubInternacional);
		return codigo;
	}

	public String VenderVueloPrivado(String origen, String destino, String fecha, int tripulantes, double precio,
			int dniComprador, int[] acompaniantes) {
		int cantidadDeVuelos = vuelos.size();
		//Calculamos jets necesarios y precio total.
		int jets = Privado.calcularJetsNecesarios(acompaniantes);
		double precioTotal = Privado.calcularPrecioFinal(jets,precio);
		//Creamos vuelo
		Privado nuevoVueloPrivado = new Privado(dniComprador, acompaniantes, tripulantes, precioTotal, jets, cantidadDeVuelos,
				aeropuertos.get(origen), aeropuertos.get(destino), fecha);
		//irep
		boolean Posterior = nuevoVueloPrivado.esFechaPosterior(fecha);
		if (!Posterior) {
			throw new RuntimeException("La fecha no es valida.");
		}
		if(!nuevoVueloPrivado.esUnVueloValido()) {
			throw new RuntimeException("El vuelo privado no se puedo generar.");
		}
		// Generamos codigo de vuelo.
		String codigoPriv = nuevoVueloPrivado.generarCodigoVuelo(cantidadDeVuelos);
		// Guardamos el nuevo vuelo.
		vuelos.put(codigoPriv, nuevoVueloPrivado);
		return codigoPriv;
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
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate fechaConsulta = LocalDate.parse(fecha, formatter);
		LocalDate fechaMaxima = fechaConsulta.plusDays(7);

		List<String> vuelosSimilares = new ArrayList<>();

		// Recorrer todos los vuelos
		for (Vuelo vuelo : vuelos.values()) {
			LocalDate fechaVuelo = LocalDate.parse(vuelo.getFecha(), formatter);

			// Verificar que el origen y destino coinciden, y que la fecha está dentro del
			// rango
			if (vuelo.getAeropuertoSalida().getNombre().equals(origen)
					&& vuelo.getAeropuertoDestino().getNombre().equals(destino) && (fechaVuelo.isEqual(fechaConsulta)
							|| (fechaVuelo.isAfter(fechaConsulta) && fechaVuelo.isBefore(fechaMaxima)))) {
				
				vuelosSimilares.add(vuelo.getIdentificacion());
			}
		}

		return vuelosSimilares;
	}

	public double totalRecaudado(String destino) {
		double totalRecaudacion = 0.0;

		// Recaudación de vuelos privados
		for (Privado vuelo : vuelosPrivados.values()) {
			if (vuelo.getAeropuertoDestino().getNombre().equals(destino)) {
				double precioPorJet = vuelo.getPrecio();
				int cantidadJets = vuelo.getCantidadJets();
				double recaudacionVuelo = cantidadJets * precioPorJet;
				recaudacionVuelo += recaudacionVuelo * 0.30; // Aplicar 30% de impuestos
				totalRecaudacion += recaudacionVuelo;
			}
		}

		// Recaudación de vuelos internacionales
		for (Internacional vuelo : vuelosPublicosInternacionales.values()) {
			if (vuelo.getAeropuertoDestino().getNombre().equals(destino)) {
				double recaudacionVuelo = 0.0;

				// Sumar el costo de los asientos según la sección
				for (int i = 0; i < vuelo.cantidadAsientos.length; i++) {
					recaudacionVuelo += vuelo.cantidadAsientos[i] * vuelo.precio[i];
				}

				// Sumar el costo de los refrigerios para todos los pasajeros
				int totalPasajeros = Arrays.stream(vuelo.cantidadAsientos).sum();
				recaudacionVuelo += totalPasajeros * vuelo.getCantidadRefrigerios() * vuelo.getValorRefrigerio();

				// Aplicar 20% de impuestos
				recaudacionVuelo += recaudacionVuelo * 0.20;
				totalRecaudacion += recaudacionVuelo;
			}
		}

		return totalRecaudacion;
	}

	public String detalleDeVuelo(String codVuelo) {
		StringBuilder detalle = new StringBuilder();
		Publico vueloNacional = vuelosPublicosNacional.get(codVuelo);
		if (vueloNacional != null) {
			detalle.append(codVuelo).append(" - ").append(vueloNacional.getAeropuertoSalida().getNombre()).append(" - ")
					.append(vueloNacional.getAeropuertoDestino().getNombre()).append(" - ")
					.append(vueloNacional.getFecha()).append(" - NACIONAL");
			return detalle.toString();
		}
		Publico vueloInternacional = vuelosPublicosInternacionales.get(codVuelo);
		if (vueloInternacional != null) {
			detalle.append(codVuelo).append(" - ").append(vueloInternacional.getAeropuertoSalida().getNombre())
					.append(" - ").append(vueloInternacional.getAeropuertoDestino().getNombre()).append(" - ")
					.append(vueloInternacional.getFecha()).append(" - INTERNACIONAL");
			return detalle.toString();
		}
		Privado vueloPrivado = vuelosPrivados.get(codVuelo);
		if (vueloPrivado != null) {
			detalle.append(codVuelo).append(" - ").append(vueloPrivado.getAeropuertoSalida().getNombre()).append(" - ")
					.append(vueloPrivado.getAeropuertoDestino().getNombre()).append(" - ")
					.append(vueloPrivado.getFecha()).append(" - PRIVADO (").append(vueloPrivado.getCantidadJets())
					.append(")");
			return detalle.toString();
		}
		throw new RuntimeException("Vuelo no encontrado para el código proporcionado: " + codVuelo);
	}

	public List<String> cancelarVuelo(String codVuelo) {
		List<String> registros = new ArrayList<>();
		Publico vueloCancelado = (Publico) vuelos.get(codVuelo);
		Publico vueloAlternativo = (Publico) vueloCancelado.vueloAlternativoPublico(vueloCancelado, vuelos);
		registros.add(vueloAlternativo.identificacion);
		return registros;
	}

	public String toString() {
		return "Aerolínea: " + this.nombreAerolinea + "\nCUIT: " + cuit;
	};

	public void cancelarPasaje(int dni, String codigoVuelo, int codPasaje) {
		Publico v = (Publico) vuelos.get(codigoVuelo);
		v.cancelarPasajePublico(dni, codPasaje, v);
	}

}// end
