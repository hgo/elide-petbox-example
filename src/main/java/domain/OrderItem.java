package domain;

import com.yahoo.elide.annotation.ComputedAttribute;
import com.yahoo.elide.annotation.Include;
import com.yahoo.elide.annotation.ReadPermission;

import javax.persistence.*;
import javax.validation.constraints.Min;

@Entity
@Include
public class OrderItem {

    @Id
    @GeneratedValue
    public Long id;


    @ManyToOne(optional = false)
    @ReadPermission(expression = "none users")
    public Product product;

    @Min(1)
    public int count;

    @ComputedAttribute
    @Transient
    public ProductMetadata getDetails() {
        return new ProductMetadata(product.id, product.name, product.unitPrice);
    }

}
