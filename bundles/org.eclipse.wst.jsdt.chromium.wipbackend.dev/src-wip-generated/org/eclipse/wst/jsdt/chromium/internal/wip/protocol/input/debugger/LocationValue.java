// Generated source.
// Generator: org.eclipse.wst.jsdt.chromium.internal.wip.tools.protocolgenerator.Generator
// Origin: https://chromium.googlesource.com/chromium/src/+/51.0.2704.79/third_party/WebKit/Source/devtools/protocol.json?format=TEXT@<unknown>

package org.eclipse.wst.jsdt.chromium.internal.wip.protocol.input.debugger;

/**
 Location in the source code.
 */
@org.eclipse.wst.jsdt.chromium.internal.protocolparser.JsonType
public interface LocationValue {
  /**
   Script identifier as reported in the <code>Debugger.scriptParsed</code>.
   */
  String/*See org.eclipse.wst.jsdt.chromium.internal.wip.protocol.common.runtime.ScriptIdTypedef*/ scriptId();

  /**
   Line number in the script (0-based).
   */
  long lineNumber();

  /**
   Column number in the script (0-based).
   */
  @org.eclipse.wst.jsdt.chromium.internal.protocolparser.JsonOptionalField
  Long columnNumber();

}
