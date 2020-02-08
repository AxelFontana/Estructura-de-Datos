package TDACola;
import TDAPila.*;
import Exceptions.*;
public class parcial {
	public static void invYElim(Queue<Stack<Integer>> Q, Integer X) {
		Stack<Stack<Integer>> pilaAux1 = new LinkedStack<Stack<Integer>>();
		Stack<Integer> pilaAux2 = new LinkedStack<Integer>();
		try {
			while(!Q.isEmpty()) {
				pilaAux1.push(Q.dequeue());
			}
			while(!pilaAux1.isEmpty()) {
				Stack<Integer> p1 = pilaAux1.pop();
				while(!p1.isEmpty()) {
					Integer i = p1.pop();
					if(i != X)
						pilaAux2.push(i);
				}
				while(!pilaAux2.isEmpty()) {
					p1.push(pilaAux2.pop());
				}
				
				Q.enqueue(p1);
			}
		}
		catch(EmptyQueueException|EmptyStackException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static void main(String [] args) {
		Stack<Integer> p1 = new LinkedStack<Integer>();
		p1.push(1);
		p1.push(2);
		p1.push(3);
		p1.push(4);
		Stack<Integer> p2 = new LinkedStack<Integer>();
		p2.push(5);
		p2.push(4);
		p2.push(7);
		p2.push(8);
		Stack<Integer> p3 = new LinkedStack<Integer>();
		p3.push(9);
		p3.push(10);
		p3.push(11);
		p3.push(4);
		Stack<Integer> p4 = new LinkedStack<Integer>();
		p4.push(13);
		p4.push(14);
		p4.push(15);
		p4.push(16);
		Queue<Stack<Integer>> cola = new ArrayQueue<Stack<Integer>>();
		cola.enqueue(p1);
		cola.enqueue(p2);
		cola.enqueue(p3);
		cola.enqueue(p4);
		invYElim(cola,4);
		System.out.println(escribir(cola));
	}
	
	private static String escribir(Queue<Stack<Integer>> Q) {
		String cad = "";
		Queue<Stack<Integer>> colaAux = new ArrayQueue<Stack<Integer>>();
		Stack<Integer> pilaAux = new LinkedStack<Integer>();
		Stack<Integer> p2 = new LinkedStack<Integer>();
		try {
			while(!Q.isEmpty()) {
				colaAux.enqueue(Q.dequeue());
			}
			while(!colaAux.isEmpty()) {
				Q.enqueue(colaAux.front());
				while(!colaAux.front().isEmpty()) {
					cad+=" "+colaAux.front().pop();
				}
				cad+="-";
				colaAux.dequeue();
			}
		}
		catch(EmptyStackException|EmptyQueueException e) {
			e.printStackTrace();
		}
		return cad;
	}
		
}
