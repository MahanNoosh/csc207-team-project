package tutcsc.group1.healthz.data_access;
import tutcsc.group1.healthz.entities.user.User; import java.util.Optional;
/** DataAccessInterface (right side): storage/retrieval of Users. */
public interface UserDataAccessInterface {
  Optional<User> findByEmail(String email);
  User save(User user);
}
