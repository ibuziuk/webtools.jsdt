// Generated source.
// Generator: org.eclipse.wst.jsdt.chromium.internal.wip.tools.protocolgenerator.Generator
// Origin: https://chromium.googlesource.com/chromium/src/+/51.0.2704.79/third_party/WebKit/Source/devtools/protocol.json?format=TEXT@<unknown>

package org.eclipse.wst.jsdt.chromium.internal.wip.protocol.output.page;

/**
Acknowledges that a screencast frame has been received by the frontend.
 */
public class ScreencastFrameAckParams extends org.eclipse.wst.jsdt.chromium.internal.wip.protocol.output.WipParams {
  /**
   @param sessionId Frame number.
   */
  public ScreencastFrameAckParams(long sessionId) {
    this.put("sessionId", sessionId);
  }

  public static final String METHOD_NAME = org.eclipse.wst.jsdt.chromium.internal.wip.protocol.BasicConstants.Domain.PAGE + ".screencastFrameAck";

  @Override protected String getRequestName() {
    return METHOD_NAME;
  }

}
