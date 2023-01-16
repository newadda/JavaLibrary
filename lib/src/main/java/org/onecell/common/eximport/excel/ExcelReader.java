package org.onecell.common.eximport.excel;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.onecell.common.eximport.base.DataReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

public class ExcelReader<D> implements DataReader<D> {

    public  enum Mode{
        HAEDERNAME,HAEDEROFFSET;

    }

    private int sheetIndex=0;
    private boolean existHeader=true;

    private Mode mode;



    private ExcelHeaderReader excelHeaderReader;

    private List<? extends ExcelReadInfo> excelReadInfos;

    public ExcelReader(int sheetIndex, boolean existHeader, Mode mode, List<? extends ExcelReadInfo> excelReadInfos) {
        this.sheetIndex = sheetIndex;
        this.existHeader = existHeader;
        this.mode = mode;
        this.excelReadInfos = excelReadInfos;
    }

    @Override
    public List<D> read(InputStream inputStream,Class<D> clazz) {

        return  process(inputStream,clazz);

    }


    public List<D> process(InputStream inputStream,Class<D> clazz)
    {
        try {
            final Workbook wb = WorkbookFactory.create(inputStream);
            final Sheet sheetAt = wb.getSheetAt(this.sheetIndex);

            final int firstRowNum = sheetAt.getFirstRowNum();
            final int lastRowNum = sheetAt.getLastRowNum();

            int startRowNum = firstRowNum;

            if(existHeader==true)
            {
                final Row row = sheetAt.getRow(firstRowNum);
                excelHeaderReader= new ExcelHeaderReader(row);
                startRowNum=startRowNum+1;
            }

            List<D> retDtos = new LinkedList<>();


            for(int i =startRowNum ; i<=lastRowNum;i++)
            {

                final Row row = sheetAt.getRow(i);
                final D d = clazz.newInstance();
                readCell(row,d);
                retDtos.add(d);

            }

            return retDtos;

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

    }


    private void readCell(Row row,D d)
    {

        excelReadInfos.stream().forEach(excelReadInfo -> {
             Integer cellNumber=null;
            if(mode.equals(Mode.HAEDERNAME))
            {
                cellNumber = excelHeaderReader.getCellNumber(excelReadInfo.getHeaderInfo().getName());

            }else {
                cellNumber = excelHeaderReader.getCellNumber(excelReadInfo.getHeaderInfo().getOffset());
            }

            excelReadInfo.getConsumer().accept(row.getCell(cellNumber),d);

        });


    }





}
