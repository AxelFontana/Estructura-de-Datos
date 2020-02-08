package TDATree;
import TDACola.*;
import Exceptions.*;
import TDAPila.*;
import TDALista.*;
public class tp5ej6 {
	public static <E> String niveles(Tree<E> T){
		String cad = "";
		Position<E> v;
		try {
			Queue<Position<E>> cola = new ArrayQueue<Position<E>>();
			cola.enqueue(T.root());
			cola.enqueue(null);
			while(!cola.isEmpty()) {
				v = cola.dequeue();
				if(v != null) {
					cad+=v.element().toString()+"-";
					for(Position<E> h: T.children(v)) {
						cola.enqueue(h);
					}
				}
				else {
					cad+="\n";
					if(!cola.isEmpty()) {
						cola.enqueue(null);
					}
				}
			}
		}
		catch(InvalidPositionException | EmptyTreeException | EmptyQueueException e) {
			System.out.println(e.getMessage());
		}
		return cad;
	}
	
	public static <E> String nivelesRev(Tree<E> T) {
		String cad = "";
		Position<E> v;
		Stack<Position<E>> lista= new LinkedStack<Position<E>>();
		Queue<Position<E>> cola = new ArrayQueue<Position<E>>();
		try {
			cola.enqueue(T.root());
			cola.enqueue(null);
			while(!cola.isEmpty()) {
				v = cola.dequeue();
				if(v != null) {
					lista.push(v);
					for(Position<E> h: T.children(v)) {
						cola.enqueue(h);
					}
				}
				else {
					lista.push(null);
					if(!cola.isEmpty()) {
						cola.enqueue(null);
					}
				}
			}
			
			while(!lista.isEmpty()) {
				v = lista.pop();
				if(v != null) {
					cad+=v.element().toString()+"-";
				}
				else
					cad+="\n";
			}
		}
			
		catch(InvalidPositionException | EmptyTreeException | EmptyQueueException | EmptyStackException e) {
			System.out.println(e.getMessage());
		}
		return cad;
	}
	
	public static void main(String [] args) {
		Tree<Integer> T = new Arbol<Integer>();
		try {
			T.createRoot(1);
			Position<Integer> raiz = T.root();
			Position<Integer> p1 = T.addLastChild(raiz, 2);
			T.addLastChild(raiz, 3);
			T.addLastChild(raiz, 4);
			
			T.addLastChild(p1, 5);
			T.addLastChild(p1, 6);
			String cad1 = nivelesRev(T);
			System.out.println(cad1);
		}
		catch(InvalidOperationException | InvalidPositionException | EmptyTreeException e) {
			e.printStackTrace();
		}
	}
	
}
