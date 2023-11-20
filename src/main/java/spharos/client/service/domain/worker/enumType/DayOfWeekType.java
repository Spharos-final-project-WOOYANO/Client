package spharos.client.service.domain.worker.enumType;

import lombok.Getter;
import spharos.client.global.common.domain.BaseEnum;
@Getter
public enum DayOfWeekType implements BaseEnum<Integer, String> {

    MONDAY(1,"MONDAY"),
    TUESDAY(2,"TUESDAY"),
    WEDNESDAY(3,"WEDNESDAY"),
    THURSDAY(4,"THURSDAY"),
    FRIDAY(5,"FRIDAY"),
    SATURDAY(6,"SATURDAY"),
    SUNDAY(7,"SUNDAY");

    private final Integer key;
    private final String value;

    DayOfWeekType(Integer key, String value) {
        this.key = key;
        this.value = value;
    }
}