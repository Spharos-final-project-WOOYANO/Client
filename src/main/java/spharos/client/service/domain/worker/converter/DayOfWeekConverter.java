package spharos.client.service.domain.worker.converter;

import jakarta.persistence.AttributeConverter;
import spharos.client.service.domain.worker.enumType.DayOfWeekType;

import java.util.EnumSet;

public class DayOfWeekConverter implements AttributeConverter<DayOfWeekType,String>{
    @Override
    public String convertToDatabaseColumn(DayOfWeekType attribute) {
        return attribute.getKey();
    }

    @Override
    public DayOfWeekType convertToEntityAttribute(String dbData) {
        return EnumSet.allOf(DayOfWeekType.class).stream()
                .filter(c -> c.getKey().equals(dbData))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("요일 타입이 잘못되었습니다."));
    }
}

