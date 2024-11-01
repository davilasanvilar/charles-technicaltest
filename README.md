-----------------INSTRUCTIONS-------------------------

API DEPLOYMENT
******************************************************
Inside the api folder:

1. Create the docker image of the API:
    docker build -t techtest-api .

2. Execute the docker-compose file:
    docker-compose up -d 

3. If everything is right, the API is going to be running in 8080.



FRONT DEPLOYMENT
*******************************************************
Inside the front folder:

1. Install de dependencies:
    yarn install // npm install

2. Execute the frontend (specify the port 3000 because the cors is configured that way):
    yarn dev --port 3000 // npm run dev -- --port 3000


INFO
********************************************************
-> The tests are inside the src/test folder of the Spring Boot project.
-> There are some objects already created in the api when initialization.
-> The front project is just a small utility for basic usage:
    - You have to login introducing the username of a created user.
    - If you login with the "admin" username, you can access to the screen of creating new cars.
    - If you enter with another username (you will have to sign up the username first), you can book cars, check your existent bookings and return those cars.


