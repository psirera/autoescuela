package autoescuela;

/**
 * @author Enrique Cano
 * @author Fernando Lucero
 * @author Pablo Sirera
 */

public class Pregunta
	{

	// atributos
	private int id;
	private String pregunta;
	private String r1;
	private String r2;
	private String r3;
	char solucion;
	int dificultad;
	int repeticiones;

	// constructor
	public Pregunta(int id, String pregunta, String r1, String r2, String r3, char solucion, int dificultad,
			int repeticiones)
		{
		this.id = id;
		this.pregunta = pregunta;
		this.r1 = r1;
		this.r2 = r2;
		this.r3 = r3;
		this.solucion = solucion;
		this.dificultad = dificultad;
		this.repeticiones = repeticiones;
		}

	// metodos

	public int getId()
		{
		return id;
		}

	public String getPregunta()
		{
		return pregunta;
		}

	public String getR1()
		{
		return r1;
		}

	public String getR2()
		{
		return r2;
		}

	public String getR3()
		{
		return r3;
		}

	public char getSolucion()
		{
		return solucion;
		}

	public int getDificultad()
		{
		return dificultad;
		}

	public void setDificultad(int dificultad)
		{
		this.dificultad = dificultad;
		}

	public int getRepeticiones()
		{
		return repeticiones;
		}

	public void setRepeticiones(int r)
		{
		this.repeticiones = r;
		}

	public void unaRepeticionMas()
		{
		this.repeticiones++;
		}
	}