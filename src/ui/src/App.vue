<template>
  <v-app>
    <v-main>
      <Subscribe/>
      <Message/>
    </v-main>
  </v-app>
</template>

<script>
import Subscribe from './components/Subscribe.vue'
import Message from './components/Message.vue'

export default {
  name: 'App',

  components: {
    Subscribe, Message
  },

  data: () => ({
    //
  }),
}
</script>

