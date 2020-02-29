package TDAMapeo;
import Exceptions.*;
import TDALista.*;

public class mapeoConHashCerrado<K,V> implements Map<K,V> {

    protected Entrada<K,V> []A;
    protected int size,N;
    protected Entrada<K,V> disponible;

    public mapeoConHashCerrado(){
        N = 7;
        size = 0;
        disponible = new Entrada<K,V>(null,null);
        A = (Entrada<K,V> []) new Entrada[N];
    }

    private int funcionHash(K k) {
        int i = Math.abs(k.hashCode());
        return (i % N);
    }

    private void checkKey(K key)throws InvalidKeyException{
        if(key==null)
            throw new InvalidKeyException("La clave es nula.");
    }



    private  void rehash(){

        int Nnueva = obtenerPrimo(N*2);
        Entrada<K,V> [] nueva = (Entrada<K,V> []) new Entrada[Nnueva];
        for(int i= 0; i<N; i++){
            if(A[i] != null && A[i]!=disponible){
                int h = Math.abs(A[i].getKey().hashCode() % Nnueva);
                while(nueva[h] != null){
                    h = (h+1) % Nnueva;
                }
                nueva[h] = A[i];
            }
        }
        A = nueva;
        N = Nnueva;
    }

    private int obtenerPrimo(int n){
        int cont = n+1; //SI NO ANDA SACARLE EL +1
        boolean encontre = false;
        while(!encontre){
            if(esPrimo(cont)){
                encontre = true;
            }
            else{
                cont++;
            }
        }
        return cont;
    }

    private boolean esPrimo(int n){
        int a;
        boolean es = true;

        for(int i = 2;i < n && es; i++){
            a = n % i;
            if(a == 0){
                es = false;
            }
        }
        return es;
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
            throw new InvalidKeyException("la clave es nula");
        }

        V ret = null;
        int h = (Math.abs(key.hashCode())% N);
        boolean encontre = false;
        int cont = 1;


        while (!encontre && cont <= A.length && A[h]!= null) {
            if(A[h].getKey().equals(key)){
                encontre = true;
            }
            else{
                h = (h+1) % N;
                cont++;
            }
        }
        if(encontre){
            ret = A[h].getValue();
        }

        return ret;
    }
    private int findAvailableEntry(K k) {
        int p = funcionHash(k);
        while(A[p]!=null && A[p]!=disponible && (!A[p].getKey().equals(k)) ) {
            p= (p+1) % N;
        }
        return p;
    }
    /**
     * Si el mapeo no tiene una entrada con clave key, inserta una entrada con clave key y valor value en el mapeo y devuelve null.
     * Si el mapeo ya tiene una entrada con clave key, entonces remplaza su valor y retorna el viejo valor.
     * @param key Clave de la entrada a crear.
     * @param value Valor de la entrada a crear.
     * @return Valor de la vieja entrada.
     * @throws InvalidKeyException si la clave pasada por par�metro es inv�lida.
     */
    public V put(K key, V value) throws InvalidKeyException {
        checkKey(key);
        if(size/N > 0.9)
            rehash();
        int p = findAvailableEntry(key);
        V mValue = null;

            if(A[p]!=null && A[p]!=disponible) {
                mValue = A[p].getValue();
                A[p].setValue(value);
            }
            else {
                size++;
                A[p] = new Entrada<K,V>(key,value);
            }

        return mValue;
    }
    /**
     * Remueve la entrada con la clave dada en el mapeo y devuelve su valor, o nulo si no fue encontrada.
     * @param key Entrada a remover.
     * @return Valor de la entrada removida.
     * @throws InvalidKeyException si la clave pasada por par�metro es inv�lida.
     */
    public V remove(K key) throws InvalidKeyException{
        if(key == null){
            throw new InvalidKeyException("Clave invalida");
        }
        V mValue=null;
        int p=(Math.abs(key.hashCode())% N);
        int cont=1;
        while(cont<=A.length && A[p]!=null && mValue==null) {
            if(A[p]!=disponible && A[p].getKey().equals(key)) {
                mValue=A[p].getValue();
                A[p]=disponible;
                size--;
            }else {
                p=(p+1)%A.length;
            }
            cont++;
        }
        return mValue;
    }

    /**
     * Retorna una colecci�n iterable con todas las claves del mapeo.
     * @return Colecci�n iterable con todas las claves del mapeo.
     */
    public Iterable<K> keys(){
        PositionList<K> l=new ListaDobleEnlaze<K>();
        for(Entrada<K,V> e:A)
            if(e!=null && e!=disponible)
                l.addLast(e.getKey());
        return l;
    }

    /**
     * Retorna una colecci�n iterable con todas los valores del mapeo.
     * @return Colecci�n iterable con todas los valores del mapeo.
     */
    public Iterable<V> values(){
        PositionList<V> l=new ListaDobleEnlaze<V>();
        for(Entrada<K,V> e:A)
            if(e!=null && e!=disponible)
                l.addLast(e.getValue());
        return l;
    }

    /**
     * Retorna una colecci�n iterable con todas las entradas del mapeo.
     * @return Colecci�n iterable con todas las entradas del mapeo.
     */
    public Iterable<Entry<K,V>> entries(){
        PositionList<Entry<K,V>> l=new ListaDobleEnlaze<Entry<K,V>>();
        for(Entrada<K,V> e:A)
            if(e!=null && e!=disponible)
                l.addLast(e);
        return l;
    }

}
