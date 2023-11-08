package spharos.client.service.domain.ServiceCategoryEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import spharos.client.global.common.domain.BaseEnum;

@Getter
@AllArgsConstructor
public enum ServiceSubCategoryType implements BaseEnum<String, String> {
        AIR_CONDITIONER("AIR_CONDITIONER","에어컨"),
        AIR_PURIFIER("AIR_PURIFIER","공기청정기"),
        REFRIGERATOR("REFRIGERATOR","냉장고"),
        WASHING_MACHINE("WASHING_MACHINE","세탁기"),
        TV("TV","TV"),
        PC("PC","PC"),
        FAN("FAN","선풍기"),
        ETC("ETC","기타");
        private final String key;
        private final String value;
}




