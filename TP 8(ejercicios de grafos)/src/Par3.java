import TDALista.*;
public class Par3 {
	
	boolean estap2;
	PositionList<Vertex<String>>camino;
	
	public Par3() {
		estap2=false;
		camino=new ListaDobleEnlaze<Vertex<String>>();
	}
	public void setEstap2(boolean b) {
		estap2=b;
	}
	public boolean getEstap2() {
		return estap2;
	}
	public void setCamino(PositionList<Vertex<String>> camino) {
		this.camino=camino;
	}
	public PositionList<Vertex<String>> getCamino() {
		return camino;
	}
}
