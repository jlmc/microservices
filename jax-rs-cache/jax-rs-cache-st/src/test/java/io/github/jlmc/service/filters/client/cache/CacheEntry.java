package io.github.jlmc.service.filters.client.cache;

import javax.ws.rs.client.ClientResponseContext;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;

public class CacheEntry {

    private Instant date;
    private EntityTag eTagHeader;
    private Date lastModified;
    private byte[] content = {};
    private MediaType mediaType;
    private CacheControl cacheControl;
    private Map<String, String> headers = Map.of();

    public boolean isExpired() {
        if (cacheControl == null) {
            return true;
        }

        return date.plusSeconds(cacheControl.getMaxAge()).isBefore(Instant.now());
    }

    public byte[] getContent() {
        return content;
    }

    public String getContentType() {
        if (mediaType == null) {
            return "*/*";
        }
        return this.mediaType.toString();
    }

    public String getETagHeader() {
        if (this.eTagHeader == null) {
            return null;
        }

        return this.eTagHeader.getValue();
    }

    public String getLastModified() {
        if (this.lastModified == null) {
            return null;
        }

        return new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz").format(this.lastModified);
    }

    public void updateCacheHeaders(ClientResponseContext response) {
        this.headers = extractAllHeaders(response);
        CacheControl cacheControl = getCacheControl(response).orElse(null);
        this.date = Instant.ofEpochMilli(response.getDate().getTime());
        this.lastModified = response.getLastModified();
        this.eTagHeader = response.getEntityTag();
        this.mediaType = response.getMediaType();
        this.content = getContent(response);
        this.cacheControl = cacheControl;
    }

    private Map<String, String> extractAllHeaders(ClientResponseContext response) {
        Map<String, String> ol = new HashMap<>();
        Set<String> headerNames = response.getHeaders().keySet();
        for (String k : headerNames) {
            String headerString = response.getHeaderString(k);
            ol.put(k, headerString);
        }
        return ol;
    }

    private byte[] getContent(ClientResponseContext response) {
        try {
            if (response.getEntityStream() == null) {
                return null;
            }

            InputStream entityStream = response.getEntityStream();

            byte[] bytes = entityStream.readAllBytes();

            response.setEntityStream(new ByteArrayInputStream(bytes));

            return bytes;


        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private Optional<CacheControl> getCacheControl(ClientResponseContext request) {
        List<String> cacheControlHeaderValue = request.getHeaders().getOrDefault("Cache-Control", List.of());

        return cacheControlHeaderValue.stream()
                .findFirst()
                .map(CacheControl::valueOf);
    }

    public Map<String, String> getHeaders() {
        if (headers == null) {
            return Collections.emptyMap();
        }

        return Map.copyOf(this.headers);
    }

    public Response toResponse() {
        Response.ResponseBuilder builder = Response.ok(new ByteArrayInputStream(getContent()));
        getHeaders().forEach(builder::header);

        return builder.build();
    }
}
