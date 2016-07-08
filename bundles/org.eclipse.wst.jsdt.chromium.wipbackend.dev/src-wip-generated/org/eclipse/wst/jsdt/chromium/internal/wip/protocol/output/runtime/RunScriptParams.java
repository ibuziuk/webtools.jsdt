// Generated source.
// Generator: org.eclipse.wst.jsdt.chromium.internal.wip.tools.protocolgenerator.Generator
// Origin: https://chromium.googlesource.com/chromium/src/+/51.0.2704.79/third_party/WebKit/Source/devtools/protocol.json?format=TEXT@<unknown>

package org.eclipse.wst.jsdt.chromium.internal.wip.protocol.output.runtime;

/**
Runs script with given id in a given context.
 */
public class RunScriptParams extends org.eclipse.wst.jsdt.chromium.internal.wip.protocol.output.WipParamsWithResponse<org.eclipse.wst.jsdt.chromium.internal.wip.protocol.input.runtime.RunScriptData> {
  /**
   @param scriptId Id of the script to run.
   @param executionContextId Specifies in which isolated context to perform script run. Each content script lives in an isolated context and this parameter is used to specify one of those contexts.
   @param objectGroupOpt Symbolic group name that can be used to release multiple objects.
   @param doNotPauseOnExceptionsAndMuteConsoleOpt Specifies whether script run should stop on exceptions and mute console. Overrides setPauseOnException state.
   @param includeCommandLineAPIOpt Determines whether Command Line API should be available during the evaluation.
   */
  public RunScriptParams(String/*See org.eclipse.wst.jsdt.chromium.internal.wip.protocol.common.runtime.ScriptIdTypedef*/ scriptId, long/*See org.eclipse.wst.jsdt.chromium.internal.wip.protocol.common.runtime.ExecutionContextIdTypedef*/ executionContextId, String objectGroupOpt, Boolean doNotPauseOnExceptionsAndMuteConsoleOpt, Boolean includeCommandLineAPIOpt) {
    this.put("scriptId", scriptId);
    this.put("executionContextId", executionContextId);
    if (objectGroupOpt != null) {
      this.put("objectGroup", objectGroupOpt);
    }
    if (doNotPauseOnExceptionsAndMuteConsoleOpt != null) {
      this.put("doNotPauseOnExceptionsAndMuteConsole", doNotPauseOnExceptionsAndMuteConsoleOpt);
    }
    if (includeCommandLineAPIOpt != null) {
      this.put("includeCommandLineAPI", includeCommandLineAPIOpt);
    }
  }

  public static final String METHOD_NAME = org.eclipse.wst.jsdt.chromium.internal.wip.protocol.BasicConstants.Domain.RUNTIME + ".runScript";

  @Override protected String getRequestName() {
    return METHOD_NAME;
  }

  @Override public org.eclipse.wst.jsdt.chromium.internal.wip.protocol.input.runtime.RunScriptData parseResponse(org.eclipse.wst.jsdt.chromium.internal.wip.protocol.input.WipCommandResponse.Data data, org.eclipse.wst.jsdt.chromium.internal.wip.protocol.input.WipGeneratedParserRoot parser) throws org.eclipse.wst.jsdt.chromium.internal.protocolparser.JsonProtocolParseException {
    return parser.parseRuntimeRunScriptData(data.getUnderlyingObject());
  }

}
