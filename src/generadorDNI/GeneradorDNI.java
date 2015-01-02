package generadorDNI;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class GeneradorDNI {	
	
	public final static int posiblesDNIs = 100000000;	
	 
	
	public static void main (String [] args){
								
		Random r = new Random();
		
		int particion = 70;
		int cantidadDNIs = 10000;
		
		//Generar porcentajes, para ir mostrandolos
		Map <Integer, String> porcentajes = new HashMap <Integer, String> (10);
		for (int i = 100; i > 0; i -= 10){
			porcentajes.put(cantidadDNIs / 100 * i, i+"%");
			System.out.println(cantidadDNIs / 100 * i);
		}
		
		//Procesar argumentos
		if (args.length == 3){
			particion = Integer.valueOf(args[1]);		
			if (particion > 100){
				System.out.println("ERROR: 0 <= particion <= 100, poniendo particion a 70");
				particion = 70;
			}
		}
		
		System.out.println("Argumentos procesados, generando DNIs...");
		
		//Generar todos los DNIs
		ArrayList <String> DNIs = new ArrayList<String>(cantidadDNIs);
		
		for (int i = 0; i < cantidadDNIs; i++){
			DNIs.add(LetraDNI.letraDNI(r.nextInt(posiblesDNIs)));
			if (porcentajes.containsKey(i))
				System.out.println(porcentajes.get(i));
		}
		
		System.out.println("DNIs generador, mezclando...");
		
		//Mezclar los DNIs
		java.util.Collections.shuffle(DNIs);
		
		System.out.println("DNIs mezclados, partiendo...");
		
		//Volcar una parte a un fichero .pat distinto

		String [] cabeceraTest = {"SNNS pattern definition file V1.0", "generated at Mon Nov 30 11:53:37 1999", "", "No. of patterns : " + ((cantidadDNIs / 100) * (100 - particion)), "No. of input units : 8", "No. of output units : 1", ""};
		String [] cabeceraTrain = {"SNNS pattern definition file V1.0", "generated at Mon Nov 30 11:53:37 1999", "", "No. of patterns : " + ((cantidadDNIs / 100) * particion), "No. of input units : 8", "No. of output units : 1", ""};
		String [] cabeceraTodo = {"SNNS pattern definition file V1.0", "generated at Mon Nov 30 11:53:37 1999", "", "No. of patterns : " + cantidadDNIs, "No. of input units : 8", "No. of output units : 1", ""};
		
		PrintWriter test, train, todo;
		
		try {
			test = new PrintWriter ("DNITest.pat", "UTF-8");
			train = new PrintWriter ("DNITrain.pat", "UTF-8");
			todo = new PrintWriter ("todo.pat", "UTF-8");
			
			//Imprimir las cabeceras
			for (int i = 0; i < cabeceraTest.length; i++){
				test.println(cabeceraTest[i]);
				train.println(cabeceraTrain[i]);
				todo.println(cabeceraTodo[i]);
			}
			
			String DNIactual;
			
			//Poner las líneas del fichero de train. La última se pone a mano para no poner un \n de más.
			for (int i = 0; i < cantidadDNIs / 100 * particion - 1; i++){
				DNIactual = DNIs.get(i);
				//Importante que sea hasta 8 para poner a mano el último y no poner un \t de más.
				for (int j = 0; j < 8; j++){
					train.print(DNIactual.charAt(j) + "\t");
				}
				train.print(DNIactual.charAt(8));
				train.print("\n");
			}		
			
			//Poner a mano la última
			DNIactual = DNIs.get(cantidadDNIs / 100 * particion - 1);
			//Importante que sea hasta 8 para poner a mano el último y no poner un \t de más.
			for (int j = 0; j < 8; j++){
				train.print(DNIactual.charAt(j) + "\t");
			}
			train.print(DNIactual.charAt(8));

			//Poner las líneas del fichero de test. La última se pone a mano para no poner un \n de más.
			for (int i = cantidadDNIs / 100 * particion; i < cantidadDNIs - 1; i++){
				DNIactual = DNIs.get(i);
				//Importante que sea hasta 8 para poner a mano el último y no poner un \t de más.
				for (int j = 0; j < 8; j++){
					test.print(DNIactual.charAt(j) + "\t");
				}
				test.print(DNIactual.charAt(8));
				test.print("\n");
			}
			
			//Poner a mano la última
			DNIactual = DNIs.get(cantidadDNIs - 1);
			//Importante que sea hasta 8 para poner a mano el último y no poner un \t de más.
			for (int j = 0; j < 8; j++){
				test.print(DNIactual.charAt(j) + "\t");
			}
			test.print(DNIactual.charAt(8));
			
			//Poner las líneas del fichero con todos los datos. La última se pone a mano para no poner un \n de más.
			for (int i = 0; i < cantidadDNIs - 1; i++){
				DNIactual = DNIs.get(i);
				//Importante que sea hasta 8 para poner a mano el último y no poner un \t de más.
				for (int j = 0; j < 8; j++){
					todo.print(DNIactual.charAt(j) + "\t");
				}
				todo.print(DNIactual.charAt(8));
				todo.print("\n");
			}
			
			//Poner a mano la última
			DNIactual = DNIs.get(cantidadDNIs - 1);
			//Importante que sea hasta 8 para poner a mano el último y no poner un \t de más.
			for (int j = 0; j < 8; j++){
				todo.print(DNIactual.charAt(j) + "\t");
			}
			todo.print(DNIactual.charAt(8));
						
			train.close();
			test.close();
			todo.close();
			
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		System.out.println("Terminado");		
	}
	
	
}
