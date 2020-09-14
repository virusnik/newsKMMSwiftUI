class NetworkConfig {
    companion object shared{
        val apiUrl = "newsapi.org"
        val apiKey = "efa12c02cba74bcfbfc150cb9e9c4b3b"

        val header: HashMap<String, String> =  hashMapOf("X-Api-Key" to apiKey)
    }


}