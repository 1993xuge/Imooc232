package retrofit2.adapter.rxjava

import com.xuge.common.log.logger

class GitHubPaging<T> : ArrayList<T>() {
    companion object {
        const val URL_PATTERN = """(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]"""
    }

    private val relMap = HashMap<String, String?>().withDefault { null }

    private val first by relMap
    private val last by relMap
    private val next by relMap
    private val prev by relMap

    val isLast
        get() = last == null

    val hasNext
        get() = next != null

    val isFirst
        get() = first == null

    val hasPrev
        get() = prev != null

    operator fun get(key: String): String? {
        return relMap[key]
    }

    fun setupLinks(link: String) {
        logger.warn("setupLinks: $link")
        Regex("""<($URL_PATTERN)>; rel="(\w+)"""").findAll(link).asIterable()
            .map { matchResult ->
                val url = matchResult.groupValues[1]
                relMap[matchResult.groupValues[3]] = url // next=....
                logger.warn("${matchResult.groupValues[3]} => ${matchResult.groupValues[1]}")
            }
    }

    fun mergeData(paging: GitHubPaging<T>): GitHubPaging<T> {
        addAll(paging)
        relMap.clear()
        relMap.putAll(paging.relMap)
        return this
    }

    override fun toString(): String {
        return "GitHubPaging(relMap=$relMap)"
    }


}