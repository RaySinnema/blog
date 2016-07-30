package com.securesoftwaredev.sandbox.filesystem;

import java.security.BasicPermission;


public class ProtectedFilePermission extends BasicPermission {

  private static final long serialVersionUID = 9182441041075090751L;

  public ProtectedFilePermission() {
    super(ProtectedFilePermission.class.getName());
  }

}
