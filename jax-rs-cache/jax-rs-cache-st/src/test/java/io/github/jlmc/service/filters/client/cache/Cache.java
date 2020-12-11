package io.github.jlmc.service.filters.client.cache;

import javax.ws.rs.client.ClientResponseContext;
import java.net.URI;
import java.util.concurrent.ConcurrentHashMap;

public class Cache {

    private ConcurrentHashMap<URI, CacheEntry> map = new ConcurrentHashMap<>();

    public CacheEntry getEntry(URI uri) {
        return map.get(uri);
    }

    public void setResponse(ClientResponseContext response, URI uri) {
        if (map.containsKey(uri) && !map.get(uri).isExpired()) {
            return;
        }

        CacheEntry cacheEntry = new CacheEntry();
        cacheEntry.updateCacheHeaders(response);

        map.put(uri, cacheEntry);
    }
}
