# Oauth2-resource-server

  Sample project for a basic support of a Oauth2 Server Resource.

## Features : 
  * Support a JWK key set exposed ( see config : security.oauth2.resource.jwt.key-set-uri)
  * Protect Http verb by a scope ( ex scope "get" for GET verb ) or a scope admin for all verbs
  * Protect the loggers actuator with the "admin" scope
  * Swagger configured to use a client-credentials grant ( see config : security.oauth2.resource.jwt.token-uri)
  * Test api at /api/hello

## Configuration

See application.yml.
  
~~~yml
security:
  oauth2:
    resource:
      id: test-autorisation-api
      jwt:
        token-uri: https://<server>/oauth/token
        key-set-uri: https://<server>/oauth/token_keys
~~~


### Where :
 * security.oauth2.resource.jwt.key-set-uri ( endpoint exposing the signature keys )
 * security.oauth2.resource.jwt.token-uri ( endpoint used to get token, used by swagger )
 * security.oauth2.resource.id is the resource id ( audience in the token )
