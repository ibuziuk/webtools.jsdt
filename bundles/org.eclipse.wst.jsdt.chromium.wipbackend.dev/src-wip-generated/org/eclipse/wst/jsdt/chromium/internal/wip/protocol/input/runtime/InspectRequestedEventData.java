// Generated source.
// Generator: org.eclipse.wst.jsdt.chromium.internal.wip.tools.protocolgenerator.Generator
// Origin: https://chromium.googlesource.com/chromium/src/+/51.0.2704.79/third_party/WebKit/Source/devtools/protocol.json?format=TEXT@<unknown>

package org.eclipse.wst.jsdt.chromium.internal.wip.protocol.input.runtime;

@org.eclipse.wst.jsdt.chromium.internal.protocolparser.JsonType
public interface InspectRequestedEventData {
  org.eclipse.wst.jsdt.chromium.internal.wip.protocol.input.runtime.RemoteObjectValue object();

  Hints hints();

  public static final org.eclipse.wst.jsdt.chromium.internal.wip.protocol.input.WipEventType<org.eclipse.wst.jsdt.chromium.internal.wip.protocol.input.runtime.InspectRequestedEventData> TYPE
      = new org.eclipse.wst.jsdt.chromium.internal.wip.protocol.input.WipEventType<org.eclipse.wst.jsdt.chromium.internal.wip.protocol.input.runtime.InspectRequestedEventData>("Runtime.inspectRequested", org.eclipse.wst.jsdt.chromium.internal.wip.protocol.input.runtime.InspectRequestedEventData.class) {
    @Override public org.eclipse.wst.jsdt.chromium.internal.wip.protocol.input.runtime.InspectRequestedEventData parse(org.eclipse.wst.jsdt.chromium.internal.wip.protocol.input.WipGeneratedParserRoot parser, org.json.simple.JSONObject obj) throws org.eclipse.wst.jsdt.chromium.internal.protocolparser.JsonProtocolParseException {
      return parser.parseRuntimeInspectRequestedEventData(obj);
    }
  };
  @org.eclipse.wst.jsdt.chromium.internal.protocolparser.JsonType(allowsOtherProperties=true)
  public interface Hints extends org.eclipse.wst.jsdt.chromium.internal.protocolparser.JsonObjectBased {
  }
}
