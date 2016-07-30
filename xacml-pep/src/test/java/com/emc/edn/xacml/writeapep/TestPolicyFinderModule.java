package com.emc.edn.xacml.writeapep;

import java.io.InputStream;

import com.sun.xacml.ParsingException;
import com.sun.xacml.finder.PolicyFinder;
import com.sun.xacml.support.finder.BasicPolicyFinderModule;
import com.sun.xacml.support.finder.PolicyReader;


public class TestPolicyFinderModule extends BasicPolicyFinderModule {

  private final InputStream[] policyStreams;

  public TestPolicyFinderModule(InputStream[] policyStreams) {
    this.policyStreams = policyStreams;
  }

  @Override
  public void init(PolicyFinder finder) {
    PolicyReader reader = new PolicyReader(finder);
    for (InputStream policyStream : policyStreams) {
      try {
        addPolicy(reader.readPolicy(policyStream));
      } catch (ParsingException e) {
        throw new IllegalArgumentException(e);
      }
    }
  }

}
