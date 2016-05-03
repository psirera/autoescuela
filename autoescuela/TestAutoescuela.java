package autoescuela;

import java.util.*;
import java.io.*;

/**
 * @author Enrique Cano
 * @author Fernando Lucero
 * @author Pablo Sirera
 */

public class TestAutoescuela
	{
	static Scanner input = new Scanner(System.in);

	/**
	 * Genera un número entero aleatorio perteneciente a un intervalo [min, max]
	 * 
	 * @param min
	 * @param max
	 * @return un número entero
	 */
	public static int aleatorio(int min, int max)
		{
		return ((int) Math.floor(Math.random() * (max - min + 1) + min));
		}

	/**
	 * Genera un nuevo test pretuntando al usuario por la novedad de las
	 * preguntas, la cantidad de ellas, y el nombre para guardar en disco
	 * 
	 * @param almacen
	 * @param candidatas
	 * @param test
	 */
	public static void generarNuevoTest(ArrayList<Pregunta> almacen, ArrayList<Pregunta> candidatas,
			ArrayList<Pregunta> test)
		{

		candidatas.clear();
		test.clear();

		if (almacen.size() < 1) leerPreguntas(almacen);

		System.out.println("¿Cuantas veces como máximo deben haber salido ya tus preguntas?");
		System.out.print("-> ");
		int numrepeticiones;
		numrepeticiones = input.nextInt();

		Iterator<Pregunta> almacenIt = almacen.iterator();
		while (almacenIt.hasNext())
			{
			Pregunta p = almacenIt.next();
			if (p.getRepeticiones() <= numrepeticiones)
				{
				candidatas.add(p);
				}
			}

		boolean abandona = false;
		int numpreguntas = candidatas.size() + 1;
		System.out
				.println("Hay " + candidatas.size() + " preguntas que se repiten " + numrepeticiones + " veces o menos");
		System.out.println("¿Cuantas preguntas quieres que tenga tu test?");
		while (!abandona && numpreguntas > candidatas.size())
			{
			System.out.println("Introduce un número (máximo " + candidatas.size() + ") o pulsa 0 para volver al menú");
			System.out.print("-> ");
			numpreguntas = input.nextInt();
			if (numpreguntas > candidatas.size()) System.out.println("Ha excedido el máximo");

			if (numpreguntas == 0) abandona = true;
			}

		if (!abandona)
			{
			int ind = 0;
			for (int a = 0; a < numpreguntas; a++)
				{
				// Elige un índice aleatorio entre el actual y el último
				ind = aleatorio(ind, candidatas.size() - 1);

				// Guarda la pregunta elegida
				// System.out.print("["+ ind + ", " + (candidatas.size()-1) + "]");
				// [comprobaciones]
				Pregunta elegida = candidatas.get(ind);

				// Añádela a la lista de elegidas
				test.add(elegida);

				// Búscala en almacen y aumenta su número de repeticiones
				for (Pregunta almacenada : almacen)
					if (almacenada.getId() == elegida.getId()) almacenada.unaRepeticionMas();

				// Bórrala de candidatas para que no se pueda escoger otra vez
				candidatas.remove(ind);

				// Si el índice ha llegado al final, ponlo otra vez a cero
				if (ind >= candidatas.size() - 1) ind = 0;
				}

			System.out.println("Elije un nombre para guardar el test en disco:");
			System.out.print("-> ");
			String ficherosTest = input.next();
			guardarTestPreguntas(test, ficherosTest);
			guardarTestRespuestas(test, ficherosTest);
			}
		}

	/**
	 * Carga las preguntas guardadas en disco para su uso
	 * 
	 * @param almacen
	 */
	public static void leerPreguntas(ArrayList<Pregunta> almacen)
		{
		File fe = new File("preguntas.txt");
		if (fe.exists())
			{
			try
				{
				FileReader fr = new FileReader(fe);
				BufferedReader br = new BufferedReader(fr); // leer linea a linea

				String cadena;
				int id = 0;
				String pregunta = null;
				String r1 = null;
				String r2 = null;
				String r3 = null;
				char solucion = 0;
				int dificultad = 0;
				int repeticiones = 0;
				ArrayList<String> lineas = new ArrayList<String>();

				while ((cadena = br.readLine()) != null)
					{ // pasamos todo el fichero a un arraylist auxiliar
					lineas.add(cadena);
					}

				br.close();

				if (lineas.size() % 7 == 0)
					{
					int numPreguntas = lineas.size() / 7; // dividimos el total de
																		// lineas en los 7 datos
																		// que proporciona el
																		// fichero

					for (int i = 0; i < numPreguntas; i++)
						{ // hacemos un bucle que va de 0 al maximo de objetos (al
							// comienzo seran 45 antes de borrar o insertar)
						int cont = i * 7; // creamos un contador que vaya de 7 en 7
						pregunta = lineas.get(cont); // asignamos cada linea a su
																// correspondiente sitio
																// 0-6-13-etc
						id++; // añadimos un ID unico al objeto empezando en 1
						r1 = lineas.get(cont + 1); // 1-7-14..
						r2 = lineas.get(cont + 2); // 2-8-15..
						r3 = lineas.get(cont + 3);
						solucion = lineas.get(cont + 4).charAt(0);
						dificultad = Integer.parseInt(lineas.get(cont + 5));
						repeticiones = Integer.parseInt(lineas.get(cont + 6));
						Pregunta a = new Pregunta(id, pregunta, r1, r2, r3, solucion, dificultad, repeticiones);
						almacen.add(a); // añadimos el objeto lleno al almacen
						}

					System.out.println("Se han cargado " + almacen.size() + " preguntas.");

					}
				else
					{
					System.err.println("El número de lineas en el fichero no coincide con el formato esperado");
					}

				}
			catch (IOException e)
				{
				e.printStackTrace();
				}
			}
		}

	/**
	 * Permite crear una pregunta nueva y añadirla al listado en uso (no guarda
	 * en disco)
	 * 
	 * @param almacen
	 */
	public static void añadirPregunta(ArrayList<Pregunta> almacen)
		{

		if (almacen.size() < 1) leerPreguntas(almacen);

		int id = almacen.size() + 1;
		input.nextLine(); // Se da el caso que a veces el buffer peta despues de
								// meter un int al intentar poner un nextLine, agregando
								// esto se come el \n
		System.out.println("Inserte enunciado de la pregunta");
		System.out.print("-> ");
		String pregunta = input.nextLine();

		System.out.println("Inserte primera respuesta");
		System.out.print("-> ");
		String r1 = input.nextLine();

		System.out.println("Inserte segunda respuesta");
		System.out.print("-> ");
		String r2 = input.nextLine();

		System.out.println("Inserte tercera respuesta");
		System.out.print("-> ");
		String r3 = input.nextLine();

		System.out.println("Inserte respuesta correcta (A-B-C)");
		System.out.print("-> ");
		char solucion = input.next().charAt(0);

		System.out.println("Inserte dificultad (1-5)");
		System.out.print("-> ");
		int dificultad = input.nextInt();

		int repeticiones = 0;
		Pregunta a = new Pregunta(id, pregunta, r1, r2, r3, solucion, dificultad, repeticiones);
		almacen.add(a);
		}

	/**
	 * Elimina una pregunta del listado en uso (no guarda en el disco)
	 * 
	 * @param almacen
	 */
	public static void eliminarPregunta(ArrayList<Pregunta> almacen)
		{

		if (almacen.size() < 1) leerPreguntas(almacen);

		System.out.println("Que número de prgunta desea eliminar?");
		System.out.print("-> ");
		int preg = input.nextInt();

		Iterator<Pregunta> it = almacen.iterator();
		while (it.hasNext())
			{
			Pregunta i = it.next();
			if (i.getId() == preg)
				{

				System.out.println("Está seguro que desea eliminar la siguiente pregunta?");

				System.out.println("Pregunta: " + i.getId() + ")" + i.getPregunta());
				System.out.println("Respuesta 1: " + i.getR1());
				System.out.println("Respuesta 2: " + i.getR2());
				System.out.println("Respuesta 3: " + i.getR3());
				System.out.println("Solucion: " + i.getSolucion());
				System.out.println("Dificultad: " + i.getDificultad());
				System.out.println("Repeticiones: " + i.getRepeticiones());
				System.out.println("-----------------");

				System.out.println("Escriba 'si' para confirmar");
				System.out.print("-> ");

				String choise = input.next().toLowerCase();

				if (choise.equals("si"))
					{
					it.remove();
					System.out.println("Ha borrado la pregunta");
					}
				else
					{
					System.out.println("abortado..");
					}
				}
			}
		}

	/**
	 * Muestra todos los datos de las preguntas del listado en uso
	 * 
	 * @param almacen
	 */
	public static void mostrarPreguntas(ArrayList<Pregunta> almacen)
		{

		if (almacen.size() < 1) leerPreguntas(almacen);

		for (Pregunta i : almacen)
			{

			System.out.println("Pregunta: " + i.getId() + ")" + i.getPregunta());
			System.out.println("Respuesta 1: " + i.getR1());
			System.out.println("Respuesta 2: " + i.getR2());
			System.out.println("Respuesta 3: " + i.getR3());
			System.out.println("Solucion: " + i.getSolucion());
			System.out.println("Dificultad: " + i.getDificultad());
			System.out.println("Repeticiones: " + i.getRepeticiones());
			System.out.println("-----------------");
			}
		}

	/**
	 * Graba sólo las preguntas y opciones del test generado para el usuario
	 * 
	 * @param test
	 * @param ficherosTest
	 */
	public static void guardarTestPreguntas(ArrayList<Pregunta> test, String ficherosTest)
		{
		try
			{
			File fs = new File("tests/" + ficherosTest + ".test");
			FileWriter fw = new FileWriter(fs);
			for (Pregunta aGrabar : test)
				{
				fw.write(aGrabar.getPregunta());
				fw.write("\r\n");
				fw.write(aGrabar.getR1());
				fw.write("\r\n");
				fw.write(aGrabar.getR2());
				fw.write("\r\n");
				fw.write(aGrabar.getR3());
				fw.write("\r\n");
				fw.write("---");
				fw.write("\r\n");
				}
			if (fw != null)
				{
				fw.close();
				}
			}
		catch (IOException e)
			{
			e.printStackTrace();
			}
		}

	/**
	 * Graba sólo las respuestas del test generado para el usuario
	 * 
	 * @param test
	 * @param ficherosTest
	 */
	public static void guardarTestRespuestas(ArrayList<Pregunta> test, String ficherosTest)
		{
		try
			{
			FileOutputStream fs = new FileOutputStream("tests/" + ficherosTest + ".solucion");
			DataOutputStream d = new DataOutputStream(fs);

			int i = 0;
			for (Pregunta aGrabar : test)
				{
				d.writeInt(i);
				d.writeChar(aGrabar.getSolucion());
				i++;
				}

			if (d != null)
				{
				d.close();
				fs.close();
				}

			}
		catch (IOException e)
			{
			e.printStackTrace();
			}
		}

	/**
	 * Transfiere el listado en uso al disco
	 * 
	 * @param almacen
	 */
	public static void guardarPreguntas(ArrayList<Pregunta> almacen)
		{
		try
			{
			File fe = new File("preguntas.txt");
			File fs = new File("preguntas2.txt");
			FileWriter fw = new FileWriter(fs);

			for (Pregunta s : almacen)
				{
				fw.write(s.getPregunta());
				fw.write("\r\n");
				fw.write(s.getR1());
				fw.write("\r\n");
				fw.write(s.getR2());
				fw.write("\r\n");
				fw.write(s.getR3());
				fw.write("\r\n");
				fw.write(s.getSolucion());
				fw.write("\r\n");
				String dificultad = String.valueOf(s.getDificultad());
				fw.write(dificultad);
				fw.write("\r\n");
				String repeticiones = String.valueOf(s.getRepeticiones());
				fw.write(repeticiones);
				fw.write("\r\n");
				}
			if (fw != null)
				{
				fw.close();
				}
			if (fe.exists()) fe.delete();
			fs.renameTo(fe); // no funciona en Windows (quiza por permisos). En
									// Linux el método funciona.
			}
		catch (IOException e)
			{
			e.printStackTrace();
			}
		}

	/**
	 * Flujo principal y menú
	 * @param args
	 */
	public static void main(String args[])
		{
		ArrayList<Pregunta> almacen = new ArrayList<Pregunta>();
		ArrayList<Pregunta> candidatas = new ArrayList<Pregunta>();
		ArrayList<Pregunta> test = new ArrayList<Pregunta>();

		char opcion;

		do
			{
			System.out.println("1) Generar nuevo Test");
			System.out.println("2) Añadir preguntas propuestas");
			System.out.println("3) Eliminar preguntas propuestas");
			System.out.println("4) Mostrar las preguntas del almacen");
			System.out.println("5) Guardar el almacen en fichero");
			System.out.println("0) Salir");
			System.out.print("-> ");

			opcion = input.next().charAt(0);

			switch (opcion)
				{
				case '1':
				generarNuevoTest(almacen, candidatas, test);
				break;
				case '2':
				añadirPregunta(almacen);
				break;
				case '3':
				eliminarPregunta(almacen);
				break;
				case '4':
				mostrarPreguntas(almacen);
				break;
				case '5':
				guardarPreguntas(almacen);
				break;
				}
			} while (opcion != '0');
		}
	}
