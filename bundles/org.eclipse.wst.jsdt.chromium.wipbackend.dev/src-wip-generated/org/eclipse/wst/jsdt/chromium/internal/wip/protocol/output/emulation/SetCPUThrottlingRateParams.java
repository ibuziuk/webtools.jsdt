// Generated source.
// Generator: org.eclipse.wst.jsdt.chromium.internal.wip.tools.protocolgenerator.Generator
// Origin: https://chromium.googlesource.com/chromium/src/+/51.0.2704.79/third_party/WebKit/Source/devtools/protocol.json?format=TEXT@<unknown>

package org.eclipse.wst.jsdt.chromium.internal.wip.protocol.output.emulation;

/**
Enables CPU throttling to emulate slow CPUs.
 */
public class SetCPUThrottlingRateParams extends org.eclipse.wst.jsdt.chromium.internal.wip.protocol.output.WipParams {
  /**
   @param rate Throttling rate as a slowdown factor (1 is no throttle, 2 is 2x slowdown, etc).
   */
  public SetCPUThrottlingRateParams(Number rate) {
    this.put("rate", rate);
  }

  public static final String METHOD_NAME = org.eclipse.wst.jsdt.chromium.internal.wip.protocol.BasicConstants.Domain.EMULATION + ".setCPUThrottlingRate";

  @Override protected String getRequestName() {
    return METHOD_NAME;
  }

}
