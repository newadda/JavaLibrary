package org.onecell.common.eximport.excel;

import lombok.Getter;
import lombok.Setter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;
import org.onecell.common.eximport.base.HeaderInfo;

import java.util.function.BiConsumer;

@Getter
@Setter
public class ExcelHeaderInfo {
    private HeaderInfo headerInfo;
    BiConsumer<Workbook, Cell> cellStyle;

    public ExcelHeaderInfo(HeaderInfo headerInfo, BiConsumer<Workbook, Cell> cellStyle) {
        this.headerInfo = headerInfo;
        if(cellStyle==null)
        {  this.cellStyle = new BiConsumer<Workbook, Cell>() {
            @Override
            public void accept(Workbook workbook, Cell cell) {

            }
        };
        }else {
            this.cellStyle = cellStyle;
        }

    }
}
