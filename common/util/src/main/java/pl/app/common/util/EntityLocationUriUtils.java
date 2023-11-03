package pl.app.common.util;

import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

public class EntityLocationUriUtils {
    public static URI createdEntityLocationURI(Object id, String url) {
        return UriComponentsBuilder
                .fromUriString(url)
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }

    public static Optional<Long> extractIdFromEntityLocationURI(String url) {
        try {
            String idString = url.substring(url.lastIndexOf('/') + 1);
            return Optional.of(Long.parseLong(idString));
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
