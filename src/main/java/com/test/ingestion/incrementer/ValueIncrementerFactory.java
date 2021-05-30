package com.test.ingestion.incrementer;

public interface ValueIncrementerFactory
{
    ValueIncrementer getIncrementer( String incrementerName );
}
