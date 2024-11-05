package bondiJet;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Aerolinea {
	public String nombreAerolinea;
	public String cuit;
	public LinkedList<Aeropuerto> aeropuertos;
	private Map<Integer, Cliente> clientes; 
	
	public Aerolinea(String nombreAerolinea, String cuit) {
		this.nombreAerolinea= nombreAerolinea;
		this.cuit= cuit;
		this.aeropuertos = new LinkedList<>();
		this.clientes = new HashMap<>();
	}
	
	public void registrarAeropuerto(String nombreAeropuerto, String pais, String provincia, String direccion) {
		Aeropuerto aeropuertoNuevo= new Aeropuerto(nombreAeropuerto, pais,provincia,direccion);
		boolean existe = false;
		for (Aeropuerto aeropuerto: aeropuertos) {
			existe = existe || aeropuerto.getNombre().equals(aeropuertoNuevo.getNombre());
		}if (existe==true) {
			throw new RuntimeException("el aeropuerto ya existe");
		}else {
		aeropuertos.add(aeropuertoNuevo);
		}
	}
	 public void registrarCliente(int dni, String nombre,String telefono) {
		 
		 if (clientes.containsKey(dni)){
			 throw new RuntimeException("El cliente ya esta cargado " );
		 }else {
			 Cliente clienteNuevo = new Cliente(dni, nombre, telefono);
			 clientes.put(dni, clienteNuevo);
			 System.out.println("registrado " + clienteNuevo.getDni());
		 }
}
	 String registrarVueloPublicoNacional(String origen, String destino, String fecha, int tripulantes, double valorRefrigerio, double[] precios, int[] cantAsientos) {
		 
	 }

}