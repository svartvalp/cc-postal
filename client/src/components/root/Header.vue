<template>
        <v-app-bar
                app
                color="amber lighten-3"
                dark
        >
            <v-app-bar-nav-icon v-on:click=clickHandling color="black"></v-app-bar-nav-icon>
            <v-toolbar-title class="black--text">ПочтаЛён</v-toolbar-title>

        </v-app-bar>
</template>

<script>
    export default {
        props: ['drawerState'],
        methods :{
            clickHandling() {
                this.$emit('drawBar', this.drawerState)
            }
        }
    }
</script>
<style>
.black--text {
  font-size: 16pt;
}
</style>
