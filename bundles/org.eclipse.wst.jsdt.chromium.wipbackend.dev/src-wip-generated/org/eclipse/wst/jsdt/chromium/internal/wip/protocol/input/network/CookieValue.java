// Generated source.
// Generator: org.eclipse.wst.jsdt.chromium.internal.wip.tools.protocolgenerator.Generator
// Origin: https://chromium.googlesource.com/chromium/src/+/51.0.2704.79/third_party/WebKit/Source/devtools/protocol.json?format=TEXT@<unknown>

package org.eclipse.wst.jsdt.chromium.internal.wip.protocol.input.network;

/**
 Cookie object
 */
@org.eclipse.wst.jsdt.chromium.internal.protocolparser.JsonType
public interface CookieValue {
  /**
   Cookie name.
   */
  String name();

  /**
   Cookie value.
   */
  String value();

  /**
   Cookie domain.
   */
  String domain();

  /**
   Cookie path.
   */
  String path();

  /**
   Cookie expires.
   */
  Number expires();

  /**
   Cookie size.
   */
  long size();

  /**
   True if cookie is http-only.
   */
  boolean httpOnly();

  /**
   True if cookie is secure.
   */
  boolean secure();

  /**
   True in case of session cookie.
   */
  boolean session();

  /**
   Represents the cookies' 'SameSite' status: https://tools.ietf.org/html/draft-west-first-party-cookies
   */
  @org.eclipse.wst.jsdt.chromium.internal.protocolparser.JsonOptionalField
  SameSite sameSite();

  /**
   Represents the cookies' 'SameSite' status: https://tools.ietf.org/html/draft-west-first-party-cookies
   */
  public enum SameSite {
    STRICT,
    LAX,
  }
}
