package bondiJet;

public class Privado extends Vuelo {
	private int dniComprador;
	private int[] acompaniantes;
	private int tripulantes;
	private double precio;
	private int cantidadJets;

	public Privado(int dniComprador, int[] acompaniantes, int tripulantes, double precio, int cantidadJets,
			String identificacion, Aeropuerto aeropuertoSalida, Aeropuerto aeropuertoDestino, String fecha) {
		super(identificacion, aeropuertoSalida, aeropuertoDestino, fecha);
		if (this.identificacion == null) {
			throw new RuntimeException("Identificacion es null");
		}
		this.dniComprador = dniComprador;
		if (this.dniComprador == 0) {
			throw new RuntimeException("Sin dni comprador");
		}
		this.acompaniantes = acompaniantes;
		if (this.acompaniantes.length < 0) {
			throw new RuntimeException("Acompaniantes es negativo");
		}
		this.tripulantes = tripulantes;
		if (precio < 0.0) {
			throw new RuntimeException("Precio negativo");
		}
		this.precio = precio;
		this.cantidadJets = cantidadJets;
		if (this.cantidadJets < 0) {
			throw new RuntimeException("no hay jets");
		}
	}

	@Override
	protected String generarCodigoVuelo(String tamañoDeHashMapVuelos) {
		StringBuilder codigoVuelo = new StringBuilder();
		codigoVuelo.append(super.generarCodigoVuelo(tamañoDeHashMapVuelos)).append("-PRI");
		String resultado = codigoVuelo.toString();
		return resultado;
	}

	protected static int calcularJetsNecesarios(int[] acompaniantes) {
		int capacidad = 15;
		int cantidadAcompaniantes = (acompaniantes.length) + 1; // el +1 es para contar al comprador del pasaje
		int seNecesita = (int) Math.ceil((double) cantidadAcompaniantes / capacidad);
		return seNecesita;
	}
	@Override
	protected double totalRecaudado() {
			return precioVuelo(0);
	}
	@Override
	protected double precioVuelo(int seccionAsiento) {
		double total = (this.precio * this.cantidadJets) * 1.30;
		return total; // agregamos el %30 de impuertos
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Privado) {
			Privado other = (Privado) obj;
			return (this.dniComprador == other.getDniComprador() && this.acompaniantes == other.getAcompaniantes()
					&& this.precio == other.getPrecio() && this.tripulantes == other.getTripulantes()
					&& this.cantidadJets == other.getCantidadJets());
		}
		return false;
	}

	public int getDniComprador() {
		return dniComprador;
	}

	public int[] getAcompaniantes() {
		return acompaniantes;
	}

	public int getTripulantes() {
		return tripulantes;
	}

	public int getCantidadJets() {
		return cantidadJets;
	}

	public double getPrecio() {
		return precio;
	}

	// genera detalles de vuelo
	@Override
	protected String generarDetalle() {
		StringBuilder detalle = new StringBuilder();
		detalle.append(super.generarDetalle()).append("PRIVADO (").append(this.cantidadJets).append(")");
		String resultado = detalle.toString();
		return resultado;
	}

	@Override
	public String verDatos() {
		return super.verDatos() + " Precio: " + this.precio + " Cantidad jets:" + this.cantidadJets;
	}

	@Override
	public String toString() {
		return " Comprador: " + this.dniComprador + " Precio: " + this.precio + " Cantidad jets:" + this.cantidadJets;
	}

}
