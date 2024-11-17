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
    @Override
	public double valorPasaje(int seccionAsiento) {
    	double refrigerio=this.valorRefrigerio * this.cantidadRefrigerios;
		double costo= (super.valorPasaje(seccionAsiento)+refrigerio) * 0.20;//agregamos el %20 de impuertos
    	return costo;
    }
   
    public boolean tieneEscala(String [] escalas) {
    	if(escalas.length==0) {
    		return false;
    	}
    	return true;
    }
    @Override
    public boolean esUnVueloValido( ) {
    	if(this.aeropuertoDestino.equals(null) || this.aeropuertoSalida.equals(null))
			return false;
		if (this.aeropuertoSalida.getPais().equals(this.getAeropuertoDestino().getPais())) {
			return false;
		}else {
			if (this.cantidadAsientos.length == 3 && this.precio.length == 3 ) {
					return true;
			}else {
				return false;
			}
	}	
	}
    public int getCantidadRefrigerios() {
        return cantidadRefrigerios;
    }

    public double getValorRefrigerio() {
        return valorRefrigerio;
    }
    @Override
    public String verDatos() {
    	return super.verDatos() +", cantidad refrigerios:"+ this.cantidadRefrigerios+ ", valor refrigerio:"+this.valorRefrigerio+ ", escalas:" + this.escalas;
    }
}
