package domain;

import com.yahoo.elide.annotation.Include;

import javax.persistence.*;
import javax.validation.constraints.Min;

@Entity
@Include
public class OrderItem {

    @Id
    @GeneratedValue
    public Long id;


    @ManyToOne(optional = false)
    public Product product;

    @Min(1)
    public int count;

}
