import TDALista.ListaDobleEnlaze;
import TDALista.PositionList;
public class Par2<E> {
	PositionList<PositionList<Character>> Camino;
	int peso;
	public Par2() {
		Camino=new ListaDobleEnlaze<PositionList<Character>>();	
		peso=999999;
	}
	public int getPeso() {
		return peso;
	}
	public void setPeso(int p) {
		peso=p;
	}
	public PositionList<PositionList<Character>> getCamino(){
		return Camino;
	}
	public void setCamino(PositionList<PositionList<Character>>ca) {
		Camino=ca;
	}
	
}
