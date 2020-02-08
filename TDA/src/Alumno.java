import TDALista.*;
import ImplementacionesListas.*;
public class Alumno {
	protected PositionList<Materia> cursadas, aprobadas;
	protected String nombre;
	public Alumno(String n) {
		nombre = n;
		cursadas = new ListaSimplementeEnlazada<Materia>();
		aprobadas = new ListaSimplementeEnlazada<Materia>();
	}
	
	public void cargarCursada(Materia m) throws AlreadyChargedException{
		if(Pertenece(cursadas, m)) throw new AlreadyChargedException("Materia ya cargada en la lista");
		cursadas.addLast(m);
	}
	
	public void cargarAprobada(Materia m) throws AlreadyChargedException {
		if(Pertenece(aprobadas, m)) throw new AlreadyChargedException("Materia ya cargada en la lista");
		else {
			if(!Pertenece(cursadas, m)) throw new AlredyChargedException("Materia no cursada");
			aprobadas.addLast(m);				
		}
	}
	
	private boolean Pertenece(PositionList<Materia> l, Materia m) {
		boolean pertenece = false;
			Position<Materia> p = l.first();
			while(!pertenece && p!=null) {
				if(p.element() == m)
					pertenece = true;
				else {
					if(p == l.last())
						p = null;
					else
						p = p.getNext();
				}
			}
		return pertenece;
	}
}
