package org.onecell.common.eximport.excel;

import lombok.Getter;
import lombok.Setter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;
import org.onecell.common.eximport.base.HeaderInfo;

import java.util.function.BiConsumer;
import java.util.function.Function;

@Getter
@Setter
public class DtoToExcelHeaderInfo<D> extends ExcelHeaderInfo{
    public Function<D, Cell> convert;

    public DtoToExcelHeaderInfo(HeaderInfo headerInfo, BiConsumer<Workbook, Cell> cellStyle, Function<D, Cell> convert) {
        super(headerInfo, cellStyle);
        this.convert = convert;
    }

}
