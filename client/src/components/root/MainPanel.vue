<template>
    <v-main>
        <v-container
                class="fill-height"
        >
            <v-row>
                <router-view v-bind:user="user" @login="login" @logout="logout" @update-user="updateUser"/>
            </v-row>
        </v-container>
    </v-main>
</template>
<script>
    export default {
        components: {},
        props: {
            user: Object
        },
      mounted() {
      },
      methods: {
            login(user) {
                this.$emit('login', user)
            },
            logout() {
                this.$emit('logout')
            },
            updateUser(user){
              this.$emit('update-user', user)
            }
        }
    }
</script>