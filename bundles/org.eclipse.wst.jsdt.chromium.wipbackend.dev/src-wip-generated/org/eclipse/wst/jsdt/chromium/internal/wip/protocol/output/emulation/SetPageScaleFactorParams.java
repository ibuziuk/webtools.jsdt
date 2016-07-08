// Generated source.
// Generator: org.eclipse.wst.jsdt.chromium.internal.wip.tools.protocolgenerator.Generator
// Origin: https://chromium.googlesource.com/chromium/src/+/51.0.2704.79/third_party/WebKit/Source/devtools/protocol.json?format=TEXT@<unknown>

package org.eclipse.wst.jsdt.chromium.internal.wip.protocol.output.emulation;

/**
Sets a specified page scale factor.
 */
public class SetPageScaleFactorParams extends org.eclipse.wst.jsdt.chromium.internal.wip.protocol.output.WipParams {
  /**
   @param pageScaleFactor Page scale factor.
   */
  public SetPageScaleFactorParams(Number pageScaleFactor) {
    this.put("pageScaleFactor", pageScaleFactor);
  }

  public static final String METHOD_NAME = org.eclipse.wst.jsdt.chromium.internal.wip.protocol.BasicConstants.Domain.EMULATION + ".setPageScaleFactor";

  @Override protected String getRequestName() {
    return METHOD_NAME;
  }

}
