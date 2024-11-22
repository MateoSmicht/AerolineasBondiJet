package bondiJet;

public class Internacional extends Publico {
	private String[] escalas;
	private int cantidadRefrigerios;
	private double valorRefrigerio;

	public Internacional(int cantidadRefrigerios, double valorRefrigerio, String[] escalas, String identificacion,
			Aeropuerto aeropuertoSalida, Aeropuerto aeropuertoDestino, String fecha, int[] cantidadAsientos,
			int cantidadTripulantes, double[] precio) {
		super(identificacion, aeropuertoSalida, aeropuertoDestino, fecha, cantidadAsientos, cantidadTripulantes,
				precio);
		if (this.identificacion == null) {
			throw new RuntimeException("Identificacion es null");
		}
		if (this.precio[0] < 0.0 || this.precio[1] < 0.0 || this.precio[2] < 0.0 || this.valorRefrigerio < 0.0) {
			throw new RuntimeException("Precio menor a 0.");
		}

		if (aeropuertoSalida.getPais().equals(aeropuertoDestino.getPais())) {
			throw new RuntimeException("El destino no es internacional.");
		}
		if (this.cantidadAsientos.length != this.precio.length) {
			throw new RuntimeException("No es un vuelo internacional");
		}
		if (this.cantidadRefrigerios < 0.0) {
			throw new RuntimeException("Cantidad refrierios es negativo.");
		}
		this.cantidadRefrigerios = cantidadRefrigerios;
		this.valorRefrigerio = valorRefrigerio;
		this.escalas = escalas;
	}

	@Override
	protected double valorPasaje(int seccionAsiento) {
		double refrigerio = this.valorRefrigerio * this.cantidadRefrigerios;
		double costo = (super.valorPasaje(seccionAsiento) + refrigerio) * 1.20;// agregamos el %20 de impuertos
		return costo;
	}

	protected boolean tieneEscala(String[] escalas) {
		if (escalas.length == 0) {
			return false;
		}
		return true;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Internacional) {
			Internacional other = (Internacional) obj;
			return (this.cantidadRefrigerios == other.getCantidadRefrigerios()
					&& this.valorRefrigerio == other.getValorRefrigerio() && this.escalas == other.getEscalas());
		}
		return false;
	}

	public int getCantidadRefrigerios() {
		return cantidadRefrigerios;
	}

	public double getValorRefrigerio() {
		return valorRefrigerio;
	}

	public String[] getEscalas() {
		return escalas;
	}

	// genera detalles de vuelo
	@Override
	public String generarDetalle() {
		StringBuilder detalle = new StringBuilder();
		detalle.append(super.generarDetalle()).append("INTERNACIONAL");
		String resultado = detalle.toString();
		return resultado;
	}

	@Override
	public String verDatos() {
		return super.verDatos() + " Cantidad refrigerios:" + this.cantidadRefrigerios + " Valor refrigerio:"
				+ this.valorRefrigerio;
	}

	@Override
	public String toString() {
		return " Cantidad refrigerios:" + this.cantidadRefrigerios + " Valor refrigerio:" + this.valorRefrigerio;

	}
}
