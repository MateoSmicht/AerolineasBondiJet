package bondiJet;

import java.util.HashMap;

public class Nacional extends Publico {
	private double refrigerio;
	
	public Nacional (double refrigerio, String identificacion, Aeropuerto aeropuertoSalida, Aeropuerto aeropuertoDestino, String fecha, int [] cantidadAsientos, int cantidadTripulantes, double[] precio) {
	super( identificacion,  aeropuertoSalida,  aeropuertoDestino,  fecha,  cantidadAsientos,  cantidadTripulantes, precio);
	this.refrigerio= refrigerio;
}
	@Override 
	public String generarCodigoVuelo(int tamañoDeHashMapVuelos) {
		String numeroString = Integer.toString(tamañoDeHashMapVuelos+1);
    	StringBuilder codigoVuelo = new StringBuilder();
    	codigoVuelo.append("{").append(numeroString).append("}").append("-PUB");
    	String resultado = codigoVuelo.toString();
    	return resultado;
    	}
	public boolean elVueloEsNacional(HashMap<String, Aeropuerto> aeropuertos,String origen, String destino) {
		if (aeropuertos.get(destino).getPais().equals("Argentina") && aeropuertos.get(origen).getPais().equals("Argentina")) {
			return true;
		}else {
			return false;
		}
	}
}