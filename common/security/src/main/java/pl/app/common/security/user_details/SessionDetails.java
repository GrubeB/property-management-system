package pl.app.common.security.user_details;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@Getter
@AllArgsConstructor
public class SessionDetails {
    private Map<String, String> tokens;
}
