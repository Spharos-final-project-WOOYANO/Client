package spharos.client.service.domain.ServiceCategoryEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import spharos.client.global.common.domain.BaseEnum;

@Getter
@AllArgsConstructor
//Category테이블 중분류 필드 생성을 위한 enum클래스
public enum ServiceBaseCategoryType implements BaseEnum<String, String>{

//    NOT_APPLICABLE(0,"NOT_APPLICABLE"),
//    ↑ 0 번은 해당하는 경우가 없는거같아서 주석처리하였습니다.

    HOUSE_KEEPER("HOUSE-KEEPER","house-keeper"),
    MOVING("MOVING-CLEAN","moving-clean"),
    OFFICE("OFFICE-CLEAN","office-clean"),
    ELECTRONICS("ELECTRONICS-CLEAN","electronics-clean");

    private final String key;
    private final String value;
}
