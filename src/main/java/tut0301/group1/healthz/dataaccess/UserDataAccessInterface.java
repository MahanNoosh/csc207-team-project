package tut0301.group1.healthz.dataaccess;
import tut0301.group1.healthz.entities.user.User; import java.util.Optional;
/** DataAccessInterface (right side): storage/retrieval of Users. */
public interface UserDataAccessInterface {
  Optional<User> findByEmail(String email);
  User save(User user);
}
