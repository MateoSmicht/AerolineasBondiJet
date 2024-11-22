package bondiJet;

import java.util.HashMap;
import java.util.Objects;

public class Aeropuerto {
	private String nombre;
	private String pais;
	private String provincia;
	private String direccion;

	public Aeropuerto(String nombre, String pais, String provincia, String direccion) {
		this.nombre = nombre;
		this.pais = pais;
		this.provincia = provincia;
		this.direccion = direccion;
		if (this.nombre.length() == 0 || this.pais.length() == 0 || this.provincia.length() == 0
				|| this.direccion.length() == 0) {
			throw new RuntimeException("Datos del aeropuerto no validos");
		}
	}

	public boolean estaRegistradoElAeropuerto(HashMap<String, Aeropuerto> aeropuertos, String nombreAeropuerto) {
		return aeropuertos.containsKey(nombreAeropuerto);
	}

	@Override
	public int hashCode() {
		return Objects.hash(direccion, nombre, pais, provincia);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Aeropuerto other = (Aeropuerto) obj;
		return Objects.equals(direccion, other.direccion) && Objects.equals(nombre, other.nombre)
				&& Objects.equals(pais, other.pais) && Objects.equals(provincia, other.provincia);
	}

	@Override
	public String toString() {
		return "Aeropuerto [nombre=" + nombre + ", pais=" + pais + ", provincia=" + provincia + ", direccion="
				+ direccion + "]";
	}

	public String getNombre() {
		return this.nombre;
	}

	public String getPais() {
		return this.pais;
	}

}
