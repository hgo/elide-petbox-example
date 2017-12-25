package app;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.yahoo.elide.resources.DefaultOpaqueUserFunction;
import com.yahoo.elide.security.checks.Check;
import com.yahoo.elide.security.checks.prefab.Role;
import com.yahoo.elide.standalone.config.ElideStandaloneSettings;
import domain.Product;
import lombok.extern.slf4j.Slf4j;
import security.Checks;

import java.util.List;
import java.util.Map;

@Slf4j
public class Settings implements ElideStandaloneSettings {


    @Override
    public List<Class<?>> getFilters() {
        return ImmutableList.of(UserFilter.class);
    }

    @Override
    public Map<String, Class<? extends Check>> getCheckMappings() {
        return ImmutableMap
                .<String, Class<? extends Check>>builder()
                .put("user owns entity", Checks.OwnedEntityChecks.class)
                .put("user is accessing self", Checks.AccessesSelf.class)
                .put("all users", Role.ALL.class)
                .put("none users", Role.NONE.class)
                .build();
    }

    @Override
    public DefaultOpaqueUserFunction getUserExtractionFunction() {
        return securityContext -> {
            return securityContext.getUserPrincipal();
        };
    }

    public String getHibernate5ConfigPath() {
        return "./settings/hibernate.cfg.xml";
    }

    public String getModelPackageName() {
        return Product.class.getPackage().getName();
    }

}
