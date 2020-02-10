package TDACola;
import Excepciones.*;
public class ColaCircular<E>implements Queue<E> {
	
	protected int Inicio,Final;
	protected E Cola [];
	protected static final int longitud=10;
	public ColaCircular() {
		Inicio=0;
		Final=0;
		Cola= (E[])new Object[longitud]; 
	}
	
	/**
	 * Devuelve la cantidad de elementos en la cola.
	 * @return Cantidad de elementos en la cola.
	 */
	public int size() {
		return (((Cola.length)-Inicio+Final) % Cola.length);
		
	}

	/**
	 * Consulta si la cola está vacía.
	 * @return Verdadero si la cola está vacía, falso en caso contrario.
	 */
	public boolean isEmpty() {
		return (Inicio==Final);
	}

	/**
	 * Inspecciona el elemento que se encuentra en el frente de la cola.
	 * @return Elemento que se encuentra en el frente de la cola.
	 * @throws EmptyQueueException si la cola está vacía.
	 */
	public E front() throws EmptyQueueException{
		if(Inicio==Final)
			throw new EmptyQueueException("Cola Vacia");
		return Cola[Inicio];
	}
	/**
	 * Inserta un elemento en el fondo de la cola.
	 * @param element Nuevo elemento a insertar.
	 */
	public void enqueue(E element) {
		if(Cola.length-1==size()) {
			E[] aux=copiar(Inicio);
			Final=size();
			Inicio=0;
			Cola=aux;
		}
			Cola[Final]=element;
			Final=(Final+1) % Cola.length;
		
	}
	private E[] copiar(int m) {
		E[]aux= (E[])new Object[2*Cola.length];
		for(int j=0;j<size();j++) {
			aux[j]=Cola[m];
			m=(m+1)% Cola.length;
		}
	return aux;
	}
	/**
	 * Remueve el elemento en el frente de la cola.
	 * @return Elemento removido.
	 * @throws EmptyQueueException si la cola está vacía.
	 */
	public E dequeue() throws EmptyQueueException{
		if(isEmpty())throw new EmptyQueueException("Cola Vacia");
		E aux=Cola[Inicio];
		Cola[Inicio]=null;
		Inicio=(Inicio+1)% Cola.length;
		return aux;
	}
}
