package spharos.client.service.domain.ServiceCategoryEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import spharos.client.global.common.domain.BaseEnum;

@Getter
@AllArgsConstructor
//Category테이블 중분류 필드 생성을 위한 enum클래스
public enum ServiceBaseCategoryType implements BaseEnum<Integer, String>{
    HOUSE_KEEPER(1,"house-keeper"),
    MOVING(2,"moving-clean"),
    OFFICE(3,"office-clean"),
    ELECTRONICS(4,"electronics-clean");

    private final Integer key;
    private final String value;

}
