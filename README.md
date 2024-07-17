# GitHub Repositories API - Task

## Description

This API application allows users to retrieve a list of GitHub repositories for a specified user. Only repositories that are not forks are returned, along with the required information:
Repository Name, Owner Login, For each branch it’s name and last commit sha

## Table of Contents

1. [Requirements](#requirements)
2. [Installation](#installation)
3. [Usage](#usage)
4. [API Endpoints](#api-endpoints)
5. [Error Handling](#error-handling)
6. [Testing](#testing)
7. [Contribution](#contribution)
8. [License](#license)

## Requirements

- Java 21
- Spring Boot 3
- Maven or Gradle

## Installation
1. Clone the repository:
 ```bash
git clone https://github.com/username/repo.git
```
2. Navigate into the project directory:
```bash
cd repo
```
4. Install dependencies:
```bash
npm install
```


## Usage
```bash
nmp start
```

## API Endpoints
List GitHub Repositories
- Endpoint: GET /{userName}/repos

- Headers: Accept: application/json
- This header specifies the media types that the client wants to receive from the server. By including Accept: application/json, the client informs the server that it expects a response in JSON format.
- Description: Retrieves all repositories for the specified GitHub user that are not forks.

Response Format:
```[
    {
        "name": "repo_name_1",
        "owner": {
            "login": "owner_name"
        },
        "branches": [
            {
                "name": "branch_name_1",
                "commit": {
                    "sha": "commit_sha_1"
                }
            }
        ]
    },
    {
        "name": "repo_name_2",
        "owner": {
            "login": "owner_name"
        },
        "branches": [
            {
                "name": "branch_name_2",
                "commit": {
                    "sha": "commit_sha_2"
                }
            }
        ]
    }
]
```
## Testing
Test Class
The tests are implemented in the TaskApplicationTests class located in pl.skotniczny.task. This class uses Spring Boot's testing framework and Mockito to perform integration tests on the API.

Test Methods:

- test_repo_name_and_owner_login: Tests that the repository name and owner login are correctly returned in the response.

- test_not_existing_user: Tests the API's response for a non-existing GitHub user, ensuring a 404 status and an appropriate error message.

- test_not_acceptable_format: Tests the API's response when an unacceptable format (e.g., XML) is requested, ensuring a 406 status and an appropriate error message.

Running Tests
To run the tests, use the following command:
```bash
./mvnw test
```

## Error Handling
Handle Non-Existing User
Endpoint: GET /repositories/{username}

Description: If the specified GitHub user does not exist, a 404 response is returned.

Response Format:
```
{
    "status": 404,
    "message": "This user does not exist"
}
```

Handle Http Media Type Not Acceptable (e.g. application/xml) 
Description: If client specified not acceptable media type format, a 406 response is returned.
```
{
    "status": 406,
    "message": "Not acceptable format"
}
```

Handle Rate limit exceeded
Description: when the user exceeded the number of server requests, a 403 response is returned.
```
{
    "status": 403,
    "message": "Rate limit exceeded"
}
```
## Contribution
Contributions to the project are welcome. Please report issues or suggest new features by opening issues in the repository. To contribute, please follow these steps:

1. Fork the repository.
2. Create a new branch (git checkout -b feature/YourFeature).
3. Make your changes and commit them (git commit -m 'Add some feature').
4. Push to the branch (git push origin feature/YourFeature).
5. Open a Pull Request.

## License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details

## Contact
- E-mail: - danielskotniczny1999@gmail.com
- GitHub - dskotniczny99
