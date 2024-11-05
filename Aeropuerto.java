package bondiJet;

import java.util.HashMap;


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
	public boolean estaRegistradoElAeropuerto(HashMap<String,Aeropuerto> aeropuertos, String nombreAeropuerto) {
		return aeropuertos.containsKey(nombreAeropuerto);
	}
	
	public String getNombre() {
		return this.nombre;
	}
	public String getPais() {
		return this.pais;
	}
	
	
}
 


