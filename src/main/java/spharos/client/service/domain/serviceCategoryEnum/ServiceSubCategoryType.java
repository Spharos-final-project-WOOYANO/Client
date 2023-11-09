package spharos.client.service.domain.serviceCategoryEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import spharos.client.global.common.domain.BaseEnum;

@Getter
@AllArgsConstructor
public enum ServiceSubCategoryType implements BaseEnum<String, String> {
        AIR_CONDITIONER("1","AIR_CONDITIONER"),
        AIR_PURIFIER("2","AIR_PURIFIER"),
        REFRIGERATOR("3","REFRIGERATOR"),
        WASHING_MACHINE("4","WASHING_MACHINE"),
        TV("5","TV"),
        PC("6","PC"),
        FAN("7","FAN"),
        ETC("8","ETC");//기타
        private final String key;
        private final String value;
}




