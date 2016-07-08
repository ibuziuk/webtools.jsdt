// Generated source.
// Generator: org.eclipse.wst.jsdt.chromium.internal.wip.tools.protocolgenerator.Generator
// Origin: https://chromium.googlesource.com/chromium/src/+/51.0.2704.79/third_party/WebKit/Source/devtools/protocol.json?format=TEXT@<unknown>

package org.eclipse.wst.jsdt.chromium.internal.wip.protocol.input.debugger;

/**
 Error data for setScriptSource command. Contains uncompilable script source error.
 */
@org.eclipse.wst.jsdt.chromium.internal.protocolparser.JsonType
public interface SetScriptSourceErrorValue {
  /**
   Compiler error message
   */
  String message();

  /**
   Compile error line number (1-based)
   */
  long lineNumber();

  /**
   Compile error column number (1-based)
   */
  long columnNumber();

}
