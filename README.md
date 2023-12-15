# Back-end system of an electronics store with checkout-system
### Tech stack used:
- Spring Boot
- Rest API
- H2 in-memory Database
---
### Testing tech stack used:
- JUnit (for testing service methods)
- Rest Assured (for testing Rest endpoints)
---
### Quick start:
- To start the program, go into the project folder and use the command line `mvn spring-boot:run`
- To stop the program, just Ctrl-C
- To run all tests, go into the project folder and use the command line `mvn test`
---
### Rest endpoint features:
- Adding admin/customer users
- Adding/removing products into the system (admin feature only)
- Adding/removing discounts (admin feature only)
- Adding/removing discounts to products (admin feature only)
- Adding/removing products to shopping basket (customer feature only)
- Checkout and get total price in basket (customer feature only)
- Pagination implemented on any lists received (such as for getting all products in basket or in general)
