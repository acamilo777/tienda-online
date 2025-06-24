# 🛒 Tienda Online - Microservicios
Proyecto de ejemplo para la gestión de una tienda utilizando arquitectura de microservicios con Spring Boot y MySQL.
Cada microservicio debe ejecutarse por separado con su correspondiente application.properties. Se debe tener MySQL activo y las bases de datos creadas (usuariosdb, productosdb, pedidosdb).




USUARIOS:

GET http://localhost:8081/usuarios
GET http://localhost:8081/usuarios/id
POST http://localhost:8081/usuarios
DELETE http://localhost:8081/usuarios/id

{
  "nombre": "Cristiano Ronaldo",
  "email": "cristiano@penaldo.cl"
}


PRODUCTOS:
GET http://localhost:8082/productos
POST http://localhost:8082/usuarios
DELETE http://localhost:8082/productos/id
{
  "nombre": "Perfume Acqua di Gio",
  "precio": 39990,
  "stock": 20
}

{
  "nombre": "Crema Nivea",
  "precio": 9900,
  "stock": 50
}


PEDIDOS:
GET http://localhost:8083/pedidos
POST http://localhost:8083/pedidos
DELETE http://localhost:8083/pedidos/id
