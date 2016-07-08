// Generated source.
// Generator: org.eclipse.wst.jsdt.chromium.internal.wip.tools.protocolgenerator.Generator
// Origin: https://chromium.googlesource.com/chromium/src/+/51.0.2704.79/third_party/WebKit/Source/devtools/protocol.json?format=TEXT@<unknown>

package org.eclipse.wst.jsdt.chromium.internal.wip.protocol.input.runtime;

/**
 Runs script with given id in a given context.
 */
@org.eclipse.wst.jsdt.chromium.internal.protocolparser.JsonType
public interface RunScriptData {
  /**
   Run result.
   */
  org.eclipse.wst.jsdt.chromium.internal.wip.protocol.input.runtime.RemoteObjectValue result();

  /**
   Exception details.
   */
  @org.eclipse.wst.jsdt.chromium.internal.protocolparser.JsonOptionalField
  org.eclipse.wst.jsdt.chromium.internal.wip.protocol.input.runtime.ExceptionDetailsValue exceptionDetails();

}
