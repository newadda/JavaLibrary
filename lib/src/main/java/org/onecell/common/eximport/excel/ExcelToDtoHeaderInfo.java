package org.onecell.common.eximport.excel;

import lombok.Getter;
import lombok.Setter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;
import org.onecell.common.eximport.base.HeaderInfo;

import java.util.function.BiConsumer;
import java.util.function.Function;

@Setter
@Getter

public class ExcelToDtoHeaderInfo<D> extends ExcelHeaderInfo{
    public Function< Cell,D> convert;

    public ExcelToDtoHeaderInfo(HeaderInfo headerInfo, BiConsumer<Workbook, Cell> cellStyle, Function<Cell, D> convert) {
        super(headerInfo, cellStyle);
        this.convert = convert;
    }
}
