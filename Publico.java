package bondiJet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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

	protected double recaudacion_pasajes_a_destino(String destino) {
		double recaudacion = 0.0;
		if (this.getAeropuertoDestino().getNombre().equals(destino)) {
			for (Pasaje pasaje : pasajes.values()) {
				recaudacion = recaudacion + this.valorPasaje(pasaje.getSeccionASiento());
			}
		}
		return recaudacion;
	}

	protected double valorPasaje(int seccionAsiento) {
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
	protected boolean elVueloEsNacional(Vuelo v) {
		Publico vuelo = (Publico) v;
		if (vuelo.aeropuertoDestino.getPais().equals("Argentina")
				&& vuelo.aeropuertoSalida.getPais().equals("Argentina")) {
			if (vuelo.cantidadAsientos.length == 2 && vuelo.precio.length == 2) {
				return true;
			}
		}
		return false;
	}

	protected Map<Integer, String> asientosDisponiblesPublico(Vuelo v) {
		Publico vuelo = (Publico) v;
		// Genera mapa de asientos
		Map<Integer, String> asientosDisponibles = new HashMap<>();
		int asientoNumero = 0;
		// NACIONAL
		if (vuelo.elVueloEsNacional(vuelo)) {
			// Añadir asientos de clase Turista
			for (int i = 0; i < vuelo.cantidadAsientos[0]; i++) {
				if (vuelo.pasajes.containsKey(i)) {
					asientosDisponibles.put(asientoNumero++, "[OCUPADO]");
				} else {
					asientosDisponibles.put(asientoNumero++, "Turista");
				}
			}
			// Añadir asientos de clase Ejecutiva
			for (int i = 0; i < vuelo.cantidadAsientos[1]; i++) {
				if (vuelo.pasajes.containsKey(i)) {
					asientosDisponibles.put(asientoNumero++, "[OCUPADO]");
				} else {
					asientosDisponibles.put(asientoNumero++, "Ejecutiva");
				}
			}
			// INTERNACIONAL
		} else {
			// Añadir asientos de clase Turista
			for (int i = 0; i < vuelo.cantidadAsientos[0]; i++) {
				if (vuelo.pasajes.containsKey(i)) {
					asientosDisponibles.put(asientoNumero++, "[OCUPADO]");
				} else {
					asientosDisponibles.put(asientoNumero++, "Turista");
				}
			}

			// Añadir asientos de clase Ejecutiva
			for (int i = 0; i < vuelo.cantidadAsientos[1]; i++) {
				if (vuelo.pasajes.containsKey(i)) {
					asientosDisponibles.put(asientoNumero++, "[OCUPADO]");
				} else {
					asientosDisponibles.put(asientoNumero++, "Ejecutiva");
				}
			}
			// Añadir asientos de primera clase
			for (int i = 0; i < vuelo.cantidadAsientos[2]; i++) {
				if (vuelo.pasajes.containsKey(i)) {
					asientosDisponibles.put(asientoNumero++, "[OCUPADO]");
				} else {
					asientosDisponibles.put(asientoNumero++, "Primera clase");
				}
			}
		}
		return asientosDisponibles;
	}

	protected List<String> pasarPasajerosNuevoVuelo(Publico vueloAcancelar, Publico vueloAlternativo,
			Map<String, Publico> vuelosPublicos) {
		List<String> registros = new ArrayList<>();
		Publico vueloCancelado = (Publico) vueloAcancelar;
		// Recorre los pasajes de el vuelo a cancelar
		for (Pasaje pasaje : vueloCancelado.pasajes.values()) {
			// manda a ubicar los pasajes del vuelo cancelado, al nuevo vuelo
			registros.add(vueloCancelado.ubicarPasajeroNuevoVuelo(pasaje, vueloCancelado, vueloAlternativo));
		}
		return registros;
	}

	private String ubicarPasajeroNuevoVuelo(Pasaje pasajero, Publico cancelado, Publico nuevoVuelo) {
		Map<Integer, String> asientosDisponibles = nuevoVuelo.asientosDisponiblesPublico(nuevoVuelo);
		String resultado = "";
		int contador = 0; // Contador de asientos
		// Si el asiento no esta ocupado vendemos un nuevo pasaje al vuelo nuevo.
		if (!asientosDisponibles.get(pasajero.getAsientoAsignado()).equals("[OCUPADO]")) {
			nuevoVuelo.venderPasajePublico(pasajero.getCliente(), nuevoVuelo, pasajero.getAsientoAsignado(),
					pasajero.getOcupadoAsiento());
			cancelado.pasajes.remove(pasajero.getAsientoAsignado());
			// Genera mensaje de informacion del cambio de vuelo
			resultado = nuevoVuelo.pasajes.get(pasajero.getAsientoAsignado())
					.generarInformacionCambioVuelo(nuevoVuelo.getIdentificacion());
			// El asiento original del pasaje se encuentra ocupado.
		} else {
			for (String asiento : asientosDisponibles.values()) {// Recorre los asientos hasta encontrar uno que no este
																	// ocupado.
				contador += 1;
				if (contador > pasajero.getAsientoAsignado()) { // Recorre hasta superar el numero del asiento del
																// pasaje.
					if (!asiento.equals("[OCUPADO]")) {
						nuevoVuelo.venderPasajePublico(pasajero.getCliente(), nuevoVuelo, contador,
								pasajero.getOcupadoAsiento());
						cancelado.pasajes.remove(pasajero.getAsientoAsignado());
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

	protected List<Publico> vuelosSimelares_vueloCancelado(Map<String, Publico> vuelosPublicos) {
		// Devuelve lista de vuelos similares.
		List<Publico> vuelosSimilares = new ArrayList<>();
		// Recorrer todos los vuelos
		for (Publico v : vuelosPublicos.values()) {
			if (!v.getIdentificacion().equals(this.getIdentificacion()) && !v.equals(null)) {
				if (v.aeropuertoDestino.getNombre().equals(this.getAeropuertoDestino().getNombre())
						&& v.aeropuertoSalida.getNombre().equals(this.getAeropuertoSalida().getNombre())) {
					vuelosSimilares.add(v);
				}
			}
		}
		return vuelosSimilares;
	}

	protected int venderPasajePublico(Cliente cliente, Vuelo codVuelo, int nroAsiento, boolean ocupado) {
		Publico vuelo = (Publico) codVuelo;
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

	protected void cancelarPasajePublico(int dni, int codigoPasaje, Vuelo v) {
		Publico vuelo = (Publico) v;
		if (vuelo.pasajes.get(codigoPasaje).getCliente().getDni() == dni) {
			pasajes.remove(codigoPasaje);
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
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
		return super.verDatos() + "<-([Identificacion])->:[" + this.identificacion + "]" + " Origen:"
				+ this.aeropuertoSalida.getNombre() + " Destino: " + this.aeropuertoDestino.getNombre() + " Fecha: "
				+ this.fecha + " Precio: " + this.precio + " Tripulantes: " + this.cantidadTripulantes;
	}

	@Override
	public String toString() {
		return " Precio: " + this.precio;
	}

}
