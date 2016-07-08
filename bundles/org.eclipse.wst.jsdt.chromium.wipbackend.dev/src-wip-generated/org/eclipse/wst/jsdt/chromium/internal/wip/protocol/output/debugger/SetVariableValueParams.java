// Generated source.
// Generator: org.eclipse.wst.jsdt.chromium.internal.wip.tools.protocolgenerator.Generator
// Origin: https://chromium.googlesource.com/chromium/src/+/51.0.2704.79/third_party/WebKit/Source/devtools/protocol.json?format=TEXT@<unknown>

package org.eclipse.wst.jsdt.chromium.internal.wip.protocol.output.debugger;

/**
Changes value of variable in a callframe. Object-based scopes are not supported and must be mutated manually.
 */
public class SetVariableValueParams extends org.eclipse.wst.jsdt.chromium.internal.wip.protocol.output.WipParams {
  /**
   @param scopeNumber 0-based number of scope as was listed in scope chain. Only 'local', 'closure' and 'catch' scope types are allowed. Other scopes could be manipulated manually.
   @param variableName Variable name.
   @param newValue New variable value.
   @param callFrameId Id of callframe that holds variable.
 * @param string 
   */
  public SetVariableValueParams(long scopeNumber, String variableName, org.eclipse.wst.jsdt.chromium.internal.wip.protocol.output.runtime.CallArgumentParam newValue, String/*See org.eclipse.wst.jsdt.chromium.internal.wip.protocol.common.debugger.CallFrameIdTypedef*/ callFrameId, String functionId) {
    this.put("scopeNumber", scopeNumber);
    this.put("variableName", variableName);
    this.put("newValue", newValue);
    this.put("callFrameId", callFrameId);
    this.put("functionId", functionId);
  }

  public static final String METHOD_NAME = org.eclipse.wst.jsdt.chromium.internal.wip.protocol.BasicConstants.Domain.DEBUGGER + ".setVariableValue";

  @Override protected String getRequestName() {
    return METHOD_NAME;
  }

}
