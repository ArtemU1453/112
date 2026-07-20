Keycloak realm export for the 112 platform.

Import into Keycloak (containerized Keycloak distributions):

Using Keycloak v20+ (quay.io/keycloak/keycloak):

```bash
# start keycloak once, then import
docker run --rm -v $(pwd)/realm-112-export.json:/tmp/realm.json \
  quay.io/keycloak/keycloak:latest import --file /tmp/realm.json
```

Or via admin console: Realm -> Import -> upload `realm-112-export.json`.

After import, create clients and map service roles as needed. Use `gateway-client` for the gateway OIDC client.

Example service role mapping:
- `gateway-client` should expose roles like `gateway_admin`, `gateway_dispatcher`, and `gateway_responder`.
- `auth-service-client` should expose `auth_admin` and `auth_user`.
- Use `realmRoles` such as `admin`, `dispatcher`, `responder` and map them to client roles in the client scope.

Recommended mapping:
- `admin` → `gateway_admin`, `auth_admin`
- `dispatcher` → `gateway_dispatcher`, `auth_user`
- `responder` → `gateway_responder`, `auth_user`
