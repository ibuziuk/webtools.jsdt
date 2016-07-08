// Generated source.
// Generator: org.eclipse.wst.jsdt.chromium.internal.wip.tools.protocolgenerator.Generator
// Origin: https://chromium.googlesource.com/chromium/src/+/51.0.2704.79/third_party/WebKit/Source/devtools/protocol.json?format=TEXT@<unknown>

package org.eclipse.wst.jsdt.chromium.internal.wip.protocol.output.network;

/**
Enables network tracking, network events will now be delivered to the client.
 */
public class EnableParams extends org.eclipse.wst.jsdt.chromium.internal.wip.protocol.output.WipParams {
  /**
   @param maxTotalBufferSizeOpt Buffer size in bytes to use when preserving network payloads (XHRs, etc).
   @param maxResourceBufferSizeOpt Per-resource buffer size in bytes to use when preserving network payloads (XHRs, etc).
   */
  public EnableParams(Long maxTotalBufferSizeOpt, Long maxResourceBufferSizeOpt) {
    if (maxTotalBufferSizeOpt != null) {
      this.put("maxTotalBufferSize", maxTotalBufferSizeOpt);
    }
    if (maxResourceBufferSizeOpt != null) {
      this.put("maxResourceBufferSize", maxResourceBufferSizeOpt);
    }
  }

  public static final String METHOD_NAME = org.eclipse.wst.jsdt.chromium.internal.wip.protocol.BasicConstants.Domain.NETWORK + ".enable";

  @Override protected String getRequestName() {
    return METHOD_NAME;
  }

}
