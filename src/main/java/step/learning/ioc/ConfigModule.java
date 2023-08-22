package step.learning.ioc;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Provides;
import com.google.inject.name.Named;
import com.google.inject.name.Names;

import java.util.Random;

/**

 Модуль конфігурації інжектор - тут зазначаються співвідношення
 інтерфейсів та їх реалізацій, а також інші засоби постачання
 * **/
public class ConfigModule extends AbstractModule {
    @Override
    protected void configure() {
        //звя'зування за типом
     //bind(GreetingService.class).to(HiService.class); //Lazy ледача залежність  (якщо не потрібний то може і не створитись)
        bind(GreetingService.class).toInstance(new HiService());  // Eager (Обовязково створеться і в конструктор можна передати параметр якщо потрібно )
                                                                  // як правило коли складний конструктор і довго створюється



     //іменоване зв'язування
        bind(PartingService.class).annotatedWith(Names.named("bye")).to(ByeService.class);

        bind(PartingService.class).annotatedWith(Names.named("goodbye")).to(GoodbyeService.class);

        //Зв'язування "готових" об'єктів
        bind(String.class)
                .annotatedWith(Names.named("planetConnection"))
                .toInstance("The planetConnection");

        bind(String.class)
                .annotatedWith(Names.named("logFileName"))
                .toInstance("The logFileName");


    }

    //Методи-постачальники
    private  Random _random;
//    @Provides
//    Random randomProvider()
//    {
//       // return new Random(); //Transient (кожного разу новий)
//        if(_random==null) //Singleton (один і той самий буде повертатись )
//        {
//            _random=new Random();
//        }
//        return _random;
//    }

    @Provides @Named("java.util")
    Random randomProvider()
    {
        // return new Random(); //Transient (кожного разу новий)
        if(_random==null) //Singleton (один і той самий буде повертатись )
        {
            _random=new Random();
        }
        return _random;
    }
}
