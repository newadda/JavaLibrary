package org.onecell.common.eximport.excel;

import lombok.Getter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

import java.util.HashMap;
import java.util.Map;

@Getter
public class ExcelHeaderReader {
    private Map<String,Integer> realHeaderInfo=new HashMap<>();
    private Integer baseCellNum;

    public ExcelHeaderReader(Row row) {
        read(row);
    }

    private void read(Row row){
        final short firstCellNum = row.getFirstCellNum();
        final short lastCellNum = row.getLastCellNum();

        baseCellNum = (int)firstCellNum;

        for(int i=firstCellNum;i<=lastCellNum;i++)
        {
            final Cell cell = row.getCell(i);
            if(cell==null || cell.getCellType().equals(CellType.BLANK))
            {
                continue;
            }

            final String stringCellValue = cell.getStringCellValue();
            realHeaderInfo.put(stringCellValue,i);
        }
    }

    public Integer getCellNumber(String headerName)
    {
        return realHeaderInfo.get(headerName);
    }


    public Integer getCellNumber(Integer offset)
    {
        return baseCellNum+offset;
    }




}
