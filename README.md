
# clj3_url


This is a Clojure-based URL shortening service consisting of a REST API server and a command-line client. The server provides endpoints to create, retrieve, update, and delete shortened URLs, while the client offers an interactive menu to perform these actions.

## Features

- **Server**:
  - REST API with endpoints:
    - `POST /normal-url`: Creates a shortened URL from a full URL.
    - `GET /:shortCode`: Retrieves the full URL for a given short code.
    - `PUT /:shortCode/:fullUrl`: Updates the full URL for a given short code.
    - `DELETE /:shortCode`: Deletes a shortened URL.
  - Stores data in an in-memory atom (simulating a database).
  - Validates URLs to ensure they start with `http://` or `https://` and contain a valid domain.
- **Client**:
  - Command-line interface with a menu:
    - '1'. Create: Shorten a new URL.
    - 2. Show: Retrieve the full URL for a short code.
    - 3. Update: Change the full URL for an existing short code.
    - 4. Delete: Remove a shortened URL.
    - 5. Exit: Close the client.
  - Communicates with the server via HTTP requests.
## Requirements

- [Leiningen](https://leiningen.org/) (Clojure build tool)
- Java 8 or higher
- Clojure 1.11.1
  
# Dependencies
- org.clojure/clojure: Core Clojure library
- compojure: Routing for the server
- ring/ring-defaults, ring/ring-json: Middleware for HTTP handling
- cheshire: JSON encoding/decoding
- clj-http: HTTP client for the client application
