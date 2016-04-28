
package Autoescuela;

public class Pregunta {
    
    //atributos
    private int id;
    private String pregunta;
    private String r1;
    private String r2;
    private String r3;
    private char solucion;
    private int dificultad;
    private int repeticiones;
    
    //metodos
    public Pregunta(String pregunta, String r1, String r2, String r3, char solucion, int dificultad, int repeticiones){
        //this.id = id;
        this.pregunta = pregunta;
        this.r1 = r1;
        this.r2 = r2;
        this.r3 = r3;
        this.solucion = solucion;
        this.dificultad = dificultad;
        this.repeticiones = repeticiones;
        
    }
    
   
    
    public int getDificultad(){
        return dificultad;
    }
    
    public void setDificultad(int dificultad){
        this.dificultad = dificultad;
    }
    
    public int getRepeticiones(){
        return repeticiones;
    }
    
    public void setRepeticiones(int repeticiones){
        this.repeticiones = repeticiones;
    }
}
