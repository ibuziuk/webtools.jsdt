// Generated source.
// Generator: org.eclipse.wst.jsdt.chromium.internal.wip.tools.protocolgenerator.Generator
// Origin: https://chromium.googlesource.com/chromium/src/+/51.0.2704.79/third_party/WebKit/Source/devtools/protocol.json?format=TEXT@<unknown>

package org.eclipse.wst.jsdt.chromium.internal.wip.protocol.output.emulation;

/**
Switches script execution in the page.
 */
public class SetScriptExecutionDisabledParams extends org.eclipse.wst.jsdt.chromium.internal.wip.protocol.output.WipParams {
  /**
   @param value Whether script execution should be disabled in the page.
   */
  public SetScriptExecutionDisabledParams(boolean value) {
    this.put("value", value);
  }

  public static final String METHOD_NAME = org.eclipse.wst.jsdt.chromium.internal.wip.protocol.BasicConstants.Domain.EMULATION + ".setScriptExecutionDisabled";

  @Override protected String getRequestName() {
    return METHOD_NAME;
  }

}
