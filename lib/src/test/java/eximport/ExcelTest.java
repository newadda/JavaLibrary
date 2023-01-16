package eximport;

import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.Test;
import org.onecell.common.eximport.base.HeaderInfo;
import org.onecell.common.eximport.excel.ExcelReader;
import org.onecell.common.eximport.excel.SimpleExcelHeaderInfo;
import org.onecell.common.misc.wrapper.StringWrapper;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

public class ExcelTest {
    @Setter
    @Getter
    public static class TestDto{
        private String name;
        private LocalDateTime dateTime;
        private Double value;

    }




    @Test
    public void readtest() throws FileNotFoundException {
        String filePath = "C:\\Users\\shh\\Desktop\\하수지능화 테스트\\Excel\\test01.xlsx";
        FileInputStream inp= new FileInputStream(filePath);


        HeaderInfo NAME = new HeaderInfo("이름",0);




        List<SimpleExcelHeaderInfo> excelReadInfos = new LinkedList<SimpleExcelHeaderInfo>(){{
            add(new SimpleExcelHeaderInfo<>(NAME, null, new StringWrapper(), TestDto::setName));


        }};

        ExcelReader<TestDto> a = new ExcelReader<>(0, true,ExcelReader.Mode.HAEDERNAME,excelReadInfos);

        final List<TestDto> read = a.read(inp, TestDto.class);


    }
}
