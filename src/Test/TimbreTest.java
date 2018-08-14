package Test;

import Excepciones.FaltaIP;
import Excepciones.FaltaNombre;
import app.Timbre;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class TimbreTest {

    @Test
    public void testCrearTimbreNombreVacio(){
        Throwable exception = assertThrows(FaltaNombre.class,() -> new Timbre("","192.168.1.1"));
    }

    @Test
    public void testCrearTimbrIPVacio(){
        Throwable exception = assertThrows(FaltaIP.class, () -> new Timbre("Timbre1",""));
    }
}
