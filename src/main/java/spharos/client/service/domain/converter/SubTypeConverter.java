package spharos.client.service.domain.converter;

import jakarta.persistence.AttributeConverter;
import spharos.client.service.domain.serviceCategoryEnum.ServiceSubCategoryType;

import java.util.EnumSet;
import java.util.NoSuchElementException;

public class SubTypeConverter implements AttributeConverter<ServiceSubCategoryType, String> {

    @Override
    public String convertToDatabaseColumn(ServiceSubCategoryType attribute) {
        return attribute.getKey();
    }

    @Override
    public ServiceSubCategoryType convertToEntityAttribute(String dbData) {
        return EnumSet.allOf(ServiceSubCategoryType.class).stream()
                .filter(c -> c.getKey().equals(dbData))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("소분류 서비스 타입이 잘못되었습니다."));
    }
}
