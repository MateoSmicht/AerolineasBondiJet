package bondiJet;

import java.util.LinkedList;

public class Aeropuerto {
	private String nombre;
	private String pais;
	private String provincia;
	private String direccion;
	
	public Aeropuerto(String nombre, String pais, String provincia, String direccion) {
		this.nombre= nombre;
		this.pais= pais;
		this.provincia= provincia;
		this.direccion= direccion;
	}
	public boolean EstaRegistradoElAeropuerto(LinkedList<Aeropuerto> aeropuertos, String nombreAeropuerto) {
		for(Aeropuerto aeropuerto: aeropuertos) {
			if (aeropuerto.getNombre().equals(nombreAeropuerto)) {
				return true;
			}
		}
		return false;
	}
	public String getNombre() {
		return this.nombre;
	}
	
	public Aeropuerto getAeropuerto(LinkedList<Aeropuerto> aeropuertos, String nombreAeropuerto) {
		for(Aeropuerto aeropuerto: aeropuertos) {
			if (aeropuerto.getNombre().equals(nombreAeropuerto)) {
				return aeropuerto;
			}
		}
		return null;
	}
 
}
