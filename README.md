# restaurantServiceSystem
Restaurant service system is application based on REST technology (requests and responses in JSON format).
Users have several roles: manager, client, employee(two kinds of employees: waiter and cook).
Manager can upload restaurant data(menu and tables), change properties of other users(deposit, current status, etc.).
Client can make an order(choosing menu items, booking table), view his order history and order details.
Waiter and cook are virtual. they have the load factor(0-5), if it les than 5(means the number of clients served simultaniously), employee can serve the order.

Stack of technologies used in the project: 
Spring core, Spring Security, Spring Data, Spring MVC, Spring Boot;
Hibernate, 
PostgreSQL,
Maven.
