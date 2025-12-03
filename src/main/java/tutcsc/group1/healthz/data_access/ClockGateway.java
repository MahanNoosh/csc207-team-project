package tutcsc.group1.healthz.data_access;
import java.time.Instant;
/** DataAccessInterface: time source. */
public interface ClockGateway { Instant now(); }
