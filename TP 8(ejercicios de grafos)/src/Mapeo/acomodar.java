package Mapeo;

import Excepciones.InvalidKeyException;

public class acomodar {
		public static <K,V> void Acomodar(Map<K,V>M) {
			try {
				Map<V,K>mAux=new MapaHash<V,K>();
				for(K clave:M.keys()) {
					V valor=M.remove(clave);
					mAux.put(valor, clave);
				}
				for(V clave:mAux.keys()) {
					K valor=mAux.remove(clave);
					M.put(valor, clave);
				}
			}
			catch(InvalidKeyException e) {
				e.printStackTrace();
			}
		}
		public static void main(String[] args) {
			// TODO Auto-generated method stub
			Map<Integer,Character> map=new MapaHash<Integer,Character>();
			try {
			map.put(1, 'a');
			map.put(2, 'b');
			map.put(3, 'a');
			map.put(4, 'a');
			map.put(5, 'b');
			for(Entry<Integer,Character> e:map.entries()) {
				System.out.println("calve"+e.getKey()+"valor"+e.getValue());
			}
			Acomodar(map);
			System.out.println("then");
			for(Entry<Integer,Character> e:map.entries()) {
				System.out.println("calve"+e.getKey()+"valor"+e.getValue());
			}
			}
			catch(InvalidKeyException e) {
				
			}
			
			
		}

	}

