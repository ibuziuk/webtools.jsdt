// Generated source.
// Generator: org.eclipse.wst.jsdt.chromium.internal.wip.tools.protocolgenerator.Generator
// Origin: http://svn.webkit.org/repository/webkit/trunk/Source/WebCore/inspector/Inspector.json@96703

package org.eclipse.wst.jsdt.chromium.internal.wip.protocol.output.network;

/**
Toggles ignoring cache for each request. If <code>true</code>, cache will not be used.
 */
public class SetCacheDisabledParams extends org.eclipse.wst.jsdt.chromium.internal.wip.protocol.output.WipParams {
  /**
   @param cacheDisabled Cache disabled state.
   */
  public SetCacheDisabledParams(boolean cacheDisabled) {
    this.put("cacheDisabled", cacheDisabled);
  }

  public static final String METHOD_NAME = org.eclipse.wst.jsdt.chromium.internal.wip.protocol.BasicConstants.Domain.NETWORK + ".setCacheDisabled";

  @Override protected String getRequestName() {
    return METHOD_NAME;
  }

}
