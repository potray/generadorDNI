package generadorDNI;

public class LetraDNI {

	//Sacado de http://es.wikibooks.org/wiki/Algoritmo_para_obtener_la_letra_del_NIF#Java
	public static final String NIF_STRING_ASOCIATION = "TRWAGMYFPDXBNJZSQVHLCKE";

	/**
	 * Devuelve un NIF completo a partir de un DNI. Es decir, a�ade la letra del NIF
	 * @param dni dni al que se quiere a�adir la letra del NIF
	 * @return NIF completo.
	 */
	public static String letraDNI(int dni) {
		//Para que me rellene el DNI con ceros si el n�mero es menor que 10000000 lo formateo.
		return String.format("%08d", dni) + (NIF_STRING_ASOCIATION.charAt(dni % 23) - ('A' - 1));
	}

}
