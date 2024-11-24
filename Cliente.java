package bondiJet;

import java.util.Objects;

public class Cliente {
	private int dni;
	private String nombre;
	private String telefeno;

	public Cliente(int dni, String nombre, String telefono) {
		this.dni = dni;
		this.nombre = nombre;
		this.telefeno = telefono;
		if (this.dni < 0 || this.nombre.length() == 0 || this.telefeno.length() == 0) {
			throw new RuntimeException("Los datos del cliente no son validos.");
		}
	}

	public int getDni() {
		return this.dni;
	}

	public String getNombre() {
		return this.nombre;
	}

	public String getTelefono() {
		return this.telefeno;
	}

	public String verDatos() {
		StringBuilder datos = new StringBuilder();
		datos.append("Cliente:\n");
		datos.append("DNI: ").append(dni).append("\n");
		datos.append("Nombre: ").append(nombre).append("\n");
		datos.append("Teléfono: ").append(this.telefeno).append("\n");
		return datos.toString();
	}

	@Override
	public int hashCode() {
		return Objects.hash(dni, nombre, telefeno);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cliente other = (Cliente) obj;
		return dni == other.dni && Objects.equals(nombre, other.nombre) && Objects.equals(telefeno, other.telefeno);
	}

	@Override
	public String toString() {
		return "Dni Cliente ["+ "nombre=" + nombre + ", telefeno=" + telefeno + "]  ";
	}

}
