import TDALista.*;
import Exceptions.*;
import ImplementacionesListas.*;
public class tp3ej9 {
	public static PositionList<Terna> GenerarListaResumen(PositionList<Character> L1, PositionList<Character> L2) {
		PositionList<Character> aux1 = new ListaSimplementeEnlazada<Character>();
		PositionList<Character> aux2 = new ListaSimplementeEnlazada<Character>();
		PositionList<Terna> R = new ListaSimplementeEnlazada<Terna>();
		try {
			Position<Character> p1 = L1.first();
			Position<Character> p2 = L2.first();
			while(p1 != null) {
				aux1.addLast(p1.element());
				if(L1.next(p1)==null || L1.next(p1).equals(L1.first()))
					p1 = null;
				else
					p1 = L1.next(p1);
			}
		
			while (p2 != null) {
				aux2.addLast(p2.element());
				if(L2.next(p2) == null || L1.next(p2).equals(L1.first()))
					p2 = null;
				else
					p2 = L2.next(p2);
			}
		}
		catch(EmptyListException|InvalidPositionException|BoundaryViolationException e) {
			System.out.println(e.getMessage());
		}
		
		while(!aux1.isEmpty() && !aux2.isEmpty()) {
			try {
				Character s = aux1.first().element();
				int cant1 = Apariciones(aux1, s);
				int cant2 = Apariciones(aux2, s);
				Borrar(aux1, s);
				Borrar(aux2, s);
				R.addLast(new Terna(s,cant1,cant2));
			}
			catch(EmptyListException e) {
				System.out.println(e.getMessage());
			}
		}
		
		while(!aux2.isEmpty()) {
			try {
				Character s = aux2.first().element();
				int cant = Apariciones(aux2, s);
				Borrar(aux2, s);
				R.addLast(new Terna(s,0,cant));
			}
			catch(EmptyListException e) {
				System.out.println(e.getMessage());
			}
		}
	return R;
		
	}
	
	private static int Apariciones(PositionList<Character> l, Character s) {
		int cant = 0;
		try {
			Position<Character> p = l.first();
			while(p!=null) {
				if(p.element().equals(s))
					cant++;
				if(l.next(p)==null || l.next(p).equals(l.first()))
					p = null;
				else
					p = l.next(p);
			}
		}
		catch(EmptyListException|BoundaryViolationException|InvalidPositionException e) {
			System.out.println(e.getMessage());
		}
		return cant;
	}
	
	private static void Borrar(PositionList<Character> l, Character s) {
		try {
			Position<Character> p = l.first();
			while(p!=null) {
				if(p.element().equals(s))
					l.remove(p);
				if(l.next(p) == null || l.next(p).equals(l.first()))
					p = null;
				else
					p = l.next(p);
			}
		}
		catch(EmptyListException|BoundaryViolationException|InvalidPositionException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static String escribir(PositionList<Character> l) {
		String cad = "";
		try {
			Position<Character> p = l.first();
			while(p!=null) {
				cad+=p.element()+",";
				if(p != l.last())
					p = l.next(p);
				else
					p = null;
			}
		}
		catch(EmptyListException|BoundaryViolationException|InvalidPositionException e) {
			System.out.println(e.getMessage());
		}
		
		return cad;
	}
	
	public static String escribirR(PositionList<Terna> l) {
		String cad = "";
		try {
			Position<Terna> p = l.first();
			while(p!=null) {
				cad+=p.element().toString()+" ";
				if(l.next(p) == null)
					p=null;
				else
					p = l.next(p);
			}
		}
		catch(BoundaryViolationException|EmptyListException|InvalidPositionException e) {
			System.out.println(e.getMessage());
		}
		
		return cad;
	}
	
	public static void main(String [] args) {
		PositionList<Character> L1 = new ListaSimplementeEnlazada<Character>();
		PositionList<Character> L2 = new ListaSimplementeEnlazada<Character>();
		PositionList<Terna> R = new ListaSimplementeEnlazada<Terna>();
		L1.addLast('a');
		L1.addLast('b');
		L1.addLast('a');
		L1.addLast('c');
		L1.addLast('d');
		L1.addLast('b');
		L2.addLast('c');
		L2.addLast('a');
		L2.addLast('b');
		L2.addLast('f');
		L2.addLast('c');
		System.out.println(escribir(L1));
		System.out.println(escribir(L2));
		R = GenerarListaResumen(L1,L2);
		System.out.println(escribirR(R));
	}
}
