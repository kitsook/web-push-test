# web-push-test
A web push notification demo with Java Spring Boot backend and Vue frontend.

## Quick start
Assuming the system has JDK (21 or later, with `JAVA_HOME` environment variable set) and npm installed:

```bash
git clone https://github.com/kitsook/web-push-test.git
# to build and start backend
cd web-push-test
./mvnw clean package spring-boot:run
# in another shell, to build and start frontend
cd web-push-test/src/ui
npm install && npm run serve
```
The backend restful API will be listening on `localhost:8081` and frontend is served via [http://localhost:8080](http://localhost.8080).

## Technical details

### Backend
- A Spring Boot application with restful APIs for managing subscriptions and triggering the backend to push notifications
- Some of the components
  - `controllers/MessageController`: serving APIs for pushing notification
  - `controllers/SubscriptionController`: serving APIs for managing subscription
  - `models/NotificationMessage`: bean for the notification message. Only a few [available fields](https://developer.mozilla.org/en-US/docs/Web/API/ServiceWorkerRegistration/showNotification) are included for demo purposes
  - `services/MessageService`: service to communicating with Push server using [webpush-java](https://github.com/web-push-libs/webpush-java)
- The Swagger document of the backend restful API is available at [http://localhost:8081/v1/swagger-ui/index.html](http://localhost:8081/v1/swagger-ui/index.html) when backend is running
 
![image](https://github.com/kitsook/web-push-test/assets/13360325/b478aaec-7be4-491a-b3f8-44f8ed8dfab9)

- To start the backend for debugging:
```bash
./mvnw clean package spring-boot:run -Dspring-boot.run.jvmArguments="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5005"
```

