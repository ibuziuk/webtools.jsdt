// Generated source.
// Generator: org.eclipse.wst.jsdt.chromium.internal.wip.tools.protocolgenerator.Generator
// Origin: https://chromium.googlesource.com/chromium/src/+/51.0.2704.79/third_party/WebKit/Source/devtools/protocol.json?format=TEXT@<unknown>

package org.eclipse.wst.jsdt.chromium.internal.wip.protocol.output.debugger;

/**
Returns source for the script with given id.
 */
public class GetScriptSourceParams extends org.eclipse.wst.jsdt.chromium.internal.wip.protocol.output.WipParamsWithResponse<org.eclipse.wst.jsdt.chromium.internal.wip.protocol.input.debugger.GetScriptSourceData> {
  /**
   @param scriptId Id of the script to get source for.
   */
  public GetScriptSourceParams(String/*See org.eclipse.wst.jsdt.chromium.internal.wip.protocol.common.runtime.ScriptIdTypedef*/ scriptId) {
    this.put("scriptId", scriptId);
  }

  public static final String METHOD_NAME = org.eclipse.wst.jsdt.chromium.internal.wip.protocol.BasicConstants.Domain.DEBUGGER + ".getScriptSource";

  @Override protected String getRequestName() {
    return METHOD_NAME;
  }

  @Override public org.eclipse.wst.jsdt.chromium.internal.wip.protocol.input.debugger.GetScriptSourceData parseResponse(org.eclipse.wst.jsdt.chromium.internal.wip.protocol.input.WipCommandResponse.Data data, org.eclipse.wst.jsdt.chromium.internal.wip.protocol.input.WipGeneratedParserRoot parser) throws org.eclipse.wst.jsdt.chromium.internal.protocolparser.JsonProtocolParseException {
    return parser.parseDebuggerGetScriptSourceData(data.getUnderlyingObject());
  }

}
