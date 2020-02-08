package TDAColaCP;

import java.util.Comparator;
import Excepciones.*;
import TDALista.*;

public class CPLista<K, V> implements PriorityQueue<K, V> {

	protected PositionList<Entrada<K, V>> lista;
	protected Comparator<K> comp;

	public CPLista(Comparator<K> c) {
		lista = new ListaDobleEnlaze<Entrada<K, V>>();
		comp = c;
	}

	/**
	 * Consulta la cantidad de elementos de la cola.
	 * 
	 * @return Cantidad de elementos de la cola.
	 */
	public int size() {
		return lista.size();
	}

	/**
	 * Consulta si la cola está vacía.
	 * 
	 * @return Verdadero si la cola está vacía, falso en caso contrario.
	 */
	public boolean isEmpty() {
		return lista.isEmpty();
	}

	/**
	 * Devuelve la entrada con menor prioridad de la cola.
	 * 
	 * @return Entrada con menor prioridad.
	 * @throws EmptyPriorityQueueException si la cola está vacía.
	 */
	public Entry<K, V> min() throws EmptyPriorityQueueException {
		if (lista.isEmpty())
			throw new EmptyPriorityQueueException("ColaCP vacia");
		Entry<K, V> ret = null;
		try {
			ret = lista.first().element();
		} catch (EmptyListException e) {
			e.printStackTrace();
		}
		return ret;
	}

	/**
	 * Inserta un par clave-valor y devuelve la entrada creada.
	 * 
	 * @param key   Clave de la entrada a insertar.
	 * @param value Valor de la entrada a insertar.
	 * @return Entrada creada.
	 * @throws InvalidKeyException si la clave es inválida.
	 */
	public Entry<K, V> insert(K key, V value) throws InvalidKeyException {
		if (key == null)
			throw new InvalidKeyException("Clave invalida");
		Entrada<K, V> nueva = new Entrada<K, V>(key, value);
		if (isEmpty()) {
			lista.addFirst(nueva);
		} else {
			try {
				Position<Entrada<K, V>> p = lista.first();
				boolean encontre = false;
				while (p != null && !encontre) {
					if (comp.compare(p.element().getKey(), key) > 0) {
						lista.addBefore(p, nueva);
						encontre=true;
					} else {
						if (p == lista.last()) {
							p = null;
						} else {
							p = lista.next(p);
						}
					}
				}
				if(!encontre)
					lista.addLast(nueva);
			} catch (EmptyListException | InvalidPositionException | BoundaryViolationException e) {
				e.printStackTrace();
			}
		}
		return nueva;

	}
	/**
	 * Remueve y devuelve la entrada con menor prioridad de la cola.
	 * @return Entrada con menor prioridad.
	 * @throws EmptyPriorityQueueException si la cola está vacía.
	 */
	public Entry<K,V> removeMin()throws EmptyPriorityQueueException{
		if(isEmpty())throw new EmptyPriorityQueueException("ColaCP Vacia");
		Entry<K,V>ret=null;
		try {
			ret=lista.remove(lista.first());
		}
		catch(EmptyListException|InvalidPositionException e) {
			System.out.println(e.getMessage());
		}
		return ret;
	}
}
