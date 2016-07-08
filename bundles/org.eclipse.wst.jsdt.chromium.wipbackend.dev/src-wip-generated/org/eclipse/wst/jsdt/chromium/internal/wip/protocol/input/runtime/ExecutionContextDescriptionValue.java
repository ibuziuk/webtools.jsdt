// Generated source.
// Generator: org.eclipse.wst.jsdt.chromium.internal.wip.tools.protocolgenerator.Generator
// Origin: https://chromium.googlesource.com/chromium/src/+/51.0.2704.79/third_party/WebKit/Source/devtools/protocol.json?format=TEXT@<unknown>

package org.eclipse.wst.jsdt.chromium.internal.wip.protocol.input.runtime;

/**
 Description of an isolated world.
 */
@org.eclipse.wst.jsdt.chromium.internal.protocolparser.JsonType
public interface ExecutionContextDescriptionValue {
  /**
   Unique id of the execution context. It can be used to specify in which execution context script evaluation should be performed.
   */
  long/*See org.eclipse.wst.jsdt.chromium.internal.wip.protocol.common.runtime.ExecutionContextIdTypedef*/ id();

  /**
   Whether context is the default page context (as opposite to e.g. context of content script).
   */
  boolean isDefault();

  /**
   Execution context origin.
   */
  String origin();

  /**
   Human readable name describing given context.
   */
  String name();

  /**
   Id of the owning frame. May be an empty string if the context is not associated with a frame.
   */
  String frameId();

}
