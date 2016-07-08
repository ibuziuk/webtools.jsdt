// Generated source.
// Generator: org.eclipse.wst.jsdt.chromium.internal.wip.tools.protocolgenerator.Generator
// Origin: https://chromium.googlesource.com/chromium/src/+/51.0.2704.79/third_party/WebKit/Source/devtools/protocol.json?format=TEXT@<unknown>

package org.eclipse.wst.jsdt.chromium.internal.wip.protocol.input.debugger;

/**
 Returns call stack including variables changed since VM was paused. VM must be paused.
 */
@org.eclipse.wst.jsdt.chromium.internal.protocolparser.JsonType
public interface GetBacktraceData {
  /**
   Call stack the virtual machine stopped on.
   */
  java.util.List<org.eclipse.wst.jsdt.chromium.internal.wip.protocol.input.debugger.CallFrameValue> callFrames();

  /**
   Async stack trace, if any.
   */
  @org.eclipse.wst.jsdt.chromium.internal.protocolparser.JsonOptionalField
  org.eclipse.wst.jsdt.chromium.internal.wip.protocol.input.runtime.StackTraceValue asyncStackTrace();

}
