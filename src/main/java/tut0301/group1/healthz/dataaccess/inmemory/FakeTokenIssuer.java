package tut0301.group1.healthz.dataaccess.inmemory;
import tut0301.group1.healthz.dataaccess.TokenIssuer; import java.time.Instant; import java.util.Set;
/** Demo-only token issuer: returns predictable strings. Replace in production. */
public class FakeTokenIssuer implements TokenIssuer {
  @Override public String issueAccessToken(String uid, Set<String> roles, Instant exp){ return "access("+uid+")"; }
  @Override public String issueRefreshToken(String uid, Instant exp){ return "refresh("+uid+")"; }
}
