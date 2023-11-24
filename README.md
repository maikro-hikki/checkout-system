# Back-end system of a store with checkout-system
- To start the app, go into the project folder and use the command line `mvn spring-boot:run`
- To run all tests, go into the project folder and use the command line `mvn test`
---
### Tech stack used:
- Spring Boot
- Rest API
- H2 in-memory Database
---
### Testing tech stack used:
- JUnit (for testing service methods)
- Rest Assured (for testing Rest endpoints)
---
### Rest endpoint features:
- Adding admin/customer users
- Adding/removing products into the system (admin feature only)
- Adding/removing discounts (admin feature only)
- Adding/removing discounts to on products (admin feature only)
- Adding/removing products to shopping basket (customer feature only)
- Checkout and get total price in basket (customer feature only)
- Pagination implemented on any lists received (such as for getting all products in basket or in general)
