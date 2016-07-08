// Generated source.
// Generator: org.eclipse.wst.jsdt.chromium.internal.wip.tools.protocolgenerator.Generator
// Origin: https://chromium.googlesource.com/chromium/src/+/51.0.2704.79/third_party/WebKit/Source/devtools/protocol.json?format=TEXT@<unknown>

package org.eclipse.wst.jsdt.chromium.internal.wip.protocol.output.emulation;

/**
Overrides the Geolocation Position or Error. Omitting any of the parameters emulates position unavailable.
 */
public class SetGeolocationOverrideParams extends org.eclipse.wst.jsdt.chromium.internal.wip.protocol.output.WipParams {
  /**
   @param latitudeOpt Mock latitude
   @param longitudeOpt Mock longitude
   @param accuracyOpt Mock accuracy
   */
  public SetGeolocationOverrideParams(Number latitudeOpt, Number longitudeOpt, Number accuracyOpt) {
    if (latitudeOpt != null) {
      this.put("latitude", latitudeOpt);
    }
    if (longitudeOpt != null) {
      this.put("longitude", longitudeOpt);
    }
    if (accuracyOpt != null) {
      this.put("accuracy", accuracyOpt);
    }
  }

  public static final String METHOD_NAME = org.eclipse.wst.jsdt.chromium.internal.wip.protocol.BasicConstants.Domain.EMULATION + ".setGeolocationOverride";

  @Override protected String getRequestName() {
    return METHOD_NAME;
  }

}
