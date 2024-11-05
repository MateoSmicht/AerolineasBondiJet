package bondiJet;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Aerolinea {
	public String nombreAerolinea;
	public String cuit;
	public LinkedList<Aeropuerto> aeropuertos;
	private Aeropuerto aeropuerto;
	private Publico publico;
	private Map<Integer, Cliente> clientes;
	private Map<String, Nacional> vuelosPublicosNacional;
	
	public Aerolinea(String nombreAerolinea, String cuit) {
		this.nombreAerolinea= nombreAerolinea;
		this.cuit= cuit;
		this.aeropuertos = new LinkedList<>();
		this.clientes = new HashMap<>();
		this.vuelosPublicosNacional = new HashMap<>();
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
		 }
}
	 public String registrarVueloPublicoNacional(String origen, String destino, String fecha, int tripulantes, double valorRefrigerio, double[] precios, int[] cantAsientos) {
		 
				int cantidadDeVuelosPublicos= vuelosPublicosNacional.size();
				String codigoVuelo=publico.GenerarCodigoVuelo(cantidadDeVuelosPublicos+1, "-PUB");
				Nacional nuevoVueloPubNacional= new Nacional(valorRefrigerio,codigoVuelo,aeropuerto.getAeropuerto(aeropuertos, origen),
						aeropuerto.getAeropuerto(aeropuertos, destino), fecha, cantAsientos, tripulantes, precios );
				System.out.println(codigoVuelo);
				if(vuelosPublicosNacional.containsKey(codigoVuelo)) {
					throw new RuntimeException("el vuelo ya esta registrado");
				}else {
				vuelosPublicosNacional.put(codigoVuelo, nuevoVueloPubNacional);
				return codigoVuelo;
				}
			}	
	
}
