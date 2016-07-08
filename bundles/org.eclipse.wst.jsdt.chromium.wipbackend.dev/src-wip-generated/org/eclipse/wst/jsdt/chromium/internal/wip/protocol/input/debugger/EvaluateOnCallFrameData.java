// Generated source.
// Generator: org.eclipse.wst.jsdt.chromium.internal.wip.tools.protocolgenerator.Generator
// Origin: https://chromium.googlesource.com/chromium/src/+/51.0.2704.79/third_party/WebKit/Source/devtools/protocol.json?format=TEXT@<unknown>

package org.eclipse.wst.jsdt.chromium.internal.wip.protocol.input.debugger;

/**
 Evaluates expression on a given call frame.
 */
@org.eclipse.wst.jsdt.chromium.internal.protocolparser.JsonType
public interface EvaluateOnCallFrameData {
  /**
   Object wrapper for the evaluation result.
   */
  org.eclipse.wst.jsdt.chromium.internal.wip.protocol.input.runtime.RemoteObjectValue result();

  /**
   True if the result was thrown during the evaluation.
   */
  @org.eclipse.wst.jsdt.chromium.internal.protocolparser.JsonOptionalField
  Boolean wasThrown();

  /**
   Exception details.
   */
  @org.eclipse.wst.jsdt.chromium.internal.protocolparser.JsonOptionalField
  org.eclipse.wst.jsdt.chromium.internal.wip.protocol.input.runtime.ExceptionDetailsValue exceptionDetails();

}
