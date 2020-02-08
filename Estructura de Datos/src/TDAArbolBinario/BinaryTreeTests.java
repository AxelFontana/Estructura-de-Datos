package TDAArbolBinario;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;
import Exceptions.*;
import TDALista.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Tester desarrollado con JUnit 4 para la estructura BinaryTree.
 *
 * @author Gonzalo Ezequiel ArrÃ³ - Departamento de Ciencias e IngenierÃ­a de la
 *         ComputaciÃ³n - Universidad Nacional del Sur -
 *         gonzalo.arro@hotmail.com
 *
 * @version 1.0 Agosto 2017
 *
 */
public class BinaryTreeTests {

    // OBJETOS DE PRUEBA

    BinaryTree<Object> binaryTree;
    BinaryTree<Object> leftTree, rightTree;
    Object o1, o2;

    // INICIALIZACIÃ“N Y LIMPIEZA DE OBJETOS PARA LAS PRUEBAS

    /**
     * Setup inicial de los objetos utilizados en los casos de prueba. (llamado
     * antes de cada test)
     */
    @Before
    public void setUp() {
        binaryTree = new ArbolBinario<Object>(); // cambiar esta lÃ­nea para
        // probar distintas
        // implementaciones
        o1 = new Object();
        o2 = new Object();
        // para mÃ©todo attach
        leftTree = new ArbolBinario<Object>();
        rightTree = new ArbolBinario<Object>();
    }

    /**
     * Limpieza de los objetos utilizados en los casos de prueba. (llamado
     * despuÃ©s de cada test)
     */
    @After
    public void tearDown() {
        binaryTree = null; // esto es importante ya que los objetos pueden
        // persistir durante la ejecuciÃ³n de todos los
        // tests, dejando objetos innecesarios en memoria
        // que ya no se estÃ¡n usando
        leftTree = rightTree = null;
        o1 = o2 = null;
    }

    // TESTS

    /**
     * Comprueba que el mÃ©todo size inicialmente retorne 0 con un Ã¡rbol vacÃ­o.
     */
    @Test
    public void size_emptyTree_returnsZero() {
        assertEquals("El mÃ©todo size no retorna 0 con un Ã¡rbol vacÃ­o.", 0, binaryTree.size());
    }

    /**
     * Comprueba que el mÃ©todo isEmpty responda correctamente con un Ã¡rbol
     * vacÃ­o.
     */
    @Test
    public void isEmpty_emptyTree_returnsTrue() {
        assertTrue("El mÃ©todo isEmpty no retorna true con un Ã¡rbol vacÃ­o.", binaryTree.isEmpty());
    }

    /**
     * Comprueba que el mÃ©todo isEmpty responda correctamente con un Ã¡rbol no
     * vacÃ­o.
     */
    @Test
    public void isEmpty_notEmptyTree_returnsFalse() throws InvalidOperationException {
        binaryTree.createRoot(new Object());
        assertFalse("El mÃ©todo isEmpty no retorna false con un Ã¡rbol no vacÃ­o.", binaryTree.isEmpty());
    }

    /**
     * Comprueba que el mÃ©todo iterator retorna un iterador vacÃ­o para un Ã¡rbol
     * vacÃ­o.
     */
    @Test
    public void iterator_emptyTree_returnsEmptyIterator() {
        assertFalse("El mÃ©todo iterator no retorna un iterador vacÃ­o para un Ã¡rbol vacÃ­o.",
                binaryTree.iterator().hasNext());
    }

    /**
     * Comprueba que el mÃ©todo iterator retorna un iterador con un solo elemento
     * para un Ã¡rbol con solo la raÃ­z.
     */
    @Test
    public void iterator_onlyRoot_iteratorSizeEqualsOne() throws InvalidOperationException {
        binaryTree.createRoot(new Object());
        Iterator<Object> iterator = binaryTree.iterator();
        int size = 0;
        while (iterator.hasNext()) {
            iterator.next();
            size++;
        }
        assertEquals("El mÃ©todo iterator retorna un iterador con " + size + " elementos para un Ã¡rbol con un elemento.",
                1, size);
    }

    /**
     * Comprueba que el mÃ©todo positions retorna una colecciÃ³n iterable vacÃ­a
     * para un Ã¡rbol vacÃ­o.
     */
    @Test
    public void positions_emptyTree_returnsEmptyIterable() {
        assertFalse("El mÃ©todo positions no retorna una colecciÃ³n iterable vacÃ­a para un Ã¡rbol vacÃ­o.",
                binaryTree.positions().iterator().hasNext());
    }

    /**
     * Comprueba que el mÃ©todo positions retorna una colecciÃ³n iterable con un
     * solo elemento para un Ã¡rbol con solo la raÃ­z.
     */
    @Test
    public void positions_onlyRoot_iterableSizeEqualsOne() throws InvalidOperationException {
        binaryTree.createRoot(new Object());
        int size = 0;
        for (Position<Object> p : binaryTree.positions()) {
            size++;
        }
        assertEquals("El mÃ©todo positions retorna una colecciÃ³n iterable con " + size
                + " elementos para un Ã¡rbol con un elemento.", 1, size);
    }

    /**
     * Comprueba que el mÃ©todo replace lanza la excepciÃ³n
     * InvalidPositionException al recibir una posiciÃ³n nula.
     */
    @Test(expected = InvalidPositionException.class)
    public void replace_nullPosition_throwsIPE() throws InvalidOperationException, InvalidPositionException {
        binaryTree.createRoot(new Object());
        binaryTree.replace(null, new Object());
    }

    /**
     * Comprueba que el mÃ©todo replace retorna correctamente el elemento previo
     * en la posiciÃ³n pasada por parÃ¡metro.
     */
    @Test
    public void replace_validPosition_returnsOldElement()
            throws InvalidOperationException, EmptyTreeException, InvalidPositionException {
        Position<Object> root = binaryTree.createRoot(o1);
        assertEquals("El mÃ©todo replace no retorna el elemento almacenado previamente.", o1,
                binaryTree.replace(root, new Object()));
    }

    /**
     * Comprueba que el mÃ©todo replace setea correctamente el nuevo elemento a
     * la posiciÃ³n pasada por parÃ¡metro.
     */
    @Test
    public void replace_validPosition_setsNewElement()
            throws InvalidOperationException, EmptyTreeException, InvalidPositionException {
        Position<Object> root = binaryTree.createRoot(new Object());
        binaryTree.replace(root, o1);
        assertEquals("El mÃ©todo replace no setea el nuevo elemento correctamente.", o1, root.element());
    }

    /**
     * Comprueba que el mÃ©todo root lanza la excepciÃ³n EmptyTreeException sobre
     * un Ã¡rbol vacÃ­o.
     */
    @Test(expected = EmptyTreeException.class)
    public void root_emptyTree_throwsETE() throws EmptyTreeException {
        binaryTree.root();
    }

    /**
     * Comprueba que el mÃ©todo root retorna correctamente la posiciÃ³n de la raÃ­z
     * del Ã¡rbol.
     */
    @Test
    public void root_notEmptyTree_returnsRoot()
            throws InvalidOperationException, EmptyTreeException, InvalidPositionException {
        binaryTree.createRoot(o1);
        Position<Object> root = binaryTree.root();
        assertEquals("El mÃ©todo root no retorna la raiz correctamente.", o1, root.element());
    }

    /**
     * Comprueba que el mÃ©todo parent lanza la excepciÃ³n
     * InvalidPositionException al recibir una posiciÃ³n nula.
     */
    @Test(expected = InvalidPositionException.class)
    public void parent_nullPosition_throwsIPE()
            throws InvalidPositionException, InvalidOperationException, BoundaryViolationException {
        binaryTree.createRoot(new Object());
        binaryTree.parent(null);
    }

    /**
     * Comprueba que el mÃ©todo parent lanza la excepciÃ³n
     * BoundaryViolationException al recibir la raÃ­z del Ã¡rbol.
     */
    @Test(expected = BoundaryViolationException.class)
    public void parent_onRoot_throwsBVE()
            throws InvalidPositionException, InvalidOperationException, BoundaryViolationException {
        Position<Object> root = binaryTree.createRoot(new Object());
        binaryTree.parent(root);
    }

    /**
     * Comprueba que el mÃ©todo parent retorna correctamente el padre de un nodo
     * dado
     */
    @Test
    public void parent_validPosition_returnsParent()
            throws InvalidPositionException, InvalidOperationException, BoundaryViolationException {
        Position<Object> parent = binaryTree.createRoot(new Object());
        binaryTree.addLeft(parent, new Object());
        assertEquals("El mÃ©todo parent no retorna correctamente el padre de un nodo.", parent,
                binaryTree.parent(binaryTree.left(parent)));
    }

    /**
     * Comprueba que el mÃ©todo children lanza la excepciÃ³n
     * InvalidPositionException al recibir una posiciÃ³n nula.
     */
    @Test(expected = InvalidPositionException.class)
    public void children_nullPosition_throwsIPE() throws InvalidPositionException, InvalidOperationException {
        binaryTree.createRoot(new Object());
        binaryTree.children(null);
    }

    /**
     * Comprueba que el mÃ©todo children retorna una colecciÃ³n vacÃ­a para un nodo
     * sin hijos.
     */
    @Test
    public void children_WithoutChildren_returnsEmptyCollection()
            throws InvalidOperationException, InvalidPositionException {
        Position<Object> positionWithoutChildren = binaryTree.createRoot(new Object());
        Iterable<Position<Object>> children = binaryTree.children(positionWithoutChildren);
        assertFalse("El mÃ©todo children no retorna una colecciÃ³n vacÃ­a para un nodo sin hijos.",
                children.iterator().hasNext());
    }

    /**
     * Comprueba que el mÃ©todo children retorna correctamente la colecciÃ³n de
     * hijos para un nodo con solo hijo izquierdo.
     */
    @Test
    public void children_onlyLeftChild_returnsCollectionWithLeftChild()
            throws InvalidOperationException, InvalidPositionException {
        Position<Object> positionWithLeftChild = binaryTree.createRoot(new Object());
        binaryTree.addLeft(positionWithLeftChild, o1);
        Iterator<Position<Object>> children = binaryTree.children(positionWithLeftChild).iterator();
        assertTrue("El mÃ©todo children retorna una colecciÃ³n vacÃ­a para un nodo con solo hijo izquierdo.",
                children.hasNext());
        assertEquals(
                "El mÃ©todo children no retorna correctamente el hijo izquierdo para un nodo con solo hijo izquierdo.",
                o1, children.next().element());
        assertFalse(
                "El mÃ©todo children no retorna una colecciÃ³n con un solo elemento para un nodo con solo hijo izquierdo.",
                children.hasNext());
    }

    /**
     * Comprueba que el mÃ©todo children retorna correctamente la colecciÃ³n de
     * hijos para un nodo con solo hijo derecho.
     */
    @Test
    public void children_onlyRightChild_returnsCollectionWithRightChild()
            throws InvalidOperationException, InvalidPositionException {
        Position<Object> positionWithRightChild = binaryTree.createRoot(new Object());
        binaryTree.addRight(positionWithRightChild, o1);
        Iterator<Position<Object>> children = binaryTree.children(positionWithRightChild).iterator();
        assertTrue("El mÃ©todo children retorna una colecciÃ³n vacÃ­a para un nodo con solo hijo derecho.",
                children.hasNext());
        assertEquals("El mÃ©todo children no retorna correctamente el hijo derecho para un nodo con solo hijo derecho.",
                o1, children.next().element());
        assertFalse(
                "El mÃ©todo children no retorna una colecciÃ³n con un solo elemento para un nodo con solo hijo derecho.",
                children.hasNext());
    }

    /**
     * Comprueba que el mÃ©todo children retorna correctamente la colecciÃ³n de
     * hijos para un nodo con solo hijo izquierdo.
     */
    @Test
    public void children_twoChildren_returnsCollectionWithChildren()
            throws InvalidOperationException, InvalidPositionException {
        Position<Object> positionWithChildren = binaryTree.createRoot(new Object());
        binaryTree.addLeft(positionWithChildren, o1);
        binaryTree.addRight(positionWithChildren, o2);
        Iterator<Position<Object>> children = binaryTree.children(positionWithChildren).iterator();
        assertTrue("El mÃ©todo children retorna una colecciÃ³n vacÃ­a para un nodo con dos hijos.", children.hasNext());
        assertEquals("El mÃ©todo children no retorna correctamente el hijo izquierdo para un nodo con dos hijos.", o1,
                children.next().element());
        assertTrue("El mÃ©todo children retorna una colecciÃ³n con solo un hijo para un nodo con dos hijos.",
                children.hasNext());
        assertEquals("El mÃ©todo children no retorna correctamente el hijo derecho para un nodo con dos hijos.", o2,
                children.next().element());
        assertFalse("El mÃ©todo children no retorna una colecciÃ³n con solo dos elementos para un nodo con dos hijos.",
                children.hasNext());
    }

    /**
     * Comprueba que el mÃ©todo isInternal lanza la excepciÃ³n
     * InvalidPositionException al recibir una posiciÃ³n nula.
     */
    @Test(expected = InvalidPositionException.class)
    public void isInternal_nullPosition_throwsIPE() throws InvalidPositionException, InvalidOperationException {
        binaryTree.createRoot(new Object());
        binaryTree.isInternal(null);
    }

    /**
     * Comprueba que el mÃ©todo isInternal retorna false en caso de recibir un
     * nodo externo.
     */
    @Test
    public void isInternal_onExternalNode_returnsFalse() throws InvalidOperationException, InvalidPositionException {
        Position<Object> externalPosition = binaryTree.createRoot(new Object());
        assertFalse("El mÃ©todo isInternal no retorna false con un nodo externo.",
                binaryTree.isInternal(externalPosition));
    }

    /**
     * Comprueba que el mÃ©todo isInternal retorna true en caso de recibir un
     * nodo interno.
     */
    @Test
    public void isInternal_onInternalNode_returnsTrue() throws InvalidOperationException, InvalidPositionException {
        Position<Object> internalPosition = binaryTree.createRoot(new Object());
        binaryTree.addLeft(internalPosition, new Object());
        assertTrue("El mÃ©todo isInternal no retorna false con un nodo externo.",
                binaryTree.isInternal(internalPosition));
    }

    /**
     * Comprueba que el mÃ©todo isExternal lanza la excepciÃ³n
     * InvalidPositionException al recibir una posiciÃ³n nula.
     */
    @Test(expected = InvalidPositionException.class)
    public void isExternal_nullPosition_throwsIPE() throws InvalidPositionException, InvalidOperationException {
        binaryTree.createRoot(new Object());
        binaryTree.isExternal(null);
    }

    /**
     * Comprueba que el mÃ©todo isExternal retorna false en caso de recibir un
     * nodo interno.
     */
    @Test
    public void isExternal_onInternalNode_returnsFalse() throws InvalidOperationException, InvalidPositionException {
        Position<Object> internalPosition = binaryTree.createRoot(o1);
        binaryTree.addLeft(internalPosition, new Object());
        assertFalse("El mÃ©todo isExternal no retorna false con un nodo interno.",
                binaryTree.isExternal(internalPosition));
    }

    /**
     * Comprueba que el mÃ©todo isExternal retorna true en caso de recibir un
     * nodo externo.
     */
    @Test
    public void isExternal_onExternalNode_returnsTrue() throws InvalidOperationException, InvalidPositionException {
        Position<Object> externalPosition = binaryTree.createRoot(new Object());
        assertTrue("El mÃ©todo isExternal no retorna true con un nodo externo.",
                binaryTree.isExternal(externalPosition));
    }

    /**
     * Comprueba que el mÃ©todo isRoot lanza la excepciÃ³n
     * InvalidPositionException al recibir una posiciÃ³n nula.
     */
    @Test(expected = InvalidPositionException.class)
    public void isRoot_nullPosition_throwsIPE() throws InvalidPositionException, InvalidOperationException {
        binaryTree.createRoot(new Object());
        binaryTree.isRoot(null);
    }

    /**
     * Comprueba que el mÃ©todo isRoot retorna true al ser llamado con la raÃ­z
     * del Ã¡rbol.
     */
    @Test
    public void isRoot_onRoot_returnsTrue() throws InvalidOperationException, InvalidPositionException {
        Position<Object> root = binaryTree.createRoot(new Object());
        assertTrue("El mÃ©todo isRoot no retorna true al ser llamado con la raÃ­z.", binaryTree.isRoot(root));
    }

    /**
     * Comprueba que el mÃ©todo isRoot retorna false al ser llamado con un nodo
     * distinto a la raÃ­z del Ã¡rbol.
     */
    @Test
    public void isRoot_notOnRoot_returnsFalse() throws InvalidOperationException, InvalidPositionException {
        Position<Object> root = binaryTree.createRoot(new Object());
        Position<Object> notRoot = binaryTree.addLeft(root, new Object());
        assertFalse("El mÃ©todo isRoot no retorna false al ser llamado con un nodo distinto a la raÃ­z.",
                binaryTree.isRoot(notRoot));
    }

    /**
     * Comprueba que el mÃ©todo left lanza la excepciÃ³n InvalidPositionException
     * al recibir una posiciÃ³n nula.
     */
    @Test(expected = InvalidPositionException.class)
    public void left_nullPosition_throwsIPE()
            throws InvalidPositionException, InvalidOperationException, BoundaryViolationException {
        binaryTree.createRoot(new Object());
        binaryTree.left(null);
    }

    /**
     * Comprueba que el mÃ©todo left lanza la excepciÃ³n
     * BoundaryViolationException al recibir una posiciÃ³n que no tiene hijo
     * izquierdo ni derecho.
     */
    @Test(expected = BoundaryViolationException.class)
    public void left_positionWithoutChildren_throwsBVE()
            throws InvalidPositionException, InvalidOperationException, BoundaryViolationException {
        Position<Object> positionWithoutLeftChild = binaryTree.createRoot(new Object());
        binaryTree.left(positionWithoutLeftChild);
    }

    /**
     * Comprueba que el mÃ©todo left lanza la excepciÃ³n
     * BoundaryViolationException al recibir una posiciÃ³n que no tiene hijo
     * izquierdo pero tiene hijo derecho.
     */
    @Test(expected = BoundaryViolationException.class)
    public void left_positionWithoutLeftChild_throwsBVE()
            throws InvalidPositionException, InvalidOperationException, BoundaryViolationException {
        Position<Object> positionWithoutLeftChild = binaryTree.createRoot(new Object());
        binaryTree.addRight(positionWithoutLeftChild, new Object());
        binaryTree.left(positionWithoutLeftChild);
    }

    /**
     * Comprueba que el mÃ©todo left retorna correctamente la posiciÃ³n del hijo
     * izquierdo de un nodo que solo tiene hijo izquierdo.
     */
    @Test
    public void left_positionWithOnlyLeftChild_returnsLeftChild()
            throws InvalidOperationException, InvalidPositionException, BoundaryViolationException {
        Position<Object> positionWithLeftChild = binaryTree.createRoot(new Object());
        binaryTree.addLeft(positionWithLeftChild, o1);
        assertEquals(
                "El mÃ©todo left no retorna correctamente el hijo izquierdo de un nodo que tiene solo hijo izquierdo.",
                o1, binaryTree.left(positionWithLeftChild).element());
    }

    /**
     * Comprueba que el mÃ©todo left retorna correctamente la posiciÃ³n del hijo
     * izquierdo de un nodo que tiene hijo izquierdo y derecho.
     */
    @Test
    public void left_positionWithTwoChilds_returnsLeftChild()
            throws InvalidOperationException, InvalidPositionException, BoundaryViolationException {
        Position<Object> positionWithTwoChilds = binaryTree.createRoot(new Object());
        binaryTree.addLeft(positionWithTwoChilds, o1);
        binaryTree.addRight(positionWithTwoChilds, new Object());
        assertEquals(
                "El mÃ©todo left no retorna correctamente el hijo izquierdo de un nodo que tiene hijo izquierdo y derecho.",
                o1, binaryTree.left(positionWithTwoChilds).element());
    }

    /**
     * Comprueba que el mÃ©todo right lanza la excepciÃ³n InvalidPositionException
     * al recibir una posiciÃ³n nula.
     */
    @Test(expected = InvalidPositionException.class)
    public void right_nullPosition_throwsIPE()
            throws InvalidPositionException, InvalidOperationException, BoundaryViolationException {
        binaryTree.createRoot(new Object());
        binaryTree.right(null);
    }

    /**
     * Comprueba que el mÃ©todo right lanza la excepciÃ³n
     * BoundaryViolationException al recibir una posiciÃ³n que no tiene hijo
     * derecho ni izquierdo.
     */
    @Test(expected = BoundaryViolationException.class)
    public void right_positionWithoutChildren_throwsBVE()
            throws InvalidPositionException, InvalidOperationException, BoundaryViolationException {
        Position<Object> positionWithoutChildren = binaryTree.createRoot(new Object());
        binaryTree.right(positionWithoutChildren);
    }

    /**
     * Comprueba que el mÃ©todo right lanza la excepciÃ³n
     * BoundaryViolationException al recibir una posiciÃ³n que no tiene hijo
     * derecho pero tiene hijo izquierdo.
     */
    @Test(expected = BoundaryViolationException.class)
    public void right_positionWithoutRightChild_throwsBVE()
            throws InvalidPositionException, InvalidOperationException, BoundaryViolationException {
        Position<Object> positionWithoutRightChild = binaryTree.createRoot(new Object());
        binaryTree.addLeft(positionWithoutRightChild, new Object());
        binaryTree.right(positionWithoutRightChild);
    }

    /**
     * Comprueba que el mÃ©todo right retorna correctamente la posiciÃ³n del hijo
     * derecho de un nodo que solo tiene hijo derecho.
     */
    @Test
    public void right_positionWithOnlyRightChild_returnsRightChild()
            throws InvalidOperationException, InvalidPositionException, BoundaryViolationException {
        Position<Object> positionWithRightChild = binaryTree.createRoot(new Object());
        binaryTree.addRight(positionWithRightChild, o1);
        assertEquals("El mÃ©todo right no retorna correctamente el hijo derecho de un nodo que tiene solo hijo derecho.",
                o1, binaryTree.right(positionWithRightChild).element());
    }

    /**
     * Comprueba que el mÃ©todo right retorna correctamente la posiciÃ³n del hijo
     * derecho de un nodo que tiene hijo izquierdo y derecho.
     */
    @Test
    public void right_positionWithTwoChilds_returnsRightChild()
            throws InvalidOperationException, InvalidPositionException, BoundaryViolationException {
        Position<Object> positionWithTwoChilds = binaryTree.createRoot(new Object());
        binaryTree.addLeft(positionWithTwoChilds, new Object());
        binaryTree.addRight(positionWithTwoChilds, o1);
        assertEquals(
                "El mÃ©todo right no retorna correctamente el hijo derecho de un nodo que tiene hijo izquierdo y derecho.",
                o1, binaryTree.right(positionWithTwoChilds).element());
    }

    /**
     * Comprueba que el mÃ©todo hasLeft lanza la excepciÃ³n
     * InvalidPositionException al recibir una posiciÃ³n nula.
     */
    @Test(expected = InvalidPositionException.class)
    public void hasLeft_nullPosition_throwsIPE() throws InvalidPositionException, InvalidOperationException {
        binaryTree.createRoot(new Object());
        binaryTree.hasLeft(null);
    }

    /**
     * Comprueba que el mÃ©todo hasLeft retorna true con un nodo que solo tiene
     * hijo izquierdo.
     */
    @Test
    public void hasLeft_onlyLeftChild_returnsTrue() throws InvalidPositionException, InvalidOperationException {
        Position<Object> nodeWithLeftChild = binaryTree.createRoot(new Object());
        binaryTree.addLeft(nodeWithLeftChild, new Object());
        assertTrue("El mÃ©todo hasLeft no retorna true con un nodo que solo tiene hijo izquierdo.",
                binaryTree.hasLeft(nodeWithLeftChild));
    }

    /**
     * Comprueba que el mÃ©todo hasLeft retorna true con un nodo que tiene hijo
     * izquierdo y derecho.
     */
    @Test
    public void hasLeft_leftAndRightChildren_returnsTrue() throws InvalidPositionException, InvalidOperationException {
        Position<Object> nodeWithChildren = binaryTree.createRoot(new Object());
        binaryTree.addLeft(nodeWithChildren, new Object());
        binaryTree.addRight(nodeWithChildren, new Object());
        assertTrue("El mÃ©todo hasLeft no retorna true con un nodo que tiene hijo izquierdo y derecho.",
                binaryTree.hasLeft(nodeWithChildren));
    }

    /**
     * Comprueba que el mÃ©todo hasLeft retorna false con un nodo que no tiene
     * hijo izquierdo pero tiene hijo derecho.
     */
    @Test
    public void hasLeft_onlyRightChild_returnsFalse() throws InvalidPositionException, InvalidOperationException {
        Position<Object> nodeWithRightChild = binaryTree.createRoot(new Object());
        binaryTree.addRight(nodeWithRightChild, new Object());
        assertFalse("El mÃ©todo hasLeft no retorna false con un nodo que solo tiene hijo derecho.",
                binaryTree.hasLeft(nodeWithRightChild));
    }

    /**
     * Comprueba que el mÃ©todo hasLeft retorna false con un nodo que no tiene
     * hijos.
     */
    @Test
    public void hasLeft_withoutChildren_returnsFalse() throws InvalidPositionException, InvalidOperationException {
        Position<Object> nodeWithoutChildren = binaryTree.createRoot(new Object());
        assertFalse("El mÃ©todo hasLeft no retorna false con un nodo que no tiene hijos.",
                binaryTree.hasLeft(nodeWithoutChildren));
    }

    /**
     * Comprueba que el mÃ©todo hasRight lanza la excepciÃ³n
     * InvalidPositionException al recibir una posiciÃ³n nula.
     */
    @Test(expected = InvalidPositionException.class)
    public void hasRight_nullPosition_throwsIPE() throws InvalidPositionException, InvalidOperationException {
        binaryTree.createRoot(new Object());
        binaryTree.hasRight(null);
    }

    /**
     * Comprueba que el mÃ©todo hasRight retorna true con un nodo que solo tiene
     * hijo derecho.
     */
    @Test
    public void hasRight_onlyRightChild_returnsTrue() throws InvalidPositionException, InvalidOperationException {
        Position<Object> nodeWithRightChild = binaryTree.createRoot(new Object());
        binaryTree.addRight(nodeWithRightChild, new Object());
        assertTrue("El mÃ©todo hasRight no retorna true con un nodo que solo tiene hijo derecho.",
                binaryTree.hasRight(nodeWithRightChild));
    }

    /**
     * Comprueba que el mÃ©todo hasRight retorna true con un nodo que tiene hijo
     * izquierdo y derecho.
     */
    @Test
    public void hasRight_leftAndRightChildren_returnsTrue() throws InvalidPositionException, InvalidOperationException {
        Position<Object> nodeWithChildren = binaryTree.createRoot(new Object());
        binaryTree.addLeft(nodeWithChildren, new Object());
        binaryTree.addRight(nodeWithChildren, new Object());
        assertTrue("El mÃ©todo hasRight no retorna true con un nodo que tiene hijo izquierdo y derecho.",
                binaryTree.hasRight(nodeWithChildren));
    }

    /**
     * Comprueba que el mÃ©todo hasRight retorna false con un nodo que no tiene
     * hijo derecho pero tiene hijo izquierdo.
     */
    @Test
    public void hasRight_onlyLeftChild_returnsFalse() throws InvalidPositionException, InvalidOperationException {
        Position<Object> nodeWithLeftChild = binaryTree.createRoot(new Object());
        binaryTree.addLeft(nodeWithLeftChild, new Object());
        assertFalse("El mÃ©todo hasRight no retorna false con un nodo que solo tiene hijo izquierdo.",
                binaryTree.hasRight(nodeWithLeftChild));
    }

    /**
     * Comprueba que el mÃ©todo hasRight retorna false con un nodo que no tiene
     * hijos.
     */
    @Test
    public void hasRight_withoutChildren_returnsFalse() throws InvalidPositionException, InvalidOperationException {
        Position<Object> nodeWithoutChildren = binaryTree.createRoot(new Object());
        assertFalse("El mÃ©todo hasRight no retorna false con un nodo que no tiene hijos.",
                binaryTree.hasRight(nodeWithoutChildren));
    }

    /**
     * Comprueba que el mÃ©todo createRoot lanza la excepciÃ³n
     * InvalidOperationException cuando se invoca sobre un Ã¡rbol que ya tiene
     * raÃ­z.
     */
    @Test(expected = InvalidOperationException.class)
    public void createRoot_existingRoot_throwsIOE() throws InvalidOperationException {
        binaryTree.createRoot(new Object());
        binaryTree.createRoot(new Object());
    }

    /**
     * Comprueba que el mÃ©todo createRoot retorna correctamente la posiciÃ³n con
     * el elemento pasado por parÃ¡metro.
     */
    @Test
    public void createRoot_emptyTree_createsRoot() throws InvalidOperationException {
        Position<Object> root = binaryTree.createRoot(o1);
        assertEquals("El mÃ©todo createRoot retorna una posiciÃ³n que no contiene el elemento pasado por parÃ¡metro.", o1,
                root.element());
    }

    /**
     * Comprueba que el tamaÃ±o del Ã¡rbol es 1 luego de invocar a createRoot.
     */
    @Test
    public void createRoot_emptyTree_treeSizeEqualsOne() throws InvalidOperationException {
        binaryTree.createRoot(new Object());
        assertEquals("El tamaÃ±o del Ã¡rbol no es 1 luego de invocar a createRoot.", 1, binaryTree.size());
    }

    /**
     * Comprueba que el mÃ©todo addLeft lanza InvalidPositionException con una
     * posiciÃ³n nula.
     */
    @Test(expected = InvalidPositionException.class)
    public void addLeft_nullPosition_throwsIPE() throws InvalidPositionException, InvalidOperationException {
        binaryTree.addLeft(null, new Object());
    }

    /**
     * Comprueba que el mÃ©todo addLeft lanza InvalidOperationException sobre un
     * nodo que ya tiene hijo izquierdo.
     */
    @Test(expected = InvalidOperationException.class)
    public void addLeft_nodeWithLeftChild_throwsIOE()
            throws InvalidOperationException, InvalidPositionException, EmptyTreeException {
        Position<Object> root = binaryTree.createRoot(new Object());
        binaryTree.addLeft(root, new Object());
        binaryTree.addLeft(root, new Object());
    }

    /**
     * Comprueba que el mÃ©todo addLeft retorna correctamente el hijo aÃ±adido.
     */
    @Test
    public void addLeft_normalBehavior_returnsNewNode()
            throws EmptyTreeException, InvalidPositionException, InvalidOperationException {
        Position<Object> root = binaryTree.createRoot(new Object());
        Position<Object> leftChild = binaryTree.addLeft(root, o1);
        assertEquals("El mÃ©todo addLeft no retorna el elemento correcto.", o1, leftChild.element());
    }

    /**
     * Comprueba que luego del mÃ©todo addLeft el nuevo nodo fue agregado al
     * Ã¡rbol.
     */
    @Test
    public void addLeft_normalBehavior_newNodeExistent()
            throws EmptyTreeException, InvalidPositionException, InvalidOperationException {
        Position<Object> root = binaryTree.createRoot(o1);
        binaryTree.addLeft(root, o2);
        Iterator<Object> nodesIterator = binaryTree.iterator();
        boolean located = false;
        while (nodesIterator.hasNext()) {
            if (nodesIterator.next().equals(o2)) {
                located = true;
                break;
            }
        }
        assertTrue(
                "Luego de aÃ±adir un nodo mediante el mÃ©todo addLeft, el mismo no estÃ¡ presente en el Ã¡rbol segÃºn el mÃ©todo iterator.",
                located);
    }

    /**
     * Comprueba que el mÃ©todo addLeft incrementa el tamaÃ±o del Ã¡rbol.
     */
    @Test
    public void addLeft_emptyTree_incrementsSize()
            throws EmptyTreeException, InvalidOperationException, InvalidPositionException {
        Position<Object> root = binaryTree.createRoot(o1);
        binaryTree.addLeft(root, o2);
        assertEquals(
                "Luego de aÃ±adir un nodo mediante el mÃ©todo addLeft a un Ã¡rbol con solo 1 elemento el tamaÃ±o no es dos.",
                2, binaryTree.size());
    }

    /**
     * Comprueba que el mÃ©todo addRight lanza InvalidPositionException con una
     * posiciÃ³n nula.
     */
    @Test(expected = InvalidPositionException.class)
    public void addRight_nullPosition_throwsIPE() throws InvalidPositionException, InvalidOperationException {
        binaryTree.addRight(null, new Object());
    }

    /**
     * Comprueba que el mÃ©todo addRight lanza InvalidOperationException sobre un
     * nodo que ya tiene hijo derecho.
     */
    @Test(expected = InvalidOperationException.class)
    public void addRight_nodeWithRightChild_throwsIOE()
            throws InvalidOperationException, InvalidPositionException, EmptyTreeException {
        Position<Object> root = binaryTree.createRoot(new Object());
        binaryTree.addRight(root, new Object());
        binaryTree.addRight(root, new Object());
    }

    /**
     * Comprueba que el mÃ©todo addRight retorna correctamente el hijo aÃ±adido.
     */
    @Test
    public void addRight_normalBehavior_returnsNewNode()
            throws EmptyTreeException, InvalidPositionException, InvalidOperationException {
        Position<Object> root = binaryTree.createRoot(new Object());
        Position<Object> rightChild = binaryTree.addRight(root, o1);
        assertEquals("El mÃ©todo addRight no retorna el elemento correcto.", o1, rightChild.element());
    }

    /**
     * Comprueba que luego del mÃ©todo addRight el nuevo nodo fue agregado al
     * Ã¡rbol.
     */
    @Test
    public void addRight_normalBehavior_newNodeExistent()
            throws EmptyTreeException, InvalidPositionException, InvalidOperationException {
        Position<Object> root = binaryTree.createRoot(o1);
        binaryTree.addRight(root, o2);
        Iterator<Object> nodesIterator = binaryTree.iterator();
        boolean located = false;
        while (nodesIterator.hasNext()) {
            if (nodesIterator.next().equals(o2)) {
                located = true;
                break;
            }
        }
        assertTrue(
                "Luego de aÃ±adir un nodo mediante el mÃ©todo addRight, el mismo no estÃ¡ presente en el Ã¡rbol segÃºn el mÃ©todo iterator.",
                located);
    }

    /**
     * Comprueba que el mÃ©todo addRight incrementa el tamaÃ±o del Ã¡rbol.
     */
    @Test
    public void addRight_emptyTree_incrementsSize()
            throws EmptyTreeException, InvalidOperationException, InvalidPositionException {
        Position<Object> root = binaryTree.createRoot(o1);
        binaryTree.addRight(root, o2);
        assertEquals(
                "Luego de aÃ±adir un nodo mediante el mÃ©todo addRight a un Ã¡rbol con solo un elemento el tamaÃ±o no es dos.",
                2, binaryTree.size());
    }

    /**
     * Comprueba que el mÃ©todo remove lanza InvalidPositionException con una
     * posiciÃ³n nula.
     */
    @Test(expected = InvalidPositionException.class)
    public void remove_nullPosition_throwsIPE() throws InvalidPositionException, InvalidOperationException {
        binaryTree.remove(null);
    }

    /**
     * Comprueba que el mÃ©todo remove lanza InvalidOperationException sobre un
     * nodo con dos hijos.
     */
    @Test(expected = InvalidOperationException.class)
    public void remove_twoChildren_throwsIOE()
            throws InvalidOperationException, EmptyTreeException, InvalidPositionException {
        Position<Object> root = binaryTree.createRoot(new Object());
        binaryTree.addLeft(root, new Object());
        binaryTree.addRight(root, new Object());
        binaryTree.remove(root);
    }

    /**
     * Comprueba que el mÃ©todo remove retorna correctamente el elemento
     * eliminado.
     */
    @Test
    public void remove_validPosition_returnsRemovedElement()
            throws EmptyTreeException, InvalidOperationException, InvalidPositionException {
        Position<Object> root = binaryTree.createRoot(o1);
        assertEquals("El mÃ©todo remove no retorna el elemento eliminado correctamente.", o1, binaryTree.remove(root));
    }

    /**
     * Comprueba que luego de invocar al mÃ©todo remove el nodo a eliminar ya no
     * aparece en el listado de nodos del Ã¡rbol.
     */
    @Test
    public void remove_validPosition_removedNode()
            throws EmptyTreeException, InvalidOperationException, InvalidPositionException {
        Position<Object> root = binaryTree.createRoot(new Object());
        binaryTree.remove(root);
        Iterator<Object> elements = binaryTree.iterator();
        assertFalse("El mÃ©todo iterator no retorna un iterador vacÃ­o luego de eliminar el Ãºnico nodo del Ã¡rbol.",
                elements.hasNext());
    }

    /**
     * Comprueba que luego de invocar al mÃ©todo remove sobre un nodo hoja, el
     * nodo eliminado ya no aparece en la lista de hijos del padre.
     */
    @Test
    public void remove_leafNode_removedFromParentChildrenList()
            throws EmptyTreeException, InvalidOperationException, InvalidPositionException {
        Position<Object> root = binaryTree.createRoot(new Object());
        Position<Object> leafChild = binaryTree.addLeft(root, new Object());
        binaryTree.remove(leafChild);
        Iterator<Position<Object>> parentChildren = binaryTree.children(root).iterator();
        assertFalse("El mÃ©todo children no retorna una colecciÃ³n vacÃ­a luego de eliminar el Ãºnico hijo de un nodo.",
                parentChildren.hasNext());
    }

    /**
     * Comprueba que luego de invocar al mÃ©todo remove sobre un nodo interno, el
     * nodo eliminado es reemplazado por su hijo en la lista de hijos del padre.
     */
    @Test
    public void remove_internalNode_replacedInParentChildrenList()
            throws EmptyTreeException, InvalidOperationException, InvalidPositionException {
        Position<Object> root = binaryTree.createRoot(new Object());
        Position<Object> internalNode = binaryTree.addLeft(root, new Object());
        binaryTree.addRight(internalNode, o1);
        binaryTree.remove(internalNode);
        Iterator<Position<Object>> parentChildren = binaryTree.children(binaryTree.root()).iterator();
        assertTrue(
                "Luego de invocar al mÃ©todo remove sobre un nodo interno, la lista de hijos del padre estÃ¡ vacÃ­a y no se hizo el reemplazo adecuado por el nodo eliminado.",
                parentChildren.hasNext());
        assertEquals(
                "Luego de invocar al mÃ©todo remove para eliminar un nodo interno, el nodo que lo reemplazÃ³ en la jerarquÃ­a no es el correcto.",
                o1, parentChildren.next().element());
        assertFalse(
                "Luego de invocar al mÃ©todo remove sobre un nodo interno, Ãºnico hijo de su padre, la colecciÃ³n de hijos del padre tiene mÃ¡s de un elemento.",
                parentChildren.hasNext());
    }

    /**
     * Comprueba que el mÃ©todo attach lanza InvalidPositionException con una
     * posiciÃ³n nula.
     */
    @Test(expected = InvalidPositionException.class)
    public void attach_nullPosition_throwsIPE() throws InvalidPositionException, InvalidOperationException {
        binaryTree.attach(null, leftTree, rightTree);
    }

    /**
     * Comprueba que el mÃ©todo attach lanza InvalidPositionException si es
     * invocado sobre un nodo interno.
     */
    @Test(expected = InvalidPositionException.class)
    public void Attach_internalNode_throwsIPE()
            throws InvalidPositionException, EmptyTreeException, InvalidOperationException {
        binaryTree.createRoot(new Object());
        Position<Object> internalNode = binaryTree.addLeft(binaryTree.root(), new Object());
        binaryTree.addLeft(internalNode, new Object());
        binaryTree.attach(internalNode, leftTree, rightTree);
    }

    /**
     * Comprueba que luego de un attach el tamaÃ±o del Ã¡rbol se incrementa acorde
     * a los subÃ¡rboles aÃ±adidos.
     */
    @Test
    public void Attach_normalBehavior_sizeIncremented()
            throws EmptyTreeException, InvalidOperationException, InvalidPositionException {
        binaryTree.createRoot(new Object());
        leftTree.createRoot(new Object());
        rightTree.createRoot(new Object());
        leftTree.addLeft(leftTree.root(), new Object());
        binaryTree.attach(binaryTree.root(), leftTree, rightTree);

        Iterator<Object> iteradorNodos = binaryTree.iterator();
        int cantNodos = 0;
        while (iteradorNodos.hasNext()) {
            cantNodos++;
            iteradorNodos.next();
        }
        assertEquals("El mÃ©todo attach no actualiza el tamaÃ±o correctamente.", 4, cantNodos);
    }

    /**
     * Comprueba que luego de un attach el Ã¡rbol tiene la nueva estructura.
     */
    @Test
    public void Attach_normalBehavior_nodesAttached()
            throws EmptyTreeException, InvalidOperationException, InvalidPositionException, BoundaryViolationException {
        Position<Object> root = binaryTree.createRoot(new Object());
        Position<Object> rootT1 = leftTree.createRoot(new Object());
        Position<Object> rootT2 = rightTree.createRoot(new Object());
        leftTree.addLeft(leftTree.root(), new Object());
        binaryTree.attach(root, leftTree, rightTree);

        assertEquals("El mÃ©todo attach no vincula correctamente el subÃ¡rbol izquierdo.", rootT1.element(), binaryTree.left(root).element());
        assertEquals("El mÃ©todo attach no vincula correctamente el subÃ¡rbol izquierdo.", rootT2.element(),
                binaryTree.right(root).element());
    }

}