package Autoescuela;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class Test {
	static Scanner tec = new Scanner(System.in);

	public static void generarNuevoTest(ArrayList<Pregunta> almacen, ArrayList<Pregunta> candidatas,
			ArrayList<Pregunta> test, String nombrefichero) {
		leerPreguntas(almacen, nombrefichero);
		System.out.println("¿Cuantas repeticiones quieres que tenga tu pregunta?");
		int numrepeticiones;
		numrepeticiones = tec.nextInt();

		Iterator almacenIt = almacen.iterator();
		while (almacenIt.hasNext()) {
			Pregunta p = (Pregunta) almacenIt.next();
			if (p.getRepeticiones() >= numrepeticiones) {
				candidatas.add(p);
			}
		}
		int numpreguntas = candidatas.size() + 1;
		while (numpreguntas > candidatas.size()) {
			System.out.println("¿Cuantas preguntas quieres?");
			System.out.println("Como máximo " + candidatas.size());
			numpreguntas = tec.nextInt();

		}
	}

	public static void main(String[] args) {
		ArrayList<Pregunta> almacen = new ArrayList<Pregunta>();
		ArrayList<Pregunta> candidatas = new ArrayList<Pregunta>();
		ArrayList<Pregunta> test = new ArrayList<Pregunta>();

		String nombrefichero = "preguntas.txt";
		int opcion;
		boolean ejecucion = true;

		while (ejecucion == true) {
			System.out.println("1.- Generarun nuevo test.");
			System.out.println("2.- Añadir nuevas propuestas.");
			System.out.println("3.- Eliminar preguntas propuestas.");
			System.out.println("4.- Mostrar las preguntas del almacen.");
			System.out.println("5.- Guardar el almacen de preguntas en el fichero.");
			System.out.println("0.- Salir.");

			opcion = tec.nextInt();

			switch (opcion) {
			case 1:
				generarNuevoTest(almacen, candidatas, test, nombrefichero);
				break;
			case 2:
				añadirPreguntas();
				break;
			case 3:
				eliminarPreguntas();
				break;
			case 4:
				mostrarPreguntas();
				break;
			case 5:
				guardarPreguntas();
				break;
			case 0:
				ejecucion = false;
				break;
			}
		}
	}

}
