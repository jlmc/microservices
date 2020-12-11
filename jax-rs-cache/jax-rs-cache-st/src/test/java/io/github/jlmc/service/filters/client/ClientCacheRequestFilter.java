package io.github.jlmc.service.filters.client;

import io.github.jlmc.service.filters.client.cache.Cache;
import io.github.jlmc.service.filters.client.cache.CacheEntry;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;


/**
 * Let’s use these two interfaces to implement a client-side cache. We want this cache to behave like a browser’s cache.
 * This means we want it to honor the Cache-Control semantics.
 * We want cache entries to expire based on the metadata within Cache-Control response headers.
 * We want to perform conditional GETs if the client is requesting an expired cache entry.
 */
public class ClientCacheRequestFilter implements ClientRequestFilter {

    private final Cache cache;

    public ClientCacheRequestFilter(Cache cache) {
        this.cache = cache;
    }

    @Override
    public void filter(ClientRequestContext ctx) {
        if (!HttpMethod.GET.equalsIgnoreCase(ctx.getMethod())) {
            return;
        }

        CacheEntry entry = cache.getEntry(ctx.getUri());
        if (entry == null) {
            return;
        }

        if (!entry.isExpired()) {
            ctx.abortWith(entry.toResponse());
            return;
        }

        String etag = entry.getETagHeader();
        String lastModified = entry.getLastModified();

        if (etag != null) {
            ctx.getHeaders().putSingle("If-None-Match", etag);
        }

        if (lastModified != null) {
            ctx.getHeaders().putSingle("If-Modified-Since", lastModified);
        }
    }
}
