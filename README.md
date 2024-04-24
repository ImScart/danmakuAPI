# API Documentation

This documentation outlines the various endpoints of the API, their expected inputs, and possible outputs.

## User Registration

**Endpoint:** `{baseURL}/register`  
**Method:** POST  
**Description:** Creates the user in the database and sends an email to confirm the user's email.

### Expected Input

```json
{
  "username": "nomUtilisateur",
  "password": "motDePasse",
  "email": "emailvalide@gmail.com"
}
```
### Possible Outputs
```json
{
  "code": "0",
  "data": null,
  "message": "Account created successfully"
}
```
```json
{
    "code": "1",
    "data": null,
    "message": "Username already exists."
}
```
```json
{
    "code": "2",
    "data": null,
    "message": "Email already exists."
}
```
```json
{
    "code": "3",
    "data": null,
    "message": "One of the values to register the user is invalid."
}
```

## User Login

**Endpoint:** `{baseURL}/login`  
**Method:** GET  
**Description:** Logs in the user and returns all the information stored in the database.

### Expected Input

```json
{
  "username": "scart",
  "password": "NouveauMotDePasse"
}
```
### Possible Outputs
```json
{
    "code": "0",
    "data": {
        "id": 22,
        "username": "scart",
        "password": "NouveauMotDePasse",
        "email": "scartcontact@gmail.com",
        "bio": null,
        "emailIsVerified": true,
        "verificationToken": "c89148bf-43b7-4348-af45-307555237ed6",
        "resetToken": "0afb20fd-6caa-4274-bf3b-b10126ad78dd",
        "profilePicture": null
    },
    "message": null
}
```
```json
{
    "code": "1",
    "data": null,
    "message": "User not found"
}
```
```json
{
    "code": "2",
    "data": null,
    "message": "Invalid password"
}
```
```json
{
    "code": "3",
    "data": null,
    "message": "One of the values to login the user is invalid."
}
```

## User Bio Change

**Endpoint:** `{baseURL}/user/updateBio`  
**Method:** POST  
**Description:** Changes the users bio in the database.

### Expected Input

```json
{
  "username": "scart",
  "password": "NouveauMotDePasse",
  "bio": "Voici ma nouvelle bio :)"
}
```
### Possible Outputs
```json
{
    "code": "0",
    "data": null,
    "message": "Bio updated successfully"
}
```
```json
{
    "code": "1",
    "data": null,
    "message": "User not found"
}
```
```json
{
    "code": "2",
    "data": null,
    "message": "Invalid password"
}
```
```json
{
    "code": "3",
    "data": null,
    "message": "One of the values to change the users bio is invalid."
}
```

## User Email Verification *INTERNAL USE ONLY*

**Endpoint:** `{baseURL}/user/verify`  
**Method:** GET  
**Description:** Used when user clicks on the link sent to his email. Changes verification status in database for later use when resetting password.

### Expected Input

```text
?token=83323f1b-a390-4766-b98e-476513fa6cc5
```
### Possible Outputs
```text
Email verified successfully
```
```text
Invalid or expired verification token
```

## Send Password Reset Email

**Endpoint:** `{baseURL}/user/sendPassword`  
**Method:** POST  
**Description:** Creates a temporary reset password token, stores it in the database and sends an email to user if email is verified to reset password.
### Expected Input

```json
{
  "email": "scartcontact@gmail.com"
}
```
### Possible Outputs
```json
{
    "code": "0",
    "data": null,
    "message": "Password reset email has been sent"
}
```
```json
{
    "code": "1",
    "data": null,
    "message": "Invalid Email"
}
```
```json
{
    "code": "2",
    "data": null,
    "message": "Email is not verified."
}
```
```json
{
    "code": "3",
    "data": null,
    "message": "One of the values to send the email is invalid."
}
```

## Confirm Password Reset

**Endpoint:** `{baseURL}/user/resetPassword`  
**Method:** POST  
**Description:** Resets a users password in the database.
### Expected Input

```json
{
  "token": "e91fa556-cc39-4a7b-a8a8-7b797261c336",
  "password": "NouveauMotDePasse"
}
```
### Possible Outputs
```json
{
    "code": "0",
    "data": null,
    "message": "Password has been reset"
}
```
```json
{
    "code": "1",
    "data": null,
    "message": "Invalid reset token"
}
```
```json
{
    "code": "3",
    "data": null,
    "message": "One of the values to change the users password is invalid."
}
```

## Create A Thread

**Endpoint:** `{baseURL}/thread/create`  
**Method:** POST  
**Description:** Creates a new thread and adds it to the database.
### Expected Input

```json
{
  "ownerId": "22",
  "title": "Titre du thread",
  "value": "texte dans le thread"
}
```
### Possible Outputs
```json
{
    "code": "0",
    "data": null,
    "message": "Thread created successfully"
}
```
```json
{
    "code": "1",
    "data": null,
    "message": "User not found with ID: 2444"
}
```
```json
{
    "code": "3",
    "data": null,
    "message": "One of the values to create the thread is invalid."
}
```

## Get All Threads

**Endpoint:** `{baseURL}/thread/all`  
**Method:** GET  
**Description:** Returns all threads in the database.
### Expected Input

```text
No input required
```
### Possible Outputs
```json
[
    {
        "id": 17,
        "title": "rr",
        "value": "Texte du thread",
        "created": "2024-04-17T19:25:06",
        "ownerId": 22,
        "username": "scart",
        "profilePicture": null
    },
    {
        "id": 18,
        "title": "Titre du thread",
        "value": "texte dans le thread",
        "created": "2024-04-23T00:15:39",
        "ownerId": 22,
        "username": "scart",
        "profilePicture": null
    }
]
```

## Like A Thread

**Endpoint:** `{baseURL}/thread/like`  
**Method:** POST  
**Description:** Adds a like for a thread in the database
### Expected Input

```json
{
  "ownerId": "22",
  "forumThreadId": "18"
}
```
### Possible Outputs
```json
{
    "code": "1",
    "data": null,
    "message": "User not found with ID: 222"
}
```
```json
{
    "code": "1",
    "data": null,
    "message": "Thread not found with ID: 19"
}
```
```json
{
    "code": "2",
    "data": null,
    "message": "User has already liked the thread"
}
```
```json
{
    "code": "3",
    "data": null,
    "message": "One of the values to like the thread is invalid."
}
```

## Get Thread Likes

**Endpoint:** `{baseURL}/thread/allLikes`  
**Method:** GET  
**Description:** Gets all likes from a specific thread ID
### Expected Input

```json
{
  "forumThreadId": "18"
}
```
### Possible Outputs
```json
[
    {
        "id": 2,
        "ownerId": 22,
        "forumThreadId": 18
    },
    {
        "id": 3,
        "ownerId": 23,
        "forumThreadId": 18
    }
]
```
```json
{
    "code": "1",
    "data": null,
    "message": "Thread not found with ID: null"
}
```

## Create A Thread Comment

**Endpoint:** `{baseURL}/thread/comment/create`  
**Method:** POST  
**Description:** Created a thread comment in the database.
### Expected Input

```json
{
  "ownerId": "22",
  "threadId": "18",
  "value": "Voici mon deuxieme commentaire"
}
```
### Possible Outputs
```json
{
    "code": "0",
    "data": null,
    "message": "Thread comment created successfully"
}
```
```json
{
    "code": "1",
    "data": null,
    "message": "User not found with ID: 255"
}
```
```json
{
    "code": "1",
    "data": null,
    "message": "Thread not found with ID: 1444"
}
```
```json
{
    "code": "3",
    "data": null,
    "message": "One of the values to comment the thread is invalid."
}
```