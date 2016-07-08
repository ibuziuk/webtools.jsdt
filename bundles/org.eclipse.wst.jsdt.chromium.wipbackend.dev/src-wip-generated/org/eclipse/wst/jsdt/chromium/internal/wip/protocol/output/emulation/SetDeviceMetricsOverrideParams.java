// Generated source.
// Generator: org.eclipse.wst.jsdt.chromium.internal.wip.tools.protocolgenerator.Generator
// Origin: https://chromium.googlesource.com/chromium/src/+/51.0.2704.79/third_party/WebKit/Source/devtools/protocol.json?format=TEXT@<unknown>

package org.eclipse.wst.jsdt.chromium.internal.wip.protocol.output.emulation;

/**
Overrides the values of device screen dimensions (window.screen.width, window.screen.height, window.innerWidth, window.innerHeight, and "device-width"/"device-height"-related CSS media query results).
 */
public class SetDeviceMetricsOverrideParams extends org.eclipse.wst.jsdt.chromium.internal.wip.protocol.output.WipParams {
  /**
   @param width Overriding width value in pixels (minimum 0, maximum 10000000). 0 disables the override.
   @param height Overriding height value in pixels (minimum 0, maximum 10000000). 0 disables the override.
   @param deviceScaleFactor Overriding device scale factor value. 0 disables the override.
   @param mobile Whether to emulate mobile device. This includes viewport meta tag, overlay scrollbars, text autosizing and more.
   @param fitWindow Whether a view that exceeds the available browser window area should be scaled down to fit.
   @param scaleOpt Scale to apply to resulting view image. Ignored in |fitWindow| mode.
   @param offsetXOpt X offset to shift resulting view image by. Ignored in |fitWindow| mode.
   @param offsetYOpt Y offset to shift resulting view image by. Ignored in |fitWindow| mode.
   @param screenWidthOpt Overriding screen width value in pixels (minimum 0, maximum 10000000). Only used for |mobile==true|.
   @param screenHeightOpt Overriding screen height value in pixels (minimum 0, maximum 10000000). Only used for |mobile==true|.
   @param positionXOpt Overriding view X position on screen in pixels (minimum 0, maximum 10000000). Only used for |mobile==true|.
   @param positionYOpt Overriding view Y position on screen in pixels (minimum 0, maximum 10000000). Only used for |mobile==true|.
   @param screenOrientationOpt Screen orientation override.
   */
  public SetDeviceMetricsOverrideParams(long width, long height, Number deviceScaleFactor, boolean mobile, boolean fitWindow, Number scaleOpt, Number offsetXOpt, Number offsetYOpt, Long screenWidthOpt, Long screenHeightOpt, Long positionXOpt, Long positionYOpt, org.eclipse.wst.jsdt.chromium.internal.wip.protocol.output.emulation.ScreenOrientationParam screenOrientationOpt) {
    this.put("width", width);
    this.put("height", height);
    this.put("deviceScaleFactor", deviceScaleFactor);
    this.put("mobile", mobile);
    this.put("fitWindow", fitWindow);
    if (scaleOpt != null) {
      this.put("scale", scaleOpt);
    }
    if (offsetXOpt != null) {
      this.put("offsetX", offsetXOpt);
    }
    if (offsetYOpt != null) {
      this.put("offsetY", offsetYOpt);
    }
    if (screenWidthOpt != null) {
      this.put("screenWidth", screenWidthOpt);
    }
    if (screenHeightOpt != null) {
      this.put("screenHeight", screenHeightOpt);
    }
    if (positionXOpt != null) {
      this.put("positionX", positionXOpt);
    }
    if (positionYOpt != null) {
      this.put("positionY", positionYOpt);
    }
    if (screenOrientationOpt != null) {
      this.put("screenOrientation", screenOrientationOpt);
    }
  }

  public static final String METHOD_NAME = org.eclipse.wst.jsdt.chromium.internal.wip.protocol.BasicConstants.Domain.EMULATION + ".setDeviceMetricsOverride";

  @Override protected String getRequestName() {
    return METHOD_NAME;
  }

}
