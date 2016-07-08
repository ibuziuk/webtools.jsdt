// Generated source.
// Generator: org.eclipse.wst.jsdt.chromium.internal.wip.tools.protocolgenerator.Generator
// Origin: https://chromium.googlesource.com/chromium/src/+/51.0.2704.79/third_party/WebKit/Source/devtools/protocol.json?format=TEXT@<unknown>

package org.eclipse.wst.jsdt.chromium.internal.wip.protocol.input.debugger;

/**
 Restarts particular call frame from the beginning.
 */
@org.eclipse.wst.jsdt.chromium.internal.protocolparser.JsonType
public interface RestartFrameData {
  /**
   New stack trace.
   */
  java.util.List<org.eclipse.wst.jsdt.chromium.internal.wip.protocol.input.debugger.CallFrameValue> callFrames();

  /**
   Async stack trace, if any.
   */
  @org.eclipse.wst.jsdt.chromium.internal.protocolparser.JsonOptionalField
  org.eclipse.wst.jsdt.chromium.internal.wip.protocol.input.runtime.StackTraceValue asyncStackTrace();

}
