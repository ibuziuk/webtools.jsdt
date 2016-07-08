// Generated source.
// Generator: org.eclipse.wst.jsdt.chromium.internal.wip.tools.protocolgenerator.Generator
// Origin: https://chromium.googlesource.com/chromium/src/+/51.0.2704.79/third_party/WebKit/Source/devtools/protocol.json?format=TEXT@<unknown>

package org.eclipse.wst.jsdt.chromium.internal.wip.protocol.input.runtime;

/**
 Returns properties of a given object. Object group of the result is inherited from the target object.
 */
@org.eclipse.wst.jsdt.chromium.internal.protocolparser.JsonType
public interface GetPropertiesData {
  /**
   Object properties.
   */
  java.util.List<org.eclipse.wst.jsdt.chromium.internal.wip.protocol.input.runtime.PropertyDescriptorValue> result();

  /**
   Internal object properties (only of the element itself).
   */
  @org.eclipse.wst.jsdt.chromium.internal.protocolparser.JsonOptionalField
  java.util.List<org.eclipse.wst.jsdt.chromium.internal.wip.protocol.input.runtime.InternalPropertyDescriptorValue> internalProperties();

  /**
   Exception details.
   */
  @org.eclipse.wst.jsdt.chromium.internal.protocolparser.JsonOptionalField
  org.eclipse.wst.jsdt.chromium.internal.wip.protocol.input.runtime.ExceptionDetailsValue exceptionDetails();

}
