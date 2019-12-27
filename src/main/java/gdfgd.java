import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class gdfgd {
    public static void main(String[] args) throws IOException {
        URL url = new URL("");
        InputStream stream = (InputStream) url.getContent();
        Scanner scanner = new Scanner(stream);
        while (scanner.hasNext()){

        }

    }
}
