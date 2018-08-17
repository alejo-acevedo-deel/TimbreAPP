package app;

import Excepciones.InformacionDifiereDeHeaderException;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.*;
import java.util.*;


public class CSV {

    private Reader reader;
    private CSVParser csvParser;
    private Writer writer;
    private CSVPrinter csvPrinter;
    private String[] header;
    private String ruta;

    public CSV(String ruta, String[] header) throws IOException {
        this.ruta = ruta;
        this.header = header;
        try {
            crearCSV();
        }catch (FileAlreadyExistsException e){
            abrirCSV();
        }
        this.reader = Files.newBufferedReader(Paths.get(ruta));
        this.csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withHeader(header).withIgnoreHeaderCase().withSkipHeaderRecord().withTrim());
    }

    private void abrirCSV()throws IOException{
        this.writer = Files.newBufferedWriter(Paths.get(ruta), StandardOpenOption.APPEND);
        this.csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader(header).withSkipHeaderRecord());


    }

    private void crearCSV()throws IOException{
        this.writer = Files.newBufferedWriter(Paths.get(ruta), StandardOpenOption.CREATE_NEW);
        this.csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader(header));

    }

    public List<String> verEncabezado() throws IOException {
        Map<String, Integer> header = csvParser.getHeaderMap();
        header = this.csvParser.getHeaderMap();
        List<String> lista_encabezado = new ArrayList<String>(header.keySet());
        return lista_encabezado;
    }

    public void agregarAlCSV(List nueva_informacion) throws InformacionDifiereDeHeaderException, IOException {
        //LA LISTA DEBE ESTAR EN EL ORDEN DEL HEADER. Y POSEER LA MISMA CANTIDAD DE ITEMS.
        if(nueva_informacion.size() != this.header.length){
            throw new InformacionDifiereDeHeaderException();
        }
        this.csvPrinter.printRecord(nueva_informacion);
        this.csvPrinter.flush();
    }

    public boolean borrarDelCSV(LinkedList<String> info_a_borrar) throws InformacionDifiereDeHeaderException{
        //LA LISTA DEBE ESTAR EN EL ORDEN DEL HEADER Y POSEER LA MISMA CANTIDAD DE ITEMS
        //DEVUELVE FALSE SI LO QUE SE DESEA BORRAR NO EXISTE EN EL CSV
        return false;
    }

    public LinkedList<HashMap<String,String>> leerTodoElCSV(){
        //FALTA IMPLEMENTACION xD.
        //DEVUELVE UNA LISTA DE LISTAS, CADA ITEM DE LA LISTA CONTIENE UN DICCIONARIO CON CADA VALOR DE LA LINEA.
        LinkedList<HashMap<String,String>> return_list = new LinkedList<HashMap<String, String>>();
        for (CSVRecord csvRecord : csvParser){
            HashMap<String, String> aux = new HashMap<String, String>();
            for (String key : header){
                aux.put(key, csvRecord.get(key));
            }
            return_list.add(aux);
        }
        return return_list;
    }


}
