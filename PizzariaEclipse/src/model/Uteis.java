package model;

import java.util.Scanner;

public class Uteis {
    private static final Scanner sc = new Scanner(System.in);

    public static void mostrarLinha(){
        System.out.println("______________________________________________");
    }
    public static int leInt(String msg){
        int numero;
        while(true){
            try{
                System.out.print(msg);
                numero = Integer.parseInt(sc.nextLine());
                break;
            }catch(NumberFormatException e){
                System.out.println("Digite um número válido.");
            }
        }
        return numero;
    }

    public static String leString(String msg){
        System.out.print(msg);
        return sc.nextLine();
    }
}
