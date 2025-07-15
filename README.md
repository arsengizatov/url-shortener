🔗 URL Shortener

A minimal and production-ready URL shortening service built with **Spring Boot**, **PostgreSQL**, and **Base62 encoding**.

> 🚀 Example:  
> Original URL → `https://openai.com`  
> Short Code → `g8LkY3`  
> Redirect URL → `http://localhost:8080/g8LkY3`

---

## 📚 Features

- ✅ Generate short links from long URLs
- 🔁 Redirect from short link to original URL
- 📊 Track usage statistics (number of redirects, creation date)
- 🔢 Base62 encoding from database ID
- 🐘 PostgreSQL as persistent store
- 🐳 Docker Compose support for local dev

---

## 💡 Architecture

```
Client → Controller → Service → Repository → PostgreSQL
                          ↓
                     Base62Encoder
```

---

## ⚙️ Tech Stack

| Component      | Description                   |
|----------------|-------------------------------|
| Java 21        | Core language                 |
| Spring Boot 3  | Web + JPA + Validation        |
| PostgreSQL     | Persistent storage            |
| Docker Compose | Local infrastructure          |
| Swagger UI     | API documentation (optional)  |
| JUnit          | Unit testing                  |

---

## 🚀 Getting Started

### 📁 1. Clone the repository

```bash
  git clone https://github.com/your-username/url-shortener.git
  cd url-shortener
```

### 🐘 2. Start PostgreSQL via Docker

```bash
  docker-compose up -d
```

This starts a PostgreSQL container on `localhost:5432`  
DB: `url_shortener`, user: `user`, password: `password`

### ☕ 3. Run the Spring Boot app

```bash
  ./mvn bootRun
```

App will start on `http://localhost:8080`

---

## 🧪 API Endpoints

### 🔸 `POST /shorten`

Creates a new short URL.

**Request body:**
```json
{
  "originalUrl": "https://openai.com"
}
```

**Response:**
```json
{
  "shortCode": "g8LkY3",
  "shortUrl": "http://localhost:8080/g8LkY3"
}
```

---

### 🔸 `GET /{shortCode}`

Redirects to the original URL.

**Example:**

```http
GET http://localhost:8080/g8LkY3
```

**Response:**
- 302 Redirect to: `https://openai.com`

---

### 🔸 `GET /{shortCode}/stats`

Returns stats about the shortened link.

**Response:**
```json
{
  "shortCode": "g8LkY3",
  "originalUrl": "https://openai.com",
  "redirectCount": 4,
  "createdAt": "2025-07-15T12:34:56"
}
```

---

## 🧾 Configuration

### `application.yml`

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/url_shortener
    username: user
    password: password
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
```

### `docker-compose.yml`

```yaml
version: '3.8'
services:
  postgres:
    image: postgres:15
    container_name: url_shortener_db
    environment:
      POSTGRES_DB: url_shortener
      POSTGRES_USER: user
      POSTGRES_PASSWORD: password
    ports:
      - "5432:5432"
    volumes:
      - pgdata:/var/lib/postgresql/data

volumes:
  pgdata:
```

---

## 📂 Project Structure

```
src/
 ├── controller/        → REST API layer
 ├── service/           → Business logic + Base62Encoder
 ├── repository/        → Spring Data JPA interfaces
 ├── model/             → JPA entities
 ├── dto/               → Request and response DTOs
 └── UrlShortenerApplication.java
```

---

## 🔮 Future Improvements

- ⏳ Expiring links (TTL)
- 🔐 Auth for managing personal links
- 📉 Admin dashboard with analytics
- 🧠 Redis cache layer
- 🌍 Custom domain support (e.g., `sho.rt/g8LkY3`)

---

## 🧑‍💻 Author

Made with ☕️ by [Arsen Gizatov](https://github.com/your-username)

---

## 📜 License

MIT License — feel free to use, modify and deploy.