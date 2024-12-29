package bondiJet;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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

	
	protected boolean esFechaPosterior(String fecha) {
		try {
			// Define el formato de la fecha
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

			// Convierte la cadena de fecha a un objeto LocalDate
			LocalDate fechaDada = LocalDate.parse(fecha, formatter);

			// Obtén la fecha actual
			LocalDate fechaVuelo = LocalDate.now();
			//LocalDate fechaSieteDias= fechaVuelo.plusDays(7); //fecha dentro de 7 dias
			// Compara las fechas
			return fechaDada.isAfter(fechaVuelo ); //&& fechaSieteDias.isBefore(fechaSieteDias);
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
	//Para aprovechar el polimorfismo
	protected double precioVuelo(int seccionAsiento) {
		return 0.0;
	}
	protected double totalRecaudado() {
		return 0.0;
	}
	protected void cancelarPasajePublico(int dni, int codigoPasaje) {
		}
	protected boolean elVueloEsNacional() {
		return false;
	}
	
	protected List<String> pasarPasajerosNuevoVuelo(Publico vueloAcancelar, Publico vueloAlternativo,
			Map<String, Vuelo> vuelos){
		return null;
	}
	
	//Comprueba si un vuelo va al destino
	protected boolean vaAlDestino(String destino) {
		if (this.getAeropuertoDestino().getNombre().equals(destino));
		return true;
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
	@Override
	public String toString() {
		return "{<-(["+this.identificacion+"])->:["  + "]" + " [Origen] :" + this.aeropuertoSalida.getNombre()
				+ " [Destino]: " + this.aeropuertoDestino.getNombre() + " [Fecha]: " + this.fecha;
	}

	@Override
	public int hashCode() {
		return Objects.hash(aeropuertoDestino, aeropuertoSalida, fecha, identificacion);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vuelo other = (Vuelo) obj;
		return Objects.equals(aeropuertoDestino, other.aeropuertoDestino)
				&& Objects.equals(aeropuertoSalida, other.aeropuertoSalida) && Objects.equals(fecha, other.fecha)
				&& Objects.equals(identificacion, other.identificacion);
	}
	

}
