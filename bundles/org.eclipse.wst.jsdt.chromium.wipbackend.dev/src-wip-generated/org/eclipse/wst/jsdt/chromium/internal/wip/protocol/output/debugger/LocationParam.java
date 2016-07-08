// Generated source.
// Generator: org.eclipse.wst.jsdt.chromium.internal.wip.tools.protocolgenerator.Generator
// Origin: https://chromium.googlesource.com/chromium/src/+/51.0.2704.79/third_party/WebKit/Source/devtools/protocol.json?format=TEXT@<unknown>

package org.eclipse.wst.jsdt.chromium.internal.wip.protocol.output.debugger;

/**
Location in the source code.
 */
public class LocationParam extends org.json.simple.JSONObject {
  /**
   @param scriptId Script identifier as reported in the <code>Debugger.scriptParsed</code>.
   @param lineNumber Line number in the script (0-based).
   @param columnNumberOpt Column number in the script (0-based).
   */
  public LocationParam(String/*See org.eclipse.wst.jsdt.chromium.internal.wip.protocol.common.runtime.ScriptIdTypedef*/ scriptId, long lineNumber, Long columnNumberOpt) {
    this.put("scriptId", scriptId);
    this.put("lineNumber", lineNumber);
    if (columnNumberOpt != null) {
      this.put("columnNumber", columnNumberOpt);
    }
  }

}
