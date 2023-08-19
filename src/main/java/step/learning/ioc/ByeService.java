package step.learning.ioc;

import com.google.inject.Singleton;


@Singleton
public class ByeService implements  PartingService{
    @Override
    public void sayGoodbye() {
        System.out.println("Bye!");
    }
}