
public class Terna {
	protected Character elem;
	protected int cant1, cant2;
	public Terna(Character e, int c1, int c2) {
		elem = e;
		cant1 = c1;
		cant2 = c2;
	}
	
	public Character obtenerElem() {
		return elem;
	}
	
	public int ObtenerCant1() {
		return cant1;
	}
	
	public int ObtenerCant2() {
		return cant2;
	}
	
	public String toString() {
		String cad = "";
		cad+="("+elem+","+cant1+","+cant2+")";
		return cad;
	}
}
