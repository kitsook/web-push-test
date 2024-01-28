<template>
  <v-container>
    <v-row class="text-center">
      <v-col cols="12" class="mb-4">
        <h2 class="display-2 font-weight-bold">Web Push Notification demo</h2>
      </v-col>
    </v-row>
    <v-row>
      <v-col cols="12" md="6" class="mb-4">
        <v-card title="Permission" class="pa-4">
          <p class="font-weight-regular mb-4 text-center">
          Current permission: <span class="font-weight-bold"> {{ notificationPermission }} </span>
          </p>

          <p class="font-italic">
          Note that if permission is "denied", browser will not prompt for subscription. Please change it on site information.
          </p>
        </v-card>
      </v-col>

      <v-col cols="12" md="6" class="mb-4">
        <v-card title="Subscription" class="pa-4">
          <p class="font-weight-regular mb-4 text-center">
            Subscription status: <span class="font-weight-bold"> {{ subscribed }} </span>
          </p>
          <v-btn class="ma-2" @click="subscribe" :disabled='subscribed'>Subscribe</v-btn>
          <v-btn class="ma-2" @click="unsubscribe" :disabled='!subscribed'>Unsubscribe</v-btn>
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
    }
  },
  async beforeMount() {
    await this.firstUpdated();
    await navigator.serviceWorker.register('/service-worker.js');
  },
  methods: {
    async firstUpdated() {
      const registration = await navigator.serviceWorker.getRegistration();
      this.subscribed = !!(await registration?.pushManager.getSubscription());
      this.notificationPermission = Notification.permission;
    },
    async subscribe() {
      this.notificationPermission = await Notification.requestPermission();
      if (this.notificationPermission === "granted") {
        const publicKey = await getPublicKey();
        const registration = await navigator.serviceWorker.getRegistration();
        const subscription = await registration?.pushManager.subscribe({
          userVisibleOnly: true,
          applicationServerKey: this.urlB64ToUint8Array(publicKey),
        });
        if (subscription) {
          this.subscribed = true;
          // Serialize keys uint8array -> base64
          postSubscription(JSON.parse(JSON.stringify(subscription)));
        } else {
          console.error("Failed to sign up with push server");
        }
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
  },
}
</script>