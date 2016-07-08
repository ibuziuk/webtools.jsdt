// Generated source.
// Generator: org.eclipse.wst.jsdt.chromium.internal.wip.tools.protocolgenerator.Generator
// Origin: https://chromium.googlesource.com/chromium/src/+/51.0.2704.79/third_party/WebKit/Source/devtools/protocol.json?format=TEXT@<unknown>

package org.eclipse.wst.jsdt.chromium.internal.wip.protocol.input.page;

/**
 Information about the Frame hierarchy along with their cached resources.
 */
@org.eclipse.wst.jsdt.chromium.internal.protocolparser.JsonType
public interface FrameResourceTreeValue {
  /**
   Frame information for this tree item.
   */
  org.eclipse.wst.jsdt.chromium.internal.wip.protocol.input.page.FrameValue frame();

  /**
   Child frames.
   */
  @org.eclipse.wst.jsdt.chromium.internal.protocolparser.JsonOptionalField
  java.util.List<org.eclipse.wst.jsdt.chromium.internal.wip.protocol.input.page.FrameResourceTreeValue> childFrames();

  /**
   Information about frame resources.
   */
  java.util.List<org.eclipse.wst.jsdt.chromium.internal.wip.protocol.input.page.FrameResourceValue> resources();

}
