package step.learning.threading;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class PercentDemo {
    private final Random random = new Random() ;
    private double sum ;  // спільний ресурс - різні потоки будуть його "додавати"
    private final Object sumLocker = new Object();//Обєкт синхронізації - довільного типу
    //лише (reference - type), доцільно брати найпростіший -  Object


    // Багатозадачність починається з налаштування вмконавця, яким, частіше за все.
    // є пул з граничною кількістю одночасно працюючих  потоків
    private final ExecutorService pool= Executors.newFixedThreadPool(5);

    public  void  runq() {
        for (int i = 0; i < 15; i++) {
            printHello(i+1);
        }
        try {
            // очікуємо завершення усіх задач але не довше часового обмеження
            pool.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
        pool.shutdown();
    }

    Future<?>printHello(int num)
    {
        return  pool.submit(()->{

            try {Thread.sleep(500+random.nextInt(2500)); }
            catch (InterruptedException ignored){}
            System.out.println("Hello "+ num);

        });
    }
    public void runOneTask(){
        //Багато задачність. Future - головний інтерфейс для задач
        Future<String> task1 = taskString();
        //код що виконується паралельно шз задачею
        System.out.println("Parallel");

        // очікуємо завершення задачі

        try {
             String result =task1.get(); //~ String result = await task1;
            System.out.println(result);
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }
        // щоб основний потік не закінчився до закінчення задач, пул потоків треба
        // завершувати примусово. За наявності незакінчених задач пулу завершить їх
        pool.shutdown();

    }

    // async String taskString(){ Thread.sleep(1500); return "Hello";}
    Future<String>taskString()      // Future<String> - задача що повертає результат String
    {
     return pool.submit(            // додавання задачі у чергу на виконання   (зі запуском)
             ()-> {                 // Callable - аналог Runnable фду з поверненням результату
         Thread.sleep(1500);
         return "Hello";

     });
    }
   // runThread
    public void run() {
        sum = 100 ;
        int months = 12;
        Thread[] threads = new Thread[months] ;
        // Створюємо та запускаємо потоки, які будуть додавати проценти до суми
        for (int i = 0; i < months; i++)
        {
            threads[i] = new PercentAdder( i + 1 ) ;
            threads[i].start();
        }

        try
        {
            for (int i = 0; i < months; i++)
            {
                threads[i].join() ; // Очікуємо завершення всіх потоків
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println( "Final sum: " + sum ) ;
    }
    class PercentAdder extends Thread {
        private int month ;

        public PercentAdder(int month) {
            this.month = month;
        }

        @Override
        public void run() {
            double localSum;
            double inflationRate;
            // імітуємо тривалий запит до API інфляції
                System.out.println("Month " + month + " started");
                int receivingTime = 100; //  random.nextInt(300)+200 ; // 200-500 мс на запит
                try {
                    Thread.sleep(receivingTime);
                } catch (InterruptedException ex) {
                    System.err.println(ex.getMessage());
                }
                // уявимо, що всі проценти = 10 (1.1)
            synchronized (sumLocker) {
                    localSum = sum;
                    inflationRate  = random.nextDouble() * 2.2;
                    sum *= inflationRate;//конкуренція одночасна робота зі спільним ресурсом
            }
            System.out.println( String.format("{  місяць: %d,\tсума до зміни: %.2f,\tсума після зміни: %.2f,\t відсоток: %.2f  }",month,localSum,sum,inflationRate)) ;
        }
    }

}
/*
Синхронізація потоків (задач)
Ідея - утворення "транзакції", яка поєднає декілька операцій:
  читання -- модифікація -- запис
у єдиний блок, який не може бути перерваний іншим потоком
Досягається це лише системними "інструментами", серед який -
  критична секція, мьютекс, семафор, тощо
 їх також називають "сигнальними об'єктами" у розумінні того, що
 їх зміна призводить до певного "сигналу" який очікують потоки
У Java (JVM) та .NET усі об'єкти (reference-type) автоматично мають у своєму складі
критичну секцію, яка можи використовуватись для задач синхронізації
засоби мови мають спеціальні оператори для спрощення управління цією секцією
Java -- synchronized,  C#  -- lock
 = Оголошуємо об'єкт довільного типу (reference-type), можна Object,
    можна вживати інші (вже наявні) об'єкти, оскільки від них потрібна
    лише критична секція.
 = Об'єкт має бути доступний у всіх потоках/задачах, що потребують
    синхронізації
 = Виділяємо "транзакцію" - набір команд, які не мають бути перервані
    Як правило - це область у який фігурує звернення до спільного ресурсу.
    !! Намагаємось зробити цю область мінімальною. Чим вона більше, тип
       гірша паралельність. Якщо синхронізується все тіло, то паралельність зникає -
       все виконується послідовно
       Часто для цього треба переорганізувати код, зокрема, ввести локальні змінні
 = !! стежимо за тим, щоб протягом виконання синхронізованого блоку об'єкт
      синхронізації не зазнав змін
      synchronized(collection) {     -- нормально
         collection.add( object ) ;  -- колекція не змінює посилання
      }
      synchronized(text) {     -- не нормально
         text += "addon" ;     -- рядок змінюється - через додавання утворюється новий
      }
      блокується text (закривається його критична секція)
      після чого у змінну text передається новий об'єкт з відкритою секцією,
        відповідно інший блок synchronized(text) не буде зупинений
      по закінченню блоку відбудеться відкриття секції але у відкритого об'єкту - це конфлікт
      початковий об'єкт text передається на GC із закритою секцією (dead lock)
  =Рекоминдація - вживати у синхроблоці лише константи  (незмінні об'єкти)
 */


/*
 Ідея: порахувати річну інфляцію якщо відомі відсотки за кожен місяць.
   ( 100 + 10% ) + 20%  =?= ( 100 + 20% ) + 10%
   100 * 1.1 * 1.2  == 100 * 1.2 * 1.1
 В силу того, що порядок врахування процентів довільний, задача може виконуватись
 у багатопоточному режимі


 конкуренція одночасна робота зі спільним ресурсом
 вирішення питань конкуренції - задача  синхронизації потоків
 */