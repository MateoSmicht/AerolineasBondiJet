package bondiJet;

public class Privado extends Vuelo {
	private int dniComprador;
	private int [] acompaniantes;
	private int tripulantes;
	private double precio;
	private int cantidadJets;
	public Privado(int dniComprador , int [] acompaniantes, int tripulantes, double precio, int cantidadJets, String identificacion, Aeropuerto aeropuertoSalida, Aeropuerto  aeropuertoDestino, String fecha ) {
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
    	codigoVuelo.append("{").append(numeroString).append("}").append("-PRI");
    	String resultado = codigoVuelo.toString();
    	return resultado;
    	}
	public  int calcularJetsNecesarios(int [] acompaniantes) {
    	int capacidad =15;
    	int cantidadAcompaniantes = acompaniantes.length; // el +1 es para contar al comprador del pasaje
    	int seNecesita=(int) Math.ceil((double) cantidadAcompaniantes+1 / capacidad);
        return seNecesita;
    }
	public double calcularPrecioFinal (int cantidadJetsNecesarios) {
		double precioJets = this.precio;
		double total= (precioJets*cantidadJetsNecesarios);
		return total;
	}

	    public boolean fechaAnterior(String fecha)  {
	    	int diaActual = 5;
		    int mesActual = 11;
		    int anioActual = 2024;
		    
		    // Crear un StringBuilder con la fecha
		    StringBuilder f = new StringBuilder(fecha);
		    
		    // Eliminar los caracteres '-' de la cadena
		    for (int i = 0; i < f.length(); i++) {
		        if (f.charAt(i) == '-') {
		            f.deleteCharAt(i);
		            i--; 
		        }
		    }
		    
		    // Convertir el StringBuilder a String
		    String fechaString = f.toString().trim(); // Asegurarse de eliminar espacios en blanco
		    
		    // Extraer el día, mes y año
		    String dia = fechaString.substring(0, 2);
		    String mes = fechaString.substring(2, 4);
		    String año = fechaString.substring(4, 8);

		    // Convertir a enteros
		    int diaInt = Integer.parseInt(dia);
		    int mesInt = Integer.parseInt(mes);
		    int anioInt = Integer.parseInt(año);

		    // Comparar años
		    if (anioInt > anioActual) {
		        return true;
		    } else if (anioInt < anioActual) {
		        return false;
		    }

		    // Comparar meses si los años son iguales
		    if (mesInt > mesActual) {
		        return true;
		    } else if (mesInt < mesActual) {
		        return false;
		    }

		    // Comparar días si los meses y años son iguales
		    return diaInt > diaActual;
		}
	    public int getCantidadJets() {
	        return cantidadJets;
	    }

	    public double getPrecio() {
	        return precio;
	    }
	    
}

	    
	



