import TDALista.*;
import Exceptions.*;
import ImplementacionesListas.*;
import Iteradores.*;
public class tp3ej3d {
	public static PositionListIt<Integer> intercalar(PositionListIt<Integer> l1, PositionListIt<Integer> l2){
		PositionListIt<Integer> l3 = new LSEconIt<Integer>();
			Iterator<Integer> itL1 = l1.iterator();
			Iterator<Integer> itL2 = l2.iterator();
			try {
				while(itL1.hasNext() && itL2.hasNext()) {
					if(itL1.next()<itL2.next() || itL1.next()==itL2.next() ) {
						l3.addLast(itL1.next());
					}
					else {
						l3.addLast(itL2.next());
					}
				}
					
				while(itL1.hasNext()) {
					l3.addLast(itL1.next());
				}
				
				while(itL2.hasNext()) {
					l3.addLast(itL2.next());
				}
			}
			catch(NoSuchElementException e) {
				System.out.println(e.getMessage());
			}
		return l3;
	}
	
	private static String escribir(PositionListIt<Integer> l) {
		Iterator<Integer> it = l.iterator();
		String cad = "";
		while(it.hasNext()) {
			try {
				cad+=it.next();
				if(it.hasNext())
					cad+=",";
			}
			catch(NoSuchElementException e) {
				System.out.println(e.getMessage());
			}
		}
		return cad;
	}
	
	public static void main(String [] args) {
			PositionListIt<Integer> L1 = new LSEconIt<Integer>();
			PositionListIt<Integer> L2 = new LSEconIt<Integer>();
			L1.addLast(1);
			L1.addLast(4);
			L1.addLast(5);
			L2.addLast(2);
			L2.addLast(3);
			L2.addLast(5);
			L2.addLast(9);
			PositionListIt<Integer> L3 = intercalar(L1,L2);
			System.out.println(escribir(L1));
			System.out.println(escribir(L2));
			System.out.println(escribir(L3));
		
	}
}
