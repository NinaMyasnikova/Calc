package Calculator;

import java.util.Locale;
import java.util.Objects;

class Calculator
{
    boolean arabBool=false; //переменная для определения системы исчсиления. Если true, то арабская, false - римская
    boolean arab = false;
    boolean rome = false;

    String [] arabUnits = new String[] {"1","2","3","4","5","6","7","8","9", "10"};
    String [] romeUnits = new String[] {"I","II","III","IV","V","VI","VII","VIII","IX", "X"};
    String [] arabDozens = new String[] {"10", "20", "30", "40", "50", "60", "70", "80", "90", "100"};
    String [] romeDozens = new String[] {"X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC", "C"};
    //4 массива для работы римскими цифрами


    public String calc(String str) throws ScannerException
    {
        String result; //переменная, возвращаемая при вызове метода

        if (str.contains(" ")) //можно ввести 5+5, а можно 5 + 5, поэтому убираю пробелы, чтобы исключить ошибку
        {
            str = str.replace(" ", "");
        }

        char[] strToArray = str.toCharArray(); // преобразование строки str в массив символов (char)

        for (char c: strToArray)
        {

            if (((c >= 'a') && (c <= 'z')) || ((c >= 'A') && (c <= 'Z')))
            {
                rome=true;
            }
            else if (((c >= '0') && (c <= '9')))
            {
                arab=true;
            }
        }

        if (arab & rome)
        {
            throw new ScannerException("Используются одновременно разные системы исчисления");
        }
        else if (arab)
        {
            arabBool = true;
        }
        else if (rome)
        {
            arabBool=false;
        }

        char addition='+';
        char subtraction='-';
        char division='/';
        char multiplication='*';

        int indexOfAddition = 0; // переменная для хранения индекса, под которым находится оператор
        int indexOfSubtraction = 0;
        int indexOfDivision = 0;
        int indexOfMultiplication = 0;

        int numberOfCharacters = 0; //переменная для проверки количества знаков, н-р если введено 5+5+5, то исключение

        for (int i=0; i<strToArray.length; i++)
        {
            if (strToArray[i]==addition)
            {
                indexOfAddition=i;
                numberOfCharacters++;
            }
            else if (strToArray[i]==division)
            {
                indexOfDivision=i;
                numberOfCharacters++;
            }
            else if (strToArray[i]==multiplication)
            {
                indexOfMultiplication=i;
                numberOfCharacters++;
            }
            else if (strToArray[i]==subtraction)
            {
                indexOfSubtraction=i;
                numberOfCharacters++;
            }
        }

        if (numberOfCharacters!=1)
        {
            throw new ScannerException("формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)");
        }

        if (indexOfAddition==1)
        {
            result= resultOfAddition(strToArray, indexOfAddition);
        }
        else if (indexOfSubtraction==1)
        {
            result= resultOfSubstraction(strToArray, indexOfSubtraction);
        }
        else if (indexOfDivision==1)
        {
            result= resultOfDivision(strToArray, indexOfDivision);
        }
        else if (indexOfMultiplication==1)
        {
            result= resultOfMultiplication(strToArray, indexOfMultiplication);
        }
        else
        {
            throw new ScannerException("Символы \"+ - / *\" не найдены.");
        }
        return result;
    }

    public int charToFirstOperand(char[] strToArray, int index) throws ScannerException // преобразование первого опренада в число
    {
        String temp= new String(strToArray, 0, index);
        int result = 0;

        if (arabBool)// если исчисление в арабских цифрах
        {
            result = Integer.parseInt(temp);
            if (!(0 < result && result <= 10))
            {
                throw new ScannerException("Калькулятор должен принимать на вход числа от 1 до 10 включительно, не более");
            }
        }

        else //если исчисление в римских цифрах
        {
            temp=temp.toUpperCase(Locale.ROOT);
            for (int i = 0; i< romeUnits.length; i++)
            {
                if (Objects.equals(romeUnits[i], temp))
                {
                    result=Integer.parseInt(arabUnits[i]);
                }
            }
            if (result==0)
            {
                throw new ScannerException("Калькулятор должен принимать на вход числа от I до X включительно, не более");
            }
        }
        return result;
    }

    public int charToSecondOperand(char[] strToArray, int index) throws ScannerException //преобразование второго операнда в число
    {
        int result = 0;
        String temp= new String(strToArray, index+1, strToArray.length-index-1);


        if (arabBool)// если исчисление в арабских цифрах
        {
            result = Integer.parseInt(temp);

            if (!(0 < result && result <= 10))
            {
                throw new ScannerException("Калькулятор должен принимать на вход числа от 1 до 10 включительно, не более");
            }
        }

        else //если исчисление в римских цифрах
        {
            temp=temp.toUpperCase(Locale.ROOT);
            for (int i = 0; i< romeUnits.length; i++)
            {
                if (Objects.equals(romeUnits[i], temp))
                {
                    result=Integer.parseInt(arabUnits[i]);
                }
            }
            if (result==0)
            {
                throw new ScannerException("Калькулятор должен принимать на вход числа от I до X включительно, не более");
            }
        }
        return result;
    }

    public String resultOfAddition(char[] strToArray, int index) throws ScannerException
    {
        int firstOperand= charToFirstOperand(strToArray, index);
        int secondOperand= charToSecondOperand(strToArray, index);
        int result;
        String resultString = null;
        if (arabBool) //арабское исчисление
        {
            result=firstOperand+secondOperand;
            resultString=String.valueOf(result);
        }
        else //римское исчисление
        {
            result=firstOperand+secondOperand;
            if (result<=10)
            {
                for (int i = 0; i< arabUnits.length; i++)
                {
                    if (Objects.equals(arabUnits[i], String.valueOf(result)))
                    {
                        resultString = romeUnits[i];
                    }
                }
            }
            else
            {
                int temp1 = result/10;
                temp1=temp1*10;
                int temp2=result-temp1;
                for (int i = 0; i< arabDozens.length; i++)
                {
                    if (Objects.equals(arabDozens[i], String.valueOf(temp1)))
                    {
                        resultString = romeDozens[i];
                    }
                }
                if (temp2!=0)
                {
                    for (int i = 0; i< arabUnits.length; i++)
                    {
                        if (Objects.equals(arabUnits[i], String.valueOf(temp2)))
                        {
                            resultString = resultString + romeUnits[i];
                        }
                    }
                }

            }
        }
        return resultString;
    }

    public String resultOfSubstraction(char[] strToArray, int index) throws ScannerException
    {
        int firstOperand= charToFirstOperand(strToArray, index);
        int secondOperand= charToSecondOperand(strToArray, index);
        int result;
        String resultString = null;
        if (arabBool)  //арабское исчисление
        {
            result=firstOperand-secondOperand;
            resultString=String.valueOf(result);
        }
        else  //римское исчисление
        {
            result=firstOperand-secondOperand;
            if (result<=0)
            {
                throw new ScannerException("в римской системе нет отрицательных чисел");
            }
            for (int i = 0; i< arabUnits.length; i++)
            {
                if (Objects.equals(arabUnits[i], String.valueOf(result)))
                {
                    resultString = romeUnits[i];
                }
            }
        }
        return resultString;
    }

    public String resultOfDivision(char[] strToArray, int index) throws ScannerException
    {
        int firstOperand= charToFirstOperand(strToArray, index);
        int secondOperand= charToSecondOperand(strToArray, index);
        int result;
        double resultDouble;
        String resultString = null;
        if (arabBool)  //арабское исчисление
        {
            if (secondOperand==0)
            {
                throw new ScannerException("в римской системе нет 0");
            }
            resultDouble=firstOperand/secondOperand;
            result=(int) resultDouble;

            resultString=String.valueOf(result);
        }
        else //римское исчисление
        {
            resultDouble=firstOperand/secondOperand;
            result=(int) resultDouble;
            if (result<1)
            {
                throw new ScannerException("в римской системе нет 0");
            }

            for (int i = 0; i< arabUnits.length; i++)
            {
                if (Objects.equals(arabUnits[i], String.valueOf(result)))
                {
                    resultString = romeUnits[i];
                }
            }
        }
        return resultString;
    }

    public String resultOfMultiplication(char[] strToArray, int index) throws ScannerException
    {
        int firstOperand= charToFirstOperand(strToArray, index);
        int secondOperand= charToSecondOperand(strToArray, index);
        int result;
        String resultString = null;
        if (arabBool) //арабское исчисление
        {
            result=firstOperand*secondOperand;
            resultString=String.valueOf(result);
        }
        else  //римское исчисление
        {
            result=firstOperand*secondOperand;
            if (result<=10)
            {
                for (int i = 0; i< arabUnits.length; i++)
                {
                    if (Objects.equals(arabUnits[i], String.valueOf(result)))
                    {
                        resultString = romeUnits[i];
                    }
                }
            }
            else
            {
                int temp1 = result /10;
                temp1=temp1*10;
                int temp2=result-temp1;
                for (int i = 0; i< arabDozens.length; i++)
                {
                    if (Objects.equals(arabDozens[i], String.valueOf(temp1)))
                    {
                        resultString = romeDozens[i];
                    }
                }
                if (temp2!=0)
                {
                    for (int i = 0; i< arabUnits.length; i++)
                    {
                        if (Objects.equals(arabUnits[i], String.valueOf(temp2)))
                        {
                            resultString = resultString + romeUnits[i];
                        }
                    }
                }
            }
        }
        return resultString;
    }
}