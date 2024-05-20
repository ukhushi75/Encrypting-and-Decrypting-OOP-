package eecs2030.lab2;

import java.util.*;

/**
 * Lab for reviewing array and String manipulation in Java and 
 * an intro to utility classes and static features 
 * 
 * @author EECS2030
 *
 */
public class Encryption {
	private final static String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ ";
	
	private static int hash;
	private static Random random = new Random();
	
	
	public static String encrypt(String textToEncrypt, String key) {
		
		//empty string test
		if(textToEncrypt.length() == 0) {
			System.out.println("Empty string detected");
		}

		textToEncrypt = textToEncrypt.toUpperCase();
	
		// setting up the random seed
		hash = key.hashCode();
		random.setSeed(hash);
		
		// substitution pattern for the alphabet
		char[] z = ALPHABET.toCharArray();
		char[] d = ALPHABET.toCharArray();
		
		// substitution pattern would be in array z, array d remains the original alphabet
		for(int i=0; i<100; i++) {
			int one = random.nextInt(27);
			int two = random.nextInt(27);
			
			char c = z[one];
			z[one] = z[two];
			z[two] = c;
		}
		
		
		// Applying the substitution to textToEncrypt string, placing results in sb1
		StringBuilder sb1 = new StringBuilder(textToEncrypt.length());
		
		for(int i = 0; i<textToEncrypt.length(); i++) {
			int j = 0;
			while (j < 27 && textToEncrypt.charAt(i) != d[j]) {
				j++;
			}
			if (j != 27) {
				sb1.append(z[j]);
			}
			else {
				sb1.append(z[26]); // z[26] contains the equivalent of " " in d 
			}
		}
			
		
		// resetting random seed 
		random.setSeed(hash);
		
		// Pattern for column
		int[] y = {0, 1, 2, 3, 4, 5, 6, 7};
		
		for(int i = 0; i < 100; i++) {
			int one = random.nextInt(8);
			int two = random.nextInt(8);
			
			int c = y[one];
			y[one] = y[two];
			y[two] = c;
		}
		
		
		
		// applying transposition and adding results to sb2
		int x = 0;
		
		StringBuilder sb2 = new StringBuilder(sb1.length());
		
		while (x != sb1.length()) {
			char[][] matrix = new char[8][8];
			
			// creating matrix
			int i = 0;
			int j = 0;
			while (i < 8 && x < sb1.length()) {
				j = 0;
				while (j < 8 && x < sb1.length()) {
					matrix[i][j] = sb1.charAt(x);
					x++;
					j++;
				}
				i++;
			}
			
			// applying transposition
			int m=0;
			int n=0;
			while (n < 8) {
				m = 0;
				while (m < 8) {
					if (matrix[m][y[n]] != 0) {
						sb2.append(matrix[m][y[n]]);
						m++;
					}
					else {
						sb2.append(z[26]);
						m++;
					}
					
				}
				n++;
			
		}
	}
		
		for(int i =0; i < textToEncrypt.length(); i++) {
			
				if(textToEncrypt.charAt(i) > 48 && textToEncrypt.charAt(i) < 57) {	
					sb2.setLength(0);
					sb2.append("Error, string contains numeric characters");	
					System.out.println(sb2);
					
					break;
				}
		}
		
		return sb2.toString();
	
	}

	
	
	public static String decrypt(String textToDecrypt, String key) {
		
		
				// setting up the random seed
				hash = key.hashCode();
				random.setSeed(hash);
				
				// substitution pattern for the alphabet
				char[] z = ALPHABET.toCharArray();
				char[] d = ALPHABET.toCharArray();
				
				// substitution pattern would be in array z, array d is still the original alphabet
				for(int i=0; i<100; i++) {
					int one = random.nextInt(27);
					int two = random.nextInt(27);
					
					char c = z[one];
					z[one] = z[two];
					z[two] = c;
				}	
				
				
				// resetting random seed
				random.setSeed(hash);
				
				// Pattern for column
				int[] y = {0, 1, 2, 3, 4, 5, 6, 7};
				
				for(int i = 0; i < 100; i++) {
					int one = random.nextInt(8);
					int two = random.nextInt(8);
					
					int c = y[one];
					y[one] = y[two];
					y[two] = c;
				}
				
				
				
				
				// reversing transposition
				int x = 0;
				StringBuilder sb1 = new StringBuilder(textToDecrypt.length());
				
				while (x != textToDecrypt.length()) {
					char[][] matrix = new char[8][8];
					
					// setting up the 8x8 matrix 
					int i = 0;
					int j = 0;
					while (j < 8) {
						i = 0;
						while (i < 8 && x < textToDecrypt.length()) {
							matrix[i][y[j]] = textToDecrypt.charAt(x);
							x++;
							i++;
						}
						j++;
					}
					
					// going through the matrix and placing results in sb1
					int m=0;
					int n=0;
					while (m < 8) {
						n = 0;
						while (n < 8) {
							sb1.append(matrix[m][n]);
							n++;	
						}
						m++;
					}
				}
				
				
				// reversing the substitution, and placing results in sb2
				StringBuilder sb2 = new StringBuilder();
				
				int i=0;
				int j=0;
				while (i<sb1.length()) {
					j=0;
					while (j<z.length) {
						if (sb1.charAt(i) == z[j]) {
							sb2.append(d[j]);
						}
						j++;
					}
					i++;
				}
				
				
				
				return sb2.toString(); 
//		return textToDecrypt;
	}

}