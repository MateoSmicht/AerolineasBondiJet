package bondiJet;

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
	protected double valorPasaje(int seccionAsiento) {
		double costo = (super.valorPasaje(seccionAsiento) + this.refrigerio) * 0.20; // agregamos el %20 de impuertos
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