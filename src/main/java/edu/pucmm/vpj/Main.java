package edu.pucmm.vpj;

import com.google.gson.Gson;
import edu.pucmm.vpj.encapsulaciones.Estudiante;
import edu.pucmm.vpj.servicios.EstudianteService;
import edu.pucmm.vpj.utilidades.JsonUtilidades;

import java.util.Set;

import static spark.Spark.*;

public class Main {


    //Llave de 32 bytes por la firma utilizada
    public final static String ACCEPT_TYPE_JSON = "application/json";
    public final static int BAD_REQUEST = 400;
    public final static int UNAUTHORIZED = 401;
    public final static int FORBIDDEN = 403;
    public final static int ERROR_INTERNO = 500;

    public static void main(String[] args) {

        //Servicios
        EstudianteService estudianteService = EstudianteService.getInstancia();

        //Indicando las rutas por defecto.
        staticFileLocation("/publico/");

        //Rutas.
        get("/", (request, response) -> {
            response.redirect("/index.html");
            return "";
        });

        /**
         * API REST para Desmostrar la consulta del token
         */
        path("/api", () -> {

            after("/*", ((request, response) -> {
                response.type(ACCEPT_TYPE_JSON);
            }));

            path("/estudiante", () -> {


                //listar todos los estudiantes.
                get("/", (request, response) -> {
                    return estudianteService.getAllEstudiantes();
                }, JsonUtilidades.json());

                //retorna un estudiante
                get("/:matricula", (request, response) -> {
                    Estudiante estudiante = estudianteService.getEstudiante(Integer.parseInt(request.params("matricula")));
                    if(estudiante==null){
                        throw new RuntimeException("No existe el cliente");
                    }
                    return  estudiante;
                }, JsonUtilidades.json());

                //crea un estudiante
                post("/", Main.ACCEPT_TYPE_JSON, (request, response) -> {

                    Estudiante estudiante = new Gson().fromJson(request.body(), Estudiante.class);

                    return estudianteService.crearEstudiante(estudiante);
                }, JsonUtilidades.json());

                //actualiza un estudiante
                put("/", Main.ACCEPT_TYPE_JSON, (request, response) -> {
                    Estudiante estudiante = new Gson().fromJson(request.body(), Estudiante.class);
                    return estudianteService.actualizarEstudiante(estudiante);
                }, JsonUtilidades.json());

                //eliminar un estudiante
                delete("/:matricula", (request, response) -> {
                    return estudianteService.eliminarEstudiante(Integer.parseInt(request.params("matricula")));
                }, JsonUtilidades.json());

            });

        });

    }
}
