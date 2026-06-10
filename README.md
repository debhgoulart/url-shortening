# URL Shortening Service

A RESTful API for shortening URLs, retrieving original URLs, updating and deleting shortened URLs, and tracking access statistics.

## Project Page

https://roadmap.sh/projects/url-shortening-service

## Features

* Create short URLs
* Retrieve URL information
* Update existing URLs
* Delete URLs
* Track access statistics
* Redirect shortened URLs to the original destination

## Technologies

* Java 21
* Spring Boot
* Spring Data JPA
* MySQL
* Docker Compose
* Maven

---

## Getting Started

### Clone the repository

```bash
git clone https://github.com/debhgoulart/url-shortening.git
cd url-shortening
```

---

## Start MySQL with Docker Compose

Run:

```bash
docker compose up -d
```

Verify the container is running:

```bash
docker ps
```

---

## Configure Application

Make sure your `application.properties` points to the database started by Docker Compose.

Example:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/url_shortening
spring.datasource.username=root
spring.datasource.password=root
spring.jpa.hibernate.ddl-auto=update
```

---

## Run the Application

```bash
mvn spring-boot:run
```

The API will start at:

```text
http://localhost:8080
```

---

## API Usage

### Create a Short URL

Request:

```http
POST /shorten
```

Body:

```json
{
  "url": "https://www.google.com"
}
```

Response:

```json
{
  "id": 1,
  "url": "https://www.google.com",
  "shortCode": "abc123",
  "createdAt": "2026-06-10T12:00:00",
  "updatedAt": "2026-06-10T12:00:00"
}
```

---

### Use the Shortened URL

Open your browser and access:

```text
http://localhost:8080/abc123
```

The application will:

1. Find the original URL.
2. Increase the access counter.
3. Redirect the user to the original URL.

Example:

```text
http://localhost:8080/abc123
```

Redirects to:

```text
https://www.google.com
```

---

### Retrieve URL Information

```http
GET /shorten/{id}
```

---

### Update a URL

```http
PUT /shorten/{id}
```

Body:

```json
{
  "url": "https://www.github.com"
}
```

---

### Delete a URL

```http
DELETE /shorten/{id}
```

---

### Get URL Statistics

```http
GET /shorten/{id}/stats
```

Response:

```json
{
  "id": 1,
  "url": "https://www.google.com",
  "shortCode": "abc123",
  "accessCount": 5
}
```

---

## License

This project was developed for educational purposes as part of the roadmap.sh backend projects.
