# 🧱 Step 1: What is ResponseEntity?

**Simple definition:**

📦 `ResponseEntity` is a class in Spring Boot that represents the entire HTTP response.

---

## 🛠️ Constructors

These allow creating `ResponseEntity` instances directly:

- `ResponseEntity(HttpStatusCode status)`  
  ➤ Create a response with only a status code (no body or headers).

- `ResponseEntity(T body, HttpStatusCode status)`  
  ➤ Create with both a body and a status.

- `ResponseEntity(MultiValueMap<String, String> headers, HttpStatusCode status)`  
  ➤ Create with headers and a status (no body).

- `ResponseEntity(T body, MultiValueMap<String, String> headers, int rawStatus)`  
  ➤ Create with body, headers, and a numeric status code.

- `ResponseEntity(T body, MultiValueMap<String, String> headers, HttpStatusCode statusCode)`  
  ➤ Same as above but using `HttpStatusCode` enum.

---

## 🧬 Inherited Methods (from `HttpEntity<T>` and `Object`)

Useful for inspecting existing `ResponseEntity` instances:

- `🔍 getBody()` – Retrieves the response body.
- `📬 getHeaders()` – Access the response headers.
- `❓ hasBody()` – Checks if there's a non-null body.
- `🧮 equals(), hashCode(), toString()` – Standard override methods.

---

## ⚙️ Status & Utility Methods

These static helpers make it easy to construct responses:

- `📡 status(HttpStatusCode status)` – Start a builder with a custom status.
- `🔢 status(int status)` – Same but with an integer code.
- `✅ ok()` – Shortcut to build a 200 OK response (builder).
- `✅ ok(T body)` – Shortcut for 200 OK with a body.
- `📦 of(Optional<T> body)` – If present, returns OK with body; if empty, returns 404 Not Found.
- `📦 ofNullable(T body)` – Like `of`, but treats null like `Optional.empty()` (OK if not null, otherwise 404).
- `📍 created(URI location)` – 201 Created + sets Location header.
- `📨 accepted()` – 202 Accepted builder.
- `🚫 noContent()` – 204 No Content builder.
- `⚠️ badRequest()` – 400 Bad Request builder.
- `🔍 notFound()` – 404 Not Found builder.
- `🧩 unprocessableEntity()` – 422 Unprocessable Entity builder.
- `💥 internalServerError()` – 500 Internal Server Error builder.

---

## 🧱 Builder Methods (`BodyBuilder` & `HeadersBuilder`)

Used after calling a `status` method to chain headers and/or body:

- `🔤 header(String name, String value)` – Add a single header.
- `📑 headers(HttpHeaders headers)` – Replace or add multiple headers.
- `📦 body(T body)` – Finalizes the `ResponseEntity` with the provided body.

**Note:**

- `BodyBuilder` supports `.header()`, `.body()`.
- `HeadersBuilder` supports setting headers and uses `.build()` to finalize without a body. 
