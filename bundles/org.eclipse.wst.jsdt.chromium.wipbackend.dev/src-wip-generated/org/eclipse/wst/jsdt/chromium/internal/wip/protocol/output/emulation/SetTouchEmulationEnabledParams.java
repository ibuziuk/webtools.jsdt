// Generated source.
// Generator: org.eclipse.wst.jsdt.chromium.internal.wip.tools.protocolgenerator.Generator
// Origin: https://chromium.googlesource.com/chromium/src/+/51.0.2704.79/third_party/WebKit/Source/devtools/protocol.json?format=TEXT@<unknown>

package org.eclipse.wst.jsdt.chromium.internal.wip.protocol.output.emulation;

/**
Toggles mouse event-based touch event emulation.
 */
public class SetTouchEmulationEnabledParams extends org.eclipse.wst.jsdt.chromium.internal.wip.protocol.output.WipParams {
  /**
   @param enabled Whether the touch event emulation should be enabled.
   @param configurationOpt Touch/gesture events configuration. Default: current platform.
   */
  public SetTouchEmulationEnabledParams(boolean enabled, Configuration configurationOpt) {
    this.put("enabled", enabled);
    if (configurationOpt != null) {
      this.put("configuration", configurationOpt);
    }
  }

  public static final String METHOD_NAME = org.eclipse.wst.jsdt.chromium.internal.wip.protocol.BasicConstants.Domain.EMULATION + ".setTouchEmulationEnabled";

  @Override protected String getRequestName() {
    return METHOD_NAME;
  }

  /**
   Touch/gesture events configuration. Default: current platform.
   */
  public enum Configuration implements org.json.simple.JSONAware{
    MOBILE("mobile"),
    DESKTOP("desktop"),
    ;
    private final String protocolValue;

    Configuration(String protocolValue) {
      this.protocolValue = protocolValue;
    }

    @Override public String toJSONString() {
      return '"' + protocolValue + '"';
    }
  }
}
