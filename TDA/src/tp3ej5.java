import ImplementacionesListas.*;
import Exceptions.*;
import TDALista.*;
public class tp3ej5 {
	public static <E> void invertir(PositionList<E> l) {
		if(l.size()>2 || l.size() == 2) {
			try {
			Position<E> Paux = l.first();
			Position<E> Pcola = l.last();
			boolean cortar = false;
			while(!cortar && Paux != Pcola) {
				E aux = Paux.element();
				l.set(Paux, Pcola.element());
				l.set(Pcola, aux);
				Paux = l.next(Paux);
				if(Paux == Pcola)
					cortar = true;
				else
					Pcola = l.prev(Pcola);
			}
			}
			catch(BoundaryViolationException|InvalidPositionException|EmptyListException e) {
				System.out.println(e.getMessage());
			}
		}
	}
	
	public static <E> void invertirRec(PositionList<E> l) {
		try {
			invertirAux(l,l.first(),l.last());
		}
		catch(EmptyListException e) {
			System.out.println(e.getMessage());
		}
	}
	
	private static <E> void invertirAux(PositionList<E> lista, Position<E> Pf, Position<E> Pc) {
		if(lista.size() == 2) {
			try {
			E aux = Pf.element();
			lista.set(Pf, Pc.element());
			lista.set(Pc, aux);
			}
			catch(InvalidPositionException e) {
				System.out.println(e.getMessage());
			}
		}
		else {
			if(lista.size()>2) {
				try {
					invertirAux(lista, lista.next(Pf), lista.prev(Pc));
					E aux = Pf.element();
					lista.set(Pf, Pc.element());
					lista.set(Pc, aux);
					}
				catch(BoundaryViolationException|InvalidPositionException e) {
					System.out.println(e.getMessage());
				}
			}
		}
	}
	
	public static <E> String escribir(PositionList<E> l) {
		String cad = "";
			if(!l.isEmpty()) {
				try {
					Position<E> p = l.first();
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
		PositionList<Integer> L1 = new ListaSimplementeEnlazada<Integer>();
		PositionList<Integer> L2 = new ListaSimplementeEnlazada<Integer>();
		L1.addLast(1);
		L1.addLast(4);
		L1.addLast(5);
		L2.addLast(2);
		L2.addLast(3);
		L2.addLast(5);
		L2.addLast(9);
		System.out.println(escribir(L1));
		System.out.println(escribir(L2));
		invertir(L1);
		invertir(L2);
		System.out.println(escribir(L1));
		System.out.println(escribir(L2));
	}
}
