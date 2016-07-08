// Generated source.
// Generator: org.eclipse.wst.jsdt.chromium.internal.wip.tools.protocolgenerator.Generator
// Origin: https://chromium.googlesource.com/chromium/src/+/51.0.2704.79/third_party/WebKit/Source/devtools/protocol.json?format=TEXT@<unknown>

package org.eclipse.wst.jsdt.chromium.internal.wip.protocol.input.runtime;

/**
 Detailed information on exception (or error) that was thrown during script compilation or execution.
 */
@org.eclipse.wst.jsdt.chromium.internal.protocolparser.JsonType
public interface ExceptionDetailsValue {
  /**
   Exception text.
   */
  String text();

  /**
   URL of the message origin.
   */
  @org.eclipse.wst.jsdt.chromium.internal.protocolparser.JsonOptionalField
  String url();

  /**
   Script ID of the message origin.
   */
  @org.eclipse.wst.jsdt.chromium.internal.protocolparser.JsonOptionalField
  String scriptId();

  /**
   Line number in the resource that generated this message.
   */
  @org.eclipse.wst.jsdt.chromium.internal.protocolparser.JsonOptionalField
  Long line();

  /**
   Column number in the resource that generated this message.
   */
  @org.eclipse.wst.jsdt.chromium.internal.protocolparser.JsonOptionalField
  Long column();

  /**
   JavaScript stack trace for assertions and error messages.
   */
  @org.eclipse.wst.jsdt.chromium.internal.protocolparser.JsonOptionalField
  org.eclipse.wst.jsdt.chromium.internal.wip.protocol.input.runtime.StackTraceValue stack();

}
