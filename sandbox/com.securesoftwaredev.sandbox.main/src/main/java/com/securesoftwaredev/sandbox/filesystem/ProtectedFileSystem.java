package com.securesoftwaredev.sandbox.filesystem;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;


public class ProtectedFileSystem {

  public InputStream file(final String path) throws FileNotFoundException {
    SecurityManager securityManager = System.getSecurityManager();
    if (securityManager != null) {
      securityManager.checkPermission(new ProtectedFilePermission());
    }
    try {
      return AccessController.doPrivileged(new PrivilegedExceptionAction<InputStream>() {
        @Override
        public InputStream run() throws FileNotFoundException {
          return new FileInputStream(path);
        }
      });
    } catch (PrivilegedActionException e) {
      throw (FileNotFoundException)e.getException();
    }
  }

}
