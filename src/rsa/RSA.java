package rsa;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;
import java.util.regex.Pattern;

public class RSA {
	
	private static int size = 512;
		
	public void setSize(int nsize){
		size = nsize;
	}
	
	
	public ArrayList<BigInteger> genKey(){
		Random rnd = new Random();
		BigInteger p = BigInteger.probablePrime(size / 2, rnd);
		BigInteger q = BigInteger.probablePrime(size / 2, rnd);
		return genKey(p,q);
		
	}
	public ArrayList<BigInteger> genKey(BigInteger p, BigInteger q){
		ArrayList<BigInteger> keys = new ArrayList<BigInteger>();
		
		BigInteger n = p.multiply(q);
		BigInteger fi = (p.subtract(BigInteger.ONE)).multiply(q
				.subtract(BigInteger.ONE));
		BigInteger lcm = getLcm(p.subtract(BigInteger.ONE), q.subtract(BigInteger.ONE));
		BigInteger e1 = getCoprime(fi);
		BigInteger e2 = getCoprime(lcm);
		BigInteger d1 = e1.modInverse(fi);
		BigInteger d2 = e2.modInverse(lcm);
		
		keys.add(e1);
		keys.add(d1);
		keys.add(e2);
		keys.add(d2);
		keys.add(n);
		return keys;
	}
	
	public static String Encrypt(String message, BigInteger e, BigInteger n){
		String m = toHexString(message.getBytes()); // message to Hex
		BigInteger big1 = new BigInteger(m, 16); // hex to BigInteger
		big1 = big1.modPow(e, n); // m^e mod n
		
		return big1.toString();
	}
	
	public static String Decrypt(String ciphertext, BigInteger d, BigInteger n){
		String m=ciphertext.replaceAll("\\s", "");
		try{
			BigInteger big1 = new BigInteger(m);
			big1 = big1.modPow(d, n);
			String tmp = big1.toString(16);
			m = fromHexString(tmp);
		}catch(Exception e){
			m="";
		}
		return m;		
	}
	
	public static BigInteger getCoprime(BigInteger m) {
		Random rnd = new Random();
		int length = m.bitLength() - 1;
		BigInteger e = BigInteger.probablePrime(length, rnd);
		while (!(m.gcd(e)).equals(BigInteger.ONE)) {
			e = BigInteger.probablePrime(length, rnd);
		}
		return e;

	}
	
	public static BigInteger getLcm(BigInteger a, BigInteger b){
		if (a.signum() == 0 || b.signum() == 0)
		    return BigInteger.ZERO;
		return a.divide(a.gcd(b)).multiply(b).abs();
	}

	

	public static String toHexString(byte[] ba) {
		StringBuilder str = new StringBuilder();
		for (int i = 0; i < ba.length; i++)
			str.append(String.format("%x", ba[i]));
		return str.toString();
	}


	
	public static String fromHexString(String hex) {
		StringBuilder str = new StringBuilder();
		for (int i = 0; i < hex.length(); i += 2) {
			str.append((char) Integer.parseInt(hex.substring(i, i + 2), 16));
		}
		return str.toString();
	}
	
	public static String stringCompletion(String s){
		String ret = s;
		int x = 160 - s.length();
		if(x!=0){
		for(int i = 0; i <x; i++)
			ret+="X";
		}
		return ret;
	}

	public static  ArrayList<String> StringToArray(String s){
		ArrayList<String> al = new ArrayList<String>(); 
		String tmp[] = s.split(Pattern.quote("(?<=\\G.{40})"),-1);
		for(int i =0 ; i <tmp.length; i++){
			al.add(tmp[i]);
		}
		return al;
	}
	
	public static  ArrayList<String> MessageToArray(String s){
		ArrayList<String> al = new ArrayList<String>(); 
		String tmp[] = s.split("\\$",-1);
		for(int i =0 ; i <tmp.length; i++){
			al.add(tmp[i]);
		}
		return al;
	}
}