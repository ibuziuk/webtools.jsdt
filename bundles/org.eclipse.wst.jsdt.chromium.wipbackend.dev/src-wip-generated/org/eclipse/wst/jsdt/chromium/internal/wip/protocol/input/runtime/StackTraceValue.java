// Generated source.
// Generator: org.eclipse.wst.jsdt.chromium.internal.wip.tools.protocolgenerator.Generator
// Origin: https://chromium.googlesource.com/chromium/src/+/51.0.2704.79/third_party/WebKit/Source/devtools/protocol.json?format=TEXT@<unknown>

package org.eclipse.wst.jsdt.chromium.internal.wip.protocol.input.runtime;

/**
 Call frames for assertions or error messages.
 */
@org.eclipse.wst.jsdt.chromium.internal.protocolparser.JsonType
public interface StackTraceValue {
  /**
   String label of this stack trace. For async traces this may be a name of the function that initiated the async call.
   */
  @org.eclipse.wst.jsdt.chromium.internal.protocolparser.JsonOptionalField
  String description();

  /**
   JavaScript function name.
   */
  java.util.List<org.eclipse.wst.jsdt.chromium.internal.wip.protocol.input.runtime.CallFrameValue> callFrames();

  /**
   Asynchronous JavaScript stack trace that preceded this stack, if available.
   */
  @org.eclipse.wst.jsdt.chromium.internal.protocolparser.JsonOptionalField
  org.eclipse.wst.jsdt.chromium.internal.wip.protocol.input.runtime.StackTraceValue parent();

}
