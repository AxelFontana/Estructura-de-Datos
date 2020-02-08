package TDADiccionario;

import Exceptions.InvalidKeyException;

public class prueba {
	public static void main(String []args) {
		Dictionary<String,Integer> dic = new DiccionarioConABB<String,Integer>();
		try {
			dic.insert("A", 1);
			System.out.println(dic.size());
			System.out.println(dic.find("A").getKey());
			dic.insert("A", 1);
			System.out.println(dic.size());
			System.out.println(dic.find("A").getKey());
			dic.insert("D", 1);
			System.out.println(dic.size());
			System.out.println(dic.find("D").getKey());
			dic.insert("B", 1);
			System.out.println(dic.size());
			System.out.println(dic.find("B").getKey());
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
