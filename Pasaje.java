package bondiJet;

public class Pasaje {
	private int asientoAsignado;
	private Vuelo vueloAsignado;
	private Cliente cliente;
	private int seccionAsiento; // Representa la sección del asiento (ej. 0 para Turista, 1 para Ejecutiva)
	private boolean ocupadoAsiento;

	// Constructor
	public Pasaje(int asientoAsignado, Vuelo vueloAsignado, Cliente cliente, int seccionAsiento,
			boolean ocupadoAsiento) {
		this.asientoAsignado = asientoAsignado;
		this.vueloAsignado = vueloAsignado;
		this.cliente = cliente;
		this.seccionAsiento = seccionAsiento;
		if (this.vueloAsignado == null || this.asientoAsignado < 0 || this.cliente == null || this.seccionAsiento < 0) {
			throw new RuntimeException("Datos incorrectos del pasaje.");
		}
		this.ocupadoAsiento = ocupadoAsiento;
	}

	protected String generarInformacionCambioVuelo(String codigoVuelo) {
		StringBuilder informacionPasaje = new StringBuilder();
		informacionPasaje.append(cliente.getDni() + " - ").append(cliente.getNombre() + " - ")
				.append(cliente.getTelefono() + " - ").append(codigoVuelo);
		String resultadoInformacion = informacionPasaje.toString();
		return resultadoInformacion;
	}

	// Getters
	public Cliente getCliente() {
		return cliente;
	}

	public boolean getOcupadoAsiento() {
		return ocupadoAsiento;
	}

	public int getAsientoAsignado() {
		return asientoAsignado;
	}

	public Vuelo getVueloAsignado() {
		return vueloAsignado;
	}

	public int getSeccionASiento() {
		return seccionAsiento;
	}

	public String verDatos() {
		StringBuilder datos = new StringBuilder();
		datos.append("Pasaje:\n");
		datos.append("Asiento Asignado: ").append(asientoAsignado).append("\n");
		datos.append("Código de Vuelo: ").append(getVueloAsignado()).append("\n");
		datos.append("DNI Cliente: ").append(cliente.getDni()).append("\n");
		return datos.toString();
	}

}