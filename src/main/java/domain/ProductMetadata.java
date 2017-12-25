package domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Value;

@Value
@Getter
@EqualsAndHashCode
public class ProductMetadata {

    private final Long id;
    private final String name;
    private final float unitPrice;
}
