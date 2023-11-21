package spharos.client.service.domain.category;

import jakarta.persistence.*;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import spharos.client.service.domain.category.enumType.ServiceBaseCategoryType;
import spharos.client.service.domain.category.enumType.ServiceSubCategoryType;
import spharos.client.service.domain.category.enumType.ServiceSuperCategoryType;
import spharos.client.service.domain.category.converter.BaseTypeConverter;
import spharos.client.service.domain.category.converter.SubTypeConverter;
import spharos.client.service.domain.category.converter.SuperTypeConverter;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Convert(converter = SuperTypeConverter.class)
    private ServiceSuperCategoryType superCategory;

    @Column(nullable = false)
    @Convert(converter = BaseTypeConverter.class)
    private ServiceBaseCategoryType baseCategory;

    @Convert(converter = SubTypeConverter.class)
    private ServiceSubCategoryType subCategory;

    public Category(ServiceSuperCategoryType superCategory,
                    ServiceBaseCategoryType baseCategory,
                    ServiceSubCategoryType subCategory) {
        this.superCategory = superCategory;
        this.baseCategory = baseCategory;
        this.subCategory = subCategory;
    }//정적 팩토리 메서드

    public static Category createCategory(ServiceSuperCategoryType superCategory,
                                          ServiceBaseCategoryType baseCategory,
                                          ServiceSubCategoryType subCategory){
        return new Category(superCategory,baseCategory,subCategory);
    }





}
