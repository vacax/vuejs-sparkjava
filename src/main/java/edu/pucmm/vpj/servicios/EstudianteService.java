package edu.pucmm.vpj.servicios;


import edu.pucmm.vpj.encapsulaciones.Estudiante;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase utilizando el patrón Singleton
 */
public class EstudianteService {

    private static EstudianteService instancia;
    private static List<Estudiante> listaEstudiantes = new ArrayList<>();


    private EstudianteService(){
       for(int i=0;i<20;i++){
          listaEstudiantes.add(new Estudiante(i, "Estudiante "+i));
       }
    }

    public static EstudianteService getInstancia(){
        if(instancia==null){
           instancia = new EstudianteService();
        }
        return instancia;
    }

    /**
     * Lista todos los estudiantes.
     * @return
     */
    public List<Estudiante> getAllEstudiantes(){
        return listaEstudiantes;
    }

    /**
     * Consulta el estudiante dado la matricula
     * @param matricula
     * @return
     */
    public Estudiante getEstudiante(int matricula){
        return listaEstudiantes.stream().filter(est -> est.getMatricula() == matricula).findFirst().orElse(null);
    }

    /**
     * Crea un nuevo estudiante.
     * @param estudiante
     * @return
     */
    public Estudiante crearEstudiante(Estudiante estudiante){
        Estudiante tmp = new Estudiante(estudiante.getMatricula(), estudiante.getNombre(), estudiante.getCorreo(), estudiante.getCarrera());
        listaEstudiantes.add(tmp);
        return tmp;
    }

    /**
     * Actualiza un estudiante
     * @param estudiante
     * @return
     */
    public Estudiante actualizarEstudiante(Estudiante estudiante){
         Estudiante tmp = getEstudiante(estudiante.getMatricula());
         if(tmp==null){
             throw new IllegalArgumentException(String.format("El estudiante con matricula %d no existe.", estudiante.getMatricula()));
         }
         tmp.setNombre(estudiante.getNombre());
         tmp.setCorreo(estudiante.getCorreo());
         tmp.setCarrera(estudiante.getCarrera());
         return tmp;
    }

    /**
     * Elimina un estudiante
     * @param matricula
     * @return
     */
    public boolean eliminarEstudiante(int matricula){
        Estudiante estudiante = getEstudiante(matricula);
        if(estudiante==null){
            throw new IllegalArgumentException(String.format("El estudiante con matricula %d no existe.", estudiante.getMatricula()));
        }
        return listaEstudiantes.remove(estudiante);
    }
    
}
