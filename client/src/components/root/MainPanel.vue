<template>
    <v-main>
        <v-container
                class="fill-height"
        >
            <v-row>
                <router-view :user="user" @login="login" @logout="logout" />
            </v-row>
        </v-container>
    </v-main>
</template>
<script>
export default {
    components: {
    },
    props : {
        user : Object
    },
    methods: {
        login(user) {
            this.$emit('login', user)
        },
        logout() {
            this.$emit('logout')
        }
    }
}
</script>