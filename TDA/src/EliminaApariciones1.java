import Exceptions.*;
import TDALista.*;
import ImplementacionesListas.*;
public class EliminaApariciones1{
	public static void elimina(Integer x, PositionList<Integer> l){
		Position<Integer> p;
		if(!l.isEmpty()) {
			try {
				p = l.first();
				while(p!=null) {
					if(p.element().equals(x))
						l.remove(p);
					if(p != l.last())
						p = l.next(p);
					else
						p = null;
				}
			}
			catch(BoundaryViolationException|InvalidPositionException|EmptyListException e) {
				System.out.println(e.getMessage());
			}
		}
	}
	
	public static String escribir(PositionList<Integer> l) {
		String cad = "";
			if(!l.isEmpty()) {
				try {
					Position<Integer> p = l.first();
					while(p!=null) {
						cad+=p.element()+" ";
						if(p != l.last())
							p = l.next(p);
						else
							p = null;
								
					}
				}
				catch(EmptyListException|BoundaryViolationException|InvalidPositionException e) {
					System.out.println(e.getMessage());
				}
			}
		return cad;
	}

	public static void main(String [] args) {
		PositionList<Integer> l = new ListaSimplementeEnlazada<Integer>();
		l.addFirst(5);
		l.addLast(3);
		l.addLast(8);
		l.addLast(2);
		l.addLast(3);
		System.out.println(escribir(l));
		elimina(3, l);
		System.out.println(escribir(l));
	}
}
