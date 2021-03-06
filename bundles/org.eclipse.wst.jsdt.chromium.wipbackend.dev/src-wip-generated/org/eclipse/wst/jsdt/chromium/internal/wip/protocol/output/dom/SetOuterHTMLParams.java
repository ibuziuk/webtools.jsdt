// Generated source.
// Generator: org.eclipse.wst.jsdt.chromium.internal.wip.tools.protocolgenerator.Generator
// Origin: http://src.chromium.org/blink/trunk/Source/devtools/protocol.json@<unknown>

package org.eclipse.wst.jsdt.chromium.internal.wip.protocol.output.dom;

/**
Sets node HTML markup, returns new node id.
 */
public class SetOuterHTMLParams extends org.eclipse.wst.jsdt.chromium.internal.wip.protocol.output.WipParams {
  /**
   @param nodeId Id of the node to set markup for.
   @param outerHTML Outer HTML markup to set.
   */
  public SetOuterHTMLParams(long/*See org.eclipse.wst.jsdt.chromium.internal.wip.protocol.common.dom.NodeIdTypedef*/ nodeId, String outerHTML) {
    this.put("nodeId", nodeId);
    this.put("outerHTML", outerHTML);
  }

  public static final String METHOD_NAME = org.eclipse.wst.jsdt.chromium.internal.wip.protocol.BasicConstants.Domain.DOM + ".setOuterHTML";

  @Override protected String getRequestName() {
    return METHOD_NAME;
  }

}
