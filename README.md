# Tienda Online - Microservicios con Spring Boot

![Java](https://img.shields.io/badge/Java-17-blue)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.3-green)
![Maven](https://img.shields.io/badge/Maven-3.8.1-blue)
![License](https://img.shields.io/badge/License-MIT-green)

---

## Descripción

Proyecto de tienda online desarrollado con arquitectura de microservicios usando Spring Boot 3.5.3.  
El sistema consta de varios microservicios independientes para **productos**, **usuarios**, **pedidos** y una API Gateway que orquesta las llamadas y ofrece interfaz web con Thymeleaf.

---

## Tecnologías

- Java 17
- Spring Boot 3.5.3
- Spring Data JPA
- Spring Web MVC / REST
- Thymeleaf (en API Gateway)
- MySQL
- Maven
- Springdoc OpenAPI (Documentación API)
- JUnit 5 (Testing)

---

## Estructura del proyecto

| Microservicio  | Descripción                                  | Puerto por defecto |
| -------------- | --------------------------------------------|--------------------|
| productos      | Gestión de productos (CRUD)                  | 8081               |
| usuarios       | Gestión de usuarios                          | 8082               |
| pedidos        | Gestión de pedidos                           | 8083               |
| tienda (gateway) | API Gateway con interfaz web y orquestación | 8084               |

---

## Requisitos

- Java 17 o superior
- Maven 3.8.1 o superior
- MySQL configurado y corriendo localmente o en servidor

---

## Instalación y ejecución

1. Clona el repositorio:

```bash
git clone https://github.com/acamilo777/tienda-online.git
cd tienda-online
