package security;

import app.UserAccount;
import com.yahoo.elide.security.ChangeSpec;
import com.yahoo.elide.security.RequestScope;
import com.yahoo.elide.security.User;
import com.yahoo.elide.security.checks.OperationCheck;
import domain.OwnedEntity;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
public class Checks {

    public static class AccessesSelf extends OperationCheck<domain.User> {

        @Override
        public boolean ok(domain.User user, RequestScope requestScope, Optional<ChangeSpec> optional) {
            User currentUser = requestScope.getUser();
            if (currentUser != null && currentUser.getOpaqueUser() != null) {
                UserAccount acc = (UserAccount) currentUser.getOpaqueUser();
                return acc.getName().equals(user.email);
            }
            return false;
        }
    }

    public static class OwnedEntityChecks extends OperationCheck<OwnedEntity> {

        @Override
        public boolean ok(OwnedEntity ownedEntity, RequestScope requestScope, Optional<ChangeSpec> optional) {
            User user = requestScope.getUser();
            try {
                UserAccount account = (UserAccount) user.getOpaqueUser();
                return ownedEntity.getOwnerEntity().email.equals(account.getName());
            } catch (NullPointerException npe) {

            }

            return false;
        }
    }
}
