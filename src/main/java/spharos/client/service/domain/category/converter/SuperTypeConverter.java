package spharos.client.service.domain.category.converter;

import java.util.EnumSet;
import java.util.NoSuchElementException;

import jakarta.persistence.AttributeConverter;
import spharos.client.service.domain.category.enumType.ServiceSuperCategoryType;

public class SuperTypeConverter implements AttributeConverter<ServiceSuperCategoryType, String> {

    @Override
    public String convertToDatabaseColumn(ServiceSuperCategoryType attribute) {
        return attribute.getKey();
    }

    @Override
    public ServiceSuperCategoryType convertToEntityAttribute(String dbData) {
        return EnumSet.allOf(ServiceSuperCategoryType.class).stream()
                .filter(c -> c.getKey().equals(dbData))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("서비스 타입이 잘못되었습니다."));
    }

}

