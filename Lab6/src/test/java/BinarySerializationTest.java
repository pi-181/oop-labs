import com.demkom58.lab6.model.Wood;
import com.demkom58.lab6.store.WoodDirectory;

import java.io.*;

public class BinarySerializationTest {
    public static void main(String[] args) {
        serializeWoodDirectory();
        deserializeWoodDirectory();
    }

    public static void serializeWoodDirectory() {
        WoodDirectory woodDirectory = new WoodDirectory();
        woodDirectory.add(new Wood(4, "Oak", 1f));

        File file = new File("object.binary");
        try(ObjectOutputStream out = new ObjectOutputStream(
                new FileOutputStream(file))) {
            out.writeObject(woodDirectory);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deserializeWoodDirectory() {
        WoodDirectory woodDirectory = null;

        File file = new File("object.binary");
        try (ObjectInputStream input = new ObjectInputStream(
                new FileInputStream(file))) {
            woodDirectory = (WoodDirectory) input.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }


        if (woodDirectory != null)
            for (Object w : woodDirectory.getArr())
                System.out.println(w.toString());
    }
}
