package fr.sparkit.starkoauthmicroservice.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;



@RestController
@RequestMapping("/api/auth/caches")
@CrossOrigin("*")
@Slf4j
public class CacheController {


    private final CacheManager cacheManager;

    @Autowired
    public CacheController(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }


    @GetMapping("clear-all-caches")
    @PreAuthorize("isAuthenticated()")
    public void clearAllCaches(){
        for (String name : cacheManager.getCacheNames()) {
            log.info("Cache name {} ",name);
            Objects.requireNonNull(cacheManager.getCache(name)).clear();
        }
    }

    @GetMapping("clear-cache-by-key")
    @PreAuthorize("isAuthenticated()")
    public void clearCacheByKey(@RequestParam String email){
        Cache cache = cacheManager.getCache("UserAuthoritiesCache");
        if(cache!=null){
            cache.evictIfPresent("UserAuthoritiesCache_"+email);
        }
    }
}
