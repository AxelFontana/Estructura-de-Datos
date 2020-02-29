package TDAMapeo;

import Exceptions.InvalidKeyException;
import Exceptions.InvalidPositionException;
import TDALista.ListaDobleEnlaze;
import TDALista.Position;
import TDALista.PositionList;

import java.util.Iterator;

public class mapeoConHashAbierto<K,V> implements Map<K,V> {

    protected PositionList<Entrada<K,V>> A[];
    protected int N = 13;
    protected int size;

    public mapeoConHashAbierto(){
        size = 0;
        A = (ListaDobleEnlaze<Entrada<K,V>>[]) new ListaDobleEnlaze[N];
        for(int i= 0; i < A.length ;i++){
            A[i] = new ListaDobleEnlaze<Entrada<K,V>>();
        }
    }

    private void rehash() {
        int Nnueva = obtenerPrimo(N*2);
        PositionList<Entrada<K,V>>nueva[]=(ListaDobleEnlaze<Entrada<K,V>>[])new ListaDobleEnlaze[Nnueva];;
        for(int i=0;i<Nnueva;i++) {// INICIALIZO TODAS
            nueva[i]=new ListaDobleEnlaze<Entrada<K,V>>();
        }
        for(int i = 0; i<N; i++) {
            for(Entrada<K,V> e: A[i]) {
                int h = Math.abs(e.getKey().hashCode() % Nnueva);
                nueva[h].addLast(e);
            }
        }
        A = nueva;
        N = Nnueva;
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
     * Consulta el n�mero de entradas del mapeo.
     * @return N�mero de entradas del mapeo.
     */
    public int size(){
        return size;
    }

    /**
     * Consulta si el mapeo est� vac�o.
     * @return Verdadero si el mapeo est� vac�o, falso en caso contrario.
     */
    public boolean isEmpty(){
        return size == 0;
    }

    /**
     * Busca una entrada con clave igual a una clave dada y devuelve el valor asociado, si no existe retorna nulo.
     * @param key Clave a buscar.
     * @return Valor de la entrada encontrada.
     * @throws InvalidKeyException si la clave pasada por par�metro es inv�lida.
     */
    public V get(K key)throws InvalidKeyException{

        if(key == null){
            throw new InvalidKeyException("Clave invalida");
        }

        V ret = null;
        int clave = Math.abs(key.hashCode()) % N;
        boolean esta = false;
        Iterator<Entrada<K,V>> it = A[clave].iterator();
        Entrada<K,V>act = it.hasNext()?it.next():null;

        while(!esta && act != null){
            if(act.getKey().equals(key)){
                ret = act.getValue();
                esta = true ;
            }
            else{
                act = it.hasNext() ? it.next() : null;
            }
        }
        return ret;
    }

    /**
     * Si el mapeo no tiene una entrada con clave key, inserta una entrada con clave key y valor value en el mapeo y devuelve null.
     * Si el mapeo ya tiene una entrada con clave key, entonces remplaza su valor y retorna el viejo valor.
     * @param key Clave de la entrada a crear.
     * @param value Valor de la entrada a crear.
     * @return Valor de la vieja entrada.
     * @throws InvalidKeyException si la clave pasada por par�metro es inv�lida.
     */
    public V put(K key, V value) throws InvalidKeyException{

        if(key == null){
            throw new InvalidKeyException("Clave Invalida");
        }
        if(size/N > 0.9){
            rehash();
        }
        int clave = Math.abs(key.hashCode()) % N;
        V ret = null;

        if(isEmpty()){ //Si la lista aun no tiene elementos
            Entrada<K,V>nueva = new Entrada<K,V>(key,value);
            A[clave].addLast(nueva);
            size++;
        }
        else{//No esta vacia
            Iterator<Entrada<K,V>> it = A[clave].iterator();
            Entrada<K,V> act = it.hasNext() ? it.next() : null;
            boolean esta = false;

            while(!esta && act != null){
                if(key.equals(act.getKey())){
                    esta = true;
                    ret = act.getValue();
                    act.setValue(value);
                }
                else{
                    act = it.hasNext() ? it.next() : null;
                }
            }
            if(!esta){//no esta vacia pero no tiene la clave key
                Entrada<K,V> nueva = new Entrada<K,V>(key,value);
                A[clave].addLast(nueva);
                size++;
            }
        }
        return ret;
    }

    /**
     * Remueve la entrada con la clave dada en el mapeo y devuelve su valor, o nulo si no fue encontrada.
     * @param key Entrada a remover.
     * @return Valor de la entrada removida.
     * @throws InvalidKeyException si la clave pasada por par�metro es inv�lida.
     */
    public V remove(K key) throws InvalidKeyException{
        if(key==null)throw new InvalidKeyException("Clave invalida");
        V valor=null;
        try {

            int clave = Math.abs(key.hashCode()) % N;
            Iterator<Position<Entrada<K,V>>> it=A [Math.abs(key.hashCode()) % N].positions().iterator();
            Position<Entrada<K,V>> pos=it.hasNext()?it.next():null;
            boolean esta=false;

            while(!esta&&pos!=null) {
                if(pos.element().getKey().equals(key)) {
                    valor=pos.element().getValue();
                    esta=true;
                    A[ Math.abs(key.hashCode()) % N].remove(pos);
                    size--;
                }
                else {
                    pos=it.hasNext()?it.next():null;
                }
            }

        }
        catch( InvalidPositionException e) {
            throw new InvalidKeyException("Posicion Invalida");
        }
        return valor;
    }

    /**
     * Retorna una colecci�n iterable con todas las claves del mapeo.
     * @return Colecci�n iterable con todas las claves del mapeo.
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
     * Retorna una colecci�n iterable con todas los valores del mapeo.
     * @return Colecci�n iterable con todas los valores del mapeo.
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
     * Retorna una colecci�n iterable con todas las entradas del mapeo.
     * @return Colecci�n iterable con todas las entradas del mapeo.
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
