package org.onecell.common.spring.converter;

import org.onecell.common.spring.dto.SortReqDto;
import org.onecell.common.spring.dto.SortType;
import org.springframework.core.convert.converter.Converter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringToSortConverter implements Converter<String, SortReqDto> {

    private static final String PATTERN= "^\\w+,(asc|desc)$";
    private static final String SPLIT= ",";

    @Override
    public SortReqDto convert(String source) {
        Pattern compile = Pattern.compile(PATTERN);
        Matcher matcher = compile.matcher(source);
        boolean b = matcher.find();
        if(b==false)
        {
            throw new IllegalArgumentException();
        }

        String[] split = source.split(SPLIT);

        SortReqDto sort = new SortReqDto();
        sort.setSortName(split[0]);
        sort.setSortType(SortType.valueOf(source.toUpperCase()));

        return sort;
    }
}
