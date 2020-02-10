package Mapeo;
import java.util.Iterator;

import Excepciones.*;
import TDALista.*;

public class mapaConLista<K,V>implements Map<K,V>{
	protected PositionList<Entrada<K,V>> lista;
	
	
	public mapaConLista() {
		lista=new ListaDobleEnlaze<Entrada<K,V>>();
	}
	/**
	 * Consulta el número de entradas del mapeo.
	 * @return Número de entradas del mapeo.
	 */
	public int size() {
		return lista.size();
		}

	/**
	 * Consulta si el mapeo está vacío.
	 * @return Verdadero si el mapeo está vacío, falso en caso contrario.
	 */
	public boolean isEmpty() {
		return lista.isEmpty();
		}
	/**
	 * Busca una entrada con clave igual a una clave dada y devuelve el valor asociado, si no existe retorna nulo.
	 * @param key Clave a buscar.
	 * @return Valor de la entrada encontrada.
	 * @throws InvalidKeyException si la clave pasada por parámetro es inválida.
	 */
	public V get(K key)throws InvalidKeyException{
		if(key==null) {
			throw new InvalidKeyException ("Pos Invalida");
		}else
			if (isEmpty()) {
				return null;
			}
			else {
		Iterator<Position<Entrada<K,V>>> it=lista.positions().iterator();
		Position<Entrada<K,V>>act=it.hasNext()?it.next():null;
		V ret=null;
		boolean esta=false;
		while(act!=null&&!esta) {
			if (act.element().getKey().equals(key)){
				esta=true;
				ret=act.element().getValue();
			}
			else {
				act=it.hasNext()?it.next():null;
			}		
		}
	   return ret;
			}
	}
	/**
	 * Si el mapeo no tiene una entrada con clave key, inserta una entrada con clave key y valor value en el mapeo y devuelve null. 
	 * Si el mapeo ya tiene una entrada con clave key, entonces remplaza su valor y retorna el viejo valor.
	 * @param key Clave de la entrada a crear.
	 * @param value Valor de la entrada a crear. 
	 * @return Valor de la vieja entrada.
	 * @throws InvalidKeyException si la clave pasada por parámetro es inválida.
	 */
	public V put(K key, V value) throws InvalidKeyException{
		if(key==null) {
			throw new InvalidKeyException("La clave pasada por parametro es nula");
		}
		if(isEmpty()) {
			
			Entrada<K,V>Nueva=new Entrada<K,V>(key,value);
			lista.addLast(Nueva);
			return null;
		}
		else {
		Iterator<Position<Entrada<K,V>>> it=lista.positions().iterator();
		Position<Entrada<K,V>>act=it.hasNext()?it.next():null;
		V ret=null;
		boolean esta=false;
		while(act!=null&&!esta) {
			if (act.element().getKey().equals(key)){
				esta=true;
				ret=act.element().getValue();
				
			}
			else {
				act=it.hasNext()?it.next():null;
			}		
		}
		if (!esta) {
			Entrada<K,V>nueva=new Entrada<K,V>(key,value);
			lista.addLast(nueva);
		}
		else {
			act.element().setValue(value);
			
		}
		return ret;
		}
	   
	}
	
	/**
	 * Remueve la entrada con la clave dada en el mapeo y devuelve su valor, o nulo si no fue encontrada.
	 * @param e Entrada a remover.
	 * @return Valor de la entrada removida.
	 * @throws InvalidKeyException si la clave pasada por parámetro es inválida.
	 */
	public V remove(K key) throws InvalidKeyException{
	
		if(key==null) {
			throw new InvalidKeyException("Clave nula");
		}
		Iterator<Position<Entrada<K,V>>> it=lista.positions().iterator();
		Position<Entrada<K,V>>act=it.hasNext()?it.next():null;
		V ret=null;
		boolean esta=false;
		try {
			while(act!=null&&!esta) {
				if (act.element().getKey().equals(key)){
					esta=true;
					ret=act.element().getValue();
					lista.remove(act);
				}
				else {
					act=it.hasNext()?it.next():null;
				}		
			}
		}
		catch(InvalidPositionException e){
			e.printStackTrace();
		}
		return ret;
	}
	/**
	 * Retorna una colección iterable con todas las claves del mapeo.
	 * @return Colección iterable con todas las claves del mapeo.
	 */
	public Iterable<K> keys(){
		PositionList<K>ret=new ListaDobleEnlaze<K>();
		for(Position<Entrada<K,V>> pos:lista.positions()) {
			ret.addLast(pos.element().getKey());
		}
		return ret;
	}
	/**
	 * Retorna una colección iterable con todas los valores del mapeo.
	 * @return Colección iterable con todas los valores del mapeo.
	 */
	public Iterable<V> values(){
		PositionList<V>ret=new ListaDobleEnlaze<V>();
		for(Position<Entrada<K,V>> pos:lista.positions()) {
			ret.addLast(pos.element().getValue());
		}
		return ret;
	}

	/**
	 * Retorna una colección iterable con todas las entradas del mapeo.
	 * @return Colección iterable con todas las entradas del mapeo.
	 */
	public Iterable<Entry<K,V>> entries(){
		PositionList<Entry<K,V>>ret =new ListaDobleEnlaze<Entry<K,V>>();
		for(Entrada<K,V>pos:lista) {
			ret.addLast(pos);
		}
		return ret;
	}
}
