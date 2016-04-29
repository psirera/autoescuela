/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FLucero_autoescuela;

import java.util.*;
import java.io.*;

/**
 *
 * @author Fer
 */
public class TestAutoescuela {
    static Scanner input = new Scanner(System.in);
    public static ArrayList<Pregunta> leerPreguntas(ArrayList<Pregunta> almacen) {
        File fe = new File("preguntas2.txt");
        if (fe.exists()) {
            try {
                FileReader fr = new FileReader(fe);
                BufferedReader br = new BufferedReader(fr); //leer linea a linea
                
                String cadena;
                int id= 0;
                String pregunta = null;
                String r1 = null;
                String r2 = null;
                String r3 = null;
                char solucion = 0;
                int dificultad = 0;
                int repeticiones = 0;
                ArrayList<String> lineas = new ArrayList<String>();

                while ((cadena = br.readLine()) != null) { //pasamos todo el fichero a un arraylist auxiliar
                    lineas.add(cadena);
                }

                br.close();

                if (lineas.size() % 7 == 0) {   
                    int numPreguntas = lineas.size() / 7; //dividimos el total de lineas en los 7 datos que proporciona el fichero

                    for (int i = 0; i < numPreguntas; i++) { //hacemos un bucle que va de 0 al maximo de objetos (al comienzo seran 45 antes de borrar o insertar)
                        int cont = i * 7;   //creamos un contador que vaya de 7 en 7
                        pregunta = lineas.get(cont);    //asignamos cada linea a su correspondiente sitio 0-6-13-etc
                        id ++; //añadimos un ID unico al objeto empezando en 1
                        r1 = lineas.get(cont + 1); // 1-7-14..
                        r2 = lineas.get(cont + 2);  // 2-8-15..
                        r3 = lineas.get(cont + 3);
                        solucion = lineas.get(cont + 4).charAt(0);
                        dificultad = Integer.parseInt(lineas.get(cont + 5));
                        repeticiones = Integer.parseInt(lineas.get(cont + 6));
                        Pregunta a = new Pregunta(id, pregunta, r1, r2, r3, solucion, dificultad, repeticiones);
                        almacen.add(a); //añadimos el objeto lleno al almacen
                    }

                    System.out.println("Se han cargado " + almacen.size() + " preguntas.");

                } else {
                    System.err.println("El número de lineas en el fichero no coincide con el formato esperado");
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return almacen;
    }
    
    public static ArrayList<Pregunta> añadirPregunta(ArrayList<Pregunta> almacen){
        
        if (almacen.size() < 1){
           almacen = leerPreguntas(almacen); 
        }
        
            int id = almacen.size()+1;
            input.nextLine();           //Se da el caso que a veces el buffer peta despues de meter un int al intentar poner un nextLine, agregando esto se come el \n
            System.out.println("Inserte enunciado de la pregunta");
            String pregunta = input.nextLine(); 
            
            System.out.println("Inserte primera respuesta");
            String r1 = input.nextLine();
            
            System.out.println("Inserte segunda respuesta");
            String r2 = input.nextLine();
            
            System.out.println("Inserte tercera respuesta");
            String r3 = input.nextLine();
            
            System.out.println("Inserte respuesta correcta (A-B-C)");
            char solucion = input.next(). charAt(0);
            
            System.out.println("Inserte dificultad (1-5)");
            int dificultad = input.nextInt();
            
            int repeticiones = 0;
            Pregunta a = new Pregunta(id, pregunta, r1 ,r2, r3, solucion, dificultad, repeticiones);
            almacen.add(a);
            
            
        return almacen;
    }
    
    public static ArrayList<Pregunta> eliminarPregunta(ArrayList<Pregunta> almacen){
        
        if (almacen.size() < 1){
           almacen = leerPreguntas(almacen); 
        }
        
        System.out.println("Que número de prgunta desea eliminar?");
        int preg = input.nextInt();
        
        
        Iterator<Pregunta> it = almacen.iterator();
        while(it.hasNext()){
            Pregunta i = it.next();
            if (i.getId()== preg){
                
                System.out.println("Está seguro que desea eliminar la siguiente pregunta?");
                
                System.out.println("Pregunta: "+ i.getId()+ ")"+ i.getPregunta());
                System.out.println("Respuesta 1: "+i.getR1());
                System.out.println("Respuesta 2: "+i.getR2());
                System.out.println("Respuesta 3: "+i.getR3());
                System.out.println("Solucion: "+i.getSolucion());
                System.out.println("Dificultad: "+i.getDificultad());
                System.out.println("Repeticiones: "+i.getRepeticiones());
                System.out.println("-----------------");
                
                System.out.println("Escriba 'si' para confirmar");
                
                String choise = input.next().toLowerCase();
                                                                    
                if (choise.equals("si")){
                    it.remove();
                    System.out.println("Ha borrado la pregunta");
                }else {
                    System.out.println("abortado..");
                }
                
            } 
        }
        
        return almacen;
    }
    
    public static void mostrarPreguntas(ArrayList<Pregunta> almacen){
        
        for(Pregunta i: almacen){
            
            System.out.println("Pregunta: "+ i.getId()+ ")"+ i.getPregunta());
            System.out.println("Respuesta 1: "+i.getR1());
            System.out.println("Respuesta 2: "+i.getR2());
            System.out.println("Respuesta 3: "+i.getR3());
            System.out.println("Solucion: "+i.getSolucion());
            System.out.println("Dificultad: "+i.getDificultad());
            System.out.println("Repeticiones: "+i.getRepeticiones());
            System.out.println("-----------------");
            
        }
    }
    
    public static void guardarPreguntas(ArrayList<Pregunta> almacen){
        try{
                File fe = new File("preguntas.txt");
                File fs = new File("preguntas2.txt");
                FileWriter fw = new FileWriter(fs);
                
                for (Pregunta s: almacen){
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
                if (fw != null){
                    fw.close();
                }
                fs.renameTo(fe);            //no funciona en Windows (quiza por permisos). En Linux el método funciona.
            } catch (IOException e){
            e.printStackTrace();
            }
        
    }
    
    public static void main(String args[]){
        
        ArrayList<Pregunta> almacen = new ArrayList<Pregunta>();
        
        char opcion;
        
        do {
            System.out.println("1) Generar nuevo Test");
            System.out.println("2) Añadir preguntas propuestas");
            System.out.println("3) Eliminar preguntas propuestas");
            System.out.println("4) Mostrar las preguntas del almacen");
            System.out.println("5) Guardar el almacen en fichero");
            System.out.println("0) Salir");
            opcion = input.next().charAt(0);
            
            switch(opcion){
               case '1':
                   almacen= leerPreguntas(almacen);
                   break;
               case '2':
                   almacen = añadirPregunta(almacen);
                   break;
               case '3':
                   almacen = eliminarPregunta(almacen);
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
