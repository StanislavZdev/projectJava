import java.io.*;
import java.util.*;

public class JavaTest270 {
    static int howNeedFrontPlus;
    static int howNeedBeckPlus;

    static int frontNoPlus = 0;
    static int beckNoPlus = 0;

    static private int howLenWithoutPlusFrontAndBeck(String m) {
        // Номер первого и последнего вхождения не '+'
        for (int a = 0; a < m.length(); a++) {
            // First
            if (m.charAt(a) != '+') {
                frontNoPlus = a;
                String rev = new StringBuilder(m).reverse().toString();
                // Last
                for (int i = 0; i < rev.length(); i++) {
                    if (rev.charAt(i) != '+') {
                        beckNoPlus = rev.length() - (i + 1);
                        break;
                    }
                }
                break;
            }
        }
        // Возвращаем длину строки без +
        return (beckNoPlus + 1) - frontNoPlus;
    }

    static private String massageWithPlus(String massage, int howNeedFrontPlus, int howNeedBeckPlus, int frontNoPlus, int beckNoPlus) {
        // создаем новую строку без знаков с двух сторон
        String result = new StringBuilder(massage).substring(frontNoPlus, beckNoPlus + 1);
        StringBuilder sB = new StringBuilder(result);

// Плюсы добавляются в отформатированную строку без '+'
        if (howNeedFrontPlus > 0) {
            for (int i = 0; i < howNeedFrontPlus; i++) {
                sB.insert(0, '+');
            }
        }
        if (howNeedBeckPlus > 0) {
            sB.append("+".repeat(howNeedBeckPlus));
        }
        return sB.toString();
    }

    static String logicCenteringLine(ArrayList<String> massage, int lengthLine) {
        StringBuilder result = new StringBuilder();
        for (String m : massage) {
            int lenMessage = howLenWithoutPlusFrontAndBeck(m);

            // Находим сколько нужно +, до и после сообщения
            if ((lengthLine - lenMessage) % 2 == 0) {
                howNeedFrontPlus = (lengthLine - lenMessage) / 2;
                howNeedBeckPlus = (lengthLine - lenMessage) / 2;

                // Если нет места для +
            } else if ((lengthLine - lenMessage) == 0) {
                howNeedFrontPlus = 0;
                howNeedBeckPlus = 0;
                //  Если только один +
            } else if ((lengthLine - lenMessage) == 1) {
                howNeedFrontPlus = 0;
                howNeedBeckPlus = 1;
            }
            // если длина не делится пополам, то уменьшаем длину на 1 и прибавляем +1 в howNeedBeckPlus(по условию задания)
            else {
                howNeedFrontPlus = ((lengthLine - 1) - lenMessage) / 2;
                howNeedBeckPlus = (((lengthLine - 1) - lenMessage) / 2) + 1;
            }
            result.append(massageWithPlus(m, howNeedFrontPlus, howNeedBeckPlus, frontNoPlus, beckNoPlus)).append("\n");

        }
        return result.toString();
    }

    public static void main(String[] args) {

        int lengthLine = 0;
        int quantitySymbol = 0;
        ArrayList<String> massage = new ArrayList<>();

        try (var scan = new Scanner(new FileReader("C:\\Users\\yanaz\\Desktop\\Стас\\dji mavic mini\\JavaTask\\1\\input.txt"));
             var fW = new FileWriter("C:\\Users\\yanaz\\Desktop\\Стас\\dji mavic mini\\JavaTask\\1\\output.txt")) {

            while (scan.hasNextInt()) {
                lengthLine = scan.nextInt();
                quantitySymbol = scan.nextInt();
                // пропускаем пустой конец строки с интами
                scan.nextLine();
            }
            while (scan.hasNextLine()) {
                massage.add(scan.nextLine());
            }
            // Проверка на валидность данных
            if (lengthLine < 1 || lengthLine > 100 ||
                    quantitySymbol < 1 || quantitySymbol > 1000
                    || quantitySymbol != massage.size())
                throw new IllegalArgumentException("Ошибка. Не валидные данные. " +
                        "lengthLine и quantitySymbol (1 ≤ lengthLine ≤ 100, 1 ≤ quantitySymbol ≤ 1000). massage.size = quantitySymbol");
            // Проверка на условие: если длина строки меньше диапазона строки, то в output запишем сообщение - Impossible
            boolean ans = false;
            for (String s : massage) {
                if (s.length() < quantitySymbol) {
                    ans = true;
                    break;
                }
            }
            // Если ans != true, то по условию задания запишем сообщение снизу, в другом случае - использование методов и расчет кол. элем.
            if (ans) fW.write("Impossible");
            else {
                logicCenteringLine(massage, lengthLine);
                // Запись
                fW.write(logicCenteringLine(massage, lengthLine));
            }
            System.out.println(lengthLine + " " + quantitySymbol + "\n" + massage);

        } catch (IOException exc) {
            System.out.println(exc.getMessage());
        }
    }
}
