package Test;

import Excepciones.InformacionDifiereDeHeaderException;
import app.CSV;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CSVTest {

    String[] header = {"Nombre", "Apellido", "Edad"};
    HashMap<String, String> usuario1 = new HashMap<String, String>(){
        {
            put("Nombre", "Alejo");
            put("Apellido", "Acevedo");
            put("Edad", "22");
        }
    };
    HashMap<String, String> usuario2 = new HashMap<String, String>(){
        {
            put("Nombre", "Federico");
            put("Apellido", "Cavazzoli");
            put("Edad", "23");
        }
    };

    @BeforeAll
    static void borrarCSV(){
        new File("testCrearCSV.csv").delete();
        new File("testAbrirCSV.csv").delete();
        new File("testEscribirCSVNuevo").delete();
        new File("testEscribirCSVUsado").delete();
        new File("testLeerTodoElCSV.csv").delete();
        new File("testBorrarDelCSV.csv").delete();
    }


    @Test
    public void testCrearCSV()throws IOException{
        String ruta = "testCrearCSV.csv";
        CSV csvPrueba = new CSV(ruta, header);
    }

    @Test
    public void testAbrirCSV()throws IOException{
        String ruta = "testAbrirCSV.csv";
        CSV csvPrueba = new CSV(ruta, header);
        csvPrueba = new CSV(ruta, header);
    }

    @Test
    public void testEscrbirCSVNuevo()throws IOException, InformacionDifiereDeHeaderException{
        String ruta = "testEscribirCSVNuevo.csv";
        CSV csvPrueba = new CSV(ruta, header);
        csvPrueba.agregarAlCSV(usuario1);
        csvPrueba.agregarAlCSV(usuario2);
    }

    @Test
    public void testEscrbirCSVUsado()throws IOException, InformacionDifiereDeHeaderException{
        String ruta = "testEscribirCSVUsado.csv";
        CSV csvPrueba = new CSV(ruta, header);
        csvPrueba.agregarAlCSV(usuario1);
        csvPrueba.agregarAlCSV(usuario2);
        csvPrueba = new CSV(ruta, header);
        csvPrueba.agregarAlCSV(usuario1);
        csvPrueba.agregarAlCSV(usuario2);
    }

    @Test
    public void testLeerTodoElCSV() throws IOException, InformacionDifiereDeHeaderException{
        String ruta = "testLeerTodoElCSV.csv";
        CSV csvPrueba = new CSV(ruta, header);
        csvPrueba.agregarAlCSV(usuario1);
        csvPrueba.agregarAlCSV(usuario2);
        csvPrueba = new CSV(ruta, header);
        LinkedList<HashMap<String,String>> lectura = csvPrueba.leerTodoElCSV();
        assertEquals("Alejo",lectura.get(0).get(header[0]));
        assertEquals("Acevedo",lectura.get(0).get(header[1]));
        assertEquals("22",lectura.get(0).get(header[2]));
        assertEquals(2, lectura.size());
    }

    @Test
    public void testBorrarDelCSV()throws IOException, InformacionDifiereDeHeaderException{
        String ruta = "testBorrarDelCSV.csv";
        CSV csvPrueba = new CSV(ruta, header);
        csvPrueba.agregarAlCSV(usuario1);
        csvPrueba.agregarAlCSV(usuario2);
        LinkedList<HashMap<String,String>> lectura = csvPrueba.leerTodoElCSV();
        assertEquals("Alejo",lectura.get(0).get(header[0]));
        assertEquals("Acevedo",lectura.get(0).get(header[1]));
        assertEquals("22",lectura.get(0).get(header[2]));
        assertEquals(2, lectura.size());
        csvPrueba.cerrarCSV();
        csvPrueba.borrarDelCSV(usuario1);
        lectura = csvPrueba.leerTodoElCSV();
        assertEquals("Federico",lectura.get(0).get(header[0]));
        assertEquals("Cavazzoli",lectura.get(0).get(header[1]));
        assertEquals("23",lectura.get(0).get(header[2]));
        assertEquals(1, lectura.size());
    }
}
