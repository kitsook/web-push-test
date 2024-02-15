<template>
  <v-container>
    <v-row>
      <v-col cols="12" md="12" class="mb-4">
        <v-card title="Manual Subscription" class="fill-height pa-4">
          <v-card-subtitle>Manually send subscription information to backend for testing, e.g.:</v-card-subtitle>

          <pre class="ml-4 mt-4 font-weight-thin">
{{subscriptionExample}}
          </pre>
          <div>
            <v-textarea label="Subscription" v-model="jsonStr"></v-textarea>
            <v-btn class="ma-2" @click="subscribe" :disabled="!isJsonString()">
              Send
            </v-btn>
          </div>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import { postSubscription } from "../services/Subscription.js";

export default {
  name: 'ManualSubscribe',
  props: {
  },
  data() {
    return {
      subscriptionExample: JSON.stringify({
          "endpoint": "https://endpoint.example.org/?token=abcdef",
          "keys": {
            "p256dh": "abcdef",
            "auth": "mnopqrs"
          }
        }, null, 2),
      jsonStr: null,
    };
  },
  methods: {
    async subscribe() {
      console.log(this.jsonStr);
      postSubscription(JSON.parse(this.jsonStr));
    },
    isJsonString() {
      if (!this.jsonStr) {
        return false;
      }
      try {
          JSON.parse(this.jsonStr);
      } catch (e) {
        return false;
      }
      return true;
    },
  },
}
</script>