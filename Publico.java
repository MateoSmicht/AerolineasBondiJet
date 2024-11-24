package bondiJet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public abstract class Publico extends Vuelo {
	protected int[] cantidadAsientos;
	protected int cantidadTripulantes;
	protected Map<Integer, Pasaje> pasajes;
	protected double[] precio;

	public Publico(String identificacion, Aeropuerto aeropuertoSalida, Aeropuerto aeropuertoDestino, String fecha,
			int[] cantidadAsientos, int cantidadTripulantes, double[] precio) {
		super(identificacion, aeropuertoSalida, aeropuertoDestino, fecha);

		this.cantidadAsientos = cantidadAsientos;
		this.cantidadTripulantes = cantidadTripulantes;
		if (this.cantidadTripulantes < 0) {
			throw new RuntimeException("el numero de tripulantes es negativo");
		}
		this.precio = precio;
		this.pasajes = new HashMap<>();
		if (this.pasajes == null) {
			throw new RuntimeException("Pasajes es null.");
		}
	}
	

	@Override
	protected double totalRecaudado() {
		double recaudacion = 0.0;
		for (Pasaje pasaje : pasajes.values()) {
			recaudacion = recaudacion + this.precioVuelo(pasaje.getSeccionASiento());
		}
		return recaudacion;
	}

	@Override
	protected double precioVuelo(int seccionAsiento) {
		double precioSeccion = precio[seccionAsiento]; // (ej. 0 para Turista, 1 para Ejecutiva)
		return precioSeccion;
	}

	protected int sumarAsientos(int[] asientos) {
		int suma = 0;
		for (int asiento : asientos) {
			suma += asiento;
		}
		return suma;
	}

	protected int determinarSeccion(int[] cantidadAsientos, int nroAsiento) {
		int seccion = 0;
		int acumulado = 0;

		for (int i = 0; i < cantidadAsientos.length; i++) {
			acumulado += cantidadAsientos[i];
			if (nroAsiento <= acumulado) {
				seccion = i;
				break;
			}
		}
		return seccion;
	}

	@Override
	protected boolean elVueloEsNacional() {
		if (this.aeropuertoDestino.getPais().equals("Argentina")
				&& this.aeropuertoSalida.getPais().equals("Argentina")) {
			if (this.cantidadAsientos.length == 2 && this.precio.length == 2) {
				return true;
			}
		}
		return false;
	}

	protected Map<Integer, String> asientosDisponibles() {
		return null;
	}
	@Override
	protected List<String> pasarPasajerosNuevoVuelo(Publico vueloAcancelar, Publico vueloAlternativo,
			Map<String, Vuelo> vuelos) {
		List<String> registros = new ArrayList<>();
		// Recorre los pasajes de el vuelo a cancelar
		for (Pasaje pasaje : vueloAcancelar.pasajes.values()) {
			// manda a ubicar los pasajes del vuelo cancelado, al nuevo vuelo
			registros.add(vueloAcancelar.ubicarPasajeroNuevoVuelo(pasaje, vueloAcancelar, vueloAlternativo));
		}
		return registros;
	}

	private String ubicarPasajeroNuevoVuelo(Pasaje pasajero, Publico cancelado, Publico nuevoVuelo) {
		Map<Integer, String> asientosDisponibles = nuevoVuelo.asientosDisponibles();
		String resultado = "";
		int contador = 0; // Contador de asientos

		if (!asientosDisponibles.get(pasajero.getAsientoAsignado()).equals("[OCUPADO]")) {
			// Si el asiento no esta ocupado vendemos un nuevo pasaje al vuelo nuevo.
			nuevoVuelo.venderPasajePublico(pasajero.getCliente(), nuevoVuelo, pasajero.getAsientoAsignado(),
					pasajero.getOcupadoAsiento());
			cancelado.pasajes.remove(pasajero.getAsientoAsignado());
			// Genera mensaje de informacion del cambio de vuelo
			resultado = nuevoVuelo.pasajes.get(pasajero.getAsientoAsignado())
					.generarInformacionCambioVuelo(nuevoVuelo.getIdentificacion());
			// El asiento original del pasaje se encuentra ocupado.
		} else {
			Iterator<String> iterator = asientosDisponibles.values().iterator();
			while (iterator.hasNext()) {
				String asiento = iterator.next();
			// Recorre los asientos hasta encontrar uno que no este ocupado
				contador += 1;
				if (contador > pasajero.getAsientoAsignado()) { // Recorre hasta superar el numero del asiento del
																// pasaje.
					if (!asiento.equals("[OCUPADO]")) {
						// Si el asiento no esta ocupado vendemos un nuevo pasaje al vuelo nuevo.
						nuevoVuelo.venderPasajePublico(pasajero.getCliente(), nuevoVuelo, contador,
								pasajero.getOcupadoAsiento());
						//eliminamos pasaje del vuelo cancelado
						cancelado.pasajes.remove(pasajero.getAsientoAsignado());
						//generamos mensaje informativo
						resultado = nuevoVuelo.pasajes.get(contador)
								.generarInformacionCambioVuelo(nuevoVuelo.getIdentificacion());
					}
				} else {// Todos los asientos ocupados, no se puedo ubicar en el nuevo vuelo.
					resultado = cancelado.pasajes.get(pasajero.getAsientoAsignado())
							.generarInformacionCambioVuelo("CANCELADO");
					cancelado.pasajes.remove(pasajero.getAsientoAsignado());
				}

			}
		}
		return resultado;// Devuelve el mensaje informativo.
	}
	
	protected Vuelo vueloSimilar_vueloCancelado(Map<String, Vuelo> vuelos) {
		// Devuelve lista de vuelos similares.
		Vuelo vueloSimilar = null;
		// Recorrer todos los vuelos
		Iterator<Vuelo> iterator = vuelos.values().iterator();
		while (iterator.hasNext()) {
			Vuelo vuelo =  iterator.next();
			if (!vuelo.getIdentificacion().equals(this.getIdentificacion()) && !vuelo.equals(null)) {
				if (vuelo.aeropuertoDestino.getNombre().equals(this.getAeropuertoDestino().getNombre())
						&& vuelo.aeropuertoSalida.getNombre().equals(this.getAeropuertoSalida().getNombre())) {
					vueloSimilar=vuelo;
					return vuelo;
				}
			}
		}
		return vueloSimilar;
		
	}

	protected int venderPasajePublico(Cliente cliente, Publico vuelo, int nroAsiento, boolean ocupado) {
		if (vuelo == null) {
			throw new RuntimeException("Vuelo no encontrado.");
		}
		// determina seccion
		int seccion = determinarSeccion(vuelo.cantidadAsientos, nroAsiento);
		// Verificar si el asiento está disponible
		if (pasajes.containsKey(nroAsiento)) {
			throw new RuntimeException("El asiento ya está ocupado.");
		}
		// Registrar el pasaje
		int codigoPasaje = nroAsiento;
		Pasaje nuevoPasaje = new Pasaje(nroAsiento, vuelo, cliente, seccion, ocupado);
		pasajes.put(nroAsiento, nuevoPasaje);

		return codigoPasaje;
	}

	@Override
	protected void cancelarPasajePublico(int dni, int codigoPasaje) {
		if (this.pasajes.get(codigoPasaje).getCliente().getDni() == dni) {
			pasajes.remove(codigoPasaje);
		}
	}
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Arrays.hashCode(cantidadAsientos);
		result = prime * result + Arrays.hashCode(precio);
		result = prime * result + Objects.hash(cantidadTripulantes, pasajes);
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Publico other = (Publico) obj;
		return Arrays.equals(cantidadAsientos, other.cantidadAsientos)
				&& cantidadTripulantes == other.cantidadTripulantes && Objects.equals(pasajes, other.pasajes)
				&& Arrays.equals(precio, other.precio);
	}

	@Override
	protected String generarCodigoVuelo(String tamañoDeHashMapVuelos) {
		StringBuilder codigoVuelo = new StringBuilder();
		codigoVuelo.append(super.generarCodigoVuelo(tamañoDeHashMapVuelos)).append("-PUB");
		String resultado = codigoVuelo.toString();
		return resultado;
	}

	// genera detalles de vuelo
	@Override
	protected String generarDetalle() {
		return super.generarDetalle();
	}

	@Override
	public String verDatos() {
		return super.verDatos() + " [Tripulantes]: " + this.cantidadTripulantes;
	}

	@Override
	public String toString() {
		return super.toString()+" [Tripulantes]: " + this.cantidadTripulantes;
	}

}
