
# API Methods

This document outlines the available API endpoints, their inputs, and expected responses.

## Base URL

All API requests should be made to `http://{baseurl}:8080`. Replace `{baseurl}` with the actual base URL of the API.

---

### 1. List All Threads

- **Endpoint:** `/thread/all`
- **Method:** `GET`
- **Input:** None
- **Returns:** An array of thread objects. Each object contains the following fields:
  - `id`: Thread ID
  - `title`: Thread title
  - `value`: Thread content
  - `created`: Creation timestamp
  - `ownerId`: ID of the thread owner
  - `username`: Owner's username
  - `profilePicture`: Filename of the owner's profile picture

**Example Response:**

```json
[
  {
    "id": 1,
    "title": "premier thread",
    "value": "ca marche :)",
    "created": "2024-04-04T02:35:06",
    "ownerId": 16,
    "username": "scartos",
    "profilePicture": "default.png"
  }
]
```

---

### 2. Register a New Account

- **Endpoint:** `/register`
- **Method:** `POST`
- **Input:** JSON object containing `username`, `password`, and `email`.
- **Returns:** A response object indicating the result of the operation:
  - `code`: Status code (`0` for success, `1` for username exists, `2` for email exists)
  - `data`: Additional data (null in this case)
  - `message`: Status message

**Example Request:**

```json
{
  "username": "exempleUsername",
  "password": "exempleMotDePasse",
  "email": "testemail@gmail.com"
}
```

**Example Response:**

```json
{
  "code": "0",
  "message": "Account created successfully"
}
```

---

### 3. User Login

- **Endpoint:** `/login`
- **Method:** `GET`
- **Input:** JSON object with `username` and `password`.
- **Returns:** A response object with login result. On success, `data` contains user details.

**Example Request:**

```json
{
  "username": "scartos",
  "password": "TestMDP123!"
}
```

---

### 4. Update User Bio

- **Endpoint:** `/user/updateBio`
- **Method:** `POST`
- **Input:** JSON object with `username`, `password`, and the new `bio`.
- **Returns:** Status message indicating if the bio was updated successfully.

---

### 5. Verify Email

- **Endpoint:** `/verify`
- **Method:** `GET`
- **Input:** Query parameter `token` for email verification.
- **Usage:** Backend only.
- **Returns:** Status message indicating the result of the email verification process.

---

### 6. Create a New Thread

- **Endpoint:** `/thread/create`
- **Method:** `POST`
- **Input:** JSON object containing `ownerId`, `title`, and `value` of the thread.
- **Returns:** Status message indicating if the thread was created successfully.

---

**Note:** Responses for each endpoint include a `code` field indicating the operation's success or specific error types, followed by a relevant message.

---

