package spharos.client.service.domain.converter;

import java.util.EnumSet;
import java.util.NoSuchElementException;
import jakarta.persistence.AttributeConverter;
import spharos.client.service.domain.ServiceCategoryEnum.ServiceBaseCategoryType;

public class BaseTypeConverter implements AttributeConverter<ServiceBaseCategoryType, String> {

    @Override
    public String convertToDatabaseColumn(ServiceBaseCategoryType attribute) {
        return attribute.getKey();
    }

    @Override
    public ServiceBaseCategoryType convertToEntityAttribute(String dbData) {
        return EnumSet.allOf(ServiceBaseCategoryType.class).stream()
                .filter(c -> c.getKey().equals(dbData))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("서비스 타입이 잘못되었습니다."));
    }

}

