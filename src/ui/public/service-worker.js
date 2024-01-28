'use strict';

/* eslint-env browser, serviceworker */

self.addEventListener('install', () => {
    self.skipWaiting();
});

self.addEventListener('push', function(event) {
    console.log('Push message received.');
    let notificationTitle = 'Notification';
    // available options https://developer.mozilla.org/en-US/docs/Web/API/ServiceWorkerRegistration/showNotification
    const notificationOptions = { data: {} };

    if (event.data) {
        const eventData = event.data.json();
        if (eventData.title) {
          notificationTitle = eventData.title
        } else {
          notificationTitle = 'Received Notification';
        }
        if (eventData.message) {
          notificationOptions.body = eventData.message
        }
        if (eventData.icon) {
          notificationOptions.icon = eventData.icon;
        }
        if (eventData.url) {
          notificationOptions.data.url = eventData.url;
        }
    }

    event.waitUntil(
        self.registration.showNotification(
            notificationTitle,
            notificationOptions,
        ),
    );
});

self.addEventListener('notificationclick', function(event) {
    console.log('Notification clicked.');
    event.notification.close();

    let clickResponsePromise = Promise.resolve();
    if (event.notification.data?.url) {
        clickResponsePromise = clients.openWindow(event.notification.data.url);
    }

    event.waitUntil(clickResponsePromise);
});