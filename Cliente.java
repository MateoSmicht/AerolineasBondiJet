package bondiJet;

public class Cliente {
	private int dni;
	private String nombre;
	private String telefeno;
	
	public Cliente(int dni, String nombre, String telefono) {
		this.dni= dni;
		this.nombre= nombre;
		this.telefeno= telefono;
	}
	
	public int getDni() {
		return this.dni;
	}
}
