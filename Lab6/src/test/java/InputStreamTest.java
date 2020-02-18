import java.io.*;

public class InputStreamTest {
    public static void main(String[] args) {
        try(final BufferedReader reader = new BufferedReader(
                new FileReader(new File("file.txt")))) {
            String s;
            while ((s = reader.readLine()) != null)
                System.out.println(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
