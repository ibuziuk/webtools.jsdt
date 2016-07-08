// Generated source.
// Generator: org.eclipse.wst.jsdt.chromium.internal.wip.tools.protocolgenerator.Generator
// Origin: https://chromium.googlesource.com/chromium/src/+/51.0.2704.79/third_party/WebKit/Source/devtools/protocol.json?format=TEXT@<unknown>

package org.eclipse.wst.jsdt.chromium.internal.wip.protocol.input.console;

/**
 Console message.
 */
@org.eclipse.wst.jsdt.chromium.internal.protocolparser.JsonType
public interface ConsoleMessageValue {
  /**
   Message source.
   */
  Source source();

  /**
   Message severity.
   */
  Level level();

  /**
   Message text.
   */
  String text();

  /**
   Console message type.
   */
  @org.eclipse.wst.jsdt.chromium.internal.protocolparser.JsonOptionalField
  Type type();

  /**
   Script ID of the message origin.
   */
  @org.eclipse.wst.jsdt.chromium.internal.protocolparser.JsonOptionalField
  String scriptId();

  /**
   URL of the message origin.
   */
  @org.eclipse.wst.jsdt.chromium.internal.protocolparser.JsonOptionalField
  String url();

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
   Repeat count for repeated messages.
   */
  @org.eclipse.wst.jsdt.chromium.internal.protocolparser.JsonOptionalField
  Long repeatCount();

  /**
   Message parameters in case of the formatted message.
   */
  @org.eclipse.wst.jsdt.chromium.internal.protocolparser.JsonOptionalField
  java.util.List<org.eclipse.wst.jsdt.chromium.internal.wip.protocol.input.runtime.RemoteObjectValue> parameters();

  /**
   JavaScript stack trace for assertions and error messages.
   */
  @org.eclipse.wst.jsdt.chromium.internal.protocolparser.JsonOptionalField
  org.eclipse.wst.jsdt.chromium.internal.wip.protocol.input.runtime.StackTraceValue stack();

  /**
   Identifier of the network request associated with this message.
   */
  @org.eclipse.wst.jsdt.chromium.internal.protocolparser.JsonOptionalField
  String/*See org.eclipse.wst.jsdt.chromium.internal.wip.protocol.common.network.RequestIdTypedef*/ networkRequestId();

  /**
   Timestamp, when this message was fired.
   */
  Number/*See org.eclipse.wst.jsdt.chromium.internal.wip.protocol.common.console.TimestampTypedef*/ timestamp();

  /**
   Identifier of the context where this message was created
   */
  @org.eclipse.wst.jsdt.chromium.internal.protocolparser.JsonOptionalField
  Long/*See org.eclipse.wst.jsdt.chromium.internal.wip.protocol.common.runtime.ExecutionContextIdTypedef*/ executionContextId();

  /**
   Message id.
   */
  @org.eclipse.wst.jsdt.chromium.internal.protocolparser.JsonOptionalField
  Long messageId();

  /**
   Related message id.
   */
  @org.eclipse.wst.jsdt.chromium.internal.protocolparser.JsonOptionalField
  Long relatedMessageId();

  /**
   Message source.
   */
  public enum Source {
    XML,
    JAVASCRIPT,
    NETWORK,
    CONSOLE_API,
    STORAGE,
    APPCACHE,
    RENDERING,
    SECURITY,
    OTHER,
    DEPRECATION,
  }
  /**
   Message severity.
   */
  public enum Level {
    LOG,
    WARNING,
    ERROR,
    DEBUG,
    INFO,
    REVOKEDERROR,
  }
  /**
   Console message type.
   */
  public enum Type {
    LOG,
    DIR,
    DIRXML,
    TABLE,
    TRACE,
    CLEAR,
    STARTGROUP,
    STARTGROUPCOLLAPSED,
    ENDGROUP,
    ASSERT,
    PROFILE,
    PROFILEEND,
  }
}
