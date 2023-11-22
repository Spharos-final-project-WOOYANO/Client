package spharos.client.worker.domain.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import spharos.client.worker.domain.enumType.DayOfWeekType;

import java.util.EnumSet;
import java.util.NoSuchElementException;

@Converter(autoApply = true)
public class DayOfWeekConverter implements AttributeConverter<DayOfWeekType,Integer>{
    @Override
    public Integer convertToDatabaseColumn(DayOfWeekType attribute) {
        return attribute.getKey();
    }

    public DayOfWeekType convertToEntityAttribute(Integer dbData) {
        return EnumSet.allOf(DayOfWeekType.class).stream()
                .filter(c -> c.getKey().equals(dbData))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("요일 타입이 잘못되었습니다."));
    }
}