# GitHub repositories searcher

This is GitHub repositories searcher 


## Tech Stack

**Language:** Java 21 (virtual threads)

**Frameworks:** Spring Boot 3.5.3

**Build tool:** Maven

**APIs:** GitHub API

**UI:** REST API




## API Reference

#### Get all repos which are not forks, with branches by username
http
  GET /repositories/{username}
  

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `username` | `string` | **Required** |


## Example response

```json
[{
  "name": "DailyCodingProblemProblem35Hard",
  "owner": {
    "login": "LukaszBakJVM"
  },
  "fork": false,
  "branches": [
    {
      "name": "master",
      "commit": {
        "sha": "9ca302f081e8ed3d36af75db862d6fa6cd34219c"
      }
    }
  ]
},
  [{
    "name": "DailyCodingProblemProblem-88-Medium",
    "owner": {
      "login": "LukaszBakJVM"
    },
    "fork": false,
    "branches": [
      {
        "name": "master",
        "commit": {
          "sha": "5ffc46d75247051049389f0e33135f91e6758b3a"
        }
      }
    ]
  
,
  
```

## Errors

### 1.Case user not found :

```json
{
  "status" : "404 Not Found",
  "message" : "User {username} not found"
}
```




