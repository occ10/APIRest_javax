package com.university.model;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.UUID;
import java.security.*;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	private String correo;
	private String nombre;
	private String apellido;
	private int edad;
	private String contraseña;
	private String telefono;
	private String salt;
	private String foto;
	private String detalles;
	private String Confirmado;
	private int opcion;
	private static final int HASH_BYTE_SIZE = 64; // 512 bits
	private static final int PBKDF2_ITERATIONS = 1000;

	public User() {
	}

	public User(String correo, String nombre, String apellido, int edad, String contraseña, String telefono,
			String salt, String foto, String detalles, String confirmado, int opcion) {
		super();
		this.correo = correo;
		this.nombre = nombre;
		this.apellido = apellido;
		this.edad = edad;
		this.contraseña = contraseña;
		this.telefono = telefono;
		this.salt = salt;
		this.foto = foto;
		this.detalles = detalles;
		Confirmado = confirmado;
		this.opcion = opcion;
	}

	public void getSalt1(String password) throws NoSuchAlgorithmException {
		byte[] hash = null;
		// Always use a SecureRandom generator
		// SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
		SecureRandom sr = new SecureRandom();
		// Create array for salt
		byte[] salt = new byte[16];
		// Get a random salt
		sr.nextBytes(salt);

		PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, PBKDF2_ITERATIONS, HASH_BYTE_SIZE);
		SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1"); 
		
		try {
			hash = skf.generateSecret(spec).getEncoded();

		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// System.out.println(toHex(salt));
		// System.out.println(toHex(hash));
		String saltHex = DatatypeConverter.printHexBinary(salt);
		String hashHex = DatatypeConverter.printHexBinary(hash);
		// setSalt(toHex(salt));
		// setContraseña(toHex(hash));
		setSalt(saltHex);
		setContraseña(hashHex);

		// return salt
		// return salt;
	}

	public boolean getPassword(String password, String hexPassword, String salt) throws NoSuchAlgorithmException {
		byte[] hash = null;

		byte[] decodedHex = DatatypeConverter.parseHexBinary(salt);

		PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), decodedHex, PBKDF2_ITERATIONS, HASH_BYTE_SIZE);
		SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1"); 
		
		try {
			hash = skf.generateSecret(spec).getEncoded();

		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String hashHex = DatatypeConverter.printHexBinary(hash);

		boolean correcto = hashHex.equalsIgnoreCase(hexPassword);

		return correcto;
	}

	public static final String toHex(final byte[] data) {
		final StringBuilder sb = new StringBuilder(data.length * 2);
		for (int i = 0; i < data.length; i++) {
			sb.append(String.format("%02x", data[i]));
		}
		return sb.toString();
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

	public String getContraseña() {
		return contraseña;
	}

	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public String getDetalles() {
		return detalles;
	}

	public void setDetalles(String detalles) {
		this.detalles = detalles;
	}

	public String getConfirmado() {
		return Confirmado;
	}

	public void setConfirmado(String confirmado) {
		Confirmado = confirmado;
	}

	public int getOpcion() {
		return opcion;
	}

	public void setOpcion(int opcion) {
		this.opcion = opcion;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
