// Generated source.
// Generator: org.eclipse.wst.jsdt.chromium.internal.wip.tools.protocolgenerator.Generator
// Origin: https://chromium.googlesource.com/chromium/src/+/51.0.2704.79/third_party/WebKit/Source/devtools/protocol.json?format=TEXT@<unknown>

package org.eclipse.wst.jsdt.chromium.internal.wip.protocol.output.debugger;

/**
Location in the source code.
 */
public class ScriptPositionParam extends org.json.simple.JSONObject {
  public ScriptPositionParam(long line, long column) {
    this.put("line", line);
    this.put("column", column);
  }

}
