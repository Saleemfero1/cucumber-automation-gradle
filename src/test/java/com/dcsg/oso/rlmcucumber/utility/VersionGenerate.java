package com.dcsg.oso.rlmcucumber.utility;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.apache.commons.io.FileUtils;

public class VersionGenerate {

  static int currentVersion = 0, totalVersions = 3;
  // static String initialFolderPath="target\\Reports\\";

  public static String folderCreation(String INITIAL_FOLDER_PATH, String FILE_NAME)
      throws IOException {

    String finalFolderPath = INITIAL_FOLDER_PATH + FILE_NAME;
    File folder = new File(INITIAL_FOLDER_PATH);
    File[] folders = null;
    if (!folder.exists()) {
      folder.mkdir();
      folders = folder.listFiles();
      return finalFolderPath;
    } else {
      folders = folder.listFiles();
    }

    if (folders != null && folders.length == totalVersions) {
      finalFolderPath = INITIAL_FOLDER_PATH + FILE_NAME + 1;
      File deleteFolder = new File(finalFolderPath);
      if (deleteFolder.exists()) {
        FileUtils.deleteDirectory(deleteFolder);
      }

      folders = folder.listFiles();
      int ver = 1;
      for (int pos = 0; pos < folders.length; pos++) {
        finalFolderPath = INITIAL_FOLDER_PATH + FILE_NAME + ver;
        File renameFile = new File(finalFolderPath);
        folders[pos].renameTo(renameFile);
        ver++;
      }
      finalFolderPath = INITIAL_FOLDER_PATH + FILE_NAME + ver;
      File createFolder = new File(finalFolderPath);
      createFolder.mkdir();
      return finalFolderPath;
    } else {
      currentVersion = folders.length + 1;
      finalFolderPath = INITIAL_FOLDER_PATH + FILE_NAME + currentVersion;
      File file = new File(finalFolderPath);
      if (!file.exists()) {
        file.mkdir();
      }
      return finalFolderPath;
    }
  }

  public static String generateBuildNumber() throws IOException {
    String file = "src/test/resources/buildNumber";
    Path pathName = Path.of(file);
    String content = Files.readString(pathName);
    System.out.println(content);

    int sum = Integer.parseInt(content) + 1;
    // System.out.println(sum);

    String incrementedValue = Integer.toString(sum);

    Files.writeString(pathName, incrementedValue);

    return incrementedValue;
  }
}
