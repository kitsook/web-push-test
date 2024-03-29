<template>
  <v-container>
    <v-row class="text-center">
      <v-col cols="12" class="mb-4">
        <h2 class="display-2 font-weight-bold">Web Push Notification demo</h2>
      </v-col>
    </v-row>
    <v-row>
      <v-col cols="12" md="6" class="mb-4">
        <v-card title="Permission" class="fill-height pa-4">
          <v-card-subtitle>Status: {{ notificationPermission }}</v-card-subtitle>
          <p class="mb-4">
          </p>

          <div>
              <p class="font-italic font-weight-thin mb-2">
              Note that if permission is "denied", browser will not prompt for subscription. Please enable it in Settings and reload the page.
              </p>

              <v-btn class="ma-2" href="https://support.microsoft.com/en-us/microsoft-edge/manage-website-notifications-in-microsoft-edge-0c555609-5bf2-479d-a59d-fb30a0b80b2b" target="_blank">
                Edge
              </v-btn>
              <v-btn class="ma-2" href="https://support.google.com/chrome/answer/3220216?hl=en&co=GENIE.Platform%3DDesktop#zippy=%2Cfix-issues-with-notifications" target="_blank">
                Chrome
              </v-btn>
              <v-btn class="ma-2" href="https://support.mozilla.org/en-US/kb/push-notifications-firefox" target="_blank">
                Firefox
              </v-btn>
          </div>
        </v-card>
      </v-col>

      <v-col cols="12" md="6" class="mb-4">
        <v-card title="Subscription" class="fill-height pa-4">
          <v-card-subtitle>Status: {{ subscribed? 'subscribed' : 'not subscribed' }}</v-card-subtitle>
          <p class="mb-4"></p>

          <v-btn class="ma-2" @click="subscribe" :disabled='subscribed'>Subscribe</v-btn>
          <v-btn class="ma-2" @click="unsubscribe" :disabled='!subscribed'>Unsubscribe</v-btn>

          <p class="font-italic font-weight-thin ma-2">
            The demo backend has no persistent storage. After restarting the backend, reload this page or re-subscribe.
          </p>
          <p class="font-weight-thin">
            <v-list lines="three">
              <v-list-item title="Application Server Key"
                :subtitle="applicationServerKey"></v-list-item>
            </v-list>
            <v-textarea label="Subscription" :model-value="jsonStr"></v-textarea>
          </p>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import { getPublicKey, postSubscription, deleteSubscription } from "../services/Subscription.js";

export default {
  name: 'Subscribe',
  props: {
  },
  data() {
    return {
      notificationPermission: null,
      subscribed: false,
      applicationServerKey: null,
      jsonStr: null,
    }
  },
  async beforeMount() {
    await navigator.serviceWorker.register('/service-worker.js');
    await this.init();
  },
  methods: {
    async init() {
      this.applicationServerKey = await getPublicKey();
      await navigator.serviceWorker.ready;
      const registration = await navigator.serviceWorker.getRegistration();
      const subscription = await registration?.pushManager.getSubscription()
      this.subscribed = !!(subscription);
      if (this.subscribed) {
        console.log(subscription.toJSON());
        postSubscription(subscription.toJSON());
      }
      this.showSubscription(subscription);
      this.notificationPermission = Notification.permission;
    },
    async subscribe() {
      if (!(this.applicationServerKey)) {
        this.applicationServerKey = await getPublicKey();
      }
      this.notificationPermission = await Notification.requestPermission();
      if (this.notificationPermission === "granted") {
        const registration = await navigator.serviceWorker.getRegistration();
        const subscription = await registration?.pushManager.subscribe({
          userVisibleOnly: true,
          applicationServerKey: this.urlB64ToUint8Array(this.applicationServerKey),
        });
        if (subscription) {
          console.log(subscription.toJSON());
          this.subscribed = true;
          postSubscription(subscription.toJSON());
        } else {
          console.error("Failed to sign up with push server");
        }
        this.showSubscription(subscription);
      }
    },
    async unsubscribe() {
      const registration = await navigator.serviceWorker.getRegistration();
      const subscription = await registration?.pushManager.getSubscription();
      if (subscription) {
        await subscription.unsubscribe();
        deleteSubscription(subscription.endpoint);
        this.subscribed = false;
      }
      this.showSubscription(null);
    },
    urlB64ToUint8Array(base64String) {
      const padding = "=".repeat((4 - (base64String.length % 4)) % 4);
      const base64 = (base64String + padding)
        .replace(/-/g, "+")
        .replace(/_/g, "/");
      const rawData = window.atob(base64);
      const outputArray = new Uint8Array(rawData.length);
      for (let i = 0; i < rawData.length; ++i) {
        outputArray[i] = rawData.charCodeAt(i);
      }
      return outputArray;
    },
    showSubscription(subscription) {
      if (subscription) {
        this.jsonStr = JSON.stringify(subscription, null, 2);
      } else {
        this.jsonStr = null;
      }
    },
  },
}
</script>