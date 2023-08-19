package step.learning.ioc;

import com.google.inject.Inject;
import com.google.inject.name.Named;

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

   @Inject
   public IocApp(GreetingService helloService, @Named("bye") PartingService byeService,@Named("goodbye") PartingService goodbyeService )
   {
      this.helloService = helloService;
      this.byeServise = byeService;
      this.goodbyeServise = goodbyeService;
   }

   public void run() {
      System.out.println( "App works" ) ;

      //один інтерфейс одна  реалізація
      helloService.sayHello() ;

      //один інтерфейс дві реалізації  (в файлі конфігурацій див )
      byeServise.sayGoodbye();
      goodbyeServise.sayGoodbye();
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