import java.util.ArrayList;
public class AlumnoArrayList {
	protected ArrayList<Materia> cursadas, aprobadas;
	protected String nombre;
	public AlumnoArrayList(String n) {
		nombre = n;
		cursadas = new ArrayList<Materia>();
		aprobadas = new ArrayList<Materia>();
	}
	
	private boolean Pertenece(ArrayList<Materia> l, Materia m) {
		boolean pertenece = false;
		for(int i = 0; i<l.size() && !pertenece; i++) {
			if(l.get(i).equals(m))
				pertenece = true;
		}
		return pertenece;
	}
	
	public void cargarCursadas(Materia m) throws AlreadyChargedException{
		if(Pertence(cursadas, m)) throw new AlreadyChargedException("Materia ya cargada en la lista");
		cursadas.add(cursadas.size(), m);
	}
	
	public void cargarAprobadas(Materia m) throws AlreadyChargedException{
		if(Pertenece(aprobadas, m)) throw new AlreadyChargedException("Materia ya cargada en la lista");
		else {
			if(!Pertenece(cursadas, m)) throw new AlreadyChargedException("Materia no cursada");
			aprobadas.add(aprobadas.size(), m);
		}
	}
}
