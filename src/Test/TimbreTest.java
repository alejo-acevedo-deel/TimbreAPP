package Test;

import Excepciones.FaltaIP;
import Excepciones.FaltaNombre;
import app.Timbre;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TimbreTest {

    String nombre = "Prueba";
    String ip = "192.168.0.1";
    Timbre timbre;

    @BeforeEach
    public void crearTimbre() throws FaltaNombre, FaltaIP{
            this.timbre = new Timbre(nombre,ip);
    }

    @Test
    public void testCrearTimbreNombreVacio(){
        Throwable exception = assertThrows(FaltaNombre.class,() -> new Timbre("",ip));
    }

    @Test
    public void testCrearTimbreIPVacio(){
        Throwable exception = assertThrows(FaltaIP.class, () -> new Timbre(nombre,""));
    }

    @Test
    public void testCrearTimbre(){
        try{
            Timbre timbre = new Timbre(nombre, ip);
            assertTrue(true);
        }catch (FaltaNombre faltaNombre){
            assertTrue(false);
        }catch (FaltaIP faltaIP){
            assertTrue(false);
        }
    }

    @Test
    public void testObtenerNombre(){
        assertEquals(nombre,this.timbre.obtenerNombre());
    }

    @Test
    public void testObtenerIP(){
        assertEquals(ip, this.timbre.obtenerIp());
    }

    @Test
    public void testSetearNombre(){
        String nuevoNombre = "Nuevo nombre";
        try{
            this.timbre.setearNombre(nuevoNombre);
        }catch (FaltaNombre faltaNombre){
            assertTrue(false);
        }
        assertEquals(nuevoNombre, this.timbre.obtenerNombre());
    }

    @Test
    public void testSetearIP(){
        String nuevaIP = "192.168.0.100";
        try {
            this.timbre.setearIp(nuevaIP);
        }catch (FaltaIP faltaIP){
            assertTrue(false);
        }
        assertEquals(nuevaIP, this.timbre.obtenerIp());
    }

}
