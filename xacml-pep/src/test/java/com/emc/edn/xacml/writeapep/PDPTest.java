package com.emc.edn.xacml.writeapep;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;
import java.util.HashSet;
import java.util.Set;

import oasis.names.tc.xacml._2_0.context.schema.os.ActionType;
import oasis.names.tc.xacml._2_0.context.schema.os.AttributeType;
import oasis.names.tc.xacml._2_0.context.schema.os.AttributeValueType;
import oasis.names.tc.xacml._2_0.context.schema.os.EnvironmentType;
import oasis.names.tc.xacml._2_0.context.schema.os.RequestType;
import oasis.names.tc.xacml._2_0.context.schema.os.ResourceType;
import oasis.names.tc.xacml._2_0.context.schema.os.SubjectType;

import org.junit.Assert;
import org.junit.Test;

import com.sun.xacml.PDP;
import com.sun.xacml.PDPConfig;
import com.sun.xacml.ParsingException;
import com.sun.xacml.ctx.ResponseCtx;
import com.sun.xacml.ctx.Result;
import com.sun.xacml.finder.AttributeFinder;
import com.sun.xacml.finder.PolicyFinder;
import com.sun.xacml.finder.PolicyFinderModule;
import com.sun.xacml.finder.ResourceFinder;


/**
 * Test how the Sun XACML PDP works.
 */
public class PDPTest {

  private static final String SIMPLE_POLICY
      = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
      + "<Policy\n"
      + "    xmlns=\"urn:oasis:names:tc:xacml:2.0:policy:schema:os\"\n"
      + "    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n"
      + "    xsi:schemaLocation=\"urn:oasis:names:tc:xacml:2.0:policy:schema:os\n"
      + "        access_control-xacml-2.0-policy-schema-os.xsd\"\n"
      + "    PolicyId=\"urn:oasis:names:tc:xacml:2.0:conformance-test:IIA1:policy\"\n"
      + "    RuleCombiningAlgId=\"urn:oasis:names:tc:xacml:1.0:rule-combining-algorithm:deny-overrides\">\n"
      + "  <Target/>\n"
      + "  <Rule\n"
      + "      RuleId=\"urn:oasis:names:tc:xacml:2.0:conformance-test:IIA1:rule\"\n"
      + "      Effect=\"Permit\">\n"
      + "    <Target>\n"
      + "      <Subjects>\n"
      + "        <Subject>\n"
      + "          <SubjectMatch\n"
      + "              MatchId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\">\n"
      + "            <AttributeValue\n"
      + "                DataType=\"http://www.w3.org/2001/XMLSchema#string\">{0}</AttributeValue>\n"
      + "            <SubjectAttributeDesignator\n"
      + "                AttributeId=\"urn:oasis:names:tc:xacml:1.0:subject:subject-id\"\n"
      + "                DataType=\"http://www.w3.org/2001/XMLSchema#string\"/>\n"
      + "          </SubjectMatch>\n"
      + "        </Subject>\n"
      + "      </Subjects>\n"
      + "      <Resources>\n"
      + "        <Resource>\n"
      + "          <ResourceMatch\n"
      + "              MatchId=\"urn:oasis:names:tc:xacml:1.0:function:anyURI-equal\">\n"
      + "            <AttributeValue\n"
      + "                DataType=\"http://www.w3.org/2001/XMLSchema#anyURI\">{1}</AttributeValue>\n"
      + "            <ResourceAttributeDesignator\n"
      + "                AttributeId=\"urn:oasis:names:tc:xacml:1.0:resource:resource-id\"\n"
      + "                DataType=\"http://www.w3.org/2001/XMLSchema#anyURI\"/>\n"
      + "          </ResourceMatch>\n"
      + "        </Resource>\n"
      + "      </Resources>\n"
      + "      <Actions>\n"
      + "        <Action>\n"
      + "          <ActionMatch\n"
      + "              MatchId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\">\n"
      + "            <AttributeValue\n"
      + "                DataType=\"http://www.w3.org/2001/XMLSchema#string\">{2}</AttributeValue>\n"
      + "            <ActionAttributeDesignator\n"
      + "                AttributeId=\"urn:oasis:names:tc:xacml:1.0:action:action-id\"\n"
      + "                DataType=\"http://www.w3.org/2001/XMLSchema#string\"/>\n"
      + "          </ActionMatch>\n"
      + "        </Action>\n"
      + "      </Actions>\n"
      + "    </Target>\n"
      + "  </Rule>\n"
      + "</Policy>\n";

  @Test
  public void evaluate() throws ParsingException {
    PDP pdp = newPdp();
    RequestType request = newRequest("ape", "bear", "cheetah");
    assertResponse("No policies, hence NotApplicable", Result.DECISION_NOT_APPLICABLE,
        pdp, request);
  }

  @Test
  public void policies() {
    PDP pdp = newPdp(newPolicy("dingo", "elephant", "fox", Result.DECISION_PERMIT));
    RequestType request = newRequest("dingo", "elephant", "fox");
    assertResponse("Allow", Result.DECISION_PERMIT, pdp, request);
  }

  private void assertResponse(String message, int expected, PDP pdp, RequestType request) {
    ResponseCtx response = pdp.evaluate(request);

    @SuppressWarnings("unchecked")
    Set<Result> results = response.getResults();
    Assert.assertEquals("# results", 1, results.size());

    Result result = results.iterator().next();
    Assert.assertEquals(message, expected, result.getDecision());
  }

  private InputStream newPolicy(String subjectId, String resourceId, String actionId, int decision) {
    return stringToStream(MessageFormat.format(
        SIMPLE_POLICY, subjectId, resourceId, actionId, decisionToString(decision)));
  }

  private String decisionToString(int decision) {
    switch (decision) {
      case Result.DECISION_PERMIT:
        return "Permit";
      case Result.DECISION_DENY:
        return "Deny";
      default:
        throw new IllegalArgumentException("Unknown decision: " + decision);
    }
  }

  private InputStream stringToStream(String value) {
    try {
      return new ByteArrayInputStream(value.getBytes("UTF-8"));
    } catch (UnsupportedEncodingException e) {
      throw new IllegalStateException(e);
    }
  }

  private PDP newPdp(InputStream... policies) {
    AttributeFinder attributeFinder = new AttributeFinder();
    PolicyFinder policyFinder = new PolicyFinder();
    HashSet<PolicyFinderModule> policyFinderModules = new HashSet<PolicyFinderModule>();
    policyFinderModules.add(new TestPolicyFinderModule(policies));
    policyFinder.setModules(policyFinderModules);
    ResourceFinder resourceFinder = new ResourceFinder();
    PDPConfig config = new PDPConfig(attributeFinder, policyFinder, resourceFinder);
    return new PDP(config);
  }

  private RequestType newRequest(String subjectId, String resourceId, String actionId) {
    RequestType result = new RequestType();

    result.getSubject().add(newSubject(subjectId));
    result.getResource().add(newResource(resourceId));
    result.setAction(newAction(actionId));
    result.setEnvironment(newEnvironment());

    return result;
  }

  private SubjectType newSubject(String subjectId) {
    SubjectType result = new SubjectType();
    AttributeType attribute = newAttribute("http://www.w3.org/2001/XMLSchema#string",
        "urn:oasis:names:tc:xacml:1.0:subject:subject-id", subjectId);
    result.getAttribute().add(attribute);
    return result;
  }

  private AttributeType newAttribute(String type, String id, String value) {
    AttributeType result = new AttributeType();
    result.setAttributeId(id);
    result.setDataType(type);
    AttributeValueType attributeValue = new AttributeValueType();
    attributeValue.getContent().add(value);
    result.getAttributeValue().add(attributeValue);
    return result;
  }

  private ResourceType newResource(String resourceId) {
    ResourceType result = new ResourceType();
    AttributeType attribute = newAttribute("http://www.w3.org/2001/XMLSchema#anyURI",
        "urn:oasis:names:tc:xacml:1.0:resource:resource-id", resourceId);
    result.getAttribute().add(attribute);
    return result;
  }

  private ActionType newAction(String actionId) {
    ActionType result = new ActionType();
    AttributeType attribute = newAttribute("http://www.w3.org/2001/XMLSchema#string",
        "urn:oasis:names:tc:xacml:1.0:action:action-id", actionId);
    result.getAttribute().add(attribute);
    return result;
  }

  private EnvironmentType newEnvironment() {
    return new EnvironmentType();
  }

}
