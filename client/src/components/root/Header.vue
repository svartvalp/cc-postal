<template>
  <v-app-bar
      app
      color="amber lighten-3"
      dark
  >
    <v-app-bar-nav-icon color="black" v-if="isAuthenticated" v-on:click=clickHandling></v-app-bar-nav-icon>
    <v-toolbar-title class="black--text">ПочтаЛён</v-toolbar-title>

  </v-app-bar>
</template>

<script>
export default {
  props: ['isAuthenticated'],
  methods: {
    clickHandling() {
      this.$emit('drawBar')
    }
  },
  computed: {
    hasToken() {
      return localStorage.getItem('jwt') !== null
    },
  }
}
</script>
<style>
.black--text {
  font-size: 16pt;
}
</style>
