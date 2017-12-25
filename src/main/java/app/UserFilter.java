package app;

import com.yahoo.elide.core.DataStore;
import com.yahoo.elide.core.DataStoreTransaction;
import com.yahoo.elide.datastores.hibernate5.HibernateStore;
import domain.Role;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.SecurityContext;
import java.io.IOException;
import java.security.Principal;
import java.util.Base64;

@Slf4j
public class UserFilter implements ContainerRequestFilter {

    @Inject
    DataStore dataStore;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String header = requestContext.getHeaderString("Authorization");
        try {
            String[] splat = new String(Base64.getDecoder().decode(header.split("Basic ")[1])).split(":");
            HibernateStore store = (HibernateStore) dataStore;
            log.info("ds = " + dataStore);
            DataStoreTransaction ts = dataStore.beginTransaction();
            Object result =store.getSession()
                    .createQuery("select id,password from User where email = :email")
                    .setParameter("email", splat[0])
                    .uniqueResult();
            ts.commit(null);
            if (result != null) {
                Object[] row = (Object[]) result;
                Long id = (Long) row[0];
                String pass = (String) row[1];
                if (splat[1].equals(pass)) {
                    requestContext.setSecurityContext(new AppContext(new UserAccount(splat[0], Role.Admin)));
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class AppContext implements SecurityContext {

        private final UserAccount userAccount;

        public AppContext(UserAccount userAccount) {
            this.userAccount = userAccount;
        }

        @Override
        public Principal getUserPrincipal() {
            return userAccount;
        }

        @Override
        public boolean isUserInRole(String s) {
            return false;
        }

        @Override
        public boolean isSecure() {
            return false;
        }

        @Override
        public String getAuthenticationScheme() {
            return "custom";
        }
    }
}
