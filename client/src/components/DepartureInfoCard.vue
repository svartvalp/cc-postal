<template>
  <v-col cols="12">
    <v-card class="mx-auto" max-width="600">
      <v-alert color="black" outlined>
        <v-card-title>
          <label for="param">{{ paramName }}</label>
        </v-card-title>
        <v-card-text id="param">{{ paramValue }}</v-card-text>
      </v-alert>
    </v-card>
  </v-col>
</template>

<script>
export default {
  props: ['paramName', 'paramValue']
}
</script>