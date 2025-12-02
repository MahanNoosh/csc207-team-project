package healthz.tut0301.group1.dataaccess;
import java.time.Instant;
/** DataAccessInterface: time source. */
public interface ClockGateway { Instant now(); }
