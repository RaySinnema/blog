package com.securesoftwaredev.sandbox.mobile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.securesoftwaredev.sandbox.filesystem.ProtectedFileSystem;
import com.securesoftwaredev.sandbox.main.Plugin;


/**
 * Example mobile code that tries to access the contents of a protected file.
 */
public class MobileCode implements Plugin {

  private static final String FILE_PATH = "file.txt";
  private static final int BUFFER_SIZE = 1024;

  @Override
  public void run() {
    System.out.println("Plugin: " + getClass().getName());
    tryNativeApi();
    tryProtectedApi();
  }

  private void tryNativeApi() {
    System.out.print("  Native    API: ");
    try (InputStream unprotectedStream = new FileInputStream(FILE_PATH)) {
      showContents(unprotectedStream);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  private void tryProtectedApi() {
    System.out.print("  Protected API: ");
    try (InputStream protectedStream = new ProtectedFileSystem().file(FILE_PATH)) {
      showContents(protectedStream);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  private void showContents(InputStream stream) throws IOException {
    StringBuilder contents = new StringBuilder();
    byte[] buffer = new byte[BUFFER_SIZE];
    int len = stream.read(buffer);
    while (len > 0) {
      contents.append(new String(buffer, 0, len));
      len = stream.read(buffer);
    }
    System.out.println(contents);
  }

}
