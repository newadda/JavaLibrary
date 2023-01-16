package org.onecell.common.eximport.excel;

import lombok.Getter;
import lombok.Setter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;
import org.onecell.common.eximport.base.HeaderInfo;

import java.util.function.BiConsumer;

@Getter
@Setter
public class ExcelReadInfo<D> extends ExcelHeaderInfo{


   private BiConsumer<Cell,D> consumer;

    public ExcelReadInfo(HeaderInfo headerInfo, BiConsumer<Workbook, Cell> cellStyle, BiConsumer<Cell, D> consumer) {
        super(headerInfo, cellStyle);
        this.consumer = consumer;
    }
}
