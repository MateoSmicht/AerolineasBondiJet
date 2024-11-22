package bondiJet;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public abstract class Vuelo {
	protected String identificacion;
	protected Aeropuerto aeropuertoSalida;
	protected Aeropuerto aeropuertoDestino;
	protected String fecha;

	public Vuelo(String identificacion, Aeropuerto aeropuertoSalida, Aeropuerto aeropuertoDestino, String fecha) {
		this.identificacion = this.generarCodigoVuelo(identificacion);
		if (aeropuertoSalida == null || aeropuertoDestino == null) {
			throw new RuntimeException("Aeropuerto es null.");
		}
		if (aeropuertoSalida.equals(aeropuertoDestino)) {
			throw new RuntimeException(" Aeropuertos origen y destino son iguales");
		}
		this.aeropuertoSalida = aeropuertoSalida;
		this.aeropuertoDestino = aeropuertoDestino;
		if (!esFechaPosterior(fecha)) { // IREP: La fecha tiene que ser posterior a fecha actual.
			throw new RuntimeException("La fecha no es valida.");
		}
		this.fecha = fecha;
	}

	protected List<String> vueloSimilar(Vuelo vuelo, String fecha, String origen, String destino) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate fechaConsulta = LocalDate.parse(fecha, formatter);
		LocalDate fechaMaxima = fechaConsulta.plusDays(7);
		List<String> vuelosSimilares = new ArrayList<>();
		LocalDate fechaVuelo = LocalDate.parse(vuelo.getFecha(), formatter);
		// Verificar que el origen y destino coinciden, y que la fecha está dentro del
		// rango
		if (vuelo.getAeropuertoSalida().getNombre().equals(origen)
				&& vuelo.getAeropuertoDestino().getNombre().equals(destino) && (fechaVuelo.isEqual(fechaConsulta)
						|| (fechaVuelo.isAfter(fechaConsulta) && fechaVuelo.isBefore(fechaMaxima)))) {

			vuelosSimilares.add(vuelo.getIdentificacion());
		}
		return vuelosSimilares;
	}

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

	protected boolean esFechaPosterior(String fecha) {
		try {
			// Define el formato de la fecha
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

			// Convierte la cadena de fecha a un objeto LocalDate
			LocalDate fechaDada = LocalDate.parse(fecha, formatter);

			// Obtén la fecha actual
			LocalDate fechaActual = LocalDate.now();

			// Compara las fechas
			return fechaDada.isAfter(fechaActual);
		} catch (DateTimeParseException e) {
			System.out.println("Formato de fecha inválido: " + fecha);
			return false;
		}
	}

	// Genera codigo de vuelo
	protected String generarCodigoVuelo(String tamañoDeHashMapVuelos) {
		StringBuilder codigoVuelo = new StringBuilder();
		codigoVuelo.append(tamañoDeHashMapVuelos);
		String resultado = codigoVuelo.toString();
		return resultado;
	}

	// genera detalles de vuelo
	protected String generarDetalle() {
		StringBuilder detalle = new StringBuilder();
		detalle.append(this.identificacion).append(" - ").append(this.aeropuertoSalida.getNombre()).append(" - ")
				.append(this.aeropuertoDestino.getNombre()).append(" - ").append(this.fecha).append(" - ");
		String resultado = detalle.toString();
		return resultado;
	}

	// Getters
	public String getIdentificacion() {
		return identificacion;
	}

	public Aeropuerto getAeropuertoSalida() {
		return aeropuertoSalida;
	}

	public Aeropuerto getAeropuertoDestino() {
		return aeropuertoDestino;
	}

	public String getFecha() {
		return fecha;
	}

	public String verDatos() {
		return toString();
	}

	public String toString() {
		return "<-([Identificacion])->:[" + this.identificacion + "]" + " Origen:" + this.aeropuertoSalida.getNombre()
				+ " Destino: " + this.aeropuertoDestino.getNombre() + " Fecha: " + this.fecha;
	}

}
