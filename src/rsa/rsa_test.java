package rsa;

import java.math.BigInteger;
import java.util.ArrayList;

public class rsa_test {

	public static void main(String[] args) {
		
		String message = "Would you like to go out on a date with me?"; // our message
		String mHex = RSA.toHexString(message.getBytes()); // message Hex
		System.out.println("HFOC as hex: " +mHex);
		BigInteger bigM = new BigInteger(mHex, 16);
		System.out.println("HFOC as int: " + bigM.toString());
		
		System.out.println("RSA CHECK");
		RSA r = new RSA();
		r.setSize(512); // do not ever use such small key!
		ArrayList<BigInteger> keys = r.genKey();
		BigInteger e1 = keys.get(0);
		BigInteger d1 = keys.get(1);
		BigInteger n = keys.get(4);
		BigInteger e2 = keys.get(2);
		BigInteger d2 = keys.get(3);
		System.out.println("KEY 1:");
		System.out.println("public: (" + e1.toString()+", "+ n.toString()+")");
		System.out.println("priv: (" + d1.toString()+", "+ n.toString()+")");
		System.out.println("KEY 2:");
		System.out.println("public: (" + e2.toString()+", "+ n.toString()+")");
		System.out.println("priv: (" + d2.toString()+", "+ n.toString()+")");
		System.out.println("\nENC&DEC KEY 1:");
		String enc1 = RSA.Encrypt(message, e1, n);
		System.out.println("enc = " + enc1);
		String dec1 = RSA.Decrypt(enc1, d1, n);
		System.out.println("dec = " + dec1);
		System.out.println("\nENC&DEC KEY 2:");
		String enc2 = RSA.Encrypt(message, e2, n);
		System.out.println("enc = " + enc2);
		String dec2 = RSA.Decrypt(enc2, d2, n);
		System.out.println("dec = " + dec2);
	}

}
