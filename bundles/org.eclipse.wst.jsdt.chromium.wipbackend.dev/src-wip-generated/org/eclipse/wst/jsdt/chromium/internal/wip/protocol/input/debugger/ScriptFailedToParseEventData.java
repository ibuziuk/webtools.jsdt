// Generated source.
// Generator: org.eclipse.wst.jsdt.chromium.internal.wip.tools.protocolgenerator.Generator
// Origin: https://chromium.googlesource.com/chromium/src/+/51.0.2704.79/third_party/WebKit/Source/devtools/protocol.json?format=TEXT@<unknown>

package org.eclipse.wst.jsdt.chromium.internal.wip.protocol.input.debugger;

/**
 Fired when virtual machine fails to parse the script.
 */
@org.eclipse.wst.jsdt.chromium.internal.protocolparser.JsonType
public interface ScriptFailedToParseEventData {
  /**
   Identifier of the script parsed.
   */
  String/*See org.eclipse.wst.jsdt.chromium.internal.wip.protocol.common.runtime.ScriptIdTypedef*/ scriptId();

  /**
   URL or name of the script parsed (if any).
   */
  String url();

  /**
   Line offset of the script within the resource with given URL (for script tags).
   */
  long startLine();

  /**
   Column offset of the script within the resource with given URL.
   */
  long startColumn();

  /**
   Last line of the script.
   */
  long endLine();

  /**
   Length of the last line of the script.
   */
  long endColumn();

  /**
   Specifies script creation context.
   */
  long/*See org.eclipse.wst.jsdt.chromium.internal.wip.protocol.common.runtime.ExecutionContextIdTypedef*/ executionContextId();

  /**
   Content hash of the script.
   */
  String hash();

  /**
   Determines whether this script is a user extension script.
   */
  @org.eclipse.wst.jsdt.chromium.internal.protocolparser.JsonOptionalField
  Boolean isContentScript();

  /**
   Determines whether this script is an internal script.
   */
  @org.eclipse.wst.jsdt.chromium.internal.protocolparser.JsonOptionalField
  Boolean isInternalScript();

  /**
   URL of source map associated with script (if any).
   */
  @org.eclipse.wst.jsdt.chromium.internal.protocolparser.JsonOptionalField
  String sourceMapURL();

  /**
   True, if this script has sourceURL.
   */
  @org.eclipse.wst.jsdt.chromium.internal.protocolparser.JsonOptionalField
  Boolean hasSourceURL();

  /**
   True, if '//@ sourceURL' or '//@ sourceMappingURL' was used.
   */
  @org.eclipse.wst.jsdt.chromium.internal.protocolparser.JsonOptionalField
  Boolean deprecatedCommentWasUsed();

  public static final org.eclipse.wst.jsdt.chromium.internal.wip.protocol.input.WipEventType<org.eclipse.wst.jsdt.chromium.internal.wip.protocol.input.debugger.ScriptFailedToParseEventData> TYPE
      = new org.eclipse.wst.jsdt.chromium.internal.wip.protocol.input.WipEventType<org.eclipse.wst.jsdt.chromium.internal.wip.protocol.input.debugger.ScriptFailedToParseEventData>("Debugger.scriptFailedToParse", org.eclipse.wst.jsdt.chromium.internal.wip.protocol.input.debugger.ScriptFailedToParseEventData.class) {
    @Override public org.eclipse.wst.jsdt.chromium.internal.wip.protocol.input.debugger.ScriptFailedToParseEventData parse(org.eclipse.wst.jsdt.chromium.internal.wip.protocol.input.WipGeneratedParserRoot parser, org.json.simple.JSONObject obj) throws org.eclipse.wst.jsdt.chromium.internal.protocolparser.JsonProtocolParseException {
      return parser.parseDebuggerScriptFailedToParseEventData(obj);
    }
  };
}
