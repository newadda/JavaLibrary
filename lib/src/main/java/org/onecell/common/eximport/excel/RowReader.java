package org.onecell.common.eximport.excel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.util.HashMap;
import java.util.Map;

public  class RowReader {
    private Row row;
    private Map<String, Integer> realHeader = new HashMap<>();

    public RowReader(Row row, Map<String, Integer> realHeader) {
        this.row = row;
        this.realHeader = realHeader;
    }

    // 오프셋으로 읽기
    public  Cell getCellByColNumber(int colNumber)
    {
       return this.row.getCell(colNumber, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
    }

    public  Cell getCellByName(String headerName)
    {
        final Integer integer = realHeader.get(headerName);
        if(integer==null)
        {
            return null;
        }
        return getCellByColNumber(integer);
    }

}
