package at.michaelaltenburger.flatfinder.util;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;
import java.util.List;

@Converter
public class StringListConverter implements
        AttributeConverter<List<String>, String> {

    @Override
    public String convertToDatabaseColumn(List<String> strings) {
        return String.join(",", strings);
    }

    @Override
    public List<String> convertToEntityAttribute(String dbList) {
        if (dbList == null) {
            return null;
        }

        return Arrays.asList(dbList.split(","));
    }
}