package step.learning.ioc;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

/**

 Модуль конфігурації інжектор - тут зазначаються співвідношення
 інтерфейсів та їх реалізацій, а також інші засоби постачання
 * **/
public class ConfigModule extends AbstractModule {
    @Override
    protected void configure() {
        //звя'зування за типом
     bind(GreetingService.class).to(HiService.class);


     //іменоване зв'язування
        bind(PartingService.class).annotatedWith(Names.named("bye")).to(ByeService.class);

        bind(PartingService.class).annotatedWith(Names.named("goodbye")).to(GoodbyeService.class);
    }
}
