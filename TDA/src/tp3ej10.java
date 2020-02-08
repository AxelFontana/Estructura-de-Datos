import ImplementacionesListas.*;
import TDALista.*;
import Exceptions.*;
public class tp3ej10 {
	public static <E> boolean Booleano(PositionList<E> L1, PositionList<E> L2) {
		boolean está = true;
		Position<E>  p1 = null, p2 = null;
		try {
			if(L1.size()<=L2.size())
				está = false;
			else {
				if(!L1.isEmpty())
					p1 = L1.first();
				if(!L2.isEmpty())
					p2 = L2.first();
		
				while(p2!=null && p1!=null && está) {
					if(!(p2.element().equals(p2.element())))
						está = false;
					else {
						if(p2 != L2.last())
							p2 = L2.next(p2);
						else
							p2 = null;
						if(p1 != L1.last())
							p1 = L1.next(p1);
						else
							p1 = null;
					}
				}
				
				p2 = L2.last();
				if(L1.size()/2<L2.size())
					está = false;
				while(p1!=null && p2!=null && está) {
					if(!(p1.element().equals(p2.element()))) {
						está = false;
					}
					else {
						if(p2 != L2.first())
							p2 = L2.prev(p2);
						else
							p2 = null;
						if(p1!=L1.last())
							p1 = L1.next(p1);
						else
							p1 = null;
					}
				}
			}
		}
		catch(EmptyListException|BoundaryViolationException|InvalidPositionException e) {
			System.out.println(e.getMessage());
		}
	return está;
	}
	
	public static <E> String escribir(PositionList<E> l) {
		String cad = "";
		Position<E>  p = null;
		try {
			if(!l.isEmpty())
				p = l.first();
			while(p!=null) {
				cad+=p.element()+",";
				if(p != l.last())
					p = l.next(p);
				else
					p = null;
			}
		}
		catch(BoundaryViolationException|EmptyListException|InvalidPositionException e) {
			System.out.println(e.getMessage());
		}
	return cad;
	}
	
	public static void main(String[] args) {
		PositionList<Integer> l1 = new ListaSimplementeEnlazada<Integer>();
		PositionList<Integer> l2 = new ListaSimplementeEnlazada<Integer>();
		l1.addLast(1);
		l1.addLast(2);
		l1.addLast(3);
		l1.addLast(4);
		l1.addLast(4);
		l1.addLast(3);
		l1.addLast(2);
		l1.addLast(1);
		l2.addLast(1);
		l2.addLast(2);
		l2.addLast(3);
		l2.addLast(4);
		System.out.println(escribir(l1));
		System.out.println(escribir(l2));
		boolean es = Booleano(l1,l2);
		System.out.println(es);
	} 
}
