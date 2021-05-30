package com.test.ingestion.incrementer;

public interface ValueIncrementer
{
    int nextIntValue();

    long nextLongValue();

    String nextStringValue();
}
