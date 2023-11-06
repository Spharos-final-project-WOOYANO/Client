package spharos.client.service.domain;

import jakarta.persistence.*;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import spharos.client.service.domain.ServiceCategoryEnum.ServiceBaseCategoryType;
import spharos.client.service.domain.ServiceCategoryEnum.ServiceSubCategoryType;
import spharos.client.service.domain.ServiceCategoryEnum.ServiceSuperCatogoryType;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "service_category")
@ToString
@Getter
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false,length = 15,name = "super_category")
    private ServiceSuperCatogoryType superCategory;
    @Column(nullable = false,length = 15,name = "base_category")
    private ServiceBaseCategoryType baseCategory;
    @Column(nullable = false,length = 15,name = "sub_category")
    private ServiceSubCategoryType subCategory;
    public Category(ServiceSuperCatogoryType superCategory,
                    ServiceBaseCategoryType baseCategory,
                    ServiceSubCategoryType subCategory) {
        this.superCategory = superCategory;
        this.baseCategory = baseCategory;
        this.subCategory = subCategory;
    }//정적 팩토리 메서드
    public static Category createCategory(ServiceSuperCatogoryType superCategory,
                                          ServiceBaseCategoryType baseCategory,
                                          ServiceSubCategoryType subCategory){
        return new Category(superCategory,baseCategory,subCategory);
    }





}
