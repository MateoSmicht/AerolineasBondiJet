package bondiJet;

import java.util.LinkedList;

public class Aerolinea {
	public String nombreAerolinea;
	public String cuit;
	public LinkedList<Aeropuerto> aeropuertos;
	
	public Aerolinea(String nombreAerolinea, String cuit) {
		this.nombreAerolinea= nombreAerolinea;
		this.cuit= cuit;
		this.aeropuertos = new LinkedList<>();
	}
	
	public void registrarAeropuerto(String nombreAeropuerto, String pais, String provincia, String direccion) {
		Aeropuerto aeropuertoNuevo= new Aeropuerto(nombreAeropuerto, pais,provincia,direccion);
		aeropuertos.add(aeropuertoNuevo);
	}
	
}
