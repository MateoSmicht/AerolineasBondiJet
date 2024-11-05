package bondiJet;

public class Privado extends Vuelo {
	private int dniComprador;
	private int [] acompaniantes;
	private int tripulantes;
	private double precio;
	private int cantidadJets;
	public Privado(int dniComprador , int [] acompaniastes, int tripulantes, double precio, int cantidadJets, String identificacion, Aeropuerto aeropuertoSalida, Aeropuerto  aeropuertoDestino, String fecha ) {
	super( identificacion,  aeropuertoSalida,  aeropuertoDestino, fecha);
		this.dniComprador= dniComprador;
		this.acompaniantes= acompaniantes;
		this.tripulantes=tripulantes;
		this.precio=precio;
		this.cantidadJets= cantidadJets;
	}
	public String generarCodigoVueloPriv(int tamañoDeHashMapVuelos) {
    	String numeroString = Integer.toString(tamañoDeHashMapVuelos+1);
    	StringBuilder codigoVuelo = new StringBuilder();
    	codigoVuelo.append("{").append(numeroString).append("}").append("-PRIV");
    	String resultado = codigoVuelo.toString();
    	return resultado;
    	}
	public  int calcularJetsNecesarios(int [] acompaniantes) {
    	int capacidad =15;
    	int cantidadAcompaniantes = acompaniantes.length + 1; // el +1 es para contar al comprador del pasaje
        return (int) Math.ceil((double) cantidadAcompaniantes / capacidad);
    }
	public double calcularPrecioFinal (int cantidadJetsNecesarios) {
		double precioJets = this.precio;
		return (precioJets*cantidadJetsNecesarios);
	}
}

