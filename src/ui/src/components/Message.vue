<template>
  <v-container>
    <v-row class="text-center">
      <v-col cols="12" class="mb-4">
        <h2 class="display-2 font-weight-bold">Publish Messages</h2>
      </v-col>
    </v-row>
    <v-row>
      <v-col cols="12" class="mb-4">
        <v-card subtitle="Trigger backend to publish messages" class="mx-auto pa-4" max-width="400">
        <v-text-field label="Title" v-model="notificationTitle"></v-text-field>
        <v-text-field label="Message (optional)" v-model="notificationMessage"></v-text-field>
        <v-select :items="icons" label="Icons (Optional)" v-model="notificationIcon">
           <template v-slot:selection="{ props, item }">
              <v-list-item v-bind="props" :title="item.raw.name" :prepend-avatar="item.raw.image" :class="item.raw.image ? 'font-regular' : 'font-italic'"></v-list-item>
           </template>
           <template v-slot:item="{ props, item }">
             <v-list-item v-bind="props" :key="item.raw.image" :title="item.raw.name" :prepend-avatar="item.raw.image" :class="item.raw.image ? 'font-regular' : 'font-italic'"></v-list-item>
           </template>
        </v-select>
        <v-text-field label="URL to open when clicked (optional)" v-model="notificationUrl"></v-text-field>
        <v-btn class="ma-2" @click="publish" :disabled='!canPublish'>Publish</v-btn>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
//import { ref } from 'vue'
import { postMessage } from "../services/Publishing.js";

export default {
  name: 'Message',
  data() {
    const defaultIcon = { name: 'Default', image: '' };
    return {
      notificationTitle: "Notification title",
      notificationMessage: "",
      notificationIcon: defaultIcon,
      notificationUrl: "",
      icons: [
        defaultIcon,
        { name: 'Megaphone', image: '/icons/megaphone-announce-red-rounded-19162.png' },
        { name: 'Bell', image: '/icons/notification-bell-13116.png' },
        { name: 'Siren', image: '/icons/siren-14176.png' },
      ],
    }
  },
  computed: {
    canPublish() {
      return this.notificationTitle.length > 0;
    }
  },
  methods: {
    async publish() {
      const msg = {};
      msg.title = this.notificationTitle;
      if (this.notificationMessage) {
        msg.message = this.notificationMessage;
      }
      if (this.notificationIcon.image) {
        msg.icon = this.notificationIcon.image;
      }
      if (this.notificationUrl) {
        msg.url = this.notificationUrl;
      }
      postMessage(msg);
    },
  },
}
</script>