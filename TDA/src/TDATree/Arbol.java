package TDATree;
import TDALista.*;
import TDAPila.*;
import ImplementacionesListas.*;
import java.util.Iterator;
import Exceptions.*;
public class Arbol<E> implements Tree<E> {
	protected int size;
	protected TNodo<E> raiz;
	
	public Arbol() {
		raiz = null;
		size = 0;
	}
	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return raiz == null;
	}

	@Override
	public Iterator<E> iterator() {
		PositionList<E> l = new DoubleLinkedList<E>();
		for(Position<E> p: positions()) {
			l.addLast(p.element());
		}
		return l.iterator();
	}

	@Override
	public Iterable<Position<E>> positions() {
		PositionList<Position<E>> l= new DoubleLinkedList<Position<E>>();
		if(!isEmpty())
			pre(l, raiz);
		return l;
	}
	
	private void pre(PositionList<Position<E>> l, TNodo<E> r) {
		l.addLast(r);
		for(TNodo<E> h: r.getHijos()) {
			pre(l,h);
		}
	}
	

	@Override
	public E replace(Position<E> v, E e) throws InvalidPositionException {
		TNodo<E> n = checkPosition(v);
		E toReturn = n.element();
		n.setElemento(e);
		return toReturn;
	}
	
	private TNodo<E> checkPosition(Position<E> p) throws InvalidPositionException{
		try {
			if(p==null) throw new InvalidPositionException("Posición nula");
			else {
				return(TNodo<E>) p;
			}
		}
		catch(ClassCastException e) {
			throw new InvalidPositionException(e.getMessage());
		}
	}

	@Override
	public Position<E> root() throws EmptyTreeException {
		if(isEmpty()) throw new EmptyTreeException("Árbol vacío");
		return raiz;
	}

	@Override
	public Position<E> parent(Position<E> v) throws InvalidPositionException, BoundaryViolationException {
		if(isRoot(v)) throw new BoundaryViolationException("Raiz, no tiene padre");
		TNodo<E> n = checkPosition(v);
		Position<E> padre = n.getPadre();
		if(padre == null) throw new BoundaryViolationException("No tiene padre");
		return padre;
	}

	@Override
	public Iterable<Position<E>> children(Position<E> v) throws InvalidPositionException {
		if(v==null) throw new InvalidPositionException("Children: Posición inválida");
		TNodo<E> n = checkPosition(v);
		PositionList<Position<E>> l = new DoubleLinkedList<Position<E>>();
		for(TNodo<E> h: n.getHijos()) {
			l.addLast(h);
		}
		
		return l;
	}

	@Override
	public boolean isInternal(Position<E> v) throws InvalidPositionException {
		TNodo<E> n = checkPosition(v);
		return (!(n.getHijos().isEmpty()));
	}

	@Override
	public boolean isExternal(Position<E> v) throws InvalidPositionException {
		TNodo<E> n = checkPosition(v);
		return n.getHijos().isEmpty();
	}

	@Override
	public boolean isRoot(Position<E> v) throws InvalidPositionException {
		TNodo<E> n = checkPosition(v);
		return n == raiz;
	}

	@Override
	public void createRoot(E e) throws InvalidOperationException {
		if(!isEmpty()) throw new InvalidOperationException("Árbol no vacío");
		raiz = new TNodo<E>(e);
		size = 1;
	}

	@Override
	public Position<E> addFirstChild(Position<E> p, E e) throws InvalidPositionException {
		if(isEmpty()) throw new InvalidPositionException("Árbol vacío");
		TNodo<E> padre = checkPosition(p);
		TNodo<E> Phijo = new TNodo<E>(e, padre);
		padre.getHijos().addFirst(Phijo);
		size++;
		return Phijo;
	}

	@Override
	public Position<E> addLastChild(Position<E> p, E e) throws InvalidPositionException {
		if(isEmpty()) throw new InvalidPositionException("Árbol vacío");
		TNodo<E> padre = checkPosition(p);
		TNodo<E> Uhijo = new TNodo<E>(e, padre);
		padre.getHijos().addLast(Uhijo);
		size++;
		return Uhijo;
	}

	@Override
	public Position<E> addBefore(Position<E> p, Position<E> rb, E e) throws InvalidPositionException {
		if(isEmpty()) throw new InvalidPositionException("Árbol vacío");
		TNodo<E> padre = checkPosition(p);
		TNodo<E> hermanoDer = checkPosition(rb);
		TNodo<E> nuevo = new TNodo<E>(e, padre);
		PositionList<TNodo<E>> hijos = padre.getHijos();
		boolean encontre = false;
		Position<TNodo<E>> pp=null;
		try {
			pp = hijos.first();
			while(pp!= null && !encontre) {
				if(pp.element() != hermanoDer )
					pp = (pp!=hijos.last() ? hijos.next(pp):null);
				else
					encontre = true;
			}
		}
		catch(BoundaryViolationException|EmptyListException er) {
			System.out.println(er.getMessage());
		}
		if(!encontre) throw new InvalidPositionException("P no es padre de RB");
		hijos.addBefore(pp, nuevo);
		size++;
		return nuevo;
	}

	@Override
	public Position<E> addAfter(Position<E> p, Position<E> lb, E e) throws InvalidPositionException {
		if(isEmpty()) throw new InvalidPositionException("Árbol vacío");
		TNodo<E> padre = checkPosition(p);
		TNodo<E> hermanoIzq = checkPosition(lb);
		TNodo<E> nuevo = new TNodo<E>(e, padre);
		PositionList<TNodo<E>> hijos = padre.getHijos();
		boolean encontre = false;
		Position<TNodo<E>> pp = null;
		try {
			pp = hijos.first();
			while(pp != null && !encontre) {
				if(pp.element() != hermanoIzq)
					pp = (pp!=hijos.last() ? hijos.next(pp): null);
				else
					encontre = true;
			}
		}
		catch(BoundaryViolationException|EmptyListException er) {
			System.out.println(er.getMessage());
		}
		if(!encontre) throw new InvalidPositionException("P no es padre de LB");
		hijos.addAfter(pp, nuevo);
		size++;
		return nuevo;
	}

	@Override
	public void removeExternalNode(Position<E> p) throws InvalidPositionException {
		if(isEmpty()) throw new InvalidPositionException("Árbol vacío");
		TNodo<E> n = checkPosition(p);
		if(!(n.getHijos().isEmpty())) throw new InvalidPositionException("P no es un nodo externo");
		if(isRoot(n)) {
			raiz.setElemento(null);
		}
		else {
			TNodo<E> padre = n.getPadre();
			PositionList<TNodo<E>> hijos = padre.getHijos();
			boolean encontre = false;
			Position<TNodo<E>> pos = null;
			Iterable<Position<TNodo<E>>> posiciones = hijos.positions();
			Iterator<Position<TNodo<E>>> it = posiciones.iterator();
			while(it.hasNext() && !encontre) {
				pos = it.next();
				if(pos.element() == n)
					encontre = true;
			}
		
		if(!encontre) throw new InvalidPositionException("P no aparece en la lista de hijos de su padre --- arbol corrupto???");
		hijos.remove(pos);
		}
		size--;	
	}

	@Override
	public void removeInternalNode(Position<E> p) throws InvalidPositionException {
		if(isEmpty()) throw new InvalidPositionException("Árbol vacío");
		TNodo<E> n = checkPosition(p);
		if(isExternal(n)) throw new InvalidPositionException("El nodo no es interno");
		if(isRoot(n) && n.getHijos().size()>1) throw new InvalidPositionException("Raíz con más de un hijo");
		if(isRoot(n)) {
			if(n.getHijos().size()==1) {
				try {
					raiz = n.getHijos().first().element();
					raiz.setPadre(null);
					n.setElemento(null);
					n.getHijos().remove(n.getHijos().first());
				}
				catch(EmptyListException e) {
					System.out.println(e.getMessage());
				}
			}
		}
		else {
			TNodo<E> padre = n.getPadre();
			PositionList<TNodo<E>> hermanos = padre.getHijos();
			PositionList<TNodo<E>> hijosN = n.getHijos();
			try {
				Position<TNodo<E>> pAEliminar = hermanos.first();
				boolean encontre = false;
				while(pAEliminar!=null && !encontre) {
					if(pAEliminar.element()==n) encontre =true;
					else pAEliminar = pAEliminar!=hermanos.last()?hermanos.next(pAEliminar):null;
				}
				if(!encontre) throw new InvalidPositionException("El nodo no se encuentra en la lista de hijos de su padre");
				while(!hijosN.isEmpty()) {
					Position<TNodo<E>> pH = hijosN.first();
					pH.element().setPadre(padre);
					hermanos.addBefore(pAEliminar, pH.element());
					hijosN.remove(pH);
				}
				pAEliminar.element().setPadre(null);
				hermanos.remove(pAEliminar);
			} catch (EmptyListException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (BoundaryViolationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		size--;
	}

	@Override
	public void removeNode(Position<E> p) throws InvalidPositionException {
		if(isInternal(p))
			removeInternalNode(p);
		else
			if(isExternal(p))
				removeExternalNode(p);
	}
	
	public Tree<E> clone(){
		Tree<E> n = new Arbol<E>();
		if(!(isEmpty())) {
			try {
				n.createRoot(raiz.element());
				cloneAux(n,raiz,n.root());
			}catch(Exception e) {
				e.printStackTrace();
			}
			}
		return n;
	}
	private void cloneAux(Tree<E> n, TNodo<E> p, Position<E> pClone) {
		try {
			for(TNodo<E> h: p.getHijos()) {
				Position<E> pos = n.addLastChild(pClone, h.element());
				cloneAux(n,h, pos);
			}
		} catch (InvalidPositionException e) {
			e.printStackTrace();
		}
		
	}
	
	public PositionList<Position<E>> tp5ej7a(){
		PositionList<Position<E>> lista = new DoubleLinkedList<Position<E>>();
		if(!isEmpty())
			preEj7a(lista,raiz);
		return lista;
	}
	
	private void preEj7a(PositionList<Position<E>> l, TNodo<E> r) {
		try {
			for(TNodo<E> h: r.getHijos()) {
				if(h == r.getHijos().first().element() && !(h.getHijos().isEmpty())) {
					l.addLast(h);
				}
				preEj7a(l,h);
			}
		}
		catch(EmptyListException e) {
			e.printStackTrace();
		}
	}
	
	public void tp5ej7b() {
		if(!isEmpty())
		RecEj7b(this, raiz);
	}
	
	private void RecEj7b(Tree<E> arbol, TNodo<E> r) {
		for(TNodo<E> h: r.getHijos()) {
			try{
				RecEj7b(arbol, h);
				if(h == r.getHijos().first().element() && isInternal(h)) {
					PositionList<TNodo<E>> hijos = h.getHijos();
					PositionList<TNodo<E>> hermanos = r.getHijos();
					for(TNodo<E> n : hijos) {
						if(hermanos.size() == 1) {
							hermanos.addLast(n);
							
						}
						else {
							hermanos.addAfter(hermanos.first(), n);
						}
						
						n.setPadre(r);
						hermanos.remove(hermanos.first());
						h.setElemento(null);
						h.setPadre(null);
						size--;
					}
				}
			}
			catch(EmptyListException | InvalidPositionException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void tp5ej7c()  {
		if(!isEmpty())
			try {
				RecEj7c(this, raiz);
			} catch (InvalidOperationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	private void RecEj7c(Tree<E> arbol, TNodo<E> r) throws InvalidOperationException {
		for(TNodo<E> h: r.getHijos()) {
			
				if(h.getHijos().isEmpty()){
					PositionList<TNodo<E>> hermanos = r.getHijos();
					Iterator<Position<TNodo<E>>> it = hermanos.positions().iterator();
					Position<TNodo<E>> p = null;
					boolean encontre = false;
					while(it.hasNext() && !encontre) {
						p = it.next();
						if(h == p.element())
							encontre = true;
					}
					if(!encontre) throw new InvalidOperationException("El nodo no aparece en la lista de hijos de su padre");
					try {
						hermanos.remove(p);
						h.setElemento(null);
						h.setPadre(null);
						size--;
					}
					catch(InvalidPositionException e) {
						e.printStackTrace();
					}
				}
				else
					RecEj7c(arbol,h);	
		}
			
	}
	
	public void tp5ej7d(E r) {
		if(!isEmpty()) {
			RecEj7d(this, raiz,r);
			if(raiz.element() == r) {
				if(raiz.getHijos().size() == 1)
					try {
						raiz = raiz.getHijos().first().element();
						size--;
					} catch (EmptyListException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		}
	}
	
	private void RecEj7d(Tree<E> arbol, TNodo<E> r, E elem) {
		for(TNodo<E> h: r.getHijos()) {
			try {
			if(h.element() == elem)
				
					removeNode(h);
				
			RecEj7d(arbol,h,elem);
			} 
			catch (InvalidPositionException e) {
			e.printStackTrace();
			}
		}
	}
	
	public void parcial(E R) {
		if(!isEmpty())
			parcialRec(R, raiz);
	}
	
	private void parcialRec(E elem, TNodo<E> r) {
		try {
			if(r.element() == elem) {
				PositionList<TNodo<E>> hijos = r.getHijos();
				if(!hijos.isEmpty()) {
					TNodo<E> aElim = hijos.last().element();
					hijos.remove(hijos.last());
					size--;
					PositionList<TNodo<E>> hijosAElim = aElim.getHijos();
					while(!hijosAElim.isEmpty()) {
						TNodo<E> h = hijosAElim.first().element();
						hijos.addLast(h);
						h.setPadre(r);
						hijosAElim.remove(hijosAElim.first());
					}
					aElim.setElemento(null);
					aElim.setPadre(null);
					
				}
			}
			for(TNodo<E> h: r.getHijos()) {
				parcialRec(elem,h);
			}
		}
		catch(EmptyListException|InvalidPositionException e) {
			e.printStackTrace();
		}
	}
	
	public Tree<E> espejo(){
		Tree<E> toRet = new Arbol<E>();
		if(size>0) {
			try {
				toRet.createRoot(raiz.element());
				espejoRec(toRet,raiz,toRet.root());
			}
			catch(EmptyTreeException|InvalidOperationException e) {
				e.printStackTrace();
			}
		}
		return toRet;
	}
	
	private void espejoRec(Tree<E> T, TNodo<E> r, Position<E> rNuevo) {
		Position<TNodo<E>> aux;
		if(!r.getHijos().isEmpty()) {
			try {
				aux = r.getHijos().last();
				while(aux != null) {
					Position<E> p1 = T.addLastChild(rNuevo, aux.element().element());
					espejoRec(T,aux.element(),p1);
					if(aux == r.getHijos().first())
						aux = null;
					else
						aux = r.getHijos().prev(aux);
				}
			}
			catch(EmptyListException|InvalidPositionException|BoundaryViolationException e) {
				e.printStackTrace();
			}
		}
	}
	
	public PositionList<E> existeCamino(TNodo<E> N1, TNodo<E> N2) {
		PositionList<E> toRet = new DoubleLinkedList<E>();
		TNodo<E> aux1 = N1;
		TNodo<E> aux2 = N2;
		E med = null;
		Stack<E> PilaAux1 = new LinkedStack<E>();
		Stack<E> PilaAux2 = new LinkedStack<E>();
		try {
			while(aux1 != null) {
				PilaAux1.push(aux1.element());
				if(aux1 != raiz)
					aux1 = aux1.getPadre();
				else
					aux1 = null;
			}
			
			while(aux2 != null) {
				PilaAux2.push(aux2.element());
				if(aux2 != raiz)
					aux2 = aux2.getPadre();
				else
					aux2 = null;
			}
			while(!PilaAux1.isEmpty() && !PilaAux2.isEmpty() && PilaAux1.top() == PilaAux2.top()) {
				med = PilaAux1.pop();
			}
			toRet.addFirst(med);
			while(!PilaAux1.isEmpty()) {
				toRet.addLast(PilaAux1.pop());
			}
			while(!PilaAux2.isEmpty()) {
				toRet.addFirst(PilaAux2.pop());
			}
		}
		catch(EmptyStackException e) {
			e.printStackTrace();
		}
		return toRet;		
	}
	
	public int Altura(Position<E> n) throws InvalidPositionException {
		TNodo<E> n1 = checkPosition(n);
		int aux = 0;
		int toRet = 0;
		for(TNodo<E> h : n1.getHijos()) {
			aux = Altura(h);
			if(aux > toRet)
				toRet = aux;
		}
		return toRet+1;
	}
	
	
}

			

	

