// Generated source.
// Generator: org.eclipse.wst.jsdt.chromium.internal.wip.tools.protocolgenerator.Generator
// Origin: https://chromium.googlesource.com/chromium/src/+/51.0.2704.79/third_party/WebKit/Source/devtools/protocol.json?format=TEXT@<unknown>

package org.eclipse.wst.jsdt.chromium.internal.wip.protocol.input.debugger;

/**
 Edits JavaScript source live.
 */
@org.eclipse.wst.jsdt.chromium.internal.protocolparser.JsonType
public interface SetScriptSourceData {
  /**
   New stack trace in case editing has happened while VM was stopped.
   */
  @org.eclipse.wst.jsdt.chromium.internal.protocolparser.JsonOptionalField
  java.util.List<org.eclipse.wst.jsdt.chromium.internal.wip.protocol.input.debugger.CallFrameValue> callFrames();

  /**
   Whether current call stack  was modified after applying the changes.
   */
  @org.eclipse.wst.jsdt.chromium.internal.protocolparser.JsonOptionalField
  Boolean stackChanged();

  /**
   Async stack trace, if any.
   */
  @org.eclipse.wst.jsdt.chromium.internal.protocolparser.JsonOptionalField
  org.eclipse.wst.jsdt.chromium.internal.wip.protocol.input.runtime.StackTraceValue asyncStackTrace();

  /**
   Error data if any.
   */
  @org.eclipse.wst.jsdt.chromium.internal.protocolparser.JsonOptionalField
  org.eclipse.wst.jsdt.chromium.internal.wip.protocol.input.debugger.SetScriptSourceErrorValue compileError();

}
