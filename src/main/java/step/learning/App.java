package step.learning;

import step.learning.control.ControlDemo;
import step.learning.file.FileDemo;
import step.learning.file.GsonDemo;
import step.learning.oop.Library;

/**
 /*
 Java Вступ

 Java - ООП мова програмування, на сьогодні курується Oracle
 Мова типу "транслятор" - компілюється у байт-код (проміжний
 код), який виконується спеціальною платформою (JRE -
 Java Runtime Environment) або JVM (Virtual Machine)
 Ця платформа встановлюється як окреме ПЗ. Для перевірки
 можна виконати у терміналі команду
 java -version

 У Java гарна "зворотна" сумісність - старші платформи нормально
 виконують код, створений ранніми платформами
 Є визначна версія - Java8 (1.8), яка оновлюється, але не
 модифікується. На ній працює більшість програмних комплексів
 типу ЕЕ

 Java SE (Standart Edition) - базовий набір
 Java EE (Enterprise Edition) - базовий набір + розширені засоби

 Для створення програм необхідний додатковий пакет - JDK (Java
 Development Kit)

 Після встановлення JRE, JDK встановлюємо IDE (Intellij Idea)
 Створюємо новий проєкт.
 Як правило, проєкти базуються на шаблонах, орієнтованих на
 простоту збирання проєкту - підключення додаткових модулів,
 формування команд компілятору та виконавцю, тощо
 Поширені системи - Maven, Gradle, Ant, Idea
 При створенні нового проєкту - вибираємо Maven Archetype
 тип - org.apache.maven.archetypes:maven-archetype-quickstart

 Після створення проєкту конфігуруємо запуск (від початку
 налаштовано запуск Current File) - Edit Configuration -
 - Create new - Application -- вводимо назву (Арр) та
 вибираємо головний клас - Арр
 */

/*Java - інтерпретована мова яка файлию. java(вихідний код)
компілює у файли .class ( проміжний код), які виконуються
 JVM командою
 >java.exe step.learning.App
 На відміну від студії окреме вікно в консолі не відкривається,
 виведення та введення проводиться через IDE, вікно Run

У Java сувора прив'язка до структури файлів та папок
- папка - це package (пакет, аналог namespace)
   назва папки один-до-одного має збігатись із іменем
   пакету. Прийнято lowercase для назви пакетів
- файл - це клас. Обмеження - один файл - один public клас
   зауваження: в одному файлі може бути декілька класів,
    але лише один public, тобто видимий у цьому файлі
    а також є внутрішні (Nested) класи - класи у класах
   Назва класа має один-до-одного збігатись з іменем файла
   Для імен класів прийнято CapitalCamelCase
 */
public class App 
{
    public static void main( String[] args )
    {
     Library library = new Library();
        //new ControlDemo().run();
        //new Library().showCatalog();
        //new FileDemo().run();
        //new GsonDemo().run();
        //new GsonDemo().run1();
        //new GsonDemo().run2();
        new GsonDemo().SaveLibrary(library);
        new GsonDemo().LoadLibrary();

    }
}
