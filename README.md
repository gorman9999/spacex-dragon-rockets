# SpaceX Dragon Rockets Library

This is a simple Java library to manage SpaceX rockets and missions.

## Features
- Add new rockets and missions.
- Assign rockets to missions.
- Change rocket and mission statuses.
- Get a summary of missions by the number of rockets assigned.

## Assumptions
- Rockets and missions are uniquely identified by their IDs.
- A rocket can only be assigned to one mission at a time.
- Mission statuses are automatically updated based on rocket assignments and statuses.

## How to Use
1. Clone the repository.
2. Run the tests using `mvn test`.
3. Use the `SpaceXService` class to interact with the library.

## Example
```java
SpaceXService service = new SpaceXService();
service.addRocket(new Rocket("1", "Dragon 1"));
service.addMission(new Mission("1", "Mars"));
service.assignRocketToMission("1", "1");