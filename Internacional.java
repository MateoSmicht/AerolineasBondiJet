package bondiJet;

import java.util.HashMap;

public class Internacional extends Publico {
    private String [] escalas;
    private int cantidadRefrigerios;
    private double valorRefrigerio;

    public Internacional (int cantidadRefrigerios, double valorRefrigerio, String [] escalas,String identificacion, Aeropuerto aeropuertoSalida, Aeropuerto aeropuertoDestino, String fecha, int [] cantidadAsientos, int cantidadTripulantes, double[] precio) {
    	super( identificacion,  aeropuertoSalida,  aeropuertoDestino,  fecha,  cantidadAsientos,  cantidadTripulantes, precio);
    	this.cantidadRefrigerios= cantidadRefrigerios;
    	this.valorRefrigerio= valorRefrigerio;
    	this.escalas = escalas;
    }
    public boolean tieneEscala(String [] escalas) {
    	if(escalas.length==0) {
    		return false;
    	}
    	return true;
    }
    public boolean elVueloEsInternacional(HashMap<String, Aeropuerto> aeropuertos,String origen, String destino,int []cantAsientos, double [] precios) {
		if (aeropuertos.get(destino).getPais().equals(aeropuertos.get(origen).getPais())) {
			return false;
		}else {
			 if (cantAsientos.length == 3 && precios.length == 3 ) {
					return true;
			}else {
				return false;
			}
	}	
	}
}
