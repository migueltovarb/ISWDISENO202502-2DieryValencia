public class Estudiante {
	
	private String nombre;
	private int codigo;
	private String programaAcademico;
	public Estudiante() {
		super();
		
	}
	public Estudiante(String nombre, int codigo, String programaAcademico) {
		super();
		this.nombre = nombre;
		this.codigo = codigo;
		this.programaAcademico = programaAcademico;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public String getProgramaAcademico() {
		return programaAcademico;
	}
	public void setProgramaAcademico(String programaAcademico) {
		this.programaAcademico = programaAcademico;
	}
	
	public void registrar() {
		System.out.println("estudiante registrado");
	}
	
	@Override
	public String toString() {
		return "Estudiante [nombre=" + nombre + ", codigo=" + codigo + ", programaAcademico=" + programaAcademico + "]";
	}	
	
	

}
