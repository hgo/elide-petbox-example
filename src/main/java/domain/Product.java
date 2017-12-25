package domain;

import com.yahoo.elide.annotation.Include;
import com.yahoo.elide.annotation.OnCreatePreCommit;
import com.yahoo.elide.annotation.SharePermission;
import com.yahoo.elide.core.RequestScope;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Include(rootLevel = true)
@Slf4j
@SharePermission(expression = "all users")
public class Product {

    @Id
    @GeneratedValue
    public Long id;

    @Column(nullable = false)
    public String name;
    @Column(nullable = false, scale = 2)
    public float unitPrice;
    @Column(nullable = false, scale = 2)
    public float unitWeight;

    @ElementCollection
    public List<String> pictures = new ArrayList<>();

    @OnCreatePreCommit
    public void onCreatePreCommit(RequestScope scope) {
        log.info("StringUtils.upperCase(this.name);");
        this.name = StringUtils.upperCase(this.name);
    }

    public int willBeStockInDays = 0;

}
