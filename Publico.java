package bondiJet;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Publico extends Vuelo {
	protected int[] cantidadAsientos;
	protected int cantidadTripulantes;
	protected Map<Integer, Pasaje> pasajes;
	protected double[] precio;

	public Publico(int identificacion, Aeropuerto aeropuertoSalida, Aeropuerto aeropuertoDestino, String fecha,
			int[] cantidadAsientos, int cantidadTripulantes, double[] precio) {
		super(identificacion, aeropuertoSalida, aeropuertoDestino, fecha);
		this.cantidadAsientos = cantidadAsientos;
		this.cantidadTripulantes = cantidadTripulantes;
		this.precio = precio;
		this.pasajes = new HashMap<>();
	}

	@Override
	public boolean esUnVueloValido() {
		if (this.aeropuertoDestino.equals(null)
				|| this.aeropuertoSalida.equals(null)) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public Publico crear() {
		return new Publico(this.identificacion, this.aeropuertoSalida, this.aeropuertoDestino, this.fecha,
				this.cantidadAsientos, this.cantidadTripulantes, this.precio);
	}

	public double valorPasaje(int seccionAsiento) {
		double precioSeccion = precio[seccionAsiento]; // (ej. 0 para Turista, 1 para Ejecutiva)
		return precioSeccion;
	}

	public int sumarAsientos(int[] asientos) {
		int suma = 0;
		for (int asiento : asientos) {
			suma += asiento;
		}
		return suma;
	}

	public int determinarSeccion(int[] cantidadAsientos, int nroAsiento) {
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

	public boolean elVueloEsNacional(Vuelo v) {
		Publico vuelo = (Publico) v;
		if (vuelo.aeropuertoDestino.getPais().equals("Argentina")
				&& vuelo.aeropuertoSalida.getPais().equals("Argentina")) {
			if (vuelo.cantidadAsientos.length == 2 && vuelo.precio.length == 2) {
				return true;
			}
		}
		return false;
	}

	public Map<Integer, String> asientosDisponiblesPublico(Vuelo v) {
		// Buscar el vuelo en el mapa de vuelos públicos nacionales
		Publico vuelo = (Publico) v;
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

	public Vuelo vueloAlternativoPublico(Vuelo vueloAcancelar, Map<String, Vuelo> publicos) {
		// Encontrar un vuelo alternativo similar (misma ruta y fecha posterior)
		Publico vueloAlternativo = null;
		Publico vueloComp = (Publico) vueloAcancelar;
		LocalDate fechaVueloACancelar = LocalDate.parse(vueloComp.getFecha(),
				DateTimeFormatter.ofPattern("dd/MM/yyyy"));

		for (Vuelo vuelo : publicos.values()) {
			if (!(vuelo.getIdentificacion()==(vueloComp.identificacion))
					&& vuelo.getAeropuertoSalida().getNombre().equals(vueloComp.getAeropuertoSalida().getNombre())
					&& vuelo.getAeropuertoDestino().getNombre().equals(vueloComp.getAeropuertoDestino().getNombre())) {

				LocalDate fechaVueloAlternativo = LocalDate.parse(vuelo.getFecha(),
						DateTimeFormatter.ofPattern("dd/MM/yyyy"));
				if (fechaVueloAlternativo.isAfter(fechaVueloACancelar)) {
					return vueloAlternativo = (Publico) vuelo;
				}
			}
		}
		return vueloAlternativo;
	}

	public int venderPasajePublico(Cliente cliente, Vuelo codVuelo, int nroAsiento, boolean ocupado) {

		// Buscar el vuelo en el mapa de vuelos públicos nacionales y internacionales
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

		// Generar y devolver un código único de pasaje (puede ser el número del asiento
		// o un contador)
		return codigoPasaje;
	}

	public Vuelo vueloDelPasaje(int codigoPasaje) {
		if (pasajes.containsKey(codigoPasaje)) {
			return pasajes.get(codigoPasaje).getVueloAsignado();
		} else {
			throw new RuntimeException("El pasaje no existe.");
		}
	}

	public void cancelarPasajePublico(int dni, int codigoPasaje, Vuelo v) {
		Publico vuelo = (Publico) v;
		if (vuelo.pasajes.get(codigoPasaje).getCliente().getDni() == dni) {
			pasajes.remove(codigoPasaje);
		}
	}

	@Override
	public String generarCodigoVuelo(int tamañoDeHashMapVuelos) {
		StringBuilder codigoVuelo = new StringBuilder();
		codigoVuelo.append(super.generarCodigoVuelo(tamañoDeHashMapVuelos)).append("-PUB");
		String resultado = codigoVuelo.toString();
		return resultado;
	}

	@Override
	public String verDatos() {
		return super.verDatos() + toString();
	}

	@Override
	public String toString() {
		return "Cantidad Asientos: " + this.cantidadAsientos + ", Cantidad tripulantes:" + this.cantidadTripulantes
				+ ", precio:" + this.precio;
	}

}
