package TDADiccionario;

import Exceptions.InvalidEntryException;
import Exceptions.InvalidKeyException;
import TDALista.ListaDobleEnlaze;
import TDALista.PositionList;
import TDAMapeo.*;

import java.util.List;

public class DiccionarioHashC<K,V> implements Dictionary<K,V>{

    protected Entrada<K,V> [] A;
    protected  Entrada<K,V> available;
    protected int size;
    protected int N;
    private static float factor = 0.9f;

    public DiccionarioHashC(){
        size = 0;
        N = 13;
        A = (Entrada<K,V>[])new Entrada[N];
        available = new Entrada<K,V>(null,null);
    }



    protected void rehash() {
        int Nnueva = obtenerPrimo(N*2);
        Entrada<K,V> [] nueva = (Entrada<K,V> []) new Entrada[Nnueva];
        for(int i = 0; i<N; i++) {
            if(A[i] != null && A[i] != available) {
                int h = Math.abs(A[i].getKey().hashCode() % Nnueva);
                while(nueva[h] != null) {
                    h = (h+1) % Nnueva;
                }
                nueva[h] = A[i];
            }
        }
        A = nueva;
        N = Nnueva;
    }

    private int obtenerPrimo(int n) {
        int cont = n+1;
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
        int a;
        boolean enc = false;
        for(int i = 2; i<n && !enc; i++) {
            a = n % i;
            if(a == 0)
                enc = true;
        }

        return enc;
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
            throw new InvalidKeyException("Clave invalida");
        }

        Entry<K,V> toRet = null;
        int h = Math.abs(key.hashCode() % N);
        boolean encontre = false;

        while(!encontre && A[h] != null){
            if(A[h] == available){
                h = (h+1) % N;
            }
            else{
                if(A[h].getKey().equals(key)){
                    toRet = A[h];
                    encontre = true;
                }
                else{
                    h = (h+1) % N;
                }
            }
        }
        return toRet;
    }

    /**
     * Retorna una colecci�n iterable que contiene todas las entradas con clave igual a una clave dada.
     * @param key Clave de las entradas a buscar.
     * @return Colecci�n iterable de las entradas encontradas.
     * @throws InvalidKeyException si la clave pasada por par�metro es inv�lida.
     */
    public Iterable<Entry<K,V>> findAll(K key) throws InvalidKeyException{
        if (key == null){
            throw new InvalidKeyException("Clave nula");
        }

        PositionList<Entry<K,V>> toRet = new ListaDobleEnlaze<Entry<K,V>>(); // Cambiar si no anda
        int h = Math.abs(key.hashCode() % N);

        while(A[h] != null){
            if (A[h].getKey().equals(key)) {
                toRet.addLast(A[h]);
            }
            h = (h+1) % N;
        }
        return toRet;
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
        if(size / N >factor){
            rehash();
        }

        Entrada<K,V> nueva = new Entrada<K,V>(key,value);
        int h = Math.abs(key.hashCode() % N);
        boolean encontre = false;// Encontre available

        while(!encontre && A[h] != null){
            if(A[h] == available){
                encontre = true;
                A[h] = nueva;
            }
            else{
                h = (h+1) % N;
            }
        }
        if(!encontre){
            A[h] = nueva;
        }
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

           Entry<K,V> toRet = null;
           int h = Math.abs(e.getKey().hashCode() % N);
           boolean encontre = false;

           while(A[h] != null && !encontre){
               if(A[h] == available){
                   h = (h+1) % N;
               }
               else{
                   if(A[h].equals(e)){
                       encontre = true;
                       toRet = A[h];
                       A[h] = available;
                   }
                   else{
                       h = (h+1) % N;
                   }
               }
           }

           if(!encontre){
               throw new InvalidEntryException("Entrada Invalida");
           }
           size--;
           return toRet;
    }

    /**
     * Retorna una colecci�n iterable con todas las entradas en el diccionario.
     * @return Colecci�n iterable de todas las entradas.
     */
    public Iterable<Entry<K,V>> entries(){
        PositionList<Entry<K,V>> lista = new ListaDobleEnlaze<Entry<K,V>>();
        for(int i =0; i<A.length ;i++){
            if(A[i] != null && !A[i].equals(available)){
                lista.addLast(A[i]);
            }
        }
        return lista;
    }
}