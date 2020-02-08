 package TDACola;

import java.util.Arrays;

import Exceptions.EmptyQueueException;

public class ArrayQueue<E> implements Queue<E> {
	protected int f, r, N;
	protected E[] q;
	public ArrayQueue() {
		q = (E[]) new Object[2000];
		r = 0;
		f = 0;
		N = 2000;
	}

	@Override
	public int size() {
		return (N-f+r) % N;
	}

	@Override
	public boolean isEmpty() {
		return f == r;
	}

	@Override
	public E front() throws EmptyQueueException {
		if(isEmpty()) throw new EmptyQueueException("cola vac�a");
		return q[f];
	}

	@Override
	public void enqueue(E element) {
	
		q[r] = element;
		r = (r+1) % N;
	}

	@Override
	public E dequeue() throws EmptyQueueException {
		if(isEmpty()) throw new EmptyQueueException("Cola vac�a");
		E aux = q[f];
		q[f] = null;
		f = (f+1) % N;
		return aux;
	}
	
	
}
