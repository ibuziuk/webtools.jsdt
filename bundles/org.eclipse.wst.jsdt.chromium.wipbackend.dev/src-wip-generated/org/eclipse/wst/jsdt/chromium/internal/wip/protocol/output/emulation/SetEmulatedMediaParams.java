// Generated source.
// Generator: org.eclipse.wst.jsdt.chromium.internal.wip.tools.protocolgenerator.Generator
// Origin: https://chromium.googlesource.com/chromium/src/+/51.0.2704.79/third_party/WebKit/Source/devtools/protocol.json?format=TEXT@<unknown>

package org.eclipse.wst.jsdt.chromium.internal.wip.protocol.output.emulation;

/**
Emulates the given media for CSS media queries.
 */
public class SetEmulatedMediaParams extends org.eclipse.wst.jsdt.chromium.internal.wip.protocol.output.WipParams {
  /**
   @param media Media type to emulate. Empty string disables the override.
   */
  public SetEmulatedMediaParams(String media) {
    this.put("media", media);
  }

  public static final String METHOD_NAME = org.eclipse.wst.jsdt.chromium.internal.wip.protocol.BasicConstants.Domain.EMULATION + ".setEmulatedMedia";

  @Override protected String getRequestName() {
    return METHOD_NAME;
  }

}
