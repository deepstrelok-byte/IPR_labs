package network

fun getImageLink(path: String?): String = "http://${NetworkConfig.host}:${NetworkConfig.port}/${path}"