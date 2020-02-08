import TDALista.*;
import ImplementacionesListas.*;
public class ListaAlumnos {
	protected PositionList<Alumno> lista;
	public ListaAlumnos() {
		lista  = new ListaSimplementeEnlazada<Alumno>();
	}
	
	public void CargarAlumno(Alumno a) {
		lista.addLast(a);
	}
}
