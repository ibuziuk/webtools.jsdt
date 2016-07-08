// Generated source.
// Generator: org.eclipse.wst.jsdt.chromium.internal.wip.tools.protocolgenerator.Generator
// Origin: https://chromium.googlesource.com/chromium/src/+/51.0.2704.79/third_party/WebKit/Source/devtools/protocol.json?format=TEXT@<unknown>

package org.eclipse.wst.jsdt.chromium.internal.wip.protocol.output.page;

/**
Controls whether browser will open a new inspector window for connected pages.
 */
public class SetAutoAttachToCreatedPagesParams extends org.eclipse.wst.jsdt.chromium.internal.wip.protocol.output.WipParams {
  /**
   @param autoAttach If true, browser will open a new inspector window for every page created from this one.
   */
  public SetAutoAttachToCreatedPagesParams(boolean autoAttach) {
    this.put("autoAttach", autoAttach);
  }

  public static final String METHOD_NAME = org.eclipse.wst.jsdt.chromium.internal.wip.protocol.BasicConstants.Domain.PAGE + ".setAutoAttachToCreatedPages";

  @Override protected String getRequestName() {
    return METHOD_NAME;
  }

}
