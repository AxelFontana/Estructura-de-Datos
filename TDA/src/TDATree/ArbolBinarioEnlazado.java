package TDATree;
import TDALista.*;
import TDACola.*;
import ImplementacionesListas.*;
import java.util.Iterator;

import Exceptions.*;
public class ArbolBinarioEnlazado<E> implements BinaryTree<E> {
	protected BTPosition<E> raiz;
	protected int size;
	
	public ArbolBinarioEnlazado() {
		raiz = null;
		size = 0;
	}
	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public Iterator<E> iterator() {
		Iterable<Position<E>> positions = positions();
		PositionList<E> elements = new DoubleLinkedList<E>();
		for(Position<E> pos: positions) {
			elements.addLast(pos.element());
		}
		
		return elements.iterator();
	}

	@Override
	public Iterable<Position<E>> positions() {
		PositionList<Position<E>> positions = new DoubleLinkedList<Position<E>>();
		if(size != 0) {
			try {
				preorderPositions(root(), positions);
			} catch (InvalidPositionException | EmptyTreeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return positions;
	}
	
	protected BTPosition<E> checkPosition(Position<E> v) throws InvalidPositionException{
		if(v == null || !(v instanceof BTPosition)) throw new InvalidPositionException("Posición inválida");
		return(BTPosition<E>) v;
	}
	
	protected void preorderPositions(Position<E> v, PositionList<Position<E>> pos) throws InvalidPositionException{
		pos.addLast(v);
			try {
				if(hasLeft(v))
					preorderPositions(left(v), pos);
				if(hasRight(v))
					preorderPositions(right(v), pos);
			} catch (BoundaryViolationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}

	@Override
	public E replace(Position<E> v, E e) throws InvalidPositionException {
		BTPosition<E> n = checkPosition(v);
		E toReturn = v.element();
		n.setElement(e);
		return toReturn;
	}

	@Override
	public Position<E> root() throws EmptyTreeException {
		if(isEmpty()) throw new EmptyTreeException("Árbol vacío");
		return raiz;
	}

	@Override
	public Position<E> parent(Position<E> v) throws InvalidPositionException, BoundaryViolationException {
		BTPosition<E> n = checkPosition(v);
		Position<E> parentPos = n.getParent();
		if(parentPos == null) throw new BoundaryViolationException("No tiene padre");
		return parentPos;
	}

	@Override
	public Iterable<Position<E>> children(Position<E> v) throws InvalidPositionException {
		PositionList<Position<E>> children = new DoubleLinkedList<Position<E>>();
		try {
			if(hasLeft(v))
				children.addLast(left(v));
			if(hasRight(v))
				children.addLast(right(v));
			} catch (BoundaryViolationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return children;
	}

	@Override
	public boolean isInternal(Position<E> v) throws InvalidPositionException {
		checkPosition(v);
		return(hasLeft(v) || hasRight(v));
	}

	@Override
	public boolean isExternal(Position<E> v) throws InvalidPositionException {
		checkPosition(v);
		return(!hasLeft(v) && !hasRight(v));
	}

	@Override
	public boolean isRoot(Position<E> v) throws InvalidPositionException {
		BTPosition<E> n = checkPosition(v);
		return n == raiz;
	}

	@Override
	public Position<E> left(Position<E> v) throws InvalidPositionException, BoundaryViolationException {
		BTPosition<E> n = checkPosition(v);
		Position<E> izq = n.getLeft();
		if(izq == null) throw new BoundaryViolationException("No tiene hijo izquierdo");
		return izq;
	}

	@Override
	public Position<E> right(Position<E> v) throws InvalidPositionException, BoundaryViolationException {
		BTPosition<E> n = checkPosition(v);
		Position<E> der = n.getRight();
		if(der == null) throw new BoundaryViolationException("No tiene hijo derecho");
		return der;
	}

	@Override
	public boolean hasLeft(Position<E> v) throws InvalidPositionException {
		BTPosition<E> n = checkPosition(v);
		return n.getLeft() != null;
	}

	@Override
	public boolean hasRight(Position<E> v) throws InvalidPositionException {
		BTPosition<E> n = checkPosition(v);
		return n.getRight() != null;	
	}

	@Override
	public Position<E> createRoot(E r) throws InvalidOperationException {
		if(!isEmpty()) throw new InvalidOperationException("Árbol no vacío");
		size = 1;
		raiz = createNodo(r,null,null,null);
		return raiz;
	}
	
	protected BTPosition<E> createNodo(E e, BTPosition<E> parent, BTPosition<E> right, BTPosition<E> left){
		return new BTNodo<E>(e, parent, left, right);
	}

	@Override
	public Position<E> addLeft(Position<E> v, E r) throws InvalidOperationException, InvalidPositionException {
		BTPosition<E> n = checkPosition(v);
		Position<E> izq = n.getLeft();
		if(izq != null) throw new InvalidOperationException("El nodo ya tiene hijo izquierdo");
		BTPosition<E> hijoIzq = createNodo(r,n,null,null);
		n.setLeft(hijoIzq);
		size++;
		return hijoIzq;
	}

	@Override
	public Position<E> addRight(Position<E> v, E r) throws InvalidOperationException, InvalidPositionException {
		BTPosition<E> n = checkPosition(v);
		Position<E> der = n.getRight();
		if(der != null) throw new InvalidOperationException("El nodo ya tiene hijo derecho");
		BTPosition<E> hijoDer = createNodo(r,n,null,null);
		n.setRight(hijoDer);
		size++;
		return hijoDer;
	}

	@Override
	public E remove(Position<E> v) throws InvalidOperationException, InvalidPositionException {
		BTPosition<E> n = checkPosition(v);
		BTPosition<E> izq = n.getLeft();
		BTPosition<E> der = n.getRight();
		if(izq != null && der != null) throw new InvalidOperationException("Nodo con más de un hijo");
		BTPosition<E> hijo;
		if(izq != null)
			hijo = izq;
		else {
			if(der != null)
				hijo = der;
			else
				hijo = null;
		}
		
		if(n == raiz) {
			if(hijo != null)
				hijo.setParent(null);
			raiz = hijo;
		}
		else {
			BTPosition<E> padre = n.getParent();
			if(n == padre.getLeft())
				padre.setLeft(hijo);
			else
				padre.setRight(hijo);
			if(hijo != null)
				hijo.setParent(padre);
		}
		
		size--;
		return v.element();
	}

	@Override
	public void Attach(Position<E> r, BinaryTree<E> T1, BinaryTree<E> T2) throws InvalidPositionException {
		BTPosition<E> padre = checkPosition(r);
		if(isInternal(padre)) throw new InvalidPositionException("El nodo ya tiene hijos");
		try {
			if(!T1.isEmpty()) {
				BTPosition<E> t1 = checkPosition(T1.root());
				padre.setLeft(t1);
				t1.setParent(padre);
			}
			if(!T2.isEmpty()) {
				BTPosition<E> t2 = checkPosition(T2.root());
				padre.setRight(t2);
				t2.setParent(padre);
			}
		}
		catch(EmptyTreeException e) {
			e.printStackTrace();
		}
		
	}
	
	public BinaryTree<E> clone(){
		BinaryTree<E> nuevo = new ArbolBinarioEnlazado<E>();
		try {
			if(!isEmpty()) {
				nuevo.createRoot(raiz.element());
				cloneAux(nuevo,raiz,nuevo.root());
			}
		}
		catch(EmptyTreeException | InvalidOperationException e) {
			e.printStackTrace();
		}
		return nuevo;
	}
	
	private void cloneAux(BinaryTree<E> n, Position<E> raiz, Position<E> raizNuevo) {
		Position<E> p1Nuevo,p2Nuevo;
		Position<E> p1, p2;
		try {
			if(hasLeft(raiz)) {
				p1 = left(raiz);
				p1Nuevo = n.addLeft(raizNuevo, left(raiz).element());
				cloneAux(n,p1,p1Nuevo);
			}
			
			if(hasRight(raiz)) {
				p2 = right(raiz);
				p2Nuevo = n.addRight(raizNuevo, right(raiz).element());
				cloneAux(n,p2,p2Nuevo);
			}		
		}
		catch(BoundaryViolationException | InvalidPositionException | InvalidOperationException e) {
			e.printStackTrace();
		}
	}
	
	public int cantNodos() {
		int cant = 0;
		
			if(raiz != null) {
				cant++;
				cant+=cantNodosRec(this, raiz);
			}
			
		return cant;
	}
	
	private int cantNodosRec(BinaryTree<E> T, BTPosition<E> r) {
		int cant = 0;
		
			if(r.getLeft() != null) {
				cant++;
				cantNodosRec(T,r.getLeft());
			}
			
			if(r.getRight() != null) {
				cant++;
				cantNodosRec(T,r.getRight());
			}
		
		return cant;
	}
	
	public String imprimirPorNiveles() {
		String cad = "";
		try {
			Queue<BTPosition<E>> cola = new ArrayQueue<BTPosition<E>>();
			cola.enqueue(raiz);
			cola.enqueue(null);
			while(!cola.isEmpty()) {
				BTPosition<E> n = cola.dequeue();
				if(n!=null) {
					cad+=" "+n.element().toString()+" ";
					if(n.getLeft() != null) {
						cola.enqueue(n.getLeft());
					}
					if(n.getRight() != null) {
						cola.enqueue(n.getRight());
					}
				}
				else {
					cad+="\n";
					if(!cola.isEmpty())
						cola.enqueue(null);
				}
			}
		}
		catch(EmptyQueueException e) {
			e.printStackTrace();
		}
		return cad;
	}

}
