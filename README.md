## Installation

- `mvn clean install package`  for **elide standalone**
- `npm install` in `src/main/resources/static` for static assets
- Also you need an nginx server to proxy requests
- [Nginx](nginx.conf) serves static files & behaves like proxy for `api` requests to prevent CORS issues


### Running
- `./n.sh b` from start nginx
- `./n.sh e` from stop nginx

#### Further Info
For simplicity & to depend on json-api, client side libs
- `@holidayextras/jsonapi-client`

- `angular-schema-form`

are included.
