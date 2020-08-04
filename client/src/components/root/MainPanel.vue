<template>
    <v-main>
        <v-container
                class="fill-height"
        >
            <v-row>
                <router-view :user="user"/>
            </v-row>
        </v-container>
    </v-main>
</template>
<script>
export default {
  props : {
    user : Object
  }
}
</script>