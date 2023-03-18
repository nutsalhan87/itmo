import consoleapplication.MainInterface;
import consoleapplication.workwithroute.ParsedObjectToListRoute;
import route.Route;
import workwithexternaldata.JSONToParsedObject;
import workwithexternaldata.parsedobjects.ParsedObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * Main class which contains a single method which reads data from file and starts console interface
 */

public class Main {
    public static void main(String[] args) throws IOException {
        List<Route> data = new LinkedList<>();
        try {
            if (!(new File(System.getenv("Lab5Data")).exists())) {
                throw new RuntimeException("Файла данных не существует");
            }
            if ( !(new File(System.getenv("Lab5Data")).canWrite() && new File(System.getenv("Lab5Data")).canRead())) {
                throw new RuntimeException("Ввод или вывод в данный файл не доступен");
            }
            ParsedObject parsedObject = new JSONToParsedObject().parseFile(System.getenv("Lab5Data"));
            data = ParsedObjectToListRoute.convertToListRoute(parsedObject);
        }
        catch (NullPointerException exn) {
            System.out.println("Заданная переменная окружения отсутствует");
        }
        catch (FileNotFoundException fnf) {
            System.out.println("Файл недоступен");
        }
        catch (RuntimeException exc) {
            System.out.println(exc.getMessage());
        }

        MainInterface mainInterface = new MainInterface();
        mainInterface.startMainInterface(data, () -> new Scanner(System.in).nextLine());
    }
}
