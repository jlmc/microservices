package io.github.jlmc.service.filters.client;

import io.github.jlmc.service.filters.client.cache.Cache;
import io.github.jlmc.service.filters.client.cache.CacheEntry;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientResponseContext;
import javax.ws.rs.client.ClientResponseFilter;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class ClientCacheResponseFilter implements ClientResponseFilter {

    private final Cache cache;

    public ClientCacheResponseFilter(Cache cache) {
        this.cache = cache;
    }

    @Override
    public void filter(ClientRequestContext request, ClientResponseContext response) throws IOException {
        if (!HttpMethod.GET.equalsIgnoreCase(request.getMethod())) {
            return;
        }

        if (response.getStatus() == 200) {
            cache.setResponse(response, request.getUri());
        } else if (response.getStatus() == 304) {
            CacheEntry entry = cache.getEntry(request.getUri());

            entry.updateCacheHeaders(response);
            response.getHeaders().clear();
            response.setStatus(200);

            response.getHeaders().putSingle("Content-Type", entry.getContentType());

            response.setEntityStream(new ByteArrayInputStream(entry.getContent()));
        }
    }
}
