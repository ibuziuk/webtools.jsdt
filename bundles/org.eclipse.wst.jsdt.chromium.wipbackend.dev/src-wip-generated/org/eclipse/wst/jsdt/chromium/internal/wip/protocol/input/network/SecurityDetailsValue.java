// Generated source.
// Generator: org.eclipse.wst.jsdt.chromium.internal.wip.tools.protocolgenerator.Generator
// Origin: https://chromium.googlesource.com/chromium/src/+/51.0.2704.79/third_party/WebKit/Source/devtools/protocol.json?format=TEXT@<unknown>

package org.eclipse.wst.jsdt.chromium.internal.wip.protocol.input.network;

/**
 Security details about a request.
 */
@org.eclipse.wst.jsdt.chromium.internal.protocolparser.JsonType
public interface SecurityDetailsValue {
  /**
   Protocol name (e.g. "TLS 1.2" or "QUIC").
   */
  String protocol();

  /**
   Key Exchange used by the connection.
   */
  String keyExchange();

  /**
   Cipher name.
   */
  String cipher();

  /**
   TLS MAC. Note that AEAD ciphers do not have separate MACs.
   */
  @org.eclipse.wst.jsdt.chromium.internal.protocolparser.JsonOptionalField
  String mac();

  /**
   Certificate ID value.
   */
  long/*See org.eclipse.wst.jsdt.chromium.internal.wip.protocol.common.network.CertificateIdTypedef*/ certificateId();

  /**
   Validation details for the request's certficate.
   */
  @org.eclipse.wst.jsdt.chromium.internal.protocolparser.JsonOptionalField
  org.eclipse.wst.jsdt.chromium.internal.wip.protocol.input.network.CertificateValidationDetailsValue certificateValidationDetails();

}
