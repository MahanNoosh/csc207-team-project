package healthz.tut0301.group1.dataaccess;
import java.time.Instant; import java.util.Set;
/** DataAccessInterface: token issuing boundary (JWT or anything). */
public interface TokenIssuer {
  String issueAccessToken(String userId, Set<String> roles, Instant exp);
  String issueRefreshToken(String userId, Instant exp);
}
