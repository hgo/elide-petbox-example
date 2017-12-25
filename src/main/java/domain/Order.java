package domain;

import com.yahoo.elide.annotation.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "orders")
@Include(rootLevel = true)
@UpdatePermission(expression = "user owns entity")
@ReadPermission(expression = "user owns entity")
@SharePermission(expression = "user owns entity")
public class Order implements OwnedEntity {

    @Id
    @GeneratedValue
    public Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    public Date created = new Date();

    @ManyToOne(optional = false)
    @JoinColumn
    public User owner;

    @OneToMany
    @JoinColumn(name = "order_id")
    public List<OrderItem> items = new ArrayList<>();

    @Override
    @Transient
    public User getOwnerEntity() {
        return this.owner;
    }

    @ComputedAttribute
    @Transient
    public List<String> getBucket() {
        return items.stream().map(orderItem -> orderItem.product.name).collect(Collectors.toList());
    }

}
