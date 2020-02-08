package TDADiccionario;

import Exceptions.InvalidEntryException;
import Exceptions.InvalidKeyException;
import Exceptions.InvalidPositionException;
import TDALista.ListaDobleEnlaze;
import TDALista.Position;
import TDALista.PositionList;
import TDAMapeo.Entrada;
import TDAMapeo.Entry;

import java.util.Iterator;

public class DiccionarioHashA<K,V> implements Dictionary<K,V> {

    protected int size;
    protected int N = 13;
    protected PositionList<Entrada<K,V>> A[];

    public DiccionarioHashA(){
        size = 0;
        A = (ListaDobleEnlaze<Entrada<K,V>>[])new ListaDobleEnlaze[N];
        for(int i = 0;i < A.length;i++){
            A[i] = new ListaDobleEnlaze<Entrada<K,V>>();
        }
    }

    private void rehash() {
        int Nnueva = obtenerPrimo(N*2);
        PositionList<Entrada<K,V>>nueva[]=(ListaDobleEnlaze<Entrada<K,V>>[])new ListaDobleEnlaze[Nnueva];;
        for(int i=0;i<nueva.length;i++) {// INICIALIZO TODAS
            nueva[i]=new ListaDobleEnlaze<Entrada<K,V>>();
        }
        for(int i = 0; i<N; i++) {//CADA BUCKET
            for(Entrada<K,V>e:A[i]) {//CADA LISTA DE LOS BUCKETS
                int clave=e.getKey().hashCode()%Nnueva;
                nueva[clave].addLast(e);
            }
            A = nueva;
            N = Nnueva;
        }
    }

    private int obtenerPrimo(int n) {
        int cont = n;
        boolean enc = false;
        while(!enc) {
            if(esPrimo(cont))
                enc = true;
            else
                cont++;
        }

        return cont;
    }

    private boolean esPrimo(int n) {
        int cont=2;
        boolean esta = true;
        while(cont<n && esta) {
            if(n % cont==0) {
                esta=false;
            }
            else {
                cont++;
            }

        }
        return esta;
    }

    /**
     * Consulta el n�mero de entradas del diccionario.
     * @return N�mero de entradas del diccionario.
     */
    public int size(){
        return size;
    }

    /**
     * Consulta si el diccionario est� vac�o.
     * @return Verdadero si el diccionario est� vac�o, falso en caso contrario.
     */
    public boolean isEmpty(){
        return size == 0;
    }

    /**
     * Busca una entrada con clave igual a una clave dada y la devuelve, si no existe retorna nulo.
     * @param key Clave a buscar.
     * @return Entrada encontrada.
     * @throws InvalidKeyException si la clave pasada por par�metro es inv�lida.
     */
    public Entry<K,V> find(K key) throws InvalidKeyException{
        if(key == null){
            throw new InvalidKeyException("Clave nula");
        }

        Entry<K,V> ret = null;
        int clave = Math.abs(key.hashCode()) % N;
        Iterator<Entrada<K,V>> it = A[clave].iterator();
        Entrada<K,V> act = it.hasNext() ? it.next() : null;
        boolean esta = false;

        while(!esta && act != null){
            if(key.equals(act.getKey())){
                ret = act;
                esta = true;
            }
            else{
                act = it.hasNext() ? it.next() : null;
            }
        }
        return ret;
    }

    /**
     * Retorna una colecci�n iterable que contiene todas las entradas con clave igual a una clave dada.
     * @param key Clave de las entradas a buscar.
     * @return Colecci�n iterable de las entradas encontradas.
     * @throws InvalidKeyException si la clave pasada por par�metro es inv�lida.
     */
    public Iterable<Entry<K,V>> findAll(K key) throws InvalidKeyException{
        if(key == null){
            throw new InvalidKeyException("Clave invalida");
        }
        PositionList<Entry<K,V>> lista = new ListaDobleEnlaze<Entry<K,V>>(); //Preguntar porque
        int clave = Math.abs(key.hashCode()) % N;

        for(Entry <K,V> e : A[clave]){
            if(key.equals(e.getKey())){
                lista.addLast(e);
            }
        }
        return lista;
    }

    /**
     * Inserta una entrada con una clave y un valor dado en el diccionario y retorna la entrada creada.
     * @param key Clave de la entrada a crear.
     * @return value Valor de la entrada a crear.
     * @throws InvalidKeyException si la clave pasada por par�metro es inv�lida.
     */
    public Entry<K,V> insert(K key, V value) throws InvalidKeyException{
        if(key == null){
            throw new InvalidKeyException("Clave nula");
        }

        int clave = Math.abs(key.hashCode()) % N;
        Entrada<K,V> nueva = new Entrada<K,V>(key,value);
        A[clave].addLast(nueva);
        size++;

        return nueva;
    }

    /**
     * Remueve una entrada dada en el diccionario y devuelve la entrada removida.
     * @param e Entrada a remover.
     * @return Entrada removida.
     * @throws InvalidEntryException si la entrada no est� en el diccionario o es inv�lida.
     */
    public Entry<K,V> remove(Entry<K,V> e) throws InvalidEntryException{
        if(e == null){
            throw new InvalidEntryException("Entrada Invalida");
        }

        int clave = Math.abs(e.getKey().hashCode()) % N;
        if(A[clave].isEmpty()){
            throw new InvalidEntryException("La entrada no esta en el diccionario");
        }

        Entry<K,V> ret = e;
        boolean esta = false;
        Iterator<Position<Entrada<K,V>>> it = A[clave].positions().iterator();
        Position<Entrada<K,V>> act = it.hasNext() ? it.next() : null;

        while(!esta && act != null){
            if(e.equals(act.element())){
                esta = true;
                try{
                    A[clave].remove(act);
                }
                catch (InvalidPositionException e1){
                    e1.printStackTrace();
                }
                size--;
            }
            else{
                act = it.hasNext() ? it.next() : null;
            }
        }
        if(!esta){
            throw new InvalidEntryException("La entrada no esta en el diccionario");
        }
        return ret;
    }

    /**
     * Retorna una colecci�n iterable con todas las entradas en el diccionario.
     * @return Colecci�n iterable de todas las entradas.
     */
    public Iterable<Entry<K,V>> entries(){
        PositionList<Entry<K,V>> lista = new ListaDobleEnlaze<Entry<K,V>>();
        for (int i =0; i < A.length; i++){
            for(Entry<K,V> elem :A[i]){
                lista.addLast(elem);
            }
        }
        return lista;
    }
}
