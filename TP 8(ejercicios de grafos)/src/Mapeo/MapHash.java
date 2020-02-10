package Mapeo;
import Excepciones.*;
import TDALista.*;
import java.util.Iterator;
public class MapHash<K,V> implements Map<K,V>{
	
	protected PositionList<Entrada<K,V>>A[];
	protected int N=7;
	protected int n;
	
	public MapHash() {
		A=(ListaDobleEnlaze<Entrada<K,V>>[])new ListaDobleEnlaze[N];
		n=0;
		for(int i=0;i<N;i++) {
			A[i]=new ListaDobleEnlaze<Entrada<K,V>>();
		}
	}
	private int H (K key) {
		return key.hashCode()%N;
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
		if(key==null) {throw new InvalidKeyException("Clave invalida");		}
		V valor=null;
		int clave=H(key);
		Iterator<Position<Entrada<K,V>>>it=A[clave].positions().iterator();
		boolean esta=false;
		Position<Entrada<K,V>>act=it.hasNext()?it.next():null;
		
		while(act!=null&&!esta) {
			if(act.element().getKey().equals(key)) {
				valor=act.element().getValue();
				esta=true;
			}
			else {
				act=it.hasNext()?it.next():null;
			}
		}
		return valor;
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
			throw new InvalidKeyException("");
			
		}
		int clave=H(key);
		V valor=null;
		Entrada<K,V> nueva=new Entrada<K,V>(key,value);
		if(A[clave].isEmpty()) {
			A[clave].addLast(nueva);
			
		}
		else {
			Iterator<Position<Entrada<K,V>>> it=A[clave].positions().iterator();
			boolean esta=false;
			Position<Entrada<K,V>> pos=it.hasNext()?it.next():null;
					while(!esta && pos!=null) {
				if(pos.element().getKey().equals(key)) {
					esta=true;
					valor=pos.element().getValue();
					pos.element().setValue(value);
				}
				else {
					pos=it.hasNext()?it.next():null;
				}
			}
			if(!esta) {
				A[clave].addLast(nueva);
			}
		}
		n++;
		return valor;
	}
	
	/**
	 * Remueve la entrada con la clave dada en el mapeo y devuelve su valor, o nulo si no fue encontrada.
	 * @param e Entrada a remover.
	 * @return Valor de la entrada removida.
	 * @throws InvalidKeyException si la clave pasada por parámetro es inválida.
	 */
	public V remove(K key) throws InvalidKeyException{
		if(key==null)throw new InvalidKeyException("Clave invalida");
		V valor=null;
		try {
			if(A[H(key)].isEmpty()) {
				return valor;
			}
			else {
				Iterator<Position<Entrada<K,V>>> it=A[H(key)].positions().iterator();
				Position<Entrada<K,V>> pos=it.hasNext()?it.next():null;
				boolean esta=false;
				
				while(!esta&&pos!=null) {
					if(pos.element().getKey().equals(key)) {
						valor=pos.element().getValue();
						esta=true;
						A[H(key)].remove(pos);
						n--;
					}
					else {
						pos=it.hasNext()?it.next():null;
					}
				}
			}
		}
		catch(InvalidPositionException e) {
			throw new InvalidKeyException("Posicion Invalida");
		}
		return valor;
	}
	
	/**
	 * Retorna una colección iterable con todas las claves del mapeo.
	 * @return Colección iterable con todas las claves del mapeo.
	 */
	public Iterable<K> keys(){
		PositionList<K>lista=new ListaDobleEnlaze<K>();
		for(int i=0;i<N;i++) {
			for(Entrada<K,V>en:A[i]) {
				lista.addLast(en.getKey());
			}
		}
		return lista;
	}
	
	/**
	 * Retorna una colección iterable con todas los valores del mapeo.
	 * @return Colección iterable con todas los valores del mapeo.
	 */
	public Iterable<V> values(){
		PositionList<V>lista=new ListaDobleEnlaze<V>();
		for(int i=0;i<N;i++) {
			for(Entrada<K,V>en:A[i]) {
				lista.addLast(en.getValue());
			}
		}
		return lista;
	}
	
	/**
	 * Retorna una colección iterable con todas las entradas del mapeo.
	 * @return Colección iterable con todas las entradas del mapeo.
	 */
	public Iterable<Entry<K,V>> entries(){
		PositionList<Entry<K,V>>lista=new ListaDobleEnlaze<Entry<K,V>>();
		for(int i=0;i<N;i++) {
			for(Entry<K,V>en:A[i]) {
				lista.addLast(en);
			}
		}
		return lista;
	}
	}
