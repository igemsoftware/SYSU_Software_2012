package igem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Guo Jiexin
 */
public class HistoryFile {

    public static String getPathFromHistoryFile(String path) throws IOException {
        File file = new File(path);
        if (!file.exists() || !file.isFile()) {
            return System.getProperty("user.dir");
        } else {
            BufferedReader br = null;
            String temp = null;
            try {
                br = new BufferedReader(new FileReader(file));
                temp = br.readLine();
                //System.out.println(temp);
                if (temp == null||!(new File(temp).exists())) {
                    br.close();
                    return System.getProperty("user.dir");
                } else {
                    br.close();
                    return temp;
                }
            } catch (IOException ex) {
                Logger.getLogger(HistoryFile.class.getName()).log(Level.SEVERE, null, ex);
            }
            br.close();
            return System.getProperty("user.dir");

        }
    }

    public static void setPathToHistoryFile(String filePath, String userPath) throws IOException {
        File file = new File(filePath);
        FileWriter fw = new FileWriter(file);
        fw.write(userPath);
        fw.close();
    }
}