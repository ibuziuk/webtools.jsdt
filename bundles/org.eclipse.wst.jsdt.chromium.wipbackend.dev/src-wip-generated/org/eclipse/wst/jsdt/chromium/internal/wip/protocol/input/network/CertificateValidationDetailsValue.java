// Generated source.
// Generator: org.eclipse.wst.jsdt.chromium.internal.wip.tools.protocolgenerator.Generator
// Origin: https://chromium.googlesource.com/chromium/src/+/51.0.2704.79/third_party/WebKit/Source/devtools/protocol.json?format=TEXT@<unknown>

package org.eclipse.wst.jsdt.chromium.internal.wip.protocol.input.network;

/**
 Details about the validation status of a request's certificate.
 */
@org.eclipse.wst.jsdt.chromium.internal.protocolparser.JsonType
public interface CertificateValidationDetailsValue {
  /**
   The number of SCTs from unknown logs.
   */
  long numUnknownScts();

  /**
   The number of invalid SCTs.
   */
  long numInvalidScts();

  /**
   The number of valid SCTs.
   */
  long numValidScts();

}
