package spharos.client.service.domain.ServiceCategoryEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import spharos.client.global.common.domain.BaseEnum;

@Getter
@AllArgsConstructor
public enum ServiceSubCategoryType implements BaseEnum<Integer, String> {

        AIR_CONDITIONER(1,"에어컨"),
        AIR_PURIFIER(2,"공기청정기"),
        REFRIGERATOR(3,"냉장고"),
        WASHING_MACHINE(4,"세탁기"),
        TV(5,"TV"),
        PC(6,"PC"),
        FAN(7,"선풍기");

        private final Integer key;
        private final String value;
}




