package org.onecell.common.eximport.excel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;

public class CellUtil {
    public static Object getValue(Cell cell)
    {
        if(cell==null)
        {
            return null;
        }

        final CellType cellType = cell.getCellType();
        switch (cellType)
        {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return cell.getNumericCellValue();
            default:
                return null;
        }
    }


}
