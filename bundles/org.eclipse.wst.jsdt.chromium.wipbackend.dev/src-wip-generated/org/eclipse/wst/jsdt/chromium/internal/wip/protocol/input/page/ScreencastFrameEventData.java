// Generated source.
// Generator: org.eclipse.wst.jsdt.chromium.internal.wip.tools.protocolgenerator.Generator
// Origin: https://chromium.googlesource.com/chromium/src/+/51.0.2704.79/third_party/WebKit/Source/devtools/protocol.json?format=TEXT@<unknown>

package org.eclipse.wst.jsdt.chromium.internal.wip.protocol.input.page;

/**
 Compressed image data requested by the <code>startScreencast</code>.
 */
@org.eclipse.wst.jsdt.chromium.internal.protocolparser.JsonType
public interface ScreencastFrameEventData {
  /**
   Base64-encoded compressed image.
   */
  String data();

  /**
   Screencast frame metadata.
   */
  org.eclipse.wst.jsdt.chromium.internal.wip.protocol.input.page.ScreencastFrameMetadataValue metadata();

  /**
   Frame number.
   */
  long sessionId();

  public static final org.eclipse.wst.jsdt.chromium.internal.wip.protocol.input.WipEventType<org.eclipse.wst.jsdt.chromium.internal.wip.protocol.input.page.ScreencastFrameEventData> TYPE
      = new org.eclipse.wst.jsdt.chromium.internal.wip.protocol.input.WipEventType<org.eclipse.wst.jsdt.chromium.internal.wip.protocol.input.page.ScreencastFrameEventData>("Page.screencastFrame", org.eclipse.wst.jsdt.chromium.internal.wip.protocol.input.page.ScreencastFrameEventData.class) {
    @Override public org.eclipse.wst.jsdt.chromium.internal.wip.protocol.input.page.ScreencastFrameEventData parse(org.eclipse.wst.jsdt.chromium.internal.wip.protocol.input.WipGeneratedParserRoot parser, org.json.simple.JSONObject obj) throws org.eclipse.wst.jsdt.chromium.internal.protocolparser.JsonProtocolParseException {
      return parser.parsePageScreencastFrameEventData(obj);
    }
  };
}
