// Generated source.
// Generator: org.eclipse.wst.jsdt.chromium.internal.wip.tools.protocolgenerator.Generator
// Origin: https://chromium.googlesource.com/chromium/src/+/51.0.2704.79/third_party/WebKit/Source/devtools/protocol.json?format=TEXT@<unknown>

package org.eclipse.wst.jsdt.chromium.internal.wip.protocol.output.runtime;

/**
Compiles expression.
 */
public class CompileScriptParams extends org.eclipse.wst.jsdt.chromium.internal.wip.protocol.output.WipParamsWithResponse<org.eclipse.wst.jsdt.chromium.internal.wip.protocol.input.runtime.CompileScriptData> {
  /**
   @param expression Expression to compile.
   @param sourceURL Source url to be set for the script.
   @param persistScript Specifies whether the compiled script should be persisted.
   @param executionContextId Specifies in which isolated context to perform script run. Each content script lives in an isolated context and this parameter is used to specify one of those contexts.
   */
  public CompileScriptParams(String expression, String sourceURL, boolean persistScript, long/*See org.eclipse.wst.jsdt.chromium.internal.wip.protocol.common.runtime.ExecutionContextIdTypedef*/ executionContextId) {
    this.put("expression", expression);
    this.put("sourceURL", sourceURL);
    this.put("persistScript", persistScript);
    this.put("executionContextId", executionContextId);
  }

  public static final String METHOD_NAME = org.eclipse.wst.jsdt.chromium.internal.wip.protocol.BasicConstants.Domain.RUNTIME + ".compileScript";

  @Override protected String getRequestName() {
    return METHOD_NAME;
  }

  @Override public org.eclipse.wst.jsdt.chromium.internal.wip.protocol.input.runtime.CompileScriptData parseResponse(org.eclipse.wst.jsdt.chromium.internal.wip.protocol.input.WipCommandResponse.Data data, org.eclipse.wst.jsdt.chromium.internal.wip.protocol.input.WipGeneratedParserRoot parser) throws org.eclipse.wst.jsdt.chromium.internal.protocolparser.JsonProtocolParseException {
    return parser.parseRuntimeCompileScriptData(data.getUnderlyingObject());
  }

}
