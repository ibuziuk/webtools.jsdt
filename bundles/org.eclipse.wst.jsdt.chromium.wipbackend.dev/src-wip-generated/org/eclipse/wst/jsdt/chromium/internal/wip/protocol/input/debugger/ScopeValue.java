// Generated source.
// Generator: org.eclipse.wst.jsdt.chromium.internal.wip.tools.protocolgenerator.Generator
// Origin: https://chromium.googlesource.com/chromium/src/+/51.0.2704.79/third_party/WebKit/Source/devtools/protocol.json?format=TEXT@<unknown>

package org.eclipse.wst.jsdt.chromium.internal.wip.protocol.input.debugger;

/**
 Scope description.
 */
@org.eclipse.wst.jsdt.chromium.internal.protocolparser.JsonType
public interface ScopeValue {
  /**
   Scope type.
   */
  Type type();

  /**
   Object representing the scope. For <code>global</code> and <code>with</code> scopes it represents the actual object; for the rest of the scopes, it is artificial transient object enumerating scope variables as its properties.
   */
  org.eclipse.wst.jsdt.chromium.internal.wip.protocol.input.runtime.RemoteObjectValue object();

  @org.eclipse.wst.jsdt.chromium.internal.protocolparser.JsonOptionalField
  String name();

  /**
   Location in the source code where scope starts
   */
  @org.eclipse.wst.jsdt.chromium.internal.protocolparser.JsonOptionalField
  org.eclipse.wst.jsdt.chromium.internal.wip.protocol.input.debugger.LocationValue startLocation();

  /**
   Location in the source code where scope ends
   */
  @org.eclipse.wst.jsdt.chromium.internal.protocolparser.JsonOptionalField
  org.eclipse.wst.jsdt.chromium.internal.wip.protocol.input.debugger.LocationValue endLocation();

  /**
   Scope type.
   */
  public enum Type {
    GLOBAL,
    LOCAL,
    WITH,
    CLOSURE,
    CATCH,
    BLOCK,
    SCRIPT,
  }
}
