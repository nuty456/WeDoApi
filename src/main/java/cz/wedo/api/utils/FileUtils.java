package cz.wedo.api.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

/**
 * A utility class for file and directory operations.
 */
@Slf4j
public class FileUtils {

  /**
   * Saves the contents of the response entity to a file.
   *
   * @param targetFile The path to the target file.
   * @param response The HttpResponse object containing the response entity.
   * @throws IOException if an I/O error occurs while writing to the file.
   */
  public static void saveResponseEntityToFile(String targetFile, HttpResponse response) throws IOException {
    byte[] bytes = EntityUtils.toByteArray(response.getEntity());

    File outputFile = new File(targetFile);
    try (FileOutputStream outputStream = new FileOutputStream(outputFile)) {
      outputStream.write(bytes);
    }
  }

  /**
   * Saves the given content to a file.
   *
   * @param content The content to be saved.
   * @param targetFile The path to the target file.
   * @return True if the content is successfully saved to the file, false otherwise.
   */
  public boolean saveToFile(final String content, final String targetFile) {
    try {
      String folder = dirname(targetFile);
      if (!dirExists(folder)) {
        mkdirP(folder);
      }
      Path destinationPath = Paths.get(targetFile);
      if (content != null) {
        Files.write(destinationPath, content.getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE);
      }
      chmod(777, targetFile);
      return true;
    } catch (Exception e) {
      log.error(String.format("Exception during writing to file[%s] content [%s] %s", targetFile, content, e.getMessage()), e);
    }
    return false;
  }

  /**
   * Returns the parent directory path of the given file or directory path.
   *
   * @param path the file or directory path whose parent directory path needs to be retrieved
   * @return the parent directory path of the given file or directory path
   */
  public String dirname(final String path) {
    return Paths.get(path).getParent().toString();
  }

  /**
   * Checks whether the specified directory exists.
   *
   * @param dir the path of the directory to be checked
   * @return true if the directory exists, false otherwise
   */
  public boolean dirExists(final String dir) {
    final Path path = Paths.get(dir);
    return Files.isDirectory(path);
  }

  /**
   * Creates a directory and any necessary parent directories. If the directory or any parent directory
   * doesn't exist, it will be created. The method also sets the permissions of the newly created
   * directory to 777.
   *
   * @param dir the path of the directory to be created
   * @return true if the directory was created successfully, false otherwise
   */
  public boolean mkdirP(final String dir) {
    try {
      Path path = Paths.get(dir);
      Files.createDirectories(path);
      try {
        chmod(path.toFile(), 777, true);
      } catch (Exception e) {
        log.error(String.format("Cannot chown 0777 dir [%s]: %s", dir, e.getMessage()), e);
      }
      return true;
    } catch (IOException e) {
      log.error(String.format("Cannot create dir [%s]: %s", dir, e.getMessage()), e);
    }
    return false;
  }

  /**
   * Changes the permissions (mode) of a file.
   *
   * @param mod  the desired permissions for the file
   * @param file the path of the file to modify
   * @return true if the permissions were successfully changed, false otherwise
   */
  public static boolean chmod(final int mod, final String file) {
    try {
      return chmod(new File(file), mod, true);
    } catch (Exception e) {
      log.error(String.format("Exception chmod(%d, %s) %s", mod, file, e.getMessage()), e);
      return false;
    }
  }

  /**
   * Changes the permissions of a file or directory.
   *
   * @param file The file or directory to change permissions for.
   * @param mode The new permissions mode to set.
   * @param recurse Flag indicating whether to change permissions recursively for directories.
   * @return true if the permissions were successfully changed, false otherwise.
   */
  public static boolean chmod(File file, int mode, boolean recurse) {
    List<String> args = new ArrayList<>();
    args.add("chmod");
    if (recurse) {
      args.add("-R");
    }
    args.add(Integer.toString(mode));
    args.add(file.getAbsolutePath());
    try {
      Runtime.getRuntime().exec(args.toArray(new String[args.size()]));
      return true;
    } catch (Exception e) {
      return false;
    }
  }

}
