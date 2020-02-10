package Mapeo;

import Excepciones.InvalidKeyException;
import TDALista.*;

public class MapaHash<K,V> implements Map<K,V>{
    private	Map <K,V>[] A;
	private int n;
	private int N=13;
	
	public MapaHash() {
		A=(Map<K,V>[])new mapaConLista[N];
		n=0;
		for(int i=0;i<N;i++) {
			A[i]=new mapaConLista<K,V>();
		}
	}
	/**
	 * Consulta el número de entradas del mapeo.
	 * @return Número de entradas del mapeo.
	 */
	public int size() {
		return n;
	}

	/**
	 * Consulta si el mapeo está vacío.
	 * @return Verdadero si el mapeo está vacío, falso en caso contrario.
	 */
	public boolean isEmpty() {
		return n==0;
	}
	/**
	 * Busca una entrada con clave igual a una clave dada y devuelve el valor asociado, si no existe retorna nulo.
	 * @param key Clave a buscar.
	 * @return Valor de la entrada encontrada.
	 * @throws InvalidKeyException si la clave pasada por parámetro es inválida.
	 */
	public V get(K key)throws InvalidKeyException{
		if(key==null) {
			throw new InvalidKeyException("clave nula");
		}
		return A[H(key)].get(key);		
		
	}
	private int H (K key) {
		return key.hashCode()%N;
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
			throw new InvalidKeyException("clave nula");
		}
		V valor=A[H(key)].put(key, value);
		if (valor==null){
			n++;
		}
		return valor;
	}
	/**
	 * Remueve la entrada con la clave dada en el mapeo y devuelve su valor, o nulo si no fue encontrada.
	 * @param e Entrada a remover.
	 * @return Valor de la entrada removida.
	 * @throws InvalidKeyException si la clave pasada por parámetro es inválida.
	 */
	public V remove(K key) throws InvalidKeyException{
		if(key==null) {
			throw new InvalidKeyException("clave nula");
		}
		V valor =A[H(key)].remove(key);
		if(valor!=null) {
			n--;
		}
		return valor;
	}
	/**
	 * Retorna una colección iterable con todas las claves del mapeo.
	 * @return Colección iterable con todas las claves del mapeo.
	 */
	public Iterable<K> keys(){
		PositionList<K>claves=new ListaDobleEnlaze<K>();
		for(int i=0;i<N;i++) {
			for(K k:A[i].keys()) {
				claves.addLast(k);
			}
		}	
		return claves;
	}
	
	/**
	 * Retorna una colección iterable con todas los valores del mapeo.
	 * @return Colección iterable con todas los valores del mapeo.
	 */
	public Iterable<V> values(){
		PositionList<V>claves=new ListaDobleEnlaze<V>();
		for(int i=0;i<N;i++) {
			for(V v:A[i].values()) {
				claves.addLast(v);
			}
		}		
		return claves;
	}

	/**
	 * Retorna una colección iterable con todas las entradas del mapeo.
	 * @return Colección iterable con todas las entradas del mapeo.
	 */
	public Iterable<Entry<K,V>> entries(){
		PositionList<Entry<K,V>>entrys=new ListaDobleEnlaze<Entry<K,V>>();
		for(int i=0;i<N;i++) {
			for(Entry<K,V> entradas:A[i].entries()) {
				entrys.addLast(entradas);
			}
		}		
		return entrys;
	}
	private void rehash() {
		Iterable<Entry<K,V>>entradas=this.entries();
		Map<K,V>[] nuevo=(Map<K,V>[])new mapaConLista[2*N];
		N=2*N;
		A=nuevo;
		try {
			for(Entry<K,V>e:entradas) {
				this.put(e.getKey(),e.getValue());
			}
		}
		catch(InvalidKeyException e) {
			e.printStackTrace();
		}
	}	
	
	
	
	
	
	}