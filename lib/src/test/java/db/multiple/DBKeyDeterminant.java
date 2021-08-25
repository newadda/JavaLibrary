package db.multiple;

import org.onecell.common.spring.multiple.DBKeyContext;

public class DBKeyDeterminant extends DBKeyContext {

    Long dbTotalCount ;

    public DBKeyDeterminant(Long dbTotalCount)
    {
        this.dbTotalCount = dbTotalCount;
    }

    public void userId(Long userid)
    {
        long mod = Math.floorMod(userid, dbTotalCount);
        this.setDbKey(String.valueOf(mod));
    }
}
