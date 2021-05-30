package com.test.ingestion.service;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class InternalIdService {


    @Qualifier("internalIdCache")
    @Autowired
    private Map<String, String> internalIdMapCache;

    @Cacheable(value="securityCache")
    public String bankEquityId( final String securityId ) {
        String secId=internalIdMapCache.get(securityId);
        return secId;
    }

}
