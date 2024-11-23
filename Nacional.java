package bondiJet;

import java.util.HashMap;
import java.util.Map;

public class Nacional extends Publico {
	private double refrigerio;

	public Nacional(double refrigerio, String identificacion, Aeropuerto aeropuertoSalida, Aeropuerto aeropuertoDestino,
			String fecha, int[] cantidadAsientos, int cantidadTripulantes, double[] precio) {
		super(identificacion, aeropuertoSalida, aeropuertoDestino, fecha, cantidadAsientos, cantidadTripulantes,
				precio);
		if (this.identificacion == null) {
			throw new RuntimeException("Identificacion es null");
		}
		if (this.precio[0] < 0.0 || this.precio[1] < 0.0 || this.refrigerio < 0.0) {
			throw new RuntimeException("Precio menor a 0.");
		}
		if (!aeropuertoSalida.getPais().equals(aeropuertoDestino.getPais())) {
			throw new RuntimeException("El destino no es nacional.");
		}
		if (this.cantidadAsientos.length != this.precio.length) {
			throw new RuntimeException("No es un vuelo nacional");
		}
		this.refrigerio = refrigerio;
	}
	@Override
	protected Map<Integer, String> asientosDisponibles() {
		// Genera mapa de asientos
		Map<Integer, String> asientosDisponibles = new HashMap<>();
		int asientoNumero = 0;
		
		// Añadir asientos de clase Turista
			for (int i = 0; i < this.cantidadAsientos[0]; i++) {
				if (this.pasajes.containsKey(i)) {
					asientosDisponibles.put(asientoNumero++, "[OCUPADO]");
				} else {
					asientosDisponibles.put(asientoNumero++, "Turista");
				}
			}
			// Añadir asientos de clase Ejecutiva
			for (int i = 0; i < this.cantidadAsientos[1]; i++) {
				if (this.pasajes.containsKey(i)) {
					asientosDisponibles.put(asientoNumero++, "[OCUPADO]");
				} else {
					asientosDisponibles.put(asientoNumero++, "Ejecutiva");
				}
			}
		return asientosDisponibles;
	}

	@Override
	protected double precioVuelo(int seccionAsiento) {
		double costo = (super.precioVuelo(seccionAsiento) + this.refrigerio) * 0.20; // agregamos el %20 de impuertos
		return costo;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Nacional) {
			Nacional other = (Nacional) obj;
			return (this.refrigerio == other.getRefrigerio());
		}
		return false;
	}

	// genera detalles de vuelo
	@Override
	public String generarDetalle() {
		StringBuilder detalle = new StringBuilder();
		detalle.append(super.generarDetalle()).append("NACIONAL");
		String resultado = detalle.toString();
		return resultado;
	}

	@Override
	public String verDatos() {
		return super.verDatos() + " refrigerios;" + this.refrigerio;
	}

	@Override
	public String toString() {
		return "refrigerios;" + this.refrigerio;
	}

	public double getRefrigerio() {
		return refrigerio;
	}

}