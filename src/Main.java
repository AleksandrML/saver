import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    public static void main(String[] args) {
        String mainPath = "/home/aleksandr/Games/savegames/";
        GameProgress gameProgress = new GameProgress(94, 10, 2, 254.32);
        GameProgress gameProgress2 = new GameProgress(78, 5, 2, 270);
        GameProgress gameProgress3 = new GameProgress(38, 1, 2, 15);
        int saveNumber = 1;
        List<String> listOfPaths = new ArrayList<>();
        for (GameProgress gamePro : new GameProgress[] {gameProgress, gameProgress2, gameProgress3}) {
            String pathToSave = mainPath + "save" + saveNumber + ".dat";
            saveGame(pathToSave, gamePro);
            saveNumber += 1;
            listOfPaths.add(pathToSave);
        }
        zipFiles("/home/aleksandr/Games/archive.zip", listOfPaths);
    }

    public static void saveGame(String path, GameProgress gameProgress) {
        try (FileOutputStream fos = new FileOutputStream(path);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gameProgress);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void zipFiles(String archivePath, List<String> filePaths) {
        try {
            FileOutputStream fos = new FileOutputStream(archivePath);
            ZipOutputStream zout = new ZipOutputStream(fos);
            for (String filePath : filePaths) {
                File srcFile = new File(filePath);
                FileInputStream fis = new FileInputStream(srcFile);
                ZipEntry zipEntry = new ZipEntry(srcFile.getName());
                zout.putNextEntry(zipEntry);
                byte[] buffer = new byte[fis.available()];
                int length;
                while((length = fis.read(buffer)) >= 0) {
                    zout.write(buffer, 0, length);
                }
                fis.close();
                srcFile.delete();  // delete sources here
            }
            zout.close();
            fos.close();
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

}
