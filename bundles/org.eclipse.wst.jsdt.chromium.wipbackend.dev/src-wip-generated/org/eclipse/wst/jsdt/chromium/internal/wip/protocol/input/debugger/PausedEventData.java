// Generated source.
// Generator: org.eclipse.wst.jsdt.chromium.internal.wip.tools.protocolgenerator.Generator
// Origin: https://chromium.googlesource.com/chromium/src/+/51.0.2704.79/third_party/WebKit/Source/devtools/protocol.json?format=TEXT@<unknown>

package org.eclipse.wst.jsdt.chromium.internal.wip.protocol.input.debugger;

/**
 Fired when the virtual machine stopped on breakpoint or exception or any other stop criteria.
 */
@org.eclipse.wst.jsdt.chromium.internal.protocolparser.JsonType
public interface PausedEventData {
  /**
   Call stack the virtual machine stopped on.
   */
  java.util.List<org.eclipse.wst.jsdt.chromium.internal.wip.protocol.input.debugger.CallFrameValue> callFrames();

  /**
   Pause reason.
   */
  Reason reason();

  /**
   Object containing break-specific auxiliary properties.
   */
  @org.eclipse.wst.jsdt.chromium.internal.protocolparser.JsonOptionalField
  Data data();

  /**
   Hit breakpoints IDs
   */
  @org.eclipse.wst.jsdt.chromium.internal.protocolparser.JsonOptionalField
  java.util.List<String> hitBreakpoints();

  /**
   Async stack trace, if any.
   */
  @org.eclipse.wst.jsdt.chromium.internal.protocolparser.JsonOptionalField
  org.eclipse.wst.jsdt.chromium.internal.wip.protocol.input.runtime.StackTraceValue asyncStackTrace();

  public static final org.eclipse.wst.jsdt.chromium.internal.wip.protocol.input.WipEventType<org.eclipse.wst.jsdt.chromium.internal.wip.protocol.input.debugger.PausedEventData> TYPE
      = new org.eclipse.wst.jsdt.chromium.internal.wip.protocol.input.WipEventType<org.eclipse.wst.jsdt.chromium.internal.wip.protocol.input.debugger.PausedEventData>("Debugger.paused", org.eclipse.wst.jsdt.chromium.internal.wip.protocol.input.debugger.PausedEventData.class) {
    @Override public org.eclipse.wst.jsdt.chromium.internal.wip.protocol.input.debugger.PausedEventData parse(org.eclipse.wst.jsdt.chromium.internal.wip.protocol.input.WipGeneratedParserRoot parser, org.json.simple.JSONObject obj) throws org.eclipse.wst.jsdt.chromium.internal.protocolparser.JsonProtocolParseException {
      return parser.parseDebuggerPausedEventData(obj);
    }
  };
  /**
   Pause reason.
   */
  public enum Reason {
    XHR,
    DOM,
    EVENTLISTENER,
    EXCEPTION,
    ASSERT,
    CSPVIOLATION,
    DEBUGCOMMAND,
    PROMISEREJECTION,
    OTHER,
  }
  /**
   Object containing break-specific auxiliary properties.
   */
  @org.eclipse.wst.jsdt.chromium.internal.protocolparser.JsonType(allowsOtherProperties=true)
  public interface Data extends org.eclipse.wst.jsdt.chromium.internal.protocolparser.JsonObjectBased {
  }
}
