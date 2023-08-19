package step.learning.hash;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import step.learning.ioc.PartingService;

import java.util.Scanner;

public class AppHash {

    public void run()
    {
        System.out.println("<--------------AppHash-------------->");
        menu();
    }

    private final IHashservice iHashserviceMd5;
    private final IHashservice iHashservicesha;
    private final IHashservice iHashservicesha256;
    private final IHashservice iHashservicesha512;
    private final IHashservice iHashserviceArgon;

    @Inject
    public AppHash(@Named("md5") IHashservice iHashservice,
                   @Named("sha1") IHashservice iHashservicesha,
                   @Named("sha256") IHashservice iHashservice256,
                   @Named("sha512") IHashservice iHashservice512,
                   @Named("argon") IHashservice iHashserviceArgon)
    {
        this.iHashserviceMd5 = iHashservice;
        this.iHashservicesha=iHashservicesha;
        this.iHashserviceArgon=iHashserviceArgon;
        this.iHashservicesha256=iHashservice256;
        this.iHashservicesha512=iHashservice512;
    }



    private int IntCheck(String str)
    {
        Scanner scanner = new Scanner(System.in) ;
        int count = 0;

        do {
            System.out.print(str);

            if (scanner.hasNextInt())
            {
                count = scanner.nextInt();
                if (count < 0)
                    System.out.println("Введіть додатнє число.");
                else
                    break; // Вихід із циклу, якщо введено коректне значення

            }
            else
            {
                System.out.println("Некорректний ввід. Введіть ціле число.");
                scanner.nextLine(); // Очищаем буфер ввода
            }
        } while (true);
        return count;
    }
    private void menu()
    {

        do {

            int choice= IntCheck("\n\nОберіть операцію (Введіть номер та натисніть Enter):" +
                    "\n1# Хешувати в MD5  :" +
                    "\n2# Хешувати в SHA1 :" +
                    "\n3# Хешувати в SHA-256 :" +
                    "\n4# Хешувати в SHA-512 :" +
                    "\n5# Хешувати в Argon2 :" +
                    "\n6# Хешувати в MD5, SHA,SHA-256,SHA-512 та Argon2 :" +
                    "\n");
            String str= getStr();
            if (choice<7&&choice>0)
            {
                switch (choice)
                {
                    case 1:
                        hashMd5 (str);
                        break;
                    case 2:
                        hashSha(str);
                        break;
                    case 3:
                        hashSha256(str);
                        break;
                    case 4:
                        hashSha512(str);
                        break;
                    case 5:
                        hashArgon2(str);
                        break;
                    case 6:
                        hashAll(str);
                        break;
                }
            }
            else
            {
                System.out.println("Некорректний ввід.");
            }
        } while (true);

    }

    private String getStr()
    {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введіть пароль: ");
        return scanner.nextLine();
    }
    private void hashMd5(String str)
    {
        String hash= this.iHashserviceMd5.GetHash(str);
        System.out.printf("\nХешовано в Md5 - <---------- '%s' ---------->\n",hash);
    }

    private void hashSha(String str)
    {
        String hash= this.iHashservicesha.GetHash(str);
        System.out.printf("\nХешовано в SHA1 - <---------- '%s' ---------->\n",hash);
    }
    private void hashArgon2(String str)
    {
        String hash= this.iHashserviceArgon.GetHash(str);
        System.out.printf("\nХешовано в Argon2 - <---------- '%s' ---------->\n",hash);
    }
    private void hashSha256(String str)
    {
        String hash= this.iHashservicesha256.GetHash(str);
        System.out.printf("\nХешовано в SHA-256 - <---------- '%s' ---------->\n",hash);
    }
    private void hashSha512(String str)
    {
        String hash= this.iHashservicesha512.GetHash(str);
        System.out.printf("\nХешовано в SHA-512 - <---------- '%s' ---------->\n",hash);
    }

    private  void hashAll(String str)
    {
        hashMd5(str);
        hashSha(str);
        hashArgon2(str);
        hashSha256(str);
        hashSha512(str);
    }
}
