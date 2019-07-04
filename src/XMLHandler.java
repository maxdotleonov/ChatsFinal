import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class XMLHandler {
    public static void writeXML(String filename, Object o) {
        try {
            XMLEncoder xmlEncoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(filename)));
            xmlEncoder.writeObject(o);
            xmlEncoder.close();
        } catch (FileNotFoundException e) {
            try {
                Files.createFile(Paths.get(filename));
                writeXML(filename, o);
            } catch (IOException ex) {
                throw new RuntimeException("Konnte die Datei '" + filename + "' nicht erzeugen");
            }
        }
    }

    public static Object readXML(String filename) {
        try {
            XMLDecoder xmlDecoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(filename)));
            Object result = xmlDecoder.readObject();
            xmlDecoder.close();
            return result;
        } catch (FileNotFoundException e) {
          throw new RuntimeException("Konnte die Datei '"+filename+"' nicht öffnen");
        }
    }
}
