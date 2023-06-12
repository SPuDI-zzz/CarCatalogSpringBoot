# CarCatalogSpringBoot

1. Create DB "car_catalog_spring"
2. Change in application.yml username and password for the user who connects to the database
3. Start app
4. Go to http://localhost:8081/swagger-ui/

## Authorize

1. Create profile in profile-controller: (POST) /api/profiles
2. Login in auth-controller: (POST) /api/auth/login
3. Copy accessToken from Response body
4. Click on Authorize in the upper right corner
5. Insert: Bearer yourAccessToken
