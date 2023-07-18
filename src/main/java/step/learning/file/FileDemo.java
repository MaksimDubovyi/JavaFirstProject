package step.learning.file;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class FileDemo {

    public void run()
    {
        System.out.println("Files");
        //dirDemo();
        //ioDemo();
        dirDemoHomeWork();

    }

    /**
     * Демонстрація роботи з файловою системою
     */
    private  void dirDemo() {
        File currentDir =                          //File(java.io) -основний для роботи як з файлами, так і папками.!!
                new File("./");   //створення new File не впливає на ОС, лише створює обєкт
//        "F:/Навчання"
        System.out.printf("File '%s' ", currentDir.getName());
        if (currentDir.exists()) {
            System.out.printf("exists %n");   // %n -> std::endl, \n -> один символ
        } else {
            System.out.printf("dose not exists %n");
        }
        if(currentDir.isDirectory())
        {
            System.out.printf("Is Directory%n");
            for(File file:currentDir.listFiles())
            {
                System.out.printf("%s\t%s %n",
                        file.getName(),
                        file.isDirectory()?"<DIR>":"file"
                );
            }

        }
        else
        {
            System.out.printf("Is File: %s%s%s %n ",     // 1  1
                    currentDir.canRead()?"r":"R",        // 1  0
                    currentDir.canWrite()?"w":"W",       // 1  1
                    currentDir.canExecute()?"x":"X");    // 7  5

        }

    }

    /**
     * Демонстрвція введення/виведення з файлами
     */
    private void ioDemo()
    {
     String fileContent = "This is content of a file\n"+
             "This is a new line";
     String filename ="test-file.txt";
     try(FileWriter writer =new FileWriter(filename))
     {
         writer.write(fileContent);
         System.out.println("Write success");
     }
     catch (IOException ex)
     {
         System.out.println(ex.getMessage());
     }
        System.out.println("Reading.....");
     try(FileReader reader =new FileReader(filename); Scanner scanner =new Scanner(reader))
     {
         while (scanner.hasNext()) {
             System.out.println(scanner.nextLine());
         }
     }
     catch (IOException ex)
     {
         System.out.println(ex.getMessage());
     }
    }

/**
 *  Д.З. Реалізувати виведення даних про вміст директорії за зразком
 *  операції "dir" ("ls")
 */
private  void dirDemoHomeWork()
{
    System.out.println("--------Введіть директорію  наприклад (D:   F:/Навчання)");
    Scanner scanner = new Scanner(System.in) ;
    String path= scanner.nextLine();

    File currentDir =new File(path);
    int fileCount=0,dirCount=0;
    long fileSize=0;
    String fileContent ="";
        System.out.printf("Is Directory%n");
        for(File file:currentDir.listFiles())
        {
            Date lastModifiedDate = new Date(file.lastModified());
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
            String formattedDate = dateFormat.format(lastModifiedDate);

            fileContent+=String.format("\t\t%s\n\t%s\t\t%s\t\t%s %n____________________________________________________%n",
                    file.getName(),
                    formattedDate,
                    file.isDirectory() ? "<DIR>" : "file",
                    file.length());

            System.out.printf("\t\t%s\n\t%s\t\t%s\t\t%s %n____________________________________________________%n",
                    file.getName(),
                    formattedDate,
                    file.isDirectory()?"<DIR>":"file",
                    file.length()
            );
            if(!(file.isDirectory()))
            {
                fileSize+=file.length();
                fileCount++;
            }
            else {
                dirCount++;
            }

        }
    System.out.println("\n\t\t\t\t"+fileCount+" - Файлів\t"+fileSize+" байт"+"\n\t\t\t\t"+dirCount+" - Папок");
    fileContent+="\n\t\t\t\t"+fileCount+" - Файлів\t"+fileSize+" байт"+"\n\t\t\t\t"+dirCount+" - Папок";

    String filename ="dir.txt";
    try(FileWriter writer =new FileWriter(filename))
    {
        writer.write(fileContent);
    }
    catch (IOException ex)
    {
        System.out.println(ex.getMessage());
    }


}

}
/*Робота з файлами поділяється на дві групи
1. Робота з файловою системою - копіювання, видалення, створення файлів,пошук тощо.
2. Вивористання файлів для збереження даних

1.Дивись. dirDemo()
2- особливість JAVA - наявнівсть великої колькості засобів роботи з потоками (stream)
FileReader -потокове читання по символах
FileImputSrteam - --//-- по байтах
BufferdReader - "оболонка" яка утворює проміжний буфер, який зменшує кіклькість прямих операцій з потоку
Scanner - оболонка для читання різних типів даних.


OutputStream
FileWriter
FileOutputStream
PrintWriter
BufferedWriter
PrintWriter - оболонка , яка яка надає засоби "друку" (переводить різні типи даних у символи для потоку)

(int32) 127 ->
(bin) 01111111 00000000 00000000 00000000
(txt) 00110001 00110010 00110111
          '1'      '2'      '7'


try-with-resource:
try(Resource res = new Resource() )
{ -- замість using (C#) --AutoClosable }
catch(){}
*/