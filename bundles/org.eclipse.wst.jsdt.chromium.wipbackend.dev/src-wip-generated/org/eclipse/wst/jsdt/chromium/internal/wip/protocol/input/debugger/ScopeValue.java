// Generated source.
// Generator: org.eclipse.wst.jsdt.chromium.internal.wip.tools.protocolgenerator.Generator
// Origin: http://src.chromium.org/blink/trunk/Source/devtools/protocol.json@<unknown>

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
