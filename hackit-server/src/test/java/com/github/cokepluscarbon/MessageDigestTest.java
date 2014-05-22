package com.github.cokepluscarbon;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Date;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.BinaryCodec;
import org.apache.commons.codec.binary.Hex;
import org.junit.Test;

public class MessageDigestTest {
	@Test
	public void t1() throws NoSuchAlgorithmException {
		KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");
		keyGenerator.init(56);

		System.out.println(Hex.encodeHexString(keyGenerator.generateKey().getEncoded()));
		System.out.println(Base64.getEncoder().encodeToString(keyGenerator.generateKey().getEncoded()));
	}

	@Test
	public void t2() throws NoSuchAlgorithmException {
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
		keyPairGenerator.initialize(512);

		System.out.println(Base64.getEncoder().encodeToString(keyPairGenerator.genKeyPair().getPublic().getEncoded()));
		System.out.println(Base64.getEncoder().encodeToString(keyPairGenerator.genKeyPair().getPrivate().getEncoded()));
	}

	@Test
	public void generateKey() throws NoSuchAlgorithmException {
		KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
		keyGenerator.init(128);
		Key key = keyGenerator.generateKey();

		System.out.println(Base64.getEncoder().encodeToString(key.getEncoded())); // MU15U/nvzHzRbvfyx+GEVg==
	}

	@Test
	public void encrypt() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
			IllegalBlockSizeException, BadPaddingException {
		byte[] keyBytes = Base64.getDecoder().decode("MU15U/nvzHzRbvfyx+GEVg==");

		SecretKeySpec key = new SecretKeySpec(keyBytes, "AES");

		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, key);

		String plaintext = "Hello AES!";
		byte[] cipherBytes = cipher.doFinal(plaintext.getBytes());
		String ciphertext = Base64.getEncoder().encodeToString(cipherBytes);

		System.out.println(ciphertext); // PGw0tz7XKpSP+h5EOjJALQ==
	}

	@Test
	public void decrypt() throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException,
			IllegalBlockSizeException, BadPaddingException {
		byte[] keyBytes = Base64.getDecoder().decode("MU15U/nvzHzRbvfyx+GEVg==");

		SecretKeySpec key = new SecretKeySpec(keyBytes, "AES");

		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.DECRYPT_MODE, key);

		String ciphertext = "PGw0tz7XKpSP+h5EOjJALQ==";
		byte[] plainBytes = cipher.doFinal(Base64.getDecoder().decode(ciphertext.getBytes()));
		String plaintext = new String(plainBytes);

		System.out.println(plaintext);
	}

	/**
	 * 0000 0 0001 1 0010 1 0011 2 0100 1 0101 2 0110 2 0111 3 1000 1 1001 2
	 * 1010 2 1011 3 1100 1 1101 3 1110 3 1111 4
	 */
	private static final int ONE_TIMES[] = { 0, 1, 1, 2, 1, 2, 2, 3, 1, 2, 2, 3, 2, 3, 3, 4 };

	@Test
	public void count() throws IOException {
		byte[] data = "Hello Algorithm! Hello World!".getBytes();

		long count = 0;
		for (byte b : data) {
			count += ONE_TIMES[(b & 0xf0) >>> 4];
			count += ONE_TIMES[b & 0x0f];
		}
		System.out.println(count); // 106
	}

	@Test
	public void count2() {
		byte[] data = "Hello Algorithm! Hello World!".getBytes();

		long count = 0;
		for (byte b : data) {
			b = (byte) ((b & 0b01010101) + ((b & 0b10101010) >>> 1));
			b = (byte) ((b & 0b00110011) + ((b & 0b11001100) >>> 2));
			b = (byte) ((b & 0b00001111) + ((b & 0b11110000) >>> 4));
			count += b;
		}

		System.out.println(count);
	}

	@Test
	public void count3() {
		byte[] data = "Hello Algorithm! Hello World!".getBytes();

		long count = 0;
		int len = data.length / 4;
		for (int i = 0, j = 0; (i < len && j < data.length); i++) {
			int x = (data[j++] << 24) + (data[j++] << 16) + (data[j++] << 8) + (data[j++]);

			x = (x & 0x55555555) + ((x >>> 1) & 0x55555555);
			x = (x & 0x33333333) + ((x >>> 2) & 0x33333333);
			x = (x & 0x0f0f0f0f) + ((x >>> 4) & 0x0f0f0f0f);
			x = (x & 0x00ff00ff) + ((x >>> 8) & 0x00ff00ff);
			x = (x & 0x0000ffff) + ((x >>> 16) & 0x0000ffff);

			count += x;
		}

		/**
		 * 补足
		 */
		int padLen = data.length % 4;
		byte[] padData = new byte[4];
		for (int i = 0; i < padLen; i++) {
			padData[i] = data[data.length - padLen + i];
			System.out.println(padData[i]);
		}

		int x = (padData[0] << 24) + (padData[1] << 16) + (padData[2] << 8) + (padData[3]);

		x = (x & 0x55555555) + ((x >>> 1) & 0x55555555);
		x = (x & 0x33333333) + ((x >>> 2) & 0x33333333);
		x = (x & 0x0f0f0f0f) + ((x >>> 4) & 0x0f0f0f0f);
		x = (x & 0x00ff00ff) + ((x >>> 8) & 0x00ff00ff);
		x = (x & 0x0000ffff) + ((x >>> 16) & 0x0000ffff);

		count += x;

		System.out.println(count);
	}

	@Test
	public void count4() {
		byte b = 1;
		System.out.println((b << 24) + (b << 16) + (b << 8));
	}

	@Test
	public void t4_keyPair() throws NoSuchAlgorithmException {
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
		keyPairGenerator.initialize(1024);
		KeyPair keyPair = keyPairGenerator.generateKeyPair();
		Key privateKey = keyPair.getPrivate();
		Key publicKey = keyPair.getPublic();

		System.out.println(Base64.getEncoder().encodeToString(privateKey.getEncoded()));
		System.out.println(Base64.getEncoder().encodeToString(publicKey.getEncoded()));
	}

	/**
	 * 
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 * @throws InvalidKeySpecException
	 */
	@Test
	public void t5_encrypt() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
			IllegalBlockSizeException, BadPaddingException, InvalidKeySpecException {
		String publicKeyStr = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCc7C/NuteEdublEfhTk9SJC0+ckhT6Q3KazYhDBGKkJcApJ1ovfaZxGFBYwK4OMm+MprGAcUTkRIN41w4l39vjZkFtWXnNRbz4pw7whJOGIzdJuaaxBQ9s7Ig5w4Hr4nfaGvwe0DnybrEQSg2MRVrsQgclDFJeuc+L7HUdNgGBhQIDAQAB";

		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(publicKeyStr));
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PublicKey publicKey = keyFactory.generatePublic(keySpec);

		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);

		byte[] cipherByetes = cipher.doFinal("Hello RSA!".getBytes());
		System.out.println(Base64.getEncoder().encodeToString(cipherByetes));
	}

	@Test
	public void t6_decrypt() throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException,
			NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		String privateKeyStr = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJzsL82614R25uUR+FOT1IkLT5ySFPpDcprNiEMEYqQlwCknWi99pnEYUFjArg4yb4ymsYBxROREg3jXDiXf2+NmQW1Zec1FvPinDvCEk4YjN0m5prEFD2zsiDnDgevid9oa/B7QOfJusRBKDYxFWuxCByUMUl65z4vsdR02AYGFAgMBAAECgYBQAIoOlOczfXsR5it4ULHWfJHF/SP8w042ZfrGUAIKNcTWNl3gQGsO1ZqA5RrX2hbM2l88YIMC7XsprFpmLM7pO8+DjfcVI5BklFnAe51mwVe6RuvhwMiaNH/0uwyOtwr6HurQGh0zBjEsrTeVtVqhBt7EbbtlXOSZGNib6WwugQJBAPS7fwrV33NO+x0MnnWnAH5JE0psucjgFQVyja6Z5pwtHJwxt9LYVpWj/41aC2ybWzhDNrKloznL8jZWGnvWbS0CQQCkJbuS7w2lB1mpCYwIgQ2bYUwsjRuUAMwcNg6uzx8FjRVg1zh1of2mNHyo7nJvYQfosrZiQebNkXvhUj9suIy5AkEApGp2vlPTTAdqwyhgKNCqjy0O7bg/1Ile9grwzrWMba4NA46PXfKDsQK9xDwTYsK4tQLK85g+Ia03q3OLVVlIfQJAWdjNcv+mWE5NpVi0bXM2H3aCW2BhWAGXt1lfcA+uyzsOGJO1M64XSIIyOcVKnexeh+BKND4eNycKx6pdTQU3AQJAJnsDuev8T2ItU32jq70DatKhcv5FdS6QeTRhtVLLSgQTY+HY/OJykQjb+EChyv7sPEVmbDY1xm6J2fIopXB66w==";
		String ciphertext = "d+eO80FHf1a/nMf6w14U2+IwbHcu6G57XC/yRK+9RSVYJcHKNlL1rTDEh37zXQKnZUE/ew82gdz8AnkHlCMmRCSEEJUxVwst1WAgsHdtn3Dfe8u71AZdbv+ujVTBBoOL6bhrTJERFU856vISA5bw1FRi6gA0VHi/MB+ag2MpXy8=";

		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKeyStr));
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PrivateKey privateKey = keyFactory.generatePrivate(keySpec);

		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, privateKey);

		byte[] plainBytes = cipher.doFinal(Base64.getDecoder().decode(ciphertext.getBytes()));
		System.out.println(new String(plainBytes));
	}
}
