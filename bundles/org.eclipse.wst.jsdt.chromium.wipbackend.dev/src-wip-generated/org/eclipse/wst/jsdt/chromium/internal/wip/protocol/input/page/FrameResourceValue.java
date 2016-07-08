// Generated source.
// Generator: org.eclipse.wst.jsdt.chromium.internal.wip.tools.protocolgenerator.Generator
// Origin: https://chromium.googlesource.com/chromium/src/+/51.0.2704.79/third_party/WebKit/Source/devtools/protocol.json?format=TEXT@<unknown>

package org.eclipse.wst.jsdt.chromium.internal.wip.protocol.input.page;

/**
 Information about the Resource on the page.
 */
@org.eclipse.wst.jsdt.chromium.internal.protocolparser.JsonType
public interface FrameResourceValue {
  /**
   Resource URL.
   */
  String url();

  /**
   Type of this resource.
   */
  org.eclipse.wst.jsdt.chromium.internal.wip.protocol.input.page.ResourceTypeEnum type();

  /**
   Resource mimeType as determined by the browser.
   */
  String mimeType();

  /**
   True if the resource failed to load.
   */
  @org.eclipse.wst.jsdt.chromium.internal.protocolparser.JsonOptionalField
  Boolean failed();

  /**
   True if the resource was canceled during loading.
   */
  @org.eclipse.wst.jsdt.chromium.internal.protocolparser.JsonOptionalField
  Boolean canceled();

}
