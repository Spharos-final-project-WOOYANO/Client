package spharos.client.service.domain.ServiceCategoryEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import spharos.client.global.common.domain.BaseEnum;

@Getter
@AllArgsConstructor
//Category테이블 대분류 필드 생성을 위한 enum클래스
public enum ServiceSuperCategoryType implements BaseEnum<String, String> {
    CLEANING("CLEANING", "CLEANING");
    private final String key;
    private final String value;
}
