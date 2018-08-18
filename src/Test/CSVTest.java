package Test;

import Excepciones.FaltaIP;
import Excepciones.FaltaNombre;
import Excepciones.InformacionDifiereDeHeaderException;
import app.CSV;
import app.Timbre;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CSVTest {

    String[] header = {"Nombre", "Apellido", "Edad"};


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
        csvPrueba.agregarAlCSV(Arrays.asList("Alejo", "Acevedo", 22));
        csvPrueba.agregarAlCSV(Arrays.asList("Federico", "Cavazzoli", 23));
        csvPrueba.agregarAlCSV(Arrays.asList("Facundo", "Varela", 24));
    }

    @Test
    public void testEscrbirCSVUsado()throws IOException, InformacionDifiereDeHeaderException{
        String ruta = "testEscribirCSVUsado.csv";
        CSV csvPrueba = new CSV(ruta, header);
        csvPrueba.agregarAlCSV(Arrays.asList("Alejo", "Acevedo", 22));
        csvPrueba.agregarAlCSV(Arrays.asList("Federico", "Cavazzoli", 23));
        csvPrueba.agregarAlCSV(Arrays.asList("Facundo", "Varela", 24));
        csvPrueba = new CSV(ruta, header);
        csvPrueba.agregarAlCSV(Arrays.asList("Alejo", "Acevedo", 22));
        csvPrueba.agregarAlCSV(Arrays.asList("Federico", "Cavazzoli", 23));
        csvPrueba.agregarAlCSV(Arrays.asList("Facundo", "Varela", 24));
    }

    @Test
    public void testLeerTodoElCSV() throws IOException, InformacionDifiereDeHeaderException{
        String ruta = "testLeerTodoElCSV.csv";
        CSV csvPrueba = new CSV(ruta, header);
        csvPrueba.agregarAlCSV(Arrays.asList("Alejo", "Acevedo", 22));
        csvPrueba.agregarAlCSV(Arrays.asList("Federico", "Cavazzoli", 23));
        csvPrueba.agregarAlCSV(Arrays.asList("Facundo", "Varela", 24));
        csvPrueba = new CSV(ruta, header);
        LinkedList<HashMap<String,String>> lectura = csvPrueba.leerTodoElCSV();
        assertEquals("Alejo",lectura.get(0).get(header[0]));
        assertEquals("Acevedo",lectura.get(0).get(header[1]));
        assertEquals("22",lectura.get(0).get(header[2]));
    }
}
