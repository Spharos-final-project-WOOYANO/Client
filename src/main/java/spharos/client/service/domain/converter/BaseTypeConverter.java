package spharos.client.service.domain.converter;

import java.util.EnumSet;
import java.util.NoSuchElementException;
import jakarta.persistence.AttributeConverter;
import spharos.client.service.domain.serviceCategoryEnum.ServiceBaseCategoryType;

public class BaseTypeConverter implements AttributeConverter<ServiceBaseCategoryType, String> {

    @Override
    public String convertToDatabaseColumn(ServiceBaseCategoryType attribute) {
        return attribute.getKey();
    }

    @Override//DB에 저장되어 있는 데이터 값으로 현재 데이터를 변환함
    public ServiceBaseCategoryType convertToEntityAttribute(String requestData) {
        return EnumSet.allOf(ServiceBaseCategoryType.class).stream()
                //↓ Enum클래스에서 getKey로 모든 키를 가져온뒤 파라미터로 받아온 값과 일치하는 키를 찾는다.
                .filter(c -> c.getKey().equals(requestData))
                //↓ 첫 번째로 일치하는 키의 "값"을 가져옴
                .findFirst()
                //↓ 일치하는값이 없으면 예외발생
                .orElseThrow(() -> new NoSuchElementException("서비스 타입이 잘못되었습니다."));
    }

}

