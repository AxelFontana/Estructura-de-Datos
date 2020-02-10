package Mapeo;

public class pruebahash {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Integer t=62;
		Integer t2=8;
		Integer t3=62;
		Integer t4=14;
		System.out.println(t.hashCode()%7);
		System.out.println(t2.hashCode()%7);
		System.out.println(t3.hashCode()%7);
		System.out.println(((2*t4.hashCode()+3) % 7) % 6 );
	}

}
//[(ai+b) modp] mod N, 