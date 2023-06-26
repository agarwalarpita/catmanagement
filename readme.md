# Cat Picture API

The Cat Picture API is a RESTful API for uploading and managing cat pictures.

## API Endpoints

### Upload a cat picture

Upload a cat picture to the server.

**Endpoint**: `POST /cats`

**Parameters**:
- `name` (string): The name of the cat picture.
- `file` (file): The image file to upload.

### Delete a cat picture

Delete a cat picture from the server.

**Endpoint**: `DELETE /cats/{id}`

**Path Parameter**:
- `id` (string): The ID of the cat picture to delete.

### Update a cat picture

Update a previously uploaded cat picture with a new image file.

**Endpoint**: `PUT /cats/{id}`

**Path Parameter**:
- `id` (string): The ID of the cat picture to update.

**Parameters**:
- `file` (file): The updated image file.

### Fetch a particular cat image file by its ID

Retrieve a cat picture by its ID.

**Endpoint**: `GET /cats/{id}`

**Path Parameter**:
- `id` (string): The ID of the cat picture to fetch.

### Fetch a list of uploaded cat pictures

Retrieve a list of all uploaded cat pictures.

**Endpoint**: `GET /cats`

## Getting Started

To get the API up and running, follow these steps:

run catApiApplication.java file 
access all endpoints with basic auth
