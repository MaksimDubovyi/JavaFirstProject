package step.learning.file;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import step.learning.oop.*;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class GsonDemo {


    public void SaveLibrary(Library library)
    {


        Gson gson=new GsonBuilder()   // Builder - створює серіалізатор із додатковими налаштуваннями
                .serializeNulls()      // значення  null будуть додаватись до результату
                .setPrettyPrinting()   // форматування виведення - відступів та рядків
                .create();

        String literature=gson.toJson(library);

        System.out.println(literature);
        try(FileWriter writer=new FileWriter("Library.txt")) //створення файлу та запис в нього
        {
            writer.write(literature);
        }
        catch (IOException ex)
        {
            System.out.println(ex.getMessage());
        }

    }

    public void LoadLibrary()
    {

        Gson gson=new GsonBuilder()   // Builder - створює серіалізатор із додатковими налаштуваннями
                .registerTypeAdapter(Literature.class, new LiteratureDeserializer())     //використовується для реєстрації кастомного десеріалізатора
                                                                                         // LiteratureDeserializer для класу Literature під час роботи з бібліотекою Gson.
                .serializeNulls()      // значення  null будуть додаватись до результату
                .setPrettyPrinting()   // форматування виведення - відступів та рядків
                .create();

        try(FileReader reader=new FileReader("Library.txt");Scanner scanner =new Scanner(reader)) //читання з файлу
        {
            StringBuilder str=new StringBuilder();
            while (scanner.hasNext())                                                     // Читаю файл в String
            {  str.append(scanner.nextLine());}
String st=str.toString();
            LoadLibrary literature =gson.fromJson(st,  LoadLibrary.class);               // Десеріалізація  String в LoadLibrary
            List<Literature> funds=literature.getFunds();                                 //

            for(Literature literatur:funds)                                               // Проходжу по  List<Literature> funds і
            {                                                                             // визначаю тип
                if(literatur instanceof Books)
                    System.out.println("____________________________________________________\nПривіт я Books:\n"+literatur.getCard());
                else if(literatur instanceof Journal)
                    System.out.println("____________________________________________________\nПривіт я Journal:\n"+literatur.getCard());
                else if(literatur instanceof Newspaper)
                    System.out.println("____________________________________________________\nПривіт я Newspaper:\n"+literatur.getCard());
                else if(literatur instanceof Hologram)
                    System.out.println("____________________________________________________\nПривіт я Hologram:\n"+literatur.getCard());
                else if(literatur instanceof Poster)
                    System.out.println("____________________________________________________\nПривіт я Poster:\n"+literatur.getCard());
            }
        }
        catch (IOException ex)
        {
            System.out.println(ex.getMessage());
        }

    }
    public  void run2()
    { Gson gson2=new GsonBuilder()   // Builder - створює серіалізатор із додатковими налаштуваннями
            .serializeNulls()      // значення  null будуть додаватись до результату
            .disableHtmlEscaping() // відключення екранування HTML тегів
            .setPrettyPrinting()   // форматування виведення - відступів та рядків
            .serializeSpecialFloatingPointValues() //включення спец. значень на кшталт Infinity
            .excludeFieldsWithModifiers(Modifier.ABSTRACT)  // зміна поведінки - замість ігнорування усіх модмфікаторів буде ігноруватись лише ABSTRACT (а STATiC - не буде )
            .setDateFormat(DateFormat.LONG)//.LONG(dateFiled": "Jul 20, 2023 11:51:13 AM)
            .create();


        DataObject2 data = new DataObject2();
        data.setFiled1(10);
        DataObject2.staticFiled=20;
        System.out.println(gson2.toJson(data));

        data.setFiled1(Double.POSITIVE_INFINITY);
        System.out.println(gson2.toJson(data));//IllegalArgumentException: Infinity is not a valid double value
    }
    public void run1()
    {
        DataObject data =new DataObject()
        .setField1("value1")   // fluid interface - всі методи повертають обєкт
        .setField2("value2");  // це дозволяє створювати ланцюг ініціалізації

        Gson gson=new Gson();
        System.out.printf("%s -- %s%n",data, gson.toJson(data)); //{"field1":"value1","field2":"value2"}
        data.setField1(null);
        System.out.printf("%s -- %s%n",data, gson.toJson(data)); //{"field2":"value2"} - null не потрапляє у JSON

        DataObject data2 =gson.fromJson("{\"field2\":\"value2\"}",DataObject.class);
        System.out.printf("%s -- %s%n",data2, gson.toJson(data2));

        data.setField1("<h1>Hello</h1>");
        //налаштування GsonBuilder   https://www.javadoc.io/doc/com.google.code.gson/gson/2.8.5/com/google/gson/GsonBuilder.html
        Gson gson2=new GsonBuilder()   // Builder - створює серіалізатор із додатковими налаштуваннями
                .serializeNulls()      // значення  null будуть додаватись до результату
                .disableHtmlEscaping() // відключення екранування HTML тегів
                .setPrettyPrinting()   // форматування виведення - відступів та рядків
                .create();             //
        System.out.println("_________Builder_________");
        System.out.printf("%s -- %s%n",data2, gson2.toJson(data2)); //{"field1":null,"field2":"value2"}

        System.out.println("\n__gson_______<h1>Hello</h1>_________");
        System.out.printf("%s -- %s%n",data, gson.toJson(data));//{"field1":"\u003ch1\u003eHello\u003c/h1\u003e","field2":"value2"}

        System.out.println("\n_gson2________<h1>Hello</h1>_________");
        System.out.printf("%s -- %s%n",data, gson2.toJson(data));//{"field1":"<h1>Hello</h1>","field2":"value2"}
    }

    public void run()
    {
        System.out.println("GsonDemo Demo");
        Gson gson=new Gson();                                                //Створюемо Сеарілізатор
        Book book =new Book("Art of Programming", "D. Knuth");

        System.out.println(gson.toJson(book));
        String json2="{\"author\":\"D. Knuth\",\"title\":\"Art of Programming\"}";
        Book book2 = gson.fromJson(json2, Book.class);

        System.out.println(book2.getCard());

         try(FileWriter writer=new FileWriter("book.txt")) //створення файлу та запис в нього
         {
             writer.write(json2);
         }
         catch (IOException ex)
         {
             System.out.println(ex.getMessage());
         }
        try(FileReader reader=new FileReader("book.txt")) //читання з файлу
        {
           Book book3 =gson.fromJson(reader, book2.getClass());
            System.out.println("-----book3------");
            System.out.println(book3.getCard());
        }
        catch (IOException ex)
        {
            System.out.println(ex.getMessage());
        }
    }
}
/*
Задежності Maven на прикладі Gson

Maven має свій репозиторій - колекцію додаткових пакетів класів - mvnrepository.com
 На цьому сайті знаходимо шуканий пакет, обираємо версію (як правило останню), копіюємо
 інструкцію залежності наприклад

 <!-- https://mvnrepository.com/artifact/com.google.code.gson/gson -->
<dependency>
    <groupId>com.google.code.gson</groupId>
    <artifactId>gson</artifactId>
    <version>2.10.1</version>
</dependency>

Вставляємо ці інструкції у файл pom.xml у секцію  <dependencies>
Після чого необхідно оновити - завантажити залежності
або) зявляється кнопка М (Maven) при будь-яких змінах у  pom.xml
або) відкриває панель (інструмент) (Maven) і натискаємо Reload

Сама функціональність - це бібліотека класів (.JAR) - аналог DLL
Вона завантажується у локальний проєкт і включається до компіляції
Також вона має бути у (Production) коді (у деплої).
*/