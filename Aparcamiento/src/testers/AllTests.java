package testers;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/** Test Suite. Ejecutar para realizar todas las pruebas.
 * 
 * @author Juan Carlos Gonzalez Cabrero
 * @author Oscar Fernandez Nuñez
 * @version 1.0
 */
@RunWith(Suite.class)
@SuiteClasses({ TestsFallo.class, TestsValidos.class })
public class AllTests {

}
