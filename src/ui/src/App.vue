<template>
  <v-app>
    <v-main>
      <Subscribe/>
      <ManualSubscribe/>
      <Message/>
    </v-main>
  </v-app>
</template>

<script>
import Subscribe from './components/Subscribe.vue'
import Message from './components/Message.vue'
import ManualSubscribe from './components/ManualSubscribe.vue'

export default {
  name: 'App',

  components: {
    Subscribe, Message, ManualSubscribe
  },

  data: () => ({
    //
  }),
}
</script>

