// Generated source.
// Generator: org.eclipse.wst.jsdt.chromium.internal.wip.tools.protocolgenerator.Generator
// Origin: https://chromium.googlesource.com/chromium/src/+/51.0.2704.79/third_party/WebKit/Source/devtools/protocol.json?format=TEXT@<unknown>

package org.eclipse.wst.jsdt.chromium.internal.wip.protocol.output.emulation;

/**
Screen orientation.
 */
public class ScreenOrientationParam extends org.json.simple.JSONObject {
  /**
   @param type Orientation type.
   @param angle Orientation angle.
   */
  public ScreenOrientationParam(Type type, long angle) {
    this.put("type", type);
    this.put("angle", angle);
  }

  /**
   Orientation type.
   */
  public enum Type implements org.json.simple.JSONAware{
    PORTRAITPRIMARY("portraitPrimary"),
    PORTRAITSECONDARY("portraitSecondary"),
    LANDSCAPEPRIMARY("landscapePrimary"),
    LANDSCAPESECONDARY("landscapeSecondary"),
    ;
    private final String protocolValue;

    Type(String protocolValue) {
      this.protocolValue = protocolValue;
    }

    @Override public String toJSONString() {
      return '"' + protocolValue + '"';
    }
  }
}
