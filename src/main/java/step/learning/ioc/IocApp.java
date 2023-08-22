package step.learning.ioc;

import com.google.inject.Inject;
import com.google.inject.name.Named;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class IocApp {

   /*@Inject
   private GreetingService helloService ;

   @Inject @Named("bye")
   private  PartingService byeServise;

   @Inject @Named("goodbye")
   private  PartingService goodbyeServise;*/

   private final GreetingService helloService ;

   private final PartingService byeServise;

   private final PartingService goodbyeServise;

   private final Random random;
   @Inject @Named("planetConnection")  // Можлива змішана інжекція і через конструктор
   private String connectionString;    // і через поля- обидві працюють одночасно
   @Inject @Named("logFileName")
   private String logFileName;

   @Inject @Named("java.util")
   private   Random random2;

   private final Logger logger; // Guice автоматично постачає Logger(java.util)
   @Inject
   public IocApp(GreetingService helloService, @Named("bye") PartingService byeService,@Named("goodbye") PartingService goodbyeService, @Named("java.util") Random random,Logger logger )
   {
      this.helloService = helloService;
      this.byeServise = byeService;
      this.goodbyeServise = goodbyeService;
      this.random=random;
      this.logger=logger;

   }

   public void run() {
      checkingAllInjectedServices();
      System.out.println( "App works" ) ;
      //один інтерфейс одна  реалізація
      helloService.sayHello() ;
      System.out.println(connectionString);
      System.out.println(logFileName);
      System.out.println(random.nextInt(100));
      System.out.println(random.hashCode() +" "+ random2.hashCode());

      //один інтерфейс дві реалізації  (в файлі конфігурацій див )
      byeServise.sayGoodbye();
      goodbyeServise.sayGoodbye();

   }
   private void checkingAllInjectedServices()
   {

      if(this.random2!=null)
         logger.log(Level.INFO,"Служба random2 ініціалізована" );
      else
         logger.log(Level.SEVERE,"Служба random2 NULL");

      if(this.random!=null)
         logger.log(Level.INFO,"Служба random ініціалізована");
      else
         logger.log(Level.SEVERE,"Служба random NULL");

      if(this.logFileName!=null)
         logger.log(Level.INFO,"Служба logFileName ініціалізована");
      else
         logger.log(Level.SEVERE,"Служба logFileName NULL");

      if(this.byeServise!=null)
         logger.log(Level.INFO,"Служба byeServise ініціалізована");
      else
         logger.log(Level.SEVERE,"Служба byeServise NULL");

      if(this.goodbyeServise!=null)
         logger.log(Level.INFO,"Служба goodbyeServise ініціалізована");
      else
         logger.log(Level.SEVERE,"Служба goodbyeServise NULL");

      if(this.helloService!=null)
         logger.log(Level.INFO,"Служба helloService ініціалізована");
      else
         logger.log(Level.SEVERE,"Служба helloService NULL");


   }
}
/**
 IOC Inversion Of Control
 **/

/**
 Інверсія управління (Inversion of control), інверсія залежності (DI - dependency injection)
 Управління чим ? Життєвим циклом обє'ктів

 - Без інверсії (звичайне управління)
   instance = new Type()- створення обє'кту
   instance = null - "знищення" об'єкту - віддача до GC

 - З інверсією
   service <- Type [Singleton]
   @Inject instance (Резолюція) --- Засіб IoC надасть посилання на об'єкт


   SOLID
   O - open/close - доповнюй, але не змінюй
   D - DIP dependency inversion (! не injection) principle не рекомендується залежність від конкретного типу,
   рекомендується - від інтерфейсу (абстрактного типу)
   Приклад:
      розробляємо нову версію (покращуємо шифратор Cipher)
   - (не рекомендовано) - вносити зміни у клас Cipher
   - (рекомендовано) - створюємо нащадка CipherNew та змінюємо
      залежності від Cipher на CipherNew
   = для спрощення другого етапу вживається DIP:
     замість того щоб створювати залежнівсть від класу (Cipher)
     private Cipher cipher;
    бажано утворити інтерфейс та впроватжувати залежність через нього
   ICipher
   Cipher:ICipher;
   private ICipher cipher;
   а у IoC зазначаємо, що під інтерфейсом ICipher буде клас Cipher
   це значно спростить заміну нової реалізації на CipherNew і зворотні зміни

      Техніка:
   До проєкту додається інвертор (інжектор), наприклад Google Guice або Spring
   Стартова точка налаштовує сервіси, вирішує (resolve) перший клас (частіше за все App)
   Інші класаи замістять створення нових об'єктів-служб вказують залежності від них

   - Google Guice - Maven залежність   <!-- https://mvnrepository.com/artifact/com.google.inject/guice -->
   - клас IocApp (цей файл )
   - Main ()
 * **/

/*
Принципи інжекції
а) якщо клас відомий у проєкті (є частиною проєкту), то інжектор може впраджувати
об'єкт даного класу без додаткових конфігурацій, достатньо додати анотацію @Inject
(приватні поля - інжектуються)
 недолік інжекції через поля - вони залишаються змінними (не константами),
 відповідно, можуть бути замінені навмисно або випадково
б) через конструктор - можлива ініціалізація незмінних (final) полів
 переваги
   - захист посилань на служби
   - захист від створення об'єкту без служб (наявність конструктора
      прибирає конструктор за замовчуванням)
 *) правило інжекторів - якщо у класа є декілька конструкторів, то брати  в якого найбільша кількість
  параметрів
 */