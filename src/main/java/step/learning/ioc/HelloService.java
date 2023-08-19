package step.learning.ioc;

/**
 * Демонстрація роботи зі службами
 */
public class HelloService implements GreetingService {
    public void sayHello() {
        System.out.println( "Hello, World!" ) ;
    }
}