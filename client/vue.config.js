let host = process.env.HOST || 'localhost'

module.exports = {
    devServer: {
        port: 8085,
        proxy: `http://${host}:8080`
    }
}
