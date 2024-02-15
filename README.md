# web-push-test
A web push notification demo with Java Spring Boot backend and Vue frontend.

<p align="center">
  <img src="https://github.com/kitsook/web-push-test/assets/13360325/722a1fa5-ced1-4e0e-a9e2-095ffcfbafdb" height="300">
</p>

## Quick start
Assuming the system has JDK (17 or later, with `JAVA_HOME` environment variable set) and npm installed:

```bash
git clone https://github.com/kitsook/web-push-test.git
# to build and start backend
cd web-push-test
./mvnw clean package spring-boot:run
# in another shell, to build and start frontend
cd web-push-test/src/ui
npm install && npm run serve
```
The backend restful API will be listening on `http://localhost:8081` and frontend is served via `http://localhost:8080`.

## Technical details

### Backend
- A Spring Boot application with restful APIs for managing subscriptions and triggering the backend to push notifications
- Key components
  - `controllers/MessageController`: serving APIs for pushing notification
  - `controllers/SubscriptionController`: serving APIs for managing subscription
  - `models/NotificationMessage`: bean for the notification message. Only a few [available fields](https://developer.mozilla.org/en-US/docs/Web/API/ServiceWorkerRegistration/showNotification) are included for demo purposes
  - `services/MessageService`: service to communicating with Push server using [webpush-java](https://github.com/web-push-libs/webpush-java)
  - `utils/Storage`: temporary storage for the demo. The public/private key pair should be stored in securied storage for production deployment. Also the subscriptions should be stored in persistent storage
  - `WebPushTestApplication`: main application. It uses `WebMvcConfigurer` to open CORS calling for demo purposes. Should setup properly for actual deployment
- Since it has no persistent storage, whenever the backend is restarted, **user will need to re-subscribe for the notification to work**
- The Swagger document of the backend restful API is available at [http://localhost:8081/v1/swagger-ui/index.html](http://localhost:8081/v1/swagger-ui/index.html) when backend is running

<p align="center">
<img src="https://github.com/kitsook/web-push-test/assets/13360325/b478aaec-7be4-491a-b3f8-44f8ed8dfab9" height="200">
</p>

- To start the backend for debugging:
```bash
./mvnw clean package spring-boot:run -Dspring-boot.run.jvmArguments="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5005"
```

### Frontend
- Implemented with Vue and Vuetify
- Key components
  - `ui/src/components/Subscribe.vue`: UI for the subscriptions
    - Shows whether user has granted permission to display notofication
    - To subscribe / unsubscribe from the notification
  - `ui/src/components/ManualScribe.vue`: allow manually add subscriptions for testing purposes
  - `ui/src/components/Message.vue`: UI for triggering the backend to send notification
  - `ui/src/services/Publishing.js` and `ui/src/services/Subscription.js`: calling the restful APIs with hard-coded `localhost` URLs
  - `ui/public/service-worker.js`: the logic to run when receiving notifications. It is registered during the initialization of `Subscribe.vue`. Alternatively, instead of using a standalone js file, the logic can be [part of the webpack](https://github.com/web-push-libs/webpush-java/wiki/Usage-Example#webpack)
