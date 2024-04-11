
# API Documentation

## User Management

### Register a New User
- **Endpoint:** `/register`
- **Method:** `POST`
- **Input:** `UserRegistrationDto` with user registration information.
- **Response:** User account creation status.
- **Success Response Code:** `200 OK`
- **Error Codes:** 
  - `1`: Username already exists.
  - `2`: Email already exists.

### User Login
- **Endpoint:** `/login`
- **Method:** `GET`
- **Input:** `UserLoginDto` with username and password.
- **Response:** User login status and user details.
- **Success Response Code:** `200 OK`

### Update User Bio
- **Endpoint:** `/user/updateBio`
- **Method:** `POST`
- **Input:** `UserBioDto` with new bio content.
- **Response:** Bio update confirmation.
- **Success Response Code:** `200 OK`

## Email Verification

### Verify User Email
- **Endpoint:** `/user/verify`
- **Method:** `GET`
- **Input:** `token` as URL parameter.
- **Response:** Email verification status.
- **Success Response Code:** `200 OK`
- **Error Code:** 
  - `1`: Invalid or expired verification token.

## Forum Thread Management

### Create a New Thread
- **Endpoint:** `/thread/create`
- **Method:** `POST`
- **Input:** `ForumThreadCreateDto` with thread creation details.
- **Response:** Thread creation status.
- **Success Response Code:** `200 OK`

### List All Threads
- **Endpoint:** `/thread/all`
- **Method:** `GET`
- **Response:** List of all forum threads.
- **Success Response Code:** `200 OK`

## Password Management

### Send Password Reset Email
- **Endpoint:** `/user/sendPassword`
- **Method:** `POST`
- **Input:** `ResetPasswordEmailDto` with user email.
- **Response:** Password reset email sending status.
- **Success Response Code:** `200 OK`

### Reset User Password
- **Endpoint:** `/user/resetPassword`
- **Method:** `POST`
- **Input:** `ResetPasswordDto` with reset token and new password.
- **Response:** Password reset status.
- **Success Response Code:** `200 OK`

## Error Handling
- **Username Exists**: `1`
- **Email Exists**: `2`
- **Username Not Found**: `1`
- **Invalid Password**: `2`
- **Expired/Invalid Token**: `1`
- **Invalid ID**: `1`
- **Invalid Email**: `1`
- **Invalid Thread Values**: `2`
- **Email Not Verified**: `1`
- **Invalid Reset Token**: `1`

Each endpoint's response includes a code indicating the result (`0` for success, other values for specific errors) and a message detailing the outcome or error.
