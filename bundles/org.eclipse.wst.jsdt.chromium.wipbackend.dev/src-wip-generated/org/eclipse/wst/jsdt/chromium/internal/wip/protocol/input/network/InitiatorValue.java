// Generated source.
// Generator: org.eclipse.wst.jsdt.chromium.internal.wip.tools.protocolgenerator.Generator
// Origin: https://chromium.googlesource.com/chromium/src/+/51.0.2704.79/third_party/WebKit/Source/devtools/protocol.json?format=TEXT@<unknown>

package org.eclipse.wst.jsdt.chromium.internal.wip.protocol.input.network;

/**
 Information about the request initiator.
 */
@org.eclipse.wst.jsdt.chromium.internal.protocolparser.JsonType
public interface InitiatorValue {
  /**
   Type of this initiator.
   */
  Type type();

  /**
   Initiator JavaScript stack trace, set for Script only.
   */
  @org.eclipse.wst.jsdt.chromium.internal.protocolparser.JsonOptionalField
  org.eclipse.wst.jsdt.chromium.internal.wip.protocol.input.runtime.StackTraceValue stack();

  /**
   Initiator URL, set for Parser type only.
   */
  @org.eclipse.wst.jsdt.chromium.internal.protocolparser.JsonOptionalField
  String url();

  /**
   Initiator line number, set for Parser type only.
   */
  @org.eclipse.wst.jsdt.chromium.internal.protocolparser.JsonOptionalField
  Number lineNumber();

  /**
   Type of this initiator.
   */
  public enum Type {
    PARSER,
    SCRIPT,
    OTHER,
  }
}
