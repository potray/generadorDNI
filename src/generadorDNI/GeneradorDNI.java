package generadorDNI;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GeneradorDNI {	
	
	public final static int posiblesDNIs = 100000000;
	 
	
	public static void main (String [] args){
		//Generar porcentajes, para ir mostrandolos
		Map <Integer, String> porcentajes = new HashMap <Integer, String> (10);
		for (int i = 100; i > 0; i -= 10){
			porcentajes.put(posiblesDNIs / 100 * i, i+"%");
			System.out.println(posiblesDNIs / 100 * i);
		}
		
		int particion = 70;
		
		//Procesar argumentos
		if (args.length == 2){
			particion = Integer.valueOf(args[1]);		
			if (particion > 100){
				System.out.println("ERROR: 0 <= particion <= 100, poniendo particion a 70");
				particion = 70;
			}
		}
		
		System.out.println("Argumentos procesados, generando DNIs...");
		
		//Generar todos los DNIs desde el 00000000 hasta el 99999999
		ArrayList <String> DNIs = new ArrayList<String>(posiblesDNIs);
		
		for (int i = 0; i < posiblesDNIs; i++){
			DNIs.add(LetraDNI.letraDNI(i));
			if (porcentajes.containsKey(i))
				System.out.println(porcentajes.get(i));
			//if (i > 80000000)
				System.out.println(i);
		}
		
		System.out.println("DNIs generador, mezclando...");
		
		//Mezclar los DNIs
		java.util.Collections.shuffle(DNIs);
		
		System.out.println("DNIs mezclados, partiendo...");
	
		//Partir el array
		ArrayList <String> DNIs1 = new ArrayList <String>(DNIs.subList(0, (posiblesDNIs / 100) * (100 - particion) - 1));
		ArrayList <String> DNIs2 = new ArrayList <String>(DNIs.subList((posiblesDNIs / 100) * (100 - particion), posiblesDNIs - 1));
	
		//Volcar cada array a un fichero .pat distinto

		String [] cabeceraPat1 = {"SNNS pattern definition file V1.0", "generated at Mon Nov 30 11:53:37 1999", "", "No. of patterns : " + ((posiblesDNIs / 100) * (100 - particion)), "No. of input units : 8", ""};
		String [] cabeceraPat2 = {"SNNS pattern definition file V1.0", "generated at Mon Nov 30 11:53:37 1999", "", "No. of patterns : " + ((posiblesDNIs / 100) * particion), "No. of input units : 8", ""};
		
		PrintWriter pw1, pw2;
		
		try {
			pw1 = new PrintWriter ("DNITrain.pat", "UTF-8");
			pw2 = new PrintWriter ("DNITest.pat", "UTF-8");
			
			//Imprimir las cabeceras
			for (int i = 0; i < cabeceraPat1.length; i++){
				pw1.println(cabeceraPat1[i]);
				pw2.println(cabeceraPat2[i]);
			}
			
			//Poner las líneas del fichero de entrenamiento
			for (int i = 0; i < DNIs1.size(); i++){
				String DNIactual = DNIs1.get(i);
				for (int j = 0; j < 9; j++){
					pw1.print(DNIactual.charAt(j) + "\t");
				}
				pw1.print("\n");
			}
			
			for (int i = 0; i < DNIs2.size(); i++){
				String DNIactual = DNIs2.get(i);
				for (int j = 0; j < 9; j++){
					pw1.print(DNIactual.charAt(j) + "\t");
				}
				pw2.print("\n");
			}
			
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
	}
}
