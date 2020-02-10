import TDALista.ListaDobleEnlaze;
import TDALista.PositionList;
public class Par4 {
	
	boolean encontre;
	PositionList<Vertex<Character>>camino;
	
	public Par4() {
		encontre=false;
		camino=new ListaDobleEnlaze<Vertex<Character>>();
	}
	public void setencontre(boolean b) {
		encontre=b;
	}
	public boolean getencontre() {
		return encontre;
	}
	public void setCamino(PositionList<Vertex<Character>> camino) {
		this.camino=camino;
	}
	public PositionList<Vertex<Character>> getCamino() {
		return camino;
	}
}
