package org.onecell.common.eximport.excel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;
import org.onecell.common.eximport.base.HeaderInfo;
import org.onecell.common.misc.wrapper.Wrapper;

import java.util.function.BiConsumer;

public class SimpleExcelHeaderInfo<D,V> extends ExcelReadInfo<D>{
    private Wrapper<V> wrapper;
    private BiConsumer<D,V> setter;

    public SimpleExcelHeaderInfo(HeaderInfo headerInfo, BiConsumer<Workbook, Cell> cellStyle, Wrapper<V> wrapper, BiConsumer<D, V> setter) {
        super(headerInfo, cellStyle, new BiConsumer<Cell, D>() {
            @Override
            public void accept(Cell cell, D d) {
                final Object cellValue = CellUtil.getValue(cell);
                final V wrapValue = wrapper.wrap(cellValue);
                setter.accept(d,wrapValue);
            }
        });
        this.wrapper = wrapper;
        this.setter = setter;
    }


}
