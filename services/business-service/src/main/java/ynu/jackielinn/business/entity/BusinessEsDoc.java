package ynu.jackielinn.business.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Data
@Document(indexName = "elm-business")
public class BusinessEsDoc {
    @Id
    private Long businessId;
    private String businessName;
    private String businessExplain;
    private String businessImg;
    private Double startPrice;
    private Double deliveryPrice;
}
