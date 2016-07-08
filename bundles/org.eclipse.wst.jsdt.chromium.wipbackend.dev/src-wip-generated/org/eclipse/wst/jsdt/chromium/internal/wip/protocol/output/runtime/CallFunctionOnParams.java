// Generated source.
// Generator: org.eclipse.wst.jsdt.chromium.internal.wip.tools.protocolgenerator.Generator
// Origin: https://chromium.googlesource.com/chromium/src/+/51.0.2704.79/third_party/WebKit/Source/devtools/protocol.json?format=TEXT@<unknown>

package org.eclipse.wst.jsdt.chromium.internal.wip.protocol.output.runtime;

/**
Calls function with given declaration on the given object. Object group of the result is inherited from the target object.
 */
public class CallFunctionOnParams extends org.eclipse.wst.jsdt.chromium.internal.wip.protocol.output.WipParamsWithResponse<org.eclipse.wst.jsdt.chromium.internal.wip.protocol.input.runtime.CallFunctionOnData> {
  /**
   @param objectId Identifier of the object to call function on.
   @param functionDeclaration Declaration of the function to call.
   @param argumentsOpt Call arguments. All call arguments must belong to the same JavaScript world as the target object.
   @param doNotPauseOnExceptionsAndMuteConsoleOpt Specifies whether function call should stop on exceptions and mute console. Overrides setPauseOnException state.
   @param returnByValueOpt Whether the result is expected to be a JSON object which should be sent by value.
   @param generatePreviewOpt Whether preview should be generated for the result.
   @param userGestureOpt Whether execution should be treated as initiated by user in the UI.
   */
  public CallFunctionOnParams(String/*See org.eclipse.wst.jsdt.chromium.internal.wip.protocol.common.runtime.RemoteObjectIdTypedef*/ objectId, String functionDeclaration, java.util.List<org.eclipse.wst.jsdt.chromium.internal.wip.protocol.output.runtime.CallArgumentParam> argumentsOpt, Boolean doNotPauseOnExceptionsAndMuteConsoleOpt, Boolean returnByValueOpt, Boolean generatePreviewOpt, Boolean userGestureOpt) {
    this.put("objectId", objectId);
    this.put("functionDeclaration", functionDeclaration);
    if (argumentsOpt != null) {
      this.put("arguments", argumentsOpt);
    }
    if (doNotPauseOnExceptionsAndMuteConsoleOpt != null) {
      this.put("doNotPauseOnExceptionsAndMuteConsole", doNotPauseOnExceptionsAndMuteConsoleOpt);
    }
    if (returnByValueOpt != null) {
      this.put("returnByValue", returnByValueOpt);
    }
    if (generatePreviewOpt != null) {
      this.put("generatePreview", generatePreviewOpt);
    }
    if (userGestureOpt != null) {
      this.put("userGesture", userGestureOpt);
    }
  }

  public static final String METHOD_NAME = org.eclipse.wst.jsdt.chromium.internal.wip.protocol.BasicConstants.Domain.RUNTIME + ".callFunctionOn";

  @Override protected String getRequestName() {
    return METHOD_NAME;
  }

  @Override public org.eclipse.wst.jsdt.chromium.internal.wip.protocol.input.runtime.CallFunctionOnData parseResponse(org.eclipse.wst.jsdt.chromium.internal.wip.protocol.input.WipCommandResponse.Data data, org.eclipse.wst.jsdt.chromium.internal.wip.protocol.input.WipGeneratedParserRoot parser) throws org.eclipse.wst.jsdt.chromium.internal.protocolparser.JsonProtocolParseException {
    return parser.parseRuntimeCallFunctionOnData(data.getUnderlyingObject());
  }

}
