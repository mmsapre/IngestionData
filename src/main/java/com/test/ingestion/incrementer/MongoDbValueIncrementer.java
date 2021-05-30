package com.test.ingestion.incrementer;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

@Component
public class MongoDbValueIncrementer implements ValueIncrementer
{
    /** The MongoDB database */
    private DB db;

    /** The name of the sequence/table containing the sequence */
    private String incrementerName;

    /** The length to which a string result should be pre-pended with zeroes */
    private int paddingLength = 0;

    public MongoDbValueIncrementer()
    {
    }

    public MongoDbValueIncrementer( DB db, String incrementerName )
    {
        this.db = db;
        this.incrementerName = incrementerName;
    }


    public DB getDb() {
        return db;
    }

    public void setDb(DB db) {
        this.db = db;
    }

    public String getIncrementerName()
    {
        return incrementerName;
    }

    public void setIncrementerName( String incrementerName )
    {
        this.incrementerName = incrementerName;
    }

    public int getPaddingLength()
    {
        return paddingLength;
    }

    public void setPaddingLength( int paddingLength )
    {
        this.paddingLength = paddingLength;
    }

    @Override
    public int nextIntValue() throws DataAccessException
    {
        return (int) getNextKey();
    }

    @Override
    public long nextLongValue() throws DataAccessException
    {
        return getNextKey();
    }

    @Override
    public String nextStringValue() throws DataAccessException
    {
        String s = Long.toString(getNextKey());
        int len = s.length();
        if( len < this.paddingLength )
        {
            StringBuilder sb = new StringBuilder(this.paddingLength);
            for( int i = 0; i < this.paddingLength - len; i++ )
            {
                sb.append('0');
            }
            sb.append(s);
            s = sb.toString();
        }
        return s;
    }

    protected long getNextKey()
    {
        DBCollection collection = (DBCollection) db.getCollection(incrementerName);
        BasicDBObject sequence = new BasicDBObject();
        collection.update(sequence, new BasicDBObject("$inc", new BasicDBObject("value", 1L)), true, false);
        return (Long) collection.findOne(sequence).get("value");
    }
}
