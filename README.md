# 🚕 Sistema de Gestión de Cooperativa de Taxis

Sistema full-stack para la administración integral de una cooperativa de taxis, desarrollado con **Spring Boot** (backend) y **Angular** (frontend).

## 🎯 Descripción

Aplicación web que permite gestionar todos los aspectos operativos de una cooperativa de taxis, incluyendo:

- **Gestión de Socios/Miembros**: Alta, baja y modificación de socios de la cooperativa con diferentes roles (choferes, administrativos, mecánicos, presidentes, operadores)
- **Abonados**: Registro y administración de clientes con suscripciones mensuales
- **Vehículos**: Control de la flota de taxis
- **Viajes**: Registro y seguimiento de servicios
- **Licencias**: Gestión de habilitaciones y permisos

## 🛠️ Tecnologías Utilizadas

### Backend
- **Java 17+**
- **Spring Boot 3.x**
  - Spring Data JPA
  - Spring Web (REST API)
  - Spring Validation
  - Spring Security (en desarrollo)
- **MySQL** - Base de datos relacional
- **Lombok** - Reducción de código boilerplate
- **Swagger/OpenAPI** - Documentación automática de API
- **Maven** - Gestión de dependencias

### Frontend (En desarrollo)
- **Angular 17+**
- **TypeScript**
- **Bootstrap/Angular Material** - Interfaz de usuario

## 📋 Características Implementadas

- ✅ API REST con arquitectura por capas (Controller → Service → Repository → Entity)
- ✅ Validaciones de datos con Bean Validation (mensajes en español)
- ✅ DTOs para transferencia de datos
- ✅ Relaciones JPA (OneToMany, ManyToOne) con cascade
- ✅ Herencia de entidades (Strategy: JOINED)
- ✅ Documentación automática con Swagger UI
- ✅ Manejo de enumerados (roles, estados)
- ✅ Soft delete para registros históricos

## 🚀 Endpoints Disponibles

### Members (Socios)
- `POST /api/members` - Crear nuevo socio
- `GET /api/members` - Listar todos los socios
- `GET /api/members/{id}` - Obtener socio por ID
- `GET /api/members/dni/{dni}` - Buscar socio por DNI
- `PUT /api/members/{id}` - Actualizar socio
- `DELETE /api/members/{id}` - Dar de baja socio (soft delete)

## 🗂️ Estructura del Proyecto

```
backend/
├── src/main/java/com/pepotec/cooperative_taxi_managment/
│   ├── models/
│   │   ├── entities/      # Entidades JPA
│   │   ├── dto/           # Data Transfer Objects
│   │   └── enums/         # Enumerados
│   ├── repositories/      # Capa de acceso a datos
│   ├── services/          # Lógica de negocio
│   └── controllers/       # Endpoints REST
└── src/main/resources/
    └── application.properties
```

## 🔧 Configuración y Ejecución

### Prerrequisitos
- JDK 17 o superior
- MySQL 8.0+
- Maven 3.8+ (o usar el wrapper incluido)

### Instalación

1. Clonar el repositorio:
```bash
git clone https://github.com/tu-usuario/cooperative_taxi_managment.git
cd cooperative_taxi_managment/backend
```

2. Configurar base de datos en `application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/cooperative_taxi_db
spring.datasource.username=root
spring.datasource.password=TU_PASSWORD
```

3. Crear la base de datos:
```sql
CREATE DATABASE cooperative_taxi_db;
```

4. Ejecutar el proyecto:
```bash
./mvnw spring-boot:run
```

5. Acceder a Swagger UI:
```
http://localhost:8080/swagger-ui.html
```

## 🔜 Próximas Funcionalidades

- [ ] Gestión de Conductores (DriverEntity)
- [ ] Gestión de Vehículos/Taxis (TaxiEntity)
- [ ] Registro de Viajes (TripEntity)
- [ ] Abonados y Suscripciones (SubscriberEntity)
- [ ] Sistema de autenticación y autorización (Spring Security + JWT)
- [ ] Manejo de excepciones global
- [ ] Testing (JUnit + Mockito)
- [ ] Frontend con Angular
- [ ] Dashboard con estadísticas
- [ ] Reportes en PDF

## 👤 Autor

**Luciano David Morales Cuevas (Pepo)**

## 📄 Licencia

Este proyecto está bajo la Licencia MIT - ver el archivo LICENSE para más detalles.

