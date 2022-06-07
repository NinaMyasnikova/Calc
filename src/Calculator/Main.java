package Calculator;

import java.util.Scanner;

public class Main
{
    public static void main(String[] args) throws ScannerException
    {
        System.out.println("Введите выражение арабскими или римскими числами");
        Scanner read = new Scanner(System.in);
        String str = read.nextLine();

        Calculator calculator = new Calculator();
        System.out.println(calculator.calc(str));
    }
}
