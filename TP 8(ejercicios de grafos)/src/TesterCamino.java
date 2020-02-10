import Excepciones.*;
import TDALista.*;
public class TesterCamino {
	public static void main(String a[]) {
		GrafoDc BA=new GrafoDc();
		
		Vertex<String> BB= BA.AgregarCiudad("Bahia Blanca");
		Vertex<String> PA= BA.AgregarCiudad("Punta Alta");
		Vertex<String> MDP= BA.AgregarCiudad("MDP");
		Vertex<String> CBA= BA.AgregarCiudad("Buenos Aires");
		
		Edge<Integer>BP=BA.AgregarRuta(BB, PA, 20);
		Edge<Integer>BM=BA.AgregarRuta(BB, MDP, 400);
		Edge<Integer>PCBA=BA.AgregarRuta(PA, CBA, 660);
		Edge<Integer>MDPCBA=BA.AgregarRuta(MDP, CBA, 400);
	
		PositionList<Vertex<String>> camino=new ListaDobleEnlaze();
		for(Vertex<String> v:BA.camino("Bahia Blanca","Punta Alta","Buenos Aires")) {
			camino.addLast(v);
		}
		for(Vertex<String> v:camino) {
			System.out.println(v.element());
		}
	}
}
