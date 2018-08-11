package app;

import Exceciones.InformacionDifiereDeHeaderException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class CSV {
    private Reader reader;
    private CSVParser csvParser;
    void CSV(String ruta,ArrayList<String> header) throws IOException {
        this.reader = Files.newBufferedReader(Paths.get(ruta));
        CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withHeader(String.valueOf(header)).withIgnoreHeaderCase().withSkipHeaderRecord().withTrim());
    }

    public List<String> verEncabezado() throws IOException {
        Map<String, Integer> header = csvParser.getHeaderMap();
        header = this.csvParser.getHeaderMap();
        List<String> lista_encabezado = new ArrayList<String>(header.keySet());
        return lista_encabezado;
    }

    public void agregarAlCSV(LinkedList<String> nueva_informacion)throws InformacionDifiereDeHeaderException {
        //LA LISTA DEBE ESTAR EN EL ORDEN DEL HEADER. Y POSEER LA MISMA CANTIDAD DE ITEMS.
    }

    public boolean borrarDelCSV(LinkedList<String> info_a_borrar) throws InformacionDifiereDeHeaderException{
        //LA LISTA DEBE ESTAR EN EL ORDEN DEL HEADER Y POSEER LA MISMA CANTIDAD DE ITEMS
        //DEVUELVE FALSE SI LO QUE SE DESEA BORRAR NO EXISTE EN EL CSV
        return false;
    }

    public LinkedList<LinkedList<String>> leerTodoElCSV(){
        //FALTA IMPLEMENTACION xD.
        //DEVUELVE UNA LISTA DE LISTAS, CADA ITEM DE LA LISTA CONTIENE UNA LISTA CON CADA VALOR DE LA LINEA.
        LinkedList<LinkedList<String>> return_list = new LinkedList<LinkedList<String>>();
        //SI, ME DABA PAJA EL ERROR Y SOLO LA DECLARE PARA NO VERLA EN ROJO.
        return return_list;
    }


}
