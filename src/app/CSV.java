package app;

import Excepciones.InformacionDifiereDeHeaderException;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
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

    }

    private void abrirCSV()throws IOException{
        this.writer = Files.newBufferedWriter(Paths.get(ruta), StandardOpenOption.APPEND);
        this.csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader(header).withSkipHeaderRecord());
    }

    private void crearCSV()throws IOException{
        this.writer = Files.newBufferedWriter(Paths.get(ruta), StandardOpenOption.CREATE_NEW);
        this.csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader(header));
    }

    private void pisarCSV()throws IOException{
        this.writer = Files.newBufferedWriter(Paths.get(ruta));
        this.csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader(header));
    }

    private void abrirLecturaCSV()throws IOException{
        this.reader = Files.newBufferedReader(Paths.get(ruta));
        this.csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withHeader(header).withIgnoreHeaderCase().withSkipHeaderRecord().withTrim());
    }

    public List<String> verEncabezado() throws IOException {
        Map<String, Integer> header = csvParser.getHeaderMap();
        header = this.csvParser.getHeaderMap();
        List<String> lista_encabezado = new ArrayList<String>(header.keySet());
        return lista_encabezado;
    }

    public void agregarAlCSV(HashMap<String, String> nueva_informacion) throws InformacionDifiereDeHeaderException, IOException {
        //LA LISTA DEBE ESTAR EN EL ORDEN DEL HEADER. Y POSEER LA MISMA CANTIDAD DE ITEMS.
        //DEVUELVO EL NUMERO DE LINEA AGREGADA
        LinkedList<String> aux = new LinkedList<String>();
        if(nueva_informacion.size() != this.header.length){
            throw new InformacionDifiereDeHeaderException();
        }
        for (String key : header){
            aux.add(nueva_informacion.get(key));
        }
        this.csvPrinter.printRecord(aux);
        this.csvPrinter.flush();
    }

    public void agregarTodoAlCSV(LinkedList<HashMap<String,String>> nueva_informacion) throws InformacionDifiereDeHeaderException, IOException{
        //LAS CLAVES DEL DICCIONARIO DEBEN SER LAS COMPONENTES DEL HEADER
        for (HashMap<String, String> linea : nueva_informacion){
            agregarAlCSV(linea);
        }
    }

    public boolean modificarDelCSV(HashMap<String,String> infoBorrar, HashMap<String, String> infoModificar) throws InformacionDifiereDeHeaderException, IOException{
        //LA LISTA DEBE ESTAR EN EL ORDEN DEL HEADER Y POSEER LA MISMA CANTIDAD DE ITEMS
        //DEVUELVE FALSE SI LO QUE SE DESEA BORRAR NO EXISTE EN EL CSV
        LinkedList<HashMap<String,String>> aux = this.leerTodoElCSV();
        boolean seBorro = false;
        this.cerrarCSV();
        this.pisarCSV();
        this.abrirLecturaCSV();
        for (HashMap<String, String> linea : aux){
            boolean borrar = false;
            for (String key : header){
                if(linea.get(key).compareTo(infoBorrar.get(key))==0){
                    borrar = true;
                    seBorro = true;
                }else{
                    borrar = false;
                    seBorro = false;
                }
            }
            if (borrar == false){
                agregarAlCSV(linea);
            }else{
                agregarAlCSV(infoModificar);
            }
        }
        return seBorro;
    }

    public boolean borrarDelCSV(HashMap<String,String> infoBorrar) throws InformacionDifiereDeHeaderException, IOException{
        //LA LISTA DEBE ESTAR EN EL ORDEN DEL HEADER Y POSEER LA MISMA CANTIDAD DE ITEMS
        //DEVUELVE FALSE SI LO QUE SE DESEA BORRAR NO EXISTE EN EL CSV
        LinkedList<HashMap<String,String>> aux = this.leerTodoElCSV();
        boolean seBorro = false;
        this.cerrarCSV();
        this.pisarCSV();
        this.abrirLecturaCSV();
        for (HashMap<String, String> linea : aux){
            boolean borrar = false;
            for (String key : header){
                if(linea.get(key).compareTo(infoBorrar.get(key))==0){
                    borrar = true;
                    seBorro = true;
                }else{
                    borrar = false;
                    seBorro = false;
                }
            }
            if (borrar == false){
                agregarAlCSV(linea);
            }
        }
        return seBorro;
    }

    public LinkedList<HashMap<String,String>> leerTodoElCSV() throws IOException {
        //DEVUELVE UNA LISTA DE LISTAS, CADA ITEM DE LA LISTA CONTIENE UN DICCIONARIO CON CADA VALOR DE LA LINEA.
        LinkedList<HashMap<String,String>> return_list = new LinkedList<HashMap<String, String>>();
        this.abrirLecturaCSV();
        for (CSVRecord csvRecord : csvParser){
            HashMap<String, String> aux = new HashMap<String, String>();
            for (String key : header){
                aux.put(key, csvRecord.get(key));
            }
            return_list.add(aux);
        }
        return return_list;
    }

    public void cerrarCSV() throws IOException{
        this.csvPrinter.close();
        this.csvParser.close();
        this.writer.close();
        this.reader.close();
    }
}
