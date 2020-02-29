package TDAMapeo;
/**
 * Interface Entry
 */
public interface Entry<K, V> {

    /**
     * Consulta la clave asociada la entrada.
     * @return Clave asociada a la entrada.
     */
    public K getKey();

    /**
     * Consulta el valor asociado la entrada.
     * @return Valor asociado a la entrada.
     */
    public V getValue();
}
